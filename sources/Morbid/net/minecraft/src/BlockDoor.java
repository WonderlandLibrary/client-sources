package net.minecraft.src;

import java.util.*;

public class BlockDoor extends Block
{
    private static final String[] doorIconNames;
    private final int doorTypeForIcon;
    private Icon[] iconArray;
    
    static {
        doorIconNames = new String[] { "doorWood_lower", "doorWood_upper", "doorIron_lower", "doorIron_upper" };
    }
    
    protected BlockDoor(final int par1, final Material par2Material) {
        super(par1, par2Material);
        if (par2Material == Material.iron) {
            this.doorTypeForIcon = 2;
        }
        else {
            this.doorTypeForIcon = 0;
        }
        final float var3 = 0.5f;
        final float var4 = 1.0f;
        this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, var4, 0.5f + var3);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return this.iconArray[this.doorTypeForIcon];
    }
    
    @Override
    public Icon getBlockTexture(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        if (par5 != 1 && par5 != 0) {
            final int var6 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
            final int var7 = var6 & 0x3;
            final boolean var8 = (var6 & 0x4) != 0x0;
            boolean var9 = false;
            final boolean var10 = (var6 & 0x8) != 0x0;
            if (var8) {
                if (var7 == 0 && par5 == 2) {
                    var9 = !var9;
                }
                else if (var7 == 1 && par5 == 5) {
                    var9 = !var9;
                }
                else if (var7 == 2 && par5 == 3) {
                    var9 = !var9;
                }
                else if (var7 == 3 && par5 == 4) {
                    var9 = !var9;
                }
            }
            else {
                if (var7 == 0 && par5 == 5) {
                    var9 = !var9;
                }
                else if (var7 == 1 && par5 == 3) {
                    var9 = !var9;
                }
                else if (var7 == 2 && par5 == 4) {
                    var9 = !var9;
                }
                else if (var7 == 3 && par5 == 2) {
                    var9 = !var9;
                }
                if ((var6 & 0x10) != 0x0) {
                    var9 = !var9;
                }
            }
            return this.iconArray[this.doorTypeForIcon + (var9 ? BlockDoor.doorIconNames.length : 0) + (var10 ? 1 : 0)];
        }
        return this.iconArray[this.doorTypeForIcon];
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.iconArray = new Icon[BlockDoor.doorIconNames.length * 2];
        for (int var2 = 0; var2 < BlockDoor.doorIconNames.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon(BlockDoor.doorIconNames[var2]);
            this.iconArray[var2 + BlockDoor.doorIconNames.length] = new IconFlipped(this.iconArray[var2], true, false);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean getBlocksMovement(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
        return (var5 & 0x4) != 0x0;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 7;
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
        this.setDoorRotation(this.getFullMetadata(par1IBlockAccess, par2, par3, par4));
    }
    
    public int getDoorOrientation(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return this.getFullMetadata(par1IBlockAccess, par2, par3, par4) & 0x3;
    }
    
    public boolean isDoorOpen(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return (this.getFullMetadata(par1IBlockAccess, par2, par3, par4) & 0x4) != 0x0;
    }
    
    private void setDoorRotation(final int par1) {
        final float var2 = 0.1875f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        final int var3 = par1 & 0x3;
        final boolean var4 = (par1 & 0x4) != 0x0;
        final boolean var5 = (par1 & 0x10) != 0x0;
        if (var3 == 0) {
            if (var4) {
                if (!var5) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var2);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - var2, 1.0f, 1.0f, 1.0f);
                }
            }
            else {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, var2, 1.0f, 1.0f);
            }
        }
        else if (var3 == 1) {
            if (var4) {
                if (!var5) {
                    this.setBlockBounds(1.0f - var2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, var2, 1.0f, 1.0f);
                }
            }
            else {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var2);
            }
        }
        else if (var3 == 2) {
            if (var4) {
                if (!var5) {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - var2, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var2);
                }
            }
            else {
                this.setBlockBounds(1.0f - var2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        else if (var3 == 3) {
            if (var4) {
                if (!var5) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, var2, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(1.0f - var2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
            }
            else {
                this.setBlockBounds(0.0f, 0.0f, 1.0f - var2, 1.0f, 1.0f, 1.0f);
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
        final int var10 = this.getFullMetadata(par1World, par2, par3, par4);
        int var11 = var10 & 0x7;
        var11 ^= 0x4;
        if ((var10 & 0x8) == 0x0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 2);
            par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
        }
        else {
            par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, var11, 2);
            par1World.markBlockRangeForRenderUpdate(par2, par3 - 1, par4, par2, par3, par4);
        }
        par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
        return true;
    }
    
    public void onPoweredBlockChange(final World par1World, final int par2, final int par3, final int par4, final boolean par5) {
        final int var6 = this.getFullMetadata(par1World, par2, par3, par4);
        final boolean var7 = (var6 & 0x4) != 0x0;
        if (var7 != par5) {
            int var8 = var6 & 0x7;
            var8 ^= 0x4;
            if ((var6 & 0x8) == 0x0) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var8, 2);
                par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
            }
            else {
                par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, var8, 2);
                par1World.markBlockRangeForRenderUpdate(par2, par3 - 1, par4, par2, par3, par4);
            }
            par1World.playAuxSFXAtEntity(null, 1003, par2, par3, par4, 0);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        if ((var6 & 0x8) == 0x0) {
            boolean var7 = false;
            if (par1World.getBlockId(par2, par3 + 1, par4) != this.blockID) {
                par1World.setBlockToAir(par2, par3, par4);
                var7 = true;
            }
            if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)) {
                par1World.setBlockToAir(par2, par3, par4);
                var7 = true;
                if (par1World.getBlockId(par2, par3 + 1, par4) == this.blockID) {
                    par1World.setBlockToAir(par2, par3 + 1, par4);
                }
            }
            if (var7) {
                if (!par1World.isRemote) {
                    this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
                }
            }
            else {
                final boolean var8 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
                if ((var8 || (par5 > 0 && Block.blocksList[par5].canProvidePower())) && par5 != this.blockID) {
                    this.onPoweredBlockChange(par1World, par2, par3, par4, var8);
                }
            }
        }
        else {
            if (par1World.getBlockId(par2, par3 - 1, par4) != this.blockID) {
                par1World.setBlockToAir(par2, par3, par4);
            }
            if (par5 > 0 && par5 != this.blockID) {
                this.onNeighborBlockChange(par1World, par2, par3 - 1, par4, par5);
            }
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return ((par1 & 0x8) != 0x0) ? 0 : ((this.blockMaterial == Material.iron) ? Item.doorIron.itemID : Item.doorWood.itemID);
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World par1World, final int par2, final int par3, final int par4, final Vec3 par5Vec3, final Vec3 par6Vec3) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return par3 < 255 && (par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && super.canPlaceBlockAt(par1World, par2, par3, par4) && super.canPlaceBlockAt(par1World, par2, par3 + 1, par4));
    }
    
    @Override
    public int getMobilityFlag() {
        return 1;
    }
    
    public int getFullMetadata(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        final boolean var6 = (var5 & 0x8) != 0x0;
        int var7;
        int var8;
        if (var6) {
            var7 = par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4);
            var8 = var5;
        }
        else {
            var7 = var5;
            var8 = par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4);
        }
        final boolean var9 = (var8 & 0x1) != 0x0;
        return (var7 & 0x7) | (var6 ? 8 : 0) | (var9 ? 16 : 0);
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return (this.blockMaterial == Material.iron) ? Item.doorIron.itemID : Item.doorWood.itemID;
    }
    
    @Override
    public void onBlockHarvested(final World par1World, final int par2, final int par3, final int par4, final int par5, final EntityPlayer par6EntityPlayer) {
        if (par6EntityPlayer.capabilities.isCreativeMode && (par5 & 0x8) != 0x0 && par1World.getBlockId(par2, par3 - 1, par4) == this.blockID) {
            par1World.setBlockToAir(par2, par3 - 1, par4);
        }
    }
}
