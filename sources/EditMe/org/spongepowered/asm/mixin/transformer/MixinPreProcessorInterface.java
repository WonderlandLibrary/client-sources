package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;
import org.spongepowered.asm.util.Bytecode;

class MixinPreProcessorInterface extends MixinPreProcessorStandard {
   MixinPreProcessorInterface(MixinInfo var1, MixinInfo.MixinClassNode var2) {
      super(var1, var2);
   }

   protected void prepareMethod(MixinInfo.MixinMethodNode var1, ClassInfo.Method var2) {
      if (!Bytecode.hasFlag((MethodNode)var1, 1) && !Bytecode.hasFlag((MethodNode)var1, 4096)) {
         throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains a non-public method! Found " + var2 + " in " + this.mixin);
      } else {
         super.prepareMethod(var1, var2);
      }
   }

   protected boolean validateField(MixinTargetContext var1, FieldNode var2, AnnotationNode var3) {
      if (!Bytecode.hasFlag((FieldNode)var2, 8)) {
         throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains an instance field! Found " + var2.name + " in " + this.mixin);
      } else {
         return super.validateField(var1, var2, var3);
      }
   }
}
