package net.futureclient.client.modules.movement.fastswim;

import net.futureclient.client.events.Event;
import net.futureclient.client.ZG;
import net.futureclient.client.mc;
import net.futureclient.client.aC;
import net.futureclient.client.modules.movement.FastSwim;
import net.futureclient.client.fg;
import net.futureclient.client.n;

public class Listener1 extends n<fg>
{
    public final FastSwim k;
    
    public Listener1(final FastSwim k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final fg fg) {
        this.k.e(String.format("FastSwim §7[§F%s§7]", FastSwim.M(this.k).M()));
        switch (aC.k[((mc.rc)FastSwim.M(this.k).M()).ordinal()]) {
            case 1:
                if (!FastSwim.getMinecraft().player.isInWater() || !ZG.b()) {
                    break;
                }
                FastSwim.M(this.k, FastSwim.M(this.k) + 1);
                if (FastSwim.M(this.k) == 4) {
                    ZG.M(fg, 1.3262473694E-314);
                }
                if (FastSwim.M(this.k) >= 5) {
                    ZG.M(fg, 5.304989477E-315);
                    FastSwim.M(this.k, 0);
                    return;
                }
                break;
            case 2:
                if (!FastSwim.getMinecraft1().player.isInWater()) {
                    FastSwim.M(this.k, true);
                    break;
                }
                if (FastSwim.M(this.k)) {
                    ZG.M(fg, 1.5278369694E-314);
                    return;
                }
                break;
        }
    }
    
    public void M(final Event event) {
        this.M((fg)event);
    }
}
