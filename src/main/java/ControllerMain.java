import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {
    String userLogedIn;
    @FXML
    private Label statusBar;
    @FXML
    private TableColumn<Employees,String> fnColumn;
    @FXML
    private TableColumn <Employees,String>lnColumn;
    @FXML
    private TableColumn<Employees,String> eColumn;
    @FXML
    private TableColumn<Employees,String> pColumn;
    @FXML
    private TableView <Employees>employeeList;
    @FXML
    private Button addEmployeeButton;
    @FXML
    private Button addTicketButton;
    @FXML
            Button logOutButton;

    ArrayList<Employees> currentEmp;



    private ObservableList<Employees> allEmployees;

    public void logOutClicked() throws Exception{
        Stage appStage = (Stage) logOutButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginGUI.fxml"));
        Parent root = loader.load();
        ControllerLogin controller = loader.getController();

        Scene scene = new Scene(root);

        appStage.setScene(scene);
        appStage.show();
    }

    public void addTicketClick()throws Exception{
        Stage appStage = (Stage) addTicketButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddTicketGUI.fxml"));
        Parent root = loader.load();
        ControllerAddTicket controller = loader.getController();
        DB db = new DB();

        controller.addData(currentEmp,db.getAll());
        Scene scene = new Scene(root);

        appStage.setScene(scene);
        appStage.show();
    }

    public void addEmployeeClick() throws Exception{
        Stage appStage = (Stage) addEmployeeButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployeeGUI.fxml"));
        Parent root = loader.load();
        ControllerAddEmployee controller = loader.getController();

        controller.setData(allEmployees,userLogedIn,currentEmp);
        Scene scene = new Scene(root);

        appStage.setScene(scene);
        appStage.show();
    }

    public void tableMouseClick(){
        employeeList.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()== 2) {
                    //System.out.println(employeeList.getSelectionModel().selectedIndexProperty().get());
                    //System.out.println();

                    Stage appStage = (Stage) employeeList.getScene().getWindow();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeGUI.fxml"));
                    try {
                        Parent root = loader.load();
                        ControllerEmp controller = loader.getController();
                        DB db = new DB();
                        ArrayList<Ticket> allTickets = db.getAllTickets(employeeList.getSelectionModel().selectedItemProperty().get().getLastName(),employeeList.getSelectionModel().selectedItemProperty().get().getPassword());
                        final ObservableList<Ticket> tickets = FXCollections.observableArrayList(allTickets);
                        controller.setData(userLogedIn,employeeList.getSelectionModel().selectedItemProperty().get(),allEmployees,tickets,currentEmp);
                        Scene scene = new Scene(root);

                        appStage.setScene(scene);
                        appStage.show();

                    }catch (Exception e){
                        System.out.println(e);
                    }


                }
            }
        });
    }

    public void setUserLogedIn(String userLogedIn) {
        this.userLogedIn = userLogedIn;
        statusBar.setText(userLogedIn);
    }

    public void populateList(ObservableList<Employees> allEmployees,ArrayList<Employees> currentEmp){
        this.currentEmp = currentEmp;
        this.allEmployees = allEmployees;


        //lnColumn.setCellValueFactory(new PropertyValueFactory<String,String>("id"));
        //fnColumn.setCellValueFactory(c -> new SimpleStringProperty(new String(test)));
        //employeeList.setItems(test);

        fnColumn.setCellValueFactory(new PropertyValueFactory<Employees, String>("firstName"));


        lnColumn.setCellValueFactory(new PropertyValueFactory<Employees, String>("lastName"));

        eColumn.setCellValueFactory(new PropertyValueFactory<Employees, String>("email"));

        pColumn.setCellValueFactory(new PropertyValueFactory<Employees, String>("auth"));

        employeeList.setItems(allEmployees);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
}

