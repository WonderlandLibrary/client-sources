package net;

import cc.novoline.events.events.Render2DEvent;
import net.minecraft.client.gui.ScaledResolution;

public class jj {
   public static ScaledResolution a(Render2DEvent var0) {
      return var0.getResolution();
   }

   public static float b(Render2DEvent var0) {
      return var0.getPartialTicks();
   }
}
