package net.minecraft.command.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team$EnumVisible;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandScoreboard extends CommandBase {
   public String getCommandName() {
      return "scoreboard";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.scoreboard.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(!this.a(var1, var2)) {
         if(var2.length < 1) {
            throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
         }

         if(var2[0].equalsIgnoreCase("objectives")) {
            if(var2.length == 1) {
               throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
            }

            if(var2[1].equalsIgnoreCase("list")) {
               this.listObjectives(var1);
            } else if(var2[1].equalsIgnoreCase("add")) {
               if(var2.length < 4) {
                  throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
               }

               this.o(var1, var2, 2);
            } else if(var2[1].equalsIgnoreCase("remove")) {
               if(var2.length != 3) {
                  throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
               }

               this.removeObjective(var1, var2[2]);
            } else {
               if(!var2[1].equalsIgnoreCase("setdisplay")) {
                  throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
               }

               if(var2.length != 3 && var2.length != 4) {
                  throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
               }

               this.setObjectiveDisplay(var1, var2, 2);
            }
         } else if(var2[0].equalsIgnoreCase("players")) {
            if(var2.length == 1) {
               throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
            }

            if(var2[1].equalsIgnoreCase("list")) {
               if(var2.length > 3) {
                  throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
               }

               this.listPlayers(var1, var2, 2);
            } else if(var2[1].equalsIgnoreCase("add")) {
               if(var2.length < 5) {
                  throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
               }

               this.setPlayer(var1, var2, 2);
            } else if(var2[1].equalsIgnoreCase("remove")) {
               if(var2.length < 5) {
                  throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
               }

               this.setPlayer(var1, var2, 2);
            } else if(var2[1].equalsIgnoreCase("set")) {
               if(var2.length < 5) {
                  throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
               }

               this.setPlayer(var1, var2, 2);
            } else if(var2[1].equalsIgnoreCase("reset")) {
               if(var2.length != 3 && var2.length != 4) {
                  throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
               }

               this.resetPlayers(var1, var2, 2);
            } else if(var2[1].equalsIgnoreCase("enable")) {
               if(var2.length != 4) {
                  throw new WrongUsageException("commands.scoreboard.players.enable.usage", new Object[0]);
               }

               this.func_175779_n(var1, var2, 2);
            } else if(var2[1].equalsIgnoreCase("test")) {
               if(var2.length != 5 && var2.length != 6) {
                  throw new WrongUsageException("commands.scoreboard.players.test.usage", new Object[0]);
               }

               this.func_175781_o(var1, var2, 2);
            } else {
               if(!var2[1].equalsIgnoreCase("operation")) {
                  throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
               }

               if(var2.length != 7) {
                  throw new WrongUsageException("commands.scoreboard.players.operation.usage", new Object[0]);
               }

               this.func_175778_p(var1, var2, 2);
            }
         } else {
            if(!var2[0].equalsIgnoreCase("teams")) {
               throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
            }

            if(var2.length == 1) {
               throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
            }

            if(var2[1].equalsIgnoreCase("list")) {
               if(var2.length > 3) {
                  throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
               }

               this.listTeams(var1, var2, 2);
            } else if(var2[1].equalsIgnoreCase("add")) {
               if(var2.length < 3) {
                  throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
               }

               this.addTeam(var1, var2, 2);
            } else if(var2[1].equalsIgnoreCase("remove")) {
               if(var2.length != 3) {
                  throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
               }

               this.removeTeam(var1, var2, 2);
            } else if(var2[1].equalsIgnoreCase("empty")) {
               if(var2.length != 3) {
                  throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
               }

               this.emptyTeam(var1, var2, 2);
            } else if(var2[1].equalsIgnoreCase("join")) {
               if(var2.length < 4 && (var2.length != 3 || !(var1 instanceof EntityPlayer))) {
                  throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
               }

               this.joinTeam(var1, var2, 2);
            } else if(var2[1].equalsIgnoreCase("leave")) {
               if(var2.length < 3 && !(var1 instanceof EntityPlayer)) {
                  throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
               }

               this.leaveTeam(var1, var2, 2);
            } else {
               if(!var2[1].equalsIgnoreCase("option")) {
                  throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
               }

               if(var2.length != 4 && var2.length != 5) {
                  throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
               }

               this.setTeamOption(var1, var2, 2);
            }
         }
      }

   }

   private boolean a(ICommandSender var1, String[] var2) throws CommandException {
      boolean var3 = true;

      for(int var4 = 0; var4 < var2.length; ++var4) {
         if(this.isUsernameIndex(var2, var4) && "*".equals(var2[var4])) {
            throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
         }
      }

      return false;
   }

   protected Scoreboard getScoreboard() {
      return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
   }

   protected ScoreObjective getObjective(String var1, boolean var2) throws CommandException {
      Scoreboard var3 = this.getScoreboard();
      ScoreObjective var4 = var3.getObjective(var1);
      throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[]{var1});
   }

   protected ScorePlayerTeam getTeam(String var1) throws CommandException {
      Scoreboard var2 = this.getScoreboard();
      ScorePlayerTeam var3 = var2.getTeam(var1);
      throw new CommandException("commands.scoreboard.teamNotFound", new Object[]{var1});
   }

   protected void o(ICommandSender var1, String[] var2, int var3) throws CommandException {
      String var4 = var2[var3++];
      String var5 = var2[var3++];
      Scoreboard var6 = this.getScoreboard();
      IScoreObjectiveCriteria var7 = (IScoreObjectiveCriteria)IScoreObjectiveCriteria.INSTANCES.get(var5);
      throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[]{var5});
   }

   protected void addTeam(ICommandSender var1, String[] var2, int var3) throws CommandException {
      String var4 = var2[var3++];
      Scoreboard var5 = this.getScoreboard();
      if(var5.getTeam(var4) != null) {
         throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[]{var4});
      } else if(var4.length() > 16) {
         throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[]{var4, Integer.valueOf(16)});
      } else if(var4.isEmpty()) {
         throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
      } else {
         if(var2.length > var3) {
            String var6 = getChatComponentFromNthArg(var1, var2, var3).getUnformattedText();
            if(var6.length() > 32) {
               throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[]{var6, Integer.valueOf(32)});
            }

            if(!var6.isEmpty()) {
               var5.g(var4).c(var6);
            } else {
               var5.g(var4);
            }
         } else {
            var5.g(var4);
         }

         notifyOperators(var1, this, "commands.scoreboard.teams.add.success", new Object[]{var4});
      }
   }

   protected void setTeamOption(ICommandSender var1, String[] var2, int var3) throws CommandException {
      ScorePlayerTeam var4 = this.getTeam(var2[var3++]);
      String var5 = var2[var3++].toLowerCase();
      if(!var5.equalsIgnoreCase("color") && !var5.equalsIgnoreCase("friendlyfire") && !var5.equalsIgnoreCase("seeFriendlyInvisibles") && !var5.equalsIgnoreCase("nametagVisibility") && !var5.equalsIgnoreCase("deathMessageVisibility")) {
         throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
      } else if(var2.length == 4) {
         if(var5.equalsIgnoreCase("color")) {
            throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false))});
         } else if(!var5.equalsIgnoreCase("friendlyfire") && !var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
            if(!var5.equalsIgnoreCase("nametagVisibility") && !var5.equalsIgnoreCase("deathMessageVisibility")) {
               throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
            } else {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceString(Team$EnumVisible.func_178825_a())});
            }
         } else {
            throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceStringFromCollection(Arrays.asList(new String[]{"true", "false"}))});
         }
      } else {
         String var6 = var2[var3];
         if(var5.equalsIgnoreCase("color")) {
            EnumChatFormatting var7 = EnumChatFormatting.b(var6);
            if(var7.isFancyStyling()) {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false))});
            }

            var4.setChatFormat(var7);
            var4.a(var7.toString());
            var4.setNameSuffix(EnumChatFormatting.RESET.toString());
         } else if(var5.equalsIgnoreCase("friendlyfire")) {
            if(!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceStringFromCollection(Arrays.asList(new String[]{"true", "false"}))});
            }

            var4.setAllowFriendlyFire(var6.equalsIgnoreCase("true"));
         } else if(var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
            if(!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceStringFromCollection(Arrays.asList(new String[]{"true", "false"}))});
            }

            var4.setSeeFriendlyInvisiblesEnabled(var6.equalsIgnoreCase("true"));
         } else {
            if(var5.equalsIgnoreCase("nametagVisibility")) {
               Team$EnumVisible var11 = Team$EnumVisible.func_178824_a(var6);
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceString(Team$EnumVisible.func_178825_a())});
            }

            if(var5.equalsIgnoreCase("deathMessageVisibility")) {
               Team$EnumVisible var10 = Team$EnumVisible.func_178824_a(var6);
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[]{var5, joinNiceString(Team$EnumVisible.func_178825_a())});
            }
         }

         notifyOperators(var1, this, "commands.scoreboard.teams.option.success", new Object[]{var5, var4.getRegisteredName(), var6});
      }
   }

   protected void removeTeam(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      ScorePlayerTeam var5 = this.getTeam(var2[var3]);
      var4.removeTeam(var5);
      notifyOperators(var1, this, "commands.scoreboard.teams.remove.success", new Object[]{var5.getRegisteredName()});
   }

   protected void listTeams(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      if(var2.length > var3) {
         ScorePlayerTeam var9 = this.getTeam(var2[var3]);
      } else {
         Collection var5 = var4.getTeams();
         var1.setCommandStat(CommandResultStats$Type.QUERY_RESULT, var5.size());
         if(var5.size() <= 0) {
            throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
         } else {
            ChatComponentTranslation var6 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", new Object[]{Integer.valueOf(var5.size())});
            var6.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            var1.addChatMessage(var6);

            for(ScorePlayerTeam var8 : var5) {
               var1.addChatMessage(new ChatComponentTranslation("commands.scoreboard.teams.list.entry", new Object[]{var8.getRegisteredName(), var8.getTeamName(), Integer.valueOf(var8.getMembershipCollection().size())}));
            }

         }
      }
   }

   protected void joinTeam(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      String var5 = var2[var3++];
      HashSet var6 = Sets.newHashSet();
      HashSet var7 = Sets.newHashSet();
      if(var1 instanceof EntityPlayer && var3 == var2.length) {
         String var13 = getCommandSenderAsPlayer(var1).getName();
         if(var4.addPlayerToTeam(var13, var5)) {
            var6.add(var13);
         } else {
            var7.add(var13);
         }
      } else {
         while(var3 < var2.length) {
            String var8 = var2[var3++];
            if(var8.startsWith("@")) {
               for(Entity var10 : func_175763_c(var1, var8)) {
                  String var11 = getEntityName(var1, var10.getUniqueID().toString());
                  if(var4.addPlayerToTeam(var11, var5)) {
                     var6.add(var11);
                  } else {
                     var7.add(var11);
                  }
               }
            } else {
               String var9 = getEntityName(var1, var8);
               if(var4.addPlayerToTeam(var9, var5)) {
                  var6.add(var9);
               } else {
                  var7.add(var9);
               }
            }
         }
      }

      if(!var6.isEmpty()) {
         var1.setCommandStat(CommandResultStats$Type.AFFECTED_ENTITIES, var6.size());
         notifyOperators(var1, this, "commands.scoreboard.teams.join.success", new Object[]{Integer.valueOf(var6.size()), var5, joinNiceString(var6.toArray(new String[var6.size()]))});
      }

      if(!var7.isEmpty()) {
         throw new CommandException("commands.scoreboard.teams.join.failure", new Object[]{Integer.valueOf(var7.size()), var5, joinNiceString(var7.toArray(new String[var7.size()]))});
      }
   }

   protected void leaveTeam(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      HashSet var5 = Sets.newHashSet();
      HashSet var6 = Sets.newHashSet();
      if(var1 instanceof EntityPlayer && var3 == var2.length) {
         String var11 = getCommandSenderAsPlayer(var1).getName();
         if(var4.removePlayerFromTeams(var11)) {
            var5.add(var11);
         } else {
            var6.add(var11);
         }
      } else {
         while(var3 < var2.length) {
            String var7 = var2[var3++];
            if(var7.startsWith("@")) {
               for(Entity var9 : func_175763_c(var1, var7)) {
                  String var10 = getEntityName(var1, var9.getUniqueID().toString());
                  if(var4.removePlayerFromTeams(var10)) {
                     var5.add(var10);
                  } else {
                     var6.add(var10);
                  }
               }
            } else {
               String var8 = getEntityName(var1, var7);
               if(var4.removePlayerFromTeams(var8)) {
                  var5.add(var8);
               } else {
                  var6.add(var8);
               }
            }
         }
      }

      if(!var5.isEmpty()) {
         var1.setCommandStat(CommandResultStats$Type.AFFECTED_ENTITIES, var5.size());
         notifyOperators(var1, this, "commands.scoreboard.teams.leave.success", new Object[]{Integer.valueOf(var5.size()), joinNiceString(var5.toArray(new String[var5.size()]))});
      }

      if(!var6.isEmpty()) {
         throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[]{Integer.valueOf(var6.size()), joinNiceString(var6.toArray(new String[var6.size()]))});
      }
   }

   protected void emptyTeam(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      ScorePlayerTeam var5 = this.getTeam(var2[var3]);
      ArrayList var6 = Lists.newArrayList(var5.getMembershipCollection());
      var1.setCommandStat(CommandResultStats$Type.AFFECTED_ENTITIES, var6.size());
      if(var6.isEmpty()) {
         throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[]{var5.getRegisteredName()});
      } else {
         for(String var8 : var6) {
            var4.removePlayerFromTeam(var8, var5);
         }

         notifyOperators(var1, this, "commands.scoreboard.teams.empty.success", new Object[]{Integer.valueOf(var6.size()), var5.getRegisteredName()});
      }
   }

   protected void removeObjective(ICommandSender var1, String var2) throws CommandException {
      Scoreboard var3 = this.getScoreboard();
      ScoreObjective var4 = this.getObjective(var2, false);
      var3.removeObjective(var4);
      notifyOperators(var1, this, "commands.scoreboard.objectives.remove.success", new Object[]{var2});
   }

   protected void listObjectives(ICommandSender var1) throws CommandException {
      Scoreboard var2 = this.getScoreboard();
      Collection var3 = var2.getScoreObjectives();
      if(var3.size() <= 0) {
         throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
      } else {
         ChatComponentTranslation var4 = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", new Object[]{Integer.valueOf(var3.size())});
         var4.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
         var1.addChatMessage(var4);

         for(ScoreObjective var6 : var3) {
            var1.addChatMessage(new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[]{var6.getName(), var6.getDisplayName(), var6.getCriteria().getName()}));
         }

      }
   }

   protected void setObjectiveDisplay(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      String var5 = var2[var3++];
      int var6 = Scoreboard.getObjectiveDisplaySlotNumber(var5);
      Object var7 = null;
      if(var2.length == 4) {
         this.getObjective(var2[var3], false);
      }

      throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[]{var5});
   }

   protected void listPlayers(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      if(var2.length > var3) {
         String var5 = getEntityName(var1, var2[var3]);
         Map var6 = var4.getObjectivesForEntity(var5);
         var1.setCommandStat(CommandResultStats$Type.QUERY_RESULT, var6.size());
         if(var6.size() <= 0) {
            throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[]{var5});
         }

         ChatComponentTranslation var7 = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", new Object[]{Integer.valueOf(var6.size()), var5});
         var7.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
         var1.addChatMessage(var7);

         for(Score var9 : var6.values()) {
            var1.addChatMessage(new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[]{Integer.valueOf(var9.getScorePoints()), var9.getObjective().getDisplayName(), var9.getObjective().getName()}));
         }
      } else {
         Collection var10 = var4.getObjectiveNames();
         var1.setCommandStat(CommandResultStats$Type.QUERY_RESULT, var10.size());
         if(var10.size() <= 0) {
            throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
         }

         ChatComponentTranslation var11 = new ChatComponentTranslation("commands.scoreboard.players.list.count", new Object[]{Integer.valueOf(var10.size())});
         var11.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
         var1.addChatMessage(var11);
         var1.addChatMessage(new ChatComponentText(joinNiceString(var10.toArray())));
      }

   }

   protected void setPlayer(ICommandSender var1, String[] var2, int var3) throws CommandException {
      String var4 = var2[var3 - 1];
      int var5 = var3;
      String var6 = getEntityName(var1, var2[var3++]);
      if(var6.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[]{var6, Integer.valueOf(40)});
      } else {
         ScoreObjective var7 = this.getObjective(var2[var3++], true);
         int var8 = var4.equalsIgnoreCase("set")?parseInt(var2[var3++]):parseInt(var2[var3++], 0);
         if(var2.length > var3) {
            Entity var9 = func_175768_b(var1, var2[var5]);

            try {
               NBTTagCompound var10 = JsonToNBT.getTagFromJson(buildString(var2, var3));
               NBTTagCompound var11 = new NBTTagCompound();
               var9.writeToNBT(var11);
               if(!NBTUtil.a(var10, var11, true)) {
                  throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[]{var6});
               }
            } catch (NBTException var12) {
               throw new CommandException("commands.scoreboard.players.set.tagError", new Object[]{var12.getMessage()});
            }
         }

         Scoreboard var16 = this.getScoreboard();
         Score var17 = var16.getValueFromObjective(var6, var7);
         if(var4.equalsIgnoreCase("set")) {
            var17.setScorePoints(var8);
         } else if(var4.equalsIgnoreCase("add")) {
            var17.increseScore(var8);
         } else {
            var17.decreaseScore(var8);
         }

         notifyOperators(var1, this, "commands.scoreboard.players.set.success", new Object[]{var7.getName(), var6, Integer.valueOf(var17.getScorePoints())});
      }
   }

   protected void resetPlayers(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      String var5 = getEntityName(var1, var2[var3++]);
      if(var2.length > var3) {
         ScoreObjective var6 = this.getObjective(var2[var3++], false);
         var4.removeObjectiveFromEntity(var5, var6);
         notifyOperators(var1, this, "commands.scoreboard.players.resetscore.success", new Object[]{var6.getName(), var5});
      } else {
         var4.removeObjectiveFromEntity(var5, (ScoreObjective)null);
         notifyOperators(var1, this, "commands.scoreboard.players.reset.success", new Object[]{var5});
      }

   }

   protected void func_175779_n(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      String var5 = getPlayerName(var1, var2[var3++]);
      if(var5.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[]{var5, Integer.valueOf(40)});
      } else {
         ScoreObjective var6 = this.getObjective(var2[var3], false);
         if(var6.getCriteria() != IScoreObjectiveCriteria.TRIGGER) {
            throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[]{var6.getName()});
         } else {
            Score var7 = var4.getValueFromObjective(var5, var6);
            var7.setLocked(false);
            notifyOperators(var1, this, "commands.scoreboard.players.enable.success", new Object[]{var6.getName(), var5});
         }
      }
   }

   protected void func_175781_o(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      String var5 = getEntityName(var1, var2[var3++]);
      if(var5.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[]{var5, Integer.valueOf(40)});
      } else {
         ScoreObjective var6 = this.getObjective(var2[var3++], false);
         if(!var4.entityHasObjective(var5, var6)) {
            throw new CommandException("commands.scoreboard.players.test.notFound", new Object[]{var6.getName(), var5});
         } else {
            int var7 = var2[var3].equals("*")?Integer.MIN_VALUE:parseInt(var2[var3]);
            ++var3;
            int var8 = var3 < var2.length && !var2[var3].equals("*")?parseInt(var2[var3], var7):Integer.MAX_VALUE;
            Score var9 = var4.getValueFromObjective(var5, var6);
            if(var9.getScorePoints() >= var7 && var9.getScorePoints() <= var8) {
               notifyOperators(var1, this, "commands.scoreboard.players.test.success", new Object[]{Integer.valueOf(var9.getScorePoints()), Integer.valueOf(var7), Integer.valueOf(var8)});
            } else {
               throw new CommandException("commands.scoreboard.players.test.failed", new Object[]{Integer.valueOf(var9.getScorePoints()), Integer.valueOf(var7), Integer.valueOf(var8)});
            }
         }
      }
   }

   protected void func_175778_p(ICommandSender var1, String[] var2, int var3) throws CommandException {
      Scoreboard var4 = this.getScoreboard();
      String var5 = getEntityName(var1, var2[var3++]);
      ScoreObjective var6 = this.getObjective(var2[var3++], true);
      String var7 = var2[var3++];
      String var8 = getEntityName(var1, var2[var3++]);
      ScoreObjective var9 = this.getObjective(var2[var3], false);
      if(var5.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[]{var5, Integer.valueOf(40)});
      } else if(var8.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[]{var8, Integer.valueOf(40)});
      } else {
         Score var10 = var4.getValueFromObjective(var5, var6);
         if(!var4.entityHasObjective(var8, var9)) {
            throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[]{var9.getName(), var8});
         } else {
            Score var11 = var4.getValueFromObjective(var8, var9);
            if(var7.equals("+=")) {
               var10.setScorePoints(var10.getScorePoints() + var11.getScorePoints());
            } else if(var7.equals("-=")) {
               var10.setScorePoints(var10.getScorePoints() - var11.getScorePoints());
            } else if(var7.equals("*=")) {
               var10.setScorePoints(var10.getScorePoints() * var11.getScorePoints());
            } else if(var7.equals("/=")) {
               if(var11.getScorePoints() != 0) {
                  var10.setScorePoints(var10.getScorePoints() / var11.getScorePoints());
               }
            } else if(var7.equals("%=")) {
               if(var11.getScorePoints() != 0) {
                  var10.setScorePoints(var10.getScorePoints() % var11.getScorePoints());
               }
            } else if(var7.equals("=")) {
               var10.setScorePoints(var11.getScorePoints());
            } else if(var7.equals("<")) {
               var10.setScorePoints(Math.min(var10.getScorePoints(), var11.getScorePoints()));
            } else if(var7.equals(">")) {
               var10.setScorePoints(Math.max(var10.getScorePoints(), var11.getScorePoints()));
            } else {
               if(!var7.equals("><")) {
                  throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[]{var7});
               }

               int var12 = var10.getScorePoints();
               var10.setScorePoints(var11.getScorePoints());
               var11.setScorePoints(var12);
            }

            notifyOperators(var1, this, "commands.scoreboard.players.operation.success", new Object[0]);
         }
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      if(var2.length == 1) {
         return getListOfStringsMatchingLastWord(var2, new String[]{"objectives", "players", "teams"});
      } else {
         if(var2[0].equalsIgnoreCase("objectives")) {
            if(var2.length == 2) {
               return getListOfStringsMatchingLastWord(var2, new String[]{"list", "add", "remove", "setdisplay"});
            }

            if(var2[1].equalsIgnoreCase("add")) {
               if(var2.length == 4) {
                  Set var4 = IScoreObjectiveCriteria.INSTANCES.keySet();
                  return getListOfStringsMatchingLastWord(var2, var4);
               }
            } else if(var2[1].equalsIgnoreCase("remove")) {
               if(var2.length == 3) {
                  return getListOfStringsMatchingLastWord(var2, this.func_147184_a(false));
               }
            } else if(var2[1].equalsIgnoreCase("setdisplay")) {
               if(var2.length == 3) {
                  return getListOfStringsMatchingLastWord(var2, Scoreboard.getDisplaySlotStrings());
               }

               if(var2.length == 4) {
                  return getListOfStringsMatchingLastWord(var2, this.func_147184_a(false));
               }
            }
         } else if(var2[0].equalsIgnoreCase("players")) {
            if(var2.length == 2) {
               return getListOfStringsMatchingLastWord(var2, new String[]{"set", "add", "remove", "reset", "list", "enable", "test", "operation"});
            }

            if(!var2[1].equalsIgnoreCase("set") && !var2[1].equalsIgnoreCase("add") && !var2[1].equalsIgnoreCase("remove") && !var2[1].equalsIgnoreCase("reset")) {
               if(var2[1].equalsIgnoreCase("enable")) {
                  if(var2.length == 3) {
                     return getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames());
                  }

                  if(var2.length == 4) {
                     return getListOfStringsMatchingLastWord(var2, this.func_175782_e());
                  }
               } else if(!var2[1].equalsIgnoreCase("list") && !var2[1].equalsIgnoreCase("test")) {
                  if(var2[1].equalsIgnoreCase("operation")) {
                     if(var2.length == 3) {
                        return getListOfStringsMatchingLastWord(var2, this.getScoreboard().getObjectiveNames());
                     }

                     if(var2.length == 4) {
                        return getListOfStringsMatchingLastWord(var2, this.func_147184_a(true));
                     }

                     if(var2.length == 5) {
                        return getListOfStringsMatchingLastWord(var2, new String[]{"+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><"});
                     }

                     if(var2.length == 6) {
                        return getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames());
                     }

                     if(var2.length == 7) {
                        return getListOfStringsMatchingLastWord(var2, this.func_147184_a(false));
                     }
                  }
               } else {
                  if(var2.length == 3) {
                     return getListOfStringsMatchingLastWord(var2, this.getScoreboard().getObjectiveNames());
                  }

                  if(var2.length == 4 && var2[1].equalsIgnoreCase("test")) {
                     return getListOfStringsMatchingLastWord(var2, this.func_147184_a(false));
                  }
               }
            } else {
               if(var2.length == 3) {
                  return getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames());
               }

               if(var2.length == 4) {
                  return getListOfStringsMatchingLastWord(var2, this.func_147184_a(true));
               }
            }
         } else if(var2[0].equalsIgnoreCase("teams")) {
            if(var2.length == 2) {
               return getListOfStringsMatchingLastWord(var2, new String[]{"add", "remove", "join", "leave", "empty", "list", "option"});
            }

            if(var2[1].equalsIgnoreCase("join")) {
               if(var2.length == 3) {
                  return getListOfStringsMatchingLastWord(var2, this.getScoreboard().getTeamNames());
               }

               if(var2.length >= 4) {
                  return getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames());
               }
            } else {
               if(var2[1].equalsIgnoreCase("leave")) {
                  return getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames());
               }

               if(!var2[1].equalsIgnoreCase("empty") && !var2[1].equalsIgnoreCase("list") && !var2[1].equalsIgnoreCase("remove")) {
                  if(var2[1].equalsIgnoreCase("option")) {
                     if(var2.length == 3) {
                        return getListOfStringsMatchingLastWord(var2, this.getScoreboard().getTeamNames());
                     }

                     if(var2.length == 4) {
                        return getListOfStringsMatchingLastWord(var2, new String[]{"color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility"});
                     }

                     if(var2.length == 5) {
                        if(var2[3].equalsIgnoreCase("color")) {
                           return getListOfStringsMatchingLastWord(var2, EnumChatFormatting.getValidValues(true, false));
                        }

                        if(var2[3].equalsIgnoreCase("nametagVisibility") || var2[3].equalsIgnoreCase("deathMessageVisibility")) {
                           return getListOfStringsMatchingLastWord(var2, Team$EnumVisible.func_178825_a());
                        }

                        if(var2[3].equalsIgnoreCase("friendlyfire") || var2[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
                           return getListOfStringsMatchingLastWord(var2, new String[]{"true", "false"});
                        }
                     }
                  }
               } else if(var2.length == 3) {
                  return getListOfStringsMatchingLastWord(var2, this.getScoreboard().getTeamNames());
               }
            }
         }

         return null;
      }
   }

   protected List func_147184_a(boolean var1) {
      Collection var2 = this.getScoreboard().getScoreObjectives();
      ArrayList var3 = Lists.newArrayList();

      for(ScoreObjective var5 : var2) {
         if(!var5.getCriteria().isReadOnly()) {
            var3.add(var5.getName());
         }
      }

      return var3;
   }

   protected List func_175782_e() {
      Collection var1 = this.getScoreboard().getScoreObjectives();
      ArrayList var2 = Lists.newArrayList();

      for(ScoreObjective var4 : var1) {
         if(var4.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
            var2.add(var4.getName());
         }
      }

      return var2;
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return !var1[0].equalsIgnoreCase("players")?var1[0].equalsIgnoreCase("teams") && var2 == 2:(var1.length > 1 && var1[1].equalsIgnoreCase("operation")?var2 == 2 || var2 == 5:var2 == 2);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
