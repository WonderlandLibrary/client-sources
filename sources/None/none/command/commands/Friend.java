package none.command.commands;

import none.Client;
import none.command.Command;
import none.friend.FriendManager;
import none.notifications.Notification;
import none.notifications.NotificationType;

public class Friend extends Command{

	@Override
	public String getAlias() {
		return "friend";
	}

	@Override
	public String getDescription() {
		return "ADD/Remove Friend List || Show you friend list";
	}

	@Override
	public String getSyntax() {
		return ".friend add/a friendname || .friend del/d friendname || .f list";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if (args == null || args.length < 2) {
			if (args[0].equalsIgnoreCase("list")) {
				FriendManager.load();
				FriendManager.save();
				if (FriendManager.friendsList.isEmpty()) {
					evc("You have no Friend. :(");
					Client.instance.notification.show(new Notification(NotificationType.INFO, "Friend", " You have no Friend. :(", 3));
					return;
				}
				for (none.friend.Friend f : FriendManager.friendsList) {
					evc("Friend:" + f.name);
				}
				Client.instance.notification.show(new Notification(NotificationType.INFO, "Friend", " This is Your Friend Name. :)", 3));
				return;
			}
			Client.instance.notification.show(new Notification(NotificationType.ERROR, "Friend", " .friend add/a friendname || .friend del/d friendname", 3));
            evc(getSyntax());
            return;
        }
        try {
            if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("a")) {
                if (FriendManager.isFriend(args[1])) {
                    evc(String.valueOf(args[1]) + " is already your friend.");
                    Client.instance.notification.show(new Notification(NotificationType.INFO, "Friend", " " + String.valueOf(args[1]) + " is already your friend.", 3));
                    return;
                }
                FriendManager.removeFriend(args[1]);
                FriendManager.addFriend(args[1], args.length == 3 ? args[2] : args[1]);
                Client.instance.notification.show(new Notification(NotificationType.INFO, "Friend", " Added " + args[1], 3));
                evc("Added " + args[1]);
                return;
            } else if (args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("d")) {
                if (FriendManager.isFriend(args[1])) {
                    FriendManager.removeFriend(args[1]);
                    evc("Removed friend: " + args[1]);
                    Client.instance.notification.show(new Notification(NotificationType.WARNING, "Friend", " Removed friend: " + args[1], 3));
                    return;
                } else {
                    evc(String.valueOf(args[1]) + " is not your friend.");
                    Client.instance.notification.show(new Notification(NotificationType.WARNING, "Friend", String.valueOf(args[1]) + " is not your friend.", 3));
                    return;
                }
            }
        } catch (NullPointerException e) {
        	Client.instance.notification.show(new Notification(NotificationType.ERROR, "Friend", " .friend add/a friendname || .friend del/d friendname", 3));
            evc(getSyntax());
        }
        Client.instance.notification.show(new Notification(NotificationType.ERROR, "Friend", " .friend add/a friendname || .friend del/d friendname", 3));
        evc(getSyntax());
        return;
    }

}
