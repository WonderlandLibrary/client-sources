package net.SliceClient.modules;

import com.darkmagician6.eventapi.EventManager;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class Casper
  extends Module
{
  public Casper()
  {
    super("Casper", Category.SPEED, 16376546);
  }
  
  private int counter = 0;
  public static Minecraft mc = ;
  
  public void onDisable()
  {
    EventManager.unregister(this);
    net.minecraft.util.Timer.timerSpeed = 1.0F;
  }
  
  public void onEnable()
  {
    EventManager.register(this);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null))
      return;
    if ((getState()) && (thePlayeronGround))
    {
      if (gameSettingskeyBindJump.pressed)
        net.minecraft.util.Timer.timerSpeed = 1.0F;
    }
    Minecraft.getMinecraft();
    if (!Minecraft.thePlayer.isSneaking())
    {
      Minecraft.getMinecraft();
      if (thePlayermoveForward == 0.0F)
      {
        Minecraft.getMinecraft();
      }
      

    }
    else
    {
      return;
    }
    Minecraft.getMinecraft();
    if (thePlayeronGround)
    {
      counter += 1;
      if (counter == 1)
      {
        net.minecraft.util.Timer.timerSpeed = 1.0F;
        thePlayermotionX *= 1.0D;
        thePlayermotionY *= 1.185825882D;
        thePlayermotionZ *= 1.0D;
      }
      if (counter == 2)
      {
        net.minecraft.util.Timer.timerSpeed = 1.0F;
        thePlayermotionX *= 1.050000047683716D;
        thePlayermotionY *= 1.185825882D;
        thePlayermotionZ *= 1.150000047683716D;
      }
      if (counter == 3)
      {
        net.minecraft.util.Timer.timerSpeed = 1.0F;
        thePlayermotionX *= 1.0D;
        thePlayermotionY *= 1.285825882D;
        thePlayermotionZ *= 1.0D;
      }
      if (counter == 4)
      {
        net.minecraft.util.Timer.timerSpeed = 1.1503987F;
        thePlayermotionX *= 1.558526265256D;
        thePlayermotionY *= 1.285825882D;
        thePlayermotionZ *= 1.158526265256D;
      }
      if (counter == 4)
      {
        net.minecraft.util.Timer.timerSpeed = 1.6667514F;
        thePlayermotionX *= 1.2500000476837159D;
        thePlayermotionY *= 1.085825882D;
        thePlayermotionZ *= 1.2500000476837159D;
        counter = 0;
      }
    }
  }
}
