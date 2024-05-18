package net.minecraft.client.audio;

import java.util.*;
import com.google.common.collect.*;

public enum SoundCategory
{
    WEATHER(SoundCategory.I[0x5A ^ 0x5C], "   ".length(), SoundCategory.I[0x45 ^ 0x42], "   ".length()), 
    MOBS(SoundCategory.I[0x28 ^ 0x22], 0x7D ^ 0x78, SoundCategory.I[0x2F ^ 0x24], 0xC ^ 0x9);
    
    private final int categoryId;
    private static final Map<Integer, SoundCategory> ID_CATEGORY_MAP;
    private static final SoundCategory[] ENUM$VALUES;
    
    BLOCKS(SoundCategory.I[0x60 ^ 0x68], 0x7B ^ 0x7F, SoundCategory.I[0xB6 ^ 0xBF], 0x7D ^ 0x79), 
    AMBIENT(SoundCategory.I[0x25 ^ 0x35], 0xCA ^ 0xC2, SoundCategory.I[0x6A ^ 0x7B], 0xAF ^ 0xA7), 
    RECORDS(SoundCategory.I[0x19 ^ 0x1D], "  ".length(), SoundCategory.I[0x2A ^ 0x2F], "  ".length()), 
    MUSIC(SoundCategory.I["  ".length()], " ".length(), SoundCategory.I["   ".length()], " ".length());
    
    private static final String[] I;
    
    ANIMALS(SoundCategory.I[0xB2 ^ 0xBE], 0xB2 ^ 0xB4, SoundCategory.I[0x33 ^ 0x3E], 0x9A ^ 0x9C);
    
    private final String categoryName;
    private static final Map<String, SoundCategory> NAME_CATEGORY_MAP;
    
    PLAYERS(SoundCategory.I[0x14 ^ 0x1A], 0x42 ^ 0x45, SoundCategory.I[0xCD ^ 0xC2], 0x5C ^ 0x5B), 
    MASTER(SoundCategory.I["".length()], "".length(), SoundCategory.I[" ".length()], "".length());
    
    public int getCategoryId() {
        return this.categoryId;
    }
    
    static {
        I();
        final SoundCategory[] enum$VALUES = new SoundCategory[0x69 ^ 0x60];
        enum$VALUES["".length()] = SoundCategory.MASTER;
        enum$VALUES[" ".length()] = SoundCategory.MUSIC;
        enum$VALUES["  ".length()] = SoundCategory.RECORDS;
        enum$VALUES["   ".length()] = SoundCategory.WEATHER;
        enum$VALUES[0x1B ^ 0x1F] = SoundCategory.BLOCKS;
        enum$VALUES[0x57 ^ 0x52] = SoundCategory.MOBS;
        enum$VALUES[0x8D ^ 0x8B] = SoundCategory.ANIMALS;
        enum$VALUES[0xF ^ 0x8] = SoundCategory.PLAYERS;
        enum$VALUES[0x6 ^ 0xE] = SoundCategory.AMBIENT;
        ENUM$VALUES = enum$VALUES;
        NAME_CATEGORY_MAP = Maps.newHashMap();
        ID_CATEGORY_MAP = Maps.newHashMap();
        final SoundCategory[] values;
        final int length = (values = values()).length;
        int i = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (i < length) {
            final SoundCategory soundCategory = values[i];
            if (SoundCategory.NAME_CATEGORY_MAP.containsKey(soundCategory.getCategoryName()) || SoundCategory.ID_CATEGORY_MAP.containsKey(soundCategory.getCategoryId())) {
                throw new Error(SoundCategory.I[0x0 ^ 0x12] + soundCategory);
            }
            SoundCategory.NAME_CATEGORY_MAP.put(soundCategory.getCategoryName(), soundCategory);
            SoundCategory.ID_CATEGORY_MAP.put(soundCategory.getCategoryId(), soundCategory);
            ++i;
        }
    }
    
    private SoundCategory(final String s, final int n, final String categoryName, final int categoryId) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }
    
    public static SoundCategory getCategory(final String s) {
        return SoundCategory.NAME_CATEGORY_MAP.get(s);
    }
    
    public String getCategoryName() {
        return this.categoryName;
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
            if (2 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x25 ^ 0x36])["".length()] = I("7/1!0(", "znbuu");
        SoundCategory.I[" ".length()] = I("\u00039\u0005\u0011)\u001c", "nXveL");
        SoundCategory.I["  ".length()] = I("\u001b!2\f\u0010", "VtaES");
        SoundCategory.I["   ".length()] = I(", ;\u0002/", "AUHkL");
        SoundCategory.I[0x11 ^ 0x15] = I("\u0006,\u0015\u0006\u000b\u0010:", "TiVIY");
        SoundCategory.I[0x29 ^ 0x2C] = I("\b\u0002\u000f\u001b:\u001e", "zgltH");
        SoundCategory.I[0x68 ^ 0x6E] = I(" \u0013#\u0005\f2\u0004", "wVbQD");
        SoundCategory.I[0x12 ^ 0x15] = I("2\n\u0015=8 \u001d", "EotIP");
        SoundCategory.I[0x93 ^ 0x9B] = I("\u0016-!3 \u0007", "Tanpk");
        SoundCategory.I[0x8E ^ 0x87] = I("+\u00198\",", "IuWAG");
        SoundCategory.I[0x72 ^ 0x78] = I(">\u0001\b\u0003", "sNJPh");
        SoundCategory.I[0x4E ^ 0x45] = I("28\u0004<\u001862", "ZWwHq");
        SoundCategory.I[0x36 ^ 0x3A] = I("\u0015\u001e\u001a\n\"\u0018\u0003", "TPSGc");
        SoundCategory.I[0x46 ^ 0x4B] = I("\u001e<\u0013\u001f\u001e\u00115", "pYfkl");
        SoundCategory.I[0xA0 ^ 0xAE] = I(" =.8\u000b\"\"", "pqoaN");
        SoundCategory.I[0x89 ^ 0x86] = I("\u0006\u000156\"\u0004", "vmTOG");
        SoundCategory.I[0x74 ^ 0x64] = I("\u000e'\u0001\u0003\b\u0001>", "OjCJM");
        SoundCategory.I[0x45 ^ 0x54] = I("0\u001c/(\u0011?\u0005", "QqMAt");
        SoundCategory.I[0x9 ^ 0x1B] = I("*\"\u0010+\u0019I'\u001fx\"\u0006;\u001f<Q*/\u0005=\u0016\u0006<\bx8-nWx?\b#\u0014x\u0001\u0006!\u001d+PI\r\u00106\u001f\u0006:Q1\u001f\u001a+\u0003,Q", "iNqXq");
    }
}
