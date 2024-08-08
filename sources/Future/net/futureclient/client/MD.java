package net.futureclient.client;

import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import net.futureclient.client.events.Event;

public class MD extends Event
{
    private Block D;
    private BlockPos k;
    
    public MD(final Block block, final BlockPos blockPos) {
        final BlockPos k = null;
        final Block d = null;
        super();
        this.D = d;
        this.k = k;
        this.M(block);
        this.M(blockPos);
    }
    
    public Block M() {
        return this.D;
    }
    
    public BlockPos M() {
        return this.k;
    }
    
    public void M(final BlockPos k) {
        this.k = k;
    }
    
    public void M(final Block d) {
        this.D = d;
    }
}
