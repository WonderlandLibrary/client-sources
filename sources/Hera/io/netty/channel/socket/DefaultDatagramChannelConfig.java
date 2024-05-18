/*     */ package io.netty.channel.socket;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.FixedRecvByteBufAllocator;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketException;
/*     */ import java.util.Map;
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
/*     */ public class DefaultDatagramChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements DatagramChannelConfig
/*     */ {
/*  44 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultDatagramChannelConfig.class);
/*     */   
/*  46 */   private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = (RecvByteBufAllocator)new FixedRecvByteBufAllocator(2048);
/*     */ 
/*     */   
/*     */   private final DatagramSocket javaSocket;
/*     */   
/*     */   private volatile boolean activeOnOpen;
/*     */ 
/*     */   
/*     */   public DefaultDatagramChannelConfig(DatagramChannel channel, DatagramSocket javaSocket) {
/*  55 */     super(channel);
/*  56 */     if (javaSocket == null) {
/*  57 */       throw new NullPointerException("javaSocket");
/*     */     }
/*  59 */     this.javaSocket = javaSocket;
/*  60 */     setRecvByteBufAllocator(DEFAULT_RCVBUF_ALLOCATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  66 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  75 */     if (option == ChannelOption.SO_BROADCAST) {
/*  76 */       return (T)Boolean.valueOf(isBroadcast());
/*     */     }
/*  78 */     if (option == ChannelOption.SO_RCVBUF) {
/*  79 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  81 */     if (option == ChannelOption.SO_SNDBUF) {
/*  82 */       return (T)Integer.valueOf(getSendBufferSize());
/*     */     }
/*  84 */     if (option == ChannelOption.SO_REUSEADDR) {
/*  85 */       return (T)Boolean.valueOf(isReuseAddress());
/*     */     }
/*  87 */     if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
/*  88 */       return (T)Boolean.valueOf(isLoopbackModeDisabled());
/*     */     }
/*  90 */     if (option == ChannelOption.IP_MULTICAST_ADDR) {
/*  91 */       return (T)getInterface();
/*     */     }
/*  93 */     if (option == ChannelOption.IP_MULTICAST_IF) {
/*  94 */       return (T)getNetworkInterface();
/*     */     }
/*  96 */     if (option == ChannelOption.IP_MULTICAST_TTL) {
/*  97 */       return (T)Integer.valueOf(getTimeToLive());
/*     */     }
/*  99 */     if (option == ChannelOption.IP_TOS) {
/* 100 */       return (T)Integer.valueOf(getTrafficClass());
/*     */     }
/* 102 */     if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/* 103 */       return (T)Boolean.valueOf(this.activeOnOpen);
/*     */     }
/* 105 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 111 */     validate(option, value);
/*     */     
/* 113 */     if (option == ChannelOption.SO_BROADCAST) {
/* 114 */       setBroadcast(((Boolean)value).booleanValue());
/* 115 */     } else if (option == ChannelOption.SO_RCVBUF) {
/* 116 */       setReceiveBufferSize(((Integer)value).intValue());
/* 117 */     } else if (option == ChannelOption.SO_SNDBUF) {
/* 118 */       setSendBufferSize(((Integer)value).intValue());
/* 119 */     } else if (option == ChannelOption.SO_REUSEADDR) {
/* 120 */       setReuseAddress(((Boolean)value).booleanValue());
/* 121 */     } else if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
/* 122 */       setLoopbackModeDisabled(((Boolean)value).booleanValue());
/* 123 */     } else if (option == ChannelOption.IP_MULTICAST_ADDR) {
/* 124 */       setInterface((InetAddress)value);
/* 125 */     } else if (option == ChannelOption.IP_MULTICAST_IF) {
/* 126 */       setNetworkInterface((NetworkInterface)value);
/* 127 */     } else if (option == ChannelOption.IP_MULTICAST_TTL) {
/* 128 */       setTimeToLive(((Integer)value).intValue());
/* 129 */     } else if (option == ChannelOption.IP_TOS) {
/* 130 */       setTrafficClass(((Integer)value).intValue());
/* 131 */     } else if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/* 132 */       setActiveOnOpen(((Boolean)value).booleanValue());
/*     */     } else {
/* 134 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/* 137 */     return true;
/*     */   }
/*     */   
/*     */   private void setActiveOnOpen(boolean activeOnOpen) {
/* 141 */     if (this.channel.isRegistered()) {
/* 142 */       throw new IllegalStateException("Can only changed before channel was registered");
/*     */     }
/* 144 */     this.activeOnOpen = activeOnOpen;
/*     */   }
/*     */   
/*     */   public boolean isBroadcast() {
/*     */     try {
/* 149 */       return this.javaSocket.getBroadcast();
/* 150 */     } catch (SocketException e) {
/* 151 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setBroadcast(boolean broadcast) {
/*     */     try {
/* 159 */       if (broadcast && !PlatformDependent.isWindows() && !PlatformDependent.isRoot() && !this.javaSocket.getLocalAddress().isAnyLocalAddress())
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 164 */         logger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; setting the SO_BROADCAST flag anyway as requested on the socket which is bound to " + this.javaSocket.getLocalSocketAddress() + '.');
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 171 */       this.javaSocket.setBroadcast(broadcast);
/* 172 */     } catch (SocketException e) {
/* 173 */       throw new ChannelException(e);
/*     */     } 
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetAddress getInterface() {
/* 180 */     if (this.javaSocket instanceof MulticastSocket) {
/*     */       try {
/* 182 */         return ((MulticastSocket)this.javaSocket).getInterface();
/* 183 */       } catch (SocketException e) {
/* 184 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 187 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setInterface(InetAddress interfaceAddress) {
/* 193 */     if (this.javaSocket instanceof MulticastSocket) {
/*     */       try {
/* 195 */         ((MulticastSocket)this.javaSocket).setInterface(interfaceAddress);
/* 196 */       } catch (SocketException e) {
/* 197 */         throw new ChannelException(e);
/*     */       } 
/*     */     } else {
/* 200 */       throw new UnsupportedOperationException();
/*     */     } 
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLoopbackModeDisabled() {
/* 207 */     if (this.javaSocket instanceof MulticastSocket) {
/*     */       try {
/* 209 */         return ((MulticastSocket)this.javaSocket).getLoopbackMode();
/* 210 */       } catch (SocketException e) {
/* 211 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 214 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
/* 220 */     if (this.javaSocket instanceof MulticastSocket) {
/*     */       try {
/* 222 */         ((MulticastSocket)this.javaSocket).setLoopbackMode(loopbackModeDisabled);
/* 223 */       } catch (SocketException e) {
/* 224 */         throw new ChannelException(e);
/*     */       } 
/*     */     } else {
/* 227 */       throw new UnsupportedOperationException();
/*     */     } 
/* 229 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public NetworkInterface getNetworkInterface() {
/* 234 */     if (this.javaSocket instanceof MulticastSocket) {
/*     */       try {
/* 236 */         return ((MulticastSocket)this.javaSocket).getNetworkInterface();
/* 237 */       } catch (SocketException e) {
/* 238 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 241 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
/* 247 */     if (this.javaSocket instanceof MulticastSocket) {
/*     */       try {
/* 249 */         ((MulticastSocket)this.javaSocket).setNetworkInterface(networkInterface);
/* 250 */       } catch (SocketException e) {
/* 251 */         throw new ChannelException(e);
/*     */       } 
/*     */     } else {
/* 254 */       throw new UnsupportedOperationException();
/*     */     } 
/* 256 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReuseAddress() {
/*     */     try {
/* 262 */       return this.javaSocket.getReuseAddress();
/* 263 */     } catch (SocketException e) {
/* 264 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setReuseAddress(boolean reuseAddress) {
/*     */     try {
/* 271 */       this.javaSocket.setReuseAddress(reuseAddress);
/* 272 */     } catch (SocketException e) {
/* 273 */       throw new ChannelException(e);
/*     */     } 
/* 275 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/*     */     try {
/* 281 */       return this.javaSocket.getReceiveBufferSize();
/* 282 */     } catch (SocketException e) {
/* 283 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */     try {
/* 290 */       this.javaSocket.setReceiveBufferSize(receiveBufferSize);
/* 291 */     } catch (SocketException e) {
/* 292 */       throw new ChannelException(e);
/*     */     } 
/* 294 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSendBufferSize() {
/*     */     try {
/* 300 */       return this.javaSocket.getSendBufferSize();
/* 301 */     } catch (SocketException e) {
/* 302 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setSendBufferSize(int sendBufferSize) {
/*     */     try {
/* 309 */       this.javaSocket.setSendBufferSize(sendBufferSize);
/* 310 */     } catch (SocketException e) {
/* 311 */       throw new ChannelException(e);
/*     */     } 
/* 313 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTimeToLive() {
/* 318 */     if (this.javaSocket instanceof MulticastSocket) {
/*     */       try {
/* 320 */         return ((MulticastSocket)this.javaSocket).getTimeToLive();
/* 321 */       } catch (IOException e) {
/* 322 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 325 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setTimeToLive(int ttl) {
/* 331 */     if (this.javaSocket instanceof MulticastSocket) {
/*     */       try {
/* 333 */         ((MulticastSocket)this.javaSocket).setTimeToLive(ttl);
/* 334 */       } catch (IOException e) {
/* 335 */         throw new ChannelException(e);
/*     */       } 
/*     */     } else {
/* 338 */       throw new UnsupportedOperationException();
/*     */     } 
/* 340 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTrafficClass() {
/*     */     try {
/* 346 */       return this.javaSocket.getTrafficClass();
/* 347 */     } catch (SocketException e) {
/* 348 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setTrafficClass(int trafficClass) {
/*     */     try {
/* 355 */       this.javaSocket.setTrafficClass(trafficClass);
/* 356 */     } catch (SocketException e) {
/* 357 */       throw new ChannelException(e);
/*     */     } 
/* 359 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 364 */     super.setWriteSpinCount(writeSpinCount);
/* 365 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 370 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 371 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 376 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 377 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 382 */     super.setAllocator(allocator);
/* 383 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 388 */     super.setRecvByteBufAllocator(allocator);
/* 389 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setAutoRead(boolean autoRead) {
/* 394 */     super.setAutoRead(autoRead);
/* 395 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setAutoClose(boolean autoClose) {
/* 400 */     super.setAutoClose(autoClose);
/* 401 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 406 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 407 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 412 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 413 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 418 */     super.setMessageSizeEstimator(estimator);
/* 419 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\socket\DefaultDatagramChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */