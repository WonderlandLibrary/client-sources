package ooo.cpacket.ruby.module.memes;

import net.minecraft.client.gui.inventory.GuiContainer;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.module.Module;

public class CakeFucker extends Module {

	public CakeFucker(String name, int key, Category category) {
		super(name, key, category);
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onDisable() {
		
	}

	@EventImpl
	public void onUpdate(EventMotionUpdate e) {
		if (mc.currentScreen instanceof GuiContainer) {
			mc.displayGuiScreen(null);
		}
	}

}
