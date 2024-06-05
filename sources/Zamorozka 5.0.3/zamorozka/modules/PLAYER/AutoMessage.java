package zamorozka.modules.PLAYER;

import net.minecraft.client.Minecraft;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventChatMessage;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AutoMessage extends Module {

	Minecraft MC = Minecraft.getMinecraft();
	
	int i;
	
	public AutoMessage() {
		super("AutoMessage", 0, Category.PLAYER);
	}
	

}