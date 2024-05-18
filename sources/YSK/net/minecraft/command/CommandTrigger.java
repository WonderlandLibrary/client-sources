package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class CommandTrigger extends CommandBase
{
    private static final String[] I;
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < "   ".length()) {
            throw new WrongUsageException(CommandTrigger.I["  ".length()], new Object["".length()]);
        }
        EntityPlayerMP entityPlayerMP;
        if (commandSender instanceof EntityPlayerMP) {
            entityPlayerMP = (EntityPlayerMP)commandSender;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            final Entity commandSenderEntity = commandSender.getCommandSenderEntity();
            if (!(commandSenderEntity instanceof EntityPlayerMP)) {
                throw new CommandException(CommandTrigger.I["   ".length()], new Object["".length()]);
            }
            entityPlayerMP = (EntityPlayerMP)commandSenderEntity;
        }
        final Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension("".length()).getScoreboard();
        final ScoreObjective objective = scoreboard.getObjective(array["".length()]);
        if (objective == null || objective.getCriteria() != IScoreObjectiveCriteria.TRIGGER) {
            final String s = CommandTrigger.I[0xB5 ^ 0xBF];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = array["".length()];
            throw new CommandException(s, array2);
        }
        final int int1 = CommandBase.parseInt(array["  ".length()]);
        if (!scoreboard.entityHasObjective(entityPlayerMP.getName(), objective)) {
            final String s2 = CommandTrigger.I[0x8A ^ 0x8E];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = array["".length()];
            throw new CommandException(s2, array3);
        }
        final Score valueFromObjective = scoreboard.getValueFromObjective(entityPlayerMP.getName(), objective);
        if (valueFromObjective.isLocked()) {
            final String s3 = CommandTrigger.I[0xA2 ^ 0xA7];
            final Object[] array4 = new Object[" ".length()];
            array4["".length()] = array["".length()];
            throw new CommandException(s3, array4);
        }
        if (CommandTrigger.I[0x3D ^ 0x3B].equals(array[" ".length()])) {
            valueFromObjective.setScorePoints(int1);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            if (!CommandTrigger.I[0x7E ^ 0x79].equals(array[" ".length()])) {
                final String s4 = CommandTrigger.I[0x25 ^ 0x2D];
                final Object[] array5 = new Object[" ".length()];
                array5["".length()] = array[" ".length()];
                throw new CommandException(s4, array5);
            }
            valueFromObjective.increseScore(int1);
        }
        valueFromObjective.setLocked(" ".length() != 0);
        if (entityPlayerMP.theItemInWorldManager.isCreative()) {
            final String s5 = CommandTrigger.I[0x0 ^ 0x9];
            final Object[] array6 = new Object["   ".length()];
            array6["".length()] = array["".length()];
            array6[" ".length()] = array[" ".length()];
            array6["  ".length()] = array["  ".length()];
            CommandBase.notifyOperators(commandSender, this, s5, array6);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
    }
    
    private static void I() {
        (I = new String[0x49 ^ 0x44])["".length()] = I("\u001f\u0011>\u0003\t\u000e\u0011", "kcWdn");
        CommandTrigger.I[" ".length()] = I("\u001a\u0017\u0003/;\u0017\u001c\u001dl.\u000b\u0011\t%?\u000bV\u001b1;\u001e\u001d", "yxnBZ");
        CommandTrigger.I["  ".length()] = I("::8  71&c5+<2*$+{ > >0", "YUUMA");
        CommandTrigger.I["   ".length()] = I("\b\u0015%\u0006\u000f\u0005\u001e;E\u001a\u0019\u0013/\f\u000b\u0019T!\u0005\u0018\n\u0016!\u000f>\u0007\u001b1\u000e\u001c", "kzHkn");
        CommandTrigger.I[0xBC ^ 0xB8] = I("4\f$\u000e\u00109\u0007:M\u0005%\n.\u0004\u0014%M \r\u00076\u000f \u0007>5\t,\u0000\u0005>\u0015,", "WcIcq");
        CommandTrigger.I[0x9B ^ 0x9E] = I("\u0007\u0018\u001b/6\n\u0013\u0005l#\u0016\u001e\u0011%2\u0016Y\u0012+$\u0005\u0015\u001a'3", "dwvBW");
        CommandTrigger.I[0x3B ^ 0x3D] = I("\u00187\u0012", "kRfuU");
        CommandTrigger.I[0x99 ^ 0x9E] = I("'%7", "FASZd");
        CommandTrigger.I[0xCE ^ 0xC6] = I(" \u0002\u001b\u0003%-\t\u0005@01\u0004\u0011\t!1C\u001f\u00002\"\u0001\u001f\n\t,\t\u0013", "CmvnD");
        CommandTrigger.I[0x3E ^ 0x37] = I("\u000f\u00168 \u0012\u0002\u001d&c\u0007\u001e\u00102*\u0016\u001eW&8\u0010\u000f\u001c&>", "lyUMs");
        CommandTrigger.I[0xCB ^ 0xC1] = I("&6?\u0004\u0005+=!G\u0010705\u000e\u00017w;\u0007\u0012$5;\r+'37\n\u0010,/7", "EYRid");
        CommandTrigger.I[0x34 ^ 0x3F] = I(",\u000b0", "MoTBs");
        CommandTrigger.I[0x99 ^ 0x95] = I("\n)\u0016", "yLbft");
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length != " ".length()) {
            List<String> listOfStringsMatchingLastWord;
            if (array.length == "  ".length()) {
                final String[] array2 = new String["  ".length()];
                array2["".length()] = CommandTrigger.I[0x26 ^ 0x2D];
                array2[" ".length()] = CommandTrigger.I[0x14 ^ 0x18];
                listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, array2);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                listOfStringsMatchingLastWord = null;
            }
            return listOfStringsMatchingLastWord;
        }
        final Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension("".length()).getScoreboard();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<ScoreObjective> iterator = scoreboard.getScoreObjectives().iterator();
        "".length();
        if (true != true) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ScoreObjective scoreObjective = iterator.next();
            if (scoreObjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
                arrayList.add(scoreObjective.getName());
            }
        }
        return CommandBase.getListOfStringsMatchingLastWord(array, (String[])arrayList.toArray(new String[arrayList.size()]));
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandTrigger.I[" ".length()];
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
            if (4 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public String getCommandName() {
        return CommandTrigger.I["".length()];
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "".length();
    }
}
