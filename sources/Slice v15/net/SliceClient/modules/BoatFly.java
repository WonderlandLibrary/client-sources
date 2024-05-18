package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.item.EntityBoat;

public class BoatFly extends Module
{
  public BoatFly()
  {
    super("BoatFly", net.SliceClient.module.Category.EXPLOITS, 16376546);
  }
  
  public void onUpdate() {
    if (!getState()) {
      return;
    }
    Minecraft.getMinecraft();net.minecraft.entity.Entity hitler = thePlayerridingEntity;
    Minecraft.getMinecraft(); if ((gameSettingskeyBindJump.pressed) && 
      ((hitler instanceof EntityBoat))) {
      motionY = 0.20000000298023224D;
    }
  }
}
