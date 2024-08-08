package net.futureclient.client.modules.miscellaneous.announcer;

import net.futureclient.client.pD;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.Announcer;
import net.futureclient.client.Ag;
import net.futureclient.client.n;

public class Listener3 extends n<Ag>
{
    public final Announcer k;
    
    public Listener3(final Announcer k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
    
    @Override
    public void M(final Ag ag) {
        if (Announcer.C(this.k).M() && ag.M() instanceof CPacketPlayerDigging) {
            switch (pD.k[((CPacketPlayerDigging)ag.M()).getAction().ordinal()]) {
                case 1:
                    try {
                        if (Announcer.e(this.k).containsKey(Announcer.getMinecraft3().world.getBlockState(((CPacketPlayerDigging)ag.M()).getPosition()).getBlock().getLocalizedName())) {
                            Announcer.e(this.k).put(Announcer.getMinecraft5().world.getBlockState(((CPacketPlayerDigging)ag.M()).getPosition()).getBlock().getLocalizedName(), Announcer.e(this.k).get(Announcer.getMinecraft().world.getBlockState(((CPacketPlayerDigging)ag.M()).getPosition()).getBlock().getLocalizedName()) + 1);
                            return;
                        }
                        Announcer.e(this.k).put(Announcer.getMinecraft2().world.getBlockState(((CPacketPlayerDigging)ag.M()).getPosition()).getBlock().getLocalizedName(), 1);
                        return;
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
            }
        }
    }
}
