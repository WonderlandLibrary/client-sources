package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public class LocalVariableAnnotationNode extends TypeAnnotationNode {
   public List start;
   public List end;
   public List index;

   public LocalVariableAnnotationNode(int var1, TypePath var2, LabelNode[] var3, LabelNode[] var4, int[] var5, String var6) {
      this(327680, var1, var2, var3, var4, var5, var6);
   }

   public LocalVariableAnnotationNode(int var1, int var2, TypePath var3, LabelNode[] var4, LabelNode[] var5, int[] var6, String var7) {
      super(var1, var2, var3, var7);
      this.start = new ArrayList(var4.length);
      this.start.addAll(Arrays.asList(var4));
      this.end = new ArrayList(var5.length);
      this.end.addAll(Arrays.asList(var5));
      this.index = new ArrayList(var6.length);
      int[] var8 = var6;
      int var9 = var6.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         int var11 = var8[var10];
         this.index.add(var11);
      }

   }

   public void accept(MethodVisitor var1, boolean var2) {
      Label[] var3 = new Label[this.start.size()];
      Label[] var4 = new Label[this.end.size()];
      int[] var5 = new int[this.index.size()];

      for(int var6 = 0; var6 < var3.length; ++var6) {
         var3[var6] = ((LabelNode)this.start.get(var6)).getLabel();
         var4[var6] = ((LabelNode)this.end.get(var6)).getLabel();
         var5[var6] = (Integer)this.index.get(var6);
      }

      this.accept(var1.visitLocalVariableAnnotation(this.typeRef, this.typePath, var3, var4, var5, this.desc, true));
   }
}
