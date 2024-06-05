package net.minecraft.src;

import java.util.*;

public class BlockFurnace extends BlockContainer
{
    private final Random furnaceRand;
    private final boolean isActive;
    private static boolean keepFurnaceInventory;
    private Icon furnaceIconTop;
    private Icon furnaceIconFront;
    
    static {
        BlockFurnace.keepFurnaceInventory = false;
    }
    
    protected BlockFurnace(final int par1, final boolean par2) {
        super(par1, Material.rock);
        this.furnaceRand = new Random();
        this.isActive = par2;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.furnaceIdle.blockID;
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }
    
    private void setDefaultDirection(final World par1World, final int par2, final int par3, final int par4) {
        if (!par1World.isRemote) {
            final int var5 = par1World.getBlockId(par2, par3, par4 - 1);
            final int var6 = par1World.getBlockId(par2, par3, par4 + 1);
            final int var7 = par1World.getBlockId(par2 - 1, par3, par4);
            final int var8 = par1World.getBlockId(par2 + 1, par3, par4);
            byte var9 = 3;
            if (Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6]) {
                var9 = 3;
            }
            if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5]) {
                var9 = 2;
            }
            if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8]) {
                var9 = 5;
            }
            if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7]) {
                var9 = 4;
            }
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var9, 2);
        }
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? this.furnaceIconTop : ((par1 == 0) ? this.furnaceIconTop : ((par1 != par2) ? this.blockIcon : this.furnaceIconFront));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("furnace_side");
        this.furnaceIconFront = par1IconRegister.registerIcon(this.isActive ? "furnace_front_lit" : "furnace_front");
        this.furnaceIconTop = par1IconRegister.registerIcon("furnace_top");
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (this.isActive) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            final float var7 = par2 + 0.5f;
            final float var8 = par3 + 0.0f + par5Random.nextFloat() * 6.0f / 16.0f;
            final float var9 = par4 + 0.5f;
            final float var10 = 0.52f;
            final float var11 = par5Random.nextFloat() * 0.6f - 0.3f;
            if (var6 == 4) {
                par1World.spawnParticle("smoke", var7 - var10, var8, var9 + var11, 0.0, 0.0, 0.0);
                par1World.spawnParticle("flame", var7 - var10, var8, var9 + var11, 0.0, 0.0, 0.0);
            }
            else if (var6 == 5) {
                par1World.spawnParticle("smoke", var7 + var10, var8, var9 + var11, 0.0, 0.0, 0.0);
                par1World.spawnParticle("flame", var7 + var10, var8, var9 + var11, 0.0, 0.0, 0.0);
            }
            else if (var6 == 2) {
                par1World.spawnParticle("smoke", var7 + var11, var8, var9 - var10, 0.0, 0.0, 0.0);
                par1World.spawnParticle("flame", var7 + var11, var8, var9 - var10, 0.0, 0.0, 0.0);
            }
            else if (var6 == 3) {
                par1World.spawnParticle("smoke", var7 + var11, var8, var9 + var10, 0.0, 0.0, 0.0);
                par1World.spawnParticle("flame", var7 + var11, var8, var9 + var10, 0.0, 0.0, 0.0);
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        final TileEntityFurnace var10 = (TileEntityFurnace)par1World.getBlockTileEntity(par2, par3, par4);
        if (var10 != null) {
            par5EntityPlayer.displayGUIFurnace(var10);
        }
        return true;
    }
    
    public static void updateFurnaceBlockState(final boolean par0, final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockMetadata(par2, par3, par4);
        final TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);
        BlockFurnace.keepFurnaceInventory = true;
        if (par0) {
            par1World.setBlock(par2, par3, par4, Block.furnaceBurning.blockID);
        }
        else {
            par1World.setBlock(par2, par3, par4, Block.furnaceIdle.blockID);
        }
        BlockFurnace.keepFurnaceInventory = false;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 2);
        if (var6 != null) {
            var6.validate();
            par1World.setBlockTileEntity(par2, par3, par4, var6);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityFurnace();
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        final int var7 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (var7 == 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
        }
        if (var7 == 1) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
        }
        if (var7 == 2) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
        }
        if (var7 == 3) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
        }
        if (par6ItemStack.hasDisplayName()) {
            ((TileEntityFurnace)par1World.getBlockTileEntity(par2, par3, par4)).func_94129_a(par6ItemStack.getDisplayName());
        }
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        if (!BlockFurnace.keepFurnaceInventory) {
            final TileEntityFurnace var7 = (TileEntityFurnace)par1World.getBlockTileEntity(par2, par3, par4);
            if (var7 != null) {
                for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
                    final ItemStack var9 = var7.getStackInSlot(var8);
                    if (var9 != null) {
                        final float var10 = this.furnaceRand.nextFloat() * 0.8f + 0.1f;
                        final float var11 = this.furnaceRand.nextFloat() * 0.8f + 0.1f;
                        final float var12 = this.furnaceRand.nextFloat() * 0.8f + 0.1f;
                        while (var9.stackSize > 0) {
                            int var13 = this.furnaceRand.nextInt(21) + 10;
                            if (var13 > var9.stackSize) {
                                var13 = var9.stackSize;
                            }
                            final ItemStack itemStack = var9;
                            itemStack.stackSize -= var13;
                            final EntityItem var14 = new EntityItem(par1World, par2 + var10, par3 + var11, par4 + var12, new ItemStack(var9.itemID, var13, var9.getItemDamage()));
                            if (var9.hasTagCompound()) {
                                var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                            }
                            final float var15 = 0.05f;
                            var14.motionX = (float)this.furnaceRand.nextGaussian() * var15;
                            var14.motionY = (float)this.furnaceRand.nextGaussian() * var15 + 0.2f;
                            var14.motionZ = (float)this.furnaceRand.nextGaussian() * var15;
                            par1World.spawnEntityInWorld(var14);
                        }
                    }
                }
                par1World.func_96440_m(par2, par3, par4, par5);
            }
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return Container.calcRedstoneFromInventory((IInventory)par1World.getBlockTileEntity(par2, par3, par4));
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Block.furnaceIdle.blockID;
    }
}
