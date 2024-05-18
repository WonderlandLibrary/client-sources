package net.minecraft.command;

import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandParticle extends CommandBase
{
    private static final String[] I;
    
    @Override
    public String getCommandName() {
        return CommandParticle.I["".length()];
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandParticle.I[" ".length()];
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    private static void I() {
        (I = new String[0x73 ^ 0x79])["".length()] = I("135%:\">\"", "ARGQS");
        CommandParticle.I[" ".length()] = I("\u0001\u0019*\u0019\u0002\f\u00124Z\u0013\u0003\u00043\u001d\u0000\u000e\u0013i\u0001\u0010\u0003\u0011\"", "bvGtc");
        CommandParticle.I["  ".length()] = I("\u000e$\u0002\u000e\u001b\u0003/\u001cM\n\f9\u001b\n\u0019\u0001.A\u0016\t\f,\n", "mKocz");
        CommandParticle.I["   ".length()] = I("\u0007\u000b\n\u0004\u0005\n\u0000\u0014G\u0014\u0005\u0016\u0013\u0000\u0007\b\u0001I\u0007\u000b\u0010\"\b\u001c\n\u0000", "ddgid");
        CommandParticle.I[0xB3 ^ 0xB7] = I(">(\u0015\f\u001f", "XGgoz");
        CommandParticle.I[0x74 ^ 0x71] = I("5", "jeukE");
        CommandParticle.I[0xA0 ^ 0xA6] = I("-8;\u000b\u0011 3%H\u0000/%\"\u000f\u0013\"2x\b\u001f:\u00119\u0013\u001e*", "NWVfp");
        CommandParticle.I[0x7C ^ 0x7B] = I("'9\u001e\u001a\u0013*2\u0000Y\u0002%$\u0007\u001e\u0011(3]\u0004\u0007'5\u0016\u0004\u0001", "DVswr");
        CommandParticle.I[0x77 ^ 0x7F] = I("=>\u0013'\u0017?", "SQaJv");
        CommandParticle.I[0x10 ^ 0x19] = I("\u0002 \u00112<", "dOcQY");
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
            if (3 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < (0x89 ^ 0x81)) {
            throw new WrongUsageException(CommandParticle.I["  ".length()], new Object["".length()]);
        }
        int n = "".length();
        EnumParticleTypes enumParticleTypes = null;
        final EnumParticleTypes[] values;
        final int length = (values = EnumParticleTypes.values()).length;
        int i = "".length();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (i < length) {
            final EnumParticleTypes enumParticleTypes2 = values[i];
            if (enumParticleTypes2.hasArguments()) {
                if (array["".length()].startsWith(enumParticleTypes2.getParticleName())) {
                    n = " ".length();
                    enumParticleTypes = enumParticleTypes2;
                    "".length();
                    if (2 == 4) {
                        throw null;
                    }
                    break;
                }
            }
            else if (array["".length()].equals(enumParticleTypes2.getParticleName())) {
                n = " ".length();
                enumParticleTypes = enumParticleTypes2;
                "".length();
                if (3 != 3) {
                    throw null;
                }
                break;
            }
            ++i;
        }
        if (n == 0) {
            final String s = CommandParticle.I["   ".length()];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = array["".length()];
            throw new CommandException(s, array2);
        }
        final String s2 = array["".length()];
        final Vec3 positionVector = commandSender.getPositionVector();
        final double n2 = (float)CommandBase.parseDouble(positionVector.xCoord, array[" ".length()], " ".length() != 0);
        final double n3 = (float)CommandBase.parseDouble(positionVector.yCoord, array["  ".length()], " ".length() != 0);
        final double n4 = (float)CommandBase.parseDouble(positionVector.zCoord, array["   ".length()], " ".length() != 0);
        final double n5 = (float)CommandBase.parseDouble(array[0x77 ^ 0x73]);
        final double n6 = (float)CommandBase.parseDouble(array[0x74 ^ 0x71]);
        final double n7 = (float)CommandBase.parseDouble(array[0x71 ^ 0x77]);
        final double n8 = (float)CommandBase.parseDouble(array[0x23 ^ 0x24]);
        int n9 = "".length();
        if (array.length > (0xB3 ^ 0xBB)) {
            n9 = CommandBase.parseInt(array[0x49 ^ 0x41], "".length());
        }
        int n10 = "".length();
        if (array.length > (0x91 ^ 0x98) && CommandParticle.I[0x4A ^ 0x4E].equals(array[0x82 ^ 0x8B])) {
            n10 = " ".length();
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (entityWorld instanceof WorldServer) {
            final WorldServer worldServer = (WorldServer)entityWorld;
            final int[] array3 = new int[enumParticleTypes.getArgumentCount()];
            if (enumParticleTypes.hasArguments()) {
                final String[] split = array["".length()].split(CommandParticle.I[0xC4 ^ 0xC1], "   ".length());
                int j = " ".length();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                while (j < split.length) {
                    try {
                        array3[j - " ".length()] = Integer.parseInt(split[j]);
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                    catch (NumberFormatException ex) {
                        final String s3 = CommandParticle.I[0x62 ^ 0x64];
                        final Object[] array4 = new Object[" ".length()];
                        array4["".length()] = array["".length()];
                        throw new CommandException(s3, array4);
                    }
                    ++j;
                }
            }
            worldServer.spawnParticle(enumParticleTypes, n10 != 0, n2, n3, n4, n9, n5, n6, n7, n8, array3);
            final String s4 = CommandParticle.I[0x3 ^ 0x4];
            final Object[] array5 = new Object["  ".length()];
            array5["".length()] = s2;
            array5[" ".length()] = Math.max(n9, " ".length());
            CommandBase.notifyOperators(commandSender, this, s4, array5);
        }
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, EnumParticleTypes.getParticleNames());
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (array.length > " ".length() && array.length <= (0x9C ^ 0x98)) {
            list = CommandBase.func_175771_a(array, " ".length(), blockPos);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (array.length == (0x84 ^ 0x8E)) {
            final String[] array2 = new String["  ".length()];
            array2["".length()] = CommandParticle.I[0x63 ^ 0x6B];
            array2[" ".length()] = CommandParticle.I[0x46 ^ 0x4F];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
    }
}
