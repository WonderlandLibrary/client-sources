package us.dev.direkt.module.internal.movement.speed.internal;

import us.dev.direkt.Wrapper;
import us.dev.direkt.module.internal.movement.speed.SpeedMode;

/**
 * @author Foundry
 */
public abstract class AbstractSpeedMode implements SpeedMode {
    private final String label;

    protected AbstractSpeedMode(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {
        Wrapper.getMinecraft().getTimer().timerSpeed = 1.0F;
    }
}
