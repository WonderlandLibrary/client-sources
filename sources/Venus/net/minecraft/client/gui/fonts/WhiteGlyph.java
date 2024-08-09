/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts;

import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.Util;

public enum WhiteGlyph implements IGlyphInfo
{
    INSTANCE;

    private static final NativeImage WHITE_GLYPH;

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 1;
    }

    @Override
    public float getAdvance() {
        return 6.0f;
    }

    @Override
    public float getOversample() {
        return 1.0f;
    }

    @Override
    public void uploadGlyph(int n, int n2) {
        WHITE_GLYPH.uploadTextureSub(0, n, n2, true);
    }

    @Override
    public boolean isColored() {
        return false;
    }

    private static void lambda$static$0(NativeImage nativeImage) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 5; ++j) {
                boolean bl = j == 0 || j + 1 == 5 || i == 0 || i + 1 == 8;
                nativeImage.setPixelRGBA(j, i, -1);
            }
        }
        nativeImage.untrack();
    }

    static {
        WHITE_GLYPH = Util.make(new NativeImage(NativeImage.PixelFormat.RGBA, 5, 8, false), WhiteGlyph::lambda$static$0);
    }
}

