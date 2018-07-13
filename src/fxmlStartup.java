import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class fxmlStartup {
    public fxmlStartup(String fxmlFileName)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("OrganizeIT");
            stage.setScene(new Scene(root1));
            Main.currentStage = stage;
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
