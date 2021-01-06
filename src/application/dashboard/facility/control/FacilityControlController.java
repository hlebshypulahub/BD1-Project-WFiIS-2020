package application.dashboard.facility.control;

import application.dashboard.employee.control.employeeall.EmployeeAllController;
import application.dashboard.facility.control.facilityall.FacilityAllController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FacilityControlController implements Initializable {
    @FXML
    private Pane mainArea;

    @FXML
    void showAddFacilityPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("facilityadd/FacilityAdd.fxml"));
        Parent root = loader.load();

        mainArea.getChildren().clear();
        mainArea.getChildren().addAll(root);
    }

    @FXML
    public void showAllFacilityPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("facilityall/FacilityAll.fxml"));
        Parent root = loader.load();

        FacilityAllController controller = loader.getController();
        controller.fillData();

        mainArea.getChildren().clear();
        mainArea.getChildren().addAll(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
