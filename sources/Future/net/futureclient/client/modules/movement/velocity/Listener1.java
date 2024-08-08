package net.futureclient.client.modules.movement.velocity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.world.World;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.futureclient.loader.mixin.common.network.packet.clientbound.wrapper.ISPacketExplosion;
import net.minecraft.network.play.server.SPacketExplosion;
import net.futureclient.loader.mixin.common.network.packet.clientbound.wrapper.ISPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.futureclient.client.ad;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.Velocity;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener1 extends n<we>
{
    public final Velocity k;
    
    public Listener1(final Velocity k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
    
    public void M(final EventPacket eventPacket) {
        if (!((ad.Dc)Velocity.M(this.k).M()).equals((Object)ad.Dc.D)) {
            return;
        }
        if (eventPacket.M() instanceof SPacketEntityVelocity) {
            final SPacketEntityVelocity sPacketEntityVelocity = (SPacketEntityVelocity)eventPacket.M();
            if (Velocity.getMinecraft1().world.getEntityByID(sPacketEntityVelocity.getEntityID()) == Velocity.getMinecraft2().player) {
                if (this.k.horizontal.B().doubleValue() > 0.0 || this.k.vertical.B().doubleValue() > 0.0) {
                    ((ISPacketEntityVelocity)sPacketEntityVelocity).setMotionX(sPacketEntityVelocity.getMotionX() * this.k.horizontal.B().intValue() / 100);
                    ((ISPacketEntityVelocity)sPacketEntityVelocity).setMotionY(sPacketEntityVelocity.getMotionY() * this.k.vertical.B().intValue() / 100);
                    ((ISPacketEntityVelocity)sPacketEntityVelocity).setMotionZ(sPacketEntityVelocity.getMotionZ() * this.k.horizontal.B().intValue() / 100);
                    return;
                }
                eventPacket.M(true);
            }
        }
        else {
            if (eventPacket.M() instanceof SPacketExplosion) {
                final SPacketExplosion sPacketExplosion;
                ((ISPacketExplosion)(sPacketExplosion = (SPacketExplosion)eventPacket.M())).setMotionX(sPacketExplosion.getMotionX() * this.k.horizontal.B().floatValue() / 100.0f);
                ((ISPacketExplosion)sPacketExplosion).setMotionY(sPacketExplosion.getMotionY() * this.k.vertical.B().floatValue() / 100.0f);
                ((ISPacketExplosion)sPacketExplosion).setMotionZ(sPacketExplosion.getMotionZ() * this.k.horizontal.B().floatValue() / 100.0f);
                return;
            }
            if (eventPacket.M() instanceof SPacketEntityStatus) {
                final SPacketEntityStatus sPacketEntityStatus = (SPacketEntityStatus)eventPacket.M();
                if (!this.k.fishingHook.M()) {
                    return;
                }
                if (sPacketEntityStatus.getOpCode() == 31) {
                    final Entity entity;
                    if ((entity = sPacketEntityStatus.getEntity((World)Velocity.getMinecraft5().world)) == null) {
                        return;
                    }
                    if (entity instanceof EntityFishHook) {
                        final EntityFishHook entityFishHook;
                        if ((entityFishHook = (EntityFishHook)entity).caughtEntity == null) {
                            return;
                        }
                        if (entityFishHook.caughtEntity == Velocity.getMinecraft().player) {
                            eventPacket.M(true);
                        }
                    }
                }
            }
        }
    }
}
