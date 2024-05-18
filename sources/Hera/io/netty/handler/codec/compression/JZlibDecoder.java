/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.jcraft.jzlib.Inflater;
/*     */ import com.jcraft.jzlib.JZlib;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JZlibDecoder
/*     */   extends ZlibDecoder
/*     */ {
/*  27 */   private final Inflater z = new Inflater();
/*     */ 
/*     */   
/*     */   private byte[] dictionary;
/*     */ 
/*     */   
/*     */   private volatile boolean finished;
/*     */ 
/*     */   
/*     */   public JZlibDecoder() {
/*  37 */     this(ZlibWrapper.ZLIB);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JZlibDecoder(ZlibWrapper wrapper) {
/*  46 */     if (wrapper == null) {
/*  47 */       throw new NullPointerException("wrapper");
/*     */     }
/*     */     
/*  50 */     int resultCode = this.z.init(ZlibUtil.convertWrapperType(wrapper));
/*  51 */     if (resultCode != 0) {
/*  52 */       ZlibUtil.fail(this.z, "initialization failure", resultCode);
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
/*     */   public JZlibDecoder(byte[] dictionary) {
/*  64 */     if (dictionary == null) {
/*  65 */       throw new NullPointerException("dictionary");
/*     */     }
/*  67 */     this.dictionary = dictionary;
/*     */ 
/*     */     
/*  70 */     int resultCode = this.z.inflateInit(JZlib.W_ZLIB);
/*  71 */     if (resultCode != 0) {
/*  72 */       ZlibUtil.fail(this.z, "initialization failure", resultCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/*  82 */     return this.finished;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  87 */     if (this.finished) {
/*     */       
/*  89 */       in.skipBytes(in.readableBytes());
/*     */       
/*     */       return;
/*     */     } 
/*  93 */     if (!in.isReadable()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  99 */       int inputLength = in.readableBytes();
/* 100 */       this.z.avail_in = inputLength;
/* 101 */       if (in.hasArray()) {
/* 102 */         this.z.next_in = in.array();
/* 103 */         this.z.next_in_index = in.arrayOffset() + in.readerIndex();
/*     */       } else {
/* 105 */         byte[] array = new byte[inputLength];
/* 106 */         in.getBytes(in.readerIndex(), array);
/* 107 */         this.z.next_in = array;
/* 108 */         this.z.next_in_index = 0;
/*     */       } 
/* 110 */       int oldNextInIndex = this.z.next_in_index;
/*     */ 
/*     */       
/* 113 */       int maxOutputLength = inputLength << 1;
/* 114 */       ByteBuf decompressed = ctx.alloc().heapBuffer(maxOutputLength);
/*     */       
/*     */       try {
/*     */         while (true) {
/* 118 */           this.z.avail_out = maxOutputLength;
/* 119 */           decompressed.ensureWritable(maxOutputLength);
/* 120 */           this.z.next_out = decompressed.array();
/* 121 */           this.z.next_out_index = decompressed.arrayOffset() + decompressed.writerIndex();
/* 122 */           int oldNextOutIndex = this.z.next_out_index;
/*     */ 
/*     */           
/* 125 */           int resultCode = this.z.inflate(2);
/* 126 */           int outputLength = this.z.next_out_index - oldNextOutIndex;
/* 127 */           if (outputLength > 0) {
/* 128 */             decompressed.writerIndex(decompressed.writerIndex() + outputLength);
/*     */           }
/*     */           
/* 131 */           switch (resultCode) {
/*     */             case 2:
/* 133 */               if (this.dictionary == null) {
/* 134 */                 ZlibUtil.fail(this.z, "decompression failure", resultCode); continue;
/*     */               } 
/* 136 */               resultCode = this.z.inflateSetDictionary(this.dictionary, this.dictionary.length);
/* 137 */               if (resultCode != 0) {
/* 138 */                 ZlibUtil.fail(this.z, "failed to set the dictionary", resultCode);
/*     */               }
/*     */               continue;
/*     */             
/*     */             case 1:
/* 143 */               this.finished = true;
/* 144 */               this.z.inflateEnd();
/*     */               break;
/*     */             case 0:
/*     */               continue;
/*     */             case -5:
/* 149 */               if (this.z.avail_in <= 0) {
/*     */                 break;
/*     */               }
/*     */               continue;
/*     */           } 
/* 154 */           ZlibUtil.fail(this.z, "decompression failure", resultCode);
/*     */         } 
/*     */       } finally {
/*     */         
/* 158 */         in.skipBytes(this.z.next_in_index - oldNextInIndex);
/* 159 */         if (decompressed.isReadable()) {
/* 160 */           out.add(decompressed);
/*     */         } else {
/* 162 */           decompressed.release();
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 170 */       this.z.next_in = null;
/* 171 */       this.z.next_out = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\compression\JZlibDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */