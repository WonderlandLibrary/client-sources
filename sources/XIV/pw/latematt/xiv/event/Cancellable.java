package pw.latematt.xiv.event;

/**
 * @author Matthew
 */
public interface Cancellable {
    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
