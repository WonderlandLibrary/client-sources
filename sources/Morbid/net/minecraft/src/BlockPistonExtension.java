package net.minecraft.src;

import java.util.*;

public class BlockPistonExtension extends Block
{
    private Icon headTexture;
    
    public BlockPistonExtension(final int par1) {
        super(par1, Material.piston);
        this.headTexture = null;
        this.setStepSound(BlockPistonExtension.soundStoneFootstep);
        this.setHardness(0.5f);
    }
    
    public void setHeadTexture(final Icon par1Icon) {
        this.headTexture = par1Icon;
    }
    
    public void clearHeadTexture() {
        this.headTexture = null;
    }
    
    @Override
    public void breakBlock(final World par1World, int par2, int par3, int par4, final int par5, int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        final int var7 = Facing.oppositeSide[getDirectionMeta(par6)];
        par2 += Facing.offsetsXForSide[var7];
        par3 += Facing.offsetsYForSide[var7];
        par4 += Facing.offsetsZForSide[var7];
        final int var8 = par1World.getBlockId(par2, par3, par4);
        if (var8 == Block.pistonBase.blockID || var8 == Block.pistonStickyBase.blockID) {
            par6 = par1World.getBlockMetadata(par2, par3, par4);
            if (BlockPistonBase.isExtended(par6)) {
                Block.blocksList[var8].dropBlockAsItem(par1World, par2, par3, par4, par6, 0);
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        final int var3 = getDirectionMeta(par2);
        return (par1 == var3) ? ((this.headTexture != null) ? this.headTexture : (((par2 & 0x8) != 0x0) ? BlockPistonBase.func_94496_b("piston_top_sticky") : BlockPistonBase.func_94496_b("piston_top"))) : ((var3 < 6 && par1 == Facing.oppositeSide[var3]) ? BlockPistonBase.func_94496_b("piston_top") : BlockPistonBase.func_94496_b("piston_side"));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
    }
    
    @Override
    public int getRenderType() {
        return 17;
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
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return false;
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    @Override
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
        final int var8 = par1World.getBlockMetadata(par2, par3, par4);
        switch (getDirectionMeta(var8)) {
            case 0: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.setBlockBounds(0.375f, 0.25f, 0.375f, 0.625f, 1.0f, 0.625f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                break;
            }
            case 1: {
                this.setBlockBounds(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.setBlockBounds(0.375f, 0.0f, 0.375f, 0.625f, 0.75f, 0.625f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                break;
            }
            case 2: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.setBlockBounds(0.25f, 0.375f, 0.25f, 0.75f, 0.625f, 1.0f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                break;
            }
            case 3: {
                this.setBlockBounds(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.setBlockBounds(0.25f, 0.375f, 0.0f, 0.75f, 0.625f, 0.75f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                break;
            }
            case 4: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.setBlockBounds(0.375f, 0.25f, 0.25f, 0.625f, 0.75f, 1.0f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                break;
            }
            case 5: {
                this.setBlockBounds(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.setBlockBounds(0.0f, 0.375f, 0.25f, 0.75f, 0.625f, 0.75f);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                break;
            }
        }
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        switch (getDirectionMeta(var5)) {
            case 0: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                break;
            }
            case 1: {
                this.setBlockBounds(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            }
            case 2: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                break;
            }
            case 5: {
                this.setBlockBounds(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = getDirectionMeta(par1World.getBlockMetadata(par2, par3, par4));
        final int var7 = par1World.getBlockId(par2 - Facing.offsetsXForSide[var6], par3 - Facing.offsetsYForSide[var6], par4 - Facing.offsetsZForSide[var6]);
        if (var7 != Block.pistonBase.blockID && var7 != Block.pistonStickyBase.blockID) {
            par1World.setBlockToAir(par2, par3, par4);
        }
        else {
            Block.blocksList[var7].onNeighborBlockChange(par1World, par2 - Facing.offsetsXForSide[var6], par3 - Facing.offsetsYForSide[var6], par4 - Facing.offsetsZForSide[var6], par5);
        }
    }
    
    public static int getDirectionMeta(final int par0) {
        return par0 & 0x7;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return 0;
    }
}
