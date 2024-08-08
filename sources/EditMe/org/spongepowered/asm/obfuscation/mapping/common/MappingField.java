package org.spongepowered.asm.obfuscation.mapping.common;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.spongepowered.asm.obfuscation.mapping.IMapping;

public class MappingField implements IMapping {
   private final String owner;
   private final String name;
   private final String desc;

   public MappingField(String var1, String var2) {
      this(var1, var2, (String)null);
   }

   public MappingField(String var1, String var2, String var3) {
      this.owner = var1;
      this.name = var2;
      this.desc = var3;
   }

   public IMapping.Type getType() {
      return IMapping.Type.FIELD;
   }

   public String getName() {
      return this.name;
   }

   public final String getSimpleName() {
      return this.name;
   }

   public final String getOwner() {
      return this.owner;
   }

   public final String getDesc() {
      return this.desc;
   }

   public MappingField getSuper() {
      return null;
   }

   public MappingField move(String var1) {
      return new MappingField(var1, this.getName(), this.getDesc());
   }

   public MappingField remap(String var1) {
      return new MappingField(this.getOwner(), var1, this.getDesc());
   }

   public MappingField transform(String var1) {
      return new MappingField(this.getOwner(), this.getName(), var1);
   }

   public MappingField copy() {
      return new MappingField(this.getOwner(), this.getName(), this.getDesc());
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.toString()});
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return var1 instanceof MappingField ? Objects.equal(this.toString(), ((MappingField)var1).toString()) : false;
      }
   }

   public String serialise() {
      return this.toString();
   }

   public String toString() {
      return String.format("L%s;%s:%s", this.getOwner(), this.getName(), Strings.nullToEmpty(this.getDesc()));
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
