import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class fxmlStartup {
    public static void start(String fxmlFileName, boolean isResizable)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFileName));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("OrganizeIT");
            stage.setScene(new Scene(root1));
            stage.setResizable(isResizable);
            Main.currentStage = stage;
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
