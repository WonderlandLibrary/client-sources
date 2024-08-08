package net.futureclient.client.modules.movement.longjump;

import net.futureclient.client.events.Event;
import net.futureclient.client.hb;
import net.futureclient.client.EB;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.modules.movement.LongJump;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener2 extends n<KF>
{
    public final LongJump k;
    
    public Listener2(final LongJump k) {
        this.k = k;
        super();
    }
    
    public void M(final EventMotion eventMotion) {
        this.k.e(String.format("LongJump §7[§F%s§7]", LongJump.M(this.k).M()));
        Label_0276: {
            switch (EB.k[eventMotion.M().ordinal()]) {
                case 1:
                    switch (EB.D[((hb.wc)LongJump.M(this.k).M()).ordinal()]) {
                        case 1: {
                            LongJump.M(this.k, LongJump.getMinecraft131().player.posX - LongJump.getMinecraft56().player.prevPosX);
                            final double n = LongJump.getMinecraft5().player.posZ - LongJump.getMinecraft120().player.prevPosZ;
                            final LongJump k = this.k;
                            final double n2 = LongJump.e(this.k) * LongJump.e(this.k);
                            final double n3 = n;
                            LongJump.e(k, Math.sqrt(n2 + n3 * n3));
                            return;
                        }
                        case 2: {
                            final double n4 = LongJump.getMinecraft62().player.posX - LongJump.getMinecraft153().player.prevPosX;
                            final double n5 = LongJump.getMinecraft37().player.posZ - LongJump.getMinecraft4().player.prevPosZ;
                            final LongJump i = this.k;
                            final double n6 = n4;
                            final double n7 = n6 * n6;
                            final double n8 = n5;
                            LongJump.e(i, Math.sqrt(n7 + n8 * n8));
                            if (LongJump.M(this.k)) {
                                LongJump.getMinecraft73().player.motionY = 5.941588215E-315;
                                break Label_0276;
                            }
                            break Label_0276;
                        }
                    }
                    break;
            }
        }
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
}
