package net.SliceClient.modules;

import com.darkmagician6.eventapi.EventManager;
import net.SliceClient.Utils.TimerUtil;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Frames extends Module
{
  private int motionTicks;
  
  public Frames()
  {
    super("Frames", net.SliceClient.module.Category.SPEED, 16376546);
  }
  

  private TimerUtil framesDelay = new TimerUtil();
  private double prevY;
  boolean hop;
  boolean move;
  
  public void onEnable()
  {
    EventManager.register(this);
    super.onEnable();
  }
  
  public void onDisable()
  {
    EventManager.unregister(this);
    net.minecraft.util.Timer.timerSpeed = 1.0F;
    super.onDisable();
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null))
      return;
    doFrames(4.3D);
  }
  
  private void doFrames(double speed)
  {
    if ((net.minecraft.util.MovementInput.moveForward > 0.0F) || (net.minecraft.util.MovementInput.moveStrafe > 0.0F)) {
      if (thePlayeronGround) {
        prevY = thePlayerposY;
        hop = true;
        Minecraft.thePlayer.jump();
        if (motionTicks == 1) {
          framesDelay.reset();
          if (move) {
            thePlayermotionX /= speed * 2.0D;
            thePlayermotionZ /= speed * 2.0D;
            move = false;
          }
          motionTicks = 0;
        } else {
          motionTicks = 1;
        }
      }
      else if ((!move) && (motionTicks == 1) && 
        (framesDelay.isDelayComplete(150L))) {
        thePlayermotionX *= speed;
        thePlayermotionZ *= speed;
        move = true;
      }
    }
  }
}
