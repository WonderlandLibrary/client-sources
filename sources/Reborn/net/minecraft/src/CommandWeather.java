package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class CommandWeather extends CommandBase
{
    @Override
    public String getCommandName() {
        return "weather";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 1) {
            throw new WrongUsageException("commands.weather.usage", new Object[0]);
        }
        int var3 = (300 + new Random().nextInt(600)) * 20;
        if (par2ArrayOfStr.length >= 2) {
            var3 = CommandBase.parseIntBounded(par1ICommandSender, par2ArrayOfStr[1], 1, 1000000) * 20;
        }
        final WorldServer var4 = MinecraftServer.getServer().worldServers[0];
        final WorldInfo var5 = var4.getWorldInfo();
        var5.setRainTime(var3);
        var5.setThunderTime(var3);
        if ("clear".equalsIgnoreCase(par2ArrayOfStr[0])) {
            var5.setRaining(false);
            var5.setThundering(false);
            CommandBase.notifyAdmins(par1ICommandSender, "commands.weather.clear", new Object[0]);
        }
        else if ("rain".equalsIgnoreCase(par2ArrayOfStr[0])) {
            var5.setRaining(true);
            var5.setThundering(false);
            CommandBase.notifyAdmins(par1ICommandSender, "commands.weather.rain", new Object[0]);
        }
        else if ("thunder".equalsIgnoreCase(par2ArrayOfStr[0])) {
            var5.setRaining(true);
            var5.setThundering(true);
            CommandBase.notifyAdmins(par1ICommandSender, "commands.weather.thunder", new Object[0]);
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "clear", "rain", "thunder") : null;
    }
}
