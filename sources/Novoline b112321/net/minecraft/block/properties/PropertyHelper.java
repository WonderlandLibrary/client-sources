package net.minecraft.block.properties;

import com.google.common.base.Objects;
import net.minecraft.block.properties.IProperty;

public abstract class PropertyHelper implements IProperty {
   private final Class valueClass;
   private final String name;

   protected PropertyHelper(String var1, Class var2) {
      this.valueClass = var2;
      this.name = var1;
   }

   public String getName() {
      return this.name;
   }

   public Class getValueClass() {
      return this.valueClass;
   }

   public String toString() {
      return Objects.toStringHelper(this).add("name", this.name).add("clazz", this.valueClass).add("values", this.getAllowedValues()).toString();
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(this.getClass() != var1.getClass()) {
         return false;
      } else {
         PropertyHelper var2 = (PropertyHelper)var1;
         return this.valueClass.equals(var2.valueClass) && this.name.equals(var2.name);
      }
   }

   public int hashCode() {
      return 31 * this.valueClass.hashCode() + this.name.hashCode();
   }
}
