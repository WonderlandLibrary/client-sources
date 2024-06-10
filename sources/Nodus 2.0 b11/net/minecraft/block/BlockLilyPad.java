/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.entity.Entity;
/*  7:   */ import net.minecraft.entity.item.EntityBoat;
/*  8:   */ import net.minecraft.init.Blocks;
/*  9:   */ import net.minecraft.util.AABBPool;
/* 10:   */ import net.minecraft.util.AxisAlignedBB;
/* 11:   */ import net.minecraft.world.IBlockAccess;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ public class BlockLilyPad
/* 15:   */   extends BlockBush
/* 16:   */ {
/* 17:   */   private static final String __OBFID = "CL_00000332";
/* 18:   */   
/* 19:   */   protected BlockLilyPad()
/* 20:   */   {
/* 21:19 */     float var1 = 0.5F;
/* 22:20 */     float var2 = 0.015625F;
/* 23:21 */     setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var2, 0.5F + var1);
/* 24:22 */     setCreativeTab(CreativeTabs.tabDecorations);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getRenderType()
/* 28:   */   {
/* 29:30 */     return 23;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
/* 33:   */   {
/* 34:35 */     if ((p_149743_7_ == null) || (!(p_149743_7_ instanceof EntityBoat))) {
/* 35:37 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/* 40:   */   {
/* 41:47 */     return AxisAlignedBB.getAABBPool().getAABB(p_149668_2_ + this.field_149759_B, p_149668_3_ + this.field_149760_C, p_149668_4_ + this.field_149754_D, p_149668_2_ + this.field_149755_E, p_149668_3_ + this.field_149756_F, p_149668_4_ + this.field_149757_G);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int getBlockColor()
/* 45:   */   {
/* 46:52 */     return 2129968;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int getRenderColor(int p_149741_1_)
/* 50:   */   {
/* 51:60 */     return 2129968;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
/* 55:   */   {
/* 56:69 */     return 2129968;
/* 57:   */   }
/* 58:   */   
/* 59:   */   protected boolean func_149854_a(Block p_149854_1_)
/* 60:   */   {
/* 61:74 */     return p_149854_1_ == Blocks.water;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/* 65:   */   {
/* 66:82 */     return (p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_).getMaterial() == Material.water) && (p_149718_1_.getBlockMetadata(p_149718_2_, p_149718_3_ - 1, p_149718_4_) == 0);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockLilyPad
 * JD-Core Version:    0.7.0.1
 */