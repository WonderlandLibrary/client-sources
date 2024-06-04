package com.polarware.module.impl.player.scaffold.sprint;

import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class VulcanSprint extends Mode<ScaffoldModule> {
    private final NumberValue speed = new NumberValue("Speed", this, 1.3, 0.9, 1.3, 0.05);

    private int time, block;

    public VulcanSprint(String name, ScaffoldModule parent) {
        super(name, parent);
    }

    @Override
    public void onDisable() {
        if (time == 1) {
            PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.thePlayer.setSprinting(false);

        final double speed = this.speed.getValue().doubleValue();

        if (!mc.gameSettings.keyBindJump.isKeyDown() && speed > 0.9) {
            if (mc.thePlayer.onGroundTicks >= 2 && block <= 10) {
                MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() * speed);

                mc.thePlayer.jump();
                mc.thePlayer.motionY = 0.012500047683714;
            }
        }

        if (mc.thePlayer.onGround) {
            MoveUtil.strafe();
        }

        time++;
        block++;

        switch (time) {
            case 1:
                PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                break;

            case 10:
                time = 0;
                break;
        }
    };

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        final Packet<?> p = event.getPacket();

        if (p instanceof C08PacketPlayerBlockPlacement) {
            final C08PacketPlayerBlockPlacement wrapper = (C08PacketPlayerBlockPlacement) p;

            if (wrapper.getPlacedBlockDirection() != 255) {
                block = 0;
            }
        }
    };
}
