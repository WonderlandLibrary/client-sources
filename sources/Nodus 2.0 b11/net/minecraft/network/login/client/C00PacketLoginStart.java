/*  1:   */ package net.minecraft.network.login.client;
/*  2:   */ 
/*  3:   */ import com.mojang.authlib.GameProfile;
/*  4:   */ import java.io.IOException;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.login.INetHandlerLoginServer;
/*  9:   */ 
/* 10:   */ public class C00PacketLoginStart
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private GameProfile field_149305_a;
/* 14:   */   private static final String __OBFID = "CL_00001379";
/* 15:   */   
/* 16:   */   public C00PacketLoginStart() {}
/* 17:   */   
/* 18:   */   public C00PacketLoginStart(GameProfile p_i45270_1_)
/* 19:   */   {
/* 20:19 */     this.field_149305_a = p_i45270_1_;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:27 */     this.field_149305_a = new GameProfile(null, p_148837_1_.readStringFromBuffer(16));
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:35 */     p_148840_1_.writeStringToBuffer(this.field_149305_a.getName());
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void processPacket(INetHandlerLoginServer p_149303_1_)
/* 36:   */   {
/* 37:40 */     p_149303_1_.processLoginStart(this);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public GameProfile func_149304_c()
/* 41:   */   {
/* 42:45 */     return this.field_149305_a;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void processPacket(INetHandler p_148833_1_)
/* 46:   */   {
/* 47:50 */     processPacket((INetHandlerLoginServer)p_148833_1_);
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.login.client.C00PacketLoginStart
 * JD-Core Version:    0.7.0.1
 */