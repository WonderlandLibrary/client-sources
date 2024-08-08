package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;

public class TableSwitchInsnNode extends AbstractInsnNode {
   public int min;
   public int max;
   public LabelNode dflt;
   public List labels;

   public TableSwitchInsnNode(int var1, int var2, LabelNode var3, LabelNode... var4) {
      super(170);
      this.min = var1;
      this.max = var2;
      this.dflt = var3;
      this.labels = new ArrayList();
      if (var4 != null) {
         this.labels.addAll(Arrays.asList(var4));
      }

   }

   public int getType() {
      return 11;
   }

   public void accept(MethodVisitor var1) {
      Label[] var2 = new Label[this.labels.size()];

      for(int var3 = 0; var3 < var2.length; ++var3) {
         var2[var3] = ((LabelNode)this.labels.get(var3)).getLabel();
      }

      var1.visitTableSwitchInsn(this.min, this.max, this.dflt.getLabel(), var2);
      this.acceptAnnotations(var1);
   }

   public AbstractInsnNode clone(Map var1) {
      return (new TableSwitchInsnNode(this.min, this.max, clone(this.dflt, var1), clone(this.labels, var1))).cloneAnnotations(this);
   }
}
