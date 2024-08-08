package org.spongepowered.asm.util.asm;

import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.util.Bytecode;

public class MethodVisitorEx extends MethodVisitor {
   public MethodVisitorEx(MethodVisitor var1) {
      super(327680, var1);
   }

   public void visitConstant(byte var1) {
      if (var1 > -2 && var1 < 6) {
         this.visitInsn(Bytecode.CONSTANTS_INT[var1 + 1]);
      } else {
         this.visitIntInsn(16, var1);
      }
   }
}
