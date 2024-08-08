package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.mapping.ResolvableMappingMethod;

public class TypeHandle {
   private final String name;
   private final PackageElement pkg;
   private final TypeElement element;
   private TypeReference reference;

   public TypeHandle(PackageElement var1, String var2) {
      this.name = var2.replace('.', '/');
      this.pkg = var1;
      this.element = null;
   }

   public TypeHandle(TypeElement var1) {
      this.pkg = TypeUtils.getPackage(var1);
      this.name = TypeUtils.getInternalName(var1);
      this.element = var1;
   }

   public TypeHandle(DeclaredType var1) {
      this((TypeElement)var1.asElement());
   }

   public final String toString() {
      return this.name.replace('/', '.');
   }

   public final String getName() {
      return this.name;
   }

   public final PackageElement getPackage() {
      return this.pkg;
   }

   public final TypeElement getElement() {
      return this.element;
   }

   protected TypeElement getTargetElement() {
      return this.element;
   }

   public AnnotationHandle getAnnotation(Class var1) {
      return AnnotationHandle.of(this.getTargetElement(), var1);
   }

   public final List getEnclosedElements() {
      return getEnclosedElements(this.getTargetElement());
   }

   public List getEnclosedElements(ElementKind... var1) {
      return getEnclosedElements(this.getTargetElement(), var1);
   }

   public TypeMirror getType() {
      return this.getTargetElement() != null ? this.getTargetElement().asType() : null;
   }

   public TypeHandle getSuperclass() {
      TypeElement var1 = this.getTargetElement();
      if (var1 == null) {
         return null;
      } else {
         TypeMirror var2 = var1.getSuperclass();
         return var2 != null && var2.getKind() != TypeKind.NONE ? new TypeHandle((DeclaredType)var2) : null;
      }
   }

   public List getInterfaces() {
      if (this.getTargetElement() == null) {
         return Collections.emptyList();
      } else {
         Builder var1 = ImmutableList.builder();
         Iterator var2 = this.getTargetElement().getInterfaces().iterator();

         while(var2.hasNext()) {
            TypeMirror var3 = (TypeMirror)var2.next();
            var1.add(new TypeHandle((DeclaredType)var3));
         }

         return var1.build();
      }
   }

   public boolean isPublic() {
      return this.getTargetElement() != null && this.getTargetElement().getModifiers().contains(Modifier.PUBLIC);
   }

   public boolean isImaginary() {
      return this.getTargetElement() == null;
   }

   public boolean isSimulated() {
      return false;
   }

   public final TypeReference getReference() {
      if (this.reference == null) {
         this.reference = new TypeReference(this);
      }

      return this.reference;
   }

   public MappingMethod getMappingMethod(String var1, String var2) {
      return new ResolvableMappingMethod(this, var1, var2);
   }

   public String findDescriptor(MemberInfo var1) {
      String var2 = var1.desc;
      if (var2 == null) {
         Iterator var3 = this.getEnclosedElements(ElementKind.METHOD).iterator();

         while(var3.hasNext()) {
            ExecutableElement var4 = (ExecutableElement)var3.next();
            if (var4.getSimpleName().toString().equals(var1.name)) {
               var2 = TypeUtils.getDescriptor(var4);
               break;
            }
         }
      }

      return var2;
   }

   public final FieldHandle findField(VariableElement var1) {
      return this.findField(var1, true);
   }

   public final FieldHandle findField(VariableElement var1, boolean var2) {
      return this.findField(var1.getSimpleName().toString(), TypeUtils.getTypeName(var1.asType()), var2);
   }

   public final FieldHandle findField(String var1, String var2) {
      return this.findField(var1, var2, true);
   }

   public FieldHandle findField(String var1, String var2, boolean var3) {
      String var4 = TypeUtils.stripGenerics(var2);
      Iterator var5 = this.getEnclosedElements(ElementKind.FIELD).iterator();

      VariableElement var6;
      do {
         if (!var5.hasNext()) {
            return null;
         }

         var6 = (VariableElement)var5.next();
         if (compareElement(var6, var1, var2, var3)) {
            return new FieldHandle(this.getTargetElement(), var6);
         }
      } while(!compareElement(var6, var1, var4, var3));

      return new FieldHandle(this.getTargetElement(), var6, true);
   }

   public final MethodHandle findMethod(ExecutableElement var1) {
      return this.findMethod(var1, true);
   }

   public final MethodHandle findMethod(ExecutableElement var1, boolean var2) {
      return this.findMethod(var1.getSimpleName().toString(), TypeUtils.getJavaSignature((Element)var1), var2);
   }

   public final MethodHandle findMethod(String var1, String var2) {
      return this.findMethod(var1, var2, true);
   }

   public MethodHandle findMethod(String var1, String var2, boolean var3) {
      String var4 = TypeUtils.stripGenerics(var2);
      return findMethod(this, var1, var2, var4, var3);
   }

   protected static MethodHandle findMethod(TypeHandle var0, String var1, String var2, String var3, boolean var4) {
      Iterator var5 = getEnclosedElements(var0.getTargetElement(), ElementKind.CONSTRUCTOR, ElementKind.METHOD).iterator();

      ExecutableElement var6;
      do {
         if (!var5.hasNext()) {
            return null;
         }

         var6 = (ExecutableElement)var5.next();
      } while(!compareElement(var6, var1, var2, var4) && !compareElement(var6, var1, var3, var4));

      return new MethodHandle(var0, var6);
   }

   protected static boolean compareElement(Element var0, String var1, String var2, boolean var3) {
      try {
         String var4 = var0.getSimpleName().toString();
         String var5 = TypeUtils.getJavaSignature(var0);
         String var6 = TypeUtils.stripGenerics(var5);
         boolean var7 = var3 ? var1.equals(var4) : var1.equalsIgnoreCase(var4);
         return var7 && (var2.length() == 0 || var2.equals(var5) || var2.equals(var6));
      } catch (NullPointerException var8) {
         return false;
      }
   }

   protected static List getEnclosedElements(TypeElement var0, ElementKind... var1) {
      if (var1 != null && var1.length >= 1) {
         if (var0 == null) {
            return Collections.emptyList();
         } else {
            Builder var2 = ImmutableList.builder();
            Iterator var3 = var0.getEnclosedElements().iterator();

            while(true) {
               while(var3.hasNext()) {
                  Element var4 = (Element)var3.next();
                  ElementKind[] var5 = var1;
                  int var6 = var1.length;

                  for(int var7 = 0; var7 < var6; ++var7) {
                     ElementKind var8 = var5[var7];
                     if (var4.getKind() == var8) {
                        var2.add(var4);
                        break;
                     }
                  }
               }

               return var2.build();
            }
         }
      } else {
         return getEnclosedElements(var0);
      }
   }

   protected static List getEnclosedElements(TypeElement var0) {
      return var0 != null ? var0.getEnclosedElements() : Collections.emptyList();
   }
}
