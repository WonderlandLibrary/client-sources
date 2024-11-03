package dev.star.utils.server;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import dev.star.utils.Utils;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServerUtils implements Utils {

    public static ServerData lastServer;
    private static boolean redirecting;

    public static boolean serverCheck(String ip) {
        if (mc.getCurrentServerData() == null)
            return false;

        ip = ip.toLowerCase();
        String server = mc.isSingleplayer() ? "" : mc.getCurrentServerData().serverIP.toLowerCase();
        return server.endsWith("." + ip) || server.equals(ip);
    }

    public static boolean isGeniuneHypixel() {
        return isOnHypixel() && !redirecting;
    }

    public static boolean isOnHypixel() {
        if (mc.isSingleplayer() || mc.getCurrentServerData() == null || mc.getCurrentServerData().serverIP == null)
            return false;
        String ip = mc.getCurrentServerData().serverIP.toLowerCase();
        if (ip.contains("hypixel")) {
            if (mc.thePlayer == null) return true;
            String brand = mc.thePlayer.getClientBrand();
            return brand != null && brand.startsWith("Hypixel BungeeCord");
        }
        return false;
    }

    public static String stripString(final String s) {
        final char[] nonValidatedString = StringUtils.stripControlCodes(s).toCharArray();
        final StringBuilder validated = new StringBuilder();
        for (final char c : nonValidatedString) {
            if (c < '' && c > '') {
                validated.append(c);
            }
        }
        return validated.toString();
    }

    public static List<String> getSidebarLines() {
        final List<String> lines = new ArrayList<>();
        if (mc.theWorld == null) {
            return lines;
        }
        final Scoreboard scoreboard = mc.theWorld.getScoreboard();
        if (scoreboard == null) {
            return lines;
        }
        final ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
        if (objective == null) {
            return lines;
        }
        Collection<Score> scores = scoreboard.getSortedScores(objective);
        final List<Score> list = new ArrayList<>();
        for (final Score input : scores) {
            if (input != null && input.getPlayerName() != null && !input.getPlayerName().startsWith("#")) {
                list.add(input);
            }
        }
        if (list.size() > 15) {
            scores = new ArrayList<>(Lists.newArrayList(Iterables.skip(list, list.size() - 15)));
        } else {
            scores = list;
        }
        int index = 0;
        for (final Score score : scores) {
            ++index;
            final ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
            lines.add(ScorePlayerTeam.formatPlayerName(team, score.getPlayerName()));
            if (index == scores.size()) {
                lines.add(objective.getDisplayName());
            }
        }
        return lines;
    }

    public static int getBedwarsStatus() {
        if (mc.thePlayer == null) {
            return -1;
        }
        final Scoreboard scoreboard = mc.theWorld.getScoreboard();
        if (scoreboard == null) {
            return -1;
        }
        final ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
        if (objective == null || !stripString(objective.getDisplayName()).contains("BED WARS")) {
            return -1;
        }
        for (String line : getSidebarLines()) {
            line = stripString(line);
            String[] parts = line.split("  ");
            if (parts.length > 1) {
                if (parts[1].startsWith("L")) {
                    return 0;
                }
            } else if (line.equals("Waiting...") || line.startsWith("Starting in")) {
                return 1;
            } else if (line.startsWith("R Red:") || line.startsWith("B Blue:")) {
                return 2;
            }
        }
        return -1;
    }


    @SuppressWarnings("ForLoopReplaceableByForEach")
    public static boolean isInLobby() {
        if (mc.theWorld == null) return true;
        List<Entity> entities = mc.theWorld.getLoadedEntityList();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity != null && entity.getName().equals("§e§lCLICK TO PLAY")) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("resource")
    public static boolean isHostsRedirectingHypixel() throws IOException {
        Path value = Paths.get(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts");
        return !Files.notExists(value) && Files.lines(value).anyMatch(s -> s.toLowerCase().contains("hypixel"));
    }

    public static void updateRedirecting() throws IOException {
        redirecting = isHostsRedirectingHypixel();
    }

    public static boolean isOnSameTeam(EntityLivingBase ent) {
        if (mc.thePlayer != null) {
            String displayName = mc.thePlayer.getDisplayName().getUnformattedText();
            if (displayName.contains("§")) {
                int start = displayName.indexOf("§");
                String substring = displayName.substring(start, start + 2);
                return ent != null && ent.getDisplayName().getFormattedText().contains(substring);
            }
        }
        return false;
    }

}
