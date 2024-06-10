/*  1:   */ package net.minecraft.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ import net.minecraft.nbt.NBTTagCompound;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.play.server.S33PacketUpdateSign;
/*  7:   */ 
/*  8:   */ public class TileEntitySign
/*  9:   */   extends TileEntity
/* 10:   */ {
/* 11:10 */   public String[] field_145915_a = { "", "", "", "" };
/* 12:11 */   public int field_145918_i = -1;
/* 13:12 */   private boolean field_145916_j = true;
/* 14:   */   private EntityPlayer field_145917_k;
/* 15:   */   private static final String __OBFID = "CL_00000363";
/* 16:   */   
/* 17:   */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 18:   */   {
/* 19:18 */     super.writeToNBT(p_145841_1_);
/* 20:19 */     p_145841_1_.setString("Text1", this.field_145915_a[0]);
/* 21:20 */     p_145841_1_.setString("Text2", this.field_145915_a[1]);
/* 22:21 */     p_145841_1_.setString("Text3", this.field_145915_a[2]);
/* 23:22 */     p_145841_1_.setString("Text4", this.field_145915_a[3]);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void readFromNBT(NBTTagCompound p_145839_1_)
/* 27:   */   {
/* 28:27 */     this.field_145916_j = false;
/* 29:28 */     super.readFromNBT(p_145839_1_);
/* 30:30 */     for (int var2 = 0; var2 < 4; var2++)
/* 31:   */     {
/* 32:32 */       this.field_145915_a[var2] = p_145839_1_.getString("Text" + (var2 + 1));
/* 33:34 */       if (this.field_145915_a[var2].length() > 15) {
/* 34:36 */         this.field_145915_a[var2] = this.field_145915_a[var2].substring(0, 15);
/* 35:   */       }
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Packet getDescriptionPacket()
/* 40:   */   {
/* 41:46 */     String[] var1 = new String[4];
/* 42:47 */     System.arraycopy(this.field_145915_a, 0, var1, 0, 4);
/* 43:48 */     return new S33PacketUpdateSign(this.field_145851_c, this.field_145848_d, this.field_145849_e, var1);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean func_145914_a()
/* 47:   */   {
/* 48:53 */     return this.field_145916_j;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void func_145913_a(boolean p_145913_1_)
/* 52:   */   {
/* 53:58 */     this.field_145916_j = p_145913_1_;
/* 54:60 */     if (!p_145913_1_) {
/* 55:62 */       this.field_145917_k = null;
/* 56:   */     }
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void func_145912_a(EntityPlayer p_145912_1_)
/* 60:   */   {
/* 61:68 */     this.field_145917_k = p_145912_1_;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public EntityPlayer func_145911_b()
/* 65:   */   {
/* 66:73 */     return this.field_145917_k;
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntitySign
 * JD-Core Version:    0.7.0.1
 */