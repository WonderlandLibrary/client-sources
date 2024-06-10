/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  8:   */ 
/*  9:   */ public class C11PacketEnchantItem
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_149541_a;
/* 13:   */   private int field_149540_b;
/* 14:   */   private static final String __OBFID = "CL_00001352";
/* 15:   */   
/* 16:   */   public C11PacketEnchantItem() {}
/* 17:   */   
/* 18:   */   public C11PacketEnchantItem(int p_i45245_1_, int p_i45245_2_)
/* 19:   */   {
/* 20:19 */     this.field_149541_a = p_i45245_1_;
/* 21:20 */     this.field_149540_b = p_i45245_2_;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void processPacket(INetHandlerPlayServer p_149538_1_)
/* 25:   */   {
/* 26:25 */     p_149538_1_.processEnchantItem(this);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:33 */     this.field_149541_a = p_148837_1_.readByte();
/* 33:34 */     this.field_149540_b = p_148837_1_.readByte();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:42 */     p_148840_1_.writeByte(this.field_149541_a);
/* 40:43 */     p_148840_1_.writeByte(this.field_149540_b);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String serialize()
/* 44:   */   {
/* 45:51 */     return String.format("id=%d, button=%d", new Object[] { Integer.valueOf(this.field_149541_a), Integer.valueOf(this.field_149540_b) });
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int func_149539_c()
/* 49:   */   {
/* 50:56 */     return this.field_149541_a;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public int func_149537_d()
/* 54:   */   {
/* 55:61 */     return this.field_149540_b;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void processPacket(INetHandler p_148833_1_)
/* 59:   */   {
/* 60:66 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C11PacketEnchantItem
 * JD-Core Version:    0.7.0.1
 */