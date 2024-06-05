package net.minecraft.src;

public class BlockLever extends Block
{
    protected BlockLever(final int par1) {
        super(par1, Material.circuits);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return null;
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
    public int getRenderType() {
        return 12;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return (par5 == 0 && par1World.isBlockNormalCube(par2, par3 + 1, par4)) || (par5 == 1 && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)) || (par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1)) || (par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1)) || (par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4)) || (par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4));
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.isBlockNormalCube(par2 - 1, par3, par4) || par1World.isBlockNormalCube(par2 + 1, par3, par4) || par1World.isBlockNormalCube(par2, par3, par4 - 1) || par1World.isBlockNormalCube(par2, par3, par4 + 1) || par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || par1World.isBlockNormalCube(par2, par3 + 1, par4);
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, final int par9) {
        final int var11 = par9 & 0x8;
        final int var12 = par9 & 0x7;
        byte var13 = -1;
        if (par5 == 0 && par1World.isBlockNormalCube(par2, par3 + 1, par4)) {
            var13 = 0;
        }
        if (par5 == 1 && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)) {
            var13 = 5;
        }
        if (par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
            var13 = 4;
        }
        if (par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
            var13 = 3;
        }
        if (par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
            var13 = 2;
        }
        if (par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
            var13 = 1;
        }
        return var13 + var11;
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        final int var7 = par1World.getBlockMetadata(par2, par3, par4);
        final int var8 = var7 & 0x7;
        final int var9 = var7 & 0x8;
        if (var8 == invertMetadata(1)) {
            if ((MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0f / 360.0f + 0.5) & 0x1) == 0x0) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x5 | var9, 2);
            }
            else {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x6 | var9, 2);
            }
        }
        else if (var8 == invertMetadata(0)) {
            if ((MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0f / 360.0f + 0.5) & 0x1) == 0x0) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x7 | var9, 2);
            }
            else {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var9, 2);
            }
        }
    }
    
    public static int invertMetadata(final int par0) {
        switch (par0) {
            case 0: {
                return 0;
            }
            case 1: {
                return 5;
            }
            case 2: {
                return 4;
            }
            case 3: {
                return 3;
            }
            case 4: {
                return 2;
            }
            case 5: {
                return 1;
            }
            default: {
                return -1;
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (this.checkIfAttachedToBlock(par1World, par2, par3, par4)) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4) & 0x7;
            boolean var7 = false;
            if (!par1World.isBlockNormalCube(par2 - 1, par3, par4) && var6 == 1) {
                var7 = true;
            }
            if (!par1World.isBlockNormalCube(par2 + 1, par3, par4) && var6 == 2) {
                var7 = true;
            }
            if (!par1World.isBlockNormalCube(par2, par3, par4 - 1) && var6 == 3) {
                var7 = true;
            }
            if (!par1World.isBlockNormalCube(par2, par3, par4 + 1) && var6 == 4) {
                var7 = true;
            }
            if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && var6 == 5) {
                var7 = true;
            }
            if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && var6 == 6) {
                var7 = true;
            }
            if (!par1World.isBlockNormalCube(par2, par3 + 1, par4) && var6 == 0) {
                var7 = true;
            }
            if (!par1World.isBlockNormalCube(par2, par3 + 1, par4) && var6 == 7) {
                var7 = true;
            }
            if (var7) {
                this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }
    
    private boolean checkIfAttachedToBlock(final World par1World, final int par2, final int par3, final int par4) {
        if (!this.canPlaceBlockAt(par1World, par2, par3, par4)) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
            return false;
        }
        return true;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 0x7;
        float var6 = 0.1875f;
        if (var5 == 1) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - var6, var6 * 2.0f, 0.8f, 0.5f + var6);
        }
        else if (var5 == 2) {
            this.setBlockBounds(1.0f - var6 * 2.0f, 0.2f, 0.5f - var6, 1.0f, 0.8f, 0.5f + var6);
        }
        else if (var5 == 3) {
            this.setBlockBounds(0.5f - var6, 0.2f, 0.0f, 0.5f + var6, 0.8f, var6 * 2.0f);
        }
        else if (var5 == 4) {
            this.setBlockBounds(0.5f - var6, 0.2f, 1.0f - var6 * 2.0f, 0.5f + var6, 0.8f, 1.0f);
        }
        else if (var5 != 5 && var5 != 6) {
            if (var5 == 0 || var5 == 7) {
                var6 = 0.25f;
                this.setBlockBounds(0.5f - var6, 0.4f, 0.5f - var6, 0.5f + var6, 1.0f, 0.5f + var6);
            }
        }
        else {
            var6 = 0.25f;
            this.setBlockBounds(0.5f - var6, 0.0f, 0.5f - var6, 0.5f + var6, 0.6f, 0.5f + var6);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        final int var10 = par1World.getBlockMetadata(par2, par3, par4);
        final int var11 = var10 & 0x7;
        final int var12 = 8 - (var10 & 0x8);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var11 + var12, 3);
        par1World.playSoundEffect(par2 + 0.5, par3 + 0.5, par4 + 0.5, "random.click", 0.3f, (var12 > 0) ? 0.6f : 0.5f);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
        if (var11 == 1) {
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
        }
        else if (var11 == 2) {
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
        }
        else if (var11 == 3) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
        }
        else if (var11 == 4) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
        }
        else if (var11 != 5 && var11 != 6) {
            if (var11 == 0 || var11 == 7) {
                par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
            }
        }
        else {
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
        }
        return true;
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        if ((par6 & 0x8) > 0) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
            final int var7 = par6 & 0x7;
            if (var7 == 1) {
                par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
            }
            else if (var7 == 2) {
                par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
            }
            else if (var7 == 3) {
                par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
            }
            else if (var7 == 4) {
                par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
            }
            else if (var7 != 5 && var7 != 6) {
                if (var7 == 0 || var7 == 7) {
                    par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
                }
            }
            else {
                par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
            }
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return ((par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 0x8) > 0) ? 15 : 0;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        if ((var6 & 0x8) == 0x0) {
            return 0;
        }
        final int var7 = var6 & 0x7;
        return (var7 == 0 && par5 == 0) ? 15 : ((var7 == 7 && par5 == 0) ? 15 : ((var7 == 6 && par5 == 1) ? 15 : ((var7 == 5 && par5 == 1) ? 15 : ((var7 == 4 && par5 == 2) ? 15 : ((var7 == 3 && par5 == 3) ? 15 : ((var7 == 2 && par5 == 4) ? 15 : ((var7 == 1 && par5 == 5) ? 15 : 0)))))));
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
}
