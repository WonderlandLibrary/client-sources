package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.PotionEffect;

public class FullBright extends Module
{
  public FullBright()
  {
    super("FullBright", Category.WORLD, 16376546);
  }
  


  public void onUpdate()
  {
    if (!getState()) {
      if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null))
        return;
      return;
    }
    Minecraft.thePlayer.addPotionEffect(new PotionEffect(16, 16340));
  }
  

  public void onDisable()
  {
    Minecraft.thePlayer.removePotionEffect(16);
  }
}
