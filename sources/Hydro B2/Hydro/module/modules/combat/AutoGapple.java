package Hydro.module.modules.combat;

import Hydro.event.Event;
import Hydro.event.events.EventMotion;
import Hydro.module.Category;
import Hydro.module.Module;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class AutoGapple extends Module {

	public AutoGapple() {
		super("AutoGapple", 0, true, Category.COMBAT, "Automatically eats gapples");
	}
	
	private double hunger = 8.5D;
	public boolean eating = false;
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventMotion) {
			int foodSlot = getFoodSlotInHotbar();
		      if (foodSlot != -1 && (this.mc.thePlayer.getFoodStats().getFoodLevel() < hunger))
		      {
		    	  eating = true;
		        this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(foodSlot));
		        this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.mainInventory[foodSlot]));
		        	for (int i = 0; i < 32; i++) {
		        		if(mc.thePlayer.onGround){
		        			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
		        		}
		        	}
		        this.mc.thePlayer.stopUsingItem();
		        this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
		        eating = false;
		      }
		}
	}
	  
	  private int getFoodSlotInHotbar()
	  {
	    for (int i = 0; i < 9; i++) {
	      if ((this.mc.thePlayer.inventory.mainInventory[i] != null) && (this.mc.thePlayer.inventory.mainInventory[i].getItem() != null) && ((this.mc.thePlayer.inventory.mainInventory[i].getItem() instanceof ItemAppleGold))) {
	        return i;
	      }
	    }
	    return -1;
	  }

}
