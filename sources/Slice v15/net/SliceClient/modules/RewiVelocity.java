package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class RewiVelocity extends Module
{
  public RewiVelocity()
  {
    super("RewiVelocity", Category.MISC, 16376546);
    
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {}
  }
  


  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    if (thePlayerhurtTime > 0)
    {
      thePlayermotionX = 0.0D;
      thePlayermotionZ = 0.0D;
    }
  }
}
