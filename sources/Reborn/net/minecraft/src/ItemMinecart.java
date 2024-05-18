package net.minecraft.src;

public class ItemMinecart extends Item
{
    private static final IBehaviorDispenseItem dispenserMinecartBehavior;
    public int minecartType;
    
    static {
        dispenserMinecartBehavior = new BehaviorDispenseMinecart();
    }
    
    public ItemMinecart(final int par1, final int par2) {
        super(par1);
        this.maxStackSize = 1;
        this.minecartType = par2;
        this.setCreativeTab(CreativeTabs.tabTransport);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, ItemMinecart.dispenserMinecartBehavior);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        final int var11 = par3World.getBlockId(par4, par5, par6);
        if (BlockRailBase.isRailBlock(var11)) {
            if (!par3World.isRemote) {
                final EntityMinecart var12 = EntityMinecart.createMinecart(par3World, par4 + 0.5f, par5 + 0.5f, par6 + 0.5f, this.minecartType);
                if (par1ItemStack.hasDisplayName()) {
                    var12.func_96094_a(par1ItemStack.getDisplayName());
                }
                par3World.spawnEntityInWorld(var12);
            }
            --par1ItemStack.stackSize;
            return true;
        }
        return false;
    }
}
