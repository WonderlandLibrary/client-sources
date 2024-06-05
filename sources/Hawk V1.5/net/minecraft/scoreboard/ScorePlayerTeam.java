package net.minecraft.scoreboard;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.util.EnumChatFormatting;

public class ScorePlayerTeam extends Team {
   private final Scoreboard theScoreboard;
   private String namePrefixSPT = "";
   private final Set membershipSet = Sets.newHashSet();
   private static final String __OBFID = "CL_00000616";
   private String teamNameSPT;
   private Team.EnumVisible field_178778_i;
   private String colorSuffix = "";
   private Team.EnumVisible field_178776_j;
   private EnumChatFormatting field_178777_k;
   private final String field_96675_b;
   private boolean canSeeFriendlyInvisibles = true;
   private boolean allowFriendlyFire = true;

   public ScorePlayerTeam(Scoreboard var1, String var2) {
      this.field_178778_i = Team.EnumVisible.ALWAYS;
      this.field_178776_j = Team.EnumVisible.ALWAYS;
      this.field_178777_k = EnumChatFormatting.RESET;
      this.theScoreboard = var1;
      this.field_96675_b = var2;
      this.teamNameSPT = var2;
   }

   public static String formatPlayerName(Team var0, String var1) {
      return var0 == null ? var1 : var0.formatString(var1);
   }

   public String getColorSuffix() {
      return this.colorSuffix;
   }

   public void func_98298_a(int var1) {
      this.setAllowFriendlyFire((var1 & 1) > 0);
      this.setSeeFriendlyInvisiblesEnabled((var1 & 2) > 0);
   }

   public void func_178774_a(EnumChatFormatting var1) {
      this.field_178777_k = var1;
   }

   public EnumChatFormatting func_178775_l() {
      return this.field_178777_k;
   }

   public void setSeeFriendlyInvisiblesEnabled(boolean var1) {
      this.canSeeFriendlyInvisibles = var1;
      this.theScoreboard.broadcastTeamRemoved(this);
   }

   public int func_98299_i() {
      int var1 = 0;
      if (this.getAllowFriendlyFire()) {
         var1 |= 1;
      }

      if (this.func_98297_h()) {
         var1 |= 2;
      }

      return var1;
   }

   public Team.EnumVisible func_178770_i() {
      return this.field_178778_i;
   }

   public String getColorPrefix() {
      return this.namePrefixSPT;
   }

   public Team.EnumVisible func_178771_j() {
      return this.field_178776_j;
   }

   public String func_96669_c() {
      return this.teamNameSPT;
   }

   public void setNameSuffix(String var1) {
      this.colorSuffix = var1;
      this.theScoreboard.broadcastTeamRemoved(this);
   }

   public void setAllowFriendlyFire(boolean var1) {
      this.allowFriendlyFire = var1;
      this.theScoreboard.broadcastTeamRemoved(this);
   }

   public String formatString(String var1) {
      return String.valueOf((new StringBuilder(String.valueOf(this.getColorPrefix()))).append(var1).append(this.getColorSuffix()));
   }

   public boolean getAllowFriendlyFire() {
      return this.allowFriendlyFire;
   }

   public void func_178773_b(Team.EnumVisible var1) {
      this.field_178776_j = var1;
      this.theScoreboard.broadcastTeamRemoved(this);
   }

   public String getRegisteredName() {
      return this.field_96675_b;
   }

   public void func_178772_a(Team.EnumVisible var1) {
      this.field_178778_i = var1;
      this.theScoreboard.broadcastTeamRemoved(this);
   }

   public Collection getMembershipCollection() {
      return this.membershipSet;
   }

   public void setTeamName(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Name cannot be null");
      } else {
         this.teamNameSPT = var1;
         this.theScoreboard.broadcastTeamRemoved(this);
      }
   }

   public boolean func_98297_h() {
      return this.canSeeFriendlyInvisibles;
   }

   public void setNamePrefix(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Prefix cannot be null");
      } else {
         this.namePrefixSPT = var1;
         this.theScoreboard.broadcastTeamRemoved(this);
      }
   }
}
