/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import net.minecraft.entity.Entity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.EntitySelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;

public interface ISelectionContext {
    public static ISelectionContext dummy() {
        return EntitySelectionContext.DUMMY;
    }

    public static ISelectionContext forEntity(Entity entity2) {
        return new EntitySelectionContext(entity2);
    }

    public boolean getPosY();

    public boolean func_216378_a(VoxelShape var1, BlockPos var2, boolean var3);

    public boolean hasItem(Item var1);

    public boolean func_230426_a_(FluidState var1, FlowingFluid var2);
}

