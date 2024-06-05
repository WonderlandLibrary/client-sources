package zamorozka.modules.ZAMOROZKA;

import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.MouseUtilis;

public class QueueAssist extends Module{

	public QueueAssist() {
		super("QueueAssist", 0, Category.Zamorozka);
		// TODO Auto-generated constructor stub
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(Mouse.isButtonDown(2) && mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChest) {
			mc.clickMouse();
		}
	}
	
	@Override
	public void onDisable() {
		MouseUtilis.setMouse(0, true);
		super.onDisable();
	}

}
