package application.dashboard.employee.control.employeeall;

import application.dashboard.employee.info.EmployeeInfoController;
import application.entities.Employee;
import application.database.Database;
import application.entities.Facility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EmployeeAllController implements Initializable {
    @FXML
    private Pane mainArea;
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
    private Button showListBySalaryButton;
    @FXML
    private Button addButton;
    @FXML
    private Button employeeInfoButton;
    @FXML
    private Button listButton;
    @FXML
    private ComboBox<String> chooseFacility;
    @FXML
    private ComboBox<String> chooseFacilityCopy;
    @FXML
    private TextField minSalary;
    @FXML
    private TextField maxSalary;
    private ObservableList<Facility> observableListFacility;
    private int facilityId;
    private ObservableList<Employee> observableList = null;

    public void initData() {
        fillTable(Database.employeeDataView("all", 0));
        observableListFacility = Database.facilityDataView();
        assert observableListFacility != null;
        for (Facility facility : observableListFacility) {
            chooseFacility.getItems().add(facility.getStatus() + " | " + facility.getName());
        }
        chooseFacilityCopy.getItems().addAll(chooseFacility.getItems());
        employeeToFacilityButton.setDisable(true);
        employeeInfoButton.setDisable(true);
        addButton.setVisible(false);
        chooseFacility.setVisible(false);
    }

    @FXML
    public void showEmployeeInfoPage(ActionEvent e) throws IOException {
        if(table.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Wybierz pracownika!");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/dashboard/employee/info/EmployeeInfo.fxml"));
        Parent root = loader.load();

        EmployeeInfoController controller = loader.getController();
        controller.initUserData(table.getSelectionModel().getSelectedItem());
        controller.drawPane("admin");

        mainArea.getChildren().clear();
        mainArea.getChildren().addAll(root);
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
        fillSalary(ol);
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

    public void filterBySalary() {
        if (chooseFacilityCopy.getValue() == null) {
            try {
                observableList = Database.employeeDataViewBySalary("all", 0, Integer.parseInt(minSalary.getText()), Integer.parseInt(maxSalary.getText()));
            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Błąd!");
                alert.setHeaderText("Podaj zakresy poprawnie!");
                alert.showAndWait();
                return;
            }
        } else {
            try {
                observableList = Database.employeeDataViewBySalary("for Facility", facilityId, Integer.parseInt(minSalary.getText()), Integer.parseInt(maxSalary.getText()));
            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Błąd!");
                alert.setHeaderText("Podaj zakresy poprawnie!");
                alert.showAndWait();
                return;
            }
        }
        assert observableList != null;
        table.setItems(observableList);
        fillSalary(observableList);
    }

    public void fillSalary(ObservableList<Employee> ol) {
        int min, max;
        if (ol == null || ol.size() == 0) {
            min = 0;
            max = 0;
        } else {
            min = ol.get(0).getSalary();
            max = ol.get(0).getSalary();
            for (Employee e : ol) {
                if (e.getSalary() < min)
                    min = e.getSalary();
                if (e.getSalary() > max)
                    max = e.getSalary();
            }
        }
        minSalary.setText(String.valueOf(min));
        maxSalary.setText(String.valueOf(max));
    }

    @FXML
    public void enableButtons(MouseEvent e) {
        if (table.getSelectionModel().getSelectedIndex() != -1) {
            employeeToFacilityButton.setDisable(false);
            employeeInfoButton.setDisable(false);
        }
    }

    @FXML
    public void addEmployeeToFacility(ActionEvent event) {
        if(table.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Wybierz pracownika!");
            alert.showAndWait();
            return;
        }
        Employee employee = table.getSelectionModel().getSelectedItem();
        String facility = chooseFacility.getSelectionModel().getSelectedItem();
        if(facility == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Podaj obiekt!");
            alert.showAndWait();
            return;
        }
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
        if(facility == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Podaj obiekt!");
            alert.showAndWait();
            return;
        }
        String[] facilityInfo = facility.split(Pattern.quote(" | "));
        String facilityStatus = facilityInfo[0];
        String facilityName = facilityInfo[1];
        facilityId = 0;
        for (Facility f : observableListFacility) {
            if (f.getName().equals(facilityName) && f.getStatus().equals(facilityStatus)) {
                facilityId = f.getId();
                break;
            }
        }
        fillTable(Database.employeeDataView("for Facility", facilityId));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
