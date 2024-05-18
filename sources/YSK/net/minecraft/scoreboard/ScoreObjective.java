package net.minecraft.scoreboard;

public class ScoreObjective
{
    private String displayName;
    private IScoreObjectiveCriteria.EnumRenderType renderType;
    private final String name;
    private final IScoreObjectiveCriteria objectiveCriteria;
    private final Scoreboard theScoreboard;
    
    public String getName() {
        return this.name;
    }
    
    public void setRenderType(final IScoreObjectiveCriteria.EnumRenderType renderType) {
        this.renderType = renderType;
        this.theScoreboard.func_96532_b(this);
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
            if (4 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
        return this.renderType;
    }
    
    public ScoreObjective(final Scoreboard theScoreboard, final String s, final IScoreObjectiveCriteria objectiveCriteria) {
        this.theScoreboard = theScoreboard;
        this.name = s;
        this.objectiveCriteria = objectiveCriteria;
        this.displayName = s;
        this.renderType = objectiveCriteria.getRenderType();
    }
    
    public Scoreboard getScoreboard() {
        return this.theScoreboard;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public IScoreObjectiveCriteria getCriteria() {
        return this.objectiveCriteria;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
        this.theScoreboard.func_96532_b(this);
    }
}
