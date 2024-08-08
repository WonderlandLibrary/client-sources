package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

@SupportedAnnotationTypes({"org.spongepowered.asm.mixin.injection.Inject", "org.spongepowered.asm.mixin.injection.ModifyArg", "org.spongepowered.asm.mixin.injection.ModifyArgs", "org.spongepowered.asm.mixin.injection.Redirect", "org.spongepowered.asm.mixin.injection.At"})
public class MixinObfuscationProcessorInjection extends MixinObfuscationProcessor {
   public boolean process(Set var1, RoundEnvironment var2) {
      if (var2.processingOver()) {
         this.postProcess(var2);
         return true;
      } else {
         this.processMixins(var2);
         this.processInjectors(var2, Inject.class);
         this.processInjectors(var2, ModifyArg.class);
         this.processInjectors(var2, ModifyArgs.class);
         this.processInjectors(var2, Redirect.class);
         this.processInjectors(var2, ModifyVariable.class);
         this.processInjectors(var2, ModifyConstant.class);
         this.postProcess(var2);
         return true;
      }
   }

   protected void postProcess(RoundEnvironment var1) {
      super.postProcess(var1);

      try {
         this.mixins.writeReferences();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void processInjectors(RoundEnvironment var1, Class var2) {
      Iterator var3 = var1.getElementsAnnotatedWith(var2).iterator();

      while(var3.hasNext()) {
         Element var4 = (Element)var3.next();
         Element var5 = var4.getEnclosingElement();
         if (!(var5 instanceof TypeElement)) {
            throw new IllegalStateException("@" + var2.getSimpleName() + " element has unexpected parent with type " + TypeUtils.getElementType(var5));
         }

         AnnotationHandle var6 = AnnotationHandle.of(var4, var2);
         if (var4.getKind() == ElementKind.METHOD) {
            this.mixins.registerInjector((TypeElement)var5, (ExecutableElement)var4, var6);
         } else {
            this.mixins.printMessage(Kind.WARNING, "Found an @" + var2.getSimpleName() + " annotation on an element which is not a method: " + var4.toString());
         }
      }

   }
}
