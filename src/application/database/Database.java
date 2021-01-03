package application.database;

import application.entities.Employee;

import java.sql.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class Database {
    private static final String dbURL = "jdbc:postgresql://hattie.db.elephantsql.com:5432/gvfmqimz";
    private static final String dbUser = "gvfmqimz";
    private static final String dbPassword = "kEfzcAjK-loVLrWOQUfwniLf8TQvZmnf";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        }

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
                System.out.println("Login");
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
                employee.setCourseDeadline(rs.getDate("course_deadline_date").toLocalDate());
                employee.setCourseHoursSum(rs.getString("course_hours_sum"));
                employee.setDOB(rs.getDate("DOB").toLocalDate());
                employee.setFiveYearEnd(rs.getDate("five_year_end").toLocalDate());
                employee.setFiveYearStart(rs.getDate("five_year_start").toLocalDate());
                employee.setPhone(rs.getString("phone"));
                employee.setPosition(rs.getString("position"));
                employee.setPPE(rs.getDate("PPE") == null ? null : rs.getDate("PPE").toLocalDate());
                employee.setSalary(rs.getString("salary"));
                ps.close();
                rs.close();
                conn.close();
                System.out.println("RETURN EMPLOYEE");
                return employee;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        System.out.println("RETURN NULL");
        return null;
    }

    public static void addEmployee(Employee employee) {
        String sql = "INSERT INTO employee (last_name, first_name) VALUES (?, ?);";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getLastName());
            ps.setString(2, employee.getFirstName());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        setEmployeeId(employee);
        System.out.println(employee.getId());
        setEmployeeData(employee);
        System.out.println(employee.getUsername() + " " + employee.getPassword());
        setEmployeeInfo(employee);
    }

    public static void setEmployeeId(Employee employee) {
        String sql = "SELECT id_employee FROM employee WHERE first_name = ? AND last_name = ?;";

        try (
                Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee.setId(rs.getInt("id_employee"));
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
        String sql = "INSERT INTO employee_data (id_employee, username, password) VALUES (?, ?, ?);";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employee.getId());
            ps.setString(2, employee.getUsername());
            ps.setString(3, employee.getPassword());
//            ps.setObject(4, employee.getRole(), Types.OTHER);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setEmployeeInfo(Employee employee) {
        String sql = "INSERT INTO employee_info (id_employee, dob, phone, address, position, category, salary, ppe, course_hours_sum) VALUES (?,?,?,?,?,?,?,?,?);";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employee.getId());
//            ps.setDate(2, Date.valueOf(employee.getDOB().equals("") ? null :employee.getDOB()));
            ps.setDate(2, null);
            ps.setString(3, employee.getPhone());
            ps.setString(4, employee.getAddress());
            ps.setObject(5, employee.getPosition(), Types.OTHER);
            ps.setObject(6, employee.getCategory(), Types.OTHER);
//            ps.setInt(7, Integer.parseInt(employee.getSalary()));
//            ps.setDate(8, Date.valueOf(employee.getPPE()));
//            ps.setInt(9, Integer.parseInt(employee.getCourseHoursSum()));
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}























































