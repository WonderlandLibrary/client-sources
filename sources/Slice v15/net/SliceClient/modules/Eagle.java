package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;

public class Eagle extends Module
{
  public Eagle()
  {
    super("Eagle", net.SliceClient.module.Category.MOVEMENT, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    


    net.minecraft.util.BlockPos bp = new net.minecraft.util.BlockPos(thePlayerposX, thePlayerposY - 1.0D, thePlayerposZ);
    if (thePlayerfallDistance <= 4.0F) {
      if (Minecraft.theWorld.getBlockState(bp).getBlock() != net.minecraft.init.Blocks.air) {
        gameSettingskeyBindSneak.pressed = false;
      } else {
        gameSettingskeyBindSneak.pressed = true;
      }
    }
  }
}
