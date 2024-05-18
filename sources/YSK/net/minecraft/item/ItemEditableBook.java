package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.command.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.nbt.*;

public class ItemEditableBook extends Item
{
    private static final String[] I;
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            final String string = itemStack.getTagCompound().getString(ItemEditableBook.I[0x39 ^ 0x3D]);
            if (!StringUtils.isNullOrEmpty(string)) {
                return string;
            }
        }
        return super.getItemStackDisplayName(itemStack);
    }
    
    public ItemEditableBook() {
        this.setMaxStackSize(" ".length());
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!world.isRemote) {
            this.resolveContents(itemStack, entityPlayer);
        }
        entityPlayer.displayGUIBook(itemStack);
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List<String> list, final boolean b) {
        if (itemStack.hasTagCompound()) {
            final NBTTagCompound tagCompound = itemStack.getTagCompound();
            final String string = tagCompound.getString(ItemEditableBook.I[0x1E ^ 0x1B]);
            if (!StringUtils.isNullOrEmpty(string)) {
                final StringBuilder append = new StringBuilder().append(EnumChatFormatting.GRAY);
                final String s = ItemEditableBook.I[0x8A ^ 0x8C];
                final Object[] array = new Object[" ".length()];
                array["".length()] = string;
                list.add(append.append(StatCollector.translateToLocalFormatted(s, array)).toString());
            }
            list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal(ItemEditableBook.I[0x7 ^ 0x0] + tagCompound.getInteger(ItemEditableBook.I[0x6D ^ 0x65])));
        }
    }
    
    static {
        I();
    }
    
    public static int getGeneration(final ItemStack itemStack) {
        return itemStack.getTagCompound().getInteger(ItemEditableBook.I["   ".length()]);
    }
    
    private static void I() {
        (I = new String[0x92 ^ 0x9F])["".length()] = I("\u0010\u0000\u001e\u0015\u0007", "dijyb");
        ItemEditableBook.I[" ".length()] = I("\u000e\u000e\u0005-\u0003", "zgqAf");
        ItemEditableBook.I["  ".length()] = I("\u0002\u00179.\u0015\u0011", "cbMFz");
        ItemEditableBook.I["   ".length()] = I("\u0010++=\b\u0016:,7\u0014", "wNEXz");
        ItemEditableBook.I[0x16 ^ 0x12] = I("70%$?", "CYQHZ");
        ItemEditableBook.I[0x3B ^ 0x3E] = I("/?%-\b<", "NJQEg");
        ItemEditableBook.I[0xBB ^ 0xBD] = I("89\t\u0001y8/'\u001f#29\u0014", "ZVfjW");
        ItemEditableBook.I[0x87 ^ 0x80] = I("+>\u001a\u0013a.4\u001b\u001d=(%\u001c\u0017!g", "IQuxO");
        ItemEditableBook.I[0x70 ^ 0x78] = I("\r,9= \u000b=>7<", "jIWXR");
        ItemEditableBook.I[0x44 ^ 0x4D] = I(":1:9\u0002>1-", "HTIVn");
        ItemEditableBook.I[0x5F ^ 0x55] = I("=\u0006\u0016 99\u0006\u0001", "OceOU");
        ItemEditableBook.I[0x87 ^ 0x8C] = I("\u001b,,\u001c?", "kMKyL");
        ItemEditableBook.I[0x7F ^ 0x73] = I(";2,\u0013!", "KSKvR");
    }
    
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    public static boolean validBookTagContents(final NBTTagCompound nbtTagCompound) {
        if (!ItemWritableBook.isNBTValid(nbtTagCompound)) {
            return "".length() != 0;
        }
        if (!nbtTagCompound.hasKey(ItemEditableBook.I["".length()], 0x7F ^ 0x77)) {
            return "".length() != 0;
        }
        final String string = nbtTagCompound.getString(ItemEditableBook.I[" ".length()]);
        int n;
        if (string != null && string.length() <= (0x4E ^ 0x6E)) {
            n = (nbtTagCompound.hasKey(ItemEditableBook.I["  ".length()], 0x85 ^ 0x8D) ? 1 : 0);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    private void resolveContents(final ItemStack itemStack, final EntityPlayer entityPlayer) {
        if (itemStack != null && itemStack.getTagCompound() != null) {
            final NBTTagCompound tagCompound = itemStack.getTagCompound();
            if (!tagCompound.getBoolean(ItemEditableBook.I[0x39 ^ 0x30])) {
                tagCompound.setBoolean(ItemEditableBook.I[0x7F ^ 0x75], " ".length() != 0);
                if (validBookTagContents(tagCompound)) {
                    final NBTTagList tagList = tagCompound.getTagList(ItemEditableBook.I[0x33 ^ 0x38], 0x93 ^ 0x9B);
                    int i = "".length();
                    "".length();
                    if (4 == 2) {
                        throw null;
                    }
                    while (i < tagList.tagCount()) {
                        final String stringTag = tagList.getStringTagAt(i);
                        IChatComponent processComponent;
                        try {
                            processComponent = ChatComponentProcessor.processComponent(entityPlayer, IChatComponent.Serializer.jsonToComponent(stringTag), entityPlayer);
                            "".length();
                            if (-1 < -1) {
                                throw null;
                            }
                        }
                        catch (Exception ex) {
                            processComponent = new ChatComponentText(stringTag);
                        }
                        tagList.set(i, new NBTTagString(IChatComponent.Serializer.componentToJson(processComponent)));
                        ++i;
                    }
                    tagCompound.setTag(ItemEditableBook.I[0x77 ^ 0x7B], tagList);
                    if (entityPlayer instanceof EntityPlayerMP && entityPlayer.getCurrentEquippedItem() == itemStack) {
                        ((EntityPlayerMP)entityPlayer).playerNetServerHandler.sendPacket(new S2FPacketSetSlot("".length(), entityPlayer.openContainer.getSlotFromInventory(entityPlayer.inventory, entityPlayer.inventory.currentItem).slotNumber, itemStack));
                    }
                }
            }
        }
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
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
