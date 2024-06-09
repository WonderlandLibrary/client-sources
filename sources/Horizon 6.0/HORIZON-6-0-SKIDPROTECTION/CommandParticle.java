package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandParticle extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00002341";
    
    @Override
    public String Ý() {
        return "particle";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.particle.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 8) {
            throw new WrongUsageException("commands.particle.usage", new Object[0]);
        }
        boolean var3 = false;
        EnumParticleTypes var4 = null;
        for (final EnumParticleTypes var8 : EnumParticleTypes.values()) {
            if (var8.Ó()) {
                if (args[0].startsWith(var8.Â())) {
                    var3 = true;
                    var4 = var8;
                    break;
                }
            }
            else if (args[0].equals(var8.Â())) {
                var3 = true;
                var4 = var8;
                break;
            }
        }
        if (!var3) {
            throw new CommandException("commands.particle.notFound", new Object[] { args[0] });
        }
        final String var9 = args[0];
        final Vec3 var10 = sender.z_();
        final double var11 = (float)CommandBase.Â(var10.HorizonCode_Horizon_È, args[1], true);
        final double var12 = (float)CommandBase.Â(var10.Â, args[2], true);
        final double var13 = (float)CommandBase.Â(var10.Ý, args[3], true);
        final double var14 = (float)CommandBase.Ý(args[4]);
        final double var15 = (float)CommandBase.Ý(args[5]);
        final double var16 = (float)CommandBase.Ý(args[6]);
        final double var17 = (float)CommandBase.Ý(args[7]);
        int var18 = 0;
        if (args.length > 8) {
            var18 = CommandBase.HorizonCode_Horizon_È(args[8], 0);
        }
        boolean var19 = false;
        if (args.length > 9 && "force".equals(args[9])) {
            var19 = true;
        }
        final World var20 = sender.k_();
        if (var20 instanceof WorldServer) {
            final WorldServer var21 = (WorldServer)var20;
            final int[] var22 = new int[var4.Ø­áŒŠá()];
            if (var4.Ó()) {
                final String[] var23 = args[0].split("_", 3);
                for (int var24 = 1; var24 < var23.length; ++var24) {
                    try {
                        var22[var24 - 1] = Integer.parseInt(var23[var24]);
                    }
                    catch (NumberFormatException var25) {
                        throw new CommandException("commands.particle.notFound", new Object[] { args[0] });
                    }
                }
            }
            var21.HorizonCode_Horizon_È(var4, var19, var11, var12, var13, var18, var14, var15, var16, var17, var22);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.particle.success", var9, Math.max(var18, 1));
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, EnumParticleTypes.HorizonCode_Horizon_È()) : ((args.length > 1 && args.length <= 4) ? CommandBase.HorizonCode_Horizon_È(args, 1, pos) : ((args.length == 9) ? CommandBase.HorizonCode_Horizon_È(args, "normal", "force") : null));
    }
}
