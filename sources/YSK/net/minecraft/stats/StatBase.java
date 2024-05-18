package net.minecraft.stats;

import java.text.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.event.*;

public class StatBase
{
    public static IStatType field_111202_k;
    private static NumberFormat numberFormat;
    public static IStatType timeStatType;
    private final IChatComponent statName;
    private static final String[] I;
    private Class<? extends IJsonSerializable> field_150956_d;
    public static IStatType distanceStatType;
    private final IStatType type;
    public final String statId;
    public boolean isIndependent;
    public static IStatType simpleStatType;
    private static DecimalFormat decimalFormat;
    private final IScoreObjectiveCriteria field_150957_c;
    
    public IScoreObjectiveCriteria func_150952_k() {
        return this.field_150957_c;
    }
    
    public StatBase(final String s, final IChatComponent chatComponent) {
        this(s, chatComponent, StatBase.simpleStatType);
    }
    
    public StatBase initIndependentStat() {
        this.isIndependent = (" ".length() != 0);
        return this;
    }
    
    public StatBase func_150953_b(final Class<? extends IJsonSerializable> field_150956_d) {
        this.field_150956_d = field_150956_d;
        return this;
    }
    
    static DecimalFormat access$1() {
        return StatBase.decimalFormat;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (o != null && this.getClass() == o.getClass()) {
            return this.statId.equals(((StatBase)o).statId);
        }
        return "".length() != 0;
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        StatBase.numberFormat = NumberFormat.getIntegerInstance(Locale.US);
        StatBase.simpleStatType = new IStatType() {
            @Override
            public String format(final int n) {
                return StatBase.access$0().format(n);
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
                    if (true != true) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        StatBase.decimalFormat = new DecimalFormat(StatBase.I["".length()]);
        StatBase.timeStatType = new IStatType() {
            private static final String[] I;
            
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
                    if (4 == 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String[0x65 ^ 0x60])["".length()] = I("M\u001b", "mbYTV");
                StatBase$2.I[" ".length()] = I("M\f", "mhzeq");
                StatBase$2.I["  ".length()] = I("C\u001a", "crLEz");
                StatBase$2.I["   ".length()] = I("B:", "bWZlv");
                StatBase$2.I[0x43 ^ 0x47] = I("b\u0010", "BcjAu");
            }
            
            static {
                I();
            }
            
            @Override
            public String format(final int n) {
                final double n2 = n / 20.0;
                final double n3 = n2 / 60.0;
                final double n4 = n3 / 60.0;
                final double n5 = n4 / 24.0;
                final double n6 = n5 / 365.0;
                String s;
                if (n6 > 0.5) {
                    s = String.valueOf(StatBase.access$1().format(n6)) + StatBase$2.I["".length()];
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                }
                else if (n5 > 0.5) {
                    s = String.valueOf(StatBase.access$1().format(n5)) + StatBase$2.I[" ".length()];
                    "".length();
                    if (2 < -1) {
                        throw null;
                    }
                }
                else if (n4 > 0.5) {
                    s = String.valueOf(StatBase.access$1().format(n4)) + StatBase$2.I["  ".length()];
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                }
                else if (n3 > 0.5) {
                    s = String.valueOf(StatBase.access$1().format(n3)) + StatBase$2.I["   ".length()];
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
                else {
                    s = String.valueOf(n2) + StatBase$2.I[0x8F ^ 0x8B];
                }
                return s;
            }
        };
        StatBase.distanceStatType = new IStatType() {
            private static final String[] I;
            
            private static void I() {
                (I = new String["   ".length()])["".length()] = I("A>.", "aUCjP");
                StatBase$3.I[" ".length()] = I("l8", "LUmYO");
                StatBase$3.I["  ".length()] = I("K\u0007\u0018", "kdudG");
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
                    if (3 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String format(final int n) {
                final double n2 = n / 100.0;
                final double n3 = n2 / 1000.0;
                String s;
                if (n3 > 0.5) {
                    s = String.valueOf(StatBase.access$1().format(n3)) + StatBase$3.I["".length()];
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
                else if (n2 > 0.5) {
                    s = String.valueOf(StatBase.access$1().format(n2)) + StatBase$3.I[" ".length()];
                    "".length();
                    if (2 == 1) {
                        throw null;
                    }
                }
                else {
                    s = String.valueOf(n) + StatBase$3.I["  ".length()];
                }
                return s;
            }
            
            static {
                I();
            }
        };
        StatBase.field_111202_k = new IStatType() {
            @Override
            public String format(final int n) {
                return StatBase.access$1().format(n * 0.1);
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
        };
    }
    
    @Override
    public int hashCode() {
        return this.statId.hashCode();
    }
    
    public IChatComponent func_150955_j() {
        final IChatComponent statName = this.getStatName();
        final IChatComponent appendText = new ChatComponentText(StatBase.I[0x5B ^ 0x5F]).appendSibling(statName).appendText(StatBase.I[0x68 ^ 0x6D]);
        appendText.setChatStyle(statName.getChatStyle());
        return appendText;
    }
    
    public boolean isAchievement() {
        return "".length() != 0;
    }
    
    public StatBase(final String statId, final IChatComponent statName, final IStatType type) {
        this.statId = statId;
        this.statName = statName;
        this.type = type;
        this.field_150957_c = new ObjectiveStat(this);
        IScoreObjectiveCriteria.INSTANCES.put(this.field_150957_c.getName(), this.field_150957_c);
    }
    
    static NumberFormat access$0() {
        return StatBase.numberFormat;
    }
    
    public Class<? extends IJsonSerializable> func_150954_l() {
        return this.field_150956_d;
    }
    
    public IChatComponent getStatName() {
        final IChatComponent copy = this.statName.createCopy();
        copy.getChatStyle().setColor(EnumChatFormatting.GRAY);
        copy.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new ChatComponentText(this.statId)));
        return copy;
    }
    
    public String format(final int n) {
        return this.type.format(n);
    }
    
    @Override
    public String toString() {
        return StatBase.I[0x4A ^ 0x4C] + this.statId + StatBase.I[0x54 ^ 0x53] + this.statName + StatBase.I[0x69 ^ 0x61] + this.isIndependent + StatBase.I[0x13 ^ 0x1A] + this.type + StatBase.I[0x3C ^ 0x36] + this.field_150957_c + (char)(0xCA ^ 0xB7);
    }
    
    public StatBase registerStat() {
        if (StatList.oneShotStats.containsKey(this.statId)) {
            throw new RuntimeException(StatBase.I[" ".length()] + StatList.oneShotStats.get(this.statId).statName + StatBase.I["  ".length()] + this.statName + StatBase.I["   ".length()] + this.statId);
        }
        StatList.allStats.add(this);
        StatList.oneShotStats.put(this.statId, this);
        return this;
    }
    
    private static void I() {
        (I = new String[0x4 ^ 0xF])["".length()] = I("GEVbPGEVq]TV", "dfuAs");
        StatBase.I[" ".length()] = I("=\r5:*\u001a\u001913c\n\f$\"c\u0010\u001c\u007fva", "yxEVC");
        StatBase.I["  ".length()] = I("xY\u0007\u000f+z[", "ZyfaO");
        StatBase.I["   ".length()] = I("Ct\u000b6U\b0J", "aTjBu");
        StatBase.I[0xE ^ 0xA] = I("\u0015", "NzxhK");
        StatBase.I[0x13 ^ 0x16] = I("\u001a", "GAeDM");
        StatBase.I[0x68 ^ 0x6E] = I("!\u001b'./\u001b\u000b{", "roFZT");
        StatBase.I[0x91 ^ 0x96] = I("Fq(\u0005\t\u000f\u0018\"Y", "jQFdd");
        StatBase.I[0xA7 ^ 0xAF] = I("or3!\u000b16\u001e9\t\">>/%->+k", "CRRVj");
        StatBase.I[0x21 ^ 0x28] = I("_V\r\u001d\b\u001e\u0017\u001f\u0006\u001f\u0001K", "svkrz");
        StatBase.I[0x6D ^ 0x67] = I("VM\u0003\u0004;\u001f\u000e\u0018\u000f'\u001f.\u001e\u000f%\u001f\u001f\u0005\u0007l", "zmlfQ");
    }
}
