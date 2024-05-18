package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.world.storage.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandWeather extends CommandBase
{
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public String getCommandName() {
        return CommandWeather.I["".length()];
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length >= " ".length() && array.length <= "  ".length()) {
            int thunderTime = (265 + 68 - 175 + 142 + new Random().nextInt(361 + 479 - 602 + 362)) * (0x1B ^ 0xF);
            if (array.length >= "  ".length()) {
                thunderTime = CommandBase.parseInt(array[" ".length()], " ".length(), 458151 + 336051 - 5568 + 211366) * (0x40 ^ 0x54);
            }
            final WorldInfo worldInfo = MinecraftServer.getServer().worldServers["".length()].getWorldInfo();
            if (CommandWeather.I["  ".length()].equalsIgnoreCase(array["".length()])) {
                worldInfo.setCleanWeatherTime(thunderTime);
                worldInfo.setRainTime("".length());
                worldInfo.setThunderTime("".length());
                worldInfo.setRaining("".length() != 0);
                worldInfo.setThundering("".length() != 0);
                CommandBase.notifyOperators(commandSender, this, CommandWeather.I["   ".length()], new Object["".length()]);
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            else if (CommandWeather.I[0x1E ^ 0x1A].equalsIgnoreCase(array["".length()])) {
                worldInfo.setCleanWeatherTime("".length());
                worldInfo.setRainTime(thunderTime);
                worldInfo.setThunderTime(thunderTime);
                worldInfo.setRaining(" ".length() != 0);
                worldInfo.setThundering("".length() != 0);
                CommandBase.notifyOperators(commandSender, this, CommandWeather.I[0x95 ^ 0x90], new Object["".length()]);
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            else {
                if (!CommandWeather.I[0x8 ^ 0xE].equalsIgnoreCase(array["".length()])) {
                    throw new WrongUsageException(CommandWeather.I[0x42 ^ 0x45], new Object["".length()]);
                }
                worldInfo.setCleanWeatherTime("".length());
                worldInfo.setRainTime(thunderTime);
                worldInfo.setThunderTime(thunderTime);
                worldInfo.setRaining(" ".length() != 0);
                worldInfo.setThundering(" ".length() != 0);
                CommandBase.notifyOperators(commandSender, this, CommandWeather.I[0xA1 ^ 0xA9], new Object["".length()]);
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
            return;
        }
        throw new WrongUsageException(CommandWeather.I[0x7C ^ 0x75], new Object["".length()]);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandWeather.I[" ".length()];
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length == " ".length()) {
            final String[] array2 = new String["   ".length()];
            array2["".length()] = CommandWeather.I[0x8 ^ 0x2];
            array2[" ".length()] = CommandWeather.I[0x8E ^ 0x85];
            array2["  ".length()] = CommandWeather.I[0x52 ^ 0x5E];
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
    }
    
    private static void I() {
        (I = new String[0x7F ^ 0x72])["".length()] = I("\u001b4;:\u000b\t#", "lQZNc");
        CommandWeather.I[" ".length()] = I("'\u0000\u0015 %*\u000b\u000bc3!\u000e\f%!6A\r>%#\n", "DoxMD");
        CommandWeather.I["  ".length()] = I(",*!&\u0016", "OFDGd");
        CommandWeather.I["   ".length()] = I("3\r\u001e\u0000\u0003>\u0006\u0000C\u00155\u0003\u0007\u0005\u0007\"L\u0010\u0001\u00071\u0010", "Pbsmb");
        CommandWeather.I[0x2 ^ 0x6] = I("!7>(", "SVWFI");
        CommandWeather.I[0x33 ^ 0x36] = I("\u0010\t\u001b\u0017\u0003\u001d\u0002\u0005T\u0015\u0016\u0007\u0002\u0012\u0007\u0001H\u0004\u001b\u000b\u001d", "sfvzb");
        CommandWeather.I[0xBC ^ 0xBA] = I("&>9\u001f\u001d7$", "RVLqy");
        CommandWeather.I[0x7B ^ 0x7C] = I("\u0014\b !'\u0019\u0003>b1\u0012\u00069$#\u0005I8?'\u0010\u0002", "wgMLF");
        CommandWeather.I[0x8E ^ 0x86] = I("!\u0016\u0019\t\u001b,\u001d\u0007J\r'\u0018\u0000\f\u001f0W\u0000\f\u000f,\u001d\u0011\u0016", "Bytdz");
        CommandWeather.I[0xCF ^ 0xC6] = I("\u0011\u0003\b\u0003\r\u001c\b\u0016@\u001b\u0017\r\u0011\u0006\t\u0000B\u0010\u001d\r\u0015\t", "rlenl");
        CommandWeather.I[0x17 ^ 0x1D] = I("\u0010\u0004?3\b", "shZRz");
        CommandWeather.I[0x3 ^ 0x8] = I("7\u0010\u0001=", "EqhSg");
        CommandWeather.I[0x41 ^ 0x4D] = I("\u0007\u0007,\u001c\u0001\u0016\u001d", "soYre");
    }
}
