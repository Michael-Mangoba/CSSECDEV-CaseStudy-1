package Controller;

import Model.User;  // Ensure you have a User model that matches this import

public class Session {
    private User user;
    private boolean isValid;

    public Session(User user) {
        this.user = user;
        this.isValid = true;
    }

    public boolean isValid() {
        return isValid;
    }

    public void invalidate() {
        isValid = false;
    }

    public User getUser() {
        return user;
    }
}
