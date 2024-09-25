package none.module.modules.combat;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.MobEffects;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.utils.ClickType;
import none.utils.MoveUtils;
import none.valuesystem.BooleanValue;
import none.valuesystem.NumberValue;

public class AutoApple extends Module{

	public AutoApple() {
		super("AutoApple", "AutoApple", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	private NumberValue<Integer> health = new NumberValue<>("REGEN-HEALTH", 17, 2, 20);
	private NumberValue<Integer> amount = new NumberValue<>("REGEN-AMOUNT", 40, 1, 60);
	private BooleanValue NOMOVE = new BooleanValue("NoMove", false);
	private int prevSlot = -1;
	
	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			
			if (e.isPre()) {
				double health = this.health.getDouble();
	            if (mc.currentScreen == null && !mc.thePlayer.isUsingItem() && mc.thePlayer.isCollidedVertically &&
	                    mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown() && (!mc.thePlayer.isInsideOfMaterial(Material.lava)) &&
	                    !mc.thePlayer.isInWater()) {
	            	if ((NOMOVE.getObject() && !MoveUtils.isMoveKeyPressed()) || !NOMOVE.getObject()) {	            		
	            		int appleSlot = getGAppleFromInventory();
	            		
	            		if (mc.thePlayer.getHealth() < health && appleSlot != -1 && mc.thePlayer.itemInUseCount == 0) {
	            			boolean swap = false;
	            			if (appleSlot < 36) {
	            				swap(getGAppleFromInventory(), 7);
	            				swap = true;
	            			}
	            			
	                        prevSlot = mc.thePlayer.inventory.currentItem;
	                        mc.getConnection().sendPacket(new C09PacketHeldItemChange(swap ? 7 : appleSlot - 36));
	                        mc.thePlayer.inventory.currentItem = swap ? 7 : appleSlot - 36;
	                        mc.getConnection().sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
	                        mc.thePlayer.itemInUseCount = 999;
	                        for (int i = 0; i < amount.getInteger(); i++) {
		            			mc.thePlayer.connection.sendPacket(new C03PacketPlayer());
		            		}
	                        evc("1");
	                    }else if (prevSlot != -1) {
	                    	evc("2");
	                    	mc.playerController.onStoppedUsingItem(mc.thePlayer);
	                    	mc.thePlayer.itemInUseCount = 0;
	                        mc.getConnection().sendPacket(new C09PacketHeldItemChange(prevSlot));
	                        mc.thePlayer.inventory.currentItem = prevSlot;
	                        prevSlot = -1;
	                    }
	            	}
	            }
			}
		}
	}
	
	protected void swap(int slot, int hotbarNum) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, ClickType.SWAP, mc.thePlayer);
    }

    public static int getGAppleFromInventory() {
        Minecraft mc = Minecraft.getMinecraft();
        int apple = -1;
        int counter = 0;
        for (int i = 1; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (Item.getIdFromItem(item) == 322) {
                    counter++;
                    apple = i;
                }
            }
        }
        return apple;
    }

    public boolean isEating() {
        return prevSlot >= 0;
    }
}
