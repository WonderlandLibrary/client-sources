/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class WebSocket08FrameEncoder
/*     */   extends MessageToMessageEncoder<WebSocketFrame>
/*     */   implements WebSocketFrameEncoder
/*     */ {
/*  74 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocket08FrameEncoder.class);
/*     */ 
/*     */   
/*     */   private static final byte OPCODE_CONT = 0;
/*     */   
/*     */   private static final byte OPCODE_TEXT = 1;
/*     */   
/*     */   private static final byte OPCODE_BINARY = 2;
/*     */   
/*     */   private static final byte OPCODE_CLOSE = 8;
/*     */   
/*     */   private static final byte OPCODE_PING = 9;
/*     */   
/*     */   private static final byte OPCODE_PONG = 10;
/*     */   
/*     */   private final boolean maskPayload;
/*     */ 
/*     */   
/*     */   public WebSocket08FrameEncoder(boolean maskPayload) {
/*  93 */     this.maskPayload = maskPayload;
/*     */   }
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
/*     */     byte opcode;
/*  98 */     ByteBuf data = msg.content();
/*     */ 
/*     */ 
/*     */     
/* 102 */     if (msg instanceof TextWebSocketFrame) {
/* 103 */       opcode = 1;
/* 104 */     } else if (msg instanceof PingWebSocketFrame) {
/* 105 */       opcode = 9;
/* 106 */     } else if (msg instanceof PongWebSocketFrame) {
/* 107 */       opcode = 10;
/* 108 */     } else if (msg instanceof CloseWebSocketFrame) {
/* 109 */       opcode = 8;
/* 110 */     } else if (msg instanceof BinaryWebSocketFrame) {
/* 111 */       opcode = 2;
/* 112 */     } else if (msg instanceof ContinuationWebSocketFrame) {
/* 113 */       opcode = 0;
/*     */     } else {
/* 115 */       throw new UnsupportedOperationException("Cannot encode frame of type: " + msg.getClass().getName());
/*     */     } 
/*     */     
/* 118 */     int length = data.readableBytes();
/*     */     
/* 120 */     if (logger.isDebugEnabled()) {
/* 121 */       logger.debug("Encoding WebSocket Frame opCode=" + opcode + " length=" + length);
/*     */     }
/*     */     
/* 124 */     int b0 = 0;
/* 125 */     if (msg.isFinalFragment()) {
/* 126 */       b0 |= 0x80;
/*     */     }
/* 128 */     b0 |= msg.rsv() % 8 << 4;
/* 129 */     b0 |= opcode % 128;
/*     */     
/* 131 */     if (opcode == 9 && length > 125) {
/* 132 */       throw new TooLongFrameException("invalid payload for PING (payload length must be <= 125, was " + length);
/*     */     }
/*     */ 
/*     */     
/* 136 */     boolean release = true;
/* 137 */     ByteBuf buf = null;
/*     */     try {
/* 139 */       int maskLength = this.maskPayload ? 4 : 0;
/* 140 */       if (length <= 125) {
/* 141 */         int size = 2 + maskLength;
/* 142 */         if (this.maskPayload) {
/* 143 */           size += length;
/*     */         }
/* 145 */         buf = ctx.alloc().buffer(size);
/* 146 */         buf.writeByte(b0);
/* 147 */         byte b = (byte)(this.maskPayload ? (0x80 | (byte)length) : (byte)length);
/* 148 */         buf.writeByte(b);
/* 149 */       } else if (length <= 65535) {
/* 150 */         int size = 4 + maskLength;
/* 151 */         if (this.maskPayload) {
/* 152 */           size += length;
/*     */         }
/* 154 */         buf = ctx.alloc().buffer(size);
/* 155 */         buf.writeByte(b0);
/* 156 */         buf.writeByte(this.maskPayload ? 254 : 126);
/* 157 */         buf.writeByte(length >>> 8 & 0xFF);
/* 158 */         buf.writeByte(length & 0xFF);
/*     */       } else {
/* 160 */         int size = 10 + maskLength;
/* 161 */         if (this.maskPayload) {
/* 162 */           size += length;
/*     */         }
/* 164 */         buf = ctx.alloc().buffer(size);
/* 165 */         buf.writeByte(b0);
/* 166 */         buf.writeByte(this.maskPayload ? 255 : 127);
/* 167 */         buf.writeLong(length);
/*     */       } 
/*     */ 
/*     */       
/* 171 */       if (this.maskPayload) {
/* 172 */         int random = (int)(Math.random() * 2.147483647E9D);
/* 173 */         byte[] mask = ByteBuffer.allocate(4).putInt(random).array();
/* 174 */         buf.writeBytes(mask);
/*     */         
/* 176 */         int counter = 0;
/* 177 */         for (int i = data.readerIndex(); i < data.writerIndex(); i++) {
/* 178 */           byte byteData = data.getByte(i);
/* 179 */           buf.writeByte(byteData ^ mask[counter++ % 4]);
/*     */         } 
/* 181 */         out.add(buf);
/*     */       }
/* 183 */       else if (buf.writableBytes() >= data.readableBytes()) {
/*     */         
/* 185 */         buf.writeBytes(data);
/* 186 */         out.add(buf);
/*     */       } else {
/* 188 */         out.add(buf);
/* 189 */         out.add(data.retain());
/*     */       } 
/*     */       
/* 192 */       release = false;
/*     */     } finally {
/* 194 */       if (release && buf != null)
/* 195 */         buf.release(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocket08FrameEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */