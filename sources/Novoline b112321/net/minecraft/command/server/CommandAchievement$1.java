package net.minecraft.command.server;

import com.google.common.base.Predicate;
import net.FO;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;

class CommandAchievement$1 implements Predicate {
   final EntityPlayerMP val$entityplayermp;
   final StatBase val$statbase;
   final FO b;

   CommandAchievement$1(FO var1, EntityPlayerMP var2, StatBase var3) {
      this.b = var1;
      this.val$entityplayermp = var2;
      this.val$statbase = var3;
   }

   public boolean apply(Achievement var1) {
      return this.val$entityplayermp.getStatFile().hasAchievementUnlocked(var1) && var1 != this.val$statbase;
   }
}
