# Console UI test plan

Run this plan with the project-local `test-ui` skill. Expected output is exact except for platform line endings.

Each test starts with an isolated data file. `Setup data` provides its initial contents; `Expected saved data` is checked after the session.

## Test case: Load and save persisted tasks

Aim: Verify that CBT loads every supported task type and completion state at startup, then saves the updated list immediately after a task-list change.

### Setup data

```text
TODO | 1 | read book
DEADLINE | 0 | return book | June 6th
EVENT | 0 | project meeting | Aug 6th 2pm | Aug 6th 4pm
```

### Inputs

```text
list
mark 2
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
Here are the tasks in your list:
1.[T][X] read book
2.[D][ ] return book (by: June 6th)
3.[E][ ] project meeting (from: Aug 6th 2pm to: Aug 6th 4pm)
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [D][X] return book (by: June 6th)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

### Expected saved data

```text
TODO | 1 | read book
DEADLINE | 1 | return book | June 6th
EVENT | 0 | project meeting | Aug 6th 2pm | Aug 6th 4pm
```

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

## Test case: Preserve state after errors on an empty list

Aim: Verify that attempting to mark a missing task does not create one, and that marking and unmarking a later valid task preserves its state correctly.

### Inputs

```text
list
mark 1
todo submit assignment
mark 1
unmark 1
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
Here are the tasks in your list:
____________________________________________________________
____________________________________________________________
Please enter a task number from the list, e.g. mark 1.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] submit assignment
Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] submit assignment
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] submit assignment
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] submit assignment
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

## Test case: Reject invalid commands without changing tasks

Aim: Verify specific errors for malformed commands and task numbers; interleave valid commands to confirm errors do not corrupt the task list.

### Inputs

```text
todo read notes
todo
deadline /by Friday
deadline return book
event meeting /from /to Friday
event meeting /from Monday
mark zero
mark 2
unmark 1
list
unknown
event review /from Monday /to Friday
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
  [T][ ] read notes
Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
The description of a todo cannot be empty. Use: todo DESCRIPTION
____________________________________________________________
____________________________________________________________
Use: deadline DESCRIPTION /by DATE_OR_TIME
____________________________________________________________
____________________________________________________________
Use: deadline DESCRIPTION /by DATE_OR_TIME
____________________________________________________________
____________________________________________________________
Use: event DESCRIPTION /from START /to END
____________________________________________________________
____________________________________________________________
Use: event DESCRIPTION /from START /to END
____________________________________________________________
____________________________________________________________
Please enter a task number from the list, e.g. mark 1.
____________________________________________________________
____________________________________________________________
Please enter a task number from the list, e.g. mark 1.
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] read notes
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read notes
____________________________________________________________
____________________________________________________________
I don't understand that command. Try todo, deadline, event, list, mark, unmark, delete, or bye.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] review (from: Monday to: Friday)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read notes
2.[E][ ] review (from: Monday to: Friday)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Delete a task

Aim: Verify that delete removes the selected task and leaves the remaining task list intact.

### Inputs

```text
todo first task
todo second task
delete 1
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
Noted. I've removed this task:
  [T][ ] first task
Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] second task
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
