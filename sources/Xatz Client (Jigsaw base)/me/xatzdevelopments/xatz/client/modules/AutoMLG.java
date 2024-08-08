package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.events.UpdateEvent;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.tools.Time;
import me.xatzdevelopments.xatz.client.tools.TimeHelper;
import me.xatzdevelopments.xatz.client.tools.Wrapper;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class AutoMLG extends Module {
	Time t = new Time();
	TimeHelper time = new TimeHelper();
	  private int oldSlot;

	public AutoMLG() {
		super("AutoMLG", Keyboard.KEY_NONE, Category.PLAYER, "Puts water underneath you when u fall.");
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	
		

	@Override
	public void onUpdate(UpdateEvent e) {
		
		
		
		 boolean check = mc.thePlayer.isBurning();
		 if ((check) && 
		      (Wrapper.findHotbarItem(326, 1) != -1))
		    {
		     
		      this.oldSlot = mc.thePlayer.inventory.currentItem;
		      mc.thePlayer.rotationPitch = 90.0F;
		      mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(Wrapper.findHotbarItem(326, 1)));
		      mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
		      mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.oldSlot));
		    }
		 if ((mc.thePlayer.fallDistance > 4F) && 
			      (Wrapper.findHotbarItem(326, 1) != -1) && ((Minecraft.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() instanceof BlockAir)))
			    {
			 mc.thePlayer.setSpeed(0);
			      mc.gameSettings.keyBindUseItem.pressed = false;
			      this.oldSlot = mc.thePlayer.inventory.currentItem;
			      e.pitch = 90.0F;
			      
			      mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(Wrapper.findHotbarItem(326, 1)));
			      mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
			      mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.oldSlot));
			    }
			  
			

		super.onUpdate();
}
	

}
