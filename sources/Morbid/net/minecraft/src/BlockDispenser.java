package net.minecraft.src;

import java.util.*;

public class BlockDispenser extends BlockContainer
{
    public static final IRegistry dispenseBehaviorRegistry;
    protected Random random;
    protected Icon furnaceTopIcon;
    protected Icon furnaceFrontIcon;
    protected Icon field_96473_e;
    
    static {
        dispenseBehaviorRegistry = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
    }
    
    protected BlockDispenser(final int par1) {
        super(par1, Material.rock);
        this.random = new Random();
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public int tickRate(final World par1World) {
        return 4;
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDispenserDefaultDirection(par1World, par2, par3, par4);
    }
    
    private void setDispenserDefaultDirection(final World par1World, final int par2, final int par3, final int par4) {
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
        final int var3 = par2 & 0x7;
        return (par1 == var3) ? ((var3 != 1 && var3 != 0) ? this.furnaceFrontIcon : this.field_96473_e) : ((var3 != 1 && var3 != 0) ? ((par1 != 1 && par1 != 0) ? this.blockIcon : this.furnaceTopIcon) : this.furnaceTopIcon);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("furnace_side");
        this.furnaceTopIcon = par1IconRegister.registerIcon("furnace_top");
        this.furnaceFrontIcon = par1IconRegister.registerIcon("dispenser_front");
        this.field_96473_e = par1IconRegister.registerIcon("dispenser_front_vertical");
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        final TileEntityDispenser var10 = (TileEntityDispenser)par1World.getBlockTileEntity(par2, par3, par4);
        if (var10 != null) {
            par5EntityPlayer.displayGUIDispenser(var10);
        }
        return true;
    }
    
    protected void dispense(final World par1World, final int par2, final int par3, final int par4) {
        final BlockSourceImpl var5 = new BlockSourceImpl(par1World, par2, par3, par4);
        final TileEntityDispenser var6 = (TileEntityDispenser)var5.getBlockTileEntity();
        if (var6 != null) {
            final int var7 = var6.getRandomStackFromInventory();
            if (var7 < 0) {
                par1World.playAuxSFX(1001, par2, par3, par4, 0);
            }
            else {
                final ItemStack var8 = var6.getStackInSlot(var7);
                final IBehaviorDispenseItem var9 = this.getBehaviorForItemStack(var8);
                if (var9 != IBehaviorDispenseItem.itemDispenseBehaviorProvider) {
                    final ItemStack var10 = var9.dispense(var5, var8);
                    var6.setInventorySlotContents(var7, (var10.stackSize == 0) ? null : var10);
                }
            }
        }
    }
    
    protected IBehaviorDispenseItem getBehaviorForItemStack(final ItemStack par1ItemStack) {
        return (IBehaviorDispenseItem)BlockDispenser.dispenseBehaviorRegistry.func_82594_a(par1ItemStack.getItem());
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        final boolean var6 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
        final int var7 = par1World.getBlockMetadata(par2, par3, par4);
        final boolean var8 = (var7 & 0x8) != 0x0;
        if (var6 && !var8) {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 | 0x8, 4);
        }
        else if (!var6 && var8) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 & 0xFFFFFFF7, 4);
        }
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!par1World.isRemote) {
            this.dispense(par1World, par2, par3, par4);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityDispenser();
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        final int var7 = BlockPistonBase.determineOrientation(par1World, par2, par3, par4, par5EntityLiving);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
        if (par6ItemStack.hasDisplayName()) {
            ((TileEntityDispenser)par1World.getBlockTileEntity(par2, par3, par4)).setCustomName(par6ItemStack.getDisplayName());
        }
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final TileEntityDispenser var7 = (TileEntityDispenser)par1World.getBlockTileEntity(par2, par3, par4);
        if (var7 != null) {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
                final ItemStack var9 = var7.getStackInSlot(var8);
                if (var9 != null) {
                    final float var10 = this.random.nextFloat() * 0.8f + 0.1f;
                    final float var11 = this.random.nextFloat() * 0.8f + 0.1f;
                    final float var12 = this.random.nextFloat() * 0.8f + 0.1f;
                    while (var9.stackSize > 0) {
                        int var13 = this.random.nextInt(21) + 10;
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
                        var14.motionX = (float)this.random.nextGaussian() * var15;
                        var14.motionY = (float)this.random.nextGaussian() * var15 + 0.2f;
                        var14.motionZ = (float)this.random.nextGaussian() * var15;
                        par1World.spawnEntityInWorld(var14);
                    }
                }
            }
            par1World.func_96440_m(par2, par3, par4, par5);
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    public static IPosition getIPositionFromBlockSource(final IBlockSource par0IBlockSource) {
        final EnumFacing var1 = getFacing(par0IBlockSource.getBlockMetadata());
        final double var2 = par0IBlockSource.getX() + 0.7 * var1.getFrontOffsetX();
        final double var3 = par0IBlockSource.getY() + 0.7 * var1.getFrontOffsetY();
        final double var4 = par0IBlockSource.getZ() + 0.7 * var1.getFrontOffsetZ();
        return new PositionImpl(var2, var3, var4);
    }
    
    public static EnumFacing getFacing(final int par0) {
        return EnumFacing.getFront(par0 & 0x7);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return Container.calcRedstoneFromInventory((IInventory)par1World.getBlockTileEntity(par2, par3, par4));
    }
}
