import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
* 
* GUI for Off-campus Housing application
*      @author
*      @version
* 
* List of resources you used:
* 
*/
public class StudentHousing extends Application {
    
    private int noOfRooms;
    private HousemateList list;   // important variable to the model, keep track of all
                                // the info entered, modified and to display
    
    // Table view to see the housemates and room numbers
    private final TableView<Housemate> table = new TableView<>();
    private final ObservableList<Housemate> data = 
        FXCollections.observableArrayList();
    final HBox hb = new HBox();

    // WIDTH and HEIGHT of GUI stored as constants 
    private final int WIDTH = 550;  // arbitrary number: change and update comments
    private final int HEIGHT = 400;

    HashMap<Integer, Housemate> filled = new HashMap<Integer, Housemate>();
    
    // visual components to COMPLETE, starting code example
    // to have partial code handler working
    
    //private Label title = new Label("Hello");
    
    private Button saveAndQuitButton  = new Button("Save and Quit");
    private Button listButton  = new Button("A List"); 
    
    private TextArea displayArea1  = new TextArea();  // bad name, but use in handler code
    private TextArea displayArea2;
    
    
    
    
    @Override
    /** Initialises the screen 
    *  @param stage:   The scene's stage 
    */
    public void start(Stage stage) {

        GridPane aPane = new GridPane();

        aPane.setPadding(new Insets(10));

        noOfRooms = getNumberOfRooms(); // call private method below for window
        // that takes in number of rooms in house 
        
        // The table view
        table.setItems(data);

        TableColumn roomNumCol = new TableColumn("Room #");
        roomNumCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        roomNumCol.setPrefWidth(50);

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);

        table.getColumns().addAll(roomNumCol, nameCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        aPane.add(table, 0, 1);

        // create some HBoxes
        HBox aLine = new HBox(10);
        HBox anotherLine = new HBox(10);
        
        // customize a button example
        listButton.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, new CornerRadii(10), Insets.EMPTY)));
        
        // add components to HBoxes
        aLine.getChildren().addAll(saveAndQuitButton);
        anotherLine.getChildren().addAll(listButton);
                
       // set font of heading
        Font font = new Font("Calibri", 16);
        TextField name = new TextField(); 

        // add all components to VBox

        // TableView housemateList = new TableView<>();

        Integer[] lRooms = new Integer[noOfRooms];
        for(int i = 0; i<noOfRooms; i++){
            lRooms[i] = i+1;
        } 

        // Creating the list
        list = new HousemateList(noOfRooms);

        // Adding name and room number
        HBox selectRoom = new HBox();

        Label rms = new Label("Room:");

        rms.setFont(font);

        HBox selectName = new HBox();

        rms.setPadding(new Insets(0, 10, 0, 0));

        Label nm = new Label("Name:");

        nm.setFont(font);

        nm.setPadding(new Insets(0, 10, 0, 0));

        ObservableList<Integer> theRooms = FXCollections.observableArrayList(lRooms);
        ComboBox<Integer> roomBox = new ComboBox<Integer>(theRooms);

        selectRoom.getChildren().addAll(rms, roomBox);
        selectName.getChildren().addAll(nm, name);

        aPane.add(selectName, 0, 0);

        aPane.add(selectRoom, 1, 0);

        HBox btns = new HBox();

        Button addButton = new Button("ADD");

        addButton.setOnAction(e -> {
            if(!name.getText().isEmpty() && !(roomBox.getValue() == null) && !filled.containsKey(roomBox.getValue())){
                Housemate test = new Housemate(name.getText(), roomBox.getValue());
                list.addHousemate(test);
                filled.put(roomBox.getValue(), test);
                data.add(test);
                name.setText("");
                roomBox.setValue(null);
        }
        });
        
        Button removeButton = new Button("REMOVE");

        removeButton.setOnAction(e -> {
            if(!(roomBox.getValue() == null) && filled.containsKey(roomBox.getValue())){
                data.remove(filled.get(roomBox.getValue()));
                filled.remove(roomBox.getValue());
                roomBox.setValue(null);
        }
        });

        btns.getChildren().addAll(addButton, removeButton);

        aPane.add(btns, 2, 0);
        
        // create the scene
        Scene scene = new Scene(aPane, WIDTH, HEIGHT);

        aPane.setHgap(10);
        aPane.setVgap(10);
      
        // call private methods for button event handlers
        // you will need one for each button added: call and complete all the ones provided
        
        stage.setScene(scene);
        stage.setTitle("Off-campus Houseing Application");
        stage.show(); 
        
    }
    
    /**
    * Method to request number of house rooms from the user
    * @return number of rooms
    */
    private int getNumberOfRooms() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("How many rooms are in the house?");
        dialog.setTitle("House Attributes");
        
        String response = dialog.showAndWait().get(); 
        return Integer.parseInt(response);  
    }
    
    /**
    * Method to initialize housemate list from file
    * Need to be called at the beginning of application to implement
    * save to file feature
    */
    private void initFromFile() {
        list  = new HousemateList(noOfRooms);   
        HousemateFileHandler.readRecords(list);
    }
    
    /**
    * Method to display housemate list in text area
    * Need to be called and completed 
    */
    public void displayHandler() {
        // A message for empty house should be displayed when appropriate
        // so user get some feedback 
        if (!list.isEmpty()) {
            // header
            displayArea1.setText("Room" +  "\t" +  "Name" +  "\n");
            // rest of info missing...
            displayArea1.appendText("To be completed \n");
        }
    }
    
    
    /**
    * Method to display payment list in text are for a particular room:
    *          !!! right now hard-coded for room 1 !!!!
    * Need be called, modified and completed to handle errors
    */
    private void listPaymentHandler() {  
        // List payments for hard-coded room 1
        // Instead of 1 should be replaced by a variable connected to a widget
        Housemate t =  list.search(1);   
        
        PaymentList p  = t.getPayments();
        if(t.getPayments().getTotal() == 0) {
            displayArea2.setText("No payments made for this housemate");
        } 
        else {  
            //The NumberFormat class is similar to the DecimalFormat class that we used previously.
            //The getCurrencyInstance method of this class reads the system values to find out 
            //which country we are in, then uses the correct currency symbol 
            /*NumberFormat nf =  NumberFormat.getCurrencyInstance();
            String s;
            displayArea2.setText("Month" +  "\t\t" +  "Amount" +  "\n");
            for (int i =  1; i <=  p.getTotal(); i++  ) {
                s =  nf.format(p.getPayment(i).getAmount());
                displayArea2.appendText("" + p.getPayment(i).getMonth() +  "\t\t\t" + s + "\n");
            } 
            displayArea2.appendText("\n" + "Total paid so far :   " + 				
            nf.format(p.calculateTotalPaid()));*/
        }
    }
    
    private void saveAndQuitHandler() {
        HousemateFileHandler.saveRecords(noOfRooms, list);
        Platform.exit();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

