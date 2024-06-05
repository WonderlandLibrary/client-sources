package xyz.vergoclient.commands.impl;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import xyz.vergoclient.commands.CommandManager;
import xyz.vergoclient.commands.OnCommandInterface;
import xyz.vergoclient.files.FileManager;
import xyz.vergoclient.keybinds.Keybind;
import xyz.vergoclient.keybinds.KeyboardManager;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.ModuleManager;
import xyz.vergoclient.util.main.ChatUtils;

public class CommandBind implements OnCommandInterface {

	@Override
	public void onCommand(String... args) {
		
		// Check to make sure that the user put the correct amount of args
		if (args.length < 3) {
			argsWarn();
			return;
		}
		
		// Get names of module and key from the args
		String moduleName = args[1];
		String keyName = args[2];
		
		// Get module
		Module module = null;
		for (Module m : ModuleManager.modules)
			if (m.getName().toLowerCase().replaceAll(" ", "").equals(moduleName.toLowerCase())) {
				module = m;
				break;
			}
		
		// Make sure that it found the module
		if (module == null) {
			ChatUtils.addChatMessage("Could not find module \"" + moduleName + "\"");
			return;
		}
		
		// Get key
		int keycode = Keyboard.getKeyIndex(keyName.toUpperCase());
		
		// Make sure that it found the key
		if (keycode == Keyboard.KEY_NONE && !keyName.equalsIgnoreCase("none")) {
			ChatUtils.addChatMessage("Could not find the key \"" + keyName + "\"");
			return;
		}
		
		// Create keybind
		Keybind bind = new Keybind(keycode, module.getName());
		
		// Make sure it doesn't already exist
		if (keycode != Keyboard.KEY_NONE)
			for (Keybind k : KeyboardManager.keybinds.keybinds)
				if (k.equals(bind)) {
					ChatUtils.addChatMessage("This keybind already exists");
					return;
				}
		
		// Removes keybinds
		if (keycode == Keyboard.KEY_NONE) {
			
			ArrayList<Keybind> keybindsToRemove = new ArrayList<>();
			
			for (Keybind k : KeyboardManager.keybinds.keybinds)
				if (k.modName.equals(module.getName()))
					keybindsToRemove.add(k);
			
			KeyboardManager.keybinds.keybinds.removeAll(keybindsToRemove);
			keybindsToRemove.forEach(k -> {
				ChatUtils.addChatMessage("Successfully unbound " + k.modName + " from " + Keyboard.getKeyName(k.keybind));
			});
			
		}
		// Adds keybinds
		else {
			KeyboardManager.keybinds.keybinds.add(bind);
			ChatUtils.addChatMessage("Successfully bound " + module.getName() + " to " + Keyboard.getKeyName(keycode));
		}
		
		// Save the keybinds
		FileManager.writeToFile(FileManager.defaultKeybindsFile, KeyboardManager.keybinds);
		
	}
	
	private void argsWarn() {
		ChatUtils.addChatMessage("Please use " + getUsage());
	}
	
	@Override
	public String getName() {
		return "bind";
	}

	@Override
	public String getUsage() {
		return CommandManager.prefix + getName() + " <module> <key/none>";
	}

	@Override
	public String getDescription() {
		return "Allows you to setup keybinds";
	}

}
