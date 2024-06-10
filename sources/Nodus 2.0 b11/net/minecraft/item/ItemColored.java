/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.util.IIcon;
/*  5:   */ 
/*  6:   */ public class ItemColored
/*  7:   */   extends ItemBlock
/*  8:   */ {
/*  9:   */   private final Block field_150944_b;
/* 10:   */   private String[] field_150945_c;
/* 11:   */   private static final String __OBFID = "CL_00000003";
/* 12:   */   
/* 13:   */   public ItemColored(Block p_i45332_1_, boolean p_i45332_2_)
/* 14:   */   {
/* 15:14 */     super(p_i45332_1_);
/* 16:15 */     this.field_150944_b = p_i45332_1_;
/* 17:17 */     if (p_i45332_2_)
/* 18:   */     {
/* 19:19 */       setMaxDamage(0);
/* 20:20 */       setHasSubtypes(true);
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
/* 25:   */   {
/* 26:26 */     return this.field_150944_b.getRenderColor(par1ItemStack.getItemDamage());
/* 27:   */   }
/* 28:   */   
/* 29:   */   public IIcon getIconFromDamage(int par1)
/* 30:   */   {
/* 31:34 */     return this.field_150944_b.getIcon(0, par1);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int getMetadata(int par1)
/* 35:   */   {
/* 36:42 */     return par1;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public ItemColored func_150943_a(String[] p_150943_1_)
/* 40:   */   {
/* 41:47 */     this.field_150945_c = p_150943_1_;
/* 42:48 */     return this;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 46:   */   {
/* 47:57 */     if (this.field_150945_c == null) {
/* 48:59 */       return super.getUnlocalizedName(par1ItemStack);
/* 49:   */     }
/* 50:63 */     int var2 = par1ItemStack.getItemDamage();
/* 51:64 */     return (var2 >= 0) && (var2 < this.field_150945_c.length) ? super.getUnlocalizedName(par1ItemStack) + "." + this.field_150945_c[var2] : super.getUnlocalizedName(par1ItemStack);
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemColored
 * JD-Core Version:    0.7.0.1
 */