package net.minecraft.scoreboard;

import java.util.*;
import net.minecraft.entity.player.*;

public class Score
{
    private final String scorePlayerName;
    private boolean field_178818_g;
    private int scorePoints;
    private boolean locked;
    private final ScoreObjective theScoreObjective;
    public static final Comparator<Score> scoreComparator;
    private final Scoreboard theScoreboard;
    private static final String[] I;
    
    public void setScorePoints(final int scorePoints) {
        final int scorePoints2 = this.scorePoints;
        this.scorePoints = scorePoints;
        if (scorePoints2 != scorePoints || this.field_178818_g) {
            this.field_178818_g = ("".length() != 0);
            this.getScoreScoreboard().func_96536_a(this);
        }
    }
    
    public Score(final Scoreboard theScoreboard, final ScoreObjective theScoreObjective, final String scorePlayerName) {
        this.theScoreboard = theScoreboard;
        this.theScoreObjective = theScoreObjective;
        this.scorePlayerName = scorePlayerName;
        this.field_178818_g = (" ".length() != 0);
    }
    
    public String getPlayerName() {
        return this.scorePlayerName;
    }
    
    public void increseScore(final int n) {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException(Score.I["".length()]);
        }
        this.setScorePoints(this.getScorePoints() + n);
    }
    
    public void func_96651_a(final List<EntityPlayer> list) {
        this.setScorePoints(this.theScoreObjective.getCriteria().func_96635_a(list));
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
    
    public void func_96648_a() {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException(Score.I["  ".length()]);
        }
        this.increseScore(" ".length());
    }
    
    public void setLocked(final boolean locked) {
        this.locked = locked;
    }
    
    public Scoreboard getScoreScoreboard() {
        return this.theScoreboard;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0006$6!\r1e5 \u0006,#!o\u0010 $<b\r+)!o\u0011&***", "EEXOb");
        Score.I[" ".length()] = I("!, \u001d\r\u0016m#\u001c\u0006\u000b+7S\u0010\u0007,*^\r\f!7S\u0011\u0001\"<\u0016", "bMNsb");
        Score.I["  ".length()] = I("\b2\"#8?s!\"3\"55m%.2(`8%?5m$(<>(", "KSLMW");
    }
    
    public void decreaseScore(final int n) {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException(Score.I[" ".length()]);
        }
        this.setScorePoints(this.getScorePoints() - n);
    }
    
    static {
        I();
        scoreComparator = new Comparator<Score>() {
            @Override
            public int compare(final Score score, final Score score2) {
                int n;
                if (score.getScorePoints() > score2.getScorePoints()) {
                    n = " ".length();
                    "".length();
                    if (-1 == 2) {
                        throw null;
                    }
                }
                else if (score.getScorePoints() < score2.getScorePoints()) {
                    n = -" ".length();
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                }
                else {
                    n = score2.getPlayerName().compareToIgnoreCase(score.getPlayerName());
                }
                return n;
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((Score)o, (Score)o2);
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
                    if (4 < 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
    }
    
    public int getScorePoints() {
        return this.scorePoints;
    }
    
    public ScoreObjective getObjective() {
        return this.theScoreObjective;
    }
    
    public boolean isLocked() {
        return this.locked;
    }
}
