package application.dashboard.employee.control.employeeadd;

import application.database.Database;
import application.entities.Employee;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class EmployeeAddController implements Initializable {
    @FXML
    private TextField firstNameAdd;
    @FXML
    private TextField lastNameAdd;
    @FXML
    private DatePicker dobAdd;
    @FXML
    private TextField phoneAdd;
    @FXML
    private TextField addressAdd;
    @FXML
    private ComboBox<String> positionAdd;
    @FXML
    private ComboBox<String> categoryAdd;
    @FXML
    private TextField salaryAdd;
    @FXML
    private TextField usernameAdd;
    @FXML
    private TextField passwordAdd;
    @FXML
    private TextField courseHoursSumAdd;
    @FXML
    private DatePicker ppeAdd;
    @FXML
    private ComboBox<String> roleAdd;

    @FXML
    public void addEmployee(ActionEvent e) {
        Employee employee = new Employee();

        employee.setUsername(usernameAdd.getText());
        employee.setAddress(firstNameAdd.getText());
        employee.setLastName(lastNameAdd.getText());
        try {
            Integer.parseInt(salaryAdd.getText());
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Wpisz wynagrodzenie poprawnie!");
            alert.showAndWait();
            return;
        }
        employee.setSalary(salaryAdd.getText());
        employee.setAddress(addressAdd.getText());
        employee.setPosition(positionAdd.getValue() == null ? "Pracownik" : positionAdd.getValue());
        if (!phoneAdd.getText().matches("^[\\+\\d]?(?:[\\d-.\\s()]*)$")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Podaj poprawny numer telefonu!");
            alert.showAndWait();
            return;
        }
        employee.setPhone(phoneAdd.getText());
        employee.setDOB(dobAdd.getValue());
        employee.setPPE(ppeAdd.getValue());
        employee.setCategory(categoryAdd.getValue() == null ? "1" : categoryAdd.getValue());
        employee.setRole(roleAdd.getValue() == null ? "employee" : roleAdd.getValue());
        employee.setPassword(passwordAdd.getText());
        try {
            Integer.parseInt(courseHoursSumAdd.getText());
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Wpisz sumę godzin na kursach poprawnie!");
            alert.showAndWait();
            return;
        }
        employee.setCourseHoursSum(courseHoursSumAdd.getText());

        if (Database.addEmployee(employee)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Udało się!");
            alert.setHeaderText("Pracownik " + employee.getFirstName() +
                    " " + employee.getLastName() + " został dodany!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Database.fillEmployeeEnums(roleAdd, categoryAdd, positionAdd);
    }
}
