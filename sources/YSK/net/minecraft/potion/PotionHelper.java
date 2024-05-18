package net.minecraft.potion;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class PotionHelper
{
    public static final String fermentedSpiderEyeEffect;
    public static final String glowstoneEffect;
    public static final String sugarEffect;
    private static final Map<Integer, String> potionAmplifiers;
    public static final String gunpowderEffect;
    public static final String field_77924_a;
    public static final String rabbitFootEffect;
    public static final String redstoneEffect;
    public static final String pufferfishEffect;
    public static final String speckledMelonEffect;
    private static final Map<Integer, String> potionRequirements;
    public static final String goldenCarrotEffect;
    public static final String magmaCreamEffect;
    public static final String ghastTearEffect;
    private static final String[] I;
    private static final String[] potionPrefixes;
    public static final String blazePowderEffect;
    public static final String spiderEyeEffect;
    private static final Map<Integer, Integer> DATAVALUE_COLORS;
    
    private static void I() {
        (I = new String[0x7E ^ 0x3D])["".length()] = I("CBXS]ZYFDDEF^SC", "hrubp");
        PotionHelper.I[" ".length()] = I("[\\KD{BGU^aCJRXd", "plfuP");
        PotionHelper.I["  ".length()] = I("CEhPjZXvGsEAnPt", "huEaG");
        PotionHelper.I["   ".length()] = I("Oll]uS", "dYAkX");
        PotionHelper.I[0x20 ^ 0x24] = I("gofZ\\}", "JZMlq");
        PotionHelper.I[0x59 ^ 0x5C] = I("F{R|A^gWi", "mJfZp");
        PotionHelper.I[0x29 ^ 0x2F] = I("gzjgRxgtpMg~lgJ", "JJGVy");
        PotionHelper.I[0x3 ^ 0x4] = I("Lt{aWUicvNJp{aI", "gDPPz");
        PotionHelper.I[0xC8 ^ 0xC0] = I("|JzVzeQ`V", "QzQeW");
        PotionHelper.I[0x98 ^ 0x91] = I("}@\\{gd]Blx{DZ{\u007f", "VpqJL");
        PotionHelper.I[0x79 ^ 0x73] = I("LFnSUU]vDLJBnSK", "gvEbx");
        PotionHelper.I[0x83 ^ 0x88] = I("{UEWDdH]@]{QEWZ", "Venfi");
        PotionHelper.I[0x72 ^ 0x7E] = I("e~SH\\zcKRF{hLTC", "HNxyw");
        PotionHelper.I[0xB ^ 0x6] = I("\u0011\u0005  '\u000fD$;-\u0007\u0003,g%\u0014\u00040(&\u0004", "ajTIH");
        PotionHelper.I[0xCA ^ 0xC4] = I("\u0003.\u001a\u0004\u000e\u001do\u001e\u001f\u0004\u0015(\u0016C\u0014\u001d(\u0000\u0019\u0004\u0001$\u001d\u0019\b\u001d&", "sAnma");
        PotionHelper.I[0x0 ^ 0xF] = I("\u0000)8 6\u001eh<;<\u0016/4g;\u001c'\"-", "pFLIY");
        PotionHelper.I[0x9D ^ 0x8D] = I(">8$\u0004: y \u001f0(>(C6\"21\u001f", "NWPmU");
        PotionHelper.I[0x56 ^ 0x47] = I("#\r&>\u000b=L\"%\u00015\u000b*y\t:\u000e9.", "SbRWd");
        PotionHelper.I[0xD2 ^ 0xC0] = I("?\u0002\u0017%\u0003!C\u0013>\t)\u0004\u001bb\b&\u000b\u00059\u001f*", "OmcLl");
        PotionHelper.I[0x4C ^ 0x5F] = I("\u0017\r&\u0004+\tL\"\u001f!\u0001\u000b*C%\u0015\u0016>\b7\u0014", "gbRmD");
        PotionHelper.I[0xB4 ^ 0xA0] = I("\u0017!\u0000 \u001b\t`\u0004;\u0011\u0001'\fg\u0000\u000f'\u001a", "gNtIt");
        PotionHelper.I[0xD2 ^ 0xC7] = I("%\u001e\u0001\u001d\u001b;_\u0005\u0006\u00113\u0018\rZ\u0015\"\u001a\u0002\u0015\u00061", "Uqutt");
        PotionHelper.I[0xB2 ^ 0xA4] = I("& 5\u0018 8a1\u0003*0&9_):.5", "VOAqO");
        PotionHelper.I[0x55 ^ 0x42] = I("\u001f$&,\u001f\u0001e\"7\u0015\t\"*k\u0012\u001a'9<", "oKREp");
        PotionHelper.I[0x81 ^ 0x99] = I("\u001e\u0018\u0000\b+\u0000Y\u0004\u0013!\b\u001e\fO&\u001b\u0019\u0013\r-\u0000\u0010", "nwtaD");
        PotionHelper.I[0x2B ^ 0x32] = I("77\u0016\u000e\u0016)v\u0012\u0015\u001c!1\u001aI\u001b2,\u0016\u0002\u000b\"<", "GXbgy");
        PotionHelper.I[0x89 ^ 0x93] = I("')\u001f*\u00019h\u001b1\u000b1/\u0013m\u001d:)\u00047\u0006", "WFkCn");
        PotionHelper.I[0x15 ^ 0xE] = I("6!\u0010\r9(`\u0014\u00163 '\u001cJ%3/\u0012\u0001", "FNddV");
        PotionHelper.I[0x1E ^ 0x2] = I(": \u0005\u0004\t$a\u0001\u001f\u0003,&\tC\u0002/-\u001e\u0003\u0007#=", "JOqmf");
        PotionHelper.I[0x51 ^ 0x4C] = I(">.#\u0018\u000b o'\u0003\u0001((/_\u0010&(4\u001a", "NAWqd");
        PotionHelper.I[0xD8 ^ 0xC6] = I("\u0002\u0003,$&\u001cB(?,\u0014\u0005 c,\u001e\t?,'\u0006", "rlXMI");
        PotionHelper.I[0x93 ^ 0x8C] = I("\u001d\u001f\r\u00028\u0003^\t\u00192\u000b\u0019\u0001E1\f\u001e\u001a\u0012", "mpykW");
        PotionHelper.I[0xB4 ^ 0x94] = I("\u0014>\u0007\u000b\u0018\n\u007f\u0003\u0010\u0012\u00028\u000bL\u0014\f0\u0001\u000f\u001e\n6", "dQsbw");
        PotionHelper.I[0x58 ^ 0x79] = I("#.\u001b\u000f!=o\u001f\u0014+5(\u0017H*22\u0007\u000f 4", "SAofN");
        PotionHelper.I[0x5F ^ 0x7D] = I("*;\u0011\u0007\f4z\u0015\u001c\u0006<=\u001d@\u0011?2\f\u0000\u0006>", "ZTenc");
        PotionHelper.I[0x19 ^ 0x3A] = I("3\u001b1\u00008-Z5\u001b2%\u001d=G4,\u0006!\u00006/", "CtEiW");
        PotionHelper.I[0x34 ^ 0x10] = I("\u001a)8$\b\u0004h<?\u0002\f/4c\u0014\u001a'>&\u000b\u0003(+", "jFLMg");
        PotionHelper.I[0x5F ^ 0x7A] = I("5\u00003:\u001c+A7!\u0016#\u0006?}\u0003*\u001b\"=\u0007", "EoGSs");
        PotionHelper.I[0x92 ^ 0xB4] = I("\u0007\u000e\u001b\u0011(\u0019O\u001f\n\"\u0011\b\u0017V!\u0018\u0014\u0003", "waoxG");
        PotionHelper.I[0x30 ^ 0x17] = I("<\b\u0013/9\"I\u001743*\u000e\u001fh9(\b\u0015*3?\u0014", "LggFV");
        PotionHelper.I[0x23 ^ 0xB] = I("\n&\u0010\u001b:\u0014g\u0014\u00000\u001c \u001c\\'\u001b'\u000f", "zIdrU");
        PotionHelper.I[0xA8 ^ 0x81] = I("4\u0001\u001e>>*@\u001a%4\"\u0007\u0012y9%\u001c\u0019?", "DnjWQ");
        PotionHelper.I[0x3A ^ 0x10] = I("\u001e\u0016\u0011*\u0002\u0000W\u00151\b\b\u0010\u001dm\f\r\u000b\f'", "nyeCm");
        PotionHelper.I[0x88 ^ 0xA3] = I("!6;0<?w?+6707w4#6<*", "QYOYS");
        PotionHelper.I[0x4 ^ 0x28] = I("3&0\u0007&-g4\u001c,% <@:7 *\u00050", "CIDnI");
        PotionHelper.I[0xBC ^ 0x91] = I("adIRU`dIRUcdIRUbdIRDzr", "QDort");
        PotionHelper.I[0x77 ^ 0x59] = I("s}ACFcmGEG`mGEGamGEWy{", "RMaef");
        PotionHelper.I[0x9E ^ 0xB1] = I("zErBdjCtCgjCtCfjCtR~|", "JeTbU");
        PotionHelper.I[0x5D ^ 0x6D] = I("EtcA[DtcAHUre@I", "uTEaz");
        PotionHelper.I[0xAD ^ 0x9C] = I("RYBRDRXBRDAIDTE@IDTVX_", "sibtd");
        PotionHelper.I[0x98 ^ 0xAA] = I("R`yBfRayBfRbyBf@p\u007fDuXf", "sPYdF");
        PotionHelper.I[0x6A ^ 0x59] = I("VXXQJVYXQJEH^WY", "whxwj");
        PotionHelper.I[0x52 ^ 0x66] = I("rSIUwbCOSvaCOSdsEI@|e", "ScisW");
        PotionHelper.I[0x6E ^ 0x5B] = I("xp`bbyp`bbzp`bphvfqh~", "HPFBC");
        PotionHelper.I[0x5D ^ 0x6B] = I("`aLSqpqJUcawLTbawLGzw", "AQluQ");
        PotionHelper.I[0x39 ^ 0xE] = I("JvTAvZfRGdK`TTvMfFL`", "kFtgV");
        PotionHelper.I[0x28 ^ 0x10] = I("BzpOxCzpOkR|v\\yTzdDo", "rZVoY");
        PotionHelper.I[0x75 ^ 0x4C] = I("SUvl|CSpm\u007fCSp\u007fmEUcg{", "cuPLM");
        PotionHelper.I[0x6A ^ 0x50] = I("R", "gLdZD");
        PotionHelper.I[0x92 ^ 0xA9] = I("[", "nhDRo");
        PotionHelper.I[0x3A ^ 0x6] = I("{", "NVySH");
        PotionHelper.I[0x36 ^ 0xB] = I("f", "SjylM");
        PotionHelper.I[0xBC ^ 0x82] = I("g", "RwcSe");
        PotionHelper.I[0x37 ^ 0x8] = I("P", "eEMGe");
        PotionHelper.I[0xE8 ^ 0xA8] = I("_", "jZwUP");
        PotionHelper.I[0x4E ^ 0xF] = I("P", "eUjOO");
        PotionHelper.I[0x29 ^ 0x6B] = I("T", "aJBAV");
    }
    
    public static int calcPotionLiquidColor(final Collection<PotionEffect> collection) {
        final int n = 1458812 + 1294726 - 2296031 + 3236515;
        if (collection == null || collection.isEmpty()) {
            return n;
        }
        float n2 = 0.0f;
        float n3 = 0.0f;
        float n4 = 0.0f;
        float n5 = 0.0f;
        final Iterator<PotionEffect> iterator = collection.iterator();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final PotionEffect potionEffect = iterator.next();
            if (potionEffect.getIsShowParticles()) {
                final int liquidColor = Potion.potionTypes[potionEffect.getPotionID()].getLiquidColor();
                int i = "".length();
                "".length();
                if (3 <= -1) {
                    throw null;
                }
                while (i <= potionEffect.getAmplifier()) {
                    n2 += (liquidColor >> (0x8B ^ 0x9B) & 126 + 177 - 240 + 192) / 255.0f;
                    n3 += (liquidColor >> (0x2D ^ 0x25) & 74 + 13 + 74 + 94) / 255.0f;
                    n4 += (liquidColor >> "".length() & 198 + 215 - 391 + 233) / 255.0f;
                    ++n5;
                    ++i;
                }
            }
        }
        if (n5 == 0.0f) {
            return "".length();
        }
        return (int)(n2 / n5 * 255.0f) << (0x46 ^ 0x56) | (int)(n3 / n5 * 255.0f) << (0x76 ^ 0x7E) | (int)(n4 / n5 * 255.0f);
    }
    
    private static int isFlagSet(final int n, final int n2) {
        int n3;
        if (checkFlag(n, n2)) {
            n3 = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        return n3;
    }
    
    public static int getLiquidColor(final int n, final boolean b) {
        final Integer func_181756_a = IntegerCache.func_181756_a(n);
        if (b) {
            return calcPotionLiquidColor(getPotionEffects(func_181756_a, (boolean)(" ".length() != 0)));
        }
        if (PotionHelper.DATAVALUE_COLORS.containsKey(func_181756_a)) {
            return PotionHelper.DATAVALUE_COLORS.get(func_181756_a);
        }
        final int calcPotionLiquidColor = calcPotionLiquidColor(getPotionEffects(func_181756_a, (boolean)("".length() != 0)));
        PotionHelper.DATAVALUE_COLORS.put(func_181756_a, calcPotionLiquidColor);
        return calcPotionLiquidColor;
    }
    
    private static int brewBitOperations(int n, final int n2, final boolean b, final boolean b2, final boolean b3) {
        if (b3) {
            if (!checkFlag(n, n2)) {
                return "".length();
            }
        }
        else if (b) {
            n &= (" ".length() << n2 ^ -" ".length());
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else if (b2) {
            if ((n & " ".length() << n2) == 0x0) {
                n |= " ".length() << n2;
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                n &= (" ".length() << n2 ^ -" ".length());
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
        }
        else {
            n |= " ".length() << n2;
        }
        return n;
    }
    
    public static boolean getAreAmbient(final Collection<PotionEffect> collection) {
        final Iterator<PotionEffect> iterator = collection.iterator();
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (!iterator.next().getIsAmbient()) {
                return "".length() != 0;
            }
        }
        return " ".length() != 0;
    }
    
    private static int func_77904_a(final boolean b, final boolean b2, final boolean b3, final int n, final int n2, final int n3, final int n4) {
        int n5 = "".length();
        if (b) {
            n5 = isFlagUnset(n4, n2);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (n != -" ".length()) {
            if (n == 0 && countSetFlags(n4) == n2) {
                n5 = " ".length();
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else if (n == " ".length() && countSetFlags(n4) > n2) {
                n5 = " ".length();
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else if (n == "  ".length() && countSetFlags(n4) < n2) {
                n5 = " ".length();
                "".length();
                if (2 == 4) {
                    throw null;
                }
            }
        }
        else {
            n5 = isFlagSet(n4, n2);
        }
        if (b2) {
            n5 *= n3;
        }
        if (b3) {
            n5 *= -" ".length();
        }
        return n5;
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static String getPotionPrefix(final int n) {
        return PotionHelper.potionPrefixes[getPotionPrefixIndex(n)];
    }
    
    private static int countSetFlags(int i) {
        int length = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i > 0) {
            i &= i - " ".length();
            ++length;
        }
        return length;
    }
    
    public static int applyIngredient(int n, final String s) {
        final int length = "".length();
        final int length2 = s.length();
        int n2 = "".length();
        int n3 = "".length();
        int n4 = "".length();
        int n5 = "".length();
        int n6 = "".length();
        int i = length;
        "".length();
        if (4 == -1) {
            throw null;
        }
        while (i < length2) {
            final char char1 = s.charAt(i);
            if (char1 >= (0x1 ^ 0x31) && char1 <= (0xB ^ 0x32)) {
                n6 = n6 * (0x50 ^ 0x5A) + (char1 - (0x69 ^ 0x59));
                n2 = " ".length();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else if (char1 == (0xA6 ^ 0x87)) {
                if (n2 != 0) {
                    n = brewBitOperations(n, n6, n4 != 0, n3 != 0, n5 != 0);
                    n5 = "".length();
                    "".length();
                    n4 = "".length();
                    n2 = "".length();
                    n6 = "".length();
                }
                n3 = " ".length();
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            else if (char1 == (0x79 ^ 0x54)) {
                if (n2 != 0) {
                    n = brewBitOperations(n, n6, n4 != 0, n3 != 0, n5 != 0);
                    n5 = "".length();
                    n3 = "".length();
                    "".length();
                    n2 = "".length();
                    n6 = "".length();
                }
                n4 = " ".length();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else if (char1 == (0xEE ^ 0xC5)) {
                if (n2 != 0) {
                    n = brewBitOperations(n, n6, n4 != 0, n3 != 0, n5 != 0);
                    n5 = "".length();
                    n3 = "".length();
                    n4 = "".length();
                    n2 = "".length();
                    n6 = "".length();
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                }
            }
            else if (char1 == (0x8D ^ 0xAB)) {
                if (n2 != 0) {
                    n = brewBitOperations(n, n6, n4 != 0, n3 != 0, n5 != 0);
                    "".length();
                    n3 = "".length();
                    n4 = "".length();
                    n2 = "".length();
                    n6 = "".length();
                }
                n5 = " ".length();
            }
            ++i;
        }
        if (n2 != 0) {
            n = brewBitOperations(n, n6, n4 != 0, n3 != 0, n5 != 0);
        }
        return n & 23560 + 31883 - 50579 + 27903;
    }
    
    private static int parsePotionEffects(final String s, final int n, final int n2, final int n3) {
        if (n >= s.length() || n2 < 0 || n >= n2) {
            return "".length();
        }
        final int index = s.indexOf(0xE8 ^ 0x94, n);
        if (index >= 0 && index < n2) {
            final int potionEffects = parsePotionEffects(s, n, index - " ".length(), n3);
            if (potionEffects > 0) {
                return potionEffects;
            }
            final int potionEffects2 = parsePotionEffects(s, index + " ".length(), n2, n3);
            int length;
            if (potionEffects2 > 0) {
                length = potionEffects2;
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
            else {
                length = "".length();
            }
            return length;
        }
        else {
            final int index2 = s.indexOf(0x6A ^ 0x4C, n);
            if (index2 >= 0 && index2 < n2) {
                final int potionEffects3 = parsePotionEffects(s, n, index2 - " ".length(), n3);
                if (potionEffects3 <= 0) {
                    return "".length();
                }
                final int potionEffects4 = parsePotionEffects(s, index2 + " ".length(), n2, n3);
                int length2;
                if (potionEffects4 <= 0) {
                    length2 = "".length();
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                }
                else if (potionEffects3 > potionEffects4) {
                    length2 = potionEffects3;
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                }
                else {
                    length2 = potionEffects4;
                }
                return length2;
            }
            else {
                int n4 = "".length();
                int n5 = "".length();
                int n6 = "".length();
                int n7 = "".length();
                int n8 = "".length();
                int n9 = -" ".length();
                int n10 = "".length();
                int n11 = "".length();
                int length3 = "".length();
                int i = n;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
                while (i < n2) {
                    final char char1 = s.charAt(i);
                    if (char1 >= (0x27 ^ 0x17) && char1 <= (0xFD ^ 0xC4)) {
                        if (n4 != 0) {
                            n11 = char1 - (0xB3 ^ 0x83);
                            n5 = " ".length();
                            "".length();
                            if (1 <= -1) {
                                throw null;
                            }
                        }
                        else {
                            n10 = n10 * (0xB1 ^ 0xBB) + (char1 - (0x45 ^ 0x75));
                            n6 = " ".length();
                            "".length();
                            if (-1 >= 2) {
                                throw null;
                            }
                        }
                    }
                    else if (char1 == (0xB ^ 0x21)) {
                        n4 = " ".length();
                        "".length();
                        if (0 >= 2) {
                            throw null;
                        }
                    }
                    else if (char1 == (0x3D ^ 0x1C)) {
                        if (n6 != 0) {
                            length3 += func_77904_a(n7 != 0, n5 != 0, n8 != 0, n9, n10, n11, n3);
                            "".length();
                            n8 = "".length();
                            n4 = "".length();
                            n5 = "".length();
                            n6 = "".length();
                            n11 = "".length();
                            n10 = "".length();
                            n9 = -" ".length();
                        }
                        n7 = " ".length();
                        "".length();
                        if (0 == -1) {
                            throw null;
                        }
                    }
                    else if (char1 == (0x57 ^ 0x7A)) {
                        if (n6 != 0) {
                            length3 += func_77904_a(n7 != 0, n5 != 0, n8 != 0, n9, n10, n11, n3);
                            n7 = "".length();
                            "".length();
                            n4 = "".length();
                            n5 = "".length();
                            n6 = "".length();
                            n11 = "".length();
                            n10 = "".length();
                            n9 = -" ".length();
                        }
                        n8 = " ".length();
                        "".length();
                        if (4 <= 2) {
                            throw null;
                        }
                    }
                    else if (char1 != (0x8A ^ 0xB7) && char1 != (0x72 ^ 0x4E) && char1 != (0x4C ^ 0x72)) {
                        if (char1 == (0x88 ^ 0xA3) && n6 != 0) {
                            length3 += func_77904_a(n7 != 0, n5 != 0, n8 != 0, n9, n10, n11, n3);
                            n7 = "".length();
                            n8 = "".length();
                            n4 = "".length();
                            n5 = "".length();
                            n6 = "".length();
                            n11 = "".length();
                            n10 = "".length();
                            n9 = -" ".length();
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                        }
                    }
                    else {
                        if (n6 != 0) {
                            length3 += func_77904_a(n7 != 0, n5 != 0, n8 != 0, n9, n10, n11, n3);
                            n7 = "".length();
                            n8 = "".length();
                            n4 = "".length();
                            n5 = "".length();
                            n6 = "".length();
                            n11 = "".length();
                            n10 = "".length();
                            n9 = -" ".length();
                        }
                        if (char1 == (0xA3 ^ 0x9E)) {
                            n9 = "".length();
                            "".length();
                            if (0 < 0) {
                                throw null;
                            }
                        }
                        else if (char1 == (0x1D ^ 0x21)) {
                            n9 = "  ".length();
                            "".length();
                            if (0 < -1) {
                                throw null;
                            }
                        }
                        else if (char1 == (0x3B ^ 0x5)) {
                            n9 = " ".length();
                        }
                    }
                    ++i;
                }
                if (n6 != 0) {
                    length3 += func_77904_a(n7 != 0, n5 != 0, n8 != 0, n9, n10, n11, n3);
                }
                return length3;
            }
        }
    }
    
    private static int isFlagUnset(final int n, final int n2) {
        int n3;
        if (checkFlag(n, n2)) {
            n3 = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n3 = " ".length();
        }
        return n3;
    }
    
    public static List<PotionEffect> getPotionEffects(final int n, final boolean b) {
        List<PotionEffect> arrayList = null;
        final Potion[] potionTypes;
        final int length = (potionTypes = Potion.potionTypes).length;
        int i = "".length();
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (i < length) {
            final Potion potion = potionTypes[i];
            if (potion != null && (!potion.isUsable() || b)) {
                final String s = PotionHelper.potionRequirements.get(potion.getId());
                if (s != null) {
                    final int potionEffects = parsePotionEffects(s, "".length(), s.length(), n);
                    if (potionEffects > 0) {
                        int n2 = "".length();
                        final String s2 = PotionHelper.potionAmplifiers.get(potion.getId());
                        if (s2 != null) {
                            n2 = parsePotionEffects(s2, "".length(), s2.length(), n);
                            if (n2 < 0) {
                                n2 = "".length();
                            }
                        }
                        int length2;
                        if (potion.isInstant()) {
                            length2 = " ".length();
                            "".length();
                            if (1 >= 3) {
                                throw null;
                            }
                        }
                        else {
                            length2 = (int)Math.round(((1168 + 866 - 1118 + 284) * (potionEffects * "   ".length() + (potionEffects - " ".length()) * "  ".length()) >> n2) * potion.getEffectiveness());
                            if ((n & 3430 + 348 + 10049 + 2557) != 0x0) {
                                length2 = (int)Math.round(length2 * 0.75 + 0.5);
                            }
                        }
                        if (arrayList == null) {
                            arrayList = (List<PotionEffect>)Lists.newArrayList();
                        }
                        final PotionEffect potionEffect = new PotionEffect(potion.getId(), length2, n2);
                        if ((n & 11717 + 7898 - 9467 + 6236) != 0x0) {
                            potionEffect.setSplashPotion(" ".length() != 0);
                        }
                        arrayList.add(potionEffect);
                    }
                }
            }
            ++i;
        }
        return arrayList;
    }
    
    public static boolean checkFlag(final int n, final int n2) {
        if ((n & " ".length() << n2) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static int func_77908_a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        int length;
        if (checkFlag(n, n2)) {
            length = (0xB1 ^ 0xA1);
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        int length2;
        if (checkFlag(n, n3)) {
            length2 = (0xB0 ^ 0xB8);
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            length2 = "".length();
        }
        final int n7 = length | length2;
        int length3;
        if (checkFlag(n, n4)) {
            length3 = (0x92 ^ 0x96);
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            length3 = "".length();
        }
        final int n8 = n7 | length3;
        int n9;
        if (checkFlag(n, n5)) {
            n9 = "  ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n9 = "".length();
        }
        final int n10 = n8 | n9;
        int n11;
        if (checkFlag(n, n6)) {
            n11 = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n11 = "".length();
        }
        return n10 | n11;
    }
    
    static {
        I();
        blazePowderEffect = PotionHelper.I["".length()];
        pufferfishEffect = PotionHelper.I[" ".length()];
        ghastTearEffect = PotionHelper.I["  ".length()];
        glowstoneEffect = PotionHelper.I["   ".length()];
        redstoneEffect = PotionHelper.I[0x9E ^ 0x9A];
        gunpowderEffect = PotionHelper.I[0x1F ^ 0x1A];
        spiderEyeEffect = PotionHelper.I[0x9E ^ 0x98];
        magmaCreamEffect = PotionHelper.I[0x91 ^ 0x96];
        fermentedSpiderEyeEffect = PotionHelper.I[0x7E ^ 0x76];
        speckledMelonEffect = PotionHelper.I[0xB0 ^ 0xB9];
        rabbitFootEffect = PotionHelper.I[0x10 ^ 0x1A];
        sugarEffect = PotionHelper.I[0xCB ^ 0xC0];
        goldenCarrotEffect = PotionHelper.I[0xBB ^ 0xB7];
        field_77924_a = null;
        potionRequirements = Maps.newHashMap();
        potionAmplifiers = Maps.newHashMap();
        DATAVALUE_COLORS = Maps.newHashMap();
        final String[] potionPrefixes2 = new String[0x93 ^ 0xB3];
        potionPrefixes2["".length()] = PotionHelper.I[0x3 ^ 0xE];
        potionPrefixes2[" ".length()] = PotionHelper.I[0x30 ^ 0x3E];
        potionPrefixes2["  ".length()] = PotionHelper.I[0x4A ^ 0x45];
        potionPrefixes2["   ".length()] = PotionHelper.I[0xB6 ^ 0xA6];
        potionPrefixes2[0x5D ^ 0x59] = PotionHelper.I[0xB7 ^ 0xA6];
        potionPrefixes2[0x32 ^ 0x37] = PotionHelper.I[0xA6 ^ 0xB4];
        potionPrefixes2[0x34 ^ 0x32] = PotionHelper.I[0x52 ^ 0x41];
        potionPrefixes2[0xBA ^ 0xBD] = PotionHelper.I[0x26 ^ 0x32];
        potionPrefixes2[0x17 ^ 0x1F] = PotionHelper.I[0xB ^ 0x1E];
        potionPrefixes2[0x81 ^ 0x88] = PotionHelper.I[0x53 ^ 0x45];
        potionPrefixes2[0x4F ^ 0x45] = PotionHelper.I[0x9D ^ 0x8A];
        potionPrefixes2[0x23 ^ 0x28] = PotionHelper.I[0x78 ^ 0x60];
        potionPrefixes2[0x2F ^ 0x23] = PotionHelper.I[0x1C ^ 0x5];
        potionPrefixes2[0x7C ^ 0x71] = PotionHelper.I[0x40 ^ 0x5A];
        potionPrefixes2[0x32 ^ 0x3C] = PotionHelper.I[0x4A ^ 0x51];
        potionPrefixes2[0x14 ^ 0x1B] = PotionHelper.I[0x59 ^ 0x45];
        potionPrefixes2[0x2F ^ 0x3F] = PotionHelper.I[0x19 ^ 0x4];
        potionPrefixes2[0xA4 ^ 0xB5] = PotionHelper.I[0x37 ^ 0x29];
        potionPrefixes2[0x7F ^ 0x6D] = PotionHelper.I[0x33 ^ 0x2C];
        potionPrefixes2[0x7D ^ 0x6E] = PotionHelper.I[0xE7 ^ 0xC7];
        potionPrefixes2[0xAA ^ 0xBE] = PotionHelper.I[0x6B ^ 0x4A];
        potionPrefixes2[0xB2 ^ 0xA7] = PotionHelper.I[0x28 ^ 0xA];
        potionPrefixes2[0xE ^ 0x18] = PotionHelper.I[0x5C ^ 0x7F];
        potionPrefixes2[0x7F ^ 0x68] = PotionHelper.I[0x33 ^ 0x17];
        potionPrefixes2[0x75 ^ 0x6D] = PotionHelper.I[0x46 ^ 0x63];
        potionPrefixes2[0xA4 ^ 0xBD] = PotionHelper.I[0xE3 ^ 0xC5];
        potionPrefixes2[0x5D ^ 0x47] = PotionHelper.I[0x63 ^ 0x44];
        potionPrefixes2[0xA6 ^ 0xBD] = PotionHelper.I[0x17 ^ 0x3F];
        potionPrefixes2[0x87 ^ 0x9B] = PotionHelper.I[0x72 ^ 0x5B];
        potionPrefixes2[0xA ^ 0x17] = PotionHelper.I[0xED ^ 0xC7];
        potionPrefixes2[0x4E ^ 0x50] = PotionHelper.I[0x68 ^ 0x43];
        potionPrefixes2[0x5 ^ 0x1A] = PotionHelper.I[0x1 ^ 0x2D];
        potionPrefixes = potionPrefixes2;
        PotionHelper.potionRequirements.put(Potion.regeneration.getId(), PotionHelper.I[0xBF ^ 0x92]);
        PotionHelper.potionRequirements.put(Potion.moveSpeed.getId(), PotionHelper.I[0x6D ^ 0x43]);
        PotionHelper.potionRequirements.put(Potion.fireResistance.getId(), PotionHelper.I[0x38 ^ 0x17]);
        PotionHelper.potionRequirements.put(Potion.heal.getId(), PotionHelper.I[0x84 ^ 0xB4]);
        PotionHelper.potionRequirements.put(Potion.poison.getId(), PotionHelper.I[0x9E ^ 0xAF]);
        PotionHelper.potionRequirements.put(Potion.weakness.getId(), PotionHelper.I[0x59 ^ 0x6B]);
        PotionHelper.potionRequirements.put(Potion.harm.getId(), PotionHelper.I[0x3E ^ 0xD]);
        PotionHelper.potionRequirements.put(Potion.moveSlowdown.getId(), PotionHelper.I[0x22 ^ 0x16]);
        PotionHelper.potionRequirements.put(Potion.damageBoost.getId(), PotionHelper.I[0x18 ^ 0x2D]);
        PotionHelper.potionRequirements.put(Potion.nightVision.getId(), PotionHelper.I[0x85 ^ 0xB3]);
        PotionHelper.potionRequirements.put(Potion.invisibility.getId(), PotionHelper.I[0x6 ^ 0x31]);
        PotionHelper.potionRequirements.put(Potion.waterBreathing.getId(), PotionHelper.I[0x66 ^ 0x5E]);
        PotionHelper.potionRequirements.put(Potion.jump.getId(), PotionHelper.I[0x2E ^ 0x17]);
        PotionHelper.potionAmplifiers.put(Potion.moveSpeed.getId(), PotionHelper.I[0x44 ^ 0x7E]);
        PotionHelper.potionAmplifiers.put(Potion.digSpeed.getId(), PotionHelper.I[0x24 ^ 0x1F]);
        PotionHelper.potionAmplifiers.put(Potion.damageBoost.getId(), PotionHelper.I[0x1F ^ 0x23]);
        PotionHelper.potionAmplifiers.put(Potion.regeneration.getId(), PotionHelper.I[0xF ^ 0x32]);
        PotionHelper.potionAmplifiers.put(Potion.harm.getId(), PotionHelper.I[0x33 ^ 0xD]);
        PotionHelper.potionAmplifiers.put(Potion.heal.getId(), PotionHelper.I[0xE ^ 0x31]);
        PotionHelper.potionAmplifiers.put(Potion.resistance.getId(), PotionHelper.I[0x74 ^ 0x34]);
        PotionHelper.potionAmplifiers.put(Potion.poison.getId(), PotionHelper.I[0xE9 ^ 0xA8]);
        PotionHelper.potionAmplifiers.put(Potion.jump.getId(), PotionHelper.I[0x63 ^ 0x21]);
    }
    
    public static int getPotionPrefixIndex(final int n) {
        return func_77908_a(n, 0x46 ^ 0x43, 0x5A ^ 0x5E, "   ".length(), "  ".length(), " ".length());
    }
}
