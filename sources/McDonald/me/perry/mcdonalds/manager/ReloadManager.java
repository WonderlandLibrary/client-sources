// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.manager;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.perry.mcdonalds.McDonalds;
import net.minecraft.network.play.client.CPacketChatMessage;
import me.perry.mcdonalds.event.events.PacketEvent;
import me.perry.mcdonalds.features.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import me.perry.mcdonalds.features.Feature;

public class ReloadManager extends Feature
{
    public String prefix;
    
    public void init(final String prefix) {
        this.prefix = prefix;
        MinecraftForge.EVENT_BUS.register((Object)this);
        if (!Feature.fullNullCheck()) {
            Command.sendMessage(ChatFormatting.RED + "McDonalds has been unloaded. Type " + prefix + "reload to reload.");
        }
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        final CPacketChatMessage packet;
        if (event.getPacket() instanceof CPacketChatMessage && (packet = event.getPacket()).getMessage().startsWith(this.prefix) && packet.getMessage().contains("reload")) {
            McDonalds.load();
            event.setCanceled(true);
        }
    }
}
