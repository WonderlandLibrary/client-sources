package koks.command.impl;

import koks.command.Command;
import koks.utilities.FriendManager;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 17:37
 */
public class Friend extends Command {

    FriendManager friendManager = new FriendManager();

    public Friend() {
        super("friend", "friends");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 3) {
            sendError("§7(§4BAD USAGE§7)", "§c§l.friend §7<§c§lADD/REMOVE§7> §7<§c§lNAME§7> §7<§c§lALIAS§7>", true);
            return;
        }

        if (args[0].equalsIgnoreCase("add")) {
            friendManager.addFriend(args[1], args[2]);
            sendmsg("Name: " + args[1], true);
            sendmsg("Alias: " + args[2], true);
        } else if (args[0].equalsIgnoreCase("remove")) {
            if (friendManager.isFriend(args[1]))
                friendManager.removeFriend(args[1]);
        } else {
            sendError("§7(§4BAD USAGE§7)", "§c§l.friend §7<§c§lADD/REMOVE§7> §7<§c§lNAME§7> §7<§c§lALIAS§7>", true);
        }
    }

}