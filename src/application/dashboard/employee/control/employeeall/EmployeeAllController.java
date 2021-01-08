package application.dashboard.employee.control.employeeall;

import application.entities.Employee;
import application.database.Database;
import application.entities.Facility;
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
import java.util.regex.Pattern;

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
    private Button employeeFromFacilityButton;
    @FXML
    private Button addButton;
    @FXML
    private Button listButton;
    @FXML
    private ComboBox<String> chooseFacility;
    @FXML
    private ComboBox<String> chooseFacilityCopy;
    private ObservableList<Facility> observableListFacility;

    public void initData() {
        fillTable(Database.employeeDataView("all", 0));
        observableListFacility = Database.facilityDataView();
        assert observableListFacility != null;
        for (Facility facility : observableListFacility) {
            chooseFacility.getItems().add(facility.getStatus() + " | " + facility.getName());
        }
        chooseFacilityCopy.getItems().addAll(chooseFacility.getItems());
        employeeToFacilityButton.setDisable(true);
        addButton.setVisible(false);
        chooseFacility.setVisible(false);
    }

    public void fillTable(ObservableList<Employee> ol) {
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        ppeCol.setCellValueFactory(new PropertyValueFactory<>("PPE"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        table.setItems(ol);
    }

    @FXML
    public void showAddEmployeeToFacility(ActionEvent event) {
        addButton.setVisible(true);
        chooseFacility.setVisible(true);
    }

    @FXML
    public void showListEmployeeFromFacility(ActionEvent event) {
        listButton.setVisible(true);
        chooseFacilityCopy.setVisible(true);
    }

    @FXML
    public void enableButtons(MouseEvent e) {
        if (table.getSelectionModel().getSelectedIndex() != -1)
            employeeToFacilityButton.setDisable(false);
    }

    @FXML
    public void addEmployeeToFacility(ActionEvent event) {
        Employee employee = table.getSelectionModel().getSelectedItem();
        String facility = chooseFacility.getSelectionModel().getSelectedItem();
        String[] facilityInfo = facility.split(Pattern.quote(" | "));
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

    @FXML
    public void listEmployeeFromFacility(ActionEvent event) {
        String facility = chooseFacilityCopy.getSelectionModel().getSelectedItem();
        String[] facilityInfo = facility.split(Pattern.quote(" | "));
        String facilityStatus = facilityInfo[0];
        String facilityName = facilityInfo[1];
        int facilityId = 0;
        for (Facility f : observableListFacility) {
            if (f.getName().equals(facilityName) && f.getStatus().equals(facilityStatus)) {
                facilityId = f.getId();
                break;
            }
        }
        fillTable(Database.employeeDataView("forFacility", facilityId));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
