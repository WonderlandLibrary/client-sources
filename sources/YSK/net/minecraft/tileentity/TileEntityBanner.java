package net.minecraft.tileentity;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class TileEntityBanner extends TileEntity
{
    private List<EnumDyeColor> colorList;
    private NBTTagList patterns;
    private String patternResourceLocation;
    private List<EnumBannerPattern> patternList;
    private boolean field_175119_g;
    private static final String[] I;
    private int baseColor;
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        func_181020_a(nbtTagCompound, this.baseColor, this.patterns);
    }
    
    public static int getPatterns(final ItemStack itemStack) {
        final NBTTagCompound subCompound = itemStack.getSubCompound(TileEntityBanner.I[0x4F ^ 0x41], "".length() != 0);
        int n;
        if (subCompound != null && subCompound.hasKey(TileEntityBanner.I[0x77 ^ 0x78])) {
            n = subCompound.getTagList(TileEntityBanner.I[0x7E ^ 0x6E], 0xF ^ 0x5).tagCount();
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    static {
        I();
    }
    
    public static void func_181020_a(final NBTTagCompound nbtTagCompound, final int n, final NBTTagList list) {
        nbtTagCompound.setInteger(TileEntityBanner.I[0x61 ^ 0x66], n);
        if (list != null) {
            nbtTagCompound.setTag(TileEntityBanner.I[0xBA ^ 0xB2], list);
        }
    }
    
    public static void removeBannerData(final ItemStack itemStack) {
        final NBTTagCompound subCompound = itemStack.getSubCompound(TileEntityBanner.I[0x90 ^ 0x85], "".length() != 0);
        if (subCompound != null && subCompound.hasKey(TileEntityBanner.I[0xD2 ^ 0xC4], 0x50 ^ 0x59)) {
            final NBTTagList tagList = subCompound.getTagList(TileEntityBanner.I[0x89 ^ 0x9E], 0x52 ^ 0x58);
            if (tagList.tagCount() > 0) {
                tagList.removeTag(tagList.tagCount() - " ".length());
                if (tagList.hasNoTags()) {
                    itemStack.getTagCompound().removeTag(TileEntityBanner.I[0xB ^ 0x13]);
                    if (itemStack.getTagCompound().hasNoTags()) {
                        itemStack.setTagCompound(null);
                    }
                }
            }
        }
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.pos, 0x25 ^ 0x23, nbtTagCompound);
    }
    
    private static void I() {
        (I = new String[0xD8 ^ 0xC1])["".length()] = I("\t\u001f\u000e3<\u000e\u001d\u00159#2'\u00007", "KsaPW");
        TileEntityBanner.I[" ".length()] = I("8:\u0019\u0014\f?8\u0002\u001e\u0013\u0003\u0002\u0017\u0010", "zVvwg");
        TileEntityBanner.I["  ".length()] = I(" \u0018\u0018\u0010\u001c\u0002\u0017\u001f", "pyldy");
        TileEntityBanner.I["   ".length()] = I("=4%0\u0002\u001f;\"", "mUQDg");
        TileEntityBanner.I[0x7F ^ 0x7B] = I("\u0007#!\u000b", "EBRnW");
        TileEntityBanner.I[0x42 ^ 0x47] = I("\u0017\u0004\u0001\u001d", "Uerxe");
        TileEntityBanner.I[0x76 ^ 0x70] = I("", "SYApn");
        TileEntityBanner.I[0xBA ^ 0xBD] = I("-\u0002\u0012\u000e", "ocakZ");
        TileEntityBanner.I[0x75 ^ 0x7D] = I("#\u0006\u001511\u0001\t\u0012", "sgaET");
        TileEntityBanner.I[0x73 ^ 0x7A] = I(" .\u0012*", "bOaOS");
        TileEntityBanner.I[0x4C ^ 0x46] = I(" 3\f\u001a'\u0002<\u000b", "pRxnB");
        TileEntityBanner.I[0x93 ^ 0x98] = I("\u0003)\u001f\u000f\u001f\u0004+\u0004\u0005\u00008\u0011\u0011\u000b", "AEplt");
        TileEntityBanner.I[0x5D ^ 0x51] = I("\u0006\u00128\u0012", "DsKwm");
        TileEntityBanner.I[0xA9 ^ 0xA4] = I("\f5\u0007\r", "NTthJ");
        TileEntityBanner.I[0x88 ^ 0x86] = I("$\u001e*;\u0003#\u001c11\u001c\u001f&$?", "frEXh");
        TileEntityBanner.I[0xE ^ 0x1] = I("*\u001b\r\u0016\u0012\b\u0014\n", "zzybw");
        TileEntityBanner.I[0x4D ^ 0x5D] = I(" ;\u001611\u00024\u0011", "pZbET");
        TileEntityBanner.I[0x6C ^ 0x7D] = I("", "wfEDM");
        TileEntityBanner.I[0x10 ^ 0x2] = I("8", "ZzemN");
        TileEntityBanner.I[0x30 ^ 0x23] = I("<\u0002\u00175-\u001e\r", "lccAH");
        TileEntityBanner.I[0x72 ^ 0x66] = I(" :+\u0016#", "cUGyQ");
        TileEntityBanner.I[0x3F ^ 0x2A] = I("4\r:/(3\u000f!%7\u000f54+", "vaULC");
        TileEntityBanner.I[0x7B ^ 0x6D] = I("8\u0006\u0004;\u0011\u001a\t\u0003", "hgpOt");
        TileEntityBanner.I[0x7F ^ 0x68] = I("\u00034\u0018\u001b !;\u001f", "SUloE");
        TileEntityBanner.I[0x79 ^ 0x61] = I("\u0000.<*#\u0007,' <;\u00162.", "BBSIH");
    }
    
    public NBTTagList func_181021_d() {
        return this.patterns;
    }
    
    public int getBaseColor() {
        return this.baseColor;
    }
    
    public List<EnumDyeColor> getColorList() {
        this.initializeBannerData();
        return this.colorList;
    }
    
    public String func_175116_e() {
        this.initializeBannerData();
        return this.patternResourceLocation;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.baseColor = nbtTagCompound.getInteger(TileEntityBanner.I[0x56 ^ 0x5F]);
        this.patterns = nbtTagCompound.getTagList(TileEntityBanner.I[0x7E ^ 0x74], 0xA0 ^ 0xAA);
        this.patternList = null;
        this.colorList = null;
        this.patternResourceLocation = null;
        this.field_175119_g = (" ".length() != 0);
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void initializeBannerData() {
        if (this.patternList == null || this.colorList == null || this.patternResourceLocation == null) {
            if (!this.field_175119_g) {
                this.patternResourceLocation = TileEntityBanner.I[0x34 ^ 0x25];
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            else {
                this.patternList = (List<EnumBannerPattern>)Lists.newArrayList();
                this.colorList = (List<EnumDyeColor>)Lists.newArrayList();
                this.patternList.add(EnumBannerPattern.BASE);
                this.colorList.add(EnumDyeColor.byDyeDamage(this.baseColor));
                this.patternResourceLocation = TileEntityBanner.I[0x3B ^ 0x29] + this.baseColor;
                if (this.patterns != null) {
                    int i = "".length();
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                    while (i < this.patterns.tagCount()) {
                        final NBTTagCompound compoundTag = this.patterns.getCompoundTagAt(i);
                        final EnumBannerPattern patternByID = EnumBannerPattern.getPatternByID(compoundTag.getString(TileEntityBanner.I[0x8D ^ 0x9E]));
                        if (patternByID != null) {
                            this.patternList.add(patternByID);
                            final int integer = compoundTag.getInteger(TileEntityBanner.I[0x3F ^ 0x2B]);
                            this.colorList.add(EnumDyeColor.byDyeDamage(integer));
                            this.patternResourceLocation = String.valueOf(this.patternResourceLocation) + patternByID.getPatternID() + integer;
                        }
                        ++i;
                    }
                }
            }
        }
    }
    
    public List<EnumBannerPattern> getPatternList() {
        this.initializeBannerData();
        return this.patternList;
    }
    
    public void setItemValues(final ItemStack itemStack) {
        this.patterns = null;
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey(TileEntityBanner.I["".length()], 0xCC ^ 0xC6)) {
            final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag(TileEntityBanner.I[" ".length()]);
            if (compoundTag.hasKey(TileEntityBanner.I["  ".length()])) {
                this.patterns = (NBTTagList)compoundTag.getTagList(TileEntityBanner.I["   ".length()], 0x7A ^ 0x70).copy();
            }
            if (compoundTag.hasKey(TileEntityBanner.I[0x6B ^ 0x6F], 0x13 ^ 0x70)) {
                this.baseColor = compoundTag.getInteger(TileEntityBanner.I[0x5A ^ 0x5F]);
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else {
                this.baseColor = (itemStack.getMetadata() & (0x4B ^ 0x44));
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
        }
        else {
            this.baseColor = (itemStack.getMetadata() & (0x58 ^ 0x57));
        }
        this.patternList = null;
        this.colorList = null;
        this.patternResourceLocation = TileEntityBanner.I[0xC3 ^ 0xC5];
        this.field_175119_g = (" ".length() != 0);
    }
    
    public static int getBaseColor(final ItemStack itemStack) {
        final NBTTagCompound subCompound = itemStack.getSubCompound(TileEntityBanner.I[0x8A ^ 0x81], "".length() != 0);
        int n;
        if (subCompound != null && subCompound.hasKey(TileEntityBanner.I[0x99 ^ 0x95])) {
            n = subCompound.getInteger(TileEntityBanner.I[0x5B ^ 0x56]);
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            n = itemStack.getMetadata();
        }
        return n;
    }
    
    public enum EnumBannerPattern
    {
        private ItemStack patternCraftingStack;
        
        STRIPE_CENTER(EnumBannerPattern.I[0x79 ^ 0x4A], 0x22 ^ 0x2B, EnumBannerPattern.I[0xF2 ^ 0xC6], EnumBannerPattern.I[0x5B ^ 0x6E], EnumBannerPattern.I[0x7E ^ 0x48], EnumBannerPattern.I[0x36 ^ 0x1], EnumBannerPattern.I[0x95 ^ 0xAD]), 
        BORDER(EnumBannerPattern.I[116 + 147 - 120 + 34], 0x9B ^ 0x85, EnumBannerPattern.I[154 + 7 - 129 + 146], EnumBannerPattern.I[66 + 149 - 154 + 118], EnumBannerPattern.I[104 + 48 - 42 + 70], EnumBannerPattern.I[132 + 17 + 7 + 25], EnumBannerPattern.I[76 + 24 + 14 + 68]), 
        DIAGONAL_LEFT(EnumBannerPattern.I[0x2B ^ 0x5E], 0x25 ^ 0x31, EnumBannerPattern.I[0x2E ^ 0x58], EnumBannerPattern.I[0xFD ^ 0x8A], EnumBannerPattern.I[0xF8 ^ 0x80], EnumBannerPattern.I[0x6B ^ 0x12], EnumBannerPattern.I[0x64 ^ 0x1E]), 
        HALF_VERTICAL(EnumBannerPattern.I[24 + 46 - 62 + 145], 0x8B ^ 0x91, EnumBannerPattern.I[78 + 65 - 4 + 15], EnumBannerPattern.I[33 + 126 - 68 + 64], EnumBannerPattern.I[145 + 31 - 26 + 6], EnumBannerPattern.I[121 + 43 - 74 + 67], EnumBannerPattern.I[0 + 79 - 12 + 91]), 
        TRIANGLES_TOP(EnumBannerPattern.I[0x19 ^ 0x76], 0x8A ^ 0x99, EnumBannerPattern.I[0x4F ^ 0x3F], EnumBannerPattern.I[0x24 ^ 0x55], EnumBannerPattern.I[0xE ^ 0x7C], EnumBannerPattern.I[0x32 ^ 0x41], EnumBannerPattern.I[0xB0 ^ 0xC4]), 
        TRIANGLES_BOTTOM(EnumBannerPattern.I[0x2 ^ 0x6B], 0x4B ^ 0x59, EnumBannerPattern.I[0x2 ^ 0x68], EnumBannerPattern.I[0xD9 ^ 0xB2], EnumBannerPattern.I[0x2D ^ 0x41], EnumBannerPattern.I[0x76 ^ 0x1B], EnumBannerPattern.I[0xE7 ^ 0x89]), 
        TRIANGLE_TOP(EnumBannerPattern.I[0xC3 ^ 0xA0], 0x2B ^ 0x3A, EnumBannerPattern.I[0x4F ^ 0x2B], EnumBannerPattern.I[0x5D ^ 0x38], EnumBannerPattern.I[0x1A ^ 0x7C], EnumBannerPattern.I[0xD0 ^ 0xB7], EnumBannerPattern.I[0xF6 ^ 0x9E]), 
        SQUARE_TOP_RIGHT(EnumBannerPattern.I[0xBE ^ 0xAB], 0x72 ^ 0x76, EnumBannerPattern.I[0x76 ^ 0x60], EnumBannerPattern.I[0x62 ^ 0x75], EnumBannerPattern.I[0x1D ^ 0x5], EnumBannerPattern.I[0xB0 ^ 0xA9], EnumBannerPattern.I[0xB9 ^ 0xA3]), 
        CREEPER(EnumBannerPattern.I[82 + 53 + 21 + 30], 0x51 ^ 0x71, EnumBannerPattern.I[93 + 174 - 130 + 50], EnumBannerPattern.I[146 + 98 - 129 + 73], new ItemStack(Items.skull, " ".length(), 0x40 ^ 0x44)), 
        SQUARE_BOTTOM_RIGHT(EnumBannerPattern.I[0x36 ^ 0x3F], "  ".length(), EnumBannerPattern.I[0x1 ^ 0xB], EnumBannerPattern.I[0xCF ^ 0xC4], EnumBannerPattern.I[0x9D ^ 0x91], EnumBannerPattern.I[0x36 ^ 0x3B], EnumBannerPattern.I[0xB1 ^ 0xBF]), 
        STRIPE_SMALL(EnumBannerPattern.I[0xD2 ^ 0x99], 0x76 ^ 0x7B, EnumBannerPattern.I[0xD8 ^ 0x94], EnumBannerPattern.I[0x55 ^ 0x18], EnumBannerPattern.I[0x27 ^ 0x69], EnumBannerPattern.I[0xCC ^ 0x83], EnumBannerPattern.I[0x95 ^ 0xC5]), 
        STRIPE_DOWNRIGHT(EnumBannerPattern.I[0x48 ^ 0x77], 0x5B ^ 0x50, EnumBannerPattern.I[0xCD ^ 0x8D], EnumBannerPattern.I[0xD5 ^ 0x94], EnumBannerPattern.I[0x0 ^ 0x42], EnumBannerPattern.I[0x20 ^ 0x63], EnumBannerPattern.I[0xDA ^ 0x9E]);
        
        private String patternName;
        private String patternID;
        
        DIAGONAL_RIGHT(EnumBannerPattern.I[0x36 ^ 0x4D], 0x85 ^ 0x90, EnumBannerPattern.I[0x28 ^ 0x54], EnumBannerPattern.I[0xED ^ 0x90], EnumBannerPattern.I[0x6B ^ 0x15], EnumBannerPattern.I[63 + 71 - 36 + 29], EnumBannerPattern.I[9 + 36 - 32 + 115]), 
        DIAGONAL_RIGHT_MIRROR(EnumBannerPattern.I[96 + 34 - 7 + 12], 0xD3 ^ 0xC4, EnumBannerPattern.I[107 + 58 - 97 + 68], EnumBannerPattern.I[4 + 46 - 3 + 90], EnumBannerPattern.I[99 + 46 - 10 + 3], EnumBannerPattern.I[42 + 78 + 19 + 0], EnumBannerPattern.I[15 + 88 - 99 + 136]), 
        TRIANGLE_BOTTOM(EnumBannerPattern.I[0x53 ^ 0xE], 0x16 ^ 0x6, EnumBannerPattern.I[0xCA ^ 0x94], EnumBannerPattern.I[0xE ^ 0x51], EnumBannerPattern.I[0x4C ^ 0x2C], EnumBannerPattern.I[0x3A ^ 0x5B], EnumBannerPattern.I[0x26 ^ 0x44]), 
        FLOWER(EnumBannerPattern.I[78 + 22 + 17 + 90], 0x58 ^ 0x7D, EnumBannerPattern.I[115 + 187 - 244 + 150], EnumBannerPattern.I[163 + 8 - 97 + 135], new ItemStack(Blocks.red_flower, " ".length(), BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta())), 
        GRADIENT_UP(EnumBannerPattern.I[124 + 153 - 229 + 147], 0x79 ^ 0x5B, EnumBannerPattern.I[26 + 4 + 142 + 24], EnumBannerPattern.I[61 + 58 - 21 + 99], EnumBannerPattern.I[35 + 15 + 118 + 30], EnumBannerPattern.I[174 + 74 - 215 + 166], EnumBannerPattern.I[99 + 158 - 89 + 32]), 
        BASE(EnumBannerPattern.I["".length()], "".length(), EnumBannerPattern.I[" ".length()], EnumBannerPattern.I["  ".length()]), 
        RHOMBUS_MIDDLE(EnumBannerPattern.I[52 + 130 - 61 + 26], 0x6F ^ 0x76, EnumBannerPattern.I[26 + 40 + 19 + 63], EnumBannerPattern.I[83 + 98 - 60 + 28], EnumBannerPattern.I[3 + 144 - 3 + 6], EnumBannerPattern.I[65 + 75 - 11 + 22], EnumBannerPattern.I[13 + 121 - 106 + 124]), 
        STRIPE_RIGHT(EnumBannerPattern.I[0x5A ^ 0x77], 0x2E ^ 0x26, EnumBannerPattern.I[0x5D ^ 0x73], EnumBannerPattern.I[0x28 ^ 0x7], EnumBannerPattern.I[0x80 ^ 0xB0], EnumBannerPattern.I[0x7C ^ 0x4D], EnumBannerPattern.I[0x1 ^ 0x33]);
        
        private static final String[] I;
        
        DIAGONAL_LEFT_MIRROR(EnumBannerPattern.I[23 + 2 + 45 + 59], 0x7C ^ 0x6A, EnumBannerPattern.I[55 + 43 + 32 + 0], EnumBannerPattern.I[37 + 26 - 54 + 122], EnumBannerPattern.I[68 + 114 - 145 + 95], EnumBannerPattern.I[115 + 31 - 93 + 80], EnumBannerPattern.I[47 + 19 + 6 + 62]), 
        HALF_HORIZONTAL_MIRROR(EnumBannerPattern.I[83 + 137 - 166 + 117], 0x79 ^ 0x64, EnumBannerPattern.I[47 + 118 - 67 + 74], EnumBannerPattern.I[49 + 54 - 24 + 94], EnumBannerPattern.I[47 + 168 - 192 + 151], EnumBannerPattern.I[49 + 1 + 76 + 49], EnumBannerPattern.I[72 + 56 + 19 + 29]), 
        CURLY_BORDER(EnumBannerPattern.I[100 + 178 - 252 + 157], 0x5E ^ 0x41, EnumBannerPattern.I[133 + 21 - 128 + 158], EnumBannerPattern.I[77 + 152 - 173 + 129], new ItemStack(Blocks.vine)), 
        BRICKS(EnumBannerPattern.I[39 + 2 + 22 + 138], 0x24 ^ 0x7, EnumBannerPattern.I[73 + 124 - 138 + 143], EnumBannerPattern.I[122 + 155 - 124 + 50], new ItemStack(Blocks.brick_block)), 
        SQUARE_TOP_LEFT(EnumBannerPattern.I[0x16 ^ 0x19], "   ".length(), EnumBannerPattern.I[0x62 ^ 0x72], EnumBannerPattern.I[0x74 ^ 0x65], EnumBannerPattern.I[0xBF ^ 0xAD], EnumBannerPattern.I[0x5E ^ 0x4D], EnumBannerPattern.I[0xB6 ^ 0xA2]), 
        STRIPE_LEFT(EnumBannerPattern.I[0x25 ^ 0x2], 0x90 ^ 0x97, EnumBannerPattern.I[0xB7 ^ 0x9F], EnumBannerPattern.I[0x7F ^ 0x56], EnumBannerPattern.I[0x1E ^ 0x34], EnumBannerPattern.I[0xF ^ 0x24], EnumBannerPattern.I[0x72 ^ 0x5E]), 
        GRADIENT(EnumBannerPattern.I[148 + 134 - 117 + 24], 0x8D ^ 0xAC, EnumBannerPattern.I[94 + 20 - 93 + 169], EnumBannerPattern.I[16 + 5 + 59 + 111], EnumBannerPattern.I[181 + 117 - 288 + 182], EnumBannerPattern.I[90 + 43 - 88 + 148], EnumBannerPattern.I[26 + 4 + 19 + 145]), 
        SKULL(EnumBannerPattern.I[110 + 137 - 192 + 149], 0xB3 ^ 0x97, EnumBannerPattern.I[86 + 63 - 51 + 107], EnumBannerPattern.I[104 + 138 - 165 + 129], new ItemStack(Items.skull, " ".length(), " ".length())), 
        STRIPE_DOWNLEFT(EnumBannerPattern.I[0xC0 ^ 0x85], 0x88 ^ 0x84, EnumBannerPattern.I[0x37 ^ 0x71], EnumBannerPattern.I[0x75 ^ 0x32], EnumBannerPattern.I[0x62 ^ 0x2A], EnumBannerPattern.I[0xCD ^ 0x84], EnumBannerPattern.I[0xC9 ^ 0x83]), 
        SQUARE_BOTTOM_LEFT(EnumBannerPattern.I["   ".length()], " ".length(), EnumBannerPattern.I[0x7F ^ 0x7B], EnumBannerPattern.I[0x35 ^ 0x30], EnumBannerPattern.I[0x7A ^ 0x7C], EnumBannerPattern.I[0x3D ^ 0x3A], EnumBannerPattern.I[0x40 ^ 0x48]), 
        HALF_VERTICAL_MIRROR(EnumBannerPattern.I[160 + 89 - 226 + 142], 0x6B ^ 0x77, EnumBannerPattern.I[114 + 157 - 201 + 96], EnumBannerPattern.I[5 + 80 + 22 + 60], EnumBannerPattern.I[148 + 37 - 45 + 28], EnumBannerPattern.I[81 + 96 - 115 + 107], EnumBannerPattern.I[105 + 141 - 93 + 17]), 
        HALF_HORIZONTAL(EnumBannerPattern.I[51 + 133 - 107 + 82], 0x52 ^ 0x49, EnumBannerPattern.I[5 + 123 - 63 + 95], EnumBannerPattern.I[1 + 74 + 57 + 29], EnumBannerPattern.I[0 + 68 + 78 + 16], EnumBannerPattern.I[37 + 11 - 41 + 156], EnumBannerPattern.I[148 + 39 - 173 + 150]);
        
        private String[] craftingLayers;
        
        CIRCLE_MIDDLE(EnumBannerPattern.I[128 + 43 - 135 + 105], 0xBC ^ 0xA4, EnumBannerPattern.I[11 + 135 - 82 + 78], EnumBannerPattern.I[71 + 88 - 77 + 61], EnumBannerPattern.I[122 + 14 - 124 + 132], EnumBannerPattern.I[72 + 47 - 6 + 32], EnumBannerPattern.I[63 + 68 - 8 + 23]);
        
        private static final EnumBannerPattern[] ENUM$VALUES;
        
        CROSS(EnumBannerPattern.I[0xD ^ 0x5C], 0x75 ^ 0x7B, EnumBannerPattern.I[0xD9 ^ 0x8B], EnumBannerPattern.I[0x32 ^ 0x61], EnumBannerPattern.I[0x5A ^ 0xE], EnumBannerPattern.I[0x31 ^ 0x64], EnumBannerPattern.I[0x6C ^ 0x3A]), 
        MOJANG(EnumBannerPattern.I[188 + 99 - 95 + 18], 0x8E ^ 0xA8, EnumBannerPattern.I[68 + 152 - 137 + 128], EnumBannerPattern.I[161 + 11 + 15 + 25], new ItemStack(Items.golden_apple, " ".length(), " ".length())), 
        STRIPE_TOP(EnumBannerPattern.I[0x23 ^ 0x2], 0x96 ^ 0x90, EnumBannerPattern.I[0x7 ^ 0x25], EnumBannerPattern.I[0xBC ^ 0x9F], EnumBannerPattern.I[0x0 ^ 0x24], EnumBannerPattern.I[0xE0 ^ 0xC5], EnumBannerPattern.I[0x68 ^ 0x4E]), 
        STRIPE_MIDDLE(EnumBannerPattern.I[0xB3 ^ 0x8A], 0xCC ^ 0xC6, EnumBannerPattern.I[0x57 ^ 0x6D], EnumBannerPattern.I[0x1A ^ 0x21], EnumBannerPattern.I[0x18 ^ 0x24], EnumBannerPattern.I[0xBC ^ 0x81], EnumBannerPattern.I[0x3B ^ 0x5]), 
        STRIPE_BOTTOM(EnumBannerPattern.I[0xA3 ^ 0xB8], 0xA1 ^ 0xA4, EnumBannerPattern.I[0x99 ^ 0x85], EnumBannerPattern.I[0xBE ^ 0xA3], EnumBannerPattern.I[0x47 ^ 0x59], EnumBannerPattern.I[0xAD ^ 0xB2], EnumBannerPattern.I[0x1C ^ 0x3C]), 
        STRAIGHT_CROSS(EnumBannerPattern.I[0x12 ^ 0x45], 0x14 ^ 0x1B, EnumBannerPattern.I[0x1 ^ 0x59], EnumBannerPattern.I[0xE5 ^ 0xBC], EnumBannerPattern.I[0xB ^ 0x51], EnumBannerPattern.I[0xCB ^ 0x90], EnumBannerPattern.I[0x6E ^ 0x32]);
        
        public boolean hasValidCrafting() {
            if (this.patternCraftingStack == null && this.craftingLayers["".length()] == null) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        public ItemStack getCraftingStack() {
            return this.patternCraftingStack;
        }
        
        public String[] getCraftingLayers() {
            return this.craftingLayers;
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
                if (0 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[16 + 137 - 29 + 89])["".length()] = I("\n9$+", "HxwnC");
            EnumBannerPattern.I[" ".length()] = I("(%= ", "JDNEB");
            EnumBannerPattern.I["  ".length()] = I("+", "IeyQn");
            EnumBannerPattern.I["   ".length()] = I("\u000b\u0000\u0000\u0014+\u001d\u000e\u0017\u001a-\f\u001e\u0018\n5\u001d\u0017\u0001", "XQUUy");
            EnumBannerPattern.I[0xC2 ^ 0xC6] = I("\u0016\u0013-&\u0011\u0000=:(\u0017\u0011\r5\u0018\u000f\u0000\u0004,", "ebXGc");
            EnumBannerPattern.I[0x9F ^ 0x9A] = I("\b)", "jESek");
            EnumBannerPattern.I[0x6F ^ 0x69] = I("pLc", "PlCMv");
            EnumBannerPattern.I[0x49 ^ 0x4E] = I("qOP", "Qopma");
            EnumBannerPattern.I[0x72 ^ 0x7A] = I("MRy", "nrYBK");
            EnumBannerPattern.I[0x35 ^ 0x3C] = I("4\u001f!+;\"\u00116%=3\u000195;.\t<>", "gNtji");
            EnumBannerPattern.I[0x1 ^ 0xB] = I("\u0012)\u0001\r\u001d\u0004\u0007\u0016\u0003\u001b\u00157\u00193\u001d\b?\u001c\u0018", "aXtlo");
            EnumBannerPattern.I[0x1C ^ 0x17] = I("\t$", "kVHoA");
            EnumBannerPattern.I[0x51 ^ 0x5D] = I("FKU", "fkuqf");
            EnumBannerPattern.I[0xB7 ^ 0xBA] = I("wDc", "WdCGJ");
            EnumBannerPattern.I[0x6 ^ 0x8] = I("ToI", "tOjos");
            EnumBannerPattern.I[0x8D ^ 0x82] = I("\u001c\u0006$3\u0015\n\b%=\u0017\u0010\u001b44\u0013", "OWqrG");
            EnumBannerPattern.I[0x8A ^ 0x9A] = I("278\u00189$\u00199\u0016;\u001e*(\u001f?", "AFMyK");
            EnumBannerPattern.I[0x7D ^ 0x6C] = I("&\u0019", "RuvwH");
            EnumBannerPattern.I[0x7E ^ 0x6C] = I("KHd", "hhDgg");
            EnumBannerPattern.I[0x3B ^ 0x28] = I("LVY", "lvyFw");
            EnumBannerPattern.I[0x5F ^ 0x4B] = I("ZlF", "zLfRO");
            EnumBannerPattern.I[0xD5 ^ 0xC0] = I("'\t\f\u001271\u0007\r\u001c5+\n\u0010\u0014- ", "tXYSe");
            EnumBannerPattern.I[0x12 ^ 0x4] = I("\u0003074\u0004\u0015\u001e6:\u0006/3+2\u001e\u0004", "pABUv");
            EnumBannerPattern.I[0xAE ^ 0xB9] = I("\u0017\u0005", "cwBUf");
            EnumBannerPattern.I[0x4 ^ 0x1C] = I("LLo", "llLqV");
            EnumBannerPattern.I[0x3F ^ 0x26] = I("jVw", "JvWWl");
            EnumBannerPattern.I[0x0 ^ 0x1A] = I("RJB", "rjbdX");
            EnumBannerPattern.I[0x3A ^ 0x21] = I("=\u0017#\u001f;+\u001c3\u0019?:\f<", "nCqVk");
            EnumBannerPattern.I[0x20 ^ 0x3C] = I("\u0011!\u0013\"\u0012\u0007\n\u0003$\u0016\u0016:\f", "bUaKb");
            EnumBannerPattern.I[0xA4 ^ 0xB9] = I("\u0000\u0012", "baxYr");
            EnumBannerPattern.I[0x2F ^ 0x31] = I("dyz", "DYZJq");
            EnumBannerPattern.I[0x88 ^ 0x97] = I("cUr", "CuRAi");
            EnumBannerPattern.I[0x88 ^ 0xA8] = I("LdY", "oGzyX");
            EnumBannerPattern.I[0x40 ^ 0x61] = I("05\u00130\u001b&>\u00156\u001b", "caAyK");
            EnumBannerPattern.I[0x45 ^ 0x67] = I("\t\u0015(9\u001b\u001f>.?\u001b", "zaZPk");
            EnumBannerPattern.I[0x6B ^ 0x48] = I("=$", "IWHRx");
            EnumBannerPattern.I[0x52 ^ 0x76] = I("tvQ", "WUrem");
            EnumBannerPattern.I[0x70 ^ 0x55] = I("ENA", "enadV");
            EnumBannerPattern.I[0x91 ^ 0xB7] = I("xuf", "XUFxO");
            EnumBannerPattern.I[0x44 ^ 0x63] = I(">\u001c\";((\u0017<7>9", "mHprx");
            EnumBannerPattern.I[0xB3 ^ 0x9B] = I("\u0007\f+&\u0016\u0011'5*\u0000\u0000", "txYOf");
            EnumBannerPattern.I[0x2C ^ 0x5] = I("&\u001f", "JlHyr");
            EnumBannerPattern.I[0xA2 ^ 0x88] = I("rxS", "QXsko");
            EnumBannerPattern.I[0xAE ^ 0x85] = I("FDe", "edEJW");
            EnumBannerPattern.I[0x15 ^ 0x39] = I("gnJ", "DNjQi");
            EnumBannerPattern.I[0x8B ^ 0xA6] = I("9\u0004$\u00061/\u000f$\u0006&\"\u0004", "jPvOa");
            EnumBannerPattern.I[0x40 ^ 0x6E] = I("*\u001f49\u0013<449\u00041\u001f", "YkFPc");
            EnumBannerPattern.I[0x3B ^ 0x14] = I(">\u001e", "LmQmh");
            EnumBannerPattern.I[0x61 ^ 0x51] = I("ySz", "YsYbJ");
            EnumBannerPattern.I[0x96 ^ 0xA7] = I("pLI", "PljSM");
            EnumBannerPattern.I[0x29 ^ 0x1B] = I("Wo@", "wOctt");
            EnumBannerPattern.I[0x4A ^ 0x79] = I("6\u000e\u001e8\u0006 \u0005\u000f4\u00181\u001f\u001e", "eZLqV");
            EnumBannerPattern.I[0x7D ^ 0x49] = I("\u0003%\u0004-\"\u0015\u000e\u0015!<\u00044\u0004", "pQvDR");
            EnumBannerPattern.I[0x34 ^ 0x1] = I("9+", "ZXOZJ");
            EnumBannerPattern.I[0x87 ^ 0xB1] = I("cns", "CMSpY");
            EnumBannerPattern.I[0x70 ^ 0x47] = I("RYv", "rzVXY");
            EnumBannerPattern.I[0x29 ^ 0x11] = I("vGD", "VddRL");
            EnumBannerPattern.I[0x1A ^ 0x23] = I("8\u001e\u0015\u0011).\u0015\n\u0011=/\u0006\u0002", "kJGXy");
            EnumBannerPattern.I[0xB4 ^ 0x8E] = I("\u0010\u001a&\u0000\"\u000619\u00006\u0007\u00021", "cnTiR");
            EnumBannerPattern.I[0x8B ^ 0xB0] = I("/6", "BEzCm");
            EnumBannerPattern.I[0x4C ^ 0x70] = I("PjR", "pJrPl");
            EnumBannerPattern.I[0xBF ^ 0x82] = I("NLO", "molWa");
            EnumBannerPattern.I[0x23 ^ 0x1D] = I("wVg", "WvGQX");
            EnumBannerPattern.I[0xF9 ^ 0xC6] = I("4\u001d\u00073\u0005\"\u0016\u00115\u0002)\u001b\u001c=\u001d3", "gIUzU");
            EnumBannerPattern.I[0x1E ^ 0x5E] = I("\u001f\u0012\u0011*6\t9\u0007,1\u0002\u0014\n$.\u0018", "lfcCF");
            EnumBannerPattern.I[0x9 ^ 0x48] = I("+\u000b$", "OyWEL");
            EnumBannerPattern.I[0x57 ^ 0x15] = I("Tan", "wANCL");
            EnumBannerPattern.I[0x35 ^ 0x76] = I("Y`u", "yCUMF");
            EnumBannerPattern.I[0x6A ^ 0x2E] = I("lIV", "LiuzS");
            EnumBannerPattern.I[0xC9 ^ 0x8C] = I("\u0006\u0013\u0019\u001c\u0011\u0010\u0018\u000f\u001a\u0016\u001b\u000b\u000e\u0013\u0015", "UGKUA");
            EnumBannerPattern.I[0xDE ^ 0x98] = I("#\u001c0\u0010\u001157&\u0016\u0016>\u0004'\u001f\u0015", "PhBya");
            EnumBannerPattern.I[0x26 ^ 0x61] = I("\u0014\r+", "paXKO");
            EnumBannerPattern.I[0xFB ^ 0xB3] = I("OaS", "oApIe");
            EnumBannerPattern.I[0x76 ^ 0x3F] = I("rIL", "RjlTS");
            EnumBannerPattern.I[0xC0 ^ 0x8A] = I("Gbg", "dBGgv");
            EnumBannerPattern.I[0x1E ^ 0x55] = I("\t\u0010\" 3\u001f\u001b#$\"\u0016\b", "ZDpic");
            EnumBannerPattern.I[0x1F ^ 0x53] = I("\u001d\u001b(\u000f\u00181\u0005=\u0011\u001d\u001e\u0013:", "nvIct");
            EnumBannerPattern.I[0x12 ^ 0x5F] = I("\u0000\u0002", "sqxvw");
            EnumBannerPattern.I[0x4A ^ 0x4] = I("Lgf", "oGEju");
            EnumBannerPattern.I[0x5E ^ 0x11] = I("qxZ", "RXyfQ");
            EnumBannerPattern.I[0x63 ^ 0x33] = I("kOQ", "KoqeW");
            EnumBannerPattern.I[0x6D ^ 0x3C] = I("\"\u000b5\u00188", "aYzKk");
            EnumBannerPattern.I[0xDE ^ 0x8C] = I(".\u001f\u00047\u0005", "MmkDv");
            EnumBannerPattern.I[0x6 ^ 0x55] = I("\u000f\"", "lPVly");
            EnumBannerPattern.I[0xD5 ^ 0x81] = I("HhR", "kHqMX");
            EnumBannerPattern.I[0xA ^ 0x5F] = I("ste", "SWElk");
            EnumBannerPattern.I[0x55 ^ 0x3] = I("hue", "KUFDD");
            EnumBannerPattern.I[0x60 ^ 0x37] = I("=\u00157\u0012+)\t1\f!<\u000e6\u0000", "nAeSb");
            EnumBannerPattern.I[0x60 ^ 0x38] = I("6\u0007\u0000\u0007<\"\u001b\u0006967\u001c\u0001\u0015", "EsrfU");
            EnumBannerPattern.I[0x32 ^ 0x6B] = I("$2", "WQgtn");
            EnumBannerPattern.I[0x20 ^ 0x7A] = I("cjp", "CIPmS");
            EnumBannerPattern.I[0x11 ^ 0x4A] = I("lou", "OLVBF");
            EnumBannerPattern.I[0x58 ^ 0x4] = I("CKw", "chWvW");
            EnumBannerPattern.I[0x56 ^ 0xB] = I("\u0000\u0018\u0002#/\u0013\u0006\u000e=#\u001b\u001e\u001f-,", "TJKba");
            EnumBannerPattern.I[0xCC ^ 0x92] = I("2+!\":!5-\u001c6)-<,9", "FYHCT");
            EnumBannerPattern.I[0x36 ^ 0x69] = I("\t\u0011", "kexXt");
            EnumBannerPattern.I[0x5A ^ 0x3A] = I("LWb", "lwBqD");
            EnumBannerPattern.I[0x32 ^ 0x53] = I("pHZ", "PkzXq");
            EnumBannerPattern.I[0x55 ^ 0x37] = I("ARU", "brvcB");
            EnumBannerPattern.I[0x29 ^ 0x4A] = I("\u0010\u0006##\u0017\u0003\u0018/=\r\u000b\u0004", "DTjbY");
            EnumBannerPattern.I[0x6F ^ 0xB] = I("-3\u0011;8>-\u001d\u0005\"61", "YAxZV");
            EnumBannerPattern.I[0xF6 ^ 0x93] = I(":&", "NRdPU");
            EnumBannerPattern.I[0x4E ^ 0x28] = I("kaa", "HABES");
            EnumBannerPattern.I[0xA ^ 0x6D] = I("K`m", "kCMWT");
            EnumBannerPattern.I[0x65 ^ 0xD] = I("hij", "HIJbC");
            EnumBannerPattern.I[0xF ^ 0x66] = I("\r=\u0011,,\u001e#\u001d>=\u001b \f9-\u0014", "YoXmb");
            EnumBannerPattern.I[0x57 ^ 0x3D] = I(":\u0006>#\u0002)\u0018213,\u001b#6\u0003#", "NtWBl");
            EnumBannerPattern.I[0x38 ^ 0x53] = I("0-)", "RYZQd");
            EnumBannerPattern.I[0xAA ^ 0xC6] = I("YtM", "yTmeb");
            EnumBannerPattern.I[0x2C ^ 0x41] = I("hgv", "KGUUO");
            EnumBannerPattern.I[0x13 ^ 0x7D] = I("UJB", "uibQu");
            EnumBannerPattern.I[0x5B ^ 0x34] = I("\u001a\u0015\u0001\n(\t\u000b\r\u00189\u001a\b\u0018", "NGHKf");
            EnumBannerPattern.I[0x26 ^ 0x56] = I("\"\"\u0007\u0006=1<\u000b\u0014\f\"?\u001e", "VPngS");
            EnumBannerPattern.I[0xEE ^ 0x9F] = I("\u0010\u001d)", "diZtv");
            EnumBannerPattern.I[0x76 ^ 0x4] = I("amW", "ANwWW");
            EnumBannerPattern.I[0xE2 ^ 0x91] = I("qvN", "RVmcI");
            EnumBannerPattern.I[0x3 ^ 0x77] = I("ovQ", "OVqYr");
            EnumBannerPattern.I[0xFD ^ 0x88] = I("#$\u000b!\u001f),\u00069\u001c\"+\u001e", "gmJfP");
            EnumBannerPattern.I[0x13 ^ 0x65] = I("\u0014!*\" \u001e)'\u001a#\u0015.?", "pHKEO");
            EnumBannerPattern.I[0x5F ^ 0x28] = I("/\u000e", "CjoAW");
            EnumBannerPattern.I[0xDC ^ 0xA4] = I("KNj", "hmJFc");
            EnumBannerPattern.I[0x3B ^ 0x42] = I("GGV", "dgvad");
            EnumBannerPattern.I[0x20 ^ 0x5A] = I("PTm", "ptMBf");
            EnumBannerPattern.I[0xE ^ 0x75] = I("\u0013/\f2\f\u0019'\u0001*\u0011\u001e!\u0005!", "WfMuC");
            EnumBannerPattern.I[0x3F ^ 0x43] = I("\u0017>9\r\u0018\u001d645\u0002\u0003\b*\u0003\u0010\u001b#", "sWXjw");
            EnumBannerPattern.I[0x15 ^ 0x68] = I("\u0003\"", "qFLqu");
            EnumBannerPattern.I[0x3B ^ 0x45] = I("Rnp", "rNPPM");
            EnumBannerPattern.I[63 + 44 - 93 + 113] = I("eFj", "EfIrn");
            EnumBannerPattern.I[105 + 21 - 48 + 50] = I("Hps", "hSPix");
            EnumBannerPattern.I[102 + 111 - 158 + 74] = I("\u000b\u0004\u0012\u000b\u0003\u0001\f\u001f\u0013\u0000\n\u000b\u0007\u0013\u0001\u0006\u001f\u0001\u0003\u001e", "OMSLL");
            EnumBannerPattern.I[49 + 14 - 33 + 100] = I(".\u000e\u0011(-$\u0006\u001c\u00107:8\u001c*$>", "JgpOB");
            EnumBannerPattern.I[34 + 7 - 13 + 103] = I("\u001b>-", "wKIYm");
            EnumBannerPattern.I[6 + 97 + 21 + 8] = I("wNp", "WnPYS");
            EnumBannerPattern.I[111 + 5 - 26 + 43] = I("yyB", "ZYboS");
            EnumBannerPattern.I[124 + 84 - 152 + 78] = I("SKu", "phUto");
            EnumBannerPattern.I[134 + 25 - 69 + 45] = I("\u00018\u0004\u0012.\u000b0\t\n3\f6\r\u0001>\b8\u0017\u0007.\u0017", "EqEUa");
            EnumBannerPattern.I[48 + 12 - 30 + 106] = I("\u0012+5!;\u0018#8\u0019&\u001f%<2", "vBTFT");
            EnumBannerPattern.I[94 + 56 - 126 + 113] = I("\u0011\u00136", "cfRuW");
            EnumBannerPattern.I[30 + 29 + 54 + 25] = I("mjn", "MIMSj");
            EnumBannerPattern.I[88 + 106 - 60 + 5] = I("HCu", "hcVdG");
            EnumBannerPattern.I[139 + 58 - 176 + 119] = I("ibK", "IBkRu");
            EnumBannerPattern.I[136 + 52 - 126 + 79] = I("-\r\u0002\u0004\u001b+\u001b\u001d\u000e\u0013*\b\u0015", "nDPGW");
            EnumBannerPattern.I[96 + 85 - 180 + 141] = I("0?\u00013=6", "SVsPQ");
            EnumBannerPattern.I[60 + 30 - 38 + 91] = I("5,", "XOQwO");
            EnumBannerPattern.I[125 + 125 - 249 + 143] = I("pXS", "PxsUL");
            EnumBannerPattern.I[93 + 135 - 185 + 102] = I("XDh", "xgHrh");
            EnumBannerPattern.I[54 + 135 - 141 + 98] = I("qqc", "QQCzl");
            EnumBannerPattern.I[64 + 139 - 178 + 122] = I("1-<\u001b\u001566,\u001b\u001e'!?\u0013", "cesVW");
            EnumBannerPattern.I[91 + 10 - 99 + 146] = I("!\u0001\u0015='&\u001a", "SizPE");
            EnumBannerPattern.I[23 + 136 - 55 + 45] = I("\u001a$", "wVqVK");
            EnumBannerPattern.I[22 + 29 + 74 + 25] = I("w{L", "WXlMw");
            EnumBannerPattern.I[31 + 30 - 54 + 144] = I("AOs", "boPWa");
            EnumBannerPattern.I[62 + 98 - 23 + 15] = I("ffC", "FEcKD");
            EnumBannerPattern.I[61 + 29 + 8 + 55] = I("\u0007\u0018'/%\u0019\u001c9=3\f\u0018'", "OYkiz");
            EnumBannerPattern.I[21 + 25 - 9 + 117] = I(".2:%-06$7\u001b%2:", "FSVCr");
            EnumBannerPattern.I[107 + 92 - 148 + 104] = I("\u001d%", "kMItj");
            EnumBannerPattern.I[13 + 10 + 117 + 16] = I("Wtx", "tWXUw");
            EnumBannerPattern.I[57 + 25 - 15 + 90] = I("QbT", "rAtzP");
            EnumBannerPattern.I[59 + 102 - 104 + 101] = I("m{H", "NXhbN");
            EnumBannerPattern.I[117 + 155 - 188 + 75] = I("#+#\u001f-#%=\u0010($$;\u0018>", "kjoYr");
            EnumBannerPattern.I[144 + 42 - 60 + 34] = I("\"\u00028\b9\"\f&\u0007\u001c%\r \u000f\n", "JcTnf");
            EnumBannerPattern.I[60 + 7 + 29 + 65] = I("!\"", "IJpsg");
            EnumBannerPattern.I[81 + 67 - 108 + 122] = I("vpG", "USdnI");
            EnumBannerPattern.I[124 + 8 - 60 + 91] = I("ygM", "ZDnxm");
            EnumBannerPattern.I[116 + 113 - 100 + 35] = I("Bka", "bKAon");
            EnumBannerPattern.I[159 + 163 - 293 + 136] = I("$7\u000e\f6:3\u0010\u001e /7\u000e\u0015$%$\u0010\u0005;", "lvBJi");
            EnumBannerPattern.I[57 + 98 - 87 + 98] = I("\u0005\u0012\u0006\u0004)\u001b\u0016\u0018\u0016\u001f\u000e\u0012\u0006=\u0004\u0004\u0014\u0002\u0016", "msjbv");
            EnumBannerPattern.I[25 + 63 + 25 + 54] = I(";\u0001\u0017", "MierA");
            EnumBannerPattern.I[6 + 17 + 103 + 42] = I("ews", "ETPDq");
            EnumBannerPattern.I[75 + 20 - 4 + 78] = I("Nep", "nFSUT");
            EnumBannerPattern.I[104 + 18 + 21 + 27] = I("EOP", "elstV");
            EnumBannerPattern.I[6 + 69 - 14 + 110] = I("\u0000\u0006>,\u0014\u0000\b #\u0011\u0007\t&+\u0007\u0017\n;8\u0019\u0007\u0015", "HGrjK");
            EnumBannerPattern.I[146 + 13 - 141 + 154] = I("\f(8*\u000e\f&&%+\u000b' -=;+;8%\u000b$", "dITLQ");
            EnumBannerPattern.I[117 + 61 - 46 + 41] = I("\f\u0011\u0017", "dyugh");
            EnumBannerPattern.I[160 + 111 - 146 + 49] = I("FVb", "fvBqE");
            EnumBannerPattern.I[94 + 169 - 89 + 1] = I("GM@", "dncRs");
            EnumBannerPattern.I[90 + 7 + 75 + 4] = I("RDo", "qgLjU");
            EnumBannerPattern.I[58 + 0 - 47 + 166] = I("'&\u001e/\u00117", "eiLkT");
            EnumBannerPattern.I[62 + 141 - 94 + 69] = I("\u0015;\u001a+$\u0005", "wThOA");
            EnumBannerPattern.I[110 + 139 - 169 + 99] = I("7\"", "UMCHR");
            EnumBannerPattern.I[103 + 140 - 230 + 167] = I("KSB", "hpadF");
            EnumBannerPattern.I[12 + 16 - 18 + 171] = I("DnG", "gNddA");
            EnumBannerPattern.I[174 + 44 - 85 + 49] = I("ali", "BOJJF");
            EnumBannerPattern.I[106 + 120 - 47 + 4] = I("\u001b\u0014\u001d\u0019\u0018\u0007\u0003\u0000\u0007\u0005\u001d\u0013", "XAOUA");
            EnumBannerPattern.I[40 + 8 - 21 + 157] = I("\u00044\u0013868#\u000e&+\u00023", "gAaTO");
            EnumBannerPattern.I[121 + 66 - 152 + 150] = I("23.", "QQAfU");
            EnumBannerPattern.I[141 + 110 - 125 + 60] = I("\u0014\u00034\n7\u0012\u0003", "WQqOg");
            EnumBannerPattern.I[101 + 177 - 127 + 36] = I("(\n.6\u0012.\n", "KxKSb");
            EnumBannerPattern.I[42 + 52 - 35 + 129] = I("\u0010<\r", "sNhoN");
            EnumBannerPattern.I[105 + 147 - 64 + 1] = I("\b'8%\u0003\n;-", "OuyaJ");
            EnumBannerPattern.I[64 + 172 - 160 + 114] = I("(\u001f2\u000e#*\u0003'", "OmSjJ");
            EnumBannerPattern.I[52 + 31 + 57 + 51] = I("7%\u0010", "PWqqd");
            EnumBannerPattern.I[185 + 174 - 167 + 0] = I("mQs", "NqPUm");
            EnumBannerPattern.I[17 + 0 + 15 + 161] = I("izc", "IYCWz");
            EnumBannerPattern.I[58 + 97 - 133 + 172] = I("HVF", "hufIt");
            EnumBannerPattern.I[135 + 172 - 244 + 132] = I("&'\b\u0011\u001c$;\u001d\n\u00001", "auIUU");
            EnumBannerPattern.I[14 + 64 - 71 + 189] = I("\n\u0014*>\f\b\b?\u0005\u0010\u001d", "mfKZe");
            EnumBannerPattern.I[166 + 30 - 74 + 75] = I("\u0005=\u0014", "bOaBs");
            EnumBannerPattern.I[156 + 124 - 261 + 179] = I("Lqo", "lROhP");
            EnumBannerPattern.I[129 + 177 - 213 + 106] = I("DJN", "dinvp");
            EnumBannerPattern.I[21 + 41 + 93 + 45] = I("ldz", "ODYvO");
            EnumBannerPattern.I[27 + 95 - 23 + 102] = I("\r*: \u0003\u001c", "OxscH");
            EnumBannerPattern.I[20 + 149 - 65 + 98] = I(".#\u001f\u0001\u0012?", "LQvby");
            EnumBannerPattern.I[177 + 8 - 168 + 186] = I("4\u0001\u0001", "Vshgu");
            EnumBannerPattern.I[73 + 69 + 18 + 44] = I("\u0010# :!", "Chuvm");
            EnumBannerPattern.I[85 + 160 - 58 + 18] = I("#%:\u001d?", "PNOqS");
            EnumBannerPattern.I[86 + 116 - 134 + 138] = I("\u000b:1", "xQDXH");
            EnumBannerPattern.I[16 + 38 - 38 + 191] = I("\u0004%\u001d<\u001d\u0010", "BiRkX");
            EnumBannerPattern.I[206 + 137 - 280 + 145] = I("\u0014*=#\u001c\u0000", "rFRTy");
            EnumBannerPattern.I[27 + 58 - 7 + 131] = I("\f\u001a\u0019", "jvvcj");
            EnumBannerPattern.I[164 + 147 - 244 + 143] = I(".;\u001d\u0005>$", "ctWDp");
            EnumBannerPattern.I[133 + 145 - 104 + 37] = I("\u001b!\u0005\u0019;\u0011", "vNoxU");
            EnumBannerPattern.I[30 + 59 - 86 + 209] = I("/7\u001b", "BXqVX");
        }
        
        static {
            I();
            final EnumBannerPattern[] enum$VALUES = new EnumBannerPattern[0x4C ^ 0x6B];
            enum$VALUES["".length()] = EnumBannerPattern.BASE;
            enum$VALUES[" ".length()] = EnumBannerPattern.SQUARE_BOTTOM_LEFT;
            enum$VALUES["  ".length()] = EnumBannerPattern.SQUARE_BOTTOM_RIGHT;
            enum$VALUES["   ".length()] = EnumBannerPattern.SQUARE_TOP_LEFT;
            enum$VALUES[0x7B ^ 0x7F] = EnumBannerPattern.SQUARE_TOP_RIGHT;
            enum$VALUES[0x3A ^ 0x3F] = EnumBannerPattern.STRIPE_BOTTOM;
            enum$VALUES[0x4B ^ 0x4D] = EnumBannerPattern.STRIPE_TOP;
            enum$VALUES[0x22 ^ 0x25] = EnumBannerPattern.STRIPE_LEFT;
            enum$VALUES[0x58 ^ 0x50] = EnumBannerPattern.STRIPE_RIGHT;
            enum$VALUES[0x93 ^ 0x9A] = EnumBannerPattern.STRIPE_CENTER;
            enum$VALUES[0xC8 ^ 0xC2] = EnumBannerPattern.STRIPE_MIDDLE;
            enum$VALUES[0xB4 ^ 0xBF] = EnumBannerPattern.STRIPE_DOWNRIGHT;
            enum$VALUES[0x4D ^ 0x41] = EnumBannerPattern.STRIPE_DOWNLEFT;
            enum$VALUES[0x9B ^ 0x96] = EnumBannerPattern.STRIPE_SMALL;
            enum$VALUES[0x2 ^ 0xC] = EnumBannerPattern.CROSS;
            enum$VALUES[0x5F ^ 0x50] = EnumBannerPattern.STRAIGHT_CROSS;
            enum$VALUES[0x60 ^ 0x70] = EnumBannerPattern.TRIANGLE_BOTTOM;
            enum$VALUES[0x86 ^ 0x97] = EnumBannerPattern.TRIANGLE_TOP;
            enum$VALUES[0x48 ^ 0x5A] = EnumBannerPattern.TRIANGLES_BOTTOM;
            enum$VALUES[0x1B ^ 0x8] = EnumBannerPattern.TRIANGLES_TOP;
            enum$VALUES[0x2A ^ 0x3E] = EnumBannerPattern.DIAGONAL_LEFT;
            enum$VALUES[0xD2 ^ 0xC7] = EnumBannerPattern.DIAGONAL_RIGHT;
            enum$VALUES[0x45 ^ 0x53] = EnumBannerPattern.DIAGONAL_LEFT_MIRROR;
            enum$VALUES[0x88 ^ 0x9F] = EnumBannerPattern.DIAGONAL_RIGHT_MIRROR;
            enum$VALUES[0x5A ^ 0x42] = EnumBannerPattern.CIRCLE_MIDDLE;
            enum$VALUES[0x84 ^ 0x9D] = EnumBannerPattern.RHOMBUS_MIDDLE;
            enum$VALUES[0x4E ^ 0x54] = EnumBannerPattern.HALF_VERTICAL;
            enum$VALUES[0x11 ^ 0xA] = EnumBannerPattern.HALF_HORIZONTAL;
            enum$VALUES[0x8B ^ 0x97] = EnumBannerPattern.HALF_VERTICAL_MIRROR;
            enum$VALUES[0x41 ^ 0x5C] = EnumBannerPattern.HALF_HORIZONTAL_MIRROR;
            enum$VALUES[0x4D ^ 0x53] = EnumBannerPattern.BORDER;
            enum$VALUES[0x7F ^ 0x60] = EnumBannerPattern.CURLY_BORDER;
            enum$VALUES[0xB8 ^ 0x98] = EnumBannerPattern.CREEPER;
            enum$VALUES[0x8B ^ 0xAA] = EnumBannerPattern.GRADIENT;
            enum$VALUES[0xB1 ^ 0x93] = EnumBannerPattern.GRADIENT_UP;
            enum$VALUES[0x71 ^ 0x52] = EnumBannerPattern.BRICKS;
            enum$VALUES[0x48 ^ 0x6C] = EnumBannerPattern.SKULL;
            enum$VALUES[0x1C ^ 0x39] = EnumBannerPattern.FLOWER;
            enum$VALUES[0x5A ^ 0x7C] = EnumBannerPattern.MOJANG;
            ENUM$VALUES = enum$VALUES;
        }
        
        public String getPatternName() {
            return this.patternName;
        }
        
        private EnumBannerPattern(final String s, final int n, final String patternName, final String patternID) {
            this.craftingLayers = new String["   ".length()];
            this.patternName = patternName;
            this.patternID = patternID;
        }
        
        public boolean hasCraftingStack() {
            if (this.patternCraftingStack != null) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public static EnumBannerPattern getPatternByID(final String s) {
            final EnumBannerPattern[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (i < length) {
                final EnumBannerPattern enumBannerPattern = values[i];
                if (enumBannerPattern.patternID.equals(s)) {
                    return enumBannerPattern;
                }
                ++i;
            }
            return null;
        }
        
        private EnumBannerPattern(final String s, final int n, final String s2, final String s3, final String s4, final String s5, final String s6) {
            this(s, n, s2, s3);
            this.craftingLayers["".length()] = s4;
            this.craftingLayers[" ".length()] = s5;
            this.craftingLayers["  ".length()] = s6;
        }
        
        public String getPatternID() {
            return this.patternID;
        }
        
        private EnumBannerPattern(final String s, final int n, final String s2, final String s3, final ItemStack patternCraftingStack) {
            this(s, n, s2, s3);
            this.patternCraftingStack = patternCraftingStack;
        }
    }
}
