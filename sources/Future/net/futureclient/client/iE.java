package net.futureclient.client;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.futureclient.client.events.Event;

public class iE extends Event
{
    private BlockPos D;
    private EnumFacing k;
    
    public iE(final BlockPos d, final EnumFacing k) {
        super();
        this.D = d;
        this.k = k;
    }
    
    public EnumFacing M() {
        return this.k;
    }
    
    public BlockPos M() {
        return this.D;
    }
}
