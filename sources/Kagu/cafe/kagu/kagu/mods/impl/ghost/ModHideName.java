/**
 * 
 */
package cafe.kagu.kagu.mods.impl.ghost;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventPacketReceive;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.player.ModNoFall;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

/**
 * @author DistastefulBannock
 * When enabled this will modify what the fontrenderers process so the players name is always hidden
 */
public class ModHideName extends Module {
	
	public ModHideName() {
		super("HideName", Category.GHOST);
	}
	
	/**
	 * Takes some text and removes the users ign from it
	 * @param text The text to parse
	 * @return The text with all instances of the users ign replaced by their kagu name
	 */
	public static String replaceNameInstances(String text) {
		if (Kagu.getModuleManager().getModule(ModHideName.class).isDisabled())
			return text;
		return text.replace(mc.getSession().getUsername(), Kagu.getLoggedInUser());
	}
	
}
