package com.alan.clients.module.impl.player.scaffold.tower;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class BlocksMCTower extends Mode<Scaffold> {

    private int tower = 5;
        public BlocksMCTower(String name, Scaffold parent) {
            super(name, parent);
        }

        @EventLink
        public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
            if (mc.gameSettings.keyBindJump.isKeyDown() && PlayerUtil.blockNear(2) && !MoveUtil.isMoving()) {
                if (mc.thePlayer.posY % 1 <= 0.00153598) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
                    mc.thePlayer.motionY = 0.42F;
                } else if (mc.thePlayer.posY % 1 < 0.1 && mc.thePlayer.offGroundTicks != 0) {
                    mc.thePlayer.motionY = 0;
                    mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
                }
            }

            if (MoveUtil.isMoving() && mc.gameSettings.keyBindJump.isKeyDown()) {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                }
            }


        };

    @EventLink
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        final Packet<?> packet = event.getPacket();
        if (MoveUtil.isMoving()){
            if (mc.thePlayer.motionY > -0.09800000190734864 && packet instanceof C08PacketPlayerBlockPlacement) {
                final C08PacketPlayerBlockPlacement wrapper = ((C08PacketPlayerBlockPlacement) packet);

                if (wrapper.getPosition().equals(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.4, mc.thePlayer.posZ))) {
                    mc.thePlayer.motionY = -0.09800000190734864;
                }
            }
    }

    };
}



