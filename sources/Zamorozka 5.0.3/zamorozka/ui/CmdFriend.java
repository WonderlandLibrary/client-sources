package zamorozka.ui;

import com.mojang.realmsclient.gui.ChatFormatting;
import zamorozka.main.indexer;

public class CmdFriend extends Command {
    public CmdFriend() {
        super("friend");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            if (args[0].equalsIgnoreCase("add")) {
                String name = args[1];
                String alias = args[2];
                if (!indexer.getFriends().isFriend(name)) {
                    ChatUtils.printChatprefix("Player " + ChatFormatting.GREEN + name + ChatFormatting.WHITE + " was added in your friend list!");
                    indexer.getFriends().addFriend(name, alias);
                    FileManager.saveFriends();
                } else {
                    ChatUtils.printChatprefix("Player " + ChatFormatting.GREEN + name + ChatFormatting.WHITE + " was removed from your friend list!");
                }
            }
            if (args[0].equalsIgnoreCase("del")) {
                String name = args[1];
                if (indexer.getFriends().isFriend(name)) {
                    indexer.getFriends().removeFriend(name);
                    ChatUtils.printChatprefix("Player " + ChatFormatting.GREEN + name + ChatFormatting.WHITE + " was removed from your friend list!");
                    FileManager.saveFriends();
                } else {
                    ChatUtils.printChatprefix("Player " + ChatFormatting.GREEN + name + ChatFormatting.WHITE + " was added in your friend list!");
                }
            }
            if (args[0].equalsIgnoreCase("list")) {
                for (Friend friend : indexer.getFriends().friendsList) {
                    ChatUtils.printChatprefix(friend.getAlias());
                }
                ChatUtils.printChatprefix(indexer.getFriends().friendsList.size() + " friend(s).");
            }
            if (args[0].equalsIgnoreCase("clear")) {
                try {
                    indexer.getFriends().friendsList.clear();
                    FileManager.saveFriends();
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            ChatUtils.printChatprefix("Usage: " + getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Adds and removes friends.";
    }

    @Override
    public String getSyntax() {
        return "friend add <name> <alias>, friend del <name>";
    }
}
