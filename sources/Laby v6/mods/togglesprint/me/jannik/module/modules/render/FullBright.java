package mods.togglesprint.me.jannik.module.modules.render;

import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import net.minecraft.client.Minecraft;

public class FullBright
  extends Module
{
  public FullBright()
  {
    super("FullBright", Category.RENDER);
  }
  
  public void onEnabled()
  {
    mc.gameSettings.gammaSetting = 100.0F;
  }
  
  public void onDisabled()
  {
    mc.gameSettings.gammaSetting = 1.0F;
  }
}
