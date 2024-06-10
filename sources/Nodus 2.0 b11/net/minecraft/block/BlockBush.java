/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.util.AxisAlignedBB;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class BlockBush
/* 11:   */   extends Block
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000208";
/* 14:   */   
/* 15:   */   protected BlockBush(Material p_i45395_1_)
/* 16:   */   {
/* 17:16 */     super(p_i45395_1_);
/* 18:17 */     setTickRandomly(true);
/* 19:18 */     float var2 = 0.2F;
/* 20:19 */     setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var2 * 3.0F, 0.5F + var2);
/* 21:20 */     setCreativeTab(CreativeTabs.tabDecorations);
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected BlockBush()
/* 25:   */   {
/* 26:25 */     this(Material.plants);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/* 30:   */   {
/* 31:30 */     return (super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_)) && (func_149854_a(p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_)));
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected boolean func_149854_a(Block p_149854_1_)
/* 35:   */   {
/* 36:35 */     return (p_149854_1_ == Blocks.grass) || (p_149854_1_ == Blocks.dirt) || (p_149854_1_ == Blocks.farmland);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 40:   */   {
/* 41:40 */     super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
/* 42:41 */     func_149855_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 46:   */   {
/* 47:49 */     func_149855_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
/* 48:   */   }
/* 49:   */   
/* 50:   */   protected void func_149855_e(World p_149855_1_, int p_149855_2_, int p_149855_3_, int p_149855_4_)
/* 51:   */   {
/* 52:54 */     if (!canBlockStay(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_))
/* 53:   */     {
/* 54:56 */       dropBlockAsItem(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_, p_149855_1_.getBlockMetadata(p_149855_2_, p_149855_3_, p_149855_4_), 0);
/* 55:57 */       p_149855_1_.setBlock(p_149855_2_, p_149855_3_, p_149855_4_, getBlockById(0), 0, 2);
/* 56:   */     }
/* 57:   */   }
/* 58:   */   
/* 59:   */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/* 60:   */   {
/* 61:66 */     return func_149854_a(p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_));
/* 62:   */   }
/* 63:   */   
/* 64:   */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/* 65:   */   {
/* 66:75 */     return null;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public boolean isOpaqueCube()
/* 70:   */   {
/* 71:80 */     return false;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public boolean renderAsNormalBlock()
/* 75:   */   {
/* 76:85 */     return false;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public int getRenderType()
/* 80:   */   {
/* 81:93 */     return 1;
/* 82:   */   }
/* 83:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockBush
 * JD-Core Version:    0.7.0.1
 */