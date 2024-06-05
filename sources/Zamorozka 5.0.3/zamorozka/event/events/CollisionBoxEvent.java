package zamorozka.event.events;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.Event;

public class CollisionBoxEvent extends Event {
    private final Block block;
    private final BlockPos pos;
    private AxisAlignedBB aabb;
    private final List<AxisAlignedBB> collidingBoxes;
    private final Entity entity;

    public CollisionBoxEvent(Block block, BlockPos pos, AxisAlignedBB aabb, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity) {
        this.block = block;
        this.pos = pos;
        this.aabb = aabb;
        this.collidingBoxes = collidingBoxes;
        this.entity = entity;
    }

    public void setAABB(AxisAlignedBB aabb) {
        this.aabb = aabb;
    }

    public final Block getBlock() {
        return this.block;
    }

    public final BlockPos getPos() {
        return this.pos;
    }

    public final AxisAlignedBB getBoundingBox() {
        return this.aabb;
    }

    public final List<AxisAlignedBB> getCollidingBoxes() {
        return this.collidingBoxes;
    }

    public final Entity getEntity() {
        return this.entity;
    }
    
}
