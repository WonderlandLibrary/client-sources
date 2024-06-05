package net.minecraft.src;

import java.util.*;

public class BlockChest extends BlockContainer
{
    private final Random random;
    public final int isTrapped;
    
    protected BlockChest(final int par1, final int par2) {
        super(par1, Material.wood);
        this.random = new Random();
        this.isTrapped = par2;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
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
        return 22;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        if (par1IBlockAccess.getBlockId(par2, par3, par4 - 1) == this.blockID) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0f, 0.9375f, 0.875f, 0.9375f);
        }
        else if (par1IBlockAccess.getBlockId(par2, par3, par4 + 1) == this.blockID) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 1.0f);
        }
        else if (par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == this.blockID) {
            this.setBlockBounds(0.0f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
        else if (par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == this.blockID) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 1.0f, 0.875f, 0.9375f);
        }
        else {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.unifyAdjacentChests(par1World, par2, par3, par4);
        final int var5 = par1World.getBlockId(par2, par3, par4 - 1);
        final int var6 = par1World.getBlockId(par2, par3, par4 + 1);
        final int var7 = par1World.getBlockId(par2 - 1, par3, par4);
        final int var8 = par1World.getBlockId(par2 + 1, par3, par4);
        if (var5 == this.blockID) {
            this.unifyAdjacentChests(par1World, par2, par3, par4 - 1);
        }
        if (var6 == this.blockID) {
            this.unifyAdjacentChests(par1World, par2, par3, par4 + 1);
        }
        if (var7 == this.blockID) {
            this.unifyAdjacentChests(par1World, par2 - 1, par3, par4);
        }
        if (var8 == this.blockID) {
            this.unifyAdjacentChests(par1World, par2 + 1, par3, par4);
        }
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        final int var7 = par1World.getBlockId(par2, par3, par4 - 1);
        final int var8 = par1World.getBlockId(par2, par3, par4 + 1);
        final int var9 = par1World.getBlockId(par2 - 1, par3, par4);
        final int var10 = par1World.getBlockId(par2 + 1, par3, par4);
        byte var11 = 0;
        final int var12 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (var12 == 0) {
            var11 = 2;
        }
        if (var12 == 1) {
            var11 = 5;
        }
        if (var12 == 2) {
            var11 = 3;
        }
        if (var12 == 3) {
            var11 = 4;
        }
        if (var7 != this.blockID && var8 != this.blockID && var9 != this.blockID && var10 != this.blockID) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 3);
        }
        else {
            if ((var7 == this.blockID || var8 == this.blockID) && (var11 == 4 || var11 == 5)) {
                if (var7 == this.blockID) {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4 - 1, var11, 3);
                }
                else {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4 + 1, var11, 3);
                }
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 3);
            }
            if ((var9 == this.blockID || var10 == this.blockID) && (var11 == 2 || var11 == 3)) {
                if (var9 == this.blockID) {
                    par1World.setBlockMetadataWithNotify(par2 - 1, par3, par4, var11, 3);
                }
                else {
                    par1World.setBlockMetadataWithNotify(par2 + 1, par3, par4, var11, 3);
                }
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 3);
            }
        }
        if (par6ItemStack.hasDisplayName()) {
            ((TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4)).func_94043_a(par6ItemStack.getDisplayName());
        }
    }
    
    public void unifyAdjacentChests(final World par1World, final int par2, final int par3, final int par4) {
        if (!par1World.isRemote) {
            final int var5 = par1World.getBlockId(par2, par3, par4 - 1);
            final int var6 = par1World.getBlockId(par2, par3, par4 + 1);
            final int var7 = par1World.getBlockId(par2 - 1, par3, par4);
            final int var8 = par1World.getBlockId(par2 + 1, par3, par4);
            final boolean var9 = true;
            byte var10;
            if (var5 != this.blockID && var6 != this.blockID) {
                if (var7 != this.blockID && var8 != this.blockID) {
                    var10 = 3;
                    if (Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6]) {
                        var10 = 3;
                    }
                    if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5]) {
                        var10 = 2;
                    }
                    if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8]) {
                        var10 = 5;
                    }
                    if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7]) {
                        var10 = 4;
                    }
                }
                else {
                    final int var11 = par1World.getBlockId((var7 == this.blockID) ? (par2 - 1) : (par2 + 1), par3, par4 - 1);
                    final int var12 = par1World.getBlockId((var7 == this.blockID) ? (par2 - 1) : (par2 + 1), par3, par4 + 1);
                    var10 = 3;
                    final boolean var13 = true;
                    int var14;
                    if (var7 == this.blockID) {
                        var14 = par1World.getBlockMetadata(par2 - 1, par3, par4);
                    }
                    else {
                        var14 = par1World.getBlockMetadata(par2 + 1, par3, par4);
                    }
                    if (var14 == 2) {
                        var10 = 2;
                    }
                    if ((Block.opaqueCubeLookup[var5] || Block.opaqueCubeLookup[var11]) && !Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var12]) {
                        var10 = 3;
                    }
                    if ((Block.opaqueCubeLookup[var6] || Block.opaqueCubeLookup[var12]) && !Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var11]) {
                        var10 = 2;
                    }
                }
            }
            else {
                final int var11 = par1World.getBlockId(par2 - 1, par3, (var5 == this.blockID) ? (par4 - 1) : (par4 + 1));
                final int var12 = par1World.getBlockId(par2 + 1, par3, (var5 == this.blockID) ? (par4 - 1) : (par4 + 1));
                var10 = 5;
                final boolean var13 = true;
                int var14;
                if (var5 == this.blockID) {
                    var14 = par1World.getBlockMetadata(par2, par3, par4 - 1);
                }
                else {
                    var14 = par1World.getBlockMetadata(par2, par3, par4 + 1);
                }
                if (var14 == 4) {
                    var10 = 4;
                }
                if ((Block.opaqueCubeLookup[var7] || Block.opaqueCubeLookup[var11]) && !Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var12]) {
                    var10 = 5;
                }
                if ((Block.opaqueCubeLookup[var8] || Block.opaqueCubeLookup[var12]) && !Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var11]) {
                    var10 = 4;
                }
            }
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var10, 3);
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        int var5 = 0;
        if (par1World.getBlockId(par2 - 1, par3, par4) == this.blockID) {
            ++var5;
        }
        if (par1World.getBlockId(par2 + 1, par3, par4) == this.blockID) {
            ++var5;
        }
        if (par1World.getBlockId(par2, par3, par4 - 1) == this.blockID) {
            ++var5;
        }
        if (par1World.getBlockId(par2, par3, par4 + 1) == this.blockID) {
            ++var5;
        }
        return var5 <= 1 && !this.isThereANeighborChest(par1World, par2 - 1, par3, par4) && !this.isThereANeighborChest(par1World, par2 + 1, par3, par4) && !this.isThereANeighborChest(par1World, par2, par3, par4 - 1) && !this.isThereANeighborChest(par1World, par2, par3, par4 + 1);
    }
    
    private boolean isThereANeighborChest(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.getBlockId(par2, par3, par4) == this.blockID && (par1World.getBlockId(par2 - 1, par3, par4) == this.blockID || par1World.getBlockId(par2 + 1, par3, par4) == this.blockID || par1World.getBlockId(par2, par3, par4 - 1) == this.blockID || par1World.getBlockId(par2, par3, par4 + 1) == this.blockID);
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
        final TileEntityChest var6 = (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);
        if (var6 != null) {
            var6.updateContainingBlockInfo();
        }
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final TileEntityChest var7 = (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);
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
                        final float var15 = 0.05f;
                        var14.motionX = (float)this.random.nextGaussian() * var15;
                        var14.motionY = (float)this.random.nextGaussian() * var15 + 0.2f;
                        var14.motionZ = (float)this.random.nextGaussian() * var15;
                        if (var9.hasTagCompound()) {
                            var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                        }
                        par1World.spawnEntityInWorld(var14);
                    }
                }
            }
            par1World.func_96440_m(par2, par3, par4, par5);
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        final IInventory var10 = this.getInventory(par1World, par2, par3, par4);
        if (var10 != null) {
            par5EntityPlayer.displayGUIChest(var10);
        }
        return true;
    }
    
    public IInventory getInventory(final World par1World, final int par2, final int par3, final int par4) {
        Object var5 = par1World.getBlockTileEntity(par2, par3, par4);
        if (var5 == null) {
            return null;
        }
        if (par1World.isBlockNormalCube(par2, par3 + 1, par4)) {
            return null;
        }
        if (isOcelotBlockingChest(par1World, par2, par3, par4)) {
            return null;
        }
        if (par1World.getBlockId(par2 - 1, par3, par4) == this.blockID && (par1World.isBlockNormalCube(par2 - 1, par3 + 1, par4) || isOcelotBlockingChest(par1World, par2 - 1, par3, par4))) {
            return null;
        }
        if (par1World.getBlockId(par2 + 1, par3, par4) == this.blockID && (par1World.isBlockNormalCube(par2 + 1, par3 + 1, par4) || isOcelotBlockingChest(par1World, par2 + 1, par3, par4))) {
            return null;
        }
        if (par1World.getBlockId(par2, par3, par4 - 1) == this.blockID && (par1World.isBlockNormalCube(par2, par3 + 1, par4 - 1) || isOcelotBlockingChest(par1World, par2, par3, par4 - 1))) {
            return null;
        }
        if (par1World.getBlockId(par2, par3, par4 + 1) == this.blockID && (par1World.isBlockNormalCube(par2, par3 + 1, par4 + 1) || isOcelotBlockingChest(par1World, par2, par3, par4 + 1))) {
            return null;
        }
        if (par1World.getBlockId(par2 - 1, par3, par4) == this.blockID) {
            var5 = new InventoryLargeChest("container.chestDouble", (IInventory)par1World.getBlockTileEntity(par2 - 1, par3, par4), (IInventory)var5);
        }
        if (par1World.getBlockId(par2 + 1, par3, par4) == this.blockID) {
            var5 = new InventoryLargeChest("container.chestDouble", (IInventory)var5, (IInventory)par1World.getBlockTileEntity(par2 + 1, par3, par4));
        }
        if (par1World.getBlockId(par2, par3, par4 - 1) == this.blockID) {
            var5 = new InventoryLargeChest("container.chestDouble", (IInventory)par1World.getBlockTileEntity(par2, par3, par4 - 1), (IInventory)var5);
        }
        if (par1World.getBlockId(par2, par3, par4 + 1) == this.blockID) {
            var5 = new InventoryLargeChest("container.chestDouble", (IInventory)var5, (IInventory)par1World.getBlockTileEntity(par2, par3, par4 + 1));
        }
        return (IInventory)var5;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        final TileEntityChest var2 = new TileEntityChest();
        return var2;
    }
    
    @Override
    public boolean canProvidePower() {
        return this.isTrapped == 1;
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        if (!this.canProvidePower()) {
            return 0;
        }
        final int var6 = ((TileEntityChest)par1IBlockAccess.getBlockTileEntity(par2, par3, par4)).numUsingPlayers;
        return MathHelper.clamp_int(var6, 0, 15);
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return (par5 == 1) ? this.isProvidingWeakPower(par1IBlockAccess, par2, par3, par4, par5) : 0;
    }
    
    private static boolean isOcelotBlockingChest(final World par0World, final int par1, final int par2, final int par3) {
        for (final EntityOcelot var6 : par0World.getEntitiesWithinAABB(EntityOcelot.class, AxisAlignedBB.getAABBPool().getAABB(par1, par2 + 1, par3, par1 + 1, par2 + 2, par3 + 1))) {
            final EntityOcelot var5 = var6;
            if (var6.isSitting()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return Container.calcRedstoneFromInventory(this.getInventory(par1World, par2, par3, par4));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("wood");
    }
}
