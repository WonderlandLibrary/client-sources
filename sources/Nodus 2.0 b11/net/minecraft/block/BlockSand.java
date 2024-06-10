/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.block.material.MapColor;
/*  5:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  6:   */ import net.minecraft.creativetab.CreativeTabs;
/*  7:   */ import net.minecraft.item.Item;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.util.IIcon;
/* 10:   */ 
/* 11:   */ public class BlockSand
/* 12:   */   extends BlockFalling
/* 13:   */ {
/* 14:13 */   public static final String[] field_149838_a = { "default", "red" };
/* 15:   */   private static IIcon field_149837_b;
/* 16:   */   private static IIcon field_149839_N;
/* 17:   */   private static final String __OBFID = "CL_00000303";
/* 18:   */   
/* 19:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 20:   */   {
/* 21:23 */     return p_149691_2_ == 1 ? field_149839_N : field_149837_b;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 25:   */   {
/* 26:28 */     field_149837_b = p_149651_1_.registerIcon("sand");
/* 27:29 */     field_149839_N = p_149651_1_.registerIcon("red_sand");
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int damageDropped(int p_149692_1_)
/* 31:   */   {
/* 32:37 */     return p_149692_1_;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 36:   */   {
/* 37:42 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/* 38:43 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
/* 39:   */   }
/* 40:   */   
/* 41:   */   public MapColor getMapColor(int p_149728_1_)
/* 42:   */   {
/* 43:48 */     return p_149728_1_ == 1 ? MapColor.field_151664_l : MapColor.field_151658_d;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockSand
 * JD-Core Version:    0.7.0.1
 */