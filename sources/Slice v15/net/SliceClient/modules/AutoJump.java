package net.SliceClient.modules;

import net.minecraft.client.Minecraft;

public class AutoJump extends net.SliceClient.module.Module
{
  public AutoJump()
  {
    super("AutoJump", net.SliceClient.module.Category.PLAYER, 16376546);
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {}
  }
  



  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    gameSettingskeyBindJump.pressed = true;
  }
  


  public void onDisable()
  {
    gameSettingskeyBindJump.pressed = false;
  }
}
