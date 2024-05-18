package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;

public class FastFall extends Module
{
  public FastFall()
  {
    super("FastFall", net.SliceClient.module.Category.MISC, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    if (gameSettingskeyBindJump.isKeyDown()) {}
    
    thePlayermotionY = 0.0D;
    

    thePlayermotionY = -4.050000190734863D;
  }
}
