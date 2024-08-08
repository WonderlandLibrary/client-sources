package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.modify.ModifyVariableInjector;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public class ModifyVariableInjectionInfo extends InjectionInfo {
   public ModifyVariableInjectionInfo(MixinTargetContext var1, MethodNode var2, AnnotationNode var3) {
      super(var1, var2, var3);
   }

   protected Injector parseInjector(AnnotationNode var1) {
      return new ModifyVariableInjector(this, LocalVariableDiscriminator.parse(var1));
   }

   protected String getDescription() {
      return "Variable modifier method";
   }
}
