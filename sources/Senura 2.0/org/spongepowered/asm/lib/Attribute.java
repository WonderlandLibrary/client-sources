/*     */ package org.spongepowered.asm.lib;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Attribute
/*     */ {
/*     */   public final String type;
/*     */   byte[] value;
/*     */   Attribute next;
/*     */   
/*     */   protected Attribute(String type) {
/*  62 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnknown() {
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCodeAttribute() {
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Label[] getLabels() {
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
/* 128 */     Attribute attr = new Attribute(this.type);
/* 129 */     attr.value = new byte[len];
/* 130 */     System.arraycopy(cr.b, off, attr.value, 0, len);
/* 131 */     return attr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
/* 161 */     ByteVector v = new ByteVector();
/* 162 */     v.data = this.value;
/* 163 */     v.length = this.value.length;
/* 164 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int getCount() {
/* 173 */     int count = 0;
/* 174 */     Attribute attr = this;
/* 175 */     while (attr != null) {
/* 176 */       count++;
/* 177 */       attr = attr.next;
/*     */     } 
/* 179 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int getSize(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
/* 209 */     Attribute attr = this;
/* 210 */     int size = 0;
/* 211 */     while (attr != null) {
/* 212 */       cw.newUTF8(attr.type);
/* 213 */       size += (attr.write(cw, code, len, maxStack, maxLocals)).length + 6;
/* 214 */       attr = attr.next;
/*     */     } 
/* 216 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void put(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals, ByteVector out) {
/* 247 */     Attribute attr = this;
/* 248 */     while (attr != null) {
/* 249 */       ByteVector b = attr.write(cw, code, len, maxStack, maxLocals);
/* 250 */       out.putShort(cw.newUTF8(attr.type)).putInt(b.length);
/* 251 */       out.putByteArray(b.data, 0, b.length);
/* 252 */       attr = attr.next;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\Attribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */