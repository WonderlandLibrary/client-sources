package net.futureclient.client.modules.world.wallhack;

import net.minecraft.util.BlockRenderLayer;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.Wallhack;
import net.futureclient.client.BD;
import net.futureclient.client.n;

public class Listener3 extends n<BD>
{
    public final Wallhack k;
    
    public Listener3(final Wallhack k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((BD)event);
    }
    
    @Override
    public void M(final BD bd) {
        if (!Wallhack.M(this.k, bd.M())) {
            bd.M(BlockRenderLayer.TRANSLUCENT);
        }
    }
}
