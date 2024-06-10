/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.BlockLeaves;
/*  4:   */ import net.minecraft.util.IIcon;
/*  5:   */ 
/*  6:   */ public class ItemLeaves
/*  7:   */   extends ItemBlock
/*  8:   */ {
/*  9:   */   private final BlockLeaves field_150940_b;
/* 10:   */   private static final String __OBFID = "CL_00000046";
/* 11:   */   
/* 12:   */   public ItemLeaves(BlockLeaves p_i45344_1_)
/* 13:   */   {
/* 14:13 */     super(p_i45344_1_);
/* 15:14 */     this.field_150940_b = p_i45344_1_;
/* 16:15 */     setMaxDamage(0);
/* 17:16 */     setHasSubtypes(true);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getMetadata(int par1)
/* 21:   */   {
/* 22:24 */     return par1 | 0x4;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public IIcon getIconFromDamage(int par1)
/* 26:   */   {
/* 27:32 */     return this.field_150940_b.getIcon(0, par1);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
/* 31:   */   {
/* 32:37 */     return this.field_150940_b.getRenderColor(par1ItemStack.getItemDamage());
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 36:   */   {
/* 37:46 */     int var2 = par1ItemStack.getItemDamage();
/* 38:48 */     if ((var2 < 0) || (var2 >= this.field_150940_b.func_150125_e().length)) {
/* 39:50 */       var2 = 0;
/* 40:   */     }
/* 41:53 */     return super.getUnlocalizedName() + "." + this.field_150940_b.func_150125_e()[var2];
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemLeaves
 * JD-Core Version:    0.7.0.1
 */