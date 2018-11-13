import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ControllerLogin {

    DB db;
    @FXML
    private Button logInButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label noteLabel;


    public void buttonClicked()throws Exception{
        db = new DB();
        System.out.println(usernameField.getText().toString());
        System.out.println(passwordField.getText().toString());
        String username = usernameField.getText();
        String password = passwordField.getText();

        ArrayList<Employees> list = db.getAll();

        final ObservableList<Employees> data = FXCollections.observableArrayList(list);

        ArrayList<Employees> user = db.checkLogin(Integer.parseInt(usernameField.getText().toString()),passwordField.getText().toString());

        if (user != null && user.get(0).getAuth().equalsIgnoreCase("manager"))  {



            //controllerMain.setUserLogedIn();
            System.out.println("Button clicked...");
            Stage appStage = (Stage) logInButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGUI.fxml"));
            Parent root = loader.load();
            ControllerMain controller = loader.getController();
            controller.setUserLogedIn("Logged in as: " + user.get(0).getFirstName() + " " + user.get(0).getLastName()+ "/" + user.get(0).getAuth());
            controller.populateList(data,user);

            Scene scene = new Scene(root);

            appStage.setScene(scene);
            appStage.show();

        }else if (user != null && user.get(0).getAuth().equalsIgnoreCase("technician")){
            Stage appStage = (Stage) logInButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("TechMainGUI.fxml"));
            Parent root = loader.load();
            ControllerMain controller = loader.getController();
            controller.setUserLogedIn("Logged in as: " + user.get(0).getFirstName() + " " + user.get(0).getLastName()+ "/" + user.get(0).getAuth());
            controller.populateList(data,user);

            Scene scene = new Scene(root);

            appStage.setScene(scene);
            appStage.show();
        }


        else {
            noteLabel.setText("Invalid login.");
        }
    }

}
