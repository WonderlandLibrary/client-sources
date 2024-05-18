/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.util.List;
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
/*     */ public class JdkZlibDecoder
/*     */   extends ZlibDecoder
/*     */ {
/*     */   private static final int FHCRC = 2;
/*     */   private static final int FEXTRA = 4;
/*     */   private static final int FNAME = 8;
/*     */   private static final int FCOMMENT = 16;
/*     */   private static final int FRESERVED = 224;
/*     */   private Inflater inflater;
/*     */   private final byte[] dictionary;
/*     */   private final CRC32 crc;
/*     */   
/*     */   private enum GzipState
/*     */   {
/*  44 */     HEADER_START,
/*  45 */     HEADER_END,
/*  46 */     FLG_READ,
/*  47 */     XLEN_READ,
/*  48 */     SKIP_FNAME,
/*  49 */     SKIP_COMMENT,
/*  50 */     PROCESS_FHCRC,
/*  51 */     FOOTER_START;
/*     */   }
/*     */   
/*  54 */   private GzipState gzipState = GzipState.HEADER_START;
/*  55 */   private int flags = -1;
/*  56 */   private int xlen = -1;
/*     */ 
/*     */   
/*     */   private volatile boolean finished;
/*     */ 
/*     */   
/*     */   private boolean decideZlibOrNone;
/*     */ 
/*     */   
/*     */   public JdkZlibDecoder() {
/*  66 */     this(ZlibWrapper.ZLIB, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkZlibDecoder(byte[] dictionary) {
/*  75 */     this(ZlibWrapper.ZLIB, dictionary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkZlibDecoder(ZlibWrapper wrapper) {
/*  84 */     this(wrapper, null);
/*     */   }
/*     */   
/*     */   private JdkZlibDecoder(ZlibWrapper wrapper, byte[] dictionary) {
/*  88 */     if (wrapper == null) {
/*  89 */       throw new NullPointerException("wrapper");
/*     */     }
/*  91 */     switch (wrapper) {
/*     */       case FOOTER_START:
/*  93 */         this.inflater = new Inflater(true);
/*  94 */         this.crc = new CRC32();
/*     */         break;
/*     */       case HEADER_START:
/*  97 */         this.inflater = new Inflater(true);
/*  98 */         this.crc = null;
/*     */         break;
/*     */       case FLG_READ:
/* 101 */         this.inflater = new Inflater();
/* 102 */         this.crc = null;
/*     */         break;
/*     */       
/*     */       case XLEN_READ:
/* 106 */         this.decideZlibOrNone = true;
/* 107 */         this.crc = null;
/*     */         break;
/*     */       default:
/* 110 */         throw new IllegalArgumentException("Only GZIP or ZLIB is supported, but you used " + wrapper);
/*     */     } 
/* 112 */     this.dictionary = dictionary;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 117 */     return this.finished;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 122 */     if (this.finished) {
/*     */       
/* 124 */       in.skipBytes(in.readableBytes());
/*     */       
/*     */       return;
/*     */     } 
/* 128 */     if (!in.isReadable()) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     if (this.decideZlibOrNone) {
/*     */       
/* 134 */       if (in.readableBytes() < 2) {
/*     */         return;
/*     */       }
/*     */       
/* 138 */       boolean nowrap = !looksLikeZlib(in.getShort(0));
/* 139 */       this.inflater = new Inflater(nowrap);
/* 140 */       this.decideZlibOrNone = false;
/*     */     } 
/*     */     
/* 143 */     if (this.crc != null) {
/* 144 */       switch (this.gzipState) {
/*     */         case FOOTER_START:
/* 146 */           if (readGZIPFooter(in)) {
/* 147 */             this.finished = true;
/*     */           }
/*     */           return;
/*     */       } 
/* 151 */       if (this.gzipState != GzipState.HEADER_END && 
/* 152 */         !readGZIPHeader(in)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 159 */     int readableBytes = in.readableBytes();
/* 160 */     if (in.hasArray()) {
/* 161 */       this.inflater.setInput(in.array(), in.arrayOffset() + in.readerIndex(), in.readableBytes());
/*     */     } else {
/* 163 */       byte[] array = new byte[in.readableBytes()];
/* 164 */       in.getBytes(in.readerIndex(), array);
/* 165 */       this.inflater.setInput(array);
/*     */     } 
/*     */     
/* 168 */     int maxOutputLength = this.inflater.getRemaining() << 1;
/* 169 */     ByteBuf decompressed = ctx.alloc().heapBuffer(maxOutputLength);
/*     */     try {
/* 171 */       boolean readFooter = false;
/* 172 */       byte[] outArray = decompressed.array();
/* 173 */       while (!this.inflater.needsInput()) {
/* 174 */         int writerIndex = decompressed.writerIndex();
/* 175 */         int outIndex = decompressed.arrayOffset() + writerIndex;
/* 176 */         int length = decompressed.writableBytes();
/*     */         
/* 178 */         if (length == 0) {
/*     */           
/* 180 */           out.add(decompressed);
/* 181 */           decompressed = ctx.alloc().heapBuffer(maxOutputLength);
/* 182 */           outArray = decompressed.array();
/*     */           
/*     */           continue;
/*     */         } 
/* 186 */         int outputLength = this.inflater.inflate(outArray, outIndex, length);
/* 187 */         if (outputLength > 0) {
/* 188 */           decompressed.writerIndex(writerIndex + outputLength);
/* 189 */           if (this.crc != null) {
/* 190 */             this.crc.update(outArray, outIndex, outputLength);
/*     */           }
/*     */         }
/* 193 */         else if (this.inflater.needsDictionary()) {
/* 194 */           if (this.dictionary == null) {
/* 195 */             throw new DecompressionException("decompression failure, unable to set dictionary as non was specified");
/*     */           }
/*     */           
/* 198 */           this.inflater.setDictionary(this.dictionary);
/*     */         } 
/*     */ 
/*     */         
/* 202 */         if (this.inflater.finished()) {
/* 203 */           if (this.crc == null) {
/* 204 */             this.finished = true; break;
/*     */           } 
/* 206 */           readFooter = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 212 */       in.skipBytes(readableBytes - this.inflater.getRemaining());
/*     */       
/* 214 */       if (readFooter) {
/* 215 */         this.gzipState = GzipState.FOOTER_START;
/* 216 */         if (readGZIPFooter(in)) {
/* 217 */           this.finished = true;
/*     */         }
/*     */       } 
/* 220 */     } catch (DataFormatException e) {
/* 221 */       throw new DecompressionException("decompression failure", e);
/*     */     } finally {
/*     */       
/* 224 */       if (decompressed.isReadable()) {
/* 225 */         out.add(decompressed);
/*     */       } else {
/* 227 */         decompressed.release();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
/* 234 */     super.handlerRemoved0(ctx);
/* 235 */     if (this.inflater != null)
/* 236 */       this.inflater.end(); 
/*     */   } private boolean readGZIPHeader(ByteBuf in) {
/*     */     int magic0;
/*     */     int magic1;
/*     */     int method;
/* 241 */     switch (this.gzipState) {
/*     */       case HEADER_START:
/* 243 */         if (in.readableBytes() < 10) {
/* 244 */           return false;
/*     */         }
/*     */         
/* 247 */         magic0 = in.readByte();
/* 248 */         magic1 = in.readByte();
/*     */         
/* 250 */         if (magic0 != 31) {
/* 251 */           throw new DecompressionException("Input is not in the GZIP format");
/*     */         }
/* 253 */         this.crc.update(magic0);
/* 254 */         this.crc.update(magic1);
/*     */         
/* 256 */         method = in.readUnsignedByte();
/* 257 */         if (method != 8) {
/* 258 */           throw new DecompressionException("Unsupported compression method " + method + " in the GZIP header");
/*     */         }
/*     */         
/* 261 */         this.crc.update(method);
/*     */         
/* 263 */         this.flags = in.readUnsignedByte();
/* 264 */         this.crc.update(this.flags);
/*     */         
/* 266 */         if ((this.flags & 0xE0) != 0) {
/* 267 */           throw new DecompressionException("Reserved flags are set in the GZIP header");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 272 */         this.crc.update(in.readByte());
/* 273 */         this.crc.update(in.readByte());
/* 274 */         this.crc.update(in.readByte());
/* 275 */         this.crc.update(in.readByte());
/*     */         
/* 277 */         this.crc.update(in.readUnsignedByte());
/* 278 */         this.crc.update(in.readUnsignedByte());
/*     */         
/* 280 */         this.gzipState = GzipState.FLG_READ;
/*     */       case FLG_READ:
/* 282 */         if ((this.flags & 0x4) != 0) {
/* 283 */           if (in.readableBytes() < 2) {
/* 284 */             return false;
/*     */           }
/* 286 */           int xlen1 = in.readUnsignedByte();
/* 287 */           int xlen2 = in.readUnsignedByte();
/* 288 */           this.crc.update(xlen1);
/* 289 */           this.crc.update(xlen2);
/*     */           
/* 291 */           this.xlen |= xlen1 << 8 | xlen2;
/*     */         } 
/* 293 */         this.gzipState = GzipState.XLEN_READ;
/*     */       case XLEN_READ:
/* 295 */         if (this.xlen != -1) {
/* 296 */           if (in.readableBytes() < this.xlen) {
/* 297 */             return false;
/*     */           }
/* 299 */           byte[] xtra = new byte[this.xlen];
/* 300 */           in.readBytes(xtra);
/* 301 */           this.crc.update(xtra);
/*     */         } 
/* 303 */         this.gzipState = GzipState.SKIP_FNAME;
/*     */       case SKIP_FNAME:
/* 305 */         if ((this.flags & 0x8) != 0) {
/* 306 */           if (!in.isReadable()) {
/* 307 */             return false;
/*     */           }
/*     */           do {
/* 310 */             int b = in.readUnsignedByte();
/* 311 */             this.crc.update(b);
/* 312 */             if (b == 0) {
/*     */               break;
/*     */             }
/* 315 */           } while (in.isReadable());
/*     */         } 
/* 317 */         this.gzipState = GzipState.SKIP_COMMENT;
/*     */       case SKIP_COMMENT:
/* 319 */         if ((this.flags & 0x10) != 0) {
/* 320 */           if (!in.isReadable()) {
/* 321 */             return false;
/*     */           }
/*     */           do {
/* 324 */             int b = in.readUnsignedByte();
/* 325 */             this.crc.update(b);
/* 326 */             if (b == 0) {
/*     */               break;
/*     */             }
/* 329 */           } while (in.isReadable());
/*     */         } 
/* 331 */         this.gzipState = GzipState.PROCESS_FHCRC;
/*     */       case PROCESS_FHCRC:
/* 333 */         if ((this.flags & 0x2) != 0) {
/* 334 */           if (in.readableBytes() < 4) {
/* 335 */             return false;
/*     */           }
/* 337 */           verifyCrc(in);
/*     */         } 
/* 339 */         this.crc.reset();
/* 340 */         this.gzipState = GzipState.HEADER_END;
/*     */       case HEADER_END:
/* 342 */         return true;
/*     */     } 
/* 344 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean readGZIPFooter(ByteBuf buf) {
/* 349 */     if (buf.readableBytes() < 8) {
/* 350 */       return false;
/*     */     }
/*     */     
/* 353 */     verifyCrc(buf);
/*     */ 
/*     */     
/* 356 */     int dataLength = 0;
/* 357 */     for (int i = 0; i < 4; i++) {
/* 358 */       dataLength |= buf.readUnsignedByte() << i * 8;
/*     */     }
/* 360 */     int readLength = this.inflater.getTotalOut();
/* 361 */     if (dataLength != readLength) {
/* 362 */       throw new DecompressionException("Number of bytes mismatch. Expected: " + dataLength + ", Got: " + readLength);
/*     */     }
/*     */     
/* 365 */     return true;
/*     */   }
/*     */   
/*     */   private void verifyCrc(ByteBuf in) {
/* 369 */     long crcValue = 0L;
/* 370 */     for (int i = 0; i < 4; i++) {
/* 371 */       crcValue |= in.readUnsignedByte() << i * 8;
/*     */     }
/* 373 */     long readCrc = this.crc.getValue();
/* 374 */     if (crcValue != readCrc) {
/* 375 */       throw new DecompressionException("CRC value missmatch. Expected: " + crcValue + ", Got: " + readCrc);
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
/*     */   private static boolean looksLikeZlib(short cmf_flg) {
/* 388 */     return ((cmf_flg & 0x7800) == 30720 && cmf_flg % 31 == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\compression\JdkZlibDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */