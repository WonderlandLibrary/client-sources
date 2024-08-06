package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.scoreboard.ScoreboardObjective;

public class ScoreboardEvent extends Event {

    public DrawContext context;
    public ScoreboardObjective objective;

    public ScoreboardEvent(DrawContext context, ScoreboardObjective objective) {
        this.context = context;
        this.objective = objective;
    }
}
