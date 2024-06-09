/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.ServerSocketChannelConfig;
/*     */ import io.netty.util.NetUtil;
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
/*     */ public final class EpollServerSocketChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements ServerSocketChannelConfig
/*     */ {
/*     */   private final EpollServerSocketChannel channel;
/*  36 */   private volatile int backlog = NetUtil.SOMAXCONN;
/*     */   
/*     */   EpollServerSocketChannelConfig(EpollServerSocketChannel channel) {
/*  39 */     super((Channel)channel);
/*  40 */     this.channel = channel;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  45 */     setReuseAddress(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  50 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG, EpollChannelOption.SO_REUSEPORT });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  56 */     if (option == ChannelOption.SO_RCVBUF) {
/*  57 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  59 */     if (option == ChannelOption.SO_REUSEADDR) {
/*  60 */       return (T)Boolean.valueOf(isReuseAddress());
/*     */     }
/*  62 */     if (option == ChannelOption.SO_BACKLOG) {
/*  63 */       return (T)Integer.valueOf(getBacklog());
/*     */     }
/*  65 */     if (option == EpollChannelOption.SO_REUSEPORT) {
/*  66 */       return (T)Boolean.valueOf(isReusePort());
/*     */     }
/*  68 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  73 */     validate(option, value);
/*     */     
/*  75 */     if (option == ChannelOption.SO_RCVBUF) {
/*  76 */       setReceiveBufferSize(((Integer)value).intValue());
/*  77 */     } else if (option == ChannelOption.SO_REUSEADDR) {
/*  78 */       setReuseAddress(((Boolean)value).booleanValue());
/*  79 */     } else if (option == ChannelOption.SO_BACKLOG) {
/*  80 */       setBacklog(((Integer)value).intValue());
/*  81 */     } else if (option == EpollChannelOption.SO_REUSEPORT) {
/*  82 */       setReusePort(((Boolean)value).booleanValue());
/*     */     } else {
/*  84 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReuseAddress() {
/*  92 */     return (Native.isReuseAddress(this.channel.fd) == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setReuseAddress(boolean reuseAddress) {
/*  97 */     Native.setReuseAddress(this.channel.fd, reuseAddress ? 1 : 0);
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/* 103 */     return Native.getReceiveBufferSize(this.channel.fd);
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/* 108 */     Native.setReceiveBufferSize(this.channel.fd, receiveBufferSize);
/*     */     
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBacklog() {
/* 120 */     return this.backlog;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setBacklog(int backlog) {
/* 125 */     if (backlog < 0) {
/* 126 */       throw new IllegalArgumentException("backlog: " + backlog);
/*     */     }
/* 128 */     this.backlog = backlog;
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 134 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 140 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 146 */     super.setWriteSpinCount(writeSpinCount);
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 152 */     super.setAllocator(allocator);
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 158 */     super.setRecvByteBufAllocator(allocator);
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setAutoRead(boolean autoRead) {
/* 164 */     super.setAutoRead(autoRead);
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 170 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 176 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 182 */     super.setMessageSizeEstimator(estimator);
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReusePort() {
/* 190 */     return (Native.isReusePort(this.channel.fd) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setReusePort(boolean reusePort) {
/* 201 */     Native.setReusePort(this.channel.fd, reusePort ? 1 : 0);
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void autoReadCleared() {
/* 207 */     this.channel.clearEpollIn();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\epoll\EpollServerSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */