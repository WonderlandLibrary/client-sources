/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetSocketAddress;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class PingResponseHandler extends ChannelInboundHandlerAdapter {
/*  16 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private NetworkSystem networkSystem;
/*     */   
/*     */   public PingResponseHandler(NetworkSystem networkSystemIn) {
/*  21 */     this.networkSystem = networkSystemIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext p_channelRead_1_, Object p_channelRead_2_) throws Exception {
/*  26 */     ByteBuf bytebuf = (ByteBuf)p_channelRead_2_;
/*  27 */     bytebuf.markReaderIndex();
/*  28 */     boolean flag = true;
/*     */ 
/*     */     
/*     */     try {
/*  32 */       if (bytebuf.readUnsignedByte() == 254) {
/*     */         String s2, s; boolean flag1; int m; boolean bool1; int k, j; String s1; ByteBuf bytebuf1;
/*  34 */         InetSocketAddress inetsocketaddress = (InetSocketAddress)p_channelRead_1_.channel().remoteAddress();
/*  35 */         MinecraftServer minecraftserver = this.networkSystem.getServer();
/*  36 */         int i = bytebuf.readableBytes();
/*     */         
/*  38 */         switch (i) {
/*     */           
/*     */           case 0:
/*  41 */             logger.debug("Ping: (<1.3.x) from {}:{}", new Object[] { inetsocketaddress.getAddress(), Integer.valueOf(inetsocketaddress.getPort()) });
/*  42 */             s2 = String.format("%s§%d§%d", new Object[] { minecraftserver.getMOTD(), Integer.valueOf(minecraftserver.getCurrentPlayerCount()), Integer.valueOf(minecraftserver.getMaxPlayers()) });
/*  43 */             writeAndFlush(p_channelRead_1_, getStringBuffer(s2));
/*     */             break;
/*     */           
/*     */           case 1:
/*  47 */             if (bytebuf.readUnsignedByte() != 1) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  52 */             logger.debug("Ping: (1.4-1.5.x) from {}:{}", new Object[] { inetsocketaddress.getAddress(), Integer.valueOf(inetsocketaddress.getPort()) });
/*  53 */             s = String.format("§1\000%d\000%s\000%s\000%d\000%d", new Object[] { Integer.valueOf(127), minecraftserver.getMinecraftVersion(), minecraftserver.getMOTD(), Integer.valueOf(minecraftserver.getCurrentPlayerCount()), Integer.valueOf(minecraftserver.getMaxPlayers()) });
/*  54 */             writeAndFlush(p_channelRead_1_, getStringBuffer(s));
/*     */             break;
/*     */           
/*     */           default:
/*  58 */             flag1 = (bytebuf.readUnsignedByte() == 1);
/*  59 */             m = flag1 & ((bytebuf.readUnsignedByte() == 250) ? 1 : 0);
/*  60 */             bool1 = m & "MC|PingHost".equals(new String(bytebuf.readBytes(bytebuf.readShort() * 2).array(), Charsets.UTF_16BE));
/*  61 */             j = bytebuf.readUnsignedShort();
/*  62 */             k = bool1 & ((bytebuf.readUnsignedByte() >= 73) ? 1 : 0);
/*  63 */             k &= (3 + (bytebuf.readBytes(bytebuf.readShort() * 2).array()).length + 4 == j) ? 1 : 0;
/*  64 */             k &= (bytebuf.readInt() <= 65535) ? 1 : 0;
/*  65 */             k &= (bytebuf.readableBytes() == 0) ? 1 : 0;
/*     */             
/*  67 */             if (k == 0) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  72 */             logger.debug("Ping: (1.6) from {}:{}", new Object[] { inetsocketaddress.getAddress(), Integer.valueOf(inetsocketaddress.getPort()) });
/*  73 */             s1 = String.format("§1\000%d\000%s\000%s\000%d\000%d", new Object[] { Integer.valueOf(127), minecraftserver.getMinecraftVersion(), minecraftserver.getMOTD(), Integer.valueOf(minecraftserver.getCurrentPlayerCount()), Integer.valueOf(minecraftserver.getMaxPlayers()) });
/*  74 */             bytebuf1 = getStringBuffer(s1);
/*     */ 
/*     */             
/*     */             try {
/*  78 */               writeAndFlush(p_channelRead_1_, bytebuf1);
/*     */             }
/*     */             finally {
/*     */               
/*  82 */               bytebuf1.release();
/*     */             } 
/*     */             break;
/*     */         } 
/*  86 */         bytebuf.release();
/*  87 */         flag = false;
/*     */         
/*     */         return;
/*     */       } 
/*  91 */     } catch (RuntimeException var21) {
/*     */       
/*     */       return;
/*     */     }
/*     */     finally {
/*     */       
/*  97 */       if (flag) {
/*     */         
/*  99 */         bytebuf.resetReaderIndex();
/* 100 */         p_channelRead_1_.channel().pipeline().remove("legacy_query");
/* 101 */         p_channelRead_1_.fireChannelRead(p_channelRead_2_);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeAndFlush(ChannelHandlerContext ctx, ByteBuf data) {
/* 108 */     ctx.pipeline().firstContext().writeAndFlush(data).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */   }
/*     */ 
/*     */   
/*     */   private ByteBuf getStringBuffer(String string) {
/* 113 */     ByteBuf bytebuf = Unpooled.buffer();
/* 114 */     bytebuf.writeByte(255);
/* 115 */     char[] achar = string.toCharArray();
/* 116 */     bytebuf.writeShort(achar.length); byte b; int i;
/*     */     char[] arrayOfChar1;
/* 118 */     for (i = (arrayOfChar1 = achar).length, b = 0; b < i; ) { char c0 = arrayOfChar1[b];
/*     */       
/* 120 */       bytebuf.writeChar(c0);
/*     */       b++; }
/*     */     
/* 123 */     return bytebuf;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\PingResponseHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */