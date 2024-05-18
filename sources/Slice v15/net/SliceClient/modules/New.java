package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class New
  extends Module
{
  int i = 0;
  int tx = 0;
  

  public New()
  {
    super("New", Category.SPEED, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    if (gameSettingskeyBindForward.pressed)
    {
      if (tx == 10)
      {
        if ((thePlayeronGround) && 
          (thePlayermoveForward > 0.17D))
        {
          thePlayerposY += 0.008937362D;
          for (int i = 0; i < 4; i++) {
            Minecraft.thePlayer.jump();
          }
        }
        tx = 0;
        return;
      }
      tx += 1;
    }
    super.onUpdate();
  }
}
