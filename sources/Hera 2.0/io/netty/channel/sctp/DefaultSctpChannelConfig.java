/*     */ package io.netty.channel.sctp;
/*     */ 
/*     */ import com.sun.nio.sctp.SctpChannel;
/*     */ import com.sun.nio.sctp.SctpStandardSocketOptions;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ public class DefaultSctpChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements SctpChannelConfig
/*     */ {
/*     */   private final SctpChannel javaChannel;
/*     */   
/*     */   public DefaultSctpChannelConfig(SctpChannel channel, SctpChannel javaChannel) {
/*  42 */     super(channel);
/*  43 */     if (javaChannel == null) {
/*  44 */       throw new NullPointerException("javaChannel");
/*     */     }
/*  46 */     this.javaChannel = javaChannel;
/*     */ 
/*     */     
/*  49 */     if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
/*     */       try {
/*  51 */         setSctpNoDelay(true);
/*  52 */       } catch (Exception e) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  60 */     return getOptions(super.getOptions(), new ChannelOption[] { SctpChannelOption.SO_RCVBUF, SctpChannelOption.SO_SNDBUF, SctpChannelOption.SCTP_NODELAY, SctpChannelOption.SCTP_INIT_MAXSTREAMS });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  68 */     if (option == SctpChannelOption.SO_RCVBUF) {
/*  69 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  71 */     if (option == SctpChannelOption.SO_SNDBUF) {
/*  72 */       return (T)Integer.valueOf(getSendBufferSize());
/*     */     }
/*  74 */     if (option == SctpChannelOption.SCTP_NODELAY) {
/*  75 */       return (T)Boolean.valueOf(isSctpNoDelay());
/*     */     }
/*  77 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  82 */     validate(option, value);
/*     */     
/*  84 */     if (option == SctpChannelOption.SO_RCVBUF) {
/*  85 */       setReceiveBufferSize(((Integer)value).intValue());
/*  86 */     } else if (option == SctpChannelOption.SO_SNDBUF) {
/*  87 */       setSendBufferSize(((Integer)value).intValue());
/*  88 */     } else if (option == SctpChannelOption.SCTP_NODELAY) {
/*  89 */       setSctpNoDelay(((Boolean)value).booleanValue());
/*  90 */     } else if (option == SctpChannelOption.SCTP_INIT_MAXSTREAMS) {
/*  91 */       setInitMaxStreams((SctpStandardSocketOptions.InitMaxStreams)value);
/*     */     } else {
/*  93 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSctpNoDelay() {
/*     */     try {
/* 102 */       return ((Boolean)this.javaChannel.<Boolean>getOption(SctpStandardSocketOptions.SCTP_NODELAY)).booleanValue();
/* 103 */     } catch (IOException e) {
/* 104 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setSctpNoDelay(boolean sctpNoDelay) {
/*     */     try {
/* 111 */       this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_NODELAY, Boolean.valueOf(sctpNoDelay));
/* 112 */     } catch (IOException e) {
/* 113 */       throw new ChannelException(e);
/*     */     } 
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSendBufferSize() {
/*     */     try {
/* 121 */       return ((Integer)this.javaChannel.<Integer>getOption(SctpStandardSocketOptions.SO_SNDBUF)).intValue();
/* 122 */     } catch (IOException e) {
/* 123 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setSendBufferSize(int sendBufferSize) {
/*     */     try {
/* 130 */       this.javaChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, Integer.valueOf(sendBufferSize));
/* 131 */     } catch (IOException e) {
/* 132 */       throw new ChannelException(e);
/*     */     } 
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/*     */     try {
/* 140 */       return ((Integer)this.javaChannel.<Integer>getOption(SctpStandardSocketOptions.SO_RCVBUF)).intValue();
/* 141 */     } catch (IOException e) {
/* 142 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */     try {
/* 149 */       this.javaChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, Integer.valueOf(receiveBufferSize));
/* 150 */     } catch (IOException e) {
/* 151 */       throw new ChannelException(e);
/*     */     } 
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpStandardSocketOptions.InitMaxStreams getInitMaxStreams() {
/*     */     try {
/* 159 */       return this.javaChannel.<SctpStandardSocketOptions.InitMaxStreams>getOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS);
/* 160 */     } catch (IOException e) {
/* 161 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setInitMaxStreams(SctpStandardSocketOptions.InitMaxStreams initMaxStreams) {
/*     */     try {
/* 168 */       this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, initMaxStreams);
/* 169 */     } catch (IOException e) {
/* 170 */       throw new ChannelException(e);
/*     */     } 
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 177 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 178 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 183 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 189 */     super.setWriteSpinCount(writeSpinCount);
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 195 */     super.setAllocator(allocator);
/* 196 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 201 */     super.setRecvByteBufAllocator(allocator);
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setAutoRead(boolean autoRead) {
/* 207 */     super.setAutoRead(autoRead);
/* 208 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setAutoClose(boolean autoClose) {
/* 213 */     super.setAutoClose(autoClose);
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 219 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 225 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 231 */     super.setMessageSizeEstimator(estimator);
/* 232 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\sctp\DefaultSctpChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */