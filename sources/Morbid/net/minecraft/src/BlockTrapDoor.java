package net.minecraft.src;

public class BlockTrapDoor extends Block
{
    protected BlockTrapDoor(final int par1, final Material par2Material) {
        super(par1, par2Material);
        final float var3 = 0.5f;
        final float var4 = 1.0f;
        this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, var4, 0.5f + var3);
        this.setCreativeTab(CreativeTabs.tabRedstone);
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
        return !isTrapdoorOpen(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    @Override
    public int getRenderType() {
        return 0;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        this.setBlockBoundsForBlockRender(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float var1 = 0.1875f;
        this.setBlockBounds(0.0f, 0.5f - var1 / 2.0f, 0.0f, 1.0f, 0.5f + var1 / 2.0f, 1.0f);
    }
    
    public void setBlockBoundsForBlockRender(final int par1) {
        final float var2 = 0.1875f;
        if ((par1 & 0x8) != 0x0) {
            this.setBlockBounds(0.0f, 1.0f - var2, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, var2, 1.0f);
        }
        if (isTrapdoorOpen(par1)) {
            if ((par1 & 0x3) == 0x0) {
                this.setBlockBounds(0.0f, 0.0f, 1.0f - var2, 1.0f, 1.0f, 1.0f);
            }
            if ((par1 & 0x3) == 0x1) {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var2);
            }
            if ((par1 & 0x3) == 0x2) {
                this.setBlockBounds(1.0f - var2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            if ((par1 & 0x3) == 0x3) {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, var2, 1.0f, 1.0f);
            }
        }
    }
    
    @Override
    public void onBlockClicked(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer) {
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (this.blockMaterial == Material.iron) {
            return true;
        }
        final int var10 = par1World.getBlockMetadata(par2, par3, par4);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var10 ^ 0x4, 2);
        par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
        return true;
    }
    
    public void onPoweredBlockChange(final World par1World, final int par2, final int par3, final int par4, final boolean par5) {
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        final boolean var7 = (var6 & 0x4) > 0;
        if (var7 != par5) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 ^ 0x4, 2);
            par1World.playAuxSFXAtEntity(null, 1003, par2, par3, par4, 0);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!par1World.isRemote) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            int var7 = par2;
            int var8 = par4;
            if ((var6 & 0x3) == 0x0) {
                var8 = par4 + 1;
            }
            if ((var6 & 0x3) == 0x1) {
                --var8;
            }
            if ((var6 & 0x3) == 0x2) {
                var7 = par2 + 1;
            }
            if ((var6 & 0x3) == 0x3) {
                --var7;
            }
            if (!isValidSupportBlock(par1World.getBlockId(var7, par3, var8))) {
                par1World.setBlockToAir(par2, par3, par4);
                this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
            }
            final boolean var9 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
            if (var9 || (par5 > 0 && Block.blocksList[par5].canProvidePower())) {
                this.onPoweredBlockChange(par1World, par2, par3, par4, var9);
            }
        }
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World par1World, final int par2, final int par3, final int par4, final Vec3 par5Vec3, final Vec3 par6Vec3) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, final int par9) {
        int var10 = 0;
        if (par5 == 2) {
            var10 = 0;
        }
        if (par5 == 3) {
            var10 = 1;
        }
        if (par5 == 4) {
            var10 = 2;
        }
        if (par5 == 5) {
            var10 = 3;
        }
        if (par5 != 1 && par5 != 0 && par7 > 0.5f) {
            var10 |= 0x8;
        }
        return var10;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World par1World, int par2, final int par3, int par4, final int par5) {
        if (par5 == 0) {
            return false;
        }
        if (par5 == 1) {
            return false;
        }
        if (par5 == 2) {
            ++par4;
        }
        if (par5 == 3) {
            --par4;
        }
        if (par5 == 4) {
            ++par2;
        }
        if (par5 == 5) {
            --par2;
        }
        return isValidSupportBlock(par1World.getBlockId(par2, par3, par4));
    }
    
    public static boolean isTrapdoorOpen(final int par0) {
        return (par0 & 0x4) != 0x0;
    }
    
    private static boolean isValidSupportBlock(final int par0) {
        if (par0 <= 0) {
            return false;
        }
        final Block var1 = Block.blocksList[par0];
        return (var1 != null && var1.blockMaterial.isOpaque() && var1.renderAsNormalBlock()) || var1 == Block.glowStone || var1 instanceof BlockHalfSlab || var1 instanceof BlockStairs;
    }
}
