// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.module.modules;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.SentPacket;
import me.kaktuswasser.client.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class AntiHunger extends Module
{
    public AntiHunger() {
        super("AntiHunger", -9868801, Category.EXPLOITS);
        this.setTag("Anti Hunger");
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof SentPacket) {
            if (Client.getModuleManager().getModuleByName("glide").isEnabled() || Client.getModuleManager().getModuleByName("fly").isEnabled()) {
                return;
            }
            final SentPacket event = (SentPacket)e;
            if (event.getPacket() instanceof C03PacketPlayer) {
                final C03PacketPlayer player = (C03PacketPlayer)event.getPacket();
                final double yDifference = AntiHunger.mc.thePlayer.posY - AntiHunger.mc.thePlayer.lastTickPosY;
                final boolean groundCheck = yDifference == 0.0;
                if (groundCheck && !AntiHunger.mc.playerController.isHittingBlock) {
                    player.field_149474_g = false;
                }
            }
        }
    }
}
