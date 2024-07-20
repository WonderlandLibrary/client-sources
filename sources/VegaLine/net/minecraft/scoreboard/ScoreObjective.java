/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.scoreboard;

import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.Scoreboard;

public class ScoreObjective {
    private final Scoreboard theScoreboard;
    private final String name;
    private final IScoreCriteria objectiveCriteria;
    private IScoreCriteria.EnumRenderType renderType;
    private String displayName;

    public ScoreObjective(Scoreboard theScoreboardIn, String nameIn, IScoreCriteria objectiveCriteriaIn) {
        this.theScoreboard = theScoreboardIn;
        this.name = nameIn;
        this.objectiveCriteria = objectiveCriteriaIn;
        this.displayName = nameIn;
        this.renderType = objectiveCriteriaIn.getRenderType();
    }

    public Scoreboard getScoreboard() {
        return this.theScoreboard;
    }

    public String getName() {
        return this.name;
    }

    public IScoreCriteria getCriteria() {
        return this.objectiveCriteria;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String nameIn) {
        this.displayName = nameIn;
        this.theScoreboard.onObjectiveDisplayNameChanged(this);
    }

    public IScoreCriteria.EnumRenderType getRenderType() {
        return this.renderType;
    }

    public void setRenderType(IScoreCriteria.EnumRenderType type2) {
        this.renderType = type2;
        this.theScoreboard.onObjectiveDisplayNameChanged(this);
    }
}

