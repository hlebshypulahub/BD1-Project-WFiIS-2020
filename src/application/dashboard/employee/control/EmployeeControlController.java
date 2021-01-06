package application.dashboard.employee.control;

import application.dashboard.employee.control.employeeall.EmployeeAllController;
import application.dashboard.mainpage.MainPageController;
import application.entities.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeControlController implements Initializable {
    @FXML
    private Pane mainArea;

    @FXML
    public void showAddEmployeePage(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("employeeadd/EmployeeAdd.fxml"));
        Parent root = loader.load();

        mainArea.getChildren().clear();
        mainArea.getChildren().addAll(root);
    }

    @FXML
    public void showAllEmployeePage(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("employeeall/EmployeeAll.fxml"));
        Parent root = loader.load();

        EmployeeAllController controller = loader.getController();
        controller.initData();

        mainArea.getChildren().clear();
        mainArea.getChildren().addAll(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
