/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
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
/*     */ public class SnappyFramedEncoder
/*     */   extends MessageToByteEncoder<ByteBuf>
/*     */ {
/*     */   private static final int MIN_COMPRESSIBLE_LENGTH = 18;
/*  42 */   private static final byte[] STREAM_START = new byte[] { -1, 6, 0, 0, 115, 78, 97, 80, 112, 89 };
/*     */ 
/*     */ 
/*     */   
/*  46 */   private final Snappy snappy = new Snappy();
/*     */   
/*     */   private boolean started;
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
/*  51 */     if (!in.isReadable()) {
/*     */       return;
/*     */     }
/*     */     
/*  55 */     if (!this.started) {
/*  56 */       this.started = true;
/*  57 */       out.writeBytes(STREAM_START);
/*     */     } 
/*     */     
/*  60 */     int dataLength = in.readableBytes();
/*  61 */     if (dataLength > 18) {
/*     */       while (true) {
/*  63 */         int lengthIdx = out.writerIndex() + 1;
/*  64 */         if (dataLength < 18) {
/*  65 */           ByteBuf byteBuf = in.readSlice(dataLength);
/*  66 */           writeUnencodedChunk(byteBuf, out, dataLength);
/*     */           
/*     */           break;
/*     */         } 
/*  70 */         out.writeInt(0);
/*  71 */         if (dataLength > 32767) {
/*  72 */           ByteBuf byteBuf = in.readSlice(32767);
/*  73 */           calculateAndWriteChecksum(byteBuf, out);
/*  74 */           this.snappy.encode(byteBuf, out, 32767);
/*  75 */           setChunkLength(out, lengthIdx);
/*  76 */           dataLength -= 32767; continue;
/*     */         } 
/*  78 */         ByteBuf slice = in.readSlice(dataLength);
/*  79 */         calculateAndWriteChecksum(slice, out);
/*  80 */         this.snappy.encode(slice, out, dataLength);
/*  81 */         setChunkLength(out, lengthIdx);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } else {
/*  86 */       writeUnencodedChunk(in, out, dataLength);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void writeUnencodedChunk(ByteBuf in, ByteBuf out, int dataLength) {
/*  91 */     out.writeByte(1);
/*  92 */     writeChunkLength(out, dataLength + 4);
/*  93 */     calculateAndWriteChecksum(in, out);
/*  94 */     out.writeBytes(in, dataLength);
/*     */   }
/*     */   
/*     */   private static void setChunkLength(ByteBuf out, int lengthIdx) {
/*  98 */     int chunkLength = out.writerIndex() - lengthIdx - 3;
/*  99 */     if (chunkLength >>> 24 != 0) {
/* 100 */       throw new CompressionException("compressed data too large: " + chunkLength);
/*     */     }
/* 102 */     out.setMedium(lengthIdx, ByteBufUtil.swapMedium(chunkLength));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writeChunkLength(ByteBuf out, int chunkLength) {
/* 112 */     out.writeMedium(ByteBufUtil.swapMedium(chunkLength));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void calculateAndWriteChecksum(ByteBuf slice, ByteBuf out) {
/* 122 */     out.writeInt(ByteBufUtil.swapInt(Snappy.calculateChecksum(slice)));
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\compression\SnappyFramedEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */