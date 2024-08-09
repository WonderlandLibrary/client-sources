/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;

public interface IHopper
extends IInventory {
    public static final VoxelShape INSIDE_BOWL_SHAPE = Block.makeCuboidShape(2.0, 11.0, 2.0, 14.0, 16.0, 14.0);
    public static final VoxelShape BLOCK_ABOVE_SHAPE = Block.makeCuboidShape(0.0, 16.0, 0.0, 16.0, 32.0, 16.0);
    public static final VoxelShape COLLECTION_AREA_SHAPE = VoxelShapes.or(INSIDE_BOWL_SHAPE, BLOCK_ABOVE_SHAPE);

    default public VoxelShape getCollectionArea() {
        return COLLECTION_AREA_SHAPE;
    }

    @Nullable
    public World getWorld();

    public double getXPos();

    public double getYPos();

    public double getZPos();
}

