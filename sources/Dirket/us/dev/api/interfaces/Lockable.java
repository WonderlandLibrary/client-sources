package us.dev.api.interfaces;

/**
 * @author Foundry
 */
public interface Lockable {
    boolean isLocked();

    void setLocked(boolean isLocked);
}
