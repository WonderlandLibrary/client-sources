/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.item.ItemStack;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  9:   */ 
/* 10:   */ public class S04PacketEntityEquipment
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_149394_a;
/* 14:   */   private int field_149392_b;
/* 15:   */   private ItemStack field_149393_c;
/* 16:   */   private static final String __OBFID = "CL_00001330";
/* 17:   */   
/* 18:   */   public S04PacketEntityEquipment() {}
/* 19:   */   
/* 20:   */   public S04PacketEntityEquipment(int p_i45221_1_, int p_i45221_2_, ItemStack p_i45221_3_)
/* 21:   */   {
/* 22:21 */     this.field_149394_a = p_i45221_1_;
/* 23:22 */     this.field_149392_b = p_i45221_2_;
/* 24:23 */     this.field_149393_c = (p_i45221_3_ == null ? null : p_i45221_3_.copy());
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 28:   */     throws IOException
/* 29:   */   {
/* 30:31 */     this.field_149394_a = p_148837_1_.readInt();
/* 31:32 */     this.field_149392_b = p_148837_1_.readShort();
/* 32:33 */     this.field_149393_c = p_148837_1_.readItemStackFromBuffer();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 36:   */     throws IOException
/* 37:   */   {
/* 38:41 */     p_148840_1_.writeInt(this.field_149394_a);
/* 39:42 */     p_148840_1_.writeShort(this.field_149392_b);
/* 40:43 */     p_148840_1_.writeItemStackToBuffer(this.field_149393_c);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void processPacket(INetHandlerPlayClient p_149391_1_)
/* 44:   */   {
/* 45:48 */     p_149391_1_.handleEntityEquipment(this);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public ItemStack func_149390_c()
/* 49:   */   {
/* 50:53 */     return this.field_149393_c;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String serialize()
/* 54:   */   {
/* 55:61 */     return String.format("entity=%d, slot=%d, item=%s", new Object[] { Integer.valueOf(this.field_149394_a), Integer.valueOf(this.field_149392_b), this.field_149393_c });
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int func_149389_d()
/* 59:   */   {
/* 60:66 */     return this.field_149394_a;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public int func_149388_e()
/* 64:   */   {
/* 65:71 */     return this.field_149392_b;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void processPacket(INetHandler p_148833_1_)
/* 69:   */   {
/* 70:76 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S04PacketEntityEquipment
 * JD-Core Version:    0.7.0.1
 */