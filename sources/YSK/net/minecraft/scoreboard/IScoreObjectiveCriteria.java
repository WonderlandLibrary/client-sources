package net.minecraft.scoreboard;

import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.player.*;

public interface IScoreObjectiveCriteria
{
    public static final IScoreObjectiveCriteria health = new ScoreHealthCriteria("health");
    public static final IScoreObjectiveCriteria totalKillCount = new ScoreDummyCriteria("totalKillCount");
    public static final IScoreObjectiveCriteria DUMMY = new ScoreDummyCriteria("dummy");
    public static final IScoreObjectiveCriteria[] field_178793_i = field_178793_i2;
    public static final IScoreObjectiveCriteria playerKillCount = new ScoreDummyCriteria("playerKillCount");
    public static final IScoreObjectiveCriteria deathCount = new ScoreDummyCriteria("deathCount");
    public static final IScoreObjectiveCriteria[] field_178792_h = field_178792_h2;
    public static final Map<String, IScoreObjectiveCriteria> INSTANCES = Maps.newHashMap();
    public static final IScoreObjectiveCriteria TRIGGER = new ScoreDummyCriteria("trigger");
    
    EnumRenderType getRenderType();
    
    String getName();
    
    boolean isReadOnly();
    
    default static {
        final IScoreObjectiveCriteria[] field_178792_h2 = new IScoreObjectiveCriteria[0x31 ^ 0x21];
        field_178792_h2["".length()] = new GoalColor("teamkill.", EnumChatFormatting.BLACK);
        field_178792_h2[" ".length()] = new GoalColor("teamkill.", EnumChatFormatting.DARK_BLUE);
        field_178792_h2["  ".length()] = new GoalColor("teamkill.", EnumChatFormatting.DARK_GREEN);
        field_178792_h2["   ".length()] = new GoalColor("teamkill.", EnumChatFormatting.DARK_AQUA);
        field_178792_h2[0x2A ^ 0x2E] = new GoalColor("teamkill.", EnumChatFormatting.DARK_RED);
        field_178792_h2[0x22 ^ 0x27] = new GoalColor("teamkill.", EnumChatFormatting.DARK_PURPLE);
        field_178792_h2[0x26 ^ 0x20] = new GoalColor("teamkill.", EnumChatFormatting.GOLD);
        field_178792_h2[0xC4 ^ 0xC3] = new GoalColor("teamkill.", EnumChatFormatting.GRAY);
        field_178792_h2[0xB4 ^ 0xBC] = new GoalColor("teamkill.", EnumChatFormatting.DARK_GRAY);
        field_178792_h2[0x32 ^ 0x3B] = new GoalColor("teamkill.", EnumChatFormatting.BLUE);
        field_178792_h2[0x63 ^ 0x69] = new GoalColor("teamkill.", EnumChatFormatting.GREEN);
        field_178792_h2[0xB1 ^ 0xBA] = new GoalColor("teamkill.", EnumChatFormatting.AQUA);
        field_178792_h2[0x3F ^ 0x33] = new GoalColor("teamkill.", EnumChatFormatting.RED);
        field_178792_h2[0xCF ^ 0xC2] = new GoalColor("teamkill.", EnumChatFormatting.LIGHT_PURPLE);
        field_178792_h2[0x95 ^ 0x9B] = new GoalColor("teamkill.", EnumChatFormatting.YELLOW);
        field_178792_h2[0x81 ^ 0x8E] = new GoalColor("teamkill.", EnumChatFormatting.WHITE);
        final IScoreObjectiveCriteria[] field_178793_i2 = new IScoreObjectiveCriteria[0x27 ^ 0x37];
        field_178793_i2["".length()] = new GoalColor("killedByTeam.", EnumChatFormatting.BLACK);
        field_178793_i2[" ".length()] = new GoalColor("killedByTeam.", EnumChatFormatting.DARK_BLUE);
        field_178793_i2["  ".length()] = new GoalColor("killedByTeam.", EnumChatFormatting.DARK_GREEN);
        field_178793_i2["   ".length()] = new GoalColor("killedByTeam.", EnumChatFormatting.DARK_AQUA);
        field_178793_i2[0x90 ^ 0x94] = new GoalColor("killedByTeam.", EnumChatFormatting.DARK_RED);
        field_178793_i2[0x33 ^ 0x36] = new GoalColor("killedByTeam.", EnumChatFormatting.DARK_PURPLE);
        field_178793_i2[0xB3 ^ 0xB5] = new GoalColor("killedByTeam.", EnumChatFormatting.GOLD);
        field_178793_i2[0x93 ^ 0x94] = new GoalColor("killedByTeam.", EnumChatFormatting.GRAY);
        field_178793_i2[0x26 ^ 0x2E] = new GoalColor("killedByTeam.", EnumChatFormatting.DARK_GRAY);
        field_178793_i2[0x47 ^ 0x4E] = new GoalColor("killedByTeam.", EnumChatFormatting.BLUE);
        field_178793_i2[0x5F ^ 0x55] = new GoalColor("killedByTeam.", EnumChatFormatting.GREEN);
        field_178793_i2[0xCA ^ 0xC1] = new GoalColor("killedByTeam.", EnumChatFormatting.AQUA);
        field_178793_i2[0x22 ^ 0x2E] = new GoalColor("killedByTeam.", EnumChatFormatting.RED);
        field_178793_i2[0x29 ^ 0x24] = new GoalColor("killedByTeam.", EnumChatFormatting.LIGHT_PURPLE);
        field_178793_i2[0x5D ^ 0x53] = new GoalColor("killedByTeam.", EnumChatFormatting.YELLOW);
        field_178793_i2[0x2F ^ 0x20] = new GoalColor("killedByTeam.", EnumChatFormatting.WHITE);
    }
    
    int func_96635_a(final List<EntityPlayer> p0);
    
    public enum EnumRenderType
    {
        HEARTS(EnumRenderType.I["  ".length()], " ".length(), EnumRenderType.I["   ".length()]);
        
        private final String field_178798_d;
        
        INTEGER(EnumRenderType.I["".length()], "".length(), EnumRenderType.I[" ".length()]);
        
        private static final EnumRenderType[] ENUM$VALUES;
        private static final String[] I;
        private static final Map<String, EnumRenderType> field_178801_c;
        
        private EnumRenderType(final String s, final int n, final String field_178798_d) {
            this.field_178798_d = field_178798_d;
        }
        
        private static void I() {
            (I = new String[0xBE ^ 0xBA])["".length()] = I("\u0005\f\u001b5\u0010\t\u0010", "LBOpW");
            EnumRenderType.I[" ".length()] = I("\u000f)\u001f\u0001(\u00035", "fGkdO");
            EnumRenderType.I["  ".length()] = I(" 4\u0017\u001d!;", "hqVOu");
            EnumRenderType.I["   ".length()] = I("\u001b*\u0000\u0004\u0004\u0000", "sOavp");
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
                if (4 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public String func_178796_a() {
            return this.field_178798_d;
        }
        
        public static EnumRenderType func_178795_a(final String s) {
            final EnumRenderType enumRenderType = EnumRenderType.field_178801_c.get(s);
            EnumRenderType integer;
            if (enumRenderType == null) {
                integer = EnumRenderType.INTEGER;
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                integer = enumRenderType;
            }
            return integer;
        }
        
        static {
            I();
            final EnumRenderType[] enum$VALUES = new EnumRenderType["  ".length()];
            enum$VALUES["".length()] = EnumRenderType.INTEGER;
            enum$VALUES[" ".length()] = EnumRenderType.HEARTS;
            ENUM$VALUES = enum$VALUES;
            field_178801_c = Maps.newHashMap();
            final EnumRenderType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
            while (i < length) {
                final EnumRenderType enumRenderType = values[i];
                EnumRenderType.field_178801_c.put(enumRenderType.func_178796_a(), enumRenderType);
                ++i;
            }
        }
    }
}
