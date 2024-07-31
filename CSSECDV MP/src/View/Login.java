package View;

import Controller.SQLite;
import Model.User;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat; // Add this import statement
import java.util.Date;

public class Login extends javax.swing.JPanel {

    public Frame frame;
    private int loginAttempts = 0;
    private long lastAttemptTime = 0;
    private static final int MAX_ATTEMPTS = 5;
    private static final long COOLDOWN_PERIOD = 5 * 60 * 1000;

    public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        usernameFld = new javax.swing.JTextField();
        passwordFld = new javax.swing.JTextField();
        registerBtn = new javax.swing.JButton();
        loginBtn = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        usernameFld.setBackground(new java.awt.Color(240, 240, 240));
        usernameFld.setFont(new java.awt.Font("Tahoma", 0, 18));
        usernameFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "USERNAME", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));

        passwordFld.setBackground(new java.awt.Color(240, 240, 240));
        passwordFld.setFont(new java.awt.Font("Tahoma", 0, 18));
        passwordFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));

        registerBtn.setFont(new java.awt.Font("Tahoma", 1, 24));
        registerBtn.setText("REGISTER");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        loginBtn.setFont(new java.awt.Font("Tahoma", 1, 24));
        loginBtn.setText("LOGIN");
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(registerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(usernameFld)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordFld, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(usernameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(126, Short.MAX_VALUE))
        );
    }

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {
        long currentTime = System.currentTimeMillis();
        SQLite sqlite = new SQLite();
        ArrayList<User> users = sqlite.getUsers();
        String inputUsername = usernameFld.getText();
        String inputPassword = passwordFld.getText();

        // Validate username and password
        if (!SecurityUtils.isValidUsername(inputUsername) || !SecurityUtils.isValidPassword(inputPassword)) {
            JOptionPane.showMessageDialog(this, "Invalid username or password format.");
            return;
        }

        if (loginAttempts >= MAX_ATTEMPTS && currentTime - lastAttemptTime < COOLDOWN_PERIOD) {
            long waitTime = (COOLDOWN_PERIOD - (currentTime - lastAttemptTime)) / 1000;
            JOptionPane.showMessageDialog(this, "Too many failed attempts. Please wait " + waitTime + " seconds before trying again.");
            logAttempt(inputUsername, false);
            return;
        }

        int userID = -1;
        boolean loginSuccess = false;

        for (User user : users) {
            if (user.getUsername().equals(inputUsername)) {
                if (user.getHashedPassword().equals(SecurityUtils.hashPassword(inputPassword)) && user.getLocked() == 0) {
                    userID = user.getId();
                    loginSuccess = true;
                    break;
                }
            }
        }

        logAttempt(inputUsername, loginSuccess);

        if (loginSuccess) {
            Session session = new Session();
            SecurityUtils.createSession(session, inputUsername);
            frame.setSession(session); // Assuming Frame class has a method to set the session
            frame.mainNav(); // Navigate to the main frame on successful login
        } else {
            loginAttempts++;
            lastAttemptTime = currentTime;
            JOptionPane.showMessageDialog(this, "Login failed; Invalid username or password. Attempts remaining: " + (MAX_ATTEMPTS - loginAttempts));
        }

        usernameFld.setText("");
        passwordFld.setText("");
    }

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {
        frame.registerNav();
        usernameFld.setText("");
        passwordFld.setText("");
    }

    private void logAttempt(String username, boolean success) {
        SQLite sqlite = new SQLite();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        String event = success ? "successful_login" : "failed_login";
        String desc = String.format("Login attempt for %s was %s", username, success ? "successful" : "failed");
        String timestamp = dateFormat.format(new Date());
        sqlite.addLogs(event, username, desc, timestamp);
    }

    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loginBtn;
    private javax.swing.JTextField passwordFld;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField usernameFld;
}
