package net.minecraft.scoreboard;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class ScorePlayerTeam extends Team
{
    private boolean canSeeFriendlyInvisibles;
    private final Set<String> membershipSet;
    private String teamNameSPT;
    private EnumVisible deathMessageVisibility;
    private static final String[] I;
    private EnumChatFormatting chatFormat;
    private String colorSuffix;
    private boolean allowFriendlyFire;
    private EnumVisible nameTagVisibility;
    private String namePrefixSPT;
    private final String registeredName;
    private final Scoreboard theScoreboard;
    
    public void setAllowFriendlyFire(final boolean allowFriendlyFire) {
        this.allowFriendlyFire = allowFriendlyFire;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    public static String formatPlayerName(final Team team, final String s) {
        String formatString;
        if (team == null) {
            formatString = s;
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            formatString = team.formatString(s);
        }
        return formatString;
    }
    
    public EnumChatFormatting getChatFormat() {
        return this.chatFormat;
    }
    
    public void func_98298_a(final int n) {
        int allowFriendlyFire;
        if ((n & " ".length()) > 0) {
            allowFriendlyFire = " ".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            allowFriendlyFire = "".length();
        }
        this.setAllowFriendlyFire(allowFriendlyFire != 0);
        int seeFriendlyInvisiblesEnabled;
        if ((n & "  ".length()) > 0) {
            seeFriendlyInvisiblesEnabled = " ".length();
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else {
            seeFriendlyInvisiblesEnabled = "".length();
        }
        this.setSeeFriendlyInvisiblesEnabled(seeFriendlyInvisiblesEnabled != 0);
    }
    
    @Override
    public String formatString(final String s) {
        return String.valueOf(this.getColorPrefix()) + s + this.getColorSuffix();
    }
    
    private static void I() {
        (I = new String[0x36 ^ 0x32])["".length()] = I("", "UvUCO");
        ScorePlayerTeam.I[" ".length()] = I("", "gyyTn");
        ScorePlayerTeam.I["  ".length()] = I("\u000f\n=2w\"\n>985K22w/\u001e<;", "AkPWW");
        ScorePlayerTeam.I["   ".length()] = I("\u001a\"2\u0003 2p4\u0004'$?#E+/p9\u0010%&", "JPWeI");
    }
    
    @Override
    public Collection<String> getMembershipCollection() {
        return this.membershipSet;
    }
    
    public void setDeathMessageVisibility(final EnumVisible deathMessageVisibility) {
        this.deathMessageVisibility = deathMessageVisibility;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    public String getColorSuffix() {
        return this.colorSuffix;
    }
    
    public ScorePlayerTeam(final Scoreboard theScoreboard, final String s) {
        this.membershipSet = (Set<String>)Sets.newHashSet();
        this.namePrefixSPT = ScorePlayerTeam.I["".length()];
        this.colorSuffix = ScorePlayerTeam.I[" ".length()];
        this.allowFriendlyFire = (" ".length() != 0);
        this.canSeeFriendlyInvisibles = (" ".length() != 0);
        this.nameTagVisibility = EnumVisible.ALWAYS;
        this.deathMessageVisibility = EnumVisible.ALWAYS;
        this.chatFormat = EnumChatFormatting.RESET;
        this.theScoreboard = theScoreboard;
        this.registeredName = s;
        this.teamNameSPT = s;
    }
    
    public int func_98299_i() {
        int length = "".length();
        if (this.getAllowFriendlyFire()) {
            length |= " ".length();
        }
        if (this.getSeeFriendlyInvisiblesEnabled()) {
            length |= "  ".length();
        }
        return length;
    }
    
    @Override
    public EnumVisible getDeathMessageVisibility() {
        return this.deathMessageVisibility;
    }
    
    @Override
    public EnumVisible getNameTagVisibility() {
        return this.nameTagVisibility;
    }
    
    static {
        I();
    }
    
    public void setSeeFriendlyInvisiblesEnabled(final boolean canSeeFriendlyInvisibles) {
        this.canSeeFriendlyInvisibles = canSeeFriendlyInvisibles;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    public String getTeamName() {
        return this.teamNameSPT;
    }
    
    @Override
    public boolean getSeeFriendlyInvisiblesEnabled() {
        return this.canSeeFriendlyInvisibles;
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean getAllowFriendlyFire() {
        return this.allowFriendlyFire;
    }
    
    @Override
    public String getRegisteredName() {
        return this.registeredName;
    }
    
    public String getColorPrefix() {
        return this.namePrefixSPT;
    }
    
    public void setTeamName(final String teamNameSPT) {
        if (teamNameSPT == null) {
            throw new IllegalArgumentException(ScorePlayerTeam.I["  ".length()]);
        }
        this.teamNameSPT = teamNameSPT;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    public void setNameTagVisibility(final EnumVisible nameTagVisibility) {
        this.nameTagVisibility = nameTagVisibility;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    public void setNameSuffix(final String colorSuffix) {
        this.colorSuffix = colorSuffix;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    public void setNamePrefix(final String namePrefixSPT) {
        if (namePrefixSPT == null) {
            throw new IllegalArgumentException(ScorePlayerTeam.I["   ".length()]);
        }
        this.namePrefixSPT = namePrefixSPT;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    public void setChatFormat(final EnumChatFormatting chatFormat) {
        this.chatFormat = chatFormat;
    }
}
