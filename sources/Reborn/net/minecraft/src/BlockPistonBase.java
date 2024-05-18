package net.minecraft.src;

import java.util.*;

public class BlockPistonBase extends Block
{
    private final boolean isSticky;
    private Icon innerTopIcon;
    private Icon bottomIcon;
    private Icon topIcon;
    
    public BlockPistonBase(final int par1, final boolean par2) {
        super(par1, Material.piston);
        this.isSticky = par2;
        this.setStepSound(BlockPistonBase.soundStoneFootstep);
        this.setHardness(0.5f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    public Icon getPistonExtensionTexture() {
        return this.topIcon;
    }
    
    public void func_96479_b(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6) {
        this.setBlockBounds(par1, par2, par3, par4, par5, par6);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        final int var3 = getOrientation(par2);
        return (var3 > 5) ? this.topIcon : ((par1 == var3) ? ((!isExtended(par2) && this.minX <= 0.0 && this.minY <= 0.0 && this.minZ <= 0.0 && this.maxX >= 1.0 && this.maxY >= 1.0 && this.maxZ >= 1.0) ? this.topIcon : this.innerTopIcon) : ((par1 == Facing.oppositeSide[var3]) ? this.bottomIcon : this.blockIcon));
    }
    
    public static Icon func_94496_b(final String par0Str) {
        return (par0Str == "piston_side") ? Block.pistonBase.blockIcon : ((par0Str == "piston_top") ? Block.pistonBase.topIcon : ((par0Str == "piston_top_sticky") ? Block.pistonStickyBase.topIcon : ((par0Str == "piston_inner_top") ? Block.pistonBase.innerTopIcon : null)));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("piston_side");
        this.topIcon = par1IconRegister.registerIcon(this.isSticky ? "piston_top_sticky" : "piston_top");
        this.innerTopIcon = par1IconRegister.registerIcon("piston_inner_top");
        this.bottomIcon = par1IconRegister.registerIcon("piston_bottom");
    }
    
    @Override
    public int getRenderType() {
        return 16;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        return false;
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        final int var7 = determineOrientation(par1World, par2, par3, par4, par5EntityLiving);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
        if (!par1World.isRemote) {
            this.updatePistonState(par1World, par2, par3, par4);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!par1World.isRemote) {
            this.updatePistonState(par1World, par2, par3, par4);
        }
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        if (!par1World.isRemote && par1World.getBlockTileEntity(par2, par3, par4) == null) {
            this.updatePistonState(par1World, par2, par3, par4);
        }
    }
    
    private void updatePistonState(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockMetadata(par2, par3, par4);
        final int var6 = getOrientation(var5);
        if (var6 != 7) {
            final boolean var7 = this.isIndirectlyPowered(par1World, par2, par3, par4, var6);
            if (var7 && !isExtended(var5)) {
                if (canExtend(par1World, par2, par3, par4, var6)) {
                    par1World.addBlockEvent(par2, par3, par4, this.blockID, 0, var6);
                }
            }
            else if (!var7 && isExtended(var5)) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
                par1World.addBlockEvent(par2, par3, par4, this.blockID, 1, var6);
            }
        }
    }
    
    private boolean isIndirectlyPowered(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return (par5 != 0 && par1World.getIndirectPowerOutput(par2, par3 - 1, par4, 0)) || (par5 != 1 && par1World.getIndirectPowerOutput(par2, par3 + 1, par4, 1)) || (par5 != 2 && par1World.getIndirectPowerOutput(par2, par3, par4 - 1, 2)) || (par5 != 3 && par1World.getIndirectPowerOutput(par2, par3, par4 + 1, 3)) || (par5 != 5 && par1World.getIndirectPowerOutput(par2 + 1, par3, par4, 5)) || (par5 != 4 && par1World.getIndirectPowerOutput(par2 - 1, par3, par4, 4)) || par1World.getIndirectPowerOutput(par2, par3, par4, 0) || par1World.getIndirectPowerOutput(par2, par3 + 2, par4, 1) || par1World.getIndirectPowerOutput(par2, par3 + 1, par4 - 1, 2) || par1World.getIndirectPowerOutput(par2, par3 + 1, par4 + 1, 3) || par1World.getIndirectPowerOutput(par2 - 1, par3 + 1, par4, 4) || par1World.getIndirectPowerOutput(par2 + 1, par3 + 1, par4, 5);
    }
    
    @Override
    public boolean onBlockEventReceived(final World par1World, int par2, int par3, int par4, final int par5, final int par6) {
        if (!par1World.isRemote) {
            final boolean var7 = this.isIndirectlyPowered(par1World, par2, par3, par4, par6);
            if (var7 && par5 == 1) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, par6 | 0x8, 2);
                return false;
            }
            if (!var7 && par5 == 0) {
                return false;
            }
        }
        if (par5 == 0) {
            if (!this.tryExtend(par1World, par2, par3, par4, par6)) {
                return false;
            }
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par6 | 0x8, 2);
            par1World.playSoundEffect(par2 + 0.5, par3 + 0.5, par4 + 0.5, "tile.piston.out", 0.5f, par1World.rand.nextFloat() * 0.25f + 0.6f);
        }
        else if (par5 == 1) {
            final TileEntity var8 = par1World.getBlockTileEntity(par2 + Facing.offsetsXForSide[par6], par3 + Facing.offsetsYForSide[par6], par4 + Facing.offsetsZForSide[par6]);
            if (var8 instanceof TileEntityPiston) {
                ((TileEntityPiston)var8).clearPistonTileEntity();
            }
            par1World.setBlock(par2, par3, par4, Block.pistonMoving.blockID, par6, 3);
            par1World.setBlockTileEntity(par2, par3, par4, BlockPistonMoving.getTileEntity(this.blockID, par6, par6, false, true));
            if (this.isSticky) {
                final int var9 = par2 + Facing.offsetsXForSide[par6] * 2;
                final int var10 = par3 + Facing.offsetsYForSide[par6] * 2;
                final int var11 = par4 + Facing.offsetsZForSide[par6] * 2;
                int var12 = par1World.getBlockId(var9, var10, var11);
                int var13 = par1World.getBlockMetadata(var9, var10, var11);
                boolean var14 = false;
                if (var12 == Block.pistonMoving.blockID) {
                    final TileEntity var15 = par1World.getBlockTileEntity(var9, var10, var11);
                    if (var15 instanceof TileEntityPiston) {
                        final TileEntityPiston var16 = (TileEntityPiston)var15;
                        if (var16.getPistonOrientation() == par6 && var16.isExtending()) {
                            var16.clearPistonTileEntity();
                            var12 = var16.getStoredBlockID();
                            var13 = var16.getBlockMetadata();
                            var14 = true;
                        }
                    }
                }
                if (!var14 && var12 > 0 && canPushBlock(var12, par1World, var9, var10, var11, false) && (Block.blocksList[var12].getMobilityFlag() == 0 || var12 == Block.pistonBase.blockID || var12 == Block.pistonStickyBase.blockID)) {
                    par2 += Facing.offsetsXForSide[par6];
                    par3 += Facing.offsetsYForSide[par6];
                    par4 += Facing.offsetsZForSide[par6];
                    par1World.setBlock(par2, par3, par4, Block.pistonMoving.blockID, var13, 3);
                    par1World.setBlockTileEntity(par2, par3, par4, BlockPistonMoving.getTileEntity(var12, var13, par6, false, false));
                    par1World.setBlockToAir(var9, var10, var11);
                }
                else if (!var14) {
                    par1World.setBlockToAir(par2 + Facing.offsetsXForSide[par6], par3 + Facing.offsetsYForSide[par6], par4 + Facing.offsetsZForSide[par6]);
                }
            }
            else {
                par1World.setBlockToAir(par2 + Facing.offsetsXForSide[par6], par3 + Facing.offsetsYForSide[par6], par4 + Facing.offsetsZForSide[par6]);
            }
            par1World.playSoundEffect(par2 + 0.5, par3 + 0.5, par4 + 0.5, "tile.piston.in", 0.5f, par1World.rand.nextFloat() * 0.15f + 0.6f);
        }
        return true;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        if (isExtended(var5)) {
            switch (getOrientation(var5)) {
                case 0: {
                    this.setBlockBounds(0.0f, 0.25f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 1: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
                    break;
                }
                case 2: {
                    this.setBlockBounds(0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 3: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.75f);
                    break;
                }
                case 4: {
                    this.setBlockBounds(0.25f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 5: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.75f, 1.0f, 1.0f);
                    break;
                }
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public static int getOrientation(final int par0) {
        return par0 & 0x7;
    }
    
    public static boolean isExtended(final int par0) {
        return (par0 & 0x8) != 0x0;
    }
    
    public static int determineOrientation(final World par0World, final int par1, final int par2, final int par3, final EntityLiving par4EntityLiving) {
        if (MathHelper.abs((float)par4EntityLiving.posX - par1) < 2.0f && MathHelper.abs((float)par4EntityLiving.posZ - par3) < 2.0f) {
            final double var5 = par4EntityLiving.posY + 1.82 - par4EntityLiving.yOffset;
            if (var5 - par2 > 2.0) {
                return 1;
            }
            if (par2 - var5 > 0.0) {
                return 0;
            }
        }
        final int var6 = MathHelper.floor_double(par4EntityLiving.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        return (var6 == 0) ? 2 : ((var6 == 1) ? 5 : ((var6 == 2) ? 3 : ((var6 == 3) ? 4 : 0)));
    }
    
    private static boolean canPushBlock(final int par0, final World par1World, final int par2, final int par3, final int par4, final boolean par5) {
        if (par0 == Block.obsidian.blockID) {
            return false;
        }
        if (par0 != Block.pistonBase.blockID && par0 != Block.pistonStickyBase.blockID) {
            if (Block.blocksList[par0].getBlockHardness(par1World, par2, par3, par4) == -1.0f) {
                return false;
            }
            if (Block.blocksList[par0].getMobilityFlag() == 2) {
                return false;
            }
            if (Block.blocksList[par0].getMobilityFlag() == 1) {
                return par5;
            }
        }
        else if (isExtended(par1World.getBlockMetadata(par2, par3, par4))) {
            return false;
        }
        return !(Block.blocksList[par0] instanceof ITileEntityProvider);
    }
    
    private static boolean canExtend(final World par0World, final int par1, final int par2, final int par3, final int par4) {
        int var5 = par1 + Facing.offsetsXForSide[par4];
        int var6 = par2 + Facing.offsetsYForSide[par4];
        int var7 = par3 + Facing.offsetsZForSide[par4];
        for (int var8 = 0; var8 < 13; ++var8) {
            if (var6 <= 0 || var6 >= 255) {
                return false;
            }
            final int var9 = par0World.getBlockId(var5, var6, var7);
            if (var9 == 0) {
                break;
            }
            if (!canPushBlock(var9, par0World, var5, var6, var7, true)) {
                return false;
            }
            if (Block.blocksList[var9].getMobilityFlag() == 1) {
                break;
            }
            if (var8 == 12) {
                return false;
            }
            var5 += Facing.offsetsXForSide[par4];
            var6 += Facing.offsetsYForSide[par4];
            var7 += Facing.offsetsZForSide[par4];
        }
        return true;
    }
    
    private boolean tryExtend(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        int var6 = par2 + Facing.offsetsXForSide[par5];
        int var7 = par3 + Facing.offsetsYForSide[par5];
        int var8 = par4 + Facing.offsetsZForSide[par5];
        for (int var9 = 0; var9 < 13; ++var9) {
            if (var7 <= 0 || var7 >= 255) {
                return false;
            }
            final int var10 = par1World.getBlockId(var6, var7, var8);
            if (var10 == 0) {
                break;
            }
            if (!canPushBlock(var10, par1World, var6, var7, var8, true)) {
                return false;
            }
            if (Block.blocksList[var10].getMobilityFlag() == 1) {
                Block.blocksList[var10].dropBlockAsItem(par1World, var6, var7, var8, par1World.getBlockMetadata(var6, var7, var8), 0);
                par1World.setBlockToAir(var6, var7, var8);
                break;
            }
            if (var9 == 12) {
                return false;
            }
            var6 += Facing.offsetsXForSide[par5];
            var7 += Facing.offsetsYForSide[par5];
            var8 += Facing.offsetsZForSide[par5];
        }
        int var9 = var6;
        final int var10 = var7;
        final int var11 = var8;
        int var12 = 0;
        final int[] var13 = new int[13];
        while (var6 != par2 || var7 != par3 || var8 != par4) {
            final int var14 = var6 - Facing.offsetsXForSide[par5];
            final int var15 = var7 - Facing.offsetsYForSide[par5];
            final int var16 = var8 - Facing.offsetsZForSide[par5];
            final int var17 = par1World.getBlockId(var14, var15, var16);
            final int var18 = par1World.getBlockMetadata(var14, var15, var16);
            if (var17 == this.blockID && var14 == par2 && var15 == par3 && var16 == par4) {
                par1World.setBlock(var6, var7, var8, Block.pistonMoving.blockID, par5 | (this.isSticky ? 8 : 0), 4);
                par1World.setBlockTileEntity(var6, var7, var8, BlockPistonMoving.getTileEntity(Block.pistonExtension.blockID, par5 | (this.isSticky ? 8 : 0), par5, true, false));
            }
            else {
                par1World.setBlock(var6, var7, var8, Block.pistonMoving.blockID, var18, 4);
                par1World.setBlockTileEntity(var6, var7, var8, BlockPistonMoving.getTileEntity(var17, var18, par5, true, false));
            }
            var13[var12++] = var17;
            var6 = var14;
            var7 = var15;
            var8 = var16;
        }
        var6 = var9;
        var7 = var10;
        var8 = var11;
        var12 = 0;
        while (var6 != par2 || var7 != par3 || var8 != par4) {
            final int var14 = var6 - Facing.offsetsXForSide[par5];
            final int var15 = var7 - Facing.offsetsYForSide[par5];
            final int var16 = var8 - Facing.offsetsZForSide[par5];
            par1World.notifyBlocksOfNeighborChange(var14, var15, var16, var13[var12++]);
            var6 = var14;
            var7 = var15;
            var8 = var16;
        }
        return true;
    }
}
