# Orbit User Guide

// Update the title above to match the actual product name

// Product screenshot goes here
![Orbit application window](Ui.png)

// Product intro goes here
Orbit is a mission-control task navigator for managing todos, deadlines, and events.

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Feature ABC

// Feature details


## Feature XYZ

// Feature details

## Sorting tasks chronologically

Use `sort date` to reorder the task list from earliest to latest. Deadlines are
ordered by their due date and time, while events are ordered by their start date
and time. Todos have no date, so they are placed after all deadlines and events.
Tasks with the same date and time keep their existing relative order.

Example: `sort date`

```text
Flight plan aligned by date:
1.[E][ ] project meeting (from: Dec 01 2026, 9:00am to: Dec 01 2026, 10:00am)
2.[D][ ] submit report (by: Dec 02 2026, 6:00pm)
3.[T][ ] read a book
```

Sorting changes the task numbers and saves the new order. Commands such as
`mark`, `unmark`, and `delete` therefore use the numbers shown after sorting.
Tasks added later are appended normally; run `sort date` again to reorder them.

## Handling invalid input and data problems

Orbit ignores harmless leading, trailing, and repeated whitespace. It rejects
unexpected command arguments, missing or repeated date markers, duplicate
tasks, invalid task numbers, descriptions containing the reserved `|`
character, and descriptions longer than 300 characters.

Calendar dates are checked strictly, so dates such as February 30 are rejected.
An event's start must also be earlier than its end. If saved data contains a
malformed or duplicate record, Orbit loads the remaining valid tasks and shows
a warning. Saves use a temporary file replacement so an interrupted write is
less likely to damage the existing task file.
