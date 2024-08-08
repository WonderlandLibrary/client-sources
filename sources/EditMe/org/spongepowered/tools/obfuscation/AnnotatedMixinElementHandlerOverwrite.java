package org.spongepowered.tools.obfuscation;

import java.lang.reflect.Method;
import java.util.Iterator;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

class AnnotatedMixinElementHandlerOverwrite extends AnnotatedMixinElementHandler {
   AnnotatedMixinElementHandlerOverwrite(IMixinAnnotationProcessor var1, AnnotatedMixin var2) {
      super(var1, var2);
   }

   public void registerMerge(ExecutableElement var1) {
      this.validateTargetMethod(var1, (AnnotationHandle)null, new AnnotatedMixinElementHandler.AliasedElementName(var1, AnnotationHandle.MISSING), "overwrite", true, true);
   }

   public void registerOverwrite(AnnotatedMixinElementHandlerOverwrite.AnnotatedElementOverwrite var1) {
      AnnotatedMixinElementHandler.AliasedElementName var2 = new AnnotatedMixinElementHandler.AliasedElementName(var1.getElement(), var1.getAnnotation());
      this.validateTargetMethod((ExecutableElement)var1.getElement(), var1.getAnnotation(), var2, "@Overwrite", true, false);
      this.checkConstraints((ExecutableElement)var1.getElement(), var1.getAnnotation());
      if (var1.shouldRemap()) {
         Iterator var3 = this.mixin.getTargets().iterator();

         while(var3.hasNext()) {
            TypeHandle var4 = (TypeHandle)var3.next();
            if (!this.registerOverwriteForTarget(var1, var4)) {
               return;
            }
         }
      }

      if (!"true".equalsIgnoreCase(this.ap.getOption("disableOverwriteChecker"))) {
         Kind var5 = "error".equalsIgnoreCase(this.ap.getOption("overwriteErrorLevel")) ? Kind.ERROR : Kind.WARNING;
         String var6 = this.ap.getJavadocProvider().getJavadoc(var1.getElement());
         if (var6 == null) {
            this.ap.printMessage(var5, "@Overwrite is missing javadoc comment", var1.getElement());
            return;
         }

         if (!var6.toLowerCase().contains("@author")) {
            this.ap.printMessage(var5, "@Overwrite is missing an @author tag", var1.getElement());
         }

         if (!var6.toLowerCase().contains("@reason")) {
            this.ap.printMessage(var5, "@Overwrite is missing an @reason tag", var1.getElement());
         }
      }

   }

   private boolean registerOverwriteForTarget(AnnotatedMixinElementHandlerOverwrite.AnnotatedElementOverwrite var1, TypeHandle var2) {
      MappingMethod var3 = var2.getMappingMethod(var1.getSimpleName(), var1.getDesc());
      ObfuscationData var4 = this.obf.getDataProvider().getObfMethod(var3);
      if (var4.isEmpty()) {
         Kind var5 = Kind.ERROR;

         try {
            Method var6 = ((ExecutableElement)var1.getElement()).getClass().getMethod("isStatic");
            if ((Boolean)var6.invoke(var1.getElement())) {
               var5 = Kind.WARNING;
            }
         } catch (Exception var7) {
         }

         this.ap.printMessage(var5, "No obfuscation mapping for @Overwrite method", var1.getElement());
         return false;
      } else {
         try {
            this.addMethodMappings(var1.getSimpleName(), var1.getDesc(), var4);
            return true;
         } catch (Mappings.MappingConflictException var8) {
            var1.printMessage(this.ap, Kind.ERROR, "Mapping conflict for @Overwrite method: " + var8.getNew().getSimpleName() + " for target " + var2 + " conflicts with existing mapping " + var8.getOld().getSimpleName());
            return false;
         }
      }
   }

   static class AnnotatedElementOverwrite extends AnnotatedMixinElementHandler.AnnotatedElement {
      private final boolean shouldRemap;

      public AnnotatedElementOverwrite(ExecutableElement var1, AnnotationHandle var2, boolean var3) {
         super(var1, var2);
         this.shouldRemap = var3;
      }

      public boolean shouldRemap() {
         return this.shouldRemap;
      }
   }
}
