package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.io.FileWriter;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandDebug extends CommandBase
{
    private static final Logger HorizonCode_Horizon_È;
    private long Â;
    private int Ý;
    private static final String Ø­áŒŠá = "CL_00000270";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    @Override
    public String Ý() {
        return "debug";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.debug.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException("commands.debug.usage", new Object[0]);
        }
        if (args[0].equals("start")) {
            if (args.length != 1) {
                throw new WrongUsageException("commands.debug.usage", new Object[0]);
            }
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.debug.start", new Object[0]);
            MinecraftServer.áƒ().£Â();
            this.Â = MinecraftServer.Œà();
            this.Ý = MinecraftServer.áƒ().Ï­Ï­Ï();
        }
        else if (args[0].equals("stop")) {
            if (args.length != 1) {
                throw new WrongUsageException("commands.debug.usage", new Object[0]);
            }
            if (!MinecraftServer.áƒ().Â.HorizonCode_Horizon_È) {
                throw new CommandException("commands.debug.notStarted", new Object[0]);
            }
            final long var3 = MinecraftServer.Œà();
            final int var4 = MinecraftServer.áƒ().Ï­Ï­Ï();
            final long var5 = var3 - this.Â;
            final int var6 = var4 - this.Ý;
            this.HorizonCode_Horizon_È(var5, var6);
            MinecraftServer.áƒ().Â.HorizonCode_Horizon_È = false;
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.debug.stop", var5 / 1000.0f, var6);
        }
        else if (args[0].equals("chunk")) {
            if (args.length != 4) {
                throw new WrongUsageException("commands.debug.usage", new Object[0]);
            }
            CommandBase.HorizonCode_Horizon_È(sender, args, 1, true);
        }
    }
    
    private void HorizonCode_Horizon_È(final long p_147205_1_, final int p_147205_3_) {
        final File var4 = new File(MinecraftServer.áƒ().Ý("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
        var4.getParentFile().mkdirs();
        try {
            final FileWriter var5 = new FileWriter(var4);
            var5.write(this.Â(p_147205_1_, p_147205_3_));
            var5.close();
        }
        catch (Throwable var6) {
            CommandDebug.HorizonCode_Horizon_È.error("Could not save profiler results to " + var4, var6);
        }
    }
    
    private String Â(final long p_147204_1_, final int p_147204_3_) {
        final StringBuilder var4 = new StringBuilder();
        var4.append("---- Minecraft Profiler Results ----\n");
        var4.append("// ");
        var4.append(Ø­áŒŠá());
        var4.append("\n\n");
        var4.append("Time span: ").append(p_147204_1_).append(" ms\n");
        var4.append("Tick span: ").append(p_147204_3_).append(" ticks\n");
        var4.append("// This is approximately ").append(String.format("%.2f", p_147204_3_ / (p_147204_1_ / 1000.0f))).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
        var4.append("--- BEGIN PROFILE DUMP ---\n\n");
        this.HorizonCode_Horizon_È(0, "root", var4);
        var4.append("--- END PROFILE DUMP ---\n\n");
        return var4.toString();
    }
    
    private void HorizonCode_Horizon_È(final int p_147202_1_, final String p_147202_2_, final StringBuilder p_147202_3_) {
        final List var4 = MinecraftServer.áƒ().Â.Â(p_147202_2_);
        if (var4 != null && var4.size() >= 3) {
            for (int var5 = 1; var5 < var4.size(); ++var5) {
                final Profiler.HorizonCode_Horizon_È var6 = var4.get(var5);
                p_147202_3_.append(String.format("[%02d] ", p_147202_1_));
                for (int var7 = 0; var7 < p_147202_1_; ++var7) {
                    p_147202_3_.append(" ");
                }
                p_147202_3_.append(var6.Ý).append(" - ").append(String.format("%.2f", var6.HorizonCode_Horizon_È)).append("%/").append(String.format("%.2f", var6.Â)).append("%\n");
                if (!var6.Ý.equals("unspecified")) {
                    try {
                        this.HorizonCode_Horizon_È(p_147202_1_ + 1, String.valueOf(p_147202_2_) + "." + var6.Ý, p_147202_3_);
                    }
                    catch (Exception var8) {
                        p_147202_3_.append("[[ EXCEPTION ").append(var8).append(" ]]");
                    }
                }
            }
        }
    }
    
    private static String Ø­áŒŠá() {
        final String[] var0 = { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
        try {
            return var0[(int)(System.nanoTime() % var0.length)];
        }
        catch (Throwable var2) {
            return "Witty comment unavailable :(";
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, "start", "stop") : null;
    }
}
