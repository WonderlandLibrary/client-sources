/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.BlockAnvil;
/*  5:   */ 
/*  6:   */ public class ItemAnvilBlock
/*  7:   */   extends ItemMultiTexture
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00001764";
/* 10:   */   
/* 11:   */   public ItemAnvilBlock(Block par1Block)
/* 12:   */   {
/* 13:12 */     super(par1Block, par1Block, BlockAnvil.field_149834_a);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public int getMetadata(int par1)
/* 17:   */   {
/* 18:20 */     return par1 << 2;
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemAnvilBlock
 * JD-Core Version:    0.7.0.1
 */