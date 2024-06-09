/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import com.jcraft.jzlib.Deflater;
/*     */ import com.jcraft.jzlib.JZlib;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.compression.CompressionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SpdyHeaderBlockJZlibEncoder
/*     */   extends SpdyHeaderBlockRawEncoder
/*     */ {
/*  28 */   private final Deflater z = new Deflater();
/*     */   
/*     */   private boolean finished;
/*     */ 
/*     */   
/*     */   SpdyHeaderBlockJZlibEncoder(SpdyVersion version, int compressionLevel, int windowBits, int memLevel) {
/*  34 */     super(version);
/*  35 */     if (compressionLevel < 0 || compressionLevel > 9) {
/*  36 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/*  39 */     if (windowBits < 9 || windowBits > 15) {
/*  40 */       throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
/*     */     }
/*     */     
/*  43 */     if (memLevel < 1 || memLevel > 9) {
/*  44 */       throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
/*     */     }
/*     */ 
/*     */     
/*  48 */     int resultCode = this.z.deflateInit(compressionLevel, windowBits, memLevel, JZlib.W_ZLIB);
/*     */     
/*  50 */     if (resultCode != 0) {
/*  51 */       throw new CompressionException("failed to initialize an SPDY header block deflater: " + resultCode);
/*     */     }
/*     */     
/*  54 */     resultCode = this.z.deflateSetDictionary(SpdyCodecUtil.SPDY_DICT, SpdyCodecUtil.SPDY_DICT.length);
/*  55 */     if (resultCode != 0) {
/*  56 */       throw new CompressionException("failed to set the SPDY dictionary: " + resultCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setInput(ByteBuf decompressed) {
/*  63 */     byte[] in = new byte[decompressed.readableBytes()];
/*  64 */     decompressed.readBytes(in);
/*  65 */     this.z.next_in = in;
/*  66 */     this.z.next_in_index = 0;
/*  67 */     this.z.avail_in = in.length;
/*     */   }
/*     */   
/*     */   private void encode(ByteBuf compressed) {
/*     */     try {
/*  72 */       byte[] out = new byte[(int)Math.ceil(this.z.next_in.length * 1.001D) + 12];
/*  73 */       this.z.next_out = out;
/*  74 */       this.z.next_out_index = 0;
/*  75 */       this.z.avail_out = out.length;
/*     */       
/*  77 */       int resultCode = this.z.deflate(2);
/*  78 */       if (resultCode != 0) {
/*  79 */         throw new CompressionException("compression failure: " + resultCode);
/*     */       }
/*     */       
/*  82 */       if (this.z.next_out_index != 0) {
/*  83 */         compressed.writeBytes(out, 0, this.z.next_out_index);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/*  90 */       this.z.next_in = null;
/*  91 */       this.z.next_out = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf encode(SpdyHeadersFrame frame) throws Exception {
/*  97 */     if (frame == null) {
/*  98 */       throw new IllegalArgumentException("frame");
/*     */     }
/*     */     
/* 101 */     if (this.finished) {
/* 102 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/* 105 */     ByteBuf decompressed = super.encode(frame);
/* 106 */     if (decompressed.readableBytes() == 0) {
/* 107 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/* 110 */     ByteBuf compressed = decompressed.alloc().buffer();
/* 111 */     setInput(decompressed);
/* 112 */     encode(compressed);
/* 113 */     return compressed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/* 118 */     if (this.finished) {
/*     */       return;
/*     */     }
/* 121 */     this.finished = true;
/* 122 */     this.z.deflateEnd();
/* 123 */     this.z.next_in = null;
/* 124 */     this.z.next_out = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockJZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */