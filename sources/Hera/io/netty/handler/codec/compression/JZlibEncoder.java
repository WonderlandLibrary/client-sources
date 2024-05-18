/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.jcraft.jzlib.Deflater;
/*     */ import com.jcraft.jzlib.JZlib;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.ChannelPromiseNotifier;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JZlibEncoder
/*     */   extends ZlibEncoder
/*     */ {
/*     */   private final int wrapperOverhead;
/*  38 */   private final Deflater z = new Deflater();
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean finished;
/*     */ 
/*     */   
/*     */   private volatile ChannelHandlerContext ctx;
/*     */ 
/*     */ 
/*     */   
/*     */   public JZlibEncoder() {
/*  50 */     this(6);
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
/*     */   public JZlibEncoder(int compressionLevel) {
/*  66 */     this(ZlibWrapper.ZLIB, compressionLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JZlibEncoder(ZlibWrapper wrapper) {
/*  77 */     this(wrapper, 6);
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
/*     */   public JZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
/*  93 */     this(wrapper, compressionLevel, 15, 8);
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
/*     */   public JZlibEncoder(ZlibWrapper wrapper, int compressionLevel, int windowBits, int memLevel) {
/* 120 */     if (compressionLevel < 0 || compressionLevel > 9) {
/* 121 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */ 
/*     */     
/* 125 */     if (windowBits < 9 || windowBits > 15) {
/* 126 */       throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
/*     */     }
/*     */     
/* 129 */     if (memLevel < 1 || memLevel > 9) {
/* 130 */       throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
/*     */     }
/*     */     
/* 133 */     if (wrapper == null) {
/* 134 */       throw new NullPointerException("wrapper");
/*     */     }
/* 136 */     if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
/* 137 */       throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " + "allowed for compression.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 142 */     int resultCode = this.z.init(compressionLevel, windowBits, memLevel, ZlibUtil.convertWrapperType(wrapper));
/*     */ 
/*     */     
/* 145 */     if (resultCode != 0) {
/* 146 */       ZlibUtil.fail(this.z, "initialization failure", resultCode);
/*     */     }
/*     */     
/* 149 */     this.wrapperOverhead = ZlibUtil.wrapperOverhead(wrapper);
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
/*     */   public JZlibEncoder(byte[] dictionary) {
/* 164 */     this(6, dictionary);
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
/*     */   public JZlibEncoder(int compressionLevel, byte[] dictionary) {
/* 183 */     this(compressionLevel, 15, 8, dictionary);
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
/*     */   public JZlibEncoder(int compressionLevel, int windowBits, int memLevel, byte[] dictionary) {
/* 212 */     if (compressionLevel < 0 || compressionLevel > 9) {
/* 213 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/* 215 */     if (windowBits < 9 || windowBits > 15) {
/* 216 */       throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
/*     */     }
/*     */     
/* 219 */     if (memLevel < 1 || memLevel > 9) {
/* 220 */       throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
/*     */     }
/*     */     
/* 223 */     if (dictionary == null) {
/* 224 */       throw new NullPointerException("dictionary");
/*     */     }
/*     */     
/* 227 */     int resultCode = this.z.deflateInit(compressionLevel, windowBits, memLevel, JZlib.W_ZLIB);
/*     */ 
/*     */     
/* 230 */     if (resultCode != 0) {
/* 231 */       ZlibUtil.fail(this.z, "initialization failure", resultCode);
/*     */     } else {
/* 233 */       resultCode = this.z.deflateSetDictionary(dictionary, dictionary.length);
/* 234 */       if (resultCode != 0) {
/* 235 */         ZlibUtil.fail(this.z, "failed to set the dictionary", resultCode);
/*     */       }
/*     */     } 
/*     */     
/* 239 */     this.wrapperOverhead = ZlibUtil.wrapperOverhead(ZlibWrapper.ZLIB);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 244 */     return close(ctx().channel().newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close(final ChannelPromise promise) {
/* 249 */     ChannelHandlerContext ctx = ctx();
/* 250 */     EventExecutor executor = ctx.executor();
/* 251 */     if (executor.inEventLoop()) {
/* 252 */       return finishEncode(ctx, promise);
/*     */     }
/* 254 */     final ChannelPromise p = ctx.newPromise();
/* 255 */     executor.execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 258 */             ChannelFuture f = JZlibEncoder.this.finishEncode(JZlibEncoder.this.ctx(), p);
/* 259 */             f.addListener((GenericFutureListener)new ChannelPromiseNotifier(new ChannelPromise[] { this.val$promise }));
/*     */           }
/*     */         });
/* 262 */     return (ChannelFuture)p;
/*     */   }
/*     */ 
/*     */   
/*     */   private ChannelHandlerContext ctx() {
/* 267 */     ChannelHandlerContext ctx = this.ctx;
/* 268 */     if (ctx == null) {
/* 269 */       throw new IllegalStateException("not added to a pipeline");
/*     */     }
/* 271 */     return ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 276 */     return this.finished;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
/* 281 */     if (this.finished) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 287 */       int resultCode, inputLength = in.readableBytes();
/* 288 */       boolean inHasArray = in.hasArray();
/* 289 */       this.z.avail_in = inputLength;
/* 290 */       if (inHasArray) {
/* 291 */         this.z.next_in = in.array();
/* 292 */         this.z.next_in_index = in.arrayOffset() + in.readerIndex();
/*     */       } else {
/* 294 */         byte[] array = new byte[inputLength];
/* 295 */         in.getBytes(in.readerIndex(), array);
/* 296 */         this.z.next_in = array;
/* 297 */         this.z.next_in_index = 0;
/*     */       } 
/* 299 */       int oldNextInIndex = this.z.next_in_index;
/*     */ 
/*     */       
/* 302 */       int maxOutputLength = (int)Math.ceil(inputLength * 1.001D) + 12 + this.wrapperOverhead;
/* 303 */       out.ensureWritable(maxOutputLength);
/* 304 */       this.z.avail_out = maxOutputLength;
/* 305 */       this.z.next_out = out.array();
/* 306 */       this.z.next_out_index = out.arrayOffset() + out.writerIndex();
/* 307 */       int oldNextOutIndex = this.z.next_out_index;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 312 */         resultCode = this.z.deflate(2);
/*     */       } finally {
/* 314 */         in.skipBytes(this.z.next_in_index - oldNextInIndex);
/*     */       } 
/*     */       
/* 317 */       if (resultCode != 0) {
/* 318 */         ZlibUtil.fail(this.z, "compression failure", resultCode);
/*     */       }
/*     */       
/* 321 */       int outputLength = this.z.next_out_index - oldNextOutIndex;
/* 322 */       if (outputLength > 0) {
/* 323 */         out.writerIndex(out.writerIndex() + outputLength);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 330 */       this.z.next_in = null;
/* 331 */       this.z.next_out = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) {
/* 339 */     ChannelFuture f = finishEncode(ctx, ctx.newPromise());
/* 340 */     f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture f) throws Exception {
/* 343 */             ctx.close(promise);
/*     */           }
/*     */         });
/*     */     
/* 347 */     if (!f.isDone())
/*     */     {
/* 349 */       ctx.executor().schedule(new Runnable()
/*     */           {
/*     */             public void run() {
/* 352 */               ctx.close(promise);
/*     */             }
/*     */           },  10L, TimeUnit.SECONDS); } 
/*     */   }
/*     */   
/*     */   private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
/*     */     ByteBuf footer;
/* 359 */     if (this.finished) {
/* 360 */       promise.setSuccess();
/* 361 */       return (ChannelFuture)promise;
/*     */     } 
/* 363 */     this.finished = true;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 368 */       this.z.next_in = EmptyArrays.EMPTY_BYTES;
/* 369 */       this.z.next_in_index = 0;
/* 370 */       this.z.avail_in = 0;
/*     */ 
/*     */       
/* 373 */       byte[] out = new byte[32];
/* 374 */       this.z.next_out = out;
/* 375 */       this.z.next_out_index = 0;
/* 376 */       this.z.avail_out = out.length;
/*     */ 
/*     */       
/* 379 */       int resultCode = this.z.deflate(4);
/* 380 */       if (resultCode != 0 && resultCode != 1) {
/* 381 */         promise.setFailure((Throwable)ZlibUtil.deflaterException(this.z, "compression failure", resultCode));
/* 382 */         return (ChannelFuture)promise;
/* 383 */       }  if (this.z.next_out_index != 0) {
/* 384 */         footer = Unpooled.wrappedBuffer(out, 0, this.z.next_out_index);
/*     */       } else {
/* 386 */         footer = Unpooled.EMPTY_BUFFER;
/*     */       } 
/*     */     } finally {
/* 389 */       this.z.deflateEnd();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 395 */       this.z.next_in = null;
/* 396 */       this.z.next_out = null;
/*     */     } 
/* 398 */     return ctx.writeAndFlush(footer, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 403 */     this.ctx = ctx;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\compression\JZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */