package application.dashboard;

import application.dashboard.employee.control.EmployeeControlController;
import application.dashboard.employee.info.EmployeeInfoController;
import application.dashboard.mainpage.MainPageController;
import application.entities.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private Employee userEmployee;
    @FXML
    private Pane mainArea;
    @FXML
    private Label controlPanelLabel;
    @FXML
    private Button employeeControlButton;
    @FXML
    private VBox buttonsVBox;
    private boolean employeeControlStageExists = false;
    private boolean facilityControlStageExists = false;

    public void initUserData(Employee employee) throws IOException {
        userEmployee = employee;
        showMainPage(new ActionEvent());
        drawPane();
    }

    public void drawPane() {
        if (!(userEmployee.getRole().equals("CEO") || userEmployee.getRole().equals("manager"))) {
            buttonsVBox.getChildren().removeAll(employeeControlButton, controlPanelLabel);
        }
    }

    @FXML
    public void showMainPage(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpage/MainPage.fxml"));
        Parent root = loader.load();

        MainPageController controller = loader.getController();
        controller.initUserData(userEmployee);

        mainArea.getChildren().clear();
        mainArea.getChildren().addAll(root);
    }

    @FXML
    public void showPersonalInfoPage(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("employee/info/EmployeeInfo.fxml"));
        Parent root = loader.load();

        EmployeeInfoController controller = loader.getController();
        controller.initUserData(userEmployee);
        controller.drawPane("user");

        mainArea.getChildren().clear();
        mainArea.getChildren().addAll(root);
    }

    @FXML
    public void showEmployeeControlStage(ActionEvent e) throws IOException {
        if (!employeeControlStageExists) {
            employeeControlStageExists = true;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("employee/control/EmployeeControl.fxml"));
            Stage stage = new Stage();
            stage.setOnCloseRequest(event -> employeeControlStageExists = false);
            stage.setTitle("Panel administratora - Pracownicy");
            stage.setScene(new Scene(loader.load()));
            stage.setX(200);
            stage.setY(200);
            stage.show();
        }
    }

    @FXML
    public void showFacilityControlStage(ActionEvent e) throws IOException {
        if (!facilityControlStageExists) {
            facilityControlStageExists = true;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("facility/control/FacilityControl.fxml"));
            Stage stage = new Stage();
            stage.setOnCloseRequest(event -> facilityControlStageExists = false);
            stage.setTitle("Panel administratora - Obiekty Handlowe");
            stage.setScene(new Scene(loader.load()));
            stage.setX(400);
            stage.setY(10);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
