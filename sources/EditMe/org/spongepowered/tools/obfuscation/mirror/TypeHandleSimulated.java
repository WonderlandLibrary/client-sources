package org.spongepowered.tools.obfuscation.mirror;

import java.util.Iterator;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.SignaturePrinter;

public class TypeHandleSimulated extends TypeHandle {
   private final TypeElement simulatedType;

   public TypeHandleSimulated(String var1, TypeMirror var2) {
      this(TypeUtils.getPackage(var2), var1, var2);
   }

   public TypeHandleSimulated(PackageElement var1, String var2, TypeMirror var3) {
      super(var1, var2);
      this.simulatedType = (TypeElement)((DeclaredType)var3).asElement();
   }

   protected TypeElement getTargetElement() {
      return this.simulatedType;
   }

   public boolean isPublic() {
      return true;
   }

   public boolean isImaginary() {
      return false;
   }

   public boolean isSimulated() {
      return true;
   }

   public AnnotationHandle getAnnotation(Class var1) {
      return null;
   }

   public TypeHandle getSuperclass() {
      return null;
   }

   public String findDescriptor(MemberInfo var1) {
      return var1 != null ? var1.desc : null;
   }

   public FieldHandle findField(String var1, String var2, boolean var3) {
      return new FieldHandle((String)null, var1, var2);
   }

   public MethodHandle findMethod(String var1, String var2, boolean var3) {
      return new MethodHandle((TypeHandle)null, var1, var2);
   }

   public MappingMethod getMappingMethod(String var1, String var2) {
      String var3 = (new SignaturePrinter(var1, var2)).setFullyQualified(true).toDescriptor();
      String var4 = TypeUtils.stripGenerics(var3);
      MethodHandle var5 = findMethodRecursive((TypeHandle)this, var1, var3, var4, true);
      return var5 != null ? var5.asMapping(true) : super.getMappingMethod(var1, var2);
   }

   private static MethodHandle findMethodRecursive(TypeHandle var0, String var1, String var2, String var3, boolean var4) {
      TypeElement var5 = var0.getTargetElement();
      if (var5 == null) {
         return null;
      } else {
         MethodHandle var6 = TypeHandle.findMethod(var0, var1, var2, var3, var4);
         if (var6 != null) {
            return var6;
         } else {
            Iterator var7 = var5.getInterfaces().iterator();

            do {
               if (!var7.hasNext()) {
                  TypeMirror var9 = var5.getSuperclass();
                  if (var9 != null && var9.getKind() != TypeKind.NONE) {
                     return findMethodRecursive(var9, var1, var2, var3, var4);
                  }

                  return null;
               }

               TypeMirror var8 = (TypeMirror)var7.next();
               var6 = findMethodRecursive(var8, var1, var2, var3, var4);
            } while(var6 == null);

            return var6;
         }
      }
   }

   private static MethodHandle findMethodRecursive(TypeMirror var0, String var1, String var2, String var3, boolean var4) {
      if (!(var0 instanceof DeclaredType)) {
         return null;
      } else {
         TypeElement var5 = (TypeElement)((DeclaredType)var0).asElement();
         return findMethodRecursive(new TypeHandle(var5), var1, var2, var3, var4);
      }
   }
}
