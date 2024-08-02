package Controller;

import java.util.Timer;
import java.util.TimerTask;

public class SessionManager {
    private Timer timer;
    private final int timeout; // Timeout in milliseconds, default to 10 minutes
    private Session session;

    // Constructor with specified timeout
    public SessionManager(int timeout, Session session) {
        this.timeout = timeout;
        this.session = session;
        startSessionTimer();
    }

    // Constructor with default timeout of 10 minutes
    public SessionManager(Session session) {
        this(600000, session); // 600000 milliseconds = 10 minutes
    }

    private void startSessionTimer() {
        if (timer != null) {
            timer.cancel(); // Cancel any previous timer
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                session.invalidate();
                System.out.println("Session expired after 10 minutes of inactivity.");
            }
        }, timeout);
    }

    public void resetTimer() {
        startSessionTimer(); // Reset the timer on user activity
    }

    public void endSession() {
        if (timer != null) {
            timer.cancel(); // Stop the timer
        }
        session.invalidate();
        System.out.println("Session manually ended.");
    }
}
