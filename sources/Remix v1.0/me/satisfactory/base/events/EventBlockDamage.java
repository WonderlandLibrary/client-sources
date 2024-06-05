package me.satisfactory.base.events;

import me.satisfactory.base.events.event.*;
import net.minecraft.util.*;

public class EventBlockDamage implements Event
{
    private BlockPos blockPos;
    private EnumFacing enumFacing;
    private boolean cancelled;
    
    public EventBlockDamage(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.blockPos = null;
        this.enumFacing = null;
        this.setBlockPos(blockPos);
        this.setEnumFacing(enumFacing);
    }
    
    public BlockPos getBlockPos() {
        return this.blockPos;
    }
    
    public void setBlockPos(final BlockPos blockPos) {
        this.blockPos = blockPos;
    }
    
    public EnumFacing getEnumFacing() {
        return this.enumFacing;
    }
    
    public void setEnumFacing(final EnumFacing enumFacing) {
        this.enumFacing = enumFacing;
    }
}
