package net.minecraft.util;

import java.util.*;

public class EnchantmentNameParts
{
    private Random rand;
    private static final EnchantmentNameParts instance;
    private String[] namePartsArray;
    private static final String[] I;
    
    private static void I() {
        (I = new String[0xB1 ^ 0xB5])["".length()] = I("\u0006-\u001dq7\u001e!\u001d#r\u0001&\n>>\u001e6X:>\u0013$\f$r\u0010 \n0&\u0013e\u001689\u00060X)+\b?\u0001q0\u001e \u000b\"r\u00110\n\"7R)\u00116:\u0006e\u001c0 \u0019+\u001d\"!R#\u0011#7R$\u0011#r\u0017$\n%:R2\u0019%7\u0000e\u0010>&R!\n(r\u0011*\u00145r\u0005 \fq;\u0015+\u0011%7R6\u0016$4\u0014e\u001d<0\u001b\"\u001f4<R1\u000f8!\u0006e\u000b9=\u00001\u001d?r\u00011\n4&\u0011-X7;\u0016!\u00144r\u0016 \u000b% \u001d<X8?\u00100\u001dq5\u0013)\u000e0<\u001b?\u001dq7\u001c&\u00100<\u0006e\u001e#7\u0017e\u00148?\u001b1\u001d5r\u0000$\u001667R*\u001eq&\u001d2\u0019#6\u0001e\u0011?!\u001b!\u001dq!\u0002-\u001d#7R&\r37R6\u001d=4R*\f97\u0000e\u001a0>\u001ee\u00154<\u0006$\u0014q\"\u001a<\u000b81\u0013)X6 \u001d2X\":\u0000,\u0016:r\u0016 \u0015><R \u00144?\u0017+\f0>R6\b8 \u001b1X0<\u001b(\u0019=r\u00117\u001d0&\u00077\u001dq0\u0017$\u000b%r\u001a0\u00150<\u001d,\u001cq'\u001c!\u001d06R#\n4!\u001ae\u000b%3\u001e X", "rExQR");
        EnchantmentNameParts.I[" ".length()] = I("K", "kMxqB");
        EnchantmentNameParts.I["  ".length()] = I("", "QDqim");
        EnchantmentNameParts.I["   ".length()] = I("l", "LdAbE");
    }
    
    public String generateNewRandomName() {
        final int n = this.rand.nextInt("  ".length()) + "   ".length();
        String s = EnchantmentNameParts.I["  ".length()];
        int i = "".length();
        "".length();
        if (0 < -1) {
            throw null;
        }
        while (i < n) {
            if (i > 0) {
                s = String.valueOf(s) + EnchantmentNameParts.I["   ".length()];
            }
            s = String.valueOf(s) + this.namePartsArray[this.rand.nextInt(this.namePartsArray.length)];
            ++i;
        }
        return s;
    }
    
    static {
        I();
        instance = new EnchantmentNameParts();
    }
    
    public void reseedRandomGenerator(final long seed) {
        this.rand.setSeed(seed);
    }
    
    public EnchantmentNameParts() {
        this.rand = new Random();
        this.namePartsArray = EnchantmentNameParts.I["".length()].split(EnchantmentNameParts.I[" ".length()]);
    }
    
    public static EnchantmentNameParts getInstance() {
        return EnchantmentNameParts.instance;
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
