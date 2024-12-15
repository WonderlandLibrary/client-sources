package com.alan.clients.module.impl.movement.flight;

import com.alan.clients.component.impl.player.ItemDamageComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class MatrixFlight extends Mode<Flight> {

    private double verticalVelocity, horizontalVelocity;
    private int velocity;
    private boolean damage;
    private ItemDamageComponent.Type type;

    public MatrixFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        this.velocity = 1000;
        ItemDamageComponent.damage(false);
        this.damage = false;
        this.type = null;
    }

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        velocity++;

        if (ItemDamageComponent.type == null) {
            return;
        }

        if (type == null) {
            type = ItemDamageComponent.type;
        }

        if (mc.thePlayer.onGround && damage) {
            onDisable();
        }

        if (type == ItemDamageComponent.Type.ROD) {
            if (this.damage) {
                final float speed = 1;

                mc.thePlayer.motionY = -1E-10D
                        + (mc.gameSettings.keyBindJump.isKeyDown() ? speed : 0.0D)
                        - (mc.gameSettings.keyBindSneak.isKeyDown() ? speed : 0.0D);

                if (mc.thePlayer.getDistance(mc.thePlayer.lastReportedPosX, mc.thePlayer.lastReportedPosY, mc.thePlayer.lastReportedPosZ) <= 10 - speed - 0.15) {
                    event.setCancelled();
                }
            }
        } else {
            if (velocity <= 3) {
                final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
                final double speedBoost = (horizontalVelocity * 1.6) / 10 + 0.3;

                MoveUtil.strafe(this.horizontalVelocity);
                mc.thePlayer.motionY = verticalVelocity;
            }
        }
    };

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        final Packet<?> packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity s12PacketEntityVelocity = ((S12PacketEntityVelocity) packet);

            if (mc.thePlayer.getEntityId() == s12PacketEntityVelocity.getEntityID()) {
                final double velocityMotion = s12PacketEntityVelocity.motionY / 8000.0;

                if (velocityMotion > 0.2) {
                    this.verticalVelocity = velocityMotion;
                    this.horizontalVelocity = Math.sqrt(Math.abs(s12PacketEntityVelocity.motionX) / 8000f + Math.abs(s12PacketEntityVelocity.motionZ) / 8000f);
                    this.velocity = 0;

                    event.setCancelled();
                    damage = true;
                }
            }
        }
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (type == ItemDamageComponent.Type.ROD && damage) {
            final float speed = 1;

            event.setSpeed(speed);
        } else if (velocity >= 1000 || velocity <= 29) {
            event.setForward(0);
            event.setStrafe(0);
        }
    };
}