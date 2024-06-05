package rip.helium.cheat.impl.misc;

import java.util.Objects;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S02PacketChat;
import rip.helium.ClientBase;
import rip.helium.Helium;
import rip.helium.cheat.Cheat;
import rip.helium.event.minecraft.PlayerUpdateEvent;
import rip.helium.event.minecraft.ProcessPacketEvent;
import rip.helium.notification.mgmt.NotificationManager;
import rip.helium.utils.Mafs;
import rip.helium.utils.Stopwatch;

public class FlagDetector extends Cheat {

    public FlagDetector() {
        super("StaffDetect", "Detects when you flag.");
    }

    public void onEnable() {
        TabListCheck();
    }

    public synchronized void staffcheccers() {
        while (true) {
            TabListCheck();
            try {
                this.wait(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    
    @Collect
    public void onproc(ProcessPacketEvent e) {
        if (e.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) e.getPacket();
            String msg = packet.getChatComponent().getUnformattedText();
            if (packet.getChatComponent().getUnformattedText().contains("A player has been removed") || msg.contains("yourbbg") || msg.contains("xBenz") || msg.contains("socialisinq") || msg.contains("Cxrtr") || msg.contains("Muel_") || msg.contains("BoofPacks") || msg.contains("Unadvised") || msg.contains("KierenBoal") || msg.contains("Teddzy") || msg.contains("JapanCrafter")|| msg.contains("MicroSquid") || msg.contains("ItsGuih")|| msg.contains("httpmeme") || msg.contains("Pizzicato_") || msg.contains("Draecotic") || msg.contains("Timppali") || msg.contains("AZXG") || msg.contains("PorkChopH3X") || msg.contains("Recrement") || msg.contains("TrippedUp") || msg.contains("Master_Aqua") || msg.contains("AyeItsBeck") || msg.contains("Chilo_") || msg.contains("teddy3684")) {
                NotificationManager.postWarning("Staff Detect", "oh fucc theres a staff.");
                ClientBase.chat("§7§m--------------------------------------------");
                ClientBase.chat("§4§lDETECTED GAY STAFF MEMBER EWWWWWW");
                ClientBase.chat("§7§m--------------------------------------------");
                mc.thePlayer.sendChatMessage("A staff member was detected! be careful my fellow hackers! (this isn't a spambot btw.)");
                if (msg.contains("Has Suicided!")) {
                    mc.thePlayer.sendChatMessage("A staff has died. They are in spec mode. BE CAREFUL!");
                }
            }
        }
    }



    public static void TabListCheck() {
                for (final Object o : mc.theWorld.loadedEntityList) {
                    if (!(o instanceof EntityLivingBase)) {
                        continue;
                    }
                    final EntityLivingBase entity = (EntityLivingBase) o;
                    if (entity.getName().equals("socialisinq") || entity.getName().equals("xBenz") || entity.getName().equals("yourbbg") || entity.getName().equals("DeathStrokeDevil") || entity.getName().equals("Cxrtr") || entity.getName().equals("Muel_") || entity.getName().equals("BoofPacks") || entity.getName().equals("Unadvised") || entity.getName().equals("KierenBoal") || entity.getName().equals("Teddzy") || entity.getName().equals("JapanCrafter") || entity.getName().equals("MicroSquid") || entity.getName().equals("ItsGuih") || entity.getName().equals("httpmeme") || entity.getName().equals("Pizzicato_") || entity.getName().equals("Draecotic") || entity.getName().equals("Timppali") || entity.getName().equals("AZXG") || entity.getName().equals("PorkChopH3X") || entity.getName().equals("Recrement") || entity.getName().equals("TrippedUp") || entity.getName().equals("Master_Aqua") || entity.getName().equals("AyeItsBeck") || entity.getName().equals("Chilo_") || entity.getName().equals("teddy3684")) {
                        ClientBase.chat("Staff detected!!!!!");
                        mc.thePlayer.sendChatMessage("[StaffDetect] Detected a staff member. Be careful fellow chea.ters!");
                    }
                }

    }


    private void isInTablist(EntityLivingBase player) {
        if (mc.isSingleplayer()) {
            return;
        }
        for (Object o : mc.getNetHandler().getPlayerInfoMap()) {
            NetworkPlayerInfo playerInfo = (NetworkPlayerInfo) o;
            if (mc.getNetHandler().getPlayerInfoMap().contains("EBallan")) {
                ClientBase.chat("found123123");
            }
        }
    }
}

