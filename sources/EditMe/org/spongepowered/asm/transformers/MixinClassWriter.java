package org.spongepowered.asm.transformers;

import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

public class MixinClassWriter extends ClassWriter {
   public MixinClassWriter(int var1) {
      super(var1);
   }

   public MixinClassWriter(ClassReader var1, int var2) {
      super(var1, var2);
   }

   protected String getCommonSuperClass(String var1, String var2) {
      return ClassInfo.getCommonSuperClass(var1, var2).getName();
   }
}
