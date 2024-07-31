package Model;

import View.SecurityUtils;

public class User {
    public enum Role {
        UNREGISTERED, DISABLED, CLIENT, STAFF, MANAGER, ADMIN
    }

    private int id;
    private String username;
    private String password;
    private Role role = Role.CLIENT;
    private int locked = 0;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public User(int id, String username, String password, Role role, int locked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.locked = locked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getHashedPassword() {
        return SecurityUtils.hashPassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public boolean isAuthorized(String action) {
        switch (this.role) {
            case CLIENT:
                return action.equals("PURCHASE") || action.equals("VIEW_OWN_HISTORY");
            case STAFF:
                return action.equals("ADD_PRODUCT") || action.equals("EDIT_PRODUCT") || action.equals("DELETE_PRODUCT");
            case MANAGER:
                return action.equals("ADD_PRODUCT") || action.equals("EDIT_PRODUCT") || action.equals("DELETE_PRODUCT") || action.equals("VIEW_ALL_HISTORY");
            case ADMIN:
                return action.equals("MANAGE_USERS") || action.equals("VIEW_LOGS");
            default:
                return false;
        }
    }
}
