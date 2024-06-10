/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  7:   */ import net.minecraft.creativetab.CreativeTabs;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ import net.minecraft.util.IIcon;
/* 11:   */ 
/* 12:   */ public class BlockStainedGlass
/* 13:   */   extends BlockBreakable
/* 14:   */ {
/* 15:15 */   private static final IIcon[] field_149998_a = new IIcon[16];
/* 16:   */   private static final String __OBFID = "CL_00000312";
/* 17:   */   
/* 18:   */   public BlockStainedGlass(Material p_i45427_1_)
/* 19:   */   {
/* 20:20 */     super("glass", p_i45427_1_, false);
/* 21:21 */     setCreativeTab(CreativeTabs.tabBlock);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 25:   */   {
/* 26:29 */     return field_149998_a[(p_149691_2_ % field_149998_a.length)];
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int damageDropped(int p_149692_1_)
/* 30:   */   {
/* 31:37 */     return p_149692_1_;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static int func_149997_b(int p_149997_0_)
/* 35:   */   {
/* 36:42 */     return (p_149997_0_ ^ 0xFFFFFFFF) & 0xF;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 40:   */   {
/* 41:47 */     for (int var4 = 0; var4 < field_149998_a.length; var4++) {
/* 42:49 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   public int getRenderBlockPass()
/* 47:   */   {
/* 48:58 */     return 1;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 52:   */   {
/* 53:63 */     for (int var2 = 0; var2 < field_149998_a.length; var2++) {
/* 54:65 */       field_149998_a[var2] = p_149651_1_.registerIcon(getTextureName() + "_" + net.minecraft.item.ItemDye.field_150921_b[func_149997_b(var2)]);
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int quantityDropped(Random p_149745_1_)
/* 59:   */   {
/* 60:74 */     return 0;
/* 61:   */   }
/* 62:   */   
/* 63:   */   protected boolean canSilkHarvest()
/* 64:   */   {
/* 65:79 */     return true;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public boolean renderAsNormalBlock()
/* 69:   */   {
/* 70:84 */     return false;
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockStainedGlass
 * JD-Core Version:    0.7.0.1
 */