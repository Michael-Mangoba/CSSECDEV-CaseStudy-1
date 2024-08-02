package Controller;

import java.util.Timer;
import java.util.TimerTask;
import View.Frame;

public class SessionManager {
    private Timer timer;
    private final int timeout; // Timeout in milliseconds
    private Frame frame;

    // Constructor that accepts a Frame object which includes session expiry handling
    public SessionManager(Frame frame, int timeout) {
        this.frame = frame;
        this.timeout = timeout;
        startSessionTimer();
    }

    private void startSessionTimer() {
        if (timer != null) {
            timer.cancel(); // Cancel any previous timer
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                frame.onSessionExpired(); // Call the method on session expiration
                System.out.println("Session expired after timeout.");
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
        frame.onSessionExpired(); // Call the method on session end
    }
}
