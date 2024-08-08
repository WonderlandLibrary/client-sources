package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class VarInsnNode extends AbstractInsnNode {
   public int var;

   public VarInsnNode(int var1, int var2) {
      super(var1);
      this.var = var2;
   }

   public void setOpcode(int var1) {
      this.opcode = var1;
   }

   public int getType() {
      return 2;
   }

   public void accept(MethodVisitor var1) {
      var1.visitVarInsn(this.opcode, this.var);
      this.acceptAnnotations(var1);
   }

   public AbstractInsnNode clone(Map var1) {
      return (new VarInsnNode(this.opcode, this.var)).cloneAnnotations(this);
   }
}
