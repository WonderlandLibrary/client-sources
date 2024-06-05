package net.minecraft.src;

public class ItemSaddle extends Item
{
    public ItemSaddle(final int par1) {
        super(par1);
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack par1ItemStack, final EntityLiving par2EntityLiving) {
        if (par2EntityLiving instanceof EntityPig) {
            final EntityPig var3 = (EntityPig)par2EntityLiving;
            if (!var3.getSaddled() && !var3.isChild()) {
                var3.setSaddled(true);
                --par1ItemStack.stackSize;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean hitEntity(final ItemStack par1ItemStack, final EntityLiving par2EntityLiving, final EntityLiving par3EntityLiving) {
        this.itemInteractionForEntity(par1ItemStack, par2EntityLiving);
        return true;
    }
}
