package net;

import cc.novoline.events.events.MoveEvent;
import cc.novoline.modules.move.TargetStrafe;
import net.minecraft.entity.Entity;

public class bn {
   public static boolean a(TargetStrafe var0) {
      return var0.isEnabled();
   }

   public static boolean b(TargetStrafe var0) {
      return var0.shouldTarget();
   }

   public static void a(TargetStrafe var0, MoveEvent var1, double var2, Entity var4) {
      var0.circleStrafe(var1, var2, var4);
   }
}
