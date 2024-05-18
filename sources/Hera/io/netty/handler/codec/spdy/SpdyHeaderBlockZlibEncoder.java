/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.util.zip.Deflater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SpdyHeaderBlockZlibEncoder
/*     */   extends SpdyHeaderBlockRawEncoder
/*     */ {
/*     */   private final Deflater compressor;
/*     */   private boolean finished;
/*     */   
/*     */   SpdyHeaderBlockZlibEncoder(SpdyVersion spdyVersion, int compressionLevel) {
/*  32 */     super(spdyVersion);
/*  33 */     if (compressionLevel < 0 || compressionLevel > 9) {
/*  34 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/*  37 */     this.compressor = new Deflater(compressionLevel);
/*  38 */     this.compressor.setDictionary(SpdyCodecUtil.SPDY_DICT);
/*     */   }
/*     */   
/*     */   private int setInput(ByteBuf decompressed) {
/*  42 */     int len = decompressed.readableBytes();
/*     */     
/*  44 */     if (decompressed.hasArray()) {
/*  45 */       this.compressor.setInput(decompressed.array(), decompressed.arrayOffset() + decompressed.readerIndex(), len);
/*     */     } else {
/*  47 */       byte[] in = new byte[len];
/*  48 */       decompressed.getBytes(decompressed.readerIndex(), in);
/*  49 */       this.compressor.setInput(in, 0, in.length);
/*     */     } 
/*     */     
/*  52 */     return len;
/*     */   }
/*     */   
/*     */   private void encode(ByteBuf compressed) {
/*  56 */     while (compressInto(compressed))
/*     */     {
/*  58 */       compressed.ensureWritable(compressed.capacity() << 1);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean compressInto(ByteBuf compressed) {
/*  63 */     byte[] out = compressed.array();
/*  64 */     int off = compressed.arrayOffset() + compressed.writerIndex();
/*  65 */     int toWrite = compressed.writableBytes();
/*  66 */     int numBytes = this.compressor.deflate(out, off, toWrite, 2);
/*  67 */     compressed.writerIndex(compressed.writerIndex() + numBytes);
/*  68 */     return (numBytes == toWrite);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf encode(SpdyHeadersFrame frame) throws Exception {
/*  73 */     if (frame == null) {
/*  74 */       throw new IllegalArgumentException("frame");
/*     */     }
/*     */     
/*  77 */     if (this.finished) {
/*  78 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/*  81 */     ByteBuf decompressed = super.encode(frame);
/*  82 */     if (decompressed.readableBytes() == 0) {
/*  83 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/*  86 */     ByteBuf compressed = decompressed.alloc().heapBuffer(decompressed.readableBytes());
/*  87 */     int len = setInput(decompressed);
/*  88 */     encode(compressed);
/*  89 */     decompressed.skipBytes(len);
/*     */     
/*  91 */     return compressed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/*  96 */     if (this.finished) {
/*     */       return;
/*     */     }
/*  99 */     this.finished = true;
/* 100 */     this.compressor.end();
/* 101 */     super.end();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */