package net.minecraft.src;

public class ItemAxe extends ItemTool
{
    private static Block[] blocksEffectiveAgainst;
    
    static {
        ItemAxe.blocksEffectiveAgainst = new Block[] { Block.planks, Block.bookShelf, Block.wood, Block.chest, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.pumpkin, Block.pumpkinLantern };
    }
    
    protected ItemAxe(final int par1, final EnumToolMaterial par2EnumToolMaterial) {
        super(par1, 3, par2EnumToolMaterial, ItemAxe.blocksEffectiveAgainst);
    }
    
    @Override
    public float getStrVsBlock(final ItemStack par1ItemStack, final Block par2Block) {
        return (par2Block != null && (par2Block.blockMaterial == Material.wood || par2Block.blockMaterial == Material.plants || par2Block.blockMaterial == Material.vine)) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(par1ItemStack, par2Block);
    }
}
