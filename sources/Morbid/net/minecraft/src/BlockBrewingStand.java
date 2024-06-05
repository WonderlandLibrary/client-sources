package net.minecraft.src;

import java.util.*;

public class BlockBrewingStand extends BlockContainer
{
    private Random rand;
    private Icon theIcon;
    
    public BlockBrewingStand(final int par1) {
        super(par1, Material.iron);
        this.rand = new Random();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 25;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityBrewingStand();
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
        this.setBlockBounds(0.4375f, 0.0f, 0.4375f, 0.5625f, 0.875f, 0.5625f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBoundsForItemRender();
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        final TileEntityBrewingStand var10 = (TileEntityBrewingStand)par1World.getBlockTileEntity(par2, par3, par4);
        if (var10 != null) {
            par5EntityPlayer.displayGUIBrewingStand(var10);
        }
        return true;
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        if (par6ItemStack.hasDisplayName()) {
            ((TileEntityBrewingStand)par1World.getBlockTileEntity(par2, par3, par4)).func_94131_a(par6ItemStack.getDisplayName());
        }
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        final double var6 = par2 + 0.4f + par5Random.nextFloat() * 0.2f;
        final double var7 = par3 + 0.7f + par5Random.nextFloat() * 0.3f;
        final double var8 = par4 + 0.4f + par5Random.nextFloat() * 0.2f;
        par1World.spawnParticle("smoke", var6, var7, var8, 0.0, 0.0, 0.0);
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final TileEntity var7 = par1World.getBlockTileEntity(par2, par3, par4);
        if (var7 instanceof TileEntityBrewingStand) {
            final TileEntityBrewingStand var8 = (TileEntityBrewingStand)var7;
            for (int var9 = 0; var9 < var8.getSizeInventory(); ++var9) {
                final ItemStack var10 = var8.getStackInSlot(var9);
                if (var10 != null) {
                    final float var11 = this.rand.nextFloat() * 0.8f + 0.1f;
                    final float var12 = this.rand.nextFloat() * 0.8f + 0.1f;
                    final float var13 = this.rand.nextFloat() * 0.8f + 0.1f;
                    while (var10.stackSize > 0) {
                        int var14 = this.rand.nextInt(21) + 10;
                        if (var14 > var10.stackSize) {
                            var14 = var10.stackSize;
                        }
                        final ItemStack itemStack = var10;
                        itemStack.stackSize -= var14;
                        final EntityItem var15 = new EntityItem(par1World, par2 + var11, par3 + var12, par4 + var13, new ItemStack(var10.itemID, var14, var10.getItemDamage()));
                        final float var16 = 0.05f;
                        var15.motionX = (float)this.rand.nextGaussian() * var16;
                        var15.motionY = (float)this.rand.nextGaussian() * var16 + 0.2f;
                        var15.motionZ = (float)this.rand.nextGaussian() * var16;
                        par1World.spawnEntityInWorld(var15);
                    }
                }
            }
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.brewingStand.itemID;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.brewingStand.itemID;
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
    public void registerIcons(final IconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        this.theIcon = par1IconRegister.registerIcon("brewingStand_base");
    }
    
    public Icon getBrewingStandIcon() {
        return this.theIcon;
    }
}
