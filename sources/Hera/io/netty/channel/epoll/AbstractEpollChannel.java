/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.OneTimeTask;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.UnresolvedAddressException;
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
/*     */ abstract class AbstractEpollChannel
/*     */   extends AbstractChannel
/*     */ {
/*  33 */   private static final ChannelMetadata DATA = new ChannelMetadata(false);
/*     */   private final int readFlag;
/*     */   protected int flags;
/*     */   protected volatile boolean active;
/*     */   volatile int fd;
/*     */   int id;
/*     */   
/*     */   AbstractEpollChannel(int fd, int flag) {
/*  41 */     this((Channel)null, fd, flag, false);
/*     */   }
/*     */   
/*     */   AbstractEpollChannel(Channel parent, int fd, int flag, boolean active) {
/*  45 */     super(parent);
/*  46 */     this.fd = fd;
/*  47 */     this.readFlag = flag;
/*  48 */     this.flags |= flag;
/*  49 */     this.active = active;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  54 */     return this.active;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  59 */     return DATA;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/*  64 */     this.active = false;
/*     */ 
/*     */     
/*  67 */     doDeregister();
/*     */     
/*  69 */     int fd = this.fd;
/*  70 */     this.fd = -1;
/*  71 */     Native.close(fd);
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/*  76 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/*  81 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/*  86 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/*  91 */     return loop instanceof EpollEventLoop;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/*  96 */     return (this.fd != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {
/* 101 */     ((EpollEventLoop)eventLoop()).remove(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 107 */     ((AbstractEpollUnsafe)unsafe()).readPending = true;
/*     */     
/* 109 */     if ((this.flags & this.readFlag) == 0) {
/* 110 */       this.flags |= this.readFlag;
/* 111 */       modifyEvents();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   final void clearEpollIn() {
/* 117 */     if (isRegistered()) {
/* 118 */       EventLoop loop = eventLoop();
/* 119 */       final AbstractEpollUnsafe unsafe = (AbstractEpollUnsafe)unsafe();
/* 120 */       if (loop.inEventLoop()) {
/* 121 */         unsafe.clearEpollIn0();
/*     */       } else {
/*     */         
/* 124 */         loop.execute((Runnable)new OneTimeTask()
/*     */             {
/*     */               public void run() {
/* 127 */                 if (!AbstractEpollChannel.this.config().isAutoRead() && !unsafe.readPending)
/*     */                 {
/* 129 */                   unsafe.clearEpollIn0();
/*     */                 }
/*     */               }
/*     */             });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 137 */       this.flags &= this.readFlag ^ 0xFFFFFFFF;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void setEpollOut() {
/* 142 */     if ((this.flags & 0x2) == 0) {
/* 143 */       this.flags |= 0x2;
/* 144 */       modifyEvents();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void clearEpollOut() {
/* 149 */     if ((this.flags & 0x2) != 0) {
/* 150 */       this.flags &= 0xFFFFFFFD;
/* 151 */       modifyEvents();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void modifyEvents() {
/* 156 */     if (isOpen()) {
/* 157 */       ((EpollEventLoop)eventLoop()).modify(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRegister() throws Exception {
/* 163 */     EpollEventLoop loop = (EpollEventLoop)eventLoop();
/* 164 */     loop.add(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuf newDirectBuffer(ByteBuf buf) {
/* 174 */     return newDirectBuffer(buf, buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuf newDirectBuffer(Object holder, ByteBuf buf) {
/* 183 */     int readableBytes = buf.readableBytes();
/* 184 */     if (readableBytes == 0) {
/* 185 */       ReferenceCountUtil.safeRelease(holder);
/* 186 */       return Unpooled.EMPTY_BUFFER;
/*     */     } 
/*     */     
/* 189 */     ByteBufAllocator alloc = alloc();
/* 190 */     if (alloc.isDirectBufferPooled()) {
/* 191 */       return newDirectBuffer0(holder, buf, alloc, readableBytes);
/*     */     }
/*     */     
/* 194 */     ByteBuf directBuf = ByteBufUtil.threadLocalDirectBuffer();
/* 195 */     if (directBuf == null) {
/* 196 */       return newDirectBuffer0(holder, buf, alloc, readableBytes);
/*     */     }
/*     */     
/* 199 */     directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
/* 200 */     ReferenceCountUtil.safeRelease(holder);
/* 201 */     return directBuf;
/*     */   }
/*     */   
/*     */   private static ByteBuf newDirectBuffer0(Object holder, ByteBuf buf, ByteBufAllocator alloc, int capacity) {
/* 205 */     ByteBuf directBuf = alloc.directBuffer(capacity);
/* 206 */     directBuf.writeBytes(buf, buf.readerIndex(), capacity);
/* 207 */     ReferenceCountUtil.safeRelease(holder);
/* 208 */     return directBuf;
/*     */   }
/*     */   protected abstract AbstractEpollUnsafe newUnsafe();
/*     */   protected static void checkResolvable(InetSocketAddress addr) {
/* 212 */     if (addr.isUnresolved())
/* 213 */       throw new UnresolvedAddressException(); 
/*     */   }
/*     */   
/*     */   protected abstract class AbstractEpollUnsafe extends AbstractChannel.AbstractUnsafe { protected AbstractEpollUnsafe() {
/* 217 */       super(AbstractEpollChannel.this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean readPending;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract void epollInReady();
/*     */ 
/*     */ 
/*     */     
/*     */     void epollRdHupReady() {}
/*     */ 
/*     */ 
/*     */     
/*     */     protected void flush0() {
/* 237 */       if (isFlushPending()) {
/*     */         return;
/*     */       }
/* 240 */       super.flush0();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void epollOutReady() {
/* 248 */       super.flush0();
/*     */     }
/*     */     
/*     */     private boolean isFlushPending() {
/* 252 */       return ((AbstractEpollChannel.this.flags & 0x2) != 0);
/*     */     }
/*     */     
/*     */     protected final void clearEpollIn0() {
/* 256 */       if ((AbstractEpollChannel.this.flags & AbstractEpollChannel.this.readFlag) != 0) {
/* 257 */         AbstractEpollChannel.this.flags &= AbstractEpollChannel.this.readFlag ^ 0xFFFFFFFF;
/* 258 */         AbstractEpollChannel.this.modifyEvents();
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\epoll\AbstractEpollChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */