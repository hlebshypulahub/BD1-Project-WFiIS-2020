package application.database;

import application.entities.Employee;
import application.entities.Facility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class Database {
    private static final String dbURL = "jdbc:postgresql://hattie.db.elephantsql.com:5432/gvfmqimz";
    private static final String dbUser = "gvfmqimz";
    private static final String dbPassword = "kEfzcAjK-loVLrWOQUfwniLf8TQvZmnf";

    public static Connection connect() {
        long startTime = System.nanoTime();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            System.out.print("Connected to the PostgreSQL server successfully");
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        }
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println(" | " + (double) elapsedTime / 1000000 + " ms");

        return conn;
    }

    public static Employee login(String username, String password) {
        String sql = "select * from loginAndFillInfo('" + username + "', '" + password + "');";

        try (
                Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            Employee employee = new Employee();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee.setId(rs.getInt("id_emp"));
                employee.setRole(rs.getString("role"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setAddress(rs.getString("address"));
                employee.setCategory(rs.getString("category"));
                employee.setCourseHoursSum(rs.getString("course_hours_sum"));
                employee.setDOB(rs.getDate("DOB") == null ? null : rs.getDate("DOB").toLocalDate());
                employee.setPhone(rs.getString("phone"));
                employee.setPosition(rs.getString("pos"));
                employee.setPPE(rs.getDate("PPE") == null ? null : rs.getDate("PPE").toLocalDate());
                employee.setSalary(rs.getString("salary"));
                ps.close();
                rs.close();
                conn.close();
                return employee;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }

        return null;
    }

    public static ArrayList<String> fillEmployeeResposibility(int employeeId) {
        String sql = "SELECT * FROM employee_resposibility WHERE id_employee = " + employeeId + ";";
        ArrayList<String> arrayList = new ArrayList<>();

        try (Connection conn = connect();
             Statement st = conn.createStatement();
        ) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                arrayList.add(rs.getString("description") + " (na obiekcie: " + rs.getString("name") + ", termin do " + rs.getDate("correction_term") + ")");
            }
            return arrayList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static boolean addEmployee(Employee employee) {
        String sql = "SELECT addemployee(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getUsername());
            ps.setString(4, employee.getPassword());
            ps.setObject(5, employee.getRole(), Types.OTHER);
            ps.setDate(6, employee.getDOB() == null ? null : Date.valueOf(String.valueOf(employee.getDOB())));
            ps.setString(7, employee.getPhone());
            ps.setString(8, employee.getAddress());
            ps.setObject(9, employee.getPosition(), Types.OTHER);
            ps.setObject(10, employee.getCategory(), Types.OTHER);
            ps.setInt(11, employee.getSalary());
            ps.setDate(12, employee.getPPE() == null ? null : Date.valueOf(String.valueOf(employee.getDOB())));
            ps.setInt(13, employee.getCourseHoursSum());
            ps.execute();
            return true;
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void fillEmployeeEnums(ComboBox<String> role, ComboBox<String> category, ComboBox<String> position) {
        String sql = "SELECT * FROM employee_enums_view;";

        try (
                Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                switch (rs.getString("typname")) {
                    case "employee_category":
                        category.getItems().add(rs.getString("enumLabel"));
                        break;
                    case "employee_position":
                        position.getItems().add(rs.getString("enumLabel"));
                        break;
                    case "employee_role":
                        role.getItems().add(rs.getString("enumLabel"));
                        break;
                }
                ps.close();
                conn.close();
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
        }
    }

    public static ObservableList<Employee> employeeDataView(String type, int idFacility) {
        String sql = "";
        if (type.equals("all")) {
            sql = "SELECT * FROM employee_data_view";
        } else if (type.equals("forFacility")) {
            sql = "SELECT * FROM employee_data_view edv JOIN employee_facility ef ON edv.id_employee = ef.id_employee WHERE ef.id_facility = " + idFacility + ";";
        }
        ObservableList<Employee> observableList = FXCollections.observableArrayList();

        try (Connection conn = connect();
             Statement st = conn.createStatement();
        ) {
            long startTime = System.nanoTime();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id_employee"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setCategory(rs.getString("category"));
                employee.setDOB(rs.getDate("DOB") == null ? null : rs.getDate("DOB").toLocalDate());
                employee.setPosition(rs.getString("position"));
                employee.setPPE(rs.getDate("PPE") == null ? null : rs.getDate("PPE").toLocalDate());
                employee.setSalary(rs.getString("salary"));
                observableList.add(employee);
            }
            long elapsedTime = System.nanoTime() - startTime;
            System.out.println(" | " + (double) elapsedTime / 1000000 + " ms");
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static ObservableList<Facility> facilityDataView() {
        String sql = "SELECT * FROM facility_data_view;";
        ObservableList<Facility> observableList = FXCollections.observableArrayList();

        try (Connection conn = connect();
             Statement st = conn.createStatement();
        ) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Facility facility = new Facility();
                facility.setId(rs.getInt("id_facility"));
                facility.setName(rs.getString("name"));
                facility.setStatus(rs.getString("status"));
                observableList.add(facility);
            }
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static void fillFacilityEnums(ComboBox<String> status) {
        String sql = "SELECT e.enumlabel FROM pg_type t, pg_enum e WHERE t.oid = e.enumtypid AND typname = 'facility_status';";

        try (
                Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                status.getItems().add(rs.getString("enumLabel"));
                ps.close();
                conn.close();
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
        }
    }

    public static boolean addFacility(Facility facility) {
        String sql = "SELECT addFacility('" + facility.getName() + "', '" + facility.getStatus() + "');";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            return true;
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean addEmployeeToFacility(int employeeId, String facilityStatus, String facilityName) {
        String sql = "SELECT addEmployeeToFacility('" + employeeId + "', '" + facilityStatus + "', '" + facilityName + "');";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            return true;
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void addInspection(int employeeId, int facilityId, LocalDate inspectionDate, String description,
                                     String question1, String answer1, int employee1Id, LocalDate date1, String description1, String question2, String answer2, int employee2Id, LocalDate date2, String description2,
                                     String question3, String answer3, int employee3Id, LocalDate date3, String description3) {
        String sql = "INSERT INTO inspection (id_employee, id_facility, date, description) VALUES (" + employeeId + ", " + facilityId + ", " + (inspectionDate == null ? null : "'" + inspectionDate + "'") + ", '" + description + "') RETURNING id_inspection;";

        int inspectionId = 0;

        try (
                Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inspectionId = rs.getInt("id_inspection");
                ps.close();
                addCheckup(conn, inspectionId, question1, answer1, employee1Id, date1, description1);
                addCheckup(conn, inspectionId, question2, answer2, employee2Id, date2, description2);
                addCheckup(conn, inspectionId, question3, answer3, employee3Id, date3, description3);
                conn.close();
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            System.exit(1);
        }
    }

    public static void addCheckup(Connection conn, int inspectionId, String question, String answer, int employeeId, LocalDate date, String description) {
        String sql = "SELECT * FROM addCheckup (" + inspectionId + ", '" + question + "', '" + answer + "', " + (date == null ? null : "'" + date + "'") + ", '" + employeeId + "', '" + description + "');";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}























































