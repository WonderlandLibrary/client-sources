package net.minecraft.src;

public class ItemExpBottle extends Item
{
    public ItemExpBottle(final int par1) {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public boolean hasEffect(final ItemStack par1ItemStack) {
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (!par3EntityPlayer.capabilities.isCreativeMode) {
            --par1ItemStack.stackSize;
        }
        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5f, 0.4f / (ItemExpBottle.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!par2World.isRemote) {
            par2World.spawnEntityInWorld(new EntityExpBottle(par2World, par3EntityPlayer));
        }
        return par1ItemStack;
    }
}
