package net.minecraft.src;

public class ItemArmor extends Item
{
    private static final int[] maxDamageArray;
    private static final String[] field_94606_cu;
    public static final String[] field_94603_a;
    private static final IBehaviorDispenseItem field_96605_cw;
    public final int armorType;
    public final int damageReduceAmount;
    public final int renderIndex;
    private final EnumArmorMaterial material;
    private Icon field_94605_cw;
    private Icon field_94604_cx;
    
    static {
        maxDamageArray = new int[] { 11, 16, 15, 13 };
        field_94606_cu = new String[] { "helmetCloth_overlay", "chestplateCloth_overlay", "leggingsCloth_overlay", "bootsCloth_overlay" };
        field_94603_a = new String[] { "slot_empty_helmet", "slot_empty_chestplate", "slot_empty_leggings", "slot_empty_boots" };
        field_96605_cw = new BehaviorDispenseArmor();
    }
    
    public ItemArmor(final int par1, final EnumArmorMaterial par2EnumArmorMaterial, final int par3, final int par4) {
        super(par1);
        this.material = par2EnumArmorMaterial;
        this.armorType = par4;
        this.renderIndex = par3;
        this.damageReduceAmount = par2EnumArmorMaterial.getDamageReductionAmount(par4);
        this.setMaxDamage(par2EnumArmorMaterial.getDurability(par4));
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, ItemArmor.field_96605_cw);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
        if (par2 > 0) {
            return 16777215;
        }
        int var3 = this.getColor(par1ItemStack);
        if (var3 < 0) {
            var3 = 16777215;
        }
        return var3;
    }
    
    @Override
    public boolean requiresMultipleRenderPasses() {
        return this.material == EnumArmorMaterial.CLOTH;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }
    
    public EnumArmorMaterial getArmorMaterial() {
        return this.material;
    }
    
    public boolean hasColor(final ItemStack par1ItemStack) {
        return this.material == EnumArmorMaterial.CLOTH && par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("display") && par1ItemStack.getTagCompound().getCompoundTag("display").hasKey("color");
    }
    
    public int getColor(final ItemStack par1ItemStack) {
        if (this.material != EnumArmorMaterial.CLOTH) {
            return -1;
        }
        final NBTTagCompound var2 = par1ItemStack.getTagCompound();
        if (var2 == null) {
            return 10511680;
        }
        final NBTTagCompound var3 = var2.getCompoundTag("display");
        return (var3 == null) ? 10511680 : (var3.hasKey("color") ? var3.getInteger("color") : 10511680);
    }
    
    @Override
    public Icon getIconFromDamageForRenderPass(final int par1, final int par2) {
        return (par2 == 1) ? this.field_94605_cw : super.getIconFromDamageForRenderPass(par1, par2);
    }
    
    public void removeColor(final ItemStack par1ItemStack) {
        if (this.material == EnumArmorMaterial.CLOTH) {
            final NBTTagCompound var2 = par1ItemStack.getTagCompound();
            if (var2 != null) {
                final NBTTagCompound var3 = var2.getCompoundTag("display");
                if (var3.hasKey("color")) {
                    var3.removeTag("color");
                }
            }
        }
    }
    
    public void func_82813_b(final ItemStack par1ItemStack, final int par2) {
        if (this.material != EnumArmorMaterial.CLOTH) {
            throw new UnsupportedOperationException("Can't dye non-leather!");
        }
        NBTTagCompound var3 = par1ItemStack.getTagCompound();
        if (var3 == null) {
            var3 = new NBTTagCompound();
            par1ItemStack.setTagCompound(var3);
        }
        final NBTTagCompound var4 = var3.getCompoundTag("display");
        if (!var3.hasKey("display")) {
            var3.setCompoundTag("display", var4);
        }
        var4.setInteger("color", par2);
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack par1ItemStack, final ItemStack par2ItemStack) {
        return this.material.getArmorCraftingMaterial() == par2ItemStack.itemID || super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        if (this.material == EnumArmorMaterial.CLOTH) {
            this.field_94605_cw = par1IconRegister.registerIcon(ItemArmor.field_94606_cu[this.armorType]);
        }
        this.field_94604_cx = par1IconRegister.registerIcon(ItemArmor.field_94603_a[this.armorType]);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        final int var4 = EntityLiving.getArmorPosition(par1ItemStack) - 1;
        final ItemStack var5 = par3EntityPlayer.getCurrentArmor(var4);
        if (var5 == null) {
            par3EntityPlayer.setCurrentItemOrArmor(var4, par1ItemStack.copy());
            par1ItemStack.stackSize = 0;
        }
        return par1ItemStack;
    }
    
    public static Icon func_94602_b(final int par0) {
        switch (par0) {
            case 0: {
                return Item.helmetDiamond.field_94604_cx;
            }
            case 1: {
                return Item.plateDiamond.field_94604_cx;
            }
            case 2: {
                return Item.legsDiamond.field_94604_cx;
            }
            case 3: {
                return Item.bootsDiamond.field_94604_cx;
            }
            default: {
                return null;
            }
        }
    }
    
    static int[] getMaxDamageArray() {
        return ItemArmor.maxDamageArray;
    }
}
