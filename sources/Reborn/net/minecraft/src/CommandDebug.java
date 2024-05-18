package net.minecraft.src;

import net.minecraft.server.*;
import java.text.*;
import java.io.*;
import java.util.*;

public class CommandDebug extends CommandBase
{
    private long startTime;
    private int startTicks;
    
    public CommandDebug() {
        this.startTime = 0L;
        this.startTicks = 0;
    }
    
    @Override
    public String getCommandName() {
        return "debug";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) {
            if (par2ArrayOfStr[0].equals("start")) {
                CommandBase.notifyAdmins(par1ICommandSender, "commands.debug.start", new Object[0]);
                MinecraftServer.getServer().enableProfiling();
                this.startTime = System.currentTimeMillis();
                this.startTicks = MinecraftServer.getServer().getTickCounter();
                return;
            }
            if (par2ArrayOfStr[0].equals("stop")) {
                if (!MinecraftServer.getServer().theProfiler.profilingEnabled) {
                    throw new CommandException("commands.debug.notStarted", new Object[0]);
                }
                final long var3 = System.currentTimeMillis();
                final int var4 = MinecraftServer.getServer().getTickCounter();
                final long var5 = var3 - this.startTime;
                final int var6 = var4 - this.startTicks;
                this.saveProfilerResults(var5, var6);
                MinecraftServer.getServer().theProfiler.profilingEnabled = false;
                CommandBase.notifyAdmins(par1ICommandSender, "commands.debug.stop", var5 / 1000.0f, var6);
                return;
            }
        }
        throw new WrongUsageException("commands.debug.usage", new Object[0]);
    }
    
    private void saveProfilerResults(final long par1, final int par3) {
        final File var4 = new File(MinecraftServer.getServer().getFile("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
        var4.getParentFile().mkdirs();
        try {
            final FileWriter var5 = new FileWriter(var4);
            var5.write(this.getProfilerResults(par1, par3));
            var5.close();
        }
        catch (Throwable var6) {
            MinecraftServer.getServer().getLogAgent().logSevereException("Could not save profiler results to " + var4, var6);
        }
    }
    
    private String getProfilerResults(final long par1, final int par3) {
        final StringBuilder var4 = new StringBuilder();
        var4.append("---- Minecraft Profiler Results ----\n");
        var4.append("// ");
        var4.append(getWittyComment());
        var4.append("\n\n");
        var4.append("Time span: ").append(par1).append(" ms\n");
        var4.append("Tick span: ").append(par3).append(" ticks\n");
        var4.append("// This is approximately ").append(String.format("%.2f", par3 / (par1 / 1000.0f))).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
        var4.append("--- BEGIN PROFILE DUMP ---\n\n");
        this.getProfileDump(0, "root", var4);
        var4.append("--- END PROFILE DUMP ---\n\n");
        return var4.toString();
    }
    
    private void getProfileDump(final int par1, final String par2Str, final StringBuilder par3StringBuilder) {
        final List var4 = MinecraftServer.getServer().theProfiler.getProfilingData(par2Str);
        if (var4 != null && var4.size() >= 3) {
            for (int var5 = 1; var5 < var4.size(); ++var5) {
                final ProfilerResult var6 = var4.get(var5);
                par3StringBuilder.append(String.format("[%02d] ", par1));
                for (int var7 = 0; var7 < par1; ++var7) {
                    par3StringBuilder.append(" ");
                }
                par3StringBuilder.append(var6.field_76331_c);
                par3StringBuilder.append(" - ");
                par3StringBuilder.append(String.format("%.2f", var6.field_76332_a));
                par3StringBuilder.append("%/");
                par3StringBuilder.append(String.format("%.2f", var6.field_76330_b));
                par3StringBuilder.append("%\n");
                if (!var6.field_76331_c.equals("unspecified")) {
                    try {
                        this.getProfileDump(par1 + 1, String.valueOf(par2Str) + "." + var6.field_76331_c, par3StringBuilder);
                    }
                    catch (Exception var8) {
                        par3StringBuilder.append("[[ EXCEPTION " + var8 + " ]]");
                    }
                }
            }
        }
    }
    
    private static String getWittyComment() {
        final String[] var0 = { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
        try {
            return var0[(int)(System.nanoTime() % var0.length)];
        }
        catch (Throwable var2) {
            return "Witty comment unavailable :(";
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "start", "stop") : null;
    }
}
