package net.minecraft.src;

public class BlockSoulSand extends Block
{
    public BlockSoulSand(final int par1) {
        super(par1, Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        final float var5 = 0.125f;
        return AxisAlignedBB.getAABBPool().getAABB(par2, par3, par4, par2 + 1, par3 + 1 - var5, par4 + 1);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
        par5Entity.motionX *= 0.4;
        par5Entity.motionZ *= 0.4;
    }
}
