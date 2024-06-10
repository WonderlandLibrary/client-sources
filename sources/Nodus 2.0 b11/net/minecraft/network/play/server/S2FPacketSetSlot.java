/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.item.ItemStack;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  9:   */ 
/* 10:   */ public class S2FPacketSetSlot
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_149179_a;
/* 14:   */   private int field_149177_b;
/* 15:   */   private ItemStack field_149178_c;
/* 16:   */   private static final String __OBFID = "CL_00001296";
/* 17:   */   
/* 18:   */   public S2FPacketSetSlot() {}
/* 19:   */   
/* 20:   */   public S2FPacketSetSlot(int p_i45188_1_, int p_i45188_2_, ItemStack p_i45188_3_)
/* 21:   */   {
/* 22:21 */     this.field_149179_a = p_i45188_1_;
/* 23:22 */     this.field_149177_b = p_i45188_2_;
/* 24:23 */     this.field_149178_c = (p_i45188_3_ == null ? null : p_i45188_3_.copy());
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void processPacket(INetHandlerPlayClient p_149176_1_)
/* 28:   */   {
/* 29:28 */     p_149176_1_.handleSetSlot(this);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:36 */     this.field_149179_a = p_148837_1_.readUnsignedByte();
/* 36:37 */     this.field_149177_b = p_148837_1_.readShort();
/* 37:38 */     this.field_149178_c = p_148837_1_.readItemStackFromBuffer();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 41:   */     throws IOException
/* 42:   */   {
/* 43:46 */     p_148840_1_.writeByte(this.field_149179_a);
/* 44:47 */     p_148840_1_.writeShort(this.field_149177_b);
/* 45:48 */     p_148840_1_.writeItemStackToBuffer(this.field_149178_c);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int func_149175_c()
/* 49:   */   {
/* 50:53 */     return this.field_149179_a;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public int func_149173_d()
/* 54:   */   {
/* 55:58 */     return this.field_149177_b;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public ItemStack func_149174_e()
/* 59:   */   {
/* 60:63 */     return this.field_149178_c;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void processPacket(INetHandler p_148833_1_)
/* 64:   */   {
/* 65:68 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S2FPacketSetSlot
 * JD-Core Version:    0.7.0.1
 */