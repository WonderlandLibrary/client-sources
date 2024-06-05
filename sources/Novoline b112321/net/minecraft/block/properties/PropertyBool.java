package net.minecraft.block.properties;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import net.minecraft.block.properties.PropertyHelper;

public class PropertyBool extends PropertyHelper {
   private final ImmutableSet allowedValues = ImmutableSet.of(Boolean.TRUE, Boolean.FALSE);

   protected PropertyBool(String var1) {
      super(var1, Boolean.class);
   }

   public Collection getAllowedValues() {
      return this.allowedValues;
   }

   public static PropertyBool create(String var0) {
      return new PropertyBool(var0);
   }

   public String getName(Boolean var1) {
      return var1.toString();
   }
}
