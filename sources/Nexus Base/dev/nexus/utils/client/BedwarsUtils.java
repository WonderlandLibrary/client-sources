package dev.nexus.utils.client;

import net.minecraft.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BedwarsUtils {
    private static final String apiURL = "https://bwstats.shivam.pro/user/";

    public static String[] getPlayerStats(String playerName) {
        String stats = getPlayerStatList(playerName);

        String wins = extractWins(stats);
        String losses = extractLosses(stats);
        String gamesPlayed = getGamesPlayed(stats);
        String winLossRatio = extractWinLossRatio(stats);
        String kdr = extractKDR(stats);
        String fkdr = extractFKDR(stats);
        String bblr = extractBBLR(stats);
        String ws = extractWS(stats);
        return new String[]{wins, losses, gamesPlayed, winLossRatio, kdr, fkdr, bblr, ws};
    }

    private static String getPlayerStatList(String playerName) {
        try {
            URL url = new URL(apiURL + StringUtils.stripControlCodes(playerName));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    private static String extractWins(String stats) {
        return extractStat(stats, "Wins: ", ",");
    }

    private static String extractLosses(String stats) {
        return extractStat(stats, "Losses: ", ",");
    }

    private static String getGamesPlayed(String stats) {
        return extractStat(stats, "Games Played: ", ",");
    }

    private static String extractWinLossRatio(String stats) {
        return extractStat(stats, "Win/Loss Ratio: ", " /");
    }

    private static String extractKDR(String stats) {
        return extractStat(stats, "K/D Ratio (KDR): ", ",");
    }

    private static String extractFKDR(String stats) {
        return extractStat(stats, "Final K/D Ratio (FKDR): ", " /");
    }

    private static String extractBBLR(String stats) {
        return extractStat(stats, "Beds B/L Ratio (BBLR): ", " /");
    }

    private static String extractWS(String stats) {
        return extractStat(stats, "Winstreak: ", ",");
    }

    private static String extractStat(String stats, String startKey, String endKey) {
        int startIndex = stats.indexOf(startKey);
        if (startIndex == -1) {
            return "N/A";
        }
        startIndex += startKey.length();
        int endIndex = stats.indexOf(endKey, startIndex);
        if (endIndex == -1) {
            endIndex = stats.length();
        }
        if (stats.substring(startIndex, endIndex).trim().equals("Infinity")) {
            return "NA";
        }
        return stats.substring(startIndex, endIndex).trim();
    }
}
