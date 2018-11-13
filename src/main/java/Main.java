import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));
        //FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainWindowView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        DB db = new DB();
        //db.addEmployee("User2","last Name","test2@gmail.com","666","test2","employee");
        ArrayList<Employees> emp = db.getAll();
        for (Employees em:emp){
            System.out.println(emp.toString());

        }
        System.out.println("added");
    }

    public static void main(String[] args) {

        launch(args);
    }


}
