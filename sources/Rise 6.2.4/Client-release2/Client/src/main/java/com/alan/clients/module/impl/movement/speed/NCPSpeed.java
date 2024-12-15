package com.alan.clients.module.impl.movement.speed;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.TeleportEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class NCPSpeed extends Mode<Speed> {
    public NCPSpeed(String name, Speed parent) {
        super(name, parent);
    }
    private final NumberValue jumpMotion = new NumberValue("Jump Motion", this, 0.4, 0.4, 0.42, 0.01);
    private final NumberValue groundSpeed = new NumberValue("Ground Speed", this, 1.75, 0.1, 2.5, 0.05);
    private final NumberValue bunnySlope = new NumberValue("Bunny Slope", this, 0.66, 0, 1, 0.01);
    private final NumberValue timer = new NumberValue("Timer", this, 1, 0.1, 10, 0.05);
    private final BooleanValue boost = new BooleanValue("Damage Boost", this, true);
    private final BooleanValue hurtBoost = new BooleanValue("Custom Boost", this, false);
    private final NumberValue boostSpeed = new NumberValue("Boost Speed", this, .8, 0.1, 9.5, 0.1);

    private final BooleanValue lowHop = new BooleanValue("Low Hop", this, false);

    private final BooleanValue yPort = new BooleanValue("Y-port Hop", this, false);
    private final NumberValue hurTime = new NumberValue("Hurt Time", this, 6, 1, 10, 1);

    private boolean reset;
    private double speed;



    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        if (!boost.getValue()) return;

        final Packet<?> packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity wrapper = ((S12PacketEntityVelocity) packet);
            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                speed = Math.hypot(wrapper.motionX / 8000.0D, wrapper.motionZ / 8000.0D);
            }
        }
    };
    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (lowHop.getValue()){
            if (mc.thePlayer.offGroundTicks == 4){
                mc.thePlayer.motionY = -0.09800000190734864;
            }
        }

        if (yPort.getValue() && mc.thePlayer.offGroundTicks == 5 && Math.abs(mc.thePlayer.motionY - 0.09800000190734864) < 0.12){
            mc.thePlayer.motionY = -0.09800000190734864;
        }

        if (hurtBoost.getValue() && mc.thePlayer.ticksSincePlayerVelocity <= hurTime.getValue().intValue()) {
            speed = boostSpeed.getValue().doubleValue();
        }

        final double base = MoveUtil.getAllowedHorizontalDistance();

        if (MoveUtil.isMoving()) {
            switch (mc.thePlayer.offGroundTicks) {
                case 0:
                    float jumpMotion = this.jumpMotion.getValue().floatValue();

                    float motion = mc.thePlayer.isCollidedHorizontally ? 0.42F : jumpMotion == 0.4f ? jumpMotion : 0.42f;
                    mc.thePlayer.motionY = MoveUtil.jumpBoostMotion(motion);
                    speed = base * groundSpeed.getValue().doubleValue();
                    break;

                case 1:
                    speed -= (bunnySlope.getValue().doubleValue() * (speed - base));
                    break;

                default:
                    speed -= speed / MoveUtil.BUNNY_FRICTION;
                    break;
            }

            mc.timer.timerSpeed = timer.getValue().floatValue();
            reset = false;
        } else if (!reset) {
            speed = MoveUtil.getAllowedHorizontalDistance();
            mc.timer.timerSpeed = 1;
            reset = true;
        }

        if (mc.thePlayer.isCollidedHorizontally) {
            speed = MoveUtil.getAllowedHorizontalDistance();
        }

        event.setSpeed(Math.max(speed, base), Math.random() / 2000);
    };

    @EventLink
    public final Listener<TeleportEvent> onTeleport = event -> {
        speed = 0;
    };

    @Override
    public void onDisable() {
        speed = 0;
    }
}