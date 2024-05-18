/*     */ package org.newdawn.slick.opengl;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TGAImageData
/*     */   implements LoadableImageData
/*     */ {
/*     */   private int texWidth;
/*     */   private int texHeight;
/*     */   private int width;
/*     */   private int height;
/*     */   private short pixelDepth;
/*     */   
/*     */   private short flipEndian(short signedShort) {
/*  47 */     int input = signedShort & 0xFFFF;
/*  48 */     return (short)(input << 8 | (input & 0xFF00) >>> 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  55 */     return this.pixelDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  62 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  69 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTexWidth() {
/*  76 */     return this.texWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTexHeight() {
/*  83 */     return this.texHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis) throws IOException {
/*  90 */     return loadImage(fis, true, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent) throws IOException {
/*  97 */     return loadImage(fis, flipped, false, transparent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, boolean forceAlpha, int[] transparent) throws IOException {
/* 104 */     if (transparent != null) {
/* 105 */       forceAlpha = true;
/*     */     }
/* 107 */     byte red = 0;
/* 108 */     byte green = 0;
/* 109 */     byte blue = 0;
/* 110 */     byte alpha = 0;
/*     */     
/* 112 */     BufferedInputStream bis = new BufferedInputStream(fis, 100000);
/* 113 */     DataInputStream dis = new DataInputStream(bis);
/*     */ 
/*     */     
/* 116 */     short idLength = (short)dis.read();
/* 117 */     short colorMapType = (short)dis.read();
/* 118 */     short imageType = (short)dis.read();
/* 119 */     short cMapStart = flipEndian(dis.readShort());
/* 120 */     short cMapLength = flipEndian(dis.readShort());
/* 121 */     short cMapDepth = (short)dis.read();
/* 122 */     short xOffset = flipEndian(dis.readShort());
/* 123 */     short yOffset = flipEndian(dis.readShort());
/*     */     
/* 125 */     if (imageType != 2) {
/* 126 */       throw new IOException("Slick only supports uncompressed RGB(A) TGA images");
/*     */     }
/*     */     
/* 129 */     this.width = flipEndian(dis.readShort());
/* 130 */     this.height = flipEndian(dis.readShort());
/* 131 */     this.pixelDepth = (short)dis.read();
/* 132 */     if (this.pixelDepth == 32) {
/* 133 */       forceAlpha = false;
/*     */     }
/*     */     
/* 136 */     this.texWidth = get2Fold(this.width);
/* 137 */     this.texHeight = get2Fold(this.height);
/*     */     
/* 139 */     short imageDescriptor = (short)dis.read();
/* 140 */     if ((imageDescriptor & 0x20) == 0) {
/* 141 */       flipped = !flipped;
/*     */     }
/*     */ 
/*     */     
/* 145 */     if (idLength > 0) {
/* 146 */       bis.skip(idLength);
/*     */     }
/*     */     
/* 149 */     byte[] rawData = null;
/* 150 */     if (this.pixelDepth == 32 || forceAlpha) {
/* 151 */       this.pixelDepth = 32;
/* 152 */       rawData = new byte[this.texWidth * this.texHeight * 4];
/* 153 */     } else if (this.pixelDepth == 24) {
/* 154 */       rawData = new byte[this.texWidth * this.texHeight * 3];
/*     */     } else {
/* 156 */       throw new RuntimeException("Only 24 and 32 bit TGAs are supported");
/*     */     } 
/*     */     
/* 159 */     if (this.pixelDepth == 24) {
/* 160 */       if (flipped) {
/* 161 */         for (int i = this.height - 1; i >= 0; i--) {
/* 162 */           for (int j = 0; j < this.width; j++) {
/* 163 */             blue = dis.readByte();
/* 164 */             green = dis.readByte();
/* 165 */             red = dis.readByte();
/*     */             
/* 167 */             int ofs = (j + i * this.texWidth) * 3;
/* 168 */             rawData[ofs] = red;
/* 169 */             rawData[ofs + 1] = green;
/* 170 */             rawData[ofs + 2] = blue;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 174 */         for (int i = 0; i < this.height; i++) {
/* 175 */           for (int j = 0; j < this.width; j++) {
/* 176 */             blue = dis.readByte();
/* 177 */             green = dis.readByte();
/* 178 */             red = dis.readByte();
/*     */             
/* 180 */             int ofs = (j + i * this.texWidth) * 3;
/* 181 */             rawData[ofs] = red;
/* 182 */             rawData[ofs + 1] = green;
/* 183 */             rawData[ofs + 2] = blue;
/*     */           } 
/*     */         } 
/*     */       } 
/* 187 */     } else if (this.pixelDepth == 32) {
/* 188 */       if (flipped) {
/* 189 */         for (int i = this.height - 1; i >= 0; i--) {
/* 190 */           for (int j = 0; j < this.width; j++) {
/* 191 */             blue = dis.readByte();
/* 192 */             green = dis.readByte();
/* 193 */             red = dis.readByte();
/* 194 */             if (forceAlpha) {
/* 195 */               alpha = -1;
/*     */             } else {
/* 197 */               alpha = dis.readByte();
/*     */             } 
/*     */             
/* 200 */             int ofs = (j + i * this.texWidth) * 4;
/*     */             
/* 202 */             rawData[ofs] = red;
/* 203 */             rawData[ofs + 1] = green;
/* 204 */             rawData[ofs + 2] = blue;
/* 205 */             rawData[ofs + 3] = alpha;
/*     */             
/* 207 */             if (alpha == 0) {
/* 208 */               rawData[ofs + 2] = 0;
/* 209 */               rawData[ofs + 1] = 0;
/* 210 */               rawData[ofs] = 0;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 215 */         for (int i = 0; i < this.height; i++) {
/* 216 */           for (int j = 0; j < this.width; j++) {
/* 217 */             blue = dis.readByte();
/* 218 */             green = dis.readByte();
/* 219 */             red = dis.readByte();
/* 220 */             if (forceAlpha) {
/* 221 */               alpha = -1;
/*     */             } else {
/* 223 */               alpha = dis.readByte();
/*     */             } 
/*     */             
/* 226 */             int ofs = (j + i * this.texWidth) * 4;
/*     */             
/* 228 */             if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
/* 229 */               rawData[ofs] = red;
/* 230 */               rawData[ofs + 1] = green;
/* 231 */               rawData[ofs + 2] = blue;
/* 232 */               rawData[ofs + 3] = alpha;
/*     */             } else {
/* 234 */               rawData[ofs] = red;
/* 235 */               rawData[ofs + 1] = green;
/* 236 */               rawData[ofs + 2] = blue;
/* 237 */               rawData[ofs + 3] = alpha;
/*     */             } 
/*     */             
/* 240 */             if (alpha == 0) {
/* 241 */               rawData[ofs + 2] = 0;
/* 242 */               rawData[ofs + 1] = 0;
/* 243 */               rawData[ofs] = 0;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 249 */     fis.close();
/*     */     
/* 251 */     if (transparent != null) {
/* 252 */       for (int i = 0; i < rawData.length; i += 4) {
/* 253 */         boolean match = true;
/* 254 */         for (int c = 0; c < 3; c++) {
/* 255 */           if (rawData[i + c] != transparent[c]) {
/* 256 */             match = false;
/*     */           }
/*     */         } 
/*     */         
/* 260 */         if (match) {
/* 261 */           rawData[i + 3] = 0;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 267 */     ByteBuffer scratch = BufferUtils.createByteBuffer(rawData.length);
/* 268 */     scratch.put(rawData);
/*     */     
/* 270 */     int perPixel = this.pixelDepth / 8;
/* 271 */     if (this.height < this.texHeight - 1) {
/* 272 */       int topOffset = (this.texHeight - 1) * this.texWidth * perPixel;
/* 273 */       int bottomOffset = (this.height - 1) * this.texWidth * perPixel;
/* 274 */       for (int x = 0; x < this.texWidth * perPixel; x++) {
/* 275 */         scratch.put(topOffset + x, scratch.get(x));
/* 276 */         scratch.put(bottomOffset + this.texWidth * perPixel + x, scratch.get(this.texWidth * perPixel + x));
/*     */       } 
/*     */     } 
/* 279 */     if (this.width < this.texWidth - 1) {
/* 280 */       for (int y = 0; y < this.texHeight; y++) {
/* 281 */         for (int i = 0; i < perPixel; i++) {
/* 282 */           scratch.put((y + 1) * this.texWidth * perPixel - perPixel + i, scratch.get(y * this.texWidth * perPixel + i));
/* 283 */           scratch.put(y * this.texWidth * perPixel + this.width * perPixel + i, scratch.get(y * this.texWidth * perPixel + (this.width - 1) * perPixel + i));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 288 */     scratch.flip();
/*     */     
/* 290 */     return scratch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int get2Fold(int fold) {
/* 300 */     int ret = 2;
/* 301 */     while (ret < fold) {
/* 302 */       ret *= 2;
/*     */     }
/* 304 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getImageBufferData() {
/* 311 */     throw new RuntimeException("TGAImageData doesn't store it's image.");
/*     */   }
/*     */   
/*     */   public void configureEdging(boolean edging) {}
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\TGAImageData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */