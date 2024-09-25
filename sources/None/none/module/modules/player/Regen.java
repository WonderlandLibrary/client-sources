package none.module.modules.player;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.utils.ClickType;
import none.utils.MoveUtils;
import none.valuesystem.BooleanValue;
import none.valuesystem.NumberValue;

public class Regen extends Module {

	public Regen() {
		super("Regen", "Regen", Category.PLAYER, Keyboard.KEY_LBRACKET);
	}

	private NumberValue<Integer> health = new NumberValue<>("REGEN-HEALTH", 17, 2, 20);
	private NumberValue<Integer> amount = new NumberValue<>("REGEN-AMOUNT", 40, 1, 60);
	private BooleanValue NOMOVE = new BooleanValue("NoMove", false);
	public HealType type;
	public boolean healing = false;
	public boolean appled = false;
	public int prevSlot = -1;

	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + ":" + ChatFormatting.GRAY +" A:" + amount.getInteger() + " H:" + health.getInteger());
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate em = (EventPreMotionUpdate) event;
	        if (em.isPre()) {
	        	if (appled) {
	        		if (prevSlot != -1) {
                    	mc.playerController.onStoppedUsingItem(mc.thePlayer);
                    	mc.thePlayer.itemInUseCount = 0;
                        mc.getConnection().sendPacket(new C09PacketHeldItemChange(prevSlot));
                        mc.thePlayer.inventory.currentItem = prevSlot;
                        prevSlot = -1;
                    }
	        	}
	            double health = this.health.getDouble();
	            if (mc.thePlayer.getHealth() < health) {
	            	int appleSlot = getGAppleFromInventory();
	            	if (appleSlot != -1) {
	            		type = HealType.Gapple;
		            	if (prevSlot == -1) {
	            			boolean swap = false;
	            			if (appleSlot < 36) {
	            				swap(getGAppleFromInventory(), 7);
	            				swap = true;
	            			}
	                        mc.getConnection().sendPacket(new C09PacketHeldItemChange(swap ? 7 : appleSlot - 36));
	                        mc.thePlayer.inventory.currentItem = swap ? 7 : appleSlot - 36;
	                        mc.getConnection().sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
	                        mc.thePlayer.itemInUseCount = 999;
	                        for (int i = 0; i < amount.getInteger(); i++) {
		            			mc.thePlayer.connection.sendPacket(new C03PacketPlayer());
		            		}
	                        appled = true;
	                        prevSlot = mc.thePlayer.inventory.currentItem;
	                    }
	            	}
	            }
	            
	            if (mc.thePlayer.getHealth() < health && mc.thePlayer.getFoodStats().getFoodLevel() > 19 && !mc.thePlayer.isUsingItem() && mc.thePlayer.isCollidedVertically &&
	                    mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown() && (!mc.thePlayer.isInsideOfMaterial(Material.lava)) &&
	                    !mc.thePlayer.isInWater()) {
	            	if ((NOMOVE.getObject() && !MoveUtils.isMoveKeyPressed()) || !NOMOVE.getObject()) {	            		
	            		for (int i = 0; i < amount.getInteger(); i++) {
	            			mc.thePlayer.connection.sendPacket(new C03PacketPlayer());
	            		}
	            	}
	            }
	            
//	            if (mc.thePlayer.isEntityAlive() && mc.thePlayer.getFoodStats().getFoodLevel() > 10 && (mc.thePlayer.getHeldItem() != null && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) && mc.thePlayer.isUsingItem() && mc.thePlayer.isCollidedVertically &&
//	                    mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown() && (!mc.thePlayer.isInsideOfMaterial(Material.lava)) &&
//	                    !mc.thePlayer.isInWater()) {
//	            	if ((NOMOVE.getObject() && !MoveUtils.isMoveKeyPressed()) || !NOMOVE.getObject()) {	            		
//	            		for (int i = 0; i < 35; i++) {
//	            			mc.thePlayer.connection.sendPacket(new C03PacketPlayer());
//	            		}
//	            		mc.playerController.onStoppedUsingItem(mc.thePlayer);
//	            	}
//	            }
	        }
		}
	}

	protected void swap(int slot, int hotbarNum) {
		mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, ClickType.SWAP,
				mc.thePlayer);
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

	public enum HealType {
		Regen, Gapple, None
	}
}
