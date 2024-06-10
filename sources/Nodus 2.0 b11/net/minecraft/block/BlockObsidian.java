/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.MapColor;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ 
/*  8:   */ public class BlockObsidian
/*  9:   */   extends BlockStone
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000279";
/* 12:   */   
/* 13:   */   public int quantityDropped(Random p_149745_1_)
/* 14:   */   {
/* 15:17 */     return 1;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 19:   */   {
/* 20:22 */     return Item.getItemFromBlock(Blocks.obsidian);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public MapColor getMapColor(int p_149728_1_)
/* 24:   */   {
/* 25:27 */     return MapColor.field_151654_J;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockObsidian
 * JD-Core Version:    0.7.0.1
 */