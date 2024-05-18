// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.events;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import com.darkmagician6.eventapi.events.Event;

public class EventClickBlock implements Event
{
    private BlockPos pos;
    private EnumFacing side;
    
    public EventClickBlock(final BlockPos pos, final EnumFacing side) {
        this.pos = pos;
        this.side = side;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public EnumFacing getSide() {
        return this.side;
    }
}
