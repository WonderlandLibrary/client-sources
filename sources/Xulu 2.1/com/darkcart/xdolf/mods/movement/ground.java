package com.darkcart.xdolf.mods.movement;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class ground extends Module
{
	public ground()
	{
		super("ground", "Old", "NCP Ground Speed for OLD NCP.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(this.isEnabled()){
	if (Minecraft.getMinecraft().gameSettings.keyBindForward.pressed)
    {
      net.minecraft.util.Timer.timerSpeed = 2.0F;
      Minecraft.player.setSprinting(true);
      Minecraft.player.cameraPitch = 0.3F;
    }
    else
    {
      net.minecraft.util.Timer.timerSpeed = 1.0F;
    }
    if (Minecraft.getMinecraft().gameSettings.keyBindForward.pressed) {
      if (Minecraft.player.onGround)
      {
        Minecraft.player.jump();
      }
      else
      {
        Minecraft.player.cameraPitch = 0.0F;
        if (Minecraft.getMinecraft().gameSettings.keyBindForward.pressed) {
          Minecraft.player.motionY = -2.0D;
        }
        net.minecraft.util.Timer.timerSpeed = 1.0F;
        }
      }
    }
  }
}

