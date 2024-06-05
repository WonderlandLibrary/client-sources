package net.minecraft.src;

import java.util.*;

public abstract class BlockHalfSlab extends Block
{
    protected final boolean isDoubleSlab;
    
    public BlockHalfSlab(final int par1, final boolean par2, final Material par3Material) {
        super(par1, par3Material);
        this.isDoubleSlab = par2;
        if (par2) {
            BlockHalfSlab.opaqueCubeLookup[par1] = true;
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        this.setLightOpacity(255);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        if (this.isDoubleSlab) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            final boolean var5 = (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 0x8) != 0x0;
            if (var5) {
                this.setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            else {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
            }
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        if (this.isDoubleSlab) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    @Override
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return this.isDoubleSlab;
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, final int par9) {
        return this.isDoubleSlab ? par9 : ((par5 != 0 && (par5 == 1 || par7 <= 0.5)) ? par9 : (par9 | 0x8));
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return this.isDoubleSlab ? 2 : 1;
    }
    
    @Override
    public int damageDropped(final int par1) {
        return par1 & 0x7;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return this.isDoubleSlab;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        if (this.isDoubleSlab) {
            return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
        }
        if (par5 != 1 && par5 != 0 && !super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5)) {
            return false;
        }
        final int var6 = par2 + Facing.offsetsXForSide[Facing.oppositeSide[par5]];
        final int var7 = par3 + Facing.offsetsYForSide[Facing.oppositeSide[par5]];
        final int var8 = par4 + Facing.offsetsZForSide[Facing.oppositeSide[par5]];
        final boolean var9 = (par1IBlockAccess.getBlockMetadata(var6, var7, var8) & 0x8) != 0x0;
        return var9 ? (par5 == 0 || (par5 == 1 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5)) || !isBlockSingleSlab(par1IBlockAccess.getBlockId(par2, par3, par4)) || (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 0x8) == 0x0) : (par5 == 1 || (par5 == 0 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5)) || !isBlockSingleSlab(par1IBlockAccess.getBlockId(par2, par3, par4)) || (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 0x8) != 0x0);
    }
    
    private static boolean isBlockSingleSlab(final int par0) {
        return par0 == Block.stoneSingleSlab.blockID || par0 == Block.woodSingleSlab.blockID;
    }
    
    public abstract String getFullSlabName(final int p0);
    
    @Override
    public int getDamageValue(final World par1World, final int par2, final int par3, final int par4) {
        return super.getDamageValue(par1World, par2, par3, par4) & 0x7;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return isBlockSingleSlab(this.blockID) ? this.blockID : ((this.blockID == Block.stoneDoubleSlab.blockID) ? Block.stoneSingleSlab.blockID : ((this.blockID == Block.woodDoubleSlab.blockID) ? Block.woodSingleSlab.blockID : Block.stoneSingleSlab.blockID));
    }
}
