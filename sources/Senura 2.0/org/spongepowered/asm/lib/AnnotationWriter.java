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
/*     */ final class AnnotationWriter
/*     */   extends AnnotationVisitor
/*     */ {
/*     */   private final ClassWriter cw;
/*     */   private int size;
/*     */   private final boolean named;
/*     */   private final ByteVector bv;
/*     */   private final ByteVector parent;
/*     */   private final int offset;
/*     */   AnnotationWriter next;
/*     */   AnnotationWriter prev;
/*     */   
/*     */   AnnotationWriter(ClassWriter cw, boolean named, ByteVector bv, ByteVector parent, int offset) {
/* 107 */     super(327680);
/* 108 */     this.cw = cw;
/* 109 */     this.named = named;
/* 110 */     this.bv = bv;
/* 111 */     this.parent = parent;
/* 112 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(String name, Object value) {
/* 121 */     this.size++;
/* 122 */     if (this.named) {
/* 123 */       this.bv.putShort(this.cw.newUTF8(name));
/*     */     }
/* 125 */     if (value instanceof String) {
/* 126 */       this.bv.put12(115, this.cw.newUTF8((String)value));
/* 127 */     } else if (value instanceof Byte) {
/* 128 */       this.bv.put12(66, (this.cw.newInteger(((Byte)value).byteValue())).index);
/* 129 */     } else if (value instanceof Boolean) {
/* 130 */       int v = ((Boolean)value).booleanValue() ? 1 : 0;
/* 131 */       this.bv.put12(90, (this.cw.newInteger(v)).index);
/* 132 */     } else if (value instanceof Character) {
/* 133 */       this.bv.put12(67, (this.cw.newInteger(((Character)value).charValue())).index);
/* 134 */     } else if (value instanceof Short) {
/* 135 */       this.bv.put12(83, (this.cw.newInteger(((Short)value).shortValue())).index);
/* 136 */     } else if (value instanceof Type) {
/* 137 */       this.bv.put12(99, this.cw.newUTF8(((Type)value).getDescriptor()));
/* 138 */     } else if (value instanceof byte[]) {
/* 139 */       byte[] v = (byte[])value;
/* 140 */       this.bv.put12(91, v.length);
/* 141 */       for (int i = 0; i < v.length; i++) {
/* 142 */         this.bv.put12(66, (this.cw.newInteger(v[i])).index);
/*     */       }
/* 144 */     } else if (value instanceof boolean[]) {
/* 145 */       boolean[] v = (boolean[])value;
/* 146 */       this.bv.put12(91, v.length);
/* 147 */       for (int i = 0; i < v.length; i++) {
/* 148 */         this.bv.put12(90, (this.cw.newInteger(v[i] ? 1 : 0)).index);
/*     */       }
/* 150 */     } else if (value instanceof short[]) {
/* 151 */       short[] v = (short[])value;
/* 152 */       this.bv.put12(91, v.length);
/* 153 */       for (int i = 0; i < v.length; i++) {
/* 154 */         this.bv.put12(83, (this.cw.newInteger(v[i])).index);
/*     */       }
/* 156 */     } else if (value instanceof char[]) {
/* 157 */       char[] v = (char[])value;
/* 158 */       this.bv.put12(91, v.length);
/* 159 */       for (int i = 0; i < v.length; i++) {
/* 160 */         this.bv.put12(67, (this.cw.newInteger(v[i])).index);
/*     */       }
/* 162 */     } else if (value instanceof int[]) {
/* 163 */       int[] v = (int[])value;
/* 164 */       this.bv.put12(91, v.length);
/* 165 */       for (int i = 0; i < v.length; i++) {
/* 166 */         this.bv.put12(73, (this.cw.newInteger(v[i])).index);
/*     */       }
/* 168 */     } else if (value instanceof long[]) {
/* 169 */       long[] v = (long[])value;
/* 170 */       this.bv.put12(91, v.length);
/* 171 */       for (int i = 0; i < v.length; i++) {
/* 172 */         this.bv.put12(74, (this.cw.newLong(v[i])).index);
/*     */       }
/* 174 */     } else if (value instanceof float[]) {
/* 175 */       float[] v = (float[])value;
/* 176 */       this.bv.put12(91, v.length);
/* 177 */       for (int i = 0; i < v.length; i++) {
/* 178 */         this.bv.put12(70, (this.cw.newFloat(v[i])).index);
/*     */       }
/* 180 */     } else if (value instanceof double[]) {
/* 181 */       double[] v = (double[])value;
/* 182 */       this.bv.put12(91, v.length);
/* 183 */       for (int i = 0; i < v.length; i++) {
/* 184 */         this.bv.put12(68, (this.cw.newDouble(v[i])).index);
/*     */       }
/*     */     } else {
/* 187 */       Item i = this.cw.newConstItem(value);
/* 188 */       this.bv.put12(".s.IFJDCS".charAt(i.type), i.index);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnum(String name, String desc, String value) {
/* 195 */     this.size++;
/* 196 */     if (this.named) {
/* 197 */       this.bv.putShort(this.cw.newUTF8(name));
/*     */     }
/* 199 */     this.bv.put12(101, this.cw.newUTF8(desc)).putShort(this.cw.newUTF8(value));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String name, String desc) {
/* 205 */     this.size++;
/* 206 */     if (this.named) {
/* 207 */       this.bv.putShort(this.cw.newUTF8(name));
/*     */     }
/*     */     
/* 210 */     this.bv.put12(64, this.cw.newUTF8(desc)).putShort(0);
/* 211 */     return new AnnotationWriter(this.cw, true, this.bv, this.bv, this.bv.length - 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitArray(String name) {
/* 216 */     this.size++;
/* 217 */     if (this.named) {
/* 218 */       this.bv.putShort(this.cw.newUTF8(name));
/*     */     }
/*     */     
/* 221 */     this.bv.put12(91, 0);
/* 222 */     return new AnnotationWriter(this.cw, false, this.bv, this.bv, this.bv.length - 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 227 */     if (this.parent != null) {
/* 228 */       byte[] data = this.parent.data;
/* 229 */       data[this.offset] = (byte)(this.size >>> 8);
/* 230 */       data[this.offset + 1] = (byte)this.size;
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
/*     */   int getSize() {
/* 244 */     int size = 0;
/* 245 */     AnnotationWriter aw = this;
/* 246 */     while (aw != null) {
/* 247 */       size += aw.bv.length;
/* 248 */       aw = aw.next;
/*     */     } 
/* 250 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void put(ByteVector out) {
/* 261 */     int n = 0;
/* 262 */     int size = 2;
/* 263 */     AnnotationWriter aw = this;
/* 264 */     AnnotationWriter last = null;
/* 265 */     while (aw != null) {
/* 266 */       n++;
/* 267 */       size += aw.bv.length;
/* 268 */       aw.visitEnd();
/* 269 */       aw.prev = last;
/* 270 */       last = aw;
/* 271 */       aw = aw.next;
/*     */     } 
/* 273 */     out.putInt(size);
/* 274 */     out.putShort(n);
/* 275 */     aw = last;
/* 276 */     while (aw != null) {
/* 277 */       out.putByteArray(aw.bv.data, 0, aw.bv.length);
/* 278 */       aw = aw.prev;
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
/*     */ 
/*     */   
/*     */   static void put(AnnotationWriter[] panns, int off, ByteVector out) {
/* 294 */     int size = 1 + 2 * (panns.length - off); int i;
/* 295 */     for (i = off; i < panns.length; i++) {
/* 296 */       size += (panns[i] == null) ? 0 : panns[i].getSize();
/*     */     }
/* 298 */     out.putInt(size).putByte(panns.length - off);
/* 299 */     for (i = off; i < panns.length; i++) {
/* 300 */       AnnotationWriter aw = panns[i];
/* 301 */       AnnotationWriter last = null;
/* 302 */       int n = 0;
/* 303 */       while (aw != null) {
/* 304 */         n++;
/* 305 */         aw.visitEnd();
/* 306 */         aw.prev = last;
/* 307 */         last = aw;
/* 308 */         aw = aw.next;
/*     */       } 
/* 310 */       out.putShort(n);
/* 311 */       aw = last;
/* 312 */       while (aw != null) {
/* 313 */         out.putByteArray(aw.bv.data, 0, aw.bv.length);
/* 314 */         aw = aw.prev;
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void putTarget(int typeRef, TypePath typePath, ByteVector out) {
/* 333 */     switch (typeRef >>> 24) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 22:
/* 337 */         out.putShort(typeRef >>> 16);
/*     */         break;
/*     */       case 19:
/*     */       case 20:
/*     */       case 21:
/* 342 */         out.putByte(typeRef >>> 24);
/*     */         break;
/*     */       case 71:
/*     */       case 72:
/*     */       case 73:
/*     */       case 74:
/*     */       case 75:
/* 349 */         out.putInt(typeRef);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 361 */         out.put12(typeRef >>> 24, (typeRef & 0xFFFF00) >> 8);
/*     */         break;
/*     */     } 
/* 364 */     if (typePath == null) {
/* 365 */       out.putByte(0);
/*     */     } else {
/* 367 */       int length = typePath.b[typePath.offset] * 2 + 1;
/* 368 */       out.putByteArray(typePath.b, typePath.offset, length);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\AnnotationWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */