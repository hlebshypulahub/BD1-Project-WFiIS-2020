package application.dashboard.employee.control.employeeall;

import application.entities.Employee;
import application.database.Database;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EmployeeAllController implements Initializable {
    @FXML
    private TableView<Employee> table;
    @FXML
    private TableColumn<Employee, String> firstNameCol;
    @FXML
    private TableColumn<Employee, String> lastNameCol;
    @FXML
    private TableColumn<Employee, LocalDate> dobCol;
    @FXML
    private TableColumn<Employee, String> positionCol;
    @FXML
    private TableColumn<Employee, String> categoryCol;
    @FXML
    private TableColumn<Employee, Integer> salaryCol;
    @FXML
    private TableColumn<Employee, LocalDate> ppeCol;
    @FXML
    private Button employeeToFacilityButton;
    @FXML
    private Button addButton;
    @FXML
    private ComboBox<String> chooseFacility;

    public void fillData() {
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        ppeCol.setCellValueFactory(new PropertyValueFactory<>("PPE"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        ObservableList<Employee> observableList = Database.employeeDataView();
        table.setItems(observableList);
        employeeToFacilityButton.setDisable(true);
        addButton.setVisible(false);
        chooseFacility.setVisible(false);
    }

    @FXML
    public void showAddEmployeeToFacility(ActionEvent event) {
        addButton.setVisible(true);
        chooseFacility.setVisible(true);
        Database.fillFacilities(chooseFacility);
    }

    @FXML
    public void enableButtons(MouseEvent e) {
        employeeToFacilityButton.setDisable(false);
    }

    @FXML
    public void addEmployeeToFacility(ActionEvent event) {
        Employee employee = table.getSelectionModel().getSelectedItem();
        String facility = chooseFacility.getSelectionModel().getSelectedItem();
        String[] facilityInfo = facility.split("\\|");
        String facilityStatus = facilityInfo[0];
        String facilityName = facilityInfo[1];
        if (Database.addEmployeeToFacility(employee.getId(), facilityStatus, facilityName)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Udało się!");
            alert.setHeaderText("Pracownik " + employee.getFirstName() +
                    " " + employee.getLastName() + " został dodany do: " + facilityInfo[0] + " | " + facilityInfo[1] + "!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
