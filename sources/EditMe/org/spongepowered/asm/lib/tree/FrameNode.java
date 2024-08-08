package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class FrameNode extends AbstractInsnNode {
   public int type;
   public List local;
   public List stack;

   private FrameNode() {
      super(-1);
   }

   public FrameNode(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      super(-1);
      this.type = var1;
      switch(var1) {
      case -1:
      case 0:
         this.local = asList(var2, var3);
         this.stack = asList(var4, var5);
         break;
      case 1:
         this.local = asList(var2, var3);
         break;
      case 2:
         this.local = Arrays.asList();
      case 3:
      default:
         break;
      case 4:
         this.stack = asList(1, var5);
      }

   }

   public int getType() {
      return 14;
   }

   public void accept(MethodVisitor var1) {
      switch(this.type) {
      case -1:
      case 0:
         var1.visitFrame(this.type, this.local.size(), asArray(this.local), this.stack.size(), asArray(this.stack));
         break;
      case 1:
         var1.visitFrame(this.type, this.local.size(), asArray(this.local), 0, (Object[])null);
         break;
      case 2:
         var1.visitFrame(this.type, this.local.size(), (Object[])null, 0, (Object[])null);
         break;
      case 3:
         var1.visitFrame(this.type, 0, (Object[])null, 0, (Object[])null);
         break;
      case 4:
         var1.visitFrame(this.type, 0, (Object[])null, 1, asArray(this.stack));
      }

   }

   public AbstractInsnNode clone(Map var1) {
      FrameNode var2 = new FrameNode();
      var2.type = this.type;
      int var3;
      Object var4;
      if (this.local != null) {
         var2.local = new ArrayList();

         for(var3 = 0; var3 < this.local.size(); ++var3) {
            var4 = this.local.get(var3);
            if (var4 instanceof LabelNode) {
               var4 = var1.get(var4);
            }

            var2.local.add(var4);
         }
      }

      if (this.stack != null) {
         var2.stack = new ArrayList();

         for(var3 = 0; var3 < this.stack.size(); ++var3) {
            var4 = this.stack.get(var3);
            if (var4 instanceof LabelNode) {
               var4 = var1.get(var4);
            }

            var2.stack.add(var4);
         }
      }

      return var2;
   }

   private static List asList(int var0, Object[] var1) {
      return Arrays.asList(var1).subList(0, var0);
   }

   private static Object[] asArray(List var0) {
      Object[] var1 = new Object[var0.size()];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         Object var3 = var0.get(var2);
         if (var3 instanceof LabelNode) {
            var3 = ((LabelNode)var3).getLabel();
         }

         var1[var2] = var3;
      }

      return var1;
   }
}
