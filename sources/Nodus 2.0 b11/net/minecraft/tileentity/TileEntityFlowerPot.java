/*  1:   */ package net.minecraft.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.item.Item;
/*  4:   */ import net.minecraft.nbt.NBTTagCompound;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  7:   */ 
/*  8:   */ public class TileEntityFlowerPot
/*  9:   */   extends TileEntity
/* 10:   */ {
/* 11:   */   private Item field_145967_a;
/* 12:   */   private int field_145968_i;
/* 13:   */   private static final String __OBFID = "CL_00000356";
/* 14:   */   
/* 15:   */   public TileEntityFlowerPot() {}
/* 16:   */   
/* 17:   */   public TileEntityFlowerPot(Item p_i45442_1_, int p_i45442_2_)
/* 18:   */   {
/* 19:18 */     this.field_145967_a = p_i45442_1_;
/* 20:19 */     this.field_145968_i = p_i45442_2_;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 24:   */   {
/* 25:24 */     super.writeToNBT(p_145841_1_);
/* 26:25 */     p_145841_1_.setInteger("Item", Item.getIdFromItem(this.field_145967_a));
/* 27:26 */     p_145841_1_.setInteger("Data", this.field_145968_i);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void readFromNBT(NBTTagCompound p_145839_1_)
/* 31:   */   {
/* 32:31 */     super.readFromNBT(p_145839_1_);
/* 33:32 */     this.field_145967_a = Item.getItemById(p_145839_1_.getInteger("Item"));
/* 34:33 */     this.field_145968_i = p_145839_1_.getInteger("Data");
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Packet getDescriptionPacket()
/* 38:   */   {
/* 39:41 */     NBTTagCompound var1 = new NBTTagCompound();
/* 40:42 */     writeToNBT(var1);
/* 41:43 */     return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 5, var1);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void func_145964_a(Item p_145964_1_, int p_145964_2_)
/* 45:   */   {
/* 46:48 */     this.field_145967_a = p_145964_1_;
/* 47:49 */     this.field_145968_i = p_145964_2_;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public Item func_145965_a()
/* 51:   */   {
/* 52:54 */     return this.field_145967_a;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public int func_145966_b()
/* 56:   */   {
/* 57:59 */     return this.field_145968_i;
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityFlowerPot
 * JD-Core Version:    0.7.0.1
 */