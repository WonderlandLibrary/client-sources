/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.FixedRecvByteBufAllocator;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
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
/*     */ public final class EpollDatagramChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements DatagramChannelConfig
/*     */ {
/*  31 */   private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = (RecvByteBufAllocator)new FixedRecvByteBufAllocator(2048);
/*     */   private final EpollDatagramChannel datagramChannel;
/*     */   private boolean activeOnOpen;
/*     */   
/*     */   EpollDatagramChannelConfig(EpollDatagramChannel channel) {
/*  36 */     super((Channel)channel);
/*  37 */     this.datagramChannel = channel;
/*  38 */     setRecvByteBufAllocator(DEFAULT_RCVBUF_ALLOCATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  44 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION, EpollChannelOption.SO_REUSEPORT });
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
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  56 */     if (option == ChannelOption.SO_BROADCAST) {
/*  57 */       return (T)Boolean.valueOf(isBroadcast());
/*     */     }
/*  59 */     if (option == ChannelOption.SO_RCVBUF) {
/*  60 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  62 */     if (option == ChannelOption.SO_SNDBUF) {
/*  63 */       return (T)Integer.valueOf(getSendBufferSize());
/*     */     }
/*  65 */     if (option == ChannelOption.SO_REUSEADDR) {
/*  66 */       return (T)Boolean.valueOf(isReuseAddress());
/*     */     }
/*  68 */     if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
/*  69 */       return (T)Boolean.valueOf(isLoopbackModeDisabled());
/*     */     }
/*  71 */     if (option == ChannelOption.IP_MULTICAST_ADDR) {
/*  72 */       return (T)getInterface();
/*     */     }
/*  74 */     if (option == ChannelOption.IP_MULTICAST_IF) {
/*  75 */       return (T)getNetworkInterface();
/*     */     }
/*  77 */     if (option == ChannelOption.IP_MULTICAST_TTL) {
/*  78 */       return (T)Integer.valueOf(getTimeToLive());
/*     */     }
/*  80 */     if (option == ChannelOption.IP_TOS) {
/*  81 */       return (T)Integer.valueOf(getTrafficClass());
/*     */     }
/*  83 */     if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/*  84 */       return (T)Boolean.valueOf(this.activeOnOpen);
/*     */     }
/*  86 */     if (option == EpollChannelOption.SO_REUSEPORT) {
/*  87 */       return (T)Boolean.valueOf(isReusePort());
/*     */     }
/*  89 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  95 */     validate(option, value);
/*     */     
/*  97 */     if (option == ChannelOption.SO_BROADCAST) {
/*  98 */       setBroadcast(((Boolean)value).booleanValue());
/*  99 */     } else if (option == ChannelOption.SO_RCVBUF) {
/* 100 */       setReceiveBufferSize(((Integer)value).intValue());
/* 101 */     } else if (option == ChannelOption.SO_SNDBUF) {
/* 102 */       setSendBufferSize(((Integer)value).intValue());
/* 103 */     } else if (option == ChannelOption.SO_REUSEADDR) {
/* 104 */       setReuseAddress(((Boolean)value).booleanValue());
/* 105 */     } else if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
/* 106 */       setLoopbackModeDisabled(((Boolean)value).booleanValue());
/* 107 */     } else if (option == ChannelOption.IP_MULTICAST_ADDR) {
/* 108 */       setInterface((InetAddress)value);
/* 109 */     } else if (option == ChannelOption.IP_MULTICAST_IF) {
/* 110 */       setNetworkInterface((NetworkInterface)value);
/* 111 */     } else if (option == ChannelOption.IP_MULTICAST_TTL) {
/* 112 */       setTimeToLive(((Integer)value).intValue());
/* 113 */     } else if (option == ChannelOption.IP_TOS) {
/* 114 */       setTrafficClass(((Integer)value).intValue());
/* 115 */     } else if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/* 116 */       setActiveOnOpen(((Boolean)value).booleanValue());
/* 117 */     } else if (option == EpollChannelOption.SO_REUSEPORT) {
/* 118 */       setReusePort(((Boolean)value).booleanValue());
/*     */     } else {
/* 120 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/* 123 */     return true;
/*     */   }
/*     */   
/*     */   private void setActiveOnOpen(boolean activeOnOpen) {
/* 127 */     if (this.channel.isRegistered()) {
/* 128 */       throw new IllegalStateException("Can only changed before channel was registered");
/*     */     }
/* 130 */     this.activeOnOpen = activeOnOpen;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 135 */     super.setMessageSizeEstimator(estimator);
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 141 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 147 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setAutoClose(boolean autoClose) {
/* 153 */     super.setAutoClose(autoClose);
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setAutoRead(boolean autoRead) {
/* 159 */     super.setAutoRead(autoRead);
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 165 */     super.setRecvByteBufAllocator(allocator);
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 171 */     super.setWriteSpinCount(writeSpinCount);
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 177 */     super.setAllocator(allocator);
/* 178 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 183 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 189 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSendBufferSize() {
/* 195 */     return Native.getSendBufferSize(this.datagramChannel.fd);
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setSendBufferSize(int sendBufferSize) {
/* 200 */     Native.setSendBufferSize(this.datagramChannel.fd, sendBufferSize);
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/* 206 */     return Native.getReceiveBufferSize(this.datagramChannel.fd);
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/* 211 */     Native.setReceiveBufferSize(this.datagramChannel.fd, receiveBufferSize);
/* 212 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTrafficClass() {
/* 217 */     return Native.getTrafficClass(this.datagramChannel.fd);
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setTrafficClass(int trafficClass) {
/* 222 */     Native.setTrafficClass(this.datagramChannel.fd, trafficClass);
/* 223 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReuseAddress() {
/* 228 */     return (Native.isReuseAddress(this.datagramChannel.fd) == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setReuseAddress(boolean reuseAddress) {
/* 233 */     Native.setReuseAddress(this.datagramChannel.fd, reuseAddress ? 1 : 0);
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBroadcast() {
/* 239 */     return (Native.isBroadcast(this.datagramChannel.fd) == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setBroadcast(boolean broadcast) {
/* 244 */     Native.setBroadcast(this.datagramChannel.fd, broadcast ? 1 : 0);
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLoopbackModeDisabled() {
/* 250 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
/* 255 */     throw new UnsupportedOperationException("Multicast not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTimeToLive() {
/* 260 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setTimeToLive(int ttl) {
/* 265 */     throw new UnsupportedOperationException("Multicast not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public InetAddress getInterface() {
/* 270 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setInterface(InetAddress interfaceAddress) {
/* 275 */     throw new UnsupportedOperationException("Multicast not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public NetworkInterface getNetworkInterface() {
/* 280 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
/* 285 */     throw new UnsupportedOperationException("Multicast not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReusePort() {
/* 292 */     return (Native.isReusePort(this.datagramChannel.fd) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setReusePort(boolean reusePort) {
/* 303 */     Native.setReusePort(this.datagramChannel.fd, reusePort ? 1 : 0);
/* 304 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void autoReadCleared() {
/* 309 */     this.datagramChannel.clearEpollIn();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\epoll\EpollDatagramChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */