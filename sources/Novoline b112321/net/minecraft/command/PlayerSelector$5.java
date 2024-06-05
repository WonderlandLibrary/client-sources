package net.minecraft.command;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.scoreboard.Team;

final class PlayerSelector$5 implements Predicate {
   final String val$s_f;
   final boolean val$flag;

   PlayerSelector$5(String var1, boolean var2) {
      this.val$s_f = var1;
      this.val$flag = var2;
   }

   public boolean apply(Entity var1) {
      if(!(var1 instanceof EntityLivingBase)) {
         return false;
      } else {
         EntityLivingBase var2 = (EntityLivingBase)var1;
         Team var3 = var2.getTeam();
         String var4 = "";
         return var4.equals(this.val$s_f) != this.val$flag;
      }
   }
}
