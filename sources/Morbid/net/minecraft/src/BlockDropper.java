package net.minecraft.src;

public class BlockDropper extends BlockDispenser
{
    private final IBehaviorDispenseItem dropperDefaultBehaviour;
    
    protected BlockDropper(final int par1) {
        super(par1);
        this.dropperDefaultBehaviour = new BehaviorDefaultDispenseItem();
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("furnace_side");
        this.furnaceTopIcon = par1IconRegister.registerIcon("furnace_top");
        this.furnaceFrontIcon = par1IconRegister.registerIcon("dropper_front");
        this.field_96473_e = par1IconRegister.registerIcon("dropper_front_vertical");
    }
    
    @Override
    protected IBehaviorDispenseItem getBehaviorForItemStack(final ItemStack par1ItemStack) {
        return this.dropperDefaultBehaviour;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityDropper();
    }
    
    @Override
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
                final int var9 = par1World.getBlockMetadata(par2, par3, par4) & 0x7;
                final IInventory var10 = TileEntityHopper.getInventoryAtLocation(par1World, par2 + Facing.offsetsXForSide[var9], par3 + Facing.offsetsYForSide[var9], par4 + Facing.offsetsZForSide[var9]);
                ItemStack var11;
                if (var10 != null) {
                    var11 = TileEntityHopper.insertStack(var10, var8.copy().splitStack(1), Facing.oppositeSide[var9]);
                    if (var11 == null) {
                        final ItemStack copy;
                        var11 = (copy = var8.copy());
                        if (--copy.stackSize == 0) {
                            var11 = null;
                        }
                    }
                    else {
                        var11 = var8.copy();
                    }
                }
                else {
                    var11 = this.dropperDefaultBehaviour.dispense(var5, var8);
                    if (var11 != null && var11.stackSize == 0) {
                        var11 = null;
                    }
                }
                var6.setInventorySlotContents(var7, var11);
            }
        }
    }
}
