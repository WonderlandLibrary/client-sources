package com.darkcart.xdolf.mods.movement;
 
import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
 
public class aacPort extends Module {
   
    public aacPort() {
        super("aacPort", "Old", "AAC yPort [TESTING].", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
    }
    
    //int clock = 0;
   
    @Override
    public void onDisable(){
    	 net.minecraft.util.Timer.timerSpeed = 1.0F;
    }
    Minecraft mc = Minecraft.getMinecraft();
    @Override
    public void onUpdate(EntityPlayerSP player) {
        if(isEnabled()){
        	if (this.mc.gameSettings.keyBindForward.pressed)
            {
              net.minecraft.util.Timer.timerSpeed = 2.5F;
              Minecraft.player.setSprinting(true);
              Minecraft.player.cameraPitch = 0.3F;
              Minecraft.player.distanceWalkedModified = 1.0E10F;
            }
            else
            {
              net.minecraft.util.Timer.timerSpeed = 1.0F;
            }
            if (this.mc.gameSettings.keyBindForward.pressed) {
              if (Minecraft.player.onGround)
              {
                Minecraft.player.jump();
              }
              else
              {
                Minecraft.player.cameraPitch = 0.0F;
                if (this.mc.gameSettings.keyBindForward.pressed) {
                  Minecraft.player.motionY = -2.0D;
                }
                net.minecraft.util.Timer.timerSpeed = 1.0F;
              }
            }
          }
        }
}