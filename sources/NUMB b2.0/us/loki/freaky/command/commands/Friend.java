package us.loki.freaky.command.commands;

import java.io.PrintStream;

import us.loki.freaky.command.Command;
import us.loki.legit.utils.FriendUtil;

public class Friend
extends Command {
    /*public Friend() {
        super("friend");
        this.desc("Adds player to friends");
        this.use("friend [add/remove] [name] [OPTIONAL: alias]");
    }*/
	@Override
	public String getAlias() {
		return "friend";
	}

	@Override
	public String getDescription() {
		return "Adds player to friends";
	}

	@Override
	public String getSyntax() {
		return "@friend [add] [name] | @friend [remove] [name]";
	}

    @Override
    public void onCommand(String command, String[] args) throws Exception {
                if (args[0].equalsIgnoreCase("add")) {
                        if (!FriendUtil.isAFriend(args[1])) {
                            FriendUtil.addWithAlias(args[1], args[1]);
                            this.printChat("Added " + args[1] + " as a friend.");
                        }
                    } else if (!FriendUtil.isAFriend(args[1])) {
                        FriendUtil.addWithoutAlias(args[1]);
                        this.printChat("Added " + args[1] + " as a friend.");
                    }
                    else if (args[0].equalsIgnoreCase("remove") && FriendUtil.isAFriend(args[1])) {
                    FriendUtil.removeFriend(args[1]);
                    this.printChat("Removed " + args[1] + " from friends.");
                } else {
                this.printChat("Not enough arguments. Required arguments: 3");
            }
        }
}
