package net.minecraft.block.properties;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;

public class PropertyBool extends PropertyHelper {
   private final ImmutableSet allowedValues = ImmutableSet.of(true, false);
   private static final String __OBFID = "CL_00002017";

   public Collection getAllowedValues() {
      return this.allowedValues;
   }

   public static PropertyBool create(String var0) {
      return new PropertyBool(var0);
   }

   protected PropertyBool(String var1) {
      super(var1, Boolean.class);
   }

   public String getName0(Boolean var1) {
      return var1.toString();
   }

   public String getName(Comparable var1) {
      return this.getName0((Boolean)var1);
   }
}
