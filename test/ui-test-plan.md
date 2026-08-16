# Console UI test plan

Run this plan with the project-local `test-ui` skill. Expected output is exact except for platform line endings.

## Test case: Add, display, and complete every task type

Aim: Verify that todo, deadline, and event commands create the right task type, retain string-based dates, and work with `mark` and `list`.

### Inputs

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
mark 2
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
  [T][ ] borrow book
Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [D][X] return book (by: Sunday)
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][X] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Keep arbitrary deadline text

Aim: Verify that deadline text is stored as a string without date parsing.

### Inputs

```text
deadline do homework /by no idea :-p
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
  [D][ ] do homework (by: no idea :-p)
Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[D][ ] do homework (by: no idea :-p)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Reject incomplete commands and invalid task numbers

Aim: Verify that required fields and the shared task-number validation report errors without terminating the session.

### Inputs

```text
todo
deadline return book
event meeting /from Monday
mark zero
unmark 1
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
The description of a todo cannot be empty.
____________________________________________________________
____________________________________________________________
Use: deadline DESCRIPTION /by DATE_OR_TIME
____________________________________________________________
____________________________________________________________
Use: event DESCRIPTION /from START /to END
____________________________________________________________
____________________________________________________________
Please enter a task number from the list.
____________________________________________________________
____________________________________________________________
Please enter a task number from the list.
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
