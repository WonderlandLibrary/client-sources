package net.minecraft.client.gui.spectator.categories;

import java.util.*;
import net.minecraft.client.network.*;
import com.google.common.collect.*;

static final class TeleportToPlayer$1 implements Comparator {
    public int func_178746_a(final NetworkPlayerInfo p_178746_1_, final NetworkPlayerInfo p_178746_2_) {
        return ComparisonChain.start().compare((Comparable)p_178746_1_.func_178845_a().getId(), (Comparable)p_178746_2_.func_178845_a().getId()).result();
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.func_178746_a((NetworkPlayerInfo)p_compare_1_, (NetworkPlayerInfo)p_compare_2_);
    }
}