package net.minecraft.util;

import com.google.common.collect.*;
import java.util.*;

public enum EnumParticleTypes
{
    LAVA(EnumParticleTypes.I[0x2E ^ 0x18], 0x76 ^ 0x6D, EnumParticleTypes.I[0x8 ^ 0x3F], 0x0 ^ 0x1B, (boolean)("".length() != 0)), 
    SPELL_MOB(EnumParticleTypes.I[0x2 ^ 0x1C], 0x48 ^ 0x47, EnumParticleTypes.I[0x9E ^ 0x81], 0x6C ^ 0x63, (boolean)("".length() != 0)), 
    SUSPENDED_DEPTH(EnumParticleTypes.I[0xB3 ^ 0xA3], 0x21 ^ 0x29, EnumParticleTypes.I[0x7D ^ 0x6C], 0x43 ^ 0x4B, (boolean)("".length() != 0)), 
    BLOCK_CRACK(EnumParticleTypes.I[0x35 ^ 0x7F], 0x1B ^ 0x3E, EnumParticleTypes.I[0x4F ^ 0x4], 0x71 ^ 0x54, (boolean)("".length() != 0), " ".length()), 
    VILLAGER_HAPPY(EnumParticleTypes.I[0x38 ^ 0x12], 0x54 ^ 0x41, EnumParticleTypes.I[0x57 ^ 0x7C], 0x89 ^ 0x9C, (boolean)("".length() != 0)), 
    DRIP_LAVA(EnumParticleTypes.I[0x74 ^ 0x52], 0xBA ^ 0xA9, EnumParticleTypes.I[0x39 ^ 0x1E], 0x9F ^ 0x8C, (boolean)("".length() != 0)), 
    SNOWBALL(EnumParticleTypes.I[0x30 ^ 0xE], 0x47 ^ 0x58, EnumParticleTypes.I[0x18 ^ 0x27], 0x65 ^ 0x7A, (boolean)("".length() != 0)), 
    DRIP_WATER(EnumParticleTypes.I[0xA9 ^ 0x8D], 0x7 ^ 0x15, EnumParticleTypes.I[0x13 ^ 0x36], 0x96 ^ 0x84, (boolean)("".length() != 0)), 
    SPELL(EnumParticleTypes.I[0x9C ^ 0x86], 0x8C ^ 0x81, EnumParticleTypes.I[0x2C ^ 0x37], 0xAC ^ 0xA1, (boolean)("".length() != 0)), 
    MOB_APPEARANCE(EnumParticleTypes.I[0x3 ^ 0x51], 0x8E ^ 0xA7, EnumParticleTypes.I[0x16 ^ 0x45], 0x30 ^ 0x19, (boolean)(" ".length() != 0)), 
    NOTE(EnumParticleTypes.I[0x9C ^ 0xB2], 0x7 ^ 0x10, EnumParticleTypes.I[0x19 ^ 0x36], 0x94 ^ 0x83, (boolean)("".length() != 0)), 
    VILLAGER_ANGRY(EnumParticleTypes.I[0xA5 ^ 0x8D], 0x56 ^ 0x42, EnumParticleTypes.I[0x8D ^ 0xA4], 0x31 ^ 0x25, (boolean)("".length() != 0)), 
    REDSTONE(EnumParticleTypes.I[0x16 ^ 0x2A], 0xAC ^ 0xB2, EnumParticleTypes.I[0x47 ^ 0x7A], 0x76 ^ 0x68, (boolean)("".length() != 0)), 
    EXPLOSION_HUGE(EnumParticleTypes.I[0x5D ^ 0x59], "  ".length(), EnumParticleTypes.I[0x3E ^ 0x3B], "  ".length(), (boolean)(" ".length() != 0)), 
    WATER_DROP(EnumParticleTypes.I[0x15 ^ 0x5B], 0x34 ^ 0x13, EnumParticleTypes.I[0xD0 ^ 0x9F], 0x59 ^ 0x7E, (boolean)("".length() != 0)), 
    PORTAL(EnumParticleTypes.I[0x61 ^ 0x51], 0x44 ^ 0x5C, EnumParticleTypes.I[0xF4 ^ 0xC5], 0x84 ^ 0x9C, (boolean)("".length() != 0)), 
    SLIME(EnumParticleTypes.I[0xD5 ^ 0x97], 0x88 ^ 0xA9, EnumParticleTypes.I[0x44 ^ 0x7], 0x55 ^ 0x74, (boolean)("".length() != 0));
    
    private final int particleID;
    
    WATER_BUBBLE(EnumParticleTypes.I[0x6A ^ 0x62], 0x99 ^ 0x9D, EnumParticleTypes.I[0x2E ^ 0x27], 0xAB ^ 0xAF, (boolean)("".length() != 0)), 
    CLOUD(EnumParticleTypes.I[0xA5 ^ 0x9F], 0xAB ^ 0xB6, EnumParticleTypes.I[0x9C ^ 0xA7], 0x63 ^ 0x7E, (boolean)("".length() != 0)), 
    SNOW_SHOVEL(EnumParticleTypes.I[0x37 ^ 0x77], 0x5C ^ 0x7C, EnumParticleTypes.I[0x51 ^ 0x10], 0x20 ^ 0x0, (boolean)("".length() != 0)), 
    CRIT_MAGIC(EnumParticleTypes.I[0x4B ^ 0x5F], 0xE ^ 0x4, EnumParticleTypes.I[0x7B ^ 0x6E], 0x2 ^ 0x8, (boolean)("".length() != 0)), 
    SMOKE_LARGE(EnumParticleTypes.I[0x71 ^ 0x69], 0x4D ^ 0x41, EnumParticleTypes.I[0x7B ^ 0x62], 0x21 ^ 0x2D, (boolean)("".length() != 0)), 
    ITEM_TAKE(EnumParticleTypes.I[0xCD ^ 0x9D], 0x6C ^ 0x44, EnumParticleTypes.I[0x54 ^ 0x5], 0x68 ^ 0x40, (boolean)("".length() != 0)), 
    WATER_SPLASH(EnumParticleTypes.I[0x4F ^ 0x45], 0x3F ^ 0x3A, EnumParticleTypes.I[0x26 ^ 0x2D], 0xC4 ^ 0xC1, (boolean)("".length() != 0)), 
    HEART(EnumParticleTypes.I[0x6D ^ 0x29], 0x1F ^ 0x3D, EnumParticleTypes.I[0xD0 ^ 0x95], 0x23 ^ 0x1, (boolean)("".length() != 0)), 
    SPELL_INSTANT(EnumParticleTypes.I[0xE ^ 0x12], 0x10 ^ 0x1E, EnumParticleTypes.I[0xB4 ^ 0xA9], 0x6A ^ 0x64, (boolean)("".length() != 0)), 
    FOOTSTEP(EnumParticleTypes.I[0x3 ^ 0x3B], 0x81 ^ 0x9D, EnumParticleTypes.I[0xA3 ^ 0x9A], 0x38 ^ 0x24, (boolean)("".length() != 0)), 
    ENCHANTMENT_TABLE(EnumParticleTypes.I[0xA1 ^ 0x93], 0x37 ^ 0x2E, EnumParticleTypes.I[0x7D ^ 0x4E], 0x54 ^ 0x4D, (boolean)("".length() != 0)), 
    BLOCK_DUST(EnumParticleTypes.I[0xC8 ^ 0x84], 0x69 ^ 0x4F, EnumParticleTypes.I[0xDE ^ 0x93], 0x78 ^ 0x5E, (boolean)("".length() != 0), " ".length()), 
    SPELL_WITCH(EnumParticleTypes.I[0x33 ^ 0x11], 0x4D ^ 0x5C, EnumParticleTypes.I[0x4F ^ 0x6C], 0x76 ^ 0x67, (boolean)("".length() != 0));
    
    private static final String[] I;
    
    EXPLOSION_NORMAL(EnumParticleTypes.I["".length()], "".length(), EnumParticleTypes.I[" ".length()], "".length(), (boolean)(" ".length() != 0));
    
    private static final String[] PARTICLE_NAMES;
    
    FLAME(EnumParticleTypes.I[0x4A ^ 0x7E], 0x5D ^ 0x47, EnumParticleTypes.I[0xB1 ^ 0x84], 0x2C ^ 0x36, (boolean)("".length() != 0)), 
    ITEM_CRACK(EnumParticleTypes.I[0x37 ^ 0x7F], 0x68 ^ 0x4C, EnumParticleTypes.I[0x6 ^ 0x4F], 0xA1 ^ 0x85, (boolean)("".length() != 0), "  ".length());
    
    private static final EnumParticleTypes[] ENUM$VALUES;
    
    WATER_WAKE(EnumParticleTypes.I[0x47 ^ 0x4B], 0x79 ^ 0x7F, EnumParticleTypes.I[0x4C ^ 0x41], 0xBB ^ 0xBD, (boolean)("".length() != 0)), 
    CRIT(EnumParticleTypes.I[0x5F ^ 0x4D], 0x84 ^ 0x8D, EnumParticleTypes.I[0xB4 ^ 0xA7], 0x5F ^ 0x56, (boolean)("".length() != 0));
    
    private static final Map<Integer, EnumParticleTypes> PARTICLES;
    
    SMOKE_NORMAL(EnumParticleTypes.I[0x70 ^ 0x66], 0x91 ^ 0x9A, EnumParticleTypes.I[0x78 ^ 0x6F], 0x80 ^ 0x8B, (boolean)("".length() != 0)), 
    TOWN_AURA(EnumParticleTypes.I[0xAF ^ 0x83], 0xD ^ 0x1B, EnumParticleTypes.I[0x58 ^ 0x75], 0xA8 ^ 0xBE, (boolean)("".length() != 0)), 
    BARRIER(EnumParticleTypes.I[0x58 ^ 0x1E], 0x19 ^ 0x3A, EnumParticleTypes.I[0xCA ^ 0x8D], 0x17 ^ 0x34, (boolean)("".length() != 0));
    
    private final boolean shouldIgnoreRange;
    
    SUSPENDED(EnumParticleTypes.I[0x68 ^ 0x66], 0xE ^ 0x9, EnumParticleTypes.I[0x8D ^ 0x82], 0x2F ^ 0x28, (boolean)("".length() != 0)), 
    EXPLOSION_LARGE(EnumParticleTypes.I["  ".length()], " ".length(), EnumParticleTypes.I["   ".length()], " ".length(), (boolean)(" ".length() != 0)), 
    SPELL_MOB_AMBIENT(EnumParticleTypes.I[0x7E ^ 0x5E], 0xBF ^ 0xAF, EnumParticleTypes.I[0x21 ^ 0x0], 0x31 ^ 0x21, (boolean)("".length() != 0));
    
    private final String particleName;
    private final int argumentCount;
    
    FIREWORKS_SPARK(EnumParticleTypes.I[0xC3 ^ 0xC5], "   ".length(), EnumParticleTypes.I[0x6A ^ 0x6D], "   ".length(), (boolean)("".length() != 0));
    
    static {
        I();
        final EnumParticleTypes[] enum$VALUES = new EnumParticleTypes[0xD ^ 0x27];
        enum$VALUES["".length()] = EnumParticleTypes.EXPLOSION_NORMAL;
        enum$VALUES[" ".length()] = EnumParticleTypes.EXPLOSION_LARGE;
        enum$VALUES["  ".length()] = EnumParticleTypes.EXPLOSION_HUGE;
        enum$VALUES["   ".length()] = EnumParticleTypes.FIREWORKS_SPARK;
        enum$VALUES[0xF ^ 0xB] = EnumParticleTypes.WATER_BUBBLE;
        enum$VALUES[0x69 ^ 0x6C] = EnumParticleTypes.WATER_SPLASH;
        enum$VALUES[0x64 ^ 0x62] = EnumParticleTypes.WATER_WAKE;
        enum$VALUES[0xC1 ^ 0xC6] = EnumParticleTypes.SUSPENDED;
        enum$VALUES[0xAB ^ 0xA3] = EnumParticleTypes.SUSPENDED_DEPTH;
        enum$VALUES[0x32 ^ 0x3B] = EnumParticleTypes.CRIT;
        enum$VALUES[0x21 ^ 0x2B] = EnumParticleTypes.CRIT_MAGIC;
        enum$VALUES[0xC8 ^ 0xC3] = EnumParticleTypes.SMOKE_NORMAL;
        enum$VALUES[0x51 ^ 0x5D] = EnumParticleTypes.SMOKE_LARGE;
        enum$VALUES[0xC ^ 0x1] = EnumParticleTypes.SPELL;
        enum$VALUES[0x1B ^ 0x15] = EnumParticleTypes.SPELL_INSTANT;
        enum$VALUES[0x9F ^ 0x90] = EnumParticleTypes.SPELL_MOB;
        enum$VALUES[0x6B ^ 0x7B] = EnumParticleTypes.SPELL_MOB_AMBIENT;
        enum$VALUES[0x69 ^ 0x78] = EnumParticleTypes.SPELL_WITCH;
        enum$VALUES[0xBA ^ 0xA8] = EnumParticleTypes.DRIP_WATER;
        enum$VALUES[0xD7 ^ 0xC4] = EnumParticleTypes.DRIP_LAVA;
        enum$VALUES[0x93 ^ 0x87] = EnumParticleTypes.VILLAGER_ANGRY;
        enum$VALUES[0x43 ^ 0x56] = EnumParticleTypes.VILLAGER_HAPPY;
        enum$VALUES[0x2D ^ 0x3B] = EnumParticleTypes.TOWN_AURA;
        enum$VALUES[0x6B ^ 0x7C] = EnumParticleTypes.NOTE;
        enum$VALUES[0xAC ^ 0xB4] = EnumParticleTypes.PORTAL;
        enum$VALUES[0x5B ^ 0x42] = EnumParticleTypes.ENCHANTMENT_TABLE;
        enum$VALUES[0x33 ^ 0x29] = EnumParticleTypes.FLAME;
        enum$VALUES[0x4D ^ 0x56] = EnumParticleTypes.LAVA;
        enum$VALUES[0x63 ^ 0x7F] = EnumParticleTypes.FOOTSTEP;
        enum$VALUES[0xD ^ 0x10] = EnumParticleTypes.CLOUD;
        enum$VALUES[0x36 ^ 0x28] = EnumParticleTypes.REDSTONE;
        enum$VALUES[0x42 ^ 0x5D] = EnumParticleTypes.SNOWBALL;
        enum$VALUES[0x74 ^ 0x54] = EnumParticleTypes.SNOW_SHOVEL;
        enum$VALUES[0x42 ^ 0x63] = EnumParticleTypes.SLIME;
        enum$VALUES[0x7C ^ 0x5E] = EnumParticleTypes.HEART;
        enum$VALUES[0xAD ^ 0x8E] = EnumParticleTypes.BARRIER;
        enum$VALUES[0x19 ^ 0x3D] = EnumParticleTypes.ITEM_CRACK;
        enum$VALUES[0x43 ^ 0x66] = EnumParticleTypes.BLOCK_CRACK;
        enum$VALUES[0x9A ^ 0xBC] = EnumParticleTypes.BLOCK_DUST;
        enum$VALUES[0xA1 ^ 0x86] = EnumParticleTypes.WATER_DROP;
        enum$VALUES[0xD ^ 0x25] = EnumParticleTypes.ITEM_TAKE;
        enum$VALUES[0x51 ^ 0x78] = EnumParticleTypes.MOB_APPEARANCE;
        ENUM$VALUES = enum$VALUES;
        PARTICLES = Maps.newHashMap();
        final ArrayList arrayList = Lists.newArrayList();
        final EnumParticleTypes[] values;
        final int length = (values = values()).length;
        int i = "".length();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (i < length) {
            final EnumParticleTypes enumParticleTypes = values[i];
            EnumParticleTypes.PARTICLES.put(enumParticleTypes.getParticleID(), enumParticleTypes);
            if (!enumParticleTypes.getParticleName().endsWith(EnumParticleTypes.I[0x70 ^ 0x24])) {
                arrayList.add(enumParticleTypes.getParticleName());
            }
            ++i;
        }
        PARTICLE_NAMES = arrayList.toArray(new String[arrayList.size()]);
    }
    
    public String getParticleName() {
        return this.particleName;
    }
    
    public static String[] getParticleNames() {
        return EnumParticleTypes.PARTICLE_NAMES;
    }
    
    private static void I() {
        (I = new String[0x3B ^ 0x6E])["".length()] = I("\u0007)*\u00169\u001185\u0014)\f>(\u00177\u000e", "BqzZv");
        EnumParticleTypes.I[" ".length()] = I("3\u0017\u0001\u0014-2\n", "VoqxB");
        EnumParticleTypes.I["  ".length()] = I("6\u001b2\u001f\u0006 \n-\u001d\u0016?\u00020\u0014\f", "sCbSI");
        EnumParticleTypes.I["   ".length()] = I("\t\u0006\u0016\t \u0000\u001f\u0014\u0002*\u0001\u0002", "egdnE");
        EnumParticleTypes.I[0x37 ^ 0x33] = I("2\u0010&&($\u00019$8?\u001d1/", "wHvjg");
        EnumParticleTypes.I[0xA4 ^ 0xA1] = I("!\u0018\u0006\f&1\u001d\r\u00060 \u0002\u000f", "ImaiC");
        EnumParticleTypes.I[0x11 ^ 0x17] = I("\u0013:1!?\u001a!(77\u0006#\"6#", "Uscdh");
        EnumParticleTypes.I[0x53 ^ 0x54] = I(")0>\u001f. +'\t\n?8>\u0011", "OYLzY");
        EnumParticleTypes.I[0x22 ^ 0x2A] = I("2%>2\u0019:&?5\t)!", "edjwK");
        EnumParticleTypes.I[0x1C ^ 0x15] = I("\t\u0011\u0012;\u0001\u000e", "kdpYm");
        EnumParticleTypes.I[0x35 ^ 0x3F] = I("\u0015\u0000%\u0002!\u001d\u0012!\u000b2\u0011\t", "BAqGs");
        EnumParticleTypes.I[0x2C ^ 0x27] = I("6:(('-", "EJDIT");
        EnumParticleTypes.I[0xB9 ^ 0xB5] = I("'\u000f\u0016\u0016\n/\u0019\u0003\u0018\u001d", "pNBSX");
        EnumParticleTypes.I[0x79 ^ 0x74] = I("\u0002\"<\u000e", "uCWkv");
        EnumParticleTypes.I[0x4D ^ 0x43] = I("8,\u001e2\u0007%=\b&", "kyMbB");
        EnumParticleTypes.I[0x9 ^ 0x6] = I(")8\u001a\u001304)\f\u0007", "ZMicU");
        EnumParticleTypes.I[0x40 ^ 0x50] = I("\u001f3< \u0010\u0002\"*4\n\b#?$\u001d", "LfopU");
        EnumParticleTypes.I[0xD ^ 0x1C] = I("\f 8\" \u001b0;&-\u0006!", "hEHVH");
        EnumParticleTypes.I[0x5B ^ 0x49] = I("-+&%", "nyoqW");
        EnumParticleTypes.I[0x55 ^ 0x46] = I(":\u00130\u001f", "YaYkl");
        EnumParticleTypes.I[0x6E ^ 0x7A] = I("\b\u0011\u000f\u00155\u0006\u0002\u0001\b)", "KCFAj");
        EnumParticleTypes.I[0xD1 ^ 0xC4] = I("72\u0016\u001c0\u0019!\u0018\u0001", "ZSquS");
        EnumParticleTypes.I[0x3B ^ 0x2D] = I("0\u0018\u001f \u0011<\u001b\u001f9\u0019\"\u0019", "cUPkT");
        EnumParticleTypes.I[0xA8 ^ 0xBF] = I("+=\u0004\u0001$", "XPkjA");
        EnumParticleTypes.I[0x9E ^ 0x86] = I("<#\u001a%\u00030\"\u0014<\u0001*", "onUnF");
        EnumParticleTypes.I[0xDC ^ 0xC5] = I("5)&\u0010 *%;\u001c ", "YHTwE");
        EnumParticleTypes.I[0x32 ^ 0x28] = I("76\r\u0000\u000b", "dfHLG");
        EnumParticleTypes.I[0x62 ^ 0x79] = I("\u001b\u0012\u0011/\u000b", "hbtCg");
        EnumParticleTypes.I[0x8C ^ 0x90] = I("\u0012\u00030\u000e$\u001e\u001a;\u0011<\u0000\u001d!", "ASuBh");
        EnumParticleTypes.I[0x62 ^ 0x7F] = I("\u001d>\u001a$\u0007\u001a$: \u0003\u0018<", "tPiPf");
        EnumParticleTypes.I[0x42 ^ 0x5C] = I("\u0002\u0017\u0016#\n\u000e\n\u001c-", "QGSoF");
        EnumParticleTypes.I[0x67 ^ 0x78] = I("\b>\u0014=\u0005\u0000=\u001a", "eQvnu");
        EnumParticleTypes.I[0x65 ^ 0x45] = I("55\f\u0015\u000f9(\u0006\u001b\u001c'(\u000b\u0010\u0006(1", "feIYC");
        EnumParticleTypes.I[0xE4 ^ 0xC5] = I(">6\u000e\u001e565\u0000\f(10\t#1", "SYlME");
        EnumParticleTypes.I[0x79 ^ 0x5B] = I("&\u001e\u0004!\u001a*\u0019\b9\u0015=", "uNAmV");
        EnumParticleTypes.I[0x8A ^ 0xA9] = I("\u001a\u0001\"0\u0019 \t1:\u0012", "mhVSq");
        EnumParticleTypes.I[0x47 ^ 0x63] = I("\u0017\u0006\u0003=\u0007\u0004\u0015\u001e(\n", "STJmX");
        EnumParticleTypes.I[0xB3 ^ 0x96] = I("\u0015\u0001\u0013\u001f\u001f\u0010\u0007\u001f\u001d", "qszoH");
        EnumParticleTypes.I[0x76 ^ 0x50] = I("\u0014\u00190)\u0005\u001c\n/8", "PKyyZ");
        EnumParticleTypes.I[0xA7 ^ 0x80] = I("-*=\u0012((.5", "IXTbd");
        EnumParticleTypes.I[0xAF ^ 0x87] = I("\f8\u001e\u0014-\u001d4\u0000\u0007-\u00146\u0000\u0001", "ZqRXl");
        EnumParticleTypes.I[0x18 ^ 0x31] = I("8\u001b\r\"\u0011\u000f\u001c\u0006<\t>\u0010\u0018", "YujPh");
        EnumParticleTypes.I[0xB7 ^ 0x9D] = I("\u0015/=.+\u0004##=\"\u00026!;", "Cfqbj");
        EnumParticleTypes.I[0x43 ^ 0x68] = I("\n\u0010\u0018\u001224\u0018\u0004\u000e*\u0005\u0014\u001a", "bqhbK");
        EnumParticleTypes.I[0x42 ^ 0x6E] = I("8\u0005\u0014#\u0016-\u001f\u0011,", "lJCmI");
        EnumParticleTypes.I[0x39 ^ 0x14] = I("\u0013\u00046*&\u0012\u0019 ", "gkADG");
        EnumParticleTypes.I[0x7D ^ 0x53] = I("4\t7+", "zFcne");
        EnumParticleTypes.I[0x51 ^ 0x7E] = I("7\u00003\t", "YoGlF");
        EnumParticleTypes.I[0x2B ^ 0x1B] = I("\"\u001d\"\u0006;>", "rRpRz");
        EnumParticleTypes.I[0x90 ^ 0xA1] = I("8\u0003%.8$", "HlWZY");
        EnumParticleTypes.I[0x8B ^ 0xB9] = I("\u001c\u00160\u0001\u0002\u0017\f>\f\r\r\u0007'\b\u0001\u0015\u001d", "YXsIC");
        EnumParticleTypes.I[0x79 ^ 0x4A] = I("\u001f=\u0001\u0010*\u0014'\u000f\u001d%\u000e'\u0003\u001a'\u001f", "zSbxK");
        EnumParticleTypes.I[0x13 ^ 0x27] = I("*\u0019+>\r", "lUjsH");
        EnumParticleTypes.I[0x1B ^ 0x2E] = I("!(\u0010\u001f-", "GDqrH");
        EnumParticleTypes.I[0x6D ^ 0x5B] = I("\u000b\u0014\u0000\u0017", "GUVVl");
        EnumParticleTypes.I[0x6D ^ 0x5A] = I("\u001c,\u0018\u0005", "pMndC");
        EnumParticleTypes.I[0xAF ^ 0x97] = I("\t\u00016\u0013$\u001b\u000b)", "ONyGw");
        EnumParticleTypes.I[0x2E ^ 0x17] = I("\u0001\u00046\u0018>\u0013\u000e)", "gkYlM");
        EnumParticleTypes.I[0x9 ^ 0x33] = I("\u0002::\u0010\u0011", "AvuEU");
        EnumParticleTypes.I[0x16 ^ 0x2D] = I("\u0016!:\u0007\u0006", "uMUrb");
        EnumParticleTypes.I[0xFB ^ 0xC7] = I("&'\u0005!\r;,\u0004", "tbArY");
        EnumParticleTypes.I[0x7 ^ 0x3A] = I("(\u001d\u0014*\u0016)\f", "ZxpNc");
        EnumParticleTypes.I[0xB3 ^ 0x8D] = I("\u0017)\f6\u0000\u0005+\u000f", "DgCaB");
        EnumParticleTypes.I[0xE ^ 0x31] = I("#!-;\u00181#.<\u0015?)", "POBLz");
        EnumParticleTypes.I[0x4C ^ 0xC] = I("\u001f8\f\u0007\u0015\u001f>\f\u0006\u000f\u0000", "LvCPJ");
        EnumParticleTypes.I[0x2E ^ 0x6F] = I("\u0019\u0006\u0017\u000f\u0017\u0002\u0007\u000e\u001d\b", "jhxxd");
        EnumParticleTypes.I[0x47 ^ 0x5] = I("!8\u0011\u0019\b", "rtXTM");
        EnumParticleTypes.I[0x39 ^ 0x7A] = I("\u001d\u00058\u0005\u0014", "niQhq");
        EnumParticleTypes.I[0x3C ^ 0x78] = I("\u0004\u00171!\u0002", "LRpsV");
        EnumParticleTypes.I[0x87 ^ 0xC2] = I("=\f\u00004\u0013", "UiaFg");
        EnumParticleTypes.I[0x4D ^ 0xB] = I("\u0004*\u0000\u0013\u0006\u00039", "FkRAO");
        EnumParticleTypes.I[0xF7 ^ 0xB0] = I("\u001363\u0005&\u0014%", "qWAwO");
        EnumParticleTypes.I[0xE ^ 0x46] = I("3\u001a\u001f\u000109\u001c\u001b\u000f$", "zNZLo");
        EnumParticleTypes.I[0x26 ^ 0x6F] = I(".-+$;5/'!\u0007", "GNDJX");
        EnumParticleTypes.I[0x2C ^ 0x66] = I("\t\u0019\u0019\u001b\u001d\u0014\u0016\u0004\u0019\u0015\u0000", "KUVXV");
        EnumParticleTypes.I[0xF0 ^ 0xBB] = I("0\u000f,\u0001;1\u0011\"\u0001;\r", "RcCbP");
        EnumParticleTypes.I[0xD0 ^ 0x9C] = I("6\r-\u0016\u001f+\u00057\u0006\u0000", "tAbUT");
        EnumParticleTypes.I[0xDD ^ 0x90] = I("\u0017\u000b\u000b\u001a\u0005\u0011\u0012\u0017\r1", "ugdyn");
        EnumParticleTypes.I[0xF1 ^ 0xBF] = I("\u001b3-2\u0002\u00136+8\u0000", "LrywP");
        EnumParticleTypes.I[0xC1 ^ 0x8E] = I("5$\u00181\u000f4\"", "QVwAc");
        EnumParticleTypes.I[0x29 ^ 0x79] = I("?\u001b\u000e\u0014\u001a\"\u000e\u0000\u001c", "vOKYE");
        EnumParticleTypes.I[0x4C ^ 0x1D] = I("\u001f\u000b\u0011'", "kjzBK");
        EnumParticleTypes.I[0xCE ^ 0x9C] = I(" \u0018\u0006\u0015\f=\u0007\u0001\u000b\u001f,\u0019\u0007\u000f", "mWDJM");
        EnumParticleTypes.I[0x4E ^ 0x1D] = I(".!&\u0002\u00113+%\u0011\u0000--!", "CNDca");
        EnumParticleTypes.I[0x38 ^ 0x6C] = I("\u000e", "QkiAO");
    }
    
    public static EnumParticleTypes getParticleFromId(final int n) {
        return EnumParticleTypes.PARTICLES.get(n);
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getArgumentCount() {
        return this.argumentCount;
    }
    
    private EnumParticleTypes(final String s, final int n, final String particleName, final int particleID, final boolean shouldIgnoreRange, final int argumentCount) {
        this.particleName = particleName;
        this.particleID = particleID;
        this.shouldIgnoreRange = shouldIgnoreRange;
        this.argumentCount = argumentCount;
    }
    
    public boolean getShouldIgnoreRange() {
        return this.shouldIgnoreRange;
    }
    
    public int getParticleID() {
        return this.particleID;
    }
    
    public boolean hasArguments() {
        if (this.argumentCount > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private EnumParticleTypes(final String s, final int n, final String s2, final int n2, final boolean b) {
        this(s, n, s2, n2, b, "".length());
    }
}
