package com.client.glowclient.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.state.*;

public class EventBlock extends Event
{
    private final BlockPos L;
    private final IBlockAccess A;
    private final BufferBuilder B;
    private final IBlockState b;
    
    public BufferBuilder getBuffer() {
        return this.B;
    }
    
    public BlockPos getPos() {
        return this.L;
    }
    
    public IBlockAccess getAccess() {
        return this.A;
    }
    
    public IBlockState getState() {
        return this.b;
    }
    
    public EventBlock(final BlockPos l, final IBlockState b, final IBlockAccess a, final BufferBuilder b2) {
        super();
        this.L = l;
        this.b = b;
        this.A = a;
        this.B = b2;
    }
}
