/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class WorldGenTaiga1
/*  10:    */   extends WorldGenAbstractTree
/*  11:    */ {
/*  12:    */   private static final String __OBFID = "CL_00000427";
/*  13:    */   
/*  14:    */   public WorldGenTaiga1()
/*  15:    */   {
/*  16: 15 */     super(false);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  20:    */   {
/*  21: 20 */     int var6 = par2Random.nextInt(5) + 7;
/*  22: 21 */     int var7 = var6 - par2Random.nextInt(2) - 3;
/*  23: 22 */     int var8 = var6 - var7;
/*  24: 23 */     int var9 = 1 + par2Random.nextInt(var8 + 1);
/*  25: 24 */     boolean var10 = true;
/*  26: 26 */     if ((par4 >= 1) && (par4 + var6 + 1 <= 256))
/*  27:    */     {
/*  28: 32 */       for (int var11 = par4; (var11 <= par4 + 1 + var6) && (var10); var11++)
/*  29:    */       {
/*  30: 34 */         boolean var12 = true;
/*  31:    */         int var18;
/*  32:    */         int var18;
/*  33: 36 */         if (var11 - par4 < var7) {
/*  34: 38 */           var18 = 0;
/*  35:    */         } else {
/*  36: 42 */           var18 = var9;
/*  37:    */         }
/*  38: 45 */         for (int var13 = par3 - var18; (var13 <= par3 + var18) && (var10); var13++) {
/*  39: 47 */           for (int var14 = par5 - var18; (var14 <= par5 + var18) && (var10); var14++) {
/*  40: 49 */             if ((var11 >= 0) && (var11 < 256))
/*  41:    */             {
/*  42: 51 */               Block var15 = par1World.getBlock(var13, var11, var14);
/*  43: 53 */               if (!func_150523_a(var15)) {
/*  44: 55 */                 var10 = false;
/*  45:    */               }
/*  46:    */             }
/*  47:    */             else
/*  48:    */             {
/*  49: 60 */               var10 = false;
/*  50:    */             }
/*  51:    */           }
/*  52:    */         }
/*  53:    */       }
/*  54: 66 */       if (!var10) {
/*  55: 68 */         return false;
/*  56:    */       }
/*  57: 72 */       Block var19 = par1World.getBlock(par3, par4 - 1, par5);
/*  58: 74 */       if (((var19 == Blocks.grass) || (var19 == Blocks.dirt)) && (par4 < 256 - var6 - 1))
/*  59:    */       {
/*  60: 76 */         func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
/*  61: 77 */         int var18 = 0;
/*  62: 79 */         for (int var13 = par4 + var6; var13 >= par4 + var7; var13--)
/*  63:    */         {
/*  64: 81 */           for (int var14 = par3 - var18; var14 <= par3 + var18; var14++)
/*  65:    */           {
/*  66: 83 */             int var21 = var14 - par3;
/*  67: 85 */             for (int var16 = par5 - var18; var16 <= par5 + var18; var16++)
/*  68:    */             {
/*  69: 87 */               int var17 = var16 - par5;
/*  70: 89 */               if (((Math.abs(var21) != var18) || (Math.abs(var17) != var18) || (var18 <= 0)) && (!par1World.getBlock(var14, var13, var16).func_149730_j())) {
/*  71: 91 */                 func_150516_a(par1World, var14, var13, var16, Blocks.leaves, 1);
/*  72:    */               }
/*  73:    */             }
/*  74:    */           }
/*  75: 96 */           if ((var18 >= 1) && (var13 == par4 + var7 + 1)) {
/*  76: 98 */             var18--;
/*  77:100 */           } else if (var18 < var9) {
/*  78:102 */             var18++;
/*  79:    */           }
/*  80:    */         }
/*  81:106 */         for (var13 = 0; var13 < var6 - 1; var13++)
/*  82:    */         {
/*  83:108 */           Block var20 = par1World.getBlock(par3, par4 + var13, par5);
/*  84:110 */           if ((var20.getMaterial() == Material.air) || (var20.getMaterial() == Material.leaves)) {
/*  85:112 */             func_150516_a(par1World, par3, par4 + var13, par5, Blocks.log, 1);
/*  86:    */           }
/*  87:    */         }
/*  88:116 */         return true;
/*  89:    */       }
/*  90:120 */       return false;
/*  91:    */     }
/*  92:126 */     return false;
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenTaiga1
 * JD-Core Version:    0.7.0.1
 */