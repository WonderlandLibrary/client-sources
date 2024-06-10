/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ import net.minecraft.creativetab.CreativeTabs;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.util.AABBPool;
/*  7:   */ import net.minecraft.util.AxisAlignedBB;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class BlockSoulSand
/* 11:   */   extends Block
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000310";
/* 14:   */   
/* 15:   */   public BlockSoulSand()
/* 16:   */   {
/* 17:15 */     super(Material.sand);
/* 18:16 */     setCreativeTab(CreativeTabs.tabBlock);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/* 22:   */   {
/* 23:25 */     float var5 = 0.125F;
/* 24:26 */     return AxisAlignedBB.getAABBPool().getAABB(p_149668_2_, p_149668_3_, p_149668_4_, p_149668_2_ + 1, p_149668_3_ + 1 - var5, p_149668_4_ + 1);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/* 28:   */   {
/* 29:31 */     p_149670_5_.motionX *= 0.4D;
/* 30:32 */     p_149670_5_.motionZ *= 0.4D;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockSoulSand
 * JD-Core Version:    0.7.0.1
 */