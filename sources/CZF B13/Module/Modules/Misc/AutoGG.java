package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketSend;
import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Blatant.Killaura;
import gq.vapu.czfclient.UI.ClientNotification;
import gq.vapu.czfclient.Util.ClientUtil;
import net.minecraft.network.play.server.S45PacketTitle;

import java.util.Timer;
import java.util.TimerTask;

public class AutoGG extends Module {

    String[] Hypixel = {"1st Place - ", "Winner - ", " - Damage Dealt - ", "Winning Team -", "1st - ", "Winners: ",
            "Winner: ", "Winning Team: ", " won the game!", "Top Seeker: ", "1st Place: ", "Last team standing!",
            "Winner #1 (", "Top Survivors", "Winners - ", "Sumo Duel - "};

    public AutoGG() {
        super("AutoGG", new String[]{"TPAccept, autotp"}, ModuleType.World);
    }

    @EventHandler
    private void onPacketSend(EventPacketSend ep) {
        if (EventPacketSend.getPacket() instanceof S45PacketTitle) {
            S45PacketTitle packet = (S45PacketTitle) EventPacketSend.getPacket();
            if (packet.getType().equals(S45PacketTitle.Type.TITLE)) {
                String text = packet.getMessage().getUnformattedText();
                if (text.contains("VICT") || text.contains("Ê¤Àû")) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mc.thePlayer.sendChatMessage("GG");
                            this.cancel();
                        }
                    }, 1340L);
                    ModuleManager.getModuleByClass(Killaura.class).setEnabled(false);
                    ClientUtil.sendClientMessage("(Auto)Killaura Disable", ClientNotification.Type.ERROR);
                }
            }
        }
//		if (event.getPacket() instanceof C01PacketChatMessage) {
//			C01PacketChatMessage packetChatMessage = (C01PacketChatMessage) event.getPacket();
//			String unformattedMessage = packetChatMessage.getMessage();
//			for (String gg : Hypixel) {
//				if (unformattedMessage.contains(gg)) {//And?
//					mc.thePlayer.sendChatMessage("GG");
//					Client.instance.getModuleManager().getModuleByClass(Killaura.class).setEnabled(false);
//					ClientUtil.sendClientMessage("(Auto)Killaura Disable", ClientNotification.Type.ERROR);
//					return;
//				}
//			}
//		}
    }
}
