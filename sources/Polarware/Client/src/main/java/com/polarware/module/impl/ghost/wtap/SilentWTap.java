package com.polarware.module.impl.ghost.wtap;

import com.polarware.module.impl.ghost.WTapModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.AttackEvent;
import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public final class SilentWTap extends Mode<WTapModule> {

    private boolean sprinting, wTap;
    private int ticks;

    public SilentWTap(String name, WTapModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        ticks = 0;
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        ticks++;

        switch (ticks) {
            case 1:
                wTap = Math.random() * 100 < getParent().chance.getValue().doubleValue();
                if (sprinting) {
                    PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                } else {
                    PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                }
                break;

            case 2:
                if (!sprinting) {
                    PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                }
                break;
        }
    };

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof C0BPacketEntityAction) {
            final C0BPacketEntityAction wrapper = (C0BPacketEntityAction) packet;

            switch (wrapper.getAction()) {
                case START_SPRINTING:
                    sprinting = true;
                    break;

                case STOP_SPRINTING:
                    sprinting = false;
                    break;
            }
        }
    };
}
