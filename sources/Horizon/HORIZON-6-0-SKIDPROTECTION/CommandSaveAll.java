package HORIZON-6-0-SKIDPROTECTION;

public class CommandSaveAll extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000826";
    
    @Override
    public String Ý() {
        return "save-all";
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.save.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        final MinecraftServer var3 = MinecraftServer.áƒ();
        sender.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.save.start", new Object[0]));
        if (var3.Œ() != null) {
            var3.Œ().ÂµÈ();
        }
        try {
            for (int var4 = 0; var4 < var3.Ý.length; ++var4) {
                if (var3.Ý[var4] != null) {
                    final WorldServer var5 = var3.Ý[var4];
                    final boolean var6 = var5.ˆá;
                    var5.ˆá = false;
                    var5.HorizonCode_Horizon_È(true, null);
                    var5.ˆá = var6;
                }
            }
            if (args.length > 0 && "flush".equals(args[0])) {
                sender.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.save.flushStart", new Object[0]));
                for (int var4 = 0; var4 < var3.Ý.length; ++var4) {
                    if (var3.Ý[var4] != null) {
                        final WorldServer var5 = var3.Ý[var4];
                        final boolean var6 = var5.ˆá;
                        var5.ˆá = false;
                        var5.Ä();
                        var5.ˆá = var6;
                    }
                }
                sender.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
            }
        }
        catch (MinecraftException var7) {
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.save.failed", var7.getMessage());
            return;
        }
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.save.success", new Object[0]);
    }
}
