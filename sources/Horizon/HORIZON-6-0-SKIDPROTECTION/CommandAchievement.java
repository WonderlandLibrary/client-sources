package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import com.google.common.collect.Iterators;
import com.google.common.base.Predicate;
import java.util.List;
import com.google.common.collect.Lists;

public class CommandAchievement extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000113";
    
    @Override
    public String Ý() {
        return "achievement";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.achievement.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException("commands.achievement.usage", new Object[0]);
        }
        final StatBase var3 = StatList.HorizonCode_Horizon_È(args[1]);
        if (var3 == null && !args[1].equals("*")) {
            throw new CommandException("commands.achievement.unknownAchievement", new Object[] { args[1] });
        }
        final EntityPlayerMP var4 = (args.length >= 3) ? CommandBase.HorizonCode_Horizon_È(sender, args[2]) : CommandBase.Â(sender);
        final boolean var5 = args[0].equalsIgnoreCase("give");
        final boolean var6 = args[0].equalsIgnoreCase("take");
        if (var5 || var6) {
            if (var3 == null) {
                if (var5) {
                    for (final Achievement var8 : AchievementList.Âµá€) {
                        var4.HorizonCode_Horizon_È(var8);
                    }
                    CommandBase.HorizonCode_Horizon_È(sender, this, "commands.achievement.give.success.all", var4.v_());
                }
                else if (var6) {
                    for (final Achievement var8 : Lists.reverse(AchievementList.Âµá€)) {
                        var4.Â(var8);
                    }
                    CommandBase.HorizonCode_Horizon_È(sender, this, "commands.achievement.take.success.all", var4.v_());
                }
            }
            else {
                if (var3 instanceof Achievement) {
                    Achievement var9 = (Achievement)var3;
                    if (var5) {
                        if (var4.áˆºÕ().HorizonCode_Horizon_È(var9)) {
                            throw new CommandException("commands.achievement.alreadyHave", new Object[] { var4.v_(), var3.áˆºÑ¢Õ() });
                        }
                        final ArrayList var10 = Lists.newArrayList();
                        while (var9.Âµá€ != null && !var4.áˆºÕ().HorizonCode_Horizon_È(var9.Âµá€)) {
                            var10.add(var9.Âµá€);
                            var9 = var9.Âµá€;
                        }
                        for (final Achievement var12 : Lists.reverse((List)var10)) {
                            var4.HorizonCode_Horizon_È(var12);
                        }
                    }
                    else if (var6) {
                        if (!var4.áˆºÕ().HorizonCode_Horizon_È(var9)) {
                            throw new CommandException("commands.achievement.dontHave", new Object[] { var4.v_(), var3.áˆºÑ¢Õ() });
                        }
                        final ArrayList var10 = Lists.newArrayList((Iterator)Iterators.filter((Iterator)AchievementList.Âµá€.iterator(), (Predicate)new Predicate() {
                            private static final String Â = "CL_00002350";
                            
                            public boolean HorizonCode_Horizon_È(final Achievement p_179605_1_) {
                                return var4.áˆºÕ().HorizonCode_Horizon_È(p_179605_1_) && p_179605_1_ != var3;
                            }
                            
                            public boolean apply(final Object p_apply_1_) {
                                return this.HorizonCode_Horizon_È((Achievement)p_apply_1_);
                            }
                        }));
                        while (var9.Âµá€ != null && var4.áˆºÕ().HorizonCode_Horizon_È(var9.Âµá€)) {
                            var10.remove(var9.Âµá€);
                            var9 = var9.Âµá€;
                        }
                        for (final Achievement var12 : var10) {
                            var4.Â(var12);
                        }
                    }
                }
                if (var5) {
                    var4.HorizonCode_Horizon_È(var3);
                    CommandBase.HorizonCode_Horizon_È(sender, this, "commands.achievement.give.success.one", var4.v_(), var3.áˆºÑ¢Õ());
                }
                else if (var6) {
                    var4.Â(var3);
                    CommandBase.HorizonCode_Horizon_È(sender, this, "commands.achievement.take.success.one", var3.áˆºÑ¢Õ(), var4.v_());
                }
            }
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (args.length == 1) {
            return CommandBase.HorizonCode_Horizon_È(args, "give", "take");
        }
        if (args.length != 2) {
            return (args.length == 3) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá()) : null;
        }
        final ArrayList var4 = Lists.newArrayList();
        for (final StatBase var6 : StatList.Â) {
            var4.add(var6.à);
        }
        return CommandBase.HorizonCode_Horizon_È(args, var4);
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 2;
    }
}
