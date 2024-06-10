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
/*     */ public class ByteVector
/*     */ {
/*     */   byte[] data;
/*     */   int length;
/*     */   
/*     */   public ByteVector() {
/*  55 */     this.data = new byte[64];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteVector(int initialSize) {
/*  66 */     this.data = new byte[initialSize];
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
/*     */   public ByteVector putByte(int b) {
/*  78 */     int length = this.length;
/*  79 */     if (length + 1 > this.data.length) {
/*  80 */       enlarge(1);
/*     */     }
/*  82 */     this.data[length++] = (byte)b;
/*  83 */     this.length = length;
/*  84 */     return this;
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
/*     */   ByteVector put11(int b1, int b2) {
/*  98 */     int length = this.length;
/*  99 */     if (length + 2 > this.data.length) {
/* 100 */       enlarge(2);
/*     */     }
/* 102 */     byte[] data = this.data;
/* 103 */     data[length++] = (byte)b1;
/* 104 */     data[length++] = (byte)b2;
/* 105 */     this.length = length;
/* 106 */     return this;
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
/*     */   public ByteVector putShort(int s) {
/* 118 */     int length = this.length;
/* 119 */     if (length + 2 > this.data.length) {
/* 120 */       enlarge(2);
/*     */     }
/* 122 */     byte[] data = this.data;
/* 123 */     data[length++] = (byte)(s >>> 8);
/* 124 */     data[length++] = (byte)s;
/* 125 */     this.length = length;
/* 126 */     return this;
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
/*     */   ByteVector put12(int b, int s) {
/* 140 */     int length = this.length;
/* 141 */     if (length + 3 > this.data.length) {
/* 142 */       enlarge(3);
/*     */     }
/* 144 */     byte[] data = this.data;
/* 145 */     data[length++] = (byte)b;
/* 146 */     data[length++] = (byte)(s >>> 8);
/* 147 */     data[length++] = (byte)s;
/* 148 */     this.length = length;
/* 149 */     return this;
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
/*     */   public ByteVector putInt(int i) {
/* 161 */     int length = this.length;
/* 162 */     if (length + 4 > this.data.length) {
/* 163 */       enlarge(4);
/*     */     }
/* 165 */     byte[] data = this.data;
/* 166 */     data[length++] = (byte)(i >>> 24);
/* 167 */     data[length++] = (byte)(i >>> 16);
/* 168 */     data[length++] = (byte)(i >>> 8);
/* 169 */     data[length++] = (byte)i;
/* 170 */     this.length = length;
/* 171 */     return this;
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
/*     */   public ByteVector putLong(long l) {
/* 183 */     int length = this.length;
/* 184 */     if (length + 8 > this.data.length) {
/* 185 */       enlarge(8);
/*     */     }
/* 187 */     byte[] data = this.data;
/* 188 */     int i = (int)(l >>> 32L);
/* 189 */     data[length++] = (byte)(i >>> 24);
/* 190 */     data[length++] = (byte)(i >>> 16);
/* 191 */     data[length++] = (byte)(i >>> 8);
/* 192 */     data[length++] = (byte)i;
/* 193 */     i = (int)l;
/* 194 */     data[length++] = (byte)(i >>> 24);
/* 195 */     data[length++] = (byte)(i >>> 16);
/* 196 */     data[length++] = (byte)(i >>> 8);
/* 197 */     data[length++] = (byte)i;
/* 198 */     this.length = length;
/* 199 */     return this;
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
/*     */   public ByteVector putUTF8(String s) {
/* 211 */     int charLength = s.length();
/* 212 */     if (charLength > 65535) {
/* 213 */       throw new IllegalArgumentException();
/*     */     }
/* 215 */     int len = this.length;
/* 216 */     if (len + 2 + charLength > this.data.length) {
/* 217 */       enlarge(2 + charLength);
/*     */     }
/* 219 */     byte[] data = this.data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 226 */     data[len++] = (byte)(charLength >>> 8);
/* 227 */     data[len++] = (byte)charLength;
/* 228 */     for (int i = 0; i < charLength; i++) {
/* 229 */       char c = s.charAt(i);
/* 230 */       if (c >= '\001' && c <= '') {
/* 231 */         data[len++] = (byte)c;
/*     */       } else {
/* 233 */         this.length = len;
/* 234 */         return encodeUTF8(s, i, 65535);
/*     */       } 
/*     */     } 
/* 237 */     this.length = len;
/* 238 */     return this;
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
/*     */   ByteVector encodeUTF8(String s, int i, int maxByteLength) {
/* 259 */     int charLength = s.length();
/* 260 */     int byteLength = i;
/*     */     
/* 262 */     for (int j = i; j < charLength; j++) {
/* 263 */       char c = s.charAt(j);
/* 264 */       if (c >= '\001' && c <= '') {
/* 265 */         byteLength++;
/* 266 */       } else if (c > '߿') {
/* 267 */         byteLength += 3;
/*     */       } else {
/* 269 */         byteLength += 2;
/*     */       } 
/*     */     } 
/* 272 */     if (byteLength > maxByteLength) {
/* 273 */       throw new IllegalArgumentException();
/*     */     }
/* 275 */     int start = this.length - i - 2;
/* 276 */     if (start >= 0) {
/* 277 */       this.data[start] = (byte)(byteLength >>> 8);
/* 278 */       this.data[start + 1] = (byte)byteLength;
/*     */     } 
/* 280 */     if (this.length + byteLength - i > this.data.length) {
/* 281 */       enlarge(byteLength - i);
/*     */     }
/* 283 */     int len = this.length;
/* 284 */     for (int k = i; k < charLength; k++) {
/* 285 */       char c = s.charAt(k);
/* 286 */       if (c >= '\001' && c <= '') {
/* 287 */         this.data[len++] = (byte)c;
/* 288 */       } else if (c > '߿') {
/* 289 */         this.data[len++] = (byte)(0xE0 | c >> 12 & 0xF);
/* 290 */         this.data[len++] = (byte)(0x80 | c >> 6 & 0x3F);
/* 291 */         this.data[len++] = (byte)(0x80 | c & 0x3F);
/*     */       } else {
/* 293 */         this.data[len++] = (byte)(0xC0 | c >> 6 & 0x1F);
/* 294 */         this.data[len++] = (byte)(0x80 | c & 0x3F);
/*     */       } 
/*     */     } 
/* 297 */     this.length = len;
/* 298 */     return this;
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
/*     */   public ByteVector putByteArray(byte[] b, int off, int len) {
/* 315 */     if (this.length + len > this.data.length) {
/* 316 */       enlarge(len);
/*     */     }
/* 318 */     if (b != null) {
/* 319 */       System.arraycopy(b, off, this.data, this.length, len);
/*     */     }
/* 321 */     this.length += len;
/* 322 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void enlarge(int size) {
/* 333 */     int length1 = 2 * this.data.length;
/* 334 */     int length2 = this.length + size;
/* 335 */     byte[] newData = new byte[(length1 > length2) ? length1 : length2];
/* 336 */     System.arraycopy(this.data, 0, newData, 0, this.length);
/* 337 */     this.data = newData;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\ByteVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */