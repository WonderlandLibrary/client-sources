package win.sightclient.module.player;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.ItemStack;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.BooleanSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.utils.PlayerUtils;
import win.sightclient.utils.TimerUtils;

public class ChestStealer extends Module {

	private TimerUtils timer = new TimerUtils();
	private double delay;
	private BooleanSetting autoclose = new BooleanSetting("AutoClose", this, true);
	private BooleanSetting baditems = new BooleanSetting("ItemFilter", this, true);
	private NumberSetting delaySet = new NumberSetting("Delay", this, 50, 0, 1000, true);
	
	public ChestStealer() {
		super("ChestStealer", Category.PLAYER);
	}
	
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate) e;
			if (eu.isPre()) {
				if (this.isChestEmpty()) {
					this.setDelay();
				}
				if (mc.currentScreen instanceof GuiChest) {
					final GuiChest chest = (GuiChest)mc.currentScreen;
					boolean close = autoclose.getValue();
					if ((this.isChestEmpty() || PlayerUtils.isInventoryFull()) && close && timer.hasReached(delay)) {
						Minecraft.getMinecraft().thePlayer.closeScreen();
						timer.reset();
						return;
					}
						
					if (timer.hasReached(delay)) {
			            for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
			                final ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
			                if (stack != null && timer.hasReached(delay) && (!PlayerUtils.isBad(stack) || !baditems.getValue())) {
			                    mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
			                    this.setDelay();
			                    this.timer.reset();
			                    continue;
			                }
			            }
			            timer.reset();
					}
				}
			}
		}
	}
	
	private boolean isChestEmpty() {
		if (mc.currentScreen instanceof GuiChest) {
			final GuiChest chest = (GuiChest)mc.currentScreen;
	        for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
	            final ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
	            if (stack != null && (!PlayerUtils.isBad(stack) || !baditems.getValue())) {
	                return false;
	            }
	        }
		}
        return true;
	}
    
    @Override
    public void onEnable() {
    	super.onEnable();
    	this.setDelay();
    }
    
    private void setDelay() {
    	if (delaySet.getValue() <= 5) {
    		this.delay = delaySet.getValue();
    	} else {
        	this.delay = delaySet.getValue() + ThreadLocalRandom.current().nextDouble(-10, 10);
    	}
    	this.setSuffix(delaySet.getValueInt() + "");
    }
}
