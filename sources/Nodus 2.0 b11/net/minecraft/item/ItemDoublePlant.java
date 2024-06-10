/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.BlockDoublePlant;
/*  5:   */ import net.minecraft.util.IIcon;
/*  6:   */ import net.minecraft.world.ColorizerGrass;
/*  7:   */ 
/*  8:   */ public class ItemDoublePlant
/*  9:   */   extends ItemMultiTexture
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000021";
/* 12:   */   
/* 13:   */   public ItemDoublePlant(Block p_i45335_1_, BlockDoublePlant p_i45335_2_, String[] p_i45335_3_)
/* 14:   */   {
/* 15:14 */     super(p_i45335_1_, p_i45335_2_, p_i45335_3_);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public IIcon getIconFromDamage(int par1)
/* 19:   */   {
/* 20:22 */     return BlockDoublePlant.func_149890_d(par1) == 0 ? ((BlockDoublePlant)this.field_150941_b).field_149891_b[0] : ((BlockDoublePlant)this.field_150941_b).func_149888_a(true, par1);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
/* 24:   */   {
/* 25:27 */     int var3 = BlockDoublePlant.func_149890_d(par1ItemStack.getItemDamage());
/* 26:28 */     return (var3 != 2) && (var3 != 3) ? super.getColorFromItemStack(par1ItemStack, par2) : ColorizerGrass.getGrassColor(0.5D, 1.0D);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemDoublePlant
 * JD-Core Version:    0.7.0.1
 */