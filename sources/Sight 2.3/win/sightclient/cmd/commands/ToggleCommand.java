package win.sightclient.cmd.commands;

import win.sightclient.Sight;
import win.sightclient.cmd.Command;
import win.sightclient.module.Module;
import win.sightclient.notification.Notification;

public class ToggleCommand extends Command {

	public ToggleCommand() {
		super(new String[] {"toggle", "t"});
	}

	@Override
	public void onCommand(String message) {
		String[] args = message.split(" ");
		if (args.length == 1) {
			Sight.instance.nm.send(new Notification("Toggle", "Usage: .toggle <Module Name>."));
			return;
		}
		
		Module toToggle = Sight.instance.mm.getModuleByName(args[1]);
		if (toToggle == null) {
			Sight.instance.nm.send(new Notification("Toggle", "The module '" + args[1] + "' doesn't exist."));
		} else {
			toToggle.toggle();
			Sight.instance.nm.send(new Notification("Toggle", "Toggled " + args[1] + "."));
		}
	}
}
