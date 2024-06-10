/*  1:   */ package net.minecraft.network.login.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.security.PrivateKey;
/*  5:   */ import java.security.PublicKey;
/*  6:   */ import javax.crypto.SecretKey;
/*  7:   */ import net.minecraft.network.INetHandler;
/*  8:   */ import net.minecraft.network.Packet;
/*  9:   */ import net.minecraft.network.PacketBuffer;
/* 10:   */ import net.minecraft.network.login.INetHandlerLoginServer;
/* 11:   */ import net.minecraft.util.CryptManager;
/* 12:   */ 
/* 13:   */ public class C01PacketEncryptionResponse
/* 14:   */   extends Packet
/* 15:   */ {
/* 16:15 */   private byte[] field_149302_a = new byte[0];
/* 17:16 */   private byte[] field_149301_b = new byte[0];
/* 18:   */   private static final String __OBFID = "CL_00001380";
/* 19:   */   
/* 20:   */   public C01PacketEncryptionResponse() {}
/* 21:   */   
/* 22:   */   public C01PacketEncryptionResponse(SecretKey p_i45271_1_, PublicKey p_i45271_2_, byte[] p_i45271_3_)
/* 23:   */   {
/* 24:23 */     this.field_149302_a = CryptManager.encryptData(p_i45271_2_, p_i45271_1_.getEncoded());
/* 25:24 */     this.field_149301_b = CryptManager.encryptData(p_i45271_2_, p_i45271_3_);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 29:   */     throws IOException
/* 30:   */   {
/* 31:32 */     this.field_149302_a = readBlob(p_148837_1_);
/* 32:33 */     this.field_149301_b = readBlob(p_148837_1_);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 36:   */     throws IOException
/* 37:   */   {
/* 38:41 */     writeBlob(p_148840_1_, this.field_149302_a);
/* 39:42 */     writeBlob(p_148840_1_, this.field_149301_b);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void processPacket(INetHandlerLoginServer p_149298_1_)
/* 43:   */   {
/* 44:47 */     p_149298_1_.processEncryptionResponse(this);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public SecretKey func_149300_a(PrivateKey p_149300_1_)
/* 48:   */   {
/* 49:52 */     return CryptManager.decryptSharedKey(p_149300_1_, this.field_149302_a);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public byte[] func_149299_b(PrivateKey p_149299_1_)
/* 53:   */   {
/* 54:57 */     return p_149299_1_ == null ? this.field_149301_b : CryptManager.decryptData(p_149299_1_, this.field_149301_b);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void processPacket(INetHandler p_148833_1_)
/* 58:   */   {
/* 59:62 */     processPacket((INetHandlerLoginServer)p_148833_1_);
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.login.client.C01PacketEncryptionResponse
 * JD-Core Version:    0.7.0.1
 */