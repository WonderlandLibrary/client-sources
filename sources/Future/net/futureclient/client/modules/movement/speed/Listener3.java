package net.futureclient.client.modules.movement.speed;

import net.minecraft.client.entity.EntityPlayerSP;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.futureclient.loader.mixin.common.wrapper.ITimer;
import net.futureclient.client.ZG;
import net.futureclient.client.IG;
import net.futureclient.client.db;
import net.futureclient.client.pb;
import net.futureclient.client.ZC;
import net.futureclient.client.modules.movement.Flight;
import net.futureclient.client.GE;
import net.futureclient.client.modules.exploits.Phase;
import net.futureclient.client.dd;
import net.futureclient.client.pg;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.Speed;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener3 extends n<KF>
{
    public final Speed k;
    
    public Listener3(final Speed k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        this.k.e(String.format("Speed §7[§F%s§7]", this.k.mode.M()));
        final Freecam freecam = (Freecam)pg.M().M().M((Class)dd.class);
        final Phase phase = (Phase)pg.M().M().M((Class)GE.class);
        final Flight flight = (Flight)pg.M().M().M((Class)ZC.class);
        if (phase != null && phase.M()) {
            return;
        }
        if (flight != null && flight.M()) {
            return;
        }
        if (freecam != null && freecam.M()) {
            return;
        }
        if (Speed.e(this.k)) {
            Speed.getMinecraft93().player.motionX = 0.0;
            Speed.getMinecraft161().player.motionZ = 0.0;
            Speed.M(this.k, 0.0);
            return;
        }
        Label_1162: {
            switch (pb.D[this.k.mode.M().ordinal()]) {
                case 1:
                case 5: {
                    final double n = Speed.getMinecraft55().player.posX - Speed.getMinecraft145().player.prevPosX;
                    final double n2 = Speed.getMinecraft2().player.posZ - Speed.getMinecraft97().player.prevPosZ;
                    final Speed k = this.k;
                    final double n3 = n;
                    final double n4 = n3 * n3;
                    final double n5 = n2;
                    Speed.e(k, Math.sqrt(n4 + n5 * n5));
                    return;
                }
                case 7:
                    switch (pb.k[eventMotion.M().ordinal()]) {
                        case 1:
                            if (!Speed.M(this.k).e(100L)) {
                                return;
                            }
                            if (!this.k.speedInWater.M() && (IG.e() || IG.M(true) || Speed.getMinecraft100().player.isOnLadder() || Speed.getMinecraft50().player.isEntityInsideOpaqueBlock())) {
                                break Label_1162;
                            }
                            if (Speed.getMinecraft11().player.moveForward == 0.0f && Speed.getMinecraft98().player.moveStrafing == 0.0f) {
                                Speed.getMinecraft103().player.motionX = 0.0;
                                Speed.getMinecraft95().player.motionZ = 0.0;
                                break;
                            }
                            if (!Speed.getMinecraft73().player.movementInput.jump && Speed.getMinecraft24().player.onGround) {
                                if (Speed.getMinecraft49().player.ticksExisted % 2 == 0) {
                                    eventMotion.M(eventMotion.B() + (IG.e(0.0).getMaterial().blocksMovement() ? (1.273197475E-314 - ZG.M()) : (3.97698272E-315 + ZG.M())));
                                }
                                if (this.k.useTimer.M()) {
                                    ((ITimer)((IMinecraft)Speed.getMinecraft116()).getTimer()).setTimerSpeed((Speed.getMinecraft87().player.ticksExisted % 3 == 0) ? 1.3f : 1.0f);
                                }
                                final EntityPlayerSP player = Speed.getMinecraft22().player;
                                player.motionX *= ((Speed.getMinecraft25().player.ticksExisted % 2 != 0) ? 0.0 : 3.395193264E-315);
                                final EntityPlayerSP player2 = Speed.getMinecraft41().player;
                                player2.motionZ *= ((Speed.getMinecraft132().player.ticksExisted % 2 != 0) ? 0.0 : 3.395193264E-315);
                                return;
                            }
                            break Label_1162;
                    }
                    return;
                case 2:
                    switch (pb.k[eventMotion.M().ordinal()]) {
                        case 1: {
                            if (Speed.K(this.k) == 0.0) {
                                final double n6 = 0.0;
                                eventMotion.M(eventMotion.B() + 1.273197475E-314 + ZG.M());
                                if (IG.e(n6).getMaterial().blocksMovement()) {
                                    eventMotion.M(eventMotion.B() - 1.273197475E-314 + ZG.M());
                                }
                            }
                            final double n7 = Speed.getMinecraft76().player.posX - Speed.getMinecraft129().player.prevPosX;
                            final double n8 = Speed.getMinecraft75().player.posZ - Speed.getMinecraft47().player.prevPosZ;
                            final Speed i = this.k;
                            final double n9 = n7;
                            final double n10 = n9 * n9;
                            final double n11 = n8;
                            Speed.e(i, Math.sqrt(n10 + n11 * n11));
                            break;
                        }
                    }
                    return;
                case 3:
                case 4:
                case 6:
                    switch (pb.k[eventMotion.M().ordinal()]) {
                        case 1: {
                            final double n12 = Speed.getMinecraft51().player.posX - Speed.getMinecraft81().player.prevPosX;
                            final double n13 = Speed.getMinecraft130().player.posZ - Speed.getMinecraft139().player.prevPosZ;
                            final Speed j = this.k;
                            final double n14 = n12;
                            final double n15 = n14 * n14;
                            final double n16 = n13;
                            Speed.e(j, Math.sqrt(n15 + n16 * n16));
                            break;
                        }
                    }
                    return;
                case 8:
                    switch (pb.k[eventMotion.M().ordinal()]) {
                        case 1: {
                            Listener3 listener3;
                            if (Speed.i(this.k)) {
                                Speed.b(this.k, Speed.c(this.k) + 1);
                                listener3 = this;
                            }
                            else {
                                Speed.b(this.k, 0);
                                listener3 = this;
                            }
                            if (Speed.c(listener3.k) != 4) {
                                break Label_1162;
                            }
                            final double n17 = 0.0;
                            eventMotion.M(eventMotion.B() + 1.273197475E-314 + ZG.M());
                            if (IG.e(n17).getMaterial().blocksMovement()) {
                                eventMotion.M(eventMotion.B() - 1.273197475E-314 + ZG.M());
                                return;
                            }
                            break Label_1162;
                        }
                        case 2:
                            if (Speed.c(this.k) == 3) {
                                final EntityPlayerSP player3 = Speed.getMinecraft155().player;
                                player3.motionX *= 0.0;
                                final EntityPlayerSP player4 = Speed.getMinecraft().player;
                                player4.motionZ *= 0.0;
                                return;
                            }
                            if (Speed.c(this.k) == 4) {
                                final EntityPlayerSP player5 = Speed.getMinecraft149().player;
                                player5.motionX /= 8.48798316E-315;
                                final EntityPlayerSP player6 = Speed.getMinecraft26().player;
                                player6.motionZ /= 8.48798316E-315;
                                Speed.b(this.k, 2);
                                break Label_1162;
                            }
                            break Label_1162;
                    }
                    break;
            }
        }
    }
}
