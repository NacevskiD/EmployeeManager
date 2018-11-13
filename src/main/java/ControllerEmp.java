import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerEmp implements Initializable {
    String userLogedIn;
    @FXML
    Label loggedInAs;
    @FXML
    TextField firstNameTF;
    @FXML
    TextField lastNameTF;
    @FXML
    TextField idTF;
    @FXML
    TextField phoneTF;
    @FXML
    TextField emailTF;
    @FXML
    TextField ticketSubmittedTF;
    @FXML
    TextField ticketResolvedTF;
    @FXML
    ComboBox <String>levelComboBox;
    @FXML
    Button backButton;
    @FXML
    Button saveButton;
    @FXML
    Button resetButton;
    @FXML
    Label ticketSubmittedLabel;
    @FXML
    Label ticketResolvedLabel;
    @FXML
    Label ticketInProgressLabel;
    @FXML
    TableView<Ticket> ticketTable;
    @FXML
    TableColumn<Ticket,String> ticketIDCol;
    @FXML
    TableColumn<Ticket,String> ticketStatusCol;
    @FXML
    Button removeButton;
    @FXML


    Employees employees;
    ObservableList<Employees> allEmployees;
    ObservableList<Ticket> allTicket;
    ArrayList<Employees> currentEMP;

    public void setData(String userLogedIn, Employees employees, ObservableList<Employees> allEmployees, ObservableList<Ticket> allTickets, ArrayList<Employees> currentEMP) {
        this.currentEMP = currentEMP;

        this.allEmployees = allEmployees;
        this.userLogedIn = userLogedIn;
        this.employees = employees;
        firstNameTF.setText(employees.getFirstName());
        lastNameTF.setText(employees.getLastName());
        idTF.setText(employees.getId());
        phoneTF.setText(employees.getPhone());
        emailTF.setText(employees.getEmail());
        ticketSubmittedTF.setText(employees.getProblemsSub());
        ticketResolvedTF.setText(employees.getProblemsSol());
        loggedInAs.setText(userLogedIn);

        if (employees.getAuth().equalsIgnoreCase("Manager")) {
            levelComboBox.getSelectionModel().select(0);
        }
        else if(employees.getAuth().equalsIgnoreCase("Employee")) {
            levelComboBox.getSelectionModel().select(1);
        }
        else if (employees.getAuth().equalsIgnoreCase("Technician")) {
            levelComboBox.getSelectionModel().select(2);
        }

        ticketResolvedLabel.setText("Ticket resolved: " +employees.getProblemsSol());
        ticketSubmittedLabel.setText("Ticket submitted: " +employees.getProblemsSub());
        ticketInProgressLabel.setText("In progress: " +Integer.toString(Integer.parseInt(employees.getProblemsSub())- Integer.parseInt(employees.getProblemsSol())));

        this.allTicket = allTickets;



        //lnColumn.setCellValueFactory(new PropertyValueFactory<String,String>("id"));
        //fnColumn.setCellValueFactory(c -> new SimpleStringProperty(new String(test)));
        //employeeList.setItems(test);

        ticketIDCol.setCellValueFactory(new PropertyValueFactory<Ticket, String>("ticketID"));


        ticketStatusCol.setCellValueFactory(new PropertyValueFactory<Ticket, String>("dateResolved"));


        ticketTable.setItems(allTickets);

    }





    public void backButtonPress(){
        Stage appStage = (Stage) backButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGUI.fxml"));
        try {
            Parent root = loader.load();
            ControllerMain controller = loader.getController();
            controller.setUserLogedIn(loggedInAs.getText());
            DB db = new DB();
            final ObservableList<Employees> data2 = FXCollections.observableArrayList(db.getAll());

            controller.populateList(data2,currentEMP);

            Scene scene = new Scene(root);

            appStage.setScene(scene);
            appStage.show();
        }catch (Exception e){
            System.out.println("Can't load scene");
        }

    }

    public void resetButtonPress(){

        firstNameTF.setText(employees.getFirstName());
        lastNameTF.setText(employees.getLastName());
        idTF.setText(employees.getId());
        phoneTF.setText(employees.getPhone());
        emailTF.setText(employees.getEmail());
        ticketSubmittedTF.setText(employees.getProblemsSub());
        ticketResolvedTF.setText(employees.getProblemsSol());
        loggedInAs.setText(userLogedIn);

        if (employees.getAuth().equalsIgnoreCase("Manager")) {
            levelComboBox.getSelectionModel().select(0);
        }
        else if(employees.getAuth().equalsIgnoreCase("Employee")) {
            levelComboBox.getSelectionModel().select(1);
        }
        else if (employees.getAuth().equalsIgnoreCase("Technician")) {
            levelComboBox.getSelectionModel().select(2);
        }
    }

    public void saveButtonPressed() throws SQLException {
        DB db = new DB();
        Stage appStage = (Stage) saveButton.getScene().getWindow();
        db.updateData(firstNameTF.getText(),lastNameTF.getText(),emailTF.getText(),phoneTF.getText(),levelComboBox.getSelectionModel().getSelectedItem(),idTF.getText());

        AlertBox.display("Notification","Data has been updated successfully!");
    }

    public void removeButtonClick() throws Exception{
        DB db = new DB();
        db.deleteItem(Integer.parseInt(employees.getId()));
        AlertBox.display("Notification","Item has been deleted.");

        Stage appStage = (Stage) removeButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGUI.fxml"));
        try {
            Parent root = loader.load();
            ControllerMain controller = loader.getController();
            controller.setUserLogedIn(loggedInAs.getText());
            final ObservableList<Employees> data = FXCollections.observableArrayList(db.getAll());

            controller.populateList(data,currentEMP);

            Scene scene = new Scene(root);

            appStage.setScene(scene);
            appStage.show();
        }catch (Exception e){
            System.out.println("Can't load scene");
        }
    }

    public void ticketTableClick(){
        ticketTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()== 2) {
                    //System.out.println(employeeList.getSelectionModel().selectedIndexProperty().get());
                    //System.out.println();

                    Stage appStage = (Stage) ticketTable.getScene().getWindow();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("TicketGUI.fxml"));
                    try {
                        Parent root = loader.load();
                        ControllerTicket controller = loader.getController();
                        DB db = new DB();
                        //ArrayList<Ticket> allTickets = db.getAllTickets(ticketTable.getSelectionModel().selectedItemProperty().get().getLastName(),employeeList.getSelectionModel().selectedItemProperty().get().getPassword());
                        //final ObservableList<Ticket> tickets = FXCollections.observableArrayList(allTickets);
                        controller.addData(ticketTable.getSelectionModel().selectedItemProperty().get(),currentEMP,allEmployees,userLogedIn,allTicket,employees);
                        Scene scene = new Scene(root);

                        appStage.setScene(scene);
                        appStage.show();

                    }catch (Exception e){
                        System.out.println(e);
                    }

                }
            }
        });}
    @Override
    public void initialize(URL url, ResourceBundle rb){
        levelComboBox.getItems().addAll("Manager","Employee","Technician");
    }
}
