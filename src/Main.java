import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static Stage currentStage = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        currentStage = primaryStage;
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        File configFile = new File("data/config");

        if(configFile.exists())
        {
            console.pln("File Exists!");
        }
        else
        {
            console.pln("Config File does not exist!");
            console.pln("This will be treated as first time use...");
        }
        //launch(args);
    }
}
