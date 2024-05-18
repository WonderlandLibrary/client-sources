package net.minecraft.client.stream;

import net.minecraft.stats.*;

public class MetadataAchievement extends Metadata
{
    private static final String[] I;
    
    static {
        I();
    }
    
    public MetadataAchievement(final Achievement achievement) {
        super(MetadataAchievement.I["".length()]);
        this.func_152808_a(MetadataAchievement.I[" ".length()], achievement.statId);
        this.func_152808_a(MetadataAchievement.I["  ".length()], achievement.getStatName().getUnformattedText());
        this.func_152808_a(MetadataAchievement.I["   ".length()], achievement.getDescription());
        this.func_152807_a(MetadataAchievement.I[0x41 ^ 0x45] + achievement.getStatName().getUnformattedText() + MetadataAchievement.I[0x5E ^ 0x5B]);
    }
    
    private static void I() {
        (I = new String[0x71 ^ 0x77])["".length()] = I("&/!>21)$293", "GLIWW");
        MetadataAchievement.I[" ".length()] = I("\u0018\u000b/<\u0011\u000f\r*0\u001a\r7.1", "yhGUt");
        MetadataAchievement.I["  ".length()] = I("\u0005\u00041=+\u0012\u000241 \u0010875#\u0001", "dgYTN");
        MetadataAchievement.I["   ".length()] = I("\u00030#&\u001c\u00146&*\u0017\u0016\f/*\n\u0001!\"?\r\u000b<%", "bSKOy");
        MetadataAchievement.I[0x33 ^ 0x37] = I("\r\u000b?\u0005\u0004:\r:\t\u000f8Hp", "LhWla");
        MetadataAchievement.I[0x79 ^ 0x7C] = I("LD\u0004\t=\n\r\u0005\u000e-J", "kdkkI");
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
