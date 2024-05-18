package net.minecraft.src;

public class BlockFenceGate extends BlockDirectional
{
    public BlockFenceGate(final int par1) {
        super(par1, Material.wood);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return Block.planks.getBlockTextureFromSide(par1);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid() && super.canPlaceBlockAt(par1World, par2, par3, par4);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockMetadata(par2, par3, par4);
        return isFenceGateOpen(var5) ? null : ((var5 != 2 && var5 != 0) ? AxisAlignedBB.getAABBPool().getAABB(par2 + 0.375f, par3, par4, par2 + 0.625f, par3 + 1.5f, par4 + 1) : AxisAlignedBB.getAABBPool().getAABB(par2, par3, par4 + 0.375f, par2 + 1, par3 + 1.5f, par4 + 0.625f));
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = BlockDirectional.getDirection(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
        if (var5 != 2 && var5 != 0) {
            this.setBlockBounds(0.375f, 0.0f, 0.0f, 0.625f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.375f, 1.0f, 1.0f, 0.625f);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean getBlocksMovement(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return isFenceGateOpen(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    @Override
    public int getRenderType() {
        return 21;
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        final int var7 = (MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) % 4;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        int var10 = par1World.getBlockMetadata(par2, par3, par4);
        if (isFenceGateOpen(var10)) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var10 & 0xFFFFFFFB, 2);
        }
        else {
            final int var11 = (MathHelper.floor_double(par5EntityPlayer.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) % 4;
            final int var12 = BlockDirectional.getDirection(var10);
            if (var12 == (var11 + 2) % 4) {
                var10 = var11;
            }
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var10 | 0x4, 2);
        }
        par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
        return true;
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!par1World.isRemote) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            final boolean var7 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
            if (var7 || (par5 > 0 && Block.blocksList[par5].canProvidePower())) {
                if (var7 && !isFenceGateOpen(var6)) {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | 0x4, 2);
                    par1World.playAuxSFXAtEntity(null, 1003, par2, par3, par4, 0);
                }
                else if (!var7 && isFenceGateOpen(var6)) {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 & 0xFFFFFFFB, 2);
                    par1World.playAuxSFXAtEntity(null, 1003, par2, par3, par4, 0);
                }
            }
        }
    }
    
    public static boolean isFenceGateOpen(final int par0) {
        return (par0 & 0x4) != 0x0;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return true;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
    }
}
