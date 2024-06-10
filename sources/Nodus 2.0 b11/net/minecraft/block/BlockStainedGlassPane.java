/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  6:   */ import net.minecraft.creativetab.CreativeTabs;
/*  7:   */ import net.minecraft.item.Item;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.util.IIcon;
/* 10:   */ 
/* 11:   */ public class BlockStainedGlassPane
/* 12:   */   extends BlockPane
/* 13:   */ {
/* 14:14 */   private static final IIcon[] field_150106_a = new IIcon[16];
/* 15:15 */   private static final IIcon[] field_150105_b = new IIcon[16];
/* 16:   */   private static final String __OBFID = "CL_00000313";
/* 17:   */   
/* 18:   */   public BlockStainedGlassPane()
/* 19:   */   {
/* 20:20 */     super("glass", "glass_pane_top", Material.glass, false);
/* 21:21 */     setCreativeTab(CreativeTabs.tabDecorations);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public IIcon func_149735_b(int p_149735_1_, int p_149735_2_)
/* 25:   */   {
/* 26:26 */     return field_150106_a[(p_149735_2_ % field_150106_a.length)];
/* 27:   */   }
/* 28:   */   
/* 29:   */   public IIcon func_150104_b(int p_150104_1_)
/* 30:   */   {
/* 31:31 */     return field_150105_b[((p_150104_1_ ^ 0xFFFFFFFF) & 0xF)];
/* 32:   */   }
/* 33:   */   
/* 34:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 35:   */   {
/* 36:39 */     return func_149735_b(p_149691_1_, (p_149691_2_ ^ 0xFFFFFFFF) & 0xF);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public int damageDropped(int p_149692_1_)
/* 40:   */   {
/* 41:47 */     return p_149692_1_;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static int func_150103_c(int p_150103_0_)
/* 45:   */   {
/* 46:52 */     return p_150103_0_ & 0xF;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 50:   */   {
/* 51:57 */     for (int var4 = 0; var4 < field_150106_a.length; var4++) {
/* 52:59 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int getRenderBlockPass()
/* 57:   */   {
/* 58:68 */     return 1;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 62:   */   {
/* 63:73 */     super.registerBlockIcons(p_149651_1_);
/* 64:75 */     for (int var2 = 0; var2 < field_150106_a.length; var2++)
/* 65:   */     {
/* 66:77 */       field_150106_a[var2] = p_149651_1_.registerIcon(getTextureName() + "_" + net.minecraft.item.ItemDye.field_150921_b[func_150103_c(var2)]);
/* 67:78 */       field_150105_b[var2] = p_149651_1_.registerIcon(getTextureName() + "_pane_top_" + net.minecraft.item.ItemDye.field_150921_b[func_150103_c(var2)]);
/* 68:   */     }
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockStainedGlassPane
 * JD-Core Version:    0.7.0.1
 */