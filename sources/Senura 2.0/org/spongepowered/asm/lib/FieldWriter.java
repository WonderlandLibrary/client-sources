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
/*     */ final class FieldWriter
/*     */   extends FieldVisitor
/*     */ {
/*     */   private final ClassWriter cw;
/*     */   private final int access;
/*     */   private final int name;
/*     */   private final int desc;
/*     */   private int signature;
/*     */   private int value;
/*     */   private AnnotationWriter anns;
/*     */   private AnnotationWriter ianns;
/*     */   private AnnotationWriter tanns;
/*     */   private AnnotationWriter itanns;
/*     */   private Attribute attrs;
/*     */   
/*     */   FieldWriter(ClassWriter cw, int access, String name, String desc, String signature, Object value) {
/* 121 */     super(327680);
/* 122 */     if (cw.firstField == null) {
/* 123 */       cw.firstField = this;
/*     */     } else {
/* 125 */       cw.lastField.fv = this;
/*     */     } 
/* 127 */     cw.lastField = this;
/* 128 */     this.cw = cw;
/* 129 */     this.access = access;
/* 130 */     this.name = cw.newUTF8(name);
/* 131 */     this.desc = cw.newUTF8(desc);
/* 132 */     if (signature != null) {
/* 133 */       this.signature = cw.newUTF8(signature);
/*     */     }
/* 135 */     if (value != null) {
/* 136 */       this.value = (cw.newConstItem(value)).index;
/*     */     }
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
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 150 */     ByteVector bv = new ByteVector();
/*     */     
/* 152 */     bv.putShort(this.cw.newUTF8(desc)).putShort(0);
/* 153 */     AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, 2);
/* 154 */     if (visible) {
/* 155 */       aw.next = this.anns;
/* 156 */       this.anns = aw;
/*     */     } else {
/* 158 */       aw.next = this.ianns;
/* 159 */       this.ianns = aw;
/*     */     } 
/* 161 */     return aw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 170 */     ByteVector bv = new ByteVector();
/*     */     
/* 172 */     AnnotationWriter.putTarget(typeRef, typePath, bv);
/*     */     
/* 174 */     bv.putShort(this.cw.newUTF8(desc)).putShort(0);
/* 175 */     AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, bv.length - 2);
/*     */     
/* 177 */     if (visible) {
/* 178 */       aw.next = this.tanns;
/* 179 */       this.tanns = aw;
/*     */     } else {
/* 181 */       aw.next = this.itanns;
/* 182 */       this.itanns = aw;
/*     */     } 
/* 184 */     return aw;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/* 189 */     attr.next = this.attrs;
/* 190 */     this.attrs = attr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getSize() {
/* 207 */     int size = 8;
/* 208 */     if (this.value != 0) {
/* 209 */       this.cw.newUTF8("ConstantValue");
/* 210 */       size += 8;
/*     */     } 
/* 212 */     if ((this.access & 0x1000) != 0 && ((
/* 213 */       this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0)) {
/*     */       
/* 215 */       this.cw.newUTF8("Synthetic");
/* 216 */       size += 6;
/*     */     } 
/*     */     
/* 219 */     if ((this.access & 0x20000) != 0) {
/* 220 */       this.cw.newUTF8("Deprecated");
/* 221 */       size += 6;
/*     */     } 
/* 223 */     if (this.signature != 0) {
/* 224 */       this.cw.newUTF8("Signature");
/* 225 */       size += 8;
/*     */     } 
/* 227 */     if (this.anns != null) {
/* 228 */       this.cw.newUTF8("RuntimeVisibleAnnotations");
/* 229 */       size += 8 + this.anns.getSize();
/*     */     } 
/* 231 */     if (this.ianns != null) {
/* 232 */       this.cw.newUTF8("RuntimeInvisibleAnnotations");
/* 233 */       size += 8 + this.ianns.getSize();
/*     */     } 
/* 235 */     if (this.tanns != null) {
/* 236 */       this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
/* 237 */       size += 8 + this.tanns.getSize();
/*     */     } 
/* 239 */     if (this.itanns != null) {
/* 240 */       this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
/* 241 */       size += 8 + this.itanns.getSize();
/*     */     } 
/* 243 */     if (this.attrs != null) {
/* 244 */       size += this.attrs.getSize(this.cw, null, 0, -1, -1);
/*     */     }
/* 246 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void put(ByteVector out) {
/* 256 */     int FACTOR = 64;
/* 257 */     int mask = 0x60000 | (this.access & 0x40000) / 64;
/*     */     
/* 259 */     out.putShort(this.access & (mask ^ 0xFFFFFFFF)).putShort(this.name).putShort(this.desc);
/* 260 */     int attributeCount = 0;
/* 261 */     if (this.value != 0) {
/* 262 */       attributeCount++;
/*     */     }
/* 264 */     if ((this.access & 0x1000) != 0 && ((
/* 265 */       this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0))
/*     */     {
/* 267 */       attributeCount++;
/*     */     }
/*     */     
/* 270 */     if ((this.access & 0x20000) != 0) {
/* 271 */       attributeCount++;
/*     */     }
/* 273 */     if (this.signature != 0) {
/* 274 */       attributeCount++;
/*     */     }
/* 276 */     if (this.anns != null) {
/* 277 */       attributeCount++;
/*     */     }
/* 279 */     if (this.ianns != null) {
/* 280 */       attributeCount++;
/*     */     }
/* 282 */     if (this.tanns != null) {
/* 283 */       attributeCount++;
/*     */     }
/* 285 */     if (this.itanns != null) {
/* 286 */       attributeCount++;
/*     */     }
/* 288 */     if (this.attrs != null) {
/* 289 */       attributeCount += this.attrs.getCount();
/*     */     }
/* 291 */     out.putShort(attributeCount);
/* 292 */     if (this.value != 0) {
/* 293 */       out.putShort(this.cw.newUTF8("ConstantValue"));
/* 294 */       out.putInt(2).putShort(this.value);
/*     */     } 
/* 296 */     if ((this.access & 0x1000) != 0 && ((
/* 297 */       this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0))
/*     */     {
/* 299 */       out.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
/*     */     }
/*     */     
/* 302 */     if ((this.access & 0x20000) != 0) {
/* 303 */       out.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
/*     */     }
/* 305 */     if (this.signature != 0) {
/* 306 */       out.putShort(this.cw.newUTF8("Signature"));
/* 307 */       out.putInt(2).putShort(this.signature);
/*     */     } 
/* 309 */     if (this.anns != null) {
/* 310 */       out.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
/* 311 */       this.anns.put(out);
/*     */     } 
/* 313 */     if (this.ianns != null) {
/* 314 */       out.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
/* 315 */       this.ianns.put(out);
/*     */     } 
/* 317 */     if (this.tanns != null) {
/* 318 */       out.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
/* 319 */       this.tanns.put(out);
/*     */     } 
/* 321 */     if (this.itanns != null) {
/* 322 */       out.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
/* 323 */       this.itanns.put(out);
/*     */     } 
/* 325 */     if (this.attrs != null)
/* 326 */       this.attrs.put(this.cw, null, 0, -1, -1, out); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\FieldWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */