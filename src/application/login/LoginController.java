package application.login;

import application.dashboard.DashboardController;
import application.database.Database;
import application.entities.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button loginButton;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    private void login(ActionEvent event) throws IOException {
        Employee employee = Database.login(username.getText(), password.getText());

        if (employee != null) {
            employee = Database.employeeFillInfo(employee);

//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Zalogowano!");
//            assert employee != null;
//            alert.setHeaderText("Witaj, " + employee.getFirstName() +
//                    " " + employee.getLastName() + "!");
//            alert.showAndWait();

            showDashboard(employee);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nie zalogowano!");
            alert.setHeaderText("Sprawdź poprawność username lub password!");
            alert.showAndWait();
        }
    }

    public void showDashboard(Employee employee) throws IOException {
        Stage primaryStage = (Stage) loginButton.getScene().getWindow();
        primaryStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../dashboard/Dashboard.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        DashboardController controller = loader.getController();
        controller.initData(employee);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
