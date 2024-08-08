package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class AnalyzerException extends Exception {
   public final AbstractInsnNode node;

   public AnalyzerException(AbstractInsnNode var1, String var2) {
      super(var2);
      this.node = var1;
   }

   public AnalyzerException(AbstractInsnNode var1, String var2, Throwable var3) {
      super(var2, var3);
      this.node = var1;
   }

   public AnalyzerException(AbstractInsnNode var1, String var2, Object var3, Value var4) {
      super((var2 == null ? "Expected " : var2 + ": expected ") + var3 + ", but found " + var4);
      this.node = var1;
   }
}
