import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControllerAddTicket implements Initializable {
    @FXML
    Button submitButton;
    @FXML
    TextArea descriptionText;

    ArrayList<Employees> employees;
    ArrayList<Employees> allEmployees;

    public void submitButtonClicked() throws Exception{
        DB db = new DB();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        ArrayList<Employees> techs = db.getTech();
        Random randomGenerator= new Random();
        int x = randomGenerator.nextInt(techs.size());
        Employees techEmp = techs.get(x);
        db.addTicket(employees.get(0).getId(),employees.get(0).lastName,employees.get(0).getPassword(),descriptionText.getText(),strDate,techEmp.getLastName()+techEmp.getId(),employees.get(0).getProblemsSub());
        AlertBox.display("Notification","Ticket added");


        Stage appStage = (Stage) submitButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGUI.fxml"));
        Parent root = loader.load();
        ControllerMain controller = loader.getController();
        controller.setUserLogedIn("Logged in as: " + employees.get(0).getFirstName() + " " + employees.get(0).getLastName()+ "/" + employees.get(0).getAuth());
        final ObservableList<Employees> data = FXCollections.observableArrayList(allEmployees);
        controller.populateList(data,employees);

        Scene scene = new Scene(root);

        appStage.setScene(scene);
        appStage.show();
    }

    public void addData(ArrayList<Employees> employees,ArrayList<Employees> allEmployees){
        this.employees = employees;
        this.allEmployees = allEmployees;
    }

    public void initialize(URL url, ResourceBundle rb){

    }

}
