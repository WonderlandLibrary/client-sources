package baritone.api.event.events.type;

public interface ICancellable
{
    void cancel();
    
    boolean isCancelled();
}
