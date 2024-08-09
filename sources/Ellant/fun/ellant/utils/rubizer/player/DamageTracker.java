package fun.ellant.utils.rubizer.player;

import java.util.HashMap;
import java.util.Map;

public class DamageTracker {
    private static final Map<String, Integer> playerDamageMap = new HashMap<>();

    public static void trackDamage(String playerName, int damage) {
        playerDamageMap.put(playerName, playerDamageMap.getOrDefault(playerName, 0) + damage);
    }

    public static int getDamage(String playerName) {
        return playerDamageMap.getOrDefault(playerName, 0);
    }
}
