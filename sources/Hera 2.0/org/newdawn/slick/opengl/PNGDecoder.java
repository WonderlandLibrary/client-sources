/*     */ package org.newdawn.slick.opengl;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.Inflater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PNGDecoder
/*     */ {
/*  47 */   public static Format ALPHA = new Format(1, true);
/*  48 */   public static Format LUMINANCE = new Format(1, false);
/*  49 */   public static Format LUMINANCE_ALPHA = new Format(2, true);
/*  50 */   public static Format RGB = new Format(3, false);
/*  51 */   public static Format RGBA = new Format(4, true);
/*  52 */   public static Format BGRA = new Format(4, true);
/*  53 */   public static Format ABGR = new Format(4, true);
/*     */   
/*     */   public static class Format
/*     */   {
/*     */     final int numComponents;
/*     */     final boolean hasAlpha;
/*     */     
/*     */     private Format(int numComponents, boolean hasAlpha) {
/*  61 */       this.numComponents = numComponents;
/*  62 */       this.hasAlpha = hasAlpha;
/*     */     }
/*     */     
/*     */     public int getNumComponents() {
/*  66 */       return this.numComponents;
/*     */     }
/*     */     
/*     */     public boolean isHasAlpha() {
/*  70 */       return this.hasAlpha;
/*     */     }
/*     */   }
/*     */   
/*  74 */   private static final byte[] SIGNATURE = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10 };
/*     */   
/*     */   private static final int IHDR = 1229472850;
/*     */   
/*     */   private static final int PLTE = 1347179589;
/*     */   
/*     */   private static final int tRNS = 1951551059;
/*     */   
/*     */   private static final int IDAT = 1229209940;
/*     */   
/*     */   private static final int IEND = 1229278788;
/*     */   private static final byte COLOR_GREYSCALE = 0;
/*     */   private static final byte COLOR_TRUECOLOR = 2;
/*     */   private static final byte COLOR_INDEXED = 3;
/*     */   private static final byte COLOR_GREYALPHA = 4;
/*     */   private static final byte COLOR_TRUEALPHA = 6;
/*     */   private final InputStream input;
/*     */   private final CRC32 crc;
/*     */   private final byte[] buffer;
/*     */   private int chunkLength;
/*     */   private int chunkType;
/*     */   private int chunkRemaining;
/*     */   private int width;
/*     */   private int height;
/*     */   private int bitdepth;
/*     */   private int colorType;
/*     */   private int bytesPerPixel;
/*     */   private byte[] palette;
/*     */   private byte[] paletteA;
/*     */   private byte[] transPixel;
/*     */   
/*     */   public PNGDecoder(InputStream input) throws IOException {
/* 106 */     this.input = input;
/* 107 */     this.crc = new CRC32();
/* 108 */     this.buffer = new byte[4096];
/*     */     
/* 110 */     readFully(this.buffer, 0, SIGNATURE.length);
/* 111 */     if (!checkSignature(this.buffer)) {
/* 112 */       throw new IOException("Not a valid PNG file");
/*     */     }
/*     */     
/* 115 */     openChunk(1229472850);
/* 116 */     readIHDR();
/* 117 */     closeChunk();
/*     */     
/*     */     while (true) {
/* 120 */       openChunk();
/* 121 */       switch (this.chunkType) {
/*     */         case 1229209940:
/*     */           break;
/*     */         case 1347179589:
/* 125 */           readPLTE();
/*     */           break;
/*     */         case 1951551059:
/* 128 */           readtRNS();
/*     */           break;
/*     */       } 
/* 131 */       closeChunk();
/*     */     } 
/*     */     
/* 134 */     if (this.colorType == 3 && this.palette == null) {
/* 135 */       throw new IOException("Missing PLTE chunk");
/*     */     }
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 140 */     return this.height;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 144 */     return this.width;
/*     */   }
/*     */   
/*     */   public boolean hasAlpha() {
/* 148 */     return (this.colorType == 6 || this.paletteA != null || this.transPixel != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRGB() {
/* 153 */     return (this.colorType == 6 || this.colorType == 2 || this.colorType == 3);
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
/*     */   public Format decideTextureFormat(Format fmt) {
/* 166 */     switch (this.colorType) {
/*     */       case 2:
/* 168 */         if (fmt == ABGR || fmt == RGBA || fmt == BGRA || fmt == RGB) {
/* 169 */           return fmt;
/*     */         }
/*     */         
/* 172 */         return RGB;
/*     */       case 6:
/* 174 */         if (fmt == ABGR || fmt == RGBA || fmt == BGRA || fmt == RGB) {
/* 175 */           return fmt;
/*     */         }
/*     */         
/* 178 */         return RGBA;
/*     */       case 0:
/* 180 */         if (fmt == LUMINANCE || fmt == ALPHA) {
/* 181 */           return fmt;
/*     */         }
/*     */         
/* 184 */         return LUMINANCE;
/*     */       case 4:
/* 186 */         return LUMINANCE_ALPHA;
/*     */       case 3:
/* 188 */         if (fmt == ABGR || fmt == RGBA || fmt == BGRA) {
/* 189 */           return fmt;
/*     */         }
/*     */         
/* 192 */         return RGBA;
/*     */     } 
/* 194 */     throw new UnsupportedOperationException("Not yet implemented");
/*     */   }
/*     */ 
/*     */   
/*     */   public void decode(ByteBuffer buffer, int stride, Format fmt) throws IOException {
/* 199 */     int offset = buffer.position();
/* 200 */     int lineSize = (this.width * this.bitdepth + 7) / 8 * this.bytesPerPixel;
/* 201 */     byte[] curLine = new byte[lineSize + 1];
/* 202 */     byte[] prevLine = new byte[lineSize + 1];
/* 203 */     byte[] palLine = (this.bitdepth < 8) ? new byte[this.width + 1] : null;
/*     */     
/* 205 */     Inflater inflater = new Inflater();
/*     */     try {
/* 207 */       for (int y = 0; y < this.height; y++) {
/* 208 */         readChunkUnzip(inflater, curLine, 0, curLine.length);
/* 209 */         unfilter(curLine, prevLine);
/*     */         
/* 211 */         buffer.position(offset + y * stride);
/*     */         
/* 213 */         switch (this.colorType) {
/*     */           case 2:
/* 215 */             if (fmt == ABGR) {
/* 216 */               copyRGBtoABGR(buffer, curLine); break;
/*     */             } 
/* 218 */             if (fmt == RGBA) {
/* 219 */               copyRGBtoRGBA(buffer, curLine); break;
/*     */             } 
/* 221 */             if (fmt == BGRA) {
/* 222 */               copyRGBtoBGRA(buffer, curLine); break;
/*     */             } 
/* 224 */             if (fmt == RGB) {
/* 225 */               copy(buffer, curLine); break;
/*     */             } 
/* 227 */             throw new UnsupportedOperationException("Unsupported format for this image");
/*     */ 
/*     */           
/*     */           case 6:
/* 231 */             if (fmt == ABGR) {
/* 232 */               copyRGBAtoABGR(buffer, curLine); break;
/* 233 */             }  if (fmt == RGBA) {
/* 234 */               copy(buffer, curLine); break;
/* 235 */             }  if (fmt == BGRA) {
/* 236 */               copyRGBAtoBGRA(buffer, curLine); break;
/* 237 */             }  if (fmt == RGB) {
/* 238 */               copyRGBAtoRGB(buffer, curLine); break;
/*     */             } 
/* 240 */             throw new UnsupportedOperationException("Unsupported format for this image");
/*     */ 
/*     */           
/*     */           case 0:
/* 244 */             if (fmt == LUMINANCE || fmt == ALPHA) {
/* 245 */               copy(buffer, curLine); break;
/*     */             } 
/* 247 */             throw new UnsupportedOperationException("Unsupported format for this image");
/*     */ 
/*     */           
/*     */           case 4:
/* 251 */             if (fmt == LUMINANCE_ALPHA) {
/* 252 */               copy(buffer, curLine); break;
/*     */             } 
/* 254 */             throw new UnsupportedOperationException("Unsupported format for this image");
/*     */ 
/*     */           
/*     */           case 3:
/* 258 */             switch (this.bitdepth) { case 8:
/* 259 */                 palLine = curLine; break;
/* 260 */               case 4: expand4(curLine, palLine); break;
/* 261 */               case 2: expand2(curLine, palLine); break;
/* 262 */               case 1: expand1(curLine, palLine); break;
/* 263 */               default: throw new UnsupportedOperationException("Unsupported bitdepth for this image"); }
/*     */             
/* 265 */             if (fmt == ABGR) {
/* 266 */               copyPALtoABGR(buffer, palLine); break;
/* 267 */             }  if (fmt == RGBA) {
/* 268 */               copyPALtoRGBA(buffer, palLine); break;
/* 269 */             }  if (fmt == BGRA) {
/* 270 */               copyPALtoBGRA(buffer, palLine); break;
/*     */             } 
/* 272 */             throw new UnsupportedOperationException("Unsupported format for this image");
/*     */ 
/*     */           
/*     */           default:
/* 276 */             throw new UnsupportedOperationException("Not yet implemented");
/*     */         } 
/*     */         
/* 279 */         byte[] tmp = curLine;
/* 280 */         curLine = prevLine;
/* 281 */         prevLine = tmp;
/*     */       } 
/*     */     } finally {
/* 284 */       inflater.end();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copy(ByteBuffer buffer, byte[] curLine) {
/* 289 */     buffer.put(curLine, 1, curLine.length - 1);
/*     */   }
/*     */   
/*     */   private void copyRGBtoABGR(ByteBuffer buffer, byte[] curLine) {
/* 293 */     if (this.transPixel != null) {
/* 294 */       byte tr = this.transPixel[1];
/* 295 */       byte tg = this.transPixel[3];
/* 296 */       byte tb = this.transPixel[5];
/* 297 */       for (int i = 1, n = curLine.length; i < n; i += 3) {
/* 298 */         byte r = curLine[i];
/* 299 */         byte g = curLine[i + 1];
/* 300 */         byte b = curLine[i + 2];
/* 301 */         byte a = -1;
/* 302 */         if (r == tr && g == tg && b == tb) {
/* 303 */           a = 0;
/*     */         }
/* 305 */         buffer.put(a).put(b).put(g).put(r);
/*     */       } 
/*     */     } else {
/* 308 */       for (int i = 1, n = curLine.length; i < n; i += 3) {
/* 309 */         buffer.put((byte)-1).put(curLine[i + 2]).put(curLine[i + 1]).put(curLine[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copyRGBtoRGBA(ByteBuffer buffer, byte[] curLine) {
/* 315 */     if (this.transPixel != null) {
/* 316 */       byte tr = this.transPixel[1];
/* 317 */       byte tg = this.transPixel[3];
/* 318 */       byte tb = this.transPixel[5];
/* 319 */       for (int i = 1, n = curLine.length; i < n; i += 3) {
/* 320 */         byte r = curLine[i];
/* 321 */         byte g = curLine[i + 1];
/* 322 */         byte b = curLine[i + 2];
/* 323 */         byte a = -1;
/* 324 */         if (r == tr && g == tg && b == tb) {
/* 325 */           a = 0;
/*     */         }
/* 327 */         buffer.put(r).put(g).put(b).put(a);
/*     */       } 
/*     */     } else {
/* 330 */       for (int i = 1, n = curLine.length; i < n; i += 3) {
/* 331 */         buffer.put(curLine[i]).put(curLine[i + 1]).put(curLine[i + 2]).put((byte)-1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copyRGBtoBGRA(ByteBuffer buffer, byte[] curLine) {
/* 337 */     if (this.transPixel != null) {
/* 338 */       byte tr = this.transPixel[1];
/* 339 */       byte tg = this.transPixel[3];
/* 340 */       byte tb = this.transPixel[5];
/* 341 */       for (int i = 1, n = curLine.length; i < n; i += 3) {
/* 342 */         byte r = curLine[i];
/* 343 */         byte g = curLine[i + 1];
/* 344 */         byte b = curLine[i + 2];
/* 345 */         byte a = -1;
/* 346 */         if (r == tr && g == tg && b == tb) {
/* 347 */           a = 0;
/*     */         }
/* 349 */         buffer.put(b).put(g).put(r).put(a);
/*     */       } 
/*     */     } else {
/* 352 */       for (int i = 1, n = curLine.length; i < n; i += 3) {
/* 353 */         buffer.put(curLine[i + 2]).put(curLine[i + 1]).put(curLine[i]).put((byte)-1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copyRGBAtoABGR(ByteBuffer buffer, byte[] curLine) {
/* 359 */     for (int i = 1, n = curLine.length; i < n; i += 4) {
/* 360 */       buffer.put(curLine[i + 3]).put(curLine[i + 2]).put(curLine[i + 1]).put(curLine[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void copyRGBAtoBGRA(ByteBuffer buffer, byte[] curLine) {
/* 365 */     for (int i = 1, n = curLine.length; i < n; i += 4) {
/* 366 */       buffer.put(curLine[i + 2]).put(curLine[i + 1]).put(curLine[i + 0]).put(curLine[i + 3]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void copyRGBAtoRGB(ByteBuffer buffer, byte[] curLine) {
/* 371 */     for (int i = 1, n = curLine.length; i < n; i += 4) {
/* 372 */       buffer.put(curLine[i]).put(curLine[i + 1]).put(curLine[i + 2]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void copyPALtoABGR(ByteBuffer buffer, byte[] curLine) {
/* 377 */     if (this.paletteA != null) {
/* 378 */       for (int i = 1, n = curLine.length; i < n; i++) {
/* 379 */         int idx = curLine[i] & 0xFF;
/* 380 */         byte r = this.palette[idx * 3 + 0];
/* 381 */         byte g = this.palette[idx * 3 + 1];
/* 382 */         byte b = this.palette[idx * 3 + 2];
/* 383 */         byte a = this.paletteA[idx];
/* 384 */         buffer.put(a).put(b).put(g).put(r);
/*     */       } 
/*     */     } else {
/* 387 */       for (int i = 1, n = curLine.length; i < n; i++) {
/* 388 */         int idx = curLine[i] & 0xFF;
/* 389 */         byte r = this.palette[idx * 3 + 0];
/* 390 */         byte g = this.palette[idx * 3 + 1];
/* 391 */         byte b = this.palette[idx * 3 + 2];
/* 392 */         byte a = -1;
/* 393 */         buffer.put(a).put(b).put(g).put(r);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copyPALtoRGBA(ByteBuffer buffer, byte[] curLine) {
/* 399 */     if (this.paletteA != null) {
/* 400 */       for (int i = 1, n = curLine.length; i < n; i++) {
/* 401 */         int idx = curLine[i] & 0xFF;
/* 402 */         byte r = this.palette[idx * 3 + 0];
/* 403 */         byte g = this.palette[idx * 3 + 1];
/* 404 */         byte b = this.palette[idx * 3 + 2];
/* 405 */         byte a = this.paletteA[idx];
/* 406 */         buffer.put(r).put(g).put(b).put(a);
/*     */       } 
/*     */     } else {
/* 409 */       for (int i = 1, n = curLine.length; i < n; i++) {
/* 410 */         int idx = curLine[i] & 0xFF;
/* 411 */         byte r = this.palette[idx * 3 + 0];
/* 412 */         byte g = this.palette[idx * 3 + 1];
/* 413 */         byte b = this.palette[idx * 3 + 2];
/* 414 */         byte a = -1;
/* 415 */         buffer.put(r).put(g).put(b).put(a);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copyPALtoBGRA(ByteBuffer buffer, byte[] curLine) {
/* 421 */     if (this.paletteA != null) {
/* 422 */       for (int i = 1, n = curLine.length; i < n; i++) {
/* 423 */         int idx = curLine[i] & 0xFF;
/* 424 */         byte r = this.palette[idx * 3 + 0];
/* 425 */         byte g = this.palette[idx * 3 + 1];
/* 426 */         byte b = this.palette[idx * 3 + 2];
/* 427 */         byte a = this.paletteA[idx];
/* 428 */         buffer.put(b).put(g).put(r).put(a);
/*     */       } 
/*     */     } else {
/* 431 */       for (int i = 1, n = curLine.length; i < n; i++) {
/* 432 */         int idx = curLine[i] & 0xFF;
/* 433 */         byte r = this.palette[idx * 3 + 0];
/* 434 */         byte g = this.palette[idx * 3 + 1];
/* 435 */         byte b = this.palette[idx * 3 + 2];
/* 436 */         byte a = -1;
/* 437 */         buffer.put(b).put(g).put(r).put(a);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void expand4(byte[] src, byte[] dst) {
/* 443 */     for (int i = 1, n = dst.length; i < n; i += 2) {
/* 444 */       int val = src[1 + (i >> 1)] & 0xFF;
/* 445 */       switch (n - i) { default:
/* 446 */           dst[i + 1] = (byte)(val & 0xF); break;
/* 447 */         case 1: break; }  dst[i] = (byte)(val >> 4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void expand2(byte[] src, byte[] dst) {
/* 453 */     for (int i = 1, n = dst.length; i < n; i += 4) {
/* 454 */       int val = src[1 + (i >> 2)] & 0xFF;
/* 455 */       switch (n - i) { default:
/* 456 */           dst[i + 3] = (byte)(val & 0x3);
/* 457 */         case 3: dst[i + 2] = (byte)(val >> 2 & 0x3);
/* 458 */         case 2: dst[i + 1] = (byte)(val >> 4 & 0x3); break;
/* 459 */         case 1: break; }  dst[i] = (byte)(val >> 6);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void expand1(byte[] src, byte[] dst) {
/* 465 */     for (int i = 1, n = dst.length; i < n; i += 8) {
/* 466 */       int val = src[1 + (i >> 3)] & 0xFF;
/* 467 */       switch (n - i) { default:
/* 468 */           dst[i + 7] = (byte)(val & 0x1);
/* 469 */         case 7: dst[i + 6] = (byte)(val >> 1 & 0x1);
/* 470 */         case 6: dst[i + 5] = (byte)(val >> 2 & 0x1);
/* 471 */         case 5: dst[i + 4] = (byte)(val >> 3 & 0x1);
/* 472 */         case 4: dst[i + 3] = (byte)(val >> 4 & 0x1);
/* 473 */         case 3: dst[i + 2] = (byte)(val >> 5 & 0x1);
/* 474 */         case 2: dst[i + 1] = (byte)(val >> 6 & 0x1); break;
/* 475 */         case 1: break; }  dst[i] = (byte)(val >> 7);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void unfilter(byte[] curLine, byte[] prevLine) throws IOException {
/* 481 */     switch (curLine[0]) {
/*     */       case 0:
/*     */         return;
/*     */       case 1:
/* 485 */         unfilterSub(curLine);
/*     */       
/*     */       case 2:
/* 488 */         unfilterUp(curLine, prevLine);
/*     */       
/*     */       case 3:
/* 491 */         unfilterAverage(curLine, prevLine);
/*     */       
/*     */       case 4:
/* 494 */         unfilterPaeth(curLine, prevLine);
/*     */     } 
/*     */     
/* 497 */     throw new IOException("invalide filter type in scanline: " + curLine[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private void unfilterSub(byte[] curLine) {
/* 502 */     int bpp = this.bytesPerPixel;
/* 503 */     for (int i = bpp + 1, n = curLine.length; i < n; i++) {
/* 504 */       curLine[i] = (byte)(curLine[i] + curLine[i - bpp]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void unfilterUp(byte[] curLine, byte[] prevLine) {
/* 509 */     int bpp = this.bytesPerPixel;
/* 510 */     for (int i = 1, n = curLine.length; i < n; i++) {
/* 511 */       curLine[i] = (byte)(curLine[i] + prevLine[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void unfilterAverage(byte[] curLine, byte[] prevLine) {
/* 516 */     int bpp = this.bytesPerPixel;
/*     */     
/*     */     int i;
/* 519 */     for (i = 1; i <= bpp; i++) {
/* 520 */       curLine[i] = (byte)(curLine[i] + (byte)((prevLine[i] & 0xFF) >>> 1));
/*     */     }
/* 522 */     for (int n = curLine.length; i < n; i++) {
/* 523 */       curLine[i] = (byte)(curLine[i] + (byte)((prevLine[i] & 0xFF) + (curLine[i - bpp] & 0xFF) >>> 1));
/*     */     }
/*     */   }
/*     */   
/*     */   private void unfilterPaeth(byte[] curLine, byte[] prevLine) {
/* 528 */     int bpp = this.bytesPerPixel;
/*     */     
/*     */     int i;
/* 531 */     for (i = 1; i <= bpp; i++) {
/* 532 */       curLine[i] = (byte)(curLine[i] + prevLine[i]);
/*     */     }
/* 534 */     for (int n = curLine.length; i < n; i++) {
/* 535 */       int a = curLine[i - bpp] & 0xFF;
/* 536 */       int b = prevLine[i] & 0xFF;
/* 537 */       int c = prevLine[i - bpp] & 0xFF;
/* 538 */       int p = a + b - c;
/* 539 */       int pa = p - a; if (pa < 0) pa = -pa; 
/* 540 */       int pb = p - b; if (pb < 0) pb = -pb; 
/* 541 */       int pc = p - c; if (pc < 0) pc = -pc; 
/* 542 */       if (pa <= pb && pa <= pc) {
/* 543 */         c = a;
/* 544 */       } else if (pb <= pc) {
/* 545 */         c = b;
/* 546 */       }  curLine[i] = (byte)(curLine[i] + (byte)c);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readIHDR() throws IOException {
/* 551 */     checkChunkLength(13);
/* 552 */     readChunk(this.buffer, 0, 13);
/* 553 */     this.width = readInt(this.buffer, 0);
/* 554 */     this.height = readInt(this.buffer, 4);
/* 555 */     this.bitdepth = this.buffer[8] & 0xFF;
/* 556 */     this.colorType = this.buffer[9] & 0xFF;
/*     */     
/* 558 */     switch (this.colorType) {
/*     */       case 0:
/* 560 */         if (this.bitdepth != 8) {
/* 561 */           throw new IOException("Unsupported bit depth: " + this.bitdepth);
/*     */         }
/* 563 */         this.bytesPerPixel = 1;
/*     */         break;
/*     */       case 4:
/* 566 */         if (this.bitdepth != 8) {
/* 567 */           throw new IOException("Unsupported bit depth: " + this.bitdepth);
/*     */         }
/* 569 */         this.bytesPerPixel = 2;
/*     */         break;
/*     */       case 2:
/* 572 */         if (this.bitdepth != 8) {
/* 573 */           throw new IOException("Unsupported bit depth: " + this.bitdepth);
/*     */         }
/* 575 */         this.bytesPerPixel = 3;
/*     */         break;
/*     */       case 6:
/* 578 */         if (this.bitdepth != 8) {
/* 579 */           throw new IOException("Unsupported bit depth: " + this.bitdepth);
/*     */         }
/* 581 */         this.bytesPerPixel = 4;
/*     */         break;
/*     */       case 3:
/* 584 */         switch (this.bitdepth) {
/*     */           case 1:
/*     */           case 2:
/*     */           case 4:
/*     */           case 8:
/* 589 */             this.bytesPerPixel = 1;
/*     */             break;
/*     */         } 
/* 592 */         throw new IOException("Unsupported bit depth: " + this.bitdepth);
/*     */ 
/*     */       
/*     */       default:
/* 596 */         throw new IOException("unsupported color format: " + this.colorType);
/*     */     } 
/*     */     
/* 599 */     if (this.buffer[10] != 0) {
/* 600 */       throw new IOException("unsupported compression method");
/*     */     }
/* 602 */     if (this.buffer[11] != 0) {
/* 603 */       throw new IOException("unsupported filtering method");
/*     */     }
/* 605 */     if (this.buffer[12] != 0) {
/* 606 */       throw new IOException("unsupported interlace method");
/*     */     }
/*     */   }
/*     */   
/*     */   private void readPLTE() throws IOException {
/* 611 */     int paletteEntries = this.chunkLength / 3;
/* 612 */     if (paletteEntries < 1 || paletteEntries > 256 || this.chunkLength % 3 != 0) {
/* 613 */       throw new IOException("PLTE chunk has wrong length");
/*     */     }
/* 615 */     this.palette = new byte[paletteEntries * 3];
/* 616 */     readChunk(this.palette, 0, this.palette.length);
/*     */   }
/*     */   
/*     */   private void readtRNS() throws IOException {
/* 620 */     switch (this.colorType) {
/*     */       case 0:
/* 622 */         checkChunkLength(2);
/* 623 */         this.transPixel = new byte[2];
/* 624 */         readChunk(this.transPixel, 0, 2);
/*     */         break;
/*     */       case 2:
/* 627 */         checkChunkLength(6);
/* 628 */         this.transPixel = new byte[6];
/* 629 */         readChunk(this.transPixel, 0, 6);
/*     */         break;
/*     */       case 3:
/* 632 */         if (this.palette == null) {
/* 633 */           throw new IOException("tRNS chunk without PLTE chunk");
/*     */         }
/* 635 */         this.paletteA = new byte[this.palette.length / 3];
/* 636 */         Arrays.fill(this.paletteA, (byte)-1);
/* 637 */         readChunk(this.paletteA, 0, this.paletteA.length);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeChunk() throws IOException {
/* 645 */     if (this.chunkRemaining > 0) {
/*     */       
/* 647 */       skip((this.chunkRemaining + 4));
/*     */     } else {
/* 649 */       readFully(this.buffer, 0, 4);
/* 650 */       int expectedCrc = readInt(this.buffer, 0);
/* 651 */       int computedCrc = (int)this.crc.getValue();
/* 652 */       if (computedCrc != expectedCrc) {
/* 653 */         throw new IOException("Invalid CRC");
/*     */       }
/*     */     } 
/* 656 */     this.chunkRemaining = 0;
/* 657 */     this.chunkLength = 0;
/* 658 */     this.chunkType = 0;
/*     */   }
/*     */   
/*     */   private void openChunk() throws IOException {
/* 662 */     readFully(this.buffer, 0, 8);
/* 663 */     this.chunkLength = readInt(this.buffer, 0);
/* 664 */     this.chunkType = readInt(this.buffer, 4);
/* 665 */     this.chunkRemaining = this.chunkLength;
/* 666 */     this.crc.reset();
/* 667 */     this.crc.update(this.buffer, 4, 4);
/*     */   }
/*     */   
/*     */   private void openChunk(int expected) throws IOException {
/* 671 */     openChunk();
/* 672 */     if (this.chunkType != expected) {
/* 673 */       throw new IOException("Expected chunk: " + Integer.toHexString(expected));
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkChunkLength(int expected) throws IOException {
/* 678 */     if (this.chunkLength != expected) {
/* 679 */       throw new IOException("Chunk has wrong size");
/*     */     }
/*     */   }
/*     */   
/*     */   private int readChunk(byte[] buffer, int offset, int length) throws IOException {
/* 684 */     if (length > this.chunkRemaining) {
/* 685 */       length = this.chunkRemaining;
/*     */     }
/* 687 */     readFully(buffer, offset, length);
/* 688 */     this.crc.update(buffer, offset, length);
/* 689 */     this.chunkRemaining -= length;
/* 690 */     return length;
/*     */   }
/*     */   
/*     */   private void refillInflater(Inflater inflater) throws IOException {
/* 694 */     while (this.chunkRemaining == 0) {
/* 695 */       closeChunk();
/* 696 */       openChunk(1229209940);
/*     */     } 
/* 698 */     int read = readChunk(this.buffer, 0, this.buffer.length);
/* 699 */     inflater.setInput(this.buffer, 0, read);
/*     */   }
/*     */   
/*     */   private void readChunkUnzip(Inflater inflater, byte[] buffer, int offset, int length) throws IOException {
/*     */     try {
/*     */       do {
/* 705 */         int read = inflater.inflate(buffer, offset, length);
/* 706 */         if (read <= 0) {
/* 707 */           if (inflater.finished()) {
/* 708 */             throw new EOFException();
/*     */           }
/* 710 */           if (inflater.needsInput()) {
/* 711 */             refillInflater(inflater);
/*     */           } else {
/* 713 */             throw new IOException("Can't inflate " + length + " bytes");
/*     */           } 
/*     */         } else {
/* 716 */           offset += read;
/* 717 */           length -= read;
/*     */         } 
/* 719 */       } while (length > 0);
/* 720 */     } catch (DataFormatException ex) {
/* 721 */       throw (IOException)(new IOException("inflate error")).initCause(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readFully(byte[] buffer, int offset, int length) throws IOException {
/*     */     do {
/* 727 */       int read = this.input.read(buffer, offset, length);
/* 728 */       if (read < 0) {
/* 729 */         throw new EOFException();
/*     */       }
/* 731 */       offset += read;
/* 732 */       length -= read;
/* 733 */     } while (length > 0);
/*     */   }
/*     */   
/*     */   private int readInt(byte[] buffer, int offset) {
/* 737 */     return buffer[offset] << 24 | (buffer[offset + 1] & 0xFF) << 16 | (buffer[offset + 2] & 0xFF) << 8 | buffer[offset + 3] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void skip(long amount) throws IOException {
/* 745 */     while (amount > 0L) {
/* 746 */       long skipped = this.input.skip(amount);
/* 747 */       if (skipped < 0L) {
/* 748 */         throw new EOFException();
/*     */       }
/* 750 */       amount -= skipped;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean checkSignature(byte[] buffer) {
/* 755 */     for (int i = 0; i < SIGNATURE.length; i++) {
/* 756 */       if (buffer[i] != SIGNATURE[i]) {
/* 757 */         return false;
/*     */       }
/*     */     } 
/* 760 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\PNGDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */