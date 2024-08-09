/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.minecraft.client.gui.fonts.IGlyph;

public class GlyphAdvanceFixed
implements IGlyph {
    private float advanceWidth;

    public GlyphAdvanceFixed(float f) {
        this.advanceWidth = f;
    }

    @Override
    public float getAdvance() {
        return this.advanceWidth;
    }
}

