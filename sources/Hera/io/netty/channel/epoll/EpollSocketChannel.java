/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.ConnectTimeoutException;
/*     */ import io.netty.channel.DefaultFileRegion;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.ChannelInputShutdownEvent;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import io.netty.channel.socket.SocketChannel;
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.ConnectException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public final class EpollSocketChannel
/*     */   extends AbstractEpollChannel
/*     */   implements SocketChannel
/*     */ {
/*  52 */   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(DefaultFileRegion.class) + ')';
/*     */   
/*     */   private final EpollSocketChannelConfig config;
/*     */   
/*     */   private ChannelPromise connectPromise;
/*     */   
/*     */   private ScheduledFuture<?> connectTimeoutFuture;
/*     */   
/*     */   private SocketAddress requestedRemoteAddress;
/*     */   
/*     */   private volatile InetSocketAddress local;
/*     */   
/*     */   private volatile InetSocketAddress remote;
/*     */   
/*     */   private volatile boolean inputShutdown;
/*     */   
/*     */   private volatile boolean outputShutdown;
/*     */ 
/*     */   
/*     */   EpollSocketChannel(Channel parent, int fd) {
/*  72 */     super(parent, fd, 1, true);
/*  73 */     this.config = new EpollSocketChannelConfig(this);
/*     */ 
/*     */     
/*  76 */     this.remote = Native.remoteAddress(fd);
/*  77 */     this.local = Native.localAddress(fd);
/*     */   }
/*     */   
/*     */   public EpollSocketChannel() {
/*  81 */     super(Native.socketStreamFd(), 1);
/*  82 */     this.config = new EpollSocketChannelConfig(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
/*  87 */     return new EpollSocketUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*  92 */     return this.local;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/*  97 */     return this.remote;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress local) throws Exception {
/* 102 */     InetSocketAddress localAddress = (InetSocketAddress)local;
/* 103 */     Native.bind(this.fd, localAddress.getAddress(), localAddress.getPort());
/* 104 */     this.local = Native.localAddress(this.fd);
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
/*     */   private boolean writeBytes(ChannelOutboundBuffer in, ByteBuf buf) throws Exception {
/*     */     // Byte code:
/*     */     //   0: aload_2
/*     */     //   1: invokevirtual readableBytes : ()I
/*     */     //   4: istore_3
/*     */     //   5: iload_3
/*     */     //   6: ifne -> 16
/*     */     //   9: aload_1
/*     */     //   10: invokevirtual remove : ()Z
/*     */     //   13: pop
/*     */     //   14: iconst_1
/*     */     //   15: ireturn
/*     */     //   16: iconst_0
/*     */     //   17: istore #4
/*     */     //   19: lconst_0
/*     */     //   20: lstore #5
/*     */     //   22: aload_2
/*     */     //   23: invokevirtual hasMemoryAddress : ()Z
/*     */     //   26: ifeq -> 118
/*     */     //   29: aload_2
/*     */     //   30: invokevirtual memoryAddress : ()J
/*     */     //   33: lstore #7
/*     */     //   35: aload_2
/*     */     //   36: invokevirtual readerIndex : ()I
/*     */     //   39: istore #9
/*     */     //   41: aload_2
/*     */     //   42: invokevirtual writerIndex : ()I
/*     */     //   45: istore #10
/*     */     //   47: aload_0
/*     */     //   48: getfield fd : I
/*     */     //   51: lload #7
/*     */     //   53: iload #9
/*     */     //   55: iload #10
/*     */     //   57: invokestatic writeAddress : (IJII)I
/*     */     //   60: istore #11
/*     */     //   62: iload #11
/*     */     //   64: ifle -> 99
/*     */     //   67: lload #5
/*     */     //   69: iload #11
/*     */     //   71: i2l
/*     */     //   72: ladd
/*     */     //   73: lstore #5
/*     */     //   75: lload #5
/*     */     //   77: iload_3
/*     */     //   78: i2l
/*     */     //   79: lcmp
/*     */     //   80: ifne -> 89
/*     */     //   83: iconst_1
/*     */     //   84: istore #4
/*     */     //   86: goto -> 109
/*     */     //   89: iload #9
/*     */     //   91: iload #11
/*     */     //   93: iadd
/*     */     //   94: istore #9
/*     */     //   96: goto -> 106
/*     */     //   99: aload_0
/*     */     //   100: invokevirtual setEpollOut : ()V
/*     */     //   103: goto -> 109
/*     */     //   106: goto -> 47
/*     */     //   109: aload_1
/*     */     //   110: lload #5
/*     */     //   112: invokevirtual removeBytes : (J)V
/*     */     //   115: iload #4
/*     */     //   117: ireturn
/*     */     //   118: aload_2
/*     */     //   119: invokevirtual nioBufferCount : ()I
/*     */     //   122: iconst_1
/*     */     //   123: if_icmpne -> 230
/*     */     //   126: aload_2
/*     */     //   127: invokevirtual readerIndex : ()I
/*     */     //   130: istore #7
/*     */     //   132: aload_2
/*     */     //   133: iload #7
/*     */     //   135: aload_2
/*     */     //   136: invokevirtual readableBytes : ()I
/*     */     //   139: invokevirtual internalNioBuffer : (II)Ljava/nio/ByteBuffer;
/*     */     //   142: astore #8
/*     */     //   144: aload #8
/*     */     //   146: invokevirtual position : ()I
/*     */     //   149: istore #9
/*     */     //   151: aload #8
/*     */     //   153: invokevirtual limit : ()I
/*     */     //   156: istore #10
/*     */     //   158: aload_0
/*     */     //   159: getfield fd : I
/*     */     //   162: aload #8
/*     */     //   164: iload #9
/*     */     //   166: iload #10
/*     */     //   168: invokestatic write : (ILjava/nio/ByteBuffer;II)I
/*     */     //   171: istore #11
/*     */     //   173: iload #11
/*     */     //   175: ifle -> 211
/*     */     //   178: aload #8
/*     */     //   180: iload #9
/*     */     //   182: iload #11
/*     */     //   184: iadd
/*     */     //   185: invokevirtual position : (I)Ljava/nio/Buffer;
/*     */     //   188: pop
/*     */     //   189: lload #5
/*     */     //   191: iload #11
/*     */     //   193: i2l
/*     */     //   194: ladd
/*     */     //   195: lstore #5
/*     */     //   197: lload #5
/*     */     //   199: iload_3
/*     */     //   200: i2l
/*     */     //   201: lcmp
/*     */     //   202: ifne -> 218
/*     */     //   205: iconst_1
/*     */     //   206: istore #4
/*     */     //   208: goto -> 221
/*     */     //   211: aload_0
/*     */     //   212: invokevirtual setEpollOut : ()V
/*     */     //   215: goto -> 221
/*     */     //   218: goto -> 144
/*     */     //   221: aload_1
/*     */     //   222: lload #5
/*     */     //   224: invokevirtual removeBytes : (J)V
/*     */     //   227: iload #4
/*     */     //   229: ireturn
/*     */     //   230: aload_2
/*     */     //   231: invokevirtual nioBuffers : ()[Ljava/nio/ByteBuffer;
/*     */     //   234: astore #7
/*     */     //   236: aload_0
/*     */     //   237: aload_1
/*     */     //   238: aload #7
/*     */     //   240: aload #7
/*     */     //   242: arraylength
/*     */     //   243: iload_3
/*     */     //   244: i2l
/*     */     //   245: invokespecial writeBytesMultiple : (Lio/netty/channel/ChannelOutboundBuffer;[Ljava/nio/ByteBuffer;IJ)Z
/*     */     //   248: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #112	-> 0
/*     */     //   #113	-> 5
/*     */     //   #114	-> 9
/*     */     //   #115	-> 14
/*     */     //   #118	-> 16
/*     */     //   #119	-> 19
/*     */     //   #120	-> 22
/*     */     //   #121	-> 29
/*     */     //   #122	-> 35
/*     */     //   #123	-> 41
/*     */     //   #125	-> 47
/*     */     //   #126	-> 62
/*     */     //   #127	-> 67
/*     */     //   #128	-> 75
/*     */     //   #129	-> 83
/*     */     //   #130	-> 86
/*     */     //   #132	-> 89
/*     */     //   #135	-> 99
/*     */     //   #136	-> 103
/*     */     //   #138	-> 106
/*     */     //   #140	-> 109
/*     */     //   #141	-> 115
/*     */     //   #142	-> 118
/*     */     //   #143	-> 126
/*     */     //   #144	-> 132
/*     */     //   #146	-> 144
/*     */     //   #147	-> 151
/*     */     //   #148	-> 158
/*     */     //   #149	-> 173
/*     */     //   #150	-> 178
/*     */     //   #151	-> 189
/*     */     //   #152	-> 197
/*     */     //   #153	-> 205
/*     */     //   #154	-> 208
/*     */     //   #158	-> 211
/*     */     //   #159	-> 215
/*     */     //   #161	-> 218
/*     */     //   #163	-> 221
/*     */     //   #164	-> 227
/*     */     //   #166	-> 230
/*     */     //   #167	-> 236
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   62	44	11	localFlushedAmount	I
/*     */     //   35	83	7	memoryAddress	J
/*     */     //   41	77	9	readerIndex	I
/*     */     //   47	71	10	writerIndex	I
/*     */     //   151	67	9	pos	I
/*     */     //   158	60	10	limit	I
/*     */     //   173	45	11	localFlushedAmount	I
/*     */     //   132	98	7	readerIndex	I
/*     */     //   144	86	8	nioBuf	Ljava/nio/ByteBuffer;
/*     */     //   236	13	7	nioBuffers	[Ljava/nio/ByteBuffer;
/*     */     //   0	249	0	this	Lio/netty/channel/epoll/EpollSocketChannel;
/*     */     //   0	249	1	in	Lio/netty/channel/ChannelOutboundBuffer;
/*     */     //   0	249	2	buf	Lio/netty/buffer/ByteBuf;
/*     */     //   5	244	3	readableBytes	I
/*     */     //   19	230	4	done	Z
/*     */     //   22	227	5	writtenBytes	J
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
/*     */   private boolean writeBytesMultiple(ChannelOutboundBuffer in, IovArray array) throws IOException {
/* 173 */     long expectedWrittenBytes = array.size();
/* 174 */     int cnt = array.count();
/*     */     
/* 176 */     assert expectedWrittenBytes != 0L;
/* 177 */     assert cnt != 0;
/*     */     
/* 179 */     boolean done = false;
/* 180 */     long writtenBytes = 0L;
/* 181 */     int offset = 0;
/* 182 */     int end = offset + cnt;
/*     */     label29: while (true) {
/* 184 */       long localWrittenBytes = Native.writevAddresses(this.fd, array.memoryAddress(offset), cnt);
/* 185 */       if (localWrittenBytes == 0L) {
/*     */         
/* 187 */         setEpollOut();
/*     */         break;
/*     */       } 
/* 190 */       expectedWrittenBytes -= localWrittenBytes;
/* 191 */       writtenBytes += localWrittenBytes;
/*     */       
/* 193 */       if (expectedWrittenBytes == 0L) {
/*     */         
/* 195 */         done = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */       while (true) {
/* 200 */         long bytes = array.processWritten(offset, localWrittenBytes);
/* 201 */         if (bytes == -1L) {
/*     */           continue label29;
/*     */         }
/*     */         
/* 205 */         offset++;
/* 206 */         cnt--;
/* 207 */         localWrittenBytes -= bytes;
/*     */         
/* 209 */         if (offset < end) { if (localWrittenBytes <= 0L)
/*     */             continue label29;  continue; }  continue label29;
/*     */       } 
/* 212 */     }  in.removeBytes(writtenBytes);
/* 213 */     return done;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean writeBytesMultiple(ChannelOutboundBuffer in, ByteBuffer[] nioBuffers, int nioBufferCnt, long expectedWrittenBytes) throws IOException {
/* 220 */     assert expectedWrittenBytes != 0L;
/*     */     
/* 222 */     boolean done = false;
/* 223 */     long writtenBytes = 0L;
/* 224 */     int offset = 0;
/* 225 */     int end = offset + nioBufferCnt;
/*     */     label26: while (true) {
/* 227 */       long localWrittenBytes = Native.writev(this.fd, nioBuffers, offset, nioBufferCnt);
/* 228 */       if (localWrittenBytes == 0L) {
/*     */         
/* 230 */         setEpollOut();
/*     */         break;
/*     */       } 
/* 233 */       expectedWrittenBytes -= localWrittenBytes;
/* 234 */       writtenBytes += localWrittenBytes;
/*     */       
/* 236 */       if (expectedWrittenBytes == 0L) {
/*     */         
/* 238 */         done = true;
/*     */         break;
/*     */       } 
/*     */       while (true) {
/* 242 */         ByteBuffer buffer = nioBuffers[offset];
/* 243 */         int pos = buffer.position();
/* 244 */         int bytes = buffer.limit() - pos;
/* 245 */         if (bytes > localWrittenBytes) {
/* 246 */           buffer.position(pos + (int)localWrittenBytes);
/*     */           
/*     */           continue label26;
/*     */         } 
/* 250 */         offset++;
/* 251 */         nioBufferCnt--;
/* 252 */         localWrittenBytes -= bytes;
/*     */         
/* 254 */         if (offset < end) { if (localWrittenBytes <= 0L)
/*     */             continue label26;  continue; }  continue label26;
/*     */       } 
/* 257 */     }  in.removeBytes(writtenBytes);
/* 258 */     return done;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean writeFileRegion(ChannelOutboundBuffer in, DefaultFileRegion region) throws Exception {
/* 268 */     long regionCount = region.count();
/* 269 */     if (region.transfered() >= regionCount) {
/* 270 */       in.remove();
/* 271 */       return true;
/*     */     } 
/*     */     
/* 274 */     long baseOffset = region.position();
/* 275 */     boolean done = false;
/* 276 */     long flushedAmount = 0L;
/*     */     
/* 278 */     for (int i = config().getWriteSpinCount() - 1; i >= 0; i--) {
/* 279 */       long offset = region.transfered();
/* 280 */       long localFlushedAmount = Native.sendfile(this.fd, region, baseOffset, offset, regionCount - offset);
/* 281 */       if (localFlushedAmount == 0L) {
/*     */         
/* 283 */         setEpollOut();
/*     */         
/*     */         break;
/*     */       } 
/* 287 */       flushedAmount += localFlushedAmount;
/* 288 */       if (region.transfered() >= regionCount) {
/* 289 */         done = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 294 */     if (flushedAmount > 0L) {
/* 295 */       in.progress(flushedAmount);
/*     */     }
/*     */     
/* 298 */     if (done) {
/* 299 */       in.remove();
/*     */     }
/* 301 */     return done;
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*     */     int msgCount;
/*     */     do {
/* 307 */       msgCount = in.size();
/*     */       
/* 309 */       if (msgCount == 0) {
/*     */         
/* 311 */         clearEpollOut();
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/* 316 */     } while ((msgCount > 1) ? ((in.current() instanceof ByteBuf) ? 
/* 317 */       !doWriteMultiple(in) : 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 325 */       !doWriteSingle(in)) : !doWriteSingle(in));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean doWriteSingle(ChannelOutboundBuffer in) throws Exception {
/* 334 */     Object msg = in.current();
/* 335 */     if (msg instanceof ByteBuf) {
/* 336 */       ByteBuf buf = (ByteBuf)msg;
/* 337 */       if (!writeBytes(in, buf))
/*     */       {
/*     */         
/* 340 */         return false;
/*     */       }
/* 342 */     } else if (msg instanceof DefaultFileRegion) {
/* 343 */       DefaultFileRegion region = (DefaultFileRegion)msg;
/* 344 */       if (!writeFileRegion(in, region))
/*     */       {
/*     */         
/* 347 */         return false;
/*     */       }
/*     */     } else {
/*     */       
/* 351 */       throw new Error();
/*     */     } 
/*     */     
/* 354 */     return true;
/*     */   }
/*     */   
/*     */   private boolean doWriteMultiple(ChannelOutboundBuffer in) throws Exception {
/* 358 */     if (PlatformDependent.hasUnsafe()) {
/*     */       
/* 360 */       IovArray array = IovArray.get(in);
/* 361 */       int cnt = array.count();
/* 362 */       if (cnt >= 1) {
/*     */         
/* 364 */         if (!writeBytesMultiple(in, array))
/*     */         {
/*     */           
/* 367 */           return false;
/*     */         }
/*     */       } else {
/* 370 */         in.removeBytes(0L);
/*     */       } 
/*     */     } else {
/* 373 */       ByteBuffer[] buffers = in.nioBuffers();
/* 374 */       int cnt = in.nioBufferCount();
/* 375 */       if (cnt >= 1) {
/*     */         
/* 377 */         if (!writeBytesMultiple(in, buffers, cnt, in.nioBufferSize()))
/*     */         {
/*     */           
/* 380 */           return false;
/*     */         }
/*     */       } else {
/* 383 */         in.removeBytes(0L);
/*     */       } 
/*     */     } 
/*     */     
/* 387 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 392 */     if (msg instanceof ByteBuf) {
/* 393 */       ByteBuf buf = (ByteBuf)msg;
/* 394 */       if (!buf.hasMemoryAddress() && (PlatformDependent.hasUnsafe() || !buf.isDirect())) {
/*     */ 
/*     */         
/* 397 */         buf = newDirectBuffer(buf);
/* 398 */         assert buf.hasMemoryAddress();
/*     */       } 
/* 400 */       return buf;
/*     */     } 
/*     */     
/* 403 */     if (msg instanceof DefaultFileRegion) {
/* 404 */       return msg;
/*     */     }
/*     */     
/* 407 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig config() {
/* 413 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInputShutdown() {
/* 418 */     return this.inputShutdown;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOutputShutdown() {
/* 423 */     return (this.outputShutdown || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput() {
/* 428 */     return shutdownOutput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput(final ChannelPromise promise) {
/* 433 */     EventLoop loop = eventLoop();
/* 434 */     if (loop.inEventLoop()) {
/*     */       try {
/* 436 */         Native.shutdown(this.fd, false, true);
/* 437 */         this.outputShutdown = true;
/* 438 */         promise.setSuccess();
/* 439 */       } catch (Throwable t) {
/* 440 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 443 */       loop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 446 */               EpollSocketChannel.this.shutdownOutput(promise);
/*     */             }
/*     */           });
/*     */     } 
/* 450 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannel parent() {
/* 455 */     return (ServerSocketChannel)super.parent();
/*     */   }
/*     */   
/*     */   final class EpollSocketUnsafe extends AbstractEpollChannel.AbstractEpollUnsafe {
/*     */     private RecvByteBufAllocator.Handle allocHandle;
/*     */     
/*     */     private void closeOnRead(ChannelPipeline pipeline) {
/* 462 */       EpollSocketChannel.this.inputShutdown = true;
/* 463 */       if (EpollSocketChannel.this.isOpen()) {
/* 464 */         if (Boolean.TRUE.equals(EpollSocketChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
/* 465 */           clearEpollIn0();
/* 466 */           pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*     */         } else {
/* 468 */           close(voidPromise());
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     private boolean handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close) {
/* 474 */       if (byteBuf != null) {
/* 475 */         if (byteBuf.isReadable()) {
/* 476 */           this.readPending = false;
/* 477 */           pipeline.fireChannelRead(byteBuf);
/*     */         } else {
/* 479 */           byteBuf.release();
/*     */         } 
/*     */       }
/* 482 */       pipeline.fireChannelReadComplete();
/* 483 */       pipeline.fireExceptionCaught(cause);
/* 484 */       if (close || cause instanceof IOException) {
/* 485 */         closeOnRead(pipeline);
/* 486 */         return true;
/*     */       } 
/* 488 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void connect(final SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 494 */       if (!promise.setUncancellable() || !ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 499 */         if (EpollSocketChannel.this.connectPromise != null) {
/* 500 */           throw new IllegalStateException("connection attempt already made");
/*     */         }
/*     */         
/* 503 */         boolean wasActive = EpollSocketChannel.this.isActive();
/* 504 */         if (doConnect((InetSocketAddress)remoteAddress, (InetSocketAddress)localAddress)) {
/* 505 */           fulfillConnectPromise(promise, wasActive);
/*     */         } else {
/* 507 */           EpollSocketChannel.this.connectPromise = promise;
/* 508 */           EpollSocketChannel.this.requestedRemoteAddress = remoteAddress;
/*     */ 
/*     */           
/* 511 */           int connectTimeoutMillis = EpollSocketChannel.this.config().getConnectTimeoutMillis();
/* 512 */           if (connectTimeoutMillis > 0) {
/* 513 */             EpollSocketChannel.this.connectTimeoutFuture = (ScheduledFuture<?>)EpollSocketChannel.this.eventLoop().schedule(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 516 */                     ChannelPromise connectPromise = EpollSocketChannel.this.connectPromise;
/* 517 */                     ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
/*     */                     
/* 519 */                     if (connectPromise != null && connectPromise.tryFailure((Throwable)cause)) {
/* 520 */                       EpollSocketChannel.EpollSocketUnsafe.this.close(EpollSocketChannel.EpollSocketUnsafe.this.voidPromise());
/*     */                     }
/*     */                   }
/*     */                 },  connectTimeoutMillis, TimeUnit.MILLISECONDS);
/*     */           }
/*     */           
/* 526 */           promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) throws Exception {
/* 529 */                   if (future.isCancelled()) {
/* 530 */                     if (EpollSocketChannel.this.connectTimeoutFuture != null) {
/* 531 */                       EpollSocketChannel.this.connectTimeoutFuture.cancel(false);
/*     */                     }
/* 533 */                     EpollSocketChannel.this.connectPromise = null;
/* 534 */                     EpollSocketChannel.EpollSocketUnsafe.this.close(EpollSocketChannel.EpollSocketUnsafe.this.voidPromise());
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/* 539 */       } catch (Throwable t) {
/* 540 */         if (t instanceof ConnectException) {
/* 541 */           Throwable newT = new ConnectException(t.getMessage() + ": " + remoteAddress);
/* 542 */           newT.setStackTrace(t.getStackTrace());
/* 543 */           t = newT;
/*     */         } 
/* 545 */         closeIfClosed();
/* 546 */         promise.tryFailure(t);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise promise, boolean wasActive) {
/* 551 */       if (promise == null) {
/*     */         return;
/*     */       }
/*     */       
/* 555 */       EpollSocketChannel.this.active = true;
/*     */ 
/*     */       
/* 558 */       boolean promiseSet = promise.trySuccess();
/*     */ 
/*     */ 
/*     */       
/* 562 */       if (!wasActive && EpollSocketChannel.this.isActive()) {
/* 563 */         EpollSocketChannel.this.pipeline().fireChannelActive();
/*     */       }
/*     */ 
/*     */       
/* 567 */       if (!promiseSet) {
/* 568 */         close(voidPromise());
/*     */       }
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise promise, Throwable cause) {
/* 573 */       if (promise == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 579 */       promise.tryFailure(cause);
/* 580 */       closeIfClosed();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void finishConnect() {
/* 587 */       assert EpollSocketChannel.this.eventLoop().inEventLoop();
/*     */       
/* 589 */       boolean connectStillInProgress = false;
/*     */       try {
/* 591 */         boolean wasActive = EpollSocketChannel.this.isActive();
/* 592 */         if (!doFinishConnect()) {
/* 593 */           connectStillInProgress = true;
/*     */           return;
/*     */         } 
/* 596 */         fulfillConnectPromise(EpollSocketChannel.this.connectPromise, wasActive);
/* 597 */       } catch (Throwable t) {
/* 598 */         if (t instanceof ConnectException) {
/* 599 */           Throwable newT = new ConnectException(t.getMessage() + ": " + EpollSocketChannel.this.requestedRemoteAddress);
/* 600 */           newT.setStackTrace(t.getStackTrace());
/* 601 */           t = newT;
/*     */         } 
/*     */         
/* 604 */         fulfillConnectPromise(EpollSocketChannel.this.connectPromise, t);
/*     */       } finally {
/* 606 */         if (!connectStillInProgress) {
/*     */ 
/*     */           
/* 609 */           if (EpollSocketChannel.this.connectTimeoutFuture != null) {
/* 610 */             EpollSocketChannel.this.connectTimeoutFuture.cancel(false);
/*     */           }
/* 612 */           EpollSocketChannel.this.connectPromise = null;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     void epollOutReady() {
/* 619 */       if (EpollSocketChannel.this.connectPromise != null) {
/*     */         
/* 621 */         finishConnect();
/*     */       } else {
/* 623 */         super.epollOutReady();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean doConnect(InetSocketAddress remoteAddress, InetSocketAddress localAddress) throws Exception {
/* 631 */       if (localAddress != null) {
/* 632 */         AbstractEpollChannel.checkResolvable(localAddress);
/* 633 */         Native.bind(EpollSocketChannel.this.fd, localAddress.getAddress(), localAddress.getPort());
/*     */       } 
/*     */       
/* 636 */       boolean success = false;
/*     */       try {
/* 638 */         AbstractEpollChannel.checkResolvable(remoteAddress);
/* 639 */         boolean connected = Native.connect(EpollSocketChannel.this.fd, remoteAddress.getAddress(), remoteAddress.getPort());
/*     */         
/* 641 */         EpollSocketChannel.this.remote = remoteAddress;
/* 642 */         EpollSocketChannel.this.local = Native.localAddress(EpollSocketChannel.this.fd);
/* 643 */         if (!connected) {
/* 644 */           EpollSocketChannel.this.setEpollOut();
/*     */         }
/* 646 */         success = true;
/* 647 */         return connected;
/*     */       } finally {
/* 649 */         if (!success) {
/* 650 */           EpollSocketChannel.this.doClose();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean doFinishConnect() throws Exception {
/* 659 */       if (Native.finishConnect(EpollSocketChannel.this.fd)) {
/* 660 */         EpollSocketChannel.this.clearEpollOut();
/* 661 */         return true;
/*     */       } 
/* 663 */       EpollSocketChannel.this.setEpollOut();
/* 664 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int doReadBytes(ByteBuf byteBuf) throws Exception {
/* 672 */       int localReadAmount, writerIndex = byteBuf.writerIndex();
/*     */       
/* 674 */       if (byteBuf.hasMemoryAddress()) {
/* 675 */         localReadAmount = Native.readAddress(EpollSocketChannel.this.fd, byteBuf.memoryAddress(), writerIndex, byteBuf.capacity());
/*     */       } else {
/* 677 */         ByteBuffer buf = byteBuf.internalNioBuffer(writerIndex, byteBuf.writableBytes());
/* 678 */         localReadAmount = Native.read(EpollSocketChannel.this.fd, buf, buf.position(), buf.limit());
/*     */       } 
/* 680 */       if (localReadAmount > 0) {
/* 681 */         byteBuf.writerIndex(writerIndex + localReadAmount);
/*     */       }
/* 683 */       return localReadAmount;
/*     */     }
/*     */ 
/*     */     
/*     */     void epollRdHupReady() {
/* 688 */       if (EpollSocketChannel.this.isActive()) {
/* 689 */         epollInReady();
/*     */       } else {
/* 691 */         closeOnRead(EpollSocketChannel.this.pipeline());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     void epollInReady() {
/* 697 */       EpollSocketChannelConfig epollSocketChannelConfig = EpollSocketChannel.this.config();
/* 698 */       ChannelPipeline pipeline = EpollSocketChannel.this.pipeline();
/* 699 */       ByteBufAllocator allocator = epollSocketChannelConfig.getAllocator();
/* 700 */       RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/* 701 */       if (allocHandle == null) {
/* 702 */         this.allocHandle = allocHandle = epollSocketChannelConfig.getRecvByteBufAllocator().newHandle();
/*     */       }
/*     */       
/* 705 */       ByteBuf byteBuf = null;
/* 706 */       boolean close = false;
/*     */       try {
/* 708 */         int writable, localReadAmount, totalReadAmount = 0;
/*     */ 
/*     */         
/*     */         do {
/* 712 */           byteBuf = allocHandle.allocate(allocator);
/* 713 */           writable = byteBuf.writableBytes();
/* 714 */           localReadAmount = doReadBytes(byteBuf);
/* 715 */           if (localReadAmount <= 0) {
/*     */             
/* 717 */             byteBuf.release();
/* 718 */             close = (localReadAmount < 0);
/*     */             break;
/*     */           } 
/* 721 */           this.readPending = false;
/* 722 */           pipeline.fireChannelRead(byteBuf);
/* 723 */           byteBuf = null;
/*     */           
/* 725 */           if (totalReadAmount >= Integer.MAX_VALUE - localReadAmount) {
/* 726 */             allocHandle.record(totalReadAmount);
/*     */ 
/*     */             
/* 729 */             totalReadAmount = localReadAmount;
/*     */           } else {
/* 731 */             totalReadAmount += localReadAmount;
/*     */           }
/*     */         
/* 734 */         } while (localReadAmount >= writable);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 740 */         pipeline.fireChannelReadComplete();
/* 741 */         allocHandle.record(totalReadAmount);
/*     */         
/* 743 */         if (close) {
/* 744 */           closeOnRead(pipeline);
/* 745 */           close = false;
/*     */         } 
/* 747 */       } catch (Throwable t) {
/* 748 */         boolean closed = handleReadException(pipeline, byteBuf, t, close);
/* 749 */         if (!closed)
/*     */         {
/*     */           
/* 752 */           EpollSocketChannel.this.eventLoop().execute(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 755 */                   EpollSocketChannel.EpollSocketUnsafe.this.epollInReady();
/*     */                 }
/*     */               });
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 766 */         if (!epollSocketChannelConfig.isAutoRead() && !this.readPending)
/* 767 */           clearEpollIn0(); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\epoll\EpollSocketChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */