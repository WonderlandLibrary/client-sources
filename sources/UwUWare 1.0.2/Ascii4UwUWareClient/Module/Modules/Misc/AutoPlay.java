package Ascii4UwUWareClient.Module.Modules.Misc;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPacketReceive;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.Math.Mafs;
import Ascii4UwUWareClient.Util.TimerUtil;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.network.play.server.S02PacketChat;

import java.util.Objects;

public class AutoPlay extends Module {
    public AutoPlay() {
        super("AutoPlay", new String[]{}, ModuleType.Misc);

    }

    @EventHandler
    public void onPacket(EventPacketReceive e) {
        S02PacketChat packet = (S02PacketChat) e.getPacket();
        if (packet.getChatComponent().getUnformattedText().contains("here!") || mc.thePlayer.isSpectator()) {
            if (!(mc.currentScreen instanceof GuiDownloadTerrain) || Objects.nonNull(mc.thePlayer)) {
                TimerUtil timer = new TimerUtil();
                if (timer.hasReached(Mafs.getRandomInRange(4000, 5000))) {
                    mc.thePlayer.sendChatMessage("/play solo_insane");

                }
            }

        }
    }
}
