package application.dashboard.employee.control.employeeall;

import application.entities.Employee;
import application.database.Database;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
