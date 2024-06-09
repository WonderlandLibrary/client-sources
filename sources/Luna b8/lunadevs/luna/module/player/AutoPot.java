package lunadevs.luna.module.player;


import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.types.Priority;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventMotion;
import lunadevs.luna.events.EventType;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AutoPot extends Module{
	
	int ticks = 0;
	int health = 10;
	boolean doPot = false;
	int delaypot = 0;

	public AutoPot() {
		super("AutoPot", 0, Category.PLAYER, false);
	}

	@EventTarget(Priority.HIGHEST)
	public void heal(EventMotion event){
		if (this.ticks > 0)
	    {
	      this.ticks -= 1;
	      return;
	    }
		if (event.getType() == EventType.PRE) {
			 int potionInInventory = findPotion(9, 36);
		     int potionInHotbar = findPotion(36, 45);
		     this.delaypot += 1;
		     if (((mc.currentScreen instanceof GuiContainer)) && (!(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory))) {
		       return;
		     }
		     if (Minecraft.thePlayer.getHealth() >= health) {
		       return;
		     }
		     if (!Minecraft.thePlayer.onGround) {
		       return;
		     }
		     if ((potionInInventory == -1) && (potionInHotbar == -1)) {
		       return;
		    }
		     if (this.delaypot <= 15) {
		      return;
		     }
		     if (potionInHotbar != -1) {
		       int oldSlot = Minecraft.thePlayer.inventory.currentItem;
		       NetHandlerPlayClient sendQueue = Minecraft.thePlayer.sendQueue;
		       
		       sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(Minecraft.thePlayer.rotationYaw, 120.0F, Minecraft.thePlayer.onGround));
		       sendQueue.addToSendQueue(new C09PacketHeldItemChange(potionInHotbar - 36));
		       mc.playerController.updateController();
		       sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventoryContainer.getSlot(potionInHotbar).getStack()));
		       sendQueue.addToSendQueue(new C09PacketHeldItemChange(oldSlot));
		       sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.rotationPitch, Minecraft.thePlayer.onGround));
		       this.delaypot = 10;
		     } else {
		       mc.playerController.windowClick(0, potionInInventory, 0, 1, Minecraft.thePlayer);
		     }
		}
	}

	public int getCount() {
		int pot = -1;
		int counter = 0;
		for (int i = 0; i < 36; ++i) {
			if (mc.thePlayer.inventory.mainInventory[i] != null) {
				ItemStack is = mc.thePlayer.inventory.mainInventory[i];
				Item item = is.getItem();
				if (item instanceof ItemPotion) {
					ItemPotion potion = (ItemPotion) item;
					if (potion.getEffects(is) != null) {
						for (Object o : potion.getEffects(is)) {
							PotionEffect effect = (PotionEffect) o;
							if (effect.getPotionID() == Potion.heal.id && ItemPotion.isSplash(is.getItemDamage())) {
								++counter;
							}
						}
					}
				}

				
			}
		}

		return counter;
	}

	private int findPotion(int startSlot, int endSlot) {
	     int i = startSlot;
	     while (i < endSlot) {
	       ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
	       if ((stack != null) && (stack.getItem() == Items.potionitem) && (ItemPotion.isSplash(stack.getItemDamage()))) {
	         for (Object o : ((ItemPotion)stack.getItem()).getEffects(stack)) {
	           if (((PotionEffect)o).getPotionID() == Potion.heal.id)
	            return i;
	         }
	    }
	       i++;
	     }
	     return -1;
	   }


	
	@Override
	public String getValue() {
		return null;
	}
}
