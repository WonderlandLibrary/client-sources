package net.minecraft.command.server;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;
import net.minecraft.nbt.*;
import net.minecraft.command.*;
import net.minecraft.entity.*;

public class CommandTestFor extends CommandBase
{
    private static final String[] I;
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x8B ^ 0x8D])["".length()] = I("$\"?\r\u0007?5", "PGLya");
        CommandTestFor.I[" ".length()] = I("; /\u001556+1V =<6\u001e;*a7\u000b5?*", "XOBxT");
        CommandTestFor.I["  ".length()] = I("08:\u0004\f=3$G\u00196$#\u000f\u0002!y\"\u001a\f42", "SWWim");
        CommandTestFor.I["   ".length()] = I("\u0002%\u000e\u0019\u0000\u000f.\u0010Z\u0015\u00049\u0017\u0012\u000e\u0013d\u0017\u0015\u0006$8\u0011\u001b\u0013", "aJcta");
        CommandTestFor.I[0x17 ^ 0x13] = I("\r7%\u001d+\u0000<;^>\u000b+<\u0016%\u001cv.\u0011#\u0002-:\u0015", "nXHpJ");
        CommandTestFor.I[0xAA ^ 0xAF] = I("*\u001f/$\u0002'\u00141g\u0017,\u00036/\f;^1<\u0000*\u00151:", "IpBIc");
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length == " ".length()) {
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < " ".length()) {
            throw new WrongUsageException(CommandTestFor.I["  ".length()], new Object["".length()]);
        }
        final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array["".length()]);
        NBTBase tagFromJson = null;
        if (array.length >= "  ".length()) {
            try {
                tagFromJson = JsonToNBT.getTagFromJson(CommandBase.buildString(array, " ".length()));
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            catch (NBTException ex) {
                final String s = CommandTestFor.I["   ".length()];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = ex.getMessage();
                throw new CommandException(s, array2);
            }
        }
        if (tagFromJson != null) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            func_175768_b.writeToNBT(nbtTagCompound);
            if (!NBTUtil.func_181123_a(tagFromJson, nbtTagCompound, " ".length() != 0)) {
                final String s2 = CommandTestFor.I[0x88 ^ 0x8C];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = func_175768_b.getName();
                throw new CommandException(s2, array3);
            }
        }
        final String s3 = CommandTestFor.I[0xBB ^ 0xBE];
        final Object[] array4 = new Object[" ".length()];
        array4["".length()] = func_175768_b.getName();
        CommandBase.notifyOperators(commandSender, this, s3, array4);
    }
    
    static {
        I();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandTestFor.I[" ".length()];
    }
    
    @Override
    public String getCommandName() {
        return CommandTestFor.I["".length()];
    }
}
