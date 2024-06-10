/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class BlockStaticLiquid
/*  9:   */   extends BlockLiquid
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000315";
/* 12:   */   
/* 13:   */   protected BlockStaticLiquid(Material p_i45429_1_)
/* 14:   */   {
/* 15:14 */     super(p_i45429_1_);
/* 16:15 */     setTickRandomly(false);
/* 17:17 */     if (p_i45429_1_ == Material.lava) {
/* 18:19 */       setTickRandomly(true);
/* 19:   */     }
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 23:   */   {
/* 24:25 */     super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
/* 25:27 */     if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_) == this) {
/* 26:29 */       setNotStationary(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   private void setNotStationary(World p_149818_1_, int p_149818_2_, int p_149818_3_, int p_149818_4_)
/* 31:   */   {
/* 32:35 */     int var5 = p_149818_1_.getBlockMetadata(p_149818_2_, p_149818_3_, p_149818_4_);
/* 33:36 */     p_149818_1_.setBlock(p_149818_2_, p_149818_3_, p_149818_4_, Block.getBlockById(Block.getIdFromBlock(this) - 1), var5, 2);
/* 34:37 */     p_149818_1_.scheduleBlockUpdate(p_149818_2_, p_149818_3_, p_149818_4_, Block.getBlockById(Block.getIdFromBlock(this) - 1), func_149738_a(p_149818_1_));
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 38:   */   {
/* 39:45 */     if (this.blockMaterial == Material.lava)
/* 40:   */     {
/* 41:47 */       int var6 = p_149674_5_.nextInt(3);
/* 42:50 */       for (int var7 = 0; var7 < var6; var7++)
/* 43:   */       {
/* 44:52 */         p_149674_2_ += p_149674_5_.nextInt(3) - 1;
/* 45:53 */         p_149674_3_++;
/* 46:54 */         p_149674_4_ += p_149674_5_.nextInt(3) - 1;
/* 47:55 */         Block var8 = p_149674_1_.getBlock(p_149674_2_, p_149674_3_, p_149674_4_);
/* 48:57 */         if (var8.blockMaterial == Material.air)
/* 49:   */         {
/* 50:59 */           if ((isFlammable(p_149674_1_, p_149674_2_ - 1, p_149674_3_, p_149674_4_)) || (isFlammable(p_149674_1_, p_149674_2_ + 1, p_149674_3_, p_149674_4_)) || (isFlammable(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ - 1)) || (isFlammable(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ + 1)) || (isFlammable(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_)) || (isFlammable(p_149674_1_, p_149674_2_, p_149674_3_ + 1, p_149674_4_))) {
/* 51:61 */             p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.fire);
/* 52:   */           }
/* 53:   */         }
/* 54:65 */         else if (var8.blockMaterial.blocksMovement()) {
/* 55:67 */           return;
/* 56:   */         }
/* 57:   */       }
/* 58:71 */       if (var6 == 0)
/* 59:   */       {
/* 60:73 */         var7 = p_149674_2_;
/* 61:74 */         int var10 = p_149674_4_;
/* 62:76 */         for (int var9 = 0; var9 < 3; var9++)
/* 63:   */         {
/* 64:78 */           p_149674_2_ = var7 + p_149674_5_.nextInt(3) - 1;
/* 65:79 */           p_149674_4_ = var10 + p_149674_5_.nextInt(3) - 1;
/* 66:81 */           if ((p_149674_1_.isAirBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_)) && (isFlammable(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_))) {
/* 67:83 */             p_149674_1_.setBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_, Blocks.fire);
/* 68:   */           }
/* 69:   */         }
/* 70:   */       }
/* 71:   */     }
/* 72:   */   }
/* 73:   */   
/* 74:   */   private boolean isFlammable(World p_149817_1_, int p_149817_2_, int p_149817_3_, int p_149817_4_)
/* 75:   */   {
/* 76:92 */     return p_149817_1_.getBlock(p_149817_2_, p_149817_3_, p_149817_4_).getMaterial().getCanBurn();
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockStaticLiquid
 * JD-Core Version:    0.7.0.1
 */