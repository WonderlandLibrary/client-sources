/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.CorruptedFrameException;
/*     */ import io.netty.handler.codec.ReplayingDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public class WebSocket08FrameDecoder
/*     */   extends ReplayingDecoder<WebSocket08FrameDecoder.State>
/*     */   implements WebSocketFrameDecoder
/*     */ {
/*  75 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocket08FrameDecoder.class);
/*     */   
/*     */   private static final byte OPCODE_CONT = 0;
/*     */   private static final byte OPCODE_TEXT = 1;
/*     */   private static final byte OPCODE_BINARY = 2;
/*     */   private static final byte OPCODE_CLOSE = 8;
/*     */   private static final byte OPCODE_PING = 9;
/*     */   private static final byte OPCODE_PONG = 10;
/*     */   private int fragmentedFramesCount;
/*     */   private final long maxFramePayloadLength;
/*     */   private boolean frameFinalFlag;
/*     */   private int frameRsv;
/*     */   private int frameOpcode;
/*     */   private long framePayloadLength;
/*     */   private ByteBuf framePayload;
/*     */   private int framePayloadBytesRead;
/*     */   private byte[] maskingKey;
/*     */   private ByteBuf payloadBuffer;
/*     */   private final boolean allowExtensions;
/*     */   private final boolean maskedPayload;
/*     */   private boolean receivedClosingHandshake;
/*     */   private Utf8Validator utf8Validator;
/*     */   
/*     */   enum State
/*     */   {
/* 100 */     FRAME_START, MASKING_KEY, PAYLOAD, CORRUPT;
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
/*     */   public WebSocket08FrameDecoder(boolean maskedPayload, boolean allowExtensions, int maxFramePayloadLength) {
/* 116 */     super(State.FRAME_START);
/* 117 */     this.maskedPayload = maskedPayload;
/* 118 */     this.allowExtensions = allowExtensions;
/* 119 */     this.maxFramePayloadLength = maxFramePayloadLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 126 */     if (this.receivedClosingHandshake) {
/* 127 */       in.skipBytes(actualReadableBytes()); return;
/*     */     }  try {
/*     */       byte b; boolean frameMasked; int framePayloadLen1;
/*     */       int rbytes;
/*     */       long willHaveReadByteCount;
/* 132 */       switch ((State)state()) {
/*     */         case FRAME_START:
/* 134 */           this.framePayloadBytesRead = 0;
/* 135 */           this.framePayloadLength = -1L;
/* 136 */           this.framePayload = null;
/* 137 */           this.payloadBuffer = null;
/*     */ 
/*     */           
/* 140 */           b = in.readByte();
/* 141 */           this.frameFinalFlag = ((b & 0x80) != 0);
/* 142 */           this.frameRsv = (b & 0x70) >> 4;
/* 143 */           this.frameOpcode = b & 0xF;
/*     */           
/* 145 */           if (logger.isDebugEnabled()) {
/* 146 */             logger.debug("Decoding WebSocket Frame opCode={}", Integer.valueOf(this.frameOpcode));
/*     */           }
/*     */ 
/*     */           
/* 150 */           b = in.readByte();
/* 151 */           frameMasked = ((b & 0x80) != 0);
/* 152 */           framePayloadLen1 = b & Byte.MAX_VALUE;
/*     */           
/* 154 */           if (this.frameRsv != 0 && !this.allowExtensions) {
/* 155 */             protocolViolation(ctx, "RSV != 0 and no extension negotiated, RSV:" + this.frameRsv);
/*     */             
/*     */             return;
/*     */           } 
/* 159 */           if (this.maskedPayload && !frameMasked) {
/* 160 */             protocolViolation(ctx, "unmasked client to server frame");
/*     */             return;
/*     */           } 
/* 163 */           if (this.frameOpcode > 7) {
/*     */ 
/*     */             
/* 166 */             if (!this.frameFinalFlag) {
/* 167 */               protocolViolation(ctx, "fragmented control frame");
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 172 */             if (framePayloadLen1 > 125) {
/* 173 */               protocolViolation(ctx, "control frame with payload length > 125 octets");
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 178 */             if (this.frameOpcode != 8 && this.frameOpcode != 9 && this.frameOpcode != 10) {
/*     */               
/* 180 */               protocolViolation(ctx, "control frame using reserved opcode " + this.frameOpcode);
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */ 
/*     */             
/* 187 */             if (this.frameOpcode == 8 && framePayloadLen1 == 1) {
/* 188 */               protocolViolation(ctx, "received close control frame with payload len 1");
/*     */               
/*     */               return;
/*     */             } 
/*     */           } else {
/* 193 */             if (this.frameOpcode != 0 && this.frameOpcode != 1 && this.frameOpcode != 2) {
/*     */               
/* 195 */               protocolViolation(ctx, "data frame using reserved opcode " + this.frameOpcode);
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 200 */             if (this.fragmentedFramesCount == 0 && this.frameOpcode == 0) {
/* 201 */               protocolViolation(ctx, "received continuation data frame outside fragmented message");
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 206 */             if (this.fragmentedFramesCount != 0 && this.frameOpcode != 0 && this.frameOpcode != 9) {
/* 207 */               protocolViolation(ctx, "received non-continuation data frame while inside fragmented message");
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/*     */           
/* 214 */           if (framePayloadLen1 == 126) {
/* 215 */             this.framePayloadLength = in.readUnsignedShort();
/* 216 */             if (this.framePayloadLength < 126L) {
/* 217 */               protocolViolation(ctx, "invalid data frame length (not using minimal length encoding)");
/*     */               return;
/*     */             } 
/* 220 */           } else if (framePayloadLen1 == 127) {
/* 221 */             this.framePayloadLength = in.readLong();
/*     */ 
/*     */ 
/*     */             
/* 225 */             if (this.framePayloadLength < 65536L) {
/* 226 */               protocolViolation(ctx, "invalid data frame length (not using minimal length encoding)");
/*     */               return;
/*     */             } 
/*     */           } else {
/* 230 */             this.framePayloadLength = framePayloadLen1;
/*     */           } 
/*     */           
/* 233 */           if (this.framePayloadLength > this.maxFramePayloadLength) {
/* 234 */             protocolViolation(ctx, "Max frame length of " + this.maxFramePayloadLength + " has been exceeded.");
/*     */             
/*     */             return;
/*     */           } 
/* 238 */           if (logger.isDebugEnabled()) {
/* 239 */             logger.debug("Decoding WebSocket Frame length={}", Long.valueOf(this.framePayloadLength));
/*     */           }
/*     */           
/* 242 */           checkpoint(State.MASKING_KEY);
/*     */         case MASKING_KEY:
/* 244 */           if (this.maskedPayload) {
/* 245 */             if (this.maskingKey == null) {
/* 246 */               this.maskingKey = new byte[4];
/*     */             }
/* 248 */             in.readBytes(this.maskingKey);
/*     */           } 
/* 250 */           checkpoint(State.PAYLOAD);
/*     */ 
/*     */         
/*     */         case PAYLOAD:
/* 254 */           rbytes = actualReadableBytes();
/*     */           
/* 256 */           willHaveReadByteCount = (this.framePayloadBytesRead + rbytes);
/*     */ 
/*     */ 
/*     */           
/* 260 */           if (willHaveReadByteCount == this.framePayloadLength)
/*     */           
/* 262 */           { this.payloadBuffer = ctx.alloc().buffer(rbytes);
/* 263 */             this.payloadBuffer.writeBytes(in, rbytes); }
/* 264 */           else { if (willHaveReadByteCount < this.framePayloadLength) {
/*     */ 
/*     */ 
/*     */               
/* 268 */               if (this.framePayload == null) {
/* 269 */                 this.framePayload = ctx.alloc().buffer(toFrameLength(this.framePayloadLength));
/*     */               }
/* 271 */               this.framePayload.writeBytes(in, rbytes);
/* 272 */               this.framePayloadBytesRead += rbytes;
/*     */               
/*     */               return;
/*     */             } 
/* 276 */             if (willHaveReadByteCount > this.framePayloadLength) {
/*     */ 
/*     */               
/* 279 */               if (this.framePayload == null) {
/* 280 */                 this.framePayload = ctx.alloc().buffer(toFrameLength(this.framePayloadLength));
/*     */               }
/* 282 */               this.framePayload.writeBytes(in, toFrameLength(this.framePayloadLength - this.framePayloadBytesRead));
/*     */             }  }
/*     */ 
/*     */ 
/*     */           
/* 287 */           checkpoint(State.FRAME_START);
/*     */ 
/*     */           
/* 290 */           if (this.framePayload == null) {
/* 291 */             this.framePayload = this.payloadBuffer;
/* 292 */             this.payloadBuffer = null;
/* 293 */           } else if (this.payloadBuffer != null) {
/* 294 */             this.framePayload.writeBytes(this.payloadBuffer);
/* 295 */             this.payloadBuffer.release();
/* 296 */             this.payloadBuffer = null;
/*     */           } 
/*     */ 
/*     */           
/* 300 */           if (this.maskedPayload) {
/* 301 */             unmask(this.framePayload);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 306 */           if (this.frameOpcode == 9) {
/* 307 */             out.add(new PingWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 308 */             this.framePayload = null;
/*     */             return;
/*     */           } 
/* 311 */           if (this.frameOpcode == 10) {
/* 312 */             out.add(new PongWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 313 */             this.framePayload = null;
/*     */             return;
/*     */           } 
/* 316 */           if (this.frameOpcode == 8) {
/* 317 */             checkCloseFrameBody(ctx, this.framePayload);
/* 318 */             this.receivedClosingHandshake = true;
/* 319 */             out.add(new CloseWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 320 */             this.framePayload = null;
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 326 */           if (this.frameFinalFlag) {
/*     */ 
/*     */             
/* 329 */             if (this.frameOpcode != 9) {
/* 330 */               this.fragmentedFramesCount = 0;
/*     */ 
/*     */               
/* 333 */               if (this.frameOpcode == 1 || (this.utf8Validator != null && this.utf8Validator.isChecking()))
/*     */               {
/*     */                 
/* 336 */                 checkUTF8String(ctx, this.framePayload);
/*     */ 
/*     */ 
/*     */                 
/* 340 */                 this.utf8Validator.finish();
/*     */               }
/*     */             
/*     */             } 
/*     */           } else {
/*     */             
/* 346 */             if (this.fragmentedFramesCount == 0) {
/*     */               
/* 348 */               if (this.frameOpcode == 1) {
/* 349 */                 checkUTF8String(ctx, this.framePayload);
/*     */               
/*     */               }
/*     */             }
/* 353 */             else if (this.utf8Validator != null && this.utf8Validator.isChecking()) {
/* 354 */               checkUTF8String(ctx, this.framePayload);
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 359 */             this.fragmentedFramesCount++;
/*     */           } 
/*     */ 
/*     */           
/* 363 */           if (this.frameOpcode == 1) {
/* 364 */             out.add(new TextWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 365 */             this.framePayload = null; return;
/*     */           } 
/* 367 */           if (this.frameOpcode == 2) {
/* 368 */             out.add(new BinaryWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 369 */             this.framePayload = null; return;
/*     */           } 
/* 371 */           if (this.frameOpcode == 0) {
/* 372 */             out.add(new ContinuationWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 373 */             this.framePayload = null;
/*     */             return;
/*     */           } 
/* 376 */           throw new UnsupportedOperationException("Cannot decode web socket frame with opcode: " + this.frameOpcode);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case CORRUPT:
/* 382 */           in.readByte();
/*     */           return;
/*     */       } 
/* 385 */       throw new Error("Shouldn't reach here.");
/*     */     }
/* 387 */     catch (Exception e) {
/* 388 */       if (this.payloadBuffer != null) {
/* 389 */         if (this.payloadBuffer.refCnt() > 0) {
/* 390 */           this.payloadBuffer.release();
/*     */         }
/* 392 */         this.payloadBuffer = null;
/*     */       } 
/* 394 */       if (this.framePayload != null) {
/* 395 */         if (this.framePayload.refCnt() > 0) {
/* 396 */           this.framePayload.release();
/*     */         }
/* 398 */         this.framePayload = null;
/*     */       } 
/* 400 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void unmask(ByteBuf frame) {
/* 405 */     for (int i = frame.readerIndex(); i < frame.writerIndex(); i++) {
/* 406 */       frame.setByte(i, frame.getByte(i) ^ this.maskingKey[i % 4]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void protocolViolation(ChannelHandlerContext ctx, String reason) {
/* 411 */     protocolViolation(ctx, new CorruptedFrameException(reason));
/*     */   }
/*     */   
/*     */   private void protocolViolation(ChannelHandlerContext ctx, CorruptedFrameException ex) {
/* 415 */     checkpoint(State.CORRUPT);
/* 416 */     if (ctx.channel().isActive()) {
/* 417 */       ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */     }
/* 419 */     throw ex;
/*     */   }
/*     */   
/*     */   private static int toFrameLength(long l) {
/* 423 */     if (l > 2147483647L) {
/* 424 */       throw new TooLongFrameException("Length:" + l);
/*     */     }
/* 426 */     return (int)l;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkUTF8String(ChannelHandlerContext ctx, ByteBuf buffer) {
/*     */     try {
/* 432 */       if (this.utf8Validator == null) {
/* 433 */         this.utf8Validator = new Utf8Validator();
/*     */       }
/* 435 */       this.utf8Validator.check(buffer);
/* 436 */     } catch (CorruptedFrameException ex) {
/* 437 */       protocolViolation(ctx, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkCloseFrameBody(ChannelHandlerContext ctx, ByteBuf buffer) {
/* 444 */     if (buffer == null || !buffer.isReadable()) {
/*     */       return;
/*     */     }
/* 447 */     if (buffer.readableBytes() == 1) {
/* 448 */       protocolViolation(ctx, "Invalid close frame body");
/*     */     }
/*     */ 
/*     */     
/* 452 */     int idx = buffer.readerIndex();
/* 453 */     buffer.readerIndex(0);
/*     */ 
/*     */     
/* 456 */     int statusCode = buffer.readShort();
/* 457 */     if ((statusCode >= 0 && statusCode <= 999) || (statusCode >= 1004 && statusCode <= 1006) || (statusCode >= 1012 && statusCode <= 2999))
/*     */     {
/* 459 */       protocolViolation(ctx, "Invalid close frame getStatus code: " + statusCode);
/*     */     }
/*     */ 
/*     */     
/* 463 */     if (buffer.isReadable()) {
/*     */       try {
/* 465 */         (new Utf8Validator()).check(buffer);
/* 466 */       } catch (CorruptedFrameException ex) {
/* 467 */         protocolViolation(ctx, ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 472 */     buffer.readerIndex(idx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 477 */     super.channelInactive(ctx);
/*     */ 
/*     */ 
/*     */     
/* 481 */     if (this.framePayload != null) {
/* 482 */       this.framePayload.release();
/*     */     }
/* 484 */     if (this.payloadBuffer != null)
/* 485 */       this.payloadBuffer.release(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocket08FrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */