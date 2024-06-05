/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.event.impl;

import cc.diablo.event.Event;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class CollisionEvent
extends Event {
    private Block block;
    private BlockPos.MutableBlockPos blockPos;
    private List<AxisAlignedBB> list;
    public double x = 0.0;
    public double y = 0.0;
    public double z = 0.0;

    public CollisionEvent(BlockPos.MutableBlockPos pos, Block block, List<AxisAlignedBB> bList) {
        this.block = block;
        this.blockPos = pos;
        this.list = bList;
        this.x = this.x;
        this.y = this.y;
        this.z = this.z;
    }

    public Block getBlock() {
        return this.block;
    }

    public BlockPos.MutableBlockPos getBlockPos() {
        return this.blockPos;
    }

    public List<AxisAlignedBB> getList() {
        return this.list;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setBlockPos(BlockPos.MutableBlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public void setList(List<AxisAlignedBB> boundingBox) {
        this.list = boundingBox;
    }
}

