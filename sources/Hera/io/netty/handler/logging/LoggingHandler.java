/*     */ package io.netty.handler.logging;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogLevel;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.SocketAddress;
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
/*     */ @Sharable
/*     */ public class LoggingHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  39 */   private static final LogLevel DEFAULT_LEVEL = LogLevel.DEBUG;
/*     */   
/*  41 */   private static final String NEWLINE = StringUtil.NEWLINE;
/*     */   
/*  43 */   private static final String[] BYTE2HEX = new String[256];
/*  44 */   private static final String[] HEXPADDING = new String[16];
/*  45 */   private static final String[] BYTEPADDING = new String[16];
/*  46 */   private static final char[] BYTE2CHAR = new char[256];
/*     */   
/*     */   protected final InternalLogger logger;
/*     */   
/*     */   static {
/*     */     int i;
/*  52 */     for (i = 0; i < BYTE2HEX.length; i++) {
/*  53 */       BYTE2HEX[i] = ' ' + StringUtil.byteToHexStringPadded(i);
/*     */     }
/*     */ 
/*     */     
/*  57 */     for (i = 0; i < HEXPADDING.length; i++) {
/*  58 */       int padding = HEXPADDING.length - i;
/*  59 */       StringBuilder buf = new StringBuilder(padding * 3);
/*  60 */       for (int j = 0; j < padding; j++) {
/*  61 */         buf.append("   ");
/*     */       }
/*  63 */       HEXPADDING[i] = buf.toString();
/*     */     } 
/*     */ 
/*     */     
/*  67 */     for (i = 0; i < BYTEPADDING.length; i++) {
/*  68 */       int padding = BYTEPADDING.length - i;
/*  69 */       StringBuilder buf = new StringBuilder(padding);
/*  70 */       for (int j = 0; j < padding; j++) {
/*  71 */         buf.append(' ');
/*     */       }
/*  73 */       BYTEPADDING[i] = buf.toString();
/*     */     } 
/*     */ 
/*     */     
/*  77 */     for (i = 0; i < BYTE2CHAR.length; i++) {
/*  78 */       if (i <= 31 || i >= 127) {
/*  79 */         BYTE2CHAR[i] = '.';
/*     */       } else {
/*  81 */         BYTE2CHAR[i] = (char)i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final InternalLogLevel internalLevel;
/*     */ 
/*     */   
/*     */   private final LogLevel level;
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler() {
/*  96 */     this(DEFAULT_LEVEL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(LogLevel level) {
/* 106 */     if (level == null) {
/* 107 */       throw new NullPointerException("level");
/*     */     }
/*     */     
/* 110 */     this.logger = InternalLoggerFactory.getInstance(getClass());
/* 111 */     this.level = level;
/* 112 */     this.internalLevel = level.toInternalLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(Class<?> clazz) {
/* 120 */     this(clazz, DEFAULT_LEVEL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(Class<?> clazz, LogLevel level) {
/* 129 */     if (clazz == null) {
/* 130 */       throw new NullPointerException("clazz");
/*     */     }
/* 132 */     if (level == null) {
/* 133 */       throw new NullPointerException("level");
/*     */     }
/* 135 */     this.logger = InternalLoggerFactory.getInstance(clazz);
/* 136 */     this.level = level;
/* 137 */     this.internalLevel = level.toInternalLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(String name) {
/* 144 */     this(name, DEFAULT_LEVEL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(String name, LogLevel level) {
/* 153 */     if (name == null) {
/* 154 */       throw new NullPointerException("name");
/*     */     }
/* 156 */     if (level == null) {
/* 157 */       throw new NullPointerException("level");
/*     */     }
/* 159 */     this.logger = InternalLoggerFactory.getInstance(name);
/* 160 */     this.level = level;
/* 161 */     this.internalLevel = level.toInternalLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogLevel level() {
/* 168 */     return this.level;
/*     */   }
/*     */   
/*     */   protected String format(ChannelHandlerContext ctx, String message) {
/* 172 */     String chStr = ctx.channel().toString();
/* 173 */     StringBuilder buf = new StringBuilder(chStr.length() + message.length() + 1);
/* 174 */     buf.append(chStr);
/* 175 */     buf.append(' ');
/* 176 */     buf.append(message);
/* 177 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 183 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 184 */       this.logger.log(this.internalLevel, format(ctx, "REGISTERED"));
/*     */     }
/* 186 */     super.channelRegistered(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
/* 192 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 193 */       this.logger.log(this.internalLevel, format(ctx, "UNREGISTERED"));
/*     */     }
/* 195 */     super.channelUnregistered(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 201 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 202 */       this.logger.log(this.internalLevel, format(ctx, "ACTIVE"));
/*     */     }
/* 204 */     super.channelActive(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 210 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 211 */       this.logger.log(this.internalLevel, format(ctx, "INACTIVE"));
/*     */     }
/* 213 */     super.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 219 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 220 */       this.logger.log(this.internalLevel, format(ctx, "EXCEPTION: " + cause), cause);
/*     */     }
/* 222 */     super.exceptionCaught(ctx, cause);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
/* 228 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 229 */       this.logger.log(this.internalLevel, format(ctx, "USER_EVENT: " + evt));
/*     */     }
/* 231 */     super.userEventTriggered(ctx, evt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 237 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 238 */       this.logger.log(this.internalLevel, format(ctx, "BIND(" + localAddress + ')'));
/*     */     }
/* 240 */     super.bind(ctx, localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 247 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 248 */       this.logger.log(this.internalLevel, format(ctx, "CONNECT(" + remoteAddress + ", " + localAddress + ')'));
/*     */     }
/* 250 */     super.connect(ctx, remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 256 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 257 */       this.logger.log(this.internalLevel, format(ctx, "DISCONNECT()"));
/*     */     }
/* 259 */     super.disconnect(ctx, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 265 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 266 */       this.logger.log(this.internalLevel, format(ctx, "CLOSE()"));
/*     */     }
/* 268 */     super.close(ctx, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 274 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 275 */       this.logger.log(this.internalLevel, format(ctx, "DEREGISTER()"));
/*     */     }
/* 277 */     super.deregister(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 282 */     logMessage(ctx, "RECEIVED", msg);
/* 283 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 288 */     logMessage(ctx, "WRITE", msg);
/* 289 */     ctx.write(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 294 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 295 */       this.logger.log(this.internalLevel, format(ctx, "FLUSH"));
/*     */     }
/* 297 */     ctx.flush();
/*     */   }
/*     */   
/*     */   private void logMessage(ChannelHandlerContext ctx, String eventName, Object msg) {
/* 301 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 302 */       this.logger.log(this.internalLevel, format(ctx, formatMessage(eventName, msg)));
/*     */     }
/*     */   }
/*     */   
/*     */   protected String formatMessage(String eventName, Object msg) {
/* 307 */     if (msg instanceof ByteBuf)
/* 308 */       return formatByteBuf(eventName, (ByteBuf)msg); 
/* 309 */     if (msg instanceof ByteBufHolder) {
/* 310 */       return formatByteBufHolder(eventName, (ByteBufHolder)msg);
/*     */     }
/* 312 */     return formatNonByteBuf(eventName, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String formatByteBuf(String eventName, ByteBuf buf) {
/* 320 */     int length = buf.readableBytes();
/* 321 */     int rows = length / 16 + ((length % 15 == 0) ? 0 : 1) + 4;
/* 322 */     StringBuilder dump = new StringBuilder(rows * 80 + eventName.length() + 16);
/*     */     
/* 324 */     dump.append(eventName).append('(').append(length).append('B').append(')');
/* 325 */     dump.append(NEWLINE + "         +-------------------------------------------------+" + NEWLINE + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |" + NEWLINE + "+--------+-------------------------------------------------+----------------+");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     int startIndex = buf.readerIndex();
/* 331 */     int endIndex = buf.writerIndex();
/*     */     
/*     */     int i;
/* 334 */     for (i = startIndex; i < endIndex; i++) {
/* 335 */       int relIdx = i - startIndex;
/* 336 */       int relIdxMod16 = relIdx & 0xF;
/* 337 */       if (relIdxMod16 == 0) {
/* 338 */         dump.append(NEWLINE);
/* 339 */         dump.append(Long.toHexString(relIdx & 0xFFFFFFFFL | 0x100000000L));
/* 340 */         dump.setCharAt(dump.length() - 9, '|');
/* 341 */         dump.append('|');
/*     */       } 
/* 343 */       dump.append(BYTE2HEX[buf.getUnsignedByte(i)]);
/* 344 */       if (relIdxMod16 == 15) {
/* 345 */         dump.append(" |");
/* 346 */         for (int j = i - 15; j <= i; j++) {
/* 347 */           dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
/*     */         }
/* 349 */         dump.append('|');
/*     */       } 
/*     */     } 
/*     */     
/* 353 */     if ((i - startIndex & 0xF) != 0) {
/* 354 */       int remainder = length & 0xF;
/* 355 */       dump.append(HEXPADDING[remainder]);
/* 356 */       dump.append(" |");
/* 357 */       for (int j = i - remainder; j < i; j++) {
/* 358 */         dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
/*     */       }
/* 360 */       dump.append(BYTEPADDING[remainder]);
/* 361 */       dump.append('|');
/*     */     } 
/*     */     
/* 364 */     dump.append(NEWLINE + "+--------+-------------------------------------------------+----------------+");
/*     */ 
/*     */     
/* 367 */     return dump.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String formatNonByteBuf(String eventName, Object msg) {
/* 374 */     return eventName + ": " + msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String formatByteBufHolder(String eventName, ByteBufHolder msg) {
/* 384 */     return formatByteBuf(eventName, msg.content());
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\logging\LoggingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */