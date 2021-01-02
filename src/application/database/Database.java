package application.database;

import application.entities.Employee;

import java.sql.*;

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
                return employee;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
                employee.setCourseDeadline(rs.getDate("course_deadline_date"));
                employee.setCourseHoursSum(rs.getString("course_hours_sum"));
                employee.setDOB(rs.getDate("DOB"));
                employee.setFiveYearEnd(rs.getDate("five_year_end"));
                employee.setFiveYearStart(rs.getDate("five_year_start"));
                employee.setPhone(rs.getString("phone"));
                employee.setPosition(rs.getString("position"));
                employee.setPPE(rs.getDate("PPE"));
                employee.setSalary(rs.getString("salary"));
                ps.close();
                rs.close();
                conn.close();
                return employee;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return null;
    }
}






















































