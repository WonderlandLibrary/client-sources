package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.item.Item;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class AutoFish extends Module {

	public AutoFish() {
		super("AutoFish", "autofish", 0, Category.PLAYER, new String[] { "" });
	}

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	@EventTarget
	public void onEvent(EventPreMotionUpdates event) {
		if ((Wrapper.getPlayer().getCurrentEquippedItem() != null) && (Wrapper.getPlayer().fishEntity != null) && (Wrapper.getPlayer().fishEntity.motionX == 0 && Wrapper.getPlayer().fishEntity.motionZ == 0 && Wrapper.getPlayer().fishEntity.motionY != 0)) {
			Wrapper.mc().playerController.sendUseItem(Wrapper.getPlayer(), Wrapper.getWorld(), Wrapper.getPlayer().inventory.getCurrentItem());
			Wrapper.mc().playerController.sendUseItem(Wrapper.getPlayer(), Wrapper.getWorld(), Wrapper.getPlayer().inventory.getCurrentItem());
		}
	}
}
