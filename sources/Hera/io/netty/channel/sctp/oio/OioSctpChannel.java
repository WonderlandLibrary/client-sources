/*     */ package io.netty.channel.sctp.oio;
/*     */ 
/*     */ import com.sun.nio.sctp.Association;
/*     */ import com.sun.nio.sctp.MessageInfo;
/*     */ import com.sun.nio.sctp.NotificationHandler;
/*     */ import com.sun.nio.sctp.SctpChannel;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.oio.AbstractOioMessageChannel;
/*     */ import io.netty.channel.sctp.DefaultSctpChannelConfig;
/*     */ import io.netty.channel.sctp.SctpChannel;
/*     */ import io.netty.channel.sctp.SctpChannelConfig;
/*     */ import io.netty.channel.sctp.SctpMessage;
/*     */ import io.netty.channel.sctp.SctpNotificationHandler;
/*     */ import io.netty.channel.sctp.SctpServerChannel;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ public class OioSctpChannel
/*     */   extends AbstractOioMessageChannel
/*     */   implements SctpChannel
/*     */ {
/*  64 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSctpChannel.class);
/*     */ 
/*     */   
/*  67 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*  68 */   private static final String EXPECTED_TYPE = " (expected: " + StringUtil.simpleClassName(SctpMessage.class) + ')';
/*     */   
/*     */   private final SctpChannel ch;
/*     */   
/*     */   private final SctpChannelConfig config;
/*     */   
/*     */   private final Selector readSelector;
/*     */   
/*     */   private final Selector writeSelector;
/*     */   private final Selector connectSelector;
/*     */   private final NotificationHandler<?> notificationHandler;
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */   
/*     */   private static SctpChannel openChannel() {
/*     */     try {
/*  83 */       return SctpChannel.open();
/*  84 */     } catch (IOException e) {
/*  85 */       throw new ChannelException("Failed to open a sctp channel.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioSctpChannel() {
/*  93 */     this(openChannel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioSctpChannel(SctpChannel ch) {
/* 102 */     this((Channel)null, ch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioSctpChannel(Channel parent, SctpChannel ch) {
/* 113 */     super(parent);
/* 114 */     this.ch = ch;
/* 115 */     boolean success = false;
/*     */     try {
/* 117 */       ch.configureBlocking(false);
/* 118 */       this.readSelector = Selector.open();
/* 119 */       this.writeSelector = Selector.open();
/* 120 */       this.connectSelector = Selector.open();
/*     */       
/* 122 */       ch.register(this.readSelector, 1);
/* 123 */       ch.register(this.writeSelector, 4);
/* 124 */       ch.register(this.connectSelector, 8);
/*     */       
/* 126 */       this.config = (SctpChannelConfig)new OioSctpChannelConfig(this, ch);
/* 127 */       this.notificationHandler = (NotificationHandler<?>)new SctpNotificationHandler(this);
/* 128 */       success = true;
/* 129 */     } catch (Exception e) {
/* 130 */       throw new ChannelException("failed to initialize a sctp channel", e);
/*     */     } finally {
/* 132 */       if (!success) {
/*     */         try {
/* 134 */           ch.close();
/* 135 */         } catch (IOException e) {
/* 136 */           logger.warn("Failed to close a sctp channel.", e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 144 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 149 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannel parent() {
/* 154 */     return (SctpServerChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 159 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig config() {
/* 164 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 169 */     return this.ch.isOpen();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> msgs) throws Exception {
/* 174 */     if (!this.readSelector.isOpen()) {
/* 175 */       return 0;
/*     */     }
/*     */     
/* 178 */     int readMessages = 0;
/*     */     
/* 180 */     int selectedKeys = this.readSelector.select(1000L);
/* 181 */     boolean keysSelected = (selectedKeys > 0);
/*     */     
/* 183 */     if (!keysSelected) {
/* 184 */       return readMessages;
/*     */     }
/*     */     
/* 187 */     Set<SelectionKey> reableKeys = this.readSelector.selectedKeys();
/*     */     try {
/* 189 */       for (SelectionKey ignored : reableKeys) {
/* 190 */         RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/* 191 */         if (allocHandle == null) {
/* 192 */           this.allocHandle = allocHandle = config().getRecvByteBufAllocator().newHandle();
/*     */         }
/* 194 */         ByteBuf buffer = allocHandle.allocate(config().getAllocator());
/* 195 */         boolean free = true;
/*     */         
/*     */         try {
/* 198 */           ByteBuffer data = buffer.nioBuffer(buffer.writerIndex(), buffer.writableBytes());
/* 199 */           MessageInfo messageInfo = this.ch.receive(data, (Object)null, this.notificationHandler);
/* 200 */           if (messageInfo == null) {
/* 201 */             return readMessages;
/*     */           }
/*     */           
/* 204 */           data.flip();
/* 205 */           msgs.add(new SctpMessage(messageInfo, buffer.writerIndex(buffer.writerIndex() + data.remaining())));
/* 206 */           free = false;
/* 207 */           readMessages++;
/* 208 */         } catch (Throwable cause) {
/* 209 */           PlatformDependent.throwException(cause);
/*     */         } finally {
/* 211 */           int bytesRead = buffer.readableBytes();
/* 212 */           allocHandle.record(bytesRead);
/* 213 */           if (free) {
/* 214 */             buffer.release();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 219 */       reableKeys.clear();
/*     */     } 
/* 221 */     return readMessages;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 226 */     if (!this.writeSelector.isOpen()) {
/*     */       return;
/*     */     }
/* 229 */     int size = in.size();
/* 230 */     int selectedKeys = this.writeSelector.select(1000L);
/* 231 */     if (selectedKeys > 0) {
/* 232 */       Set<SelectionKey> writableKeys = this.writeSelector.selectedKeys();
/* 233 */       if (writableKeys.isEmpty()) {
/*     */         return;
/*     */       }
/* 236 */       Iterator<SelectionKey> writableKeysIt = writableKeys.iterator();
/* 237 */       int written = 0; do {
/*     */         ByteBuffer nioData;
/* 239 */         if (written == size) {
/*     */           return;
/*     */         }
/*     */         
/* 243 */         writableKeysIt.next();
/* 244 */         writableKeysIt.remove();
/*     */         
/* 246 */         SctpMessage packet = (SctpMessage)in.current();
/* 247 */         if (packet == null) {
/*     */           return;
/*     */         }
/*     */         
/* 251 */         ByteBuf data = packet.content();
/* 252 */         int dataLen = data.readableBytes();
/*     */ 
/*     */         
/* 255 */         if (data.nioBufferCount() != -1) {
/* 256 */           nioData = data.nioBuffer();
/*     */         } else {
/* 258 */           nioData = ByteBuffer.allocate(dataLen);
/* 259 */           data.getBytes(data.readerIndex(), nioData);
/* 260 */           nioData.flip();
/*     */         } 
/*     */         
/* 263 */         MessageInfo mi = MessageInfo.createOutgoing(association(), null, packet.streamIdentifier());
/* 264 */         mi.payloadProtocolID(packet.protocolIdentifier());
/* 265 */         mi.streamNumber(packet.streamIdentifier());
/*     */         
/* 267 */         this.ch.send(nioData, mi);
/* 268 */         written++;
/* 269 */         in.remove();
/*     */       }
/* 271 */       while (writableKeysIt.hasNext());
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) throws Exception {
/* 280 */     if (msg instanceof SctpMessage) {
/* 281 */       return msg;
/*     */     }
/*     */     
/* 284 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Association association() {
/*     */     try {
/* 291 */       return this.ch.association();
/* 292 */     } catch (IOException ignored) {
/* 293 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 299 */     return (isOpen() && association() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*     */     try {
/* 305 */       Iterator<SocketAddress> i = this.ch.getAllLocalAddresses().iterator();
/* 306 */       if (i.hasNext()) {
/* 307 */         return i.next();
/*     */       }
/* 309 */     } catch (IOException e) {}
/*     */ 
/*     */     
/* 312 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<InetSocketAddress> allLocalAddresses() {
/*     */     try {
/* 318 */       Set<SocketAddress> allLocalAddresses = this.ch.getAllLocalAddresses();
/* 319 */       Set<InetSocketAddress> addresses = new LinkedHashSet<InetSocketAddress>(allLocalAddresses.size());
/* 320 */       for (SocketAddress socketAddress : allLocalAddresses) {
/* 321 */         addresses.add((InetSocketAddress)socketAddress);
/*     */       }
/* 323 */       return addresses;
/* 324 */     } catch (Throwable ignored) {
/* 325 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/*     */     try {
/* 332 */       Iterator<SocketAddress> i = this.ch.getRemoteAddresses().iterator();
/* 333 */       if (i.hasNext()) {
/* 334 */         return i.next();
/*     */       }
/* 336 */     } catch (IOException e) {}
/*     */ 
/*     */     
/* 339 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<InetSocketAddress> allRemoteAddresses() {
/*     */     try {
/* 345 */       Set<SocketAddress> allLocalAddresses = this.ch.getRemoteAddresses();
/* 346 */       Set<InetSocketAddress> addresses = new LinkedHashSet<InetSocketAddress>(allLocalAddresses.size());
/* 347 */       for (SocketAddress socketAddress : allLocalAddresses) {
/* 348 */         addresses.add((InetSocketAddress)socketAddress);
/*     */       }
/* 350 */       return addresses;
/* 351 */     } catch (Throwable ignored) {
/* 352 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 358 */     this.ch.bind(localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 364 */     if (localAddress != null) {
/* 365 */       this.ch.bind(localAddress);
/*     */     }
/*     */     
/* 368 */     boolean success = false;
/*     */     try {
/* 370 */       this.ch.connect(remoteAddress);
/* 371 */       boolean finishConnect = false;
/* 372 */       while (!finishConnect) {
/* 373 */         if (this.connectSelector.select(1000L) >= 0) {
/* 374 */           Set<SelectionKey> selectionKeys = this.connectSelector.selectedKeys();
/* 375 */           for (SelectionKey key : selectionKeys) {
/* 376 */             if (key.isConnectable()) {
/* 377 */               selectionKeys.clear();
/* 378 */               finishConnect = true;
/*     */               break;
/*     */             } 
/*     */           } 
/* 382 */           selectionKeys.clear();
/*     */         } 
/*     */       } 
/* 385 */       success = this.ch.finishConnect();
/*     */     } finally {
/* 387 */       if (!success) {
/* 388 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 395 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 400 */     closeSelector("read", this.readSelector);
/* 401 */     closeSelector("write", this.writeSelector);
/* 402 */     closeSelector("connect", this.connectSelector);
/* 403 */     this.ch.close();
/*     */   }
/*     */   
/*     */   private static void closeSelector(String selectorName, Selector selector) {
/*     */     try {
/* 408 */       selector.close();
/* 409 */     } catch (IOException e) {
/* 410 */       logger.warn("Failed to close a " + selectorName + " selector.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bindAddress(InetAddress localAddress) {
/* 416 */     return bindAddress(localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bindAddress(final InetAddress localAddress, final ChannelPromise promise) {
/* 421 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 423 */         this.ch.bindAddress(localAddress);
/* 424 */         promise.setSuccess();
/* 425 */       } catch (Throwable t) {
/* 426 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 429 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 432 */               OioSctpChannel.this.bindAddress(localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 436 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture unbindAddress(InetAddress localAddress) {
/* 441 */     return unbindAddress(localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture unbindAddress(final InetAddress localAddress, final ChannelPromise promise) {
/* 446 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 448 */         this.ch.unbindAddress(localAddress);
/* 449 */         promise.setSuccess();
/* 450 */       } catch (Throwable t) {
/* 451 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 454 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 457 */               OioSctpChannel.this.unbindAddress(localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 461 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   private final class OioSctpChannelConfig extends DefaultSctpChannelConfig {
/*     */     private OioSctpChannelConfig(OioSctpChannel channel, SctpChannel javaChannel) {
/* 466 */       super(channel, javaChannel);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void autoReadCleared() {
/* 471 */       OioSctpChannel.this.setReadPending(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\sctp\oio\OioSctpChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */