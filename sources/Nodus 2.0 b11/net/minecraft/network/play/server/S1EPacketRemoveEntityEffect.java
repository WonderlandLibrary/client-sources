/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ import net.minecraft.potion.PotionEffect;
/*  9:   */ 
/* 10:   */ public class S1EPacketRemoveEntityEffect
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_149079_a;
/* 14:   */   private int field_149078_b;
/* 15:   */   private static final String __OBFID = "CL_00001321";
/* 16:   */   
/* 17:   */   public S1EPacketRemoveEntityEffect() {}
/* 18:   */   
/* 19:   */   public S1EPacketRemoveEntityEffect(int p_i45212_1_, PotionEffect p_i45212_2_)
/* 20:   */   {
/* 21:20 */     this.field_149079_a = p_i45212_1_;
/* 22:21 */     this.field_149078_b = p_i45212_2_.getPotionID();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 26:   */     throws IOException
/* 27:   */   {
/* 28:29 */     this.field_149079_a = p_148837_1_.readInt();
/* 29:30 */     this.field_149078_b = p_148837_1_.readUnsignedByte();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:38 */     p_148840_1_.writeInt(this.field_149079_a);
/* 36:39 */     p_148840_1_.writeByte(this.field_149078_b);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void processPacket(INetHandlerPlayClient p_149077_1_)
/* 40:   */   {
/* 41:44 */     p_149077_1_.handleRemoveEntityEffect(this);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int func_149076_c()
/* 45:   */   {
/* 46:49 */     return this.field_149079_a;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int func_149075_d()
/* 50:   */   {
/* 51:54 */     return this.field_149078_b;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void processPacket(INetHandler p_148833_1_)
/* 55:   */   {
/* 56:59 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S1EPacketRemoveEntityEffect
 * JD-Core Version:    0.7.0.1
 */