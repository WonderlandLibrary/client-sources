package lunadevs.luna.module.movement;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Step extends Module{

	public Step() {
		super("Step", 0, Category.MOVEMENT, false);
	}
	
	@Override
	public void onUpdate() {
		if(!this.isEnabled) return;
		if ((Minecraft.thePlayer.isCollidedHorizontally) && (Minecraft.thePlayer.onGround) && (Minecraft.thePlayer.isCollidedVertically) && (Minecraft.thePlayer.isCollided))
	    {
	      Minecraft.thePlayer.setSprinting(true);
	      Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, 
	        Minecraft.thePlayer.posY + 0.42D, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
	      Minecraft.thePlayer.setSprinting(true);
	      Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, 
	        Minecraft.thePlayer.posY + 0.753D, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
	      Minecraft.thePlayer.setSprinting(true);
	      Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1.0D, Minecraft.thePlayer.posZ);
	      Minecraft.thePlayer.setSprinting(true);
		super.onUpdate();
	    }
	}
	
	public void onEnable(){
		super.onEnable();
	}
	
	public void onDisable(){
		mc.thePlayer.stepHeight = 1.6f;
		super.onDisable();
	}

	@Override
	public String getValue() {
		return "NCP";
	}

}
