/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  7:   */ import net.minecraft.creativetab.CreativeTabs;
/*  8:   */ import net.minecraft.init.Blocks;
/*  9:   */ import net.minecraft.item.Item;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.util.IIcon;
/* 12:   */ 
/* 13:   */ public class BlockWoodSlab
/* 14:   */   extends BlockSlab
/* 15:   */ {
/* 16:15 */   public static final String[] field_150005_b = { "oak", "spruce", "birch", "jungle", "acacia", "big_oak" };
/* 17:   */   private static final String __OBFID = "CL_00000337";
/* 18:   */   
/* 19:   */   public BlockWoodSlab(boolean p_i45437_1_)
/* 20:   */   {
/* 21:20 */     super(p_i45437_1_, Material.wood);
/* 22:21 */     setCreativeTab(CreativeTabs.tabBlock);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 26:   */   {
/* 27:29 */     return Blocks.planks.getIcon(p_149691_1_, p_149691_2_ & 0x7);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 31:   */   {
/* 32:34 */     return Item.getItemFromBlock(Blocks.wooden_slab);
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected ItemStack createStackedBlock(int p_149644_1_)
/* 36:   */   {
/* 37:43 */     return new ItemStack(Item.getItemFromBlock(Blocks.wooden_slab), 2, p_149644_1_ & 0x7);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String func_150002_b(int p_150002_1_)
/* 41:   */   {
/* 42:48 */     if ((p_150002_1_ < 0) || (p_150002_1_ >= field_150005_b.length)) {
/* 43:50 */       p_150002_1_ = 0;
/* 44:   */     }
/* 45:53 */     return super.getUnlocalizedName() + "." + field_150005_b[p_150002_1_];
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 49:   */   {
/* 50:58 */     if (p_149666_1_ != Item.getItemFromBlock(Blocks.double_wooden_slab)) {
/* 51:60 */       for (int var4 = 0; var4 < field_150005_b.length; var4++) {
/* 52:62 */         p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
/* 53:   */       }
/* 54:   */     }
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void registerBlockIcons(IIconRegister p_149651_1_) {}
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockWoodSlab
 * JD-Core Version:    0.7.0.1
 */