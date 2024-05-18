package net.minecraft.item;

import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;

public class ItemFirework extends Item
{
    private static final String[] I;
    
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
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List<String> list, final boolean b) {
        if (itemStack.hasTagCompound()) {
            final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag(ItemFirework.I["".length()]);
            if (compoundTag != null) {
                if (compoundTag.hasKey(ItemFirework.I[" ".length()], 0xC0 ^ 0xA3)) {
                    list.add(String.valueOf(StatCollector.translateToLocal(ItemFirework.I["  ".length()])) + ItemFirework.I["   ".length()] + compoundTag.getByte(ItemFirework.I[0x28 ^ 0x2C]));
                }
                final NBTTagList tagList = compoundTag.getTagList(ItemFirework.I[0xC ^ 0x9], 0x10 ^ 0x1A);
                if (tagList != null && tagList.tagCount() > 0) {
                    int i = "".length();
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                    while (i < tagList.tagCount()) {
                        final NBTTagCompound compoundTag2 = tagList.getCompoundTagAt(i);
                        final ArrayList arrayList = Lists.newArrayList();
                        ItemFireworkCharge.addExplosionInfo(compoundTag2, arrayList);
                        if (arrayList.size() > 0) {
                            int j = " ".length();
                            "".length();
                            if (4 <= 3) {
                                throw null;
                            }
                            while (j < arrayList.size()) {
                                arrayList.set(j, ItemFirework.I[0x8B ^ 0x8D] + (String)arrayList.get(j));
                                ++j;
                            }
                            list.addAll(arrayList);
                        }
                        ++i;
                    }
                }
            }
        }
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityFireworkRocket(world, blockPos.getX() + n, blockPos.getY() + n2, blockPos.getZ() + n3, itemStack));
            if (!entityPlayer.capabilities.isCreativeMode) {
                itemStack.stackSize -= " ".length();
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x71 ^ 0x76])["".length()] = I("\u0017\u0000\u0005'\u0002>\u001b\u001c1", "QiwBu");
        ItemFirework.I[" ".length()] = I("%\u001b\u001f\f\u001b\u0017", "cwvks");
        ItemFirework.I["  ".length()] = I(" \u0006\u0014\u001dC/\u001b\u0003\u0015\u001a&\u0000\u001a\u0003C/\u001e\u0018\u0017\u0005=", "Irqpm");
        ItemFirework.I["   ".length()] = I("m", "MjmXL");
        ItemFirework.I[0x53 ^ 0x57] = I("#;\u0003\u0012\u0000\u0011", "eWjuh");
        ItemFirework.I[0x4A ^ 0x4F] = I("\u00073?\u000b#1\" \t?", "BKOgL");
        ItemFirework.I[0xB ^ 0xD] = I("Tv", "tVizC");
    }
    
    static {
        I();
    }
}
