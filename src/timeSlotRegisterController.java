import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
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
    private VBox timeSlotConfigureVBox;

    @FXML
    private ScrollPane timeSlots1ScrollPane;

    @FXML
    private Label subHeadingLabel;

    ArrayList timeSlotTimings = new ArrayList();
    File employeeConfigFile = new File("data/timeSlots/weekDaysConfig");
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeSlots1ScrollPane.setVisible(false);
        if(!employeeConfigFile.exists())
        {
            console.pln("data/timeSlots/weekDaysConfig file not found ...");
            console.pln("This will be treated as first time use ...");
            noOfTimeSlotsField.setVisible(true);
            firstMsgPrompt.setVisible(true);
            setWeekDaysCheckBoxVisibility(true);
            timeSlotsRegisterScrollPane.setVisible(false);
            subHeadingLabel.setText("");
        }
        else
        {
            showTimeRoutine();
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
                currentStatus = -1;
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
                    eachColumn.setPadding(new Insets(10,10,10,10));
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

                String TOTimeSlot = TOTimeSlotTextField.getText();
                String FROMTimeSlot = FROMTimeSlotTextField.getText();

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
                        for(int ix = 0;ix<weekDays.size();ix++)
                        {
                            String each = weekDays.get(ix).toString();
                            toBePrintedStringBuilder.append(each.substring(0,each.length()-1)+Main.seperatorCharacter);
                        }
                        toBePrinted = toBePrintedStringBuilder.toString();

                        filer.write(toBePrinted,timeSlotsConfigFile);

                        StringBuilder toBePrintedStringBuilder2 = new StringBuilder();
                        File timeSlotTimingConfigFile = new File("data/timeSlots/timingsConfig");

                        for(int xi = 0;xi<timeSlotTimings.size();xi++)
                        {
                            toBePrintedStringBuilder2.append(timeSlotTimings.get(xi)+Main.seperatorCharacter);
                        }

                        filer.write(toBePrintedStringBuilder2.toString(),timeSlotTimingConfigFile);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert timeSlotsRegisterSuccessAlert = new Alert(Alert.AlertType.INFORMATION,"Time Slots have been successfully registered!\n You will now have to assign the time slots\nfor the respected employees...", ButtonType.OK);
                                timeSlotsRegisterSuccessAlert.show();
                            }
                        });

                        showTimeRoutine();
                    }
                };

                new Thread(t).start();
            }
        }

        else if(currentStatus == 2)
        {
            ObservableList<Node> childrenList = timeSlotConfigureVBox.getChildren();
            ArrayList<String> timeSlotsConfig = new ArrayList<>();
            boolean isValidationError = false;
            for(int i = 0; i<weekDayList.length; i++)
            {
                timeSlotsConfig.add(weekDayList[i]);

                HBox eachHBox = (HBox) childrenList.get(i+1);
                ObservableList<Node> HBoxChildren =  eachHBox.getChildren();
                for(int j = 1;j<HBoxChildren.size();j++)
                {
                    JFXTextField tSlotConfig = (JFXTextField) HBoxChildren.get(j);
                    tSlotConfig.setText(tSlotConfig.getText().toLowerCase());
                    String txt = tSlotConfig.getText();
                    if(txt.equals(""))
                    {
                        isValidationError = true;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                tSlotConfig.setUnFocusColor(Color.RED);
                            }
                        });
                    }
                    else
                    {
                        if(txt.equals("free"))
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    tSlotConfig.setUnFocusColor(Color.GREEN);
                                }
                            });
                        else
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    tSlotConfig.setUnFocusColor(Color.BLUE);
                                }
                            });
                    }

                    if(!isValidationError)
                    {
                        timeSlotsConfig.add(tSlotConfig.getPromptText());
                        timeSlotsConfig.add(txt);
                    }
                }
            }

            for(int i = 0;i<timeSlotsConfig.size();i++)
            {
                console.pln(timeSlotsConfig.get(i));
            }

            if(isValidationError)
            {
                timeSlotsConfig.clear();
                new Alert(Alert.AlertType.ERROR,"Please fill up all the fields correctly!",ButtonType.OK).show();
                currentStatus = 1;
            }
            else
            {
                int skip = (timeSlotsList.length * 2)+1;
                for(int index =0;index<timeSlotsConfig.size();index+=skip)
                {
                    String weekDayCode = timeSlotsConfig.get(index);
                    File eachSlotConf = new File("data/timeSlots/"+employeeIDList.get(currentEmployeeIndex)+weekDayCode);
                    StringBuilder toBePrinted = new StringBuilder();
                    for(int i2 = (index+1); i2<=((index+skip)-1); i2++)
                    {
                        toBePrinted.append(timeSlotsConfig.get(i2)+Main.seperatorCharacter);
                    }
                    filer.write(toBePrinted.toString(),eachSlotConf);
                }


                if(currentEmployeeIndex<(employeeIDList.size()-1))
                {
                    currentEmployeeIndex++;
                    showEachRoutine(currentEmployeeIndex);
                    currentStatus = 1;
                }
                else
                {
                    Alert m = new Alert(Alert.AlertType.INFORMATION,"All the timeslots have been configured!\nLogin again to see effects!",ButtonType.OK);
                    m.showAndWait();
                    Main.currentStage.close();
                    fxmlStartup.start("login.fxml",false);
                }
            }
        }
        else if(currentStatus == 3)
        {

        }

        currentStatus++;
    }

    int currentEmployeeIndex = 0;
    ArrayList<String> employeeNameList = new ArrayList<>();
    ArrayList<String> employeeIDList = new ArrayList<>();
    String[] timeSlotsList;
    String[] weekDayList;

    public void showTimeRoutine()
    {
        currentStatus = 2;
        setWeekDaysCheckBoxVisibility(false);
        timeSlots1ScrollPane.setVisible(true);
        timeSlotsRegisterScrollPane.setVisible(false);

        File[] listOfEmployeeConfigFiles = new File("data/employees/").listFiles();
        for(File eachEmployeeFile : listOfEmployeeConfigFiles)
        {
            String[] fileContent = filer.read(eachEmployeeFile).split(Main.seperatorCharacter);
            employeeNameList.add(fileContent[0]);
            employeeIDList.add(fileContent[1]);
        }

        timeSlotsList = filer.read(new File("data/timeSlots/timingsConfig")).split(Main.seperatorCharacter);
        weekDayList = filer.read(new File("data/timeSlots/weekDaysConfig")).split(Main.seperatorCharacter);
        showEachRoutine(currentEmployeeIndex);
    }

    public void showEachRoutine(int employeeIndex)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                subHeadingLabel.setText("Configure Time Slot for "+employeeNameList.get(employeeIndex)+", ("+employeeIDList.get(employeeIndex)+")");
                timeSlotConfigureVBox.getChildren().clear();
            }
        });



        HBox headingHBox = new HBox();

        Label weekDayHeadingLabel = new Label("Week Day");
        weekDayHeadingLabel.setPadding(new Insets(10,10,10,20));
        headingHBox.getChildren().add(weekDayHeadingLabel);

        for(int lol = 0;lol<timeSlotsList.length; lol++)
        {
            Label eachTimeSlotHeadingExample = new Label(timeSlotsList[lol]);
            eachTimeSlotHeadingExample.setPadding(new Insets(10,50,10,40));
            headingHBox.getChildren().add(eachTimeSlotHeadingExample);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timeSlotConfigureVBox.getChildren().add(headingHBox);
            }
        });




        for(int xoxo = 0;xoxo<weekDayList.length; xoxo++) {
            HBox newHBox = new HBox();

            newHBox.setPadding(new Insets(10, 10, 10, 20));

            String eachWeekDayCode = weekDayList[xoxo];
            String finalWeekDayLabelCode = "NULL";
            if (eachWeekDayCode.equals("SUN"))
                finalWeekDayLabelCode = "Sunday   ";
            else if (eachWeekDayCode.equals("MON"))
                finalWeekDayLabelCode = "Monday   ";
            else if (eachWeekDayCode.equals("TUE"))
                finalWeekDayLabelCode = "Tuesday  ";
            else if (eachWeekDayCode.equals("WED"))
                finalWeekDayLabelCode = "Wednesday";
            else if (eachWeekDayCode.equals("THU"))
                finalWeekDayLabelCode = "Thursday ";
            else if (eachWeekDayCode.equals("FRI"))
                finalWeekDayLabelCode = "Friday   ";
            else if (eachWeekDayCode.equals("SAT"))
                finalWeekDayLabelCode = "Saturday ";


            Label weekDayLabel = new Label(finalWeekDayLabelCode);
            weekDayLabel.setPrefWidth(100.0);
            newHBox.getChildren().add(weekDayLabel);

            File lol = new File("data/timeSlots/" + employeeIDList.get(employeeIndex) + eachWeekDayCode);
            String alreadyConfigured[] = new String[69];
            if (lol.exists())
            {
                console.pln("Time Slot Config File Already Exists!");
                alreadyConfigured = filer.read(lol).split(Main.seperatorCharacter);
            }

            for(int xoxo2 = 0;xoxo2<timeSlotsList.length; xoxo2++)
            {
                String itsValue = "";
                if(lol.exists())
                {
                    for(int ix =0; ix<alreadyConfigured.length;ix++)
                    {
                        if(alreadyConfigured[ix].equals(timeSlotsList[xoxo2]))
                        {
                            itsValue = alreadyConfigured[ix+1];
                        }
                    }
                }
                JFXTextField eachTimeSlot = new JFXTextField();
                eachTimeSlot.setPadding(new Insets(0,10,0,10));
                eachTimeSlot.setPromptText(timeSlotsList[xoxo2]);
                eachTimeSlot.setText(itsValue);
                newHBox.getChildren().add(eachTimeSlot);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    timeSlotConfigureVBox.getChildren().add(newHBox);
                }
            });

        }
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