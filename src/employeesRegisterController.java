import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.deploy.services.PlatformType;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class employeesRegisterController implements Initializable
{
    String errorList = "";

    @FXML
    private Label firstMsgLabel;

    @FXML
    private VBox mainVBox;

    @FXML
    private JFXButton continueButton;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private HBox headingHBox;

    @FXML
    private JFXButton resetButton;

    @FXML
    private Label congratulationsLabel;

    @FXML
    private Label congratsLabel2;

    @FXML
    private Label congratsLabel3;

    @FXML
    private JFXProgressBar loadingProgressBar;

    @FXML
    private Label loadingLabel;

    @FXML
    private JFXTextField numOfEmployeesField;

    int currentStatus = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        resetButtonClicked();
    }

    int noOfEmployeesToBeAdded = 0;
    int totalNoOfEmployeesAfterAmendment = 0;
    public void continueButtonClicked()
    {
        if(currentStatus == 0)
        {
            TimerTask t = new TimerTask() {
                @Override
                public void run() {
                    String numEmployeesToBeAddedStr = numOfEmployeesField.getText();
                    boolean isInputError = false;
                    try
                    {
                        noOfEmployeesToBeAdded = Integer.parseInt(numEmployeesToBeAddedStr);
                    }
                    catch (Exception e)
                    {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert numParseError = new Alert(Alert.AlertType.ERROR, "Please enter a valid number of candidates\nfrom 0 - 500 in numbers.",ButtonType.OK);
                                numParseError.show();
                            }
                        });
                        currentStatus = -1;
                        return;
                    }

                    try
                    {
                        File employeesIndex = new File("data/employeesIndex");
                        int noOfEmployeesAlreadyRegistered = 0;
                        if(employeesIndex.exists())
                        {
                            String str = filer.read(employeesIndex);
                            str = str.replace("\n","");
                            noOfEmployeesAlreadyRegistered = Integer.parseInt(str);
                        }
                        int thisEmployeeIndex = noOfEmployeesAlreadyRegistered + 1;
                        totalNoOfEmployeesAfterAmendment = noOfEmployeesAlreadyRegistered+noOfEmployeesToBeAdded;
                        firstMsgLabel.setVisible(false);
                        numOfEmployeesField.setVisible(false);

                        for(int index = 0;index<noOfEmployeesToBeAdded; index++)
                        {
                            HBox eachColumn = new HBox();

                            Label eachSlNo = new Label((index+1)+"");
                            eachSlNo.setAlignment(Pos.CENTER);
                            eachSlNo.setPrefHeight(24.0);
                            eachSlNo.setPrefWidth(54.0);

                            JFXTextField eachEmployeeName = new JFXTextField();
                            eachEmployeeName.setPadding(new Insets(10,20,10,10));
                            eachEmployeeName.setPrefWidth(20);
                            eachEmployeeName.setPrefHeight(24.0);
                            eachEmployeeName.setId("employeeName"+index);
                            eachEmployeeName.setPrefWidth(538.0);

                            JFXTextField eachEmployeeID = new JFXTextField();
                            eachEmployeeID.setPadding(new Insets(10,10,10,10));
                            eachEmployeeID.setPrefWidth(20);
                            eachEmployeeID.setPrefHeight(24.0);
                            eachEmployeeID.setPrefWidth(150.0);
                            eachEmployeeID.setEditable(false);
                            eachEmployeeID.setId("e"+thisEmployeeIndex);
                            eachEmployeeID.setText("e"+thisEmployeeIndex);
                            thisEmployeeIndex++;

                            eachColumn.getChildren().add(eachSlNo);
                            eachColumn.getChildren().add(eachEmployeeName);
                            eachColumn.getChildren().add(eachEmployeeID);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    mainVBox.getChildren().add(eachColumn);
                                }
                            });
                        }

                        mainScrollPane.setVisible(true);
                        headingHBox.setVisible(true);
                        resetButton.setVisible(true);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            new Timer().schedule(t,50);
        }
        else if(currentStatus == 2)
        {
            headingHBox.setVisible(false);
            mainScrollPane.setVisible(false);
            resetButton.setVisible(false);
            continueButton.setVisible(false);

            TimerTask registerEmployeesTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            loadingProgressBar.setProgress(-1);
                            loadingProgressBar.setVisible(true);
                            loadingLabel.setVisible(true);
                        }
                    });

                    ObservableList<Node> childrenList = mainVBox.getChildren();
                    boolean isValidationError = false;
                    for(int index = 0; index<childrenList.size(); index++)
                    {
                        HBox eachHBox = (HBox) childrenList.get(index);
                        ObservableList<Node> eachHBoxChildren = eachHBox.getChildren();
                        JFXTextField eachNameField = (JFXTextField) eachHBoxChildren.get(1);
                        JFXTextField eachIDField = (JFXTextField) eachHBoxChildren.get(2);
                        String eachName = eachNameField.getText();
                        String eachID = eachIDField.getText();

                        if(eachName.length() < 3)
                        {
                            console.pln(eachName.length()+"");
                            errorList += "Please enter a valid full name\nfor Employee : "+(index+1)+"\nEmployee ID : "+eachID+"\n";
                            isValidationError = true;
                            eachNameField.setUnFocusColor(Color.RED);
                            eachIDField.setUnFocusColor(Color.RED);
                        }
                        else
                        {
                            eachNameField.setUnFocusColor(Color.GREEN);
                            eachIDField.setUnFocusColor(Color.GREEN);
                        }

                        if(!isValidationError)
                        {
                            File eachEmployeeFile = new File("data/employees/" + eachID);
                            String toBeWritten = eachName + "\n" + eachID + "\n";
                            filer.write(toBeWritten, eachEmployeeFile);
                        }
                    }

                    if(isValidationError)
                    {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                headingHBox.setVisible(true);
                                mainScrollPane.setVisible(true);
                                resetButton.setVisible(true);

                                loadingProgressBar.setVisible(false);
                                loadingLabel.setVisible(false);
                                Alert invalidValAlert = new Alert(Alert.AlertType.ERROR,"Please check the following errors and try again.\n"+errorList,ButtonType.OK);
                                invalidValAlert.setResizable(true);
                                invalidValAlert.show();
                                currentStatus = 1;
                                errorList = "";
                                continueButton.setVisible(true);
                            }
                        });


                    }

                    else
                    {
                        File employeesIndexFile = new File("data/employeesIndex");
                        filer.write(totalNoOfEmployeesAfterAmendment+"", employeesIndexFile);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                congratsLabel2.setText(noOfEmployeesToBeAdded+" employees have been successfully added to the system!");
                                loadingProgressBar.setVisible(false);
                                loadingLabel.setVisible(false);
                                congratulationsLabel.setVisible(true);
                                congratsLabel2.setVisible(true);
                                congratsLabel3.setVisible(true);
                                continueButton.setVisible(true);
                            }
                        });
                    }


                }
            };
            new Timer().schedule(registerEmployeesTask,50);
        }
        else if(currentStatus == 3)
        {

        }

        currentStatus++;
    }

    @FXML
    public void resetButtonClicked()
    {
        congratulationsLabel.setVisible(false);
        congratsLabel2.setVisible(false);
        congratsLabel3.setVisible(false);
        headingHBox.setVisible(false);
        mainScrollPane.setVisible(false);
        mainVBox.getChildren().clear();
        firstMsgLabel.setVisible(true);
        numOfEmployeesField.setVisible(true);
        currentStatus = 0;
        resetButton.setVisible(false);
        loadingProgressBar.setVisible(false);
        loadingLabel.setVisible(false);
    }
}