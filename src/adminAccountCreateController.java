import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimerTask;

public class adminAccountCreateController implements Initializable {

    @FXML
    private TextField adminFirstNameField;

    @FXML
    private TextField adminSecondNameField;

    @FXML
    private TextField adminUsernameField;

    @FXML
    private PasswordField adminPasswordField;

    @FXML
    private Label errorLabelDialog;

    @FXML
    private DialogPane errorDialogPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onCreateAccountButtonClicked()
    {
        String adminFirstNameEntered = adminFirstNameField.getText();
        String adminSecondNameEntered = adminSecondNameField.getText();
        String adminUsernameEntered = adminUsernameField.getText();
        String adminPasswordEntered = adminPasswordField.getText();

        boolean isValidationError = false;
        String errorList = "";

        if(adminFirstNameEntered.length() < 2)
        {
            errorList+="Please enter a valid First Name.\n";
            isValidationError = true;
        }

        if(adminSecondNameEntered.length() < 2)
        {
            errorList+="Please enter a valid Second Name.\n";
            isValidationError = true;
        }

        if(adminUsernameEntered.length() < 5)
        {
            errorList+="Please enter a valid Admin Username. It must contain at least 5 characters.\n";
            isValidationError = true;
        }

        if(adminPasswordEntered.length() < 8)
        {
            errorList+="Please enter a valid password. It must contain at least 8 characters.\n";
            isValidationError = true;
        }

        if(isValidationError)
        {
            errorLabelDialog.setText(errorList);
            errorDialogPane.setVisible(true);
        }
    }
}
