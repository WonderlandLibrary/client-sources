package net.minecraft.src;

public class ItemTool extends Item
{
    private Block[] blocksEffectiveAgainst;
    protected float efficiencyOnProperMaterial;
    private int damageVsEntity;
    protected EnumToolMaterial toolMaterial;
    
    protected ItemTool(final int par1, final int par2, final EnumToolMaterial par3EnumToolMaterial, final Block[] par4ArrayOfBlock) {
        super(par1);
        this.efficiencyOnProperMaterial = 4.0f;
        this.toolMaterial = par3EnumToolMaterial;
        this.blocksEffectiveAgainst = par4ArrayOfBlock;
        this.maxStackSize = 1;
        this.setMaxDamage(par3EnumToolMaterial.getMaxUses());
        this.efficiencyOnProperMaterial = par3EnumToolMaterial.getEfficiencyOnProperMaterial();
        this.damageVsEntity = par2 + par3EnumToolMaterial.getDamageVsEntity();
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public float getStrVsBlock(final ItemStack par1ItemStack, final Block par2Block) {
        for (int var3 = 0; var3 < this.blocksEffectiveAgainst.length; ++var3) {
            if (this.blocksEffectiveAgainst[var3] == par2Block) {
                return this.efficiencyOnProperMaterial;
            }
        }
        return 1.0f;
    }
    
    @Override
    public boolean hitEntity(final ItemStack par1ItemStack, final EntityLiving par2EntityLiving, final EntityLiving par3EntityLiving) {
        par1ItemStack.damageItem(2, par3EntityLiving);
        return true;
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack par1ItemStack, final World par2World, final int par3, final int par4, final int par5, final int par6, final EntityLiving par7EntityLiving) {
        if (Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0) {
            par1ItemStack.damageItem(1, par7EntityLiving);
        }
        return true;
    }
    
    @Override
    public int getDamageVsEntity(final Entity par1Entity) {
        return this.damageVsEntity;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
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
