package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.base.Strings;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;

public class FieldHandle extends MemberHandle {
   private final VariableElement element;
   private final boolean rawType;

   public FieldHandle(TypeElement var1, VariableElement var2) {
      this(TypeUtils.getInternalName(var1), var2);
   }

   public FieldHandle(String var1, VariableElement var2) {
      this(var1, var2, false);
   }

   public FieldHandle(TypeElement var1, VariableElement var2, boolean var3) {
      this(TypeUtils.getInternalName(var1), var2, var3);
   }

   public FieldHandle(String var1, VariableElement var2, boolean var3) {
      this(var1, var2, var3, TypeUtils.getName(var2), TypeUtils.getInternalName(var2));
   }

   public FieldHandle(String var1, String var2, String var3) {
      this(var1, (VariableElement)null, false, var2, var3);
   }

   private FieldHandle(String var1, VariableElement var2, boolean var3, String var4, String var5) {
      super(var1, var4, var5);
      this.element = var2;
      this.rawType = var3;
   }

   public boolean isImaginary() {
      return this.element == null;
   }

   public VariableElement getElement() {
      return this.element;
   }

   public Visibility getVisibility() {
      return TypeUtils.getVisibility(this.element);
   }

   public boolean isRawType() {
      return this.rawType;
   }

   public MappingField asMapping(boolean var1) {
      return new MappingField(var1 ? this.getOwner() : null, this.getName(), this.getDesc());
   }

   public String toString() {
      String var1 = this.getOwner() != null ? "L" + this.getOwner() + ";" : "";
      String var2 = Strings.nullToEmpty(this.getName());
      String var3 = Strings.nullToEmpty(this.getDesc());
      return String.format("%s%s:%s", var1, var2, var3);
   }

   public IMapping asMapping(boolean var1) {
      return this.asMapping(var1);
   }
}
