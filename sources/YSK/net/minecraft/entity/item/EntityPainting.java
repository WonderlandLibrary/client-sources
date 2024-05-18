package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class EntityPainting extends EntityHanging
{
    private static final String[] I;
    public EnumArt art;
    
    @Override
    public int getWidthPixels() {
        return this.art.sizeX;
    }
    
    public EntityPainting(final World world) {
        super(world);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        final String string = nbtTagCompound.getString(EntityPainting.I[" ".length()]);
        final EnumArt[] values;
        final int length = (values = EnumArt.values()).length;
        int i = "".length();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (i < length) {
            final EnumArt art = values[i];
            if (art.title.equals(string)) {
                this.art = art;
            }
            ++i;
        }
        if (this.art == null) {
            this.art = EnumArt.KEBAB;
        }
        super.readEntityFromNBT(nbtTagCompound);
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getHeightPixels() {
        return this.art.sizeY;
    }
    
    @Override
    public void setPositionAndRotation2(final double n, final double n2, final double n3, final float n4, final float n5, final int n6, final boolean b) {
        final BlockPos add = this.hangingPosition.add(n - this.posX, n2 - this.posY, n3 - this.posZ);
        this.setPosition(add.getX(), add.getY(), add.getZ());
    }
    
    static {
        I();
    }
    
    @Override
    public void setLocationAndAngles(final double n, final double n2, final double n3, final float n4, final float n5) {
        final BlockPos add = this.hangingPosition.add(n - this.posX, n2 - this.posY, n3 - this.posZ);
        this.setPosition(add.getX(), add.getY(), add.getZ());
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setString(EntityPainting.I["".length()], this.art.title);
        super.writeEntityToNBT(nbtTagCompound);
    }
    
    @Override
    public void onBroken(final Entity entity) {
        if (this.worldObj.getGameRules().getBoolean(EntityPainting.I["  ".length()])) {
            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
                return;
            }
            this.entityDropItem(new ItemStack(Items.painting), 0.0f);
        }
    }
    
    public EntityPainting(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        super(world, blockPos);
        final ArrayList arrayList = Lists.newArrayList();
        final EnumArt[] values;
        final int length = (values = EnumArt.values()).length;
        int i = "".length();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (i < length) {
            final EnumArt art = values[i];
            this.art = art;
            this.updateFacingWithBoundingBox(enumFacing);
            if (this.onValidSurface()) {
                arrayList.add(art);
            }
            ++i;
        }
        if (!arrayList.isEmpty()) {
            this.art = (EnumArt)arrayList.get(this.rand.nextInt(arrayList.size()));
        }
        this.updateFacingWithBoundingBox(enumFacing);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u001b'=9=3", "VHIPK");
        EntityPainting.I[" ".length()] = I("* 3\"\u0015\u0002", "gOGKc");
        EntityPainting.I["  ".length()] = I("/\"66\u001f\"9\n\u001c\u0019$=\u0000", "KMsXk");
    }
    
    public EntityPainting(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final String s) {
        this(world, blockPos, enumFacing);
        final EnumArt[] values;
        final int length = (values = EnumArt.values()).length;
        int i = "".length();
        "".length();
        if (4 < 2) {
            throw null;
        }
        while (i < length) {
            final EnumArt art = values[i];
            if (art.title.equals(s)) {
                this.art = art;
                "".length();
                if (2 < -1) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
            }
        }
        this.updateFacingWithBoundingBox(enumFacing);
    }
    
    public enum EnumArt
    {
        BURNING_SKULL(EnumArt.I[0x9B ^ 0xB5], 0x98 ^ 0x8F, EnumArt.I[0x1 ^ 0x2E], 0x33 ^ 0x73, 0xA ^ 0x4A, 33 + 66 - 16 + 45, 115 + 101 - 69 + 45), 
        DONKEY_KONG(EnumArt.I[0x83 ^ 0xB1], 0x5B ^ 0x42, EnumArt.I[0x74 ^ 0x47], 0xF4 ^ 0xB4, 0x3D ^ 0xD, 116 + 119 - 192 + 149, 0xCA ^ 0xBA), 
        KEBAB(EnumArt.I["".length()], "".length(), EnumArt.I[" ".length()], 0x1A ^ 0xA, 0x94 ^ 0x84, "".length(), "".length());
        
        private static final EnumArt[] ENUM$VALUES;
        
        STAGE(EnumArt.I[0x92 ^ 0xB2], 0x65 ^ 0x75, EnumArt.I[0xE1 ^ 0xC0], 0x9E ^ 0xBE, 0x54 ^ 0x74, 0xC0 ^ 0x80, 53 + 26 + 22 + 27), 
        SUNSET(EnumArt.I[0xA3 ^ 0xB7], 0xAA ^ 0xA0, EnumArt.I[0x6A ^ 0x7F], 0x1F ^ 0x3F, 0x33 ^ 0x23, 0xCD ^ 0xAD, 0xA9 ^ 0x89), 
        SKULL_AND_ROSES(EnumArt.I[0x36 ^ 0x12], 0xD6 ^ 0xC4, EnumArt.I[0x32 ^ 0x17], 0x74 ^ 0x54, 0xB2 ^ 0x92, 119 + 27 - 22 + 4, 22 + 15 + 20 + 71), 
        COURBET(EnumArt.I[0x50 ^ 0x40], 0x92 ^ 0x9A, EnumArt.I[0x66 ^ 0x77], 0x46 ^ 0x66, 0x1E ^ 0xE, 0x88 ^ 0xA8, 0x65 ^ 0x45);
        
        public final int offsetX;
        
        SKELETON(EnumArt.I[0x54 ^ 0x64], 0x20 ^ 0x38, EnumArt.I[0x63 ^ 0x52], 0xC1 ^ 0x81, 0x3A ^ 0xA, 70 + 90 - 84 + 116, 0x26 ^ 0x66), 
        POOL(EnumArt.I[0x1C ^ 0x12], 0xA6 ^ 0xA1, EnumArt.I[0x71 ^ 0x7E], 0xA7 ^ 0x87, 0x67 ^ 0x77, "".length(), 0x2B ^ 0xB), 
        WANDERER(EnumArt.I[0x21 ^ 0x39], 0x88 ^ 0x84, EnumArt.I[0x92 ^ 0x8B], 0x3A ^ 0x2A, 0x29 ^ 0x9, "".length(), 0xE2 ^ 0xA2);
        
        public final int sizeY;
        
        VOID(EnumArt.I[0x61 ^ 0x43], 0xF ^ 0x1E, EnumArt.I[0x6F ^ 0x4C], 0x50 ^ 0x70, 0x13 ^ 0x33, 0x79 ^ 0x19, 68 + 122 - 88 + 26), 
        ALBAN(EnumArt.I[0x79 ^ 0x7D], "  ".length(), EnumArt.I[0xB ^ 0xE], 0x77 ^ 0x67, 0x9B ^ 0x8B, 0x1C ^ 0x3C, "".length()), 
        PIGSCENE(EnumArt.I[0x8C ^ 0xA0], 0x7D ^ 0x6B, EnumArt.I[0x44 ^ 0x69], 0x16 ^ 0x56, 0xD9 ^ 0x99, 0xE1 ^ 0xA1, 115 + 168 - 146 + 55);
        
        private static final String[] I;
        public final String title;
        
        WITHER(EnumArt.I[0x20 ^ 0x6], 0xBA ^ 0xA9, EnumArt.I[0x2B ^ 0xC], 0x46 ^ 0x66, 0xBB ^ 0x9B, 23 + 107 + 5 + 25, 115 + 60 - 69 + 22), 
        MATCH(EnumArt.I[0xBB ^ 0xA7], 0x35 ^ 0x3B, EnumArt.I[0x22 ^ 0x3F], 0x41 ^ 0x61, 0x39 ^ 0x19, "".length(), 118 + 39 - 118 + 89), 
        WASTELAND(EnumArt.I[0x86 ^ 0x8A], 0x1D ^ 0x1B, EnumArt.I[0x8E ^ 0x83], 0x7F ^ 0x6F, 0x6B ^ 0x7B, 0x62 ^ 0x2, "".length()), 
        BUST(EnumArt.I[0x69 ^ 0x77], 0xB0 ^ 0xBF, EnumArt.I[0x2E ^ 0x31], 0x7F ^ 0x5F, 0x1D ^ 0x3D, 0x40 ^ 0x60, 30 + 51 - 23 + 70);
        
        public final int sizeX;
        public static final int field_180001_A;
        
        POINTER(EnumArt.I[0x60 ^ 0x4A], 0x3F ^ 0x2A, EnumArt.I[0x33 ^ 0x18], 0xE5 ^ 0xA5, 0x4 ^ 0x44, "".length(), 101 + 16 + 9 + 66), 
        PLANT(EnumArt.I[0xBD ^ 0xB7], 0x70 ^ 0x75, EnumArt.I[0x14 ^ 0x1F], 0x4F ^ 0x5F, 0x3B ^ 0x2B, 0x7E ^ 0x2E, "".length()), 
        AZTEC_2(EnumArt.I[0x95 ^ 0x93], "   ".length(), EnumArt.I[0x38 ^ 0x3F], 0x10 ^ 0x0, 0x7 ^ 0x17, 0x67 ^ 0x57, "".length()), 
        GRAHAM(EnumArt.I[0x58 ^ 0x42], 0x5F ^ 0x52, EnumArt.I[0xAA ^ 0xB1], 0x0 ^ 0x10, 0x8D ^ 0xAD, 0x8E ^ 0x9E, 0x85 ^ 0xC5);
        
        public final int offsetY;
        
        CREEBET(EnumArt.I[0xD2 ^ 0xC4], 0xA2 ^ 0xA9, EnumArt.I[0x55 ^ 0x42], 0xB9 ^ 0x99, 0x4B ^ 0x5B, 79 + 48 - 117 + 118, 0xE7 ^ 0xC7), 
        BOMB(EnumArt.I[0xA5 ^ 0xAD], 0xC0 ^ 0xC4, EnumArt.I[0x9E ^ 0x97], 0x23 ^ 0x33, 0xA6 ^ 0xB6, 0xDD ^ 0x9D, "".length()), 
        SEA(EnumArt.I[0x1F ^ 0xD], 0x4 ^ 0xD, EnumArt.I[0x8B ^ 0x98], 0xA0 ^ 0x80, 0x0 ^ 0x10, 0xF ^ 0x4F, 0xA9 ^ 0x89), 
        AZTEC(EnumArt.I["  ".length()], " ".length(), EnumArt.I["   ".length()], 0x3F ^ 0x2F, 0x7F ^ 0x6F, 0xB8 ^ 0xA8, "".length()), 
        FIGHTERS(EnumArt.I[0x82 ^ 0xAA], 0xA1 ^ 0xB5, EnumArt.I[0xA1 ^ 0x88], 0x6D ^ 0x2D, 0xAC ^ 0x8C, "".length(), 0xEB ^ 0x8B);
        
        private static void I() {
            (I = new String[0x35 ^ 0x0])["".length()] = I("\u0019\u0016!\u0003)", "RScBk");
            EnumArt.I[" ".length()] = I("&*\u0012;!", "mOpZC");
            EnumArt.I["  ".length()] = I(";\u001b\u0001\u0016\u0004", "zAUSG");
            EnumArt.I["   ".length()] = I("\u0005555$", "DOAPG");
            EnumArt.I[0x8C ^ 0x88] = I("\t6;\b\u0003", "HzyIM");
            EnumArt.I[0xD ^ 0x8] = I("0 *\u0013<", "qLHrR");
            EnumArt.I[0x6C ^ 0x6A] = I("\t6\u001e-&\u0017^", "HlJhe");
            EnumArt.I[0xB8 ^ 0xBF] = I(".6?\u0014\u0002]", "oLKqa");
            EnumArt.I[0x54 ^ 0x5C] = I("\u0014*\u001f6", "VeRti");
            EnumArt.I[0xA1 ^ 0xA8] = I("*&\u0003\u0017", "hInuT");
            EnumArt.I[0xB0 ^ 0xBA] = I("\u001f='\u000b\u0011", "OqfEE");
            EnumArt.I[0x5E ^ 0x55] = I(">\u001a\u0016!-", "nvwOY");
            EnumArt.I[0xCE ^ 0xC2] = I("\u0003*\u001e#\u0017\u0018*\u00033", "TkMwR");
            EnumArt.I[0x61 ^ 0x6C] = I(";\u000f\u0001%'\u0000\u000f\u001c5", "lnrQB");
            EnumArt.I[0xCC ^ 0xC2] = I("\u001c\n\u000e\u001c", "LEAPP");
            EnumArt.I[0x5B ^ 0x54] = I(";=\u001c\u0016", "kRszX");
            EnumArt.I[0x24 ^ 0x34] = I("\u0004\n\u0005*\u000e\u0002\u0011", "GEPxL");
            EnumArt.I[0xB6 ^ 0xA7] = I(":\f,;\u000f\u001c\u0017", "ycYIm");
            EnumArt.I[0x44 ^ 0x56] = I("<\u0017-", "oRlFb");
            EnumArt.I[0x6D ^ 0x7E] = I("\"\"\u0018", "qGyJb");
            EnumArt.I[0x9C ^ 0x88] = I(" '<\u0019\u0001'", "srrJD");
            EnumArt.I[0x4B ^ 0x5E] = I("\";\t#-\u0005", "qNgPH");
            EnumArt.I[0x90 ^ 0x86] = I("\u0007\u0013\u000e\u001f\u0004\u0001\u0015", "DAKZF");
            EnumArt.I[0x58 ^ 0x4F] = I("0;.\u0000(\u0016=", "sIKeJ");
            EnumArt.I[0x18 ^ 0x0] = I("\u0001;-\u001c\u000f\u0004?1", "VzcXJ");
            EnumArt.I[0xA9 ^ 0xB0] = I("\u001f'\u0018#!:#\u0004", "HFvGD");
            EnumArt.I[0x3B ^ 0x21] = I("\u0011!#\u0019\u0004\u001b", "VsbQE");
            EnumArt.I[0xB0 ^ 0xAB] = I("#\u001e\u0018\u0018-\t", "dlypL");
            EnumArt.I[0x2D ^ 0x31] = I("5\u0019;\t9", "xXoJq");
            EnumArt.I[0xB6 ^ 0xAB] = I(">\u00116-\u0005", "spBNm");
            EnumArt.I[0x86 ^ 0x98] = I("/%\u001e\u0001", "mpMUz");
            EnumArt.I[0x27 ^ 0x38] = I(" \"\u0004\u0015", "bWwaS");
            EnumArt.I[0x10 ^ 0x30] = I(" \u001a\u0007\u0003*", "sNFDo");
            EnumArt.I[0xBA ^ 0x9B] = I("\u0011:(\u00163", "BNIqV");
            EnumArt.I[0xBD ^ 0x9F] = I("\u001b\u001f+\u000b", "MPbOH");
            EnumArt.I[0x44 ^ 0x67] = I(",?\u001d ", "zPtDd");
            EnumArt.I[0xB4 ^ 0x90] = I("\u00068\u0004)8\n2\u001f!+\u0007<\u0002 '", "UsQet");
            EnumArt.I[0x9C ^ 0xB9] = I("\u001e\u0013\u0006\"\u001a\f\u0016\u0017\u001c\u0019>\u001d\u0000", "MxsNv");
            EnumArt.I[0x70 ^ 0x56] = I("\u0007\u0005;\u0004-\u0002", "PLoLh");
            EnumArt.I[0x8A ^ 0xAD] = I("#\u0019.\u0018\u0017\u0006", "tpZpr");
            EnumArt.I[0x41 ^ 0x69] = I("\u0001*/ \u0006\u00021;", "GchhR");
            EnumArt.I[0x2E ^ 0x7] = I("':\u001f)\u0005\u0004!\u000b", "aSxAq");
            EnumArt.I[0x14 ^ 0x3E] = I("\u001f$#\n;\n9", "OkjDo");
            EnumArt.I[0x13 ^ 0x38] = I("\u0001\u001e$,\u001c4\u0003", "QqMBh");
            EnumArt.I[0x56 ^ 0x7A] = I("=%\u0010\u0017&(\"\u0012", "mlWDe");
            EnumArt.I[0xA1 ^ 0x8C] = I("\u0000\u000e\u0003*65\t\u0001", "PgdYU");
            EnumArt.I[0x38 ^ 0x16] = I("3\u0012\u0005\u001d\u001f?\u0000\b\u0000\u001d$\u000b\u001b", "qGWSV");
            EnumArt.I[0x7F ^ 0x50] = I(";\u001c\u00179%\u0017\u000e6<9\u0015\u0005", "yieWL");
            EnumArt.I[0xA7 ^ 0x97] = I("'\u001d\u0012$\u0011 \u0019\u0019", "tVWhT");
            EnumArt.I[0x91 ^ 0xA0] = I("5#1\u0000,\u0012':", "fHTlI");
            EnumArt.I[0x7 ^ 0x35] = I(">>\u0018\u0012\u0010#.\u001d\u0016\u001b=", "zqVYU");
            EnumArt.I[0x17 ^ 0x24] = I("\u001e#9\r2#\u00078\b0", "ZLWfW");
            EnumArt.I[0x12 ^ 0x26] = I(">\n9 >,\u000f(\u001e=\u001e\u0004?", "maLLR");
        }
        
        static {
            I();
            final EnumArt[] enum$VALUES = new EnumArt[0x5D ^ 0x47];
            enum$VALUES["".length()] = EnumArt.KEBAB;
            enum$VALUES[" ".length()] = EnumArt.AZTEC;
            enum$VALUES["  ".length()] = EnumArt.ALBAN;
            enum$VALUES["   ".length()] = EnumArt.AZTEC_2;
            enum$VALUES[0x67 ^ 0x63] = EnumArt.BOMB;
            enum$VALUES[0x4B ^ 0x4E] = EnumArt.PLANT;
            enum$VALUES[0x60 ^ 0x66] = EnumArt.WASTELAND;
            enum$VALUES[0x48 ^ 0x4F] = EnumArt.POOL;
            enum$VALUES[0x7B ^ 0x73] = EnumArt.COURBET;
            enum$VALUES[0xBE ^ 0xB7] = EnumArt.SEA;
            enum$VALUES[0x96 ^ 0x9C] = EnumArt.SUNSET;
            enum$VALUES[0x5A ^ 0x51] = EnumArt.CREEBET;
            enum$VALUES[0x78 ^ 0x74] = EnumArt.WANDERER;
            enum$VALUES[0x2B ^ 0x26] = EnumArt.GRAHAM;
            enum$VALUES[0x1C ^ 0x12] = EnumArt.MATCH;
            enum$VALUES[0x41 ^ 0x4E] = EnumArt.BUST;
            enum$VALUES[0x30 ^ 0x20] = EnumArt.STAGE;
            enum$VALUES[0x4 ^ 0x15] = EnumArt.VOID;
            enum$VALUES[0x4A ^ 0x58] = EnumArt.SKULL_AND_ROSES;
            enum$VALUES[0x32 ^ 0x21] = EnumArt.WITHER;
            enum$VALUES[0x9E ^ 0x8A] = EnumArt.FIGHTERS;
            enum$VALUES[0x63 ^ 0x76] = EnumArt.POINTER;
            enum$VALUES[0xB2 ^ 0xA4] = EnumArt.PIGSCENE;
            enum$VALUES[0x81 ^ 0x96] = EnumArt.BURNING_SKULL;
            enum$VALUES[0x4F ^ 0x57] = EnumArt.SKELETON;
            enum$VALUES[0x60 ^ 0x79] = EnumArt.DONKEY_KONG;
            ENUM$VALUES = enum$VALUES;
            field_180001_A = EnumArt.I[0x37 ^ 0x3].length();
        }
        
        private EnumArt(final String s, final int n, final String title, final int sizeX, final int sizeY, final int offsetX, final int offsetY) {
            this.title = title;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
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
                if (0 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
