package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class CommandMessageRaw extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000667";
    
    @Override
    public String Ý() {
        return "tellraw";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.tellraw.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.HorizonCode_Horizon_È(sender, args[0]);
        final String var4 = CommandBase.HorizonCode_Horizon_È(args, 1);
        try {
            final IChatComponent var5 = IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var4);
            var3.HorizonCode_Horizon_È(ChatComponentProcessor.HorizonCode_Horizon_È(sender, var5, var3));
        }
        catch (JsonParseException var7) {
            final Throwable var6 = ExceptionUtils.getRootCause((Throwable)var7);
            throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] { (var6 == null) ? "" : var6.getMessage() });
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá()) : null;
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 0;
    }
}
