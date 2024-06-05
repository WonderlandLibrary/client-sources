package net.minecraft.src;

import java.util.*;

public class ItemPotion extends Item
{
    private HashMap effectCache;
    private static final Map field_77835_b;
    private Icon field_94591_c;
    private Icon field_94590_d;
    private Icon field_94592_ct;
    
    static {
        field_77835_b = new LinkedHashMap();
    }
    
    public ItemPotion(final int par1) {
        super(par1);
        this.effectCache = new HashMap();
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }
    
    public List getEffects(final ItemStack par1ItemStack) {
        if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("CustomPotionEffects")) {
            final ArrayList var6 = new ArrayList();
            final NBTTagList var7 = par1ItemStack.getTagCompound().getTagList("CustomPotionEffects");
            for (int var8 = 0; var8 < var7.tagCount(); ++var8) {
                final NBTTagCompound var9 = (NBTTagCompound)var7.tagAt(var8);
                var6.add(PotionEffect.readCustomPotionEffectFromNBT(var9));
            }
            return var6;
        }
        List var10 = this.effectCache.get(par1ItemStack.getItemDamage());
        if (var10 == null) {
            var10 = PotionHelper.getPotionEffects(par1ItemStack.getItemDamage(), false);
            this.effectCache.put(par1ItemStack.getItemDamage(), var10);
        }
        return var10;
    }
    
    public List getEffects(final int par1) {
        List var2 = this.effectCache.get(par1);
        if (var2 == null) {
            var2 = PotionHelper.getPotionEffects(par1, false);
            this.effectCache.put(par1, var2);
        }
        return var2;
    }
    
    @Override
    public ItemStack onEaten(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (!par3EntityPlayer.capabilities.isCreativeMode) {
            --par1ItemStack.stackSize;
        }
        if (!par2World.isRemote) {
            final List var4 = this.getEffects(par1ItemStack);
            if (var4 != null) {
                for (final PotionEffect var6 : var4) {
                    par3EntityPlayer.addPotionEffect(new PotionEffect(var6));
                }
            }
        }
        if (!par3EntityPlayer.capabilities.isCreativeMode) {
            if (par1ItemStack.stackSize <= 0) {
                return new ItemStack(Item.glassBottle);
            }
            par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
        }
        return par1ItemStack;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 32;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.drink;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (isSplash(par1ItemStack.getItemDamage())) {
            if (!par3EntityPlayer.capabilities.isCreativeMode) {
                --par1ItemStack.stackSize;
            }
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5f, 0.4f / (ItemPotion.itemRand.nextFloat() * 0.4f + 0.8f));
            if (!par2World.isRemote) {
                par2World.spawnEntityInWorld(new EntityPotion(par2World, par3EntityPlayer, par1ItemStack));
            }
            return par1ItemStack;
        }
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        return false;
    }
    
    @Override
    public Icon getIconFromDamage(final int par1) {
        return isSplash(par1) ? this.field_94591_c : this.field_94590_d;
    }
    
    @Override
    public Icon getIconFromDamageForRenderPass(final int par1, final int par2) {
        return (par2 == 0) ? this.field_94592_ct : super.getIconFromDamageForRenderPass(par1, par2);
    }
    
    public static boolean isSplash(final int par0) {
        return (par0 & 0x4000) != 0x0;
    }
    
    public int getColorFromDamage(final int par1) {
        return PotionHelper.func_77915_a(par1, false);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
        return (par2 > 0) ? 16777215 : this.getColorFromDamage(par1ItemStack.getItemDamage());
    }
    
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    public boolean isEffectInstant(final int par1) {
        final List var2 = this.getEffects(par1);
        if (var2 != null && !var2.isEmpty()) {
            for (final PotionEffect var4 : var2) {
                if (Potion.potionTypes[var4.getPotionID()].isInstant()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    @Override
    public String getItemDisplayName(final ItemStack par1ItemStack) {
        if (par1ItemStack.getItemDamage() == 0) {
            return StatCollector.translateToLocal("item.emptyPotion.name").trim();
        }
        String var2 = "";
        if (isSplash(par1ItemStack.getItemDamage())) {
            var2 = String.valueOf(StatCollector.translateToLocal("potion.prefix.grenade").trim()) + " ";
        }
        final List var3 = Item.potion.getEffects(par1ItemStack);
        if (var3 != null && !var3.isEmpty()) {
            String var4 = var3.get(0).getEffectName();
            var4 = String.valueOf(var4) + ".postfix";
            return String.valueOf(var2) + StatCollector.translateToLocal(var4).trim();
        }
        String var4 = PotionHelper.func_77905_c(par1ItemStack.getItemDamage());
        return String.valueOf(StatCollector.translateToLocal(var4).trim()) + " " + super.getItemDisplayName(par1ItemStack);
    }
    
    @Override
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        if (par1ItemStack.getItemDamage() != 0) {
            final List var5 = Item.potion.getEffects(par1ItemStack);
            if (var5 != null && !var5.isEmpty()) {
                for (final PotionEffect var7 : var5) {
                    String var8 = StatCollector.translateToLocal(var7.getEffectName()).trim();
                    if (var7.getAmplifier() > 0) {
                        var8 = String.valueOf(var8) + " " + StatCollector.translateToLocal("potion.potency." + var7.getAmplifier()).trim();
                    }
                    if (var7.getDuration() > 20) {
                        var8 = String.valueOf(var8) + " (" + Potion.getDurationString(var7) + ")";
                    }
                    if (Potion.potionTypes[var7.getPotionID()].isBadEffect()) {
                        par3List.add(EnumChatFormatting.RED + var8);
                    }
                    else {
                        par3List.add(EnumChatFormatting.GRAY + var8);
                    }
                }
            }
            else {
                final String var9 = StatCollector.translateToLocal("potion.empty").trim();
                par3List.add(EnumChatFormatting.GRAY + var9);
            }
        }
    }
    
    @Override
    public boolean hasEffect(final ItemStack par1ItemStack) {
        final List var2 = this.getEffects(par1ItemStack);
        return var2 != null && !var2.isEmpty();
    }
    
    @Override
    public void getSubItems(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        super.getSubItems(par1, par2CreativeTabs, par3List);
        if (ItemPotion.field_77835_b.isEmpty()) {
            for (int var4 = 0; var4 <= 15; ++var4) {
                for (int var5 = 0; var5 <= 1; ++var5) {
                    int var6;
                    if (var5 == 0) {
                        var6 = (var4 | 0x2000);
                    }
                    else {
                        var6 = (var4 | 0x4000);
                    }
                    for (int var7 = 0; var7 <= 2; ++var7) {
                        int var8 = var6;
                        if (var7 != 0) {
                            if (var7 == 1) {
                                var8 = (var6 | 0x20);
                            }
                            else if (var7 == 2) {
                                var8 = (var6 | 0x40);
                            }
                        }
                        final List var9 = PotionHelper.getPotionEffects(var8, false);
                        if (var9 != null && !var9.isEmpty()) {
                            ItemPotion.field_77835_b.put(var9, var8);
                        }
                    }
                }
            }
        }
        for (final int var5 : ItemPotion.field_77835_b.values()) {
            par3List.add(new ItemStack(par1, 1, var5));
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.field_94590_d = par1IconRegister.registerIcon("potion");
        this.field_94591_c = par1IconRegister.registerIcon("potion_splash");
        this.field_94592_ct = par1IconRegister.registerIcon("potion_contents");
    }
    
    public static Icon func_94589_d(final String par0Str) {
        return (par0Str == "potion") ? Item.potion.field_94590_d : ((par0Str == "potion_splash") ? Item.potion.field_94591_c : ((par0Str == "potion_contents") ? Item.potion.field_94592_ct : null));
    }
}
