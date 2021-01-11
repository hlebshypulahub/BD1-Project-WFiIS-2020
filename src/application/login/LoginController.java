package application.login;

import application.dashboard.DashboardController;
import application.database.Database;
import application.entities.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
            showDashboard(employee);
        }
    }

    public void showDashboard(Employee employee) throws IOException {
        Stage primaryStage = (Stage) loginButton.getScene().getWindow();
        primaryStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/dashboard/Dashboard.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Panel użytkownika");
        stage.setScene(new Scene(loader.load()));
        DashboardController controller = loader.getController();
        controller.initUserData(employee);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
