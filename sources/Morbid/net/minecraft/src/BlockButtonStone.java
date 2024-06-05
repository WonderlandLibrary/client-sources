package net.minecraft.src;

public class BlockButtonStone extends BlockButton
{
    protected BlockButtonStone(final int par1) {
        super(par1, false);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return Block.stone.getBlockTextureFromSide(1);
    }
}
