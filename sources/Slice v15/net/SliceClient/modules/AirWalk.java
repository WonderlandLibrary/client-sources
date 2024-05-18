package net.SliceClient.modules;

import com.darkmagician6.eventapi.EventManager;
import net.SliceClient.Slice;
import net.SliceClient.Utils.TimeHelper;
import net.SliceClient.event.EventUpdatePreMotion;
import net.SliceClient.module.Module;
import net.SliceClient.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.MovementInput;

public class AirWalk extends Module
{
  private double glideSpeed;
  private double speed;
  private final TimeHelper time;
  
  public AirWalk()
  {
    super("AirWalk", net.SliceClient.module.Category.MOVEMENT, 16376546);
    glideSpeed = 0.0D;
    speed = 0.0D;
    time = new TimeHelper();
  }
  

  public void onDisable()
  {
    EventManager.unregister(this);
  }
  
  public void onEnable()
  {
    EventManager.register(this);
    Minecraft mc = mc;
    if (Minecraft.thePlayer != null) {
      time.reset();
      Minecraft mc2 = mc;
      if (Minecraft.thePlayer != null) {
        Minecraft mc3 = mc;
        if (thePlayeronGround) {
          for (int i = 0; i < 70; i++) {
            thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 0.06D, thePlayerposZ, false));
            thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY, thePlayerposZ, false));
          }
          thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 0.1D, thePlayerposZ, false));
        }
      }
      Minecraft mc4 = mc;
      Minecraft.thePlayer.swingItem();
    }
  }
  
  @com.darkmagician6.eventapi.EventTarget
  public void onMotion(EventUpdatePreMotion event) {
    if ((!thePlayercapabilities.isFlying) && (thePlayerfallDistance > 0.0F) && (!Minecraft.thePlayer.isSneaking()) && (time.hasReached(500L))) {
      thePlayermotionY = -0.0D;
      net.minecraft.util.Timer timer = mctimer;
      net.minecraft.util.Timer.timerSpeed = 0.98F;
    }
    Minecraft mc = mc;
    
    if (!thePlayermovementInput.jump) {
      Minecraft mc2 = mc;
      if (!thePlayermovementInput.sneak) {
        Slice.getTrap();
        if (ModuleManager.getModule(AirWalk.class).isEnabled()) {
          Minecraft mc3 = mc;
          if (MovementInput.moveForward == 0.0D) {
            Minecraft mc4 = mc;
            if (MovementInput.moveStrafe == 0.0D) {
              Minecraft mc5 = mc;
              EntityPlayerSP thePlayer = Minecraft.thePlayer;
              Minecraft mc6 = mc;
              EntityPlayerSP thePlayer2 = Minecraft.thePlayer;
              Minecraft mc7 = mc;
              EntityPlayerSP thePlayer3 = Minecraft.thePlayer;
              double motionX = 0.0D;
              motionY = 0.0D;
              motionZ = 0.0D;
              motionX = 0.0D;
              event.setCancelled(true);
              break label338;
            }
          }
        }
      }
    }
    Minecraft mc8 = mc;
    if (thePlayermovementInput.jump) {
      Minecraft mc9 = mc;
      thePlayermotionY = speed;
    }
    else {
      Minecraft mc10 = mc;
      if (thePlayermovementInput.sneak) {
        Minecraft mc11 = mc;
        thePlayermotionY = (-speed);
      }
      else {
        Slice.getTrap();
        if (ModuleManager.getModule(AirWalk.class).isEnabled()) {
          Minecraft mc12 = mc;
          thePlayermotionY = -0.0D;
        }
        else {
          Minecraft mc13 = mc;
          thePlayermotionY = -0.0D;
        }
      }
    }
    label338:
    Minecraft mc14 = mc;
    if (thePlayermovementInput.jump) {
      Minecraft mc15 = mc;
      thePlayermotionY = (speed / 2.0D);
    }
    else {
      Minecraft mc16 = mc;
      if (thePlayermovementInput.sneak) {
        Minecraft mc17 = mc;
        thePlayermotionY = (-speed / 2.0D);
      }
      else {
        Slice.getTrap();
        if (ModuleManager.getModule(AirWalk.class).isEnabled()) {
          Minecraft mc18 = mc;
          thePlayermotionY = -0.0D;
        }
        else {
          Minecraft mc19 = mc;
          thePlayermotionY = -0.0D;
        }
      }
    }
  }
}
