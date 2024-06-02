/**
 * 
 */
package cafe.kagu.kagu.prot;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.RandomUtils;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.prot.ui.GuiAuthNeeded;
import net.minecraft.client.Minecraft;

/**
 * @author DistastefulBannock
 *
 */
public class OffAuthScreensWithoutLoginCheck {
	
	/**
	 * Called at cheat start
	 */
	public static void start() {
		new Thread(() -> {
			int flags = 0;
			while (!Kagu.getKeyAuth().isLoggedIn()) {
				try {
					Thread.sleep(RandomUtils.nextLong(250, 400));
				} catch (Exception e) {}
				if (!Kagu.isLoggedIn() && !(Minecraft.getMinecraft().getCurrentScreen() instanceof GuiAuthNeeded)) {
					Minecraft.getMinecraft().displayGuiScreen(new GuiAuthNeeded());
					flags++;
					if (flags > 10) {
						Runtime.getRuntime().halt(Note.GUISCREEN_CHANGING_WITHOUT_LOGIN);
					}
				}
			}
		}).start();
	}
	
}
