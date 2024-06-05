/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPacketReceive;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.UTILS.helper.Helper;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.EnumChatFormatting;

public class LightningTracker
extends Module {
    public LightningTracker() {
        super("Lightning Tracker", new String[0], Type.MISC, "No");
    }

    @EventHandler
    public void onReceive(EventPacketReceive e) {
        if (!(e.getPacket() instanceof S29PacketSoundEffect)) return;
        S29PacketSoundEffect packetSoundEffect = (S29PacketSoundEffect)e.getPacket();
        if (!packetSoundEffect.getSoundName().equals("ambient.weather.thunder")) return;
        int packetX = (int)packetSoundEffect.getX();
        int packetY = (int)(packetSoundEffect.getY() + 5.0);
        int packetZ = (int)packetSoundEffect.getZ();
        Helper.sendMessage("Lightning detected at " + (Object)((Object)EnumChatFormatting.DARK_GRAY) + "[" + (Object)((Object)EnumChatFormatting.RED) + packetX + " " + packetY + " " + packetZ + (Object)((Object)EnumChatFormatting.DARK_GRAY) + "]");
    }
}

