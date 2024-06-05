package me.vaziak.sensation.client.impl.visual;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.client.api.event.events.RenderNametagEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class NameProtect extends Module {
    private BooleanProperty nametags = new BooleanProperty("Nametag destruction", "Remove nametags - prevent people from seeing your opponents", null, false);
    private BooleanProperty scoreboard = new BooleanProperty("Scoreboard destruction", "Removes the scoreboard - prevent people from seeing the scoreboard which may contain sensitive information such as YOUR in game name", null, false);

    public NameProtect() {
        super("Name Protect", Category.VISUAL);
        registerValue(nametags, scoreboard);
    }

    int delaytime; 

    public void onEnable() {

    }

    public void onDisable() {

    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        delaytime++;
    }

    @Collect
    public void onSendPacket(SendPacketEvent sendPacketEvent) {

    }

    @Collect
    public void onProcessPacket(ProcessPacketEvent processPacketEvent) { 
        if (processPacketEvent.getPacket() instanceof S02PacketChat && mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.toLowerCase().contains("minemen")) {
            S02PacketChat packet = (S02PacketChat) processPacketEvent.getPacket();
            System.out.println(packet.getChatComponent().getUnformattedText());
            if (packet.getChatComponent().getUnformattedText().contains(mc.session.getUsername())) {
             //   processPacketEvent.setCancelled(true);
            }
            if (packet.getChatComponent().getUnformattedText().contains("Winner") && packet.getChatComponent().getUnformattedText().contains(mc.session.getUsername())) {
                mc.thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "___________________________________________"));
                mc.thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Canceled packets containing round info" + EnumChatFormatting.YELLOW + " - you won btw!"));
                mc.thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "___________________________________________"));
            }
        }
        if (processPacketEvent.getPacket() instanceof S3DPacketDisplayScoreboard) {
            S3DPacketDisplayScoreboard packet = (S3DPacketDisplayScoreboard)processPacketEvent.getPacket();
            packet.setString(packet.func_149370_d().replace("minemen.club", "mantheIsSpecial.edu").replace(mc.session.getUsername(), "ArchyAi"));
        }
        if (processPacketEvent.getPacket() instanceof S3DPacketDisplayScoreboard && scoreboard.getValue()) {//Canceling scoreboard, display name is big nono
            processPacketEvent.setCancelled(true);
        }
    }

    @Collect
    public void onRenderNametag(RenderNametagEvent renderNametagEvent) {
        if (nametags.getValue()) {
            renderNametagEvent.setCancelled(true);
        }
    }

}