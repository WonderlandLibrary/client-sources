/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  6:   */ import net.minecraft.creativetab.CreativeTabs;
/*  7:   */ import net.minecraft.init.Items;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ import net.minecraft.util.IIcon;
/* 11:   */ import net.minecraft.world.ColorizerFoliage;
/* 12:   */ import net.minecraft.world.IBlockAccess;
/* 13:   */ import net.minecraft.world.World;
/* 14:   */ 
/* 15:   */ public class BlockOldLeaf
/* 16:   */   extends BlockLeaves
/* 17:   */ {
/* 18:16 */   public static final String[][] field_150130_N = { { "leaves_oak", "leaves_spruce", "leaves_birch", "leaves_jungle" }, { "leaves_oak_opaque", "leaves_spruce_opaque", "leaves_birch_opaque", "leaves_jungle_opaque" } };
/* 19:17 */   public static final String[] field_150131_O = { "oak", "spruce", "birch", "jungle" };
/* 20:   */   private static final String __OBFID = "CL_00000280";
/* 21:   */   
/* 22:   */   public int getRenderColor(int p_149741_1_)
/* 23:   */   {
/* 24:25 */     return (p_149741_1_ & 0x3) == 2 ? ColorizerFoliage.getFoliageColorBirch() : (p_149741_1_ & 0x3) == 1 ? ColorizerFoliage.getFoliageColorPine() : super.getRenderColor(p_149741_1_);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
/* 28:   */   {
/* 29:34 */     int var5 = p_149720_1_.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_);
/* 30:35 */     return (var5 & 0x3) == 2 ? ColorizerFoliage.getFoliageColorBirch() : (var5 & 0x3) == 1 ? ColorizerFoliage.getFoliageColorPine() : super.colorMultiplier(p_149720_1_, p_149720_2_, p_149720_3_, p_149720_4_);
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected void func_150124_c(World p_150124_1_, int p_150124_2_, int p_150124_3_, int p_150124_4_, int p_150124_5_, int p_150124_6_)
/* 34:   */   {
/* 35:40 */     if (((p_150124_5_ & 0x3) == 0) && (p_150124_1_.rand.nextInt(p_150124_6_) == 0)) {
/* 36:42 */       dropBlockAsItem_do(p_150124_1_, p_150124_2_, p_150124_3_, p_150124_4_, new ItemStack(Items.apple, 1, 0));
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected int func_150123_b(int p_150123_1_)
/* 41:   */   {
/* 42:48 */     int var2 = super.func_150123_b(p_150123_1_);
/* 43:50 */     if ((p_150123_1_ & 0x3) == 3) {
/* 44:52 */       var2 = 40;
/* 45:   */     }
/* 46:55 */     return var2;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 50:   */   {
/* 51:63 */     return (p_149691_2_ & 0x3) == 2 ? this.field_150129_M[this.field_150127_b][2] : (p_149691_2_ & 0x3) == 3 ? this.field_150129_M[this.field_150127_b][3] : (p_149691_2_ & 0x3) == 1 ? this.field_150129_M[this.field_150127_b][1] : this.field_150129_M[this.field_150127_b][0];
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 55:   */   {
/* 56:68 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/* 57:69 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
/* 58:70 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
/* 59:71 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 63:   */   {
/* 64:76 */     for (int var2 = 0; var2 < field_150130_N.length; var2++)
/* 65:   */     {
/* 66:78 */       this.field_150129_M[var2] = new IIcon[field_150130_N[var2].length];
/* 67:80 */       for (int var3 = 0; var3 < field_150130_N[var2].length; var3++) {
/* 68:82 */         this.field_150129_M[var2][var3] = p_149651_1_.registerIcon(field_150130_N[var2][var3]);
/* 69:   */       }
/* 70:   */     }
/* 71:   */   }
/* 72:   */   
/* 73:   */   public String[] func_150125_e()
/* 74:   */   {
/* 75:89 */     return field_150131_O;
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockOldLeaf
 * JD-Core Version:    0.7.0.1
 */