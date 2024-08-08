package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class MultiANewArrayInsnNode extends AbstractInsnNode {
   public String desc;
   public int dims;

   public MultiANewArrayInsnNode(String var1, int var2) {
      super(197);
      this.desc = var1;
      this.dims = var2;
   }

   public int getType() {
      return 13;
   }

   public void accept(MethodVisitor var1) {
      var1.visitMultiANewArrayInsn(this.desc, this.dims);
      this.acceptAnnotations(var1);
   }

   public AbstractInsnNode clone(Map var1) {
      return (new MultiANewArrayInsnNode(this.desc, this.dims)).cloneAnnotations(this);
   }
}
