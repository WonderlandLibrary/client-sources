package net.minecraft.item;

import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;

public class ItemEnchantedBook extends Item
{
    private static final String[] I;
    
    public WeightedRandomChestContent getRandom(final Random random, final int n, final int n2, final int n3) {
        final ItemStack itemStack = new ItemStack(Items.book, " ".length(), "".length());
        EnchantmentHelper.addRandomEnchantment(random, itemStack, 0x91 ^ 0x8F);
        return new WeightedRandomChestContent(itemStack, n, n2, n3);
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List<String> list, final boolean b) {
        super.addInformation(itemStack, entityPlayer, list, b);
        final NBTTagList enchantments = this.getEnchantments(itemStack);
        if (enchantments != null) {
            int i = "".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
            while (i < enchantments.tagCount()) {
                final short short1 = enchantments.getCompoundTagAt(i).getShort(ItemEnchantedBook.I["  ".length()]);
                final short short2 = enchantments.getCompoundTagAt(i).getShort(ItemEnchantedBook.I["   ".length()]);
                if (Enchantment.getEnchantmentById(short1) != null) {
                    list.add(Enchantment.getEnchantmentById(short1).getTranslatedName(short2));
                }
                ++i;
            }
        }
    }
    
    public WeightedRandomChestContent getRandom(final Random random) {
        return this.getRandom(random, " ".length(), " ".length(), " ".length());
    }
    
    public void getAll(final Enchantment enchantment, final List<ItemStack> list) {
        int i = enchantment.getMinLevel();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i <= enchantment.getMaxLevel()) {
            list.add(this.getEnchantedItemStack(new EnchantmentData(enchantment, i)));
            ++i;
        }
    }
    
    public void addEnchantment(final ItemStack itemStack, final EnchantmentData enchantmentData) {
        final NBTTagList enchantments = this.getEnchantments(itemStack);
        int n = " ".length();
        int i = "".length();
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (i < enchantments.tagCount()) {
            final NBTTagCompound compoundTag = enchantments.getCompoundTagAt(i);
            if (compoundTag.getShort(ItemEnchantedBook.I[0xA4 ^ 0xA0]) == enchantmentData.enchantmentobj.effectId) {
                if (compoundTag.getShort(ItemEnchantedBook.I[0x9B ^ 0x9E]) < enchantmentData.enchantmentLevel) {
                    compoundTag.setShort(ItemEnchantedBook.I[0x77 ^ 0x71], (short)enchantmentData.enchantmentLevel);
                }
                n = "".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
            }
        }
        if (n != 0) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setShort(ItemEnchantedBook.I[0x27 ^ 0x20], (short)enchantmentData.enchantmentobj.effectId);
            nbtTagCompound.setShort(ItemEnchantedBook.I[0xBF ^ 0xB7], (short)enchantmentData.enchantmentLevel);
            enchantments.appendTag(nbtTagCompound);
        }
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        itemStack.getTagCompound().setTag(ItemEnchantedBook.I[0x1E ^ 0x17], enchantments);
    }
    
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x5 ^ 0xF])["".length()] = I("\u001f>\u00046\u0011(\u000f\u0005'\u001c-$\u001f)\u0011\">\u0018", "LJkDt");
        ItemEnchantedBook.I[" ".length()] = I("'3\f\u0015=\u0010\u0002\r\u00040\u0015)\u0017\n=\u001a3\u0010", "tGcgX");
        ItemEnchantedBook.I["  ".length()] = I("(\u0011", "AuAIX");
        ItemEnchantedBook.I["   ".length()] = I("/&/", "CPCnd");
        ItemEnchantedBook.I[0x4E ^ 0x4A] = I(">\u0003", "WgWib");
        ItemEnchantedBook.I[0x9C ^ 0x99] = I("\u001b\u0014%", "wbImU");
        ItemEnchantedBook.I[0x70 ^ 0x76] = I("\b\u0014+", "dbGIf");
        ItemEnchantedBook.I[0x1 ^ 0x6] = I("\u001f\u0012", "vvgGR");
        ItemEnchantedBook.I[0x69 ^ 0x61] = I("<\u0003\b", "PudaO");
        ItemEnchantedBook.I[0xB6 ^ 0xBF] = I("\u001f>?>\u0000(\u000f>/\r-$$!\u0000\">#", "LJPLe");
    }
    
    public NBTTagList getEnchantments(final ItemStack itemStack) {
        final NBTTagCompound tagCompound = itemStack.getTagCompound();
        NBTTagList list;
        if (tagCompound != null && tagCompound.hasKey(ItemEnchantedBook.I["".length()], 0x12 ^ 0x1B)) {
            list = (NBTTagList)tagCompound.getTag(ItemEnchantedBook.I[" ".length()]);
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            list = new NBTTagList();
        }
        return list;
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
            if (4 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemStack getEnchantedItemStack(final EnchantmentData enchantmentData) {
        final ItemStack itemStack = new ItemStack(this);
        this.addEnchantment(itemStack, enchantmentData);
        return itemStack;
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack itemStack) {
        EnumRarity enumRarity;
        if (this.getEnchantments(itemStack).tagCount() > 0) {
            enumRarity = EnumRarity.UNCOMMON;
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else {
            enumRarity = super.getRarity(itemStack);
        }
        return enumRarity;
    }
    
    @Override
    public boolean isItemTool(final ItemStack itemStack) {
        return "".length() != 0;
    }
    
    static {
        I();
    }
}
