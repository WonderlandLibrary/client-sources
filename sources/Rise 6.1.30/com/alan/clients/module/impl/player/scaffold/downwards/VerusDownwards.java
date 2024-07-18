package com.alan.clients.module.impl.player.scaffold.downwards;

import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.vector.Vector3d;
import com.alan.clients.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;

public class VerusDownwards extends Mode<Scaffold> {
    int blocks;

    public VerusDownwards(String name, Scaffold parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        blocks = 1000;
    }

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<StrafeEvent> onPreMotionEvent = event -> {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
            return;
        }

//        if (MoveUtil.speed() > MoveUtil.getAllowedHorizontalDistance() * 0.7) {
//            MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() * 0.5);
//        }

        mc.gameSettings.keyBindSprint.setPressed(false);
        mc.thePlayer.setSprinting(false);
    };

    @EventLink(value = Priorities.HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) || mc.thePlayer.ticksSincePlace > 20) {
            blocks = 1000;
            return;
        }

        blocks++;

        if (mc.thePlayer.posY % 1 != 0) {
            blocks = 0;
        }

        if (mc.thePlayer.motionY <= 0 && blocks >= 15) {
            getParent().offset = getParent().offset.add(0, -1, 0);
        }
    };

    @EventLink
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof C08PacketPlayerBlockPlacement) {
            C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement = (C08PacketPlayerBlockPlacement) packet;

            if (!c08PacketPlayerBlockPlacement.getPosition().equalsVector(new Vector3d(-1, -1, -1)) &&
                    c08PacketPlayerBlockPlacement.getPlacedBlockDirection() == EnumFacing.DOWN.getIndex()) {
                if (false)blocks = 0;
            }
        }
    };

}
