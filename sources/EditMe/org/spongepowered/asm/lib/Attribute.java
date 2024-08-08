package org.spongepowered.asm.lib;

public class Attribute {
   public final String type;
   byte[] value;
   Attribute next;

   protected Attribute(String var1) {
      this.type = var1;
   }

   public boolean isUnknown() {
      return true;
   }

   public boolean isCodeAttribute() {
      return false;
   }

   protected Label[] getLabels() {
      return null;
   }

   protected Attribute read(ClassReader var1, int var2, int var3, char[] var4, int var5, Label[] var6) {
      Attribute var7 = new Attribute(this.type);
      var7.value = new byte[var3];
      System.arraycopy(var1.b, var2, var7.value, 0, var3);
      return var7;
   }

   protected ByteVector write(ClassWriter var1, byte[] var2, int var3, int var4, int var5) {
      ByteVector var6 = new ByteVector();
      var6.data = this.value;
      var6.length = this.value.length;
      return var6;
   }

   final int getCount() {
      int var1 = 0;

      for(Attribute var2 = this; var2 != null; var2 = var2.next) {
         ++var1;
      }

      return var1;
   }

   final int getSize(ClassWriter var1, byte[] var2, int var3, int var4, int var5) {
      Attribute var6 = this;

      int var7;
      for(var7 = 0; var6 != null; var6 = var6.next) {
         var1.newUTF8(var6.type);
         var7 += var6.write(var1, var2, var3, var4, var5).length + 6;
      }

      return var7;
   }

   final void put(ClassWriter var1, byte[] var2, int var3, int var4, int var5, ByteVector var6) {
      for(Attribute var7 = this; var7 != null; var7 = var7.next) {
         ByteVector var8 = var7.write(var1, var2, var3, var4, var5);
         var6.putShort(var1.newUTF8(var7.type)).putInt(var8.length);
         var6.putByteArray(var8.data, 0, var8.length);
      }

   }
}
