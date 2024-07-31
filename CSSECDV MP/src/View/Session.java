package View;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private Map<String, Object> attributes = new HashMap<>();
    private long creationTime;
    private long lastAccessedTime;
    private int maxInactiveInterval; // in seconds

    public Session() {
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = System.currentTimeMillis();
        this.maxInactiveInterval = 30 * 60; // Default to 30 minutes
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
        this.lastAccessedTime = System.currentTimeMillis();
    }

    public Object getAttribute(String name) {
        this.lastAccessedTime = System.currentTimeMillis();
        return attributes.get(name);
    }

    public void invalidate() {
        attributes.clear();
    }

    public boolean isValid() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastAccessedTime) < (maxInactiveInterval * 1000);
    }

    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }
}
