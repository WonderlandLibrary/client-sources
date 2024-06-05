package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class ServerCommandScoreboard extends CommandBase
{
    @Override
    public String getCommandName() {
        return "scoreboard";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 1) {
            if (par2ArrayOfStr[0].equalsIgnoreCase("objectives")) {
                if (par2ArrayOfStr.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                }
                if (par2ArrayOfStr[1].equalsIgnoreCase("list")) {
                    this.getObjectivesList(par1ICommandSender);
                }
                else if (par2ArrayOfStr[1].equalsIgnoreCase("add")) {
                    if (par2ArrayOfStr.length < 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
                    }
                    this.addObjective(par1ICommandSender, par2ArrayOfStr, 2);
                }
                else if (par2ArrayOfStr[1].equalsIgnoreCase("remove")) {
                    if (par2ArrayOfStr.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
                    }
                    this.removeObjective(par1ICommandSender, par2ArrayOfStr[2]);
                }
                else {
                    if (!par2ArrayOfStr[1].equalsIgnoreCase("setdisplay")) {
                        throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                    }
                    if (par2ArrayOfStr.length != 3 && par2ArrayOfStr.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
                    }
                    this.setObjectivesDisplay(par1ICommandSender, par2ArrayOfStr, 2);
                }
                return;
            }
            else if (par2ArrayOfStr[0].equalsIgnoreCase("players")) {
                if (par2ArrayOfStr.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                }
                if (par2ArrayOfStr[1].equalsIgnoreCase("list")) {
                    if (par2ArrayOfStr.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
                    }
                    this.listPlayers(par1ICommandSender, par2ArrayOfStr, 2);
                }
                else if (par2ArrayOfStr[1].equalsIgnoreCase("add")) {
                    if (par2ArrayOfStr.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
                    }
                    this.setPlayerScore(par1ICommandSender, par2ArrayOfStr, 2);
                }
                else if (par2ArrayOfStr[1].equalsIgnoreCase("remove")) {
                    if (par2ArrayOfStr.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
                    }
                    this.setPlayerScore(par1ICommandSender, par2ArrayOfStr, 2);
                }
                else if (par2ArrayOfStr[1].equalsIgnoreCase("set")) {
                    if (par2ArrayOfStr.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
                    }
                    this.setPlayerScore(par1ICommandSender, par2ArrayOfStr, 2);
                }
                else {
                    if (!par2ArrayOfStr[1].equalsIgnoreCase("reset")) {
                        throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                    }
                    if (par2ArrayOfStr.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
                    }
                    this.resetPlayerScore(par1ICommandSender, par2ArrayOfStr, 2);
                }
                return;
            }
            else if (par2ArrayOfStr[0].equalsIgnoreCase("teams")) {
                if (par2ArrayOfStr.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                }
                if (par2ArrayOfStr[1].equalsIgnoreCase("list")) {
                    if (par2ArrayOfStr.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
                    }
                    this.getTeamList(par1ICommandSender, par2ArrayOfStr, 2);
                }
                else if (par2ArrayOfStr[1].equalsIgnoreCase("add")) {
                    if (par2ArrayOfStr.length < 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
                    }
                    this.addTeam(par1ICommandSender, par2ArrayOfStr, 2);
                }
                else if (par2ArrayOfStr[1].equalsIgnoreCase("remove")) {
                    if (par2ArrayOfStr.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
                    }
                    this.removeTeam(par1ICommandSender, par2ArrayOfStr, 2);
                }
                else if (par2ArrayOfStr[1].equalsIgnoreCase("empty")) {
                    if (par2ArrayOfStr.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
                    }
                    this.emptyTeam(par1ICommandSender, par2ArrayOfStr, 2);
                }
                else if (par2ArrayOfStr[1].equalsIgnoreCase("join")) {
                    if (par2ArrayOfStr.length < 4 && (par2ArrayOfStr.length != 3 || !(par1ICommandSender instanceof EntityPlayer))) {
                        throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
                    }
                    this.joinTeam(par1ICommandSender, par2ArrayOfStr, 2);
                }
                else if (par2ArrayOfStr[1].equalsIgnoreCase("leave")) {
                    if (par2ArrayOfStr.length < 3 && !(par1ICommandSender instanceof EntityPlayer)) {
                        throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
                    }
                    this.leaveTeam(par1ICommandSender, par2ArrayOfStr, 2);
                }
                else {
                    if (!par2ArrayOfStr[1].equalsIgnoreCase("option")) {
                        throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                    }
                    if (par2ArrayOfStr.length != 4 && par2ArrayOfStr.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                    }
                    this.setTeamOption(par1ICommandSender, par2ArrayOfStr, 2);
                }
                return;
            }
        }
        throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
    }
    
    protected Scoreboard getScoreboardFromWorldServer() {
        return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
    }
    
    protected ScoreObjective getScoreObjective(final String par1Str, final boolean par2) {
        final Scoreboard var3 = this.getScoreboardFromWorldServer();
        final ScoreObjective var4 = var3.getObjective(par1Str);
        if (var4 == null) {
            throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { par1Str });
        }
        if (par2 && var4.getCriteria().isReadOnly()) {
            throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { par1Str });
        }
        return var4;
    }
    
    protected ScorePlayerTeam getTeam(final String par1Str) {
        final Scoreboard var2 = this.getScoreboardFromWorldServer();
        final ScorePlayerTeam var3 = var2.func_96508_e(par1Str);
        if (var3 == null) {
            throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { par1Str });
        }
        return var3;
    }
    
    protected void addObjective(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr, int par3) {
        final String var4 = par2ArrayOfStr[par3++];
        final String var5 = par2ArrayOfStr[par3++];
        final Scoreboard var6 = this.getScoreboardFromWorldServer();
        final ScoreObjectiveCriteria var7 = ScoreObjectiveCriteria.field_96643_a.get(var5);
        if (var7 == null) {
            final String[] var8 = (String[])ScoreObjectiveCriteria.field_96643_a.keySet().toArray(new String[0]);
            throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[] { CommandBase.joinNiceString(var8) });
        }
        if (var6.getObjective(var4) != null) {
            throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { var4 });
        }
        if (var4.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[] { var4, 16 });
        }
        final ScoreObjective var9 = var6.func_96535_a(var4, var7);
        if (par2ArrayOfStr.length > par3) {
            final String var10 = CommandBase.func_82360_a(par1ICommandSender, par2ArrayOfStr, par3);
            if (var10.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[] { var10, 32 });
            }
            if (var10.length() > 0) {
                var9.setDisplayName(var10);
            }
        }
        CommandBase.notifyAdmins(par1ICommandSender, "commands.scoreboard.objectives.add.success", var4);
    }
    
    protected void addTeam(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr, int par3) {
        final String var4 = par2ArrayOfStr[par3++];
        final Scoreboard var5 = this.getScoreboardFromWorldServer();
        if (var5.func_96508_e(var4) != null) {
            throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { var4 });
        }
        if (var4.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[] { var4, 16 });
        }
        final ScorePlayerTeam var6 = var5.func_96527_f(var4);
        if (par2ArrayOfStr.length > par3) {
            final String var7 = CommandBase.func_82360_a(par1ICommandSender, par2ArrayOfStr, par3);
            if (var7.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[] { var7, 32 });
            }
            if (var7.length() > 0) {
                var6.func_96664_a(var7);
            }
        }
        CommandBase.notifyAdmins(par1ICommandSender, "commands.scoreboard.teams.add.success", var4);
    }
    
    protected void setTeamOption(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr, int par3) {
        final ScorePlayerTeam var4 = this.getTeam(par2ArrayOfStr[par3++]);
        final String var5 = par2ArrayOfStr[par3++].toLowerCase();
        if (!var5.equalsIgnoreCase("color") && !var5.equalsIgnoreCase("friendlyfire") && !var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
            throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
        }
        if (par2ArrayOfStr.length != 4) {
            final String var6 = par2ArrayOfStr[par3++];
            if (var5.equalsIgnoreCase("color")) {
                final EnumChatFormatting var7 = EnumChatFormatting.func_96300_b(var6);
                if (var6 == null) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.func_96333_a(EnumChatFormatting.func_96296_a(true, false)) });
                }
                var4.func_96666_b(var7.toString());
                var4.func_96662_c(EnumChatFormatting.RESET.toString());
            }
            else if (var5.equalsIgnoreCase("friendlyfire")) {
                if (!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.func_96333_a(Arrays.asList("true", "false")) });
                }
                var4.func_96660_a(var6.equalsIgnoreCase("true"));
            }
            else if (var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
                if (!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.func_96333_a(Arrays.asList("true", "false")) });
                }
                var4.func_98300_b(var6.equalsIgnoreCase("true"));
            }
            CommandBase.notifyAdmins(par1ICommandSender, "commands.scoreboard.teams.option.success", var5, var4.func_96661_b(), var6);
            return;
        }
        if (var5.equalsIgnoreCase("color")) {
            throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.func_96333_a(EnumChatFormatting.func_96296_a(true, false)) });
        }
        if (!var5.equalsIgnoreCase("friendlyfire") && !var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
            throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
        }
        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.func_96333_a(Arrays.asList("true", "false")) });
    }
    
    protected void removeTeam(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr, int par3) {
        final Scoreboard var4 = this.getScoreboardFromWorldServer();
        final ScorePlayerTeam var5 = this.getTeam(par2ArrayOfStr[par3++]);
        var4.func_96511_d(var5);
        CommandBase.notifyAdmins(par1ICommandSender, "commands.scoreboard.teams.remove.success", var5.func_96661_b());
    }
    
    protected void getTeamList(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr, int par3) {
        final Scoreboard var4 = this.getScoreboardFromWorldServer();
        if (par2ArrayOfStr.length > par3) {
            final ScorePlayerTeam var5 = this.getTeam(par2ArrayOfStr[par3++]);
            final Collection var6 = var5.getMembershipCollection();
            if (var6.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { var5.func_96661_b() });
            }
            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + par1ICommandSender.translateString("commands.scoreboard.teams.list.player.count", var6.size(), var5.func_96661_b()));
            par1ICommandSender.sendChatToPlayer(CommandBase.joinNiceString(var6.toArray()));
        }
        else {
            final Collection var7 = var4.func_96525_g();
            if (var7.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
            }
            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + par1ICommandSender.translateString("commands.scoreboard.teams.list.count", var7.size()));
            for (final ScorePlayerTeam var9 : var7) {
                par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString("commands.scoreboard.teams.list.entry", var9.func_96661_b(), var9.func_96669_c(), var9.getMembershipCollection().size()));
            }
        }
    }
    
    protected void joinTeam(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr, int par3) {
        final Scoreboard var4 = this.getScoreboardFromWorldServer();
        final ScorePlayerTeam var5 = var4.func_96508_e(par2ArrayOfStr[par3++]);
        final HashSet var6 = new HashSet();
        if (par1ICommandSender instanceof EntityPlayer && par3 == par2ArrayOfStr.length) {
            final String var7 = CommandBase.getCommandSenderAsPlayer(par1ICommandSender).getEntityName();
            var4.func_96521_a(var7, var5);
            var6.add(var7);
        }
        else {
            while (par3 < par2ArrayOfStr.length) {
                final String var7 = CommandBase.func_96332_d(par1ICommandSender, par2ArrayOfStr[par3++]);
                var4.func_96521_a(var7, var5);
                var6.add(var7);
            }
        }
        if (!var6.isEmpty()) {
            CommandBase.notifyAdmins(par1ICommandSender, "commands.scoreboard.teams.join.success", var6.size(), var5.func_96661_b(), CommandBase.joinNiceString(var6.toArray(new String[0])));
        }
    }
    
    protected void leaveTeam(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr, int par3) {
        final Scoreboard var4 = this.getScoreboardFromWorldServer();
        final HashSet var5 = new HashSet();
        final HashSet var6 = new HashSet();
        if (par1ICommandSender instanceof EntityPlayer && par3 == par2ArrayOfStr.length) {
            final String var7 = CommandBase.getCommandSenderAsPlayer(par1ICommandSender).getEntityName();
            if (var4.func_96524_g(var7)) {
                var5.add(var7);
            }
            else {
                var6.add(var7);
            }
        }
        else {
            while (par3 < par2ArrayOfStr.length) {
                final String var7 = CommandBase.func_96332_d(par1ICommandSender, par2ArrayOfStr[par3++]);
                if (var4.func_96524_g(var7)) {
                    var5.add(var7);
                }
                else {
                    var6.add(var7);
                }
            }
        }
        if (!var5.isEmpty()) {
            CommandBase.notifyAdmins(par1ICommandSender, "commands.scoreboard.teams.leave.success", var5.size(), CommandBase.joinNiceString(var5.toArray(new String[0])));
        }
        if (!var6.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { var6.size(), CommandBase.joinNiceString(var6.toArray(new String[0])) });
        }
    }
    
    protected void emptyTeam(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr, int par3) {
        final Scoreboard var4 = this.getScoreboardFromWorldServer();
        final ScorePlayerTeam var5 = this.getTeam(par2ArrayOfStr[par3++]);
        final ArrayList var6 = new ArrayList(var5.getMembershipCollection());
        if (var6.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { var5.func_96661_b() });
        }
        for (final String var8 : var6) {
            var4.removePlayerFromTeam(var8, var5);
        }
        CommandBase.notifyAdmins(par1ICommandSender, "commands.scoreboard.teams.empty.success", var6.size(), var5.func_96661_b());
    }
    
    protected void removeObjective(final ICommandSender par1ICommandSender, final String par2Str) {
        final Scoreboard var3 = this.getScoreboardFromWorldServer();
        final ScoreObjective var4 = this.getScoreObjective(par2Str, false);
        var3.func_96519_k(var4);
        CommandBase.notifyAdmins(par1ICommandSender, "commands.scoreboard.objectives.remove.success", par2Str);
    }
    
    protected void getObjectivesList(final ICommandSender par1ICommandSender) {
        final Scoreboard var2 = this.getScoreboardFromWorldServer();
        final Collection var3 = var2.getScoreObjectives();
        if (var3.size() <= 0) {
            throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
        }
        par1ICommandSender.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + par1ICommandSender.translateString("commands.scoreboard.objectives.list.count", var3.size()));
        for (final ScoreObjective var5 : var3) {
            par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString("commands.scoreboard.objectives.list.entry", var5.getName(), var5.getDisplayName(), var5.getCriteria().func_96636_a()));
        }
    }
    
    protected void setObjectivesDisplay(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr, int par3) {
        final Scoreboard var4 = this.getScoreboardFromWorldServer();
        final String var5 = par2ArrayOfStr[par3++];
        final int var6 = Scoreboard.getObjectiveDisplaySlotNumber(var5);
        ScoreObjective var7 = null;
        if (par2ArrayOfStr.length == 4) {
            var7 = this.getScoreObjective(par2ArrayOfStr[par3++], false);
        }
        if (var6 < 0) {
            throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { var5 });
        }
        var4.func_96530_a(var6, var7);
        if (var7 != null) {
            CommandBase.notifyAdmins(par1ICommandSender, "commands.scoreboard.objectives.setdisplay.successSet", Scoreboard.getObjectiveDisplaySlot(var6), var7.getName());
        }
        else {
            CommandBase.notifyAdmins(par1ICommandSender, "commands.scoreboard.objectives.setdisplay.successCleared", Scoreboard.getObjectiveDisplaySlot(var6));
        }
    }
    
    protected void listPlayers(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr, int par3) {
        final Scoreboard var4 = this.getScoreboardFromWorldServer();
        if (par2ArrayOfStr.length > par3) {
            final String var5 = CommandBase.func_96332_d(par1ICommandSender, par2ArrayOfStr[par3++]);
            final Map var6 = var4.func_96510_d(var5);
            if (var6.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { var5 });
            }
            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + par1ICommandSender.translateString("commands.scoreboard.players.list.player.count", var6.size(), var5));
            for (final Score var8 : var6.values()) {
                par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString("commands.scoreboard.players.list.player.entry", var8.func_96652_c(), var8.func_96645_d().getDisplayName(), var8.func_96645_d().getName()));
            }
        }
        else {
            final Collection var9 = var4.getObjectiveNames();
            if (var9.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
            }
            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + par1ICommandSender.translateString("commands.scoreboard.players.list.count", var9.size()));
            par1ICommandSender.sendChatToPlayer(CommandBase.joinNiceString(var9.toArray()));
        }
    }
    
    protected void setPlayerScore(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr, int par3) {
        final String var4 = par2ArrayOfStr[par3 - 1];
        final String var5 = CommandBase.func_96332_d(par1ICommandSender, par2ArrayOfStr[par3++]);
        final ScoreObjective var6 = this.getScoreObjective(par2ArrayOfStr[par3++], true);
        final int var7 = var4.equalsIgnoreCase("set") ? CommandBase.parseInt(par1ICommandSender, par2ArrayOfStr[par3++]) : CommandBase.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[par3++], 1);
        final Scoreboard var8 = this.getScoreboardFromWorldServer();
        final Score var9 = var8.func_96529_a(var5, var6);
        if (var4.equalsIgnoreCase("set")) {
            var9.func_96647_c(var7);
        }
        else if (var4.equalsIgnoreCase("add")) {
            var9.func_96649_a(var7);
        }
        else {
            var9.func_96646_b(var7);
        }
        CommandBase.notifyAdmins(par1ICommandSender, "commands.scoreboard.players.set.success", var6.getName(), var5, var9.func_96652_c());
    }
    
    protected void resetPlayerScore(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr, int par3) {
        final Scoreboard var4 = this.getScoreboardFromWorldServer();
        final String var5 = CommandBase.func_96332_d(par1ICommandSender, par2ArrayOfStr[par3++]);
        var4.func_96515_c(var5);
        CommandBase.notifyAdmins(par1ICommandSender, "commands.scoreboard.players.reset.success", var5);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "objectives", "players", "teams");
        }
        if (par2ArrayOfStr[0].equalsIgnoreCase("objectives")) {
            if (par2ArrayOfStr.length == 2) {
                return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "list", "add", "remove", "setdisplay");
            }
            if (par2ArrayOfStr[1].equalsIgnoreCase("add")) {
                if (par2ArrayOfStr.length == 4) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, ScoreObjectiveCriteria.field_96643_a.keySet());
                }
            }
            else if (par2ArrayOfStr[1].equalsIgnoreCase("remove")) {
                if (par2ArrayOfStr.length == 3) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.getScoreObjectivesList(false));
                }
            }
            else if (par2ArrayOfStr[1].equalsIgnoreCase("setdisplay")) {
                if (par2ArrayOfStr.length == 3) {
                    return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "list", "sidebar", "belowName");
                }
                if (par2ArrayOfStr.length == 4) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.getScoreObjectivesList(false));
                }
            }
        }
        else if (par2ArrayOfStr[0].equalsIgnoreCase("players")) {
            if (par2ArrayOfStr.length == 2) {
                return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "set", "add", "remove", "reset", "list");
            }
            if (!par2ArrayOfStr[1].equalsIgnoreCase("set") && !par2ArrayOfStr[1].equalsIgnoreCase("add") && !par2ArrayOfStr[1].equalsIgnoreCase("remove")) {
                if ((par2ArrayOfStr[1].equalsIgnoreCase("reset") || par2ArrayOfStr[1].equalsIgnoreCase("list")) && par2ArrayOfStr.length == 3) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.getScoreboardFromWorldServer().getObjectiveNames());
                }
            }
            else {
                if (par2ArrayOfStr.length == 3) {
                    return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
                }
                if (par2ArrayOfStr.length == 4) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.getScoreObjectivesList(true));
                }
            }
        }
        else if (par2ArrayOfStr[0].equalsIgnoreCase("teams")) {
            if (par2ArrayOfStr.length == 2) {
                return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "add", "remove", "join", "leave", "empty", "list", "option");
            }
            if (par2ArrayOfStr[1].equalsIgnoreCase("join")) {
                if (par2ArrayOfStr.length == 3) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.getScoreboardFromWorldServer().func_96531_f());
                }
                if (par2ArrayOfStr.length >= 4) {
                    return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
                }
            }
            else {
                if (par2ArrayOfStr[1].equalsIgnoreCase("leave")) {
                    return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
                }
                if (!par2ArrayOfStr[1].equalsIgnoreCase("empty") && !par2ArrayOfStr[1].equalsIgnoreCase("list") && !par2ArrayOfStr[1].equalsIgnoreCase("remove")) {
                    if (par2ArrayOfStr[1].equalsIgnoreCase("option")) {
                        if (par2ArrayOfStr.length == 3) {
                            return CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.getScoreboardFromWorldServer().func_96531_f());
                        }
                        if (par2ArrayOfStr.length == 4) {
                            return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "color", "friendlyfire", "seeFriendlyInvisibles");
                        }
                        if (par2ArrayOfStr.length == 5) {
                            if (par2ArrayOfStr[3].equalsIgnoreCase("color")) {
                                return CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, EnumChatFormatting.func_96296_a(true, false));
                            }
                            if (par2ArrayOfStr[3].equalsIgnoreCase("friendlyfire") || par2ArrayOfStr[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
                                return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "true", "false");
                            }
                        }
                    }
                }
                else if (par2ArrayOfStr.length == 3) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.getScoreboardFromWorldServer().func_96531_f());
                }
            }
        }
        return null;
    }
    
    protected List getScoreObjectivesList(final boolean par1) {
        final Collection var2 = this.getScoreboardFromWorldServer().getScoreObjectives();
        final ArrayList var3 = new ArrayList();
        for (final ScoreObjective var5 : var2) {
            if (!par1 || !var5.getCriteria().isReadOnly()) {
                var3.add(var5.getName());
            }
        }
        return var3;
    }
    
    @Override
    public boolean isUsernameIndex(final String[] par1ArrayOfStr, final int par2) {
        return par1ArrayOfStr[0].equalsIgnoreCase("players") ? (par2 == 2) : (par1ArrayOfStr[0].equalsIgnoreCase("teams") && (par2 == 2 || par2 == 3));
    }
}
