import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TimerTask;

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

    @FXML
    private ScrollPane timeSlotsRegisterScrollPane;

    @FXML
    private VBox timeSlotsRegisterVBox;

    @FXML
    private Label subHeadingLabel;

    ArrayList timeSlotTimings = new ArrayList();

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
            timeSlotsRegisterScrollPane.setVisible(false);
            subHeadingLabel.setText("");
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
                subHeadingLabel.setText("Fill up the timings of slots... ");
                noOfTimeSlotsField.setVisible(false);
                firstMsgPrompt.setVisible(false);
                setWeekDaysCheckBoxVisibility(false);

                timeSlotsRegisterScrollPane.setVisible(true);
                for(int index = 0; index<noOfTimeSlots; index++)
                {
                    Label eachTimeSlotLabel = new Label("Time Slot "+(index+1));
                    eachTimeSlotLabel.setPadding(new Insets(5,15,5,15));

                    JFXTextField FROMtimeSlotValueField = new JFXTextField();
                    FROMtimeSlotValueField.setPromptText("From");
                    FROMtimeSlotValueField.setPadding(new Insets(5,5,5,10));

                    JFXTextField TOtimeSlotValueField = new JFXTextField();
                    TOtimeSlotValueField.setPromptText("To");
                    TOtimeSlotValueField.setPadding(new Insets(5,5,5,10));

                    HBox eachColumn = new HBox();
                    eachColumn.getChildren().add(eachTimeSlotLabel);
                    eachColumn.getChildren().add(FROMtimeSlotValueField);
                    eachColumn.getChildren().add(TOtimeSlotValueField);

                    timeSlotsRegisterVBox.getChildren().add(eachColumn);
                }
            }
        }
        else if(currentStatus == 1)
        {
            boolean isValidationError = false;
            String errorList = "";
            ObservableList<Node> childrenList = timeSlotsRegisterVBox.getChildren();

            timeSlotTimings.clear();
            for(int eachIndex = 0; eachIndex<childrenList.size(); eachIndex++)
            {
                HBox eachChild = (HBox) childrenList.get(eachIndex);

                JFXTextField FROMTimeSlotTextField = (JFXTextField) eachChild.getChildren().get(1);
                JFXTextField TOTimeSlotTextField = (JFXTextField) eachChild.getChildren().get(2);

                String FROMTimeSlot = TOTimeSlotTextField.getText();
                String TOTimeSlot = FROMTimeSlotTextField.getText();

                int colonOccurrence = 0;

                for(int i = 0; i<FROMTimeSlot.length(); i++)
                {
                    char eachChar = FROMTimeSlot.charAt(i);
                    if(eachChar == ':')
                        colonOccurrence++;
                }

                for(int j =0 ;j<TOTimeSlot.length(); j++)
                {
                    char eachChar = TOTimeSlot.charAt(j);
                    if(eachChar == ':')
                        colonOccurrence++;
                }

                if(colonOccurrence!=2)
                {
                    isValidationError = true;
                    errorList+="* Invalid Time Slot\n  Config at time Slot "+eachIndex+"\n";
                    FROMTimeSlotTextField.setUnFocusColor(Color.RED);
                    TOTimeSlotTextField.setUnFocusColor(Color.RED);
                }
                else
                {
                    FROMTimeSlotTextField.setUnFocusColor(Color.GREEN);
                    TOTimeSlotTextField.setUnFocusColor(Color.GREEN);
                }

                if(!isValidationError)
                {
                    String toBeAdded = FROMTimeSlot+" - "+TOTimeSlot;
                    timeSlotTimings.add(toBeAdded);
                }


            }

            if(isValidationError)
            {
                Alert valErrorAlert = new Alert(Alert.AlertType.ERROR, "Check the following errors and try again :-\n"+errorList,ButtonType.OK);
                valErrorAlert.show();
                currentStatus = 0;
            }
            else
            {
                TimerTask t = new TimerTask() {
                    @Override
                    public void run() {
                        File timeSlotsConfigFile = new File("data/timeSlots/weekDaysConfig");
                        ArrayList weekDays = getWeekDaysCheckedUserData();
                        String toBePrinted = "";
                        StringBuilder toBePrintedStringBuilder = new StringBuilder();
                        toBePrintedStringBuilder.append(weekDays.size());
                        for(int ix = 0;ix<weekDays.size();ix++)
                        {
                            toBePrintedStringBuilder.append(weekDays.get(ix).toString()+"");
                        }
                        toBePrinted = toBePrintedStringBuilder.toString();

                        filer.write(toBePrinted,timeSlotsConfigFile);

                        StringBuilder toBePrintedStringBuilder2 = new StringBuilder();
                        File timeSlotTimingConfigFile = new File("data/timeSlots/timingsConfig");
                        toBePrintedStringBuilder2.append(timeSlotTimings.size());

                        for(int xi = 0;xi<timeSlotTimings.size();xi++)
                        {
                            toBePrintedStringBuilder2.append(timeSlotTimings.get(xi));
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //Alert timeSlotsRegisterSuccess = new Alert(Alert.AlertType.INFORMATION,"Time Slots hav ")
                            }
                        });
                    }
                };
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