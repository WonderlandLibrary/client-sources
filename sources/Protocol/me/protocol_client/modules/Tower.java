package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.MovingObjectPosition;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPostMotionUpdates;

public class Tower extends Module{

	public Tower() {
		super("Tower", "tower", 0, Category.WORLD, new String[]{""});
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	@EventTarget
	public void onEvent(EventPostMotionUpdates event){
		 if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
	            if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mc.thePlayer.onGround && mc.gameSettings.keyBindUseItem.getIsKeyPressed()) {
	                if (mc.thePlayer.rotationPitch > 40) {
	                    mc.thePlayer.motionY = 0.382f;
	                }
	            }
	        }
	}
}
