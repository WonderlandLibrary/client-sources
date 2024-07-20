/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.scoreboard;

import net.minecraft.scoreboard.ScoreCriteria;

public class ScoreCriteriaReadOnly
extends ScoreCriteria {
    public ScoreCriteriaReadOnly(String name) {
        super(name);
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }
}

