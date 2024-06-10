/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Sets;
/*  4:   */ import java.util.Set;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.block.material.Material;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ 
/*  9:   */ public class ItemAxe
/* 10:   */   extends ItemTool
/* 11:   */ {
/* 12:11 */   private static final Set field_150917_c = Sets.newHashSet(new Block[] { Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin });
/* 13:   */   private static final String __OBFID = "CL_00001770";
/* 14:   */   
/* 15:   */   protected ItemAxe(Item.ToolMaterial p_i45327_1_)
/* 16:   */   {
/* 17:16 */     super(3.0F, p_i45327_1_, field_150917_c);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
/* 21:   */   {
/* 22:21 */     return (p_150893_2_.getMaterial() != Material.wood) && (p_150893_2_.getMaterial() != Material.plants) && (p_150893_2_.getMaterial() != Material.vine) ? super.func_150893_a(p_150893_1_, p_150893_2_) : this.efficiencyOnProperMaterial;
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemAxe
 * JD-Core Version:    0.7.0.1
 */