/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.world.EnumSkyBlock;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  10:    */ 
/*  11:    */ public class WorldGenLakes
/*  12:    */   extends WorldGenerator
/*  13:    */ {
/*  14:    */   private Block field_150556_a;
/*  15:    */   private static final String __OBFID = "CL_00000418";
/*  16:    */   
/*  17:    */   public WorldGenLakes(Block p_i45455_1_)
/*  18:    */   {
/*  19: 18 */     this.field_150556_a = p_i45455_1_;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  23:    */   {
/*  24:    */     
/*  25: 25 */     for (par5 -= 8; (par4 > 5) && (par1World.isAirBlock(par3, par4, par5)); par4--) {}
/*  26: 30 */     if (par4 <= 4) {
/*  27: 32 */       return false;
/*  28:    */     }
/*  29: 36 */     par4 -= 4;
/*  30: 37 */     boolean[] var6 = new boolean[2048];
/*  31: 38 */     int var7 = par2Random.nextInt(4) + 4;
/*  32: 41 */     for (int var8 = 0; var8 < var7; var8++)
/*  33:    */     {
/*  34: 43 */       double var9 = par2Random.nextDouble() * 6.0D + 3.0D;
/*  35: 44 */       double var11 = par2Random.nextDouble() * 4.0D + 2.0D;
/*  36: 45 */       double var13 = par2Random.nextDouble() * 6.0D + 3.0D;
/*  37: 46 */       double var15 = par2Random.nextDouble() * (16.0D - var9 - 2.0D) + 1.0D + var9 / 2.0D;
/*  38: 47 */       double var17 = par2Random.nextDouble() * (8.0D - var11 - 4.0D) + 2.0D + var11 / 2.0D;
/*  39: 48 */       double var19 = par2Random.nextDouble() * (16.0D - var13 - 2.0D) + 1.0D + var13 / 2.0D;
/*  40: 50 */       for (int var21 = 1; var21 < 15; var21++) {
/*  41: 52 */         for (int var22 = 1; var22 < 15; var22++) {
/*  42: 54 */           for (int var23 = 1; var23 < 7; var23++)
/*  43:    */           {
/*  44: 56 */             double var24 = (var21 - var15) / (var9 / 2.0D);
/*  45: 57 */             double var26 = (var23 - var17) / (var11 / 2.0D);
/*  46: 58 */             double var28 = (var22 - var19) / (var13 / 2.0D);
/*  47: 59 */             double var30 = var24 * var24 + var26 * var26 + var28 * var28;
/*  48: 61 */             if (var30 < 1.0D) {
/*  49: 63 */               var6[((var21 * 16 + var22) * 8 + var23)] = true;
/*  50:    */             }
/*  51:    */           }
/*  52:    */         }
/*  53:    */       }
/*  54:    */     }
/*  55: 74 */     for (var8 = 0; var8 < 16; var8++) {
/*  56: 76 */       for (int var32 = 0; var32 < 16; var32++) {
/*  57: 78 */         for (int var10 = 0; var10 < 8; var10++)
/*  58:    */         {
/*  59: 80 */           boolean var33 = (var6[((var8 * 16 + var32) * 8 + var10)] == 0) && (((var8 < 15) && (var6[(((var8 + 1) * 16 + var32) * 8 + var10)] != 0)) || ((var8 > 0) && (var6[(((var8 - 1) * 16 + var32) * 8 + var10)] != 0)) || ((var32 < 15) && (var6[((var8 * 16 + var32 + 1) * 8 + var10)] != 0)) || ((var32 > 0) && (var6[((var8 * 16 + (var32 - 1)) * 8 + var10)] != 0)) || ((var10 < 7) && (var6[((var8 * 16 + var32) * 8 + var10 + 1)] != 0)) || ((var10 > 0) && (var6[((var8 * 16 + var32) * 8 + (var10 - 1))] != 0)));
/*  60: 82 */           if (var33)
/*  61:    */           {
/*  62: 84 */             Material var12 = par1World.getBlock(par3 + var8, par4 + var10, par5 + var32).getMaterial();
/*  63: 86 */             if ((var10 >= 4) && (var12.isLiquid())) {
/*  64: 88 */               return false;
/*  65:    */             }
/*  66: 91 */             if ((var10 < 4) && (!var12.isSolid()) && (par1World.getBlock(par3 + var8, par4 + var10, par5 + var32) != this.field_150556_a)) {
/*  67: 93 */               return false;
/*  68:    */             }
/*  69:    */           }
/*  70:    */         }
/*  71:    */       }
/*  72:    */     }
/*  73:100 */     for (var8 = 0; var8 < 16; var8++) {
/*  74:102 */       for (int var32 = 0; var32 < 16; var32++) {
/*  75:104 */         for (int var10 = 0; var10 < 8; var10++) {
/*  76:106 */           if (var6[((var8 * 16 + var32) * 8 + var10)] != 0) {
/*  77:108 */             par1World.setBlock(par3 + var8, par4 + var10, par5 + var32, var10 >= 4 ? Blocks.air : this.field_150556_a, 0, 2);
/*  78:    */           }
/*  79:    */         }
/*  80:    */       }
/*  81:    */     }
/*  82:114 */     for (var8 = 0; var8 < 16; var8++) {
/*  83:116 */       for (int var32 = 0; var32 < 16; var32++) {
/*  84:118 */         for (int var10 = 4; var10 < 8; var10++) {
/*  85:120 */           if ((var6[((var8 * 16 + var32) * 8 + var10)] != 0) && (par1World.getBlock(par3 + var8, par4 + var10 - 1, par5 + var32) == Blocks.dirt) && (par1World.getSavedLightValue(EnumSkyBlock.Sky, par3 + var8, par4 + var10, par5 + var32) > 0))
/*  86:    */           {
/*  87:122 */             BiomeGenBase var35 = par1World.getBiomeGenForCoords(par3 + var8, par5 + var32);
/*  88:124 */             if (var35.topBlock == Blocks.mycelium) {
/*  89:126 */               par1World.setBlock(par3 + var8, par4 + var10 - 1, par5 + var32, Blocks.mycelium, 0, 2);
/*  90:    */             } else {
/*  91:130 */               par1World.setBlock(par3 + var8, par4 + var10 - 1, par5 + var32, Blocks.grass, 0, 2);
/*  92:    */             }
/*  93:    */           }
/*  94:    */         }
/*  95:    */       }
/*  96:    */     }
/*  97:137 */     if (this.field_150556_a.getMaterial() == Material.lava) {
/*  98:139 */       for (var8 = 0; var8 < 16; var8++) {
/*  99:141 */         for (int var32 = 0; var32 < 16; var32++) {
/* 100:143 */           for (int var10 = 0; var10 < 8; var10++)
/* 101:    */           {
/* 102:145 */             boolean var33 = (var6[((var8 * 16 + var32) * 8 + var10)] == 0) && (((var8 < 15) && (var6[(((var8 + 1) * 16 + var32) * 8 + var10)] != 0)) || ((var8 > 0) && (var6[(((var8 - 1) * 16 + var32) * 8 + var10)] != 0)) || ((var32 < 15) && (var6[((var8 * 16 + var32 + 1) * 8 + var10)] != 0)) || ((var32 > 0) && (var6[((var8 * 16 + (var32 - 1)) * 8 + var10)] != 0)) || ((var10 < 7) && (var6[((var8 * 16 + var32) * 8 + var10 + 1)] != 0)) || ((var10 > 0) && (var6[((var8 * 16 + var32) * 8 + (var10 - 1))] != 0)));
/* 103:147 */             if ((var33) && ((var10 < 4) || (par2Random.nextInt(2) != 0)) && (par1World.getBlock(par3 + var8, par4 + var10, par5 + var32).getMaterial().isSolid())) {
/* 104:149 */               par1World.setBlock(par3 + var8, par4 + var10, par5 + var32, Blocks.stone, 0, 2);
/* 105:    */             }
/* 106:    */           }
/* 107:    */         }
/* 108:    */       }
/* 109:    */     }
/* 110:156 */     if (this.field_150556_a.getMaterial() == Material.water) {
/* 111:158 */       for (var8 = 0; var8 < 16; var8++) {
/* 112:160 */         for (int var32 = 0; var32 < 16; var32++)
/* 113:    */         {
/* 114:162 */           byte var34 = 4;
/* 115:164 */           if (par1World.isBlockFreezable(par3 + var8, par4 + var34, par5 + var32)) {
/* 116:166 */             par1World.setBlock(par3 + var8, par4 + var34, par5 + var32, Blocks.ice, 0, 2);
/* 117:    */           }
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:172 */     return true;
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenLakes
 * JD-Core Version:    0.7.0.1
 */