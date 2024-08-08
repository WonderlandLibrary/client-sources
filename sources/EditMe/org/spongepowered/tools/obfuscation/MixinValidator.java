package org.spongepowered.tools.obfuscation;

import java.util.Collection;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IOptionProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;

public abstract class MixinValidator implements IMixinValidator {
   protected final ProcessingEnvironment processingEnv;
   protected final Messager messager;
   protected final IOptionProvider options;
   protected final IMixinValidator.ValidationPass pass;

   public MixinValidator(IMixinAnnotationProcessor var1, IMixinValidator.ValidationPass var2) {
      this.processingEnv = var1.getProcessingEnvironment();
      this.messager = var1;
      this.options = var1;
      this.pass = var2;
   }

   public final boolean validate(IMixinValidator.ValidationPass var1, TypeElement var2, AnnotationHandle var3, Collection var4) {
      return var1 != this.pass ? true : this.validate(var2, var3, var4);
   }

   protected abstract boolean validate(TypeElement var1, AnnotationHandle var2, Collection var3);

   protected final void note(String var1, Element var2) {
      this.messager.printMessage(Kind.NOTE, var1, var2);
   }

   protected final void warning(String var1, Element var2) {
      this.messager.printMessage(Kind.WARNING, var1, var2);
   }

   protected final void error(String var1, Element var2) {
      this.messager.printMessage(Kind.ERROR, var1, var2);
   }

   protected final Collection getMixinsTargeting(TypeMirror var1) {
      return AnnotatedMixins.getMixinsForEnvironment(this.processingEnv).getMixinsTargeting(var1);
   }
}
