/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.ChannelInputShutdownEvent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractNioByteChannel
/*     */   extends AbstractNioChannel
/*     */ {
/*  39 */   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')';
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Runnable flushTask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractNioByteChannel(Channel parent, SelectableChannel ch) {
/*  52 */     super(parent, ch, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
/*  57 */     return new NioByteUnsafe();
/*     */   }
/*     */   
/*     */   private final class NioByteUnsafe extends AbstractNioChannel.AbstractNioUnsafe {
/*     */     private RecvByteBufAllocator.Handle allocHandle;
/*     */     
/*     */     private void closeOnRead(ChannelPipeline pipeline) {
/*  64 */       SelectionKey key = AbstractNioByteChannel.this.selectionKey();
/*  65 */       AbstractNioByteChannel.this.setInputShutdown();
/*  66 */       if (AbstractNioByteChannel.this.isOpen())
/*  67 */         if (Boolean.TRUE.equals(AbstractNioByteChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
/*  68 */           key.interestOps(key.interestOps() & (AbstractNioByteChannel.this.readInterestOp ^ 0xFFFFFFFF));
/*  69 */           pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*     */         } else {
/*  71 */           close(voidPromise());
/*     */         }  
/*     */     }
/*     */     
/*     */     private NioByteUnsafe() {}
/*     */     
/*     */     private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close) {
/*  78 */       if (byteBuf != null) {
/*  79 */         if (byteBuf.isReadable()) {
/*  80 */           AbstractNioByteChannel.this.setReadPending(false);
/*  81 */           pipeline.fireChannelRead(byteBuf);
/*     */         } else {
/*  83 */           byteBuf.release();
/*     */         } 
/*     */       }
/*  86 */       pipeline.fireChannelReadComplete();
/*  87 */       pipeline.fireExceptionCaught(cause);
/*  88 */       if (close || cause instanceof java.io.IOException) {
/*  89 */         closeOnRead(pipeline);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void read() {
/*  95 */       ChannelConfig config = AbstractNioByteChannel.this.config();
/*  96 */       if (!config.isAutoRead() && !AbstractNioByteChannel.this.isReadPending()) {
/*     */         
/*  98 */         removeReadOp();
/*     */         
/*     */         return;
/*     */       } 
/* 102 */       ChannelPipeline pipeline = AbstractNioByteChannel.this.pipeline();
/* 103 */       ByteBufAllocator allocator = config.getAllocator();
/* 104 */       int maxMessagesPerRead = config.getMaxMessagesPerRead();
/* 105 */       RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/* 106 */       if (allocHandle == null) {
/* 107 */         this.allocHandle = allocHandle = config.getRecvByteBufAllocator().newHandle();
/*     */       }
/*     */       
/* 110 */       ByteBuf byteBuf = null;
/* 111 */       int messages = 0;
/* 112 */       boolean close = false;
/*     */       try {
/* 114 */         int totalReadAmount = 0;
/* 115 */         boolean readPendingReset = false;
/*     */         do {
/* 117 */           byteBuf = allocHandle.allocate(allocator);
/* 118 */           int writable = byteBuf.writableBytes();
/* 119 */           int localReadAmount = AbstractNioByteChannel.this.doReadBytes(byteBuf);
/* 120 */           if (localReadAmount <= 0) {
/*     */             
/* 122 */             byteBuf.release();
/* 123 */             close = (localReadAmount < 0);
/*     */             break;
/*     */           } 
/* 126 */           if (!readPendingReset) {
/* 127 */             readPendingReset = true;
/* 128 */             AbstractNioByteChannel.this.setReadPending(false);
/*     */           } 
/* 130 */           pipeline.fireChannelRead(byteBuf);
/* 131 */           byteBuf = null;
/*     */           
/* 133 */           if (totalReadAmount >= Integer.MAX_VALUE - localReadAmount) {
/*     */             
/* 135 */             totalReadAmount = Integer.MAX_VALUE;
/*     */             
/*     */             break;
/*     */           } 
/* 139 */           totalReadAmount += localReadAmount;
/*     */ 
/*     */           
/* 142 */           if (!config.isAutoRead()) {
/*     */             break;
/*     */           }
/*     */           
/* 146 */           if (localReadAmount < writable)
/*     */           {
/*     */             break;
/*     */           }
/*     */         }
/* 151 */         while (++messages < maxMessagesPerRead);
/*     */         
/* 153 */         pipeline.fireChannelReadComplete();
/* 154 */         allocHandle.record(totalReadAmount);
/*     */         
/* 156 */         if (close) {
/* 157 */           closeOnRead(pipeline);
/* 158 */           close = false;
/*     */         } 
/* 160 */       } catch (Throwable t) {
/* 161 */         handleReadException(pipeline, byteBuf, t, close);
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */ 
/*     */         
/* 169 */         if (!config.isAutoRead() && !AbstractNioByteChannel.this.isReadPending()) {
/* 170 */           removeReadOp();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 178 */     int writeSpinCount = -1;
/*     */     
/*     */     while (true) {
/* 181 */       Object msg = in.current();
/* 182 */       if (msg == null) {
/*     */         
/* 184 */         clearOpWrite();
/*     */         
/*     */         break;
/*     */       } 
/* 188 */       if (msg instanceof ByteBuf) {
/* 189 */         ByteBuf buf = (ByteBuf)msg;
/* 190 */         int readableBytes = buf.readableBytes();
/* 191 */         if (readableBytes == 0) {
/* 192 */           in.remove();
/*     */           
/*     */           continue;
/*     */         } 
/* 196 */         boolean setOpWrite = false;
/* 197 */         boolean done = false;
/* 198 */         long flushedAmount = 0L;
/* 199 */         if (writeSpinCount == -1) {
/* 200 */           writeSpinCount = config().getWriteSpinCount();
/*     */         }
/* 202 */         for (int i = writeSpinCount - 1; i >= 0; i--) {
/* 203 */           int localFlushedAmount = doWriteBytes(buf);
/* 204 */           if (localFlushedAmount == 0) {
/* 205 */             setOpWrite = true;
/*     */             
/*     */             break;
/*     */           } 
/* 209 */           flushedAmount += localFlushedAmount;
/* 210 */           if (!buf.isReadable()) {
/* 211 */             done = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 216 */         in.progress(flushedAmount);
/*     */         
/* 218 */         if (done) {
/* 219 */           in.remove(); continue;
/*     */         } 
/* 221 */         incompleteWrite(setOpWrite);
/*     */         break;
/*     */       } 
/* 224 */       if (msg instanceof FileRegion) {
/* 225 */         FileRegion region = (FileRegion)msg;
/* 226 */         boolean setOpWrite = false;
/* 227 */         boolean done = false;
/* 228 */         long flushedAmount = 0L;
/* 229 */         if (writeSpinCount == -1) {
/* 230 */           writeSpinCount = config().getWriteSpinCount();
/*     */         }
/* 232 */         for (int i = writeSpinCount - 1; i >= 0; i--) {
/* 233 */           long localFlushedAmount = doWriteFileRegion(region);
/* 234 */           if (localFlushedAmount == 0L) {
/* 235 */             setOpWrite = true;
/*     */             
/*     */             break;
/*     */           } 
/* 239 */           flushedAmount += localFlushedAmount;
/* 240 */           if (region.transfered() >= region.count()) {
/* 241 */             done = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 246 */         in.progress(flushedAmount);
/*     */         
/* 248 */         if (done) {
/* 249 */           in.remove(); continue;
/*     */         } 
/* 251 */         incompleteWrite(setOpWrite);
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 256 */       throw new Error();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object filterOutboundMessage(Object msg) {
/* 263 */     if (msg instanceof ByteBuf) {
/* 264 */       ByteBuf buf = (ByteBuf)msg;
/* 265 */       if (buf.isDirect()) {
/* 266 */         return msg;
/*     */       }
/*     */       
/* 269 */       return newDirectBuffer(buf);
/*     */     } 
/*     */     
/* 272 */     if (msg instanceof FileRegion) {
/* 273 */       return msg;
/*     */     }
/*     */     
/* 276 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void incompleteWrite(boolean setOpWrite) {
/* 282 */     if (setOpWrite) {
/* 283 */       setOpWrite();
/*     */     } else {
/*     */       
/* 286 */       Runnable flushTask = this.flushTask;
/* 287 */       if (flushTask == null) {
/* 288 */         flushTask = this.flushTask = new Runnable()
/*     */           {
/*     */             public void run() {
/* 291 */               AbstractNioByteChannel.this.flush();
/*     */             }
/*     */           };
/*     */       }
/* 295 */       eventLoop().execute(flushTask);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setOpWrite() {
/* 320 */     SelectionKey key = selectionKey();
/*     */ 
/*     */ 
/*     */     
/* 324 */     if (!key.isValid()) {
/*     */       return;
/*     */     }
/* 327 */     int interestOps = key.interestOps();
/* 328 */     if ((interestOps & 0x4) == 0) {
/* 329 */       key.interestOps(interestOps | 0x4);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void clearOpWrite() {
/* 334 */     SelectionKey key = selectionKey();
/*     */ 
/*     */ 
/*     */     
/* 338 */     if (!key.isValid()) {
/*     */       return;
/*     */     }
/* 341 */     int interestOps = key.interestOps();
/* 342 */     if ((interestOps & 0x4) != 0)
/* 343 */       key.interestOps(interestOps & 0xFFFFFFFB); 
/*     */   }
/*     */   
/*     */   protected abstract long doWriteFileRegion(FileRegion paramFileRegion) throws Exception;
/*     */   
/*     */   protected abstract int doReadBytes(ByteBuf paramByteBuf) throws Exception;
/*     */   
/*     */   protected abstract int doWriteBytes(ByteBuf paramByteBuf) throws Exception;
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\nio\AbstractNioByteChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */