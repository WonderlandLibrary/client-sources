package net.minecraft.src;

public class ItemFishingRod extends Item
{
    private Icon theIcon;
    
    public ItemFishingRod(final int par1) {
        super(par1);
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (par3EntityPlayer.fishEntity != null) {
            final int var4 = par3EntityPlayer.fishEntity.catchFish();
            par1ItemStack.damageItem(var4, par3EntityPlayer);
            par3EntityPlayer.swingItem();
        }
        else {
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5f, 0.4f / (ItemFishingRod.itemRand.nextFloat() * 0.4f + 0.8f));
            if (!par2World.isRemote) {
                par2World.spawnEntityInWorld(new EntityFishHook(par2World, par3EntityPlayer));
            }
            par3EntityPlayer.swingItem();
        }
        return par1ItemStack;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        this.theIcon = par1IconRegister.registerIcon("fishingRod_empty");
    }
    
    public Icon func_94597_g() {
        return this.theIcon;
    }
}
