import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class firstTimeUseController implements Initializable {

    int currentStatus = 0;

    @FXML
    private TextArea creatorMessageTextArea;

    @FXML
    private Label welcomeMessageSubHeadingLabel;

    @FXML
    private Label secondMessage0Label;

    @FXML
    private Label secondMessage1Label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void continueButton()
    {
        switch (currentStatus)
        {
            case 0:
                creatorMessageTextArea.setVisible(false);
                welcomeMessageSubHeadingLabel.setVisible(false);

                secondMessage0Label.setVisible(true);
                secondMessage1Label.setVisible(true);
                break;

            case 1:
                Main.currentStage.close();
                new fxmlStartup("adminAccountCreate.fxml");
                break;
        }

        currentStatus++;
    }
}