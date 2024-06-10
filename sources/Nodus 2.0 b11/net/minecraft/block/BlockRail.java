/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  4:   */ import net.minecraft.util.IIcon;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class BlockRail
/*  8:   */   extends BlockRailBase
/*  9:   */ {
/* 10:   */   private IIcon field_150056_b;
/* 11:   */   private static final String __OBFID = "CL_00000293";
/* 12:   */   
/* 13:   */   protected BlockRail()
/* 14:   */   {
/* 15:14 */     super(false);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 19:   */   {
/* 20:22 */     return p_149691_2_ >= 6 ? this.field_150056_b : this.blockIcon;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 24:   */   {
/* 25:27 */     super.registerBlockIcons(p_149651_1_);
/* 26:28 */     this.field_150056_b = p_149651_1_.registerIcon(getTextureName() + "_turned");
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected void func_150048_a(World p_150048_1_, int p_150048_2_, int p_150048_3_, int p_150048_4_, int p_150048_5_, int p_150048_6_, Block p_150048_7_)
/* 30:   */   {
/* 31:33 */     if ((p_150048_7_.canProvidePower()) && (new BlockRailBase.Rail(this, p_150048_1_, p_150048_2_, p_150048_3_, p_150048_4_).func_150650_a() == 3)) {
/* 32:35 */       func_150052_a(p_150048_1_, p_150048_2_, p_150048_3_, p_150048_4_, false);
/* 33:   */     }
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockRail
 * JD-Core Version:    0.7.0.1
 */