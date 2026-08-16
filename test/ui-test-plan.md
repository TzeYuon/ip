# Console UI test plan

Run this plan with the project-local `test-ui` skill. The expected output is exact apart from Windows, macOS, and Linux line-ending differences.

## Test case: Add a task and list it

Aim: Verify that a new task is saved and shown as incomplete by `list`.

### Inputs

```text
read book
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
added: read book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[ ] read book
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Mark and unmark a task

Aim: Verify that completion status changes are reflected in the confirmation and list output.

### Inputs

```text
submit assignment
mark 1
list
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
added: submit assignment
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [X] submit assignment
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[X] submit assignment
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [ ] submit assignment
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[ ] submit assignment
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Reject an invalid task number

Aim: Verify that marking a task outside the list reports an error and continues safely.

### Inputs

```text
mark 1
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
Please enter a task number from the list.
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
