package net.minecraft.scoreboard;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.IScoreObjectiveCriteria$EnumRenderType;
import net.minecraft.scoreboard.ScoreDummyCriteria;
import net.minecraft.util.MathHelper;

public class ScoreHealthCriteria extends ScoreDummyCriteria {
   public ScoreHealthCriteria(String var1) {
      super(var1);
   }

   public int func_96635_a(List var1) {
      float var2 = 0.0F;

      for(EntityPlayer var4 : var1) {
         var2 += var4.getHealth() + var4.getAbsorptionAmount();
      }

      if(!var1.isEmpty()) {
         var2 /= (float)var1.size();
      }

      return MathHelper.ceiling_float_int(var2);
   }

   public boolean isReadOnly() {
      return true;
   }

   public IScoreObjectiveCriteria$EnumRenderType getRenderType() {
      return IScoreObjectiveCriteria$EnumRenderType.HEARTS;
   }
}
