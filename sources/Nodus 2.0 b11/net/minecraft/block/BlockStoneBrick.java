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
/* 11:   */ public class BlockStoneBrick
/* 12:   */   extends Block
/* 13:   */ {
/* 14:13 */   public static final String[] field_150142_a = { "default", "mossy", "cracked", "chiseled" };
/* 15:14 */   public static final String[] field_150141_b = { 0, "mossy", "cracked", "carved" };
/* 16:   */   private IIcon[] field_150143_M;
/* 17:   */   private static final String __OBFID = "CL_00000318";
/* 18:   */   
/* 19:   */   public BlockStoneBrick()
/* 20:   */   {
/* 21:20 */     super(Material.rock);
/* 22:21 */     setCreativeTab(CreativeTabs.tabBlock);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 26:   */   {
/* 27:29 */     if ((p_149691_2_ < 0) || (p_149691_2_ >= field_150141_b.length)) {
/* 28:31 */       p_149691_2_ = 0;
/* 29:   */     }
/* 30:34 */     return this.field_150143_M[p_149691_2_];
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int damageDropped(int p_149692_1_)
/* 34:   */   {
/* 35:42 */     return p_149692_1_;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 39:   */   {
/* 40:47 */     for (int var4 = 0; var4 < 4; var4++) {
/* 41:49 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 46:   */   {
/* 47:55 */     this.field_150143_M = new IIcon[field_150141_b.length];
/* 48:57 */     for (int var2 = 0; var2 < this.field_150143_M.length; var2++)
/* 49:   */     {
/* 50:59 */       String var3 = getTextureName();
/* 51:61 */       if (field_150141_b[var2] != null) {
/* 52:63 */         var3 = var3 + "_" + field_150141_b[var2];
/* 53:   */       }
/* 54:66 */       this.field_150143_M[var2] = p_149651_1_.registerIcon(var3);
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockStoneBrick
 * JD-Core Version:    0.7.0.1
 */