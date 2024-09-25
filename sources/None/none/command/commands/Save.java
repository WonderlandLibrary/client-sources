package none.command.commands;

import none.Client;
import none.command.Command;
import none.notifications.Notification;
import none.notifications.NotificationType;

public class Save extends Command{
	@Override
	public String getAlias() {
		return "save";
	}

	@Override
	public String getDescription() {
		return "Save Settings.";
	}

	@Override
	public String getSyntax() {
		return ".Save";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		Client.instance.notification.show(new Notification(NotificationType.WARNING, "FileManager", ":Save File!", 6));
		try {
			Client.instance.fileManager.save();
			Client.instance.fileManager.saveTargeter();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}