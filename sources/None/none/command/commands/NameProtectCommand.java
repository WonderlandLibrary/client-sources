package none.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.Client;
import none.command.Command;
import none.event.events.EventChat;
import none.module.modules.render.NameProtect;
import none.notifications.Notification;
import none.notifications.NotificationType;
import none.utils.ChatUtil;

public class NameProtectCommand extends Command{

	@Override
	public String getAlias() {
		return "NameProtect";
	}

	@Override
	public String getDescription() {
		return "ChangeName";
	}

	@Override
	public String getSyntax() {
		return ".NameProtect [Name]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if (args[0].isEmpty()) {
			EventChat.addchatmessage(getSyntax());
			Client.instance.notification.show(new Notification(NotificationType.INFO, "NameProtect", " " + getSyntax(), 1));
		}else {
			NameProtect.setNameProtect(args[0]);
			EventChat.addchatmessage("Set Name to " + NameProtect.GetName());
			Client.instance.notification.show(new Notification(NotificationType.INFO, "NameProtect", " Set Name to " + NameProtect.GetName(), 1));
		}
	}

}
