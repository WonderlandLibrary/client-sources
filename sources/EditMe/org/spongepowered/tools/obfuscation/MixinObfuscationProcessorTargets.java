package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

@SupportedAnnotationTypes({"org.spongepowered.asm.mixin.Mixin", "org.spongepowered.asm.mixin.Shadow", "org.spongepowered.asm.mixin.Overwrite", "org.spongepowered.asm.mixin.gen.Accessor", "org.spongepowered.asm.mixin.Implements"})
public class MixinObfuscationProcessorTargets extends MixinObfuscationProcessor {
   public boolean process(Set var1, RoundEnvironment var2) {
      if (var2.processingOver()) {
         this.postProcess(var2);
         return true;
      } else {
         this.processMixins(var2);
         this.processShadows(var2);
         this.processOverwrites(var2);
         this.processAccessors(var2);
         this.processInvokers(var2);
         this.processImplements(var2);
         this.postProcess(var2);
         return true;
      }
   }

   protected void postProcess(RoundEnvironment var1) {
      super.postProcess(var1);

      try {
         this.mixins.writeReferences();
         this.mixins.writeMappings();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void processShadows(RoundEnvironment var1) {
      Iterator var2 = var1.getElementsAnnotatedWith(Shadow.class).iterator();

      while(var2.hasNext()) {
         Element var3 = (Element)var2.next();
         Element var4 = var3.getEnclosingElement();
         if (!(var4 instanceof TypeElement)) {
            this.mixins.printMessage(Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(var4), var3);
         } else {
            AnnotationHandle var5 = AnnotationHandle.of(var3, Shadow.class);
            if (var3.getKind() == ElementKind.FIELD) {
               this.mixins.registerShadow((TypeElement)var4, (VariableElement)var3, var5);
            } else if (var3.getKind() == ElementKind.METHOD) {
               this.mixins.registerShadow((TypeElement)var4, (ExecutableElement)var3, var5);
            } else {
               this.mixins.printMessage(Kind.ERROR, "Element is not a method or field", var3);
            }
         }
      }

   }

   private void processOverwrites(RoundEnvironment var1) {
      Iterator var2 = var1.getElementsAnnotatedWith(Overwrite.class).iterator();

      while(var2.hasNext()) {
         Element var3 = (Element)var2.next();
         Element var4 = var3.getEnclosingElement();
         if (!(var4 instanceof TypeElement)) {
            this.mixins.printMessage(Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(var4), var3);
         } else if (var3.getKind() == ElementKind.METHOD) {
            this.mixins.registerOverwrite((TypeElement)var4, (ExecutableElement)var3);
         } else {
            this.mixins.printMessage(Kind.ERROR, "Element is not a method", var3);
         }
      }

   }

   private void processAccessors(RoundEnvironment var1) {
      Iterator var2 = var1.getElementsAnnotatedWith(Accessor.class).iterator();

      while(var2.hasNext()) {
         Element var3 = (Element)var2.next();
         Element var4 = var3.getEnclosingElement();
         if (!(var4 instanceof TypeElement)) {
            this.mixins.printMessage(Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(var4), var3);
         } else if (var3.getKind() == ElementKind.METHOD) {
            this.mixins.registerAccessor((TypeElement)var4, (ExecutableElement)var3);
         } else {
            this.mixins.printMessage(Kind.ERROR, "Element is not a method", var3);
         }
      }

   }

   private void processInvokers(RoundEnvironment var1) {
      Iterator var2 = var1.getElementsAnnotatedWith(Invoker.class).iterator();

      while(var2.hasNext()) {
         Element var3 = (Element)var2.next();
         Element var4 = var3.getEnclosingElement();
         if (!(var4 instanceof TypeElement)) {
            this.mixins.printMessage(Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(var4), var3);
         } else if (var3.getKind() == ElementKind.METHOD) {
            this.mixins.registerInvoker((TypeElement)var4, (ExecutableElement)var3);
         } else {
            this.mixins.printMessage(Kind.ERROR, "Element is not a method", var3);
         }
      }

   }

   private void processImplements(RoundEnvironment var1) {
      Iterator var2 = var1.getElementsAnnotatedWith(Implements.class).iterator();

      while(true) {
         while(var2.hasNext()) {
            Element var3 = (Element)var2.next();
            if (var3.getKind() != ElementKind.CLASS && var3.getKind() != ElementKind.INTERFACE) {
               this.mixins.printMessage(Kind.ERROR, "Found an @Implements annotation on an element which is not a class or interface", var3);
            } else {
               AnnotationHandle var4 = AnnotationHandle.of(var3, Implements.class);
               this.mixins.registerSoftImplements((TypeElement)var3, var4);
            }
         }

         return;
      }
   }
}
