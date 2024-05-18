/*     */ package io.netty.channel.udt;
/*     */ 
/*     */ import com.barchart.udt.OptionUDT;
/*     */ import com.barchart.udt.SocketUDT;
/*     */ import com.barchart.udt.nio.ChannelUDT;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
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
/*     */ 
/*     */ public class DefaultUdtChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements UdtChannelConfig
/*     */ {
/*     */   private static final int K = 1024;
/*     */   private static final int M = 1048576;
/*  41 */   private volatile int protocolReceiveBuferSize = 10485760;
/*  42 */   private volatile int protocolSendBuferSize = 10485760;
/*     */   
/*  44 */   private volatile int systemReceiveBufferSize = 1048576;
/*  45 */   private volatile int systemSendBuferSize = 1048576;
/*     */   
/*  47 */   private volatile int allocatorReceiveBufferSize = 131072;
/*  48 */   private volatile int allocatorSendBufferSize = 131072;
/*     */ 
/*     */   
/*     */   private volatile int soLinger;
/*     */   
/*     */   private volatile boolean reuseAddress = true;
/*     */ 
/*     */   
/*     */   public DefaultUdtChannelConfig(UdtChannel channel, ChannelUDT channelUDT, boolean apply) throws IOException {
/*  57 */     super(channel);
/*  58 */     if (apply) {
/*  59 */       apply(channelUDT);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void apply(ChannelUDT channelUDT) throws IOException {
/*  64 */     SocketUDT socketUDT = channelUDT.socketUDT();
/*  65 */     socketUDT.setReuseAddress(isReuseAddress());
/*  66 */     socketUDT.setSendBufferSize(getSendBufferSize());
/*  67 */     if (getSoLinger() <= 0) {
/*  68 */       socketUDT.setSoLinger(false, 0);
/*     */     } else {
/*  70 */       socketUDT.setSoLinger(true, getSoLinger());
/*     */     } 
/*  72 */     socketUDT.setOption(OptionUDT.Protocol_Receive_Buffer_Size, Integer.valueOf(getProtocolReceiveBufferSize()));
/*     */     
/*  74 */     socketUDT.setOption(OptionUDT.Protocol_Send_Buffer_Size, Integer.valueOf(getProtocolSendBufferSize()));
/*     */     
/*  76 */     socketUDT.setOption(OptionUDT.System_Receive_Buffer_Size, Integer.valueOf(getSystemReceiveBufferSize()));
/*     */     
/*  78 */     socketUDT.setOption(OptionUDT.System_Send_Buffer_Size, Integer.valueOf(getSystemSendBufferSize()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProtocolReceiveBufferSize() {
/*  84 */     return this.protocolReceiveBuferSize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  90 */     if (option == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
/*  91 */       return (T)Integer.valueOf(getProtocolReceiveBufferSize());
/*     */     }
/*  93 */     if (option == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
/*  94 */       return (T)Integer.valueOf(getProtocolSendBufferSize());
/*     */     }
/*  96 */     if (option == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
/*  97 */       return (T)Integer.valueOf(getSystemReceiveBufferSize());
/*     */     }
/*  99 */     if (option == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
/* 100 */       return (T)Integer.valueOf(getSystemSendBufferSize());
/*     */     }
/* 102 */     if (option == UdtChannelOption.SO_RCVBUF) {
/* 103 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/* 105 */     if (option == UdtChannelOption.SO_SNDBUF) {
/* 106 */       return (T)Integer.valueOf(getSendBufferSize());
/*     */     }
/* 108 */     if (option == UdtChannelOption.SO_REUSEADDR) {
/* 109 */       return (T)Boolean.valueOf(isReuseAddress());
/*     */     }
/* 111 */     if (option == UdtChannelOption.SO_LINGER) {
/* 112 */       return (T)Integer.valueOf(getSoLinger());
/*     */     }
/* 114 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/* 119 */     return getOptions(super.getOptions(), new ChannelOption[] { UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE, UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE, UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE, UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE, UdtChannelOption.SO_RCVBUF, UdtChannelOption.SO_SNDBUF, UdtChannelOption.SO_REUSEADDR, UdtChannelOption.SO_LINGER });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/* 127 */     return this.allocatorReceiveBufferSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSendBufferSize() {
/* 132 */     return this.allocatorSendBufferSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSoLinger() {
/* 137 */     return this.soLinger;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReuseAddress() {
/* 142 */     return this.reuseAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setProtocolReceiveBufferSize(int protocolReceiveBuferSize) {
/* 147 */     this.protocolReceiveBuferSize = protocolReceiveBuferSize;
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 153 */     validate(option, value);
/* 154 */     if (option == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
/* 155 */       setProtocolReceiveBufferSize(((Integer)value).intValue());
/* 156 */     } else if (option == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
/* 157 */       setProtocolSendBufferSize(((Integer)value).intValue());
/* 158 */     } else if (option == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
/* 159 */       setSystemReceiveBufferSize(((Integer)value).intValue());
/* 160 */     } else if (option == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
/* 161 */       setSystemSendBufferSize(((Integer)value).intValue());
/* 162 */     } else if (option == UdtChannelOption.SO_RCVBUF) {
/* 163 */       setReceiveBufferSize(((Integer)value).intValue());
/* 164 */     } else if (option == UdtChannelOption.SO_SNDBUF) {
/* 165 */       setSendBufferSize(((Integer)value).intValue());
/* 166 */     } else if (option == UdtChannelOption.SO_REUSEADDR) {
/* 167 */       setReuseAddress(((Boolean)value).booleanValue());
/* 168 */     } else if (option == UdtChannelOption.SO_LINGER) {
/* 169 */       setSoLinger(((Integer)value).intValue());
/*     */     } else {
/* 171 */       return super.setOption(option, value);
/*     */     } 
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/* 178 */     this.allocatorReceiveBufferSize = receiveBufferSize;
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setReuseAddress(boolean reuseAddress) {
/* 184 */     this.reuseAddress = reuseAddress;
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setSendBufferSize(int sendBufferSize) {
/* 190 */     this.allocatorSendBufferSize = sendBufferSize;
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setSoLinger(int soLinger) {
/* 196 */     this.soLinger = soLinger;
/* 197 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSystemReceiveBufferSize() {
/* 202 */     return this.systemReceiveBufferSize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setSystemSendBufferSize(int systemReceiveBufferSize) {
/* 208 */     this.systemReceiveBufferSize = systemReceiveBufferSize;
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getProtocolSendBufferSize() {
/* 214 */     return this.protocolSendBuferSize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setProtocolSendBufferSize(int protocolSendBuferSize) {
/* 220 */     this.protocolSendBuferSize = protocolSendBuferSize;
/* 221 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setSystemReceiveBufferSize(int systemSendBuferSize) {
/* 227 */     this.systemSendBuferSize = systemSendBuferSize;
/* 228 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSystemSendBufferSize() {
/* 233 */     return this.systemSendBuferSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 238 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 239 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 244 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 250 */     super.setWriteSpinCount(writeSpinCount);
/* 251 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 256 */     super.setAllocator(allocator);
/* 257 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 262 */     super.setRecvByteBufAllocator(allocator);
/* 263 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setAutoRead(boolean autoRead) {
/* 268 */     super.setAutoRead(autoRead);
/* 269 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setAutoClose(boolean autoClose) {
/* 274 */     super.setAutoClose(autoClose);
/* 275 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 280 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 281 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 286 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 287 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 292 */     super.setMessageSizeEstimator(estimator);
/* 293 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channe\\udt\DefaultUdtChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */