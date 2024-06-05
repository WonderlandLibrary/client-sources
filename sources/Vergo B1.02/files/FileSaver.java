package xyz.vergoclient.files;

import xyz.vergoclient.keybinds.KeyboardManager;
import xyz.vergoclient.ui.guis.GuiAltManager;
import xyz.vergoclient.ui.click.GuiClickGui;

public class FileSaver {
	
	public static void init() {
		
		new Thread(() -> {
			
			while (true) {
				
				FileManager.writeToFile(FileManager.defaultKeybindsFile, KeyboardManager.keybinds);
				FileManager.writeToFile(FileManager.clickguiTabs, GuiClickGui.tabs);
				FileManager.writeToFile(FileManager.altsFile, GuiAltManager.altsFile);
				
				try {
					Thread.sleep(10000);
				} catch (Exception e) {}
				
			}
			
		}).start();
		
	}
	
}
