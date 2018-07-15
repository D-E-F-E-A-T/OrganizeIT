import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class adminAccountCreateController implements Initializable {

    @FXML
    private JFXTextField adminFirstNameField;

    @FXML
    private JFXTextField adminSecondNameField;

    @FXML
    private JFXTextField adminUsernameField;

    @FXML
    private JFXPasswordField adminPasswordField;

    FileWriter fileWriter;

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
            console.pln("Validation Error Occurred!");
            console.pln("Showing alert...");
            Alert err = new Alert(Alert.AlertType.WARNING, "Please check the following errors and try again :-\n"+errorList,ButtonType.OK);
            err.setTitle("Uh Oh!");
            err.show();
        }
        else
        {
            console.pln("Validation Of Admin details are successful!");

            try
            {
                File configFile = new File("data/config");
                String toBeWritten = adminFirstNameEntered+Main.seperatorCharacter+adminSecondNameEntered+Main.seperatorCharacter+adminUsernameEntered+Main.seperatorCharacter+adminPasswordEntered;
                filer.write(toBeWritten,configFile);

                Main.currentStage.close();
                new fxmlStartup("employeesRegister.fxml", true);


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
