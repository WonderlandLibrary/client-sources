package dev.stephen.nexus.utils.bedwars;

import dev.stephen.nexus.utils.render.ColorUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BedwarsUtils {

    public static StatsClass getPlayerStats(String playerName) {
        String confusedStats = getPlayerStatList(playerName);

        String winstreak = extractValue(confusedStats, "'winstreak': ", ",");
        String winLoseR = extractValue(confusedStats, "'win_loss_bedwars': '", "'");
        String killDeathR = extractValue(confusedStats, "'kill_death_bedwars': '", "'");
        String fkdr = extractValue(confusedStats, "'final_kill_death_bedwars': '", "'");
        String totalWins = extractValue(confusedStats, "'wins_bedwars': ", ",");
        String lossesBedwars = extractValue(confusedStats, "'losses_bedwars': ", ",");
        return new StatsClass(playerName, winstreak, winLoseR, killDeathR, fkdr, totalWins, lossesBedwars);
    }

    private static String extractValue(String source, String prefix, String suffix) {
        Pattern pattern = Pattern.compile(Pattern.quote(prefix) + "(.*?)" + Pattern.quote(suffix));
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public static String getPlayerStatList(String playerName) {
        try {
            URL url = new URL("https://www.shmeado.club/player/stats/" + ColorUtils.stripControlCodes(playerName) + "/bedwars/table/");
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
}