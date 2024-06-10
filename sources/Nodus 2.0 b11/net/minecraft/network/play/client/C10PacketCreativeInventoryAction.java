/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.item.ItemStack;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  9:   */ 
/* 10:   */ public class C10PacketCreativeInventoryAction
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_149629_a;
/* 14:   */   private ItemStack field_149628_b;
/* 15:   */   private static final String __OBFID = "CL_00001369";
/* 16:   */   
/* 17:   */   public C10PacketCreativeInventoryAction() {}
/* 18:   */   
/* 19:   */   public C10PacketCreativeInventoryAction(int p_i45263_1_, ItemStack p_i45263_2_)
/* 20:   */   {
/* 21:20 */     this.field_149629_a = p_i45263_1_;
/* 22:21 */     this.field_149628_b = (p_i45263_2_ != null ? p_i45263_2_.copy() : null);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void processPacket(INetHandlerPlayServer p_149626_1_)
/* 26:   */   {
/* 27:26 */     p_149626_1_.processCreativeInventoryAction(this);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:34 */     this.field_149629_a = p_148837_1_.readShort();
/* 34:35 */     this.field_149628_b = p_148837_1_.readItemStackFromBuffer();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 38:   */     throws IOException
/* 39:   */   {
/* 40:43 */     p_148840_1_.writeShort(this.field_149629_a);
/* 41:44 */     p_148840_1_.writeItemStackToBuffer(this.field_149628_b);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int func_149627_c()
/* 45:   */   {
/* 46:49 */     return this.field_149629_a;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public ItemStack func_149625_d()
/* 50:   */   {
/* 51:54 */     return this.field_149628_b;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void processPacket(INetHandler p_148833_1_)
/* 55:   */   {
/* 56:59 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C10PacketCreativeInventoryAction
 * JD-Core Version:    0.7.0.1
 */