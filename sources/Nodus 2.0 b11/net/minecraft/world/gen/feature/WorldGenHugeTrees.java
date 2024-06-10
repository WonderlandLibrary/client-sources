/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public abstract class WorldGenHugeTrees
/*  10:    */   extends WorldGenAbstractTree
/*  11:    */ {
/*  12:    */   protected final int baseHeight;
/*  13:    */   protected final int woodMetadata;
/*  14:    */   protected final int leavesMetadata;
/*  15:    */   protected int field_150538_d;
/*  16:    */   private static final String __OBFID = "CL_00000423";
/*  17:    */   
/*  18:    */   public WorldGenHugeTrees(boolean p_i45458_1_, int p_i45458_2_, int p_i45458_3_, int p_i45458_4_, int p_i45458_5_)
/*  19:    */   {
/*  20: 24 */     super(p_i45458_1_);
/*  21: 25 */     this.baseHeight = p_i45458_2_;
/*  22: 26 */     this.field_150538_d = p_i45458_3_;
/*  23: 27 */     this.woodMetadata = p_i45458_4_;
/*  24: 28 */     this.leavesMetadata = p_i45458_5_;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected int func_150533_a(Random p_150533_1_)
/*  28:    */   {
/*  29: 33 */     int var2 = p_150533_1_.nextInt(3) + this.baseHeight;
/*  30: 35 */     if (this.field_150538_d > 1) {
/*  31: 37 */       var2 += p_150533_1_.nextInt(this.field_150538_d);
/*  32:    */     }
/*  33: 40 */     return var2;
/*  34:    */   }
/*  35:    */   
/*  36:    */   private boolean func_150536_b(World p_150536_1_, Random p_150536_2_, int p_150536_3_, int p_150536_4_, int p_150536_5_, int p_150536_6_)
/*  37:    */   {
/*  38: 45 */     boolean var7 = true;
/*  39: 47 */     if ((p_150536_4_ >= 1) && (p_150536_4_ + p_150536_6_ + 1 <= 256))
/*  40:    */     {
/*  41: 49 */       for (int var8 = p_150536_4_; var8 <= p_150536_4_ + 1 + p_150536_6_; var8++)
/*  42:    */       {
/*  43: 51 */         byte var9 = 2;
/*  44: 53 */         if (var8 == p_150536_4_) {
/*  45: 55 */           var9 = 1;
/*  46:    */         }
/*  47: 58 */         if (var8 >= p_150536_4_ + 1 + p_150536_6_ - 2) {
/*  48: 60 */           var9 = 2;
/*  49:    */         }
/*  50: 63 */         for (int var10 = p_150536_3_ - var9; (var10 <= p_150536_3_ + var9) && (var7); var10++) {
/*  51: 65 */           for (int var11 = p_150536_5_ - var9; (var11 <= p_150536_5_ + var9) && (var7); var11++) {
/*  52: 67 */             if ((var8 >= 0) && (var8 < 256))
/*  53:    */             {
/*  54: 69 */               Block var12 = p_150536_1_.getBlock(var10, var8, var11);
/*  55: 71 */               if (!func_150523_a(var12)) {
/*  56: 73 */                 var7 = false;
/*  57:    */               }
/*  58:    */             }
/*  59:    */             else
/*  60:    */             {
/*  61: 78 */               var7 = false;
/*  62:    */             }
/*  63:    */           }
/*  64:    */         }
/*  65:    */       }
/*  66: 84 */       return var7;
/*  67:    */     }
/*  68: 88 */     return false;
/*  69:    */   }
/*  70:    */   
/*  71:    */   private boolean func_150532_c(World p_150532_1_, Random p_150532_2_, int p_150532_3_, int p_150532_4_, int p_150532_5_)
/*  72:    */   {
/*  73: 94 */     Block var6 = p_150532_1_.getBlock(p_150532_3_, p_150532_4_ - 1, p_150532_5_);
/*  74: 96 */     if (((var6 == Blocks.grass) || (var6 == Blocks.dirt)) && (p_150532_4_ >= 2))
/*  75:    */     {
/*  76: 98 */       p_150532_1_.setBlock(p_150532_3_, p_150532_4_ - 1, p_150532_5_, Blocks.dirt, 0, 2);
/*  77: 99 */       p_150532_1_.setBlock(p_150532_3_ + 1, p_150532_4_ - 1, p_150532_5_, Blocks.dirt, 0, 2);
/*  78:100 */       p_150532_1_.setBlock(p_150532_3_, p_150532_4_ - 1, p_150532_5_ + 1, Blocks.dirt, 0, 2);
/*  79:101 */       p_150532_1_.setBlock(p_150532_3_ + 1, p_150532_4_ - 1, p_150532_5_ + 1, Blocks.dirt, 0, 2);
/*  80:102 */       return true;
/*  81:    */     }
/*  82:106 */     return false;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected boolean func_150537_a(World p_150537_1_, Random p_150537_2_, int p_150537_3_, int p_150537_4_, int p_150537_5_, int p_150537_6_)
/*  86:    */   {
/*  87:112 */     return (func_150536_b(p_150537_1_, p_150537_2_, p_150537_3_, p_150537_4_, p_150537_5_, p_150537_6_)) && (func_150532_c(p_150537_1_, p_150537_2_, p_150537_3_, p_150537_4_, p_150537_5_));
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected void func_150535_a(World p_150535_1_, int p_150535_2_, int p_150535_3_, int p_150535_4_, int p_150535_5_, Random p_150535_6_)
/*  91:    */   {
/*  92:117 */     int var7 = p_150535_5_ * p_150535_5_;
/*  93:119 */     for (int var8 = p_150535_2_ - p_150535_5_; var8 <= p_150535_2_ + p_150535_5_ + 1; var8++)
/*  94:    */     {
/*  95:121 */       int var9 = var8 - p_150535_2_;
/*  96:123 */       for (int var10 = p_150535_4_ - p_150535_5_; var10 <= p_150535_4_ + p_150535_5_ + 1; var10++)
/*  97:    */       {
/*  98:125 */         int var11 = var10 - p_150535_4_;
/*  99:126 */         int var12 = var9 - 1;
/* 100:127 */         int var13 = var11 - 1;
/* 101:129 */         if ((var9 * var9 + var11 * var11 <= var7) || (var12 * var12 + var13 * var13 <= var7) || (var9 * var9 + var13 * var13 <= var7) || (var12 * var12 + var11 * var11 <= var7))
/* 102:    */         {
/* 103:131 */           Block var14 = p_150535_1_.getBlock(var8, p_150535_3_, var10);
/* 104:133 */           if ((var14.getMaterial() == Material.air) || (var14.getMaterial() == Material.leaves)) {
/* 105:135 */             func_150516_a(p_150535_1_, var8, p_150535_3_, var10, Blocks.leaves, this.leavesMetadata);
/* 106:    */           }
/* 107:    */         }
/* 108:    */       }
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected void func_150534_b(World p_150534_1_, int p_150534_2_, int p_150534_3_, int p_150534_4_, int p_150534_5_, Random p_150534_6_)
/* 113:    */   {
/* 114:144 */     int var7 = p_150534_5_ * p_150534_5_;
/* 115:146 */     for (int var8 = p_150534_2_ - p_150534_5_; var8 <= p_150534_2_ + p_150534_5_; var8++)
/* 116:    */     {
/* 117:148 */       int var9 = var8 - p_150534_2_;
/* 118:150 */       for (int var10 = p_150534_4_ - p_150534_5_; var10 <= p_150534_4_ + p_150534_5_; var10++)
/* 119:    */       {
/* 120:152 */         int var11 = var10 - p_150534_4_;
/* 121:154 */         if (var9 * var9 + var11 * var11 <= var7)
/* 122:    */         {
/* 123:156 */           Block var12 = p_150534_1_.getBlock(var8, p_150534_3_, var10);
/* 124:158 */           if ((var12.getMaterial() == Material.air) || (var12.getMaterial() == Material.leaves)) {
/* 125:160 */             func_150516_a(p_150534_1_, var8, p_150534_3_, var10, Blocks.leaves, this.leavesMetadata);
/* 126:    */           }
/* 127:    */         }
/* 128:    */       }
/* 129:    */     }
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenHugeTrees
 * JD-Core Version:    0.7.0.1
 */