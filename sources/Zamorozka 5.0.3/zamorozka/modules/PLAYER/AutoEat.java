package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.BlockContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.Wrapper;


public class AutoEat extends Module {

	public AutoEat() {
		super("AutoEat", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	boolean doneEating;
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
    ItemStack curStack = mc.player.inventory.getCurrentItem();
        	
			if(!shouldEat()) {
				Wrapper.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
				return;
			}
				
			FoodStats foodStats = mc.player.getFoodStats();
            {
            	eatFood();
            }
		}
	public void onDisable() {
    	Wrapper.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
	}
	
	private void eatFood() {
		
		for(int slot = 44; slot >= 9; slot--) {
			ItemStack stack = Wrapper.getPlayer().inventoryContainer.getSlot(slot).getStack();
			
			
			if(stack != null) {
				if(slot >= 36 && slot <= 44) {
					if(stack.getItem() instanceof ItemFood 
							&& !(stack.getItem() instanceof ItemAppleGold)) {
						Wrapper.getPlayer().inventory.currentItem = slot - 36;
						Wrapper.getMinecraft().gameSettings.keyBindUseItem.pressed = true;
						return;
					}
				} else if(stack.getItem() instanceof ItemFood 
						&& !(stack.getItem() instanceof ItemAppleGold)) {
					int itemSlot = slot;
					int currentSlot = Wrapper.getPlayer().inventory.currentItem + 36;
					Wrapper.getMinecraft().playerController.windowClick(0, slot, 0, ClickType.PICKUP, Wrapper.getPlayer());
					Wrapper.getMinecraft().playerController.windowClick(0, currentSlot, 0, ClickType.PICKUP, Wrapper.getPlayer());
					Wrapper.getMinecraft().playerController.windowClick(0, slot, 0, ClickType.PICKUP, Wrapper.getPlayer());
					return;
				}
			}
		}
	}
	
	private boolean shouldEat()
	{	
		if(!Wrapper.getPlayer().canEat(false))
			return false;
		
		if(Wrapper.getMinecraft().currentScreen != null)
			return false;
		
		if(Wrapper.getMinecraft().currentScreen == null && Wrapper.getMinecraft().objectMouseOver != null)
		{
			Entity entity = Wrapper.getMinecraft().objectMouseOver.entityHit;
			if(entity instanceof EntityVillager || entity instanceof EntityTameable)
				return false;
			
			if(Wrapper.getMinecraft().objectMouseOver.getBlockPos() != null && Wrapper.getWorld().
					getBlockState(Wrapper.getMinecraft().objectMouseOver.getBlockPos()).getBlock() instanceof BlockContainer)
				return false;
		}
		
		return true; 

					
				}
			}