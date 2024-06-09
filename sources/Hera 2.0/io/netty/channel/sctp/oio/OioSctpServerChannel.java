/*     */ package io.netty.channel.sctp.oio;
/*     */ 
/*     */ import com.sun.nio.sctp.SctpChannel;
/*     */ import com.sun.nio.sctp.SctpServerChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.oio.AbstractOioMessageChannel;
/*     */ import io.netty.channel.sctp.DefaultSctpServerChannelConfig;
/*     */ import io.netty.channel.sctp.SctpServerChannel;
/*     */ import io.netty.channel.sctp.SctpServerChannelConfig;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class OioSctpServerChannel
/*     */   extends AbstractOioMessageChannel
/*     */   implements SctpServerChannel
/*     */ {
/*  53 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSctpServerChannel.class);
/*     */ 
/*     */   
/*  56 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*     */   private static SctpServerChannel newServerSocket() {
/*     */     try {
/*  60 */       return SctpServerChannel.open();
/*  61 */     } catch (IOException e) {
/*  62 */       throw new ChannelException("failed to create a sctp server channel", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final SctpServerChannel sch;
/*     */   
/*     */   private final SctpServerChannelConfig config;
/*     */   
/*     */   private final Selector selector;
/*     */   
/*     */   public OioSctpServerChannel() {
/*  74 */     this(newServerSocket());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioSctpServerChannel(SctpServerChannel sch) {
/*  83 */     super(null);
/*  84 */     if (sch == null) {
/*  85 */       throw new NullPointerException("sctp server channel");
/*     */     }
/*     */     
/*  88 */     this.sch = sch;
/*  89 */     boolean success = false;
/*     */     try {
/*  91 */       sch.configureBlocking(false);
/*  92 */       this.selector = Selector.open();
/*  93 */       sch.register(this.selector, 16);
/*  94 */       this.config = (SctpServerChannelConfig)new OioSctpServerChannelConfig(this, sch);
/*  95 */       success = true;
/*  96 */     } catch (Exception e) {
/*  97 */       throw new ChannelException("failed to initialize a sctp server channel", e);
/*     */     } finally {
/*  99 */       if (!success) {
/*     */         try {
/* 101 */           sch.close();
/* 102 */         } catch (IOException e) {
/* 103 */           logger.warn("Failed to close a sctp server channel.", e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 111 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig config() {
/* 116 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 121 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 126 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 131 */     return this.sch.isOpen();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*     */     try {
/* 137 */       Iterator<SocketAddress> i = this.sch.getAllLocalAddresses().iterator();
/* 138 */       if (i.hasNext()) {
/* 139 */         return i.next();
/*     */       }
/* 141 */     } catch (IOException e) {}
/*     */ 
/*     */     
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<InetSocketAddress> allLocalAddresses() {
/*     */     try {
/* 150 */       Set<SocketAddress> allLocalAddresses = this.sch.getAllLocalAddresses();
/* 151 */       Set<InetSocketAddress> addresses = new LinkedHashSet<InetSocketAddress>(allLocalAddresses.size());
/* 152 */       for (SocketAddress socketAddress : allLocalAddresses) {
/* 153 */         addresses.add((InetSocketAddress)socketAddress);
/*     */       }
/* 155 */       return addresses;
/* 156 */     } catch (Throwable ignored) {
/* 157 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 163 */     return (isOpen() && localAddress0() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 168 */     this.sch.bind(localAddress, this.config.getBacklog());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/*     */     try {
/* 174 */       this.selector.close();
/* 175 */     } catch (IOException e) {
/* 176 */       logger.warn("Failed to close a selector.", e);
/*     */     } 
/* 178 */     this.sch.close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 183 */     if (!isActive()) {
/* 184 */       return -1;
/*     */     }
/*     */     
/* 187 */     SctpChannel s = null;
/* 188 */     int acceptedChannels = 0;
/*     */     try {
/* 190 */       int selectedKeys = this.selector.select(1000L);
/* 191 */       if (selectedKeys > 0) {
/* 192 */         Iterator<SelectionKey> selectionKeys = this.selector.selectedKeys().iterator();
/*     */         while (true) {
/* 194 */           SelectionKey key = selectionKeys.next();
/* 195 */           selectionKeys.remove();
/* 196 */           if (key.isAcceptable()) {
/* 197 */             s = this.sch.accept();
/* 198 */             if (s != null) {
/* 199 */               buf.add(new OioSctpChannel((Channel)this, s));
/* 200 */               acceptedChannels++;
/*     */             } 
/*     */           } 
/* 203 */           if (!selectionKeys.hasNext()) {
/* 204 */             return acceptedChannels;
/*     */           }
/*     */         } 
/*     */       } 
/* 208 */     } catch (Throwable t) {
/* 209 */       logger.warn("Failed to create a new channel from an accepted sctp channel.", t);
/* 210 */       if (s != null) {
/*     */         try {
/* 212 */           s.close();
/* 213 */         } catch (Throwable t2) {
/* 214 */           logger.warn("Failed to close a sctp channel.", t2);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 219 */     return acceptedChannels;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bindAddress(InetAddress localAddress) {
/* 224 */     return bindAddress(localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bindAddress(final InetAddress localAddress, final ChannelPromise promise) {
/* 229 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 231 */         this.sch.bindAddress(localAddress);
/* 232 */         promise.setSuccess();
/* 233 */       } catch (Throwable t) {
/* 234 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 237 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 240 */               OioSctpServerChannel.this.bindAddress(localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 244 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture unbindAddress(InetAddress localAddress) {
/* 249 */     return unbindAddress(localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture unbindAddress(final InetAddress localAddress, final ChannelPromise promise) {
/* 254 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 256 */         this.sch.unbindAddress(localAddress);
/* 257 */         promise.setSuccess();
/* 258 */       } catch (Throwable t) {
/* 259 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 262 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 265 */               OioSctpServerChannel.this.unbindAddress(localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 269 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 275 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 280 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 285 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 290 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) throws Exception {
/* 295 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private final class OioSctpServerChannelConfig extends DefaultSctpServerChannelConfig {
/*     */     private OioSctpServerChannelConfig(OioSctpServerChannel channel, SctpServerChannel javaChannel) {
/* 300 */       super(channel, javaChannel);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void autoReadCleared() {
/* 305 */       OioSctpServerChannel.this.setReadPending(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\sctp\oio\OioSctpServerChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */