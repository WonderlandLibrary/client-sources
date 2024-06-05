package net.minecraft.src;

public class ItemPiston extends ItemBlock
{
    public ItemPiston(final int par1) {
        super(par1);
    }
    
    @Override
    public int getMetadata(final int par1) {
        return 7;
    }
}
