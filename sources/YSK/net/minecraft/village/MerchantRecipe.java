package net.minecraft.village;

import net.minecraft.nbt.*;
import net.minecraft.item.*;

public class MerchantRecipe
{
    private static final String[] I;
    private ItemStack secondItemToBuy;
    private ItemStack itemToSell;
    private int maxTradeUses;
    private ItemStack itemToBuy;
    private int toolUses;
    private boolean rewardsExp;
    
    public boolean isRecipeDisabled() {
        if (this.toolUses >= this.maxTradeUses) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public ItemStack getItemToBuy() {
        return this.itemToBuy;
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x3A ^ 0x2A])["".length()] = I("\u000e\"\u0017", "lWnWW");
        MerchantRecipe.I[" ".length()] = I("\u00070\u0001\u0016", "tUmzd");
        MerchantRecipe.I["  ".length()] = I("\n2\u001d\b", "hGdJn");
        MerchantRecipe.I["   ".length()] = I("(\u0006.8", "JsWzp");
        MerchantRecipe.I[0x11 ^ 0x15] = I("\r\u001f\f\u001c", "xlioW");
        MerchantRecipe.I[0x49 ^ 0x4C] = I(" \u0015\u0011\u001d", "Uftnc");
        MerchantRecipe.I[0xB8 ^ 0xBE] = I("\u000f-3\u0005?\u0007?", "bLKPL");
        MerchantRecipe.I[0xBE ^ 0xB9] = I("=4\n>\u00015&", "PUrkr");
        MerchantRecipe.I[0x37 ^ 0x3F] = I(">\u0002\u0006(4(\"\t9", "LgqIF");
        MerchantRecipe.I[0x22 ^ 0x2B] = I("\u00030.4!\u0015\u0010!%", "qUYUS");
        MerchantRecipe.I[0xB3 ^ 0xB9] = I("5\r<", "WxEvm");
        MerchantRecipe.I[0x5 ^ 0xE] = I(";#\u001c\u001e", "HFprd");
        MerchantRecipe.I[0x9B ^ 0x97] = I("\u001a4?5", "xAFwi");
        MerchantRecipe.I[0x83 ^ 0x8E] = I("\u0003\u0019\u0013\u001b", "vjvhb");
        MerchantRecipe.I[0x6C ^ 0x62] = I("\u001d\b\u001e\u0019'\u0015\u001a", "pifLT");
        MerchantRecipe.I[0x36 ^ 0x39] = I("9\"=\u0018\u0010/\u00022\t", "KGJyb");
    }
    
    public boolean hasSecondItemToBuy() {
        if (this.secondItemToBuy != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void compensateToolUses() {
        this.toolUses = this.maxTradeUses;
    }
    
    public int getMaxTradeUses() {
        return this.maxTradeUses;
    }
    
    public ItemStack getSecondItemToBuy() {
        return this.secondItemToBuy;
    }
    
    public void readFromTags(final NBTTagCompound nbtTagCompound) {
        this.itemToBuy = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(MerchantRecipe.I["".length()]));
        this.itemToSell = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(MerchantRecipe.I[" ".length()]));
        if (nbtTagCompound.hasKey(MerchantRecipe.I["  ".length()], 0x7 ^ 0xD)) {
            this.secondItemToBuy = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(MerchantRecipe.I["   ".length()]));
        }
        if (nbtTagCompound.hasKey(MerchantRecipe.I[0x15 ^ 0x11], 0x44 ^ 0x27)) {
            this.toolUses = nbtTagCompound.getInteger(MerchantRecipe.I[0x4E ^ 0x4B]);
        }
        if (nbtTagCompound.hasKey(MerchantRecipe.I[0x40 ^ 0x46], 0x17 ^ 0x74)) {
            this.maxTradeUses = nbtTagCompound.getInteger(MerchantRecipe.I[0x82 ^ 0x85]);
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            this.maxTradeUses = (0x51 ^ 0x56);
        }
        if (nbtTagCompound.hasKey(MerchantRecipe.I[0x3A ^ 0x32], " ".length())) {
            this.rewardsExp = nbtTagCompound.getBoolean(MerchantRecipe.I[0x59 ^ 0x50]);
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            this.rewardsExp = (" ".length() != 0);
        }
    }
    
    public MerchantRecipe(final ItemStack itemStack, final ItemStack itemStack2, final ItemStack itemStack3) {
        this(itemStack, itemStack2, itemStack3, "".length(), 0x85 ^ 0x82);
    }
    
    public void incrementToolUses() {
        this.toolUses += " ".length();
    }
    
    public void increaseMaxTradeUses(final int n) {
        this.maxTradeUses += n;
    }
    
    public int getToolUses() {
        return this.toolUses;
    }
    
    public MerchantRecipe(final ItemStack itemStack, final ItemStack itemStack2) {
        this(itemStack, null, itemStack2);
    }
    
    public NBTTagCompound writeToTags() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setTag(MerchantRecipe.I[0x15 ^ 0x1F], this.itemToBuy.writeToNBT(new NBTTagCompound()));
        nbtTagCompound.setTag(MerchantRecipe.I[0xA ^ 0x1], this.itemToSell.writeToNBT(new NBTTagCompound()));
        if (this.secondItemToBuy != null) {
            nbtTagCompound.setTag(MerchantRecipe.I[0xB7 ^ 0xBB], this.secondItemToBuy.writeToNBT(new NBTTagCompound()));
        }
        nbtTagCompound.setInteger(MerchantRecipe.I[0x8C ^ 0x81], this.toolUses);
        nbtTagCompound.setInteger(MerchantRecipe.I[0x9D ^ 0x93], this.maxTradeUses);
        nbtTagCompound.setBoolean(MerchantRecipe.I[0x54 ^ 0x5B], this.rewardsExp);
        return nbtTagCompound;
    }
    
    public MerchantRecipe(final NBTTagCompound nbtTagCompound) {
        this.readFromTags(nbtTagCompound);
    }
    
    public MerchantRecipe(final ItemStack itemToBuy, final ItemStack secondItemToBuy, final ItemStack itemToSell, final int toolUses, final int maxTradeUses) {
        this.itemToBuy = itemToBuy;
        this.secondItemToBuy = secondItemToBuy;
        this.itemToSell = itemToSell;
        this.toolUses = toolUses;
        this.maxTradeUses = maxTradeUses;
        this.rewardsExp = (" ".length() != 0);
    }
    
    public MerchantRecipe(final ItemStack itemStack, final Item item) {
        this(itemStack, new ItemStack(item));
    }
    
    public ItemStack getItemToSell() {
        return this.itemToSell;
    }
    
    public boolean getRewardsExp() {
        return this.rewardsExp;
    }
}
