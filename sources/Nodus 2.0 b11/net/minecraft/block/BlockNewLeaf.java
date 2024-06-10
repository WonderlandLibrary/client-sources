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
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ 
/* 13:   */ public class BlockNewLeaf
/* 14:   */   extends BlockLeaves
/* 15:   */ {
/* 16:14 */   public static final String[][] field_150132_N = { { "leaves_acacia", "leaves_big_oak" }, { "leaves_acacia_opaque", "leaves_big_oak_opaque" } };
/* 17:15 */   public static final String[] field_150133_O = { "acacia", "big_oak" };
/* 18:   */   private static final String __OBFID = "CL_00000276";
/* 19:   */   
/* 20:   */   protected void func_150124_c(World p_150124_1_, int p_150124_2_, int p_150124_3_, int p_150124_4_, int p_150124_5_, int p_150124_6_)
/* 21:   */   {
/* 22:20 */     if (((p_150124_5_ & 0x3) == 1) && (p_150124_1_.rand.nextInt(p_150124_6_) == 0)) {
/* 23:22 */       dropBlockAsItem_do(p_150124_1_, p_150124_2_, p_150124_3_, p_150124_4_, new ItemStack(Items.apple, 1, 0));
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int damageDropped(int p_149692_1_)
/* 28:   */   {
/* 29:31 */     return super.damageDropped(p_149692_1_) + 4;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
/* 33:   */   {
/* 34:39 */     return p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_) & 0x3;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 38:   */   {
/* 39:47 */     return (p_149691_2_ & 0x3) == 1 ? this.field_150129_M[this.field_150127_b][1] : this.field_150129_M[this.field_150127_b][0];
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 43:   */   {
/* 44:52 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/* 45:53 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 49:   */   {
/* 50:58 */     for (int var2 = 0; var2 < field_150132_N.length; var2++)
/* 51:   */     {
/* 52:60 */       this.field_150129_M[var2] = new IIcon[field_150132_N[var2].length];
/* 53:62 */       for (int var3 = 0; var3 < field_150132_N[var2].length; var3++) {
/* 54:64 */         this.field_150129_M[var2][var3] = p_149651_1_.registerIcon(field_150132_N[var2][var3]);
/* 55:   */       }
/* 56:   */     }
/* 57:   */   }
/* 58:   */   
/* 59:   */   public String[] func_150125_e()
/* 60:   */   {
/* 61:71 */     return field_150133_O;
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockNewLeaf
 * JD-Core Version:    0.7.0.1
 */