package zamorozka.modules.PLAYER;

import zamorozka.event.EventTarget;
import zamorozka.event.events.EventChatMessage;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AutoReg extends Module {

	public AutoReg() {
		super("AutoReg", 0, Category.PLAYER);
	}

	@EventTarget
	public void onChat(EventChatMessage event) {
		if (event.getMessage().contains("/reg") || event.getMessage().contains("/register") || event.getMessage().contains("Зарегестрируйтесь")) {
			mc.player.sendChatMessage("/reg qwerty123 qwerty123");
		} else if (event.getMessage().contains("Авторизуйтесь") || event.getMessage().contains("/l")) {
			mc.player.sendChatMessage("/login qwerty123");
		}
	}

}
