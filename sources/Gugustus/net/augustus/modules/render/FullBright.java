package net.augustus.modules.render;

import java.awt.Color;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;

public class FullBright extends Module {
   private float oldGamma = 1.0F;

   public FullBright() {
      super("FullBright", new Color(188, 213, 25), Categorys.RENDER);
   }

   @Override
   public void onEnable() {
      this.oldGamma = mc.gameSettings.gammaSetting;
      mc.gameSettings.gammaSetting = 10000.0F;
   }

   @Override
   public void onDisable() {
      mc.gameSettings.gammaSetting = this.oldGamma;
   }
}
