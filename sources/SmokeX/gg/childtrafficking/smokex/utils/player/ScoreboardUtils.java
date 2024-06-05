// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.player;

import net.minecraft.scoreboard.ScorePlayerTeam;
import java.util.Iterator;
import java.util.Collection;
import net.minecraft.scoreboard.Score;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.client.Minecraft;

public class ScoreboardUtils
{
    protected static Minecraft mc;
    
    public static String getScorebardTitle() {
        final Scoreboard scoreboard = ScoreboardUtils.mc.theWorld.getScoreboard();
        final ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
        if (objective != null) {
            return objective.getDisplayName();
        }
        return "";
    }
    
    public static List<String> getScoreboardData() {
        final Scoreboard scoreboard = ScoreboardUtils.mc.theWorld.getScoreboard();
        final List<String> found = new ArrayList<String>();
        final ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
        if (objective != null) {
            final List<Score> scores = new ArrayList<Score>(scoreboard.getScores());
            for (final Score score : scores) {
                if (score.getObjective() != null && score.getObjective().equals(objective)) {
                    final ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
                    if (team == null) {
                        continue;
                    }
                    found.add(team.getColorPrefix() + team.getColorSuffix());
                }
            }
        }
        return found;
    }
    
    static {
        ScoreboardUtils.mc = Minecraft.getMinecraft();
    }
}
