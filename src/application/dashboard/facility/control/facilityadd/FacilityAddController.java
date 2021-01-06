package application.dashboard.facility.control.facilityadd;

import application.database.Database;
import application.entities.Facility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class FacilityAddController implements Initializable {
    @FXML
    private Pane mainArea;
    @FXML
    private TextField nameAdd;
    @FXML
    private ComboBox<String> statusAdd;

    @FXML
    public void addFacility(ActionEvent e) {
        Facility facility = new Facility();

        facility.setName(nameAdd.getText());
        facility.setStatus(statusAdd.getValue() == null ? "Apteka" : statusAdd.getValue());
        if (Database.addFacility(facility)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Udało się!");
            alert.setHeaderText("Obiekt o nazwie \"" + facility.getName() + "\" i statusie " +
                    "\"" + facility.getStatus() + "\" został dodany!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Database.fillFacilityEnums(statusAdd);
    }
}
