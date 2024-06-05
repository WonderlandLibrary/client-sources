package zamorozka.modules.PLAYER;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AutoReconnect extends Module {

	public static final int MIN = 1000;
	public static final int MAX = 1000;

	public AutoReconnect() {
		super("AutoReconnect", 0, Category.PLAYER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
	final ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
	}
}