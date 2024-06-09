package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.mojang.authlib.GameProfile;
import java.util.Date;

public class CommandBanPlayer extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000165";
    
    @Override
    public String Ý() {
        return "ban";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.ban.usage";
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ICommandSender sender) {
        return MinecraftServer.áƒ().Œ().áŒŠÆ().HorizonCode_Horizon_È() && super.HorizonCode_Horizon_È(sender);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1 || args[0].length() <= 0) {
            throw new WrongUsageException("commands.ban.usage", new Object[0]);
        }
        final MinecraftServer var3 = MinecraftServer.áƒ();
        final GameProfile var4 = var3.áŒŠÏ().HorizonCode_Horizon_È(args[0]);
        if (var4 == null) {
            throw new CommandException("commands.ban.failed", new Object[] { args[0] });
        }
        String var5 = null;
        if (args.length >= 2) {
            var5 = CommandBase.HorizonCode_Horizon_È(sender, args, 1).Ø();
        }
        final UserListBansEntry var6 = new UserListBansEntry(var4, null, sender.v_(), null, var5);
        var3.Œ().áŒŠÆ().HorizonCode_Horizon_È(var6);
        final EntityPlayerMP var7 = var3.Œ().HorizonCode_Horizon_È(args[0]);
        if (var7 != null) {
            var7.HorizonCode_Horizon_È.HorizonCode_Horizon_È("You are banned from this server.");
        }
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.ban.success", args[0]);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length >= 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá()) : null;
    }
}
