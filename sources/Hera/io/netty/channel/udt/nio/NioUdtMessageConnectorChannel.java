/*     */ package io.netty.channel.udt.nio;
/*     */ 
/*     */ import com.barchart.udt.StatusUDT;
/*     */ import com.barchart.udt.TypeUDT;
/*     */ import com.barchart.udt.nio.ChannelUDT;
/*     */ import com.barchart.udt.nio.SocketChannelUDT;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import io.netty.channel.udt.DefaultUdtChannelConfig;
/*     */ import io.netty.channel.udt.UdtChannel;
/*     */ import io.netty.channel.udt.UdtChannelConfig;
/*     */ import io.netty.channel.udt.UdtMessage;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.util.List;
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
/*     */ public class NioUdtMessageConnectorChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements UdtChannel
/*     */ {
/*  47 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioUdtMessageConnectorChannel.class);
/*     */ 
/*     */   
/*  50 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*  51 */   private static final String EXPECTED_TYPE = " (expected: " + StringUtil.simpleClassName(UdtMessage.class) + ')';
/*     */   
/*     */   private final UdtChannelConfig config;
/*     */   
/*     */   public NioUdtMessageConnectorChannel() {
/*  56 */     this(TypeUDT.DATAGRAM);
/*     */   }
/*     */   
/*     */   public NioUdtMessageConnectorChannel(Channel parent, SocketChannelUDT channelUDT) {
/*  60 */     super(parent, (SelectableChannel)channelUDT, 1);
/*     */     try {
/*  62 */       channelUDT.configureBlocking(false);
/*  63 */       switch (channelUDT.socketUDT().status()) {
/*     */         case INIT:
/*     */         case OPENED:
/*  66 */           this.config = (UdtChannelConfig)new DefaultUdtChannelConfig(this, (ChannelUDT)channelUDT, true);
/*     */           return;
/*     */       } 
/*  69 */       this.config = (UdtChannelConfig)new DefaultUdtChannelConfig(this, (ChannelUDT)channelUDT, false);
/*     */     
/*     */     }
/*  72 */     catch (Exception e) {
/*     */       try {
/*  74 */         channelUDT.close();
/*  75 */       } catch (Exception e2) {
/*  76 */         if (logger.isWarnEnabled()) {
/*  77 */           logger.warn("Failed to close channel.", e2);
/*     */         }
/*     */       } 
/*  80 */       throw new ChannelException("Failed to configure channel.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public NioUdtMessageConnectorChannel(SocketChannelUDT channelUDT) {
/*  85 */     this(null, channelUDT);
/*     */   }
/*     */   
/*     */   public NioUdtMessageConnectorChannel(TypeUDT type) {
/*  89 */     this(NioUdtProvider.newConnectorChannelUDT(type));
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtChannelConfig config() {
/*  94 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/*  99 */     javaChannel().bind(localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 104 */     javaChannel().close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 110 */     doBind((localAddress != null) ? localAddress : new InetSocketAddress(0));
/* 111 */     boolean success = false;
/*     */     try {
/* 113 */       boolean connected = javaChannel().connect(remoteAddress);
/* 114 */       if (!connected) {
/* 115 */         selectionKey().interestOps(selectionKey().interestOps() | 0x8);
/*     */       }
/*     */       
/* 118 */       success = true;
/* 119 */       return connected;
/*     */     } finally {
/* 121 */       if (!success) {
/* 122 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 129 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 134 */     if (javaChannel().finishConnect()) {
/* 135 */       selectionKey().interestOps(selectionKey().interestOps() & 0xFFFFFFF7);
/*     */     } else {
/*     */       
/* 138 */       throw new Error("Provider error: failed to finish connect. Provider library should be upgraded.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 146 */     int maximumMessageSize = this.config.getReceiveBufferSize();
/*     */     
/* 148 */     ByteBuf byteBuf = this.config.getAllocator().directBuffer(maximumMessageSize);
/*     */ 
/*     */     
/* 151 */     int receivedMessageSize = byteBuf.writeBytes((ScatteringByteChannel)javaChannel(), maximumMessageSize);
/*     */ 
/*     */     
/* 154 */     if (receivedMessageSize <= 0) {
/* 155 */       byteBuf.release();
/* 156 */       return 0;
/*     */     } 
/*     */     
/* 159 */     if (receivedMessageSize >= maximumMessageSize) {
/* 160 */       javaChannel().close();
/* 161 */       throw new ChannelException("Invalid config : increase receive buffer size to avoid message truncation");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     buf.add(new UdtMessage(byteBuf));
/*     */     
/* 168 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
/*     */     long writtenBytes;
/* 174 */     UdtMessage message = (UdtMessage)msg;
/*     */     
/* 176 */     ByteBuf byteBuf = message.content();
/*     */     
/* 178 */     int messageSize = byteBuf.readableBytes();
/*     */ 
/*     */     
/* 181 */     if (byteBuf.nioBufferCount() == 1) {
/* 182 */       writtenBytes = javaChannel().write(byteBuf.nioBuffer());
/*     */     } else {
/* 184 */       writtenBytes = javaChannel().write(byteBuf.nioBuffers());
/*     */     } 
/*     */ 
/*     */     
/* 188 */     if (writtenBytes <= 0L && messageSize > 0) {
/* 189 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 193 */     if (writtenBytes != messageSize) {
/* 194 */       throw new Error("Provider error: failed to write message. Provider library should be upgraded.");
/*     */     }
/*     */ 
/*     */     
/* 198 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final Object filterOutboundMessage(Object msg) throws Exception {
/* 203 */     if (msg instanceof UdtMessage) {
/* 204 */       return msg;
/*     */     }
/*     */     
/* 207 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 213 */     SocketChannelUDT channelUDT = javaChannel();
/* 214 */     return (channelUDT.isOpen() && channelUDT.isConnectFinished());
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketChannelUDT javaChannel() {
/* 219 */     return (SocketChannelUDT)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 224 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 229 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 234 */     return javaChannel().socket().getRemoteSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 239 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 244 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channe\\udt\nio\NioUdtMessageConnectorChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */