package net.minecraft.src;

public class ItemSnowball extends Item
{
    public ItemSnowball(final int par1) {
        super(par1);
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (!par3EntityPlayer.capabilities.isCreativeMode) {
            --par1ItemStack.stackSize;
        }
        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5f, 0.4f / (ItemSnowball.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!par2World.isRemote) {
            par2World.spawnEntityInWorld(new EntitySnowball(par2World, par3EntityPlayer));
        }
        return par1ItemStack;
    }
}
