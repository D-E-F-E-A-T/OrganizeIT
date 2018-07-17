import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class firstTimeUseController implements Initializable {

    int currentStatus = 0;

    @FXML
    private Label firstMsgLabel;

    @FXML
    private Label secondMsgLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void continueButton()
    {
        switch (currentStatus)
        {
            case 0:
                firstMsgLabel.setVisible(false);
                secondMsgLabel.setVisible(true);
                break;

            case 1:
                Main.currentStage.close();
                fxmlStartup.start("adminAccountCreate.fxml", false);
                break;
        }

        currentStatus++;
    }
}