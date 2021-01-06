package application.dashboard.facility.control.facilityall;

import application.database.Database;
import application.entities.Facility;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class FacilityAllController implements Initializable {
    @FXML
    private TableView<Facility> table;
    @FXML
    private TableColumn<Facility, String> nameCol;
    @FXML
    private TableColumn<Facility, String> statusCol;

    public void fillData() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<Facility> observableList = Database.facilityDataView();
        table.setItems(observableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
