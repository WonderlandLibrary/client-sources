package net.SliceClient.modules;

import net.minecraft.client.Minecraft;

public class yee extends net.SliceClient.module.Module
{
  public yee()
  {
    super("ACP", net.SliceClient.module.Category.SPEED, 16376546);
  }
  
  private int bhopState = 0;
  private int hypixelState = 0;
  private float ncpPrevYaw;
  private double ncpPrevY;
  private boolean ncpMove;
  private boolean ncpIsHopping;
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    if ((thePlayermoveForward > 0.0F) && (!thePlayerisCollidedHorizontally))
    {
      Minecraft.thePlayer.setSprinting(true);
      if ((ncpIsHopping) && (!ncpMove) && (thePlayerposY >= ncpPrevY + 1.399994D))
      {
        thePlayermotionY = -10.0D;
        thePlayerposY = ncpPrevY;
        ncpIsHopping = false;
      }
      if ((ncpIsHopping) && (thePlayerposY >= ncpPrevY + 0.399994D))
      {
        thePlayermotionY = -0.9D;
        thePlayerposY = ncpPrevY;
        ncpIsHopping = false;
      }
      if ((thePlayermoveForward == 0.0F) && (thePlayermoveStrafing == 0.0F))
      {
        thePlayermotionX = 0.0D;
        thePlayermotionZ = 0.0D;
        if (thePlayerisCollidedVertically)
        {
          Minecraft.thePlayer.jump();
          ncpMove = true;
        }
        if ((ncpMove) && (thePlayerisCollidedVertically)) {
          ncpMove = false;
        }
      }
      if (thePlayerisCollidedVertically)
      {
        thePlayermotionX *= 1.0479D;
        thePlayermotionZ *= 1.0479D;
        ncpIsHopping = true;
        ncpPrevY = thePlayerposY;
        Minecraft.thePlayer.jump();
      }
    }
  }
}
