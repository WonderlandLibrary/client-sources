package net;

import java.util.List;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.IScoreObjectiveCriteria$EnumRenderType;

public class a5O {
   public static String c(IScoreObjectiveCriteria var0) {
      return var0.getName();
   }

   public static IScoreObjectiveCriteria$EnumRenderType a(IScoreObjectiveCriteria var0) {
      return var0.getRenderType();
   }

   public static boolean b(IScoreObjectiveCriteria var0) {
      return var0.isReadOnly();
   }

   public static int a(IScoreObjectiveCriteria var0, List var1) {
      return var0.func_96635_a(var1);
   }
}
