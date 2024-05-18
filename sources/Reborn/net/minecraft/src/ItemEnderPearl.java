package net.minecraft.src;

public class ItemEnderPearl extends Item
{
    public ItemEnderPearl(final int par1) {
        super(par1);
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (par3EntityPlayer.capabilities.isCreativeMode) {
            return par1ItemStack;
        }
        if (par3EntityPlayer.ridingEntity != null) {
            return par1ItemStack;
        }
        --par1ItemStack.stackSize;
        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5f, 0.4f / (ItemEnderPearl.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!par2World.isRemote) {
            par2World.spawnEntityInWorld(new EntityEnderPearl(par2World, par3EntityPlayer));
        }
        return par1ItemStack;
    }
}
