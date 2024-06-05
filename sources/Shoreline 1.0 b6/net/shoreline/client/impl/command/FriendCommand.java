package net.shoreline.client.impl.command;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.OptionalArgument;
import net.shoreline.client.api.command.arg.arguments.BooleanArgument;
import net.shoreline.client.api.command.arg.arguments.PlayerArgument;
import net.shoreline.client.api.command.arg.arguments.StringArgument;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.chat.ChatUtil;

import java.util.Arrays;

/**
 * @author linus
 * @since 1.0
 */
public class FriendCommand extends Command {
    //
    @OptionalArgument
    Argument<String> actionArgument = new StringArgument("Add/Remove", "Whether to add or remove the friend", "add", "remove", "del");
    Argument<PlayerEntity> playerArgument = new PlayerArgument("Player", "The player to add/remove friend");
    // Optionally notifies the player you are friending in chat
    @OptionalArgument
    Argument<Boolean> notifyArgument = new BooleanArgument("Notify", "Notifies the friended player in the chat");

    /**
     *
     */
    public FriendCommand() {
        super("Friend", "Adds/Removes a friend from the player list");
    }

    /**
     *
     */
    @Override
    public void onCommandInput() {
        final PlayerEntity player = playerArgument.getValue();
        if (player != null) {
            final String action = actionArgument.getValue();
            final Boolean notify = notifyArgument.getValue();
            if (action != null) {
                if (action.equalsIgnoreCase("add")) {
                    ChatUtil.clientSendMessage("Added friend with name " +
                            Formatting.AQUA + player.getName().getString() + Formatting.RESET + "!");
                    Managers.SOCIAL.addFriend(player.getUuid());
                    if (notify != null && notify) {
                        ChatUtil.serverSendMessage(player, "You were friended by %s!",
                                mc.player.getName().getString());
                    }
                } else if (action.equalsIgnoreCase("remove")
                        || action.equalsIgnoreCase("del")) {
                    ChatUtil.clientSendMessage("Removed friend with name " +
                            Formatting.DARK_BLUE + player.getName().getString() + Formatting.RESET + "!");
                    Managers.SOCIAL.remove(player.getUuid());
                }
            } else {
                ChatUtil.clientSendMessage("Added friend with name " +
                        Formatting.DARK_BLUE + player.getName().getString() + Formatting.RESET + "!");
                Managers.SOCIAL.addFriend(player.getUuid());
                if (notify != null && notify) {
                    ChatUtil.serverSendMessage(player, "You were friended by %s!",
                            mc.player.getName().getString());
                }
            }
        }
    }
}
