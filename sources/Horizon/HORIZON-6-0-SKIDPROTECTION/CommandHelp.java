package HORIZON-6-0-SKIDPROTECTION;

import java.util.Set;
import java.util.Collections;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

public class CommandHelp extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000529";
    
    @Override
    public String Ý() {
        return "help";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 0;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.help.usage";
    }
    
    @Override
    public List Â() {
        return Arrays.asList("?");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        final List var3 = this.Ø­áŒŠá(sender);
        final boolean var4 = true;
        final int var5 = (var3.size() - 1) / 7;
        final boolean var6 = false;
        int var7;
        try {
            var7 = ((args.length == 0) ? 0 : (CommandBase.HorizonCode_Horizon_È(args[0], 1, var5 + 1) - 1));
        }
        catch (NumberInvalidException var10) {
            final Map var8 = this.Ø­áŒŠá();
            final ICommand var9 = var8.get(args[0]);
            if (var9 != null) {
                throw new WrongUsageException(var9.Ý(sender), new Object[0]);
            }
            if (MathHelper.HorizonCode_Horizon_È(args[0], -1) != -1) {
                throw var10;
            }
            throw new CommandNotFoundException();
        }
        final int var11 = Math.min((var7 + 1) * 7, var3.size());
        final ChatComponentTranslation var12 = new ChatComponentTranslation("commands.help.header", new Object[] { var7 + 1, var5 + 1 });
        var12.à().HorizonCode_Horizon_È(EnumChatFormatting.Ý);
        sender.HorizonCode_Horizon_È(var12);
        for (int var13 = var7 * 7; var13 < var11; ++var13) {
            final ICommand var14 = var3.get(var13);
            final ChatComponentTranslation var15 = new ChatComponentTranslation(var14.Ý(sender), new Object[0]);
            var15.à().HorizonCode_Horizon_È(new ClickEvent(ClickEvent.HorizonCode_Horizon_È.Âµá€, "/" + var14.Ý() + " "));
            sender.HorizonCode_Horizon_È(var15);
        }
        if (var7 == 0 && sender instanceof EntityPlayer) {
            final ChatComponentTranslation var16 = new ChatComponentTranslation("commands.help.footer", new Object[0]);
            var16.à().HorizonCode_Horizon_È(EnumChatFormatting.ÂµÈ);
            sender.HorizonCode_Horizon_È(var16);
        }
    }
    
    protected List Ø­áŒŠá(final ICommandSender p_71534_1_) {
        final List var2 = MinecraftServer.áƒ().Õ().HorizonCode_Horizon_È(p_71534_1_);
        Collections.sort((List<Comparable>)var2);
        return var2;
    }
    
    protected Map Ø­áŒŠá() {
        return MinecraftServer.áƒ().Õ().HorizonCode_Horizon_È();
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (args.length == 1) {
            final Set var4 = this.Ø­áŒŠá().keySet();
            return CommandBase.HorizonCode_Horizon_È(args, (String[])var4.toArray(new String[var4.size()]));
        }
        return null;
    }
}
