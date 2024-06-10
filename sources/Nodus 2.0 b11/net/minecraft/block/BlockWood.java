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
/* 11:   */ public class BlockWood
/* 12:   */   extends Block
/* 13:   */ {
/* 14:13 */   public static final String[] field_150096_a = { "oak", "spruce", "birch", "jungle", "acacia", "big_oak" };
/* 15:   */   private IIcon[] field_150095_b;
/* 16:   */   private static final String __OBFID = "CL_00000335";
/* 17:   */   
/* 18:   */   public BlockWood()
/* 19:   */   {
/* 20:19 */     super(Material.wood);
/* 21:20 */     setCreativeTab(CreativeTabs.tabBlock);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 25:   */   {
/* 26:28 */     if ((p_149691_2_ < 0) || (p_149691_2_ >= this.field_150095_b.length)) {
/* 27:30 */       p_149691_2_ = 0;
/* 28:   */     }
/* 29:33 */     return this.field_150095_b[p_149691_2_];
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int damageDropped(int p_149692_1_)
/* 33:   */   {
/* 34:41 */     return p_149692_1_;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 38:   */   {
/* 39:46 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/* 40:47 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
/* 41:48 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
/* 42:49 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
/* 43:50 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 4));
/* 44:51 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 5));
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 48:   */   {
/* 49:56 */     this.field_150095_b = new IIcon[field_150096_a.length];
/* 50:58 */     for (int var2 = 0; var2 < this.field_150095_b.length; var2++) {
/* 51:60 */       this.field_150095_b[var2] = p_149651_1_.registerIcon(getTextureName() + "_" + field_150096_a[var2]);
/* 52:   */     }
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockWood
 * JD-Core Version:    0.7.0.1
 */