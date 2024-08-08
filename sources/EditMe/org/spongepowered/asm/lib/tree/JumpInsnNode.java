package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class JumpInsnNode extends AbstractInsnNode {
   public LabelNode label;

   public JumpInsnNode(int var1, LabelNode var2) {
      super(var1);
      this.label = var2;
   }

   public void setOpcode(int var1) {
      this.opcode = var1;
   }

   public int getType() {
      return 7;
   }

   public void accept(MethodVisitor var1) {
      var1.visitJumpInsn(this.opcode, this.label.getLabel());
      this.acceptAnnotations(var1);
   }

   public AbstractInsnNode clone(Map var1) {
      return (new JumpInsnNode(this.opcode, clone(this.label, var1))).cloneAnnotations(this);
   }
}
