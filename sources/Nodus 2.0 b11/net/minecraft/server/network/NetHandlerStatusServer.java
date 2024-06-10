/*  1:   */ package net.minecraft.server.network;
/*  2:   */ 
/*  3:   */ import io.netty.util.concurrent.GenericFutureListener;
/*  4:   */ import net.minecraft.network.EnumConnectionState;
/*  5:   */ import net.minecraft.network.NetworkManager;
/*  6:   */ import net.minecraft.network.status.INetHandlerStatusServer;
/*  7:   */ import net.minecraft.network.status.client.C00PacketServerQuery;
/*  8:   */ import net.minecraft.network.status.client.C01PacketPing;
/*  9:   */ import net.minecraft.network.status.server.S00PacketServerInfo;
/* 10:   */ import net.minecraft.network.status.server.S01PacketPong;
/* 11:   */ import net.minecraft.server.MinecraftServer;
/* 12:   */ import net.minecraft.util.IChatComponent;
/* 13:   */ 
/* 14:   */ public class NetHandlerStatusServer
/* 15:   */   implements INetHandlerStatusServer
/* 16:   */ {
/* 17:   */   private final MinecraftServer field_147314_a;
/* 18:   */   private final NetworkManager field_147313_b;
/* 19:   */   private static final String __OBFID = "CL_00001464";
/* 20:   */   
/* 21:   */   public NetHandlerStatusServer(MinecraftServer p_i45299_1_, NetworkManager p_i45299_2_)
/* 22:   */   {
/* 23:22 */     this.field_147314_a = p_i45299_1_;
/* 24:23 */     this.field_147313_b = p_i45299_2_;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void onDisconnect(IChatComponent p_147231_1_) {}
/* 28:   */   
/* 29:   */   public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
/* 30:   */   {
/* 31:37 */     if (p_147232_2_ != EnumConnectionState.STATUS) {
/* 32:39 */       throw new UnsupportedOperationException("Unexpected change in protocol to " + p_147232_2_);
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void onNetworkTick() {}
/* 37:   */   
/* 38:   */   public void processServerQuery(C00PacketServerQuery p_147312_1_)
/* 39:   */   {
/* 40:51 */     this.field_147313_b.scheduleOutboundPacket(new S00PacketServerInfo(this.field_147314_a.func_147134_at()), new GenericFutureListener[0]);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void processPing(C01PacketPing p_147311_1_)
/* 44:   */   {
/* 45:56 */     this.field_147313_b.scheduleOutboundPacket(new S01PacketPong(p_147311_1_.func_149289_c()), new GenericFutureListener[0]);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.network.NetHandlerStatusServer
 * JD-Core Version:    0.7.0.1
 */