package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class InsnNode extends AbstractInsnNode {
   public InsnNode(int var1) {
      super(var1);
   }

   public int getType() {
      return 0;
   }

   public void accept(MethodVisitor var1) {
      var1.visitInsn(this.opcode);
      this.acceptAnnotations(var1);
   }

   public AbstractInsnNode clone(Map var1) {
      return (new InsnNode(this.opcode)).cloneAnnotations(this);
   }
}
