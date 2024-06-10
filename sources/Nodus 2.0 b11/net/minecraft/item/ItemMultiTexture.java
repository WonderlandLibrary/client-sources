/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.util.IIcon;
/*  5:   */ 
/*  6:   */ public class ItemMultiTexture
/*  7:   */   extends ItemBlock
/*  8:   */ {
/*  9:   */   protected final Block field_150941_b;
/* 10:   */   protected final String[] field_150942_c;
/* 11:   */   private static final String __OBFID = "CL_00000051";
/* 12:   */   
/* 13:   */   public ItemMultiTexture(Block p_i45346_1_, Block p_i45346_2_, String[] p_i45346_3_)
/* 14:   */   {
/* 15:14 */     super(p_i45346_1_);
/* 16:15 */     this.field_150941_b = p_i45346_2_;
/* 17:16 */     this.field_150942_c = p_i45346_3_;
/* 18:17 */     setMaxDamage(0);
/* 19:18 */     setHasSubtypes(true);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public IIcon getIconFromDamage(int par1)
/* 23:   */   {
/* 24:26 */     return this.field_150941_b.getIcon(2, par1);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getMetadata(int par1)
/* 28:   */   {
/* 29:34 */     return par1;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 33:   */   {
/* 34:43 */     int var2 = par1ItemStack.getItemDamage();
/* 35:45 */     if ((var2 < 0) || (var2 >= this.field_150942_c.length)) {
/* 36:47 */       var2 = 0;
/* 37:   */     }
/* 38:50 */     return super.getUnlocalizedName() + "." + this.field_150942_c[var2];
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemMultiTexture
 * JD-Core Version:    0.7.0.1
 */