package net.futureclient.client.modules.render.search;

import net.futureclient.client.events.Event;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.futureclient.client.sB;
import net.futureclient.client.modules.render.Search;
import net.futureclient.client.MD;
import net.futureclient.client.n;

public class Listener1 extends n<MD>
{
    public final Search k;
    
    public Listener1(final Search k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final MD md) {
        if (this.k.M.size() >= 100000) {
            this.k.M.clear();
        }
        final sB.cC cc = new sB.cC((double)(float)md.M().getX(), (double)(float)md.M().getY(), (double)(float)md.M().getZ());
        if (!this.k.M.contains(cc) && this.k.D.contains(md.M())) {
            final int idFromBlock = Block.getIdFromBlock(Search.getMinecraft6().world.getBlockState(new BlockPos(cc.b(), cc.M(), cc.e())).getBlock());
            if (Search.getMinecraft9().player.getDistance(cc.b(), cc.M(), cc.e()) <= 0.0 && idFromBlock != 0) {
                this.k.M.add(new sB.cC((double)(float)md.M().getX(), (double)(float)md.M().getY(), (double)(float)md.M().getZ()));
            }
        }
    }
    
    public void M(final Event event) {
        this.M((MD)event);
    }
}
