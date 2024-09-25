package none.module.modules.combat;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.utils.ClickType;
import none.utils.TimeHelper;
import none.valuesystem.BooleanValue;
import none.valuesystem.NumberValue;

public class AutoSoup extends Module{

	public AutoSoup() {
		super("AutoSoup", "AutoSoup", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	private NumberValue<Integer> health = new NumberValue<Integer>("Health", 12, 0, 20);
	private NumberValue<Integer> delay = new NumberValue<Integer>("Delay", 100, 0, 400);
	private BooleanValue openinv = new BooleanValue("OpenInv", false);
	
	private TimeHelper timer = new TimeHelper();
	private int soupcount;
	
	@Override
	protected void onEnable() {
		super.onEnable();
		soupcount = 0;
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		soupcount = 0;
	}
	
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate em = (EventPreMotionUpdate) event;
            if (em.isPre()) {
            	if (soupcount != 0) {
            		if (!openinv.getObject() || (openinv.getObject() && mc.currentScreen instanceof GuiInventory)) {
            				swap(getSoupFromInventory(), NotgetSoupFromHotbar());
                	}
            	}
            	int currentitem = mc.thePlayer.inventory.currentItem;
                int soupSlot = getSoupFromInventory();
                if (soupcount != 0) {
                	if (soupSlot != -1 && mc.thePlayer.getHealth() < (float)health.getObject()
                            && timer.hasTimeReached(delay.getObject())) {
                        mc.getConnection().sendPacket(new C09PacketHeldItemChange(8));
//                        mc.thePlayer.inventory.currentItem = 8;
                        mc.getConnection().sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
//                        mc.getNetHandler().sendPacket(new C08PacketPlayerUse(EnumHand.MAIN_HAND));
                        mc.getConnection().sendPacket(new C09PacketHeldItemChange(currentitem));
//                        mc.thePlayer.inventory.currentItem = currentitem;
                        timer.setLastMS();
                    }
                }
            }
		}
	}
	
	protected void swap(int slot, int hotbarNum) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, ClickType.SWAP, mc.thePlayer);
    }

    public int getSoupFromInventory() {
        int soup = 0;
        int counter = 0;
        for (int i = 1; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (Item.getIdFromItem(item) == 282) {
                    counter++;
                    soup = i;
                }
            }
            soupcount = counter;
        }
        return soup;
    }
    
    public int getSoupFromHotbar() {
        int soup = 0;
        for (int i = 1; i <= 8; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (Item.getIdFromItem(item) == 282) {
                    soup = i;
                }
            }
        }
        return soup;
    }
    
    public int NotgetSoupFromHotbar() {
    	int soup = 0;
        for (int i = 1; i <= 8; i++) {
            if (i != getSoupFromHotbar()) {
            	soup = i;
            }
        }
        return soup;
    }
    
    public boolean NotSoupFromHotbar(int slot) {
        if (mc.thePlayer.inventoryContainer.getSlot(slot).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
            if (Item.getIdFromItem(is.getItem()) != 282) {
                return true;
            }
        }
        return false;
    }
}
