package net.minecraft.scoreboard;

import java.util.Collection;
import net.minecraft.scoreboard.Team$EnumVisible;

public abstract class Team {
   public boolean isSameTeam(Team var1) {
      return this == var1;
   }

   public abstract String getRegisteredName();

   public abstract String formatString(String var1);

   public abstract boolean getSeeFriendlyInvisiblesEnabled();

   public abstract boolean getAllowFriendlyFire();

   public abstract Team$EnumVisible getNameTagVisibility();

   public abstract Collection getMembershipCollection();

   public abstract Team$EnumVisible getDeathMessageVisibility();
}
