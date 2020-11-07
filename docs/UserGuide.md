# User Guide

- [Introduction](#introduction)
- [Quick Start](#quick-start)
- [Features](#features)
    - [View Help](#view-help-help)
    - [Add a task](#add-a-task-add-or-a)  
    - [List tasks](#list-tasks-list-or-l)  
    - [Mark task as done](#mark-task-as-done-done-or-dn)  
    - [Edit task](#edit-task-edit-or-e)  
    - [Delete task](#delete-task-delete-or-dl)
    - [View task](#view-task-view-or-v)
    - [Exit](#exit-program-exit-or-ex)
    - [Saving the data](#saving-the-data)
- [FAQ](#faq)    
- [Command Summary](#command-summary)  

## Introduction

Welcome and thank you for choosing ATHENA! ATHENA is your Automated Timetable Helper Encourager n' Assistant and is a desktop daily life planner optimized for use via a Command Line Interface (CLI).

ATHENA uses algorithmic optimisation to give you the best timetable that allows you to make the most of your time. With ATHENA, you can save time on planning your timetable and be more efficient with your time!

You can take a look at the table of contents above if you are looking for a specific command, or you can begin by looking at the **[Quick Start](#Quick-Start)** section below!

We hope you enjoy using ATHENA and start being more productive today!

## Quick Start

1. Ensure that you have **Java 11** or above installed.
2. Download the latest version of **ATHENA** from [here](https://github.com/AY2021S1-CS2113T-W12-2/tp/releases).
3. Copy the downloaded Athena.jar into your **Desktop**.
4. Open the terminal/command prompt and enter `cd Desktop`.
5. Then, enter `java -jar Athena.jar`.
6. A welcome message as seen below will be shown:


![welcome screenshot](screenshots/athena%20welcome.jpg)
 

## Features 
Notes about the command format:
* Words in UPPER_CASE are the parameters to be supplied by the user.
e.g. in `add n/NAME`, NAME is a parameter which can be used as: `add n/Assignment1`.
* Parameters can be in any order.
e.g. if the command specifies `n/NAME t/TIME`, `t/TIME n/NAME` is also acceptable.
* Any unknown parameter type entered by the user that does not fit the parameters accepted in a respective feature will be ignored.
e.g `add n/NAME p/PARAMETER`, since `p/` is an unknown parameter type for `add` command, `p/PARAMETER` is ignored.
* If user types in 2 or more parameter description for each parameter type, only the first description will be taken.
e.g. if user types `n/NAME1 n/NAME2`, only `NAME1` will be recorded as the task's name.
* Items in square brackets are optional. e.g `n/NAME  [d/DURATION]` can be used as `n/Task1 d/1` or as `n/Task1`.
(Refer to the Intermediate Usage section of each guide for more information)
* For some commands, if no parameters are specified, the command will execute using the default values for each parameter.
* For dates, the program follows the **DD-MM-YYYY** format.
* For time, the program follows the **HHMM** format.

### View Help: `help`
Prints out a message on how to use ATHENA.

#### Format: `help`

#### Expected output

![help command screenshot](screenshots/athena%20help.jpg)


### Add a task: `add` or `a`
This command adds a task to the planner.

The guide for this command is split into three sections:

* [Basic Usage (add)](#basic-usage-add) (For users new to command line applications)
* [Intermediate Usage (add)](#intermediate-usage-add) (For users comfortable with command line applications)
* [Advanced Usage (add)](#advanced-usage-add) (Extension to *Intermediate Usage (add)*)

#### Basic Usage (add)

In this section, for easier understanding, the `add` command will make use of all parameters.
Users new to command line applications can find it easy to use as long as they follow the description below.

##### Command Format

`add n/NAME t/TIME d/DURATION D/DEADLINE r/RECURRENCE i/IMPORTANCE a/ADDITIONAL-NOTES`

##### Parameters
The `add` command accepts 7 parameters.

* `NAME` is the name of the task.

* `TIME` is the time to start doing this task (**HHMM**). For example, 1100.

* `DURATION` is the expected time taken to complete task (in hours)

* `DEADLINE` is the date to do task by (**DD-MM-YYYY**). For example, 16-09-2020.

* `RECURRENCE` is one of **TODAY**, **MONDAY**, **TUESDAY**, **WEDNESDAY**, **THURSDAY**, **FRIDAY**, **SATURDAY**, **SUNDAY** or a specific date (**DD-MM-YYYY**).

   This is where the user can input a specific date to do the task on.
   If it is not **TODAY** or a specific date, it will be treated as occuring every week. For example, **MONDAY** means happening every Monday.
   
* `IMPORTANCE` is one of **HIGH**, **MEDIUM**, **LOW**.

* `ADDITIONAL-NOTES` is the additional notes of the task.

##### Example Usage

The following shows the output from ATHENA after entering `add n/Assignment1 t/1100 D/16-09-2020 d/2 r/Today i/high a/Refer to lecture notes`.
You should expect to see a message to confirm that the task is added.

![add command screenshot](screenshots/athena%20add.jpg)

#### Intermediate Usage (add)

In this section, parameters in square brackets are optional. e.g `n/NAME  [d/DURATION]` can be used as `n/Task1 d/1` or as `n/Task1`.
Users comfortable with command line applications have a choice now and can find it easy to use as long as they follow the description below.

##### Command Format

`add n/NAME [t/TIME] [d/DURATION] [D/DEADLINE] [r/RECURRENCE] [i/IMPORTANCE] [a/ADDITIONAL-NOTES]`

##### Parameters
The `add` command accepts 7 parameters.

* `NAME` is the name of the task. It is a compulsory parameter the user has to input.

This parameter `TIME` is optional only if the task is non-recurring. Else, the user must input a start time for the task.
* `TIME` is the time to start doing this task (**HHMM**). For example, 1100.

   Default: Allocated by Athena (only available for non-recurring tasks)

The following parameters are optional. If they are left empty, ATHENA will use the default value assigned.
* `DURATION` is the expected time taken to complete task (in hours).

   Default: 1 hour.
* `DEADLINE` is the date to do task by (**DD-MM-YYYY**). For example, 16-09-2020.

   Default: No deadline.
* `RECURRENCE` is one of **TODAY**, **MONDAY**, **TUESDAY**, **WEDNESDAY**, **THURSDAY**, **FRIDAY**, **SATURDAY**, **SUNDAY** or a specific date (**DD-MM-YYYY**).

   This is where the user can input a specific date to do the task on. 
   If it is not **TODAY** or a specific date (**DD-MM-YYYY**), it will be treated as occuring every week. For example, **MONDAY** means happening every Monday.
   
   Default: **TODAY**.
* `IMPORTANCE` is one of **HIGH**, **MEDIUM**, **LOW**.

   Default: **MEDIUM**.
* `ADDITIONAL-NOTES` is the additional notes of the task.

   Default: No notes.

##### Example Usage

The following shows the output from ATHENA after entering `add n/Assignment2 t/1400 i/high`.
You should expect to see a message to confirm that the task is added with some of the parameter's default value assigned.

*TODO: Insert screenshot*

#### Advanced Usage (add)

This section is an extension to the [Intermediate Usage (add)](#intermediate-usage-add) section.

The `add` command supports shortcuts for advanced users.

##### Supported Shortcuts

The following commands on the left will be expanded to the corresponding commands on the right.

| Shortcut | Expanded command |
|----------|------------------|
| `a`     | `add`  |

##### Example usage

The user can just type `a` instead of `add`.
The following shows the output from ATHENA after entering `a n/Assignment3 t/1900 D/16-09-2020 d/2 a/Refer to notes`.
You should expect to see a message to confirm that the task is added with some of the parameter's default value assigned.

*TODO: Update screenshot*


### List tasks: `list` or `l`

This command shows your tasks organized nicely in a timetable.
Each task will be printed with a number that is used as an identifier for other commands (e.g. `edit`, `done`, `delete`).

*TODO: Insert screenshot*

| :bulb:  If the timetable is too big to fit in the screen, consider using a smaller font size in your terminal/command prompt, or stretch your window to full width. |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

The guide for this command is split into three sections:

* [Basic Usage](#basic-usage) (For users new to command line applications)
* [Intermediate Usage](#intermediate-usage) (For users comfortable with command line applications)
* [Advanced Usage](#advanced-usage) (Extension to *Intermediate Usage*)

#### Basic Usage

The `list` command accepts 2 parameters.
You can tell the command to filter your tasks based on

* the range of days you want to see
* the importance of the tasks

##### Command Format

`list f/FORECAST i/IMPORTANCE`

* `FORECAST`: **DAY** to show the tasks today, **WEEK** to show the tasks one week from now, **ALL** to show the tasks within one month from now.

* `IMPORTANCE`: One of **HIGH**, **MEDIUM**, **LOW**, **ALL**.

##### Example Usage

The following shows the output from ATHENA after entering `list f/Week i/High`.
You should expect to see all **high importance tasks** occuring **within one week from now**.

*TODO: Insert screenshot*

#### Intermediate Usage

The `list` command accepts 2 parameters.
You can tell the command to filter your tasks based on

* the range of days you want to see
* the importance of the tasks

These parameters are optional. If they are left empty, ATHENA will use the default value assigned.

##### Command Format

`list [f/FORECAST] [i/IMPORTANCE]`

* `FORECAST`: **DAY** to show the tasks today, **WEEK** to show the tasks within one week from now, **ALL** to show the tasks within one month from now.

  Default: **WEEK**.

* `IMPORTANCE`: One of **HIGH**, **MEDIUM**, **LOW**, **ALL**.

  Default: **ALL**.

##### Example Usage

The following shows the output from ATHENA after entering `list f/Week i/High`.
You should expect to see all **high importance tasks** occuring **within one week from now**.

*TODO: Insert screenshot*

Alternatively, you can leave ATHENA to use the default values as specified above.
For example, entering `list i/Low` will show all **low importance** tasks occuring **within one week from now**.

*TODO: Insert screenshot*

#### Advanced Usage

This section is an extension to the [Intermediate Usage](intermediate-usage) section.

The `list` command supports shortcuts for advanced users.

##### Supported Shortcuts

The following commands on the left will be expanded to the corresponding commands on the right.

| Shortcut | Expanded command |
|----------|------------------|
| `l3`     | `list i/HIGH`    |
| `l2`     | `list i/MEDIUM`  |
| `l1`     | `list i/LOW`     |
| `lw`     | `list f/WEEK`    |
| `ld`     | `list f/DAY`     |
| `la`     | `list f/ALL`     |

##### Example usage

The following are all valid commands.

* `l`
* `ld`
* `list f/WEEK i/medium`
* `l2 f/WEEK`

You can enter `l2 f/DAY` to list **medium importance** tasks occuring **today**.

*TODO: Update screenshot*

### Mark task as done: `done` or `dn`
Mark the specified task from the planner as done.

#### Format
`done INDEX`

* `INDEX` refers to the index number shown in the displayed task list. It must be a positive integer.

#### Example usage

* `done 0`
* `dn 0` 

#### Expected output

![done command screenshot](screenshots/athena%20done.jpg)


### Edit task: `edit` or `e`
This command edits a specific task in the planner.

The guide for this command is split into three sections:

* [Basic Usage (edit)](#basic-usage-edit) (For users new to command line applications)
* [Intermediate Usage (edit)](#intermediate-usage-edit) (For users comfortable with command line applications)
* [Advanced Usage (edit)](#advanced-usage-edit) (Extension to *Intermediate Usage (edit)*)

#### Basic Usage (edit)

In this section, for easier understanding, the `edit` command will make use of all parameters.
Users new to command line applications can find it easy to use as long as they follow the description below.

##### Command Format

`edit TASK-ID n/NAME t/TIME d/DURATION D/DEADLINE r/RECURRENCE i/IMPORTANCE a/ADDITIONAL-NOTES`

##### Parameters
The `edit` command accepts 8 parameters.

* `TASK-ID` refers to the number shown beside the task that the user wants to edit in the displayed task list. It must be a non-negative integer.

At least one parameter shown below have to be edited:
* `NAME` is the name of the task.

* `TIME` is the time to start doing this task (**HHMM**). For example, 1100.

* `DURATION` is the expected time taken to complete task (in hours).

* `DEADLINE` is the date to do task by (**DD-MM-YYYY**). For example, 16-09-2020.

* `RECURRENCE` is one of **TODAY**, **MONDAY**, **TUESDAY**, **WEDNESDAY**, **THURSDAY**, **FRIDAY**, **SATURDAY**, **SUNDAY** or a specific date (**DD-MM-YYYY**).

* `IMPORTANCE` is one of **HIGH**, **MEDIUM**, **LOW**.

* `ADDITIONAL-NOTES` is the additional notes of the task.

##### Example Usage

Firstly, use [list command](#list-tasks-list-or-l) to get the `TASK-ID` of the task to be edited.

The following shows the output from ATHENA after entering `edit 1 n/Assignment2 t/1800 D/17-09-2020 d/1 r/16-09-2020 i/low a/Refer to notes`.
You should expect to see a message to confirm that the task with index 1 is edited.

![edit command screenshot](screenshots/athena%20edit.jpg)

#### Intermediate Usage (edit)

In this section, parameters in square brackets are optional. e.g `TASK-ID [n/NAME] [d/DURATION]` can be used as `1 n/Task1 d/1` or as `1 n/Task1`.
Users comfortable with command line applications have a choice now and can find it easy to use as long as they follow the description below.

##### Command Format

`edit TASK-ID [n/NAME] [t/TIME] [d/DURATION] [D/DEADLINE] [r/RECURRENCE] [i/IMPORTANCE] [a/ADDITIONAL-NOTES]`

##### Parameters
The `edit` command accepts 8 parameters.

* `TASK-ID` refers to the number shown beside the task that the user wants to edit in the displayed task list. It must be a non-negative integer.

At least one parameter shown below have to be edited:
* `NAME` is the name of the task.

* `TIME` is the time to start doing this task (**HHMM**). For example, 1100.

* `DURATION` is the expected time taken to complete task (in hours).

* `DEADLINE` is the date to do task by (**DD-MM-YYYY**). For example, 16-09-2020.

* `RECURRENCE` is one of **TODAY**, **MONDAY**, **TUESDAY**, **WEDNESDAY**, **THURSDAY**, **FRIDAY**, **SATURDAY**, **SUNDAY** or a specific date (**DD-MM-YYYY**).

* `IMPORTANCE` is one of **HIGH**, **MEDIUM**, **LOW**.

* `ADDITIONAL-NOTES` is the additional notes of the task.

##### Example Usage

Firstly, use [list command](#list-tasks-list-or-l) to get the `TASK-ID` of the task to be edited.

The following shows the output from ATHENA after entering `edit 1 t/1800`.
You should expect to see a message to confirm that the time of task with `TASK-ID` '1' is edited.

*TODO: Insert screenshot*

#### Advanced Usage (edit)

This section is an extension to the [Intermediate Usage (edit)](#intermediate-usage-edit) section.

The `edit` command supports shortcuts for advanced users.

##### Supported Shortcuts

The following commands on the left will be expanded to the corresponding commands on the right.

| Shortcut | Expanded command |
|----------|------------------|
| `e`     | `edit`  |

##### Example usage

Firstly, use [list command](#list-tasks-list-or-l) to get the `TASK-ID` of the task to be edited.

The user can just type `e` instead of `edit`.
The following shows the output from ATHENA after entering `e 1 n/Assignment2 t/1800 D/17-09-2020 d/1 r/today i/high a/Refer to lecture notes`.
You should expect to see a message to confirm that the time of task with `TASK-ID` '1' is edited.

*TODO: Update screenshot*


### Delete task: `delete` or `dl`
Deletes the specified task from the planner. ATHENA will ensure to leave the index of other tasks untouched.

* [Basic Usage (delete)](#basic-usage-delete) (For users new to command line applications)
* [Intermediate Usage (delete)](#intermediate-usage-delete) (For users comfortable with command line applications)

#### Basic Usage (delete)

##### Command Format

`delete TASK-ID`

##### Parameters
The `delete` command requires 1 parameter only.

* `TASK-ID` refers to the number shown beside the task that the user wants to delete in the displayed task list. It must be a non-negative integer.

##### Example Usage

Firstly, use the [list command](#list-tasks-list-or-l) to get the `TASK-ID` of the task to be deleted.
The following shows the output from ATHENA after entering `delete 0`.
You should expect to see a message to confirm that the task with `TASK-ID` of `0` is deleted. 
ATHENA also prints the command required to restore the deleted task.

*SCREENSHOT*

#### Intermediate Usage (delete)
The `delete` command supports shortcuts for intermediate users.

##### Supported Shortcuts

The following commands on the left will be expanded to the corresponding commands on the right.

| Shortcut | Expanded command |
|----------|------------------|
| `dl`     | `delete`  |

##### Example usage

Firstly, use the [list command](#list-tasks-list-or-l) to get the `TASK-ID` of the task to be deleted.

The user can just type `dl` instead of `delete`.
The following shows the output from ATHENA after entering `dl 0`.

*insert screenshot*


### View task: `view` or `v`
Views the specified task details from the planner.
The guide for this command is split into three sections:

* [Basic Usage (view)](#basic-usage-view) (For users new to command line applications)
* [Intermediate Usage (view)](#intermediate-usage-view) (For users comfortable with command line applications)

#### Basic Usage (view)

##### Command Format

`view TASK-ID`

##### Parameters
The `view` command requires 1 parameter only.

* `TASK-ID` refers to the number shown beside the task that the user wants to view in the displayed task list. It must be a non-negative integer.

##### Example Usage

Firstly, use the [list command](#list-tasks-list-or-l) to get the `TASK-ID` of the task to be viewed.
The following shows the output from ATHENA after entering `view 0`.
You should expect to see the details of your task with `TASK-ID` of `0`.

*SCREENSHOT*

#### Intermediate Usage (view)
The `view` command supports shortcuts for intermediate users.

##### Supported Shortcuts

The following commands on the left will be expanded to the corresponding commands on the right.

| Shortcut | Expanded command |
|----------|------------------|
| `v`     | `view`  |

##### Example usage

Firstly, use the [list command](#list-tasks-list-or-l) to get the `TASK-ID` of the task to be edited.

The user can just type `v` instead of `view`.
The following shows the output from ATHENA after entering `v 0`.

![view command screenshot](screenshots/athena%20view.jpg)


### Exit program: `exit` or `ex`
Exits the program.

#### Format: `exit` or `ex`

#### Expected output:

![exit command screenshot](screenshots/athena%20exit.jpg)


### Saving the data
Your tasks are automatically saved in *data.csv* which is located next to the program jar file. There is no need to save manually.  
   
## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: You can copy the *data.csv* file created next to ATHENA.jar to the other computer and place it next to ATHENA.jar there.


**Q**: Can I make flexible tasks into fixed tasks and vice versa? 

**A**: No. Once a task is set without a time, ATHENA is given control over its time allocation permanently. One workaround is deleting the task and making a new one

**Q**: What happens if I do not mark tasks as done? 

**A**: For fixed tasks, it will just record that you did not do that task. But for flexible tasks, ATHENA will allocate it again at a later date so that you can have another opportunity to work on it.

**Q**: ATHENA is allocating tasks on time that has already passed? 

**A**: ATHENA is best used in the morning before you start doing your tasks. The program is best suited for weekly planning. If you have tasks that need immediate attention, it is reccomended that you start on those things immediately. Alternatitively, putting dummy rest tasks can also aid ATHENA in understanding which time slots can be used.


**Q**: ATHENA is not allocating some of my tasks? 

**A**: ATHENA works on a best effort basis. If there is no possible configuration for the tasks, it will not assign those tasks. Try to split up bigger tasks into smaller ones or reduce the amount of work you do in a sitting.

**Q**: The timetable is showing up weirdly? 

**A**: Try using a higher resolution screen, that should make the timetable easier to see.

**Q**: Is it possible to freeze flexible tasks? 

**A**: As mentioned previously, it is not possible to convert flexible tasks. However, marking the task as done will prevent ATHENA from making further changes to its timing.
## Command Summary

| Action            | Format                                                                                                      |
| :---------------- | :---------------------------------------------------------------------------------------------------------- |
| Help              | `help`                                                                                                      |
| Add task          | `add n/NAME [t/TIME] [d/DURATION] [D/DEADLINE] [r/RECURRENCE] [i/IMPORTANCE] [a/ADDITIONAL-NOTES]`            |
| List tasks        | `list [f/FORECAST] [i/IMPORTANCE]`                                                                          |
| Mark task as done | `done INDEX`                                                                                                |
| Edit task         | `edit TASK-ID [n/NAME] [t/TIME] [d/DURATION] [D/DEADLINE] [r/RECURRENCE] [i/IMPORTANCE] [a/ADDITIONAL-NOTES]` |
| Delete task       | `delete INDEX`                                                                                              |
| View task         | `view INDEX`                                                                                                |
| Exit program      | `exit`                                                                                                      |
