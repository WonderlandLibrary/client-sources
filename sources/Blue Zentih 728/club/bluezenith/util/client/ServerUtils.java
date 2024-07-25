package club.bluezenith.util.client;

import club.bluezenith.BlueZenith;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.function.Predicate;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.client.ServerUtils.HypixelGameMode.OTHER;
import static club.bluezenith.util.render.ColorUtil.stripFormatting;
import static java.util.Arrays.stream;
import static java.util.Locale.ROOT;

public class ServerUtils {

    public static boolean hypixel;
    public static boolean blocksmc;

    public static boolean isMushMC() {
        return getBlueZenith() != null
                && getBlueZenith().getCurrentServerIP() != null
                && getBlueZenith().getCurrentServerIP().contains("mushmc.com");
    }

    public static boolean isBlocksMC() {
        return blocksmc;

    }

    public static boolean checkHypixel() {
        if(Minecraft.getMinecraft().isSingleplayer()) return false;
        final String ip = BlueZenith.getBlueZenith().getCurrentServerIP();
        final boolean hasModifiedHosts = hasModifiedHosts("hypixel.net");
        final boolean result = !hasModifiedHosts && ip.contains("hypixel.net");
        return hypixel = result;
    }


    public static boolean checkBlocksMC() {
        if(Minecraft.getMinecraft().isSingleplayer()) return false;
        final String ip = BlueZenith.getBlueZenith().getCurrentServerIP();
        final boolean hasModifiedHosts = hasModifiedHosts("blocksmc");
        final boolean result = !hasModifiedHosts && ip.contains("blocksmc.com");
        return blocksmc = result;
    }

    public static HypixelGameMode getGameMode() {
        if(Minecraft.getMinecraft().isSingleplayer()) return HypixelGameMode.NONE;
        if(!hypixel) return HypixelGameMode.NONE;
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if(player == null) return HypixelGameMode.NONE;
        final ItemStack stack = player.inventory.mainInventory[4];
        if(stack != null) {
            if(stripFormatting(stack.getDisplayName()).contains("Collectibles")) return HypixelGameMode.LOBBY;
        }
      // final String gamemodeName = stripFormatting(mc.theWorld.getScoreboard().getScoreObjectives().iterator().next().getDisplayName());

        return stream(HypixelGameMode.values()).filter(mode -> mode.matcher.test("gamemodeName")).findFirst().orElse(OTHER);
    }

    static boolean hasModifiedHosts(String lookFor) {
        try {
            File hostsFile = new File(System.getenv("WinDir")
                        + "\\system32\\drivers\\etc\\hosts");
            BufferedReader reader = new BufferedReader(new FileReader(hostsFile));
            String ln;
            StringBuilder builder = new StringBuilder();
            while ((ln = reader.readLine()) != null) {
                    builder.append(ln);
                    if (!ln.startsWith("#") && ln.toLowerCase().contains(lookFor.toLowerCase())) return true;
                }
            return builder.toString().toLowerCase(ROOT).contains("hypixel");
        } catch (Exception exception) {
            exception.printStackTrace();
            return true;
        }
    }

    public enum HypixelGameMode {
        SKYWARS(str -> str.equalsIgnoreCase("skywars")),
        BEDWARS(str -> str.equalsIgnoreCase("bed wars")),
        UHCCHAMPIONS(str -> str.equalsIgnoreCase("uhc champions")),
        ARCADEGAMES(str -> str.equalsIgnoreCase("arcade games")),
        DUELS(str -> str.equalsIgnoreCase("duels")),
        LOBBY(str -> false),
        OTHER(str -> false),
        NONE(str -> false);

        public Predicate<String> matcher;

        HypixelGameMode(Predicate<String> matcher) {
            this.matcher = matcher;
        }
    }
}
