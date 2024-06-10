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
/* 11:   */ public class BlockSandStone
/* 12:   */   extends Block
/* 13:   */ {
/* 14:13 */   public static final String[] field_150157_a = { "default", "chiseled", "smooth" };
/* 15:14 */   private static final String[] field_150156_b = { "normal", "carved", "smooth" };
/* 16:   */   private IIcon[] field_150158_M;
/* 17:   */   private IIcon field_150159_N;
/* 18:   */   private IIcon field_150160_O;
/* 19:   */   private static final String __OBFID = "CL_00000304";
/* 20:   */   
/* 21:   */   public BlockSandStone()
/* 22:   */   {
/* 23:22 */     super(Material.rock);
/* 24:23 */     setCreativeTab(CreativeTabs.tabBlock);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 28:   */   {
/* 29:31 */     if ((p_149691_1_ != 1) && ((p_149691_1_ != 0) || ((p_149691_2_ != 1) && (p_149691_2_ != 2))))
/* 30:   */     {
/* 31:33 */       if (p_149691_1_ == 0) {
/* 32:35 */         return this.field_150160_O;
/* 33:   */       }
/* 34:39 */       if ((p_149691_2_ < 0) || (p_149691_2_ >= this.field_150158_M.length)) {
/* 35:41 */         p_149691_2_ = 0;
/* 36:   */       }
/* 37:44 */       return this.field_150158_M[p_149691_2_];
/* 38:   */     }
/* 39:49 */     return this.field_150159_N;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public int damageDropped(int p_149692_1_)
/* 43:   */   {
/* 44:58 */     return p_149692_1_;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 48:   */   {
/* 49:63 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/* 50:64 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
/* 51:65 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 55:   */   {
/* 56:70 */     this.field_150158_M = new IIcon[field_150156_b.length];
/* 57:72 */     for (int var2 = 0; var2 < this.field_150158_M.length; var2++) {
/* 58:74 */       this.field_150158_M[var2] = p_149651_1_.registerIcon(getTextureName() + "_" + field_150156_b[var2]);
/* 59:   */     }
/* 60:77 */     this.field_150159_N = p_149651_1_.registerIcon(getTextureName() + "_top");
/* 61:78 */     this.field_150160_O = p_149651_1_.registerIcon(getTextureName() + "_bottom");
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockSandStone
 * JD-Core Version:    0.7.0.1
 */