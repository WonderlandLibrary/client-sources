// 
// Decompiled by Procyon v0.5.36
// 

package events.listeners;

import events.Event;

public class EventGlowESP extends Event
{
    private final Runnable runnable;
    public boolean cancelled;
    
    public Runnable getRunnable() {
        return this.runnable;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EventGlowESP)) {
            return false;
        }
        final EventGlowESP other = (EventGlowESP)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isCancelled() != other.isCancelled()) {
            return false;
        }
        final Object this$runnable = this.getRunnable();
        final Object other$runnable = other.getRunnable();
        if (this$runnable == null) {
            if (other$runnable == null) {
                return true;
            }
        }
        else if (this$runnable.equals(other$runnable)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof EventGlowESP;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isCancelled() ? 79 : 97);
        final Object $runnable = this.getRunnable();
        result = result * 59 + (($runnable == null) ? 43 : $runnable.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "EventGlowESP(runnable=" + this.getRunnable() + ", cancelled=" + this.isCancelled() + ")";
    }
    
    public EventGlowESP(final Runnable runnable) {
        this.runnable = runnable;
    }
}
