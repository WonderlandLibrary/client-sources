package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.server.MinecraftServer;

public class ServerScoreboard extends Scoreboard {
   private final MinecraftServer scoreboardMCServer;
   private final Set field_96553_b = Sets.newHashSet();
   private ScoreboardSaveData scoreboardSaveData;

   public ServerScoreboard(MinecraftServer var1) {
      this.scoreboardMCServer = var1;
   }

   public void func_96536_a(Score var1) {
      super.func_96536_a(var1);
      if(this.field_96553_b.contains(var1.getObjective())) {
         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(var1));
      }

      this.func_96551_b();
   }

   public void func_96516_a(String var1) {
      super.func_96516_a(var1);
      this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(var1));
      this.func_96551_b();
   }

   public void func_178820_a(String var1, ScoreObjective var2) {
      super.func_178820_a(var1, var2);
      this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(var1, var2));
      this.func_96551_b();
   }

   public void setObjectiveInDisplaySlot(int var1, ScoreObjective var2) {
      ScoreObjective var3 = this.getObjectiveInDisplaySlot(var1);
      super.setObjectiveInDisplaySlot(var1, var2);
      if(var3 != var2) {
         if(this.func_96552_h(var3) > 0) {
            this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(var1, var2));
         } else {
            this.getPlayerIterator(var3);
         }
      }

      if(this.field_96553_b.contains(var2)) {
         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(var1, var2));
      } else {
         this.func_96549_e(var2);
      }

      this.func_96551_b();
   }

   public boolean addPlayerToTeam(String var1, String var2) {
      if(super.addPlayerToTeam(var1, var2)) {
         ScorePlayerTeam var3 = this.getTeam(var2);
         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(var3, Arrays.asList(new String[]{var1}), 3));
         this.func_96551_b();
         return true;
      } else {
         return false;
      }
   }

   public void removePlayerFromTeam(String var1, ScorePlayerTeam var2) {
      super.removePlayerFromTeam(var1, var2);
      this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(var2, Arrays.asList(new String[]{var1}), 4));
      this.func_96551_b();
   }

   public void onScoreObjectiveAdded(ScoreObjective var1) {
      super.onScoreObjectiveAdded(var1);
      this.func_96551_b();
   }

   public void func_96532_b(ScoreObjective var1) {
      super.func_96532_b(var1);
      if(this.field_96553_b.contains(var1)) {
         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3BPacketScoreboardObjective(var1, 2));
      }

      this.func_96551_b();
   }

   public void func_96533_c(ScoreObjective var1) {
      super.func_96533_c(var1);
      if(this.field_96553_b.contains(var1)) {
         this.getPlayerIterator(var1);
      }

      this.func_96551_b();
   }

   public void d(ScorePlayerTeam var1) {
      super.d(var1);
      this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(var1, 0));
      this.func_96551_b();
   }

   public void sendTeamUpdate(ScorePlayerTeam var1) {
      super.sendTeamUpdate(var1);
      this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(var1, 2));
      this.func_96551_b();
   }

   public void b(ScorePlayerTeam var1) {
      super.b(var1);
      this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(var1, 1));
      this.func_96551_b();
   }

   public void func_96547_a(ScoreboardSaveData var1) {
      this.scoreboardSaveData = var1;
   }

   protected void func_96551_b() {
      if(this.scoreboardSaveData != null) {
         this.scoreboardSaveData.markDirty();
      }

   }

   public List func_96550_d(ScoreObjective var1) {
      ArrayList var2 = Lists.newArrayList();
      var2.add(new S3BPacketScoreboardObjective(var1, 0));

      for(int var3 = 0; var3 < 19; ++var3) {
         if(this.getObjectiveInDisplaySlot(var3) == var1) {
            var2.add(new S3DPacketDisplayScoreboard(var3, var1));
         }
      }

      for(Score var4 : this.getSortedScores(var1)) {
         var2.add(new S3CPacketUpdateScore(var4));
      }

      return var2;
   }

   public void func_96549_e(ScoreObjective var1) {
      List var2 = this.func_96550_d(var1);

      for(EntityPlayerMP var4 : this.scoreboardMCServer.getConfigurationManager().func_181057_v()) {
         for(Packet var6 : var2) {
            var4.playerNetServerHandler.sendPacket(var6);
         }
      }

      this.field_96553_b.add(var1);
   }

   public List func_96548_f(ScoreObjective var1) {
      ArrayList var2 = Lists.newArrayList();
      var2.add(new S3BPacketScoreboardObjective(var1, 1));

      for(int var3 = 0; var3 < 19; ++var3) {
         if(this.getObjectiveInDisplaySlot(var3) == var1) {
            var2.add(new S3DPacketDisplayScoreboard(var3, var1));
         }
      }

      return var2;
   }

   public void getPlayerIterator(ScoreObjective var1) {
      List var2 = this.func_96548_f(var1);

      for(EntityPlayerMP var4 : this.scoreboardMCServer.getConfigurationManager().func_181057_v()) {
         for(Packet var6 : var2) {
            var4.playerNetServerHandler.sendPacket(var6);
         }
      }

      this.field_96553_b.remove(var1);
   }

   public int func_96552_h(ScoreObjective var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < 19; ++var3) {
         if(this.getObjectiveInDisplaySlot(var3) == var1) {
            ++var2;
         }
      }

      return var2;
   }
}
