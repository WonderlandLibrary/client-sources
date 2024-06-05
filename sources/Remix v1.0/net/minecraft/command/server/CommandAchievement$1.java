package net.minecraft.command.server;

import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;

class CommandAchievement$1 implements Predicate {
    final /* synthetic */ EntityPlayerMP val$var4;
    final /* synthetic */ StatBase val$var3;
    
    public boolean func_179605_a(final Achievement p_179605_1_) {
        return this.val$var4.getStatFile().hasAchievementUnlocked(p_179605_1_) && p_179605_1_ != this.val$var3;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_179605_a((Achievement)p_apply_1_);
    }
}