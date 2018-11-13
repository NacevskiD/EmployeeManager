import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControllerTicket implements Initializable {
    @FXML
    TextField ticketID;
    @FXML
    TextArea ticketDescriptionText;
    @FXML
    TextField dateAdded;
    @FXML
    TextField dateResolved;
    @FXML
    TextField technician;
    @FXML
    Button backButton;

    Ticket ticket;
    ArrayList<Employees> currentEmp;
    ObservableList<Employees> allEmployees;
    String userLogedIn;
    ObservableList<Ticket> allTickets;
    Employees employees;

    void addData(Ticket ticket,ArrayList<Employees> currentEMP,ObservableList<Employees> allEmployees,String userLogedIn,ObservableList<Ticket> allTickets,Employees employees){
        this.employees = employees;
        this.ticket = ticket;
        this.currentEmp = currentEMP;
        this.allEmployees = allEmployees;
        this.userLogedIn = userLogedIn;
        this.allTickets = allTickets;
        ticketID.setText(ticket.getTicketID());
        ticketDescriptionText.setText(ticket.getDescription());
        dateAdded.setText(ticket.getDateAdded());
        dateResolved.setText(ticket.getDateResolved());
        technician.setText(ticket.getTech());
    }

    public void backButtonClick() throws Exception {
        Stage appStage = (Stage) backButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeGUI.fxml"));
        try {
            Parent root = loader.load();
            ControllerEmp controller = loader.getController();
            DB db = new DB();

            controller.setData(userLogedIn, employees, allEmployees, allTickets, currentEmp);
            Scene scene = new Scene(root);

            appStage.setScene(scene);
            appStage.show();
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
}
