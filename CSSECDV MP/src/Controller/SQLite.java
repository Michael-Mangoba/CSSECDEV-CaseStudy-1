package Controller;

import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SQLite {
    
    public int DEBUG_MODE = 0;
    String driverURL = "jdbc:sqlite:" + "database.db";
    public User currentUser;
        
    public void login(String username){
        this.currentUser = this.getUser(username);
    }
    
    public void logout(){
        this.currentUser = null;
    }
    
    public User getCurrentUser(){
        return this.currentUser;
    }
    
    public int getCurrentUserRole(){
        return this.currentUser.getRole();
    }
    
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
    
    public void dropHistoryTable() {
        String sql = "DROP TABLE IF EXISTS history;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropLogsTable() {
        String sql = "DROP TABLE IF EXISTS logs;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropProductTable() {
        String sql = "DROP TABLE IF EXISTS product;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropUserTable() {
        String sql = "DROP TABLE IF EXISTS users;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addHistory(String username, String name, int stock, String timestamp) {
        String sql = "INSERT INTO history(username,name,stock,timestamp) VALUES('" + username + "','" + name + "','" + stock + "','" + timestamp + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addLogs(String event, String username, String desc, String timestamp) {
        String sql = "INSERT INTO logs(event,username,desc,timestamp) VALUES('" + event + "','" + username + "','" + desc + "','" + timestamp + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addProduct(String name, int stock, double price) {
        String sql = "INSERT INTO product(name,stock,price) VALUES('" + name + "','" + stock + "','" + price + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addUser(String username, String password) {
        String sql = "INSERT INTO users(username,password) VALUES('" + username + "','" + password + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            
//      PREPARED STATEMENT EXAMPLE
//      String sql = "INSERT INTO users(username,password) VALUES(?,?)";
//      PreparedStatement pstmt = conn.prepareStatement(sql)) {
//      pstmt.setString(1, username);
//      pstmt.setString(2, password);
//      pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    
    public ArrayList<History> getHistory(){
        String sql = "SELECT id, username, name, stock, timestamp FROM history";
        ArrayList<History> histories = new ArrayList<History>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
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
    
    public ArrayList<History> getHistory(String searchText) {
        String sql = "SELECT id, username, name, stock, timestamp FROM history WHERE username LIKE '%" + searchText + "%' OR name LIKE '%" + searchText + "%'";
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
    
    public ArrayList<Logs> getLogs(){
        String sql = "SELECT id, event, username, desc, timestamp FROM logs";
        ArrayList<Logs> logs = new ArrayList<Logs>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
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
    
    public User getUser(String username) {
        String sql = "SELECT id, username, password, role, locked FROM users WHERE username = '" + username + "'";
        User user = null;

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                user = new User(rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getInt("role"),
                                rs.getInt("locked"));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }

        return user;
    }
   
    public boolean isLast20AttemptsFailed(String username) {
        String sql = "SELECT * FROM logs WHERE event IN ('successful_login', 'failed_login') AND username='" + username + "' ORDER BY timestamp DESC LIMIT 20";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int failedCount = 0;
            int totalCount = 0;

            while (rs.next()) {
                totalCount++;
                if (rs.getString("desc").contains("was failed")) {
                    failedCount++;
                }
            }

            System.out.println("Total attempts checked: " + totalCount);
            System.out.println("Failed attempts: " + failedCount);

            return totalCount == 20 && failedCount == 20;

        } catch (Exception ex) {
            System.out.print(ex);
        }

        return false;
    }
    
    public Integer getLock(String username) {
        String sql = "SELECT locked FROM users WHERE username = '" + username + "'";
        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next() ? rs.getInt("locked") : null;

        } catch (Exception ex) {
            System.out.print(ex);
            return null;  // Error occurred
        }
    }
    
    public void setLock(String username) {
        String selectSql = "SELECT locked FROM users WHERE username = ?";
        String updateSql = "UPDATE users SET locked = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            // Fetch current lock status
            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int currentLockStatus = rs.getInt("locked");
                int newLockStatus = (currentLockStatus == 0) ? 1 : 0;

                // Update lock status
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

        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
   
    public ArrayList<Product> getProduct(){
        String sql = "SELECT id, name, stock, price FROM product";
        ArrayList<Product> products = new ArrayList<Product>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                products.add(new Product(rs.getInt("id"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return products;
    }
    
    public ArrayList<User> getUsers(){
        String sql = "SELECT id, username, password, role, locked FROM users";
        ArrayList<User> users = new ArrayList<User>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("password"),
                                   rs.getInt("role"),
                                   rs.getInt("locked")));
            }
        } catch (Exception ex) {}
        return users;
    }
    
    public int getUserRole(String username) {
        String sql = "SELECT role FROM users WHERE username = '" + username + "'";
        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt("role");
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return -1;
    }
   
    
    public void addUser(String username, String password, int role) {
        String sql = "INSERT INTO users(username,password,role) VALUES('" + username + "','" + password + "','" + role + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void removeUser(String username) {
        String sql = "DELETE FROM users WHERE username='" + username + "';";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("User " + username + " has been deleted.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public Product getProduct(String name){
        String sql = "SELECT id, name, stock, price FROM product WHERE name='" + name + "';";
        Product product = null;
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            product = new Product(  rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getInt("stock"),
                                    rs.getFloat("price"));
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return product;
    }
    
    public void updateProduct(Product product) {
        String sql = "UPDATE product SET name = '" + product.getName() + 
                     "', stock = " + product.getStock() +
                     ", price = " + product.getPrice() +
                     " WHERE id = " + product.getId() + ";";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                System.out.println("Product updated successfully: ID " + product.getId());
            } else {
                System.out.println("Product not found with ID: " + product.getId());
            }
        } catch (Exception ex) {
            System.out.println("Error updating product: " + ex.getMessage());
        }
    }
    
    public void removeProduct(String name) {
        String sql = "DELETE FROM product WHERE name='" + name + "';";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                System.out.println("Product '" + name + "' was successfully removed.");
            } else {
                System.out.println("No product found with the name '" + name + "'.");
            }

        } catch (Exception ex) {
            System.out.println("Error removing product: " + ex.getMessage());
        }
    }

    public void updateUserPassword(int userID, String newHashedPassword) {
        // SQL statement to update the password for a specific user ID
        String sql = "UPDATE users SET password = ? WHERE id = ?;";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set parameters for the prepared statement
            pstmt.setString(1, newHashedPassword);
            pstmt.setInt(2, userID);
            // Execute the update
            pstmt.executeUpdate();
            System.out.println("Password updated for user ID: " + userID);
        } catch (SQLException e) {
            System.out.println("Error updating user password: " + e.getMessage());
        }
    }

    public void updateUserRole(String username, int role) {
        String sql = "UPDATE users SET role = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, role);
            pstmt.setString(2, username);
            pstmt.executeUpdate();

            System.out.println("Updated role for user: " + username);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void updateUserPassword(String username, String password) {
        String sql = "UPDATE users SET password = '" + password + "' WHERE username = '" + username + "'";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Password updated successfully for user: " + username);
        } catch (Exception ex) {
            System.out.println("Error updating password: " + ex.getMessage());
        }
    }
}