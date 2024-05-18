package net.minecraft.src;

import java.util.*;

public abstract class BlockButton extends Block
{
    private final boolean sensible;
    
    protected BlockButton(final int par1, final boolean par2) {
        super(par1, Material.circuits);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.sensible = par2;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return null;
    }
    
    @Override
    public int tickRate(final World par1World) {
        return this.sensible ? 30 : 20;
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
    public boolean canPlaceBlockOnSide(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return (par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1)) || (par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1)) || (par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4)) || (par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4));
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.isBlockNormalCube(par2 - 1, par3, par4) || par1World.isBlockNormalCube(par2 + 1, par3, par4) || par1World.isBlockNormalCube(par2, par3, par4 - 1) || par1World.isBlockNormalCube(par2, par3, par4 + 1);
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, final int par9) {
        int var10 = par1World.getBlockMetadata(par2, par3, par4);
        final int var11 = var10 & 0x8;
        var10 &= 0x7;
        if (par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
            var10 = 4;
        }
        else if (par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
            var10 = 3;
        }
        else if (par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
            var10 = 2;
        }
        else if (par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
            var10 = 1;
        }
        else {
            var10 = this.getOrientation(par1World, par2, par3, par4);
        }
        return var10 + var11;
    }
    
    private int getOrientation(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.isBlockNormalCube(par2 - 1, par3, par4) ? 1 : (par1World.isBlockNormalCube(par2 + 1, par3, par4) ? 2 : (par1World.isBlockNormalCube(par2, par3, par4 - 1) ? 3 : (par1World.isBlockNormalCube(par2, par3, par4 + 1) ? 4 : 1)));
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (this.redundantCanPlaceBlockAt(par1World, par2, par3, par4)) {
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
            if (var7) {
                this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }
    
    private boolean redundantCanPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        if (!this.canPlaceBlockAt(par1World, par2, par3, par4)) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
            return false;
        }
        return true;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        this.func_82534_e(var5);
    }
    
    private void func_82534_e(final int par1) {
        final int var2 = par1 & 0x7;
        final boolean var3 = (par1 & 0x8) > 0;
        final float var4 = 0.375f;
        final float var5 = 0.625f;
        final float var6 = 0.1875f;
        float var7 = 0.125f;
        if (var3) {
            var7 = 0.0625f;
        }
        if (var2 == 1) {
            this.setBlockBounds(0.0f, var4, 0.5f - var6, var7, var5, 0.5f + var6);
        }
        else if (var2 == 2) {
            this.setBlockBounds(1.0f - var7, var4, 0.5f - var6, 1.0f, var5, 0.5f + var6);
        }
        else if (var2 == 3) {
            this.setBlockBounds(0.5f - var6, var4, 0.0f, 0.5f + var6, var5, var7);
        }
        else if (var2 == 4) {
            this.setBlockBounds(0.5f - var6, var4, 1.0f - var7, 0.5f + var6, var5, 1.0f);
        }
    }
    
    @Override
    public void onBlockClicked(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer) {
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        final int var10 = par1World.getBlockMetadata(par2, par3, par4);
        final int var11 = var10 & 0x7;
        final int var12 = 8 - (var10 & 0x8);
        if (var12 == 0) {
            return true;
        }
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var11 + var12, 3);
        par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
        par1World.playSoundEffect(par2 + 0.5, par3 + 0.5, par4 + 0.5, "random.click", 0.3f, 0.6f);
        this.func_82536_d(par1World, par2, par3, par4, var11);
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
        return true;
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        if ((par6 & 0x8) > 0) {
            final int var7 = par6 & 0x7;
            this.func_82536_d(par1World, par2, par3, par4, var7);
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
        return (var7 == 5 && par5 == 1) ? 15 : ((var7 == 4 && par5 == 2) ? 15 : ((var7 == 3 && par5 == 3) ? 15 : ((var7 == 2 && par5 == 4) ? 15 : ((var7 == 1 && par5 == 5) ? 15 : 0))));
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!par1World.isRemote) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            if ((var6 & 0x8) != 0x0) {
                if (this.sensible) {
                    this.func_82535_o(par1World, par2, par3, par4);
                }
                else {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 & 0x7, 3);
                    final int var7 = var6 & 0x7;
                    this.func_82536_d(par1World, par2, par3, par4, var7);
                    par1World.playSoundEffect(par2 + 0.5, par3 + 0.5, par4 + 0.5, "random.click", 0.3f, 0.5f);
                    par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
                }
            }
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float var1 = 0.1875f;
        final float var2 = 0.125f;
        final float var3 = 0.125f;
        this.setBlockBounds(0.5f - var1, 0.5f - var2, 0.5f - var3, 0.5f + var1, 0.5f + var2, 0.5f + var3);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
        if (!par1World.isRemote && this.sensible && (par1World.getBlockMetadata(par2, par3, par4) & 0x8) == 0x0) {
            this.func_82535_o(par1World, par2, par3, par4);
        }
    }
    
    private void func_82535_o(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockMetadata(par2, par3, par4);
        final int var6 = var5 & 0x7;
        final boolean var7 = (var5 & 0x8) != 0x0;
        this.func_82534_e(var5);
        final List var8 = par1World.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getAABBPool().getAABB(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ));
        final boolean var9 = !var8.isEmpty();
        if (var9 && !var7) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | 0x8, 3);
            this.func_82536_d(par1World, par2, par3, par4, var6);
            par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
            par1World.playSoundEffect(par2 + 0.5, par3 + 0.5, par4 + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (!var9 && var7) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 3);
            this.func_82536_d(par1World, par2, par3, par4, var6);
            par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
            par1World.playSoundEffect(par2 + 0.5, par3 + 0.5, par4 + 0.5, "random.click", 0.3f, 0.5f);
        }
        if (var9) {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
        }
    }
    
    private void func_82536_d(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
        if (par5 == 1) {
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
        }
        else if (par5 == 2) {
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
        }
        else if (par5 == 3) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
        }
        else if (par5 == 4) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
        }
        else {
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
    }
}
