/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S32PacketConfirmTransaction
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_148894_a;
/* 13:   */   private short field_148892_b;
/* 14:   */   private boolean field_148893_c;
/* 15:   */   private static final String __OBFID = "CL_00001291";
/* 16:   */   
/* 17:   */   public S32PacketConfirmTransaction() {}
/* 18:   */   
/* 19:   */   public S32PacketConfirmTransaction(int p_i45182_1_, short p_i45182_2_, boolean p_i45182_3_)
/* 20:   */   {
/* 21:20 */     this.field_148894_a = p_i45182_1_;
/* 22:21 */     this.field_148892_b = p_i45182_2_;
/* 23:22 */     this.field_148893_c = p_i45182_3_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void processPacket(INetHandlerPlayClient p_148891_1_)
/* 27:   */   {
/* 28:27 */     p_148891_1_.handleConfirmTransaction(this);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:35 */     this.field_148894_a = p_148837_1_.readUnsignedByte();
/* 35:36 */     this.field_148892_b = p_148837_1_.readShort();
/* 36:37 */     this.field_148893_c = p_148837_1_.readBoolean();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 40:   */     throws IOException
/* 41:   */   {
/* 42:45 */     p_148840_1_.writeByte(this.field_148894_a);
/* 43:46 */     p_148840_1_.writeShort(this.field_148892_b);
/* 44:47 */     p_148840_1_.writeBoolean(this.field_148893_c);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String serialize()
/* 48:   */   {
/* 49:55 */     return String.format("id=%d, uid=%d, accepted=%b", new Object[] { Integer.valueOf(this.field_148894_a), Short.valueOf(this.field_148892_b), Boolean.valueOf(this.field_148893_c) });
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int func_148889_c()
/* 53:   */   {
/* 54:60 */     return this.field_148894_a;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public short func_148890_d()
/* 58:   */   {
/* 59:65 */     return this.field_148892_b;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public boolean func_148888_e()
/* 63:   */   {
/* 64:70 */     return this.field_148893_c;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void processPacket(INetHandler p_148833_1_)
/* 68:   */   {
/* 69:75 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S32PacketConfirmTransaction
 * JD-Core Version:    0.7.0.1
 */