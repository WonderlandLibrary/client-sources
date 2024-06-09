package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class CommandWeather extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001185";
    
    @Override
    public String Ý() {
        return "weather";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.weather.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length >= 1 && args.length <= 2) {
            int var3 = (300 + new Random().nextInt(600)) * 20;
            if (args.length >= 2) {
                var3 = CommandBase.HorizonCode_Horizon_È(args[1], 1, 1000000) * 20;
            }
            final WorldServer var4 = MinecraftServer.áƒ().Ý[0];
            final WorldInfo var5 = var4.ŒÏ();
            if ("clear".equalsIgnoreCase(args[0])) {
                var5.à(var3);
                var5.Ó(0);
                var5.Âµá€(0);
                var5.Â(false);
                var5.HorizonCode_Horizon_È(false);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.weather.clear", new Object[0]);
            }
            else if ("rain".equalsIgnoreCase(args[0])) {
                var5.à(0);
                var5.Ó(var3);
                var5.Âµá€(var3);
                var5.Â(true);
                var5.HorizonCode_Horizon_È(false);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.weather.rain", new Object[0]);
            }
            else {
                if (!"thunder".equalsIgnoreCase(args[0])) {
                    throw new WrongUsageException("commands.weather.usage", new Object[0]);
                }
                var5.à(0);
                var5.Ó(var3);
                var5.Âµá€(var3);
                var5.Â(true);
                var5.HorizonCode_Horizon_È(true);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.weather.thunder", new Object[0]);
            }
            return;
        }
        throw new WrongUsageException("commands.weather.usage", new Object[0]);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, "clear", "rain", "thunder") : null;
    }
}
