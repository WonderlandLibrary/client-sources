package us.dev.api.interfaces;

/**
 * Created by Foundry on 11/15/2015.
 */
public interface Toggleable {
    default void toggle() {
        this.setRunning(!this.isRunning());
    }

    boolean isRunning();

    void setRunning(boolean running);

    void onEnable();

    void onDisable();
}
