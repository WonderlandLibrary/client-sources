/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ReplayingDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
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
/*     */ public class WebSocket00FrameDecoder
/*     */   extends ReplayingDecoder<Void>
/*     */   implements WebSocketFrameDecoder
/*     */ {
/*     */   static final int DEFAULT_MAX_FRAME_SIZE = 16384;
/*     */   private final long maxFrameSize;
/*     */   private boolean receivedClosingHandshake;
/*     */   
/*     */   public WebSocket00FrameDecoder() {
/*  39 */     this(16384);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocket00FrameDecoder(int maxFrameSize) {
/*  50 */     this.maxFrameSize = maxFrameSize;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*     */     WebSocketFrame frame;
/*  56 */     if (this.receivedClosingHandshake) {
/*  57 */       in.skipBytes(actualReadableBytes());
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  62 */     byte type = in.readByte();
/*     */     
/*  64 */     if ((type & 0x80) == 128) {
/*     */       
/*  66 */       frame = decodeBinaryFrame(ctx, type, in);
/*     */     } else {
/*     */       
/*  69 */       frame = decodeTextFrame(ctx, in);
/*     */     } 
/*     */     
/*  72 */     if (frame != null)
/*  73 */       out.add(frame); 
/*     */   }
/*     */   
/*     */   private WebSocketFrame decodeBinaryFrame(ChannelHandlerContext ctx, byte type, ByteBuf buffer) {
/*     */     byte b;
/*  78 */     long frameSize = 0L;
/*  79 */     int lengthFieldSize = 0;
/*     */     
/*     */     do {
/*  82 */       b = buffer.readByte();
/*  83 */       frameSize <<= 7L;
/*  84 */       frameSize |= (b & Byte.MAX_VALUE);
/*  85 */       if (frameSize > this.maxFrameSize) {
/*  86 */         throw new TooLongFrameException();
/*     */       }
/*  88 */       lengthFieldSize++;
/*  89 */       if (lengthFieldSize > 8)
/*     */       {
/*  91 */         throw new TooLongFrameException();
/*     */       }
/*  93 */     } while ((b & 0x80) == 128);
/*     */     
/*  95 */     if (type == -1 && frameSize == 0L) {
/*  96 */       this.receivedClosingHandshake = true;
/*  97 */       return new CloseWebSocketFrame();
/*     */     } 
/*  99 */     ByteBuf payload = ctx.alloc().buffer((int)frameSize);
/* 100 */     buffer.readBytes(payload);
/* 101 */     return new BinaryWebSocketFrame(payload);
/*     */   }
/*     */   
/*     */   private WebSocketFrame decodeTextFrame(ChannelHandlerContext ctx, ByteBuf buffer) {
/* 105 */     int ridx = buffer.readerIndex();
/* 106 */     int rbytes = actualReadableBytes();
/* 107 */     int delimPos = buffer.indexOf(ridx, ridx + rbytes, (byte)-1);
/* 108 */     if (delimPos == -1) {
/*     */       
/* 110 */       if (rbytes > this.maxFrameSize)
/*     */       {
/* 112 */         throw new TooLongFrameException();
/*     */       }
/*     */       
/* 115 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 119 */     int frameSize = delimPos - ridx;
/* 120 */     if (frameSize > this.maxFrameSize) {
/* 121 */       throw new TooLongFrameException();
/*     */     }
/*     */     
/* 124 */     ByteBuf binaryData = ctx.alloc().buffer(frameSize);
/* 125 */     buffer.readBytes(binaryData);
/* 126 */     buffer.skipBytes(1);
/*     */     
/* 128 */     int ffDelimPos = binaryData.indexOf(binaryData.readerIndex(), binaryData.writerIndex(), (byte)-1);
/* 129 */     if (ffDelimPos >= 0) {
/* 130 */       throw new IllegalArgumentException("a text frame should not contain 0xFF.");
/*     */     }
/*     */     
/* 133 */     return new TextWebSocketFrame(binaryData);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocket00FrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */