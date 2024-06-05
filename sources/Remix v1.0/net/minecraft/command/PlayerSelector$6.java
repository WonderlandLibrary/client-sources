package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.scoreboard.*;

static final class PlayerSelector$6 implements Predicate {
    final /* synthetic */ Map val$var2;
    
    public boolean func_179603_a(final Entity p_179603_1_) {
        final Scoreboard var2x = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
        for (final Map.Entry var4 : this.val$var2.entrySet()) {
            String var5 = var4.getKey();
            boolean var6 = false;
            if (var5.endsWith("_min") && var5.length() > 4) {
                var6 = true;
                var5 = var5.substring(0, var5.length() - 4);
            }
            final ScoreObjective var7 = var2x.getObjective(var5);
            if (var7 == null) {
                return false;
            }
            final String var8 = (p_179603_1_ instanceof EntityPlayerMP) ? p_179603_1_.getName() : p_179603_1_.getUniqueID().toString();
            if (!var2x.func_178819_b(var8, var7)) {
                return false;
            }
            final Score var9 = var2x.getValueFromObjective(var8, var7);
            final int var10 = var9.getScorePoints();
            if (var10 < var4.getValue() && var6) {
                return false;
            }
            if (var10 > var4.getValue() && !var6) {
                return false;
            }
        }
        return true;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179603_a((Entity)p_apply_1_);
    }
}