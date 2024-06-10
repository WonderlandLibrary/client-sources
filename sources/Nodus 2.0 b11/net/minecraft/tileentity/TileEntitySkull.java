/*  1:   */ package net.minecraft.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.nbt.NBTTagCompound;
/*  4:   */ import net.minecraft.network.Packet;
/*  5:   */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  6:   */ 
/*  7:   */ public class TileEntitySkull
/*  8:   */   extends TileEntity
/*  9:   */ {
/* 10:   */   private int field_145908_a;
/* 11:   */   private int field_145910_i;
/* 12:11 */   private String field_145909_j = "";
/* 13:   */   private static final String __OBFID = "CL_00000364";
/* 14:   */   
/* 15:   */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 16:   */   {
/* 17:16 */     super.writeToNBT(p_145841_1_);
/* 18:17 */     p_145841_1_.setByte("SkullType", (byte)(this.field_145908_a & 0xFF));
/* 19:18 */     p_145841_1_.setByte("Rot", (byte)(this.field_145910_i & 0xFF));
/* 20:19 */     p_145841_1_.setString("ExtraType", this.field_145909_j);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void readFromNBT(NBTTagCompound p_145839_1_)
/* 24:   */   {
/* 25:24 */     super.readFromNBT(p_145839_1_);
/* 26:25 */     this.field_145908_a = p_145839_1_.getByte("SkullType");
/* 27:26 */     this.field_145910_i = p_145839_1_.getByte("Rot");
/* 28:28 */     if (p_145839_1_.func_150297_b("ExtraType", 8)) {
/* 29:30 */       this.field_145909_j = p_145839_1_.getString("ExtraType");
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Packet getDescriptionPacket()
/* 34:   */   {
/* 35:39 */     NBTTagCompound var1 = new NBTTagCompound();
/* 36:40 */     writeToNBT(var1);
/* 37:41 */     return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 4, var1);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void func_145905_a(int p_145905_1_, String p_145905_2_)
/* 41:   */   {
/* 42:46 */     this.field_145908_a = p_145905_1_;
/* 43:47 */     this.field_145909_j = p_145905_2_;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public int func_145904_a()
/* 47:   */   {
/* 48:52 */     return this.field_145908_a;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public int func_145906_b()
/* 52:   */   {
/* 53:57 */     return this.field_145910_i;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void func_145903_a(int p_145903_1_)
/* 57:   */   {
/* 58:62 */     this.field_145910_i = p_145903_1_;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String func_145907_c()
/* 62:   */   {
/* 63:67 */     return this.field_145909_j;
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntitySkull
 * JD-Core Version:    0.7.0.1
 */