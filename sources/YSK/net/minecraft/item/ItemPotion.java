package net.minecraft.item;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.potion.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;

public class ItemPotion extends Item
{
    private static final String[] I;
    private static final Map<List<PotionEffect>, Integer> SUB_ITEMS_CACHE;
    private Map<Integer, List<PotionEffect>> effectCache;
    
    public List<PotionEffect> getEffects(final ItemStack itemStack) {
        if (!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey(ItemPotion.I["".length()], 0x17 ^ 0x1E)) {
            List<PotionEffect> potionEffects = this.effectCache.get(itemStack.getMetadata());
            if (potionEffects == null) {
                potionEffects = PotionHelper.getPotionEffects(itemStack.getMetadata(), "".length() != 0);
                this.effectCache.put(itemStack.getMetadata(), potionEffects);
            }
            return potionEffects;
        }
        final ArrayList arrayList = Lists.newArrayList();
        final NBTTagList tagList = itemStack.getTagCompound().getTagList(ItemPotion.I[" ".length()], 0xAD ^ 0xA7);
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            final PotionEffect customPotionEffectFromNBT = PotionEffect.readCustomPotionEffectFromNBT(tagList.getCompoundTagAt(i));
            if (customPotionEffectFromNBT != null) {
                arrayList.add(customPotionEffectFromNBT);
            }
            ++i;
        }
        return (List<PotionEffect>)arrayList;
    }
    
    public static boolean isSplash(final int n) {
        if ((n & 1648 + 7643 + 560 + 6533) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            itemStack.stackSize -= " ".length();
        }
        if (!world.isRemote) {
            final List<PotionEffect> effects = this.getEffects(itemStack);
            if (effects != null) {
                final Iterator<PotionEffect> iterator = effects.iterator();
                "".length();
                if (3 <= -1) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    entityPlayer.addPotionEffect(new PotionEffect(iterator.next()));
                }
            }
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        if (!entityPlayer.capabilities.isCreativeMode) {
            if (itemStack.stackSize <= 0) {
                return new ItemStack(Items.glass_bottle);
            }
            entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }
        return itemStack;
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List<String> list, final boolean b) {
        if (itemStack.getMetadata() != 0) {
            final List<PotionEffect> effects = Items.potionitem.getEffects(itemStack);
            final HashMultimap create = HashMultimap.create();
            if (effects != null && !effects.isEmpty()) {
                final Iterator<PotionEffect> iterator = effects.iterator();
                "".length();
                if (4 == 0) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final PotionEffect potionEffect = iterator.next();
                    String s = StatCollector.translateToLocal(potionEffect.getEffectName()).trim();
                    final Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                    final Map<IAttribute, AttributeModifier> attributeModifierMap = potion.getAttributeModifierMap();
                    if (attributeModifierMap != null && attributeModifierMap.size() > 0) {
                        final Iterator<Map.Entry<IAttribute, AttributeModifier>> iterator2 = attributeModifierMap.entrySet().iterator();
                        "".length();
                        if (-1 >= 3) {
                            throw null;
                        }
                        while (iterator2.hasNext()) {
                            final Map.Entry<IAttribute, AttributeModifier> entry = iterator2.next();
                            final AttributeModifier attributeModifier = entry.getValue();
                            ((Multimap)create).put((Object)entry.getKey().getAttributeUnlocalizedName(), (Object)new AttributeModifier(attributeModifier.getName(), potion.getAttributeModifierAmount(potionEffect.getAmplifier(), attributeModifier), attributeModifier.getOperation()));
                        }
                    }
                    if (potionEffect.getAmplifier() > 0) {
                        s = String.valueOf(s) + ItemPotion.I[0x3C ^ 0x35] + StatCollector.translateToLocal(ItemPotion.I[0x1A ^ 0x10] + potionEffect.getAmplifier()).trim();
                    }
                    if (potionEffect.getDuration() > (0x71 ^ 0x65)) {
                        s = String.valueOf(s) + ItemPotion.I[0x76 ^ 0x7D] + Potion.getDurationString(potionEffect) + ItemPotion.I[0x9C ^ 0x90];
                    }
                    if (potion.isBadEffect()) {
                        list.add(EnumChatFormatting.RED + s);
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        list.add(EnumChatFormatting.GRAY + s);
                    }
                }
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal(ItemPotion.I[0x93 ^ 0x9E]).trim());
            }
            if (!((Multimap)create).isEmpty()) {
                list.add(ItemPotion.I[0x21 ^ 0x2F]);
                list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal(ItemPotion.I[0xCE ^ 0xC1]));
                final Iterator iterator3 = ((Multimap)create).entries().iterator();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
                while (iterator3.hasNext()) {
                    final Map.Entry<K, AttributeModifier> entry2 = iterator3.next();
                    final AttributeModifier attributeModifier2 = entry2.getValue();
                    final double amount = attributeModifier2.getAmount();
                    double amount2;
                    if (attributeModifier2.getOperation() != " ".length() && attributeModifier2.getOperation() != "  ".length()) {
                        amount2 = attributeModifier2.getAmount();
                        "".length();
                        if (0 >= 3) {
                            throw null;
                        }
                    }
                    else {
                        amount2 = attributeModifier2.getAmount() * 100.0;
                    }
                    if (amount > 0.0) {
                        final StringBuilder append = new StringBuilder().append(EnumChatFormatting.BLUE);
                        final String string = ItemPotion.I[0x1C ^ 0xC] + attributeModifier2.getOperation();
                        final Object[] array = new Object["  ".length()];
                        array["".length()] = ItemStack.DECIMALFORMAT.format(amount2);
                        array[" ".length()] = StatCollector.translateToLocal(ItemPotion.I[0xD7 ^ 0xC6] + (String)entry2.getKey());
                        list.add(append.append(StatCollector.translateToLocalFormatted(string, array)).toString());
                        "".length();
                        if (2 == 4) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        if (amount >= 0.0) {
                            continue;
                        }
                        final double n = amount2 * -1.0;
                        final StringBuilder append2 = new StringBuilder().append(EnumChatFormatting.RED);
                        final String string2 = ItemPotion.I[0xE ^ 0x1C] + attributeModifier2.getOperation();
                        final Object[] array2 = new Object["  ".length()];
                        array2["".length()] = ItemStack.DECIMALFORMAT.format(n);
                        array2[" ".length()] = StatCollector.translateToLocal(ItemPotion.I[0x15 ^ 0x6] + (String)entry2.getKey());
                        list.add(append2.append(StatCollector.translateToLocalFormatted(string2, array2)).toString());
                    }
                }
            }
        }
    }
    
    public ItemPotion() {
        this.effectCache = (Map<Integer, List<PotionEffect>>)Maps.newHashMap();
        this.setMaxStackSize(" ".length());
        this.setHasSubtypes(" ".length() != 0);
        this.setMaxDamage("".length());
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }
    
    static {
        I();
        SUB_ITEMS_CACHE = Maps.newLinkedHashMap();
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        int colorFromDamage;
        if (n > 0) {
            colorFromDamage = 6592292 + 133261 + 6131071 + 3920591;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            colorFromDamage = this.getColorFromDamage(itemStack.getMetadata());
        }
        return colorFromDamage;
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        super.getSubItems(item, creativeTabs, list);
        if (ItemPotion.SUB_ITEMS_CACHE.isEmpty()) {
            int i = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
            while (i <= (0x34 ^ 0x3B)) {
                int j = "".length();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                while (j <= " ".length()) {
                    int n;
                    if (j == 0) {
                        n = (i | 3737 + 6691 - 5387 + 3151);
                        "".length();
                        if (4 < 3) {
                            throw null;
                        }
                    }
                    else {
                        n = (i | 15566 + 10388 - 11093 + 1523);
                    }
                    int k = "".length();
                    "".length();
                    if (0 == 2) {
                        throw null;
                    }
                    while (k <= "  ".length()) {
                        int n2 = n;
                        if (k != 0) {
                            if (k == " ".length()) {
                                n2 = (n | (0x8E ^ 0xAE));
                                "".length();
                                if (3 <= 2) {
                                    throw null;
                                }
                            }
                            else if (k == "  ".length()) {
                                n2 = (n | (0xCC ^ 0x8C));
                            }
                        }
                        final List<PotionEffect> potionEffects = PotionHelper.getPotionEffects(n2, "".length() != 0);
                        if (potionEffects != null && !potionEffects.isEmpty()) {
                            ItemPotion.SUB_ITEMS_CACHE.put(potionEffects, n2);
                        }
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }
        final Iterator<Integer> iterator = ItemPotion.SUB_ITEMS_CACHE.values().iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            list.add(new ItemStack(item, " ".length(), iterator.next()));
        }
    }
    
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        final List<PotionEffect> effects = this.getEffects(itemStack);
        if (effects != null && !effects.isEmpty()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        if (itemStack.getMetadata() == 0) {
            return StatCollector.translateToLocal(ItemPotion.I["   ".length()]).trim();
        }
        String string = ItemPotion.I[0x26 ^ 0x22];
        if (isSplash(itemStack.getMetadata())) {
            string = String.valueOf(StatCollector.translateToLocal(ItemPotion.I[0x5 ^ 0x0]).trim()) + ItemPotion.I[0x4B ^ 0x4D];
        }
        final List<PotionEffect> effects = Items.potionitem.getEffects(itemStack);
        if (effects != null && !effects.isEmpty()) {
            return String.valueOf(string) + StatCollector.translateToLocal(String.valueOf(effects.get("".length()).getEffectName()) + ItemPotion.I[0x82 ^ 0x85]).trim();
        }
        return String.valueOf(StatCollector.translateToLocal(PotionHelper.getPotionPrefix(itemStack.getMetadata())).trim()) + ItemPotion.I[0xD ^ 0x5] + super.getItemStackDisplayName(itemStack);
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.DRINK;
    }
    
    public boolean isEffectInstant(final int n) {
        final List<PotionEffect> effects = this.getEffects(n);
        if (effects == null || effects.isEmpty()) {
            return "".length() != 0;
        }
        final Iterator<PotionEffect> iterator = effects.iterator();
        "".length();
        if (1 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (Potion.potionTypes[iterator.next().getPotionID()].isInstant()) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public List<PotionEffect> getEffects(final int n) {
        List<PotionEffect> potionEffects = this.effectCache.get(n);
        if (potionEffects == null) {
            potionEffects = PotionHelper.getPotionEffects(n, "".length() != 0);
            this.effectCache.put(n, potionEffects);
        }
        return potionEffects;
    }
    
    public int getColorFromDamage(final int n) {
        return PotionHelper.getLiquidColor(n, "".length() != 0);
    }
    
    private static void I() {
        (I = new String[0x56 ^ 0x42])["".length()] = I("-%:\u0001\u0017\u0003\u0000&\u0001\u0011\u0001>\f\u0013\u001e\u000b3=\u0006", "nPIux");
        ItemPotion.I[" ".length()] = I("\n=\u001e>!$\u0018\u0002>'&&(,(,+\u00199", "IHmJN");
        ItemPotion.I["  ".length()] = I("\u0004\u0019\u0019\u0000\u0005\u001bV\u0015\u000b\u001d", "vxwdj");
        ItemPotion.I["   ".length()] = I(",7\r\u001b\u007f .\u0018\u0002(\u0015,\u001c\u001f>+m\u0006\u0017< ", "EChvQ");
        ItemPotion.I[0x61 ^ 0x65] = I("", "unVbB");
        ItemPotion.I[0xC ^ 0x9] = I(">$\u0000=& e\u0004&,(\"\fz.<.\u001a5-+", "NKtTI");
        ItemPotion.I[0x24 ^ 0x22] = I("G", "gWRek");
        ItemPotion.I[0xAB ^ 0xAC] = I("z\u0001\t\u001b:2\u0018\u001e", "TqfhN");
        ItemPotion.I[0x11 ^ 0x19] = I("F", "fYftL");
        ItemPotion.I[0x73 ^ 0x7A] = I("A", "aPdwu");
        ItemPotion.I[0x6B ^ 0x61] = I("9.\u00031;'o\u00077 ,/\u0014!z", "IAwXT");
        ItemPotion.I[0x56 ^ 0x5D] = I("dO", "DgAAH");
        ItemPotion.I[0x79 ^ 0x75] = I("S", "zFIHw");
        ItemPotion.I[0x4 ^ 0x9] = I(")7\u00053,7v\u001473-!", "YXqZC");
        ItemPotion.I[0x28 ^ 0x26] = I("", "HWUwF");
        ItemPotion.I[0x3 ^ 0xC] = I("#&00\u001b=g!?\u00126*0*Z$!!70!(*2", "SIDYt");
        ItemPotion.I[0x3D ^ 0x2D] = I("\u001110\u0015'\u001200\u0002`\u001d* \u000e(\u0019 6I>\u001c07I", "pEDgN");
        ItemPotion.I[0x33 ^ 0x22] = I("3\u001a\u00186\u00180\u001b\u0018!_<\u000f\u0001!_", "RnlDq");
        ItemPotion.I[0xA5 ^ 0xB7] = I("\t\u001e\u001d\u001c0\n\u001f\u001d\u000bw\u0005\u0005\r\u0007?\u0001\u000f\u001b@-\t\u0001\f@", "hjinY");
        ItemPotion.I[0x65 ^ 0x76] = I("%\u0003\u001b><&\u0002\u001b){*\u0016\u0002){", "DwoLU");
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (isSplash(itemStack.getMetadata())) {
            if (!entityPlayer.capabilities.isCreativeMode) {
                itemStack.stackSize -= " ".length();
            }
            world.playSoundAtEntity(entityPlayer, ItemPotion.I["  ".length()], 0.5f, 0.4f / (ItemPotion.itemRand.nextFloat() * 0.4f + 0.8f));
            if (!world.isRemote) {
                world.spawnEntityInWorld(new EntityPotion(world, entityPlayer, itemStack));
            }
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            return itemStack;
        }
        entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 0x9B ^ 0xBB;
    }
}
