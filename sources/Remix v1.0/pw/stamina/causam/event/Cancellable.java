package pw.stamina.causam.event;

public interface Cancellable
{
    void setCancelled(final boolean p0);
    
    boolean isCancelled();
    
    void cancel();
}
