package net.minecraft.stats;

import net.minecraft.scoreboard.ScoreDummyCriteria;

public class ObjectiveStat extends ScoreDummyCriteria {
   private final StatBase field_151459_g;
   private static final String __OBFID = "CL_00000625";

   public ObjectiveStat(StatBase var1) {
      super(var1.statId);
      this.field_151459_g = var1;
   }
}
