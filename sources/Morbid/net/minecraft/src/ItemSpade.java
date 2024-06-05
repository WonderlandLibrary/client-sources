package net.minecraft.src;

public class ItemSpade extends ItemTool
{
    private static Block[] blocksEffectiveAgainst;
    
    static {
        ItemSpade.blocksEffectiveAgainst = new Block[] { Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay, Block.tilledField, Block.slowSand, Block.mycelium };
    }
    
    public ItemSpade(final int par1, final EnumToolMaterial par2EnumToolMaterial) {
        super(par1, 1, par2EnumToolMaterial, ItemSpade.blocksEffectiveAgainst);
    }
    
    @Override
    public boolean canHarvestBlock(final Block par1Block) {
        return par1Block == Block.snow || par1Block == Block.blockSnow;
    }
}
