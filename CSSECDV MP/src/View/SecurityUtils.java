package View;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Pattern;

public class SecurityUtils {

    // Password hashing
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    // Session management
    public static void createSession(Session session, String username) {
        session.setAttribute("username", username);
        session.setMaxInactiveInterval(30 * 60); // Session timeout set to 30 minutes
    }

    public static void invalidateSession(Session session) {
        session.invalidate();
    }

    public static String getUserFromSession(Session session) {
        return (String) session.getAttribute("username");
    }

    public static boolean isSessionValid(Session session) {
        return session.isValid();
    }

    // Data validation
    public static boolean isValidUsername(String username) {
        String regex = "^[a-zA-Z0-9_]{3,20}$";
        return Pattern.matches(regex, username);
    }

    public static boolean isValidPassword(String password) {
        // Minimum 8 characters, at least one uppercase letter, one lowercase letter, and one number
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return Pattern.matches(regex, password);
    }

    public static boolean isValidProductName(String productName) {
        return productName != null && !productName.trim().isEmpty();
    }
}
