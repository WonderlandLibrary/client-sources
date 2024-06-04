package net.minecraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import yung.purity.Client;
import yung.purity.module.Module;
import yung.purity.module.modules.player.InvMove;

public class MovementInputFromOptions extends MovementInput
{
  private final GameSettings gameSettings;
  private static final String __OBFID = "CL_00000937";
  
  public MovementInputFromOptions(GameSettings p_i1237_1_)
  {
    gameSettings = p_i1237_1_;
  }
  


  public void updatePlayerMoveState()
  {
    if ((Client.instance.getModuleManager().getModuleByClass(InvMove.class).isEnabled()) && (!(mccurrentScreen instanceof net.minecraft.client.gui.GuiChat)))
    {
      moveStrafe = 0.0F;
      moveForward = 0.0F;
      
      if (Keyboard.isKeyDown(gameSettings.keyBindForward.getKeyCode()))
      {
        moveForward += 1.0F;
      }
      
      if (Keyboard.isKeyDown(gameSettings.keyBindBack.getKeyCode()))
      {
        moveForward -= 1.0F;
      }
      
      if (Keyboard.isKeyDown(gameSettings.keyBindLeft.getKeyCode()))
      {
        moveStrafe += 1.0F;
      }
      
      if (Keyboard.isKeyDown(gameSettings.keyBindRight.getKeyCode()))
      {
        moveStrafe -= 1.0F;
      }
      
      jump = Keyboard.isKeyDown(gameSettings.keyBindJump.getKeyCode());
      sneak = gameSettings.keyBindSneak.getIsKeyPressed();
      
      if (sneak)
      {
        moveStrafe = ((float)(moveStrafe * 0.3D));
        moveForward = ((float)(moveForward * 0.3D));
      }
    }
    else
    {
      moveStrafe = 0.0F;
      moveForward = 0.0F;
      
      if (gameSettings.keyBindForward.getIsKeyPressed()) {
        moveForward += 1.0F;
      }
      
      if (gameSettings.keyBindBack.getIsKeyPressed()) {
        moveForward -= 1.0F;
      }
      
      if (gameSettings.keyBindLeft.getIsKeyPressed()) {
        moveStrafe += 1.0F;
      }
      
      if (gameSettings.keyBindRight.getIsKeyPressed()) {
        moveStrafe -= 1.0F;
      }
      
      jump = gameSettings.keyBindJump.getIsKeyPressed();
      sneak = gameSettings.keyBindSneak.getIsKeyPressed();
      
      if (sneak)
      {
        moveStrafe = ((float)(moveStrafe * 0.3D));
        moveForward = ((float)(moveForward * 0.3D));
      }
    }
  }
}
