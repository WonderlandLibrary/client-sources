/*  1:   */ package net.minecraft.client.network;
/*  2:   */ 
/*  3:   */ import net.minecraft.network.EnumConnectionState;
/*  4:   */ import net.minecraft.network.NetworkManager;
/*  5:   */ import net.minecraft.network.handshake.INetHandlerHandshakeServer;
/*  6:   */ import net.minecraft.network.handshake.client.C00Handshake;
/*  7:   */ import net.minecraft.server.MinecraftServer;
/*  8:   */ import net.minecraft.server.network.NetHandlerLoginServer;
/*  9:   */ import net.minecraft.util.IChatComponent;
/* 10:   */ import org.apache.commons.lang3.Validate;
/* 11:   */ 
/* 12:   */ public class NetHandlerHandshakeMemory
/* 13:   */   implements INetHandlerHandshakeServer
/* 14:   */ {
/* 15:   */   private final MinecraftServer field_147385_a;
/* 16:   */   private final NetworkManager field_147384_b;
/* 17:   */   private static final String __OBFID = "CL_00001445";
/* 18:   */   
/* 19:   */   public NetHandlerHandshakeMemory(MinecraftServer p_i45287_1_, NetworkManager p_i45287_2_)
/* 20:   */   {
/* 21:20 */     this.field_147385_a = p_i45287_1_;
/* 22:21 */     this.field_147384_b = p_i45287_2_;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void processHandshake(C00Handshake p_147383_1_)
/* 26:   */   {
/* 27:31 */     this.field_147384_b.setConnectionState(p_147383_1_.func_149594_c());
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void onDisconnect(IChatComponent p_147231_1_) {}
/* 31:   */   
/* 32:   */   public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
/* 33:   */   {
/* 34:45 */     Validate.validState((p_147232_2_ == EnumConnectionState.LOGIN) || (p_147232_2_ == EnumConnectionState.STATUS), "Unexpected protocol " + p_147232_2_, new Object[0]);
/* 35:47 */     switch (SwitchEnumConnectionState.field_151263_a[p_147232_2_.ordinal()])
/* 36:   */     {
/* 37:   */     case 1: 
/* 38:50 */       this.field_147384_b.setNetHandler(new NetHandlerLoginServer(this.field_147385_a, this.field_147384_b));
/* 39:51 */       break;
/* 40:   */     case 2: 
/* 41:54 */       throw new UnsupportedOperationException("NYI");
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void onNetworkTick() {}
/* 46:   */   
/* 47:   */   static final class SwitchEnumConnectionState
/* 48:   */   {
/* 49:68 */     static final int[] field_151263_a = new int[EnumConnectionState.values().length];
/* 50:   */     private static final String __OBFID = "CL_00001446";
/* 51:   */     
/* 52:   */     static
/* 53:   */     {
/* 54:   */       try
/* 55:   */       {
/* 56:75 */         field_151263_a[EnumConnectionState.LOGIN.ordinal()] = 1;
/* 57:   */       }
/* 58:   */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/* 59:   */       try
/* 60:   */       {
/* 61:84 */         field_151263_a[EnumConnectionState.STATUS.ordinal()] = 2;
/* 62:   */       }
/* 63:   */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/* 64:   */     }
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.network.NetHandlerHandshakeMemory
 * JD-Core Version:    0.7.0.1
 */