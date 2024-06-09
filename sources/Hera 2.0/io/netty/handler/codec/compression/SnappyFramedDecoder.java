/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import java.util.Arrays;
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
/*     */ public class SnappyFramedDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private enum ChunkType
/*     */   {
/*  41 */     STREAM_IDENTIFIER,
/*  42 */     COMPRESSED_DATA,
/*  43 */     UNCOMPRESSED_DATA,
/*  44 */     RESERVED_UNSKIPPABLE,
/*  45 */     RESERVED_SKIPPABLE;
/*     */   }
/*     */   
/*  48 */   private static final byte[] SNAPPY = new byte[] { 115, 78, 97, 80, 112, 89 };
/*     */   
/*     */   private static final int MAX_UNCOMPRESSED_DATA_SIZE = 65540;
/*  51 */   private final Snappy snappy = new Snappy();
/*     */ 
/*     */   
/*     */   private final boolean validateChecksums;
/*     */ 
/*     */   
/*     */   private boolean started;
/*     */   
/*     */   private boolean corrupted;
/*     */ 
/*     */   
/*     */   public SnappyFramedDecoder() {
/*  63 */     this(false);
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
/*     */   public SnappyFramedDecoder(boolean validateChecksums) {
/*  76 */     this.validateChecksums = validateChecksums;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  81 */     if (this.corrupted) {
/*  82 */       in.skipBytes(in.readableBytes()); return;
/*     */     }  try {
/*     */       byte[] identifier;
/*     */       int checksum;
/*     */       ByteBuf uncompressed;
/*  87 */       int idx = in.readerIndex();
/*  88 */       int inSize = in.readableBytes();
/*  89 */       if (inSize < 4) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  95 */       int chunkTypeVal = in.getUnsignedByte(idx);
/*  96 */       ChunkType chunkType = mapChunkType((byte)chunkTypeVal);
/*  97 */       int chunkLength = ByteBufUtil.swapMedium(in.getUnsignedMedium(idx + 1));
/*     */       
/*  99 */       switch (chunkType) {
/*     */         case STREAM_IDENTIFIER:
/* 101 */           if (chunkLength != SNAPPY.length) {
/* 102 */             throw new DecompressionException("Unexpected length of stream identifier: " + chunkLength);
/*     */           }
/*     */           
/* 105 */           if (inSize < 4 + SNAPPY.length) {
/*     */             break;
/*     */           }
/*     */           
/* 109 */           identifier = new byte[chunkLength];
/* 110 */           in.skipBytes(4).readBytes(identifier);
/*     */           
/* 112 */           if (!Arrays.equals(identifier, SNAPPY)) {
/* 113 */             throw new DecompressionException("Unexpected stream identifier contents. Mismatched snappy protocol version?");
/*     */           }
/*     */ 
/*     */           
/* 117 */           this.started = true;
/*     */           break;
/*     */         case RESERVED_SKIPPABLE:
/* 120 */           if (!this.started) {
/* 121 */             throw new DecompressionException("Received RESERVED_SKIPPABLE tag before STREAM_IDENTIFIER");
/*     */           }
/*     */           
/* 124 */           if (inSize < 4 + chunkLength) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 129 */           in.skipBytes(4 + chunkLength);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case RESERVED_UNSKIPPABLE:
/* 135 */           throw new DecompressionException("Found reserved unskippable chunk type: 0x" + Integer.toHexString(chunkTypeVal));
/*     */         
/*     */         case UNCOMPRESSED_DATA:
/* 138 */           if (!this.started) {
/* 139 */             throw new DecompressionException("Received UNCOMPRESSED_DATA tag before STREAM_IDENTIFIER");
/*     */           }
/* 141 */           if (chunkLength > 65540) {
/* 142 */             throw new DecompressionException("Received UNCOMPRESSED_DATA larger than 65540 bytes");
/*     */           }
/*     */           
/* 145 */           if (inSize < 4 + chunkLength) {
/*     */             return;
/*     */           }
/*     */           
/* 149 */           in.skipBytes(4);
/* 150 */           if (this.validateChecksums) {
/* 151 */             int i = ByteBufUtil.swapInt(in.readInt());
/* 152 */             Snappy.validateChecksum(i, in, in.readerIndex(), chunkLength - 4);
/*     */           } else {
/* 154 */             in.skipBytes(4);
/*     */           } 
/* 156 */           out.add(in.readSlice(chunkLength - 4).retain());
/*     */           break;
/*     */         case COMPRESSED_DATA:
/* 159 */           if (!this.started) {
/* 160 */             throw new DecompressionException("Received COMPRESSED_DATA tag before STREAM_IDENTIFIER");
/*     */           }
/*     */           
/* 163 */           if (inSize < 4 + chunkLength) {
/*     */             return;
/*     */           }
/*     */           
/* 167 */           in.skipBytes(4);
/* 168 */           checksum = ByteBufUtil.swapInt(in.readInt());
/* 169 */           uncompressed = ctx.alloc().buffer(0);
/* 170 */           if (this.validateChecksums) {
/* 171 */             int oldWriterIndex = in.writerIndex();
/*     */             try {
/* 173 */               in.writerIndex(in.readerIndex() + chunkLength - 4);
/* 174 */               this.snappy.decode(in, uncompressed);
/*     */             } finally {
/* 176 */               in.writerIndex(oldWriterIndex);
/*     */             } 
/* 178 */             Snappy.validateChecksum(checksum, uncompressed, 0, uncompressed.writerIndex());
/*     */           } else {
/* 180 */             this.snappy.decode(in.readSlice(chunkLength - 4), uncompressed);
/*     */           } 
/* 182 */           out.add(uncompressed);
/* 183 */           this.snappy.reset();
/*     */           break;
/*     */       } 
/* 186 */     } catch (Exception e) {
/* 187 */       this.corrupted = true;
/* 188 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ChunkType mapChunkType(byte type) {
/* 199 */     if (type == 0)
/* 200 */       return ChunkType.COMPRESSED_DATA; 
/* 201 */     if (type == 1)
/* 202 */       return ChunkType.UNCOMPRESSED_DATA; 
/* 203 */     if (type == -1)
/* 204 */       return ChunkType.STREAM_IDENTIFIER; 
/* 205 */     if ((type & 0x80) == 128) {
/* 206 */       return ChunkType.RESERVED_SKIPPABLE;
/*     */     }
/* 208 */     return ChunkType.RESERVED_UNSKIPPABLE;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\compression\SnappyFramedDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */