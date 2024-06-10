/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import io.netty.util.concurrent.GenericFutureListener;
/*   4:    */ import java.net.InetAddress;
/*   5:    */ import java.net.UnknownHostException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import me.connorm.Nodus.Nodus;
/*  11:    */ import net.minecraft.client.Minecraft;
/*  12:    */ import net.minecraft.client.mco.McoServer;
/*  13:    */ import net.minecraft.client.multiplayer.ServerAddress;
/*  14:    */ import net.minecraft.network.EnumConnectionState;
/*  15:    */ import net.minecraft.network.NetworkManager;
/*  16:    */ import net.minecraft.network.ServerStatusResponse;
/*  17:    */ import net.minecraft.network.ServerStatusResponse.PlayerCountData;
/*  18:    */ import net.minecraft.network.handshake.client.C00Handshake;
/*  19:    */ import net.minecraft.network.status.INetHandlerStatusClient;
/*  20:    */ import net.minecraft.network.status.client.C00PacketServerQuery;
/*  21:    */ import net.minecraft.network.status.client.C01PacketPing;
/*  22:    */ import net.minecraft.network.status.server.S00PacketServerInfo;
/*  23:    */ import net.minecraft.network.status.server.S01PacketPong;
/*  24:    */ import net.minecraft.util.ChatComponentText;
/*  25:    */ import net.minecraft.util.EnumChatFormatting;
/*  26:    */ import net.minecraft.util.IChatComponent;
/*  27:    */ import org.apache.logging.log4j.LogManager;
/*  28:    */ import org.apache.logging.log4j.Logger;
/*  29:    */ 
/*  30:    */ public class GuiScreenRealmsPinger
/*  31:    */ {
/*  32: 34 */   private static final Logger logger = ;
/*  33: 35 */   private final List field_148509_b = Collections.synchronizedList(new ArrayList());
/*  34:    */   private static final String __OBFID = "CL_00000807";
/*  35:    */   
/*  36:    */   public void func_148506_a(final McoServer p_148506_1_)
/*  37:    */     throws UnknownHostException
/*  38:    */   {
/*  39: 40 */     if (p_148506_1_.field_148807_g != null)
/*  40:    */     {
/*  41: 42 */       ServerAddress var2 = ServerAddress.func_78860_a(p_148506_1_.field_148807_g);
/*  42: 43 */       final NetworkManager var3 = NetworkManager.provideLanClient(InetAddress.getByName(var2.getIP()), var2.getPort());
/*  43: 44 */       this.field_148509_b.add(var3);
/*  44: 45 */       var3.setNetHandler(new INetHandlerStatusClient()
/*  45:    */       {
/*  46: 47 */         private boolean field_147399_d = false;
/*  47:    */         private static final String __OBFID = "CL_00000808";
/*  48:    */         
/*  49:    */         public void handleServerInfo(S00PacketServerInfo p_147397_1_)
/*  50:    */         {
/*  51: 51 */           ServerStatusResponse var2 = p_147397_1_.func_149294_c();
/*  52: 53 */           if (var2.func_151318_b() != null) {
/*  53: 55 */             p_148506_1_.field_148813_n = (EnumChatFormatting.GRAY + var2.func_151318_b().func_151333_b());
/*  54:    */           }
/*  55: 58 */           var3.scheduleOutboundPacket(new C01PacketPing(Minecraft.getSystemTime()), new GenericFutureListener[0]);
/*  56: 59 */           this.field_147399_d = true;
/*  57:    */         }
/*  58:    */         
/*  59:    */         public void handlePong(S01PacketPong p_147398_1_)
/*  60:    */         {
/*  61: 63 */           var3.closeChannel(new ChatComponentText("Finished"));
/*  62:    */         }
/*  63:    */         
/*  64:    */         public void onDisconnect(IChatComponent p_147231_1_)
/*  65:    */         {
/*  66: 67 */           if (!this.field_147399_d) {
/*  67: 69 */             GuiScreenRealmsPinger.logger.error("Can't ping " + p_148506_1_.field_148807_g + ": " + p_147231_1_.getUnformattedText());
/*  68:    */           }
/*  69:    */         }
/*  70:    */         
/*  71:    */         public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
/*  72:    */         {
/*  73: 74 */           if (p_147232_2_ != EnumConnectionState.STATUS) {
/*  74: 76 */             throw new UnsupportedOperationException("Unexpected change in protocol to " + p_147232_2_);
/*  75:    */           }
/*  76:    */         }
/*  77:    */         
/*  78:    */         public void onNetworkTick() {}
/*  79:    */       });
/*  80:    */       try
/*  81:    */       {
/*  82: 85 */         var3.scheduleOutboundPacket(new C00Handshake(Nodus.theNodus.getMinecraftVersion(), var2.getIP(), var2.getPort(), EnumConnectionState.STATUS), new GenericFutureListener[0]);
/*  83: 86 */         var3.scheduleOutboundPacket(new C00PacketServerQuery(), new GenericFutureListener[0]);
/*  84:    */       }
/*  85:    */       catch (Throwable var5)
/*  86:    */       {
/*  87: 90 */         logger.error(var5);
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void func_148507_b()
/*  93:    */   {
/*  94: 97 */     List var1 = this.field_148509_b;
/*  95: 99 */     synchronized (this.field_148509_b)
/*  96:    */     {
/*  97:101 */       Iterator var2 = this.field_148509_b.iterator();
/*  98:103 */       while (var2.hasNext())
/*  99:    */       {
/* 100:105 */         NetworkManager var3 = (NetworkManager)var2.next();
/* 101:107 */         if (var3.isChannelOpen())
/* 102:    */         {
/* 103:109 */           var2.remove();
/* 104:110 */           var3.closeChannel(new ChatComponentText("Cancelled"));
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:    */   }
/* 109:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenRealmsPinger
 * JD-Core Version:    0.7.0.1
 */