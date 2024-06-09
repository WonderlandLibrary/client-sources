/*     */ package io.netty.channel.socket.nio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.nio.AbstractNioByteChannel;
/*     */ import io.netty.channel.nio.NioEventLoop;
/*     */ import io.netty.channel.socket.DefaultSocketChannelConfig;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import io.netty.channel.socket.SocketChannel;
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.util.internal.OneTimeTask;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.nio.channels.spi.SelectorProvider;
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
/*     */ public class NioSocketChannel
/*     */   extends AbstractNioByteChannel
/*     */   implements SocketChannel
/*     */ {
/*  47 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*  48 */   private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
/*     */ 
/*     */ 
/*     */   
/*     */   private final SocketChannelConfig config;
/*     */ 
/*     */ 
/*     */   
/*     */   private static SocketChannel newSocket(SelectorProvider provider) {
/*     */     try {
/*  58 */       return provider.openSocketChannel();
/*  59 */     } catch (IOException e) {
/*  60 */       throw new ChannelException("Failed to open a socket.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSocketChannel() {
/*  70 */     this(newSocket(DEFAULT_SELECTOR_PROVIDER));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSocketChannel(SelectorProvider provider) {
/*  77 */     this(newSocket(provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSocketChannel(SocketChannel socket) {
/*  84 */     this((Channel)null, socket);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSocketChannel(Channel parent, SocketChannel socket) {
/*  94 */     super(parent, socket);
/*  95 */     this.config = (SocketChannelConfig)new NioSocketChannelConfig(this, socket.socket());
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannel parent() {
/* 100 */     return (ServerSocketChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 105 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketChannelConfig config() {
/* 110 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketChannel javaChannel() {
/* 115 */     return (SocketChannel)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 120 */     SocketChannel ch = javaChannel();
/* 121 */     return (ch.isOpen() && ch.isConnected());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInputShutdown() {
/* 126 */     return super.isInputShutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 131 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 136 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOutputShutdown() {
/* 141 */     return (javaChannel().socket().isOutputShutdown() || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput() {
/* 146 */     return shutdownOutput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput(final ChannelPromise promise) {
/* 151 */     NioEventLoop nioEventLoop = eventLoop();
/* 152 */     if (nioEventLoop.inEventLoop()) {
/*     */       try {
/* 154 */         javaChannel().socket().shutdownOutput();
/* 155 */         promise.setSuccess();
/* 156 */       } catch (Throwable t) {
/* 157 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 160 */       nioEventLoop.execute((Runnable)new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 163 */               NioSocketChannel.this.shutdownOutput(promise);
/*     */             }
/*     */           });
/*     */     } 
/* 167 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 172 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 177 */     return javaChannel().socket().getRemoteSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 182 */     javaChannel().socket().bind(localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 187 */     if (localAddress != null) {
/* 188 */       javaChannel().socket().bind(localAddress);
/*     */     }
/*     */     
/* 191 */     boolean success = false;
/*     */     try {
/* 193 */       boolean connected = javaChannel().connect(remoteAddress);
/* 194 */       if (!connected) {
/* 195 */         selectionKey().interestOps(8);
/*     */       }
/* 197 */       success = true;
/* 198 */       return connected;
/*     */     } finally {
/* 200 */       if (!success) {
/* 201 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 208 */     if (!javaChannel().finishConnect()) {
/* 209 */       throw new Error();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 215 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 220 */     javaChannel().close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadBytes(ByteBuf byteBuf) throws Exception {
/* 225 */     return byteBuf.writeBytes(javaChannel(), byteBuf.writableBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doWriteBytes(ByteBuf buf) throws Exception {
/* 230 */     int expectedWrittenBytes = buf.readableBytes();
/* 231 */     return buf.readBytes(javaChannel(), expectedWrittenBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long doWriteFileRegion(FileRegion region) throws Exception {
/* 236 */     long position = region.transfered();
/* 237 */     return region.transferTo(javaChannel(), position);
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*     */     while (true) {
/*     */       ByteBuffer nioBuffer;
/* 243 */       int i, size = in.size();
/* 244 */       if (size == 0) {
/*     */         
/* 246 */         clearOpWrite();
/*     */         break;
/*     */       } 
/* 249 */       long writtenBytes = 0L;
/* 250 */       boolean done = false;
/* 251 */       boolean setOpWrite = false;
/*     */ 
/*     */       
/* 254 */       ByteBuffer[] nioBuffers = in.nioBuffers();
/* 255 */       int nioBufferCnt = in.nioBufferCount();
/* 256 */       long expectedWrittenBytes = in.nioBufferSize();
/* 257 */       SocketChannel ch = javaChannel();
/*     */ 
/*     */ 
/*     */       
/* 261 */       switch (nioBufferCnt) {
/*     */         
/*     */         case 0:
/* 264 */           super.doWrite(in);
/*     */           return;
/*     */         
/*     */         case 1:
/* 268 */           nioBuffer = nioBuffers[0];
/* 269 */           for (i = config().getWriteSpinCount() - 1; i >= 0; i--) {
/* 270 */             int localWrittenBytes = ch.write(nioBuffer);
/* 271 */             if (localWrittenBytes == 0) {
/* 272 */               setOpWrite = true;
/*     */               break;
/*     */             } 
/* 275 */             expectedWrittenBytes -= localWrittenBytes;
/* 276 */             writtenBytes += localWrittenBytes;
/* 277 */             if (expectedWrittenBytes == 0L) {
/* 278 */               done = true;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         default:
/* 284 */           for (i = config().getWriteSpinCount() - 1; i >= 0; i--) {
/* 285 */             long localWrittenBytes = ch.write(nioBuffers, 0, nioBufferCnt);
/* 286 */             if (localWrittenBytes == 0L) {
/* 287 */               setOpWrite = true;
/*     */               break;
/*     */             } 
/* 290 */             expectedWrittenBytes -= localWrittenBytes;
/* 291 */             writtenBytes += localWrittenBytes;
/* 292 */             if (expectedWrittenBytes == 0L) {
/* 293 */               done = true;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 301 */       in.removeBytes(writtenBytes);
/*     */       
/* 303 */       if (!done) {
/*     */         
/* 305 */         incompleteWrite(setOpWrite);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private final class NioSocketChannelConfig extends DefaultSocketChannelConfig {
/*     */     private NioSocketChannelConfig(NioSocketChannel channel, Socket javaSocket) {
/* 313 */       super(channel, javaSocket);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void autoReadCleared() {
/* 318 */       NioSocketChannel.this.setReadPending(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\socket\nio\NioSocketChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */