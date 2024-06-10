/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Sets;
/*  4:   */ import java.util.Set;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ 
/*  8:   */ public class ItemSpade
/*  9:   */   extends ItemTool
/* 10:   */ {
/* 11:10 */   private static final Set field_150916_c = Sets.newHashSet(new Block[] { Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium });
/* 12:   */   private static final String __OBFID = "CL_00000063";
/* 13:   */   
/* 14:   */   public ItemSpade(Item.ToolMaterial p_i45353_1_)
/* 15:   */   {
/* 16:15 */     super(1.0F, p_i45353_1_, field_150916_c);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean func_150897_b(Block p_150897_1_)
/* 20:   */   {
/* 21:20 */     return p_150897_1_ == Blocks.snow_layer;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemSpade
 * JD-Core Version:    0.7.0.1
 */