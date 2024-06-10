/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.init.Items;
/*  5:   */ import net.minecraft.item.Item;
/*  6:   */ 
/*  7:   */ public class BlockGravel
/*  8:   */   extends BlockFalling
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000252";
/* 11:   */   
/* 12:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 13:   */   {
/* 14:13 */     if (p_149650_3_ > 3) {
/* 15:15 */       p_149650_3_ = 3;
/* 16:   */     }
/* 17:18 */     return p_149650_2_.nextInt(10 - p_149650_3_ * 3) == 0 ? Items.flint : Item.getItemFromBlock(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockGravel
 * JD-Core Version:    0.7.0.1
 */