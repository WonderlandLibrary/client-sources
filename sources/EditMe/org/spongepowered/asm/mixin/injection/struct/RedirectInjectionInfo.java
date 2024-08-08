package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.RedirectInjector;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public class RedirectInjectionInfo extends InjectionInfo {
   public RedirectInjectionInfo(MixinTargetContext var1, MethodNode var2, AnnotationNode var3) {
      super(var1, var2, var3);
   }

   protected Injector parseInjector(AnnotationNode var1) {
      return new RedirectInjector(this);
   }

   protected String getDescription() {
      return "Redirector";
   }
}
