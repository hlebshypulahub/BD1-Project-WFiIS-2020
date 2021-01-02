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
                System.out.println("4");
                employee.setId(rs.getInt("id_employee"));
                System.out.println(employee.getId());
                employee.setRole(rs.getString("role"));
                ps.close();
                rs.close();
                conn.close();
                return employee;
            }
            System.out.println("5");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return null;
    }
}






















































