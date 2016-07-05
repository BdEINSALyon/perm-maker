# Perm Maker
Small Java app to help us organize our duty periods.

## WARNING
This application DOES NOT SAVE anything. It will may be added in a future version.

## How to use ?
### Launch
#### Linux and OS X
```
java -jar perm-maker.jar
```
#### Windows
Double click on the JAR.

### Main Frame
The main frame contains three buttons and a table to show the results.

First click on the "Set planning" button.

### Setting planning
Here you can either use the default planning (click on the button) or create your own.

A planning is a set of tasks. Each task has a name (label), a start and end time (hour:minutes), a number of needed resources, and can be repeated over multiple weekdays.

Once a task is added, it cannot be edited. You must suppress it and add it again.

After validating the planning, the table will show your tasks. You'll see that a unique tasks may be splitted over multiple lines. That's because a task will show one line per resource it needs.

You can still go and edit the planning after validation.

### Setting resources
Here you can create resources. A resource is typically a person.

A person has a name (which will be displayed in the table) and multiple availabilities.

The abilities shown are deducted from the planning you set earlier. It corresponds to a day and a time slot :

- If multiple tasks are the same day and at the same time slot, only one availability will be showned.
- If you have Task A on Monday from 10 to 11 and Task B from 10 to 12, two availabilities will be showned. Selecting only the largest WILL NOT allow the resource to be assigned the smallest.

### Create planning
Once you have all your tasks and resources, you can click the button to assign resources to tasks. Using this function multiple times with the same planning and set of resources will result in different assignments.

