package net.futureclient.client;

import net.futureclient.client.events.Event;

public class kB extends n<re.Ff>
{
    public final yb k;
    
    public kB(final yb k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((re.Ff)event);
    }
    
    @Override
    public void M(final re.Ff re) {
        if (yb.getMinecraft15().player.getRidingEntity() != null) {
            yb.getMinecraft2().player.getRidingEntity().stepHeight = (this.k.E.M() ? 256.0f : 1.0f);
        }
        yb.M(this.k, re.M().minY);
        if (yb.M(this.k, yb.M(this.k))) {
            re.M(yb.M(this.k).B().floatValue());
        }
    }
}
