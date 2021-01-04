package application.database;

import application.entities.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.*;

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
        String sql = "SELECT id_employee, role FROM employee_data where username = ? AND password = ?";

        try (
                Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            Employee employee = new Employee();
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee.setId(rs.getInt("id_employee"));
                employee.setRole(rs.getString("role"));
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

    public static Employee employeeFillInfo(Employee employee) {
        String sql = "SELECT e.first_name, e.last_name, ei.* FROM employee e JOIN employee_info ei ON e.id_employee = ei.id_employee WHERE e.id_employee = ?;";

        try (
                Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employee.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setAddress(rs.getString("address"));
                employee.setCategory(rs.getString("category"));
                employee.setCourseHoursSum(rs.getString("course_hours_sum"));
                employee.setDOB(rs.getDate("DOB") == null ? null : rs.getDate("DOB").toLocalDate());
                employee.setPhone(rs.getString("phone"));
                employee.setPosition(rs.getString("position"));
                employee.setPPE(rs.getDate("PPE") == null ? null : rs.getDate("PPE").toLocalDate());
                employee.setSalary(rs.getString("salary"));
                ps.close();
                rs.close();
                conn.close();
                return employee;
            }
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }

        return null;
    }

    public static boolean checkIfAddPossible(Employee employee) {
        String sql = "SELECT EXISTS(SELECT username FROM employee_data WHERE username = ?);";
        String sql2 = "SELECT EXISTS(SELECT first_name, last_name FROM employee WHERE first_name = '" + employee.getFirstName() + "' AND last_name = '" + employee.getLastName() + "');";

        try (
                Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql);
                PreparedStatement ps2 = conn.prepareStatement(sql2)) {
            ps.setString(1, employee.getUsername());
            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            boolean exists = false, exists2 = false;
            if (rs.next()) {
                exists = rs.getBoolean("exists");
                ps.close();
                rs.close();
                conn.close();
            }
            if (rs2.next()) {
                exists2 = rs2.getBoolean("exists");
                ps2.close();
                rs2.close();
            }
            return !(exists || exists2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
        }

        return false;
    }

    public static boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO employee (last_name, first_name) VALUES (?, ?);";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getLastName());
            System.out.println(employee.getFirstName());
            ps.setString(2, employee.getFirstName());
            System.out.println(employee.getLastName());
            ps.execute();
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }

        setEmployeeId(employee);
        setEmployeeData(employee);
        setEmployeeInfo(employee);

        return true;
    }

    public static void setEmployeeId(Employee employee) {
        String sql = "SELECT id_employee FROM employee WHERE first_name = '" + employee.getFirstName() + "' AND last_name = '" + employee.getLastName() + "'";

        try (
                Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee.setId(rs.getInt("id_employee"));
                System.out.println(employee.getId());
                ps.close();
                rs.close();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
        }
    }

    public static void setEmployeeData(Employee employee) {
        String sql = "INSERT INTO employee_data (id_employee, username, password, role) VALUES (?, ?, ?, ?);";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employee.getId());
            ps.setString(2, employee.getUsername());
            ps.setString(3, employee.getPassword());
            ps.setObject(4, employee.getRole(), Types.OTHER);
            ps.execute();
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void setEmployeeInfo(Employee employee) {
        String sql = "INSERT INTO employee_info (id_employee, dob, phone, address, position, category, salary, ppe, course_hours_sum) VALUES (?,?,?,?,?,?,?,?,?);";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employee.getId());
            ps.setDate(2, employee.getDOB() == null ? null : Date.valueOf(String.valueOf(employee.getDOB())));
            ps.setString(3, employee.getPhone());
            ps.setString(4, employee.getAddress());
            ps.setObject(5, employee.getPosition(), Types.OTHER);
            ps.setObject(6, employee.getCategory(), Types.OTHER);
            ps.setInt(7, employee.getSalary());
            ps.setDate(8, employee.getPPE() == null ? null : Date.valueOf(String.valueOf(employee.getDOB())));
            ps.setInt(9, employee.getCourseHoursSum());
            ps.execute();
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void fillEmployeeEnums(ComboBox<String> role, ComboBox<String> category, ComboBox<String> position) {
        String sql = "SELECT t.typname, e.enumlabel FROM pg_type t, pg_enum e WHERE t.oid = e.enumtypid AND typname IN ('employee_role', 'employee_position', 'employee_category');\n";

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

    public static ObservableList<Employee> employeeDataView() {
        String sql = "SELECT * FROM employee_data_view";
        ObservableList<Employee> observableList = FXCollections.observableArrayList();

        try (Connection conn = connect();
             Statement st = conn.createStatement();
        ) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setCategory(rs.getString("category"));
                employee.setDOB(rs.getDate("DOB") == null ? null : rs.getDate("DOB").toLocalDate());
                employee.setPosition(rs.getString("position"));
                employee.setPPE(rs.getDate("PPE") == null ? null : rs.getDate("PPE").toLocalDate());
                employee.setSalary(rs.getString("salary"));
                observableList.add(employee);
            }
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }
}























































