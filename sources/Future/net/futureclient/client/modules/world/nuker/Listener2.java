package net.futureclient.client.modules.world.nuker;

import net.minecraft.block.state.IBlockState;
import net.futureclient.client.ka;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.Nuker;
import net.futureclient.client.iE;
import net.futureclient.client.n;

public class Listener2 extends n<iE>
{
    public final Nuker k;
    
    public Listener2(final Nuker k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((iE)event);
    }
    
    @Override
    public void M(final iE ie) {
        if (((ka.la)Nuker.M(this.k).M()).equals((Object)ka.la.M) && Nuker.e(this.k).M() && Nuker.e(this.k).e(50L)) {
            final IBlockState blockState = Nuker.getMinecraft17().world.getBlockState(ie.M());
            if (!this.k.M.contains(blockState)) {
                this.k.M.add(blockState);
                Nuker.e(this.k).e();
            }
        }
    }
}
