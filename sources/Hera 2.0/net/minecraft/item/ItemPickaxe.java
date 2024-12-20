/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class ItemPickaxe
/*    */   extends ItemTool {
/* 11 */   private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet((Object[])new Block[] { Blocks.activator_rail, Blocks.coal_ore, Blocks.cobblestone, Blocks.detector_rail, Blocks.diamond_block, Blocks.diamond_ore, (Block)Blocks.double_stone_slab, Blocks.golden_rail, Blocks.gold_block, Blocks.gold_ore, Blocks.ice, Blocks.iron_block, Blocks.iron_ore, Blocks.lapis_block, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.mossy_cobblestone, Blocks.netherrack, Blocks.packed_ice, Blocks.rail, Blocks.redstone_ore, Blocks.sandstone, Blocks.red_sandstone, Blocks.stone, (Block)Blocks.stone_slab });
/*    */ 
/*    */   
/*    */   protected ItemPickaxe(Item.ToolMaterial material) {
/* 15 */     super(2.0F, material, EFFECTIVE_ON);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canHarvestBlock(Block blockIn) {
/* 23 */     return (blockIn == Blocks.obsidian) ? ((this.toolMaterial.getHarvestLevel() == 3)) : ((blockIn != Blocks.diamond_block && blockIn != Blocks.diamond_ore) ? ((blockIn != Blocks.emerald_ore && blockIn != Blocks.emerald_block) ? ((blockIn != Blocks.gold_block && blockIn != Blocks.gold_ore) ? ((blockIn != Blocks.iron_block && blockIn != Blocks.iron_ore) ? ((blockIn != Blocks.lapis_block && blockIn != Blocks.lapis_ore) ? ((blockIn != Blocks.redstone_ore && blockIn != Blocks.lit_redstone_ore) ? ((blockIn.getMaterial() == Material.rock) ? true : ((blockIn.getMaterial() == Material.iron) ? true : ((blockIn.getMaterial() == Material.anvil)))) : ((this.toolMaterial.getHarvestLevel() >= 2))) : ((this.toolMaterial.getHarvestLevel() >= 1))) : ((this.toolMaterial.getHarvestLevel() >= 1))) : ((this.toolMaterial.getHarvestLevel() >= 2))) : ((this.toolMaterial.getHarvestLevel() >= 2))) : ((this.toolMaterial.getHarvestLevel() >= 2)));
/*    */   }
/*    */ 
/*    */   
/*    */   public float getStrVsBlock(ItemStack stack, Block block) {
/* 28 */     return (block.getMaterial() != Material.iron && block.getMaterial() != Material.anvil && block.getMaterial() != Material.rock) ? super.getStrVsBlock(stack, block) : this.efficiencyOnProperMaterial;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemPickaxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */