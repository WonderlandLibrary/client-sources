/**
 * 
 */
package cafe.kagu.kagu.utils;

import cafe.kagu.kagu.ui.clickgui.GuiCsgoClickgui;
import cafe.kagu.kagu.ui.clickgui.GuiDropdownClickgui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

/**
 * @author lavaflowglow
 *
 */
public class ClickGuiUtils {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * Called at the start of the client
	 */
	public static void start() {
		
	}
	
	/**
	 * @return true if the player is in the clickgui, otherwise false
	 */
	public static boolean isInClickGui() {
		GuiScreen currentScreen = mc.getCurrentScreen();
		return currentScreen instanceof GuiCsgoClickgui || currentScreen instanceof GuiDropdownClickgui;
	}
	
}
