package net.SliceClient.modules;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;




public class OnGround
  extends Module
{
  boolean move;
  boolean hop;
  private double[] startCoords;
  private double prevY;
  private int motionTicks;
  
  public OnGround()
  {
    super("OnGround", Category.SPEED, 16376546);
    
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {}
  }
  

  public void onEnable()
  {
    super.onEnable();
    
    motionTicks = 0;
    move = false;
    hop = false;
    prevY = 0.0D;
  }
  


  private void doMiniHop()
  {
    hop = true;
    prevY = thePlayerposY;
    Minecraft.thePlayer.jump();
  }
  
  public double round(double value, int places)
  {
    if (places < 0) {
      throw new IllegalArgumentException();
    }
    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
  



  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    if ((hop) && 
      (thePlayerposY >= prevY + 0.399994D))
    {
      thePlayermotionY = -0.9D;
      thePlayerposY = prevY;
      hop = false;
    }
    if ((thePlayermoveForward != 0.0F) && (!thePlayerisCollidedHorizontally) && (!Minecraft.thePlayer.isEating()))
    {
      if ((thePlayermoveForward == 0.0F) && (thePlayermoveStrafing == 0.0F))
      {
        thePlayermotionX = 0.0D;
        thePlayermotionZ = 0.0D;
        if (thePlayerisCollidedVertically)
        {
          Minecraft.thePlayer.jump();
          move = true;
        }
        if ((move) && (thePlayerisCollidedVertically)) {
          move = false;
        }
      }
      if (thePlayerisCollidedVertically)
      {
        thePlayermotionX *= 1.0379D;
        thePlayermotionZ *= 1.0379D;
        doMiniHop();
      }
      if ((hop) && (!move) && 
        (thePlayerposY >= prevY + 0.399994D))
      {
        thePlayermotionY = -100.0D;
        thePlayerposY = prevY;
        hop = false;
      }
    }
  }
}
