package us.dev.direkt.module.internal.movement.speed;

import us.dev.api.interfaces.Labeled;

/**
 * @author Foundry
 */
public interface SpeedMode extends Labeled {
    void onEnable();

    void onDisable();

    default boolean isSpeeding() {
        return false;
    }
}
