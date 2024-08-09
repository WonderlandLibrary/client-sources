package dev.darkmoon.client.manager.staff;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
public class PlayerTimeTracker {
        private Map<String, Instant> playerLoginTimes;

        public PlayerTimeTracker() {
            playerLoginTimes = new HashMap<>();
        }

        public void playerLoggedIn(String playerName) {
            playerLoginTimes.put(playerName, Instant.now());
        }

        public String getPlayerTime(String playerName) {
            if (!playerLoginTimes.containsKey(playerName)) {
                return "Player not found"; // Возможно, стоит бросать исключение, в зависимости от вашей логики
            }

            Instant loginTime = playerLoginTimes.get(playerName);
            Duration duration = Duration.between(loginTime, Instant.now());

            long hours = duration.toHours();
            long minutes = duration.minusHours(hours).toMinutes();
            long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

            if (hours > 0) {
                return String.format("%dh %dm", hours, minutes);
            } else if (minutes > 0) {
                return String.format("%dm %ds", minutes, seconds);
            } else {
                return String.format("%ds", seconds);
            }
        }
    }