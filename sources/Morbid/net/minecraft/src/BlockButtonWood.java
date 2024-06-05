package net.minecraft.src;

public class BlockButtonWood extends BlockButton
{
    protected BlockButtonWood(final int par1) {
        super(par1, true);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return Block.planks.getBlockTextureFromSide(1);
    }
}
