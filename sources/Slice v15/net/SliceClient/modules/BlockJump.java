package net.SliceClient.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class BlockJump extends net.SliceClient.module.Module
{
  public BlockJump()
  {
    super("BlockJump", net.SliceClient.module.Category.MOVEMENT, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    





    if ((getState()) && 
      (thePlayeronGround) && 
      (thePlayerisCollidedVertically) && 
      (thePlayerisCollidedHorizontally) && 
      (thePlayerisCollided)) {
      Minecraft.thePlayer.jump();
    }
  }
}
