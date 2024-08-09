/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.texture;

import net.optifine.texture.BlenderLinear;
import net.optifine.texture.ColorBlenderSeparate;

public class ColorBlenderLinear
extends ColorBlenderSeparate {
    public ColorBlenderLinear() {
        super(new BlenderLinear(), new BlenderLinear(), new BlenderLinear(), new BlenderLinear());
    }

    @Override
    public int blend(int n, int n2, int n3, int n4) {
        return n == n2 && n2 == n3 && n3 == n4 ? n : super.blend(n, n2, n3, n4);
    }
}

