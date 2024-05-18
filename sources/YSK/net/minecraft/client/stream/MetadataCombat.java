package net.minecraft.client.stream;

import net.minecraft.entity.*;

public class MetadataCombat extends Metadata
{
    private static final String[] I;
    
    public MetadataCombat(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        super(MetadataCombat.I["".length()]);
        this.func_152808_a(MetadataCombat.I[" ".length()], entityLivingBase.getName());
        if (entityLivingBase2 != null) {
            this.func_152808_a(MetadataCombat.I["  ".length()], entityLivingBase2.getName());
        }
        if (entityLivingBase2 != null) {
            this.func_152807_a(MetadataCombat.I["   ".length()] + entityLivingBase.getName() + MetadataCombat.I[0x3F ^ 0x3B] + entityLivingBase2.getName());
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            this.func_152807_a(MetadataCombat.I[0x72 ^ 0x77] + entityLivingBase.getName() + MetadataCombat.I[0x33 ^ 0x35]);
        }
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x27 ^ 0x20])["".length()] = I("\u00008\u0000\u0001.\u0002\u000b\u0002\u0017&\u00125\u0015", "pTaxK");
        MetadataCombat.I[" ".length()] = I("!\u0016 8(#", "QzAAM");
        MetadataCombat.I["  ".length()] = I("<?\u001e).>4(+?<\"\u0019!!8", "LMwDO");
        MetadataCombat.I["   ".length()] = I("(\u00069(\u0014\u001fI6/\u0001\u001c\f1$U", "kiTJu");
        MetadataCombat.I[0x7F ^ 0x7B] = I("o\u0002\r=S", "OccYs");
        MetadataCombat.I[0x19 ^ 0x1C] = I("\"%\u000e8\u0018\u0015j\u0001?\r\u0016/\u00064Y", "aJcZy");
        MetadataCombat.I[0x7B ^ 0x7D] = I("A'\u0001<Y\u000e2\u0007=\u000b\u0012", "aFoXy");
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
            if (2 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
