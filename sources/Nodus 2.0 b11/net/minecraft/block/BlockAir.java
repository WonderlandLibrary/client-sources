/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ import net.minecraft.util.AxisAlignedBB;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class BlockAir
/*  8:   */   extends Block
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000190";
/* 11:   */   
/* 12:   */   protected BlockAir()
/* 13:   */   {
/* 14:13 */     super(Material.air);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int getRenderType()
/* 18:   */   {
/* 19:21 */     return -1;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/* 23:   */   {
/* 24:30 */     return null;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean isOpaqueCube()
/* 28:   */   {
/* 29:35 */     return false;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
/* 33:   */   {
/* 34:44 */     return false;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {}
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockAir
 * JD-Core Version:    0.7.0.1
 */