package net.minecraft.src;

public class ItemPickaxe extends ItemTool
{
    private static Block[] blocksEffectiveAgainst;
    
    static {
        ItemPickaxe.blocksEffectiveAgainst = new Block[] { Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered, Block.railActivator };
    }
    
    protected ItemPickaxe(final int par1, final EnumToolMaterial par2EnumToolMaterial) {
        super(par1, 2, par2EnumToolMaterial, ItemPickaxe.blocksEffectiveAgainst);
    }
    
    @Override
    public boolean canHarvestBlock(final Block par1Block) {
        return (par1Block == Block.obsidian) ? (this.toolMaterial.getHarvestLevel() == 3) : ((par1Block != Block.blockDiamond && par1Block != Block.oreDiamond) ? ((par1Block != Block.oreEmerald && par1Block != Block.blockEmerald) ? ((par1Block != Block.blockGold && par1Block != Block.oreGold) ? ((par1Block != Block.blockIron && par1Block != Block.oreIron) ? ((par1Block != Block.blockLapis && par1Block != Block.oreLapis) ? ((par1Block != Block.oreRedstone && par1Block != Block.oreRedstoneGlowing) ? (par1Block.blockMaterial == Material.rock || par1Block.blockMaterial == Material.iron || par1Block.blockMaterial == Material.anvil) : (this.toolMaterial.getHarvestLevel() >= 2)) : (this.toolMaterial.getHarvestLevel() >= 1)) : (this.toolMaterial.getHarvestLevel() >= 1)) : (this.toolMaterial.getHarvestLevel() >= 2)) : (this.toolMaterial.getHarvestLevel() >= 2)) : (this.toolMaterial.getHarvestLevel() >= 2));
    }
    
    @Override
    public float getStrVsBlock(final ItemStack par1ItemStack, final Block par2Block) {
        return (par2Block != null && (par2Block.blockMaterial == Material.iron || par2Block.blockMaterial == Material.anvil || par2Block.blockMaterial == Material.rock)) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(par1ItemStack, par2Block);
    }
}
