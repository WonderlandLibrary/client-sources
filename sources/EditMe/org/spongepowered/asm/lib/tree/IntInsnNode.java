package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class IntInsnNode extends AbstractInsnNode {
   public int operand;

   public IntInsnNode(int var1, int var2) {
      super(var1);
      this.operand = var2;
   }

   public void setOpcode(int var1) {
      this.opcode = var1;
   }

   public int getType() {
      return 1;
   }

   public void accept(MethodVisitor var1) {
      var1.visitIntInsn(this.opcode, this.operand);
      this.acceptAnnotations(var1);
   }

   public AbstractInsnNode clone(Map var1) {
      return (new IntInsnNode(this.opcode, this.operand)).cloneAnnotations(this);
   }
}
