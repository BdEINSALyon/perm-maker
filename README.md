# Perm Maker
Small Java app to help us organize our duty periods.

## WARNING
This application DOES NOT SAVE anything. It will may be added in a future version.

## How to use ?
### Launch
At startup, the application automatically checks for an update. If an update is available, a dialog is shown to the user.

#### Linux
```
java -jar perm-maker.jar
```
#### Windows
Double click on the JAR.

#### macOS
Open the .app file.

### Main Frame
The main frame contains three buttons and a table to show the results.

First click on the "Set planning" button.

### Planning

#### Create planning
Here you can either use the default planning (click on the button) or create your own.

A planning is a set of tasks. Each task has a name (label), a start and end time (hour:minutes), a number of needed resources, and can be repeated over multiple weekdays.

Once a task is added, it cannot be edited. You must suppress it and add it again.

After validating the planning, the table will show your tasks. You'll see that a unique tasks may be splitted over multiple lines. That's because a task will show one line per resource it needs.

You can still go and edit the planning after validation.

#### Create Doodle
Clicking this button will redirect you to Doodle website with all the fields pre-filled with the planning you created.

#### Save/Load planning
You can save a planning for a later use (for example waiting for Doodle answers). Save the planning to the folder you want.

You can then load the planning by clicking on the button and selecting the planning you saved.

### Resources

#### Create resources
Here you can create resources. A resource is typically a person.

A person has a name (which will be displayed in the table) and multiple availabilities.

The availabilities shown are deducted from the planning you set earlier. It corresponds to a day and a time slot :

- If multiple tasks are the same day and at the same time slot, only one availability will be showned.
- If you have Task A on Monday from 10 to 11 and Task B from 10 to 12, two availabilities will be showned. Selecting only the largest WILL NOT allow the resource to be assigned the smallest.

#### From Doodle
You can import Doodle answers by clicking on the button and selecting the Doodle Excel export file.

The Doodle dates and times need to match the planning you set in the application.

#### Save/Load resources
You can save resources for a later use.

You can then load the resources by clicking on the button and selecting the resources you saved.

### Create planning
Once you have all your tasks and resources, you can click the button to assign resources to tasks. Using this function multiple times with the same planning and set of resources will result in different assignments.

You can then select the cells, copy them (<kbd>Ctrl</kbd>+<kbd>C</kbd>) and paste it in Excel.
