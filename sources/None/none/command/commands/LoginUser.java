package none.command.commands;

import none.Client;
import none.command.Command;
import none.event.events.EventChat;
import none.notifications.Notification;
import none.notifications.NotificationType;

public class LoginUser extends Command{

	@Override
	public String getAlias() {
		return "Login";
	}

	@Override
	public String getDescription() {
		return "Login Client Command";
	}

	@Override
	public String getSyntax() {
		return ".Login [Username] [Password]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if (args[0].equalsIgnoreCase("MossTK")) {
			if (args[1].equalsIgnoreCase("dev")) {
				Client.ISDev = true;
				EventChat.addchatmessage(" Login as MossTK account.");
				Client.instance.notification.show(new Notification(NotificationType.INFO, "Login System", " Login as MossTK account.", 5));
				Client.instance.CheckSession();
			}else if (args[1].isEmpty()) {
				EventChat.addchatmessage(" Login failed.");
				Client.ISDev = false;
				Client.instance.notification.show(new Notification(NotificationType.WARNING, "Login System", " Login failed.", 5));
				Client.instance.CheckSession();
			}
		}else {
			EventChat.addchatmessage(" Login failed.");
			Client.ISDev = false;
			Client.instance.notification.show(new Notification(NotificationType.WARNING, "Login System", " Login failed.", 5));
			Client.instance.CheckSession();
		}
	}

}
