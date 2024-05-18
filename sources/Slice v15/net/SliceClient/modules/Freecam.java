package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;









public class Freecam
  extends Module
{
  private EntityOtherPlayerMP fakePlayer = null;
  
  private double oldX;
  private double oldY;
  private double oldZ;
  
  public Freecam()
  {
    super("Freecam", Category.RENDER, 16376546);
  }
  
  public void onEnable()
  {
    oldX = thePlayerposX;
    oldY = thePlayerposY;
    oldZ = thePlayerposZ;
    fakePlayer = new EntityOtherPlayerMP(Minecraft.theWorld, Minecraft.thePlayer.getGameProfile());
    fakePlayer.clonePlayer(Minecraft.thePlayer, true);
    fakePlayer.copyLocationAndAnglesFrom(Minecraft.thePlayer);
    fakePlayer.rotationYawHead = thePlayerrotationYawHead;
    Minecraft.theWorld.addEntityToWorld(-69, fakePlayer);
  }
  
  public void onUpdate()
  {
    if (getState())
    {
      thePlayermotionX = 0.0D;
      thePlayermotionY = 0.0D;
      thePlayermotionZ = 0.0D;
      float flightSpeed = 0.1F;
      thePlayerjumpMovementFactor = flightSpeed;
      if (gameSettingskeyBindJump.pressed) {
        thePlayermotionY += flightSpeed + 1.0F;
      }
      if (gameSettingskeyBindSneak.pressed) {
        thePlayermotionY -= flightSpeed + 1.0F;
      }
    }
  }
  
  public void onDisable()
  {
    Minecraft.thePlayer.setPositionAndRotation(oldX, oldY, oldZ, thePlayerrotationYaw, thePlayerrotationPitch);
    Minecraft.theWorld.removeEntityFromWorld(-69);
    fakePlayer = null;
    mcrenderGlobal.loadRenderers();
  }
}
