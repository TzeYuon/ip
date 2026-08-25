# Console UI test plan

Run this plan with the project-local `test-ui` skill. Expected output is exact except for platform line endings.

Each test starts with an isolated data file. `Setup data` supplies its initial contents; `Expected saved data` is checked after the session.

## Test case: Create, display, find, and save dated tasks

Aim: Verify that deadlines and events parse into dates and times, display in a user-friendly format, are found by date, and are saved without losing 24-hour times.

### Inputs

```text
deadline return book /by 2/12/2019 1800
event project meeting /from 2019-12-02 0900 /to 2019-12-03 1700
date 2019-12-02
list
bye
```

### Expected output

```text
____________________________________________________________
  ____ ____ _____
 / ___| __ )_   _|
| |   |  _ \ | |
| |___| |_) || |
 \____|____/ |_|

Hello! I'm CBT.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Dec 02 2019, 6:00pm)
Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Dec 02 2019, 9:00am to: Dec 03 2019, 5:00pm)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the deadlines and events on Dec 02 2019:
1.[D][ ] return book (by: Dec 02 2019, 6:00pm)
2.[E][ ] project meeting (from: Dec 02 2019, 9:00am to: Dec 03 2019, 5:00pm)
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[D][ ] return book (by: Dec 02 2019, 6:00pm)
2.[E][ ] project meeting (from: Dec 02 2019, 9:00am to: Dec 03 2019, 5:00pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

### Expected saved data

```text
DEADLINE | 0 | return book | 02/12/2019 1800
EVENT | 0 | project meeting | 02/12/2019 0900 | 03/12/2019 1700
```

## Test case: Keep existing task-list actions working

Aim: Verify that date support does not affect creating, marking, unmarking, deleting, and listing todos.

### Inputs

```text
todo first task
todo second task
mark 1
unmark 1
delete 2
list
bye
```

### Expected output

```text
____________________________________________________________
  ____ ____ _____
 / ___| __ )_   _|
| |   |  _ \ | |
| |___| |_) || |
 \____|____/ |_|

Hello! I'm CBT.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] first task
Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] second task
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] first task
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] first task
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
  [T][ ] second task
Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] first task
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Reject invalid dates and event ranges

Aim: Verify that malformed and impossible dates, missing event fields, and an event whose start is after its end do not add tasks.

### Inputs

```text
deadline invalid leap day /by 2019-02-29 0900
event missing end /from 2019-12-02 0900
event backwards /from 2019-12-03 0900 /to 2019-12-02 0900
date 2019-02-29
date
list
bye
```

### Expected output

```text
____________________________________________________________
  ____ ____ _____
 / ___| __ )_   _|
| |   |  _ \ | |
| |___| |_) || |
 \____|____/ |_|

Hello! I'm CBT.
What can I do for you?
____________________________________________________________
____________________________________________________________
Cannot recognize date/time! Example valid formats:
  - 2/12/2019 1800
  - 2-12-2019 1800
  - 2019-12-02 1800
  - 2/12/2019
____________________________________________________________
____________________________________________________________
Use: event DESCRIPTION /from START /to END
____________________________________________________________
____________________________________________________________
The event start date and time cannot be after its end date and time.
____________________________________________________________
____________________________________________________________
Cannot recognize date! Use a date such as 2019-12-02.
____________________________________________________________
____________________________________________________________
Use: date DATE (for example, 2019-12-02)
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Load and query persisted dated tasks

Aim: Verify that canonical saved dates reload as date-time objects and that a multi-day event appears on its final date.

### Setup data

```text
TODO | 0 | read book
DEADLINE | 1 | return book | 02/12/2019 1800
EVENT | 0 | conference | 01/12/2019 0900 | 03/12/2019 1700
```

### Inputs

```text
date 2019-12-03
bye
```

### Expected output

```text
____________________________________________________________
  ____ ____ _____
 / ___| __ )_   _|
| |   |  _ \ | |
| |___| |_) || |
 \____|____/ |_|

Hello! I'm CBT.
What can I do for you?
____________________________________________________________
____________________________________________________________
Here are the deadlines and events on Dec 03 2019:
3.[E][ ] conference (from: Dec 01 2019, 9:00am to: Dec 03 2019, 5:00pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
