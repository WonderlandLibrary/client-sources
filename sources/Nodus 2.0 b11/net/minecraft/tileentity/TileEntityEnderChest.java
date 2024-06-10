/*   1:    */ package net.minecraft.tileentity;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.world.World;
/*   7:    */ 
/*   8:    */ public class TileEntityEnderChest
/*   9:    */   extends TileEntity
/*  10:    */ {
/*  11:    */   public float field_145972_a;
/*  12:    */   public float field_145975_i;
/*  13:    */   public int field_145973_j;
/*  14:    */   private int field_145974_k;
/*  15:    */   private static final String __OBFID = "CL_00000355";
/*  16:    */   
/*  17:    */   public void updateEntity()
/*  18:    */   {
/*  19: 16 */     super.updateEntity();
/*  20: 18 */     if (++this.field_145974_k % 20 * 4 == 0) {
/*  21: 20 */       this.worldObj.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, Blocks.ender_chest, 1, this.field_145973_j);
/*  22:    */     }
/*  23: 23 */     this.field_145975_i = this.field_145972_a;
/*  24: 24 */     float var1 = 0.1F;
/*  25: 27 */     if ((this.field_145973_j > 0) && (this.field_145972_a == 0.0F))
/*  26:    */     {
/*  27: 29 */       double var2 = this.field_145851_c + 0.5D;
/*  28: 30 */       double var4 = this.field_145849_e + 0.5D;
/*  29: 31 */       this.worldObj.playSoundEffect(var2, this.field_145848_d + 0.5D, var4, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*  30:    */     }
/*  31: 34 */     if (((this.field_145973_j == 0) && (this.field_145972_a > 0.0F)) || ((this.field_145973_j > 0) && (this.field_145972_a < 1.0F)))
/*  32:    */     {
/*  33: 36 */       float var8 = this.field_145972_a;
/*  34: 38 */       if (this.field_145973_j > 0) {
/*  35: 40 */         this.field_145972_a += var1;
/*  36:    */       } else {
/*  37: 44 */         this.field_145972_a -= var1;
/*  38:    */       }
/*  39: 47 */       if (this.field_145972_a > 1.0F) {
/*  40: 49 */         this.field_145972_a = 1.0F;
/*  41:    */       }
/*  42: 52 */       float var3 = 0.5F;
/*  43: 54 */       if ((this.field_145972_a < var3) && (var8 >= var3))
/*  44:    */       {
/*  45: 56 */         double var4 = this.field_145851_c + 0.5D;
/*  46: 57 */         double var6 = this.field_145849_e + 0.5D;
/*  47: 58 */         this.worldObj.playSoundEffect(var4, this.field_145848_d + 0.5D, var6, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*  48:    */       }
/*  49: 61 */       if (this.field_145972_a < 0.0F) {
/*  50: 63 */         this.field_145972_a = 0.0F;
/*  51:    */       }
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_)
/*  56:    */   {
/*  57: 70 */     if (p_145842_1_ == 1)
/*  58:    */     {
/*  59: 72 */       this.field_145973_j = p_145842_2_;
/*  60: 73 */       return true;
/*  61:    */     }
/*  62: 77 */     return super.receiveClientEvent(p_145842_1_, p_145842_2_);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void invalidate()
/*  66:    */   {
/*  67: 86 */     updateContainingBlockInfo();
/*  68: 87 */     super.invalidate();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void func_145969_a()
/*  72:    */   {
/*  73: 92 */     this.field_145973_j += 1;
/*  74: 93 */     this.worldObj.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, Blocks.ender_chest, 1, this.field_145973_j);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void func_145970_b()
/*  78:    */   {
/*  79: 98 */     this.field_145973_j -= 1;
/*  80: 99 */     this.worldObj.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, Blocks.ender_chest, 1, this.field_145973_j);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean func_145971_a(EntityPlayer p_145971_1_)
/*  84:    */   {
/*  85:104 */     return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this;
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityEnderChest
 * JD-Core Version:    0.7.0.1
 */