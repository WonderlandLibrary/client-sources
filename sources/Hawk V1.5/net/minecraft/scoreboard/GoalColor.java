package net.minecraft.scoreboard;

import java.util.List;
import net.minecraft.util.EnumChatFormatting;

public class GoalColor implements IScoreObjectiveCriteria {
   private static final String __OBFID = "CL_00001961";
   private final String field_178794_j;

   public IScoreObjectiveCriteria.EnumRenderType func_178790_c() {
      return IScoreObjectiveCriteria.EnumRenderType.INTEGER;
   }

   public boolean isReadOnly() {
      return false;
   }

   public String getName() {
      return this.field_178794_j;
   }

   public int func_96635_a(List var1) {
      return 0;
   }

   public GoalColor(String var1, EnumChatFormatting var2) {
      this.field_178794_j = String.valueOf((new StringBuilder(String.valueOf(var1))).append(var2.getFriendlyName()));
      IScoreObjectiveCriteria.INSTANCES.put(this.field_178794_j, this);
   }
}
