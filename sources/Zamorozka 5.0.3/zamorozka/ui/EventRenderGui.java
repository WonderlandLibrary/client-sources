package zamorozka.ui;

import net.minecraft.client.gui.ScaledResolution;

public class EventRenderGui  extends Event {
    private ScaledResolution resolution;

    public void fire(ScaledResolution resolution) {
        this.resolution = resolution;
        super.fire();
    }

    public ScaledResolution getResolution() {
        return resolution;
    }
}