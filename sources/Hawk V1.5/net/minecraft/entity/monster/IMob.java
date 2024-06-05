package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IAnimals;

public interface IMob extends IAnimals {
   Predicate mobSelector = new Predicate() {
      private static final String __OBFID = "CL_00001688";

      public boolean func_179983_a(Entity var1) {
         return var1 instanceof IMob;
      }

      public boolean apply(Object var1) {
         return this.func_179983_a((Entity)var1);
      }
   };
   Predicate field_175450_e = new Predicate() {
      private static final String __OBFID = "CL_00002218";

      public boolean func_179982_a(Entity var1) {
         return var1 instanceof IMob && !var1.isInvisible();
      }

      public boolean apply(Object var1) {
         return this.func_179982_a((Entity)var1);
      }
   };
}
