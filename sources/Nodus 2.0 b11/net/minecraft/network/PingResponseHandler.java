/*   1:    */ package net.minecraft.network;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Charsets;
/*   4:    */ import io.netty.buffer.ByteBuf;
/*   5:    */ import io.netty.buffer.Unpooled;
/*   6:    */ import io.netty.channel.Channel;
/*   7:    */ import io.netty.channel.ChannelFuture;
/*   8:    */ import io.netty.channel.ChannelFutureListener;
/*   9:    */ import io.netty.channel.ChannelHandlerContext;
/*  10:    */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*  11:    */ import io.netty.channel.ChannelPipeline;
/*  12:    */ import java.net.InetSocketAddress;
/*  13:    */ import net.minecraft.server.MinecraftServer;
/*  14:    */ import org.apache.logging.log4j.LogManager;
/*  15:    */ import org.apache.logging.log4j.Logger;
/*  16:    */ 
/*  17:    */ public class PingResponseHandler
/*  18:    */   extends ChannelInboundHandlerAdapter
/*  19:    */ {
/*  20: 16 */   private static final Logger logger = ;
/*  21:    */   private NetworkSystem field_151257_b;
/*  22:    */   private static final String __OBFID = "CL_00001444";
/*  23:    */   
/*  24:    */   public PingResponseHandler(NetworkSystem p_i45286_1_)
/*  25:    */   {
/*  26: 22 */     this.field_151257_b = p_i45286_1_;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void channelRead(ChannelHandlerContext p_channelRead_1_, Object p_channelRead_2_)
/*  30:    */   {
/*  31: 27 */     ByteBuf var3 = (ByteBuf)p_channelRead_2_;
/*  32: 28 */     var3.markReaderIndex();
/*  33: 29 */     boolean var4 = true;
/*  34:    */     try
/*  35:    */     {
/*  36: 35 */       if (var3.readUnsignedByte() != 254) {
/*  37: 37 */         return;
/*  38:    */       }
/*  39: 40 */       InetSocketAddress var5 = (InetSocketAddress)p_channelRead_1_.channel().remoteAddress();
/*  40: 41 */       MinecraftServer var6 = this.field_151257_b.func_151267_d();
/*  41: 42 */       int var7 = var3.readableBytes();
/*  42: 45 */       switch (var7)
/*  43:    */       {
/*  44:    */       case 0: 
/*  45: 48 */         logger.debug("Ping: (<1.3.x) from {}:{}", new Object[] { var5.getAddress(), Integer.valueOf(var5.getPort()) });
/*  46: 49 */         String var8 = String.format("%s§%d§%d", new Object[] { var6.getMOTD(), Integer.valueOf(var6.getCurrentPlayerCount()), Integer.valueOf(var6.getMaxPlayers()) });
/*  47: 50 */         func_151256_a(p_channelRead_1_, func_151255_a(var8));
/*  48: 51 */         break;
/*  49:    */       case 1: 
/*  50: 54 */         if (var3.readUnsignedByte() != 1) {
/*  51: 56 */           return;
/*  52:    */         }
/*  53: 59 */         logger.debug("Ping: (1.4-1.5.x) from {}:{}", new Object[] { var5.getAddress(), Integer.valueOf(var5.getPort()) });
/*  54: 60 */         String var8 = String.format("", new Object[] { Integer.valueOf(127), var6.getMinecraftVersion(), var6.getMOTD(), Integer.valueOf(var6.getCurrentPlayerCount()), Integer.valueOf(var6.getMaxPlayers()) });
/*  55: 61 */         func_151256_a(p_channelRead_1_, func_151255_a(var8));
/*  56: 62 */         break;
/*  57:    */       default: 
/*  58: 65 */         boolean var16 = var3.readUnsignedByte() == 1;
/*  59: 66 */         var16 &= var3.readUnsignedByte() == 250;
/*  60: 67 */         var16 &= "MC|PingHost".equals(new String(var3.readBytes(var3.readShort() * 2).array(), Charsets.UTF_16BE));
/*  61: 68 */         int var9 = var3.readUnsignedShort();
/*  62: 69 */         var16 &= var3.readUnsignedByte() >= 73;
/*  63: 70 */         var16 &= 3 + var3.readBytes(var3.readShort() * 2).array().length + 4 == var9;
/*  64: 71 */         var16 &= var3.readInt() <= 65535;
/*  65: 72 */         var16 &= var3.readableBytes() == 0;
/*  66: 74 */         if (!var16) {
/*  67: 76 */           return;
/*  68:    */         }
/*  69: 79 */         logger.debug("Ping: (1.6) from {}:{}", new Object[] { var5.getAddress(), Integer.valueOf(var5.getPort()) });
/*  70: 80 */         String var10 = String.format("", new Object[] { Integer.valueOf(127), var6.getMinecraftVersion(), var6.getMOTD(), Integer.valueOf(var6.getCurrentPlayerCount()), Integer.valueOf(var6.getMaxPlayers()) });
/*  71: 81 */         func_151256_a(p_channelRead_1_, func_151255_a(var10));
/*  72:    */       }
/*  73: 84 */       var3.release();
/*  74: 85 */       var4 = false;
/*  75:    */     }
/*  76:    */     catch (RuntimeException localRuntimeException) {}finally
/*  77:    */     {
/*  78: 94 */       if (var4)
/*  79:    */       {
/*  80: 96 */         var3.resetReaderIndex();
/*  81: 97 */         p_channelRead_1_.channel().pipeline().remove("legacy_query");
/*  82: 98 */         p_channelRead_1_.fireChannelRead(p_channelRead_2_);
/*  83:    */       }
/*  84:    */     }
/*  85: 94 */     if (var4)
/*  86:    */     {
/*  87: 96 */       var3.resetReaderIndex();
/*  88: 97 */       p_channelRead_1_.channel().pipeline().remove("legacy_query");
/*  89: 98 */       p_channelRead_1_.fireChannelRead(p_channelRead_2_);
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   private void func_151256_a(ChannelHandlerContext p_151256_1_, ByteBuf p_151256_2_)
/*  94:    */   {
/*  95:105 */     p_151256_1_.pipeline().firstContext().writeAndFlush(p_151256_2_).addListener(ChannelFutureListener.CLOSE);
/*  96:    */   }
/*  97:    */   
/*  98:    */   private ByteBuf func_151255_a(String p_151255_1_)
/*  99:    */   {
/* 100:110 */     ByteBuf var2 = Unpooled.buffer();
/* 101:111 */     var2.writeByte(255);
/* 102:112 */     char[] var3 = p_151255_1_.toCharArray();
/* 103:113 */     var2.writeShort(var3.length);
/* 104:114 */     char[] var4 = var3;
/* 105:115 */     int var5 = var3.length;
/* 106:117 */     for (int var6 = 0; var6 < var5; var6++)
/* 107:    */     {
/* 108:119 */       char var7 = var4[var6];
/* 109:120 */       var2.writeChar(var7);
/* 110:    */     }
/* 111:123 */     return var2;
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.PingResponseHandler
 * JD-Core Version:    0.7.0.1
 */