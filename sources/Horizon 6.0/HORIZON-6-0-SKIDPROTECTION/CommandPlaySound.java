package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandPlaySound extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000774";
    
    @Override
    public String Ý() {
        return "playsound";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.playsound.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException(this.Ý(sender), new Object[0]);
        }
        final byte var3 = 0;
        int var4 = var3 + 1;
        final String var5 = args[var3];
        final EntityPlayerMP var6 = CommandBase.HorizonCode_Horizon_È(sender, args[var4++]);
        final Vec3 var7 = sender.z_();
        double var8 = var7.HorizonCode_Horizon_È;
        if (args.length > var4) {
            var8 = CommandBase.Â(var8, args[var4++], true);
        }
        double var9 = var7.Â;
        if (args.length > var4) {
            var9 = CommandBase.Â(var9, args[var4++], 0, 0, false);
        }
        double var10 = var7.Ý;
        if (args.length > var4) {
            var10 = CommandBase.Â(var10, args[var4++], true);
        }
        double var11 = 1.0;
        if (args.length > var4) {
            var11 = CommandBase.HorizonCode_Horizon_È(args[var4++], 0.0, 3.4028234663852886E38);
        }
        double var12 = 1.0;
        if (args.length > var4) {
            var12 = CommandBase.HorizonCode_Horizon_È(args[var4++], 0.0, 2.0);
        }
        double var13 = 0.0;
        if (args.length > var4) {
            var13 = CommandBase.HorizonCode_Horizon_È(args[var4], 0.0, 1.0);
        }
        final double var14 = (var11 > 1.0) ? (var11 * 16.0) : 16.0;
        final double var15 = var6.Ó(var8, var9, var10);
        if (var15 > var14) {
            if (var13 <= 0.0) {
                throw new CommandException("commands.playsound.playerTooFar", new Object[] { var6.v_() });
            }
            final double var16 = var8 - var6.ŒÏ;
            final double var17 = var9 - var6.Çªà¢;
            final double var18 = var10 - var6.Ê;
            final double var19 = Math.sqrt(var16 * var16 + var17 * var17 + var18 * var18);
            if (var19 > 0.0) {
                var8 = var6.ŒÏ + var16 / var19 * 2.0;
                var9 = var6.Çªà¢ + var17 / var19 * 2.0;
                var10 = var6.Ê + var18 / var19 * 2.0;
            }
            var11 = var13;
        }
        var6.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S29PacketSoundEffect(var5, var8, var9, var10, (float)var11, (float)var12));
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.playsound.success", var5, var6.v_());
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 2) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá()) : ((args.length > 2 && args.length <= 5) ? CommandBase.HorizonCode_Horizon_È(args, 2, pos) : null);
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 1;
    }
}
