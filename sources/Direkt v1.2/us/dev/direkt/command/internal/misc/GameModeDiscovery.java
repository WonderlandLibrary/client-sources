package us.dev.direkt.command.internal.misc;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Foundry
 */
public class GameModeDiscovery extends Command {
    public GameModeDiscovery() {
        super(Direkt.getInstance().getCommandManager(), "gamemodediscovery", "gamemodedisc", "gmdisc");
    }

    @Executes
    public String run(Optional<GameType> gameType) {
        if (!Wrapper.getSendQueue().getPlayerInfoMap().isEmpty()) {
        	GameType targetType; int[] playerCount = new int[1];
            if (gameType.isPresent()) {
                final String lookupText = "Players with the \247a" + (targetType = gameType.get()).getName() + "\2477 gamemode: " + Wrapper.getSendQueue().getPlayerInfoMap().stream()
                        .filter(networkPlayerInfo -> networkPlayerInfo.getGameType() == targetType)
                        .peek(x -> playerCount[0]++)
                        .map(networkPlayerInfo -> "\2477" + networkPlayerInfo.getGameProfile().getName() + " \2477(\247a" + networkPlayerInfo.getGameType().getName() + "\2477)")
                        .collect(Collectors.joining(", "));

                return playerCount[0] == 0 ? "No players with the \247a" + targetType + "\2477 gamemode found." : lookupText;
            } else {
                final String lookupText = "Players not in " + (targetType = Wrapper.getPlayerController().getCurrentGameType()).getName() + ": " + Wrapper.getSendQueue().getPlayerInfoMap().stream()
                        .filter(networkPlayerInfo -> networkPlayerInfo.getGameType() != targetType)
                        .peek(x -> playerCount[0]++)
                        .map(networkPlayerInfo -> "\2477" + networkPlayerInfo.getGameProfile().getName() + " \2477(\247a" + networkPlayerInfo.getGameType().getName() + "\2477)")
                        .collect(Collectors.joining(", "));

                return playerCount[0] == 0 ? "No players with abnormal gamemodes found." : lookupText;
            }
        } else {
            return "No players with abnormal gamemodes found.";
        }
    }

    @Executes("player")
    public String run(String playerName) {
        final NetworkPlayerInfo playerInfoLookup;
        if ((playerInfoLookup = Wrapper.getSendQueue().getPlayerInfo(playerName)) != null) {
            return "Gamemode of " + playerInfoLookup.getGameProfile().getName() + " is: \247a" + playerInfoLookup.getGameType().getName() + "\2477.";
        } else {
            return String.format("player \"%s\" does not exist", playerName);
        }
    }
}
