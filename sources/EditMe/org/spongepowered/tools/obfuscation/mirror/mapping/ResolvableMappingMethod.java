package org.spongepowered.tools.obfuscation.mirror.mapping;

import java.util.Iterator;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public final class ResolvableMappingMethod extends MappingMethod {
   private final TypeHandle ownerHandle;

   public ResolvableMappingMethod(TypeHandle var1, String var2, String var3) {
      super(var1.getName(), var2, var3);
      this.ownerHandle = var1;
   }

   public MappingMethod getSuper() {
      if (this.ownerHandle == null) {
         return super.getSuper();
      } else {
         String var1 = this.getSimpleName();
         String var2 = this.getDesc();
         String var3 = TypeUtils.getJavaSignature(var2);
         TypeHandle var4 = this.ownerHandle.getSuperclass();
         if (var4 != null && var4.findMethod(var1, var3) != null) {
            return var4.getMappingMethod(var1, var2);
         } else {
            Iterator var5 = this.ownerHandle.getInterfaces().iterator();

            TypeHandle var6;
            do {
               if (!var5.hasNext()) {
                  if (var4 != null) {
                     return var4.getMappingMethod(var1, var2).getSuper();
                  }

                  return super.getSuper();
               }

               var6 = (TypeHandle)var5.next();
            } while(var6.findMethod(var1, var3) == null);

            return var6.getMappingMethod(var1, var2);
         }
      }
   }

   public MappingMethod move(TypeHandle var1) {
      return new ResolvableMappingMethod(var1, this.getSimpleName(), this.getDesc());
   }

   public MappingMethod remap(String var1) {
      return new ResolvableMappingMethod(this.ownerHandle, var1, this.getDesc());
   }

   public MappingMethod transform(String var1) {
      return new ResolvableMappingMethod(this.ownerHandle, this.getSimpleName(), var1);
   }

   public MappingMethod copy() {
      return new ResolvableMappingMethod(this.ownerHandle, this.getSimpleName(), this.getDesc());
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
}
