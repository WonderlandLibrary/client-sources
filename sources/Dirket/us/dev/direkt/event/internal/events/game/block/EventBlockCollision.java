package us.dev.direkt.event.internal.events.game.block;

import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import us.dev.direkt.event.Event;
public class EventBlockCollision implements Event {
	
    private Block block;
    private AxisAlignedBB boundingBox;
    private BlockPos pos;

    public EventBlockCollision(Block block,  AxisAlignedBB boundingBox,  BlockPos pos) {
        this.block = block;
        this.boundingBox = boundingBox;
        this.pos = pos;
    }

    public Block getBlock() {
        return this.block;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public BlockPos getBlockPos() {
        return this.pos;
    }

    public void setBoundingBox( AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }

    public int getX() {
        return this.pos.getX();
    }
    
    public int getY() {
        return this.pos.getY();
    }

    public int getZ() {
        return this.pos.getZ();
    }
}