/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandler;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.UnsupportedMessageTypeException;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.SocketAddress;
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
/*     */ public class SpdyFrameCodec
/*     */   extends ByteToMessageDecoder
/*     */   implements SpdyFrameDecoderDelegate, ChannelOutboundHandler
/*     */ {
/*  37 */   private static final SpdyProtocolException INVALID_FRAME = new SpdyProtocolException("Received invalid frame");
/*     */ 
/*     */   
/*     */   private final SpdyFrameDecoder spdyFrameDecoder;
/*     */ 
/*     */   
/*     */   private final SpdyFrameEncoder spdyFrameEncoder;
/*     */ 
/*     */   
/*     */   private final SpdyHeaderBlockDecoder spdyHeaderBlockDecoder;
/*     */   
/*     */   private final SpdyHeaderBlockEncoder spdyHeaderBlockEncoder;
/*     */   
/*     */   private SpdyHeadersFrame spdyHeadersFrame;
/*     */   
/*     */   private SpdySettingsFrame spdySettingsFrame;
/*     */   
/*     */   private ChannelHandlerContext ctx;
/*     */ 
/*     */   
/*     */   public SpdyFrameCodec(SpdyVersion version) {
/*  58 */     this(version, 8192, 16384, 6, 15, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdyFrameCodec(SpdyVersion version, int maxChunkSize, int maxHeaderSize, int compressionLevel, int windowBits, int memLevel) {
/*  67 */     this(version, maxChunkSize, SpdyHeaderBlockDecoder.newInstance(version, maxHeaderSize), SpdyHeaderBlockEncoder.newInstance(version, compressionLevel, windowBits, memLevel));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SpdyFrameCodec(SpdyVersion version, int maxChunkSize, SpdyHeaderBlockDecoder spdyHeaderBlockDecoder, SpdyHeaderBlockEncoder spdyHeaderBlockEncoder) {
/*  74 */     this.spdyFrameDecoder = new SpdyFrameDecoder(version, this, maxChunkSize);
/*  75 */     this.spdyFrameEncoder = new SpdyFrameEncoder(version);
/*  76 */     this.spdyHeaderBlockDecoder = spdyHeaderBlockDecoder;
/*  77 */     this.spdyHeaderBlockEncoder = spdyHeaderBlockEncoder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  82 */     super.handlerAdded(ctx);
/*  83 */     this.ctx = ctx;
/*  84 */     ctx.channel().closeFuture().addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) throws Exception {
/*  87 */             SpdyFrameCodec.this.spdyHeaderBlockDecoder.end();
/*  88 */             SpdyFrameCodec.this.spdyHeaderBlockEncoder.end();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  95 */     this.spdyFrameDecoder.decode(in);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 100 */     ctx.bind(localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 106 */     ctx.connect(remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 111 */     ctx.disconnect(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 116 */     ctx.close(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 121 */     ctx.deregister(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 126 */     ctx.read();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 131 */     ctx.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 138 */     if (msg instanceof SpdyDataFrame) {
/*     */       
/* 140 */       SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
/* 141 */       ByteBuf frame = this.spdyFrameEncoder.encodeDataFrame(ctx.alloc(), spdyDataFrame.streamId(), spdyDataFrame.isLast(), spdyDataFrame.content());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 147 */       spdyDataFrame.release();
/* 148 */       ctx.write(frame, promise);
/*     */     }
/* 150 */     else if (msg instanceof SpdySynStreamFrame) {
/*     */       ByteBuf frame;
/* 152 */       SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
/* 153 */       ByteBuf headerBlock = this.spdyHeaderBlockEncoder.encode(spdySynStreamFrame);
/*     */       try {
/* 155 */         frame = this.spdyFrameEncoder.encodeSynStreamFrame(ctx.alloc(), spdySynStreamFrame.streamId(), spdySynStreamFrame.associatedStreamId(), spdySynStreamFrame.priority(), spdySynStreamFrame.isLast(), spdySynStreamFrame.isUnidirectional(), headerBlock);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 165 */         headerBlock.release();
/*     */       } 
/* 167 */       ctx.write(frame, promise);
/*     */     }
/* 169 */     else if (msg instanceof SpdySynReplyFrame) {
/*     */       ByteBuf frame;
/* 171 */       SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
/* 172 */       ByteBuf headerBlock = this.spdyHeaderBlockEncoder.encode(spdySynReplyFrame);
/*     */       try {
/* 174 */         frame = this.spdyFrameEncoder.encodeSynReplyFrame(ctx.alloc(), spdySynReplyFrame.streamId(), spdySynReplyFrame.isLast(), headerBlock);
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 181 */         headerBlock.release();
/*     */       } 
/* 183 */       ctx.write(frame, promise);
/*     */     }
/* 185 */     else if (msg instanceof SpdyRstStreamFrame) {
/*     */       
/* 187 */       SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
/* 188 */       ByteBuf frame = this.spdyFrameEncoder.encodeRstStreamFrame(ctx.alloc(), spdyRstStreamFrame.streamId(), spdyRstStreamFrame.status().code());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       ctx.write(frame, promise);
/*     */     }
/* 195 */     else if (msg instanceof SpdySettingsFrame) {
/*     */       
/* 197 */       SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
/* 198 */       ByteBuf frame = this.spdyFrameEncoder.encodeSettingsFrame(ctx.alloc(), spdySettingsFrame);
/*     */ 
/*     */ 
/*     */       
/* 202 */       ctx.write(frame, promise);
/*     */     }
/* 204 */     else if (msg instanceof SpdyPingFrame) {
/*     */       
/* 206 */       SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
/* 207 */       ByteBuf frame = this.spdyFrameEncoder.encodePingFrame(ctx.alloc(), spdyPingFrame.id());
/*     */ 
/*     */ 
/*     */       
/* 211 */       ctx.write(frame, promise);
/*     */     }
/* 213 */     else if (msg instanceof SpdyGoAwayFrame) {
/*     */       
/* 215 */       SpdyGoAwayFrame spdyGoAwayFrame = (SpdyGoAwayFrame)msg;
/* 216 */       ByteBuf frame = this.spdyFrameEncoder.encodeGoAwayFrame(ctx.alloc(), spdyGoAwayFrame.lastGoodStreamId(), spdyGoAwayFrame.status().code());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 221 */       ctx.write(frame, promise);
/*     */     }
/* 223 */     else if (msg instanceof SpdyHeadersFrame) {
/*     */       ByteBuf frame;
/* 225 */       SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
/* 226 */       ByteBuf headerBlock = this.spdyHeaderBlockEncoder.encode(spdyHeadersFrame);
/*     */       try {
/* 228 */         frame = this.spdyFrameEncoder.encodeHeadersFrame(ctx.alloc(), spdyHeadersFrame.streamId(), spdyHeadersFrame.isLast(), headerBlock);
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 235 */         headerBlock.release();
/*     */       } 
/* 237 */       ctx.write(frame, promise);
/*     */     }
/* 239 */     else if (msg instanceof SpdyWindowUpdateFrame) {
/*     */       
/* 241 */       SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame)msg;
/* 242 */       ByteBuf frame = this.spdyFrameEncoder.encodeWindowUpdateFrame(ctx.alloc(), spdyWindowUpdateFrame.streamId(), spdyWindowUpdateFrame.deltaWindowSize());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 247 */       ctx.write(frame, promise);
/*     */     } else {
/* 249 */       throw new UnsupportedMessageTypeException(msg, new Class[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readDataFrame(int streamId, boolean last, ByteBuf data) {
/* 255 */     SpdyDataFrame spdyDataFrame = new DefaultSpdyDataFrame(streamId, data);
/* 256 */     spdyDataFrame.setLast(last);
/* 257 */     this.ctx.fireChannelRead(spdyDataFrame);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readSynStreamFrame(int streamId, int associatedToStreamId, byte priority, boolean last, boolean unidirectional) {
/* 263 */     SpdySynStreamFrame spdySynStreamFrame = new DefaultSpdySynStreamFrame(streamId, associatedToStreamId, priority);
/* 264 */     spdySynStreamFrame.setLast(last);
/* 265 */     spdySynStreamFrame.setUnidirectional(unidirectional);
/* 266 */     this.spdyHeadersFrame = spdySynStreamFrame;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSynReplyFrame(int streamId, boolean last) {
/* 271 */     SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
/* 272 */     spdySynReplyFrame.setLast(last);
/* 273 */     this.spdyHeadersFrame = spdySynReplyFrame;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readRstStreamFrame(int streamId, int statusCode) {
/* 278 */     SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, statusCode);
/* 279 */     this.ctx.fireChannelRead(spdyRstStreamFrame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSettingsFrame(boolean clearPersisted) {
/* 284 */     this.spdySettingsFrame = new DefaultSpdySettingsFrame();
/* 285 */     this.spdySettingsFrame.setClearPreviouslyPersistedSettings(clearPersisted);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSetting(int id, int value, boolean persistValue, boolean persisted) {
/* 290 */     this.spdySettingsFrame.setValue(id, value, persistValue, persisted);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSettingsEnd() {
/* 295 */     Object frame = this.spdySettingsFrame;
/* 296 */     this.spdySettingsFrame = null;
/* 297 */     this.ctx.fireChannelRead(frame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readPingFrame(int id) {
/* 302 */     SpdyPingFrame spdyPingFrame = new DefaultSpdyPingFrame(id);
/* 303 */     this.ctx.fireChannelRead(spdyPingFrame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readGoAwayFrame(int lastGoodStreamId, int statusCode) {
/* 308 */     SpdyGoAwayFrame spdyGoAwayFrame = new DefaultSpdyGoAwayFrame(lastGoodStreamId, statusCode);
/* 309 */     this.ctx.fireChannelRead(spdyGoAwayFrame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readHeadersFrame(int streamId, boolean last) {
/* 314 */     this.spdyHeadersFrame = new DefaultSpdyHeadersFrame(streamId);
/* 315 */     this.spdyHeadersFrame.setLast(last);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readWindowUpdateFrame(int streamId, int deltaWindowSize) {
/* 320 */     SpdyWindowUpdateFrame spdyWindowUpdateFrame = new DefaultSpdyWindowUpdateFrame(streamId, deltaWindowSize);
/* 321 */     this.ctx.fireChannelRead(spdyWindowUpdateFrame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readHeaderBlock(ByteBuf headerBlock) {
/*     */     try {
/* 327 */       this.spdyHeaderBlockDecoder.decode(headerBlock, this.spdyHeadersFrame);
/* 328 */     } catch (Exception e) {
/* 329 */       this.ctx.fireExceptionCaught(e);
/*     */     } finally {
/* 331 */       headerBlock.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readHeaderBlockEnd() {
/* 337 */     Object frame = null;
/*     */     try {
/* 339 */       this.spdyHeaderBlockDecoder.endHeaderBlock(this.spdyHeadersFrame);
/* 340 */       frame = this.spdyHeadersFrame;
/* 341 */       this.spdyHeadersFrame = null;
/* 342 */     } catch (Exception e) {
/* 343 */       this.ctx.fireExceptionCaught(e);
/*     */     } 
/* 345 */     if (frame != null) {
/* 346 */       this.ctx.fireChannelRead(frame);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFrameError(String message) {
/* 352 */     this.ctx.fireExceptionCaught(INVALID_FRAME);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyFrameCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */