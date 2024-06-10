/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.util.MathHelper;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class WorldGenIceSpike
/*  11:    */   extends WorldGenerator
/*  12:    */ {
/*  13:    */   private static final String __OBFID = "CL_00000417";
/*  14:    */   
/*  15:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  16:    */   {
/*  17: 16 */     while ((par1World.isAirBlock(par3, par4, par5)) && (par4 > 2)) {
/*  18: 18 */       par4--;
/*  19:    */     }
/*  20: 21 */     if (par1World.getBlock(par3, par4, par5) != Blocks.snow) {
/*  21: 23 */       return false;
/*  22:    */     }
/*  23: 27 */     par4 += par2Random.nextInt(4);
/*  24: 28 */     int var6 = par2Random.nextInt(4) + 7;
/*  25: 29 */     int var7 = var6 / 4 + par2Random.nextInt(2);
/*  26: 31 */     if ((var7 > 1) && (par2Random.nextInt(60) == 0)) {
/*  27: 33 */       par4 += 10 + par2Random.nextInt(30);
/*  28:    */     }
/*  29: 40 */     for (int var8 = 0; var8 < var6; var8++)
/*  30:    */     {
/*  31: 42 */       float var9 = (1.0F - var8 / var6) * var7;
/*  32: 43 */       int var10 = MathHelper.ceiling_float_int(var9);
/*  33: 45 */       for (int var11 = -var10; var11 <= var10; var11++)
/*  34:    */       {
/*  35: 47 */         float var12 = MathHelper.abs_int(var11) - 0.25F;
/*  36: 49 */         for (int var13 = -var10; var13 <= var10; var13++)
/*  37:    */         {
/*  38: 51 */           float var14 = MathHelper.abs_int(var13) - 0.25F;
/*  39: 53 */           if (((var11 == 0) && (var13 == 0)) || ((var12 * var12 + var14 * var14 <= var9 * var9) && (((var11 != -var10) && (var11 != var10) && (var13 != -var10) && (var13 != var10)) || (par2Random.nextFloat() <= 0.75F))))
/*  40:    */           {
/*  41: 55 */             Block var15 = par1World.getBlock(par3 + var11, par4 + var8, par5 + var13);
/*  42: 57 */             if ((var15.getMaterial() == Material.air) || (var15 == Blocks.dirt) || (var15 == Blocks.snow) || (var15 == Blocks.ice)) {
/*  43: 59 */               func_150515_a(par1World, par3 + var11, par4 + var8, par5 + var13, Blocks.packed_ice);
/*  44:    */             }
/*  45: 62 */             if ((var8 != 0) && (var10 > 1))
/*  46:    */             {
/*  47: 64 */               var15 = par1World.getBlock(par3 + var11, par4 - var8, par5 + var13);
/*  48: 66 */               if ((var15.getMaterial() == Material.air) || (var15 == Blocks.dirt) || (var15 == Blocks.snow) || (var15 == Blocks.ice)) {
/*  49: 68 */                 func_150515_a(par1World, par3 + var11, par4 - var8, par5 + var13, Blocks.packed_ice);
/*  50:    */               }
/*  51:    */             }
/*  52:    */           }
/*  53:    */         }
/*  54:    */       }
/*  55:    */     }
/*  56: 76 */     var8 = var7 - 1;
/*  57: 78 */     if (var8 < 0) {
/*  58: 80 */       var8 = 0;
/*  59: 82 */     } else if (var8 > 1) {
/*  60: 84 */       var8 = 1;
/*  61:    */     }
/*  62: 87 */     for (int var16 = -var8; var16 <= var8; var16++)
/*  63:    */     {
/*  64: 89 */       int var10 = -var8;
/*  65: 91 */       while (var10 <= var8)
/*  66:    */       {
/*  67: 93 */         int var11 = par4 - 1;
/*  68: 94 */         int var17 = 50;
/*  69: 96 */         if ((Math.abs(var16) == 1) && (Math.abs(var10) == 1)) {
/*  70: 98 */           var17 = par2Random.nextInt(5);
/*  71:    */         }
/*  72:103 */         while (var11 > 50)
/*  73:    */         {
/*  74:105 */           Block var18 = par1World.getBlock(par3 + var16, var11, par5 + var10);
/*  75:107 */           if ((var18.getMaterial() != Material.air) && (var18 != Blocks.dirt) && (var18 != Blocks.snow) && (var18 != Blocks.ice) && (var18 != Blocks.packed_ice)) {
/*  76:    */             break;
/*  77:    */           }
/*  78:109 */           func_150515_a(par1World, par3 + var16, var11, par5 + var10, Blocks.packed_ice);
/*  79:110 */           var11--;
/*  80:111 */           var17--;
/*  81:113 */           if (var17 <= 0)
/*  82:    */           {
/*  83:115 */             var11 -= par2Random.nextInt(5) + 1;
/*  84:116 */             var17 = par2Random.nextInt(5);
/*  85:    */           }
/*  86:    */         }
/*  87:123 */         var10++;
/*  88:    */       }
/*  89:    */     }
/*  90:129 */     return true;
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenIceSpike
 * JD-Core Version:    0.7.0.1
 */