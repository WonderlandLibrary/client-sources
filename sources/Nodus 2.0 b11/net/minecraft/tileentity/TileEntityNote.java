/*  1:   */ package net.minecraft.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.nbt.NBTTagCompound;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class TileEntityNote
/* 10:   */   extends TileEntity
/* 11:   */ {
/* 12:   */   public byte field_145879_a;
/* 13:   */   public boolean field_145880_i;
/* 14:   */   private static final String __OBFID = "CL_00000362";
/* 15:   */   
/* 16:   */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 17:   */   {
/* 18:16 */     super.writeToNBT(p_145841_1_);
/* 19:17 */     p_145841_1_.setByte("note", this.field_145879_a);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void readFromNBT(NBTTagCompound p_145839_1_)
/* 23:   */   {
/* 24:22 */     super.readFromNBT(p_145839_1_);
/* 25:23 */     this.field_145879_a = p_145839_1_.getByte("note");
/* 26:25 */     if (this.field_145879_a < 0) {
/* 27:27 */       this.field_145879_a = 0;
/* 28:   */     }
/* 29:30 */     if (this.field_145879_a > 24) {
/* 30:32 */       this.field_145879_a = 24;
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void func_145877_a()
/* 35:   */   {
/* 36:38 */     this.field_145879_a = ((byte)((this.field_145879_a + 1) % 25));
/* 37:39 */     onInventoryChanged();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void func_145878_a(World p_145878_1_, int p_145878_2_, int p_145878_3_, int p_145878_4_)
/* 41:   */   {
/* 42:44 */     if (p_145878_1_.getBlock(p_145878_2_, p_145878_3_ + 1, p_145878_4_).getMaterial() == Material.air)
/* 43:   */     {
/* 44:46 */       Material var5 = p_145878_1_.getBlock(p_145878_2_, p_145878_3_ - 1, p_145878_4_).getMaterial();
/* 45:47 */       byte var6 = 0;
/* 46:49 */       if (var5 == Material.rock) {
/* 47:51 */         var6 = 1;
/* 48:   */       }
/* 49:54 */       if (var5 == Material.sand) {
/* 50:56 */         var6 = 2;
/* 51:   */       }
/* 52:59 */       if (var5 == Material.glass) {
/* 53:61 */         var6 = 3;
/* 54:   */       }
/* 55:64 */       if (var5 == Material.wood) {
/* 56:66 */         var6 = 4;
/* 57:   */       }
/* 58:69 */       p_145878_1_.func_147452_c(p_145878_2_, p_145878_3_, p_145878_4_, Blocks.noteblock, var6, this.field_145879_a);
/* 59:   */     }
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityNote
 * JD-Core Version:    0.7.0.1
 */