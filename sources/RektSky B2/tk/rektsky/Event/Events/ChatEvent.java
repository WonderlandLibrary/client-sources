package tk.rektsky.Event.Events;

import tk.rektsky.Event.*;

public class ChatEvent extends Event
{
    private String message;
    private boolean canceled;
    private boolean canceledLogging;
    
    public ChatEvent(final String message) {
        this.canceled = false;
        this.canceledLogging = false;
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public boolean isCanceled() {
        return this.canceled;
    }
    
    public void setCanceled(final boolean canceled) {
        this.canceled = canceled;
    }
    
    public boolean isCanceledLogging() {
        return this.canceledLogging;
    }
    
    public void setCanceledLogging(final boolean canceledLogging) {
        this.canceledLogging = canceledLogging;
    }
}
