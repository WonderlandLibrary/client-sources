package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.mojang.authlib.GameProfile;

public class CommandWhitelist extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001186";
    
    @Override
    public String Ý() {
        return "whitelist";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.whitelist.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
        }
        final MinecraftServer var3 = MinecraftServer.áƒ();
        if (args[0].equals("on")) {
            var3.Œ().HorizonCode_Horizon_È(true);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.whitelist.enabled", new Object[0]);
        }
        else if (args[0].equals("off")) {
            var3.Œ().HorizonCode_Horizon_È(false);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.whitelist.disabled", new Object[0]);
        }
        else if (args[0].equals("list")) {
            sender.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.whitelist.list", new Object[] { var3.Œ().ˆÏ­().length, var3.Œ().¥Æ().length }));
            final String[] var4 = var3.Œ().ˆÏ­();
            sender.HorizonCode_Horizon_È(new ChatComponentText(CommandBase.HorizonCode_Horizon_È((Object[])var4)));
        }
        else if (args[0].equals("add")) {
            if (args.length < 2) {
                throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
            }
            final GameProfile var5 = var3.áŒŠÏ().HorizonCode_Horizon_È(args[1]);
            if (var5 == null) {
                throw new CommandException("commands.whitelist.add.failed", new Object[] { args[1] });
            }
            var3.Œ().Ó(var5);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.whitelist.add.success", args[1]);
        }
        else if (args[0].equals("remove")) {
            if (args.length < 2) {
                throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
            }
            final GameProfile var5 = var3.Œ().á().HorizonCode_Horizon_È(args[1]);
            if (var5 == null) {
                throw new CommandException("commands.whitelist.remove.failed", new Object[] { args[1] });
            }
            var3.Œ().à(var5);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.whitelist.remove.success", args[1]);
        }
        else if (args[0].equals("reload")) {
            var3.Œ().£à();
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.whitelist.reloaded", new Object[0]);
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (args.length == 1) {
            return CommandBase.HorizonCode_Horizon_È(args, "on", "off", "list", "add", "remove", "reload");
        }
        if (args.length == 2) {
            if (args[0].equals("remove")) {
                return CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().Œ().ˆÏ­());
            }
            if (args[0].equals("add")) {
                return CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().áŒŠÏ().HorizonCode_Horizon_È());
            }
        }
        return null;
    }
}
