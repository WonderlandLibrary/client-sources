package zamorozka.modules.PLAYER;

import org.apache.commons.lang3.RandomStringUtils;

import zamorozka.event.EventTarget;
import zamorozka.event.events.EventChatMessage;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.modules.COMBAT.KillAura;

public class AutoGG extends Module {

	public AutoGG() {
		super("AutoGG", 0, Category.PLAYER);
	}

	@EventTarget
	public void onChatMessage(EventChatMessage event) {
		String str1 = RandomStringUtils.randomAlphabetic(7);
        String str2 = RandomStringUtils.randomPrint(10);
		if(event.getMessage().contains("Вы выиграли бой!") || event.getMessage().contains("бой!")) {
			mc.player.sendChatMessage("![" +str1 + "] " + KillAura.target.getName() + " ха, соснул " + "[" + str2 + "]");
		}
	}	
}