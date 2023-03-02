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
import javafx.scene.shape.FillRule;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.input.KeyCode;
import java.text.NumberFormat;
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
    private Button addPaymentButton = new Button("Add payment");
    
    private TextArea displayArea1  = new TextArea();  // bad name, but use in handler code
    private TextArea paymentInfo;
    
    
    public void addHousemateToList(String name, int val){
        Housemate test = new Housemate(name, val);
        list.addHousemate(test);
        filled.put(val, test);
        data.add(test);
    }
    
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

        aPane.add(table, 0, 1, 2, 1);

       // set font of heading
        Font font = new Font("Calibri", 16);
        TextField name = new TextField(); 

        // TableView housemateList = new TableView<>();

        Integer[] lRooms = new Integer[noOfRooms];
        for(int i = 0; i<noOfRooms; i++){
            lRooms[i] = i+1;
        } 

        // Creating the list
        list = new HousemateList(noOfRooms);

        // Adding name and room number
        VBox selectRoom = new VBox();

        Label rms = new Label("Room:");

        rms.setFont(font);

        VBox selectName = new VBox();

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

        VBox btns = new VBox();

        Button addButton = new Button("ADD");
        
        addButton.setPrefWidth(75);

        addButton.setOnAction(e -> {
            if(!name.getText().isEmpty() && !(roomBox.getValue() == null) && !filled.containsKey(roomBox.getValue())){
                addHousemateToList(name.getText(), roomBox.getValue());
                name.setText("");
                roomBox.setValue(null);
        }
        });

        roomBox.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER && !name.getText().isEmpty() && !(roomBox.getValue() == null) && !filled.containsKey(roomBox.getValue())){
                addHousemateToList(name.getText(), roomBox.getValue());
                name.setText("");
                roomBox.setValue(null);
        }
        });

        selectName.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER && !name.getText().isEmpty() && !(roomBox.getValue() == null) && !filled.containsKey(roomBox.getValue())){
                addHousemateToList(name.getText(), roomBox.getValue());
                name.setText("");
                roomBox.setValue(null);
        }
        });
        
        Button removeButton = new Button("REMOVE");

        removeButton.setPrefWidth(75);

        removeButton.setOnAction(e -> {
            if(!(roomBox.getValue() == null) && filled.containsKey(roomBox.getValue())){
                data.remove(filled.get(roomBox.getValue()));
                filled.remove(roomBox.getValue());
                roomBox.setValue(null);
            }
            else{
                if(table.getSelectionModel().getSelectedItem() != null){data.remove(table.getSelectionModel().getSelectedItem());}
            }
        });

        btns.getChildren().addAll(addButton, removeButton);

        btns.setSpacing(5);

        aPane.add(btns, 2, 0);

        Button hideP = new Button("Hide Payments");
        hideP.setVisible(false);

        aPane.add(hideP, 3, 0);

        paymentInfo = new TextArea();

        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        
        ObservableList<String> months = FXCollections.observableArrayList(monthNames);

        HBox forPayment = new HBox();

        forPayment.setSpacing(5);

        ComboBox<String> monthBox = new ComboBox<String>(months);

        Label currency = new Label("$");

        currency.setFont(font);

        TextField amount = new TextField();

        forPayment.getChildren().addAll(monthBox, currency, amount);

        addPaymentButton.setOnAction(e -> {
            if(table.getSelectionModel().getSelectedItem() != null && monthBox.getValue() != null && !amount.getText().isEmpty()){
                Housemate h = table.getSelectionModel().getSelectedItem();
                h.makePayment(new Payment(monthBox.getValue(), Integer.parseInt(amount.getText())));
                listPaymentHandler(h);
        }
        });

        table.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.BACK_SPACE && table.getSelectionModel().getSelectedItem() != null) {
                data.remove(table.getSelectionModel().getSelectedItem());
            }
        });

        VBox paymentArea = new VBox();

        paymentArea.setSpacing(5);

        paymentArea.getChildren().addAll(paymentInfo, forPayment, addPaymentButton);

        paymentArea.setVisible(false);

        aPane.add(paymentArea, 2, 1, 2, 1);

        table.setOnMouseClicked(e -> {
            if(!data.isEmpty() && table.getSelectionModel().getSelectedItem() != null){
                listPaymentHandler(table.getSelectionModel().getSelectedItem());
                paymentArea.setVisible(true);
                hideP.setVisible(true);
            }
        });

        hideP.setOnAction(e -> {
            paymentArea.setVisible(false);
            hideP.setVisible(false);
        });
        
        // create the scene
        Scene scene = new Scene(aPane, WIDTH, HEIGHT);

        aPane.setHgap(10);
        aPane.setVgap(10);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
              Platform.exit();
            }
        });



        table.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(11);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(22);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(34);

        aPane.getColumnConstraints().addAll(col1, col2, col3);

        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.ALWAYS);

        aPane.getRowConstraints().addAll(row2);

      
        // call private methods for button event handlers
        // you will need one for each button added: call and complete all the ones provided
        
        stage.setScene(scene);
        stage.setTitle("Off-campus Housing Application");
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
    private void listPaymentHandler(Housemate h) {  
        // List payments for hard-coded room 1
        // Instead of 1 should be replaced by a variable connected to a widget

        PaymentList p  = h.getPayments();
        if(h.getPayments().getTotal() == 0) {
            paymentInfo.setText("No payments made for " + h.getName());
        } 
        else {  
            //The NumberFormat class is similar to the DecimalFormat class that we used previously.
            //The getCurrencyInstance method of this class reads the system values to find out 
            //which country we are in, then uses the correct currency symbol 
            NumberFormat nf =  NumberFormat.getCurrencyInstance();
            String s;
            paymentInfo.setText("Month" +  "\t\t\t" +  "Amount" +  "\n");
            paymentInfo.appendText("\n");
            for (int i =  1; i <=  p.getTotal(); i++  ) {
                s =  nf.format(p.getPayment(i).getAmount());
                paymentInfo.appendText("" + p.getPayment(i).getMonth() +  "\t\t" + s + "\n");
            } 
            paymentInfo.appendText("\n" + "Total paid so far :   " + 				
            nf.format(p.calculateTotalPaid()));
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

