package com.polarware.module.impl.combat.regen;

import com.polarware.module.impl.combat.RegenModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.MoveInputEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S2DPacketOpenWindow;

public final class WorldGuardRegen extends Mode<RegenModule> {

    private final NumberValue health = new NumberValue("Minimum Health", this, 15, 1, 20, 1);

    private int ticks;

    private float yaw;
    private float pitch;

    public WorldGuardRegen(String name, RegenModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.onGround && mc.thePlayer.getHealth() < this.health.getValue().floatValue()) {
            if (ticks <= 1) {
                event.setPosY(event.getPosY() - 0.05);
                event.setYaw(yaw);
                event.setPitch(pitch);
                ticks++;
            }
        } else {
            yaw = event.getYaw();
            pitch = event.getPitch();
            ticks = 0;
        }
    };

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        final Packet<?> p = event.getPacket();

        if (p instanceof C02PacketUseEntity && mc.thePlayer.onGround && mc.thePlayer.getHealth() < this.health.getValue().floatValue() && ticks <= 1) {
            final C02PacketUseEntity wrapper = (C02PacketUseEntity) p;

            if (wrapper.getAction().equals(C02PacketUseEntity.Action.ATTACK)) {
                event.setCancelled(true);

                PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                PacketUtil.sendNoEvent(wrapper);
                PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.05, mc.thePlayer.posZ, false));
            }
        }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        if (event.getPacket() instanceof S2DPacketOpenWindow && ticks <= 1) {
            event.setCancelled(true);
        }
    };

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (mc.thePlayer.onGround && mc.thePlayer.getHealth() < this.health.getValue().floatValue() && ticks <= 1) {
            event.setSpeed(0);
        }
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        if (mc.thePlayer.onGround && mc.thePlayer.getHealth() < this.health.getValue().floatValue() && ticks <= 1) {
            event.setJump(false);
        }
    };
}