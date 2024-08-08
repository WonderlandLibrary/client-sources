package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;

public class AnnotationNode extends AnnotationVisitor {
   public String desc;
   public List values;

   public AnnotationNode(String var1) {
      this(327680, var1);
      if (this.getClass() != AnnotationNode.class) {
         throw new IllegalStateException();
      }
   }

   public AnnotationNode(int var1, String var2) {
      super(var1);
      this.desc = var2;
   }

   AnnotationNode(List var1) {
      super(327680);
      this.values = var1;
   }

   public void visit(String var1, Object var2) {
      if (this.values == null) {
         this.values = new ArrayList(this.desc != null ? 2 : 1);
      }

      if (this.desc != null) {
         this.values.add(var1);
      }

      ArrayList var4;
      int var6;
      int var7;
      if (var2 instanceof byte[]) {
         byte[] var3 = (byte[])((byte[])var2);
         var4 = new ArrayList(var3.length);
         byte[] var5 = var3;
         var6 = var3.length;

         for(var7 = 0; var7 < var6; ++var7) {
            byte var8 = var5[var7];
            var4.add(var8);
         }

         this.values.add(var4);
      } else if (var2 instanceof boolean[]) {
         boolean[] var11 = (boolean[])((boolean[])var2);
         var4 = new ArrayList(var11.length);
         boolean[] var17 = var11;
         var6 = var11.length;

         for(var7 = 0; var7 < var6; ++var7) {
            boolean var25 = var17[var7];
            var4.add(var25);
         }

         this.values.add(var4);
      } else if (var2 instanceof short[]) {
         short[] var12 = (short[])((short[])var2);
         var4 = new ArrayList(var12.length);
         short[] var19 = var12;
         var6 = var12.length;

         for(var7 = 0; var7 < var6; ++var7) {
            short var26 = var19[var7];
            var4.add(var26);
         }

         this.values.add(var4);
      } else if (var2 instanceof char[]) {
         char[] var13 = (char[])((char[])var2);
         var4 = new ArrayList(var13.length);
         char[] var20 = var13;
         var6 = var13.length;

         for(var7 = 0; var7 < var6; ++var7) {
            char var27 = var20[var7];
            var4.add(var27);
         }

         this.values.add(var4);
      } else if (var2 instanceof int[]) {
         int[] var14 = (int[])((int[])var2);
         var4 = new ArrayList(var14.length);
         int[] var21 = var14;
         var6 = var14.length;

         for(var7 = 0; var7 < var6; ++var7) {
            int var28 = var21[var7];
            var4.add(var28);
         }

         this.values.add(var4);
      } else if (var2 instanceof long[]) {
         long[] var15 = (long[])((long[])var2);
         var4 = new ArrayList(var15.length);
         long[] var22 = var15;
         var6 = var15.length;

         for(var7 = 0; var7 < var6; ++var7) {
            long var9 = var22[var7];
            var4.add(var9);
         }

         this.values.add(var4);
      } else if (var2 instanceof float[]) {
         float[] var16 = (float[])((float[])var2);
         var4 = new ArrayList(var16.length);
         float[] var23 = var16;
         var6 = var16.length;

         for(var7 = 0; var7 < var6; ++var7) {
            float var30 = var23[var7];
            var4.add(var30);
         }

         this.values.add(var4);
      } else if (var2 instanceof double[]) {
         double[] var18 = (double[])((double[])var2);
         var4 = new ArrayList(var18.length);
         double[] var24 = var18;
         var6 = var18.length;

         for(var7 = 0; var7 < var6; ++var7) {
            double var29 = var24[var7];
            var4.add(var29);
         }

         this.values.add(var4);
      } else {
         this.values.add(var2);
      }

   }

   public void visitEnum(String var1, String var2, String var3) {
      if (this.values == null) {
         this.values = new ArrayList(this.desc != null ? 2 : 1);
      }

      if (this.desc != null) {
         this.values.add(var1);
      }

      this.values.add(new String[]{var2, var3});
   }

   public AnnotationVisitor visitAnnotation(String var1, String var2) {
      if (this.values == null) {
         this.values = new ArrayList(this.desc != null ? 2 : 1);
      }

      if (this.desc != null) {
         this.values.add(var1);
      }

      AnnotationNode var3 = new AnnotationNode(var2);
      this.values.add(var3);
      return var3;
   }

   public AnnotationVisitor visitArray(String var1) {
      if (this.values == null) {
         this.values = new ArrayList(this.desc != null ? 2 : 1);
      }

      if (this.desc != null) {
         this.values.add(var1);
      }

      ArrayList var2 = new ArrayList();
      this.values.add(var2);
      return new AnnotationNode(var2);
   }

   public void visitEnd() {
   }

   public void check(int var1) {
   }

   public void accept(AnnotationVisitor var1) {
      if (var1 != null) {
         if (this.values != null) {
            for(int var2 = 0; var2 < this.values.size(); var2 += 2) {
               String var3 = (String)this.values.get(var2);
               Object var4 = this.values.get(var2 + 1);
               accept(var1, var3, var4);
            }
         }

         var1.visitEnd();
      }

   }

   static void accept(AnnotationVisitor var0, String var1, Object var2) {
      if (var0 != null) {
         if (var2 instanceof String[]) {
            String[] var3 = (String[])((String[])var2);
            var0.visitEnum(var1, var3[0], var3[1]);
         } else if (var2 instanceof AnnotationNode) {
            AnnotationNode var6 = (AnnotationNode)var2;
            var6.accept(var0.visitAnnotation(var1, var6.desc));
         } else if (var2 instanceof List) {
            AnnotationVisitor var7 = var0.visitArray(var1);
            if (var7 != null) {
               List var4 = (List)var2;

               for(int var5 = 0; var5 < var4.size(); ++var5) {
                  accept(var7, (String)null, var4.get(var5));
               }

               var7.visitEnd();
            }
         } else {
            var0.visit(var1, var2);
         }
      }

   }
}
