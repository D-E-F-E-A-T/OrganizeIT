import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static Stage currentStage = null;

    static String fxmlToBeLoaded = "";
    static String seperatorCharacter = "�";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(fxmlToBeLoaded));
        primaryStage.setTitle("OrganizeIT");
        primaryStage.setScene(new Scene(root, 600, 400));
        //primaryStage.setResizable(false);
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
            fxmlToBeLoaded = "employeesRegister.fxml";
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    new fxmlStartup(fxmlToBeLoaded, true);
                }
            });
        }
        //launch(args);
    }
}
