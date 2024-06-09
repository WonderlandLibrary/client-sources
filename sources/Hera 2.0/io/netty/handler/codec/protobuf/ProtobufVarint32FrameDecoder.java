/*    */ package io.netty.handler.codec.protobuf;
/*    */ 
/*    */ import com.google.protobuf.CodedInputStream;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import io.netty.handler.codec.CorruptedFrameException;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProtobufVarint32FrameDecoder
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 49 */     in.markReaderIndex();
/* 50 */     byte[] buf = new byte[5];
/* 51 */     for (int i = 0; i < buf.length; i++) {
/* 52 */       if (!in.isReadable()) {
/* 53 */         in.resetReaderIndex();
/*    */         
/*    */         return;
/*    */       } 
/* 57 */       buf[i] = in.readByte();
/* 58 */       if (buf[i] >= 0) {
/* 59 */         int length = CodedInputStream.newInstance(buf, 0, i + 1).readRawVarint32();
/* 60 */         if (length < 0) {
/* 61 */           throw new CorruptedFrameException("negative length: " + length);
/*    */         }
/*    */         
/* 64 */         if (in.readableBytes() < length) {
/* 65 */           in.resetReaderIndex();
/*    */           return;
/*    */         } 
/* 68 */         out.add(in.readBytes(length));
/*    */ 
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/*    */     
/* 75 */     throw new CorruptedFrameException("length wider than 32-bit");
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\protobuf\ProtobufVarint32FrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */