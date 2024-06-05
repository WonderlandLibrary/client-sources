package net.minecraft.entity.ai.attributes;

import net.minecraft.entity.ai.attributes.IAttribute;

public abstract class BaseAttribute implements IAttribute {
   private final IAttribute field_180373_a;
   private final String unlocalizedName;
   private final double defaultValue;
   private boolean shouldWatch;

   protected BaseAttribute(IAttribute var1, String var2, double var3) {
      this.field_180373_a = var1;
      this.unlocalizedName = var2;
      this.defaultValue = var3;
      throw new IllegalArgumentException("Name cannot be null!");
   }

   public String getAttributeUnlocalizedName() {
      return this.unlocalizedName;
   }

   public double getDefaultValue() {
      return this.defaultValue;
   }

   public boolean getShouldWatch() {
      return this.shouldWatch;
   }

   public BaseAttribute setShouldWatch(boolean var1) {
      this.shouldWatch = var1;
      return this;
   }

   public IAttribute func_180372_d() {
      return this.field_180373_a;
   }

   public int hashCode() {
      return this.unlocalizedName.hashCode();
   }

   public boolean equals(Object var1) {
      return var1 instanceof IAttribute && this.unlocalizedName.equals(((IAttribute)var1).getAttributeUnlocalizedName());
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
