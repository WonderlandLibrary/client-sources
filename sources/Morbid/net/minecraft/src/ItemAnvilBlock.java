package net.minecraft.src;

public class ItemAnvilBlock extends ItemMultiTextureTile
{
    public ItemAnvilBlock(final Block par1Block) {
        super(par1Block.blockID - 256, par1Block, BlockAnvil.statuses);
    }
    
    @Override
    public int getMetadata(final int par1) {
        return par1 << 2;
    }
}
