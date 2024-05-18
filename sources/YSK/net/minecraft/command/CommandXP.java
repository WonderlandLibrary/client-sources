package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandXP extends CommandBase
{
    private static final String[] I;
    
    @Override
    public String getCommandName() {
        return CommandXP.I["".length()];
    }
    
    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandXP.I[" ".length()];
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0) {
            throw new WrongUsageException(CommandXP.I["  ".length()], new Object["".length()]);
        }
        String substring = array["".length()];
        int n;
        if (!substring.endsWith(CommandXP.I["   ".length()]) && !substring.endsWith(CommandXP.I[0x49 ^ 0x4D])) {
            n = "".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final int n2 = n;
        if (n2 != 0 && substring.length() > " ".length()) {
            substring = substring.substring("".length(), substring.length() - " ".length());
        }
        int int1 = CommandBase.parseInt(substring);
        int n3;
        if (int1 < 0) {
            n3 = " ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        final int n4 = n3;
        if (n4 != 0) {
            int1 *= -" ".length();
        }
        EntityPlayerMP entityPlayerMP;
        if (array.length > " ".length()) {
            entityPlayerMP = CommandBase.getPlayer(commandSender, array[" ".length()]);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            entityPlayerMP = CommandBase.getCommandSenderAsPlayer(commandSender);
        }
        final EntityPlayerMP entityPlayerMP2 = entityPlayerMP;
        if (n2 != 0) {
            commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, entityPlayerMP2.experienceLevel);
            if (n4 != 0) {
                entityPlayerMP2.addExperienceLevel(-int1);
                final String s = CommandXP.I[0x27 ^ 0x22];
                final Object[] array2 = new Object["  ".length()];
                array2["".length()] = int1;
                array2[" ".length()] = entityPlayerMP2.getName();
                CommandBase.notifyOperators(commandSender, this, s, array2);
                "".length();
                if (4 == -1) {
                    throw null;
                }
            }
            else {
                entityPlayerMP2.addExperienceLevel(int1);
                final String s2 = CommandXP.I[0x1A ^ 0x1C];
                final Object[] array3 = new Object["  ".length()];
                array3["".length()] = int1;
                array3[" ".length()] = entityPlayerMP2.getName();
                CommandBase.notifyOperators(commandSender, this, s2, array3);
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
        }
        else {
            commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, entityPlayerMP2.experienceTotal);
            if (n4 != 0) {
                throw new CommandException(CommandXP.I[0x86 ^ 0x81], new Object["".length()]);
            }
            entityPlayerMP2.addExperience(int1);
            final String s3 = CommandXP.I[0xB ^ 0x3];
            final Object[] array4 = new Object["  ".length()];
            array4["".length()] = int1;
            array4[" ".length()] = entityPlayerMP2.getName();
            CommandBase.notifyOperators(commandSender, this, s3, array4);
        }
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length == "  ".length()) {
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, this.getAllUsernames());
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x3C ^ 0x35])["".length()] = I("\u0012(", "jXsTm");
        CommandXP.I[" ".length()] = I("7?\n/\u0002:4\u0014l\u001b$~\u00121\u000235", "TPgBc");
        CommandXP.I["  ".length()] = I("\u0006,*\u00033\u000b'4@*\u0015m2\u001d3\u0002&", "eCGnR");
        CommandXP.I["   ".length()] = I("\u0018", "tWSMo");
        CommandXP.I[0x2D ^ 0x29] = I(":", "vRFKj");
        CommandXP.I[0xA3 ^ 0xA6] = I("$\n\u001c\u001f0)\u0001\u0002\\)7K\u0002\u00072$\u0000\u0002\u0001\u007f)\u0000\u0016\u0013%.\u0013\u0014\\=\"\u0013\u0014\u001e\"", "GeqrQ");
        CommandXP.I[0x91 ^ 0x97] = I("4\u0006\u0000>*9\r\u001e}3'G\u001e&(4\f\u001e e;\f\u001b6'$", "WimSK");
        CommandXP.I[0xC5 ^ 0xC2] = I("*\u001a88\u0002'\u0011&{\u001b9[34\n%\u0000'0M>\u001c1!\u000b-\u00074\";9", "IuUUc");
        CommandXP.I[0x2D ^ 0x25] = I("7-\u0002\u000b\u000e:&\u001cH\u0017$l\u001c\u0013\f7'\u001c\u0015", "TBofo");
    }
    
    static {
        I();
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
