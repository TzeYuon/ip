# CBT User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

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
I've sorted your tasks chronologically:
1.[E][ ] project meeting (from: Dec 01 2026, 9:00am to: Dec 01 2026, 10:00am)
2.[D][ ] submit report (by: Dec 02 2026, 6:00pm)
3.[T][ ] read a book
```

Sorting changes the task numbers and saves the new order. Commands such as
`mark`, `unmark`, and `delete` therefore use the numbers shown after sorting.
Tasks added later are appended normally; run `sort date` again to reorder them.
