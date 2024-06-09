/**
 * 
 */
package cafe.kagu.kagu.commands.impl;

import java.io.File;

import cafe.kagu.kagu.commands.Command;
import cafe.kagu.kagu.commands.CommandAction;
import cafe.kagu.kagu.managers.ConfigManager;
import cafe.kagu.kagu.managers.FileManager;
import cafe.kagu.kagu.managers.KeybindManager;
import cafe.kagu.kagu.utils.ChatUtils;

/**
 * @author lavaflowglow
 *
 */
public class CommandConfig extends Command {
	
	private static ActionRequirement save = new ActionRequirement((CommandAction)args -> {
		if (args.length < 1)
			return true;
		String stitchedArgs = "";
		for (String str : args) {
			stitchedArgs += (stitchedArgs.isEmpty() ? "" : " ") + str;
		}
		File file = new File(FileManager.CONFIGS_DIR, stitchedArgs + ".kagu");
		ChatUtils.addChatMessage("Saved config to \"" + file.getName() + "\"");
		ConfigManager.save(file);
		return true;
	}, "save");
	
	private static ActionRequirement load = new ActionRequirement((CommandAction)args -> {
		if (args.length < 1)
			return true;
		String stitchedArgs = "";
		for (String str : args) {
			stitchedArgs += (stitchedArgs.isEmpty() ? "" : " ") + str;
		}
		File file = new File(FileManager.CONFIGS_DIR, stitchedArgs + ".kagu");
		if (!file.exists()) {
			ChatUtils.addChatMessage("Could not find file \"" + file.getName() + "\"");
			return true;
		}
		ConfigManager.load(file);
		ConfigManager.save(FileManager.DEFAULT_CONFIG);
		ChatUtils.addChatMessage("Loaded config from \"" + file.getName() + "\"");
		return true;
	}, "load");
	
	private static ActionRequirement list = new ActionRequirement((CommandAction)args -> {
		String[] fileNames = FileManager.CONFIGS_DIR.list();
		ChatUtils.addChatMessage("Found " + fileNames.length + " config file" + (fileNames.length == 1 ? "" : "s"));
		for (String fileName : fileNames) {
			ChatUtils.addChatMessage("    " + fileName);
		}
		return true;
	}, "list");
	
	public CommandConfig() {
		super("config", "<save/load/list> <name>", save, load, list);
	}
	
}
