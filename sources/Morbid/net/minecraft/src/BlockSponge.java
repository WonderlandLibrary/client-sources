package net.minecraft.src;

public class BlockSponge extends Block
{
    protected BlockSponge(final int par1) {
        super(par1, Material.sponge);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
