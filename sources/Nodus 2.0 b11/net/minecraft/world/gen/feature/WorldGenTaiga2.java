/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class WorldGenTaiga2
/*  10:    */   extends WorldGenAbstractTree
/*  11:    */ {
/*  12:    */   private static final String __OBFID = "CL_00000435";
/*  13:    */   
/*  14:    */   public WorldGenTaiga2(boolean par1)
/*  15:    */   {
/*  16: 15 */     super(par1);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  20:    */   {
/*  21: 20 */     int var6 = par2Random.nextInt(4) + 6;
/*  22: 21 */     int var7 = 1 + par2Random.nextInt(2);
/*  23: 22 */     int var8 = var6 - var7;
/*  24: 23 */     int var9 = 2 + par2Random.nextInt(2);
/*  25: 24 */     boolean var10 = true;
/*  26: 26 */     if ((par4 >= 1) && (par4 + var6 + 1 <= 256))
/*  27:    */     {
/*  28: 31 */       for (int var11 = par4; (var11 <= par4 + 1 + var6) && (var10); var11++)
/*  29:    */       {
/*  30: 33 */         boolean var12 = true;
/*  31:    */         int var21;
/*  32:    */         int var21;
/*  33: 35 */         if (var11 - par4 < var7) {
/*  34: 37 */           var21 = 0;
/*  35:    */         } else {
/*  36: 41 */           var21 = var9;
/*  37:    */         }
/*  38: 44 */         for (int var13 = par3 - var21; (var13 <= par3 + var21) && (var10); var13++) {
/*  39: 46 */           for (int var14 = par5 - var21; (var14 <= par5 + var21) && (var10); var14++) {
/*  40: 48 */             if ((var11 >= 0) && (var11 < 256))
/*  41:    */             {
/*  42: 50 */               Block var15 = par1World.getBlock(var13, var11, var14);
/*  43: 52 */               if ((var15.getMaterial() != Material.air) && (var15.getMaterial() != Material.leaves)) {
/*  44: 54 */                 var10 = false;
/*  45:    */               }
/*  46:    */             }
/*  47:    */             else
/*  48:    */             {
/*  49: 59 */               var10 = false;
/*  50:    */             }
/*  51:    */           }
/*  52:    */         }
/*  53:    */       }
/*  54: 65 */       if (!var10) {
/*  55: 67 */         return false;
/*  56:    */       }
/*  57: 71 */       Block var22 = par1World.getBlock(par3, par4 - 1, par5);
/*  58: 73 */       if (((var22 == Blocks.grass) || (var22 == Blocks.dirt) || (var22 == Blocks.farmland)) && (par4 < 256 - var6 - 1))
/*  59:    */       {
/*  60: 75 */         func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
/*  61: 76 */         int var21 = par2Random.nextInt(2);
/*  62: 77 */         int var13 = 1;
/*  63: 78 */         byte var24 = 0;
/*  64: 82 */         for (int var23 = 0; var23 <= var8; var23++)
/*  65:    */         {
/*  66: 84 */           int var16 = par4 + var6 - var23;
/*  67: 86 */           for (int var17 = par3 - var21; var17 <= par3 + var21; var17++)
/*  68:    */           {
/*  69: 88 */             int var18 = var17 - par3;
/*  70: 90 */             for (int var19 = par5 - var21; var19 <= par5 + var21; var19++)
/*  71:    */             {
/*  72: 92 */               int var20 = var19 - par5;
/*  73: 94 */               if (((Math.abs(var18) != var21) || (Math.abs(var20) != var21) || (var21 <= 0)) && (!par1World.getBlock(var17, var16, var19).func_149730_j())) {
/*  74: 96 */                 func_150516_a(par1World, var17, var16, var19, Blocks.leaves, 1);
/*  75:    */               }
/*  76:    */             }
/*  77:    */           }
/*  78:101 */           if (var21 >= var13)
/*  79:    */           {
/*  80:103 */             var21 = var24;
/*  81:104 */             var24 = 1;
/*  82:105 */             var13++;
/*  83:107 */             if (var13 > var9) {
/*  84:109 */               var13 = var9;
/*  85:    */             }
/*  86:    */           }
/*  87:    */           else
/*  88:    */           {
/*  89:114 */             var21++;
/*  90:    */           }
/*  91:    */         }
/*  92:118 */         var23 = par2Random.nextInt(3);
/*  93:120 */         for (int var16 = 0; var16 < var6 - var23; var16++)
/*  94:    */         {
/*  95:122 */           Block var25 = par1World.getBlock(par3, par4 + var16, par5);
/*  96:124 */           if ((var25.getMaterial() == Material.air) || (var25.getMaterial() == Material.leaves)) {
/*  97:126 */             func_150516_a(par1World, par3, par4 + var16, par5, Blocks.log, 1);
/*  98:    */           }
/*  99:    */         }
/* 100:130 */         return true;
/* 101:    */       }
/* 102:134 */       return false;
/* 103:    */     }
/* 104:140 */     return false;
/* 105:    */   }
/* 106:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenTaiga2
 * JD-Core Version:    0.7.0.1
 */