package org.spongepowered.tools.obfuscation.validation;

import java.util.Collection;
import java.util.Iterator;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.tools.obfuscation.MixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class TargetValidator extends MixinValidator {
   public TargetValidator(IMixinAnnotationProcessor var1) {
      super(var1, IMixinValidator.ValidationPass.LATE);
   }

   public boolean validate(TypeElement var1, AnnotationHandle var2, Collection var3) {
      if ("true".equalsIgnoreCase(this.options.getOption("disableTargetValidator"))) {
         return true;
      } else {
         if (var1.getKind() == ElementKind.INTERFACE) {
            this.validateInterfaceMixin(var1, var3);
         } else {
            this.validateClassMixin(var1, var3);
         }

         return true;
      }
   }

   private void validateInterfaceMixin(TypeElement var1, Collection var2) {
      boolean var3 = false;
      Iterator var4 = var1.getEnclosedElements().iterator();

      while(true) {
         Element var5;
         do {
            if (!var4.hasNext()) {
               if (!var3) {
                  return;
               }

               var4 = var2.iterator();

               while(var4.hasNext()) {
                  TypeHandle var8 = (TypeHandle)var4.next();
                  TypeElement var9 = var8.getElement();
                  if (var9 != null && var9.getKind() != ElementKind.INTERFACE) {
                     this.error("Targetted type '" + var8 + " of " + var1 + " is not an interface", var1);
                  }
               }

               return;
            }

            var5 = (Element)var4.next();
         } while(var5.getKind() != ElementKind.METHOD);

         boolean var6 = AnnotationHandle.of(var5, Accessor.class).exists();
         boolean var7 = AnnotationHandle.of(var5, Invoker.class).exists();
         var3 |= !var6 && !var7;
      }
   }

   private void validateClassMixin(TypeElement var1, Collection var2) {
      TypeMirror var3 = var1.getSuperclass();
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         TypeHandle var5 = (TypeHandle)var4.next();
         TypeMirror var6 = var5.getType();
         if (var6 != null && !this.validateSuperClass(var6, var3)) {
            this.error("Superclass " + var3 + " of " + var1 + " was not found in the hierarchy of target class " + var6, var1);
         }
      }

   }

   private boolean validateSuperClass(TypeMirror var1, TypeMirror var2) {
      return TypeUtils.isAssignable(this.processingEnv, var1, var2) ? true : this.validateSuperClassRecursive(var1, var2);
   }

   private boolean validateSuperClassRecursive(TypeMirror var1, TypeMirror var2) {
      if (!(var1 instanceof DeclaredType)) {
         return false;
      } else if (TypeUtils.isAssignable(this.processingEnv, var1, var2)) {
         return true;
      } else {
         TypeElement var3 = (TypeElement)((DeclaredType)var1).asElement();
         TypeMirror var4 = var3.getSuperclass();
         if (var4.getKind() == TypeKind.NONE) {
            return false;
         } else {
            return this.checkMixinsFor(var4, var2) ? true : this.validateSuperClassRecursive(var4, var2);
         }
      }
   }

   private boolean checkMixinsFor(TypeMirror var1, TypeMirror var2) {
      Iterator var3 = this.getMixinsTargeting(var1).iterator();

      TypeMirror var4;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         var4 = (TypeMirror)var3.next();
      } while(!TypeUtils.isAssignable(this.processingEnv, var4, var2));

      return true;
   }
}
