/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ import net.minecraft.network.INetHandler;
/*  7:   */ import net.minecraft.network.Packet;
/*  8:   */ import net.minecraft.network.PacketBuffer;
/*  9:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/* 10:   */ 
/* 11:   */ public class S30PacketWindowItems
/* 12:   */   extends Packet
/* 13:   */ {
/* 14:   */   private int field_148914_a;
/* 15:   */   private ItemStack[] field_148913_b;
/* 16:   */   private static final String __OBFID = "CL_00001294";
/* 17:   */   
/* 18:   */   public S30PacketWindowItems() {}
/* 19:   */   
/* 20:   */   public S30PacketWindowItems(int p_i45186_1_, List p_i45186_2_)
/* 21:   */   {
/* 22:21 */     this.field_148914_a = p_i45186_1_;
/* 23:22 */     this.field_148913_b = new ItemStack[p_i45186_2_.size()];
/* 24:24 */     for (int var3 = 0; var3 < this.field_148913_b.length; var3++)
/* 25:   */     {
/* 26:26 */       ItemStack var4 = (ItemStack)p_i45186_2_.get(var3);
/* 27:27 */       this.field_148913_b[var3] = (var4 == null ? null : var4.copy());
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:36 */     this.field_148914_a = p_148837_1_.readUnsignedByte();
/* 35:37 */     short var2 = p_148837_1_.readShort();
/* 36:38 */     this.field_148913_b = new ItemStack[var2];
/* 37:40 */     for (int var3 = 0; var3 < var2; var3++) {
/* 38:42 */       this.field_148913_b[var3] = p_148837_1_.readItemStackFromBuffer();
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 43:   */     throws IOException
/* 44:   */   {
/* 45:51 */     p_148840_1_.writeByte(this.field_148914_a);
/* 46:52 */     p_148840_1_.writeShort(this.field_148913_b.length);
/* 47:53 */     ItemStack[] var2 = this.field_148913_b;
/* 48:54 */     int var3 = var2.length;
/* 49:56 */     for (int var4 = 0; var4 < var3; var4++)
/* 50:   */     {
/* 51:58 */       ItemStack var5 = var2[var4];
/* 52:59 */       p_148840_1_.writeItemStackToBuffer(var5);
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void processPacket(INetHandlerPlayClient p_148912_1_)
/* 57:   */   {
/* 58:65 */     p_148912_1_.handleWindowItems(this);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int func_148911_c()
/* 62:   */   {
/* 63:70 */     return this.field_148914_a;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public ItemStack[] func_148910_d()
/* 67:   */   {
/* 68:75 */     return this.field_148913_b;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public void processPacket(INetHandler p_148833_1_)
/* 72:   */   {
/* 73:80 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S30PacketWindowItems
 * JD-Core Version:    0.7.0.1
 */