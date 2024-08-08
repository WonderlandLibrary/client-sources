package net.futureclient.client.modules.render.norender;

import net.futureclient.client.events.Event;
import net.minecraft.world.EnumSkyBlock;
import net.futureclient.client.modules.render.NoRender;
import net.futureclient.client.tD;
import net.futureclient.client.n;

public class Listener7 extends n<tD>
{
    public final NoRender k;
    
    public Listener7(final NoRender k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final tD td) {
        td.M(this.k.skylight.M() && td.M().equals((Object)EnumSkyBlock.SKY));
    }
    
    public void M(final Event event) {
        this.M((tD)event);
    }
}
