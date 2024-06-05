package net.minecraft.src;

public class ItemBlockWithMetadata extends ItemBlock
{
    private Block theBlock;
    
    public ItemBlockWithMetadata(final int par1, final Block par2Block) {
        super(par1);
        this.theBlock = par2Block;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public Icon getIconFromDamage(final int par1) {
        return this.theBlock.getIcon(2, par1);
    }
    
    @Override
    public int getMetadata(final int par1) {
        return par1;
    }
}
