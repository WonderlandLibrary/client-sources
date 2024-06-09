package me.hexxed.mercury.modules;

import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;

public class TrueFreecam extends Module
{
  public double x;
  public double y;
  public double z;
  public float pitch;
  public float yaw;
  
  public TrueFreecam()
  {
    super("TrueFreecam", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.PLAYER);
  }
  





  public void onEnable()
  {
    x = mc.thePlayer.posX;
    y = mc.thePlayer.posY;
    z = mc.thePlayer.posZ;
    pitch = mc.thePlayer.rotationPitch;
    yaw = mc.thePlayer.rotationYaw;
    EntityOtherPlayerMP spawnSelf = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
    spawnSelf.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, 180.0F, 0.0F);
    mc.theWorld.addEntityToWorld(69, spawnSelf);
    mc.field_175622_Z = spawnSelf;
  }
  
  public void onPreUpdate()
  {
    float speed = (float)(getValuesFlySpeed / 3.0D);
    
    mc.field_175622_Z.motionX = 0.0D;
    mc.field_175622_Z.motionY = 0.0D;
    mc.field_175622_Z.motionZ = 0.0D;
    
    if (getMinecraftcurrentScreen == null)
    {
      if (GameSettings.isKeyDown(getMinecraftgameSettings.keyBindJump)) {
        mc.field_175622_Z.motionY = (speed * 5.0F / 6.0F);
      }
      if (GameSettings.isKeyDown(getMinecraftgameSettings.keyBindSneak)) {
        mc.field_175622_Z.motionY = (-speed * 5.0F / 6.0F);
      }
    }
  }
  

  public void onDisable()
  {
    mc.thePlayer.setPosition(x, y, z);
    mc.thePlayer.rotationPitch = pitch;
    mc.thePlayer.rotationYaw = yaw;
    mc.field_175622_Z = mc.thePlayer;
    mc.theWorld.removeEntityFromWorld(69);
  }
}
