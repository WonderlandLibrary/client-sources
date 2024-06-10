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
/* 13:   */ public class BlockStoneSlab
/* 14:   */   extends BlockSlab
/* 15:   */ {
/* 16:15 */   public static final String[] field_150006_b = { "stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick", "netherBrick", "quartz" };
/* 17:   */   private IIcon field_150007_M;
/* 18:   */   private static final String __OBFID = "CL_00000320";
/* 19:   */   
/* 20:   */   public BlockStoneSlab(boolean p_i45431_1_)
/* 21:   */   {
/* 22:21 */     super(p_i45431_1_, Material.rock);
/* 23:22 */     setCreativeTab(CreativeTabs.tabBlock);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 27:   */   {
/* 28:30 */     int var3 = p_149691_2_ & 0x7;
/* 29:32 */     if ((this.field_150004_a) && ((p_149691_2_ & 0x8) != 0)) {
/* 30:34 */       p_149691_1_ = 1;
/* 31:   */     }
/* 32:37 */     return var3 == 7 ? Blocks.quartz_block.getBlockTextureFromSide(p_149691_1_) : var3 == 6 ? Blocks.nether_brick.getBlockTextureFromSide(1) : var3 == 5 ? Blocks.stonebrick.getIcon(p_149691_1_, 0) : var3 == 4 ? Blocks.brick_block.getBlockTextureFromSide(p_149691_1_) : var3 == 3 ? Blocks.cobblestone.getBlockTextureFromSide(p_149691_1_) : var3 == 2 ? Blocks.planks.getBlockTextureFromSide(p_149691_1_) : var3 == 1 ? Blocks.sandstone.getBlockTextureFromSide(p_149691_1_) : var3 == 0 ? this.blockIcon : (p_149691_1_ != 1) && (p_149691_1_ != 0) ? this.field_150007_M : this.blockIcon;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 36:   */   {
/* 37:42 */     this.blockIcon = p_149651_1_.registerIcon("stone_slab_top");
/* 38:43 */     this.field_150007_M = p_149651_1_.registerIcon("stone_slab_side");
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 42:   */   {
/* 43:48 */     return Item.getItemFromBlock(Blocks.stone_slab);
/* 44:   */   }
/* 45:   */   
/* 46:   */   protected ItemStack createStackedBlock(int p_149644_1_)
/* 47:   */   {
/* 48:57 */     return new ItemStack(Item.getItemFromBlock(Blocks.stone_slab), 2, p_149644_1_ & 0x7);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String func_150002_b(int p_150002_1_)
/* 52:   */   {
/* 53:62 */     if ((p_150002_1_ < 0) || (p_150002_1_ >= field_150006_b.length)) {
/* 54:64 */       p_150002_1_ = 0;
/* 55:   */     }
/* 56:67 */     return super.getUnlocalizedName() + "." + field_150006_b[p_150002_1_];
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 60:   */   {
/* 61:72 */     if (p_149666_1_ != Item.getItemFromBlock(Blocks.double_stone_slab)) {
/* 62:74 */       for (int var4 = 0; var4 <= 7; var4++) {
/* 63:76 */         if (var4 != 2) {
/* 64:78 */           p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
/* 65:   */         }
/* 66:   */       }
/* 67:   */     }
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockStoneSlab
 * JD-Core Version:    0.7.0.1
 */