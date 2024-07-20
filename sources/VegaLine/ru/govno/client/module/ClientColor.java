/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module;

import ru.govno.client.utils.Render.ColorUtils;

public class ClientColor {
    public int color = -1;
    public int color2 = -1;

    public static enum PresetColors {
        WHITE(-1, -1, true),
        EARLBLUE(ColorUtils.getColor(100, 100, 100), ColorUtils.getColor(255, 0, 0), true);

        boolean twoColored;
        int color1;
        int color2;

        private PresetColors(int color1, int color2, boolean twoColored) {
            this.color1 = color1;
            this.color2 = color2;
            this.twoColored = twoColored;
        }
    }
}

