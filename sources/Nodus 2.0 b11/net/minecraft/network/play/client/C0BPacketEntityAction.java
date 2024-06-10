/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  9:   */ 
/* 10:   */ public class C0BPacketEntityAction
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_149517_a;
/* 14:   */   private int field_149515_b;
/* 15:   */   private int field_149516_c;
/* 16:   */   private static final String __OBFID = "CL_00001366";
/* 17:   */   
/* 18:   */   public C0BPacketEntityAction() {}
/* 19:   */   
/* 20:   */   public C0BPacketEntityAction(Entity p_i45259_1_, int p_i45259_2_)
/* 21:   */   {
/* 22:21 */     this(p_i45259_1_, p_i45259_2_, 0);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public C0BPacketEntityAction(Entity p_i45260_1_, int p_i45260_2_, int p_i45260_3_)
/* 26:   */   {
/* 27:26 */     this.field_149517_a = p_i45260_1_.getEntityId();
/* 28:27 */     this.field_149515_b = p_i45260_2_;
/* 29:28 */     this.field_149516_c = p_i45260_3_;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:36 */     this.field_149517_a = p_148837_1_.readInt();
/* 36:37 */     this.field_149515_b = p_148837_1_.readByte();
/* 37:38 */     this.field_149516_c = p_148837_1_.readInt();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 41:   */     throws IOException
/* 42:   */   {
/* 43:46 */     p_148840_1_.writeInt(this.field_149517_a);
/* 44:47 */     p_148840_1_.writeByte(this.field_149515_b);
/* 45:48 */     p_148840_1_.writeInt(this.field_149516_c);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void processPacket(INetHandlerPlayServer p_149514_1_)
/* 49:   */   {
/* 50:53 */     p_149514_1_.processEntityAction(this);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public int func_149513_d()
/* 54:   */   {
/* 55:58 */     return this.field_149515_b;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int func_149512_e()
/* 59:   */   {
/* 60:63 */     return this.field_149516_c;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void processPacket(INetHandler p_148833_1_)
/* 64:   */   {
/* 65:68 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C0BPacketEntityAction
 * JD-Core Version:    0.7.0.1
 */