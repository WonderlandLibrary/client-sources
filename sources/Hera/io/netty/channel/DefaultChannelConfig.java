/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import java.util.IdentityHashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultChannelConfig
/*     */   implements ChannelConfig
/*     */ {
/*  33 */   private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = AdaptiveRecvByteBufAllocator.DEFAULT;
/*  34 */   private static final MessageSizeEstimator DEFAULT_MSG_SIZE_ESTIMATOR = DefaultMessageSizeEstimator.DEFAULT;
/*     */   
/*     */   private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
/*     */   
/*     */   protected final Channel channel;
/*     */   
/*  40 */   private volatile ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
/*  41 */   private volatile RecvByteBufAllocator rcvBufAllocator = DEFAULT_RCVBUF_ALLOCATOR;
/*  42 */   private volatile MessageSizeEstimator msgSizeEstimator = DEFAULT_MSG_SIZE_ESTIMATOR;
/*     */   
/*  44 */   private volatile int connectTimeoutMillis = 30000;
/*     */   private volatile int maxMessagesPerRead;
/*  46 */   private volatile int writeSpinCount = 16;
/*     */   private volatile boolean autoRead = true;
/*     */   private volatile boolean autoClose = true;
/*  49 */   private volatile int writeBufferHighWaterMark = 65536;
/*  50 */   private volatile int writeBufferLowWaterMark = 32768;
/*     */   
/*     */   public DefaultChannelConfig(Channel channel) {
/*  53 */     if (channel == null) {
/*  54 */       throw new NullPointerException("channel");
/*     */     }
/*  56 */     this.channel = channel;
/*     */     
/*  58 */     if (channel instanceof ServerChannel || channel instanceof io.netty.channel.nio.AbstractNioByteChannel) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  63 */       this.maxMessagesPerRead = 16;
/*     */     } else {
/*  65 */       this.maxMessagesPerRead = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  72 */     return getOptions(null, (ChannelOption<?>[])new ChannelOption[] { ChannelOption.CONNECT_TIMEOUT_MILLIS, ChannelOption.MAX_MESSAGES_PER_READ, ChannelOption.WRITE_SPIN_COUNT, ChannelOption.ALLOCATOR, ChannelOption.AUTO_READ, ChannelOption.AUTO_CLOSE, ChannelOption.RCVBUF_ALLOCATOR, ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, ChannelOption.MESSAGE_SIZE_ESTIMATOR });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Map<ChannelOption<?>, Object> getOptions(Map<ChannelOption<?>, Object> result, ChannelOption<?>... options) {
/*  81 */     if (result == null) {
/*  82 */       result = new IdentityHashMap<ChannelOption<?>, Object>();
/*     */     }
/*  84 */     for (ChannelOption<?> o : options) {
/*  85 */       result.put(o, getOption(o));
/*     */     }
/*  87 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setOptions(Map<ChannelOption<?>, ?> options) {
/*  93 */     if (options == null) {
/*  94 */       throw new NullPointerException("options");
/*     */     }
/*     */     
/*  97 */     boolean setAllOptions = true;
/*  98 */     for (Map.Entry<ChannelOption<?>, ?> e : options.entrySet()) {
/*  99 */       if (!setOption(e.getKey(), e.getValue())) {
/* 100 */         setAllOptions = false;
/*     */       }
/*     */     } 
/*     */     
/* 104 */     return setAllOptions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/* 110 */     if (option == null) {
/* 111 */       throw new NullPointerException("option");
/*     */     }
/*     */     
/* 114 */     if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
/* 115 */       return (T)Integer.valueOf(getConnectTimeoutMillis());
/*     */     }
/* 117 */     if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
/* 118 */       return (T)Integer.valueOf(getMaxMessagesPerRead());
/*     */     }
/* 120 */     if (option == ChannelOption.WRITE_SPIN_COUNT) {
/* 121 */       return (T)Integer.valueOf(getWriteSpinCount());
/*     */     }
/* 123 */     if (option == ChannelOption.ALLOCATOR) {
/* 124 */       return (T)getAllocator();
/*     */     }
/* 126 */     if (option == ChannelOption.RCVBUF_ALLOCATOR) {
/* 127 */       return (T)getRecvByteBufAllocator();
/*     */     }
/* 129 */     if (option == ChannelOption.AUTO_READ) {
/* 130 */       return (T)Boolean.valueOf(isAutoRead());
/*     */     }
/* 132 */     if (option == ChannelOption.AUTO_CLOSE) {
/* 133 */       return (T)Boolean.valueOf(isAutoClose());
/*     */     }
/* 135 */     if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
/* 136 */       return (T)Integer.valueOf(getWriteBufferHighWaterMark());
/*     */     }
/* 138 */     if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
/* 139 */       return (T)Integer.valueOf(getWriteBufferLowWaterMark());
/*     */     }
/* 141 */     if (option == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
/* 142 */       return (T)getMessageSizeEstimator();
/*     */     }
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 150 */     validate(option, value);
/*     */     
/* 152 */     if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
/* 153 */       setConnectTimeoutMillis(((Integer)value).intValue());
/* 154 */     } else if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
/* 155 */       setMaxMessagesPerRead(((Integer)value).intValue());
/* 156 */     } else if (option == ChannelOption.WRITE_SPIN_COUNT) {
/* 157 */       setWriteSpinCount(((Integer)value).intValue());
/* 158 */     } else if (option == ChannelOption.ALLOCATOR) {
/* 159 */       setAllocator((ByteBufAllocator)value);
/* 160 */     } else if (option == ChannelOption.RCVBUF_ALLOCATOR) {
/* 161 */       setRecvByteBufAllocator((RecvByteBufAllocator)value);
/* 162 */     } else if (option == ChannelOption.AUTO_READ) {
/* 163 */       setAutoRead(((Boolean)value).booleanValue());
/* 164 */     } else if (option == ChannelOption.AUTO_CLOSE) {
/* 165 */       setAutoClose(((Boolean)value).booleanValue());
/* 166 */     } else if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
/* 167 */       setWriteBufferHighWaterMark(((Integer)value).intValue());
/* 168 */     } else if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
/* 169 */       setWriteBufferLowWaterMark(((Integer)value).intValue());
/* 170 */     } else if (option == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
/* 171 */       setMessageSizeEstimator((MessageSizeEstimator)value);
/*     */     } else {
/* 173 */       return false;
/*     */     } 
/*     */     
/* 176 */     return true;
/*     */   }
/*     */   
/*     */   protected <T> void validate(ChannelOption<T> option, T value) {
/* 180 */     if (option == null) {
/* 181 */       throw new NullPointerException("option");
/*     */     }
/* 183 */     option.validate(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getConnectTimeoutMillis() {
/* 188 */     return this.connectTimeoutMillis;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 193 */     if (connectTimeoutMillis < 0) {
/* 194 */       throw new IllegalArgumentException(String.format("connectTimeoutMillis: %d (expected: >= 0)", new Object[] { Integer.valueOf(connectTimeoutMillis) }));
/*     */     }
/*     */     
/* 197 */     this.connectTimeoutMillis = connectTimeoutMillis;
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxMessagesPerRead() {
/* 203 */     return this.maxMessagesPerRead;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 208 */     if (maxMessagesPerRead <= 0) {
/* 209 */       throw new IllegalArgumentException("maxMessagesPerRead: " + maxMessagesPerRead + " (expected: > 0)");
/*     */     }
/* 211 */     this.maxMessagesPerRead = maxMessagesPerRead;
/* 212 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWriteSpinCount() {
/* 217 */     return this.writeSpinCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 222 */     if (writeSpinCount <= 0) {
/* 223 */       throw new IllegalArgumentException("writeSpinCount must be a positive integer.");
/*     */     }
/*     */     
/* 226 */     this.writeSpinCount = writeSpinCount;
/* 227 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator getAllocator() {
/* 232 */     return this.allocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 237 */     if (allocator == null) {
/* 238 */       throw new NullPointerException("allocator");
/*     */     }
/* 240 */     this.allocator = allocator;
/* 241 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecvByteBufAllocator getRecvByteBufAllocator() {
/* 246 */     return this.rcvBufAllocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 251 */     if (allocator == null) {
/* 252 */       throw new NullPointerException("allocator");
/*     */     }
/* 254 */     this.rcvBufAllocator = allocator;
/* 255 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAutoRead() {
/* 260 */     return this.autoRead;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setAutoRead(boolean autoRead) {
/* 265 */     boolean oldAutoRead = this.autoRead;
/* 266 */     this.autoRead = autoRead;
/* 267 */     if (autoRead && !oldAutoRead) {
/* 268 */       this.channel.read();
/* 269 */     } else if (!autoRead && oldAutoRead) {
/* 270 */       autoReadCleared();
/*     */     } 
/* 272 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void autoReadCleared() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoClose() {
/* 283 */     return this.autoClose;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setAutoClose(boolean autoClose) {
/* 288 */     this.autoClose = autoClose;
/* 289 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWriteBufferHighWaterMark() {
/* 294 */     return this.writeBufferHighWaterMark;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 299 */     if (writeBufferHighWaterMark < getWriteBufferLowWaterMark()) {
/* 300 */       throw new IllegalArgumentException("writeBufferHighWaterMark cannot be less than writeBufferLowWaterMark (" + getWriteBufferLowWaterMark() + "): " + writeBufferHighWaterMark);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 305 */     if (writeBufferHighWaterMark < 0) {
/* 306 */       throw new IllegalArgumentException("writeBufferHighWaterMark must be >= 0");
/*     */     }
/*     */     
/* 309 */     this.writeBufferHighWaterMark = writeBufferHighWaterMark;
/* 310 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWriteBufferLowWaterMark() {
/* 315 */     return this.writeBufferLowWaterMark;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 320 */     if (writeBufferLowWaterMark > getWriteBufferHighWaterMark()) {
/* 321 */       throw new IllegalArgumentException("writeBufferLowWaterMark cannot be greater than writeBufferHighWaterMark (" + getWriteBufferHighWaterMark() + "): " + writeBufferLowWaterMark);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 326 */     if (writeBufferLowWaterMark < 0) {
/* 327 */       throw new IllegalArgumentException("writeBufferLowWaterMark must be >= 0");
/*     */     }
/*     */     
/* 330 */     this.writeBufferLowWaterMark = writeBufferLowWaterMark;
/* 331 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageSizeEstimator getMessageSizeEstimator() {
/* 336 */     return this.msgSizeEstimator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 341 */     if (estimator == null) {
/* 342 */       throw new NullPointerException("estimator");
/*     */     }
/* 344 */     this.msgSizeEstimator = estimator;
/* 345 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\DefaultChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */