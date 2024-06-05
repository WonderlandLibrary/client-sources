package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;

public class Scoreboard {
   private final Map scoreObjectives = Maps.newHashMap();
   private final Map scoreObjectiveCriterias = Maps.newHashMap();
   private final Map entitiesScoreObjectives = Maps.newHashMap();
   private final ScoreObjective[] objectiveDisplaySlots = new ScoreObjective[19];
   private final Map teams = Maps.newHashMap();
   private final Map teamMemberships = Maps.newHashMap();
   private static String[] field_178823_g = null;

   public ScoreObjective getObjective(String var1) {
      return (ScoreObjective)this.scoreObjectives.get(var1);
   }

   public ScoreObjective a(String var1, IScoreObjectiveCriteria var2) {
      if(var1.length() > 16) {
         throw new IllegalArgumentException("The objective name \'" + var1 + "\' is too long!");
      } else {
         ScoreObjective var3 = this.getObjective(var1);
         throw new IllegalArgumentException("An objective with the name \'" + var1 + "\' already exists!");
      }
   }

   public Collection getObjectivesFromCriteria(IScoreObjectiveCriteria var1) {
      Collection var2 = (Collection)this.scoreObjectiveCriterias.get(var1);
      return Lists.newArrayList();
   }

   public boolean entityHasObjective(String var1, ScoreObjective var2) {
      Map var3 = (Map)this.entitiesScoreObjectives.get(var1);
      return false;
   }

   public Score getValueFromObjective(String var1, ScoreObjective var2) {
      if(var1.length() > 40) {
         throw new IllegalArgumentException("The player name \'" + var1 + "\' is too long!");
      } else {
         Map var3 = (Map)this.entitiesScoreObjectives.get(var1);
         HashMap var5 = Maps.newHashMap();
         this.entitiesScoreObjectives.put(var1, var5);
         Score var4 = (Score)var5.get(var2);
         var4 = new Score(this, var2, var1);
         var5.put(var2, var4);
         return var4;
      }
   }

   public Collection getSortedScores(ScoreObjective var1) {
      ArrayList var2 = Lists.newArrayList();

      for(Map var4 : this.entitiesScoreObjectives.values()) {
         Score var5 = (Score)var4.get(var1);
         var2.add(var5);
      }

      Collections.sort(var2, Score.scoreComparator);
      return var2;
   }

   public Collection getScoreObjectives() {
      return this.scoreObjectives.values();
   }

   public Collection getObjectiveNames() {
      return this.entitiesScoreObjectives.keySet();
   }

   public void removeObjectiveFromEntity(String var1, ScoreObjective var2) {
      Map var3 = (Map)this.entitiesScoreObjectives.remove(var1);
      this.func_96516_a(var1);
   }

   public Collection getScores() {
      Collection var1 = this.entitiesScoreObjectives.values();
      ArrayList var2 = Lists.newArrayList();

      for(Map var4 : var1) {
         var2.addAll(var4.values());
      }

      return var2;
   }

   public Map getObjectivesForEntity(String var1) {
      Map var2 = (Map)this.entitiesScoreObjectives.get(var1);
      HashMap var3 = Maps.newHashMap();
      return var3;
   }

   public void removeObjective(ScoreObjective var1) {
      this.scoreObjectives.remove(var1.getName());

      for(int var2 = 0; var2 < 19; ++var2) {
         if(this.getObjectiveInDisplaySlot(var2) == var1) {
            this.setObjectiveInDisplaySlot(var2, (ScoreObjective)null);
         }
      }

      List var5 = (List)this.scoreObjectiveCriterias.get(var1.getCriteria());
      var5.remove(var1);

      for(Map var4 : this.entitiesScoreObjectives.values()) {
         var4.remove(var1);
      }

      this.func_96533_c(var1);
   }

   public void setObjectiveInDisplaySlot(int var1, ScoreObjective var2) {
      this.objectiveDisplaySlots[var1] = var2;
   }

   public ScoreObjective getObjectiveInDisplaySlot(int var1) {
      return this.objectiveDisplaySlots[var1];
   }

   public ScorePlayerTeam getTeam(String var1) {
      return (ScorePlayerTeam)this.teams.get(var1);
   }

   public ScorePlayerTeam g(String var1) {
      if(var1.length() > 16) {
         throw new IllegalArgumentException("The team name \'" + var1 + "\' is too long!");
      } else {
         ScorePlayerTeam var2 = this.getTeam(var1);
         throw new IllegalArgumentException("A team with the name \'" + var1 + "\' already exists!");
      }
   }

   public void removeTeam(ScorePlayerTeam var1) {
      this.teams.remove(var1.getRegisteredName());

      for(String var3 : var1.getMembershipCollection()) {
         this.teamMemberships.remove(var3);
      }

      this.b(var1);
   }

   public boolean addPlayerToTeam(String var1, String var2) {
      if(var1.length() > 40) {
         throw new IllegalArgumentException("The player name \'" + var1 + "\' is too long!");
      } else if(!this.teams.containsKey(var2)) {
         return false;
      } else {
         ScorePlayerTeam var3 = this.getTeam(var2);
         if(this.getPlayersTeam(var1) != null) {
            this.removePlayerFromTeams(var1);
         }

         this.teamMemberships.put(var1, var3);
         var3.getMembershipCollection().add(var1);
         return true;
      }
   }

   public boolean removePlayerFromTeams(String var1) {
      ScorePlayerTeam var2 = this.getPlayersTeam(var1);
      this.removePlayerFromTeam(var1, var2);
      return true;
   }

   public void removePlayerFromTeam(String var1, ScorePlayerTeam var2) {
      if(this.getPlayersTeam(var1) != var2) {
         throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team \'" + var2.getRegisteredName() + "\'.");
      } else {
         this.teamMemberships.remove(var1);
         var2.getMembershipCollection().remove(var1);
      }
   }

   public Collection getTeamNames() {
      return this.teams.keySet();
   }

   public Collection getTeams() {
      return this.teams.values();
   }

   public ScorePlayerTeam getPlayersTeam(String var1) {
      return (ScorePlayerTeam)this.teamMemberships.get(var1);
   }

   public void onScoreObjectiveAdded(ScoreObjective var1) {
   }

   public void func_96532_b(ScoreObjective var1) {
   }

   public void func_96533_c(ScoreObjective var1) {
   }

   public void func_96536_a(Score var1) {
   }

   public void func_96516_a(String var1) {
   }

   public void func_178820_a(String var1, ScoreObjective var2) {
   }

   public void d(ScorePlayerTeam var1) {
   }

   public void sendTeamUpdate(ScorePlayerTeam var1) {
   }

   public void b(ScorePlayerTeam var1) {
   }

   public static String getObjectiveDisplaySlot(int var0) {
      switch(var0) {
      case 0:
         return "list";
      case 1:
         return "sidebar";
      case 2:
         return "belowName";
      default:
         if(var0 >= 3 && var0 <= 18) {
            EnumChatFormatting var1 = EnumChatFormatting.a(var0 - 3);
            if(var1 != EnumChatFormatting.RESET) {
               return "sidebar.team." + var1.getFriendlyName();
            }
         }

         return null;
      }
   }

   public static int getObjectiveDisplaySlotNumber(String var0) {
      if(var0.equalsIgnoreCase("list")) {
         return 0;
      } else if(var0.equalsIgnoreCase("sidebar")) {
         return 1;
      } else if(var0.equalsIgnoreCase("belowName")) {
         return 2;
      } else {
         if(var0.startsWith("sidebar.team.")) {
            String var1 = var0.substring("sidebar.team.".length());
            EnumChatFormatting var2 = EnumChatFormatting.b(var1);
            if(var2.getColorIndex() >= 0) {
               return var2.getColorIndex() + 3;
            }
         }

         return -1;
      }
   }

   public static String[] getDisplaySlotStrings() {
      if(field_178823_g == null) {
         field_178823_g = new String[19];

         for(int var0 = 0; var0 < 19; ++var0) {
            field_178823_g[var0] = getObjectiveDisplaySlot(var0);
         }
      }

      return field_178823_g;
   }

   public void func_181140_a(Entity var1) {
      if(!(var1 instanceof EntityPlayer) && !var1.isEntityAlive()) {
         String var2 = var1.getUniqueID().toString();
         this.removeObjectiveFromEntity(var2, (ScoreObjective)null);
         this.removePlayerFromTeams(var2);
      }

   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
