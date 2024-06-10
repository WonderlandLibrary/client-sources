/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public abstract class WorldGenAbstractTree
/* 10:   */   extends WorldGenerator
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000399";
/* 13:   */   
/* 14:   */   public WorldGenAbstractTree(boolean p_i45448_1_)
/* 15:   */   {
/* 16:15 */     super(p_i45448_1_);
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected boolean func_150523_a(Block p_150523_1_)
/* 20:   */   {
/* 21:20 */     return (p_150523_1_.getMaterial() == Material.air) || (p_150523_1_.getMaterial() == Material.leaves) || (p_150523_1_ == Blocks.grass) || (p_150523_1_ == Blocks.dirt) || (p_150523_1_ == Blocks.log) || (p_150523_1_ == Blocks.log2) || (p_150523_1_ == Blocks.sapling) || (p_150523_1_ == Blocks.vine);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void func_150524_b(World p_150524_1_, Random p_150524_2_, int p_150524_3_, int p_150524_4_, int p_150524_5_) {}
/* 25:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenAbstractTree
 * JD-Core Version:    0.7.0.1
 */