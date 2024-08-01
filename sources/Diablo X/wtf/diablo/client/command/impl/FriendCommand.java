package wtf.diablo.client.command.impl;

import wtf.diablo.client.command.api.AbstractCommand;
import wtf.diablo.client.command.api.data.Command;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;

@Command(name = "friend", description = "Friend command", aliases = {"f"}, usage = "friend <add|remove|list> <name>")
public final class FriendCommand extends AbstractCommand {
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            return;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                Diablo.getInstance().getFriendRepository().addFriend(args[1]);
                ChatUtil.addChatMessage("Added " + args[1] + " as a friend.");
                break;
            case "remove":
                Diablo.getInstance().getFriendRepository().removeFriend(args[1]);
                ChatUtil.addChatMessage("Removed " + args[1] + " as a friend.");
                break;
            case "list":
                Diablo.getInstance().getFriendRepository().getFriends().forEach(friend -> ChatUtil.addChatMessage(friend.getName()));
                break;
        }
    }
}
