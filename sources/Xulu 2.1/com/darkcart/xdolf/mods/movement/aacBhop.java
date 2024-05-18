package com.darkcart.xdolf.mods.movement;
 
import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
 
public class aacBhop extends Module {
   
    public aacBhop() {
        super("aacBhop", "Old", "AAC 3.1.2 Bhop [TESTING].", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
    }
    
    int clock = 0;
   
    @Override
    public void onDisable(){
    }
    Minecraft mc = Minecraft.getMinecraft();
    @Override
    public void onUpdate(EntityPlayerSP player) {
        if(isEnabled()){
            if(mc.gameSettings.keyBindJump.pressed){
                return;
            }
            if(mc.player.isCollidedHorizontally && !mc.player.isSneaking()) {
                mc.player.setSprinting(true);
            }
            if(mc.player.onGround && !mc.player.isSneaking() && mc.gameSettings.keyBindForward.pressed){
            	if(mc.player.isAirBorne){
            		//this.clock += 1;
            		//if (this.clock >= 490)
            	    //{
            		//	player.jump();
            	   // }
            		//this.clock = 0;
            		mc.player.motionX *= 1.9D;
            		 Minecraft.player.motionY = 0.399100090122223D;
            		mc.player.motionZ *= 1.9D;
            	}else{
            		mc.player.onGround = true;
            	}
            }
        }
    }
}