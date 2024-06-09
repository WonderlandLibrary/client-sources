/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package me.travis.wurstplus.event.events;

import java.util.List;
import me.travis.wurstplus.event.wurstplusEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AddCollisionBoxToListEvent
extends wurstplusEvent {
    private final Block block;
    private final IBlockState state;
    private final World world;
    private final BlockPos pos;
    private final AxisAlignedBB entityBox;
    private final List<AxisAlignedBB> collidingBoxes;
    private final Entity entity;
    private final boolean bool;

    public AddCollisionBoxToListEvent(Block block, IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean bool) {
        this.block = block;
        this.state = state;
        this.world = worldIn;
        this.pos = pos;
        this.entityBox = entityBox;
        this.collidingBoxes = collidingBoxes;
        this.entity = entityIn;
        this.bool = bool;
    }

    public Block getBlock() {
        return this.block;
    }

    public IBlockState getState() {
        return this.state;
    }

    public World getWorld() {
        return this.world;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public AxisAlignedBB getEntityBox() {
        return this.entityBox;
    }

    public List<AxisAlignedBB> getCollidingBoxes() {
        return this.collidingBoxes;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public boolean isBool() {
        return this.bool;
    }
}

