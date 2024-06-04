package com.polarware.module.impl.movement.flight;

import com.polarware.component.impl.player.BadPacketsComponent;
import com.polarware.module.impl.movement.FlightModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.MoveInputEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class BufferAbuseFlight extends Mode<FlightModule> {

    private final NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 9.5, 0.1);
    private final BooleanValue sendFlying = new BooleanValue("Send Flying", this, false);

    public BufferAbuseFlight(String name, FlightModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        final float speed = this.speed.getValue().floatValue();
        event.setSpeed(speed);
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        final float speed = this.speed.getValue().floatValue();

        mc.thePlayer.motionY = -1E-10D
                + (mc.gameSettings.keyBindJump.isKeyDown() ? speed : 0.0D)
                - (mc.gameSettings.keyBindSneak.isKeyDown() ? speed : 0.0D);

        if (mc.thePlayer.getDistance(mc.thePlayer.lastReportedPosX, mc.thePlayer.lastReportedPosY, mc.thePlayer.lastReportedPosZ) <= 10 - speed - 0.15) {
            event.setCancelled(true);
        }
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setSneak(false);
    };

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        if (!sendFlying.getValue()) {
            Packet<?> packet = event.getPacket();

            if (packet instanceof C03PacketPlayer) {
                C03PacketPlayer c03PacketPlayer = ((C03PacketPlayer) packet);

                if (!c03PacketPlayer.isMoving() && !BadPacketsComponent.bad()) {
                    event.setCancelled(true);
                }
            }
        }
    };
}