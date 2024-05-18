package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class ChestStealer extends Module{
	
	public ChestStealer() {
		super("Chest Stealer", "cheststealer", 0, Category.WORLD, new String[]{"cheststealer", "stealer"});
	}
	private final ClampedValue<Float> delay = new ClampedValue<>("cheststealer_delay", 0F, 0F, 125F);
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	@EventTarget
	public void onEvent(EventPreMotionUpdates event){
		updateMS();
		this.setDisplayName("Chest Stealer [" + this.delay.getValue() + "]");
		if(Wrapper.mc().thePlayer.openContainer != null && Wrapper.mc().thePlayer.openContainer instanceof ContainerChest){
			ContainerChest container = (ContainerChest)Wrapper.mc().thePlayer.openContainer;
			if(isEmpty(container)){
				mc.thePlayer.closeScreen();
			}
			for(int i = 0; i < container.getLowerChestInventory().getSizeInventory(); i++){
				if((container.getLowerChestInventory().getStackInSlot(i) != null) && hasTimePassedM(delay.getValue().longValue())){
					Wrapper.mc().playerController.windowClick(container.windowId, i, 0, 1, Wrapper.getPlayer());
					updateLastMS();
				}
			}
		}
	}
	public boolean isEmpty(final Container container) {
		for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
			if (container.getSlot(i).getHasStack()) {
				return false;
			}
		}
		return true;
	}
}
