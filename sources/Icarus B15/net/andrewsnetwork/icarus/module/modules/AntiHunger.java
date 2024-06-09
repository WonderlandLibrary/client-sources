// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.event.events.SentPacket;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

public class AntiHunger extends Module
{
    public AntiHunger() {
        super("AntiHunger", -9868801, Category.EXPLOITS);
        this.setTag("Anti Hunger");
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof SentPacket) {
            if (Icarus.getModuleManager().getModuleByName("glide").isEnabled() || Icarus.getModuleManager().getModuleByName("fly").isEnabled()) {
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
