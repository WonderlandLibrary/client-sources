package net.futureclient.client.modules.movement.flight;

import net.minecraft.client.entity.EntityPlayerSP;
import net.futureclient.client.ZG;
import net.futureclient.client.IG;
import net.futureclient.client.ZC;
import net.futureclient.client.AA;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.Flight;
import net.futureclient.client.fg;
import net.futureclient.client.n;

public class Listener5 extends n<fg>
{
    public final Flight k;
    
    public Listener5(final Flight k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((fg)event);
    }
    
    @Override
    public void M(final fg fg) {
        switch (AA.k[((ZC.SB)Flight.M(this.k).M()).ordinal()]) {
            case 1:
                fg.e(fg.M() * Flight.M(this.k).B().doubleValue());
                fg.M(fg.b() * Flight.M(this.k).B().doubleValue());
            case 4:
                if (!Flight.getMinecraft3().player.onGround && !IG.e()) {
                    ZG.M(fg, 7.957484216E-315);
                    return;
                }
                break;
            case 5:
                if (Flight.M(this.k)) {
                    ZG.M(fg, Flight.e(this.k).B().doubleValue());
                    return;
                }
                break;
            case 6: {
                final double motionY = Flight.M(this.k).B().doubleValue() / 0.0;
                fg fg2;
                if (Flight.getMinecraft22().player.movementInput.jump) {
                    fg.b(Flight.getMinecraft39().player.motionY = motionY);
                    fg2 = fg;
                }
                else if (Flight.getMinecraft40().player.movementInput.sneak) {
                    fg.b(Flight.getMinecraft37().player.motionY = -motionY);
                    fg2 = fg;
                }
                else {
                    fg.b(Flight.getMinecraft29().player.motionY = 0.0);
                    if (!Flight.getMinecraft13().player.collidedVertically && Flight.e(this.k).M()) {
                        final EntityPlayerSP player = Flight.getMinecraft44().player;
                        fg.b(player.motionY -= Flight.B(this.k).B().doubleValue());
                    }
                    fg2 = fg;
                }
                ZG.M(fg2, motionY);
            }
            case 3:
                ZG.M(fg, ZG.e());
                break;
        }
    }
}
