package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class LdcInsnNode extends AbstractInsnNode {
   public Object cst;

   public LdcInsnNode(Object var1) {
      super(18);
      this.cst = var1;
   }

   public int getType() {
      return 9;
   }

   public void accept(MethodVisitor var1) {
      var1.visitLdcInsn(this.cst);
      this.acceptAnnotations(var1);
   }

   public AbstractInsnNode clone(Map var1) {
      return (new LdcInsnNode(this.cst)).cloneAnnotations(this);
   }
}
