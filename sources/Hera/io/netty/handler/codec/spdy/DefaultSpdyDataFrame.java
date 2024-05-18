/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.IllegalReferenceCountException;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ public class DefaultSpdyDataFrame
/*     */   extends DefaultSpdyStreamFrame
/*     */   implements SpdyDataFrame
/*     */ {
/*     */   private final ByteBuf data;
/*     */   
/*     */   public DefaultSpdyDataFrame(int streamId) {
/*  36 */     this(streamId, Unpooled.buffer(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSpdyDataFrame(int streamId, ByteBuf data) {
/*  46 */     super(streamId);
/*  47 */     if (data == null) {
/*  48 */       throw new NullPointerException("data");
/*     */     }
/*  50 */     this.data = validate(data);
/*     */   }
/*     */   
/*     */   private static ByteBuf validate(ByteBuf data) {
/*  54 */     if (data.readableBytes() > 16777215) {
/*  55 */       throw new IllegalArgumentException("data payload cannot exceed 16777215 bytes");
/*     */     }
/*     */     
/*  58 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame setStreamId(int streamId) {
/*  63 */     super.setStreamId(streamId);
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame setLast(boolean last) {
/*  69 */     super.setLast(last);
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/*  75 */     if (this.data.refCnt() <= 0) {
/*  76 */       throw new IllegalReferenceCountException(this.data.refCnt());
/*     */     }
/*  78 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame copy() {
/*  83 */     SpdyDataFrame frame = new DefaultSpdyDataFrame(streamId(), content().copy());
/*  84 */     frame.setLast(isLast());
/*  85 */     return frame;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame duplicate() {
/*  90 */     SpdyDataFrame frame = new DefaultSpdyDataFrame(streamId(), content().duplicate());
/*  91 */     frame.setLast(isLast());
/*  92 */     return frame;
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/*  97 */     return this.data.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame retain() {
/* 102 */     this.data.retain();
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame retain(int increment) {
/* 108 */     this.data.retain(increment);
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/* 114 */     return this.data.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/* 119 */     return this.data.release(decrement);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     StringBuilder buf = new StringBuilder();
/* 125 */     buf.append(StringUtil.simpleClassName(this));
/* 126 */     buf.append("(last: ");
/* 127 */     buf.append(isLast());
/* 128 */     buf.append(')');
/* 129 */     buf.append(StringUtil.NEWLINE);
/* 130 */     buf.append("--> Stream-ID = ");
/* 131 */     buf.append(streamId());
/* 132 */     buf.append(StringUtil.NEWLINE);
/* 133 */     buf.append("--> Size = ");
/* 134 */     if (refCnt() == 0) {
/* 135 */       buf.append("(freed)");
/*     */     } else {
/* 137 */       buf.append(content().readableBytes());
/*     */     } 
/* 139 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\DefaultSpdyDataFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */