package net.minecraft.client.gui;

import java.util.*;
import net.minecraft.client.network.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.scoreboard.*;

static class PlayerComparator implements Comparator
{
    private PlayerComparator() {
    }
    
    PlayerComparator(final Object p_i45528_1_) {
        this();
    }
    
    public int func_178952_a(final NetworkPlayerInfo p_178952_1_, final NetworkPlayerInfo p_178952_2_) {
        final ScorePlayerTeam var3 = p_178952_1_.func_178850_i();
        final ScorePlayerTeam var4 = p_178952_2_.func_178850_i();
        return ComparisonChain.start().compareTrueFirst(p_178952_1_.getGameType() != WorldSettings.GameType.SPECTATOR, p_178952_2_.getGameType() != WorldSettings.GameType.SPECTATOR).compare((Comparable)((var3 != null) ? var3.getRegisteredName() : ""), (Comparable)((var4 != null) ? var4.getRegisteredName() : "")).compare((Comparable)p_178952_1_.func_178845_a().getName(), (Comparable)p_178952_2_.func_178845_a().getName()).result();
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.func_178952_a((NetworkPlayerInfo)p_compare_1_, (NetworkPlayerInfo)p_compare_2_);
    }
}
