/**
 * 
 */
package cafe.kagu.kagu.commands.impl;

import java.io.File;

import cafe.kagu.kagu.commands.Command;
import cafe.kagu.kagu.commands.CommandAction;
import cafe.kagu.kagu.managers.FileManager;
import cafe.kagu.kagu.managers.KeybindManager;
import cafe.kagu.kagu.utils.ChatUtils;

/**
 * @author lavaflowglow
 *
 */
public class CommandKeybinds extends Command {
	
	private static ActionRequirement save = new ActionRequirement((CommandAction)args -> {
		if (args.length < 1)
			return true;
		String stitchedArgs = "";
		for (String str : args) {
			stitchedArgs += (stitchedArgs.isEmpty() ? "" : " ") + str;
		}
		File file = new File(FileManager.KEYBINDS_DIR, stitchedArgs + ".E621");
		ChatUtils.addChatMessage("Saved keybinds to \"" + file.getName() + "\"");
		KeybindManager.save(file);
		return true;
	}, "save");
	
	private static ActionRequirement load = new ActionRequirement((CommandAction)args -> {
		if (args.length < 1)
			return true;
		String stitchedArgs = "";
		for (String str : args) {
			stitchedArgs += (stitchedArgs.isEmpty() ? "" : " ") + str;
		}
		File file = new File(FileManager.KEYBINDS_DIR, stitchedArgs + ".E621");
		if (!file.exists()) {
			ChatUtils.addChatMessage("Could not find file \"" + file.getName() + "\"");
			return true;
		}
		KeybindManager.load(file);
		KeybindManager.save(FileManager.DEFAULT_KEYBINDS);
		ChatUtils.addChatMessage("Loaded keybinds from \"" + file.getName() + "\"");
		return true;
	}, "load");
	
	private static ActionRequirement list = new ActionRequirement((CommandAction)args -> {
		String[] fileNames = FileManager.KEYBINDS_DIR.list();
		ChatUtils.addChatMessage("Found " + fileNames.length + " keybind file" + (fileNames.length == 1 ? "" : "s"));
		for (String fileName : fileNames) {
			ChatUtils.addChatMessage("    " + fileName);
		}
		return true;
	}, "list");
	
	public CommandKeybinds() {
		super("keybinds", "<save/load/list> <name>", save, load, list);
	}
	
}
