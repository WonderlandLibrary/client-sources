package win.sightclient.cmd.commands;

import org.lwjgl.input.Keyboard;

import win.sightclient.Sight;
import win.sightclient.cmd.Command;
import win.sightclient.module.Module;
import win.sightclient.notification.Notification;
import win.sightclient.utils.minecraft.ChatUtils;


public class BindCommand extends Command {

	public BindCommand() {
		super(new String[] {"bind"});
	}

	@Override
	public void onCommand(String message) {
		String[] args = message.split(" ");
		if (args.length > 1 && args[1].equalsIgnoreCase("del")) {
			Module toDel = Sight.instance.mm.getModuleByName(args[2]);
			if (toDel == null) {
				Sight.instance.nm.send(new Notification("Keybinds", "The module '" + args[2] + "' doesn't exist."));
			} else {
				toDel.setKey(Keyboard.KEY_NONE);
				Sight.instance.nm.send(new Notification("Keybinds", "Deleted the bind of '" + args[2] + "'."));
			}
		} else if (args.length > 1 && args[1].equalsIgnoreCase("clear")) {
			for (Module m : Sight.instance.mm.getModules()) {
				m.setKey(Keyboard.KEY_NONE);
			}
			Sight.instance.nm.send(new Notification("Keybinds", "Removed all keybinds."));
		} else if (args.length > 2) {
			Module toDel = Sight.instance.mm.getModuleByName(args[1]);
			if (toDel == null) {
				Sight.instance.nm.send(new Notification("Keybinds", "The module '" + args[1] + "' doesn't exist."));
			} else {
				String keyName = args[2].toUpperCase();
				if (keyName.equalsIgnoreCase("rightshift")) {
					keyName = "RSHIFT";
				}
				if (keyName.equalsIgnoreCase("leftshift")) {
					keyName = "LSHIFT";
				}
				int key = Keyboard.getKeyIndex(keyName);
				if (key == Keyboard.KEY_NONE) {
					Sight.instance.nm.send(new Notification("Keybinds", "The key '" + args[2] + "' doesn't exist."));
				} else {
					toDel.setKey(key);
					Sight.instance.nm.send(new Notification("Keybinds", "Bound '" + args[1] + "' to " + Keyboard.getKeyName(key) + "."));
				}
			}
		} else {
			ChatUtils.sendMessage("Usage: ");
			ChatUtils.sendMessage(".bind del <ModuleName>");
			ChatUtils.sendMessage(".bind clear");
			ChatUtils.sendMessage(".bind <ModuleName> <Key>");
		}
	}
}
