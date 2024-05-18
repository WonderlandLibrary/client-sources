package net.minecraft.item.crafting;

import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import java.util.*;

public class RecipeFireworks implements IRecipe
{
    private static final String[] I;
    private ItemStack field_92102_a;
    
    private static void I() {
        (I = new String[0x3D ^ 0x31])["".length()] = I(" \f\u0018\u001a\u001d\u0016\u001d\u0007\u0018", "ethvr");
        RecipeFireworks.I[" ".length()] = I("\u0006==\u0003\u001c0,\"\u0001", "CEMos");
        RecipeFireworks.I["  ".length()] = I("1\u000e\u001d\n*\u0007\u001f\u0002\b6", "tvmfE");
        RecipeFireworks.I["   ".length()] = I("'(\u000f\b*\u0015", "aDfoB");
        RecipeFireworks.I[0x9F ^ 0x9B] = I("*>>\u0016\u001e\u0003%'\u0000", "lWLsi");
        RecipeFireworks.I[0x4A ^ 0x4F] = I("5\u0003 \"/\u0016\u001d", "soIAD");
        RecipeFireworks.I[0x3B ^ 0x3D] = I("\"*\r/5", "vXlFY");
        RecipeFireworks.I[0x74 ^ 0x73] = I("\u0016\u000b\u001d\u000b(&", "UdqdZ");
        RecipeFireworks.I[0x34 ^ 0x3C] = I("\u0010!'\u0001", "DXWdr");
        RecipeFireworks.I[0x9A ^ 0x93] = I("1\u00163\u0005\u001c\u0007\u0007,\u0007", "tnCis");
        RecipeFireworks.I[0x8 ^ 0x2] = I("!\u000e\u001c\u0002\u001f\u0017\u001f\u0003\u0000", "dvlnp");
        RecipeFireworks.I[0x23 ^ 0x28] = I("\u000b$\b\f9\")\u0003\u001b\t", "MEliz");
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        return this.field_92102_a.copy();
    }
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        this.field_92102_a = null;
        int length = "".length();
        int length2 = "".length();
        int length3 = "".length();
        int length4 = "".length();
        int length5 = "".length();
        int length6 = "".length();
        int i = "".length();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (i < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
            if (stackInSlot != null) {
                if (stackInSlot.getItem() == Items.gunpowder) {
                    ++length2;
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (stackInSlot.getItem() == Items.firework_charge) {
                    ++length4;
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
                else if (stackInSlot.getItem() == Items.dye) {
                    ++length3;
                    "".length();
                    if (3 == -1) {
                        throw null;
                    }
                }
                else if (stackInSlot.getItem() == Items.paper) {
                    ++length;
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else if (stackInSlot.getItem() == Items.glowstone_dust) {
                    ++length5;
                    "".length();
                    if (2 == -1) {
                        throw null;
                    }
                }
                else if (stackInSlot.getItem() == Items.diamond) {
                    ++length5;
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
                else if (stackInSlot.getItem() == Items.fire_charge) {
                    ++length6;
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
                else if (stackInSlot.getItem() == Items.feather) {
                    ++length6;
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                }
                else if (stackInSlot.getItem() == Items.gold_nugget) {
                    ++length6;
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
                else {
                    if (stackInSlot.getItem() != Items.skull) {
                        return "".length() != 0;
                    }
                    ++length6;
                }
            }
            ++i;
        }
        final int n = length5 + length3 + length6;
        if (length2 > "   ".length() || length > " ".length()) {
            return "".length() != 0;
        }
        if (length2 >= " ".length() && length == " ".length() && n == 0) {
            this.field_92102_a = new ItemStack(Items.fireworks);
            if (length4 > 0) {
                final NBTTagCompound tagCompound = new NBTTagCompound();
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                final NBTTagList list = new NBTTagList();
                int j = "".length();
                "".length();
                if (-1 < -1) {
                    throw null;
                }
                while (j < inventoryCrafting.getSizeInventory()) {
                    final ItemStack stackInSlot2 = inventoryCrafting.getStackInSlot(j);
                    if (stackInSlot2 != null && stackInSlot2.getItem() == Items.firework_charge && stackInSlot2.hasTagCompound() && stackInSlot2.getTagCompound().hasKey(RecipeFireworks.I["".length()], 0x27 ^ 0x2D)) {
                        list.appendTag(stackInSlot2.getTagCompound().getCompoundTag(RecipeFireworks.I[" ".length()]));
                    }
                    ++j;
                }
                nbtTagCompound.setTag(RecipeFireworks.I["  ".length()], list);
                nbtTagCompound.setByte(RecipeFireworks.I["   ".length()], (byte)length2);
                tagCompound.setTag(RecipeFireworks.I[0x23 ^ 0x27], nbtTagCompound);
                this.field_92102_a.setTagCompound(tagCompound);
            }
            return " ".length() != 0;
        }
        if (length2 == " ".length() && length == 0 && length4 == 0 && length3 > 0 && length6 <= " ".length()) {
            this.field_92102_a = new ItemStack(Items.firework_charge);
            final NBTTagCompound tagCompound2 = new NBTTagCompound();
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            int n2 = "".length();
            final ArrayList arrayList = Lists.newArrayList();
            int k = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (k < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot3 = inventoryCrafting.getStackInSlot(k);
                if (stackInSlot3 != null) {
                    if (stackInSlot3.getItem() == Items.dye) {
                        arrayList.add(ItemDye.dyeColors[stackInSlot3.getMetadata() & (0x54 ^ 0x5B)]);
                        "".length();
                        if (-1 >= 2) {
                            throw null;
                        }
                    }
                    else if (stackInSlot3.getItem() == Items.glowstone_dust) {
                        nbtTagCompound2.setBoolean(RecipeFireworks.I[0x8F ^ 0x8A], " ".length() != 0);
                        "".length();
                        if (2 <= 0) {
                            throw null;
                        }
                    }
                    else if (stackInSlot3.getItem() == Items.diamond) {
                        nbtTagCompound2.setBoolean(RecipeFireworks.I[0x27 ^ 0x21], " ".length() != 0);
                        "".length();
                        if (4 <= -1) {
                            throw null;
                        }
                    }
                    else if (stackInSlot3.getItem() == Items.fire_charge) {
                        n2 = " ".length();
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                    else if (stackInSlot3.getItem() == Items.feather) {
                        n2 = (0x3B ^ 0x3F);
                        "".length();
                        if (0 == 4) {
                            throw null;
                        }
                    }
                    else if (stackInSlot3.getItem() == Items.gold_nugget) {
                        n2 = "  ".length();
                        "".length();
                        if (4 <= 3) {
                            throw null;
                        }
                    }
                    else if (stackInSlot3.getItem() == Items.skull) {
                        n2 = "   ".length();
                    }
                }
                ++k;
            }
            final int[] array = new int[arrayList.size()];
            int l = "".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
            while (l < array.length) {
                array[l] = (int)arrayList.get(l);
                ++l;
            }
            nbtTagCompound2.setIntArray(RecipeFireworks.I[0x66 ^ 0x61], array);
            nbtTagCompound2.setByte(RecipeFireworks.I[0x6 ^ 0xE], (byte)n2);
            tagCompound2.setTag(RecipeFireworks.I[0x4E ^ 0x47], nbtTagCompound2);
            this.field_92102_a.setTagCompound(tagCompound2);
            return " ".length() != 0;
        }
        else {
            if (length2 != 0 || length != 0 || length4 != " ".length() || length3 <= 0 || length3 != n) {
                return "".length() != 0;
            }
            final ArrayList arrayList2 = Lists.newArrayList();
            int length7 = "".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
            while (length7 < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot4 = inventoryCrafting.getStackInSlot(length7);
                if (stackInSlot4 != null) {
                    if (stackInSlot4.getItem() == Items.dye) {
                        arrayList2.add(ItemDye.dyeColors[stackInSlot4.getMetadata() & (0x29 ^ 0x26)]);
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                    }
                    else if (stackInSlot4.getItem() == Items.firework_charge) {
                        this.field_92102_a = stackInSlot4.copy();
                        this.field_92102_a.stackSize = " ".length();
                    }
                }
                ++length7;
            }
            final int[] array2 = new int[arrayList2.size()];
            int length8 = "".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
            while (length8 < array2.length) {
                array2[length8] = (int)arrayList2.get(length8);
                ++length8;
            }
            if (this.field_92102_a == null || !this.field_92102_a.hasTagCompound()) {
                return "".length() != 0;
            }
            final NBTTagCompound compoundTag = this.field_92102_a.getTagCompound().getCompoundTag(RecipeFireworks.I[0x79 ^ 0x73]);
            if (compoundTag == null) {
                return "".length() != 0;
            }
            compoundTag.setIntArray(RecipeFireworks.I[0x96 ^ 0x9D], array2);
            return " ".length() != 0;
        }
    }
    
    @Override
    public int getRecipeSize() {
        return 0xCD ^ 0xC7;
    }
    
    static {
        I();
    }
    
    @Override
    public ItemStack[] getRemainingItems(final InventoryCrafting inventoryCrafting) {
        final ItemStack[] array = new ItemStack[inventoryCrafting.getSizeInventory()];
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < array.length) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
            if (stackInSlot != null && stackInSlot.getItem().hasContainerItem()) {
                array[i] = new ItemStack(stackInSlot.getItem().getContainerItem());
            }
            ++i;
        }
        return array;
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.field_92102_a;
    }
}
