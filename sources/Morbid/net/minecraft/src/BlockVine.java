package net.minecraft.src;

import java.util.*;

public class BlockVine extends Block
{
    public BlockVine(final int par1) {
        super(par1, Material.vine);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public int getRenderType() {
        return 20;
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
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        float var7 = 1.0f;
        float var8 = 1.0f;
        float var9 = 1.0f;
        float var10 = 0.0f;
        float var11 = 0.0f;
        float var12 = 0.0f;
        boolean var13 = var6 > 0;
        if ((var6 & 0x2) != 0x0) {
            var10 = Math.max(var10, 0.0625f);
            var7 = 0.0f;
            var8 = 0.0f;
            var11 = 1.0f;
            var9 = 0.0f;
            var12 = 1.0f;
            var13 = true;
        }
        if ((var6 & 0x8) != 0x0) {
            var7 = Math.min(var7, 0.9375f);
            var10 = 1.0f;
            var8 = 0.0f;
            var11 = 1.0f;
            var9 = 0.0f;
            var12 = 1.0f;
            var13 = true;
        }
        if ((var6 & 0x4) != 0x0) {
            var12 = Math.max(var12, 0.0625f);
            var9 = 0.0f;
            var7 = 0.0f;
            var10 = 1.0f;
            var8 = 0.0f;
            var11 = 1.0f;
            var13 = true;
        }
        if ((var6 & 0x1) != 0x0) {
            var9 = Math.min(var9, 0.9375f);
            var12 = 1.0f;
            var7 = 0.0f;
            var10 = 1.0f;
            var8 = 0.0f;
            var11 = 1.0f;
            var13 = true;
        }
        if (!var13 && this.canBePlacedOn(par1IBlockAccess.getBlockId(par2, par3 + 1, par4))) {
            var8 = Math.min(var8, 0.9375f);
            var11 = 1.0f;
            var7 = 0.0f;
            var10 = 1.0f;
            var9 = 0.0f;
            var12 = 1.0f;
        }
        this.setBlockBounds(var7, var8, var9, var10, var11, var12);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return null;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        switch (par5) {
            case 1: {
                return this.canBePlacedOn(par1World.getBlockId(par2, par3 + 1, par4));
            }
            case 2: {
                return this.canBePlacedOn(par1World.getBlockId(par2, par3, par4 + 1));
            }
            case 3: {
                return this.canBePlacedOn(par1World.getBlockId(par2, par3, par4 - 1));
            }
            case 4: {
                return this.canBePlacedOn(par1World.getBlockId(par2 + 1, par3, par4));
            }
            case 5: {
                return this.canBePlacedOn(par1World.getBlockId(par2 - 1, par3, par4));
            }
            default: {
                return false;
            }
        }
    }
    
    private boolean canBePlacedOn(final int par1) {
        if (par1 == 0) {
            return false;
        }
        final Block var2 = Block.blocksList[par1];
        return var2.renderAsNormalBlock() && var2.blockMaterial.blocksMovement();
    }
    
    private boolean canVineStay(final World par1World, final int par2, final int par3, final int par4) {
        int var6;
        final int var5 = var6 = par1World.getBlockMetadata(par2, par3, par4);
        if (var5 > 0) {
            for (int var7 = 0; var7 <= 3; ++var7) {
                final int var8 = 1 << var7;
                if ((var5 & var8) != 0x0 && !this.canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var7], par3, par4 + Direction.offsetZ[var7])) && (par1World.getBlockId(par2, par3 + 1, par4) != this.blockID || (par1World.getBlockMetadata(par2, par3 + 1, par4) & var8) == 0x0)) {
                    var6 &= ~var8;
                }
            }
        }
        if (var6 == 0 && !this.canBePlacedOn(par1World.getBlockId(par2, par3 + 1, par4))) {
            return false;
        }
        if (var6 != var5) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
        }
        return true;
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerFoliage.getFoliageColorBasic();
    }
    
    @Override
    public int getRenderColor(final int par1) {
        return ColorizerFoliage.getFoliageColorBasic();
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeFoliageColor();
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!par1World.isRemote && !this.canVineStay(par1World, par2, par3, par4)) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!par1World.isRemote && par1World.rand.nextInt(4) == 0) {
            final byte var6 = 4;
            int var7 = 5;
            boolean var8 = false;
        Label_0121:
            for (int var9 = par2 - var6; var9 <= par2 + var6; ++var9) {
                for (int var10 = par4 - var6; var10 <= par4 + var6; ++var10) {
                    for (int var11 = par3 - 1; var11 <= par3 + 1; ++var11) {
                        if (par1World.getBlockId(var9, var11, var10) == this.blockID && --var7 <= 0) {
                            var8 = true;
                            break Label_0121;
                        }
                    }
                }
            }
            int var9 = par1World.getBlockMetadata(par2, par3, par4);
            int var10 = par1World.rand.nextInt(6);
            int var11 = Direction.facingToDirection[var10];
            if (var10 == 1 && par3 < 255 && par1World.isAirBlock(par2, par3 + 1, par4)) {
                if (var8) {
                    return;
                }
                int var12 = par1World.rand.nextInt(16) & var9;
                if (var12 > 0) {
                    for (int var13 = 0; var13 <= 3; ++var13) {
                        if (!this.canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var13], par3 + 1, par4 + Direction.offsetZ[var13]))) {
                            var12 &= ~(1 << var13);
                        }
                    }
                    if (var12 > 0) {
                        par1World.setBlock(par2, par3 + 1, par4, this.blockID, var12, 2);
                    }
                }
            }
            else if (var10 >= 2 && var10 <= 5 && (var9 & 1 << var11) == 0x0) {
                if (var8) {
                    return;
                }
                final int var12 = par1World.getBlockId(par2 + Direction.offsetX[var11], par3, par4 + Direction.offsetZ[var11]);
                if (var12 != 0 && Block.blocksList[var12] != null) {
                    if (Block.blocksList[var12].blockMaterial.isOpaque() && Block.blocksList[var12].renderAsNormalBlock()) {
                        par1World.setBlockMetadataWithNotify(par2, par3, par4, var9 | 1 << var11, 2);
                    }
                }
                else {
                    final int var13 = var11 + 1 & 0x3;
                    final int var14 = var11 + 3 & 0x3;
                    if ((var9 & 1 << var13) != 0x0 && this.canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var11] + Direction.offsetX[var13], par3, par4 + Direction.offsetZ[var11] + Direction.offsetZ[var13]))) {
                        par1World.setBlock(par2 + Direction.offsetX[var11], par3, par4 + Direction.offsetZ[var11], this.blockID, 1 << var13, 2);
                    }
                    else if ((var9 & 1 << var14) != 0x0 && this.canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var11] + Direction.offsetX[var14], par3, par4 + Direction.offsetZ[var11] + Direction.offsetZ[var14]))) {
                        par1World.setBlock(par2 + Direction.offsetX[var11], par3, par4 + Direction.offsetZ[var11], this.blockID, 1 << var14, 2);
                    }
                    else if ((var9 & 1 << var13) != 0x0 && par1World.isAirBlock(par2 + Direction.offsetX[var11] + Direction.offsetX[var13], par3, par4 + Direction.offsetZ[var11] + Direction.offsetZ[var13]) && this.canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var13], par3, par4 + Direction.offsetZ[var13]))) {
                        par1World.setBlock(par2 + Direction.offsetX[var11] + Direction.offsetX[var13], par3, par4 + Direction.offsetZ[var11] + Direction.offsetZ[var13], this.blockID, 1 << (var11 + 2 & 0x3), 2);
                    }
                    else if ((var9 & 1 << var14) != 0x0 && par1World.isAirBlock(par2 + Direction.offsetX[var11] + Direction.offsetX[var14], par3, par4 + Direction.offsetZ[var11] + Direction.offsetZ[var14]) && this.canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var14], par3, par4 + Direction.offsetZ[var14]))) {
                        par1World.setBlock(par2 + Direction.offsetX[var11] + Direction.offsetX[var14], par3, par4 + Direction.offsetZ[var11] + Direction.offsetZ[var14], this.blockID, 1 << (var11 + 2 & 0x3), 2);
                    }
                    else if (this.canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var11], par3 + 1, par4 + Direction.offsetZ[var11]))) {
                        par1World.setBlock(par2 + Direction.offsetX[var11], par3, par4 + Direction.offsetZ[var11], this.blockID, 0, 2);
                    }
                }
            }
            else if (par3 > 1) {
                final int var12 = par1World.getBlockId(par2, par3 - 1, par4);
                if (var12 == 0) {
                    final int var13 = par1World.rand.nextInt(16) & var9;
                    if (var13 > 0) {
                        par1World.setBlock(par2, par3 - 1, par4, this.blockID, var13, 2);
                    }
                }
                else if (var12 == this.blockID) {
                    final int var13 = par1World.rand.nextInt(16) & var9;
                    final int var14 = par1World.getBlockMetadata(par2, par3 - 1, par4);
                    if (var14 != (var14 | var13)) {
                        par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, var14 | var13, 2);
                    }
                }
            }
        }
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, final int par9) {
        byte var10 = 0;
        switch (par5) {
            case 2: {
                var10 = 1;
                break;
            }
            case 3: {
                var10 = 4;
                break;
            }
            case 4: {
                var10 = 8;
                break;
            }
            case 5: {
                var10 = 2;
                break;
            }
        }
        return (var10 != 0) ? var10 : par9;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return 0;
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    @Override
    public void harvestBlock(final World par1World, final EntityPlayer par2EntityPlayer, final int par3, final int par4, final int par5, final int par6) {
        if (!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.itemID) {
            par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
            this.dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(Block.vine, 1, 0));
        }
        else {
            super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
        }
    }
}
