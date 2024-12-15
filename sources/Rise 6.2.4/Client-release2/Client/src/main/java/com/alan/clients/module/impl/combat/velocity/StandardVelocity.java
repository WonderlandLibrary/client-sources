package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.TeleportEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.combat.Criticals;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class StandardVelocity extends Mode<Velocity> {
    public Boolean disable2 = false;

    private Integer time = 0;
    private Speed speed = null;

    private String mode = null;

    private int amount;

    private final NumberValue horizontal = new NumberValue("Horizontal", this, 0, 0, 100, 1);
    private final NumberValue vertical = new NumberValue("Vertical", this, 0, 0, 100, 1);

    private final BooleanValue onExplode = new BooleanValue("Explosion Ignore", this, false);


    @Override
    public void onEnable() {
      time = 0;
    setDisable2(false);
    }

    public StandardVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if ((getParent().onSwing.getValue() && !mc.thePlayer.isSwingInProgress) || event.isCancelled()) {
            return;
        }

        final Packet<?> p = event.getPacket();

        final double horizontal = this.horizontal.getValue().doubleValue();
        final double vertical = this.vertical.getValue().doubleValue();
        final boolean onExplode = this.onExplode.getValue();

        if (p instanceof S12PacketEntityVelocity) {

            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {


    if (horizontal == 0) {
        if (vertical != 0 && !event.isCancelled()) {

            mc.thePlayer.motionY = wrapper.getMotionY() / 8000.0D;
        }


        event.setCancelled();
        return;
    }

    wrapper.motionX *= horizontal / 100;
    wrapper.motionY *= vertical / 100;
    wrapper.motionZ *= horizontal / 100;

    event.setPacket(wrapper);


            }
        } else if (p instanceof S27PacketExplosion) {
            final S27PacketExplosion wrapper = (S27PacketExplosion) p;

            if (onExplode) {
                event.setCancelled();
                return;
            }

            wrapper.posX *= horizontal / 100;
            wrapper.posY *= vertical / 100;
            wrapper.posZ *= horizontal / 100;

            event.setPacket(wrapper);
        }
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (speed == null) {
            speed = getModule(Speed.class);

        }


/*
if(speed.isEnabled() && Client.INSTANCE.getModuleManager().get(Speed.class).mode.getValue().getName().equals("Watchdog")) {

        if (mc.thePlayer.hurtTime > 9) {
            mc.thePlayer.motionZ *= 0;
            mc.thePlayer.motionX *= 0;
            time++;

            if (!MoveUtil.isMoving()) {
                mc.thePlayer.motionZ *= 0;
                mc.thePlayer.motionX *= 0;
            }
        }

        if (mc.thePlayer.hurtTime == 9) {
            if (MoveUtil.isMoving() && !mc.thePlayer.onGround && !( mc.thePlayer.offGroundTicks == 1)) {
               // MoveUtil.partialStrafePercent(99);
                double speed =1;
                speed = Math.random()/50;
                MoveUtil.strafe(.3);
                  // mc.thePlayer.motionZ *= .95;
                 //  mc.thePlayer.motionX *= .95;
            } else {
                mc.thePlayer.motionZ *= -.2;
                mc.thePlayer.motionX *= -.2;
            }


        }
    }

 */

    };

    @EventLink
    public final Listener<TeleportEvent> onTeleport = event -> {
        if (event.getPosY() < mc.thePlayer.posY - 2);


    };

    public Boolean getDisable2() {
        return disable2;
    }

    public void setDisable2(Boolean disable2) {
        this.disable2 = disable2;
    }
}


