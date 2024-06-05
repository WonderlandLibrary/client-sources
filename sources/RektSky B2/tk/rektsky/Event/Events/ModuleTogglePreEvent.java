package tk.rektsky.Event.Events;

import tk.rektsky.Event.*;
import tk.rektsky.Module.*;

public class ModuleTogglePreEvent extends Event
{
    private Module module;
    private boolean enabled;
    private boolean canceled;
    
    public Module getModule() {
        return this.module;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public boolean isCanceled() {
        return this.canceled;
    }
    
    public void setCanceled(final boolean canceled) {
        this.canceled = canceled;
    }
    
    public ModuleTogglePreEvent(final Module module, final boolean enabled) {
        this.module = module;
        this.enabled = enabled;
    }
}
