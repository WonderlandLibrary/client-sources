/*     */ package io.netty.channel.oio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.ChannelInputShutdownEvent;
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
/*     */ public abstract class AbstractOioByteChannel
/*     */   extends AbstractOioChannel
/*     */ {
/*  37 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*  38 */   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')';
/*     */ 
/*     */   
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */ 
/*     */   
/*     */   private volatile boolean inputShutdown;
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractOioByteChannel(Channel parent) {
/*  49 */     super(parent);
/*     */   }
/*     */   
/*     */   protected boolean isInputShutdown() {
/*  53 */     return this.inputShutdown;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  58 */     return METADATA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkInputShutdown() {
/*  66 */     if (this.inputShutdown) {
/*     */       try {
/*  68 */         Thread.sleep(1000L);
/*  69 */       } catch (InterruptedException e) {}
/*     */ 
/*     */       
/*  72 */       return true;
/*     */     } 
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRead() {
/*  79 */     if (checkInputShutdown()) {
/*     */       return;
/*     */     }
/*  82 */     ChannelConfig config = config();
/*  83 */     ChannelPipeline pipeline = pipeline();
/*     */     
/*  85 */     RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/*  86 */     if (allocHandle == null) {
/*  87 */       this.allocHandle = allocHandle = config.getRecvByteBufAllocator().newHandle();
/*     */     }
/*     */     
/*  90 */     ByteBuf byteBuf = allocHandle.allocate(alloc());
/*     */     
/*  92 */     boolean closed = false;
/*  93 */     boolean read = false;
/*  94 */     Throwable exception = null;
/*  95 */     int localReadAmount = 0;
/*     */     try {
/*  97 */       int totalReadAmount = 0;
/*     */       
/*     */       do {
/* 100 */         localReadAmount = doReadBytes(byteBuf);
/* 101 */         if (localReadAmount > 0) {
/* 102 */           read = true;
/* 103 */         } else if (localReadAmount < 0) {
/* 104 */           closed = true;
/*     */         } 
/*     */         
/* 107 */         int available = available();
/* 108 */         if (available <= 0) {
/*     */           break;
/*     */         }
/*     */         
/* 112 */         if (!byteBuf.isWritable()) {
/* 113 */           int capacity = byteBuf.capacity();
/* 114 */           int maxCapacity = byteBuf.maxCapacity();
/* 115 */           if (capacity == maxCapacity) {
/* 116 */             if (read) {
/* 117 */               read = false;
/* 118 */               pipeline.fireChannelRead(byteBuf);
/* 119 */               byteBuf = alloc().buffer();
/*     */             } 
/*     */           } else {
/* 122 */             int writerIndex = byteBuf.writerIndex();
/* 123 */             if (writerIndex + available > maxCapacity) {
/* 124 */               byteBuf.capacity(maxCapacity);
/*     */             } else {
/* 126 */               byteBuf.ensureWritable(available);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 131 */         if (totalReadAmount >= Integer.MAX_VALUE - localReadAmount) {
/*     */           
/* 133 */           totalReadAmount = Integer.MAX_VALUE;
/*     */           
/*     */           break;
/*     */         } 
/* 137 */         totalReadAmount += localReadAmount;
/*     */       }
/* 139 */       while (config.isAutoRead());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       allocHandle.record(totalReadAmount);
/*     */     }
/* 147 */     catch (Throwable t) {
/* 148 */       exception = t;
/*     */     } finally {
/* 150 */       if (read) {
/* 151 */         pipeline.fireChannelRead(byteBuf);
/*     */       } else {
/*     */         
/* 154 */         byteBuf.release();
/*     */       } 
/*     */       
/* 157 */       pipeline.fireChannelReadComplete();
/* 158 */       if (exception != null) {
/* 159 */         if (exception instanceof java.io.IOException) {
/* 160 */           closed = true;
/* 161 */           pipeline().fireExceptionCaught(exception);
/*     */         } else {
/* 163 */           pipeline.fireExceptionCaught(exception);
/* 164 */           unsafe().close(voidPromise());
/*     */         } 
/*     */       }
/*     */       
/* 168 */       if (closed) {
/* 169 */         this.inputShutdown = true;
/* 170 */         if (isOpen()) {
/* 171 */           if (Boolean.TRUE.equals(config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
/* 172 */             pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*     */           } else {
/* 174 */             unsafe().close(unsafe().voidPromise());
/*     */           } 
/*     */         }
/*     */       } 
/* 178 */       if (localReadAmount == 0 && isActive())
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 185 */         read();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*     */     while (true) {
/* 193 */       Object msg = in.current();
/* 194 */       if (msg == null) {
/*     */         break;
/*     */       }
/*     */       
/* 198 */       if (msg instanceof ByteBuf) {
/* 199 */         ByteBuf buf = (ByteBuf)msg;
/* 200 */         int readableBytes = buf.readableBytes();
/* 201 */         while (readableBytes > 0) {
/* 202 */           doWriteBytes(buf);
/* 203 */           int newReadableBytes = buf.readableBytes();
/* 204 */           in.progress((readableBytes - newReadableBytes));
/* 205 */           readableBytes = newReadableBytes;
/*     */         } 
/* 207 */         in.remove(); continue;
/* 208 */       }  if (msg instanceof FileRegion) {
/* 209 */         FileRegion region = (FileRegion)msg;
/* 210 */         long transfered = region.transfered();
/* 211 */         doWriteFileRegion(region);
/* 212 */         in.progress(region.transfered() - transfered);
/* 213 */         in.remove(); continue;
/*     */       } 
/* 215 */       in.remove(new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object filterOutboundMessage(Object msg) throws Exception {
/* 223 */     if (msg instanceof ByteBuf || msg instanceof FileRegion) {
/* 224 */       return msg;
/*     */     }
/*     */     
/* 227 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */   
/*     */   protected abstract int available();
/*     */   
/*     */   protected abstract int doReadBytes(ByteBuf paramByteBuf) throws Exception;
/*     */   
/*     */   protected abstract void doWriteBytes(ByteBuf paramByteBuf) throws Exception;
/*     */   
/*     */   protected abstract void doWriteFileRegion(FileRegion paramFileRegion) throws Exception;
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\oio\AbstractOioByteChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */