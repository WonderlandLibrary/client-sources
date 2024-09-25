package none.module.modules.player;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;

public class AutoRespawn extends Module{

	public AutoRespawn() {
		super("AutoRespawn", "AutoRespawn", Category.PLAYER, Keyboard.KEY_NONE);
	}

	@Override
	@RegisterEvent(events = EventTick.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventTick) {
			if (mc.thePlayer.isDead) {
				mc.thePlayer.respawnPlayer();
				mc.displayGuiScreen((GuiScreen)null);
	        }
		}
	}

}
