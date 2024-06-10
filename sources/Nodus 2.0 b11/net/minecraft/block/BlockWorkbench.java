/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ import net.minecraft.util.IIcon;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class BlockWorkbench
/* 12:   */   extends Block
/* 13:   */ {
/* 14:   */   private IIcon field_150035_a;
/* 15:   */   private IIcon field_150034_b;
/* 16:   */   private static final String __OBFID = "CL_00000221";
/* 17:   */   
/* 18:   */   protected BlockWorkbench()
/* 19:   */   {
/* 20:19 */     super(Material.wood);
/* 21:20 */     setCreativeTab(CreativeTabs.tabDecorations);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 25:   */   {
/* 26:28 */     return (p_149691_1_ != 2) && (p_149691_1_ != 4) ? this.blockIcon : p_149691_1_ == 0 ? Blocks.planks.getBlockTextureFromSide(p_149691_1_) : p_149691_1_ == 1 ? this.field_150035_a : this.field_150034_b;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 30:   */   {
/* 31:33 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
/* 32:34 */     this.field_150035_a = p_149651_1_.registerIcon(getTextureName() + "_top");
/* 33:35 */     this.field_150034_b = p_149651_1_.registerIcon(getTextureName() + "_front");
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 37:   */   {
/* 38:43 */     if (p_149727_1_.isClient) {
/* 39:45 */       return true;
/* 40:   */     }
/* 41:49 */     p_149727_5_.displayGUIWorkbench(p_149727_2_, p_149727_3_, p_149727_4_);
/* 42:50 */     return true;
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockWorkbench
 * JD-Core Version:    0.7.0.1
 */