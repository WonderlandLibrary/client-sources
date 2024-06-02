/**
 * 
 */
package cafe.kagu.kagu.commands.impl;

import org.lwjgl.input.Keyboard;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.commands.Command;
import cafe.kagu.kagu.commands.CommandAction;
import cafe.kagu.kagu.managers.FileManager;
import cafe.kagu.kagu.managers.KeybindManager;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.utils.ChatUtils;

/**
 * @author lavaflowglow
 *
 */
public class CommandBind extends Command {
	
	private static ActionRequirement add = new ActionRequirement((CommandAction)args -> {
		if (args.length < 2) {
			return false;
		}
		
		try {
			String moduleName = args[0];
			int keyCode = Keyboard.getKeyIndex(args[1].toUpperCase().replace(" ", "_"));
			for (Module module : Kagu.getModuleManager().getModules()) {
				if (module.getName().replace(" ", "").equalsIgnoreCase(moduleName)) {
					KeybindManager.addKeybind(module.getName(), keyCode);
					ChatUtils.addChatMessage("Binded " + module.getName() + " to " + Keyboard.getKeyName(keyCode));
					KeybindManager.save(FileManager.DEFAULT_KEYBINDS);
					return true;
				}
			}
			ChatUtils.addChatMessage("Could not find module \"" + moduleName + "\"");
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}, "add");
	
	private static ActionRequirement remove = new ActionRequirement((CommandAction)args -> {
		if (args.length < 1) {
			return false;
		}
		
		try {
			String moduleName = args[0];
			for (Module module : Kagu.getModuleManager().getModules()) {
				if (module.getName().replace(" ", "").equalsIgnoreCase(moduleName)) {
					KeybindManager.removeKeybind(module.getName());
					ChatUtils.addChatMessage("Cleared binds for " + module.getName());
					KeybindManager.save(FileManager.DEFAULT_KEYBINDS);
					return true;
				}
			}
			ChatUtils.addChatMessage("Could not find module \"" + moduleName + "\"");
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}, "remove");
	
	public CommandBind() {
		super("bind", "<add/remove> <module name> <keybind>", add, remove);
	}

}
