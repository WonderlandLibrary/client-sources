package net.minecraft.scoreboard;

import java.util.List;

public class ScoreDummyCriteria implements IScoreObjectiveCriteria {
   private final String field_96644_g;
   private static final String __OBFID = "CL_00000622";

   public boolean isReadOnly() {
      return false;
   }

   public ScoreDummyCriteria(String var1) {
      this.field_96644_g = var1;
      IScoreObjectiveCriteria.INSTANCES.put(var1, this);
   }

   public IScoreObjectiveCriteria.EnumRenderType func_178790_c() {
      return IScoreObjectiveCriteria.EnumRenderType.INTEGER;
   }

   public String getName() {
      return this.field_96644_g;
   }

   public int func_96635_a(List var1) {
      return 0;
   }
}
