package net.minecraft.village;

import java.util.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.nbt.*;

public class MerchantRecipeList extends ArrayList<MerchantRecipe>
{
    private static final String[] I;
    
    public MerchantRecipeList() {
    }
    
    public static MerchantRecipeList readFromBuf(final PacketBuffer packetBuffer) throws IOException {
        final MerchantRecipeList list = new MerchantRecipeList();
        final int n = packetBuffer.readByte() & 103 + 116 - 194 + 230;
        int i = "".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (i < n) {
            final ItemStack itemStackFromBuffer = packetBuffer.readItemStackFromBuffer();
            final ItemStack itemStackFromBuffer2 = packetBuffer.readItemStackFromBuffer();
            ItemStack itemStackFromBuffer3 = null;
            if (packetBuffer.readBoolean()) {
                itemStackFromBuffer3 = packetBuffer.readItemStackFromBuffer();
            }
            final boolean boolean1 = packetBuffer.readBoolean();
            final MerchantRecipe merchantRecipe = new MerchantRecipe(itemStackFromBuffer, itemStackFromBuffer3, itemStackFromBuffer2, packetBuffer.readInt(), packetBuffer.readInt());
            if (boolean1) {
                merchantRecipe.compensateToolUses();
            }
            list.add(merchantRecipe);
            ++i;
        }
        return list;
    }
    
    public void writeToBuf(final PacketBuffer packetBuffer) {
        packetBuffer.writeByte((byte)(this.size() & 33 + 67 + 21 + 134));
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < this.size()) {
            final MerchantRecipe merchantRecipe = this.get(i);
            packetBuffer.writeItemStackToBuffer(merchantRecipe.getItemToBuy());
            packetBuffer.writeItemStackToBuffer(merchantRecipe.getItemToSell());
            final ItemStack secondItemToBuy = merchantRecipe.getSecondItemToBuy();
            int n;
            if (secondItemToBuy != null) {
                n = " ".length();
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            packetBuffer.writeBoolean(n != 0);
            if (secondItemToBuy != null) {
                packetBuffer.writeItemStackToBuffer(secondItemToBuy);
            }
            packetBuffer.writeBoolean(merchantRecipe.isRecipeDisabled());
            packetBuffer.writeInt(merchantRecipe.getToolUses());
            packetBuffer.writeInt(merchantRecipe.getMaxTradeUses());
            ++i;
        }
    }
    
    public NBTTagCompound getRecipiesAsTags() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final NBTTagList list = new NBTTagList();
        int i = "".length();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (i < this.size()) {
            list.appendTag(this.get(i).writeToTags());
            ++i;
        }
        nbtTagCompound.setTag(MerchantRecipeList.I[" ".length()], list);
        return nbtTagCompound;
    }
    
    private boolean func_181078_a(final ItemStack itemStack, final ItemStack itemStack2) {
        if (ItemStack.areItemsEqual(itemStack, itemStack2) && (!itemStack2.hasTagCompound() || (itemStack.hasTagCompound() && NBTUtil.func_181123_a(itemStack2.getTagCompound(), itemStack.getTagCompound(), "".length() != 0)))) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public MerchantRecipeList(final NBTTagCompound nbtTagCompound) {
        this.readRecipiesFromTags(nbtTagCompound);
    }
    
    public void readRecipiesFromTags(final NBTTagCompound nbtTagCompound) {
        final NBTTagList tagList = nbtTagCompound.getTagList(MerchantRecipeList.I["".length()], 0x55 ^ 0x5F);
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            this.add(new MerchantRecipe(tagList.getCompoundTagAt(i)));
            ++i;
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u001b\u0016\f\u0010\b,\u0000", "Isoyx");
        MerchantRecipeList.I[" ".length()] = I("7 '\u0003\u001b\u00006", "eEDjk");
    }
    
    public MerchantRecipe canRecipeBeUsed(final ItemStack itemStack, final ItemStack itemStack2, final int n) {
        if (n > 0 && n < this.size()) {
            final MerchantRecipe merchantRecipe = this.get(n);
            MerchantRecipe merchantRecipe2;
            if (!this.func_181078_a(itemStack, merchantRecipe.getItemToBuy()) || ((itemStack2 != null || merchantRecipe.hasSecondItemToBuy()) && (!merchantRecipe.hasSecondItemToBuy() || !this.func_181078_a(itemStack2, merchantRecipe.getSecondItemToBuy()))) || itemStack.stackSize < merchantRecipe.getItemToBuy().stackSize || (merchantRecipe.hasSecondItemToBuy() && itemStack2.stackSize < merchantRecipe.getSecondItemToBuy().stackSize)) {
                merchantRecipe2 = null;
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else {
                merchantRecipe2 = merchantRecipe;
            }
            return merchantRecipe2;
        }
        int i = "".length();
        "".length();
        if (3 == -1) {
            throw null;
        }
        while (i < this.size()) {
            final MerchantRecipe merchantRecipe3 = this.get(i);
            if (this.func_181078_a(itemStack, merchantRecipe3.getItemToBuy()) && itemStack.stackSize >= merchantRecipe3.getItemToBuy().stackSize && ((!merchantRecipe3.hasSecondItemToBuy() && itemStack2 == null) || (merchantRecipe3.hasSecondItemToBuy() && this.func_181078_a(itemStack2, merchantRecipe3.getSecondItemToBuy()) && itemStack2.stackSize >= merchantRecipe3.getSecondItemToBuy().stackSize))) {
                return merchantRecipe3;
            }
            ++i;
        }
        return null;
    }
}
