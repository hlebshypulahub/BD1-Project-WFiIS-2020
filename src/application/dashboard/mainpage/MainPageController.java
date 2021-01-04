package application.dashboard.mainpage;

import application.entities.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML
    private Label header;
    private Employee userEmployee;

    public void initUserData(Employee employee) {
        userEmployee = employee;
        setHeader();
    }

    public void setHeader() {
        header.setText("Witam, " + userEmployee.getFirstName() + " " + userEmployee.getLastName() + "!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
