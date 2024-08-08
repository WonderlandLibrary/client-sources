package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Type;

public class CheckAnnotationAdapter extends AnnotationVisitor {
   private final boolean named;
   private boolean end;

   public CheckAnnotationAdapter(AnnotationVisitor var1) {
      this(var1, true);
   }

   CheckAnnotationAdapter(AnnotationVisitor var1, boolean var2) {
      super(327680, var1);
      this.named = var2;
   }

   public void visit(String var1, Object var2) {
      this.checkEnd();
      this.checkName(var1);
      if (!(var2 instanceof Byte) && !(var2 instanceof Boolean) && !(var2 instanceof Character) && !(var2 instanceof Short) && !(var2 instanceof Integer) && !(var2 instanceof Long) && !(var2 instanceof Float) && !(var2 instanceof Double) && !(var2 instanceof String) && !(var2 instanceof Type) && !(var2 instanceof byte[]) && !(var2 instanceof boolean[]) && !(var2 instanceof char[]) && !(var2 instanceof short[]) && !(var2 instanceof int[]) && !(var2 instanceof long[]) && !(var2 instanceof float[]) && !(var2 instanceof double[])) {
         throw new IllegalArgumentException("Invalid annotation value");
      } else {
         if (var2 instanceof Type) {
            int var3 = ((Type)var2).getSort();
            if (var3 == 11) {
               throw new IllegalArgumentException("Invalid annotation value");
            }
         }

         if (this.av != null) {
            this.av.visit(var1, var2);
         }

      }
   }

   public void visitEnum(String var1, String var2, String var3) {
      this.checkEnd();
      this.checkName(var1);
      CheckMethodAdapter.checkDesc(var2, false);
      if (var3 == null) {
         throw new IllegalArgumentException("Invalid enum value");
      } else {
         if (this.av != null) {
            this.av.visitEnum(var1, var2, var3);
         }

      }
   }

   public AnnotationVisitor visitAnnotation(String var1, String var2) {
      this.checkEnd();
      this.checkName(var1);
      CheckMethodAdapter.checkDesc(var2, false);
      return new CheckAnnotationAdapter(this.av == null ? null : this.av.visitAnnotation(var1, var2));
   }

   public AnnotationVisitor visitArray(String var1) {
      this.checkEnd();
      this.checkName(var1);
      return new CheckAnnotationAdapter(this.av == null ? null : this.av.visitArray(var1), false);
   }

   public void visitEnd() {
      this.checkEnd();
      this.end = true;
      if (this.av != null) {
         this.av.visitEnd();
      }

   }

   private void checkEnd() {
      if (this.end) {
         throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
      }
   }

   private void checkName(String var1) {
      if (this.named && var1 == null) {
         throw new IllegalArgumentException("Annotation value name must not be null");
      }
   }
}
