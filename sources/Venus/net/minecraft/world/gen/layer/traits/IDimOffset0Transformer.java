/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer.traits;

import net.minecraft.world.gen.layer.traits.IDimTransformer;

public interface IDimOffset0Transformer
extends IDimTransformer {
    @Override
    default public int getOffsetX(int n) {
        return n;
    }

    @Override
    default public int getOffsetZ(int n) {
        return n;
    }
}

