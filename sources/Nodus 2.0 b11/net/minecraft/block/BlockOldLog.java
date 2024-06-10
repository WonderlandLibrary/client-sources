/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.util.IIcon;
/*  9:   */ 
/* 10:   */ public class BlockOldLog
/* 11:   */   extends BlockLog
/* 12:   */ {
/* 13:12 */   public static final String[] field_150168_M = { "oak", "spruce", "birch", "jungle" };
/* 14:   */   private static final String __OBFID = "CL_00000281";
/* 15:   */   
/* 16:   */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 17:   */   {
/* 18:17 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/* 19:18 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
/* 20:19 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
/* 21:20 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 25:   */   {
/* 26:25 */     this.field_150167_a = new IIcon[field_150168_M.length];
/* 27:26 */     this.field_150166_b = new IIcon[field_150168_M.length];
/* 28:28 */     for (int var2 = 0; var2 < this.field_150167_a.length; var2++)
/* 29:   */     {
/* 30:30 */       this.field_150167_a[var2] = p_149651_1_.registerIcon(getTextureName() + "_" + field_150168_M[var2]);
/* 31:31 */       this.field_150166_b[var2] = p_149651_1_.registerIcon(getTextureName() + "_" + field_150168_M[var2] + "_top");
/* 32:   */     }
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockOldLog
 * JD-Core Version:    0.7.0.1
 */