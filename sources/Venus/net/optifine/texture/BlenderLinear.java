/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.texture;

import net.optifine.texture.IBlender;

public class BlenderLinear
implements IBlender {
    @Override
    public int blend(int n, int n2, int n3, int n4) {
        return (n + n2 + n3 + n4) / 4;
    }
}

