package net.minecraft.command;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

final class PlayerSelector$2 implements Predicate {
   final String val$s_f;
   final boolean val$flag;

   PlayerSelector$2(String var1, boolean var2) {
      this.val$s_f = var1;
      this.val$flag = var2;
   }

   public boolean apply(Entity var1) {
      return EntityList.isStringEntityName(var1, this.val$s_f) != this.val$flag;
   }
}
