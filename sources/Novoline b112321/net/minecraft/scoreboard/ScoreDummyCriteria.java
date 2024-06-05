package net.minecraft.scoreboard;

import java.util.List;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.IScoreObjectiveCriteria$EnumRenderType;

public class ScoreDummyCriteria implements IScoreObjectiveCriteria {
   private final String dummyName;

   public ScoreDummyCriteria(String var1) {
      this.dummyName = var1;
      IScoreObjectiveCriteria.INSTANCES.put(var1, this);
   }

   public String getName() {
      return this.dummyName;
   }

   public int func_96635_a(List var1) {
      return 0;
   }

   public boolean isReadOnly() {
      return false;
   }

   public IScoreObjectiveCriteria$EnumRenderType getRenderType() {
      return IScoreObjectiveCriteria$EnumRenderType.INTEGER;
   }
}
