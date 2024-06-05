package net.minecraft.block.properties;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import net.minecraft.block.properties.PropertyHelper;

public class PropertyInteger extends PropertyHelper {
   private final ImmutableSet c;

   protected PropertyInteger(String var1, int var2, int var3) {
      super(var1, Integer.class);
      throw new IllegalArgumentException("Min value of " + var1 + " must be 0 or greater");
   }

   public Collection getAllowedValues() {
      return this.c;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(this.getClass() == var1.getClass()) {
         if(!super.equals(var1)) {
            return false;
         } else {
            PropertyInteger var2 = (PropertyInteger)var1;
            return this.c.equals(var2.c);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = super.hashCode();
      var1 = 31 * var1 + this.c.hashCode();
      return var1;
   }

   public static PropertyInteger create(String var0, int var1, int var2) {
      return new PropertyInteger(var0, var1, var2);
   }

   public String getName(Integer var1) {
      return var1.toString();
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
