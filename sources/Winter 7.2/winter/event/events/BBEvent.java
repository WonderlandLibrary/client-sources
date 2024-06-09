/*
 * Decompiled with CFR 0_122.
 */
package winter.event.events;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import winter.event.Event;

public class BBEvent
extends Event {
    private Block block;
    private BlockPos pos;
    private Entity entity;
    private AxisAlignedBB bounds;

    public BBEvent(Entity entity, Block block, BlockPos pos, AxisAlignedBB bounds) {
        this.block = block;
        this.pos = pos;
        this.bounds = bounds;
        this.entity = entity;
    }

    public AxisAlignedBB getBounds() {
        return this.bounds;
    }

    public void setBounds(AxisAlignedBB bounds) {
        this.bounds = bounds;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Block getBlock() {
        return this.block;
    }
}

