/*  1:   */ package net.minecraft.network.login.server;
/*  2:   */ 
/*  3:   */ import com.mojang.authlib.GameProfile;
/*  4:   */ import java.io.IOException;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.login.INetHandlerLoginClient;
/*  9:   */ 
/* 10:   */ public class S02PacketLoginSuccess
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private GameProfile field_149602_a;
/* 14:   */   private static final String __OBFID = "CL_00001375";
/* 15:   */   
/* 16:   */   public S02PacketLoginSuccess() {}
/* 17:   */   
/* 18:   */   public S02PacketLoginSuccess(GameProfile p_i45267_1_)
/* 19:   */   {
/* 20:19 */     this.field_149602_a = p_i45267_1_;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:27 */     String var2 = p_148837_1_.readStringFromBuffer(36);
/* 27:28 */     String var3 = p_148837_1_.readStringFromBuffer(16);
/* 28:29 */     this.field_149602_a = new GameProfile(var2, var3);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:37 */     p_148840_1_.writeStringToBuffer(this.field_149602_a.getId());
/* 35:38 */     p_148840_1_.writeStringToBuffer(this.field_149602_a.getName());
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void processPacket(INetHandlerLoginClient p_149601_1_)
/* 39:   */   {
/* 40:43 */     p_149601_1_.handleLoginSuccess(this);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean hasPriority()
/* 44:   */   {
/* 45:52 */     return true;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void processPacket(INetHandler p_148833_1_)
/* 49:   */   {
/* 50:57 */     processPacket((INetHandlerLoginClient)p_148833_1_);
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.login.server.S02PacketLoginSuccess
 * JD-Core Version:    0.7.0.1
 */