import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class timeSlotRegisterController implements Initializable
{
    @FXML
    private JFXTextField noOfTimeSlotsField;

    @FXML
    private Label firstMsgPrompt;

    @FXML
    private JFXCheckBox monCheckBox;

    @FXML
    private JFXCheckBox tueCheckBox;

    @FXML
    private JFXCheckBox wedCheckBox;

    @FXML
    private JFXCheckBox thuCheckBox;

    @FXML
    private JFXCheckBox friCheckBox;

    @FXML
    private JFXCheckBox satCheckBox;

    @FXML
    private JFXCheckBox sunCheckBox;

    @FXML
    private Label specifyWeekDaysLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File employeeConfigFile = new File("data/timeSlots/config");
        if(!employeeConfigFile.exists())
        {
            console.pln("data/timeSlots/config file not found ...");
            console.pln("This will be treated as first time use ...");
            noOfTimeSlotsField.setVisible(true);
            firstMsgPrompt.setVisible(true);
            setWeekDaysCheckBoxVisibility(true);
        }
    }

    int currentStatus = 0;
    int noOfTimeSlots = 0;

    @FXML
    public void onContinueButtonClicked()
    {
        if(currentStatus == 0)
        {
            String noOfTimeSlotsInt = noOfTimeSlotsField.getText();
            boolean isValError = false;
            try
            {
                noOfTimeSlots = Integer.parseInt(noOfTimeSlotsInt);
            }
            catch (Exception e)
            {
                isValError = true;
            }

            if(isValError)
            {
                Alert valError = new Alert(Alert.AlertType.ERROR, "Please enter a valid number of time\nslots between 0-50 in numbers...",ButtonType.OK);
                valError.show();
            }
            else
            {
                ArrayList weekDayData = getWeekDaysCheckedUserData();

                if(weekDayData.contains("MON"))
                {

                }
            }
        }

        currentStatus++;
    }


    public void setWeekDaysCheckBoxVisibility(boolean status)
    {
        if(status)
        {
            specifyWeekDaysLabel.setVisible(true);
            monCheckBox.setVisible(true);
            tueCheckBox.setVisible(true);
            wedCheckBox.setVisible(true);
            thuCheckBox.setVisible(true);
            friCheckBox.setVisible(true);
            satCheckBox.setVisible(true);
            sunCheckBox.setVisible(true);
        }
        else
        {
            specifyWeekDaysLabel.setVisible(false);
            monCheckBox.setVisible(false);
            tueCheckBox.setVisible(false);
            wedCheckBox.setVisible(false);
            thuCheckBox.setVisible(false);
            friCheckBox.setVisible(false);
            satCheckBox.setVisible(false);
            sunCheckBox.setVisible(false);
        }
    }

    public ArrayList getWeekDaysCheckedUserData() {
        ArrayList toBeReturned = new ArrayList();

        if (monCheckBox.isSelected())
            toBeReturned.add("MON,");

        if (tueCheckBox.isSelected())
            toBeReturned.add("TUE,");

        if (wedCheckBox.isSelected())
            toBeReturned.add("WED,");

        if (thuCheckBox.isSelected())
            toBeReturned.add("THU,");

        if (friCheckBox.isSelected())
            toBeReturned.add("FRI,");

        if (satCheckBox.isSelected())
            toBeReturned.add("SAT,");

        if (sunCheckBox.isSelected())
            toBeReturned.add("SUN,");

        return toBeReturned;
    }
}