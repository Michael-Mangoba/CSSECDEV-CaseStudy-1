package Controller;

import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import java.sql.*;
import java.util.ArrayList;

public class SQLite {

    public int DEBUG_MODE = 0;
    String driverURL = "jdbc:sqlite:" + "database.db";

    public void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Database database.db created.");
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public void createHistoryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS history (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " username TEXT NOT NULL,\n"
                + " name TEXT NOT NULL,\n"
                + " stock INTEGER DEFAULT 0,\n"
                + " timestamp TEXT NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public void createLogsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS logs (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " event TEXT NOT NULL,\n"
                + " username TEXT NOT NULL,\n"
                + " desc TEXT NOT NULL,\n"
                + " timestamp TEXT NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public void createProductTable() {
        String sql = "CREATE TABLE IF NOT EXISTS product (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name TEXT NOT NULL UNIQUE,\n"
                + " stock INTEGER DEFAULT 0,\n"
                + " price REAL DEFAULT 0.00\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " username TEXT NOT NULL UNIQUE,\n"
                + " password TEXT NOT NULL,\n"
                + " role INTEGER DEFAULT 2,\n"
                + " locked INTEGER DEFAULT 0\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public void addHistory(String username, String name, int stock, String timestamp) {
        String sql = "INSERT INTO history(username, name, stock, timestamp) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, name);
            pstmt.setInt(3, stock);
            pstmt.setString(4, timestamp);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public void addLogs(String event, String username, String desc, String timestamp) {
        String sql = "INSERT INTO logs(event, username, desc, timestamp) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, event);
            pstmt.setString(2, username);
            pstmt.setString(3, desc);
            pstmt.setString(4, timestamp);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public void addProduct(String name, int stock, double price) {
        String sql = "INSERT INTO product(name, stock, price) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, stock);
            pstmt.setDouble(3, price);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public void addUser(String username, String password, int role) {
        String sql = "INSERT INTO users(username, password, role) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, role);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public ArrayList<History> getHistory() {
        String sql = "SELECT id, username, name, stock, timestamp FROM history";
        ArrayList<History> histories = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return histories;
    }
public void updateUserRole(String username, int role) {
        String sql = "UPDATE users SET role = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, role);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            System.out.println("Updated role for user: " + username);
        } catch (SQLException ex) {
            System.out.println("Error updating user role: " + ex.getMessage());
        }
    }

    public void removeUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            System.out.println("User " + username + " has been deleted.");
        } catch (SQLException ex) {
            System.out.println("Error removing user: " + ex.getMessage());
        }
    }

    public void setLock(String username) {
        String selectSql = "SELECT locked FROM users WHERE username = ?";
        String updateSql = "UPDATE users SET locked = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int currentLockStatus = rs.getInt("locked");
                int newLockStatus = (currentLockStatus == 0) ? 1 : 0;

                updateStmt.setInt(1, newLockStatus);
                updateStmt.setString(2, username);
                int affectedRows = updateStmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("User " + username + " has been " + (newLockStatus == 1 ? "locked." : "unlocked."));
                } else {
                    System.out.println("Failed to update lock status for user " + username);
                }
            } else {
                System.out.println("User " + username + " not found.");
            }

        } catch (SQLException ex) {
            System.out.println("Error setting lock: " + ex.getMessage());
        }
    }

    public void updateUserPassword(String username, String password) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, password);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            System.out.println("Password updated for user: " + username);
        } catch (SQLException ex) {
            System.out.println("Error updating user password: " + ex.getMessage());
        }
    }


    public ArrayList<Logs> getLogs() {
        String sql = "SELECT id, event, username, desc, timestamp FROM logs";
        ArrayList<Logs> logs = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                logs.add(new Logs(rs.getInt("id"),
                        rs.getString("event"),
                        rs.getString("username"),
                        rs.getString("desc"),
                        rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return logs;
    }
    
    

    public ArrayList<User> getUsers() {
        String sql = "SELECT id, username, password, role, locked FROM users";
        ArrayList<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("role"),
                        rs.getInt("locked")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return users;
    }

    // Add other necessary methods for session management and user handling
}
