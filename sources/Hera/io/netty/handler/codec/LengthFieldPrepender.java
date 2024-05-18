/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
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
/*     */ @Sharable
/*     */ public class LengthFieldPrepender
/*     */   extends MessageToByteEncoder<ByteBuf>
/*     */ {
/*     */   private final int lengthFieldLength;
/*     */   private final boolean lengthIncludesLengthFieldLength;
/*     */   private final int lengthAdjustment;
/*     */   
/*     */   public LengthFieldPrepender(int lengthFieldLength) {
/*  66 */     this(lengthFieldLength, false);
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
/*     */   public LengthFieldPrepender(int lengthFieldLength, boolean lengthIncludesLengthFieldLength) {
/*  83 */     this(lengthFieldLength, 0, lengthIncludesLengthFieldLength);
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
/*     */   public LengthFieldPrepender(int lengthFieldLength, int lengthAdjustment) {
/*  98 */     this(lengthFieldLength, lengthAdjustment, false);
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
/*     */   public LengthFieldPrepender(int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
/* 117 */     if (lengthFieldLength != 1 && lengthFieldLength != 2 && lengthFieldLength != 3 && lengthFieldLength != 4 && lengthFieldLength != 8)
/*     */     {
/*     */       
/* 120 */       throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + lengthFieldLength);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 125 */     this.lengthFieldLength = lengthFieldLength;
/* 126 */     this.lengthIncludesLengthFieldLength = lengthIncludesLengthFieldLength;
/* 127 */     this.lengthAdjustment = lengthAdjustment;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
/* 132 */     int length = msg.readableBytes() + this.lengthAdjustment;
/* 133 */     if (this.lengthIncludesLengthFieldLength) {
/* 134 */       length += this.lengthFieldLength;
/*     */     }
/*     */     
/* 137 */     if (length < 0) {
/* 138 */       throw new IllegalArgumentException("Adjusted frame length (" + length + ") is less than zero");
/*     */     }
/*     */ 
/*     */     
/* 142 */     switch (this.lengthFieldLength) {
/*     */       case 1:
/* 144 */         if (length >= 256) {
/* 145 */           throw new IllegalArgumentException("length does not fit into a byte: " + length);
/*     */         }
/*     */         
/* 148 */         out.writeByte((byte)length);
/*     */         break;
/*     */       case 2:
/* 151 */         if (length >= 65536) {
/* 152 */           throw new IllegalArgumentException("length does not fit into a short integer: " + length);
/*     */         }
/*     */         
/* 155 */         out.writeShort((short)length);
/*     */         break;
/*     */       case 3:
/* 158 */         if (length >= 16777216) {
/* 159 */           throw new IllegalArgumentException("length does not fit into a medium integer: " + length);
/*     */         }
/*     */         
/* 162 */         out.writeMedium(length);
/*     */         break;
/*     */       case 4:
/* 165 */         out.writeInt(length);
/*     */         break;
/*     */       case 8:
/* 168 */         out.writeLong(length);
/*     */         break;
/*     */       default:
/* 171 */         throw new Error("should not reach here");
/*     */     } 
/*     */     
/* 174 */     out.writeBytes(msg, msg.readerIndex(), msg.readableBytes());
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\LengthFieldPrepender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */