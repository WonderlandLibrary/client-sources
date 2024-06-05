package rip.helium.cheat.impl.visual;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import rip.helium.ClientBase;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.PlayerUpdateEvent;
import rip.helium.event.minecraft.ProcessPacketEvent;
import rip.helium.event.minecraft.RenderNametagEvent;
import rip.helium.event.minecraft.SendPacketEvent;
import rip.helium.utils.property.impl.BooleanProperty;

public class NameProtect extends Cheat {
    public NameProtect() {
        super("Streaming", "Makes mods and admins (such as hapis) have a hard time finding your name on stream", CheatCategory.VISUAL);
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
                processPacketEvent.setCancelled(true);
            }
            if (packet.getChatComponent().getUnformattedText().contains("Winner") && packet.getChatComponent().getUnformattedText().contains(mc.session.getUsername())) {
                ClientBase.chat("You won.");
            }
        }
    }

    @Collect
    public void onRenderNametag(RenderNametagEvent renderNametagEvent) {
        renderNametagEvent.setCancelled(true);
    }

}