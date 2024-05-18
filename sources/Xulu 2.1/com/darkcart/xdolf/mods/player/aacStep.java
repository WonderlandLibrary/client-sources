package com.darkcart.xdolf.mods.player;
 
import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
 
public class aacStep extends Module {
   
    public aacStep() {
        super("aacStep", "Old", "AAC Step [TESTING].", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
    }
    
    //int clock = 0;
   
    @Override
    public void onDisable(){
    }
    Minecraft mc = Minecraft.getMinecraft();
    @Override
    public void onUpdate(EntityPlayerSP player) {
        if(isEnabled()){
        	if ((Minecraft.player.isCollidedHorizontally) && (Minecraft.player.isCollidedVertically)) {
                Minecraft.player.stepHeight = 30.0F;
              }
              if (Minecraft.player.stepHeight == 30.0F) {
                Minecraft.player.setSneaking(true);
              }
              if (Minecraft.player.isSneaking()) {
                Minecraft.player.setSneaking(false);
              }
            }
          }
        }
