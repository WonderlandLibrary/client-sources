package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyArgInjector;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

public class ModifyArgInjectionInfo extends InjectionInfo {
   public ModifyArgInjectionInfo(MixinTargetContext var1, MethodNode var2, AnnotationNode var3) {
      super(var1, var2, var3);
   }

   protected Injector parseInjector(AnnotationNode var1) {
      int var2 = (Integer)Annotations.getValue(var1, "index", (int)-1);
      return new ModifyArgInjector(this, var2);
   }

   protected String getDescription() {
      return "Argument modifier method";
   }
}
