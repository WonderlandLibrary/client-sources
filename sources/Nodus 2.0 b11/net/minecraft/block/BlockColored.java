/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.block.material.MapColor;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  7:   */ import net.minecraft.creativetab.CreativeTabs;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ import net.minecraft.util.IIcon;
/* 11:   */ 
/* 12:   */ public class BlockColored
/* 13:   */   extends Block
/* 14:   */ {
/* 15:   */   private IIcon[] field_150033_a;
/* 16:   */   private static final String __OBFID = "CL_00000217";
/* 17:   */   
/* 18:   */   public BlockColored(Material p_i45398_1_)
/* 19:   */   {
/* 20:20 */     super(p_i45398_1_);
/* 21:21 */     setCreativeTab(CreativeTabs.tabBlock);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 25:   */   {
/* 26:29 */     return this.field_150033_a[(p_149691_2_ % this.field_150033_a.length)];
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int damageDropped(int p_149692_1_)
/* 30:   */   {
/* 31:37 */     return p_149692_1_;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static int func_150032_b(int p_150032_0_)
/* 35:   */   {
/* 36:42 */     return func_150031_c(p_150032_0_);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static int func_150031_c(int p_150031_0_)
/* 40:   */   {
/* 41:47 */     return (p_150031_0_ ^ 0xFFFFFFFF) & 0xF;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 45:   */   {
/* 46:52 */     for (int var4 = 0; var4 < 16; var4++) {
/* 47:54 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 52:   */   {
/* 53:60 */     this.field_150033_a = new IIcon[16];
/* 54:62 */     for (int var2 = 0; var2 < this.field_150033_a.length; var2++) {
/* 55:64 */       this.field_150033_a[var2] = p_149651_1_.registerIcon(getTextureName() + "_" + net.minecraft.item.ItemDye.field_150921_b[func_150031_c(var2)]);
/* 56:   */     }
/* 57:   */   }
/* 58:   */   
/* 59:   */   public MapColor getMapColor(int p_149728_1_)
/* 60:   */   {
/* 61:70 */     return MapColor.func_151644_a(p_149728_1_);
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockColored
 * JD-Core Version:    0.7.0.1
 */