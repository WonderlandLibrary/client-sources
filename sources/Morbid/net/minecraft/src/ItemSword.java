package net.minecraft.src;

public class ItemSword extends Item
{
    private int weaponDamage;
    private final EnumToolMaterial toolMaterial;
    
    public ItemSword(final int par1, final EnumToolMaterial par2EnumToolMaterial) {
        super(par1);
        this.toolMaterial = par2EnumToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(par2EnumToolMaterial.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.weaponDamage = 4 + par2EnumToolMaterial.getDamageVsEntity();
    }
    
    public int func_82803_g() {
        return this.toolMaterial.getDamageVsEntity();
    }
    
    @Override
    public float getStrVsBlock(final ItemStack par1ItemStack, final Block par2Block) {
        if (par2Block.blockID == Block.web.blockID) {
            return 15.0f;
        }
        final Material var3 = par2Block.blockMaterial;
        return (var3 != Material.plants && var3 != Material.vine && var3 != Material.coral && var3 != Material.leaves && var3 != Material.pumpkin) ? 1.0f : 1.5f;
    }
    
    @Override
    public boolean hitEntity(final ItemStack par1ItemStack, final EntityLiving par2EntityLiving, final EntityLiving par3EntityLiving) {
        par1ItemStack.damageItem(1, par3EntityLiving);
        return true;
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack par1ItemStack, final World par2World, final int par3, final int par4, final int par5, final int par6, final EntityLiving par7EntityLiving) {
        if (Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0) {
            par1ItemStack.damageItem(2, par7EntityLiving);
        }
        return true;
    }
    
    @Override
    public int getDamageVsEntity(final Entity par1Entity) {
        return this.weaponDamage;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.block;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
    
    @Override
    public boolean canHarvestBlock(final Block par1Block) {
        return par1Block.blockID == Block.web.blockID;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.toolMaterial.getEnchantability();
    }
    
    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack par1ItemStack, final ItemStack par2ItemStack) {
        return this.toolMaterial.getToolCraftingMaterial() == par2ItemStack.itemID || super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
}
