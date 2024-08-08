package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.client.tools.Utils;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Imitate extends Module {

	public Imitate() {
		super("Imitate", Keyboard.KEY_NONE, Category.FUN, "Imitates the closest entity.");
	}

	@Override
	public void onUpdate() {
		EntityLivingBase newEn = null;
		newEn = Utils.getClosestEntity((float) 10);
		if (newEn == null) {							
			
			return;
		}
		if (mc.thePlayer.getDistanceToEntity(newEn) < 10) {
			
			
			mc.thePlayer.rotationPitch = newEn.rotationPitch;
			mc.thePlayer.rotationYaw = newEn.rotationYaw;
			mc.thePlayer.rotationYawHead = newEn.rotationYawHead;
		
			
			
			if (newEn.isSneaking()){
				mc.thePlayer.isSneaking();
				
			}
			if (newEn.isSwingInProgress){
				
				Xatz.click();
				
			}
			if (newEn.isSprinting()){
				mc.thePlayer.setSprinting(true);
				
			}
			
			if (newEn.motionX < 0) {
				mc.thePlayer.motionX = newEn.motionX;
				
			}
			if (newEn.motionY < 0) {
				mc.thePlayer.motionY = newEn.motionY;
				
			}
			if (newEn.motionZ < 0) {
				mc.thePlayer.motionZ = newEn.motionZ;
				
			}
			
			
			
			
		}
		}
}