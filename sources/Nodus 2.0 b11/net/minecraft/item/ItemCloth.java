/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.BlockColored;
/*  5:   */ import net.minecraft.util.IIcon;
/*  6:   */ 
/*  7:   */ public class ItemCloth
/*  8:   */   extends ItemBlock
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000075";
/* 11:   */   
/* 12:   */   public ItemCloth(Block p_i45358_1_)
/* 13:   */   {
/* 14:13 */     super(p_i45358_1_);
/* 15:14 */     setMaxDamage(0);
/* 16:15 */     setHasSubtypes(true);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public IIcon getIconFromDamage(int par1)
/* 20:   */   {
/* 21:23 */     return this.field_150939_a.func_149735_b(2, BlockColored.func_150032_b(par1));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getMetadata(int par1)
/* 25:   */   {
/* 26:31 */     return par1;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 30:   */   {
/* 31:40 */     return super.getUnlocalizedName() + "." + ItemDye.field_150923_a[BlockColored.func_150032_b(par1ItemStack.getItemDamage())];
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemCloth
 * JD-Core Version:    0.7.0.1
 */