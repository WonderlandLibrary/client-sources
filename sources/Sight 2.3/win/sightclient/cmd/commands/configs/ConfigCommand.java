package win.sightclient.cmd.commands.configs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.util.EnumChatFormatting;
import win.sightclient.Sight;
import win.sightclient.cmd.Command;
import win.sightclient.filemanager.files.ConfigFile;
import win.sightclient.notification.Notification;
import win.sightclient.utils.minecraft.ChatUtils;

public class ConfigCommand extends Command {

	public ConfigCommand() {
		super(new String[] {"Config", "c"});
	}

	@Override
	public void onCommand(String message) {
		String[] args = message.split(" ");
		if (args.length > 1 && args[1].equalsIgnoreCase("load") && args.length > 2) {
			ArrayList<File> files = Sight.instance.fileManager.getConfigs();
			for (File cr : files) {
				if (args[2].equalsIgnoreCase(cr.getName().substring(0, cr.getName().length() - 4))) {
					if (!cr.exists()) {
						try {
							cr.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					ConfigFile c = new ConfigFile(cr);
					c.load();
					Sight.instance.nm.send(new Notification("Configs", "Loaded the config with the name " + args[2] + "."));
					return;
				}
			}
			Sight.instance.nm.send(new Notification("Configs", "There was a error loading the config with the name " + args[2] + "."));
		} else if (args.length > 1 && args[1].equalsIgnoreCase("list")) {
			ArrayList<File> files = Sight.instance.fileManager.getConfigs();
			if (files.isEmpty()) {
				Sight.instance.nm.send(new Notification("Configs", "You have no configs."));
			} else {
				ChatUtils.sendMessage("Configs: ");
				for (File cr : files) {
					ChatUtils.sendMessage(cr.getName().substring(0, cr.getName().length() - 4));
				}
			}
		} else if (args.length > 1 && args[1].equalsIgnoreCase("create") && args.length > 2) {
			ConfigFile c = new ConfigFile(new File(Sight.instance.fileManager.configDir, args[2] + ".txt"));
			c.save();
			Sight.instance.nm.send(new Notification("Configs", "Created a config with the name " + args[2] + "."));
		} else if (args.length > 1 && args[1].equalsIgnoreCase("save") && args.length > 2) {
			ConfigFile c = new ConfigFile(new File(Sight.instance.fileManager.configDir, args[2] + ".txt"));
			c.save();
			Sight.instance.nm.send(new Notification("Configs", "Saved the config with the name " + args[2] + "."));
		} else if (args.length > 1 && args[1].equalsIgnoreCase("delete") && args.length > 2) {
			File f = new File (Sight.instance.fileManager.configDir, args[2] + ".txt");
			if (f.exists()) {
				if (f.delete()) {
					Sight.instance.nm.send(new Notification("Configs", "Deleted the config with the name " + args[2] + "."));
				} else {
					Sight.instance.nm.send(new Notification("Configs", "There was a error deleting the config with the name " + args[2] + "."));
				}
			} else {
				Sight.instance.nm.send(new Notification("Configs", "The config with the name " + args[2] + " does not exist."));
			}
		} else {
			ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Usage: ");
			ChatUtils.sendMessage(".c load <Config Name>");
			ChatUtils.sendMessage(".c list");
			ChatUtils.sendMessage(".c create <Config Name>");
			ChatUtils.sendMessage(".c save <Config Name>");
			ChatUtils.sendMessage(".c delete <Config Name>");
		}
	}
}
