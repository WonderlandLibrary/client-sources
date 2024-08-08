package net.futureclient.client.modules.movement.flight;

import net.futureclient.client.ZC;
import net.futureclient.client.AA;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.Flight;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener3 extends n<lF>
{
    public final Flight k;
    
    public Listener3(final Flight k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        this.k.e(String.format("Flight §7[§F%s§7]", Flight.M(this.k).M()));
        switch (AA.k[((ZC.SB)Flight.M(this.k).M()).ordinal()]) {
            case 4:
                if (Flight.getMinecraft36().player.hurtTime == 10.0f) {
                    Flight.getMinecraft15().player.motionY = Flight.b(this.k).B().doubleValue();
                    return;
                }
                break;
            case 5:
                if (Flight.getMinecraft28().player.posY <= Flight.M(this.k) && Flight.M(this.k).e(200L)) {
                    Flight.getMinecraft33().player.jump();
                    Flight.M(this.k).e();
                    Flight.M(this.k, Flight.M(this.k) - Flight.C(this.k).B().doubleValue());
                    Flight.M(this.k, true);
                    break;
                }
                break;
        }
    }
}
