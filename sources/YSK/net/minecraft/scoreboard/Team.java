package net.minecraft.scoreboard;

import java.util.*;
import com.google.common.collect.*;

public abstract class Team
{
    public abstract String formatString(final String p0);
    
    public abstract boolean getSeeFriendlyInvisiblesEnabled();
    
    public abstract String getRegisteredName();
    
    public abstract EnumVisible getNameTagVisibility();
    
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
            if (2 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public abstract EnumVisible getDeathMessageVisibility();
    
    public abstract Collection<String> getMembershipCollection();
    
    public abstract boolean getAllowFriendlyFire();
    
    public boolean isSameTeam(final Team team) {
        int n;
        if (team == null) {
            n = "".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else if (this == team) {
            n = " ".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public enum EnumVisible
    {
        private static Map<String, EnumVisible> field_178828_g;
        
        HIDE_FOR_OTHER_TEAMS(EnumVisible.I[0x70 ^ 0x74], "  ".length(), EnumVisible.I[0x52 ^ 0x57], "  ".length());
        
        private static final EnumVisible[] ENUM$VALUES;
        
        NEVER(EnumVisible.I["  ".length()], " ".length(), EnumVisible.I["   ".length()], " ".length());
        
        public final int field_178827_f;
        
        HIDE_FOR_OWN_TEAM(EnumVisible.I[0x14 ^ 0x12], "   ".length(), EnumVisible.I[0x6C ^ 0x6B], "   ".length()), 
        ALWAYS(EnumVisible.I["".length()], "".length(), EnumVisible.I[" ".length()], "".length());
        
        public final String field_178830_e;
        private static final String[] I;
        
        static {
            I();
            final EnumVisible[] enum$VALUES = new EnumVisible[0x48 ^ 0x4C];
            enum$VALUES["".length()] = EnumVisible.ALWAYS;
            enum$VALUES[" ".length()] = EnumVisible.NEVER;
            enum$VALUES["  ".length()] = EnumVisible.HIDE_FOR_OTHER_TEAMS;
            enum$VALUES["   ".length()] = EnumVisible.HIDE_FOR_OWN_TEAM;
            ENUM$VALUES = enum$VALUES;
            EnumVisible.field_178828_g = (Map<String, EnumVisible>)Maps.newHashMap();
            final EnumVisible[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
            while (i < length) {
                final EnumVisible enumVisible = values[i];
                EnumVisible.field_178828_g.put(enumVisible.field_178830_e, enumVisible);
                ++i;
            }
        }
        
        private static void I() {
            (I = new String[0xCF ^ 0xC7])["".length()] = I(" \u00181832", "aTfyj");
            EnumVisible.I[" ".length()] = I("0\u0005.&\u0001\"", "QiYGx");
            EnumVisible.I["  ".length()] = I("\u0000\n\u0005<\u0014", "NOSyF");
            EnumVisible.I["   ".length()] = I("7\u00110\u0016\u0018", "YtFsj");
            EnumVisible.I[0x3D ^ 0x39] = I(";\u0019\u000b\u0003-5\u001f\u001d\u0019='\u0018\n\u0014-'\u0015\u000e\u000b!", "sPOFr");
            EnumVisible.I[0x88 ^ 0x8D] = I(",$\t\u001d\u000b+?\"\f%!?9\u001d,)>", "DMmxM");
            EnumVisible.I[0x74 ^ 0x72] = I("!\u001c\u0012<\u0007/\u001a\u0004&\u0017>\u001b\t-\u001d(\u0018", "iUVyX");
            EnumVisible.I[0x79 ^ 0x7E] = I("+\u000e\f\u00026,\u0015'\u0010\u001e\u0017\u0002\t\n", "Cghgp");
        }
        
        private EnumVisible(final String s, final int n, final String field_178830_e, final int field_178827_f) {
            this.field_178830_e = field_178830_e;
            this.field_178827_f = field_178827_f;
        }
        
        public static EnumVisible func_178824_a(final String s) {
            return EnumVisible.field_178828_g.get(s);
        }
        
        public static String[] func_178825_a() {
            return EnumVisible.field_178828_g.keySet().toArray(new String[EnumVisible.field_178828_g.size()]);
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
                if (3 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
