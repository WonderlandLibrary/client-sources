/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;

public abstract class MarginedStructureStart<C extends IFeatureConfig>
extends StructureStart<C> {
    public MarginedStructureStart(Structure<C> structure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
        super(structure, n, n2, mutableBoundingBox, n3, l);
    }

    @Override
    protected void recalculateStructureSize() {
        super.recalculateStructureSize();
        int n = 12;
        this.bounds.minX -= 12;
        this.bounds.minY -= 12;
        this.bounds.minZ -= 12;
        this.bounds.maxX += 12;
        this.bounds.maxY += 12;
        this.bounds.maxZ += 12;
    }
}

