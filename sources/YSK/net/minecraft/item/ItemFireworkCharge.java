package net.minecraft.item;

import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class ItemFireworkCharge extends Item
{
    private static final String[] I;
    
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
            if (0 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List<String> list, final boolean b) {
        if (itemStack.hasTagCompound()) {
            final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag(ItemFireworkCharge.I["  ".length()]);
            if (compoundTag != null) {
                addExplosionInfo(compoundTag, list);
            }
        }
    }
    
    public static void addExplosionInfo(final NBTTagCompound nbtTagCompound, final List<String> list) {
        final byte byte1 = nbtTagCompound.getByte(ItemFireworkCharge.I["   ".length()]);
        if (byte1 >= 0 && byte1 <= (0x97 ^ 0x93)) {
            list.add(StatCollector.translateToLocal(ItemFireworkCharge.I[0x19 ^ 0x1D] + byte1).trim());
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            list.add(StatCollector.translateToLocal(ItemFireworkCharge.I[0xBB ^ 0xBE]).trim());
        }
        final int[] intArray = nbtTagCompound.getIntArray(ItemFireworkCharge.I[0x8A ^ 0x8C]);
        if (intArray.length > 0) {
            int n = " ".length();
            String s = ItemFireworkCharge.I[0x1B ^ 0x1C];
            final int[] array;
            final int length = (array = intArray).length;
            int i = "".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
            while (i < length) {
                final int n2 = array[i];
                if (n == 0) {
                    s = String.valueOf(s) + ItemFireworkCharge.I[0x5 ^ 0xD];
                }
                n = "".length();
                int n3 = "".length();
                int j = "".length();
                "".length();
                if (1 == 3) {
                    throw null;
                }
                while (j < ItemDye.dyeColors.length) {
                    if (n2 == ItemDye.dyeColors[j]) {
                        n3 = " ".length();
                        s = String.valueOf(s) + StatCollector.translateToLocal(ItemFireworkCharge.I[0x5E ^ 0x57] + EnumDyeColor.byDyeDamage(j).getUnlocalizedName());
                        "".length();
                        if (2 <= 1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++j;
                    }
                }
                if (n3 == 0) {
                    s = String.valueOf(s) + StatCollector.translateToLocal(ItemFireworkCharge.I[0x59 ^ 0x53]);
                }
                ++i;
            }
            list.add(s);
        }
        final int[] intArray2 = nbtTagCompound.getIntArray(ItemFireworkCharge.I[0x18 ^ 0x13]);
        if (intArray2.length > 0) {
            int n4 = " ".length();
            String s2 = String.valueOf(StatCollector.translateToLocal(ItemFireworkCharge.I[0x36 ^ 0x3A])) + ItemFireworkCharge.I[0xB7 ^ 0xBA];
            final int[] array2;
            final int length2 = (array2 = intArray2).length;
            int k = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (k < length2) {
                final int n5 = array2[k];
                if (n4 == 0) {
                    s2 = String.valueOf(s2) + ItemFireworkCharge.I[0x61 ^ 0x6F];
                }
                n4 = "".length();
                int n6 = "".length();
                int l = "".length();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
                while (l < (0x3C ^ 0x2C)) {
                    if (n5 == ItemDye.dyeColors[l]) {
                        n6 = " ".length();
                        s2 = String.valueOf(s2) + StatCollector.translateToLocal(ItemFireworkCharge.I[0x94 ^ 0x9B] + EnumDyeColor.byDyeDamage(l).getUnlocalizedName());
                        "".length();
                        if (2 <= 0) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++l;
                    }
                }
                if (n6 == 0) {
                    s2 = String.valueOf(s2) + StatCollector.translateToLocal(ItemFireworkCharge.I[0x53 ^ 0x43]);
                }
                ++k;
            }
            list.add(s2);
        }
        if (nbtTagCompound.getBoolean(ItemFireworkCharge.I[0x55 ^ 0x44])) {
            list.add(StatCollector.translateToLocal(ItemFireworkCharge.I[0x51 ^ 0x43]));
        }
        if (nbtTagCompound.getBoolean(ItemFireworkCharge.I[0x6E ^ 0x7D])) {
            list.add(StatCollector.translateToLocal(ItemFireworkCharge.I[0xAF ^ 0xBB]));
        }
    }
    
    private static void I() {
        (I = new String[0x8B ^ 0x9E])["".length()] = I("\u0000\u0019;\u0003\u001f0", "CvWlm");
        ItemFireworkCharge.I[" ".length()] = I("\u0016 1(\r 1.*", "SXADb");
        ItemFireworkCharge.I["  ".length()] = I("\u001d1%\r:+ :\u000f", "XIUaU");
        ItemFireworkCharge.I["   ".length()] = I("=\u0015\u0011\u0007", "ilabh");
        ItemFireworkCharge.I[0x4C ^ 0x48] = I("(\u0012,\u001eo'\u000f;\u00166.\u0014\"\u0000\u0002)\u0007;\u0014$o\u00120\u0003$o", "AfIsA");
        ItemFireworkCharge.I[0xA3 ^ 0xA6] = I("*\u0010,\u0018Y%\r;\u0010\u0000,\u0016\"\u00064+\u0005;\u0012\u0012m\u00100\u0005\u0012", "CdIuw");
        ItemFireworkCharge.I[0x8B ^ 0x8D] = I("\u0010\n\u0002\u001b0 ", "SentB");
        ItemFireworkCharge.I[0x54 ^ 0x53] = I("", "KdyFc");
        ItemFireworkCharge.I[0x5F ^ 0x57] = I("dt", "HTZow");
        ItemFireworkCharge.I[0xA4 ^ 0xAD] = I("\u0003\u0006\u0017*B\f\u001b\u0000\"\u001b\u0005\u0000\u00194/\u0002\u0013\u0000 \tD", "jrrGl");
        ItemFireworkCharge.I[0x11 ^ 0x1B] = I(".%09V!8'1\u000f(#>';/0'3\u001di2 '\f(<\u0016;\u0014(#", "GQUTx");
        ItemFireworkCharge.I[0x37 ^ 0x3C] = I("\u0010)+#*9$ 4\u001a", "VHOFi");
        ItemFireworkCharge.I[0xB8 ^ 0xB4] = I("#\u0005\u00038k,\u0018\u001402%\u0003\r&\u0006\"\u0010\u00142 d\u0017\u00071 \u001e\u001e", "JqfUE");
        ItemFireworkCharge.I[0x5 ^ 0x8] = I("c", "CAkvE");
        ItemFireworkCharge.I[0x10 ^ 0x1E] = I("JV", "fvGxJ");
        ItemFireworkCharge.I[0x7 ^ 0x8] = I("?\u0013)=D0\u000e>5\u001d9\u0015'#)>\u0006>7\u000fx", "VgLPj");
        ItemFireworkCharge.I[0x1C ^ 0xC] = I("3\u0003\u000f\u0004G<\u001e\u0018\f\u001e5\u0005\u0001\u001a*2\u0016\u0018\u000e\ft\u0014\u001f\u001a\u001d5\u001a)\u0006\u00055\u0005", "Zwjii");
        ItemFireworkCharge.I[0x40 ^ 0x51] = I("'\u0005*!%", "swKHI");
        ItemFireworkCharge.I[0x6 ^ 0x14] = I("&%!\u0005L)86\r\u0015 #/\u001b!'06\u000f\u0007a%6\t\u000b#", "OQDhb");
        ItemFireworkCharge.I[0x95 ^ 0x86] = I("$\u000e;\u0007\u0002\u0007\u0010", "bbRdi");
        ItemFireworkCharge.I[0xD3 ^ 0xC7] = I("\u00119(\u0007T\u001e$?\u000f\r\u0017?&\u00199\u0010,?\r\u001fV+!\u0003\u0019\u0013(?", "xMMjz");
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        if (n != " ".length()) {
            return super.getColorFromItemStack(itemStack, n);
        }
        final NBTBase explosionTag = getExplosionTag(itemStack, ItemFireworkCharge.I["".length()]);
        if (!(explosionTag instanceof NBTTagIntArray)) {
            return 2610428 + 8486184 - 10305664 + 8288486;
        }
        final int[] intArray = ((NBTTagIntArray)explosionTag).getIntArray();
        if (intArray.length == " ".length()) {
            return intArray["".length()];
        }
        int length = "".length();
        int length2 = "".length();
        int length3 = "".length();
        final int[] array;
        final int length4 = (array = intArray).length;
        int i = "".length();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i < length4) {
            final int n2 = array[i];
            length += (n2 & 7203613 + 4632426 + 4370805 + 504836) >> (0x1E ^ 0xE);
            length2 += (n2 & 1569 + 35348 - 20392 + 48755) >> (0xE ^ 0x6);
            length3 += (n2 & 106 + 239 - 328 + 238) >> "".length();
            ++i;
        }
        return length / intArray.length << (0x8F ^ 0x9F) | length2 / intArray.length << (0x9B ^ 0x93) | length3 / intArray.length;
    }
    
    public static NBTBase getExplosionTag(final ItemStack itemStack, final String s) {
        if (itemStack.hasTagCompound()) {
            final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag(ItemFireworkCharge.I[" ".length()]);
            if (compoundTag != null) {
                return compoundTag.getTag(s);
            }
        }
        return null;
    }
}
