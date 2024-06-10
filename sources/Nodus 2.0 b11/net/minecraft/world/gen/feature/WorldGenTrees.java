/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class WorldGenTrees
/*  10:    */   extends WorldGenAbstractTree
/*  11:    */ {
/*  12:    */   private final int minTreeHeight;
/*  13:    */   private final boolean vinesGrow;
/*  14:    */   private final int metaWood;
/*  15:    */   private final int metaLeaves;
/*  16:    */   private static final String __OBFID = "CL_00000438";
/*  17:    */   
/*  18:    */   public WorldGenTrees(boolean par1)
/*  19:    */   {
/*  20: 27 */     this(par1, 4, 0, 0, false);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public WorldGenTrees(boolean par1, int par2, int par3, int par4, boolean par5)
/*  24:    */   {
/*  25: 32 */     super(par1);
/*  26: 33 */     this.minTreeHeight = par2;
/*  27: 34 */     this.metaWood = par3;
/*  28: 35 */     this.metaLeaves = par4;
/*  29: 36 */     this.vinesGrow = par5;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  33:    */   {
/*  34: 41 */     int var6 = par2Random.nextInt(3) + this.minTreeHeight;
/*  35: 42 */     boolean var7 = true;
/*  36: 44 */     if ((par4 >= 1) && (par4 + var6 + 1 <= 256))
/*  37:    */     {
/*  38: 50 */       for (int var8 = par4; var8 <= par4 + 1 + var6; var8++)
/*  39:    */       {
/*  40: 52 */         byte var9 = 1;
/*  41: 54 */         if (var8 == par4) {
/*  42: 56 */           var9 = 0;
/*  43:    */         }
/*  44: 59 */         if (var8 >= par4 + 1 + var6 - 2) {
/*  45: 61 */           var9 = 2;
/*  46:    */         }
/*  47: 64 */         for (int var10 = par3 - var9; (var10 <= par3 + var9) && (var7); var10++) {
/*  48: 66 */           for (int var11 = par5 - var9; (var11 <= par5 + var9) && (var7); var11++) {
/*  49: 68 */             if ((var8 >= 0) && (var8 < 256))
/*  50:    */             {
/*  51: 70 */               Block var12 = par1World.getBlock(var10, var8, var11);
/*  52: 72 */               if (!func_150523_a(var12)) {
/*  53: 74 */                 var7 = false;
/*  54:    */               }
/*  55:    */             }
/*  56:    */             else
/*  57:    */             {
/*  58: 79 */               var7 = false;
/*  59:    */             }
/*  60:    */           }
/*  61:    */         }
/*  62:    */       }
/*  63: 85 */       if (!var7) {
/*  64: 87 */         return false;
/*  65:    */       }
/*  66: 91 */       Block var19 = par1World.getBlock(par3, par4 - 1, par5);
/*  67: 93 */       if (((var19 == Blocks.grass) || (var19 == Blocks.dirt) || (var19 == Blocks.farmland)) && (par4 < 256 - var6 - 1))
/*  68:    */       {
/*  69: 95 */         func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
/*  70: 96 */         byte var9 = 3;
/*  71: 97 */         byte var20 = 0;
/*  72:103 */         for (int var11 = par4 - var9 + var6; var11 <= par4 + var6; var11++)
/*  73:    */         {
/*  74:105 */           int var21 = var11 - (par4 + var6);
/*  75:106 */           int var13 = var20 + 1 - var21 / 2;
/*  76:108 */           for (int var14 = par3 - var13; var14 <= par3 + var13; var14++)
/*  77:    */           {
/*  78:110 */             int var15 = var14 - par3;
/*  79:112 */             for (int var16 = par5 - var13; var16 <= par5 + var13; var16++)
/*  80:    */             {
/*  81:114 */               int var17 = var16 - par5;
/*  82:116 */               if ((Math.abs(var15) != var13) || (Math.abs(var17) != var13) || ((par2Random.nextInt(2) != 0) && (var21 != 0)))
/*  83:    */               {
/*  84:118 */                 Block var18 = par1World.getBlock(var14, var11, var16);
/*  85:120 */                 if ((var18.getMaterial() == Material.air) || (var18.getMaterial() == Material.leaves)) {
/*  86:122 */                   func_150516_a(par1World, var14, var11, var16, Blocks.leaves, this.metaLeaves);
/*  87:    */                 }
/*  88:    */               }
/*  89:    */             }
/*  90:    */           }
/*  91:    */         }
/*  92:129 */         for (var11 = 0; var11 < var6; var11++)
/*  93:    */         {
/*  94:131 */           Block var12 = par1World.getBlock(par3, par4 + var11, par5);
/*  95:133 */           if ((var12.getMaterial() == Material.air) || (var12.getMaterial() == Material.leaves))
/*  96:    */           {
/*  97:135 */             func_150516_a(par1World, par3, par4 + var11, par5, Blocks.log, this.metaWood);
/*  98:137 */             if ((this.vinesGrow) && (var11 > 0))
/*  99:    */             {
/* 100:139 */               if ((par2Random.nextInt(3) > 0) && (par1World.isAirBlock(par3 - 1, par4 + var11, par5))) {
/* 101:141 */                 func_150516_a(par1World, par3 - 1, par4 + var11, par5, Blocks.vine, 8);
/* 102:    */               }
/* 103:144 */               if ((par2Random.nextInt(3) > 0) && (par1World.isAirBlock(par3 + 1, par4 + var11, par5))) {
/* 104:146 */                 func_150516_a(par1World, par3 + 1, par4 + var11, par5, Blocks.vine, 2);
/* 105:    */               }
/* 106:149 */               if ((par2Random.nextInt(3) > 0) && (par1World.isAirBlock(par3, par4 + var11, par5 - 1))) {
/* 107:151 */                 func_150516_a(par1World, par3, par4 + var11, par5 - 1, Blocks.vine, 1);
/* 108:    */               }
/* 109:154 */               if ((par2Random.nextInt(3) > 0) && (par1World.isAirBlock(par3, par4 + var11, par5 + 1))) {
/* 110:156 */                 func_150516_a(par1World, par3, par4 + var11, par5 + 1, Blocks.vine, 4);
/* 111:    */               }
/* 112:    */             }
/* 113:    */           }
/* 114:    */         }
/* 115:162 */         if (this.vinesGrow)
/* 116:    */         {
/* 117:164 */           for (var11 = par4 - 3 + var6; var11 <= par4 + var6; var11++)
/* 118:    */           {
/* 119:166 */             int var21 = var11 - (par4 + var6);
/* 120:167 */             int var13 = 2 - var21 / 2;
/* 121:169 */             for (int var14 = par3 - var13; var14 <= par3 + var13; var14++) {
/* 122:171 */               for (int var15 = par5 - var13; var15 <= par5 + var13; var15++) {
/* 123:173 */                 if (par1World.getBlock(var14, var11, var15).getMaterial() == Material.leaves)
/* 124:    */                 {
/* 125:175 */                   if ((par2Random.nextInt(4) == 0) && (par1World.getBlock(var14 - 1, var11, var15).getMaterial() == Material.air)) {
/* 126:177 */                     growVines(par1World, var14 - 1, var11, var15, 8);
/* 127:    */                   }
/* 128:180 */                   if ((par2Random.nextInt(4) == 0) && (par1World.getBlock(var14 + 1, var11, var15).getMaterial() == Material.air)) {
/* 129:182 */                     growVines(par1World, var14 + 1, var11, var15, 2);
/* 130:    */                   }
/* 131:185 */                   if ((par2Random.nextInt(4) == 0) && (par1World.getBlock(var14, var11, var15 - 1).getMaterial() == Material.air)) {
/* 132:187 */                     growVines(par1World, var14, var11, var15 - 1, 1);
/* 133:    */                   }
/* 134:190 */                   if ((par2Random.nextInt(4) == 0) && (par1World.getBlock(var14, var11, var15 + 1).getMaterial() == Material.air)) {
/* 135:192 */                     growVines(par1World, var14, var11, var15 + 1, 4);
/* 136:    */                   }
/* 137:    */                 }
/* 138:    */               }
/* 139:    */             }
/* 140:    */           }
/* 141:199 */           if ((par2Random.nextInt(5) == 0) && (var6 > 5)) {
/* 142:201 */             for (var11 = 0; var11 < 2; var11++) {
/* 143:203 */               for (int var21 = 0; var21 < 4; var21++) {
/* 144:205 */                 if (par2Random.nextInt(4 - var11) == 0)
/* 145:    */                 {
/* 146:207 */                   int var13 = par2Random.nextInt(3);
/* 147:208 */                   func_150516_a(par1World, par3 + net.minecraft.util.Direction.offsetX[net.minecraft.util.Direction.rotateOpposite[var21]], par4 + var6 - 5 + var11, par5 + net.minecraft.util.Direction.offsetZ[net.minecraft.util.Direction.rotateOpposite[var21]], Blocks.cocoa, var13 << 2 | var21);
/* 148:    */                 }
/* 149:    */               }
/* 150:    */             }
/* 151:    */           }
/* 152:    */         }
/* 153:215 */         return true;
/* 154:    */       }
/* 155:219 */       return false;
/* 156:    */     }
/* 157:225 */     return false;
/* 158:    */   }
/* 159:    */   
/* 160:    */   private void growVines(World par1World, int par2, int par3, int par4, int par5)
/* 161:    */   {
/* 162:234 */     func_150516_a(par1World, par2, par3, par4, Blocks.vine, par5);
/* 163:235 */     int var6 = 4;
/* 164:    */     for (;;)
/* 165:    */     {
/* 166:239 */       par3--;
/* 167:241 */       if ((par1World.getBlock(par2, par3, par4).getMaterial() != Material.air) || (var6 <= 0)) {
/* 168:243 */         return;
/* 169:    */       }
/* 170:246 */       func_150516_a(par1World, par2, par3, par4, Blocks.vine, par5);
/* 171:247 */       var6--;
/* 172:    */     }
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenTrees
 * JD-Core Version:    0.7.0.1
 */