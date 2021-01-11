package application.dashboard.facility.control.facilityall;

import application.dashboard.employee.info.EmployeeInfoController;
import application.database.Database;
import application.entities.Employee;
import application.entities.Facility;
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
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FacilityAllController implements Initializable {
    @FXML
    private Pane mainArea;
    @FXML
    private TableView<Facility> table;
    @FXML
    private TableColumn<Facility, String> nameCol;
    @FXML
    private TableColumn<Facility, String> statusCol;
    @FXML
    private TableView<Employee> tableEmployee;
    @FXML
    private TableColumn<Employee, String> employeeFirstNameCol;
    @FXML
    private TableColumn<Employee, String> employeeLastNameCol;
    @FXML
    private TableColumn<Employee, String> employeePositionCol;
    @FXML
    private ListView<String> tableOptions;
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
    private TextField holidayProceeds;
    @FXML
    private DatePicker date1;
    @FXML
    private DatePicker holidayDate;
    @FXML
    private DatePicker date2;
    @FXML
    private DatePicker date3;
    @FXML
    private Button addButton;
    @FXML
    private Button addButtonHoliday;
    @FXML
    private Button listInspectionButton;
    @FXML
    private Button listHolidayButton;
    @FXML
    private Button listEmployeeButton;
    @FXML
    private Button employeeInfoButton;
    @FXML
    private Pane pane;
    @FXML
    private Pane holidayPane;
    @FXML
    private ComboBox<String> holidayName;
    @FXML
    private ComboBox<String> holidayEmployee;
    private ObservableList<Employee> observableList;
    private ObservableList<Employee> observableListForFacility;
    int choosenFacility;

    public void fillData() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<Facility> observableList = Database.facilityDataView();
        table.setItems(observableList);
        choosenFacility = table.getSelectionModel().getSelectedIndex();
    }

    @FXML
    public void showEmployeeInfoPage(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/dashboard/employee/info/EmployeeInfo.fxml"));
        Parent root = loader.load();

        EmployeeInfoController controller = loader.getController();
        controller.initUserData(tableEmployee.getSelectionModel().getSelectedItem());
        controller.drawPane("admin");

        mainArea.getChildren().clear();
        mainArea.getChildren().addAll(root);
    }

    @FXML
    public void showInspections(ActionEvent event) {
        ObservableList<String> observableListInspection = Database.inspectionDataView(table.getSelectionModel().getSelectedItem().getId());
        tableOptions.setItems(observableListInspection);
    }

    @FXML
    public void showHolidays(ActionEvent event) {
        ObservableList<String> observableListInspection = Database.holidayDataView(table.getSelectionModel().getSelectedItem().getId());
        tableOptions.setItems(observableListInspection);
    }

    @FXML
    public void showEmployees(ActionEvent event) {
        employeeFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employeeLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employeePositionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        observableListForFacility = Database.employeeDataView("for Facility", table.getSelectionModel().getSelectedItem().getId());
        tableEmployee.setItems(observableListForFacility);
    }

    @FXML
    public void addHoliday(ActionEvent event) {
        String employeeFirstName;
        String employeeLastName;

        if (holidayEmployee.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Podaj dane pracownika!");
            alert.showAndWait();
            return;
        }

        if (holidayName.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Podaj święto!");
            alert.showAndWait();
            return;
        }

        int employeeId = 0;
        employeeFirstName = holidayEmployee.getValue().split(" ", 3)[1];
        employeeLastName = holidayEmployee.getValue().split(" ", 3)[0];
        for (Employee e : observableListForFacility) {
            if (e.getFirstName().equals(employeeFirstName) && e.getLastName().equals(employeeLastName)) {
                employeeId = e.getId();
            }
        }

        int facilityId = table.getSelectionModel().getSelectedItem().getId();

        try {
            Database.addHolidayForFacility(facilityId, employeeId, holidayName.getValue(), holidayDate.getValue(), Float.parseFloat(holidayProceeds.getText()));
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Podaj dochód poprawnie!");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Udało się!");
        alert.setHeaderText("święto zostało dodane!");
        alert.showAndWait();

        table.getSelectionModel().select(-1);
        addButton.setDisable(true);
        addButtonHoliday.setDisable(true);
        listInspectionButton.setDisable(true);
        listHolidayButton.setDisable(true);
        listEmployeeButton.setDisable(true);
        employeeInfoButton.setDisable(true);
        tableOptions.getItems().clear();
        tableEmployee.getItems().clear();
        pane.setVisible(false);
        holidayPane.setVisible(false);
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
            if(employee1Id == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Udało się!");
                alert.setHeaderText("Pracownik nie istnieje!");
                alert.showAndWait();
                return;
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
            if(employee2Id == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Udało się!");
                alert.setHeaderText("Pracownik nie istnieje!");
                alert.showAndWait();
                return;
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
            if(employee3Id == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Udało się!");
                alert.setHeaderText("Pracownik nie istnieje!");
                alert.showAndWait();
                return;
            }
        }

        int facilityId = table.getSelectionModel().getSelectedItem().getId();

        if(employeeId == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Udało się!");
            alert.setHeaderText("Pracownik nie istnieje!");
            alert.showAndWait();
            return;
        }

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
        listInspectionButton.setDisable(true);
        listHolidayButton.setDisable(true);
        listEmployeeButton.setDisable(true);
        tableOptions.getItems().clear();
        tableEmployee.getItems().clear();
        addButtonHoliday.setDisable(true);
        employeeInfoButton.setDisable(true);
        holidayPane.setVisible(false);
        pane.setVisible(false);
    }

    @FXML
    public void enableButtons(MouseEvent event) {
        if (choosenFacility != table.getSelectionModel().getSelectedIndex()) {
            choosenFacility = table.getSelectionModel().getSelectedIndex();
            addButton.setDisable(true);
            addButtonHoliday.setDisable(true);
            pane.setVisible(false);
            holidayPane.setVisible(false);
            holidayEmployee.getItems().clear();
            listInspectionButton.setDisable(true);
            listHolidayButton.setDisable(true);
            listEmployeeButton.setDisable(true);
            tableEmployee.getItems().clear();
        }
        if (choosenFacility != -1) {
            addButton.setDisable(false);
            addButtonHoliday.setDisable(false);
            listInspectionButton.setDisable(false);
            listHolidayButton.setDisable(false);
            listEmployeeButton.setDisable(false);
        }
    }

    @FXML
    public void enableEmployeeInfoButton(MouseEvent event) {
        if (tableEmployee.getSelectionModel().getSelectedIndex() != -1) {
            employeeInfoButton.setDisable(false);
        }
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
    public void showAddHoliday(ActionEvent event) {
        holidayPane.setVisible(true);
        holidayName.getItems().addAll("Wielkanoc", "Boże Narodzenie");
        observableListForFacility = Database.employeeDataView("for Facility", table.getSelectionModel().getSelectedItem().getId());
        assert observableListForFacility != null;
        for (Employee e : observableListForFacility) {
            holidayEmployee.getItems().add(e.toString());
        }
        assert observableListForFacility != null;
        employeeText.setText(observableListForFacility.get(0).toString());
    }

    @FXML
    public void checkAnswer(ActionEvent event) {
        if (answer1.getValue() != null) {
            if (answer1.getValue().equals("Nie")) {
                employeeText1.setVisible(true);
                descriptionText1.setVisible(true);
                date1.setVisible(true);
                assert observableList != null;
                employeeText1.setText(observableList.get(0).toString());
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
                assert observableList != null;
                employeeText2.setText(observableList.get(0).toString());
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
                assert observableList != null;
                employeeText3.setText(observableList.get(0).toString());
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
        addButtonHoliday.setDisable(true);
        pane.setVisible(false);
        holidayPane.setVisible(false);
        listInspectionButton.setDisable(true);
        listHolidayButton.setDisable(true);
        listEmployeeButton.setDisable(true);
        employeeInfoButton.setDisable(true);

        observableList = Database.employeeDataView("all", 0);

        assert observableList != null;
        employeeText.setText(observableList.get(0).toString());

        TextFields.bindAutoCompletion(employeeText, observableList);
        TextFields.bindAutoCompletion(employeeText1, observableList);
        TextFields.bindAutoCompletion(employeeText2, observableList);
        TextFields.bindAutoCompletion(employeeText3, observableList);
    }
}
