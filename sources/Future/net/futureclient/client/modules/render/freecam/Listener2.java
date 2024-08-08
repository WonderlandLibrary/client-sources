package net.futureclient.client.modules.render.freecam;

import net.futureclient.client.ZG;
import net.futureclient.client.dd;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.fg;
import net.futureclient.client.n;

public class Listener2 extends n<fg>
{
    public final Freecam k;
    
    public Listener2(final Freecam k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((fg)event);
    }
    
    @Override
    public void M(final fg fg) {
        if (Freecam.M(this.k).M() == dd.FB.D) {
            Freecam.getMinecraft13().player.noClip = true;
            fg fg2;
            if (Freecam.getMinecraft12().player.movementInput.jump) {
                fg.b(Freecam.getMinecraft1().player.motionY = this.k.speed.B().doubleValue());
                fg2 = fg;
            }
            else if (Freecam.getMinecraft7().player.movementInput.sneak) {
                fg.b(Freecam.getMinecraft3().player.motionY = -this.k.speed.B().doubleValue());
                fg2 = fg;
            }
            else {
                fg.b(Freecam.getMinecraft10().player.motionY = 0.0);
                fg2 = fg;
            }
            ZG.M(fg2, this.k.speed.B().doubleValue());
        }
    }
}
