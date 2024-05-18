package me.hexxed.mercury.modules;

import java.util.Random;
import me.hexxed.mercury.modulebase.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class SpookySkin extends Module
{
  private java.util.Set original;
  
  public SpookySkin()
  {
    super("Spooky Skin", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.PLAYER);
  }
  


  Random r = new Random();
  
  public void onDisable()
  {
    for (EnumPlayerModelParts part : ) {
      mc.gameSettings.func_178878_a(part, true);
    }
  }
  
  public void onTick()
  {
    for (EnumPlayerModelParts part : ) {
      mc.gameSettings.func_178878_a(part, r.nextBoolean());
    }
  }
}
