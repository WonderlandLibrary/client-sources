package us.dev.direkt.util.server;

/**
 * Created by Foundry on 11/13/2015.
 */
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import us.dev.direkt.Wrapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ServerUtils {

    /**
     * Gets the list of all currently connected players. Necessary to use this instead of Minecraft's default method.
     * @return the list of all players currently connected to the server
     */
    public static List<String> getPlayerList() {
        return Wrapper.getMinecraft().getConnection() != null
                ? Wrapper.getMinecraft().getConnection().getPlayerInfoMap().stream()
                    .map(ServerUtils::resolveNameUnformatted)
                    .collect(Collectors.toList())
                : Collections.emptyList();
    }

    /**
     * Resolves a NetworkPlayerInfo object to the player's formatted name
     * @param playerInfo the NetworkPlayerInfo object to resolve a name from
     * @return the formatted name of the passed player
     */
    public static String resolveNameFormatted(NetworkPlayerInfo playerInfo) {
        return (Wrapper.getWorld() != null && playerInfo.getDisplayName() != null) ? playerInfo.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(playerInfo.getPlayerTeam(), playerInfo.getGameProfile().getName());
    }

    /**
     * Resolves a NetworkPlayerInfo object to the player's unformatted name
     * @param playerInfo the NetworkPlayerInfo object to resolve a name from
     * @return the unformatted name of the passed player
     */
    public static String resolveNameUnformatted(NetworkPlayerInfo playerInfo) {
        return playerInfo.getGameProfile().getName();
    }

}
