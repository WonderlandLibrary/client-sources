package wtf.diablo.client.gui.clickgui.material.animations;

import net.minecraft.client.Minecraft;

public final class Animation {

    private long lastMS = System.currentTimeMillis();
    private double value;

    public void step(final double target, double speed) {
        speed *= 1D / (System.currentTimeMillis() - this.lastMS);
        this.value += (target - this.value) / (speed * 50);
        this.lastMS = System.currentTimeMillis();
    }

    public void slide(final double target, double speed) {
        speed *= 1D / (System.currentTimeMillis() - this.lastMS);
        // prevents extremely fucking weird visual glitches
        if (Minecraft.getDebugFPS() < 10) {
            this.value = target;
        } else {
            this.value += (target - this.value) / (speed * 50);
        }

        this.lastMS = System.currentTimeMillis();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}