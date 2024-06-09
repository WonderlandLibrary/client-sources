package ooo.cpacket.ruby.command.commands;

import ooo.cpacket.ruby.Ruby;
import ooo.cpacket.ruby.command.Command;
import ooo.cpacket.ruby.manager.FriendManager;

public class Friend extends Command {

	public Friend() {
		super("friend", "af");
	}

	@Override
	public void run(String[] args) {
		try {
			if (!FriendManager.isFriend(args[1])) {
				if (args.length >= 3) {
					FriendManager.friends.put(args[1], args[2]);
					Ruby.getRuby.chat("Friend " + args[1] + " added as " + args[2] + ".");
				}
				else {
					FriendManager.friends.put(args[1], args[1]);
					Ruby.getRuby.chat("Friend " + args[1] + " added.");
				}
			}
			else {
				FriendManager.friends.remove(args[1]);
				Ruby.getRuby.chat("Friend " + args[1] + " removed.");
			}
		}catch (Exception e){
			Ruby.getRuby.chat("Error (Invalid arguments?)");
		}
	}

}
