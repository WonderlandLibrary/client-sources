package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandTitle extends CommandBase
{
    private static final Logger HorizonCode_Horizon_È;
    private static final String Â = "CL_00002338";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    @Override
    public String Ý() {
        return "title";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.title.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException("commands.title.usage", new Object[0]);
        }
        if (args.length < 3) {
            if ("title".equals(args[1]) || "subtitle".equals(args[1])) {
                throw new WrongUsageException("commands.title.usage.title", new Object[0]);
            }
            if ("times".equals(args[1])) {
                throw new WrongUsageException("commands.title.usage.times", new Object[0]);
            }
        }
        final EntityPlayerMP var3 = CommandBase.HorizonCode_Horizon_È(sender, args[0]);
        final S45PacketTitle.HorizonCode_Horizon_È var4 = S45PacketTitle.HorizonCode_Horizon_È.HorizonCode_Horizon_È(args[1]);
        if (var4 != S45PacketTitle.HorizonCode_Horizon_È.Ø­áŒŠá && var4 != S45PacketTitle.HorizonCode_Horizon_È.Âµá€) {
            if (var4 == S45PacketTitle.HorizonCode_Horizon_È.Ý) {
                if (args.length != 5) {
                    throw new WrongUsageException("commands.title.usage", new Object[0]);
                }
                final int var5 = CommandBase.HorizonCode_Horizon_È(args[2]);
                final int var6 = CommandBase.HorizonCode_Horizon_È(args[3]);
                final int var7 = CommandBase.HorizonCode_Horizon_È(args[4]);
                final S45PacketTitle var8 = new S45PacketTitle(var5, var6, var7);
                var3.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var8);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.title.success", new Object[0]);
            }
            else {
                if (args.length < 3) {
                    throw new WrongUsageException("commands.title.usage", new Object[0]);
                }
                final String var9 = CommandBase.HorizonCode_Horizon_È(args, 2);
                IChatComponent var10;
                try {
                    var10 = IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var9);
                }
                catch (JsonParseException var12) {
                    final Throwable var11 = ExceptionUtils.getRootCause((Throwable)var12);
                    throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] { (var11 == null) ? "" : var11.getMessage() });
                }
                final S45PacketTitle var13 = new S45PacketTitle(var4, ChatComponentProcessor.HorizonCode_Horizon_È(sender, var10, var3));
                var3.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var13);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.title.success", new Object[0]);
            }
        }
        else {
            if (args.length != 2) {
                throw new WrongUsageException("commands.title.usage", new Object[0]);
            }
            final S45PacketTitle var14 = new S45PacketTitle(var4, null);
            var3.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var14);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.title.success", new Object[0]);
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá()) : ((args.length == 2) ? CommandBase.HorizonCode_Horizon_È(args, S45PacketTitle.HorizonCode_Horizon_È.HorizonCode_Horizon_È()) : null);
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 0;
    }
}
