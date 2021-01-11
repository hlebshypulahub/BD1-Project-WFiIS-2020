package application.dashboard.employee.info;

import application.database.Database;
import application.entities.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class EmployeeInfoController implements Initializable {
    @FXML
    private Label header;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label dob;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label position;
    @FXML
    private Label category;
    @FXML
    private Label salary;
    @FXML
    private Label ppe;
    @FXML
    private Label courseHoursSum;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField firstNameEdit;
    @FXML
    private TextField lastNameEdit;
    @FXML
    private DatePicker dobEdit;
    @FXML
    private TextField phoneEdit;
    @FXML
    private TextField addressEdit;
    @FXML
    private TextField positionEdit;
    @FXML
    private TextField categoryEdit;
    @FXML
    private TextField salaryEdit;
    @FXML
    private TextField courseHoursSumEdit;
    @FXML
    private TextField courseName;
    @FXML
    private TextField courseHours;
    @FXML
    private Button courseAddButton;
    @FXML
    private Button showCourseAddButton;
    @FXML
    private DatePicker ppeEdit;
    @FXML
    private VBox dataVBox;
    @FXML
    private VBox editVBox;
    @FXML
    private ListView<String> responsibilityList;
    private Employee employee;

    @FXML
    public void deleteEmployee(ActionEvent event) {
        Database.deleteEmployee(employee);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Udało się!");
        alert.setHeaderText("Pracownik został usunięty!");
        alert.showAndWait();
    }

    @FXML
    public void showCourseAdd(ActionEvent event) {
        courseHours.setVisible(true);
        courseName.setVisible(true);
        courseAddButton.setVisible(true);
    }

    @FXML
    public void addCourse(ActionEvent event) {
        try {
            courseHoursSum.setText(courseHoursSum.getText().split(":")[0] + " " + Database.addCourse(employee, courseName.getText(), Integer.parseInt(courseHours.getText())));
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Podaj ilość godzin poprawnie!");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Udało się!");
        alert.setHeaderText("Kurs został dodany!");
        alert.showAndWait();
        courseName.setVisible(false);
        courseHours.setVisible(false);
        courseAddButton.setVisible(false);
    }

    public void initUserData(Employee employee) {
        this.employee = employee;
        ArrayList<String> arrayList = Database.fillEmployeeResposibility(employee.getId());
        assert arrayList != null;
        for (String a : arrayList) {
            responsibilityList.getItems().add(a);
        }
        setHeader();
        setData();
    }

    public void setHeader() {
        header.setText("Dane użytkownika: " + employee.getFirstName() + " " + employee.getLastName());
    }

    public void drawPane(String type) {
        if (type.equals("user")) {
            dataVBox.getChildren().removeAll(editButton, deleteButton);
            editVBox.getChildren().removeAll(firstNameEdit, lastNameEdit, dobEdit,
                    phoneEdit, addressEdit, positionEdit, categoryEdit, salaryEdit, ppeEdit, courseHoursSumEdit, showCourseAddButton);
        }
        if (type.equals("admin")) {
            dataVBox.getChildren().removeAll(editButton);
            editVBox.getChildren().removeAll(firstNameEdit, lastNameEdit, dobEdit,
                    phoneEdit, addressEdit, positionEdit, categoryEdit, salaryEdit, ppeEdit, courseHoursSumEdit);
        }
    }

    public void setData() {
        firstName.setText(firstName.getText() + employee.getFirstName());
        lastName.setText(lastName.getText() + employee.getLastName());
        dob.setText(dob.getText() + (employee.getDOB() == null ? "" : employee.getDOB()));
        phone.setText(phone.getText() + employee.getPhone());
        address.setText(address.getText() + employee.getAddress());
        position.setText(position.getText() + employee.getPosition());
        category.setText(category.getText() + employee.getCategory());
        salary.setText(salary.getText() + employee.getSalary());
        ppe.setText(ppe.getText() + (employee.getPPE() == null ? "" : employee.getPPE()));
        courseHoursSum.setText(courseHoursSum.getText() + employee.getCourseHoursSum());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courseHours.setVisible(false);
        courseName.setVisible(false);
        courseAddButton.setVisible(false);
    }
}
