import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

import javax.security.auth.login.Configuration;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable
{
    @FXML
    private Label versionLabel;

    @FXML
    private JFXTextField usernameLoginField;

    @FXML
    private JFXPasswordField passwordLoginField;

    @FXML
    private JFXButton loginButton;

    @FXML
    private Label loginMsg;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        versionLabel.setText("Version "+Main.version);
    }

    @FXML
    public void validateValues()
    {
        if(usernameLoginField.getText().length()> 0 && passwordLoginField.getText().length()>0)
        {
            loginButton.setDisable(false);

            try
            {
                Thread.sleep(2000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            loginButton.setDisable(true);
        }
    }

    @FXML
    public void checkUserProvidedLoginValues()
    {
        String usernameEntered = usernameLoginField.getText();
        String passwordEntered = passwordLoginField.getText();

        File configFile = new File("data/config");
        String values[] = filer.read(configFile).split(Main.seperatorCharacter);
        String username = values[2];
        String password = values[3];

        if(usernameEntered.equals(username) && passwordEntered.equals(password))
        {
            loginMsg.setText("Successfully Logged In!");
            loginMsg.setTextFill(Paint.valueOf("#1fb800"));
        }
        else
        {
            loginMsg.setText("Incorrect Username/Password");
            loginMsg.setTextFill(Paint.valueOf("#ff0000"));
        }

        loginMsg.setVisible(true);
    }


}