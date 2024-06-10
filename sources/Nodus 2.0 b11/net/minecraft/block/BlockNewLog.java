/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.util.IIcon;
/*  9:   */ 
/* 10:   */ public class BlockNewLog
/* 11:   */   extends BlockLog
/* 12:   */ {
/* 13:12 */   public static final String[] field_150169_M = { "acacia", "big_oak" };
/* 14:   */   private static final String __OBFID = "CL_00000277";
/* 15:   */   
/* 16:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 17:   */   {
/* 18:17 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/* 19:18 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 23:   */   {
/* 24:23 */     this.field_150167_a = new IIcon[field_150169_M.length];
/* 25:24 */     this.field_150166_b = new IIcon[field_150169_M.length];
/* 26:26 */     for (int var2 = 0; var2 < this.field_150167_a.length; var2++)
/* 27:   */     {
/* 28:28 */       this.field_150167_a[var2] = p_149651_1_.registerIcon(getTextureName() + "_" + field_150169_M[var2]);
/* 29:29 */       this.field_150166_b[var2] = p_149651_1_.registerIcon(getTextureName() + "_" + field_150169_M[var2] + "_top");
/* 30:   */     }
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockNewLog
 * JD-Core Version:    0.7.0.1
 */