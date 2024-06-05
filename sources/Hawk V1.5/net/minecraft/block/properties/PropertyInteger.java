package net.minecraft.block.properties;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;

public class PropertyInteger extends PropertyHelper {
   private final ImmutableSet allowedValues;
   private static final String __OBFID = "CL_00002014";

   public String getName(Comparable var1) {
      return this.getName0((Integer)var1);
   }

   public int hashCode() {
      int var1 = super.hashCode();
      var1 = 31 * var1 + this.allowedValues.hashCode();
      return var1;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         if (!super.equals(var1)) {
            return false;
         } else {
            PropertyInteger var2 = (PropertyInteger)var1;
            return this.allowedValues.equals(var2.allowedValues);
         }
      } else {
         return false;
      }
   }

   public Collection getAllowedValues() {
      return this.allowedValues;
   }

   protected PropertyInteger(String var1, int var2, int var3) {
      super(var1, Integer.class);
      if (var2 < 0) {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder("Min value of ")).append(var1).append(" must be 0 or greater")));
      } else if (var3 <= var2) {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder("Max value of ")).append(var1).append(" must be greater than min (").append(var2).append(")")));
      } else {
         HashSet var4 = Sets.newHashSet();

         for(int var5 = var2; var5 <= var3; ++var5) {
            var4.add(var5);
         }

         this.allowedValues = ImmutableSet.copyOf(var4);
      }
   }

   public static PropertyInteger create(String var0, int var1, int var2) {
      return new PropertyInteger(var0, var1, var2);
   }

   public String getName0(Integer var1) {
      return var1.toString();
   }
}
