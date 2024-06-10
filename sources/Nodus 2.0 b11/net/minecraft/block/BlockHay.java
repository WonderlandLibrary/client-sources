/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.util.IIcon;
/*  7:   */ 
/*  8:   */ public class BlockHay
/*  9:   */   extends BlockRotatedPillar
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000256";
/* 12:   */   
/* 13:   */   public BlockHay()
/* 14:   */   {
/* 15:14 */     super(Material.grass);
/* 16:15 */     setCreativeTab(CreativeTabs.tabBlock);
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected IIcon func_150163_b(int p_150163_1_)
/* 20:   */   {
/* 21:20 */     return this.blockIcon;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 25:   */   {
/* 26:25 */     this.field_150164_N = p_149651_1_.registerIcon(getTextureName() + "_top");
/* 27:26 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockHay
 * JD-Core Version:    0.7.0.1
 */