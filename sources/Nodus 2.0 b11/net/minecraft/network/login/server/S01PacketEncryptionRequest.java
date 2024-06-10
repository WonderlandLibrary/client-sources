/*  1:   */ package net.minecraft.network.login.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.security.PublicKey;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.login.INetHandlerLoginClient;
/*  9:   */ import net.minecraft.util.CryptManager;
/* 10:   */ 
/* 11:   */ public class S01PacketEncryptionRequest
/* 12:   */   extends Packet
/* 13:   */ {
/* 14:   */   private String field_149612_a;
/* 15:   */   private PublicKey field_149610_b;
/* 16:   */   private byte[] field_149611_c;
/* 17:   */   private static final String __OBFID = "CL_00001376";
/* 18:   */   
/* 19:   */   public S01PacketEncryptionRequest() {}
/* 20:   */   
/* 21:   */   public S01PacketEncryptionRequest(String p_i45268_1_, PublicKey p_i45268_2_, byte[] p_i45268_3_)
/* 22:   */   {
/* 23:22 */     this.field_149612_a = p_i45268_1_;
/* 24:23 */     this.field_149610_b = p_i45268_2_;
/* 25:24 */     this.field_149611_c = p_i45268_3_;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 29:   */     throws IOException
/* 30:   */   {
/* 31:32 */     this.field_149612_a = p_148837_1_.readStringFromBuffer(20);
/* 32:33 */     this.field_149610_b = CryptManager.decodePublicKey(readBlob(p_148837_1_));
/* 33:34 */     this.field_149611_c = readBlob(p_148837_1_);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:42 */     p_148840_1_.writeStringToBuffer(this.field_149612_a);
/* 40:43 */     writeBlob(p_148840_1_, this.field_149610_b.getEncoded());
/* 41:44 */     writeBlob(p_148840_1_, this.field_149611_c);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void processPacket(INetHandlerLoginClient p_149606_1_)
/* 45:   */   {
/* 46:49 */     p_149606_1_.handleEncryptionRequest(this);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String func_149609_c()
/* 50:   */   {
/* 51:54 */     return this.field_149612_a;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public PublicKey func_149608_d()
/* 55:   */   {
/* 56:59 */     return this.field_149610_b;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public byte[] func_149607_e()
/* 60:   */   {
/* 61:64 */     return this.field_149611_c;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void processPacket(INetHandler p_148833_1_)
/* 65:   */   {
/* 66:69 */     processPacket((INetHandlerLoginClient)p_148833_1_);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.login.server.S01PacketEncryptionRequest
 * JD-Core Version:    0.7.0.1
 */