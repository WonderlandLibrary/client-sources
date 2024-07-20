/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialPortal
extends Material {
    public MaterialPortal(MapColor color) {
        super(color);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean blocksLight() {
        return false;
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }
}

