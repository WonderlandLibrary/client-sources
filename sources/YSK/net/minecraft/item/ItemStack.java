package net.minecraft.item;

import net.minecraft.block.*;
import java.text.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.event.*;
import java.util.*;
import net.minecraft.enchantment.*;

public final class ItemStack
{
    private Block canPlaceOnCacheBlock;
    private static final String[] I;
    private boolean canPlaceOnCacheResult;
    private NBTTagCompound stackTagCompound;
    public int stackSize;
    public int animationsToGo;
    private Block canDestroyCacheBlock;
    public static final DecimalFormat DECIMALFORMAT;
    private Item item;
    private int itemDamage;
    private EntityItemFrame itemFrame;
    private boolean canDestroyCacheResult;
    
    public boolean isOnItemFrame() {
        if (this.itemFrame != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public Multimap<String, AttributeModifier> getAttributeModifiers() {
        Object o;
        if (this.hasTagCompound() && this.stackTagCompound.hasKey(ItemStack.I[0xDE ^ 0x8E], 0xA5 ^ 0xAC)) {
            o = HashMultimap.create();
            final NBTTagList tagList = this.stackTagCompound.getTagList(ItemStack.I[0x38 ^ 0x69], 0x71 ^ 0x7B);
            int i = "".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
            while (i < tagList.tagCount()) {
                final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
                final AttributeModifier attributeModifierFromNBT = SharedMonsterAttributes.readAttributeModifierFromNBT(compoundTag);
                if (attributeModifierFromNBT != null && attributeModifierFromNBT.getID().getLeastSignificantBits() != 0L && attributeModifierFromNBT.getID().getMostSignificantBits() != 0L) {
                    ((Multimap)o).put((Object)compoundTag.getString(ItemStack.I[0xCF ^ 0x9D]), (Object)attributeModifierFromNBT);
                }
                ++i;
            }
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            o = this.getItem().getItemAttributeModifiers();
        }
        return (Multimap<String, AttributeModifier>)o;
    }
    
    public boolean isItemEnchanted() {
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey(ItemStack.I[0xDD ^ 0x91], 0x86 ^ 0x8F)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean hasEffect() {
        return this.getItem().hasEffect(this);
    }
    
    public static boolean areItemStackTagsEqual(final ItemStack itemStack, final ItemStack itemStack2) {
        int n;
        if (itemStack == null && itemStack2 == null) {
            n = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (itemStack != null && itemStack2 != null) {
            if (itemStack.stackTagCompound == null && itemStack2.stackTagCompound != null) {
                n = "".length();
                "".length();
                if (0 < -1) {
                    throw null;
                }
            }
            else if (itemStack.stackTagCompound != null && !itemStack.stackTagCompound.equals(itemStack2.stackTagCompound)) {
                n = "".length();
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
            else {
                n = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public ItemStack splitStack(final int n) {
        final ItemStack itemStack = new ItemStack(this.item, n, this.itemDamage);
        if (this.stackTagCompound != null) {
            itemStack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }
        this.stackSize -= n;
        return itemStack;
    }
    
    public boolean canPlaceOn(final Block canPlaceOnCacheBlock) {
        if (canPlaceOnCacheBlock == this.canPlaceOnCacheBlock) {
            return this.canPlaceOnCacheResult;
        }
        this.canPlaceOnCacheBlock = canPlaceOnCacheBlock;
        if (this.hasTagCompound() && this.stackTagCompound.hasKey(ItemStack.I[0x1A ^ 0x4D], 0x51 ^ 0x58)) {
            final NBTTagList tagList = this.stackTagCompound.getTagList(ItemStack.I[0x6F ^ 0x37], 0x25 ^ 0x2D);
            int i = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (i < tagList.tagCount()) {
                if (Block.getBlockFromName(tagList.getStringTagAt(i)) == canPlaceOnCacheBlock) {
                    this.canPlaceOnCacheResult = (" ".length() != 0);
                    return " ".length() != 0;
                }
                ++i;
            }
        }
        this.canPlaceOnCacheResult = ("".length() != 0);
        return "".length() != 0;
    }
    
    public static ItemStack copyItemStack(final ItemStack itemStack) {
        ItemStack copy;
        if (itemStack == null) {
            copy = null;
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            copy = itemStack.copy();
        }
        return copy;
    }
    
    public NBTTagCompound getTagCompound() {
        return this.stackTagCompound;
    }
    
    public NBTTagCompound getSubCompound(final String s, final boolean b) {
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey(s, 0x64 ^ 0x6E)) {
            return this.stackTagCompound.getCompoundTag(s);
        }
        if (b) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            this.setTagInfo(s, nbtTagCompound);
            return nbtTagCompound;
        }
        return null;
    }
    
    public boolean isItemStackDamageable() {
        int n;
        if (this.item == null) {
            n = "".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else if (this.item.getMaxDamage() <= 0) {
            n = "".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else if (this.hasTagCompound() && this.getTagCompound().getBoolean(ItemStack.I[0x75 ^ 0x78])) {
            n = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public void onPlayerStoppedUsing(final World world, final EntityPlayer entityPlayer, final int n) {
        this.getItem().onPlayerStoppedUsing(this, world, entityPlayer, n);
    }
    
    public String getDisplayName() {
        String s = this.getItem().getItemStackDisplayName(this);
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey(ItemStack.I[0x5C ^ 0x4D], 0x19 ^ 0x13)) {
            final NBTTagCompound compoundTag = this.stackTagCompound.getCompoundTag(ItemStack.I[0x58 ^ 0x4A]);
            if (compoundTag.hasKey(ItemStack.I[0x74 ^ 0x67], 0x86 ^ 0x8E)) {
                s = compoundTag.getString(ItemStack.I[0xA6 ^ 0xB2]);
            }
        }
        return s;
    }
    
    public ItemStack(final Block block, final int n) {
        this(block, n, "".length());
    }
    
    private static void I() {
        (I = new String[0x35 ^ 0x6C])["".length()] = I("w{T[s", "TUwxP");
        ItemStack.I[" ".length()] = I("!\u0011", "HuHHp");
        ItemStack.I["  ".length()] = I("\u001b%\u000f-\u0015\u0004-\u0007<L\u0017%\u0013", "vLaHv");
        ItemStack.I["   ".length()] = I("-\"6&6", "nMCHB");
        ItemStack.I[0x92 ^ 0x96] = I("\u0010\t'\u0016 1", "ThJwG");
        ItemStack.I[0x1C ^ 0x19] = I("\u0012\u0005\u0004", "fdczK");
        ItemStack.I[0x9B ^ 0x9D] = I("#/", "JKUzR");
        ItemStack.I[0x6 ^ 0x1] = I("\u0002\u0000", "kdOdb");
        ItemStack.I[0xB8 ^ 0xB0] = I("\u00180", "qTcad");
        ItemStack.I[0x28 ^ 0x21] = I("9!\u0010?6", "zNeQB");
        ItemStack.I[0xA1 ^ 0xAB] = I("(\u00077\n-\t", "lfZkJ");
        ItemStack.I[0x67 ^ 0x6C] = I("'\f\u0017", "SmpdD");
        ItemStack.I[0x6C ^ 0x60] = I("\u001b\u000f ", "onGLD");
        ItemStack.I[0x97 ^ 0x9A] = I("\u001d<\u0010$\u001f)9\u00134\u0016-", "HRrVz");
        ItemStack.I[0xE ^ 0x0] = I("\r", "uIcDc");
        ItemStack.I[0x54 ^ 0x5B] = I("%", "eCSaP");
        ItemStack.I[0xAB ^ 0xBB] = I("\u000e\"\u0019=", "kLzUH");
        ItemStack.I[0x7E ^ 0x6F] = I("\u000b%\u0011$5\u000e5", "oLbTY");
        ItemStack.I[0x34 ^ 0x26] = I("4+\u0003&)1;", "PBpVE");
        ItemStack.I[0x4 ^ 0x17] = I("\u0018;\u0005\u0013", "VZhvD");
        ItemStack.I[0x20 ^ 0x34] = I("\b.\u001e\u0014", "FOsqG");
        ItemStack.I[0x9B ^ 0x8E] = I("\u0011/\u00156\u000f\u0014?", "uFfFc");
        ItemStack.I[0x21 ^ 0x37] = I("#.1\u0014\u0006&>", "GGBdj");
        ItemStack.I[0xAE ^ 0xB9] = I("\u0007-?\u001c)\u0002=", "cDLlE");
        ItemStack.I[0x7 ^ 0x1F] = I("<\u0016!\u0012", "rwLwf");
        ItemStack.I[0x32 ^ 0x2B] = I("\u000f\b?\u0005\u000b\n\u0018", "kaLug");
        ItemStack.I[0x37 ^ 0x2D] = I(".\n#\u001a\n+\u001a", "JcPjf");
        ItemStack.I[0x2D ^ 0x36] = I("'\u0006\u000b+", "igfNn");
        ItemStack.I[0x1A ^ 0x6] = I("+\u0001\u0005=\r.\u0011", "OhvMa");
        ItemStack.I[0x80 ^ 0x9D] = I("<9'489)", "XPTDT");
        ItemStack.I[0xBB ^ 0xA5] = I("\u0012\u001e7%\u001c\u0017\u000e", "vwDUp");
        ItemStack.I[0x6D ^ 0x72] = I("7\u0015(\u0002", "ytEgl");
        ItemStack.I[0x63 ^ 0x43] = I("", "lhBFi");
        ItemStack.I[0x4C ^ 0x6D] = I("Ue", "uMzZv");
        ItemStack.I[0x82 ^ 0xA0] = I("c", "JUVlI");
        ItemStack.I[0x32 ^ 0x11] = I("zMS\\\u0003vM\u0007M\u0014", "Yhchg");
        ItemStack.I[0x2F ^ 0xB] = I("N\\q|\rH\n", "myAHi");
        ItemStack.I[0x67 ^ 0x42] = I("qe", "QFHTp");
        ItemStack.I[0x96 ^ 0xB0] = I("\u0010\u000e \u000b.4\u0006#\u001d", "XgDnh");
        ItemStack.I[0xAF ^ 0x88] = I(":\u001c \u0004\u0000\u001e\u0014#\u0012", "ruDaF");
        ItemStack.I[0x3 ^ 0x2B] = I("\u0010\u0000", "ydCrv");
        ItemStack.I[0xB ^ 0x22] = I("\u0018\u0017\u0019", "tauIQ");
        ItemStack.I[0xA1 ^ 0x8B] = I("0\u00066\u0019\u000b5\u0016", "ToEig");
        ItemStack.I[0x4A ^ 0x61] = I("\u0014&8\u0012\"\u00116", "pOKbN");
        ItemStack.I[0x96 ^ 0xBA] = I("\u0017.<\u00153", "tAPzA");
        ItemStack.I[0x9E ^ 0xB3] = I("1+ \u0001=Hdo", "rDLnO");
        ItemStack.I[0xE9 ^ 0xC7] = I("4\f? \u0013", "WcSOa");
        ItemStack.I[0x3A ^ 0x15] = I("03=\u0003a=>=\n", "YGXnO");
        ItemStack.I[0xAB ^ 0x9B] = I("6)1=", "zFCXa");
        ItemStack.I[0x79 ^ 0x48] = I("\u0006\"\u0014<", "JMfYH");
        ItemStack.I[0x11 ^ 0x23] = I("", "CSrnS");
        ItemStack.I[0x17 ^ 0x24] = I("2\u001a\u0004;\u00181\u001b\u0004,_>\u0001\u0014 \u0017:\u000b\u0002g\u0001?\u001b\u0003g", "SnpIq");
        ItemStack.I[0x19 ^ 0x2D] = I("283 \b1937O=-*7O", "SLGRa");
        ItemStack.I[0xA2 ^ 0x97] = I("\u0005?\u000e\u0019\u000e\u0006>\u000e\u000eI\t$\u001e\u0002\u0001\r.\bE\u0013\u0005 \u001fE", "dKzkg");
        ItemStack.I[0xAB ^ 0x9D] = I("-2\u001e\u0005\u001f.3\u001e\u0012X\"'\u0007\u0012X", "LFjwv");
        ItemStack.I[0xB6 ^ 0x81] = I("\u0017-8\u0010\u0016#(;\u0000\u001f'", "BCZbs");
        ItemStack.I[0x10 ^ 0x28] = I("\u000b6\u0006\u001bd\u0017,\u0001\u0004/\u0003)\u0002\u0014&\u0007", "bBcvJ");
        ItemStack.I[0xBE ^ 0x87] = I(")58\u0012(\u0019 $94", "jTVVM");
        ItemStack.I[0x8F ^ 0xB5] = I("\u0006\u000b\u0002!/6\u001e\u001e\n3", "EjleJ");
        ItemStack.I[0xB2 ^ 0x89] = I("", "favyP");
        ItemStack.I[0x6A ^ 0x56] = I("\u001a\u0013\r\u0014w\u0010\u0006\u0006;+\u0016\u0006\u0003", "sghyY");
        ItemStack.I[0xB ^ 0x36] = I("5\u0002=8+6\f $", "XkNKB");
        ItemStack.I[0x43 ^ 0x7D] = I(".,\u001a\u001d(\f.\u0011\u0002*", "mMtMD");
        ItemStack.I[0x6E ^ 0x51] = I(" 8#\u0014.\u0002:(\u000b,", "cYMDB");
        ItemStack.I[0xDC ^ 0x9C] = I("", "SAngG");
        ItemStack.I[0xE1 ^ 0xA0] = I(",.\b#I&;\u0003\u001e\u000b$9\b", "EZmNg");
        ItemStack.I[0xF1 ^ 0xB3] = I(":\u0000\u0016\u0018+9\u000e\u000b\u0004", "WiekB");
        ItemStack.I[0x1C ^ 0x5F] = I("42\u0017\u00184\u0019+\f\r/Jg", "pGeyV");
        ItemStack.I[0xE0 ^ 0xA4] = I("yLC", "YccHf");
        ItemStack.I[0x48 ^ 0xD] = I("\u001e\u000f$[t", "PMpaT");
        ItemStack.I[0x33 ^ 0x75] = I("a\u001b\t\u0012b2F", "AohuJ");
        ItemStack.I[0xD6 ^ 0x91] = I("\u000f\u0001/\u001d", "joLub");
        ItemStack.I[0xF1 ^ 0xB9] = I("\r\u001c-\r", "hrNem");
        ItemStack.I[0x70 ^ 0x39] = I("\u0000\"/\u000b", "eLLco");
        ItemStack.I[0x1A ^ 0x50] = I(" \u0016", "IreEd");
        ItemStack.I[0xE9 ^ 0xA2] = I(";3\u0002", "WEnot");
        ItemStack.I[0x1 ^ 0x4D] = I("\t!\u0007\u0018", "lOdpv");
        ItemStack.I[0x22 ^ 0x6F] = I("36'58\u0013\u00108'%", "aSWTQ");
        ItemStack.I[0xDB ^ 0x95] = I("\u001b\t\u0012\u0006\u0011;/\r\u0014\f", "Ilbgx");
        ItemStack.I[0x2C ^ 0x63] = I("%3$\u00021\u0005\u0015;\u0010,", "wVTcX");
        ItemStack.I[0xA ^ 0x5A] = I("\t\u0011\u00077\u000b*\u0010\u0007 /'\u0001\u001a#\u000b-\u0017\u0000", "HesEb");
        ItemStack.I[0xE ^ 0x5F] = I(".\u0011\u0003\u000b\u000b\r\u0010\u0003\u001c/\u0000\u0001\u001e\u001f\u000b\n\u0017\u0004", "oewyb");
        ItemStack.I[0x3B ^ 0x69] = I("\u0017:\u0013\u0007\u00034;\u0013\u0010$7#\u0002", "VNguj");
        ItemStack.I[0x5D ^ 0xE] = I("\u0001", "ZYTmd");
        ItemStack.I[0x37 ^ 0x63] = I("7", "jLQYa");
        ItemStack.I[0xE1 ^ 0xB4] = I("$\t:< \u0014\u001c&\u0017<", "ghTxE");
        ItemStack.I[0x79 ^ 0x2F] = I("4;\u000b&/\u0004.\u0017\r3", "wZebJ");
        ItemStack.I[0x22 ^ 0x75] = I(")9*\u0017:\u000b;!\b8", "jXDGV");
        ItemStack.I[0x4F ^ 0x17] = I("&\u0016#\u001a \u0004\u0014(\u0005\"", "ewMJL");
    }
    
    public void setItemDamage(final int itemDamage) {
        this.itemDamage = itemDamage;
        if (this.itemDamage < 0) {
            this.itemDamage = "".length();
        }
    }
    
    public void setItem(final Item item) {
        this.item = item;
    }
    
    public float getStrVsBlock(final Block block) {
        return this.getItem().getStrVsBlock(this, block);
    }
    
    public boolean onItemUse(final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final boolean onItemUse = this.getItem().onItemUse(this, entityPlayer, world, blockPos, enumFacing, n, n2, n3);
        if (onItemUse) {
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
        }
        return onItemUse;
    }
    
    public boolean canDestroy(final Block canDestroyCacheBlock) {
        if (canDestroyCacheBlock == this.canDestroyCacheBlock) {
            return this.canDestroyCacheResult;
        }
        this.canDestroyCacheBlock = canDestroyCacheBlock;
        if (this.hasTagCompound() && this.stackTagCompound.hasKey(ItemStack.I[0xE0 ^ 0xB5], 0x2C ^ 0x25)) {
            final NBTTagList tagList = this.stackTagCompound.getTagList(ItemStack.I[0x2A ^ 0x7C], 0xCA ^ 0xC2);
            int i = "".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (i < tagList.tagCount()) {
                if (Block.getBlockFromName(tagList.getStringTagAt(i)) == canDestroyCacheBlock) {
                    this.canDestroyCacheResult = (" ".length() != 0);
                    return " ".length() != 0;
                }
                ++i;
            }
        }
        this.canDestroyCacheResult = ("".length() != 0);
        return "".length() != 0;
    }
    
    public ItemStack setStackDisplayName(final String s) {
        if (this.stackTagCompound == null) {
            this.stackTagCompound = new NBTTagCompound();
        }
        if (!this.stackTagCompound.hasKey(ItemStack.I[0x65 ^ 0x70], 0x62 ^ 0x68)) {
            this.stackTagCompound.setTag(ItemStack.I[0x23 ^ 0x35], new NBTTagCompound());
        }
        this.stackTagCompound.getCompoundTag(ItemStack.I[0x5C ^ 0x4B]).setString(ItemStack.I[0x17 ^ 0xF], s);
        return this;
    }
    
    public boolean canHarvestBlock(final Block block) {
        return this.item.canHarvestBlock(block);
    }
    
    public int getMaxDamage() {
        return this.item.getMaxDamage();
    }
    
    public NBTTagList getEnchantmentTagList() {
        NBTTagList tagList;
        if (this.stackTagCompound == null) {
            tagList = null;
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            tagList = this.stackTagCompound.getTagList(ItemStack.I[0x39 ^ 0x29], 0x3 ^ 0x9);
        }
        return tagList;
    }
    
    public boolean getIsItemStackEqual(final ItemStack itemStack) {
        return this.isItemStackEqual(itemStack);
    }
    
    public ItemStack(final Item item) {
        this(item, " ".length());
    }
    
    public ItemStack(final Block block) {
        this(block, " ".length());
    }
    
    public ItemStack(final Item item, final int n) {
        this(item, n, "".length());
    }
    
    public ItemStack(final Item item, final int stackSize, final int itemDamage) {
        this.canDestroyCacheBlock = null;
        this.canDestroyCacheResult = ("".length() != 0);
        this.canPlaceOnCacheBlock = null;
        this.canPlaceOnCacheResult = ("".length() != 0);
        this.item = item;
        this.stackSize = stackSize;
        this.itemDamage = itemDamage;
        if (this.itemDamage < 0) {
            this.itemDamage = "".length();
        }
    }
    
    private boolean isItemStackEqual(final ItemStack itemStack) {
        int n;
        if (this.stackSize != itemStack.stackSize) {
            n = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else if (this.item != itemStack.item) {
            n = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (this.itemDamage != itemStack.itemDamage) {
            n = "".length();
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else if (this.stackTagCompound == null && itemStack.stackTagCompound != null) {
            n = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else if (this.stackTagCompound != null && !this.stackTagCompound.equals(itemStack.stackTagCompound)) {
            n = "".length();
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public int getMaxStackSize() {
        return this.getItem().getItemStackLimit();
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.stackSize) + ItemStack.I[0x75 ^ 0x7B] + this.item.getUnlocalizedName() + ItemStack.I[0x85 ^ 0x8A] + this.itemDamage;
    }
    
    public void setItemFrame(final EntityItemFrame itemFrame) {
        this.itemFrame = itemFrame;
    }
    
    public int getMaxItemUseDuration() {
        return this.getItem().getMaxItemUseDuration(this);
    }
    
    public List<String> getTooltip(final EntityPlayer entityPlayer, final boolean b) {
        final ArrayList arrayList = Lists.newArrayList();
        String s = this.getDisplayName();
        if (this.hasDisplayName()) {
            s = EnumChatFormatting.ITALIC + s;
        }
        String s2 = String.valueOf(s) + EnumChatFormatting.RESET;
        if (b) {
            String s3 = ItemStack.I[0x39 ^ 0x19];
            if (s2.length() > 0) {
                s2 = String.valueOf(s2) + ItemStack.I[0xB6 ^ 0x97];
                s3 = ItemStack.I[0x23 ^ 0x1];
            }
            final int idFromItem = Item.getIdFromItem(this.item);
            if (this.getHasSubtypes()) {
                final StringBuilder sb = new StringBuilder(String.valueOf(s2));
                final String s4 = ItemStack.I[0x9F ^ 0xBC];
                final Object[] array = new Object["   ".length()];
                array["".length()] = idFromItem;
                array[" ".length()] = this.itemDamage;
                array["  ".length()] = s3;
                s2 = sb.append(String.format(s4, array)).toString();
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                final StringBuilder sb2 = new StringBuilder(String.valueOf(s2));
                final String s5 = ItemStack.I[0x95 ^ 0xB1];
                final Object[] array2 = new Object["  ".length()];
                array2["".length()] = idFromItem;
                array2[" ".length()] = s3;
                s2 = sb2.append(String.format(s5, array2)).toString();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
        }
        else if (!this.hasDisplayName() && this.item == Items.filled_map) {
            s2 = String.valueOf(s2) + ItemStack.I[0x68 ^ 0x4D] + this.itemDamage;
        }
        arrayList.add(s2);
        int n = "".length();
        if (this.hasTagCompound() && this.stackTagCompound.hasKey(ItemStack.I[0x91 ^ 0xB7], 0xC4 ^ 0xA7)) {
            n = this.stackTagCompound.getInteger(ItemStack.I[0x83 ^ 0xA4]);
        }
        if ((n & (0x54 ^ 0x74)) == 0x0) {
            this.item.addInformation(this, entityPlayer, arrayList, b);
        }
        if (this.hasTagCompound()) {
            if ((n & " ".length()) == 0x0) {
                final NBTTagList enchantmentTagList = this.getEnchantmentTagList();
                if (enchantmentTagList != null) {
                    int i = "".length();
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                    while (i < enchantmentTagList.tagCount()) {
                        final short short1 = enchantmentTagList.getCompoundTagAt(i).getShort(ItemStack.I[0x18 ^ 0x30]);
                        final short short2 = enchantmentTagList.getCompoundTagAt(i).getShort(ItemStack.I[0xB8 ^ 0x91]);
                        if (Enchantment.getEnchantmentById(short1) != null) {
                            arrayList.add(Enchantment.getEnchantmentById(short1).getTranslatedName(short2));
                        }
                        ++i;
                    }
                }
            }
            if (this.stackTagCompound.hasKey(ItemStack.I[0x40 ^ 0x6A], 0x14 ^ 0x1E)) {
                final NBTTagCompound compoundTag = this.stackTagCompound.getCompoundTag(ItemStack.I[0xF ^ 0x24]);
                if (compoundTag.hasKey(ItemStack.I[0x7B ^ 0x57], "   ".length())) {
                    if (b) {
                        arrayList.add(ItemStack.I[0x58 ^ 0x75] + Integer.toHexString(compoundTag.getInteger(ItemStack.I[0x23 ^ 0xD])).toUpperCase());
                        "".length();
                        if (3 <= 0) {
                            throw null;
                        }
                    }
                    else {
                        arrayList.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal(ItemStack.I[0x62 ^ 0x4D]));
                    }
                }
                if (compoundTag.getTagId(ItemStack.I[0x51 ^ 0x61]) == (0xB0 ^ 0xB9)) {
                    final NBTTagList tagList = compoundTag.getTagList(ItemStack.I[0x80 ^ 0xB1], 0x93 ^ 0x9B);
                    if (tagList.tagCount() > 0) {
                        int j = "".length();
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                        while (j < tagList.tagCount()) {
                            arrayList.add(new StringBuilder().append(EnumChatFormatting.DARK_PURPLE).append(EnumChatFormatting.ITALIC).append(tagList.getStringTagAt(j)).toString());
                            ++j;
                        }
                    }
                }
            }
        }
        final Multimap<String, AttributeModifier> attributeModifiers = this.getAttributeModifiers();
        if (!attributeModifiers.isEmpty() && (n & "  ".length()) == 0x0) {
            arrayList.add(ItemStack.I[0xAC ^ 0x9E]);
            final Iterator iterator = attributeModifiers.entries().iterator();
            "".length();
            if (0 == -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Map.Entry<K, AttributeModifier> entry = iterator.next();
                final AttributeModifier attributeModifier = entry.getValue();
                double amount = attributeModifier.getAmount();
                if (attributeModifier.getID() == Item.itemModifierUUID) {
                    amount += EnchantmentHelper.func_152377_a(this, EnumCreatureAttribute.UNDEFINED);
                }
                double n2;
                if (attributeModifier.getOperation() != " ".length() && attributeModifier.getOperation() != "  ".length()) {
                    n2 = amount;
                    "".length();
                    if (0 < 0) {
                        throw null;
                    }
                }
                else {
                    n2 = amount * 100.0;
                }
                if (amount > 0.0) {
                    final ArrayList<String> list = (ArrayList<String>)arrayList;
                    final StringBuilder append = new StringBuilder().append(EnumChatFormatting.BLUE);
                    final String string = ItemStack.I[0x5A ^ 0x69] + attributeModifier.getOperation();
                    final Object[] array3 = new Object["  ".length()];
                    array3["".length()] = ItemStack.DECIMALFORMAT.format(n2);
                    array3[" ".length()] = StatCollector.translateToLocal(ItemStack.I[0x64 ^ 0x50] + (String)entry.getKey());
                    list.add(append.append(StatCollector.translateToLocalFormatted(string, array3)).toString());
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                    continue;
                }
                else {
                    if (amount >= 0.0) {
                        continue;
                    }
                    final double n3 = n2 * -1.0;
                    final ArrayList<String> list2 = (ArrayList<String>)arrayList;
                    final StringBuilder append2 = new StringBuilder().append(EnumChatFormatting.RED);
                    final String string2 = ItemStack.I[0x41 ^ 0x74] + attributeModifier.getOperation();
                    final Object[] array4 = new Object["  ".length()];
                    array4["".length()] = ItemStack.DECIMALFORMAT.format(n3);
                    array4[" ".length()] = StatCollector.translateToLocal(ItemStack.I[0x4 ^ 0x32] + (String)entry.getKey());
                    list2.add(append2.append(StatCollector.translateToLocalFormatted(string2, array4)).toString());
                }
            }
        }
        if (this.hasTagCompound() && this.getTagCompound().getBoolean(ItemStack.I[0xAB ^ 0x9C]) && (n & (0x36 ^ 0x32)) == 0x0) {
            arrayList.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal(ItemStack.I[0xA3 ^ 0x9B]));
        }
        if (this.hasTagCompound() && this.stackTagCompound.hasKey(ItemStack.I[0xAC ^ 0x95], 0x26 ^ 0x2F) && (n & (0x20 ^ 0x28)) == 0x0) {
            final NBTTagList tagList2 = this.stackTagCompound.getTagList(ItemStack.I[0x13 ^ 0x29], 0x78 ^ 0x70);
            if (tagList2.tagCount() > 0) {
                arrayList.add(ItemStack.I[0x87 ^ 0xBC]);
                arrayList.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal(ItemStack.I[0xAD ^ 0x91]));
                int k = "".length();
                "".length();
                if (0 == -1) {
                    throw null;
                }
                while (k < tagList2.tagCount()) {
                    final Block blockFromName = Block.getBlockFromName(tagList2.getStringTagAt(k));
                    if (blockFromName != null) {
                        arrayList.add(EnumChatFormatting.DARK_GRAY + blockFromName.getLocalizedName());
                        "".length();
                        if (1 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        arrayList.add(EnumChatFormatting.DARK_GRAY + ItemStack.I[0x8C ^ 0xB1]);
                    }
                    ++k;
                }
            }
        }
        if (this.hasTagCompound() && this.stackTagCompound.hasKey(ItemStack.I[0x88 ^ 0xB6], 0x9 ^ 0x0) && (n & (0x89 ^ 0x99)) == 0x0) {
            final NBTTagList tagList3 = this.stackTagCompound.getTagList(ItemStack.I[0x22 ^ 0x1D], 0x95 ^ 0x9D);
            if (tagList3.tagCount() > 0) {
                arrayList.add(ItemStack.I[0x7A ^ 0x3A]);
                arrayList.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal(ItemStack.I[0x3F ^ 0x7E]));
                int l = "".length();
                "".length();
                if (4 == 3) {
                    throw null;
                }
                while (l < tagList3.tagCount()) {
                    final Block blockFromName2 = Block.getBlockFromName(tagList3.getStringTagAt(l));
                    if (blockFromName2 != null) {
                        arrayList.add(EnumChatFormatting.DARK_GRAY + blockFromName2.getLocalizedName());
                        "".length();
                        if (2 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        arrayList.add(EnumChatFormatting.DARK_GRAY + ItemStack.I[0xF5 ^ 0xB7]);
                    }
                    ++l;
                }
            }
        }
        if (b) {
            if (this.isItemDamaged()) {
                arrayList.add(ItemStack.I[0x42 ^ 0x1] + (this.getMaxDamage() - this.getItemDamage()) + ItemStack.I[0x2 ^ 0x46] + this.getMaxDamage());
            }
            arrayList.add(EnumChatFormatting.DARK_GRAY + Item.itemRegistry.getNameForObject(this.item).toString());
            if (this.hasTagCompound()) {
                arrayList.add(EnumChatFormatting.DARK_GRAY + ItemStack.I[0x1A ^ 0x5F] + this.getTagCompound().getKeySet().size() + ItemStack.I[0xFC ^ 0xBA]);
            }
        }
        return (List<String>)arrayList;
    }
    
    public void onCrafting(final World world, final EntityPlayer entityPlayer, final int n) {
        entityPlayer.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.item)], n);
        this.item.onCreated(this, world, entityPlayer);
    }
    
    public String getUnlocalizedName() {
        return this.item.getUnlocalizedName(this);
    }
    
    public EntityItemFrame getItemFrame() {
        return this.itemFrame;
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
            if (2 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static ItemStack loadItemStackFromNBT(final NBTTagCompound nbtTagCompound) {
        final ItemStack itemStack = new ItemStack();
        itemStack.readFromNBT(nbtTagCompound);
        ItemStack itemStack2;
        if (itemStack.getItem() != null) {
            itemStack2 = itemStack;
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            itemStack2 = null;
        }
        return itemStack2;
    }
    
    public void clearCustomName() {
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey(ItemStack.I[0x50 ^ 0x49], 0x14 ^ 0x1E)) {
            final NBTTagCompound compoundTag = this.stackTagCompound.getCompoundTag(ItemStack.I[0x4B ^ 0x51]);
            compoundTag.removeTag(ItemStack.I[0x54 ^ 0x4F]);
            if (compoundTag.hasNoTags()) {
                this.stackTagCompound.removeTag(ItemStack.I[0xBA ^ 0xA6]);
                if (this.stackTagCompound.hasNoTags()) {
                    this.setTagCompound(null);
                }
            }
        }
    }
    
    public boolean canEditBlocks() {
        return this.getItem().canItemEditBlocks();
    }
    
    public Item getItem() {
        return this.item;
    }
    
    public int getRepairCost() {
        int n;
        if (this.hasTagCompound() && this.stackTagCompound.hasKey(ItemStack.I[0x7 ^ 0x4A], "   ".length())) {
            n = this.stackTagCompound.getInteger(ItemStack.I[0x5E ^ 0x10]);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    public void setRepairCost(final int n) {
        if (!this.hasTagCompound()) {
            this.stackTagCompound = new NBTTagCompound();
        }
        this.stackTagCompound.setInteger(ItemStack.I[0x14 ^ 0x5B], n);
    }
    
    public boolean getHasSubtypes() {
        return this.item.getHasSubtypes();
    }
    
    public ItemStack useItemRightClick(final World world, final EntityPlayer entityPlayer) {
        return this.getItem().onItemRightClick(this, world, entityPlayer);
    }
    
    public static boolean areItemsEqual(final ItemStack itemStack, final ItemStack itemStack2) {
        int n;
        if (itemStack == null && itemStack2 == null) {
            n = " ".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else if (itemStack != null && itemStack2 != null) {
            n = (itemStack.isItemEqual(itemStack2) ? 1 : 0);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public ItemStack copy() {
        final ItemStack itemStack = new ItemStack(this.item, this.stackSize, this.itemDamage);
        if (this.stackTagCompound != null) {
            itemStack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }
        return itemStack;
    }
    
    static {
        I();
        DECIMALFORMAT = new DecimalFormat(ItemStack.I["".length()]);
    }
    
    public void damageItem(final int n, final EntityLivingBase entityLivingBase) {
        if ((!(entityLivingBase instanceof EntityPlayer) || !((EntityPlayer)entityLivingBase).capabilities.isCreativeMode) && this.isItemStackDamageable() && this.attemptDamageItem(n, entityLivingBase.getRNG())) {
            entityLivingBase.renderBrokenItemStack(this);
            this.stackSize -= " ".length();
            if (entityLivingBase instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
                entityPlayer.triggerAchievement(StatList.objectBreakStats[Item.getIdFromItem(this.item)]);
                if (this.stackSize == 0 && this.getItem() instanceof ItemBow) {
                    entityPlayer.destroyCurrentEquippedItem();
                }
            }
            if (this.stackSize < 0) {
                this.stackSize = "".length();
            }
            this.itemDamage = "".length();
        }
    }
    
    public void setTagCompound(final NBTTagCompound stackTagCompound) {
        this.stackTagCompound = stackTagCompound;
    }
    
    public int getMetadata() {
        return this.itemDamage;
    }
    
    public void hitEntity(final EntityLivingBase entityLivingBase, final EntityPlayer entityPlayer) {
        if (this.item.hitEntity(this, entityLivingBase, entityPlayer)) {
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
        }
    }
    
    public boolean isItemEqual(final ItemStack itemStack) {
        if (itemStack != null && this.item == itemStack.item && this.itemDamage == itemStack.itemDamage) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isItemDamaged() {
        if (this.isItemStackDamageable() && this.itemDamage > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey(ItemStack.I[0x18 ^ 0x1E], 0x9D ^ 0x95)) {
            this.item = Item.getByNameOrId(nbtTagCompound.getString(ItemStack.I[0xC6 ^ 0xC1]));
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            this.item = Item.getItemById(nbtTagCompound.getShort(ItemStack.I[0x15 ^ 0x1D]));
        }
        this.stackSize = nbtTagCompound.getByte(ItemStack.I[0x96 ^ 0x9F]);
        this.itemDamage = nbtTagCompound.getShort(ItemStack.I[0x40 ^ 0x4A]);
        if (this.itemDamage < 0) {
            this.itemDamage = "".length();
        }
        if (nbtTagCompound.hasKey(ItemStack.I[0xB6 ^ 0xBD], 0xC9 ^ 0xC3)) {
            this.stackTagCompound = nbtTagCompound.getCompoundTag(ItemStack.I[0x2A ^ 0x26]);
            if (this.item != null) {
                this.item.updateItemStackNBT(this.stackTagCompound);
            }
        }
    }
    
    public ItemStack(final Block block, final int n, final int n2) {
        this(Item.getItemFromBlock(block), n, n2);
    }
    
    public void updateAnimation(final World world, final Entity entity, final int n, final boolean b) {
        if (this.animationsToGo > 0) {
            this.animationsToGo -= " ".length();
        }
        this.item.onUpdate(this, world, entity, n, b);
    }
    
    public void onBlockDestroyed(final World world, final Block block, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        if (this.item.onBlockDestroyed(this, world, block, blockPos, entityPlayer)) {
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
        }
    }
    
    public IChatComponent getChatComponent() {
        final ChatComponentText chatComponentText = new ChatComponentText(this.getDisplayName());
        if (this.hasDisplayName()) {
            chatComponentText.getChatStyle().setItalic(" ".length() != 0);
        }
        final IChatComponent appendText = new ChatComponentText(ItemStack.I[0xF0 ^ 0xA3]).appendSibling(chatComponentText).appendText(ItemStack.I[0x14 ^ 0x40]);
        if (this.item != null) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            this.writeToNBT(nbtTagCompound);
            appendText.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(nbtTagCompound.toString())));
            appendText.getChatStyle().setColor(this.getRarity().rarityColor);
        }
        return appendText;
    }
    
    public boolean isStackable() {
        if (this.getMaxStackSize() > " ".length() && (!this.isItemStackDamageable() || !this.isItemDamaged())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int getItemDamage() {
        return this.itemDamage;
    }
    
    public void setTagInfo(final String s, final NBTBase nbtBase) {
        if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
        }
        this.stackTagCompound.setTag(s, nbtBase);
    }
    
    public EnumRarity getRarity() {
        return this.getItem().getRarity(this);
    }
    
    public boolean isItemEnchantable() {
        int n;
        if (!this.getItem().isItemTool(this)) {
            n = "".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (this.isItemEnchanted()) {
            n = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public boolean attemptDamageItem(int n, final Random random) {
        if (!this.isItemStackDamageable()) {
            return "".length() != 0;
        }
        if (n > 0) {
            final int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
            int length = "".length();
            int length2 = "".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (enchantmentLevel > 0 && length2 < n) {
                if (EnchantmentDurability.negateDamage(this, enchantmentLevel, random)) {
                    ++length;
                }
                ++length2;
            }
            n -= length;
            if (n <= 0) {
                return "".length() != 0;
            }
        }
        this.itemDamage += n;
        if (this.itemDamage > this.getMaxDamage()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EnumAction getItemUseAction() {
        return this.getItem().getItemUseAction(this);
    }
    
    public NBTTagCompound writeToNBT(final NBTTagCompound nbtTagCompound) {
        final ResourceLocation resourceLocation = Item.itemRegistry.getNameForObject(this.item);
        final String s = ItemStack.I[" ".length()];
        String string;
        if (resourceLocation == null) {
            string = ItemStack.I["  ".length()];
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            string = resourceLocation.toString();
        }
        nbtTagCompound.setString(s, string);
        nbtTagCompound.setByte(ItemStack.I["   ".length()], (byte)this.stackSize);
        nbtTagCompound.setShort(ItemStack.I[0x88 ^ 0x8C], (short)this.itemDamage);
        if (this.stackTagCompound != null) {
            nbtTagCompound.setTag(ItemStack.I[0x2B ^ 0x2E], this.stackTagCompound);
        }
        return nbtTagCompound;
    }
    
    public boolean hasTagCompound() {
        if (this.stackTagCompound != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean hasDisplayName() {
        int n;
        if (this.stackTagCompound == null) {
            n = "".length();
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else if (!this.stackTagCompound.hasKey(ItemStack.I[0xBD ^ 0xA0], 0x40 ^ 0x4A)) {
            n = "".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            n = (this.stackTagCompound.getCompoundTag(ItemStack.I[0x5D ^ 0x43]).hasKey(ItemStack.I[0xBB ^ 0xA4], 0xE ^ 0x6) ? 1 : 0);
        }
        return n != 0;
    }
    
    public boolean interactWithEntity(final EntityPlayer entityPlayer, final EntityLivingBase entityLivingBase) {
        return this.item.itemInteractionForEntity(this, entityPlayer, entityLivingBase);
    }
    
    public void addEnchantment(final Enchantment enchantment, final int n) {
        if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
        }
        if (!this.stackTagCompound.hasKey(ItemStack.I[0x34 ^ 0x73], 0xAB ^ 0xA2)) {
            this.stackTagCompound.setTag(ItemStack.I[0x34 ^ 0x7C], new NBTTagList());
        }
        final NBTTagList tagList = this.stackTagCompound.getTagList(ItemStack.I[0x7 ^ 0x4E], 0x19 ^ 0x13);
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setShort(ItemStack.I[0x2A ^ 0x60], (short)enchantment.effectId);
        nbtTagCompound.setShort(ItemStack.I[0xE6 ^ 0xAD], (byte)n);
        tagList.appendTag(nbtTagCompound);
    }
    
    private ItemStack() {
        this.canDestroyCacheBlock = null;
        this.canDestroyCacheResult = ("".length() != 0);
        this.canPlaceOnCacheBlock = null;
        this.canPlaceOnCacheResult = ("".length() != 0);
    }
    
    public static boolean areItemStacksEqual(final ItemStack itemStack, final ItemStack itemStack2) {
        int n;
        if (itemStack == null && itemStack2 == null) {
            n = " ".length();
            "".length();
            if (!true) {
                throw null;
            }
        }
        else if (itemStack != null && itemStack2 != null) {
            n = (itemStack.isItemStackEqual(itemStack2) ? 1 : 0);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public ItemStack onItemUseFinish(final World world, final EntityPlayer entityPlayer) {
        return this.getItem().onItemUseFinish(this, world, entityPlayer);
    }
}
