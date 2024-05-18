package net.minecraft.command.server;

import net.minecraft.stats.*;
import net.minecraft.command.*;
import com.google.common.base.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.server.*;
import java.util.*;

public class CommandAchievement extends CommandBase
{
    private static final String[] I;
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandAchievement.I[" ".length()];
    }
    
    static {
        I();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == "  ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public String getCommandName() {
        return CommandAchievement.I["".length()];
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < "  ".length()) {
            throw new WrongUsageException(CommandAchievement.I["  ".length()], new Object["".length()]);
        }
        final StatBase oneShotStat = StatList.getOneShotStat(array[" ".length()]);
        if (oneShotStat == null && !array[" ".length()].equals(CommandAchievement.I["   ".length()])) {
            final String s = CommandAchievement.I[0x2B ^ 0x2F];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = array[" ".length()];
            throw new CommandException(s, array2);
        }
        EntityPlayerMP entityPlayerMP;
        if (array.length >= "   ".length()) {
            entityPlayerMP = CommandBase.getPlayer(commandSender, array["  ".length()]);
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            entityPlayerMP = CommandBase.getCommandSenderAsPlayer(commandSender);
        }
        final EntityPlayerMP entityPlayerMP2 = entityPlayerMP;
        final boolean equalsIgnoreCase = array["".length()].equalsIgnoreCase(CommandAchievement.I[0x80 ^ 0x85]);
        final boolean equalsIgnoreCase2 = array["".length()].equalsIgnoreCase(CommandAchievement.I[0x4A ^ 0x4C]);
        if (equalsIgnoreCase || equalsIgnoreCase2) {
            if (oneShotStat == null) {
                if (equalsIgnoreCase) {
                    final Iterator<Achievement> iterator = AchievementList.achievementList.iterator();
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        entityPlayerMP2.triggerAchievement(iterator.next());
                    }
                    final String s2 = CommandAchievement.I[0x8A ^ 0x8D];
                    final Object[] array3 = new Object[" ".length()];
                    array3["".length()] = entityPlayerMP2.getName();
                    CommandBase.notifyOperators(commandSender, this, s2, array3);
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                else if (equalsIgnoreCase2) {
                    final Iterator<Achievement> iterator2 = (Iterator<Achievement>)Lists.reverse((List)AchievementList.achievementList).iterator();
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                    while (iterator2.hasNext()) {
                        entityPlayerMP2.func_175145_a(iterator2.next());
                    }
                    final String s3 = CommandAchievement.I[0x75 ^ 0x7D];
                    final Object[] array4 = new Object[" ".length()];
                    array4["".length()] = entityPlayerMP2.getName();
                    CommandBase.notifyOperators(commandSender, this, s3, array4);
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
            }
            else {
                if (oneShotStat instanceof Achievement) {
                    Achievement parentAchievement = (Achievement)oneShotStat;
                    if (equalsIgnoreCase) {
                        if (entityPlayerMP2.getStatFile().hasAchievementUnlocked(parentAchievement)) {
                            final String s4 = CommandAchievement.I[0x4D ^ 0x44];
                            final Object[] array5 = new Object["  ".length()];
                            array5["".length()] = entityPlayerMP2.getName();
                            array5[" ".length()] = oneShotStat.func_150955_j();
                            throw new CommandException(s4, array5);
                        }
                        final ArrayList arrayList = Lists.newArrayList();
                        "".length();
                        if (2 < 0) {
                            throw null;
                        }
                        while (parentAchievement.parentAchievement != null && !entityPlayerMP2.getStatFile().hasAchievementUnlocked(parentAchievement.parentAchievement)) {
                            arrayList.add(parentAchievement.parentAchievement);
                            parentAchievement = parentAchievement.parentAchievement;
                        }
                        final Iterator iterator3 = Lists.reverse((List)arrayList).iterator();
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                        while (iterator3.hasNext()) {
                            entityPlayerMP2.triggerAchievement(iterator3.next());
                        }
                        "".length();
                        if (1 >= 4) {
                            throw null;
                        }
                    }
                    else if (equalsIgnoreCase2) {
                        if (!entityPlayerMP2.getStatFile().hasAchievementUnlocked(parentAchievement)) {
                            final String s5 = CommandAchievement.I[0xBB ^ 0xB1];
                            final Object[] array6 = new Object["  ".length()];
                            array6["".length()] = entityPlayerMP2.getName();
                            array6[" ".length()] = oneShotStat.func_150955_j();
                            throw new CommandException(s5, array6);
                        }
                        final ArrayList arrayList2 = Lists.newArrayList((Iterator)Iterators.filter((Iterator)AchievementList.achievementList.iterator(), (Predicate)new Predicate<Achievement>(this, entityPlayerMP2, oneShotStat) {
                            final CommandAchievement this$0;
                            private final EntityPlayerMP val$entityplayermp;
                            private final StatBase val$statbase;
                            
                            public boolean apply(final Object o) {
                                return this.apply((Achievement)o);
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
                                    if (4 <= 3) {
                                        throw null;
                                    }
                                }
                                return sb.toString();
                            }
                            
                            public boolean apply(final Achievement achievement) {
                                if (this.val$entityplayermp.getStatFile().hasAchievementUnlocked(achievement) && achievement != this.val$statbase) {
                                    return " ".length() != 0;
                                }
                                return "".length() != 0;
                            }
                        }));
                        final ArrayList arrayList3 = Lists.newArrayList((Iterable)arrayList2);
                        final Iterator<Achievement> iterator4 = (Iterator<Achievement>)arrayList2.iterator();
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                        while (iterator4.hasNext()) {
                            Achievement parentAchievement2;
                            final Achievement achievement = parentAchievement2 = iterator4.next();
                            int n = "".length();
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                            while (parentAchievement2 != null) {
                                if (parentAchievement2 == oneShotStat) {
                                    n = " ".length();
                                }
                                parentAchievement2 = parentAchievement2.parentAchievement;
                            }
                            if (n != 0) {
                                continue;
                            }
                            Achievement parentAchievement3 = achievement;
                            "".length();
                            if (4 < -1) {
                                throw null;
                            }
                            while (parentAchievement3 != null) {
                                arrayList3.remove(achievement);
                                parentAchievement3 = parentAchievement3.parentAchievement;
                            }
                        }
                        final Iterator<Achievement> iterator5 = (Iterator<Achievement>)arrayList3.iterator();
                        "".length();
                        if (4 == 0) {
                            throw null;
                        }
                        while (iterator5.hasNext()) {
                            entityPlayerMP2.func_175145_a(iterator5.next());
                        }
                    }
                }
                if (equalsIgnoreCase) {
                    entityPlayerMP2.triggerAchievement(oneShotStat);
                    final String s6 = CommandAchievement.I[0x6C ^ 0x67];
                    final Object[] array7 = new Object["  ".length()];
                    array7["".length()] = entityPlayerMP2.getName();
                    array7[" ".length()] = oneShotStat.func_150955_j();
                    CommandBase.notifyOperators(commandSender, this, s6, array7);
                    "".length();
                    if (-1 == 4) {
                        throw null;
                    }
                }
                else if (equalsIgnoreCase2) {
                    entityPlayerMP2.func_175145_a(oneShotStat);
                    final String s7 = CommandAchievement.I[0x94 ^ 0x98];
                    final Object[] array8 = new Object["  ".length()];
                    array8["".length()] = oneShotStat.func_150955_j();
                    array8[" ".length()] = entityPlayerMP2.getName();
                    CommandBase.notifyOperators(commandSender, this, s7, array8);
                }
            }
        }
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == " ".length()) {
            final String[] array2 = new String["  ".length()];
            array2["".length()] = CommandAchievement.I[0x92 ^ 0x9F];
            array2[" ".length()] = CommandAchievement.I[0xB8 ^ 0xB6];
            return CommandBase.getListOfStringsMatchingLastWord(array, array2);
        }
        if (array.length != "  ".length()) {
            List<String> listOfStringsMatchingLastWord;
            if (array.length == "   ".length()) {
                listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                listOfStringsMatchingLastWord = null;
            }
            return listOfStringsMatchingLastWord;
        }
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<StatBase> iterator = StatList.allStats.iterator();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().statId);
        }
        return CommandBase.getListOfStringsMatchingLastWord(array, arrayList);
    }
    
    private static void I() {
        (I = new String[0x2 ^ 0xD])["".length()] = I("/\u0006-\u001378\u0000(\u001f<:", "NeEzR");
        CommandAchievement.I[" ".length()] = I("\u00148\u0018\f\u0012\u00193\u0006O\u0012\u0014?\u001c\u0004\u0005\u0012:\u0010\u000f\u0007Y\"\u0006\u0000\u0014\u0012", "wWuas");
        CommandAchievement.I["  ".length()] = I("\r\b8\u001a\u0005\u0000\u0003&Y\u0005\r\u000f<\u0012\u0012\u000b\n0\u0019\u0010@\u0012&\u0016\u0003\u000b", "ngUwd");
        CommandAchievement.I["   ".length()] = I("y", "SnOJQ");
        CommandAchievement.I[0x35 ^ 0x31] = I(" \u0007*\u0005+-\f4F+ \u0000.\r<&\u0005\"\u0006>m\u001d)\u0003$,\u001f)))+\u0001\"\u001e/.\r)\u001c", "ChGhJ");
        CommandAchievement.I[0x94 ^ 0x91] = I("!\u001f\u0015\u000f", "FvcjT");
        CommandAchievement.I[0xC3 ^ 0xC5] = I("\u0001\t\u001a\u0010", "uhquC");
        CommandAchievement.I[0x47 ^ 0x40] = I("\u0017\u000585\u000b\u001a\u000e&v\u000b\u0017\u0002<=\u001c\u0011\u000706\u001eZ\r<.\u000fZ\u0019 ;\t\u0011\u0019&v\u000b\u0018\u0006", "tjUXj");
        CommandAchievement.I[0x30 ^ 0x38] = I(";\u000b\u0017=\t6\u0000\t~\t;\f\u00135\u001e=\t\u001f>\u001cv\u0010\u001b;\rv\u0017\u000f3\u000b=\u0017\t~\t4\b", "XdzPh");
        CommandAchievement.I[0x58 ^ 0x51] = I("9%\u0019%\u00154.\u0007f\u00159\"\u001d-\u0002?'\u0011&\u0000t+\u0018:\u0011;.\r\u0000\u0015,/", "ZJtHt");
        CommandAchievement.I[0x9B ^ 0x91] = I("!&\u0007,\u0018,-\u0019o\u0018!!\u0003$\u000f'$\u000f/\rl-\u0005/\r\n(\u001c$", "BIjAy");
        CommandAchievement.I[0x1B ^ 0x10] = I("%9\u001a<$(2\u0004\u007f$%>\u001e43#;\u0012?1h1\u001e' h%\u00022&#%\u0004\u007f*(3", "FVwQE");
        CommandAchievement.I[0x63 ^ 0x6F] = I("(\u001f\u001f\u0001+%\u0014\u0001B+(\u0018\u001b\t<.\u001d\u0017\u0002>e\u0004\u0013\u0007/e\u0003\u0007\u000f).\u0003\u0001B%%\u0015", "KprlJ");
        CommandAchievement.I[0xB3 ^ 0xBE] = I("1\u0011?<", "VxIYL");
        CommandAchievement.I[0x7 ^ 0x9] = I("\u001f\u0003/3", "kbDVA");
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
