package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandBanIp extends CommandBase
{
    public static final Pattern HorizonCode_Horizon_È;
    private static final String Â = "CL_00000139";
    
    static {
        HorizonCode_Horizon_È = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    }
    
    @Override
    public String Ý() {
        return "ban-ip";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ICommandSender sender) {
        return MinecraftServer.áƒ().Œ().áˆºÑ¢Õ().HorizonCode_Horizon_È() && super.HorizonCode_Horizon_È(sender);
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.banip.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length >= 1 && args[0].length() > 1) {
            final IChatComponent var3 = (args.length >= 2) ? CommandBase.HorizonCode_Horizon_È(sender, args, 1) : null;
            final Matcher var4 = CommandBanIp.HorizonCode_Horizon_È.matcher(args[0]);
            if (var4.matches()) {
                this.HorizonCode_Horizon_È(sender, args[0], (var3 == null) ? null : var3.Ø());
            }
            else {
                final EntityPlayerMP var5 = MinecraftServer.áƒ().Œ().HorizonCode_Horizon_È(args[0]);
                if (var5 == null) {
                    throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
                }
                this.HorizonCode_Horizon_È(sender, var5.ÐƒÇŽà(), (var3 == null) ? null : var3.Ø());
            }
            return;
        }
        throw new WrongUsageException("commands.banip.usage", new Object[0]);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá()) : null;
    }
    
    protected void HorizonCode_Horizon_È(final ICommandSender p_147210_1_, final String p_147210_2_, final String p_147210_3_) {
        final IPBanEntry var4 = new IPBanEntry(p_147210_2_, null, p_147210_1_.v_(), null, p_147210_3_);
        MinecraftServer.áƒ().Œ().áˆºÑ¢Õ().HorizonCode_Horizon_È(var4);
        final List var5 = MinecraftServer.áƒ().Œ().Â(p_147210_2_);
        final String[] var6 = new String[var5.size()];
        int var7 = 0;
        for (final EntityPlayerMP var9 : var5) {
            var9.HorizonCode_Horizon_È.HorizonCode_Horizon_È("You have been IP banned.");
            var6[var7++] = var9.v_();
        }
        if (var5.isEmpty()) {
            CommandBase.HorizonCode_Horizon_È(p_147210_1_, this, "commands.banip.success", p_147210_2_);
        }
        else {
            CommandBase.HorizonCode_Horizon_È(p_147210_1_, this, "commands.banip.success.players", p_147210_2_, CommandBase.HorizonCode_Horizon_È((Object[])var6));
        }
    }
}
