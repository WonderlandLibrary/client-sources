package net.futureclient.client.modules.movement.noslow;

import net.futureclient.client.events.Event;
import net.futureclient.loader.mixin.common.entity.wrapper.IEntity;
import net.futureclient.client.modules.movement.NoSlow;
import net.futureclient.client.fg;
import net.futureclient.client.n;

public class Listener4 extends n<fg>
{
    public final NoSlow k;
    
    public Listener4(final NoSlow k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final fg fg) {
        if (this.k.webs.M() && NoSlow.M(this.k).e(250L) && ((IEntity)NoSlow.getMinecraft8().player).isIsInWeb()) {
            if (NoSlow.getMinecraft25().player.onGround) {
                fg.e(fg.M() * NoSlow.M(this.k).B().doubleValue());
                fg.M(fg.b() * NoSlow.M(this.k).B().doubleValue());
                return;
            }
            if (NoSlow.getMinecraft13().gameSettings.keyBindSneak.isKeyDown()) {
                fg.b(fg.e() * (NoSlow.M(this.k).B().doubleValue() + 0.0));
            }
        }
    }
    
    public void M(final Event event) {
        this.M((fg)event);
    }
}
