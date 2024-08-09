/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts;

import net.minecraft.client.gui.fonts.IGlyph;

public interface IGlyphInfo
extends IGlyph {
    public int getWidth();

    public int getHeight();

    public void uploadGlyph(int var1, int var2);

    public boolean isColored();

    public float getOversample();

    default public float func_211198_f() {
        return this.getBearingX();
    }

    default public float func_211199_g() {
        return this.func_211198_f() + (float)this.getWidth() / this.getOversample();
    }

    default public float func_211200_h() {
        return this.getBearingY();
    }

    default public float func_211204_i() {
        return this.func_211200_h() + (float)this.getHeight() / this.getOversample();
    }

    default public float getBearingY() {
        return 3.0f;
    }
}

