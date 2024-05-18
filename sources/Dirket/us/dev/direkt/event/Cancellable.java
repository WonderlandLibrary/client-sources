package us.dev.direkt.event;

/**
 * @author Foundry
 */
public interface Cancellable {
    void setCancelled(boolean state);

    boolean isCancelled();
}
