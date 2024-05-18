package net.SliceClient.modules;

import com.darkmagician6.eventapi.EventManager;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class Motion extends Module
{
  public Motion()
  {
    super("Motion", Category.SPEED, 16376546);
  }
  
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
    if (getState()) {
      if (thePlayeronGround)
      {
        if (((thePlayermoveForward != 0.0F) || (thePlayermoveStrafing != 0.0F)) && (!thePlayerisCollidedHorizontally))
        {
          if (thePlayerfallDistance > 1.894D) {
            return;
          }
          if ((Minecraft.thePlayer.isInWater()) || (Minecraft.thePlayer.isOnLadder()) || (gameSettingskeyBindJump.pressed)) {
            net.minecraft.util.Timer.timerSpeed = 1.0F;
            return;
          }
          net.minecraft.util.Timer.timerSpeed = 1.0F;
        }
        if ((Minecraft.thePlayer.isInWater()) || (Minecraft.thePlayer.isOnLadder())) {
          return;
        }
        Minecraft.getMinecraft();
        if ((thePlayeronGround) && (thePlayermovementInput != null))
        {
          thePlayermotionX *= 1.4D;
          thePlayermotionZ *= 1.4D;
        }
        


        if ((!thePlayeronGround) && (!thePlayerisCollidedHorizontally))
        {
          net.minecraft.util.Timer.timerSpeed = 1.0F;
          thePlayermotionY = -5.0D;
        }
        
      }
      else
      {
        net.minecraft.util.Timer.timerSpeed = 1.0F;
      }
    }
  }
}
