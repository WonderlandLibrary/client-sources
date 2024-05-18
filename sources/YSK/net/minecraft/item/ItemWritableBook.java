package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.nbt.*;

public class ItemWritableBook extends Item
{
    private static final String[] I;
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.displayGUIBook(itemStack);
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    public static boolean isNBTValid(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound == null) {
            return "".length() != 0;
        }
        if (!nbtTagCompound.hasKey(ItemWritableBook.I["".length()], 0x1C ^ 0x15)) {
            return "".length() != 0;
        }
        final NBTTagList tagList = nbtTagCompound.getTagList(ItemWritableBook.I[" ".length()], 0x43 ^ 0x4B);
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            final String stringTag = tagList.getStringTagAt(i);
            if (stringTag == null) {
                return "".length() != 0;
            }
            if (stringTag.length() > 17364 + 5805 + 3255 + 6343) {
                return "".length() != 0;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u001b\u0005\u001e\u0014\u001c", "kdyqo");
        ItemWritableBook.I[" ".length()] = I("1\u0012=\"0", "AsZGC");
    }
    
    public ItemWritableBook() {
        this.setMaxStackSize(" ".length());
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
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
}
