package studio.dreamys.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class PlayerUtils {
    public static final String prefix = "§b§l[§f§lnear§b§l]§r ";
    public static final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

    public static void addMessage(String msg, String result) {
        if (player == null) {
            System.out.println(msg);
            return;
        }

        ChatFormatting color = ChatFormatting.RESET;
            switch (result) {
                case "success":
                    color = ChatFormatting.GREEN;
                    break;
                case "error":
                    color = ChatFormatting.RED;
                    break;
            }

        player.addChatComponentMessage(new ChatComponentText(prefix + color + msg));
    }

    public static void addMessage(ChatComponentText comp) {
        player.addChatComponentMessage(new ChatComponentText(prefix + comp));
    }


    public static void addMessage(String msg) {
        if (player == null) {
            System.out.println(msg);
            return;
        }

        player.addChatComponentMessage(new ChatComponentText(prefix + msg));
    }

    public static boolean inDungeons() {
        if (inSkyblock()) {
            List<String> scoreboard = ScoreboardHandler.getSidebarLines();
            for (String s : scoreboard) {
                String sCleaned = ScoreboardHandler.cleanSB(s);
                if (sCleaned.contains("The Catacombs")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean inSkyblock() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
            ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (scoreboardObj != null) {
                String scObjName = ScoreboardHandler.cleanSB(scoreboardObj.getDisplayName());
                return scObjName.contains("SKYBLOCK");
            }
        }
        return false;
    }
}
