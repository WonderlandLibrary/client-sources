/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.util.MathHelper;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class WorldGenMegaPineTree
/*  11:    */   extends WorldGenHugeTrees
/*  12:    */ {
/*  13:    */   private boolean field_150542_e;
/*  14:    */   private static final String __OBFID = "CL_00000421";
/*  15:    */   
/*  16:    */   public WorldGenMegaPineTree(boolean p_i45457_1_, boolean p_i45457_2_)
/*  17:    */   {
/*  18: 17 */     super(p_i45457_1_, 13, 15, 1, 1);
/*  19: 18 */     this.field_150542_e = p_i45457_2_;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  23:    */   {
/*  24: 23 */     int var6 = func_150533_a(par2Random);
/*  25: 25 */     if (!func_150537_a(par1World, par2Random, par3, par4, par5, var6)) {
/*  26: 27 */       return false;
/*  27:    */     }
/*  28: 31 */     func_150541_c(par1World, par3, par5, par4 + var6, 0, par2Random);
/*  29: 33 */     for (int var7 = 0; var7 < var6; var7++)
/*  30:    */     {
/*  31: 35 */       Block var8 = par1World.getBlock(par3, par4 + var7, par5);
/*  32: 37 */       if ((var8.getMaterial() == Material.air) || (var8.getMaterial() == Material.leaves)) {
/*  33: 39 */         func_150516_a(par1World, par3, par4 + var7, par5, Blocks.log, this.woodMetadata);
/*  34:    */       }
/*  35: 42 */       if (var7 < var6 - 1)
/*  36:    */       {
/*  37: 44 */         var8 = par1World.getBlock(par3 + 1, par4 + var7, par5);
/*  38: 46 */         if ((var8.getMaterial() == Material.air) || (var8.getMaterial() == Material.leaves)) {
/*  39: 48 */           func_150516_a(par1World, par3 + 1, par4 + var7, par5, Blocks.log, this.woodMetadata);
/*  40:    */         }
/*  41: 51 */         var8 = par1World.getBlock(par3 + 1, par4 + var7, par5 + 1);
/*  42: 53 */         if ((var8.getMaterial() == Material.air) || (var8.getMaterial() == Material.leaves)) {
/*  43: 55 */           func_150516_a(par1World, par3 + 1, par4 + var7, par5 + 1, Blocks.log, this.woodMetadata);
/*  44:    */         }
/*  45: 58 */         var8 = par1World.getBlock(par3, par4 + var7, par5 + 1);
/*  46: 60 */         if ((var8.getMaterial() == Material.air) || (var8.getMaterial() == Material.leaves)) {
/*  47: 62 */           func_150516_a(par1World, par3, par4 + var7, par5 + 1, Blocks.log, this.woodMetadata);
/*  48:    */         }
/*  49:    */       }
/*  50:    */     }
/*  51: 67 */     return true;
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void func_150541_c(World p_150541_1_, int p_150541_2_, int p_150541_3_, int p_150541_4_, int p_150541_5_, Random p_150541_6_)
/*  55:    */   {
/*  56: 73 */     int var7 = p_150541_6_.nextInt(5);
/*  57: 75 */     if (this.field_150542_e) {
/*  58: 77 */       var7 += this.baseHeight;
/*  59:    */     } else {
/*  60: 81 */       var7 += 3;
/*  61:    */     }
/*  62: 84 */     int var8 = 0;
/*  63: 86 */     for (int var9 = p_150541_4_ - var7; var9 <= p_150541_4_; var9++)
/*  64:    */     {
/*  65: 88 */       int var10 = p_150541_4_ - var9;
/*  66: 89 */       int var11 = p_150541_5_ + MathHelper.floor_float(var10 / var7 * 3.5F);
/*  67: 90 */       func_150535_a(p_150541_1_, p_150541_2_, var9, p_150541_3_, var11 + ((var10 > 0) && (var11 == var8) && ((var9 & 0x1) == 0) ? 1 : 0), p_150541_6_);
/*  68: 91 */       var8 = var11;
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void func_150524_b(World p_150524_1_, Random p_150524_2_, int p_150524_3_, int p_150524_4_, int p_150524_5_)
/*  73:    */   {
/*  74: 97 */     func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ - 1, p_150524_4_, p_150524_5_ - 1);
/*  75: 98 */     func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ + 2, p_150524_4_, p_150524_5_ - 1);
/*  76: 99 */     func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ - 1, p_150524_4_, p_150524_5_ + 2);
/*  77:100 */     func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ + 2, p_150524_4_, p_150524_5_ + 2);
/*  78:102 */     for (int var6 = 0; var6 < 5; var6++)
/*  79:    */     {
/*  80:104 */       int var7 = p_150524_2_.nextInt(64);
/*  81:105 */       int var8 = var7 % 8;
/*  82:106 */       int var9 = var7 / 8;
/*  83:108 */       if ((var8 == 0) || (var8 == 7) || (var9 == 0) || (var9 == 7)) {
/*  84:110 */         func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ - 3 + var8, p_150524_4_, p_150524_5_ - 3 + var9);
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   private void func_150539_c(World p_150539_1_, Random p_150539_2_, int p_150539_3_, int p_150539_4_, int p_150539_5_)
/*  90:    */   {
/*  91:117 */     for (int var6 = -2; var6 <= 2; var6++) {
/*  92:119 */       for (int var7 = -2; var7 <= 2; var7++) {
/*  93:121 */         if ((Math.abs(var6) != 2) || (Math.abs(var7) != 2)) {
/*  94:123 */           func_150540_a(p_150539_1_, p_150539_3_ + var6, p_150539_4_, p_150539_5_ + var7);
/*  95:    */         }
/*  96:    */       }
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   private void func_150540_a(World p_150540_1_, int p_150540_2_, int p_150540_3_, int p_150540_4_)
/* 101:    */   {
/* 102:131 */     for (int var5 = p_150540_3_ + 2; var5 >= p_150540_3_ - 3; var5--)
/* 103:    */     {
/* 104:133 */       Block var6 = p_150540_1_.getBlock(p_150540_2_, var5, p_150540_4_);
/* 105:135 */       if ((var6 == Blocks.grass) || (var6 == Blocks.dirt)) {
/* 106:137 */         func_150516_a(p_150540_1_, p_150540_2_, var5, p_150540_4_, Blocks.dirt, 2);
/* 107:    */       } else {
/* 108:141 */         if ((var6.getMaterial() != Material.air) && (var5 < p_150540_3_)) {
/* 109:    */           break;
/* 110:    */         }
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenMegaPineTree
 * JD-Core Version:    0.7.0.1
 */