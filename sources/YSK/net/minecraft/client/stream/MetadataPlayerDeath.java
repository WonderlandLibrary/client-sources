package net.minecraft.client.stream;

import net.minecraft.entity.*;

public class MetadataPlayerDeath extends Metadata
{
    private static final String[] I;
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0006*42\u0010\u0004\u00191.\u0014\u0002.", "vFUKu");
        MetadataPlayerDeath.I[" ".length()] = I("\u001d\u00054\n \u001f", "miUsE");
        MetadataPlayerDeath.I["  ".length()] = I("\r/\u0003%\t\u0014", "fFoIl");
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public MetadataPlayerDeath(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        super(MetadataPlayerDeath.I["".length()]);
        if (entityLivingBase != null) {
            this.func_152808_a(MetadataPlayerDeath.I[" ".length()], entityLivingBase.getName());
        }
        if (entityLivingBase2 != null) {
            this.func_152808_a(MetadataPlayerDeath.I["  ".length()], entityLivingBase2.getName());
        }
    }
    
    static {
        I();
    }
}
