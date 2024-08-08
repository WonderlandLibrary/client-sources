package org.spongepowered.asm.obfuscation.mapping.common;

import com.google.common.base.Objects;
import org.spongepowered.asm.obfuscation.mapping.IMapping;

public class MappingMethod implements IMapping {
   private final String owner;
   private final String name;
   private final String desc;

   public MappingMethod(String var1, String var2) {
      this(getOwnerFromName(var1), getBaseName(var1), var2);
   }

   public MappingMethod(String var1, String var2, String var3) {
      this.owner = var1;
      this.name = var2;
      this.desc = var3;
   }

   public IMapping.Type getType() {
      return IMapping.Type.METHOD;
   }

   public String getName() {
      return this.name == null ? null : (this.owner != null ? this.owner + "/" : "") + this.name;
   }

   public String getSimpleName() {
      return this.name;
   }

   public String getOwner() {
      return this.owner;
   }

   public String getDesc() {
      return this.desc;
   }

   public MappingMethod getSuper() {
      return null;
   }

   public boolean isConstructor() {
      return "<init>".equals(this.name);
   }

   public MappingMethod move(String var1) {
      return new MappingMethod(var1, this.getSimpleName(), this.getDesc());
   }

   public MappingMethod remap(String var1) {
      return new MappingMethod(this.getOwner(), var1, this.getDesc());
   }

   public MappingMethod transform(String var1) {
      return new MappingMethod(this.getOwner(), this.getSimpleName(), var1);
   }

   public MappingMethod copy() {
      return new MappingMethod(this.getOwner(), this.getSimpleName(), this.getDesc());
   }

   public MappingMethod addPrefix(String var1) {
      String var2 = this.getSimpleName();
      return var2 != null && !var2.startsWith(var1) ? new MappingMethod(this.getOwner(), var1 + var2, this.getDesc()) : this;
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.getName(), this.getDesc()});
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof MappingMethod)) {
         return false;
      } else {
         return Objects.equal(this.name, ((MappingMethod)var1).name) && Objects.equal(this.desc, ((MappingMethod)var1).desc);
      }
   }

   public String serialise() {
      return this.toString();
   }

   public String toString() {
      String var1 = this.getDesc();
      return String.format("%s%s%s", this.getName(), var1 != null ? " " : "", var1 != null ? var1 : "");
   }

   private static String getBaseName(String var0) {
      if (var0 == null) {
         return null;
      } else {
         int var1 = var0.lastIndexOf(47);
         return var1 > -1 ? var0.substring(var1 + 1) : var0;
      }
   }

   private static String getOwnerFromName(String var0) {
      if (var0 == null) {
         return null;
      } else {
         int var1 = var0.lastIndexOf(47);
         return var1 > -1 ? var0.substring(0, var1) : null;
      }
   }

   public Object getSuper() {
      return this.getSuper();
   }

   public Object copy() {
      return this.copy();
   }

   public Object transform(String var1) {
      return this.transform(var1);
   }

   public Object remap(String var1) {
      return this.remap(var1);
   }

   public Object move(String var1) {
      return this.move(var1);
   }
}
