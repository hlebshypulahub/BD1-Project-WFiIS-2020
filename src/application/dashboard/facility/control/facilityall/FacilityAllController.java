package application.dashboard.facility.control.facilityall;

import application.database.Database;
import application.entities.Employee;
import application.entities.Facility;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ResourceBundle;

public class FacilityAllController implements Initializable {
    @FXML
    private TableView<Facility> table;
    @FXML
    private TableColumn<Facility, String> nameCol;
    @FXML
    private TableColumn<Facility, String> statusCol;
    @FXML
    private TextField employeeText;
    @FXML
    private DatePicker date;
    @FXML
    private TextField descriptionText;
    @FXML
    private Label questionLabel;
    @FXML
    private Label question1Label;
    @FXML
    private Label question2Label;
    @FXML
    private Label question3Label;
    @FXML
    private ComboBox<String> answer1;
    @FXML
    private ComboBox<String> answer2;
    @FXML
    private ComboBox<String> answer3;
    @FXML
    private TextField employeeText1;
    @FXML
    private TextField employeeText2;
    @FXML
    private TextField employeeText3;
    @FXML
    private TextField descriptionText1;
    @FXML
    private TextField descriptionText2;
    @FXML
    private TextField descriptionText3;
    @FXML
    private DatePicker date1;
    @FXML
    private DatePicker date2;
    @FXML
    private DatePicker date3;
    @FXML
    private Button addButton;
    @FXML
    private Button addInspectionButton;
    @FXML
    private Pane pane;
    private ObservableList<Employee> observableList;

    public void fillData() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<Facility> observableList = Database.facilityDataView();
        table.setItems(observableList);
    }

    @FXML
    public void addInspection(ActionEvent event) {
        String employeeFirstName;
        String employeeLastName;

        if (employeeText.getText().equals("")
                || (employeeText1.isVisible() && employeeText1.getText().equals(""))
                || (employeeText2.isVisible() && employeeText2.getText().equals(""))
                || (employeeText3.isVisible() && employeeText3.getText().equals(""))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Podaj dane osoby odpowiedzialnej!");
            alert.showAndWait();
            return;
        }

        int employeeId = 0;
        if (!employeeText.getText().equals("")) {
            employeeFirstName = employeeText.getText().split(" ", 3)[1];
            employeeLastName = employeeText.getText().split(" ", 3)[0];
            for (Employee e : observableList) {
                if (e.getFirstName().equals(employeeFirstName) && e.getLastName().equals(employeeLastName)) {
                    employeeId = e.getId();
                }
            }
        }

        int employee1Id = 0;
        if (!employeeText1.getText().equals("")) {
            employeeFirstName = employeeText1.getText().split(" ", 3)[1];
            employeeLastName = employeeText1.getText().split(" ", 3)[0];
            for (Employee e : observableList) {
                if (e.getFirstName().equals(employeeFirstName) && e.getLastName().equals(employeeLastName)) {
                    employee1Id = e.getId();
                }
            }
        }

        int employee2Id = 0;
        if (!employeeText2.getText().equals("")) {
            employeeFirstName = employeeText2.getText().split(" ", 3)[1];
            employeeLastName = employeeText2.getText().split(" ", 3)[0];
            for (Employee e : observableList) {
                if (e.getFirstName().equals(employeeFirstName) && e.getLastName().equals(employeeLastName)) {
                    employee2Id = e.getId();
                }
            }
        }

        int employee3Id = 0;
        if (!employeeText3.getText().equals("")) {
            employeeFirstName = employeeText3.getText().split(" ", 3)[1];
            employeeLastName = employeeText3.getText().split(" ", 3)[0];
            for (Employee e : observableList) {
                if (e.getFirstName().equals(employeeFirstName) && e.getLastName().equals(employeeLastName)) {
                    employee3Id = e.getId();
                }
            }
        }

        int facilityId = table.getSelectionModel().getSelectedItem().getId();

        System.out.println(employeeId);

        Database.addInspection(employeeId, facilityId, date.getValue(), descriptionText.getText(),
                question1Label.getText(), answer1.getValue(), employee1Id, date1.getValue(), descriptionText1.getText(),
                question2Label.getText(), answer2.getValue(), employee2Id, date2.getValue(), descriptionText2.getText(),
                question3Label.getText(), answer3.getValue(), employee3Id, date3.getValue(), descriptionText3.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Udało się!");
        alert.setHeaderText("Inspekcja została dodana!");
        alert.showAndWait();

        table.getSelectionModel().select(-1);
        addButton.setDisable(true);
        pane.setVisible(false);
    }

    @FXML
    public void enableButtons(MouseEvent e) {
        if (table.getSelectionModel().getSelectedIndex() != -1)
            addButton.setDisable(false);
    }

    @FXML
    public void showAddInspection(ActionEvent event) {
        pane.setVisible(true);
        employeeText1.setVisible(false);
        descriptionText1.setVisible(false);
        date1.setVisible(false);
        employeeText2.setVisible(false);
        descriptionText2.setVisible(false);
        date2.setVisible(false);
        employeeText3.setVisible(false);
        descriptionText3.setVisible(false);
        date3.setVisible(false);

        answer1.getItems().addAll("Tak", "Nie");
        answer1.setValue("Tak");
        answer2.getItems().addAll("Tak", "Nie");
        answer2.setValue("Tak");
        answer3.getItems().addAll("Tak", "Nie");
        answer3.setValue("Tak");
    }

    @FXML
    public void checkAnswer(ActionEvent event) {
        if (answer1.getValue() != null) {
            if (answer1.getValue().equals("Nie")) {
                employeeText1.setVisible(true);
                descriptionText1.setVisible(true);
                date1.setVisible(true);
            } else {
                employeeText1.setVisible(false);
                descriptionText1.setVisible(false);
                date1.setVisible(false);
            }
        }
        if (answer2.getValue() != null) {
            if (answer2.getValue().equals("Nie")) {
                employeeText2.setVisible(true);
                descriptionText2.setVisible(true);
                date2.setVisible(true);
            } else {
                employeeText2.setVisible(false);
                descriptionText2.setVisible(false);
                date2.setVisible(false);
            }
        }
        if (answer3.getValue() != null) {
            if (answer3.getValue().equals("Nie")) {
                employeeText3.setVisible(true);
                descriptionText3.setVisible(true);
                date3.setVisible(true);
            } else {
                employeeText3.setVisible(false);
                descriptionText3.setVisible(false);
                date3.setVisible(false);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButton.setDisable(true);
        pane.setVisible(false);

        observableList = Database.employeeDataView("all", 0);

        TextFields.bindAutoCompletion(employeeText, observableList);
        TextFields.bindAutoCompletion(employeeText1, observableList);
        TextFields.bindAutoCompletion(employeeText2, observableList);
        TextFields.bindAutoCompletion(employeeText3, observableList);
    }
}
