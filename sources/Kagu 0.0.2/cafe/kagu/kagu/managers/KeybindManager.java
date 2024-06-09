/**
 * 
 */
package cafe.kagu.kagu.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventBus;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.impl.EventKeyUpdate;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.visual.ModClickGui;

/**
 * @author lavaflowglow
 *
 */
public class KeybindManager {
	
	/**
	 * Private because I don't want anything else creating an instance of this class
	 */
	private KeybindManager() {}
	
	private static Map<String, ArrayList<Integer>> keybinds = new HashMap<>();
	
	/**
	 * Called when the client starts
	 */
	public static void start() {
		
		// Load the default keybinds if they exist, otherwise add a single clickgui keybind and save the file to defaults
		if (FileManager.DEFAULT_KEYBINDS.exists()) {
			load(FileManager.DEFAULT_KEYBINDS);
		}else {
			addKeybind(Kagu.getModuleManager().getModule(ModClickGui.class).getName(), Keyboard.KEY_RSHIFT);
		}
		
		// Subscribe to the key event so we can use the keybinds
		Kagu.getEventBus().subscribe(new KeybindManager());
		
	}
	
	/**
	 * Adds a keybind
	 * @param module The name of the module
	 * @param keyCode The keycode to bind
	 */
	public static void addKeybind(String module, int keyCode) {
		module = module.toLowerCase();
		ArrayList<Integer> binds = new ArrayList<>(getKeybinds(module));
		if (binds.contains(keyCode))
			return;
		binds.add(keyCode);
		keybinds.put(module, binds);
		save(FileManager.DEFAULT_KEYBINDS);
	}
	
	/**
	 * Removes all keybinds for a module
	 * @param module The name of the module
	 */
	public static void removeKeybind(String module) {
		module = module.toLowerCase();
		keybinds.remove(module);
		save(FileManager.DEFAULT_KEYBINDS);
	}
	
	/**
	 * Returns an array of keybinds for the module
	 * @param module The name of the module
	 * @return
	 */
	public static ArrayList<Integer> getKeybinds(String module) {
		module = module.toLowerCase();
		ArrayList<Integer> binds = new ArrayList<>();
		try {
			binds.addAll(keybinds.get(module));
		} catch (Exception e) {}
		
		return binds;
	}
	
	/**
	 * Saves the keybinds to a file
	 * @param saveFile The file to save them to
	 */
	public static void save(File saveFile) {
		String save = "";
		for (String module : keybinds.keySet()) {
			save += (save.isEmpty() ? "" : Kagu.UNIT_SEPARATOR) + module + Kagu.GROUP_SEPARATOR;
			String binds = "";
			for (Integer bind : getKeybinds(module)) {
				binds += (binds.isEmpty() ? "" : Kagu.RECORD_SEPARATOR) + bind;
			}
			save += binds;
		}
		FileManager.writeStringToFile(saveFile, save);
	}
	
	/**
	 * Saves the keybinds from a file
	 * @param file The file to load them from
	 */
	public static void load(File file) {
		String fileData = FileManager.readStringFromFile(file);
		if (fileData.isEmpty())
			return;
		keybinds.clear();
		for (String bind : fileData.split(Kagu.UNIT_SEPARATOR)) {
			try {
				String[] bindArray = bind.split(Kagu.GROUP_SEPARATOR);
				
				ArrayList<Integer> keyCodes = new ArrayList<>();
				for (String code : bindArray[1].split(Kagu.RECORD_SEPARATOR)) {
					keyCodes.add(Integer.valueOf(code));
				}
				
				keybinds.put(bindArray[0], keyCodes);
			} catch (Exception e) {
				
			}
		}
	}
	
	/**
	 * Listener for the keybinds
	 */
	@EventHandler
	private Handler<EventKeyUpdate> subscriber = e -> {
		if (e.isPost() || !e.isPressed() || e.isCanceled())
			return;
		
		int keyCode = e.getKeyCode();
		for (Module module : Kagu.getModuleManager().getModules()) {
			if (getKeybinds(module.getName()).contains(keyCode)) {
				module.toggle();
			}
		}
		
	};
	
}
