import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControllerAddEmployee implements Initializable {
    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField emailField;
    @FXML
    TextField phoneField;
    @FXML
    PasswordField passwordField;
    @FXML
    ComboBox<String> authComboBox;
    @FXML
    Button cancelButton;
    @FXML
    Button saveButton;

    String user;

    ObservableList<Employees> allEmployees;
    ArrayList<Employees> currentEMP;

    public void saveButtonClick()throws Exception{
        DB db = new DB();
        db.addEmployee(firstNameField.getText(),lastNameField.getText(),emailField.getText(),
                phoneField.getText(),passwordField.getText(),authComboBox.getSelectionModel().getSelectedItem());
        AlertBox.display("Notification","Employee added successfully!");

        Stage appStage = (Stage) saveButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGUI.fxml"));
        Parent root = loader.load();
        ControllerMain controller = loader.getController();
        controller.setUserLogedIn(user);
        final ObservableList<Employees> data = FXCollections.observableArrayList(db.getAll());
        controller.populateList(data,currentEMP);

        Scene scene = new Scene(root);

        appStage.setScene(scene);
        appStage.show();

    }

    public void cancelButtonClick() throws Exception{
        Stage appStage = (Stage) cancelButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGUI.fxml"));
        Parent root = loader.load();
        ControllerMain controller = loader.getController();
        controller.setUserLogedIn(user);
        controller.populateList(allEmployees,currentEMP);

        Scene scene = new Scene(root);

        appStage.setScene(scene);
        appStage.show();
    }

    public void setData(ObservableList<Employees> allEmployees, String user, ArrayList<Employees> currentEMP){
        this.currentEMP = currentEMP;
        this.user = user;
        this.allEmployees = allEmployees;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        authComboBox.getItems().addAll("Manager","Employee","Technician");
    }

}
