/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.ChannelPromiseNotifier;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.zip.CRC32;
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
/*     */ 
/*     */ 
/*     */ public class JdkZlibEncoder
/*     */   extends ZlibEncoder
/*     */ {
/*     */   private final ZlibWrapper wrapper;
/*     */   private final Deflater deflater;
/*     */   private volatile boolean finished;
/*     */   private volatile ChannelHandlerContext ctx;
/*  43 */   private final CRC32 crc = new CRC32();
/*  44 */   private static final byte[] gzipHeader = new byte[] { 31, -117, 8, 0, 0, 0, 0, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean writeHeader = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkZlibEncoder() {
/*  54 */     this(6);
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
/*     */   public JdkZlibEncoder(int compressionLevel) {
/*  69 */     this(ZlibWrapper.ZLIB, compressionLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkZlibEncoder(ZlibWrapper wrapper) {
/*  79 */     this(wrapper, 6);
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
/*     */   public JdkZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
/*  94 */     if (compressionLevel < 0 || compressionLevel > 9) {
/*  95 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/*  98 */     if (wrapper == null) {
/*  99 */       throw new NullPointerException("wrapper");
/*     */     }
/* 101 */     if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
/* 102 */       throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " + "allowed for compression.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 107 */     this.wrapper = wrapper;
/* 108 */     this.deflater = new Deflater(compressionLevel, (wrapper != ZlibWrapper.ZLIB));
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
/*     */   public JdkZlibEncoder(byte[] dictionary) {
/* 122 */     this(6, dictionary);
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
/*     */   public JdkZlibEncoder(int compressionLevel, byte[] dictionary) {
/* 140 */     if (compressionLevel < 0 || compressionLevel > 9) {
/* 141 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/* 144 */     if (dictionary == null) {
/* 145 */       throw new NullPointerException("dictionary");
/*     */     }
/*     */     
/* 148 */     this.wrapper = ZlibWrapper.ZLIB;
/* 149 */     this.deflater = new Deflater(compressionLevel);
/* 150 */     this.deflater.setDictionary(dictionary);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 155 */     return close(ctx().newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close(final ChannelPromise promise) {
/* 160 */     ChannelHandlerContext ctx = ctx();
/* 161 */     EventExecutor executor = ctx.executor();
/* 162 */     if (executor.inEventLoop()) {
/* 163 */       return finishEncode(ctx, promise);
/*     */     }
/* 165 */     final ChannelPromise p = ctx.newPromise();
/* 166 */     executor.execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 169 */             ChannelFuture f = JdkZlibEncoder.this.finishEncode(JdkZlibEncoder.this.ctx(), p);
/* 170 */             f.addListener((GenericFutureListener)new ChannelPromiseNotifier(new ChannelPromise[] { this.val$promise }));
/*     */           }
/*     */         });
/* 173 */     return (ChannelFuture)p;
/*     */   }
/*     */ 
/*     */   
/*     */   private ChannelHandlerContext ctx() {
/* 178 */     ChannelHandlerContext ctx = this.ctx;
/* 179 */     if (ctx == null) {
/* 180 */       throw new IllegalStateException("not added to a pipeline");
/*     */     }
/* 182 */     return ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 187 */     return this.finished;
/*     */   }
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf uncompressed, ByteBuf out) throws Exception {
/*     */     int offset;
/*     */     byte[] inAry;
/* 192 */     if (this.finished) {
/* 193 */       out.writeBytes(uncompressed);
/*     */       
/*     */       return;
/*     */     } 
/* 197 */     int len = uncompressed.readableBytes();
/*     */ 
/*     */     
/* 200 */     if (uncompressed.hasArray()) {
/*     */       
/* 202 */       inAry = uncompressed.array();
/* 203 */       offset = uncompressed.arrayOffset() + uncompressed.readerIndex();
/*     */       
/* 205 */       uncompressed.skipBytes(len);
/*     */     } else {
/* 207 */       inAry = new byte[len];
/* 208 */       uncompressed.readBytes(inAry);
/* 209 */       offset = 0;
/*     */     } 
/*     */     
/* 212 */     if (this.writeHeader) {
/* 213 */       this.writeHeader = false;
/* 214 */       if (this.wrapper == ZlibWrapper.GZIP) {
/* 215 */         out.writeBytes(gzipHeader);
/*     */       }
/*     */     } 
/*     */     
/* 219 */     if (this.wrapper == ZlibWrapper.GZIP) {
/* 220 */       this.crc.update(inAry, offset, len);
/*     */     }
/*     */     
/* 223 */     this.deflater.setInput(inAry, offset, len);
/* 224 */     while (!this.deflater.needsInput()) {
/* 225 */       deflate(out);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect) throws Exception {
/* 232 */     int sizeEstimate = (int)Math.ceil(msg.readableBytes() * 1.001D) + 12;
/* 233 */     if (this.writeHeader) {
/* 234 */       switch (this.wrapper) {
/*     */         case GZIP:
/* 236 */           sizeEstimate += gzipHeader.length;
/*     */           break;
/*     */         case ZLIB:
/* 239 */           sizeEstimate += 2;
/*     */           break;
/*     */       } 
/*     */     }
/* 243 */     return ctx.alloc().heapBuffer(sizeEstimate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
/* 248 */     ChannelFuture f = finishEncode(ctx, ctx.newPromise());
/* 249 */     f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture f) throws Exception {
/* 252 */             ctx.close(promise);
/*     */           }
/*     */         });
/*     */     
/* 256 */     if (!f.isDone())
/*     */     {
/* 258 */       ctx.executor().schedule(new Runnable()
/*     */           {
/*     */             public void run() {
/* 261 */               ctx.close(promise);
/*     */             }
/*     */           },  10L, TimeUnit.SECONDS);
/*     */     }
/*     */   }
/*     */   
/*     */   private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 268 */     if (this.finished) {
/* 269 */       promise.setSuccess();
/* 270 */       return (ChannelFuture)promise;
/*     */     } 
/*     */     
/* 273 */     this.finished = true;
/* 274 */     ByteBuf footer = ctx.alloc().heapBuffer();
/* 275 */     if (this.writeHeader && this.wrapper == ZlibWrapper.GZIP) {
/*     */       
/* 277 */       this.writeHeader = false;
/* 278 */       footer.writeBytes(gzipHeader);
/*     */     } 
/*     */     
/* 281 */     this.deflater.finish();
/*     */     
/* 283 */     while (!this.deflater.finished()) {
/* 284 */       deflate(footer);
/* 285 */       if (!footer.isWritable()) {
/*     */         
/* 287 */         ctx.write(footer);
/* 288 */         footer = ctx.alloc().heapBuffer();
/*     */       } 
/*     */     } 
/* 291 */     if (this.wrapper == ZlibWrapper.GZIP) {
/* 292 */       int crcValue = (int)this.crc.getValue();
/* 293 */       int uncBytes = this.deflater.getTotalIn();
/* 294 */       footer.writeByte(crcValue);
/* 295 */       footer.writeByte(crcValue >>> 8);
/* 296 */       footer.writeByte(crcValue >>> 16);
/* 297 */       footer.writeByte(crcValue >>> 24);
/* 298 */       footer.writeByte(uncBytes);
/* 299 */       footer.writeByte(uncBytes >>> 8);
/* 300 */       footer.writeByte(uncBytes >>> 16);
/* 301 */       footer.writeByte(uncBytes >>> 24);
/*     */     } 
/* 303 */     this.deflater.end();
/* 304 */     return ctx.writeAndFlush(footer, promise);
/*     */   }
/*     */   
/*     */   private void deflate(ByteBuf out) {
/*     */     int numBytes;
/*     */     do {
/* 310 */       int writerIndex = out.writerIndex();
/* 311 */       numBytes = this.deflater.deflate(out.array(), out.arrayOffset() + writerIndex, out.writableBytes(), 2);
/*     */       
/* 313 */       out.writerIndex(writerIndex + numBytes);
/* 314 */     } while (numBytes > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 319 */     this.ctx = ctx;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\compression\JdkZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */