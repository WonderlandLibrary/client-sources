package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

import org.lwjgl.input.Keyboard;

import darkmagician6.Event;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class AutoEat extends Module{
	public AutoEat() {
		super("AutoEat", "autoeat", 0, Category.PLAYER, new String[]{""});
	}
	private double hunger = 8.5D;
	public boolean eating = false;
	
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	  @EventTarget
	  private void onPreEvent(EventPreMotionUpdates event)
	  {
	      int foodSlot = getFoodSlotInHotbar();
	      if (foodSlot != -1 && (this.mc.thePlayer.getFoodStats().getFoodLevel() < 18))
	      {
	    	  eating = true;
	        this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(foodSlot));
	        this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.mainInventory[foodSlot]));
	        	for (int i = 0; i < 32; i++) {
	        		if(Wrapper.getPlayer().onGround){
	        	Wrapper.sendPacket(new C03PacketPlayer(false));
	        		}
	        	}
	        this.mc.thePlayer.stopUsingItem();
	        this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
	        eating = false;
	      }
	    }
	  private int getFoodSlotInHotbar()
	  {
	    for (int i = 0; i < 9; i++) {
	      if ((this.mc.thePlayer.inventory.mainInventory[i] != null) && (this.mc.thePlayer.inventory.mainInventory[i].getItem() != null) && ((this.mc.thePlayer.inventory.mainInventory[i].getItem() instanceof ItemFood))) {
	        return i;
	      }
	    }
	    return -1;
	  }
	}
