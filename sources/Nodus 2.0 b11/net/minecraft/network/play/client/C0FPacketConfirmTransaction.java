/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  8:   */ 
/*  9:   */ public class C0FPacketConfirmTransaction
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_149536_a;
/* 13:   */   private short field_149534_b;
/* 14:   */   private boolean field_149535_c;
/* 15:   */   private static final String __OBFID = "CL_00001351";
/* 16:   */   
/* 17:   */   public C0FPacketConfirmTransaction() {}
/* 18:   */   
/* 19:   */   public C0FPacketConfirmTransaction(int p_i45244_1_, short p_i45244_2_, boolean p_i45244_3_)
/* 20:   */   {
/* 21:20 */     this.field_149536_a = p_i45244_1_;
/* 22:21 */     this.field_149534_b = p_i45244_2_;
/* 23:22 */     this.field_149535_c = p_i45244_3_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void processPacket(INetHandlerPlayServer p_149531_1_)
/* 27:   */   {
/* 28:27 */     p_149531_1_.processConfirmTransaction(this);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:35 */     this.field_149536_a = p_148837_1_.readByte();
/* 35:36 */     this.field_149534_b = p_148837_1_.readShort();
/* 36:37 */     this.field_149535_c = (p_148837_1_.readByte() != 0);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 40:   */     throws IOException
/* 41:   */   {
/* 42:45 */     p_148840_1_.writeByte(this.field_149536_a);
/* 43:46 */     p_148840_1_.writeShort(this.field_149534_b);
/* 44:47 */     p_148840_1_.writeByte(this.field_149535_c ? 1 : 0);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String serialize()
/* 48:   */   {
/* 49:55 */     return String.format("id=%d, uid=%d, accepted=%b", new Object[] { Integer.valueOf(this.field_149536_a), Short.valueOf(this.field_149534_b), Boolean.valueOf(this.field_149535_c) });
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int func_149532_c()
/* 53:   */   {
/* 54:60 */     return this.field_149536_a;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public short func_149533_d()
/* 58:   */   {
/* 59:65 */     return this.field_149534_b;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void processPacket(INetHandler p_148833_1_)
/* 63:   */   {
/* 64:70 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C0FPacketConfirmTransaction
 * JD-Core Version:    0.7.0.1
 */