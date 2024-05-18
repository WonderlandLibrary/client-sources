package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandDifficulty extends CommandBase
{
    private static final String[] I;
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0) {
            throw new WrongUsageException(CommandDifficulty.I["  ".length()], new Object["".length()]);
        }
        final EnumDifficulty difficultyFromCommand = this.getDifficultyFromCommand(array["".length()]);
        MinecraftServer.getServer().setDifficultyForAllWorlds(difficultyFromCommand);
        final String s = CommandDifficulty.I["   ".length()];
        final Object[] array2 = new Object[" ".length()];
        array2["".length()] = new ChatComponentTranslation(difficultyFromCommand.getDifficultyResourceKey(), new Object["".length()]);
        CommandBase.notifyOperators(commandSender, this, s, array2);
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length == " ".length()) {
            final String[] array2 = new String[0x1C ^ 0x18];
            array2["".length()] = CommandDifficulty.I[0x7 ^ 0xB];
            array2[" ".length()] = CommandDifficulty.I[0x96 ^ 0x9B];
            array2["  ".length()] = CommandDifficulty.I[0x3A ^ 0x34];
            array2["   ".length()] = CommandDifficulty.I[0x8F ^ 0x80];
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
    
    @Override
    public String getCommandName() {
        return CommandDifficulty.I["".length()];
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
            if (1 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandDifficulty.I[" ".length()];
    }
    
    private static void I() {
        (I = new String[0xAE ^ 0xBE])["".length()] = I("7(\u0013#/04\u00191?", "SAuEF");
        CommandDifficulty.I[" ".length()] = I("2\u0000.\u0003\u0004?\u000b0@\u00018\t%\u0007\u0006$\u00037\u0017K$\u001c\"\t\u0000", "QoCne");
        CommandDifficulty.I["  ".length()] = I("'\u000e\u0019\u001d\u0010*\u0005\u0007^\u0015-\u0007\u0012\u0019\u00121\r\u0000\t_1\u0012\u0015\u0017\u0014", "Datpq");
        CommandDifficulty.I["   ".length()] = I("9\u0016\n.\u000b4\u001d\u0014m\u000e3\u001f\u0001*\t/\u0015\u0013:D)\f\u0004 \u000f)\n", "ZygCj");
        CommandDifficulty.I[0x50 ^ 0x54] = I("$',\t.27!", "TBMjK");
        CommandDifficulty.I[0x28 ^ 0x2D] = I("\u001e", "nsRJl");
        CommandDifficulty.I[0x52 ^ 0x54] = I("\u000e\u0006>\u001a", "kgMcF");
        CommandDifficulty.I[0x32 ^ 0x35] = I("\u0012", "wpAns");
        CommandDifficulty.I[0x9F ^ 0x97] = I("<(\u001e \u0006>", "RGlMg");
        CommandDifficulty.I[0x9E ^ 0x97] = I(";", "UfuTy");
        CommandDifficulty.I[0x2E ^ 0x24] = I("=(\u0003\u0000", "UIqdM");
        CommandDifficulty.I[0x1E ^ 0x15] = I("\u000e", "fELaS");
        CommandDifficulty.I[0xA0 ^ 0xAC] = I("4,997\"<4", "DIXZR");
        CommandDifficulty.I[0x9C ^ 0x91] = I("(\"\u00170", "MCdIR");
        CommandDifficulty.I[0x2F ^ 0x21] = I(",\u00196\u000b\u0000.", "BvDfa");
        CommandDifficulty.I[0x3 ^ 0xC] = I("\u0004.11", "lOCUl");
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    static {
        I();
    }
    
    protected EnumDifficulty getDifficultyFromCommand(final String s) throws CommandException, NumberInvalidException {
        EnumDifficulty enumDifficulty;
        if (!s.equalsIgnoreCase(CommandDifficulty.I[0x6D ^ 0x69]) && !s.equalsIgnoreCase(CommandDifficulty.I[0x8 ^ 0xD])) {
            if (!s.equalsIgnoreCase(CommandDifficulty.I[0x6 ^ 0x0]) && !s.equalsIgnoreCase(CommandDifficulty.I[0xA7 ^ 0xA0])) {
                if (!s.equalsIgnoreCase(CommandDifficulty.I[0xAA ^ 0xA2]) && !s.equalsIgnoreCase(CommandDifficulty.I[0xBC ^ 0xB5])) {
                    if (!s.equalsIgnoreCase(CommandDifficulty.I[0x72 ^ 0x78]) && !s.equalsIgnoreCase(CommandDifficulty.I[0xB0 ^ 0xBB])) {
                        enumDifficulty = EnumDifficulty.getDifficultyEnum(CommandBase.parseInt(s, "".length(), "   ".length()));
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else {
                        enumDifficulty = EnumDifficulty.HARD;
                        "".length();
                        if (-1 >= 2) {
                            throw null;
                        }
                    }
                }
                else {
                    enumDifficulty = EnumDifficulty.NORMAL;
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                }
            }
            else {
                enumDifficulty = EnumDifficulty.EASY;
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
        }
        else {
            enumDifficulty = EnumDifficulty.PEACEFUL;
        }
        return enumDifficulty;
    }
}
