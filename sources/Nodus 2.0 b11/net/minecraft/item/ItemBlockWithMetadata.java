/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.util.IIcon;
/*  5:   */ 
/*  6:   */ public class ItemBlockWithMetadata
/*  7:   */   extends ItemBlock
/*  8:   */ {
/*  9:   */   private Block field_150950_b;
/* 10:   */   private static final String __OBFID = "CL_00001769";
/* 11:   */   
/* 12:   */   public ItemBlockWithMetadata(Block p_i45326_1_, Block p_i45326_2_)
/* 13:   */   {
/* 14:13 */     super(p_i45326_1_);
/* 15:14 */     this.field_150950_b = p_i45326_2_;
/* 16:15 */     setMaxDamage(0);
/* 17:16 */     setHasSubtypes(true);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public IIcon getIconFromDamage(int par1)
/* 21:   */   {
/* 22:24 */     return this.field_150950_b.getIcon(2, par1);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getMetadata(int par1)
/* 26:   */   {
/* 27:32 */     return par1;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemBlockWithMetadata
 * JD-Core Version:    0.7.0.1
 */