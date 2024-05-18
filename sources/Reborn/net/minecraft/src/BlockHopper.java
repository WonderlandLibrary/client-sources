package net.minecraft.src;

import java.util.*;

public class BlockHopper extends BlockContainer
{
    private final Random field_94457_a;
    private Icon hopperIcon;
    private Icon hopperTopIcon;
    private Icon hopperInsideIcon;
    
    public BlockHopper(final int par1) {
        super(par1, Material.iron);
        this.field_94457_a = new Random();
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        final float var8 = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, var8, 1.0f, 1.0f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var8);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(1.0f - var8, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0f, 0.0f, 1.0f - var8, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, final int par9) {
        int var10 = Facing.oppositeSide[par5];
        if (var10 == 1) {
            var10 = 0;
        }
        return var10;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityHopper();
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);
        if (par6ItemStack.hasDisplayName()) {
            final TileEntityHopper var7 = getHopperTile(par1World, par2, par3, par4);
            var7.setInventoryName(par6ItemStack.getDisplayName());
        }
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.updateMetadata(par1World, par2, par3, par4);
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        final TileEntityHopper var10 = getHopperTile(par1World, par2, par3, par4);
        if (var10 != null) {
            par5EntityPlayer.displayGUIHopper(var10);
        }
        return true;
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        this.updateMetadata(par1World, par2, par3, par4);
    }
    
    private void updateMetadata(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockMetadata(par2, par3, par4);
        final int var6 = getDirectionFromMetadata(var5);
        final boolean var7 = !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
        final boolean var8 = getIsBlockNotPoweredFromMetadata(var5);
        if (var7 != var8) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | (var7 ? 0 : 8), 4);
        }
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final TileEntityHopper var7 = (TileEntityHopper)par1World.getBlockTileEntity(par2, par3, par4);
        if (var7 != null) {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
                final ItemStack var9 = var7.getStackInSlot(var8);
                if (var9 != null) {
                    final float var10 = this.field_94457_a.nextFloat() * 0.8f + 0.1f;
                    final float var11 = this.field_94457_a.nextFloat() * 0.8f + 0.1f;
                    final float var12 = this.field_94457_a.nextFloat() * 0.8f + 0.1f;
                    while (var9.stackSize > 0) {
                        int var13 = this.field_94457_a.nextInt(21) + 10;
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
                        var14.motionX = (float)this.field_94457_a.nextGaussian() * var15;
                        var14.motionY = (float)this.field_94457_a.nextGaussian() * var15 + 0.2f;
                        var14.motionZ = (float)this.field_94457_a.nextGaussian() * var15;
                        par1World.spawnEntityInWorld(var14);
                    }
                }
            }
            par1World.func_96440_m(par2, par3, par4, par5);
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public int getRenderType() {
        return 38;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return true;
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? this.hopperTopIcon : this.hopperIcon;
    }
    
    public static int getDirectionFromMetadata(final int par0) {
        return par0 & 0x7;
    }
    
    public static boolean getIsBlockNotPoweredFromMetadata(final int par0) {
        return (par0 & 0x8) != 0x8;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return Container.calcRedstoneFromInventory(getHopperTile(par1World, par2, par3, par4));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.hopperIcon = par1IconRegister.registerIcon("hopper");
        this.hopperTopIcon = par1IconRegister.registerIcon("hopper_top");
        this.hopperInsideIcon = par1IconRegister.registerIcon("hopper_inside");
    }
    
    public static Icon getHopperIcon(final String par0Str) {
        return (par0Str == "hopper") ? Block.hopperBlock.hopperIcon : ((par0Str == "hopper_inside") ? Block.hopperBlock.hopperInsideIcon : null);
    }
    
    @Override
    public String getItemIconName() {
        return "hopper";
    }
    
    public static TileEntityHopper getHopperTile(final IBlockAccess par0IBlockAccess, final int par1, final int par2, final int par3) {
        return (TileEntityHopper)par0IBlockAccess.getBlockTileEntity(par1, par2, par3);
    }
}
