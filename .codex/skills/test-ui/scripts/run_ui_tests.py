#!/usr/bin/env python3
"""Run the console UI test cases recorded in a Markdown test plan."""

from __future__ import annotations

import argparse
import re
import shutil
import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[4]
DEFAULT_PLAN = ROOT / "test" / "ui-test-plan.md"
BUILD_DIRECTORY = ROOT / "_temp" / "ui-test-classes"


def normalize(text: str) -> str:
    """Make platform line endings comparable while preserving all content."""
    return text.replace("\r\n", "\n").replace("\r", "\n")


def parse_test_cases(plan: Path) -> list[tuple[str, str, str]]:
    """Return name, input, and expected output from each documented test case."""
    content = plan.read_text(encoding="utf-8")
    sections = re.split(r"(?m)^## Test case: (.+)$", content)
    if len(sections) == 1:
        raise ValueError("No test cases found; use headings such as '## Test case: Add task'.")
    cases = []
    pattern = r"(?ms)^### (Inputs|Expected output)\s*\n```text\n(.*?)\n```\s*$"
    for index in range(1, len(sections), 2):
        name, section = sections[index].strip(), sections[index + 1]
        blocks = {kind: body for kind, body in re.findall(pattern, section)}
        missing = {"Inputs", "Expected output"} - blocks.keys()
        if missing:
            raise ValueError(f"{name}: missing {', '.join(sorted(missing))} text block(s).")
        cases.append((name, normalize(blocks["Inputs"]), normalize(blocks["Expected output"]) + "\n"))
    return cases


def compile_program() -> None:
    """Compile project sources into an isolated temporary test-class directory."""
    source_files = sorted((ROOT / "src" / "main" / "java").glob("*.java"))
    if not source_files:
        raise RuntimeError("No Java source files found in src/main/java.")
    shutil.rmtree(BUILD_DIRECTORY, ignore_errors=True)
    BUILD_DIRECTORY.mkdir(parents=True)
    result = subprocess.run(["javac", "-d", str(BUILD_DIRECTORY), *map(str, source_files)], cwd=ROOT,
                            text=True, capture_output=True, check=False)
    if result.returncode:
        raise RuntimeError(f"Compilation failed:\n{result.stdout}{result.stderr}")


def run_case(inputs: str) -> str:
    """Run one clean application session and return its console output."""
    result = subprocess.run(["java", "-cp", str(BUILD_DIRECTORY), "CBT"], input=inputs + "\n", cwd=ROOT,
                            text=True, capture_output=True, check=False)
    if result.returncode:
        raise RuntimeError(f"Program exited with code {result.returncode}:\n{result.stderr}")
    return normalize(result.stdout)


def show_block(label: str, value: str) -> None:
    """Print a clearly delimited transcript block."""
    print(f"--- {label} ---")
    print(value, end="" if value.endswith("\n") else "\n")


def main() -> int:
    """Compile the program and run every documented UI test until one fails."""
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--plan", type=Path, default=DEFAULT_PLAN)
    plan = parser.parse_args().plan.resolve()
    try:
        cases = parse_test_cases(plan)
        compile_program()
    except (OSError, RuntimeError, ValueError) as error:
        print(f"TEST SETUP FAILED: {error}", file=sys.stderr)
        return 2
    for position, (name, inputs, expected) in enumerate(cases, start=1):
        print(f"\n=== Test {position}: {name} ===")
        show_block("Console input", inputs)
        try:
            actual = run_case(inputs)
        except RuntimeError as error:
            print(f"TEST FAILED: {error}", file=sys.stderr)
            return 1
        show_block("Console output", actual)
        if actual != expected:
            show_block("EXPECTED OUTPUT", expected)
            show_block("ACTUAL OUTPUT", actual)
            print(f"TEST FAILED: {name}", file=sys.stderr)
            return 1
        print("RESULT: PASS")
    print(f"\nAll {len(cases)} UI test(s) passed.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())