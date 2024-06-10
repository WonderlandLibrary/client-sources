/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Sets;
/*  4:   */ import java.util.Set;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.block.material.Material;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ 
/*  9:   */ public class ItemPickaxe
/* 10:   */   extends ItemTool
/* 11:   */ {
/* 12:11 */   private static final Set field_150915_c = Sets.newHashSet(new Block[] { Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail });
/* 13:   */   private static final String __OBFID = "CL_00000053";
/* 14:   */   
/* 15:   */   protected ItemPickaxe(Item.ToolMaterial p_i45347_1_)
/* 16:   */   {
/* 17:16 */     super(2.0F, p_i45347_1_, field_150915_c);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean func_150897_b(Block p_150897_1_)
/* 21:   */   {
/* 22:21 */     return this.toolMaterial.getHarvestLevel() == 3;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
/* 26:   */   {
/* 27:26 */     return (p_150893_2_.getMaterial() != Material.iron) && (p_150893_2_.getMaterial() != Material.anvil) && (p_150893_2_.getMaterial() != Material.rock) ? super.func_150893_a(p_150893_1_, p_150893_2_) : this.efficiencyOnProperMaterial;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemPickaxe
 * JD-Core Version:    0.7.0.1
 */