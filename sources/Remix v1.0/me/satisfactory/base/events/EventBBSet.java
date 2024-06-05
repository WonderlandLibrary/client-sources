package me.satisfactory.base.events;

import me.satisfactory.base.events.event.callables.*;
import me.satisfactory.base.events.event.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class EventBBSet extends EventCancellable implements Event
{
    private Block block;
    private BlockPos pos;
    private AxisAlignedBB boundingBox;
    
    public EventBBSet(final Block block, final BlockPos pos, final AxisAlignedBB boundingBox) {
        this.block = block;
        this.pos = pos;
        this.boundingBox = boundingBox;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
}
