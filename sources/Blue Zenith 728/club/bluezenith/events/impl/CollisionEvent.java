package club.bluezenith.events.impl;

import club.bluezenith.events.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class CollisionEvent extends Event {
    public final BlockPos pos;

    public final Block block;

    public AxisAlignedBB boundingBox;

    public CollisionEvent(BlockPos pos, Block block, AxisAlignedBB boundingBox) {
        this.pos = pos;
        this.block = block;
        this.boundingBox = boundingBox;
    }
}
