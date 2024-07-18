package com.alan.clients.module.impl.movement.longjump;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public final class VulcanLongJump extends Mode<LongJump> {

    private boolean ignore;
    private int ticks;

    public VulcanLongJump(String name, LongJump parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        ticks++;

        if (mc.thePlayer.fallDistance > 0 && ticks % 2 == 0 && mc.thePlayer.fallDistance < 2.2) {
            mc.thePlayer.motionY += 0.14F;
        }

        switch (ticks) {
            case 1:
                mc.timer.timerSpeed = 0.5F;

                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.0784000015258789, mc.thePlayer.posZ, mc.thePlayer.onGround));
                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));

                ignore = true;
                MoveUtil.strafe(7.9);
                mc.thePlayer.motionY = 0.42F;
                break;

            case 2:

                mc.thePlayer.motionY += 0.1F;
                MoveUtil.strafe(2.79);
                break;

            case 3:
                MoveUtil.strafe(2.56);
                break;

            case 4:
                event.setOnGround(true);
                mc.thePlayer.onGround = true;
                MoveUtil.strafe(0.49);
                break;

            case 5:

                MoveUtil.strafe(0.59);
                break;

            case 6:
                MoveUtil.stop();
                MoveUtil.strafe(0.3);
                break;
        }
    };

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }

    @Override
    public void onEnable() {
        if (!mc.thePlayer.onGround) {
            this.toggle();
        }

        ignore = false;
        ticks = 0;
    }

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        final Packet<?> packet = event.getPacket();

        if (packet instanceof S08PacketPlayerPosLook && ignore) {
            event.setCancelled();
            ignore = false;
        }
    };
}