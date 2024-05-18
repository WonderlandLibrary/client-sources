package com.darkcart.xdolf.mods.world;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

import net.minecraft.client.entity.EntityPlayerSP;

public class Speedmine extends Module {
	
	public static Value mineSpeed = new Value("Mine Speed");
	
	public Speedmine() {
		super("SpeedMine", "Allows you to break blocks faster", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.WORLD);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled())
        {
	        if (Wrapper.getWorld() != null) {
	            if (Wrapper.getMinecraft().playerController.curBlockDamageMP < mineSpeed.getValue()) {
	            	Wrapper.getMinecraft().playerController.curBlockDamageMP = mineSpeed.getValue();
	            }
	            if (Wrapper.getMinecraft().playerController.blockHitDelay > 1) {
	            	Wrapper.getMinecraft().playerController.blockHitDelay = 1;
	            }
	        }
        }
	}
}