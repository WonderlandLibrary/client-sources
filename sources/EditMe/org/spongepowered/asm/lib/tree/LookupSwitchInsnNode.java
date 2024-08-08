package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;

public class LookupSwitchInsnNode extends AbstractInsnNode {
   public LabelNode dflt;
   public List keys;
   public List labels;

   public LookupSwitchInsnNode(LabelNode var1, int[] var2, LabelNode[] var3) {
      super(171);
      this.dflt = var1;
      this.keys = new ArrayList(var2 == null ? 0 : var2.length);
      this.labels = new ArrayList(var3 == null ? 0 : var3.length);
      if (var2 != null) {
         for(int var4 = 0; var4 < var2.length; ++var4) {
            this.keys.add(var2[var4]);
         }
      }

      if (var3 != null) {
         this.labels.addAll(Arrays.asList(var3));
      }

   }

   public int getType() {
      return 12;
   }

   public void accept(MethodVisitor var1) {
      int[] var2 = new int[this.keys.size()];

      for(int var3 = 0; var3 < var2.length; ++var3) {
         var2[var3] = (Integer)this.keys.get(var3);
      }

      Label[] var5 = new Label[this.labels.size()];

      for(int var4 = 0; var4 < var5.length; ++var4) {
         var5[var4] = ((LabelNode)this.labels.get(var4)).getLabel();
      }

      var1.visitLookupSwitchInsn(this.dflt.getLabel(), var2, var5);
      this.acceptAnnotations(var1);
   }

   public AbstractInsnNode clone(Map var1) {
      LookupSwitchInsnNode var2 = new LookupSwitchInsnNode(clone(this.dflt, var1), (int[])null, clone(this.labels, var1));
      var2.keys.addAll(this.keys);
      return var2.cloneAnnotations(this);
   }
}
