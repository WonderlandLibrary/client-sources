package xyz.vergoclient.keybinds;

import xyz.vergoclient.event.impl.EventKey;
import xyz.vergoclient.files.impl.FileKeybinds;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.ModuleManager;
import xyz.vergoclient.ui.guis.GuiStart;
import net.minecraft.client.Minecraft;

public class KeyboardManager {
	
	public static FileKeybinds keybinds;
	
	// Returns true if it should cancel the event
	public static boolean keypress(int key) {
		EventKey event = new EventKey(key);
		event.fire();
		if (keybinds == null)
			keybinds = new FileKeybinds();
		toggleModules(key);
		return event.isCanceled();
	}
	
	public static void toggleModules(int key) {
		
		// To prevent bugs
		if (!GuiStart.hasLoaded || ModuleManager.currentlyLoadingConfig || Minecraft.getMinecraft() == null || Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null)
			return;
		
		for (Keybind bind : keybinds.keybinds) {
			if (bind.keybind == key) {
				for (Module m : ModuleManager.modules) {
					if (m.getName().equals(bind.modName)) {
						m.toggle();
						break;
					}
				}
			}
		}
		
	}
	
}
