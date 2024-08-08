package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.MethodVisitor;

public class InvokeDynamicInsnNode extends AbstractInsnNode {
   public String name;
   public String desc;
   public Handle bsm;
   public Object[] bsmArgs;

   public InvokeDynamicInsnNode(String var1, String var2, Handle var3, Object... var4) {
      super(186);
      this.name = var1;
      this.desc = var2;
      this.bsm = var3;
      this.bsmArgs = var4;
   }

   public int getType() {
      return 6;
   }

   public void accept(MethodVisitor var1) {
      var1.visitInvokeDynamicInsn(this.name, this.desc, this.bsm, this.bsmArgs);
      this.acceptAnnotations(var1);
   }

   public AbstractInsnNode clone(Map var1) {
      return (new InvokeDynamicInsnNode(this.name, this.desc, this.bsm, this.bsmArgs)).cloneAnnotations(this);
   }
}
