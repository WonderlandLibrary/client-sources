package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_20;

public class Debug extends AbstractModule {
   public Debug(ModuleManager var1) {
      super(var1, EnumModuleType.MISC, "Debug");
   }

   @EventTarget
   public void a(Render2DEvent var1) {
      WindowedFullscreen.a();
      byte var3 = 121;

      try {
         Object[] var4 = new Object[]{"Timer: " + this.mc.timer.timerSpeed, "blocks/s: " + this.mc.player.getLastTickDistance() * 20.0D, Float.valueOf(this.mc.player.fallDistance), this.mc.world.getScoreboard().getObjectiveInDisplaySlot(1).getDisplayName()};
         int var5 = var4.length;
         int var6 = 0;
         if(var6 < var5) {
            Object var7 = var4[var6];
            Fonts$SF$SF_20.SF_20.drawString(String.valueOf(var7), 1.0D, (double)var3, 16777215, true);
            int var10000 = var3 + Fonts$SF$SF_20.SF_20.getHeight() + 2;
            ++var6;
         }
      } catch (NullPointerException var8) {
         ;
      }

   }

   private static NullPointerException a(NullPointerException var0) {
      return var0;
   }
}
