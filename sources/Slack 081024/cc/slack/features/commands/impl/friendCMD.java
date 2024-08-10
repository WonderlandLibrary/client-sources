package cc.slack.features.commands.impl;

import cc.slack.start.Slack;
import cc.slack.features.commands.api.CMD;
import cc.slack.features.commands.api.CMDInfo;
import cc.slack.features.friends.FriendManager;
import cc.slack.utils.other.PrintUtil;

import java.util.ArrayList;

@CMDInfo(
        name = "friend",
        alias = "f",
        description = "Add or remove a friend."
)
public class friendCMD extends CMD {

    @Override
    public void onCommand(String[] args, String cmd) {
        if (args.length == 0) {
            PrintUtil.message("§fInvalid use of arguments. §cFormat: .friend <add/remove/list> <name>");
            return;
        }

        String action = args[0].toLowerCase();
        FriendManager friendManager = Slack.getInstance().getFriendManager();

        try {
            if (action.equals("add")) {
                if (args.length != 2) {
                    PrintUtil.message("§fInvalid use of arguments. §cFormat: .friend add <name>");
                    return;
                }
                String friend = args[1].replace('_', ' ');
                if (friendManager.getFriends().contains(friend)) {
                    PrintUtil.message("§c" + friend + " §fis already your friend.");
                    return;
                }
                friendManager.addFriend(friend);
                PrintUtil.message("§fSuccessfully added §a" + friend + " §fas a friend.");
            } else if (action.equals("remove")) {
                if (args.length != 2) {
                    PrintUtil.message("§fInvalid use of arguments. §cFormat: .friend remove <name>");
                    return;
                }
                String friend = args[1].replace('_', ' ');
                friendManager.removeFriend(friend);
                PrintUtil.message("§fSuccessfully removed §c" + friend + " §ffrom friends.");
            } else if (action.equals("list")) {
                if (args.length != 1) {
                    PrintUtil.message("§fInvalid use of arguments. §cFormat: .friend list");
                    return;
                }
                listFriends(friendManager);
            } else {
                PrintUtil.message("§fInvalid action. §cUse 'add', 'remove', or 'list'§f.");
            }
        } catch (Exception e) {
            PrintUtil.message("§4An error occurred while processing the command§f.");
        }
    }

    private void listFriends(FriendManager friendManager) {
        ArrayList<String> friends = friendManager.getFriends();
        if (friends.isEmpty()) {
            PrintUtil.message("§cYou have no friends added.");
        } else {
            PrintUtil.message("§fShowing Friends list:");
            for (String friend : friends) {
                PrintUtil.msgNoPrefix("§c> §a" + friend);
            }
        }
    }
}
