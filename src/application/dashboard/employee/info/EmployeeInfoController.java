package application.dashboard.employee.info;

import application.entities.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
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
    private DatePicker ppeEdit;
    @FXML
    private VBox dataVBox;
    @FXML
    private VBox editVBox;
    private Employee userEmployee;
    private Employee procEmployee;

    public void initUserData(Employee employee) {
        this.userEmployee = employee;
        setHeader();
        setData();
    }

    public void setHeader() {
        header.setText("Dane użytkownika: " + userEmployee.getFirstName() + " " + userEmployee.getLastName());
    }

    public void drawPane(String type) {
        if (type.equals("user")) {
            dataVBox.getChildren().removeAll(editButton, deleteButton);
            editVBox.getChildren().removeAll(firstNameEdit, lastNameEdit, dobEdit,
                    phoneEdit, addressEdit, positionEdit, categoryEdit, salaryEdit, ppeEdit, courseHoursSumEdit);
        }
    }

    public void setData() {
        firstName.setText(firstName.getText() + userEmployee.getFirstName());
        lastName.setText(lastName.getText() + userEmployee.getLastName());
        dob.setText(dob.getText() + userEmployee.getDOB());
        phone.setText(phone.getText() + userEmployee.getPhone());
        address.setText(address.getText() + userEmployee.getAddress());
        position.setText(position.getText() + userEmployee.getPosition());
        category.setText(category.getText() + userEmployee.getCategory());
        salary.setText(salary.getText() + userEmployee.getSalary());
        ppe.setText(ppe.getText() + userEmployee.getPPE());
        courseHoursSum.setText(courseHoursSum.getText() + userEmployee.getCourseHoursSum());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
