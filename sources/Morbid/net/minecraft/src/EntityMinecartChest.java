package net.minecraft.src;

public class EntityMinecartChest extends EntityMinecartContainer
{
    public EntityMinecartChest(final World par1World) {
        super(par1World);
    }
    
    public EntityMinecartChest(final World par1, final double par2, final double par4, final double par6) {
        super(par1, par2, par4, par6);
    }
    
    @Override
    public void killMinecart(final DamageSource par1DamageSource) {
        super.killMinecart(par1DamageSource);
        this.dropItemWithOffset(Block.chest.blockID, 1, 0.0f);
    }
    
    @Override
    public int getSizeInventory() {
        return 27;
    }
    
    @Override
    public int getMinecartType() {
        return 1;
    }
    
    @Override
    public Block getDefaultDisplayTile() {
        return Block.chest;
    }
    
    @Override
    public int getDefaultDisplayTileOffset() {
        return 8;
    }
}
