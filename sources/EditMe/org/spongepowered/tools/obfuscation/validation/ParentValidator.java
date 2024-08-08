package org.spongepowered.tools.obfuscation.validation;

import java.util.Collection;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import org.spongepowered.tools.obfuscation.MixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;

public class ParentValidator extends MixinValidator {
   public ParentValidator(IMixinAnnotationProcessor var1) {
      super(var1, IMixinValidator.ValidationPass.EARLY);
   }

   public boolean validate(TypeElement var1, AnnotationHandle var2, Collection var3) {
      if (var1.getEnclosingElement().getKind() != ElementKind.PACKAGE && !var1.getModifiers().contains(Modifier.STATIC)) {
         this.error("Inner class mixin must be declared static", var1);
      }

      return true;
   }
}
