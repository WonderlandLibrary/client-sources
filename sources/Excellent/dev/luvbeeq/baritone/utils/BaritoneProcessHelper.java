package dev.luvbeeq.baritone.utils;

import dev.luvbeeq.baritone.Baritone;
import dev.luvbeeq.baritone.api.process.IBaritoneProcess;
import dev.luvbeeq.baritone.api.utils.Helper;
import dev.luvbeeq.baritone.api.utils.IPlayerContext;

public abstract class BaritoneProcessHelper implements IBaritoneProcess, Helper {

    protected final Baritone baritone;
    protected final IPlayerContext ctx;

    public BaritoneProcessHelper(Baritone baritone) {
        this.baritone = baritone;
        this.ctx = baritone.getPlayerContext();
    }

    @Override
    public boolean isTemporary() {
        return false;
    }
}
