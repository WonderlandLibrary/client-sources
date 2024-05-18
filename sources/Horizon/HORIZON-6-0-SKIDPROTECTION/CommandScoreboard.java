package HORIZON-6-0-SKIDPROTECTION;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;

public class CommandScoreboard extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000896";
    
    @Override
    public String Ý() {
        return "scoreboard";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.scoreboard.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (!this.Â(sender, args)) {
            if (args.length < 1) {
                throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
            }
            if (args[0].equalsIgnoreCase("objectives")) {
                if (args.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                }
                if (args[1].equalsIgnoreCase("list")) {
                    this.Ø­áŒŠá(sender);
                }
                else if (args[1].equalsIgnoreCase("add")) {
                    if (args.length < 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
                    }
                    this.Â(sender, args, 2);
                }
                else if (args[1].equalsIgnoreCase("remove")) {
                    if (args.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
                    }
                    this.Ø(sender, args[2]);
                }
                else {
                    if (!args[1].equalsIgnoreCase("setdisplay")) {
                        throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                    }
                    if (args.length != 3 && args.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
                    }
                    this.áˆºÑ¢Õ(sender, args, 2);
                }
            }
            else if (args[0].equalsIgnoreCase("players")) {
                if (args.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                }
                if (args[1].equalsIgnoreCase("list")) {
                    if (args.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
                    }
                    this.ÂµÈ(sender, args, 2);
                }
                else if (args[1].equalsIgnoreCase("add")) {
                    if (args.length < 5) {
                        throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
                    }
                    this.á(sender, args, 2);
                }
                else if (args[1].equalsIgnoreCase("remove")) {
                    if (args.length < 5) {
                        throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
                    }
                    this.á(sender, args, 2);
                }
                else if (args[1].equalsIgnoreCase("set")) {
                    if (args.length < 5) {
                        throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
                    }
                    this.á(sender, args, 2);
                }
                else if (args[1].equalsIgnoreCase("reset")) {
                    if (args.length != 3 && args.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
                    }
                    this.ˆÏ­(sender, args, 2);
                }
                else if (args[1].equalsIgnoreCase("enable")) {
                    if (args.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.players.enable.usage", new Object[0]);
                    }
                    this.£á(sender, args, 2);
                }
                else if (args[1].equalsIgnoreCase("test")) {
                    if (args.length != 5 && args.length != 6) {
                        throw new WrongUsageException("commands.scoreboard.players.test.usage", new Object[0]);
                    }
                    this.Å(sender, args, 2);
                }
                else {
                    if (!args[1].equalsIgnoreCase("operation")) {
                        throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                    }
                    if (args.length != 7) {
                        throw new WrongUsageException("commands.scoreboard.players.operation.usage", new Object[0]);
                    }
                    this.£à(sender, args, 2);
                }
            }
            else if (args[0].equalsIgnoreCase("teams")) {
                if (args.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                }
                if (args[1].equalsIgnoreCase("list")) {
                    if (args.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
                    }
                    this.Ó(sender, args, 2);
                }
                else if (args[1].equalsIgnoreCase("add")) {
                    if (args.length < 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
                    }
                    this.Ý(sender, args, 2);
                }
                else if (args[1].equalsIgnoreCase("remove")) {
                    if (args.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
                    }
                    this.Âµá€(sender, args, 2);
                }
                else if (args[1].equalsIgnoreCase("empty")) {
                    if (args.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
                    }
                    this.áŒŠÆ(sender, args, 2);
                }
                else if (args[1].equalsIgnoreCase("join")) {
                    if (args.length < 4 && (args.length != 3 || !(sender instanceof EntityPlayer))) {
                        throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
                    }
                    this.à(sender, args, 2);
                }
                else if (args[1].equalsIgnoreCase("leave")) {
                    if (args.length < 3 && !(sender instanceof EntityPlayer)) {
                        throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
                    }
                    this.Ø(sender, args, 2);
                }
                else {
                    if (!args[1].equalsIgnoreCase("option")) {
                        throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                    }
                    if (args.length != 4 && args.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                    }
                    this.Ø­áŒŠá(sender, args, 2);
                }
            }
        }
    }
    
    private boolean Â(final ICommandSender p_175780_1_, final String[] p_175780_2_) throws CommandException {
        int var3 = -1;
        for (int var4 = 0; var4 < p_175780_2_.length; ++var4) {
            if (this.Â(p_175780_2_, var4) && "*".equals(p_175780_2_[var4])) {
                if (var3 >= 0) {
                    throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
                }
                var3 = var4;
            }
        }
        if (var3 < 0) {
            return false;
        }
        final ArrayList var5 = Lists.newArrayList((Iterable)this.Ø­áŒŠá().Â());
        final String var6 = p_175780_2_[var3];
        final ArrayList var7 = Lists.newArrayList();
        for (final String var9 : var5) {
            p_175780_2_[var3] = var9;
            try {
                this.HorizonCode_Horizon_È(p_175780_1_, p_175780_2_);
                var7.add(var9);
            }
            catch (CommandException var11) {
                final ChatComponentTranslation var10 = new ChatComponentTranslation(var11.getMessage(), var11.HorizonCode_Horizon_È());
                var10.à().HorizonCode_Horizon_È(EnumChatFormatting.ˆÏ­);
                p_175780_1_.HorizonCode_Horizon_È(var10);
            }
        }
        p_175780_2_[var3] = var6;
        p_175780_1_.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ý, var7.size());
        if (var7.size() == 0) {
            throw new WrongUsageException("commands.scoreboard.allMatchesFailed", new Object[0]);
        }
        return true;
    }
    
    protected Scoreboard Ø­áŒŠá() {
        return MinecraftServer.áƒ().HorizonCode_Horizon_È(0).à¢();
    }
    
    protected ScoreObjective HorizonCode_Horizon_È(final String name, final boolean edit) throws CommandException {
        final Scoreboard var3 = this.Ø­áŒŠá();
        final ScoreObjective var4 = var3.HorizonCode_Horizon_È(name);
        if (var4 == null) {
            throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { name });
        }
        if (edit && var4.Ý().Â()) {
            throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { name });
        }
        return var4;
    }
    
    protected ScorePlayerTeam Âµá€(final String name) throws CommandException {
        final Scoreboard var2 = this.Ø­áŒŠá();
        final ScorePlayerTeam var3 = var2.Ý(name);
        if (var3 == null) {
            throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { name });
        }
        return var3;
    }
    
    protected void Â(final ICommandSender sender, final String[] args, int index) throws CommandException {
        final String var4 = args[index++];
        final String var5 = args[index++];
        final Scoreboard var6 = this.Ø­áŒŠá();
        final IScoreObjectiveCriteria var7 = IScoreObjectiveCriteria.HorizonCode_Horizon_È.get(var5);
        if (var7 == null) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[] { var5 });
        }
        if (var6.HorizonCode_Horizon_È(var4) != null) {
            throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { var4 });
        }
        if (var4.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[] { var4, 16 });
        }
        if (var4.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
        }
        if (args.length > index) {
            final String var8 = CommandBase.HorizonCode_Horizon_È(sender, args, index).Ø();
            if (var8.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[] { var8, 32 });
            }
            if (var8.length() > 0) {
                var6.HorizonCode_Horizon_È(var4, var7).HorizonCode_Horizon_È(var8);
            }
            else {
                var6.HorizonCode_Horizon_È(var4, var7);
            }
        }
        else {
            var6.HorizonCode_Horizon_È(var4, var7);
        }
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.scoreboard.objectives.add.success", var4);
    }
    
    protected void Ý(final ICommandSender p_147185_1_, final String[] p_147185_2_, int p_147185_3_) throws CommandException {
        final String var4 = p_147185_2_[p_147185_3_++];
        final Scoreboard var5 = this.Ø­áŒŠá();
        if (var5.Ý(var4) != null) {
            throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { var4 });
        }
        if (var4.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[] { var4, 16 });
        }
        if (var4.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
        }
        if (p_147185_2_.length > p_147185_3_) {
            final String var6 = CommandBase.HorizonCode_Horizon_È(p_147185_1_, p_147185_2_, p_147185_3_).Ø();
            if (var6.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[] { var6, 32 });
            }
            if (var6.length() > 0) {
                var5.Ø­áŒŠá(var4).HorizonCode_Horizon_È(var6);
            }
            else {
                var5.Ø­áŒŠá(var4);
            }
        }
        else {
            var5.Ø­áŒŠá(var4);
        }
        CommandBase.HorizonCode_Horizon_È(p_147185_1_, this, "commands.scoreboard.teams.add.success", var4);
    }
    
    protected void Ø­áŒŠá(final ICommandSender p_147200_1_, final String[] p_147200_2_, int p_147200_3_) throws CommandException {
        final ScorePlayerTeam var4 = this.Âµá€(p_147200_2_[p_147200_3_++]);
        if (var4 != null) {
            final String var5 = p_147200_2_[p_147200_3_++].toLowerCase();
            if (!var5.equalsIgnoreCase("color") && !var5.equalsIgnoreCase("friendlyfire") && !var5.equalsIgnoreCase("seeFriendlyInvisibles") && !var5.equalsIgnoreCase("nametagVisibility") && !var5.equalsIgnoreCase("deathMessageVisibility")) {
                throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
            }
            if (p_147200_2_.length == 4) {
                if (var5.equalsIgnoreCase("color")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.HorizonCode_Horizon_È(EnumChatFormatting.HorizonCode_Horizon_È(true, false)) });
                }
                if (var5.equalsIgnoreCase("friendlyfire") || var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.HorizonCode_Horizon_È((Collection)Arrays.asList("true", "false")) });
                }
                if (!var5.equalsIgnoreCase("nametagVisibility") && !var5.equalsIgnoreCase("deathMessageVisibility")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                }
                throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.HorizonCode_Horizon_È((Object[])Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È()) });
            }
            else {
                final String var6 = p_147200_2_[p_147200_3_];
                if (var5.equalsIgnoreCase("color")) {
                    final EnumChatFormatting var7 = EnumChatFormatting.Â(var6);
                    if (var7 == null || var7.Â()) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.HorizonCode_Horizon_È(EnumChatFormatting.HorizonCode_Horizon_È(true, false)) });
                    }
                    var4.HorizonCode_Horizon_È(var7);
                    var4.Â(var7.toString());
                    var4.Ý(EnumChatFormatting.Æ.toString());
                }
                else if (var5.equalsIgnoreCase("friendlyfire")) {
                    if (!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.HorizonCode_Horizon_È((Collection)Arrays.asList("true", "false")) });
                    }
                    var4.HorizonCode_Horizon_È(var6.equalsIgnoreCase("true"));
                }
                else if (var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    if (!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.HorizonCode_Horizon_È((Collection)Arrays.asList("true", "false")) });
                    }
                    var4.Â(var6.equalsIgnoreCase("true"));
                }
                else if (var5.equalsIgnoreCase("nametagVisibility")) {
                    final Team.HorizonCode_Horizon_È var8 = Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var6);
                    if (var8 == null) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.HorizonCode_Horizon_È((Object[])Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È()) });
                    }
                    var4.HorizonCode_Horizon_È(var8);
                }
                else if (var5.equalsIgnoreCase("deathMessageVisibility")) {
                    final Team.HorizonCode_Horizon_È var8 = Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var6);
                    if (var8 == null) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.HorizonCode_Horizon_È((Object[])Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È()) });
                    }
                    var4.Â(var8);
                }
                CommandBase.HorizonCode_Horizon_È(p_147200_1_, this, "commands.scoreboard.teams.option.success", var5, var4.HorizonCode_Horizon_È(), var6);
            }
        }
    }
    
    protected void Âµá€(final ICommandSender p_147194_1_, final String[] p_147194_2_, final int p_147194_3_) throws CommandException {
        final Scoreboard var4 = this.Ø­áŒŠá();
        final ScorePlayerTeam var5 = this.Âµá€(p_147194_2_[p_147194_3_]);
        if (var5 != null) {
            var4.HorizonCode_Horizon_È(var5);
            CommandBase.HorizonCode_Horizon_È(p_147194_1_, this, "commands.scoreboard.teams.remove.success", var5.HorizonCode_Horizon_È());
        }
    }
    
    protected void Ó(final ICommandSender p_147186_1_, final String[] p_147186_2_, final int p_147186_3_) throws CommandException {
        final Scoreboard var4 = this.Ø­áŒŠá();
        if (p_147186_2_.length > p_147186_3_) {
            final ScorePlayerTeam var5 = this.Âµá€(p_147186_2_[p_147186_3_]);
            if (var5 == null) {
                return;
            }
            final Collection var6 = var5.Ý();
            p_147186_1_.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Âµá€, var6.size());
            if (var6.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { var5.HorizonCode_Horizon_È() });
            }
            final ChatComponentTranslation var7 = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", new Object[] { var6.size(), var5.HorizonCode_Horizon_È() });
            var7.à().HorizonCode_Horizon_È(EnumChatFormatting.Ý);
            p_147186_1_.HorizonCode_Horizon_È(var7);
            p_147186_1_.HorizonCode_Horizon_È(new ChatComponentText(CommandBase.HorizonCode_Horizon_È(var6.toArray())));
        }
        else {
            final Collection var8 = var4.Âµá€();
            p_147186_1_.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Âµá€, var8.size());
            if (var8.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
            }
            final ChatComponentTranslation var9 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", new Object[] { var8.size() });
            var9.à().HorizonCode_Horizon_È(EnumChatFormatting.Ý);
            p_147186_1_.HorizonCode_Horizon_È(var9);
            for (final ScorePlayerTeam var11 : var8) {
                p_147186_1_.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.scoreboard.teams.list.entry", new Object[] { var11.HorizonCode_Horizon_È(), var11.Â(), var11.Ý().size() }));
            }
        }
    }
    
    protected void à(final ICommandSender p_147190_1_, final String[] p_147190_2_, int p_147190_3_) throws CommandException {
        final Scoreboard var4 = this.Ø­áŒŠá();
        final String var5 = p_147190_2_[p_147190_3_++];
        final HashSet var6 = Sets.newHashSet();
        final HashSet var7 = Sets.newHashSet();
        if (p_147190_1_ instanceof EntityPlayer && p_147190_3_ == p_147190_2_.length) {
            final String var8 = CommandBase.Â(p_147190_1_).v_();
            if (var4.HorizonCode_Horizon_È(var8, var5)) {
                var6.add(var8);
            }
            else {
                var7.add(var8);
            }
        }
        else {
            while (p_147190_3_ < p_147190_2_.length) {
                final String var8 = p_147190_2_[p_147190_3_++];
                if (var8.startsWith("@")) {
                    final List var9 = CommandBase.Ý(p_147190_1_, var8);
                    for (final Entity var11 : var9) {
                        final String var12 = CommandBase.Âµá€(p_147190_1_, var11.£áŒŠá().toString());
                        if (var4.HorizonCode_Horizon_È(var12, var5)) {
                            var6.add(var12);
                        }
                        else {
                            var7.add(var12);
                        }
                    }
                }
                else {
                    final String var13 = CommandBase.Âµá€(p_147190_1_, var8);
                    if (var4.HorizonCode_Horizon_È(var13, var5)) {
                        var6.add(var13);
                    }
                    else {
                        var7.add(var13);
                    }
                }
            }
        }
        if (!var6.isEmpty()) {
            p_147190_1_.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ý, var6.size());
            CommandBase.HorizonCode_Horizon_È(p_147190_1_, this, "commands.scoreboard.teams.join.success", var6.size(), var5, CommandBase.HorizonCode_Horizon_È(var6.toArray(new String[0])));
        }
        if (!var7.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { var7.size(), var5, CommandBase.HorizonCode_Horizon_È(var7.toArray(new String[0])) });
        }
    }
    
    protected void Ø(final ICommandSender p_147199_1_, final String[] p_147199_2_, int p_147199_3_) throws CommandException {
        final Scoreboard var4 = this.Ø­áŒŠá();
        final HashSet var5 = Sets.newHashSet();
        final HashSet var6 = Sets.newHashSet();
        if (p_147199_1_ instanceof EntityPlayer && p_147199_3_ == p_147199_2_.length) {
            final String var7 = CommandBase.Â(p_147199_1_).v_();
            if (var4.Âµá€(var7)) {
                var5.add(var7);
            }
            else {
                var6.add(var7);
            }
        }
        else {
            while (p_147199_3_ < p_147199_2_.length) {
                final String var7 = p_147199_2_[p_147199_3_++];
                if (var7.startsWith("@")) {
                    final List var8 = CommandBase.Ý(p_147199_1_, var7);
                    for (final Entity var10 : var8) {
                        final String var11 = CommandBase.Âµá€(p_147199_1_, var10.£áŒŠá().toString());
                        if (var4.Âµá€(var11)) {
                            var5.add(var11);
                        }
                        else {
                            var6.add(var11);
                        }
                    }
                }
                else {
                    final String var12 = CommandBase.Âµá€(p_147199_1_, var7);
                    if (var4.Âµá€(var12)) {
                        var5.add(var12);
                    }
                    else {
                        var6.add(var12);
                    }
                }
            }
        }
        if (!var5.isEmpty()) {
            p_147199_1_.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ý, var5.size());
            CommandBase.HorizonCode_Horizon_È(p_147199_1_, this, "commands.scoreboard.teams.leave.success", var5.size(), CommandBase.HorizonCode_Horizon_È(var5.toArray(new String[0])));
        }
        if (!var6.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { var6.size(), CommandBase.HorizonCode_Horizon_È(var6.toArray(new String[0])) });
        }
    }
    
    protected void áŒŠÆ(final ICommandSender p_147188_1_, final String[] p_147188_2_, final int p_147188_3_) throws CommandException {
        final Scoreboard var4 = this.Ø­áŒŠá();
        final ScorePlayerTeam var5 = this.Âµá€(p_147188_2_[p_147188_3_]);
        if (var5 != null) {
            final ArrayList var6 = Lists.newArrayList((Iterable)var5.Ý());
            p_147188_1_.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ý, var6.size());
            if (var6.isEmpty()) {
                throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { var5.HorizonCode_Horizon_È() });
            }
            for (final String var8 : var6) {
                var4.HorizonCode_Horizon_È(var8, var5);
            }
            CommandBase.HorizonCode_Horizon_È(p_147188_1_, this, "commands.scoreboard.teams.empty.success", var6.size(), var5.HorizonCode_Horizon_È());
        }
    }
    
    protected void Ø(final ICommandSender p_147191_1_, final String p_147191_2_) throws CommandException {
        final Scoreboard var3 = this.Ø­áŒŠá();
        final ScoreObjective var4 = this.HorizonCode_Horizon_È(p_147191_2_, false);
        var3.Â(var4);
        CommandBase.HorizonCode_Horizon_È(p_147191_1_, this, "commands.scoreboard.objectives.remove.success", p_147191_2_);
    }
    
    protected void Ø­áŒŠá(final ICommandSender p_147196_1_) throws CommandException {
        final Scoreboard var2 = this.Ø­áŒŠá();
        final Collection var3 = var2.HorizonCode_Horizon_È();
        if (var3.size() <= 0) {
            throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
        }
        final ChatComponentTranslation var4 = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", new Object[] { var3.size() });
        var4.à().HorizonCode_Horizon_È(EnumChatFormatting.Ý);
        p_147196_1_.HorizonCode_Horizon_È(var4);
        for (final ScoreObjective var6 : var3) {
            p_147196_1_.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[] { var6.Â(), var6.Ø­áŒŠá(), var6.Ý().HorizonCode_Horizon_È() }));
        }
    }
    
    protected void áˆºÑ¢Õ(final ICommandSender p_147198_1_, final String[] p_147198_2_, int p_147198_3_) throws CommandException {
        final Scoreboard var4 = this.Ø­áŒŠá();
        final String var5 = p_147198_2_[p_147198_3_++];
        final int var6 = Scoreboard.Ø(var5);
        ScoreObjective var7 = null;
        if (p_147198_2_.length == 4) {
            var7 = this.HorizonCode_Horizon_È(p_147198_2_[p_147198_3_], false);
        }
        if (var6 < 0) {
            throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { var5 });
        }
        var4.HorizonCode_Horizon_È(var6, var7);
        if (var7 != null) {
            CommandBase.HorizonCode_Horizon_È(p_147198_1_, this, "commands.scoreboard.objectives.setdisplay.successSet", Scoreboard.Â(var6), var7.Â());
        }
        else {
            CommandBase.HorizonCode_Horizon_È(p_147198_1_, this, "commands.scoreboard.objectives.setdisplay.successCleared", Scoreboard.Â(var6));
        }
    }
    
    protected void ÂµÈ(final ICommandSender p_147195_1_, final String[] p_147195_2_, final int p_147195_3_) throws CommandException {
        final Scoreboard var4 = this.Ø­áŒŠá();
        if (p_147195_2_.length > p_147195_3_) {
            final String var5 = CommandBase.Âµá€(p_147195_1_, p_147195_2_[p_147195_3_]);
            final Map var6 = var4.Â(var5);
            p_147195_1_.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Âµá€, var6.size());
            if (var6.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { var5 });
            }
            final ChatComponentTranslation var7 = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", new Object[] { var6.size(), var5 });
            var7.à().HorizonCode_Horizon_È(EnumChatFormatting.Ý);
            p_147195_1_.HorizonCode_Horizon_È(var7);
            for (final Score var9 : var6.values()) {
                p_147195_1_.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[] { var9.Â(), var9.Ý().Ø­áŒŠá(), var9.Ý().Â() }));
            }
        }
        else {
            final Collection var10 = var4.Â();
            p_147195_1_.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Âµá€, var10.size());
            if (var10.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
            }
            final ChatComponentTranslation var11 = new ChatComponentTranslation("commands.scoreboard.players.list.count", new Object[] { var10.size() });
            var11.à().HorizonCode_Horizon_È(EnumChatFormatting.Ý);
            p_147195_1_.HorizonCode_Horizon_È(var11);
            p_147195_1_.HorizonCode_Horizon_È(new ChatComponentText(CommandBase.HorizonCode_Horizon_È(var10.toArray())));
        }
    }
    
    protected void á(final ICommandSender p_147197_1_, final String[] p_147197_2_, int p_147197_3_) throws CommandException {
        final String var4 = p_147197_2_[p_147197_3_ - 1];
        final int var5 = p_147197_3_;
        final String var6 = CommandBase.Âµá€(p_147197_1_, p_147197_2_[p_147197_3_++]);
        final ScoreObjective var7 = this.HorizonCode_Horizon_È(p_147197_2_[p_147197_3_++], true);
        final int var8 = var4.equalsIgnoreCase("set") ? CommandBase.HorizonCode_Horizon_È(p_147197_2_[p_147197_3_++]) : CommandBase.HorizonCode_Horizon_È(p_147197_2_[p_147197_3_++], 0);
        if (p_147197_2_.length > p_147197_3_) {
            final Entity var9 = CommandBase.Â(p_147197_1_, p_147197_2_[var5]);
            try {
                final NBTTagCompound var10 = JsonToNBT.HorizonCode_Horizon_È(CommandBase.HorizonCode_Horizon_È(p_147197_2_, p_147197_3_));
                final NBTTagCompound var11 = new NBTTagCompound();
                var9.Âµá€(var11);
                if (!CommandTestForBlock.HorizonCode_Horizon_È(var10, var11, true)) {
                    throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[] { var6 });
                }
            }
            catch (NBTException var12) {
                throw new CommandException("commands.scoreboard.players.set.tagError", new Object[] { var12.getMessage() });
            }
        }
        final Scoreboard var13 = this.Ø­áŒŠá();
        final Score var14 = var13.Â(var6, var7);
        if (var4.equalsIgnoreCase("set")) {
            var14.Ý(var8);
        }
        else if (var4.equalsIgnoreCase("add")) {
            var14.HorizonCode_Horizon_È(var8);
        }
        else {
            var14.Â(var8);
        }
        CommandBase.HorizonCode_Horizon_È(p_147197_1_, this, "commands.scoreboard.players.set.success", var7.Â(), var6, var14.Â());
    }
    
    protected void ˆÏ­(final ICommandSender p_147187_1_, final String[] p_147187_2_, int p_147187_3_) throws CommandException {
        final Scoreboard var4 = this.Ø­áŒŠá();
        final String var5 = CommandBase.Âµá€(p_147187_1_, p_147187_2_[p_147187_3_++]);
        if (p_147187_2_.length > p_147187_3_) {
            final ScoreObjective var6 = this.HorizonCode_Horizon_È(p_147187_2_[p_147187_3_++], false);
            var4.Ý(var5, var6);
            CommandBase.HorizonCode_Horizon_È(p_147187_1_, this, "commands.scoreboard.players.resetscore.success", var6.Â(), var5);
        }
        else {
            var4.Ý(var5, null);
            CommandBase.HorizonCode_Horizon_È(p_147187_1_, this, "commands.scoreboard.players.reset.success", var5);
        }
    }
    
    protected void £á(final ICommandSender p_175779_1_, final String[] p_175779_2_, int p_175779_3_) throws CommandException {
        final Scoreboard var4 = this.Ø­áŒŠá();
        final String var5 = CommandBase.Ø­áŒŠá(p_175779_1_, p_175779_2_[p_175779_3_++]);
        final ScoreObjective var6 = this.HorizonCode_Horizon_È(p_175779_2_[p_175779_3_], false);
        if (var6.Ý() != IScoreObjectiveCriteria.Ý) {
            throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[] { var6.Â() });
        }
        final Score var7 = var4.Â(var5, var6);
        var7.HorizonCode_Horizon_È(false);
        CommandBase.HorizonCode_Horizon_È(p_175779_1_, this, "commands.scoreboard.players.enable.success", var6.Â(), var5);
    }
    
    protected void Å(final ICommandSender p_175781_1_, final String[] p_175781_2_, int p_175781_3_) throws CommandException {
        final Scoreboard var4 = this.Ø­áŒŠá();
        final String var5 = CommandBase.Âµá€(p_175781_1_, p_175781_2_[p_175781_3_++]);
        final ScoreObjective var6 = this.HorizonCode_Horizon_È(p_175781_2_[p_175781_3_++], false);
        if (!var4.HorizonCode_Horizon_È(var5, var6)) {
            throw new CommandException("commands.scoreboard.players.test.notFound", new Object[] { var6.Â(), var5 });
        }
        final int var7 = p_175781_2_[p_175781_3_].equals("*") ? Integer.MIN_VALUE : CommandBase.HorizonCode_Horizon_È(p_175781_2_[p_175781_3_]);
        final int var8 = (++p_175781_3_ < p_175781_2_.length && !p_175781_2_[p_175781_3_].equals("*")) ? CommandBase.HorizonCode_Horizon_È(p_175781_2_[p_175781_3_], var7) : Integer.MAX_VALUE;
        final Score var9 = var4.Â(var5, var6);
        if (var9.Â() >= var7 && var9.Â() <= var8) {
            CommandBase.HorizonCode_Horizon_È(p_175781_1_, this, "commands.scoreboard.players.test.success", var9.Â(), var7, var8);
            return;
        }
        throw new CommandException("commands.scoreboard.players.test.failed", new Object[] { var9.Â(), var7, var8 });
    }
    
    protected void £à(final ICommandSender p_175778_1_, final String[] p_175778_2_, int p_175778_3_) throws CommandException {
        final Scoreboard var4 = this.Ø­áŒŠá();
        final String var5 = CommandBase.Âµá€(p_175778_1_, p_175778_2_[p_175778_3_++]);
        final ScoreObjective var6 = this.HorizonCode_Horizon_È(p_175778_2_[p_175778_3_++], true);
        final String var7 = p_175778_2_[p_175778_3_++];
        final String var8 = CommandBase.Âµá€(p_175778_1_, p_175778_2_[p_175778_3_++]);
        final ScoreObjective var9 = this.HorizonCode_Horizon_È(p_175778_2_[p_175778_3_], false);
        final Score var10 = var4.Â(var5, var6);
        if (!var4.HorizonCode_Horizon_È(var8, var9)) {
            throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[] { var9.Â(), var8 });
        }
        final Score var11 = var4.Â(var8, var9);
        if (var7.equals("+=")) {
            var10.Ý(var10.Â() + var11.Â());
        }
        else if (var7.equals("-=")) {
            var10.Ý(var10.Â() - var11.Â());
        }
        else if (var7.equals("*=")) {
            var10.Ý(var10.Â() * var11.Â());
        }
        else if (var7.equals("/=")) {
            if (var11.Â() != 0) {
                var10.Ý(var10.Â() / var11.Â());
            }
        }
        else if (var7.equals("%=")) {
            if (var11.Â() != 0) {
                var10.Ý(var10.Â() % var11.Â());
            }
        }
        else if (var7.equals("=")) {
            var10.Ý(var11.Â());
        }
        else if (var7.equals("<")) {
            var10.Ý(Math.min(var10.Â(), var11.Â()));
        }
        else if (var7.equals(">")) {
            var10.Ý(Math.max(var10.Â(), var11.Â()));
        }
        else {
            if (!var7.equals("><")) {
                throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[] { var7 });
            }
            final int var12 = var10.Â();
            var10.Ý(var11.Â());
            var11.Ý(var12);
        }
        CommandBase.HorizonCode_Horizon_È(p_175778_1_, this, "commands.scoreboard.players.operation.success", new Object[0]);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (args.length == 1) {
            return CommandBase.HorizonCode_Horizon_È(args, "objectives", "players", "teams");
        }
        if (args[0].equalsIgnoreCase("objectives")) {
            if (args.length == 2) {
                return CommandBase.HorizonCode_Horizon_È(args, "list", "add", "remove", "setdisplay");
            }
            if (args[1].equalsIgnoreCase("add")) {
                if (args.length == 4) {
                    final Set var4 = IScoreObjectiveCriteria.HorizonCode_Horizon_È.keySet();
                    return CommandBase.HorizonCode_Horizon_È(args, var4);
                }
            }
            else if (args[1].equalsIgnoreCase("remove")) {
                if (args.length == 3) {
                    return CommandBase.HorizonCode_Horizon_È(args, this.HorizonCode_Horizon_È(false));
                }
            }
            else if (args[1].equalsIgnoreCase("setdisplay")) {
                if (args.length == 3) {
                    return CommandBase.HorizonCode_Horizon_È(args, Scoreboard.Ó());
                }
                if (args.length == 4) {
                    return CommandBase.HorizonCode_Horizon_È(args, this.HorizonCode_Horizon_È(false));
                }
            }
        }
        else if (args[0].equalsIgnoreCase("players")) {
            if (args.length == 2) {
                return CommandBase.HorizonCode_Horizon_È(args, "set", "add", "remove", "reset", "list", "enable", "test", "operation");
            }
            if (!args[1].equalsIgnoreCase("set") && !args[1].equalsIgnoreCase("add") && !args[1].equalsIgnoreCase("remove") && !args[1].equalsIgnoreCase("reset")) {
                if (args[1].equalsIgnoreCase("enable")) {
                    if (args.length == 3) {
                        return CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá());
                    }
                    if (args.length == 4) {
                        return CommandBase.HorizonCode_Horizon_È(args, this.Âµá€());
                    }
                }
                else if (!args[1].equalsIgnoreCase("list") && !args[1].equalsIgnoreCase("test")) {
                    if (args[1].equalsIgnoreCase("operation")) {
                        if (args.length == 3) {
                            return CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá().Â());
                        }
                        if (args.length == 4) {
                            return CommandBase.HorizonCode_Horizon_È(args, this.HorizonCode_Horizon_È(true));
                        }
                        if (args.length == 5) {
                            return CommandBase.HorizonCode_Horizon_È(args, "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><");
                        }
                        if (args.length == 6) {
                            return CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá());
                        }
                        if (args.length == 7) {
                            return CommandBase.HorizonCode_Horizon_È(args, this.HorizonCode_Horizon_È(false));
                        }
                    }
                }
                else {
                    if (args.length == 3) {
                        return CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá().Â());
                    }
                    if (args.length == 4 && args[1].equalsIgnoreCase("test")) {
                        return CommandBase.HorizonCode_Horizon_È(args, this.HorizonCode_Horizon_È(false));
                    }
                }
            }
            else {
                if (args.length == 3) {
                    return CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá());
                }
                if (args.length == 4) {
                    return CommandBase.HorizonCode_Horizon_È(args, this.HorizonCode_Horizon_È(true));
                }
            }
        }
        else if (args[0].equalsIgnoreCase("teams")) {
            if (args.length == 2) {
                return CommandBase.HorizonCode_Horizon_È(args, "add", "remove", "join", "leave", "empty", "list", "option");
            }
            if (args[1].equalsIgnoreCase("join")) {
                if (args.length == 3) {
                    return CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá().Ø­áŒŠá());
                }
                if (args.length >= 4) {
                    return CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá());
                }
            }
            else {
                if (args[1].equalsIgnoreCase("leave")) {
                    return CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá());
                }
                if (!args[1].equalsIgnoreCase("empty") && !args[1].equalsIgnoreCase("list") && !args[1].equalsIgnoreCase("remove")) {
                    if (args[1].equalsIgnoreCase("option")) {
                        if (args.length == 3) {
                            return CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá().Ø­áŒŠá());
                        }
                        if (args.length == 4) {
                            return CommandBase.HorizonCode_Horizon_È(args, "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility");
                        }
                        if (args.length == 5) {
                            if (args[3].equalsIgnoreCase("color")) {
                                return CommandBase.HorizonCode_Horizon_È(args, EnumChatFormatting.HorizonCode_Horizon_È(true, false));
                            }
                            if (args[3].equalsIgnoreCase("nametagVisibility") || args[3].equalsIgnoreCase("deathMessageVisibility")) {
                                return CommandBase.HorizonCode_Horizon_È(args, Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È());
                            }
                            if (args[3].equalsIgnoreCase("friendlyfire") || args[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
                                return CommandBase.HorizonCode_Horizon_È(args, "true", "false");
                            }
                        }
                    }
                }
                else if (args.length == 3) {
                    return CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá().Ø­áŒŠá());
                }
            }
        }
        return null;
    }
    
    protected List HorizonCode_Horizon_È(final boolean p_147184_1_) {
        final Collection var2 = this.Ø­áŒŠá().HorizonCode_Horizon_È();
        final ArrayList var3 = Lists.newArrayList();
        for (final ScoreObjective var5 : var2) {
            if (!p_147184_1_ || !var5.Ý().Â()) {
                var3.add(var5.Â());
            }
        }
        return var3;
    }
    
    protected List Âµá€() {
        final Collection var1 = this.Ø­áŒŠá().HorizonCode_Horizon_È();
        final ArrayList var2 = Lists.newArrayList();
        for (final ScoreObjective var4 : var1) {
            if (var4.Ý() == IScoreObjectiveCriteria.Ý) {
                var2.add(var4.Â());
            }
        }
        return var2;
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return args[0].equalsIgnoreCase("players") ? ((args.length > 1 && args[1].equalsIgnoreCase("operation")) ? (index == 2 || index == 5) : (index == 2)) : (args[0].equalsIgnoreCase("teams") && index == 2);
    }
}
