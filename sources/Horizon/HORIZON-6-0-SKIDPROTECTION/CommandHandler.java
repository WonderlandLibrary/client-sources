package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import com.google.common.collect.Sets;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import java.util.Set;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class CommandHandler implements ICommandManager
{
    private static final Logger HorizonCode_Horizon_È;
    private final Map Â;
    private final Set Ý;
    private static final String Ø­áŒŠá = "CL_00001765";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public CommandHandler() {
        this.Â = Maps.newHashMap();
        this.Ý = Sets.newHashSet();
    }
    
    @Override
    public int HorizonCode_Horizon_È(final ICommandSender sender, String command) {
        command = command.trim();
        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        String[] var3 = command.split(" ");
        final String var4 = var3[0];
        var3 = HorizonCode_Horizon_È(var3);
        final ICommand var5 = this.Â.get(var4);
        final int var6 = this.HorizonCode_Horizon_È(var5, var3);
        int var7 = 0;
        if (var5 == null) {
            final ChatComponentTranslation var8 = new ChatComponentTranslation("commands.generic.notFound", new Object[0]);
            var8.à().HorizonCode_Horizon_È(EnumChatFormatting.ˆÏ­);
            sender.HorizonCode_Horizon_È(var8);
        }
        else if (var5.HorizonCode_Horizon_È(sender)) {
            if (var6 > -1) {
                final List var9 = PlayerSelector.Â(sender, var3[var6], Entity.class);
                final String var10 = var3[var6];
                sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ý, var9.size());
                for (final Entity var12 : var9) {
                    var3[var6] = var12.£áŒŠá().toString();
                    if (this.HorizonCode_Horizon_È(sender, var3, var5, command)) {
                        ++var7;
                    }
                }
                var3[var6] = var10;
            }
            else {
                sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ý, 1);
                if (this.HorizonCode_Horizon_È(sender, var3, var5, command)) {
                    ++var7;
                }
            }
        }
        else {
            final ChatComponentTranslation var8 = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
            var8.à().HorizonCode_Horizon_È(EnumChatFormatting.ˆÏ­);
            sender.HorizonCode_Horizon_È(var8);
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.HorizonCode_Horizon_È, var7);
        return var7;
    }
    
    protected boolean HorizonCode_Horizon_È(final ICommandSender p_175786_1_, final String[] p_175786_2_, final ICommand p_175786_3_, final String p_175786_4_) {
        try {
            p_175786_3_.HorizonCode_Horizon_È(p_175786_1_, p_175786_2_);
            return true;
        }
        catch (WrongUsageException var7) {
            final ChatComponentTranslation var6 = new ChatComponentTranslation("commands.generic.usage", new Object[] { new ChatComponentTranslation(var7.getMessage(), var7.HorizonCode_Horizon_È()) });
            var6.à().HorizonCode_Horizon_È(EnumChatFormatting.ˆÏ­);
            p_175786_1_.HorizonCode_Horizon_È(var6);
        }
        catch (CommandException var8) {
            final ChatComponentTranslation var6 = new ChatComponentTranslation(var8.getMessage(), var8.HorizonCode_Horizon_È());
            var6.à().HorizonCode_Horizon_È(EnumChatFormatting.ˆÏ­);
            p_175786_1_.HorizonCode_Horizon_È(var6);
        }
        catch (Throwable var9) {
            final ChatComponentTranslation var6 = new ChatComponentTranslation("commands.generic.exception", new Object[0]);
            var6.à().HorizonCode_Horizon_È(EnumChatFormatting.ˆÏ­);
            p_175786_1_.HorizonCode_Horizon_È(var6);
            CommandHandler.HorizonCode_Horizon_È.error("Couldn't process command: '" + p_175786_4_ + "'", var9);
        }
        return false;
    }
    
    public ICommand HorizonCode_Horizon_È(final ICommand p_71560_1_) {
        this.Â.put(p_71560_1_.Ý(), p_71560_1_);
        this.Ý.add(p_71560_1_);
        for (final String var3 : p_71560_1_.Â()) {
            final ICommand var4 = this.Â.get(var3);
            if (var4 == null || !var4.Ý().equals(var3)) {
                this.Â.put(var3, p_71560_1_);
            }
        }
        return p_71560_1_;
    }
    
    private static String[] HorizonCode_Horizon_È(final String[] p_71559_0_) {
        final String[] var1 = new String[p_71559_0_.length - 1];
        System.arraycopy(p_71559_0_, 1, var1, 0, p_71559_0_.length - 1);
        return var1;
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String input, final BlockPos pos) {
        final String[] var4 = input.split(" ", -1);
        final String var5 = var4[0];
        if (var4.length == 1) {
            final ArrayList var6 = Lists.newArrayList();
            for (final Map.Entry var8 : this.Â.entrySet()) {
                if (CommandBase.HorizonCode_Horizon_È(var5, var8.getKey()) && var8.getValue().HorizonCode_Horizon_È(sender)) {
                    var6.add(var8.getKey());
                }
            }
            return var6;
        }
        if (var4.length > 1) {
            final ICommand var9 = this.Â.get(var5);
            if (var9 != null && var9.HorizonCode_Horizon_È(sender)) {
                return var9.HorizonCode_Horizon_È(sender, HorizonCode_Horizon_È(var4), pos);
            }
        }
        return null;
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender) {
        final ArrayList var2 = Lists.newArrayList();
        for (final ICommand var4 : this.Ý) {
            if (var4.HorizonCode_Horizon_È(sender)) {
                var2.add(var4);
            }
        }
        return var2;
    }
    
    @Override
    public Map HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    private int HorizonCode_Horizon_È(final ICommand p_82370_1_, final String[] p_82370_2_) {
        if (p_82370_1_ == null) {
            return -1;
        }
        for (int var3 = 0; var3 < p_82370_2_.length; ++var3) {
            if (p_82370_1_.Â(p_82370_2_, var3) && PlayerSelector.HorizonCode_Horizon_È(p_82370_2_[var3])) {
                return var3;
            }
        }
        return -1;
    }
}
