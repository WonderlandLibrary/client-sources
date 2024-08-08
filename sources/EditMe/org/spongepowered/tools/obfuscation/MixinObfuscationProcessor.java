package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.Mixin;

public abstract class MixinObfuscationProcessor extends AbstractProcessor {
   protected AnnotatedMixins mixins;

   public synchronized void init(ProcessingEnvironment var1) {
      super.init(var1);
      this.mixins = AnnotatedMixins.getMixinsForEnvironment(var1);
   }

   protected void processMixins(RoundEnvironment var1) {
      this.mixins.onPassStarted();
      Iterator var2 = var1.getElementsAnnotatedWith(Mixin.class).iterator();

      while(true) {
         while(var2.hasNext()) {
            Element var3 = (Element)var2.next();
            if (var3.getKind() != ElementKind.CLASS && var3.getKind() != ElementKind.INTERFACE) {
               this.mixins.printMessage(Kind.ERROR, "Found an @Mixin annotation on an element which is not a class or interface", var3);
            } else {
               this.mixins.registerMixin((TypeElement)var3);
            }
         }

         return;
      }
   }

   protected void postProcess(RoundEnvironment var1) {
      this.mixins.onPassCompleted(var1);
   }

   public SourceVersion getSupportedSourceVersion() {
      try {
         return SourceVersion.valueOf("RELEASE_8");
      } catch (IllegalArgumentException var2) {
         return super.getSupportedSourceVersion();
      }
   }

   public Set getSupportedOptions() {
      return SupportedOptions.getAllOptions();
   }
}
