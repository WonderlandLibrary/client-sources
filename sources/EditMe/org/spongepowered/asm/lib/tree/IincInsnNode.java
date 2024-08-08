package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class IincInsnNode extends AbstractInsnNode {
   public int var;
   public int incr;

   public IincInsnNode(int var1, int var2) {
      super(132);
      this.var = var1;
      this.incr = var2;
   }

   public int getType() {
      return 10;
   }

   public void accept(MethodVisitor var1) {
      var1.visitIincInsn(this.var, this.incr);
      this.acceptAnnotations(var1);
   }

   public AbstractInsnNode clone(Map var1) {
      return (new IincInsnNode(this.var, this.incr)).cloneAnnotations(this);
   }
}
