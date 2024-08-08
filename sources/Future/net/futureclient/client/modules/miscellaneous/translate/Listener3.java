package net.futureclient.client.modules.miscellaneous.translate;

import net.futureclient.client.dj;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.Translate;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener3 extends n<lF>
{
    public final Translate k;
    
    public Listener3(final Translate k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (Translate.M(this.k) == null || !Translate.M(this.k).equals(Translate.M(this.k).M())) {
            Translate.M(this.k, (dj)Translate.M(this.k).M());
            if (!((dj)Translate.M(this.k).M()).equals(dj.Wa)) {
                this.k.e(String.format("Translate §7[§F%s§7]", Translate.M(this.k).M()));
                return;
            }
            this.k.e("Translate");
        }
    }
}
