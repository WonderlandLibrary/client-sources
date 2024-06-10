/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.init.Blocks;
/*   5:    */ import net.minecraft.world.World;
/*   6:    */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*   7:    */ 
/*   8:    */ public class BlockMushroom
/*   9:    */   extends BlockBush
/*  10:    */   implements IGrowable
/*  11:    */ {
/*  12:    */   private static final String __OBFID = "CL_00000272";
/*  13:    */   
/*  14:    */   protected BlockMushroom()
/*  15:    */   {
/*  16: 14 */     float var1 = 0.2F;
/*  17: 15 */     setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var1 * 2.0F, 0.5F + var1);
/*  18: 16 */     setTickRandomly(true);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  22:    */   {
/*  23: 24 */     if (p_149674_5_.nextInt(25) == 0)
/*  24:    */     {
/*  25: 26 */       byte var6 = 4;
/*  26: 27 */       int var7 = 5;
/*  27: 32 */       for (int var8 = p_149674_2_ - var6; var8 <= p_149674_2_ + var6; var8++) {
/*  28: 34 */         for (int var9 = p_149674_4_ - var6; var9 <= p_149674_4_ + var6; var9++) {
/*  29: 36 */           for (int var10 = p_149674_3_ - 1; var10 <= p_149674_3_ + 1; var10++) {
/*  30: 38 */             if (p_149674_1_.getBlock(var8, var10, var9) == this)
/*  31:    */             {
/*  32: 40 */               var7--;
/*  33: 42 */               if (var7 <= 0) {
/*  34: 44 */                 return;
/*  35:    */               }
/*  36:    */             }
/*  37:    */           }
/*  38:    */         }
/*  39:    */       }
/*  40: 51 */       var8 = p_149674_2_ + p_149674_5_.nextInt(3) - 1;
/*  41: 52 */       int var9 = p_149674_3_ + p_149674_5_.nextInt(2) - p_149674_5_.nextInt(2);
/*  42: 53 */       int var10 = p_149674_4_ + p_149674_5_.nextInt(3) - 1;
/*  43: 55 */       for (int var11 = 0; var11 < 4; var11++)
/*  44:    */       {
/*  45: 57 */         if ((p_149674_1_.isAirBlock(var8, var9, var10)) && (canBlockStay(p_149674_1_, var8, var9, var10)))
/*  46:    */         {
/*  47: 59 */           p_149674_2_ = var8;
/*  48: 60 */           p_149674_3_ = var9;
/*  49: 61 */           p_149674_4_ = var10;
/*  50:    */         }
/*  51: 64 */         var8 = p_149674_2_ + p_149674_5_.nextInt(3) - 1;
/*  52: 65 */         var9 = p_149674_3_ + p_149674_5_.nextInt(2) - p_149674_5_.nextInt(2);
/*  53: 66 */         var10 = p_149674_4_ + p_149674_5_.nextInt(3) - 1;
/*  54:    */       }
/*  55: 69 */       if ((p_149674_1_.isAirBlock(var8, var9, var10)) && (canBlockStay(p_149674_1_, var8, var9, var10))) {
/*  56: 71 */         p_149674_1_.setBlock(var8, var9, var10, this, 0, 2);
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  62:    */   {
/*  63: 78 */     return (super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_)) && (canBlockStay(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_));
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected boolean func_149854_a(Block p_149854_1_)
/*  67:    */   {
/*  68: 83 */     return p_149854_1_.func_149730_j();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/*  72:    */   {
/*  73: 91 */     if ((p_149718_3_ >= 0) && (p_149718_3_ < 256))
/*  74:    */     {
/*  75: 93 */       Block var5 = p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_);
/*  76: 94 */       return (var5 == Blocks.mycelium) || ((var5 == Blocks.dirt) && (p_149718_1_.getBlockMetadata(p_149718_2_, p_149718_3_ - 1, p_149718_4_) == 2)) || ((p_149718_1_.getFullBlockLightValue(p_149718_2_, p_149718_3_, p_149718_4_) < 13) && (func_149854_a(var5)));
/*  77:    */     }
/*  78: 98 */     return false;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean func_149884_c(World p_149884_1_, int p_149884_2_, int p_149884_3_, int p_149884_4_, Random p_149884_5_)
/*  82:    */   {
/*  83:104 */     int var6 = p_149884_1_.getBlockMetadata(p_149884_2_, p_149884_3_, p_149884_4_);
/*  84:105 */     p_149884_1_.setBlockToAir(p_149884_2_, p_149884_3_, p_149884_4_);
/*  85:106 */     WorldGenBigMushroom var7 = null;
/*  86:108 */     if (this == Blocks.brown_mushroom) {
/*  87:110 */       var7 = new WorldGenBigMushroom(0);
/*  88:112 */     } else if (this == Blocks.red_mushroom) {
/*  89:114 */       var7 = new WorldGenBigMushroom(1);
/*  90:    */     }
/*  91:117 */     if ((var7 != null) && (var7.generate(p_149884_1_, p_149884_5_, p_149884_2_, p_149884_3_, p_149884_4_))) {
/*  92:119 */       return true;
/*  93:    */     }
/*  94:123 */     p_149884_1_.setBlock(p_149884_2_, p_149884_3_, p_149884_4_, this, var6, 3);
/*  95:124 */     return false;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
/*  99:    */   {
/* 100:130 */     return true;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
/* 104:    */   {
/* 105:135 */     return p_149852_2_.nextFloat() < 0.4D;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
/* 109:    */   {
/* 110:140 */     func_149884_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_);
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockMushroom
 * JD-Core Version:    0.7.0.1
 */