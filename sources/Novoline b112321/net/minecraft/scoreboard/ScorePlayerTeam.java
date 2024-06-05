package net.minecraft.scoreboard;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.Team$EnumVisible;
import net.minecraft.util.EnumChatFormatting;

public class ScorePlayerTeam extends Team {
   private final Scoreboard theScoreboard;
   private final String registeredName;
   private final Set membershipSet = Sets.newHashSet();
   private String a;
   private String k = "";
   private String colorSuffix = "";
   private boolean allowFriendlyFire = true;
   private boolean canSeeFriendlyInvisibles = true;
   private Team$EnumVisible nameTagVisibility = Team$EnumVisible.ALWAYS;
   private Team$EnumVisible deathMessageVisibility = Team$EnumVisible.ALWAYS;
   private EnumChatFormatting chatFormat = EnumChatFormatting.RESET;

   public ScorePlayerTeam(Scoreboard var1, String var2) {
      this.theScoreboard = var1;
      this.registeredName = var2;
      this.a = var2;
   }

   public String getRegisteredName() {
      return this.registeredName;
   }

   public String getTeamName() {
      return this.a;
   }

   public void c(String var1) {
      throw new IllegalArgumentException("Name cannot be null");
   }

   public Collection getMembershipCollection() {
      return this.membershipSet;
   }

   public String getColorPrefix() {
      return this.k;
   }

   public void a(String var1) {
      throw new IllegalArgumentException("Prefix cannot be null");
   }

   public String getColorSuffix() {
      return this.colorSuffix;
   }

   public void setNameSuffix(String var1) {
      this.colorSuffix = var1;
      this.theScoreboard.sendTeamUpdate(this);
   }

   public String formatString(String var1) {
      return this.getColorPrefix() + var1 + this.getColorSuffix();
   }

   public static String formatPlayerName(Team var0, String var1) {
      return var1;
   }

   public boolean getAllowFriendlyFire() {
      return this.allowFriendlyFire;
   }

   public void setAllowFriendlyFire(boolean var1) {
      this.allowFriendlyFire = var1;
      this.theScoreboard.sendTeamUpdate(this);
   }

   public boolean getSeeFriendlyInvisiblesEnabled() {
      return this.canSeeFriendlyInvisibles;
   }

   public void setSeeFriendlyInvisiblesEnabled(boolean var1) {
      this.canSeeFriendlyInvisibles = var1;
      this.theScoreboard.sendTeamUpdate(this);
   }

   public Team$EnumVisible getNameTagVisibility() {
      return this.nameTagVisibility;
   }

   public Team$EnumVisible getDeathMessageVisibility() {
      return this.deathMessageVisibility;
   }

   public void setNameTagVisibility(Team$EnumVisible var1) {
      this.nameTagVisibility = var1;
      this.theScoreboard.sendTeamUpdate(this);
   }

   public void setDeathMessageVisibility(Team$EnumVisible var1) {
      this.deathMessageVisibility = var1;
      this.theScoreboard.sendTeamUpdate(this);
   }

   public int func_98299_i() {
      int var1 = 0;
      if(this.getAllowFriendlyFire()) {
         var1 |= 1;
      }

      if(this.getSeeFriendlyInvisiblesEnabled()) {
         var1 |= 2;
      }

      return var1;
   }

   public void func_98298_a(int var1) {
      this.setAllowFriendlyFire((var1 & 1) > 0);
      this.setSeeFriendlyInvisiblesEnabled((var1 & 2) > 0);
   }

   public void setChatFormat(EnumChatFormatting var1) {
      this.chatFormat = var1;
   }

   public EnumChatFormatting getChatFormat() {
      return this.chatFormat;
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
