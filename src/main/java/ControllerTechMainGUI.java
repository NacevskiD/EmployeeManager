import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerTechMainGUI implements Initializable {

    @FXML
    Label loggedInAs;
    @FXML
    Label ticketInProgress;
    @FXML
    Label ticketsSolved;
    @FXML
    TableColumn<Ticket,String> ticketIDCol;
    @FXML
    TableColumn<Ticket,String> datePostedCol;

    String userLogedIn;
    ObservableList<Ticket> allTicket;

    public void setData(String userLogedIn, Employees employees, ObservableList<Employees> allEmployees, ObservableList<Ticket> allTickets, ArrayList<Employees> currentEMP) {



        this.userLogedIn = userLogedIn;



        loggedInAs.setText(userLogedIn);


        ticketsSolved.setText("Ticket resolved: " +employees.getProblemsSol());


        this.allTicket = allTickets;



        //lnColumn.setCellValueFactory(new PropertyValueFactory<String,String>("id"));
        //fnColumn.setCellValueFactory(c -> new SimpleStringProperty(new String(test)));
        //employeeList.setItems(test);

        ticketIDCol.setCellValueFactory(new PropertyValueFactory<Ticket, String>("ticketID"));


       // ticketStatusCol.setCellValueFactory(new PropertyValueFactory<Ticket, String>("dateResolved"));


        //ticketTable.setItems(allTickets);

    }


    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
}
