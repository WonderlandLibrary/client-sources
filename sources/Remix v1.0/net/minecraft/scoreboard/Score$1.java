package net.minecraft.scoreboard;

import java.util.*;

static final class Score$1 implements Comparator {
    public int compare(final Score p_compare_1_, final Score p_compare_2_) {
        return (p_compare_1_.getScorePoints() > p_compare_2_.getScorePoints()) ? 1 : ((p_compare_1_.getScorePoints() < p_compare_2_.getScorePoints()) ? -1 : p_compare_2_.getPlayerName().compareToIgnoreCase(p_compare_1_.getPlayerName()));
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.compare((Score)p_compare_1_, (Score)p_compare_2_);
    }
}