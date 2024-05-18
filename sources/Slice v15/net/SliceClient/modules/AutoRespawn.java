package net.SliceClient.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class AutoRespawn extends net.SliceClient.module.Module
{
  public AutoRespawn()
  {
    super("AutoRespawn", net.SliceClient.module.Category.PLAYER, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    


    if ((!Minecraft.thePlayer.isEntityAlive()) && (!thePlayerisDead)) {
      Minecraft.thePlayer.respawnPlayer();
    }
  }
}
