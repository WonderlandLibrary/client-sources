/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class WorldGenForest
/*  10:    */   extends WorldGenAbstractTree
/*  11:    */ {
/*  12:    */   private boolean field_150531_a;
/*  13:    */   private static final String __OBFID = "CL_00000401";
/*  14:    */   
/*  15:    */   public WorldGenForest(boolean p_i45449_1_, boolean p_i45449_2_)
/*  16:    */   {
/*  17: 16 */     super(p_i45449_1_);
/*  18: 17 */     this.field_150531_a = p_i45449_2_;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  22:    */   {
/*  23: 22 */     int var6 = par2Random.nextInt(3) + 5;
/*  24: 24 */     if (this.field_150531_a) {
/*  25: 26 */       var6 += par2Random.nextInt(7);
/*  26:    */     }
/*  27: 29 */     boolean var7 = true;
/*  28: 31 */     if ((par4 >= 1) && (par4 + var6 + 1 <= 256))
/*  29:    */     {
/*  30: 36 */       for (int var8 = par4; var8 <= par4 + 1 + var6; var8++)
/*  31:    */       {
/*  32: 38 */         byte var9 = 1;
/*  33: 40 */         if (var8 == par4) {
/*  34: 42 */           var9 = 0;
/*  35:    */         }
/*  36: 45 */         if (var8 >= par4 + 1 + var6 - 2) {
/*  37: 47 */           var9 = 2;
/*  38:    */         }
/*  39: 50 */         for (int var10 = par3 - var9; (var10 <= par3 + var9) && (var7); var10++) {
/*  40: 52 */           for (int var11 = par5 - var9; (var11 <= par5 + var9) && (var7); var11++) {
/*  41: 54 */             if ((var8 >= 0) && (var8 < 256))
/*  42:    */             {
/*  43: 56 */               Block var12 = par1World.getBlock(var10, var8, var11);
/*  44: 58 */               if (!func_150523_a(var12)) {
/*  45: 60 */                 var7 = false;
/*  46:    */               }
/*  47:    */             }
/*  48:    */             else
/*  49:    */             {
/*  50: 65 */               var7 = false;
/*  51:    */             }
/*  52:    */           }
/*  53:    */         }
/*  54:    */       }
/*  55: 71 */       if (!var7) {
/*  56: 73 */         return false;
/*  57:    */       }
/*  58: 77 */       Block var17 = par1World.getBlock(par3, par4 - 1, par5);
/*  59: 79 */       if (((var17 == Blocks.grass) || (var17 == Blocks.dirt) || (var17 == Blocks.farmland)) && (par4 < 256 - var6 - 1))
/*  60:    */       {
/*  61: 81 */         func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
/*  62: 84 */         for (int var19 = par4 - 3 + var6; var19 <= par4 + var6; var19++)
/*  63:    */         {
/*  64: 86 */           int var10 = var19 - (par4 + var6);
/*  65: 87 */           int var11 = 1 - var10 / 2;
/*  66: 89 */           for (int var20 = par3 - var11; var20 <= par3 + var11; var20++)
/*  67:    */           {
/*  68: 91 */             int var13 = var20 - par3;
/*  69: 93 */             for (int var14 = par5 - var11; var14 <= par5 + var11; var14++)
/*  70:    */             {
/*  71: 95 */               int var15 = var14 - par5;
/*  72: 97 */               if ((Math.abs(var13) != var11) || (Math.abs(var15) != var11) || ((par2Random.nextInt(2) != 0) && (var10 != 0)))
/*  73:    */               {
/*  74: 99 */                 Block var16 = par1World.getBlock(var20, var19, var14);
/*  75:101 */                 if ((var16.getMaterial() == Material.air) || (var16.getMaterial() == Material.leaves)) {
/*  76:103 */                   func_150516_a(par1World, var20, var19, var14, Blocks.leaves, 2);
/*  77:    */                 }
/*  78:    */               }
/*  79:    */             }
/*  80:    */           }
/*  81:    */         }
/*  82:110 */         for (var19 = 0; var19 < var6; var19++)
/*  83:    */         {
/*  84:112 */           Block var18 = par1World.getBlock(par3, par4 + var19, par5);
/*  85:114 */           if ((var18.getMaterial() == Material.air) || (var18.getMaterial() == Material.leaves)) {
/*  86:116 */             func_150516_a(par1World, par3, par4 + var19, par5, Blocks.log, 2);
/*  87:    */           }
/*  88:    */         }
/*  89:120 */         return true;
/*  90:    */       }
/*  91:124 */       return false;
/*  92:    */     }
/*  93:130 */     return false;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenForest
 * JD-Core Version:    0.7.0.1
 */