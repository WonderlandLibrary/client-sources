package pw.latematt.xiv.ui.notifications;

import pw.latematt.timer.Timer;
import pw.latematt.xiv.XIV;

/**
 * @author Matthew
 */
public class Notification {
    private final String message;
    private boolean important;
    private final Timer timer;

    public Notification(String message, boolean important) {
        this.message = message;
        this.important = important;
        timer = new Timer();

        XIV.getInstance().getNotificationsHandler().getContents().add(this);
    }

    public String getMessage() {
        return message;
    }

    public boolean isImportant() {
        return important;
    }

    public Timer getTimer() {
        return timer;
    }
}
