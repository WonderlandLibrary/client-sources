package cc.slack.features.modules.impl.movement.glides.impl;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.glides.IGlide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class SololegendsGlide implements IGlide {

    private boolean spoofRequired = false;
    private double fallPacketCount = 0.0;


    @Override
    public void onEnable() {
        spoofRequired = false;
        fallPacketCount = 0.0;
    }

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer && spoofRequired) {
            ((C03PacketPlayer) event.getPacket()).onGround = true;
            spoofRequired = false;
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        EntityPlayer currentPlayer = mc.thePlayer;
        if (currentPlayer.fallDistance - currentPlayer.motionY > 0.5) {
            currentPlayer.motionY = 0.0;
            currentPlayer.fallDistance = 0.0f;
            currentPlayer.motionX *= 0.6;
            currentPlayer.motionZ *= 0.6;
            spoofRequired = true;
        }

        if (currentPlayer.fallDistance / 3 > fallPacketCount) {
            fallPacketCount = currentPlayer.fallDistance / 0.5;
        }

        if (currentPlayer.onGround) {
            fallPacketCount = 0.0;
        }
    }

    @Override
    public String toString() {
        return "SoloLegends";
    }
}
