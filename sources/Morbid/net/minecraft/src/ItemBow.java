package net.minecraft.src;

public class ItemBow extends Item
{
    public static final String[] bowPullIconNameArray;
    private Icon[] iconArray;
    
    static {
        bowPullIconNameArray = new String[] { "bow_pull_0", "bow_pull_1", "bow_pull_2" };
    }
    
    public ItemBow(final int par1) {
        super(par1);
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }
    
    @Override
    public void onPlayerStoppedUsing(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer, final int par4) {
        final boolean var5 = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
        if (var5 || par3EntityPlayer.inventory.hasItem(Item.arrow.itemID)) {
            final int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;
            float var7 = var6 / 20.0f;
            var7 = (var7 * var7 + var7 * 2.0f) / 3.0f;
            if (var7 < 0.1) {
                return;
            }
            if (var7 > 1.0f) {
                var7 = 1.0f;
            }
            final EntityArrow var8 = new EntityArrow(par2World, par3EntityPlayer, var7 * 2.0f);
            if (var7 == 1.0f) {
                var8.setIsCritical(true);
            }
            final int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
            if (var9 > 0) {
                var8.setDamage(var8.getDamage() + var9 * 0.5 + 0.5);
            }
            final int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);
            if (var10 > 0) {
                var8.setKnockbackStrength(var10);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0) {
                var8.setFire(100);
            }
            par1ItemStack.damageItem(1, par3EntityPlayer);
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0f, 1.0f / (ItemBow.itemRand.nextFloat() * 0.4f + 1.2f) + var7 * 0.5f);
            if (var5) {
                var8.canBePickedUp = 2;
            }
            else {
                par3EntityPlayer.inventory.consumeInventoryItem(Item.arrow.itemID);
            }
            if (!par2World.isRemote) {
                par2World.spawnEntityInWorld(var8);
            }
        }
    }
    
    @Override
    public ItemStack onEaten(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        return par1ItemStack;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Item.arrow.itemID)) {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }
        return par1ItemStack;
    }
    
    @Override
    public int getItemEnchantability() {
        return 1;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        this.iconArray = new Icon[ItemBow.bowPullIconNameArray.length];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon(ItemBow.bowPullIconNameArray[var2]);
        }
    }
    
    public Icon getItemIconForUseDuration(final int par1) {
        return this.iconArray[par1];
    }
}
