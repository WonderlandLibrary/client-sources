/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.scoreboard;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

public class Score {
    public static final Comparator<Score> SCORE_COMPARATOR = Score::lambda$static$0;
    private final Scoreboard scoreboard;
    @Nullable
    private final ScoreObjective objective;
    private final String scorePlayerName;
    private int scorePoints;
    private boolean locked;
    private boolean forceUpdate;

    public Score(Scoreboard scoreboard, ScoreObjective scoreObjective, String string) {
        this.scoreboard = scoreboard;
        this.objective = scoreObjective;
        this.scorePlayerName = string;
        this.locked = true;
        this.forceUpdate = true;
    }

    public void increaseScore(int n) {
        if (this.objective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.setScorePoints(this.getScorePoints() + n);
    }

    public void incrementScore() {
        this.increaseScore(1);
    }

    public int getScorePoints() {
        return this.scorePoints;
    }

    public void reset() {
        this.setScorePoints(0);
    }

    public void setScorePoints(int n) {
        int n2 = this.scorePoints;
        this.scorePoints = n;
        if (n2 != n || this.forceUpdate) {
            this.forceUpdate = false;
            this.getScoreScoreboard().onScoreChanged(this);
        }
    }

    @Nullable
    public ScoreObjective getObjective() {
        return this.objective;
    }

    public String getPlayerName() {
        return this.scorePlayerName;
    }

    public Scoreboard getScoreScoreboard() {
        return this.scoreboard;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean bl) {
        this.locked = bl;
    }

    private static int lambda$static$0(Score score, Score score2) {
        if (score.getScorePoints() > score2.getScorePoints()) {
            return 0;
        }
        return score.getScorePoints() < score2.getScorePoints() ? -1 : score2.getPlayerName().compareToIgnoreCase(score.getPlayerName());
    }
}

