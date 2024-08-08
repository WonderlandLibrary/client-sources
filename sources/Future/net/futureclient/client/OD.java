package net.futureclient.client;

import net.minecraft.client.entity.EntityPlayerSP;
import net.futureclient.client.events.EventType2;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;

public class OD extends n<KF>
{
    public final ne k;
    
    public OD(final ne k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        if (!eventMotion.M().equals(EventType2.POST)) {
            return;
        }
        this.k.e(String.format("PacketFly §7[§F%s§7]", ne.e(this.k).M()));
        final EntityPlayerSP player = ne.getMinecraft16().player;
        final EntityPlayerSP player2 = ne.getMinecraft13().player;
        final EntityPlayerSP player3 = ne.getMinecraft().player;
        final double motionX = 0.0;
        player3.motionZ = motionX;
        player2.motionY = motionX;
        player.motionX = motionX;
        if (!((ne.FE)ne.e(this.k).M()).equals(ne.FE.k) && ne.M(this.k) == 0) {
            final ne k = this.k;
            final double n = 0.0;
            ne.M(k, n, n, n, false);
            return;
        }
        final boolean m = ne.M(this.k);
        double n2;
        OD od;
        if (ne.getMinecraft11().gameSettings.keyBindJump.isKeyDown() && (m || !ZG.b())) {
            n2 = ((ne.M(this.k).M() && !m) ? (ne.M(this.k, ((ne.FE)ne.e(this.k).M()).equals(ne.FE.k) ? 10 : 20) ? 1.748524532E-314 : 1.6636447E-314) : 1.6636447E-314);
            od = this;
        }
        else if (ne.getMinecraft6().gameSettings.keyBindSneak.isKeyDown()) {
            n2 = 1.6636447E-314;
            od = this;
        }
        else {
            n2 = (m ? 0.0 : (ne.M(this.k, 4) ? (ne.M(this.k).M() ? 5.941588215E-315 : 0.0) : 0.0));
            od = this;
        }
        if (((ne.uf)ne.M(od.k).M()).equals(ne.uf.k) && m && ZG.b() && n2 != 0.0) {
            n2 /= 0.0;
        }
        final double[] i = ZG.M((((ne.uf)ne.M(this.k).M()).equals(ne.uf.k) && m) ? 1.6636447E-314 : 8.48798317E-316);
        int j = 1;
        int n3 = 1;
        while (j <= (((ne.FE)ne.e(this.k).M()).equals(ne.FE.a) ? ne.M(this.k).B().intValue() : 1)) {
            ne.M(this.k, ne.getMinecraft3().player.motionX = i[0] * n3, ne.getMinecraft17().player.motionY = n2 * n3, ne.getMinecraft9().player.motionZ = i[1] * n3, !((ne.FE)ne.e(this.k).M()).equals(ne.FE.k));
            j = ++n3;
        }
    }
}
