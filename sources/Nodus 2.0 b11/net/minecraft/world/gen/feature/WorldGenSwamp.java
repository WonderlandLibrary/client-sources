/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class WorldGenSwamp
/*  10:    */   extends WorldGenAbstractTree
/*  11:    */ {
/*  12:    */   private static final String __OBFID = "CL_00000436";
/*  13:    */   
/*  14:    */   public WorldGenSwamp()
/*  15:    */   {
/*  16: 15 */     super(false);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  20:    */   {
/*  21: 22 */     for (int var6 = par2Random.nextInt(4) + 5; par1World.getBlock(par3, par4 - 1, par5).getMaterial() == Material.water; par4--) {}
/*  22: 27 */     boolean var7 = true;
/*  23: 29 */     if ((par4 >= 1) && (par4 + var6 + 1 <= 256))
/*  24:    */     {
/*  25: 34 */       for (int var8 = par4; var8 <= par4 + 1 + var6; var8++)
/*  26:    */       {
/*  27: 36 */         byte var9 = 1;
/*  28: 38 */         if (var8 == par4) {
/*  29: 40 */           var9 = 0;
/*  30:    */         }
/*  31: 43 */         if (var8 >= par4 + 1 + var6 - 2) {
/*  32: 45 */           var9 = 3;
/*  33:    */         }
/*  34: 48 */         for (int var10 = par3 - var9; (var10 <= par3 + var9) && (var7); var10++) {
/*  35: 50 */           for (int var11 = par5 - var9; (var11 <= par5 + var9) && (var7); var11++) {
/*  36: 52 */             if ((var8 >= 0) && (var8 < 256))
/*  37:    */             {
/*  38: 54 */               Block var12 = par1World.getBlock(var10, var8, var11);
/*  39: 56 */               if ((var12.getMaterial() != Material.air) && (var12.getMaterial() != Material.leaves)) {
/*  40: 58 */                 if ((var12 != Blocks.water) && (var12 != Blocks.flowing_water)) {
/*  41: 60 */                   var7 = false;
/*  42: 62 */                 } else if (var8 > par4) {
/*  43: 64 */                   var7 = false;
/*  44:    */                 }
/*  45:    */               }
/*  46:    */             }
/*  47:    */             else
/*  48:    */             {
/*  49: 70 */               var7 = false;
/*  50:    */             }
/*  51:    */           }
/*  52:    */         }
/*  53:    */       }
/*  54: 76 */       if (!var7) {
/*  55: 78 */         return false;
/*  56:    */       }
/*  57: 82 */       Block var16 = par1World.getBlock(par3, par4 - 1, par5);
/*  58: 84 */       if (((var16 == Blocks.grass) || (var16 == Blocks.dirt)) && (par4 < 256 - var6 - 1))
/*  59:    */       {
/*  60: 86 */         func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
/*  61: 91 */         for (int var18 = par4 - 3 + var6; var18 <= par4 + var6; var18++)
/*  62:    */         {
/*  63: 93 */           int var10 = var18 - (par4 + var6);
/*  64: 94 */           int var11 = 2 - var10 / 2;
/*  65: 96 */           for (int var19 = par3 - var11; var19 <= par3 + var11; var19++)
/*  66:    */           {
/*  67: 98 */             int var13 = var19 - par3;
/*  68:100 */             for (int var14 = par5 - var11; var14 <= par5 + var11; var14++)
/*  69:    */             {
/*  70:102 */               int var15 = var14 - par5;
/*  71:104 */               if (((Math.abs(var13) != var11) || (Math.abs(var15) != var11) || ((par2Random.nextInt(2) != 0) && (var10 != 0))) && (!par1World.getBlock(var19, var18, var14).func_149730_j())) {
/*  72:106 */                 func_150515_a(par1World, var19, var18, var14, Blocks.leaves);
/*  73:    */               }
/*  74:    */             }
/*  75:    */           }
/*  76:    */         }
/*  77:112 */         for (var18 = 0; var18 < var6; var18++)
/*  78:    */         {
/*  79:114 */           Block var17 = par1World.getBlock(par3, par4 + var18, par5);
/*  80:116 */           if ((var17.getMaterial() == Material.air) || (var17.getMaterial() == Material.leaves) || (var17 == Blocks.flowing_water) || (var17 == Blocks.water)) {
/*  81:118 */             func_150515_a(par1World, par3, par4 + var18, par5, Blocks.log);
/*  82:    */           }
/*  83:    */         }
/*  84:122 */         for (var18 = par4 - 3 + var6; var18 <= par4 + var6; var18++)
/*  85:    */         {
/*  86:124 */           int var10 = var18 - (par4 + var6);
/*  87:125 */           int var11 = 2 - var10 / 2;
/*  88:127 */           for (int var19 = par3 - var11; var19 <= par3 + var11; var19++) {
/*  89:129 */             for (int var13 = par5 - var11; var13 <= par5 + var11; var13++) {
/*  90:131 */               if (par1World.getBlock(var19, var18, var13).getMaterial() == Material.leaves)
/*  91:    */               {
/*  92:133 */                 if ((par2Random.nextInt(4) == 0) && (par1World.getBlock(var19 - 1, var18, var13).getMaterial() == Material.air)) {
/*  93:135 */                   generateVines(par1World, var19 - 1, var18, var13, 8);
/*  94:    */                 }
/*  95:138 */                 if ((par2Random.nextInt(4) == 0) && (par1World.getBlock(var19 + 1, var18, var13).getMaterial() == Material.air)) {
/*  96:140 */                   generateVines(par1World, var19 + 1, var18, var13, 2);
/*  97:    */                 }
/*  98:143 */                 if ((par2Random.nextInt(4) == 0) && (par1World.getBlock(var19, var18, var13 - 1).getMaterial() == Material.air)) {
/*  99:145 */                   generateVines(par1World, var19, var18, var13 - 1, 1);
/* 100:    */                 }
/* 101:148 */                 if ((par2Random.nextInt(4) == 0) && (par1World.getBlock(var19, var18, var13 + 1).getMaterial() == Material.air)) {
/* 102:150 */                   generateVines(par1World, var19, var18, var13 + 1, 4);
/* 103:    */                 }
/* 104:    */               }
/* 105:    */             }
/* 106:    */           }
/* 107:    */         }
/* 108:157 */         return true;
/* 109:    */       }
/* 110:161 */       return false;
/* 111:    */     }
/* 112:167 */     return false;
/* 113:    */   }
/* 114:    */   
/* 115:    */   private void generateVines(World par1World, int par2, int par3, int par4, int par5)
/* 116:    */   {
/* 117:176 */     func_150516_a(par1World, par2, par3, par4, Blocks.vine, par5);
/* 118:177 */     int var6 = 4;
/* 119:    */     for (;;)
/* 120:    */     {
/* 121:181 */       par3--;
/* 122:183 */       if ((par1World.getBlock(par2, par3, par4).getMaterial() != Material.air) || (var6 <= 0)) {
/* 123:185 */         return;
/* 124:    */       }
/* 125:188 */       func_150516_a(par1World, par2, par3, par4, Blocks.vine, par5);
/* 126:189 */       var6--;
/* 127:    */     }
/* 128:    */   }
/* 129:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenSwamp
 * JD-Core Version:    0.7.0.1
 */