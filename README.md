# Student Off Campus Housing

## Description

- This application allows the user to keep track of who is in their house and when they have paid. Specifically, it allows the user to add student names and rooms, remove students, add student payments per student, and load and save previous student housing lists.

- When the application is first opened, a pop window appears where the user can input the number of rooms in their house.
- Then, once the number of rooms is inputed, the main screen appears.
At the top, their is a text field and a combo box that allows the user to input a student name and a room number. The combo box will only show the number of rooms available. 
- The user can then click the add housemate button, or remove housemate button. The add button will not work if both fields are not filled out, and the remove button will not work unless the room number is filled out.

- In order to store and display the added housemates, we use an observable list, which is displayed in a table view.
- The table view allows the user to see the student name and room number in a row.
When the user clicks a row, a payment text area appears next to the table view, which allows the user to see that student's payments.

- The text area shows the month in which the student paid, and how much the student paid that month.
There is an add payment buttton beneath the text area. The payment button takes a month from a combo box and an amount from a text field, and then adds those values to the payment text area for that student.
- There is also a hide payment button above the text area that hides the payment text area, button, combo box, and text field.

- At the bottom right of the screen, their is a save and a load button which allowd the user to save the housemate list to, and load the housemate list from a file. 



## Authors

Diego Abanto Ibarguen
### Contributions:
- Research, Payment text area, payment buttons, payment combo box, clickable table view 


Jack Donovan
### Contributions:
- Reasearh, style sheets, load and save functions.

## Combined Contributions during lab:
- Housemate Table view, combo boxes, and text area. Add housemate and remove housemate buttons.

## Acknowledgments

Inspiration, code snippets, etc.
* [CSS] https://www.w3.org/wiki/CSS/Properties/color/keywords
* [TableViewCSS]https://edencoding.com/style-tableview-javafx/
* [TableView]http://www.java2s.com/Tutorials/Java/JavaFX/0650__JavaFX_TableView.htm 
* [ComboBox](https://docs.oracle.com/javafx/2/ui_controls/combo-box.htm 
* https://stackoverflow.com/questions/17388866/getting-selected-item-from-a-javafx-tableview
* 
