package net.minecraft.command;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;

public class CommandSetSpawnpoint extends CommandBase
{
    private static final String[] I;
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (array.length > " ".length() && array.length <= (0xF ^ 0xB)) {
            list = CommandBase.func_175771_a(array, " ".length(), blockPos);
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length > " ".length() && array.length < (0x41 ^ 0x45)) {
            throw new WrongUsageException(CommandSetSpawnpoint.I["  ".length()], new Object["".length()]);
        }
        EntityPlayerMP entityPlayerMP;
        if (array.length > 0) {
            entityPlayerMP = CommandBase.getPlayer(commandSender, array["".length()]);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else {
            entityPlayerMP = CommandBase.getCommandSenderAsPlayer(commandSender);
        }
        final EntityPlayerMP entityPlayerMP2 = entityPlayerMP;
        BlockPos blockPos;
        if (array.length > "   ".length()) {
            blockPos = CommandBase.parseBlockPos(commandSender, array, " ".length(), " ".length() != 0);
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else {
            blockPos = entityPlayerMP2.getPosition();
        }
        final BlockPos blockPos2 = blockPos;
        if (entityPlayerMP2.worldObj != null) {
            entityPlayerMP2.setSpawnPoint(blockPos2, " ".length() != 0);
            final String s = CommandSetSpawnpoint.I["   ".length()];
            final Object[] array2 = new Object[0x25 ^ 0x21];
            array2["".length()] = entityPlayerMP2.getName();
            array2[" ".length()] = blockPos2.getX();
            array2["  ".length()] = blockPos2.getY();
            array2["   ".length()] = blockPos2.getZ();
            CommandBase.notifyOperators(commandSender, this, s, array2);
        }
    }
    
    @Override
    public String getCommandName() {
        return CommandSetSpawnpoint.I["".length()];
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
            if (-1 == 4) {
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
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x39 ^ 0x3D])["".length()] = I("\u0018\u0016&0\u0001\u001b\t.)\u001b", "kfGGo");
        CommandSetSpawnpoint.I[" ".length()] = I(" :\u0003>%-1\u001d}734\u0019=4,<\u0000'j6&\u000f4!", "CUnSD");
        CommandSetSpawnpoint.I["  ".length()] = I("\u0013&>\u001b\u0014\u001e- X\u0006\u0000($\u0018\u0005\u001f =\u0002[\u0005:2\u0011\u0010", "pISvu");
        CommandSetSpawnpoint.I["   ".length()] = I("\u0004\u000e\u0006.3\t\u0005\u0018m!\u0017\u0000\u001c-\"\b\b\u00057|\u0014\u0014\b 7\u0014\u0012", "gakCR");
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandSetSpawnpoint.I[" ".length()];
    }
}
