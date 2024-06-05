package net.shoreline.client.api.command.arg.arguments;

import net.minecraft.entity.player.PlayerEntity;
import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.util.Globals;
import net.shoreline.client.util.chat.ChatUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author linus
 * @since 1.0
 */
public class PlayerArgument extends Argument<PlayerEntity> implements Globals {
    /**
     *
     */
    public PlayerArgument(String name, String desc) {
        super(name, desc);
    }

    /**
     * @see Command#onCommandInput()
     */
    @Override
    public PlayerEntity getValue() {
        if (mc.world != null) {
            for (PlayerEntity player : mc.world.getPlayers()) {
                if (player.getName().getString().toLowerCase().equalsIgnoreCase(getLiteral())) {
                    return player;
                }
            }
        }
        ChatUtil.error("Failed to find player!");
        return null;
    }

    /**
     * @return
     */
    @Override
    public Collection<String> getSuggestions() {
        Collection<String> playerNames = new ArrayList<>();
        if (mc.player != null && mc.getServer() != null) {
            for (String player : mc.getServer().getPlayerNames()) {
                if (player.equalsIgnoreCase(mc.player.getName().getString())) {
                    continue;
                }
                playerNames.add(player);
            }
        }
        return playerNames;
    }
}
