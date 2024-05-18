package net.SliceClient.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.SliceClient.Utils.TimeHelper;
import net.SliceClient.event.EventMovePlayer;
import net.SliceClient.event.EventUpdatePreMotion;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class Glide
  extends Module
{
  public Glide()
  {
    super("Glide", Category.MOVEMENT, 16376546);
  }
  
  private Float motion = Float.valueOf(1.0F);
  private final TimeHelper time = new TimeHelper();
  private final TimeHelper ground = new TimeHelper();
  private boolean shouldCancelPacket = false;
  private int ticks = 0;
  
  public void onEnable()
  {
    EventManager.register(this);
    if (Minecraft.thePlayer != null)
    {
      time.reset();
      if (thePlayeronGround)
      {
        thePlayermotionY += 1.0E-5D;
        Minecraft.thePlayer.swingItem();
      }
    }
    Minecraft.thePlayer.jump();
    thePlayeronGround = true;
    thePlayermotionY = 1.0E-4D;
  }
  
  public void onDisable()
  {
    EventManager.unregister(this);
    if (Minecraft.thePlayer != null) {
      net.minecraft.util.Timer.timerSpeed = 1.0F;
    }
  }
  
  public void onUpdate()
  {
    if (!getState()) {}
  }
  



  @EventTarget
  public void onPreMotion(EventUpdatePreMotion event)
  {
    thePlayermotionX = 0.0D;
    thePlayermotionZ = 0.0D;
    thePlayermotionY = 0.0D;
    if (thePlayerfallDistance > 2.0F) {
      Minecraft.thePlayer.jump();
      Minecraft.thePlayer.jump();
      thePlayeronGround = true;
      thePlayermotionY = 0.00224D;
      
      if (time.hasReached(200L)) {}
      thePlayermotionY = 0.001D;
      if (time.hasReached(3000L)) {}
      thePlayermotionY += 0.05000000074505806D;
      
      if (time.hasReached(100L)) {}
      thePlayermotionY += 1.409999966621399D;
      
      thePlayermotionY += 5.000000237487257E-4D;
    }
  }
  

  @EventTarget
  public void onPlayerMove(EventMovePlayer event)
  {
    if (!gameSettingskeyBindSneak.pressed) {
      event.setY(event.getY() * motion.floatValue());
    }
  }
}
