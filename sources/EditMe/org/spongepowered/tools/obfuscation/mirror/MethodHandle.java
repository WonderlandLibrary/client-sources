package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.base.Strings;
import javax.lang.model.element.ExecutableElement;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.mapping.ResolvableMappingMethod;

public class MethodHandle extends MemberHandle {
   private final ExecutableElement element;
   private final TypeHandle ownerHandle;

   public MethodHandle(TypeHandle var1, ExecutableElement var2) {
      this(var1, var2, TypeUtils.getName(var2), TypeUtils.getDescriptor(var2));
   }

   public MethodHandle(TypeHandle var1, String var2, String var3) {
      this(var1, (ExecutableElement)null, var2, var3);
   }

   private MethodHandle(TypeHandle var1, ExecutableElement var2, String var3, String var4) {
      super(var1 != null ? var1.getName() : null, var3, var4);
      this.element = var2;
      this.ownerHandle = var1;
   }

   public boolean isImaginary() {
      return this.element == null;
   }

   public ExecutableElement getElement() {
      return this.element;
   }

   public Visibility getVisibility() {
      return TypeUtils.getVisibility(this.element);
   }

   public MappingMethod asMapping(boolean var1) {
      if (var1) {
         return (MappingMethod)(this.ownerHandle != null ? new ResolvableMappingMethod(this.ownerHandle, this.getName(), this.getDesc()) : new MappingMethod(this.getOwner(), this.getName(), this.getDesc()));
      } else {
         return new MappingMethod((String)null, this.getName(), this.getDesc());
      }
   }

   public String toString() {
      String var1 = this.getOwner() != null ? "L" + this.getOwner() + ";" : "";
      String var2 = Strings.nullToEmpty(this.getName());
      String var3 = Strings.nullToEmpty(this.getDesc());
      return String.format("%s%s%s", var1, var2, var3);
   }

   public IMapping asMapping(boolean var1) {
      return this.asMapping(var1);
   }
}
