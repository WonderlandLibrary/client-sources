/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.internal.RecyclableArrayList;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ByteToMessageDecoder
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   ByteBuf cumulation;
/*     */   private boolean singleDecode;
/*     */   private boolean decodeWasNull;
/*     */   private boolean first;
/*     */   
/*     */   protected ByteToMessageDecoder() {
/*  55 */     if (isSharable()) {
/*  56 */       throw new IllegalStateException("@Sharable annotation is not allowed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSingleDecode(boolean singleDecode) {
/*  67 */     this.singleDecode = singleDecode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSingleDecode() {
/*  77 */     return this.singleDecode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int actualReadableBytes() {
/*  87 */     return internalBuffer().readableBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteBuf internalBuffer() {
/*  96 */     if (this.cumulation != null) {
/*  97 */       return this.cumulation;
/*     */     }
/*  99 */     return Unpooled.EMPTY_BUFFER;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 105 */     ByteBuf buf = internalBuffer();
/* 106 */     int readable = buf.readableBytes();
/* 107 */     if (buf.isReadable()) {
/* 108 */       ByteBuf bytes = buf.readBytes(readable);
/* 109 */       buf.release();
/* 110 */       ctx.fireChannelRead(bytes);
/*     */     } else {
/* 112 */       buf.release();
/*     */     } 
/* 114 */     this.cumulation = null;
/* 115 */     ctx.fireChannelReadComplete();
/* 116 */     handlerRemoved0(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 127 */     if (msg instanceof ByteBuf) {
/* 128 */       RecyclableArrayList out = RecyclableArrayList.newInstance();
/*     */       try {
/* 130 */         ByteBuf data = (ByteBuf)msg;
/* 131 */         this.first = (this.cumulation == null);
/* 132 */         if (this.first) {
/* 133 */           this.cumulation = data;
/*     */         } else {
/* 135 */           if (this.cumulation.writerIndex() > this.cumulation.maxCapacity() - data.readableBytes() || this.cumulation.refCnt() > 1)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 144 */             expandCumulation(ctx, data.readableBytes());
/*     */           }
/* 146 */           this.cumulation.writeBytes(data);
/* 147 */           data.release();
/*     */         } 
/* 149 */         callDecode(ctx, this.cumulation, (List<Object>)out);
/* 150 */       } catch (DecoderException e) {
/* 151 */         throw e;
/* 152 */       } catch (Throwable t) {
/* 153 */         throw new DecoderException(t);
/*     */       } finally {
/* 155 */         if (this.cumulation != null && !this.cumulation.isReadable()) {
/* 156 */           this.cumulation.release();
/* 157 */           this.cumulation = null;
/*     */         } 
/* 159 */         int size = out.size();
/* 160 */         this.decodeWasNull = (size == 0);
/*     */         
/* 162 */         for (int i = 0; i < size; i++) {
/* 163 */           ctx.fireChannelRead(out.get(i));
/*     */         }
/* 165 */         out.recycle();
/*     */       } 
/*     */     } else {
/* 168 */       ctx.fireChannelRead(msg);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void expandCumulation(ChannelHandlerContext ctx, int readable) {
/* 173 */     ByteBuf oldCumulation = this.cumulation;
/* 174 */     this.cumulation = ctx.alloc().buffer(oldCumulation.readableBytes() + readable);
/* 175 */     this.cumulation.writeBytes(oldCumulation);
/* 176 */     oldCumulation.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 181 */     if (this.cumulation != null && !this.first && this.cumulation.refCnt() == 1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 189 */       this.cumulation.discardSomeReadBytes();
/*     */     }
/* 191 */     if (this.decodeWasNull) {
/* 192 */       this.decodeWasNull = false;
/* 193 */       if (!ctx.channel().config().isAutoRead()) {
/* 194 */         ctx.read();
/*     */       }
/*     */     } 
/* 197 */     ctx.fireChannelReadComplete();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 202 */     RecyclableArrayList out = RecyclableArrayList.newInstance();
/*     */     try {
/* 204 */       if (this.cumulation != null) {
/* 205 */         callDecode(ctx, this.cumulation, (List<Object>)out);
/* 206 */         decodeLast(ctx, this.cumulation, (List<Object>)out);
/*     */       } else {
/* 208 */         decodeLast(ctx, Unpooled.EMPTY_BUFFER, (List<Object>)out);
/*     */       } 
/* 210 */     } catch (DecoderException e) {
/* 211 */       throw e;
/* 212 */     } catch (Exception e) {
/* 213 */       throw new DecoderException(e);
/*     */     } finally {
/*     */       try {
/* 216 */         if (this.cumulation != null) {
/* 217 */           this.cumulation.release();
/* 218 */           this.cumulation = null;
/*     */         } 
/* 220 */         int size = out.size();
/* 221 */         for (int i = 0; i < size; i++) {
/* 222 */           ctx.fireChannelRead(out.get(i));
/*     */         }
/* 224 */         if (size > 0)
/*     */         {
/* 226 */           ctx.fireChannelReadComplete();
/*     */         }
/* 228 */         ctx.fireChannelInactive();
/*     */       } finally {
/*     */         
/* 231 */         out.recycle();
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
/*     */   protected void callDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
/*     */     try {
/* 246 */       while (in.isReadable()) {
/* 247 */         int outSize = out.size();
/* 248 */         int oldInputLength = in.readableBytes();
/* 249 */         decode(ctx, in, out);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 255 */         if (ctx.isRemoved()) {
/*     */           break;
/*     */         }
/*     */         
/* 259 */         if (outSize == out.size()) {
/* 260 */           if (oldInputLength == in.readableBytes()) {
/*     */             break;
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 267 */         if (oldInputLength == in.readableBytes()) {
/* 268 */           throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() did not read anything but decoded a message.");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 273 */         if (isSingleDecode()) {
/*     */           break;
/*     */         }
/*     */       } 
/* 277 */     } catch (DecoderException e) {
/* 278 */       throw e;
/* 279 */     } catch (Throwable cause) {
/* 280 */       throw new DecoderException(cause);
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
/*     */   protected abstract void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 304 */     decode(ctx, in, out);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\ByteToMessageDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */