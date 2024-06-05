package net.minecraft.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$EnumFacing$1;

public enum EnumFacing$Plane implements Predicate, Iterable {
   HORIZONTAL("HORIZONTAL", 0),
   VERTICAL("VERTICAL", 1);

   private static final EnumFacing$Plane[] $VALUES = new EnumFacing$Plane[]{HORIZONTAL, VERTICAL};

   private EnumFacing$Plane(String var3, int var4) {
   }

   public EnumFacing[] facings() {
      switch(EnumFacing$EnumFacing$1.field_179514_c[this.ordinal()]) {
      case 1:
         return new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST};
      case 2:
         return new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN};
      default:
         throw new Error("Someone\'s been tampering with the universe!");
      }
   }

   public EnumFacing random(Random var1) {
      EnumFacing[] var2 = this.facings();
      return var2[var1.nextInt(var2.length)];
   }

   public boolean apply(EnumFacing var1) {
      return var1.getAxis().getPlane() == this;
   }

   public Iterator iterator() {
      return Iterators.forArray(this.facings());
   }

   public boolean apply(Object var1) {
      return this.apply((EnumFacing)var1);
   }
}
