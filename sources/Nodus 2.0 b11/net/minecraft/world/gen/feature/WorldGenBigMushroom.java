/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class WorldGenBigMushroom
/*  10:    */   extends WorldGenerator
/*  11:    */ {
/*  12: 12 */   private int mushroomType = -1;
/*  13:    */   private static final String __OBFID = "CL_00000415";
/*  14:    */   
/*  15:    */   public WorldGenBigMushroom(int par1)
/*  16:    */   {
/*  17: 17 */     super(true);
/*  18: 18 */     this.mushroomType = par1;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public WorldGenBigMushroom()
/*  22:    */   {
/*  23: 23 */     super(false);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  27:    */   {
/*  28: 28 */     int var6 = par2Random.nextInt(2);
/*  29: 30 */     if (this.mushroomType >= 0) {
/*  30: 32 */       var6 = this.mushroomType;
/*  31:    */     }
/*  32: 35 */     int var7 = par2Random.nextInt(3) + 4;
/*  33: 36 */     boolean var8 = true;
/*  34: 38 */     if ((par4 >= 1) && (par4 + var7 + 1 < 256))
/*  35:    */     {
/*  36: 43 */       for (int var9 = par4; var9 <= par4 + 1 + var7; var9++)
/*  37:    */       {
/*  38: 45 */         byte var10 = 3;
/*  39: 47 */         if (var9 <= par4 + 3) {
/*  40: 49 */           var10 = 0;
/*  41:    */         }
/*  42: 52 */         for (int var11 = par3 - var10; (var11 <= par3 + var10) && (var8); var11++) {
/*  43: 54 */           for (int var12 = par5 - var10; (var12 <= par5 + var10) && (var8); var12++) {
/*  44: 56 */             if ((var9 >= 0) && (var9 < 256))
/*  45:    */             {
/*  46: 58 */               Block var13 = par1World.getBlock(var11, var9, var12);
/*  47: 60 */               if ((var13.getMaterial() != Material.air) && (var13.getMaterial() != Material.leaves)) {
/*  48: 62 */                 var8 = false;
/*  49:    */               }
/*  50:    */             }
/*  51:    */             else
/*  52:    */             {
/*  53: 67 */               var8 = false;
/*  54:    */             }
/*  55:    */           }
/*  56:    */         }
/*  57:    */       }
/*  58: 73 */       if (!var8) {
/*  59: 75 */         return false;
/*  60:    */       }
/*  61: 79 */       Block var16 = par1World.getBlock(par3, par4 - 1, par5);
/*  62: 81 */       if ((var16 != Blocks.dirt) && (var16 != Blocks.grass) && (var16 != Blocks.mycelium)) {
/*  63: 83 */         return false;
/*  64:    */       }
/*  65: 87 */       int var17 = par4 + var7;
/*  66: 89 */       if (var6 == 1) {
/*  67: 91 */         var17 = par4 + var7 - 3;
/*  68:    */       }
/*  69: 94 */       for (int var11 = var17; var11 <= par4 + var7; var11++)
/*  70:    */       {
/*  71: 96 */         int var12 = 1;
/*  72: 98 */         if (var11 < par4 + var7) {
/*  73:100 */           var12++;
/*  74:    */         }
/*  75:103 */         if (var6 == 0) {
/*  76:105 */           var12 = 3;
/*  77:    */         }
/*  78:108 */         for (int var19 = par3 - var12; var19 <= par3 + var12; var19++) {
/*  79:110 */           for (int var14 = par5 - var12; var14 <= par5 + var12; var14++)
/*  80:    */           {
/*  81:112 */             int var15 = 5;
/*  82:114 */             if (var19 == par3 - var12) {
/*  83:116 */               var15--;
/*  84:    */             }
/*  85:119 */             if (var19 == par3 + var12) {
/*  86:121 */               var15++;
/*  87:    */             }
/*  88:124 */             if (var14 == par5 - var12) {
/*  89:126 */               var15 -= 3;
/*  90:    */             }
/*  91:129 */             if (var14 == par5 + var12) {
/*  92:131 */               var15 += 3;
/*  93:    */             }
/*  94:134 */             if ((var6 == 0) || (var11 < par4 + var7))
/*  95:    */             {
/*  96:136 */               if (((var19 == par3 - var12) || (var19 == par3 + var12)) && ((var14 == par5 - var12) || (var14 == par5 + var12))) {
/*  97:    */                 continue;
/*  98:    */               }
/*  99:141 */               if ((var19 == par3 - (var12 - 1)) && (var14 == par5 - var12)) {
/* 100:143 */                 var15 = 1;
/* 101:    */               }
/* 102:146 */               if ((var19 == par3 - var12) && (var14 == par5 - (var12 - 1))) {
/* 103:148 */                 var15 = 1;
/* 104:    */               }
/* 105:151 */               if ((var19 == par3 + (var12 - 1)) && (var14 == par5 - var12)) {
/* 106:153 */                 var15 = 3;
/* 107:    */               }
/* 108:156 */               if ((var19 == par3 + var12) && (var14 == par5 - (var12 - 1))) {
/* 109:158 */                 var15 = 3;
/* 110:    */               }
/* 111:161 */               if ((var19 == par3 - (var12 - 1)) && (var14 == par5 + var12)) {
/* 112:163 */                 var15 = 7;
/* 113:    */               }
/* 114:166 */               if ((var19 == par3 - var12) && (var14 == par5 + (var12 - 1))) {
/* 115:168 */                 var15 = 7;
/* 116:    */               }
/* 117:171 */               if ((var19 == par3 + (var12 - 1)) && (var14 == par5 + var12)) {
/* 118:173 */                 var15 = 9;
/* 119:    */               }
/* 120:176 */               if ((var19 == par3 + var12) && (var14 == par5 + (var12 - 1))) {
/* 121:178 */                 var15 = 9;
/* 122:    */               }
/* 123:    */             }
/* 124:182 */             if ((var15 == 5) && (var11 < par4 + var7)) {
/* 125:184 */               var15 = 0;
/* 126:    */             }
/* 127:187 */             if (((var15 != 0) || (par4 >= par4 + var7 - 1)) && (!par1World.getBlock(var19, var11, var14).func_149730_j())) {
/* 128:189 */               func_150516_a(par1World, var19, var11, var14, Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + var6), var15);
/* 129:    */             }
/* 130:    */           }
/* 131:    */         }
/* 132:    */       }
/* 133:195 */       for (var11 = 0; var11 < var7; var11++)
/* 134:    */       {
/* 135:197 */         Block var18 = par1World.getBlock(par3, par4 + var11, par5);
/* 136:199 */         if (!var18.func_149730_j()) {
/* 137:201 */           func_150516_a(par1World, par3, par4 + var11, par5, Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + var6), 10);
/* 138:    */         }
/* 139:    */       }
/* 140:205 */       return true;
/* 141:    */     }
/* 142:211 */     return false;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenBigMushroom
 * JD-Core Version:    0.7.0.1
 */