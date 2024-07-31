package Controller;

import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import Model.User.Role;
import View.Frame;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public SQLite sqlite;

    public static void main(String[] args) {
        new Main().init();
    }

    public void init() {
        // Initialize a driver object
        sqlite = new SQLite();

        // Uncomment the following lines to set up the database initially
        /*
        sqlite.createNewDatabase();
        sqlite.dropHistoryTable();
        sqlite.dropLogsTable();
        sqlite.dropProductTable();
        sqlite.dropUserTable();
        sqlite.createHistoryTable();
        sqlite.createLogsTable();
        sqlite.createProductTable();
        sqlite.createUserTable();

        sqlite.addHistory("admin", "Antivirus", 1, "2019-04-03 14:30:00.000");
        sqlite.addHistory("manager", "Firewall", 1, "2019-04-03 14:30:01.000");
        sqlite.addHistory("staff", "Scanner", 1, "2019-04-03 14:30:02.000");

        sqlite.addLogs("NOTICE", "admin", "User creation successful", new Timestamp(new Date().getTime()).toString());
        sqlite.addLogs("NOTICE", "manager", "User creation successful", new Timestamp(new Date().getTime()).toString());
        sqlite.addLogs("NOTICE", "admin", "User creation successful", new Timestamp(new Date().getTime()).toString());

        sqlite.addProduct("Antivirus", 5, 500.0);
        sqlite.addProduct("Firewall", 3, 1000.0);
        sqlite.addProduct("Scanner", 10, 100.0);

        sqlite.addUser("admin", "qwerty1234", Role.ADMIN.ordinal());
        sqlite.addUser("manager", "qwerty1234", Role.MANAGER.ordinal());
        sqlite.addUser("staff", "qwerty1234", Role.STAFF.ordinal());
        sqlite.addUser("client1", "qwerty1234", Role.CLIENT.ordinal());
        sqlite.addUser("client2", "qwerty1234", Role.CLIENT.ordinal());

        ArrayList<History> histories = sqlite.getHistory();
        for (History history : histories) {
            System.out.println("===== History " + history.getId() + " =====");
            System.out.println(" Username: " + history.getUsername());
            System.out.println(" Name: " + history.getName());
            System.out.println(" Stock: " + history.getStock());
            System.out.println(" Timestamp: " + history.getTimestamp());
        }

        ArrayList<Logs> logs = sqlite.getLogs();
        for (Logs log : logs) {
            System.out.println("===== Logs " + log.getId() + " =====");
            System.out.println(" Username: " + log.getEvent());
            System.out.println(" Password: " + log.getUsername());
            System.out.println(" Role: " + log.getDesc());
            System.out.println(" Timestamp: " + log.getTimestamp());
        }

        ArrayList<Product> products = sqlite.getProduct();
        for (Product product : products) {
            System.out.println("===== Product " + product.getId() + " =====");
            System.out.println(" Name: " + product.getName());
            System.out.println(" Stock: " + product.getStock());
            System.out.println(" Price: " + product.getPrice());
        }

        ArrayList<User> users = sqlite.getUsers();
        for (User user : users) {
            System.out.println("===== User " + user.getId() + " =====");
            System.out.println(" Username: " + user.getUsername());
            System.out.println(" Password: " + user.getPassword());
            System.out.println(" Role: " + Role.values()[user.getRole()]);
            System.out.println(" Locked: " + user.getLocked());
        }
        */

        // Initialize User Interface
        Frame frame = new Frame();
        frame.init(this);
    }
}
