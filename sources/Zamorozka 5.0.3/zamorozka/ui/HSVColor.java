package zamorozka.ui;

import java.awt.*;

public class HSVColor {
    private final float h;
    private final float s;
    private final float v;

    public HSVColor(float h, float s, float v) {
        this.h = h;
        this.s = s;
        this.v = v;
    }

    public HSVColor(Color c) {
        float[] cl = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        this.h = cl[0];
        this.s = cl[1];
        this.v = cl[2];
    }

    public float getHue() {
        return h;
    }

    public float getSaturation() {
        return s;
    }

    public float getValue() {
        return v;
    }
}
