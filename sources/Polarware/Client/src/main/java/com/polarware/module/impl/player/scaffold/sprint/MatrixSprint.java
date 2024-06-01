package com.polarware.module.impl.player.scaffold.sprint;

import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.MoveInputEvent;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class MatrixSprint extends Mode<ScaffoldModule> {
    private int time;
    private boolean ignore;

    public MatrixSprint(String name, ScaffoldModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        time++;

        mc.gameSettings.keyBindSneak.setPressed(time >= 4);
    };

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        final Packet<?> p = event.getPacket();

        if (p instanceof C08PacketPlayerBlockPlacement) {
            final C08PacketPlayerBlockPlacement wrapper = (C08PacketPlayerBlockPlacement) p;

            if (wrapper.getPlacedBlockDirection() != 255) {
                time = 0;
            }
        }
    };

    @EventLink(value = Priority.MEDIUM)
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setSneakSlowDownMultiplier(0.5);
        mc.gameSettings.keyBindSprint.setPressed(false);
        mc.thePlayer.setSprinting(false);
    };
}
