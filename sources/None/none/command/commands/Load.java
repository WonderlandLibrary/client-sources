package none.command.commands;

import none.Client;
import none.command.Command;
import none.notifications.Notification;
import none.notifications.NotificationType;

public class Load extends Command{
	@Override
	public String getAlias() {
		return "Load";
	}

	@Override
	public String getDescription() {
		return "Load Settings.";
	}

	@Override
	public String getSyntax() {
		return ".Load";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		Client.instance.notification.show(new Notification(NotificationType.WARNING, "FileManager", ":Load File!", 6));
		try {
			Client.instance.fileManager.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
