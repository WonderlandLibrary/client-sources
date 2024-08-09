/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.texture;

import net.optifine.Mipmaps;
import net.optifine.texture.IColorBlender;

public class ColorBlenderAlpha
implements IColorBlender {
    @Override
    public int blend(int n, int n2, int n3, int n4) {
        return Mipmaps.alphaBlend(n, n2, n3, n4);
    }
}

