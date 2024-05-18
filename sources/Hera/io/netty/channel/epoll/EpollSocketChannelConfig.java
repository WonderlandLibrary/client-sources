/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ public final class EpollSocketChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements SocketChannelConfig
/*     */ {
/*     */   private final EpollSocketChannel channel;
/*     */   private volatile boolean allowHalfClosure;
/*     */   
/*     */   EpollSocketChannelConfig(EpollSocketChannel channel) {
/*  39 */     super((Channel)channel);
/*     */     
/*  41 */     this.channel = channel;
/*  42 */     if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
/*  43 */       setTcpNoDelay(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  49 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.ALLOW_HALF_CLOSURE, EpollChannelOption.TCP_CORK, EpollChannelOption.TCP_KEEPCNT, EpollChannelOption.TCP_KEEPIDLE, EpollChannelOption.TCP_KEEPINTVL });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  59 */     if (option == ChannelOption.SO_RCVBUF) {
/*  60 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  62 */     if (option == ChannelOption.SO_SNDBUF) {
/*  63 */       return (T)Integer.valueOf(getSendBufferSize());
/*     */     }
/*  65 */     if (option == ChannelOption.TCP_NODELAY) {
/*  66 */       return (T)Boolean.valueOf(isTcpNoDelay());
/*     */     }
/*  68 */     if (option == ChannelOption.SO_KEEPALIVE) {
/*  69 */       return (T)Boolean.valueOf(isKeepAlive());
/*     */     }
/*  71 */     if (option == ChannelOption.SO_REUSEADDR) {
/*  72 */       return (T)Boolean.valueOf(isReuseAddress());
/*     */     }
/*  74 */     if (option == ChannelOption.SO_LINGER) {
/*  75 */       return (T)Integer.valueOf(getSoLinger());
/*     */     }
/*  77 */     if (option == ChannelOption.IP_TOS) {
/*  78 */       return (T)Integer.valueOf(getTrafficClass());
/*     */     }
/*  80 */     if (option == ChannelOption.ALLOW_HALF_CLOSURE) {
/*  81 */       return (T)Boolean.valueOf(isAllowHalfClosure());
/*     */     }
/*  83 */     if (option == EpollChannelOption.TCP_CORK) {
/*  84 */       return (T)Boolean.valueOf(isTcpCork());
/*     */     }
/*  86 */     if (option == EpollChannelOption.TCP_KEEPIDLE) {
/*  87 */       return (T)Integer.valueOf(getTcpKeepIdle());
/*     */     }
/*  89 */     if (option == EpollChannelOption.TCP_KEEPINTVL) {
/*  90 */       return (T)Integer.valueOf(getTcpKeepIntvl());
/*     */     }
/*  92 */     if (option == EpollChannelOption.TCP_KEEPCNT) {
/*  93 */       return (T)Integer.valueOf(getTcpKeepCnt());
/*     */     }
/*  95 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 100 */     validate(option, value);
/*     */     
/* 102 */     if (option == ChannelOption.SO_RCVBUF) {
/* 103 */       setReceiveBufferSize(((Integer)value).intValue());
/* 104 */     } else if (option == ChannelOption.SO_SNDBUF) {
/* 105 */       setSendBufferSize(((Integer)value).intValue());
/* 106 */     } else if (option == ChannelOption.TCP_NODELAY) {
/* 107 */       setTcpNoDelay(((Boolean)value).booleanValue());
/* 108 */     } else if (option == ChannelOption.SO_KEEPALIVE) {
/* 109 */       setKeepAlive(((Boolean)value).booleanValue());
/* 110 */     } else if (option == ChannelOption.SO_REUSEADDR) {
/* 111 */       setReuseAddress(((Boolean)value).booleanValue());
/* 112 */     } else if (option == ChannelOption.SO_LINGER) {
/* 113 */       setSoLinger(((Integer)value).intValue());
/* 114 */     } else if (option == ChannelOption.IP_TOS) {
/* 115 */       setTrafficClass(((Integer)value).intValue());
/* 116 */     } else if (option == ChannelOption.ALLOW_HALF_CLOSURE) {
/* 117 */       setAllowHalfClosure(((Boolean)value).booleanValue());
/* 118 */     } else if (option == EpollChannelOption.TCP_CORK) {
/* 119 */       setTcpCork(((Boolean)value).booleanValue());
/* 120 */     } else if (option == EpollChannelOption.TCP_KEEPIDLE) {
/* 121 */       setTcpKeepIdle(((Integer)value).intValue());
/* 122 */     } else if (option == EpollChannelOption.TCP_KEEPCNT) {
/* 123 */       setTcpKeepCntl(((Integer)value).intValue());
/* 124 */     } else if (option == EpollChannelOption.TCP_KEEPINTVL) {
/* 125 */       setTcpKeepIntvl(((Integer)value).intValue());
/*     */     } else {
/* 127 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/* 135 */     return Native.getReceiveBufferSize(this.channel.fd);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSendBufferSize() {
/* 140 */     return Native.getSendBufferSize(this.channel.fd);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSoLinger() {
/* 145 */     return Native.getSoLinger(this.channel.fd);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTrafficClass() {
/* 150 */     return Native.getTrafficClass(this.channel.fd);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isKeepAlive() {
/* 155 */     return (Native.isKeepAlive(this.channel.fd) == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReuseAddress() {
/* 160 */     return (Native.isReuseAddress(this.channel.fd) == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTcpNoDelay() {
/* 165 */     return (Native.isTcpNoDelay(this.channel.fd) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTcpCork() {
/* 172 */     return (Native.isTcpCork(this.channel.fd) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTcpKeepIdle() {
/* 179 */     return Native.getTcpKeepIdle(this.channel.fd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTcpKeepIntvl() {
/* 186 */     return Native.getTcpKeepIntvl(this.channel.fd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTcpKeepCnt() {
/* 193 */     return Native.getTcpKeepCnt(this.channel.fd);
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setKeepAlive(boolean keepAlive) {
/* 198 */     Native.setKeepAlive(this.channel.fd, keepAlive ? 1 : 0);
/* 199 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
/* 205 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/* 210 */     Native.setReceiveBufferSize(this.channel.fd, receiveBufferSize);
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setReuseAddress(boolean reuseAddress) {
/* 216 */     Native.setReuseAddress(this.channel.fd, reuseAddress ? 1 : 0);
/* 217 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setSendBufferSize(int sendBufferSize) {
/* 222 */     Native.setSendBufferSize(this.channel.fd, sendBufferSize);
/* 223 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setSoLinger(int soLinger) {
/* 228 */     Native.setSoLinger(this.channel.fd, soLinger);
/* 229 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpNoDelay(boolean tcpNoDelay) {
/* 234 */     Native.setTcpNoDelay(this.channel.fd, tcpNoDelay ? 1 : 0);
/* 235 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpCork(boolean tcpCork) {
/* 242 */     Native.setTcpCork(this.channel.fd, tcpCork ? 1 : 0);
/* 243 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTrafficClass(int trafficClass) {
/* 248 */     Native.setTrafficClass(this.channel.fd, trafficClass);
/* 249 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpKeepIdle(int seconds) {
/* 256 */     Native.setTcpKeepIdle(this.channel.fd, seconds);
/* 257 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpKeepIntvl(int seconds) {
/* 264 */     Native.setTcpKeepIntvl(this.channel.fd, seconds);
/* 265 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpKeepCntl(int probes) {
/* 272 */     Native.setTcpKeepCnt(this.channel.fd, probes);
/* 273 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowHalfClosure() {
/* 278 */     return this.allowHalfClosure;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setAllowHalfClosure(boolean allowHalfClosure) {
/* 283 */     this.allowHalfClosure = allowHalfClosure;
/* 284 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 289 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 290 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 295 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 296 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 301 */     super.setWriteSpinCount(writeSpinCount);
/* 302 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 307 */     super.setAllocator(allocator);
/* 308 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 313 */     super.setRecvByteBufAllocator(allocator);
/* 314 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setAutoRead(boolean autoRead) {
/* 319 */     super.setAutoRead(autoRead);
/* 320 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setAutoClose(boolean autoClose) {
/* 325 */     super.setAutoClose(autoClose);
/* 326 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 331 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 332 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 337 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 338 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 343 */     super.setMessageSizeEstimator(estimator);
/* 344 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void autoReadCleared() {
/* 349 */     this.channel.clearEpollIn();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\epoll\EpollSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */