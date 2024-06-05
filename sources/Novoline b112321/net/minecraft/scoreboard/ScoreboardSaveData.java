package net.minecraft.scoreboard;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.IScoreObjectiveCriteria$EnumRenderType;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team$EnumVisible;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreboardSaveData extends WorldSavedData {
   private static final Logger LOGGER = LogManager.getLogger();
   private Scoreboard theScoreboard;
   private NBTTagCompound delayedInitNbt;

   public ScoreboardSaveData() {
      this("scoreboard");
   }

   public ScoreboardSaveData(String var1) {
      super(var1);
   }

   public void setScoreboard(Scoreboard var1) {
      this.theScoreboard = var1;
      if(this.delayedInitNbt != null) {
         this.readFromNBT(this.delayedInitNbt);
      }

   }

   public void readFromNBT(NBTTagCompound var1) {
      if(this.theScoreboard == null) {
         this.delayedInitNbt = var1;
      } else {
         this.readObjectives(var1.getTagList("Objectives", 10));
         this.readScores(var1.getTagList("PlayerScores", 10));
         if(var1.hasKey("DisplaySlots", 10)) {
            this.readDisplayConfig(var1.getCompoundTag("DisplaySlots"));
         }

         if(var1.hasKey("Teams", 9)) {
            this.readTeams(var1.getTagList("Teams", 10));
         }
      }

   }

   protected void readTeams(NBTTagList var1) {
      for(int var2 = 0; var2 < var1.tagCount(); ++var2) {
         NBTTagCompound var3 = var1.getCompoundTagAt(var2);
         String var4 = var3.getString("Name");
         if(var4.length() > 16) {
            var4 = var4.substring(0, 16);
         }

         ScorePlayerTeam var5 = this.theScoreboard.g(var4);
         String var6 = var3.getString("DisplayName");
         if(var6.length() > 32) {
            var6 = var6.substring(0, 32);
         }

         var5.c(var6);
         if(var3.hasKey("TeamColor", 8)) {
            var5.setChatFormat(EnumChatFormatting.b(var3.getString("TeamColor")));
         }

         var5.a(var3.getString("Prefix"));
         var5.setNameSuffix(var3.getString("Suffix"));
         if(var3.hasKey("AllowFriendlyFire", 99)) {
            var5.setAllowFriendlyFire(var3.getBoolean("AllowFriendlyFire"));
         }

         if(var3.hasKey("SeeFriendlyInvisibles", 99)) {
            var5.setSeeFriendlyInvisiblesEnabled(var3.getBoolean("SeeFriendlyInvisibles"));
         }

         if(var3.hasKey("NameTagVisibility", 8)) {
            Team$EnumVisible var7 = Team$EnumVisible.func_178824_a(var3.getString("NameTagVisibility"));
            var5.setNameTagVisibility(var7);
         }

         if(var3.hasKey("DeathMessageVisibility", 8)) {
            Team$EnumVisible var8 = Team$EnumVisible.func_178824_a(var3.getString("DeathMessageVisibility"));
            var5.setDeathMessageVisibility(var8);
         }

         this.func_96502_a(var5, var3.getTagList("Players", 8));
      }

   }

   protected void func_96502_a(ScorePlayerTeam var1, NBTTagList var2) {
      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         this.theScoreboard.addPlayerToTeam(var2.getStringTagAt(var3), var1.getRegisteredName());
      }

   }

   protected void readDisplayConfig(NBTTagCompound var1) {
      for(int var2 = 0; var2 < 19; ++var2) {
         if(var1.hasKey("slot_" + var2, 8)) {
            String var3 = var1.getString("slot_" + var2);
            ScoreObjective var4 = this.theScoreboard.getObjective(var3);
            this.theScoreboard.setObjectiveInDisplaySlot(var2, var4);
         }
      }

   }

   protected void readObjectives(NBTTagList var1) {
      for(int var2 = 0; var2 < var1.tagCount(); ++var2) {
         NBTTagCompound var3 = var1.getCompoundTagAt(var2);
         IScoreObjectiveCriteria var4 = (IScoreObjectiveCriteria)IScoreObjectiveCriteria.INSTANCES.get(var3.getString("CriteriaName"));
         String var5 = var3.getString("Name");
         if(var5.length() > 16) {
            var5 = var5.substring(0, 16);
         }

         ScoreObjective var6 = this.theScoreboard.a(var5, var4);
         var6.setDisplayName(var3.getString("DisplayName"));
         var6.setRenderType(IScoreObjectiveCriteria$EnumRenderType.func_178795_a(var3.getString("RenderType")));
      }

   }

   protected void readScores(NBTTagList var1) {
      for(int var2 = 0; var2 < var1.tagCount(); ++var2) {
         NBTTagCompound var3 = var1.getCompoundTagAt(var2);
         ScoreObjective var4 = this.theScoreboard.getObjective(var3.getString("Objective"));
         String var5 = var3.getString("Name");
         if(var5.length() > 40) {
            var5 = var5.substring(0, 40);
         }

         Score var6 = this.theScoreboard.getValueFromObjective(var5, var4);
         var6.setScorePoints(var3.getInteger("Score"));
         if(var3.hasKey("Locked")) {
            var6.setLocked(var3.getBoolean("Locked"));
         }
      }

   }

   public void writeToNBT(NBTTagCompound var1) {
      if(this.theScoreboard == null) {
         LOGGER.warn("Tried to save scoreboard without having a scoreboard...");
      } else {
         var1.setTag("Objectives", this.objectivesToNbt());
         var1.setTag("PlayerScores", this.scoresToNbt());
         var1.setTag("Teams", this.func_96496_a());
         this.func_96497_d(var1);
      }

   }

   protected NBTTagList func_96496_a() {
      NBTTagList var1 = new NBTTagList();

      for(ScorePlayerTeam var3 : this.theScoreboard.getTeams()) {
         NBTTagCompound var4 = new NBTTagCompound();
         var4.setString("Name", var3.getRegisteredName());
         var4.setString("DisplayName", var3.getTeamName());
         if(var3.getChatFormat().getColorIndex() >= 0) {
            var4.setString("TeamColor", var3.getChatFormat().getFriendlyName());
         }

         var4.setString("Prefix", var3.getColorPrefix());
         var4.setString("Suffix", var3.getColorSuffix());
         var4.setBoolean("AllowFriendlyFire", var3.getAllowFriendlyFire());
         var4.setBoolean("SeeFriendlyInvisibles", var3.getSeeFriendlyInvisiblesEnabled());
         var4.setString("NameTagVisibility", var3.getNameTagVisibility().field_178830_e);
         var4.setString("DeathMessageVisibility", var3.getDeathMessageVisibility().field_178830_e);
         NBTTagList var5 = new NBTTagList();

         for(String var7 : var3.getMembershipCollection()) {
            var5.appendTag(new NBTTagString(var7));
         }

         var4.setTag("Players", var5);
         var1.appendTag(var4);
      }

      return var1;
   }

   protected void func_96497_d(NBTTagCompound var1) {
      NBTTagCompound var2 = new NBTTagCompound();
      boolean var3 = false;

      for(int var4 = 0; var4 < 19; ++var4) {
         ScoreObjective var5 = this.theScoreboard.getObjectiveInDisplaySlot(var4);
         var2.setString("slot_" + var4, var5.getName());
         var3 = true;
      }

      var1.setTag("DisplaySlots", var2);
   }

   protected NBTTagList objectivesToNbt() {
      NBTTagList var1 = new NBTTagList();

      for(ScoreObjective var3 : this.theScoreboard.getScoreObjectives()) {
         if(var3.getCriteria() != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.setString("Name", var3.getName());
            var4.setString("CriteriaName", var3.getCriteria().getName());
            var4.setString("DisplayName", var3.getDisplayName());
            var4.setString("RenderType", var3.getRenderType().func_178796_a());
            var1.appendTag(var4);
         }
      }

      return var1;
   }

   protected NBTTagList scoresToNbt() {
      NBTTagList var1 = new NBTTagList();

      for(Score var3 : this.theScoreboard.getScores()) {
         if(var3.getObjective() != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.setString("Name", var3.getPlayerName());
            var4.setString("Objective", var3.getObjective().getName());
            var4.setInteger("Score", var3.getScorePoints());
            var4.setBoolean("Locked", var3.isLocked());
            var1.appendTag(var4);
         }
      }

      return var1;
   }
}
