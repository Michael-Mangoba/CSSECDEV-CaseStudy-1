package View;
import Controller.SQLite;
import Controller.Main;
import Model.User;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import Controller.SessionManager;
import Controller.Session;


import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.io.FileReader;
import java.io.BufferedReader;

public class Login extends javax.swing.JPanel {

    public Frame frame;
    // Variable declaration
    private int loginAttempts = 0;
    private long lastAttemptTime = 0;
    private static final int MAX_ATTEMPTS = 5;
    private static final long COOLDOWN_PERIOD = 5 * 60 * 1000;
    private static final int DISABLED_ATTEMPTS = 15;
    private static final long ONE_HOUR = 60 * 60 * 1000;
    public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        usernameFld = new javax.swing.JTextField();
        passwordFld = new javax.swing.JTextField();
        registerBtn = new javax.swing.JButton();
        loginBtn = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        usernameFld.setBackground(new java.awt.Color(240, 240, 240));
        usernameFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernameFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "USERNAME", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        passwordFld.setBackground(new java.awt.Color(240, 240, 240));
        passwordFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passwordFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        registerBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        registerBtn.setText("REGISTER");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        loginBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
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
    }// </editor-fold>//GEN-END:initComponents
    // private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
    //     long currentTime = System.currentTimeMillis();
    //     SQLite sqlite = new SQLite();
    //     ArrayList<User> users = sqlite.getUsers();
    //     String inputUsername = usernameFld.getText();
    //     String inputPassword = passwordFld.getText();
        
    //     if(sqlite.getLock(inputUsername) == 1 || sqlite.getUserRole(inputUsername) == 1){
    //         JOptionPane.showMessageDialog(this, "Login failed; User is Disabled or Locked, please communicate with an Admin in order to re-enable the account");
    //         return;
    //     }
        
    //     if (loginAttempts >= MAX_ATTEMPTS && currentTime - lastAttemptTime < COOLDOWN_PERIOD) {
    //         long waitTime = (COOLDOWN_PERIOD - (currentTime - lastAttemptTime)) / 1000;
    //         JOptionPane.showMessageDialog(this, "Too many failed attempts. Please wait " + waitTime + " seconds before trying again.");
    //         //Not sure about this
    //         logAttempt(usernameFld.getText(), false);
    //         return;
    //     }
    
        
    
    //     int userID = -1;
    //     boolean loginSuccess = false;
    
    //     for (User user : users) {
    //         if (user.getUsername().equals(inputUsername)) {
    //             if (user.getHashedPassword().equals(SecurityUtils.hashPassword(inputPassword))) {
    //                 userID = user.getId();
    //                 loginSuccess = true;
    //                 break;
    //             }
    //         }
    //     }
    
    //     logAttempt(inputUsername, loginSuccess);
    
    //     if (loginSuccess) {
    //         frame.mainNav(inputUsername); // Navigate to the main frame on successful login
    //     } else {
    //         checkDisableUser(inputUsername);
    //         loginAttempts++;
    //         lastAttemptTime = currentTime;
    //         JOptionPane.showMessageDialog(this, "Login failed; Invalid user ID or password. Attempts remaining: " + (MAX_ATTEMPTS - loginAttempts));
    //     }
        
    //     usernameFld.setText("");
    //     passwordFld.setText("");
    // }
    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {
        long currentTime = System.currentTimeMillis();
        SQLite sqlite = new SQLite();
        ArrayList<User> users = sqlite.getUsers();
        String inputUsername = usernameFld.getText();
        String inputPassword = passwordFld.getText();
        
        boolean loginSuccess = false;
        User loggedInUser = null;
    
        for (User user : users) {
            if (user.getUsername().equals(inputUsername) && user.getHashedPassword().equals(SecurityUtils.hashPassword(inputPassword))) {
                loggedInUser = user;
                loginSuccess = true;
                break;
            }
        }
    
        logAttempt(inputUsername, loginSuccess);
    
        if (loginSuccess && loggedInUser != null) {
            SessionManager sessionManager = new SessionManager(frame, 30000); // 5 minutes timeout
            frame.setUserSession(sessionManager);  // Assuming this method sets the session manager in Frame
            frame.mainNav(loggedInUser.getUsername()); // Navigate to the main frame on successful login
        } else {
            checkDisableUser(inputUsername);
            loginAttempts++;
            lastAttemptTime = currentTime;
            JOptionPane.showMessageDialog(this, "Login failed; Invalid user ID or password. Attempts remaining: " + (MAX_ATTEMPTS - loginAttempts));
        }
        
    
        usernameFld.setText("");
        passwordFld.setText("");
    }
    
    
    
    
    private void checkDisableUser(String username){
        SQLite sqlite = new SQLite();
        if(sqlite.isLast20AttemptsFailed(username)){
            sqlite.updateUserRole(username, 1);
            JOptionPane.showMessageDialog(this, "User has been disabled");
        }
    }

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        frame.registerNav();
        usernameFld.setText("");
        passwordFld.setText("");
    }//GEN-LAST:event_registerBtnActionPerformed

    private void logAttempt(String username, boolean success) {
        SQLite sqlite = new SQLite();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss.SSS");
        String event = success ? "successful_login" : "failed_login";
        String desc = String.format("Login attempt for %s was %s", username, success ? "successful" : "failed");
        String timestamp = dateFormat.format(new Date());
        sqlite.addLogs(event, username, desc, timestamp);
    }
    

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loginBtn;
    private javax.swing.JTextField passwordFld;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField usernameFld;
    // End of variables declaration//GEN-END:variables
}

