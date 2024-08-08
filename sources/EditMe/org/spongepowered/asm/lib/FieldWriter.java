package org.spongepowered.asm.lib;

final class FieldWriter extends FieldVisitor {
   private final ClassWriter cw;
   private final int access;
   private final int name;
   private final int desc;
   private int signature;
   private int value;
   private AnnotationWriter anns;
   private AnnotationWriter ianns;
   private AnnotationWriter tanns;
   private AnnotationWriter itanns;
   private Attribute attrs;

   FieldWriter(ClassWriter var1, int var2, String var3, String var4, String var5, Object var6) {
      super(327680);
      if (var1.firstField == null) {
         var1.firstField = this;
      } else {
         var1.lastField.fv = this;
      }

      var1.lastField = this;
      this.cw = var1;
      this.access = var2;
      this.name = var1.newUTF8(var3);
      this.desc = var1.newUTF8(var4);
      if (var5 != null) {
         this.signature = var1.newUTF8(var5);
      }

      if (var6 != null) {
         this.value = var1.newConstItem(var6).index;
      }

   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      ByteVector var3 = new ByteVector();
      var3.putShort(this.cw.newUTF8(var1)).putShort(0);
      AnnotationWriter var4 = new AnnotationWriter(this.cw, true, var3, var3, 2);
      if (var2) {
         var4.next = this.anns;
         this.anns = var4;
      } else {
         var4.next = this.ianns;
         this.ianns = var4;
      }

      return var4;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      AnnotationWriter.putTarget(var1, var2, var5);
      var5.putShort(this.cw.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this.cw, true, var5, var5, var5.length - 2);
      if (var4) {
         var6.next = this.tanns;
         this.tanns = var6;
      } else {
         var6.next = this.itanns;
         this.itanns = var6;
      }

      return var6;
   }

   public void visitAttribute(Attribute var1) {
      var1.next = this.attrs;
      this.attrs = var1;
   }

   public void visitEnd() {
   }

   int getSize() {
      int var1 = 8;
      if (this.value != 0) {
         this.cw.newUTF8("ConstantValue");
         var1 += 8;
      }

      if ((this.access & 4096) != 0 && ((this.cw.version & '\uffff') < 49 || (this.access & 262144) != 0)) {
         this.cw.newUTF8("Synthetic");
         var1 += 6;
      }

      if ((this.access & 131072) != 0) {
         this.cw.newUTF8("Deprecated");
         var1 += 6;
      }

      if (this.signature != 0) {
         this.cw.newUTF8("Signature");
         var1 += 8;
      }

      if (this.anns != null) {
         this.cw.newUTF8("RuntimeVisibleAnnotations");
         var1 += 8 + this.anns.getSize();
      }

      if (this.ianns != null) {
         this.cw.newUTF8("RuntimeInvisibleAnnotations");
         var1 += 8 + this.ianns.getSize();
      }

      if (this.tanns != null) {
         this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
         var1 += 8 + this.tanns.getSize();
      }

      if (this.itanns != null) {
         this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
         var1 += 8 + this.itanns.getSize();
      }

      if (this.attrs != null) {
         var1 += this.attrs.getSize(this.cw, (byte[])null, 0, -1, -1);
      }

      return var1;
   }

   void put(ByteVector var1) {
      boolean var2 = true;
      int var3 = 393216 | (this.access & 262144) / 64;
      var1.putShort(this.access & ~var3).putShort(this.name).putShort(this.desc);
      int var4 = 0;
      if (this.value != 0) {
         ++var4;
      }

      if ((this.access & 4096) != 0 && ((this.cw.version & '\uffff') < 49 || (this.access & 262144) != 0)) {
         ++var4;
      }

      if ((this.access & 131072) != 0) {
         ++var4;
      }

      if (this.signature != 0) {
         ++var4;
      }

      if (this.anns != null) {
         ++var4;
      }

      if (this.ianns != null) {
         ++var4;
      }

      if (this.tanns != null) {
         ++var4;
      }

      if (this.itanns != null) {
         ++var4;
      }

      if (this.attrs != null) {
         var4 += this.attrs.getCount();
      }

      var1.putShort(var4);
      if (this.value != 0) {
         var1.putShort(this.cw.newUTF8("ConstantValue"));
         var1.putInt(2).putShort(this.value);
      }

      if ((this.access & 4096) != 0 && ((this.cw.version & '\uffff') < 49 || (this.access & 262144) != 0)) {
         var1.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
      }

      if ((this.access & 131072) != 0) {
         var1.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
      }

      if (this.signature != 0) {
         var1.putShort(this.cw.newUTF8("Signature"));
         var1.putInt(2).putShort(this.signature);
      }

      if (this.anns != null) {
         var1.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
         this.anns.put(var1);
      }

      if (this.ianns != null) {
         var1.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
         this.ianns.put(var1);
      }

      if (this.tanns != null) {
         var1.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
         this.tanns.put(var1);
      }

      if (this.itanns != null) {
         var1.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
         this.itanns.put(var1);
      }

      if (this.attrs != null) {
         this.attrs.put(this.cw, (byte[])null, 0, -1, -1, var1);
      }

   }
}
