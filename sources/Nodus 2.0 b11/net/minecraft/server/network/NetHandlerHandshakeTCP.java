/*   1:    */ package net.minecraft.server.network;
/*   2:    */ 
/*   3:    */ import io.netty.util.concurrent.GenericFutureListener;
/*   4:    */ import net.minecraft.network.EnumConnectionState;
/*   5:    */ import net.minecraft.network.NetworkManager;
/*   6:    */ import net.minecraft.network.handshake.INetHandlerHandshakeServer;
/*   7:    */ import net.minecraft.network.handshake.client.C00Handshake;
/*   8:    */ import net.minecraft.network.login.server.S00PacketDisconnect;
/*   9:    */ import net.minecraft.server.MinecraftServer;
/*  10:    */ import net.minecraft.util.ChatComponentText;
/*  11:    */ import net.minecraft.util.IChatComponent;
/*  12:    */ 
/*  13:    */ public class NetHandlerHandshakeTCP
/*  14:    */   implements INetHandlerHandshakeServer
/*  15:    */ {
/*  16:    */   private final MinecraftServer field_147387_a;
/*  17:    */   private final NetworkManager field_147386_b;
/*  18:    */   private static final String __OBFID = "CL_00001456";
/*  19:    */   
/*  20:    */   public NetHandlerHandshakeTCP(MinecraftServer p_i45295_1_, NetworkManager p_i45295_2_)
/*  21:    */   {
/*  22: 21 */     this.field_147387_a = p_i45295_1_;
/*  23: 22 */     this.field_147386_b = p_i45295_2_;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void processHandshake(C00Handshake p_147383_1_)
/*  27:    */   {
/*  28: 32 */     switch (SwitchEnumConnectionState.field_151291_a[p_147383_1_.func_149594_c().ordinal()])
/*  29:    */     {
/*  30:    */     case 1: 
/*  31: 35 */       this.field_147386_b.setConnectionState(EnumConnectionState.LOGIN);
/*  32: 38 */       if (p_147383_1_.func_149595_d() > 4)
/*  33:    */       {
/*  34: 40 */         ChatComponentText var2 = new ChatComponentText("Outdated server! I'm still on 1.7.2");
/*  35: 41 */         this.field_147386_b.scheduleOutboundPacket(new S00PacketDisconnect(var2), new GenericFutureListener[0]);
/*  36: 42 */         this.field_147386_b.closeChannel(var2);
/*  37:    */       }
/*  38: 44 */       else if (p_147383_1_.func_149595_d() < 4)
/*  39:    */       {
/*  40: 46 */         ChatComponentText var2 = new ChatComponentText("Outdated client! Please use 1.7.2");
/*  41: 47 */         this.field_147386_b.scheduleOutboundPacket(new S00PacketDisconnect(var2), new GenericFutureListener[0]);
/*  42: 48 */         this.field_147386_b.closeChannel(var2);
/*  43:    */       }
/*  44:    */       else
/*  45:    */       {
/*  46: 52 */         this.field_147386_b.setNetHandler(new NetHandlerLoginServer(this.field_147387_a, this.field_147386_b));
/*  47:    */       }
/*  48: 55 */       break;
/*  49:    */     case 2: 
/*  50: 58 */       this.field_147386_b.setConnectionState(EnumConnectionState.STATUS);
/*  51: 59 */       this.field_147386_b.setNetHandler(new NetHandlerStatusServer(this.field_147387_a, this.field_147386_b));
/*  52: 60 */       break;
/*  53:    */     default: 
/*  54: 63 */       throw new UnsupportedOperationException("Invalid intention " + p_147383_1_.func_149594_c());
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void onDisconnect(IChatComponent p_147231_1_) {}
/*  59:    */   
/*  60:    */   public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
/*  61:    */   {
/*  62: 78 */     if ((p_147232_2_ != EnumConnectionState.LOGIN) && (p_147232_2_ != EnumConnectionState.STATUS)) {
/*  63: 80 */       throw new UnsupportedOperationException("Invalid state " + p_147232_2_);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void onNetworkTick() {}
/*  68:    */   
/*  69:    */   static final class SwitchEnumConnectionState
/*  70:    */   {
/*  71: 92 */     static final int[] field_151291_a = new int[EnumConnectionState.values().length];
/*  72:    */     private static final String __OBFID = "CL_00001457";
/*  73:    */     
/*  74:    */     static
/*  75:    */     {
/*  76:    */       try
/*  77:    */       {
/*  78: 99 */         field_151291_a[EnumConnectionState.LOGIN.ordinal()] = 1;
/*  79:    */       }
/*  80:    */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*  81:    */       try
/*  82:    */       {
/*  83:108 */         field_151291_a[EnumConnectionState.STATUS.ordinal()] = 2;
/*  84:    */       }
/*  85:    */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*  86:    */     }
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.network.NetHandlerHandshakeTCP
 * JD-Core Version:    0.7.0.1
 */