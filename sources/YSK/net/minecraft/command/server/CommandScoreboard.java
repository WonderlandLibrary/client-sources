package net.minecraft.command.server;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.command.*;
import net.minecraft.scoreboard.*;
import net.minecraft.server.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;

public class CommandScoreboard extends CommandBase
{
    private static final String[] I;
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (!this.func_175780_b(commandSender, array)) {
            if (array.length < " ".length()) {
                throw new WrongUsageException(CommandScoreboard.I["  ".length()], new Object["".length()]);
            }
            if (array["".length()].equalsIgnoreCase(CommandScoreboard.I["   ".length()])) {
                if (array.length == " ".length()) {
                    throw new WrongUsageException(CommandScoreboard.I[0x53 ^ 0x57], new Object["".length()]);
                }
                if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0xC ^ 0x9])) {
                    this.listObjectives(commandSender);
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x10 ^ 0x16])) {
                    if (array.length < (0xE ^ 0xA)) {
                        throw new WrongUsageException(CommandScoreboard.I[0x94 ^ 0x93], new Object["".length()]);
                    }
                    this.addObjective(commandSender, array, "  ".length());
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x5D ^ 0x55])) {
                    if (array.length != "   ".length()) {
                        throw new WrongUsageException(CommandScoreboard.I[0x2 ^ 0xB], new Object["".length()]);
                    }
                    this.removeObjective(commandSender, array["  ".length()]);
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                }
                else {
                    if (!array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x47 ^ 0x4D])) {
                        throw new WrongUsageException(CommandScoreboard.I[0xB2 ^ 0xB9], new Object["".length()]);
                    }
                    if (array.length != "   ".length() && array.length != (0x75 ^ 0x71)) {
                        throw new WrongUsageException(CommandScoreboard.I[0x54 ^ 0x58], new Object["".length()]);
                    }
                    this.setObjectiveDisplay(commandSender, array, "  ".length());
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
            }
            else if (array["".length()].equalsIgnoreCase(CommandScoreboard.I[0x6C ^ 0x61])) {
                if (array.length == " ".length()) {
                    throw new WrongUsageException(CommandScoreboard.I[0xB4 ^ 0xBA], new Object["".length()]);
                }
                if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x67 ^ 0x68])) {
                    if (array.length > "   ".length()) {
                        throw new WrongUsageException(CommandScoreboard.I[0x3C ^ 0x2C], new Object["".length()]);
                    }
                    this.listPlayers(commandSender, array, "  ".length());
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x11 ^ 0x0])) {
                    if (array.length < (0x99 ^ 0x9C)) {
                        throw new WrongUsageException(CommandScoreboard.I[0x99 ^ 0x8B], new Object["".length()]);
                    }
                    this.setPlayer(commandSender, array, "  ".length());
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x49 ^ 0x5A])) {
                    if (array.length < (0x83 ^ 0x86)) {
                        throw new WrongUsageException(CommandScoreboard.I[0x8 ^ 0x1C], new Object["".length()]);
                    }
                    this.setPlayer(commandSender, array, "  ".length());
                    "".length();
                    if (1 == -1) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x41 ^ 0x54])) {
                    if (array.length < (0x3A ^ 0x3F)) {
                        throw new WrongUsageException(CommandScoreboard.I[0x29 ^ 0x3F], new Object["".length()]);
                    }
                    this.setPlayer(commandSender, array, "  ".length());
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x7 ^ 0x10])) {
                    if (array.length != "   ".length() && array.length != (0x25 ^ 0x21)) {
                        throw new WrongUsageException(CommandScoreboard.I[0x1E ^ 0x6], new Object["".length()]);
                    }
                    this.resetPlayers(commandSender, array, "  ".length());
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x7A ^ 0x63])) {
                    if (array.length != (0x7E ^ 0x7A)) {
                        throw new WrongUsageException(CommandScoreboard.I[0x1E ^ 0x4], new Object["".length()]);
                    }
                    this.func_175779_n(commandSender, array, "  ".length());
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x8 ^ 0x13])) {
                    if (array.length != (0x95 ^ 0x90) && array.length != (0x97 ^ 0x91)) {
                        throw new WrongUsageException(CommandScoreboard.I[0x7F ^ 0x63], new Object["".length()]);
                    }
                    this.func_175781_o(commandSender, array, "  ".length());
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    if (!array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x50 ^ 0x4D])) {
                        throw new WrongUsageException(CommandScoreboard.I[0x87 ^ 0x99], new Object["".length()]);
                    }
                    if (array.length != (0x35 ^ 0x32)) {
                        throw new WrongUsageException(CommandScoreboard.I[0x37 ^ 0x28], new Object["".length()]);
                    }
                    this.func_175778_p(commandSender, array, "  ".length());
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
            }
            else {
                if (!array["".length()].equalsIgnoreCase(CommandScoreboard.I[0x6C ^ 0x4C])) {
                    throw new WrongUsageException(CommandScoreboard.I[0x1 ^ 0x20], new Object["".length()]);
                }
                if (array.length == " ".length()) {
                    throw new WrongUsageException(CommandScoreboard.I[0x36 ^ 0x14], new Object["".length()]);
                }
                if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x7C ^ 0x5F])) {
                    if (array.length > "   ".length()) {
                        throw new WrongUsageException(CommandScoreboard.I[0x8D ^ 0xA9], new Object["".length()]);
                    }
                    this.listTeams(commandSender, array, "  ".length());
                    "".length();
                    if (1 < 0) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x10 ^ 0x35])) {
                    if (array.length < "   ".length()) {
                        throw new WrongUsageException(CommandScoreboard.I[0x56 ^ 0x70], new Object["".length()]);
                    }
                    this.addTeam(commandSender, array, "  ".length());
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x20 ^ 0x7])) {
                    if (array.length != "   ".length()) {
                        throw new WrongUsageException(CommandScoreboard.I[0x2D ^ 0x5], new Object["".length()]);
                    }
                    this.removeTeam(commandSender, array, "  ".length());
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x43 ^ 0x6A])) {
                    if (array.length != "   ".length()) {
                        throw new WrongUsageException(CommandScoreboard.I[0xAD ^ 0x87], new Object["".length()]);
                    }
                    this.emptyTeam(commandSender, array, "  ".length());
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x6C ^ 0x47])) {
                    if (array.length < (0x56 ^ 0x52) && (array.length != "   ".length() || !(commandSender instanceof EntityPlayer))) {
                        throw new WrongUsageException(CommandScoreboard.I[0xB1 ^ 0x9D], new Object["".length()]);
                    }
                    this.joinTeam(commandSender, array, "  ".length());
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x28 ^ 0x5])) {
                    if (array.length < "   ".length() && !(commandSender instanceof EntityPlayer)) {
                        throw new WrongUsageException(CommandScoreboard.I[0xB9 ^ 0x97], new Object["".length()]);
                    }
                    this.leaveTeam(commandSender, array, "  ".length());
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else {
                    if (!array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[0x31 ^ 0x1E])) {
                        throw new WrongUsageException(CommandScoreboard.I[0x20 ^ 0x10], new Object["".length()]);
                    }
                    if (array.length != (0xC0 ^ 0xC4) && array.length != (0x8D ^ 0x88)) {
                        throw new WrongUsageException(CommandScoreboard.I[0x84 ^ 0xB5], new Object["".length()]);
                    }
                    this.setTeamOption(commandSender, array, "  ".length());
                }
            }
        }
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    protected ScoreObjective getObjective(final String s, final boolean b) throws CommandException {
        final ScoreObjective objective = this.getScoreboard().getObjective(s);
        if (objective == null) {
            final String s2 = CommandScoreboard.I[0x43 ^ 0x76];
            final Object[] array = new Object[" ".length()];
            array["".length()] = s;
            throw new CommandException(s2, array);
        }
        if (b && objective.getCriteria().isReadOnly()) {
            final String s3 = CommandScoreboard.I[0xA7 ^ 0x91];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = s;
            throw new CommandException(s3, array2);
        }
        return objective;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void resetPlayers(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String entityName = CommandBase.getEntityName(commandSender, array[n++]);
        if (array.length > n) {
            final ScoreObjective objective = this.getObjective(array[n++], "".length() != 0);
            scoreboard.removeObjectiveFromEntity(entityName, objective);
            final String s = CommandScoreboard.I[100 + 45 - 131 + 124];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = objective.getName();
            array2[" ".length()] = entityName;
            CommandBase.notifyOperators(commandSender, this, s, array2);
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            scoreboard.removeObjectiveFromEntity(entityName, null);
            final String s2 = CommandScoreboard.I[74 + 91 - 123 + 97];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = entityName;
            CommandBase.notifyOperators(commandSender, this, s2, array3);
        }
    }
    
    protected void setPlayer(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final String s = array[n - " ".length()];
        final int n2 = n;
        final String entityName = CommandBase.getEntityName(commandSender, array[n++]);
        if (entityName.length() > (0xC ^ 0x24)) {
            final String s2 = CommandScoreboard.I[20 + 84 + 6 + 21];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = entityName;
            array2[" ".length()] = (0x34 ^ 0x1C);
            throw new SyntaxErrorException(s2, array2);
        }
        final ScoreObjective objective = this.getObjective(array[n++], " ".length() != 0);
        int n3;
        if (s.equalsIgnoreCase(CommandScoreboard.I[33 + 1 + 56 + 42])) {
            n3 = CommandBase.parseInt(array[n++]);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n3 = CommandBase.parseInt(array[n++], "".length());
        }
        final int scorePoints = n3;
        if (array.length > n) {
            final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[n2]);
            try {
                final NBTTagCompound tagFromJson = JsonToNBT.getTagFromJson(CommandBase.buildString(array, n));
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                func_175768_b.writeToNBT(nbtTagCompound);
                if (!NBTUtil.func_181123_a(tagFromJson, nbtTagCompound, " ".length() != 0)) {
                    final String s3 = CommandScoreboard.I[31 + 48 + 44 + 10];
                    final Object[] array3 = new Object[" ".length()];
                    array3["".length()] = entityName;
                    throw new CommandException(s3, array3);
                }
            }
            catch (NBTException ex) {
                final String s4 = CommandScoreboard.I[87 + 58 - 17 + 6];
                final Object[] array4 = new Object[" ".length()];
                array4["".length()] = ex.getMessage();
                throw new CommandException(s4, array4);
            }
        }
        final Score valueFromObjective = this.getScoreboard().getValueFromObjective(entityName, objective);
        if (s.equalsIgnoreCase(CommandScoreboard.I[17 + 55 + 45 + 18])) {
            valueFromObjective.setScorePoints(scorePoints);
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else if (s.equalsIgnoreCase(CommandScoreboard.I[114 + 72 - 53 + 3])) {
            valueFromObjective.increseScore(scorePoints);
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            valueFromObjective.decreaseScore(scorePoints);
        }
        final String s5 = CommandScoreboard.I[67 + 133 - 127 + 64];
        final Object[] array5 = new Object["   ".length()];
        array5["".length()] = objective.getName();
        array5[" ".length()] = entityName;
        array5["  ".length()] = valueFromObjective.getScorePoints();
        CommandBase.notifyOperators(commandSender, this, s5, array5);
    }
    
    private static void I() {
        (I = new String[91 + 156 - 52 + 35])["".length()] = I("%\u00176# 4\u001b8#!", "VtYQE");
        CommandScoreboard.I[" ".length()] = I(".'\u0003?2#,\u001d| .'\u001c71\")\u001c6}8;\u000f56", "MHnRS");
        CommandScoreboard.I["  ".length()] = I("*9\u0003\t;'2\u001dJ)*9\u001c\u00018&7\u001c\u0000t<%\u000f\u0003?", "IVndZ");
        CommandScoreboard.I["   ".length()] = I("\t\u000e\"2\t\u0012\u0005>2\u0019", "flHWj");
        CommandScoreboard.I[0x55 ^ 0x51] = I("+>=\u0002\u0004&5#A\u0016+>\"\n\u0007'0\"\u000bK'3:\n\u0006<8&\n\u0016f$#\u000e\u0002-", "HQPoe");
        CommandScoreboard.I[0x61 ^ 0x64] = I(">\"\t:", "RKzNi");
        CommandScoreboard.I[0x78 ^ 0x7E] = I("\u0007&,", "fBHpd");
        CommandScoreboard.I[0x3C ^ 0x3B] = I("\t;\u0001\u0019\u000b\u00040\u001fZ\u0019\t;\u001e\u0011\b\u00055\u001e\u0010D\u00056\u0006\u0011\t\u001e=\u001a\u0011\u0019D5\b\u0010D\u001f'\r\u0013\u000f", "jTltj");
        CommandScoreboard.I[0x7F ^ 0x77] = I("\u001d\u000f)*,\n", "ojDEZ");
        CommandScoreboard.I[0x4B ^ 0x42] = I("\u0000\u001a\u001d\u0014\f\r\u0011\u0003W\u001e\u0000\u001a\u0002\u001c\u000f\f\u0014\u0002\u001dC\f\u0017\u001a\u001c\u000e\u0017\u001c\u0006\u001c\u001eM\u0007\u0015\u0014\u0002\u0015\u0010^\f\u001e\u0002\u0012\u0015", "cupym");
        CommandScoreboard.I[0x4B ^ 0x41] = I("\u0003=:!\n\u0003(\"$\u001a", "pXNEc");
        CommandScoreboard.I[0xBA ^ 0xB1] = I("0\u001d\u0017$*=\u0016\tg80\u001d\b,)<\u0013\b-e<\u0010\u0010,('\u001b\f,8}\u0007\t(,6", "SrzIK");
        CommandScoreboard.I[0x6E ^ 0x62] = I("+'*\u001f\u0014&,4\\\u0006+'5\u0017\u0017')5\u0016['*-\u0017\u0016<!1\u0017\u0006f;\"\u0006\u0011!;7\u001e\u00141f2\u0001\u0014/-", "HHGru");
        CommandScoreboard.I[0x3B ^ 0x36] = I("\u0016\u000f\u0004.*\u0014\u0010", "fceWO");
        CommandScoreboard.I[0xA2 ^ 0xAC] = I(",#/\u0007&!(1D4,#0\u000f% -0\u000ei? #\u0013\"=?l\u001f4.+'", "OLBjG");
        CommandScoreboard.I[0x1D ^ 0x12] = I("*#\u0000\u0001", "FJsul");
        CommandScoreboard.I[0xD7 ^ 0xC7] = I(")\u001d\t\u000f\u0013$\u0016\u0017L\u0001)\u001d\u0016\u0007\u0010%\u0013\u0016\u0006\\:\u001e\u0005\u001b\u00178\u0001J\u000e\u001b9\u0006J\u0017\u0001+\u0015\u0001", "Jrdbr");
        CommandScoreboard.I[0x9 ^ 0x18] = I("\u0010&&", "qBBhl");
        CommandScoreboard.I[0x86 ^ 0x94] = I("6\u001e\"*$;\u0015<i66\u001e=\"':\u0010=#k%\u001d.> '\u0002a&!1_:4$2\u0014", "UqOGE");
        CommandScoreboard.I[0x19 ^ 0xA] = I("\u001d*7-\u0010\n", "oOZBf");
        CommandScoreboard.I[0x99 ^ 0x8D] = I("\r\u0016*\u001b\u000f\u0000\u001d4X\u001d\r\u00165\u0013\f\u0001\u00185\u0012@\u001e\u0015&\u000f\u000b\u001c\ni\u0004\u000b\u0003\u00161\u0013@\u001b\n&\u0011\u000b", "nyGvn");
        CommandScoreboard.I[0x9F ^ 0x8A] = I("\u0000-\u0003", "sHwQw");
        CommandScoreboard.I[0xD1 ^ 0xC7] = I("\u0017\u001b\u001c\u000f#\u001a\u0010\u0002L1\u0017\u001b\u0003\u0007 \u001b\u0015\u0003\u0006l\u0004\u0018\u0010\u001b'\u0006\u0007_\u0011'\u0000Z\u0004\u0011#\u0013\u0011", "ttqbB");
        CommandScoreboard.I[0x82 ^ 0x95] = I("\u00131\u0003, ", "aTpIT");
        CommandScoreboard.I[0xA6 ^ 0xBE] = I("\u0006#\u001e\u0000\u0000\u000b(\u0000C\u0012\u0006#\u0001\b\u0003\n-\u0001\tO\u0015 \u0012\u0014\u0004\u0017?]\u001f\u0004\u0016)\u0007C\u0014\u0016-\u0014\b", "eLsma");
        CommandScoreboard.I[0x82 ^ 0x9B] = I("<6 \u0003\u000b<", "YXAag");
        CommandScoreboard.I[0xB4 ^ 0xAE] = I(":)\u0005.\u000e7\"\u001bm\u001c:)\u001a&\r6'\u001a'A)*\t:\n+5F&\u00018$\u0004&A,5\t$\n", "YFhCo");
        CommandScoreboard.I[0x32 ^ 0x29] = I(",\u0002\u00140", "XggDa");
        CommandScoreboard.I[0x8B ^ 0x97] = I("\u000f)\u001d\u0018\u0003\u0002\"\u0003[\u0011\u000f)\u0002\u0010\u0000\u0003'\u0002\u0011L\u001c*\u0011\f\u0007\u001e5^\u0001\u0007\u001f2^\u0000\u0011\r!\u0015", "lFpub");
        CommandScoreboard.I[0xA1 ^ 0xBC] = I("\u0015\u0013$\u0005\u0004\u000e\n.\u0019", "zcAwe");
        CommandScoreboard.I[0xA9 ^ 0xB7] = I("\u0012\u0006\u0004\f/\u001f\r\u001aO=\u0012\u0006\u001b\u0004,\u001e\b\u001b\u0005`\u0001\u0005\b\u0018+\u0003\u001aG\u0014=\u0010\u000e\f", "qiiaN");
        CommandScoreboard.I[0x79 ^ 0x66] = I("'%'%\u001b*.9f\t'%8-\u0018++8,T4&+1\u001f69d'\n!8+<\u0013+$d=\t%-/", "DJJHz");
        CommandScoreboard.I[0xA3 ^ 0x83] = I("=/+/\u001c", "IJJBo");
        CommandScoreboard.I[0x61 ^ 0x40] = I("\u0013\b%\b\u0010\u001e\u0003;K\u0002\u0013\b:\u0000\u0013\u001f\u0006:\u0001_\u0005\u0014)\u0002\u0014", "pgHeq");
        CommandScoreboard.I[0xA5 ^ 0x87] = I(",( \b-!#>K?,(?\u0000. &?\u0001b;\",\b?a2>\u0004+*", "OGMeL");
        CommandScoreboard.I[0x5 ^ 0x26] = I("-.\u00161", "AGeEq");
        CommandScoreboard.I[0x72 ^ 0x56] = I("\t)7\u0007\u000f\u0004\")D\u001d\t)(\u000f\f\u0005'(\u000e@\u001e#;\u0007\u001dD*3\u0019\u001aD3)\u000b\t\u000f", "jFZjn");
        CommandScoreboard.I[0xE1 ^ 0xC4] = I("\r01", "lTUlA");
        CommandScoreboard.I[0x5B ^ 0x7D] = I("1!*!(<*4b:1!5)+=/5(g&+&!:|/#(g'=&+,", "RNGLI");
        CommandScoreboard.I[0x98 ^ 0xBF] = I("\u0013\u0001(7\u0004\u0004", "adEXr");
        CommandScoreboard.I[0x7D ^ 0x55] = I("4\u00057?\f9\u000e)|\u001e4\u0005(7\u000f8\u000b(6C#\u000f;?\u001ey\u0018??\u0002!\u000ft'\u001e6\r?", "WjZRm");
        CommandScoreboard.I[0xE9 ^ 0xC0] = I("?\u0000$!\u001a", "ZmTUc");
        CommandScoreboard.I[0xAB ^ 0x81] = I("\u0000*\u0000\u0014\r\r!\u001eW\u001f\u0000*\u001f\u001c\u000e\f$\u001f\u001dB\u0017 \f\u0014\u001fM \u0000\t\u0018\u001ak\u0018\n\r\u0004 ", "cEmyl");
        CommandScoreboard.I[0x61 ^ 0x4A] = I("\u0018.\u0007\"", "rAnLR");
        CommandScoreboard.I[0xAF ^ 0x83] = I("\u0016\u0006/\u0004;\u001b\r1G)\u0016\u00060\f8\u001a\b0\rt\u0001\f#\u0004)[\u0003-\u00004[\u001c1\b=\u0010", "uiBiZ");
        CommandScoreboard.I[0xEF ^ 0xC2] = I("\u0019\u0004\t\u0018\u000b", "uahnn");
        CommandScoreboard.I[0x2D ^ 0x3] = I("!\u0016(\u0019&,\u001d6Z4!\u00167\u0011%-\u00187\u0010i6\u001c$\u00194l\u0015 \u00151'W0\u0007&%\u001c", "ByEtG");
        CommandScoreboard.I[0x88 ^ 0xA7] = I("\u001a\u0018 \u001f\u0006\u001b", "uhTvi");
        CommandScoreboard.I[0x25 ^ 0x15] = I("5%\u00077\u00168.\u0019t\u00045%\u0018?\u00159+\u0018>Y\"/\u000b7\u0004x?\u0019;\u00103", "VJjZw");
        CommandScoreboard.I[0xBF ^ 0x8E] = I("\u00108$4\"\u001d3:w0\u00108;<!\u001c6;=m\u00072(40]89-*\u001c9g,0\u00120,", "sWIYC");
        CommandScoreboard.I[0x2 ^ 0x30] = I("H", "bjKTG");
        CommandScoreboard.I[0x5C ^ 0x6F] = I(",%\f+\u0015!.\u0012h\u0007,%\u0013#\u0016 +\u0013\"Z!%,3\u0018;#6/\u0018+)\u00004\u0010", "OJaFt");
        CommandScoreboard.I[0x23 ^ 0x17] = I("4\u001a\u0015.09\u0011\u000bm\"4\u001a\n&38\u0014\n'\u007f6\u0019\u0014\u000e0#\u0016\u0010&\"\u0011\u0014\u0011/43", "WuxCQ");
        CommandScoreboard.I[0x39 ^ 0xC] = I("\u0006\u001a;<\u000f\u000b\u0011%\u007f\u001d\u0006\u001a$4\f\n\u0014$5@\n\u0017<4\r\u0011\u001c 4 \n\u0001\u0010>\u001b\u000b\u0011", "euVQn");
        CommandScoreboard.I[0x30 ^ 0x6] = I("\u0013'\u0001'-\u001e,\u001fd?\u0013'\u001e/.\u001f)\u001e.b\u001f*\u0006//\u0004!\u001a/\u001e\u0015)\b\u0005\"\u001c1", "pHlJL");
        CommandScoreboard.I[0x19 ^ 0x2E] = I("\u000f9\u0007\"5\u00022\u0019a'\u000f9\u0018*6\u00037\u0018+z\u00183\u000b\"\u001a\u0003\", !\u00022", "lVjOT");
        CommandScoreboard.I[0x90 ^ 0xA8] = I("2\u000b+\u00019?\u00005B+2\u000b4\t:>\u00054\bv>\u0006,\t;%\r0\t+\u007f\u0005\"\bv&\u0016)\u0002?\u0005\u001d6\t", "QdFlX");
        CommandScoreboard.I[0xA7 ^ 0x9E] = I("\n\u001c479\u0007\u0017*t+\n\u001c+?:\u0006\u0012+>v\u0006\u00113?;\u001d\u001a/?+G\u0012=>v\b\u001f+?9\r\n\u001c\"1\u001a\u0007*", "isYZX");
        CommandScoreboard.I[0x13 ^ 0x29] = I("\u001a\u001a&\u0006)\u0017\u00118E;\u001a\u001a9\u000e*\u0016\u00149\u000ff\u0016\u0017!\u000e+\r\u001c=\u000e;W\u0014/\u000ff\r\u001a$''\u0017\u0012", "yuKkH");
        CommandScoreboard.I[0x0 ^ 0x3B] = I("--\u001a\u0004\u0017 &\u0004G\u0005--\u0005\f\u0014!#\u0005\rX! \u001d\f\u0015:+\u0001\f\u0005`#\u0013\rX;1\u0016\u000e\u0013", "NBwiv");
        CommandScoreboard.I[0xB4 ^ 0x88] = I("2'?\u001d\u0017?,!^\u00052' \u0015\u0014>) \u0014X>*8\u0015\u0015%!$\u0015\u0005\u007f)6\u0014X5!!\u0000\u001a01\u0006\u001f\u0019\u001d'<\u0017", "QHRpv");
        CommandScoreboard.I[0x6 ^ 0x3B] = I("67\u0002\u0000#;<\u001cC167\u001d\b :9\u001d\tl::\u0005\b!!1\u0019\b1{9\u000b\tl&-\f\u000e'&+", "UXomB");
        CommandScoreboard.I[0x41 ^ 0x7F] = I("\f&$\t)\u0001-:J;\f&;\u0001*\u0000(;\u0000f\u001b,(\t;A(-\u0000f\u000e%;\u0001)\u000b0\f\u001c!\u001c=:", "oIIdH");
        CommandScoreboard.I[0x42 ^ 0x7D] = I("\u0015\"\u0006\u001a.\u0018)\u0018Y<\u0015\"\u0019\u0012-\u0019,\u0019\u0013a\u0002(\n\u001a<X,\u000f\u0013a\u0002\"\u0004; \u0018*", "vMkwO");
        CommandScoreboard.I[0xF3 ^ 0xB3] = I("\u0005\u0016:?\u0012\b\u001d$|\u0000\u0005\u0016%7\u0011\t\u0018%6]\u0012\u001c6?\u0000H\u001836]\u0013\n65\u0016", "fyWRs");
        CommandScoreboard.I[0x3C ^ 0x7D] = I("\u000f*+\n7\u0002!5I%\u000f*4\u00024\u0003$4\u0003x\u0018 '\n%B$\"\u0003x\b,5\u0017:\r<\u0012\b9 *(\u0000", "lEFgV");
        CommandScoreboard.I[0x3C ^ 0x7E] = I("\u0014\f\u001b$.\u0019\u0007\u0005g<\u0014\f\u0004,-\u0018\u0002\u0004-a\u0003\u0006\u0017$<Y\u0002\u0012-a\u0004\u0016\u0015**\u0004\u0010", "wcvIO");
        CommandScoreboard.I[0xF8 ^ 0xBB] = I("3\"\n;\u0018", "PMfTj");
        CommandScoreboard.I[0x69 ^ 0x2D] = I("\u0003\u0019\u0004\u001f\b\u0001\u0007\u0014\u001c\u000f\u0017\u000e", "ekmzf");
        CommandScoreboard.I[0x21 ^ 0x64] = I(">\u0004\u0004*8$\u0004\u000f\b&4(\u000f\u001a#>\b\u0003\u0000/>", "MaalJ");
        CommandScoreboard.I[0x33 ^ 0x75] = I("\u000f\u0010;'\u0012\u0000\u0016\u0000+\u0015\b\u0013?.\u000f\u0015\b", "aqVBf");
        CommandScoreboard.I[0xF7 ^ 0xB0] = I("\u0000!\u000b\u0017\u001f)!\u0019\u0010\u0016\u0003!<\n\u0004\r&\u0003\u000f\u001e\u0010=", "dDjcw");
        CommandScoreboard.I[0x5C ^ 0x14] = I("9.\u001c\u0000\u00064%\u0002C\u00149.\u0003\b\u00055 \u0003\tI.$\u0010\u0000\u0014t.\u0001\u0019\u000e5/_\u0018\u0014;&\u0014", "ZAqmg");
        CommandScoreboard.I[0xF4 ^ 0xBD] = I("!\u0002\u001d*7", "BmqEE");
        CommandScoreboard.I[0x25 ^ 0x6F] = I("\u0019<\t+8\u00147\u0017h*\u0019<\u0016#;\u00152\u0016\"w\u000e6\u0005+*T<\u001420\u0015=J(6,2\b3<", "zSdFY");
        CommandScoreboard.I[0x49 ^ 0x2] = I("65\u000e=\u00074+\u001e>\u0000\"\"", "PGgXi");
        CommandScoreboard.I[0x2A ^ 0x66] = I(";\u0002\t\u0011\u0015!\u0002\u00023\u000b1.\u0002!\u000e;\u000e\u000e;\u0002;", "HglWg");
        CommandScoreboard.I[0x7 ^ 0x4A] = I("\u000f&=\u001f5\u0000 \u0006\u00132\b%9\u0016(\u0015>", "aGPzA");
        CommandScoreboard.I[0x72 ^ 0x3C] = I("/-\u0016.\u001d\u0006-\u0004)\u0014,-!3\u0006\"*\u001e6\u001c?1", "KHwZu");
        CommandScoreboard.I[0xA ^ 0x45] = I("/\u0015\u001f\t\u0003\"\u001e\u0001J\u0011/\u0015\u0000\u0001\u0000#\u001b\u0000\u0000L8\u001f\u0013\t\u0011b\u0015\u0002\u0010\u000b#\u0014\\\u0011\u0011-\u001d\u0017", "Lzrdb");
        CommandScoreboard.I[0xD7 ^ 0x87] = I("\u00155\u0003\u000e\u000f\u0018>\u001dM\u001d\u00155\u001c\u0006\f\u0019;\u001c\u0007@\u0002?\u000f\u000e\u001dX5\u001e\u0017\u0007\u00194@\r\u0001 ;\u0002\u0016\u000b", "vZncn");
        CommandScoreboard.I[0x66 ^ 0x37] = I("\u0005\u0019\u0014\b\u0003\b\u0012\nK\u0011\u0005\u0019\u000b\u0000\u0000\t\u0017\u000b\u0001L\u0012\u0013\u0018\b\u0011H\u0019\t\u0011\u000b\t\u0018W\u000b\r0\u0017\u0015\u0010\u0007", "fvyeb");
        CommandScoreboard.I[0x43 ^ 0x11] = I("\"=;\u0010", "VONue");
        CommandScoreboard.I[0x4A ^ 0x19] = I("\u00051\u001b\u0018&", "cPwkC");
        CommandScoreboard.I[0x70 ^ 0x24] = I("!\u0017\u000e\u000e?", "BxbaM");
        CommandScoreboard.I[0x13 ^ 0x46] = I("\u001b\u0016\u0007 &\u0016\u001d\u0019c4\u001b\u0016\u0018(%\u0017\u0018\u0018)i\f\u001c\u000b 4V\u0016\u001a9.\u0017\u0017D#(.\u0018\u00068\"", "xyjMG");
        CommandScoreboard.I[0xEE ^ 0xB8] = I("3\u0015/\u0015\u00041\u000b?\u0016\u0003'\u0002", "UgFpj");
        CommandScoreboard.I[0xFA ^ 0xAD] = I("\u0018\n\u001f2", "lxjWe");
        CommandScoreboard.I[0x56 ^ 0xE] = I("\u0005\u0013$\u001a\u0007", "crHib");
        CommandScoreboard.I[0x59 ^ 0x0] = I("\u001a6/\u001e1\u0017=1]#\u001a60\u00162\u001680\u0017~\r<#\u001e#W62\u00079\u00167l\u001d?/8.\u00065", "yYBsP");
        CommandScoreboard.I[0x31 ^ 0x6B] = I(",\u00174\u0011", "XeAtW");
        CommandScoreboard.I[0xE5 ^ 0xBE] = I("\u000e\u000b\t\u00033", "hjepV");
        CommandScoreboard.I[0x9A ^ 0xC6] = I("9 -\f", "MRXiN");
        CommandScoreboard.I[0x2C ^ 0x71] = I("&\u000f\r3\u001a<\u000f\u0006\u0011\u0004,#\u0006\u0003\u0001&\u0003\n\u0019\r&", "Ujhuh");
        CommandScoreboard.I[0xC ^ 0x52] = I("\u00110\u000f\u0017", "eBzrv");
        CommandScoreboard.I[0x2F ^ 0x70] = I("-++\u0000\u0015", "KJGsp");
        CommandScoreboard.I[0x17 ^ 0x77] = I("\u0013\u0019\u0019\u000e\n\u001e\u0012\u0007M\u0018\u0013\u0019\u0006\u0006\t\u001f\u0017\u0006\u0007E\u0004\u0013\u0015\u000e\u0018^\u0019\u0004\u0017\u0002\u001f\u0018Z\r\u0004&\u0017\u0018\u0016\u000e", "pvtck");
        CommandScoreboard.I[0x36 ^ 0x57] = I("6\u0019\u0012\u000f", "Bkgju");
        CommandScoreboard.I[0xDE ^ 0xBC] = I(")#/</", "OBCOJ");
        CommandScoreboard.I[0xC ^ 0x6F] = I("\u0004'!\u0017", "pUTrt");
        CommandScoreboard.I[0x2E ^ 0x4A] = I("\u001f\u00169''\u0010\u0010\u0002+ \u0018\u0015=.:\u0005\u000e", "qwTBS");
        CommandScoreboard.I[0xB ^ 0x6E] = I("6\t\u001b(8;\u0002\u0005k*6\t\u0004 ;:\u0007\u0004!w!\u0003\u0017(*{\t\u000610:\bX+6\u0003\u0007\u001a0<", "UfvEY");
        CommandScoreboard.I[0x1C ^ 0x7A] = I("*0\u001b&\u0007\u00030\t!\u000e)0,;\u001c'7\u0013>\u0006:,", "NUzRo");
        CommandScoreboard.I[0x25 ^ 0x42] = I("\u0011\u001b\u001d49\u001c\u0010\u0003w+\u0011\u001b\u0002<:\u001d\u0015\u0002=v\u0006\u0011\u00114+\\\u001b\u0000-1\u001d\u001a^77$\u0015\u001c,=", "rtpYX");
        CommandScoreboard.I[0xF2 ^ 0x9A] = I("6\"94\u0003;)'w\u00116\"&<\u0000:,&=L!(54\u0011{\"$-\u000b:#z*\u00176.1*\u0011", "UMTYb");
        CommandScoreboard.I[0x2B ^ 0x42] = I("\u000287\u00040\u000f3)G\"\u00028(\f3\u000e6(\r\u007f\u00152;\u0004\"O%?\u0004>\u00172t\u001a$\u00024?\u001a\"", "aWZiQ");
        CommandScoreboard.I[0x24 ^ 0x4E] = I("+!\u0018\t &*\u0006J2+!\u0007\u0001#'/\u0007\u0000o<+\u0014\t2f\"\u001c\u00175f>\u0019\u00058-<[\u0001,8:\f", "HNudA");
        CommandScoreboard.I[0xDE ^ 0xB5] = I(".\u0000,?##\u000b2|1.\u000037 \"\u000e36l9\n ?1c\u0003(!6c\u001f-3;(\u001do1-8\u00015", "MoARB");
        CommandScoreboard.I[0x2B ^ 0x47] = I("\r6\u0002*\f\u0000=\u001ci\u001e\r6\u001d\"\u000f\u00018\u001d#C\u001a<\u000e*\u001e@5\u00064\u0019@<\u00027\u0019\u0017", "nYoGm");
        CommandScoreboard.I[0x26 ^ 0x4B] = I("\t\n\u00064\u0016\u0004\u0001\u0018w\u0004\t\n\u0019<\u0015\u0005\u0004\u0019=Y\u001e\u0000\n4\u0004D\t\u0002*\u0003D\u0006\u0004,\u0019\u001e", "jekYw");
        CommandScoreboard.I[0x44 ^ 0x2A] = I("\u0001(\u001e\u001a)\f#\u0000Y;\u0001(\u0001\u0012*\r&\u0001\u0013f\u0016\"\u0012\u001a;L+\u001a\u0004<L\"\u001d\u0003:\u001b", "bGswH");
        CommandScoreboard.I[0x3D ^ 0x52] = I("(", "hVsrm");
        CommandScoreboard.I[0xD8 ^ 0xA8] = I("3\u001f\u0003(0>\u0014\u001dk\"3\u001f\u001c 3?\u0011\u001c!\u007f$\u0015\u000f(\"~\u001a\u0001,?~\u0003\u001b&25\u0003\u001d", "PpnEQ");
        CommandScoreboard.I[0xD7 ^ 0xA6] = I("\"\u0016\u000e\"\u0011/\u001d\u0010a\u0003\"\u0016\u0011*\u0012.\u0018\u0011+^5\u001c\u0002\"\u0003o\u0013\f&\u001eo\u001f\u0002&\u001c4\u000b\u0006", "AycOp");
        CommandScoreboard.I[0x34 ^ 0x46] = I("\u0001", "AHyPs");
        CommandScoreboard.I[0xD7 ^ 0xA4] = I("\u0001\u0016\u000f;\u0004\f\u001d\u0011x\u0016\u0001\u0016\u00103\u0007\r\u0018\u00102K\u0016\u001c\u0003;\u0016L\u0015\u00077\u0013\u0007W\u0011#\u0006\u0001\u001c\u0011%", "bybVe");
        CommandScoreboard.I[0xFD ^ 0x89] = I(":\r \u0002+7\u0006>A9:\r?\n(6\u0003?\u000bd-\u0007,\u00029w\u000e(\u000e<<L+\u000e#5\u0017?\n", "YbMoJ");
        CommandScoreboard.I[0x33 ^ 0x46] = I("\u0006\u001b+\u0018.\u000b\u00105[<\u0006\u001b4\u0010-\n\u00154\u0011a\u0011\u0011'\u0018<K\u0011+\u0005;\u001cZ'\u0019=\u0000\u0015\"\f\n\b\u00042\f", "etFuO");
        CommandScoreboard.I[0xF7 ^ 0x81] = I("2.\"\u001a)?%<Y;2.=\u0012*> =\u0013f%$.\u001a;\u007f$\"\u0007<(o<\u0002+2$<\u0004", "QAOwH");
        CommandScoreboard.I[0x68 ^ 0x1F] = I("4\u0019\u0006\u001e\u00109\u0012\u0018]\u00024\u0019\u0019\u0016\u00138\u0017\u0019\u0017_8\u0014\u0001\u0016\u0012#\u001f\u001d\u0016\u0002y\u0004\u000e\u001e\u001e!\u0013E\u0000\u00044\u0015\u000e\u0000\u0002", "Wvksq");
        CommandScoreboard.I[0x2D ^ 0x55] = I("\"%=\t&/.#J4\"%\"\u0001%.+\"\u0000i.(:\u0001$5#&\u00014o&9\u00173o/=\u001438", "AJPdG");
        CommandScoreboard.I[0xDF ^ 0xA6] = I("!$)\b\u0012,/7K\u0000!$6\u0000\u0011-*6\u0001]-).\u0000\u00106\"2\u0000\u0000l'-\u0016\u0007l(+\u0010\u001d6", "BKDes");
        CommandScoreboard.I[0x1A ^ 0x60] = I("\u00058\u001d\u0014$\b3\u0003W6\u00058\u0002\u001c'\t6\u0002\u001dk\t5\u001a\u001c&\u0012>\u0006\u001c6H;\u0019\n1H2\u001e\r7\u001f", "fWpyE");
        CommandScoreboard.I[0xC4 ^ 0xBF] = I("\u0019\u001f\u001852\u0014\u0014\u0006v \u0019\u001f\u0007=1\u0015\u0011\u0007<}\u0015\u0012\u001f=0\u000e\u0019\u0003= T\u0003\u0010,7\u0013\u0003\u000542\u0003^\u001c6%\u001b\u001c\u001c<\u0000\u0016\u001f\u0001", "zpuXS");
        CommandScoreboard.I[0x18 ^ 0x64] = I("\u001b> \u0018+\u00165>[9\u001b>?\u0010(\u00170?\u0011d\u00173'\u0010)\f8;\u00109V\"(\u0001.\u0011\"=\u0019+\u0001\u007f>\u0000)\u001b4>\u0006\u0019\u001d%", "xQMuJ");
        CommandScoreboard.I[0x54 ^ 0x29] = I(";7\u000e,.6<\u0010o<;7\u0011$-79\u0011%a7:\t$,,1\u0015$<v+\u00065+1+\u0013-.!v\u00104,;=\u00102\f4=\u00023*<", "XXcAO");
        CommandScoreboard.I[0x1B ^ 0x65] = I("\u0016$\u0005\u0014\u0012\u001b/\u001bW\u0000\u0016$\u001a\u001c\u0011\u001a*\u001a\u001d]\u0005'\t\u0000\u0016\u00078F\u0015\u001a\u0006?F\t\u001f\u00142\r\u000b]\u0010&\u0018\r\n", "uKhys");
        CommandScoreboard.I[113 + 103 - 142 + 53] = I("2)\u001c*3?\"\u0002i!2)\u0003\"0>'\u0003#|!*\u0010>7#5_+;\"2_7>0?\u00145|2)\u0004)&", "QFqGR");
        CommandScoreboard.I[35 + 8 + 41 + 44] = I("'<%98*7;z*'<:1;+2:0w4?)-<6 f807'f$5%*-&w!=<& ", "DSHTY");
        CommandScoreboard.I[103 + 86 - 85 + 25] = I("*\t\u0005\u0000\u0002'\u0002\u001bC\u0010*\t\u001a\b\u0001&\u0007\u001a\tM9\n\t\u0014\u0006;\u0015F\u0001\n:\u0012F\b\u000e9\u0012\u0011", "Ifhmc");
        CommandScoreboard.I[102 + 105 - 151 + 74] = I("\b'\u001f84\u0005,\u0001{&\b'\u000007\u0004)\u00001{\u001b$\u0013,0\u0019;\\9<\u0018<\\6:\u001e&\u0006", "kHrUU");
        CommandScoreboard.I[18 + 96 - 57 + 74] = I("4(\u00055\u00159#\u001bv\u00074(\u001a=\u00168&\u001a<Z'+\t!\u0011%4F6\u0015:\"F,\u001b8\u000b\u00076\u0013", "WGhXt");
        CommandScoreboard.I[82 + 73 - 151 + 128] = I("\u001f\f\u0019", "limIv");
        CommandScoreboard.I[111 + 31 - 121 + 112] = I("\u0001\u001a\u0017\u0014#\f\u0011\tW1\u0001\u001a\b\u001c \r\u0014\b\u001dl\u0012\u0019\u001b\u0000'\u0010\u0006T\n'\u0016[\u000e\u0018%/\u001c\t\u0014#\u0016\u0016\u0012", "buzyB");
        CommandScoreboard.I[49 + 84 - 43 + 44] = I("/;??\n\"0!|\u0018/; 7\t#5 6E<83+\u000e>'|!\u000e8z&3\f\t& =\u0019", "LTRRk");
        CommandScoreboard.I[90 + 114 - 112 + 43] = I("\u0007);", "tLOMw");
        CommandScoreboard.I[85 + 121 - 157 + 87] = I("\u0016&\u0007", "wBcWO");
        CommandScoreboard.I[136 + 73 - 108 + 36] = I("2\u0019\u0018\f)?\u0012\u0006O;2\u0019\u0007\u0004*>\u0017\u0007\u0005f!\u001a\u0014\u0018-#\u0005[\u0012-%X\u0006\u0014+2\u0013\u0006\u0012", "QvuaH");
        CommandScoreboard.I[72 + 119 - 121 + 68] = I("\u0019%\u0019\u001b3\u0014.\u0007X!\u0019%\u0006\u00130\u0015+\u0006\u0012|\n&\u0015\u000f7\b9Z\u00047\t/\u0000\u00051\u00158\u0011X!\u000f)\u0017\u0013!\t", "zJtvR");
        CommandScoreboard.I[56 + 30 + 17 + 36] = I("\"\u00008/6/\u000b&l$\"\u0000''5.\u000e'&y1\u00034;23\u001c{022\n!l$4\f6'$2", "AoUBW");
        CommandScoreboard.I[77 + 46 - 11 + 28] = I("\n\r\u0005=0\u0007\u0006\u001b~\"\n\r\u001a53\u0006\u0003\u001a4\u007f\u0019\u000e\t)4\u001b\u0011F>0\u0004\u0007F$>\u0006.\u0007>6", "ibhPQ");
        CommandScoreboard.I[33 + 73 + 3 + 32] = I("-*\u001e\u0015$ !\u0000V6-*\u0001\u001d'!$\u0001\u001ck>)\u0012\u0001 <6]\u001d+/'\u001f\u001dk *'\n,)\"\u0016\n", "NEsxE");
        CommandScoreboard.I[108 + 23 - 62 + 73] = I("'\u001a/8\u0018*\u00111{\n'\u001a00\u001b+\u001401W4\u0019#,\u001c6\u0006l0\u0017%\u0017.0W7\u0000!6\u001c7\u0006", "DuBUy");
        CommandScoreboard.I[25 + 49 - 14 + 83] = I("\t\u001b<\b0\u0004\u0010\"K\"\t\u001b#\u00003\u0005\u0015#\u0001\u007f\u001a\u00180\u001c4\u0018\u0007\u007f\u000b0\u0007\u0011\u007f\u0011>\u00058>\u000b6", "jtQeQ");
        CommandScoreboard.I[92 + 11 - 88 + 129] = I(".+%/$# ;l6.+:''\"%:&k=(); ?7f6 >0f,*9\u0002'7+)", "MDHBE");
        CommandScoreboard.I[120 + 54 - 141 + 112] = I("i", "COtBO");
        CommandScoreboard.I[79 + 80 - 20 + 7] = I("R", "xCPfZ");
        CommandScoreboard.I[21 + 95 + 20 + 11] = I("4\u0005\u000f\u0000\u00049\u000e\u0011C\u00164\u0005\u0010\b\u00078\u000b\u0010\tK'\u0006\u0003\u0014\u0000%\u0019L\u0019\u0000$\u001eL\u001e\u00104\t\u0007\u001e\u0016", "Wjbme");
        CommandScoreboard.I[2 + 106 - 64 + 104] = I("+\u0017+8\u000b&\u001c5{\u0019+\u001740\b'\u001941D8\u0014',\u000f:\u000bh!\u000f;\fh3\u000b!\u0014#1", "HxFUj");
        CommandScoreboard.I[137 + 40 - 71 + 43] = I("\u000b.\b\u0001.\u0006%\u0016B<\u000b.\u0017\t-\u0007 \u0017\ba\u0018-\u0004\u0015*\u001a2K\u0002.\u0005$K\u0018 \u0007\r\n\u0002(", "hAelO");
        CommandScoreboard.I[36 + 117 - 69 + 66] = I("&,\u0004=\n+'\u001a~\u0018&,\u001b5\t*\"\u001b4E5/\b)\u000e70G>\n(&G$\u0004*\u000f\u0006>\f", "ECiPk");
        CommandScoreboard.I[142 + 76 - 95 + 28] = I("\u0014 \u000f&\u0015\u0019+\u0011e\u0007\u0014 \u0010.\u0016\u0018.\u0010/Z\u0007#\u00032\u0011\u0005<L$\u0004\u0012=\u0003?\u001d\u0018!L%\u001b\u0003\t\r>\u001a\u0013", "wObKt");
        CommandScoreboard.I[43 + 6 - 12 + 115] = I("CS", "hnSCw");
        CommandScoreboard.I[106 + 40 - 135 + 142] = I("n_", "CbBQR");
        CommandScoreboard.I[75 + 144 - 70 + 5] = I("}x", "WEXmG");
        CommandScoreboard.I[147 + 75 - 208 + 141] = I("^i", "qTYFc");
        CommandScoreboard.I[107 + 81 - 159 + 127] = I("md", "HYiNi");
        CommandScoreboard.I[42 + 122 - 18 + 11] = I("Q", "lUjcJ");
        CommandScoreboard.I[112 + 154 - 152 + 44] = I("i", "UGylM");
        CommandScoreboard.I[36 + 145 - 127 + 105] = I("f", "XBcGK");
        CommandScoreboard.I[120 + 137 - 132 + 35] = I("Rn", "lRnwU");
        CommandScoreboard.I[102 + 21 - 13 + 51] = I("\u0006\u001b\u001a\u0005#\u000b\u0010\u0004F1\u0006\u001b\u0005\r \n\u0015\u0005\fl\u0015\u0018\u0016\u0011'\u0017\u0007Y\u00072\u0000\u0006\u0016\u001c+\n\u001aY\u0001,\u0013\u0015\u001b\u0001&*\u0004\u0012\u001a#\u0011\u001d\u0018\u0006", "etwhB");
        CommandScoreboard.I[3 + 43 + 55 + 61] = I("\u0007\u001b\u0002\f\u0007\n\u0010\u001cO\u0015\u0007\u001b\u001d\u0004\u0004\u000b\u0015\u001d\u0005H\u0014\u0018\u000e\u0018\u0003\u0016\u0007A\u000e\u0016\u0001\u0006\u000e\u0015\u000f\u000b\u001aA\u0012\u0013\u0007\u0017\n\u0012\u0015", "dtoaf");
        CommandScoreboard.I[25 + 46 + 51 + 41] = I("\u001b\u000e\b\n-\u0000\u0005\u0014\n=", "tlboN");
        CommandScoreboard.I[134 + 51 - 67 + 46] = I("\u001c\u0018\u0010>\u0010\u001e\u0007", "ltqGu");
        CommandScoreboard.I[12 + 118 - 50 + 85] = I("76\u0002\f!", "CScaR");
        CommandScoreboard.I[115 + 126 - 132 + 57] = I("\u0002-\u0003\u0004\r\u0019&\u001f\u0004\u001d", "mOian");
        CommandScoreboard.I[132 + 27 - 75 + 83] = I(".\b\u001d7", "BanCq");
        CommandScoreboard.I[110 + 107 - 140 + 91] = I("/)\u0013", "NMwrX");
        CommandScoreboard.I[66 + 12 - 9 + 100] = I("\u0017\u0016\u0000\n\u0005\u0000", "esmes");
        CommandScoreboard.I[155 + 26 - 115 + 104] = I(")1\u0015/,)$\r*<", "ZTaKE");
        CommandScoreboard.I[144 + 86 - 97 + 38] = I("\u001731", "vWUqJ");
        CommandScoreboard.I[49 + 59 - 49 + 113] = I("\u00154\u0019\u00178\u0002", "gQtxN");
        CommandScoreboard.I[144 + 6 - 143 + 166] = I("\u001c\u0015\u001b6:\u001c\u0000\u00033*", "opoRS");
        CommandScoreboard.I[24 + 171 - 155 + 134] = I("\u0012%2\u0018.\u0010:", "bISaK");
        CommandScoreboard.I[106 + 94 - 85 + 60] = I("\u0003\u001d\f", "pxxVo");
        CommandScoreboard.I[8 + 31 + 51 + 86] = I("\u000f\u000b.", "noJkh");
        CommandScoreboard.I[95 + 101 - 138 + 119] = I("\u001f!\b\u00029\b", "mDemO");
        CommandScoreboard.I[162 + 111 - 97 + 2] = I("\u0004\u00134\t\u0017", "vvGlc");
        CommandScoreboard.I[123 + 62 - 120 + 114] = I(" \u001e7\u0000", "LwDtc");
        CommandScoreboard.I[108 + 178 - 228 + 122] = I("\u001c\u000b\u0015\u0017\t\u001c", "yetue");
        CommandScoreboard.I[78 + 79 + 18 + 6] = I("&=\u0014\u0018", "RXglO");
        CommandScoreboard.I[147 + 54 - 61 + 42] = I(".8\b\u001d\u00195!\u0002\u0001", "AHmox");
        CommandScoreboard.I[135 + 105 - 115 + 58] = I("\u0014(;", "gMOCg");
        CommandScoreboard.I[164 + 8 - 149 + 161] = I("6\"\u0011", "WFuSF");
        CommandScoreboard.I[148 + 109 - 239 + 167] = I("1\u000459\u001d&", "CaXVk");
        CommandScoreboard.I[147 + 70 - 135 + 104] = I("\u0014.:\u0015\u0012", "fKIpf");
        CommandScoreboard.I[46 + 158 - 176 + 159] = I("\u0007\u0004\u0013\u0014\r\u0007", "bjrva");
        CommandScoreboard.I[31 + 0 + 30 + 127] = I("\u0006\u0013*$", "jzYPA");
        CommandScoreboard.I[25 + 9 + 9 + 146] = I("#\u0000\u0018\u0019", "Wekmx");
        CommandScoreboard.I[117 + 3 - 82 + 152] = I("\n\u00163\u00047\u0011\u000f9\u0018", "efVvV");
        CommandScoreboard.I[8 + 44 - 20 + 159] = I("zi", "QTJYj");
        CommandScoreboard.I[81 + 116 - 113 + 108] = I("@U", "mhjdH");
        CommandScoreboard.I[72 + 115 - 156 + 162] = I("gX", "MelNf");
        CommandScoreboard.I[149 + 192 - 229 + 82] = I("Z_", "ubXpT");
        CommandScoreboard.I[115 + 102 - 109 + 87] = I("uE", "PxkyL");
        CommandScoreboard.I[134 + 12 - 137 + 187] = I("p", "MZHco");
        CommandScoreboard.I[112 + 18 - 100 + 167] = I("]", "adLGP");
        CommandScoreboard.I[63 + 43 + 33 + 59] = I("R", "lOfYB");
        CommandScoreboard.I[83 + 169 - 92 + 39] = I("VS", "hobRO");
        CommandScoreboard.I[105 + 116 - 196 + 175] = I("3#*\u001c", "GFYhW");
        CommandScoreboard.I[69 + 132 - 30 + 30] = I("1?;\u0005!", "EZZhR");
        CommandScoreboard.I[153 + 61 - 29 + 17] = I("\u00172.", "vVJzA");
        CommandScoreboard.I[62 + 148 - 149 + 142] = I("\u0000\u0003\u001e\u0006\u0004\u0017", "rfsir");
        CommandScoreboard.I[171 + 87 - 159 + 105] = I("\u0006\u0019\u0010*", "lvyDC");
        CommandScoreboard.I[179 + 81 - 138 + 83] = I("=/\u0017\u0017/", "QJvaJ");
        CommandScoreboard.I[160 + 197 - 350 + 199] = I("3\u001f=\u0001\u0014", "VrMum");
        CommandScoreboard.I[199 + 114 - 115 + 9] = I("\u001a\f\u001d\u0003", "venwH");
        CommandScoreboard.I[40 + 49 - 79 + 198] = I(";\b\u0002!\u001b:", "TxvHt");
        CommandScoreboard.I[99 + 165 - 211 + 156] = I("\b\u00039%", "blPKz");
        CommandScoreboard.I[141 + 98 - 67 + 38] = I("\u001b!#!\b", "wDBWm");
        CommandScoreboard.I[46 + 33 + 23 + 109] = I("=\u0019\u001c0\u001c", "XtlDe");
        CommandScoreboard.I[32 + 147 - 176 + 209] = I(">\u0007!\f", "RnRxE");
        CommandScoreboard.I[208 + 90 - 99 + 14] = I("\u00116>\u001a\u0014\u0006", "cSSub");
        CommandScoreboard.I[205 + 93 - 135 + 51] = I("97=$\f8", "VGIMc");
        CommandScoreboard.I[45 + 11 + 43 + 116] = I("%.\u0003*\u001b", "FAoEi");
        CommandScoreboard.I[44 + 86 - 80 + 166] = I("\u0001\u0017!1\u001a\u0003\t12\u001d\u0015\u0000", "geHTt");
        CommandScoreboard.I[184 + 22 - 91 + 102] = I("&2\u0007\u0004*<2\f&4,\u001e\f41&>\u0000.=&", "UWbBX");
        CommandScoreboard.I[86 + 211 - 138 + 59] = I("\f\u0014\f$,\u0003\u00127(+\u000b\u0017\b-1\u0016\f", "buaAX");
        CommandScoreboard.I[68 + 187 - 156 + 120] = I("\u00012'\u0000\u000f(25\u0007\u0006\u00022\u0010\u001d\u0014\f5/\u0018\u000e\u0011.", "eWFtg");
        CommandScoreboard.I[64 + 131 - 93 + 118] = I("+\r+\u001d7", "HbGrE");
        CommandScoreboard.I[38 + 114 - 147 + 216] = I("\u0003\u0011+-0\f\u0017\u0010!7\u0004\u0012/$-\u0019\t", "mpFHD");
        CommandScoreboard.I[47 + 156 - 38 + 57] = I("\u0001\u00131\u0018\n(\u0013#\u001f\u0003\u0002\u0013\u0006\u0005\u0011\f\u00149\u0000\u000b\u0011\u000f", "evPlb");
        CommandScoreboard.I[17 + 192 - 8 + 22] = I("+(\u001e\u001d\n)6\u000e\u001e\r??", "MZwxd");
        CommandScoreboard.I[3 + 91 + 59 + 71] = I("0-#1\"*-(\u0013<:\u0001(\u000190!$\u001b50", "CHFwP");
        CommandScoreboard.I[166 + 14 - 51 + 96] = I(".\u0000\u000f\"", "ZrzGX");
        CommandScoreboard.I[31 + 221 - 165 + 139] = I(">\u0015\u001e9\u001f", "XtrJz");
        CommandScoreboard.I[10 + 8 + 151 + 58] = I("5\u001e\u0012?27\u0001", "ErsFW");
        CommandScoreboard.I[5 + 16 - 12 + 219] = I("\u0000\u001f\t#)", "tzhNZ");
        CommandScoreboard.I[116 + 145 - 112 + 80] = I("5\u000038\u0013.\u00199$", "ZpVJr");
    }
    
    protected void listTeams(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        if (array.length > n) {
            final ScorePlayerTeam team = this.getTeam(array[n]);
            if (team == null) {
                return;
            }
            final Collection<String> membershipCollection = team.getMembershipCollection();
            commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, membershipCollection.size());
            if (membershipCollection.size() <= 0) {
                final String s = CommandScoreboard.I[0x14 ^ 0x7E];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = team.getRegisteredName();
                throw new CommandException(s, array2);
            }
            final String s2 = CommandScoreboard.I[0xC8 ^ 0xA3];
            final Object[] array3 = new Object["  ".length()];
            array3["".length()] = membershipCollection.size();
            array3[" ".length()] = team.getRegisteredName();
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(s2, array3);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation);
            commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(membershipCollection.toArray())));
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            final Collection<ScorePlayerTeam> teams = scoreboard.getTeams();
            commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, teams.size());
            if (teams.size() <= 0) {
                throw new CommandException(CommandScoreboard.I[0x26 ^ 0x4A], new Object["".length()]);
            }
            final String s3 = CommandScoreboard.I[0xAC ^ 0xC1];
            final Object[] array4 = new Object[" ".length()];
            array4["".length()] = teams.size();
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation(s3, array4);
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation2);
            final Iterator<ScorePlayerTeam> iterator = teams.iterator();
            "".length();
            if (1 == -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final ScorePlayerTeam scorePlayerTeam = iterator.next();
                final String s4 = CommandScoreboard.I[0xED ^ 0x83];
                final Object[] array5 = new Object["   ".length()];
                array5["".length()] = scorePlayerTeam.getRegisteredName();
                array5[" ".length()] = scorePlayerTeam.getTeamName();
                array5["  ".length()] = scorePlayerTeam.getMembershipCollection().size();
                commandSender.addChatMessage(new ChatComponentTranslation(s4, array5));
            }
        }
    }
    
    protected void setTeamOption(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final ScorePlayerTeam team = this.getTeam(array[n++]);
        if (team != null) {
            final String lowerCase = array[n++].toLowerCase();
            if (!lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x54 ^ 0x17]) && !lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x3C ^ 0x78]) && !lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x57 ^ 0x12]) && !lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x82 ^ 0xC4]) && !lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x53 ^ 0x14])) {
                throw new WrongUsageException(CommandScoreboard.I[0xF8 ^ 0xB0], new Object["".length()]);
            }
            if (array.length == (0x47 ^ 0x43)) {
                if (lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x51 ^ 0x18])) {
                    final String s = CommandScoreboard.I[0x74 ^ 0x3E];
                    final Object[] array2 = new Object["  ".length()];
                    array2["".length()] = lowerCase;
                    array2[" ".length()] = CommandBase.joinNiceStringFromCollection(EnumChatFormatting.getValidValues((boolean)(" ".length() != 0), (boolean)("".length() != 0)));
                    throw new WrongUsageException(s, array2);
                }
                if (lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x71 ^ 0x3A]) || lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x8B ^ 0xC7])) {
                    final String s2 = CommandScoreboard.I[0xED ^ 0xBC];
                    final Object[] array3 = new Object["  ".length()];
                    array3["".length()] = lowerCase;
                    final int length = " ".length();
                    final String[] array4 = new String["  ".length()];
                    array4["".length()] = CommandScoreboard.I[0x95 ^ 0xC7];
                    array4[" ".length()] = CommandScoreboard.I[0x6B ^ 0x38];
                    array3[length] = CommandBase.joinNiceStringFromCollection(Arrays.asList(array4));
                    throw new WrongUsageException(s2, array3);
                }
                if (!lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x56 ^ 0x1B]) && !lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x29 ^ 0x67])) {
                    throw new WrongUsageException(CommandScoreboard.I[0x4E ^ 0x1], new Object["".length()]);
                }
                final String s3 = CommandScoreboard.I[0x6E ^ 0x3E];
                final Object[] array5 = new Object["  ".length()];
                array5["".length()] = lowerCase;
                array5[" ".length()] = CommandBase.joinNiceString(Team.EnumVisible.func_178825_a());
                throw new WrongUsageException(s3, array5);
            }
            else {
                final String s4 = array[n];
                if (lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x71 ^ 0x25])) {
                    final EnumChatFormatting valueByName = EnumChatFormatting.getValueByName(s4);
                    if (valueByName == null || valueByName.isFancyStyling()) {
                        final String s5 = CommandScoreboard.I[0x8 ^ 0x5D];
                        final Object[] array6 = new Object["  ".length()];
                        array6["".length()] = lowerCase;
                        array6[" ".length()] = CommandBase.joinNiceStringFromCollection(EnumChatFormatting.getValidValues((boolean)(" ".length() != 0), (boolean)("".length() != 0)));
                        throw new WrongUsageException(s5, array6);
                    }
                    team.setChatFormat(valueByName);
                    team.setNamePrefix(valueByName.toString());
                    team.setNameSuffix(EnumChatFormatting.RESET.toString());
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                }
                else if (lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x3 ^ 0x55])) {
                    if (!s4.equalsIgnoreCase(CommandScoreboard.I[0x1B ^ 0x4C]) && !s4.equalsIgnoreCase(CommandScoreboard.I[0x15 ^ 0x4D])) {
                        final String s6 = CommandScoreboard.I[0x28 ^ 0x71];
                        final Object[] array7 = new Object["  ".length()];
                        array7["".length()] = lowerCase;
                        final int length2 = " ".length();
                        final String[] array8 = new String["  ".length()];
                        array8["".length()] = CommandScoreboard.I[0x64 ^ 0x3E];
                        array8[" ".length()] = CommandScoreboard.I[0x6D ^ 0x36];
                        array7[length2] = CommandBase.joinNiceStringFromCollection(Arrays.asList(array8));
                        throw new WrongUsageException(s6, array7);
                    }
                    team.setAllowFriendlyFire(s4.equalsIgnoreCase(CommandScoreboard.I[0x9C ^ 0xC0]));
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (lowerCase.equalsIgnoreCase(CommandScoreboard.I[0xCF ^ 0x92])) {
                    if (!s4.equalsIgnoreCase(CommandScoreboard.I[0xF2 ^ 0xAC]) && !s4.equalsIgnoreCase(CommandScoreboard.I[0x76 ^ 0x29])) {
                        final String s7 = CommandScoreboard.I[0x23 ^ 0x43];
                        final Object[] array9 = new Object["  ".length()];
                        array9["".length()] = lowerCase;
                        final int length3 = " ".length();
                        final String[] array10 = new String["  ".length()];
                        array10["".length()] = CommandScoreboard.I[0x5A ^ 0x3B];
                        array10[" ".length()] = CommandScoreboard.I[0x7C ^ 0x1E];
                        array9[length3] = CommandBase.joinNiceStringFromCollection(Arrays.asList(array10));
                        throw new WrongUsageException(s7, array9);
                    }
                    team.setSeeFriendlyInvisiblesEnabled(s4.equalsIgnoreCase(CommandScoreboard.I[0xDF ^ 0xBC]));
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else if (lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x73 ^ 0x17])) {
                    final Team.EnumVisible func_178824_a = Team.EnumVisible.func_178824_a(s4);
                    if (func_178824_a == null) {
                        final String s8 = CommandScoreboard.I[0xA2 ^ 0xC7];
                        final Object[] array11 = new Object["  ".length()];
                        array11["".length()] = lowerCase;
                        array11[" ".length()] = CommandBase.joinNiceString(Team.EnumVisible.func_178825_a());
                        throw new WrongUsageException(s8, array11);
                    }
                    team.setNameTagVisibility(func_178824_a);
                    "".length();
                    if (4 < 4) {
                        throw null;
                    }
                }
                else if (lowerCase.equalsIgnoreCase(CommandScoreboard.I[0x53 ^ 0x35])) {
                    final Team.EnumVisible func_178824_a2 = Team.EnumVisible.func_178824_a(s4);
                    if (func_178824_a2 == null) {
                        final String s9 = CommandScoreboard.I[0xD2 ^ 0xB5];
                        final Object[] array12 = new Object["  ".length()];
                        array12["".length()] = lowerCase;
                        array12[" ".length()] = CommandBase.joinNiceString(Team.EnumVisible.func_178825_a());
                        throw new WrongUsageException(s9, array12);
                    }
                    team.setDeathMessageVisibility(func_178824_a2);
                }
                final String s10 = CommandScoreboard.I[0xF2 ^ 0x9A];
                final Object[] array13 = new Object["   ".length()];
                array13["".length()] = lowerCase;
                array13[" ".length()] = team.getRegisteredName();
                array13["  ".length()] = s4;
                CommandBase.notifyOperators(commandSender, this, s10, array13);
            }
        }
    }
    
    protected void func_175778_p(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String entityName = CommandBase.getEntityName(commandSender, array[n++]);
        final ScoreObjective objective = this.getObjective(array[n++], " ".length() != 0);
        final String s = array[n++];
        final String entityName2 = CommandBase.getEntityName(commandSender, array[n++]);
        final ScoreObjective objective2 = this.getObjective(array[n], "".length() != 0);
        if (entityName.length() > (0xAB ^ 0x83)) {
            final String s2 = CommandScoreboard.I[97 + 114 - 182 + 120];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = entityName;
            array2[" ".length()] = (0x8 ^ 0x20);
            throw new SyntaxErrorException(s2, array2);
        }
        if (entityName2.length() > (0x6 ^ 0x2E)) {
            final String s3 = CommandScoreboard.I[103 + 90 - 180 + 137];
            final Object[] array3 = new Object["  ".length()];
            array3["".length()] = entityName2;
            array3[" ".length()] = (0x21 ^ 0x9);
            throw new SyntaxErrorException(s3, array3);
        }
        final Score valueFromObjective = scoreboard.getValueFromObjective(entityName, objective);
        if (!scoreboard.entityHasObjective(entityName2, objective2)) {
            final String s4 = CommandScoreboard.I[132 + 81 - 188 + 126];
            final Object[] array4 = new Object["  ".length()];
            array4["".length()] = objective2.getName();
            array4[" ".length()] = entityName2;
            throw new CommandException(s4, array4);
        }
        final Score valueFromObjective2 = scoreboard.getValueFromObjective(entityName2, objective2);
        if (s.equals(CommandScoreboard.I[108 + 64 - 94 + 74])) {
            valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() + valueFromObjective2.getScorePoints());
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else if (s.equals(CommandScoreboard.I[13 + 103 - 11 + 48])) {
            valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() - valueFromObjective2.getScorePoints());
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else if (s.equals(CommandScoreboard.I[131 + 48 - 176 + 151])) {
            valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() * valueFromObjective2.getScorePoints());
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else if (s.equals(CommandScoreboard.I[118 + 98 - 61 + 0])) {
            if (valueFromObjective2.getScorePoints() != 0) {
                valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() / valueFromObjective2.getScorePoints());
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
        }
        else if (s.equals(CommandScoreboard.I[144 + 132 - 221 + 101])) {
            if (valueFromObjective2.getScorePoints() != 0) {
                valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() % valueFromObjective2.getScorePoints());
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
        }
        else if (s.equals(CommandScoreboard.I[84 + 83 - 119 + 109])) {
            valueFromObjective.setScorePoints(valueFromObjective2.getScorePoints());
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else if (s.equals(CommandScoreboard.I[77 + 29 - 76 + 128])) {
            valueFromObjective.setScorePoints(Math.min(valueFromObjective.getScorePoints(), valueFromObjective2.getScorePoints()));
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (s.equals(CommandScoreboard.I[140 + 133 - 267 + 153])) {
            valueFromObjective.setScorePoints(Math.max(valueFromObjective.getScorePoints(), valueFromObjective2.getScorePoints()));
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            if (!s.equals(CommandScoreboard.I[53 + 92 - 116 + 131])) {
                final String s5 = CommandScoreboard.I[126 + 121 - 191 + 105];
                final Object[] array5 = new Object[" ".length()];
                array5["".length()] = s;
                throw new CommandException(s5, array5);
            }
            final int scorePoints = valueFromObjective.getScorePoints();
            valueFromObjective.setScorePoints(valueFromObjective2.getScorePoints());
            valueFromObjective2.setScorePoints(scorePoints);
        }
        CommandBase.notifyOperators(commandSender, this, CommandScoreboard.I[157 + 76 - 129 + 58], new Object["".length()]);
    }
    
    protected void func_175779_n(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String playerName = CommandBase.getPlayerName(commandSender, array[n++]);
        if (playerName.length() > (0x16 ^ 0x3E)) {
            final String s = CommandScoreboard.I[130 + 51 - 70 + 29];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = playerName;
            array2[" ".length()] = (0x18 ^ 0x30);
            throw new SyntaxErrorException(s, array2);
        }
        final ScoreObjective objective = this.getObjective(array[n], "".length() != 0);
        if (objective.getCriteria() != IScoreObjectiveCriteria.TRIGGER) {
            final String s2 = CommandScoreboard.I[64 + 10 - 8 + 75];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = objective.getName();
            throw new CommandException(s2, array3);
        }
        scoreboard.getValueFromObjective(playerName, objective).setLocked("".length() != 0);
        final String s3 = CommandScoreboard.I[101 + 93 - 90 + 38];
        final Object[] array4 = new Object["  ".length()];
        array4["".length()] = objective.getName();
        array4[" ".length()] = playerName;
        CommandBase.notifyOperators(commandSender, this, s3, array4);
    }
    
    protected Scoreboard getScoreboard() {
        return MinecraftServer.getServer().worldServerForDimension("".length()).getScoreboard();
    }
    
    protected List<String> func_175782_e() {
        final Collection<ScoreObjective> scoreObjectives = this.getScoreboard().getScoreObjectives();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<ScoreObjective> iterator = scoreObjectives.iterator();
        "".length();
        if (4 < 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ScoreObjective scoreObjective = iterator.next();
            if (scoreObjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
                arrayList.add(scoreObjective.getName());
            }
        }
        return (List<String>)arrayList;
    }
    
    protected void func_175781_o(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String entityName = CommandBase.getEntityName(commandSender, array[n++]);
        if (entityName.length() > (0x37 ^ 0x1F)) {
            final String s = CommandScoreboard.I[1 + 132 - 95 + 105];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = entityName;
            array2[" ".length()] = (0xEE ^ 0xC6);
            throw new SyntaxErrorException(s, array2);
        }
        final ScoreObjective objective = this.getObjective(array[n++], "".length() != 0);
        if (!scoreboard.entityHasObjective(entityName, objective)) {
            final String s2 = CommandScoreboard.I[83 + 92 - 43 + 12];
            final Object[] array3 = new Object["  ".length()];
            array3["".length()] = objective.getName();
            array3[" ".length()] = entityName;
            throw new CommandException(s2, array3);
        }
        int int1;
        if (array[n].equals(CommandScoreboard.I[96 + 50 - 45 + 44])) {
            int1 = -"".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            int1 = CommandBase.parseInt(array[n]);
        }
        final int n2 = int1;
        int int2;
        if (++n < array.length && !array[n].equals(CommandScoreboard.I[62 + 20 - 14 + 78])) {
            int2 = CommandBase.parseInt(array[n], n2);
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            int2 = 2095049634 + 1778032672 - 1780369812 + 54771153;
        }
        final int n3 = int2;
        final Score valueFromObjective = scoreboard.getValueFromObjective(entityName, objective);
        if (valueFromObjective.getScorePoints() < n2 || valueFromObjective.getScorePoints() > n3) {
            final String s3 = CommandScoreboard.I[32 + 103 + 8 + 5];
            final Object[] array4 = new Object["   ".length()];
            array4["".length()] = valueFromObjective.getScorePoints();
            array4[" ".length()] = n2;
            array4["  ".length()] = n3;
            throw new CommandException(s3, array4);
        }
        final String s4 = CommandScoreboard.I[143 + 37 - 135 + 102];
        final Object[] array5 = new Object["   ".length()];
        array5["".length()] = valueFromObjective.getScorePoints();
        array5[" ".length()] = n2;
        array5["  ".length()] = n3;
        CommandBase.notifyOperators(commandSender, this, s4, array5);
        "".length();
        if (0 == 4) {
            throw null;
        }
    }
    
    protected void addObjective(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final String s = array[n++];
        final String s2 = array[n++];
        final Scoreboard scoreboard = this.getScoreboard();
        final IScoreObjectiveCriteria scoreObjectiveCriteria = IScoreObjectiveCriteria.INSTANCES.get(s2);
        if (scoreObjectiveCriteria == null) {
            final String s3 = CommandScoreboard.I[0x6F ^ 0x57];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = s2;
            throw new WrongUsageException(s3, array2);
        }
        if (scoreboard.getObjective(s) != null) {
            final String s4 = CommandScoreboard.I[0x27 ^ 0x1E];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = s;
            throw new CommandException(s4, array3);
        }
        if (s.length() > (0x2C ^ 0x3C)) {
            final String s5 = CommandScoreboard.I[0x2D ^ 0x17];
            final Object[] array4 = new Object["  ".length()];
            array4["".length()] = s;
            array4[" ".length()] = (0x98 ^ 0x88);
            throw new SyntaxErrorException(s5, array4);
        }
        if (s.length() == 0) {
            throw new WrongUsageException(CommandScoreboard.I[0x4A ^ 0x71], new Object["".length()]);
        }
        if (array.length > n) {
            final String unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, n).getUnformattedText();
            if (unformattedText.length() > (0x1C ^ 0x3C)) {
                final String s6 = CommandScoreboard.I[0xFD ^ 0xC1];
                final Object[] array5 = new Object["  ".length()];
                array5["".length()] = unformattedText;
                array5[" ".length()] = (0xBC ^ 0x9C);
                throw new SyntaxErrorException(s6, array5);
            }
            if (unformattedText.length() > 0) {
                scoreboard.addScoreObjective(s, scoreObjectiveCriteria).setDisplayName(unformattedText);
                "".length();
                if (0 == -1) {
                    throw null;
                }
            }
            else {
                scoreboard.addScoreObjective(s, scoreObjectiveCriteria);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
        }
        else {
            scoreboard.addScoreObjective(s, scoreObjectiveCriteria);
        }
        final String s7 = CommandScoreboard.I[0x42 ^ 0x7F];
        final Object[] array6 = new Object[" ".length()];
        array6["".length()] = s;
        CommandBase.notifyOperators(commandSender, this, s7, array6);
    }
    
    protected void emptyTeam(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final ScorePlayerTeam team = this.getTeam(array[n]);
        if (team != null) {
            final ArrayList arrayList = Lists.newArrayList((Iterable)team.getMembershipCollection());
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, arrayList.size());
            if (arrayList.isEmpty()) {
                final String s = CommandScoreboard.I[0xEB ^ 0x9E];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = team.getRegisteredName();
                throw new CommandException(s, array2);
            }
            final Iterator<String> iterator = (Iterator<String>)arrayList.iterator();
            "".length();
            if (3 == -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                scoreboard.removePlayerFromTeam(iterator.next(), team);
            }
            final String s2 = CommandScoreboard.I[0x7A ^ 0xC];
            final Object[] array3 = new Object["  ".length()];
            array3["".length()] = arrayList.size();
            array3[" ".length()] = team.getRegisteredName();
            CommandBase.notifyOperators(commandSender, this, s2, array3);
        }
    }
    
    private boolean func_175780_b(final ICommandSender commandSender, final String[] array) throws CommandException {
        int n = -" ".length();
        int i = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < array.length) {
            if (this.isUsernameIndex(array, i) && CommandScoreboard.I[0x8B ^ 0xB9].equals(array[i])) {
                if (n >= 0) {
                    throw new CommandException(CommandScoreboard.I[0x67 ^ 0x54], new Object["".length()]);
                }
                n = i;
            }
            ++i;
        }
        if (n < 0) {
            return "".length() != 0;
        }
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.getScoreboard().getObjectiveNames());
        final String s = array[n];
        final ArrayList arrayList2 = Lists.newArrayList();
        final Iterator<String> iterator = (Iterator<String>)arrayList.iterator();
        "".length();
        if (3 == 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s2 = iterator.next();
            array[n] = s2;
            try {
                this.processCommand(commandSender, array);
                arrayList2.add(s2);
                "".length();
                if (4 < 4) {
                    throw null;
                }
                continue;
            }
            catch (CommandException ex) {
                final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(ex.getMessage(), ex.getErrorObjects());
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                commandSender.addChatMessage(chatComponentTranslation);
            }
        }
        array[n] = s;
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, arrayList2.size());
        if (arrayList2.size() == 0) {
            throw new WrongUsageException(CommandScoreboard.I[0x85 ^ 0xB1], new Object["".length()]);
        }
        return " ".length() != 0;
    }
    
    protected void addTeam(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final String s = array[n++];
        final Scoreboard scoreboard = this.getScoreboard();
        if (scoreboard.getTeam(s) != null) {
            final String s2 = CommandScoreboard.I[0x97 ^ 0xA9];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = s;
            throw new CommandException(s2, array2);
        }
        if (s.length() > (0xBE ^ 0xAE)) {
            final String s3 = CommandScoreboard.I[0xFF ^ 0xC0];
            final Object[] array3 = new Object["  ".length()];
            array3["".length()] = s;
            array3[" ".length()] = (0x92 ^ 0x82);
            throw new SyntaxErrorException(s3, array3);
        }
        if (s.length() == 0) {
            throw new WrongUsageException(CommandScoreboard.I[0x31 ^ 0x71], new Object["".length()]);
        }
        if (array.length > n) {
            final String unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, n).getUnformattedText();
            if (unformattedText.length() > (0x9D ^ 0xBD)) {
                final String s4 = CommandScoreboard.I[0xC9 ^ 0x88];
                final Object[] array4 = new Object["  ".length()];
                array4["".length()] = unformattedText;
                array4[" ".length()] = (0x36 ^ 0x16);
                throw new SyntaxErrorException(s4, array4);
            }
            if (unformattedText.length() > 0) {
                scoreboard.createTeam(s).setTeamName(unformattedText);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                scoreboard.createTeam(s);
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
        }
        else {
            scoreboard.createTeam(s);
        }
        final String s5 = CommandScoreboard.I[0xFA ^ 0xB8];
        final Object[] array5 = new Object[" ".length()];
        array5["".length()] = s;
        CommandBase.notifyOperators(commandSender, this, s5, array5);
    }
    
    protected void listObjectives(final ICommandSender commandSender) throws CommandException {
        final Collection<ScoreObjective> scoreObjectives = this.getScoreboard().getScoreObjectives();
        if (scoreObjectives.size() <= 0) {
            throw new CommandException(CommandScoreboard.I[0x76 ^ 0xE], new Object["".length()]);
        }
        final String s = CommandScoreboard.I[0x4E ^ 0x37];
        final Object[] array = new Object[" ".length()];
        array["".length()] = scoreObjectives.size();
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(s, array);
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        commandSender.addChatMessage(chatComponentTranslation);
        final Iterator<ScoreObjective> iterator = scoreObjectives.iterator();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ScoreObjective scoreObjective = iterator.next();
            final String s2 = CommandScoreboard.I[0xC4 ^ 0xBE];
            final Object[] array2 = new Object["   ".length()];
            array2["".length()] = scoreObjective.getName();
            array2[" ".length()] = scoreObjective.getDisplayName();
            array2["  ".length()] = scoreObjective.getCriteria().getName();
            commandSender.addChatMessage(new ChatComponentTranslation(s2, array2));
        }
    }
    
    protected void removeObjective(final ICommandSender commandSender, final String s) throws CommandException {
        this.getScoreboard().removeObjective(this.getObjective(s, (boolean)("".length() != 0)));
        final String s2 = CommandScoreboard.I[0xCC ^ 0xBB];
        final Object[] array = new Object[" ".length()];
        array["".length()] = s;
        CommandBase.notifyOperators(commandSender, this, s2, array);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandScoreboard.I[" ".length()];
    }
    
    protected void listPlayers(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        if (array.length > n) {
            final String entityName = CommandBase.getEntityName(commandSender, array[n]);
            final Map<ScoreObjective, Score> objectivesForEntity = scoreboard.getObjectivesForEntity(entityName);
            commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, objectivesForEntity.size());
            if (objectivesForEntity.size() <= 0) {
                final String s = CommandScoreboard.I[0xD8 ^ 0xA6];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = entityName;
                throw new CommandException(s, array2);
            }
            final String s2 = CommandScoreboard.I[61 + 31 - 2 + 37];
            final Object[] array3 = new Object["  ".length()];
            array3["".length()] = objectivesForEntity.size();
            array3[" ".length()] = entityName;
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(s2, array3);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation);
            final Iterator<Score> iterator = objectivesForEntity.values().iterator();
            "".length();
            if (2 >= 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Score score = iterator.next();
                final String s3 = CommandScoreboard.I[103 + 78 - 134 + 81];
                final Object[] array4 = new Object["   ".length()];
                array4["".length()] = score.getScorePoints();
                array4[" ".length()] = score.getObjective().getDisplayName();
                array4["  ".length()] = score.getObjective().getName();
                commandSender.addChatMessage(new ChatComponentTranslation(s3, array4));
            }
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            final Collection<String> objectiveNames = scoreboard.getObjectiveNames();
            commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, objectiveNames.size());
            if (objectiveNames.size() <= 0) {
                throw new CommandException(CommandScoreboard.I[50 + 47 - 0 + 32], new Object["".length()]);
            }
            final String s4 = CommandScoreboard.I[47 + 47 + 7 + 29];
            final Object[] array5 = new Object[" ".length()];
            array5["".length()] = objectiveNames.size();
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation(s4, array5);
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation2);
            commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(objectiveNames.toArray())));
        }
    }
    
    @Override
    public String getCommandName() {
        return CommandScoreboard.I["".length()];
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        int n2;
        if (!array["".length()].equalsIgnoreCase(CommandScoreboard.I[37 + 126 - 96 + 160])) {
            if (array["".length()].equalsIgnoreCase(CommandScoreboard.I[27 + 77 + 113 + 11])) {
                if (n == "  ".length()) {
                    n2 = " ".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    n2 = "".length();
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                }
            }
            else {
                n2 = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
        }
        else if (array.length > " ".length() && array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[183 + 170 - 182 + 58])) {
            if (n != "  ".length() && n != (0x89 ^ 0x8C)) {
                n2 = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                n2 = " ".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
        }
        else if (n == "  ".length()) {
            n2 = " ".length();
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2 != 0;
    }
    
    protected ScorePlayerTeam getTeam(final String s) throws CommandException {
        final ScorePlayerTeam team = this.getScoreboard().getTeam(s);
        if (team == null) {
            final String s2 = CommandScoreboard.I[0x2 ^ 0x35];
            final Object[] array = new Object[" ".length()];
            array["".length()] = s;
            throw new CommandException(s2, array);
        }
        return team;
    }
    
    protected void joinTeam(final ICommandSender commandSender, final String[] array, int i) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String s = array[i++];
        final HashSet hashSet = Sets.newHashSet();
        final HashSet hashSet2 = Sets.newHashSet();
        if (commandSender instanceof EntityPlayer && i == array.length) {
            final String name = CommandBase.getCommandSenderAsPlayer(commandSender).getName();
            if (scoreboard.addPlayerToTeam(name, s)) {
                hashSet.add(name);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                hashSet2.add(name);
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
        }
        else {
            while (i < array.length) {
                final String s2 = array[i++];
                if (s2.startsWith(CommandScoreboard.I[0xE1 ^ 0x8E])) {
                    final Iterator<Entity> iterator = CommandBase.func_175763_c(commandSender, s2).iterator();
                    "".length();
                    if (1 < 1) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final String entityName = CommandBase.getEntityName(commandSender, iterator.next().getUniqueID().toString());
                        if (scoreboard.addPlayerToTeam(entityName, s)) {
                            hashSet.add(entityName);
                            "".length();
                            if (-1 >= 1) {
                                throw null;
                            }
                            continue;
                        }
                        else {
                            hashSet2.add(entityName);
                        }
                    }
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                    continue;
                }
                else {
                    final String entityName2 = CommandBase.getEntityName(commandSender, s2);
                    if (scoreboard.addPlayerToTeam(entityName2, s)) {
                        hashSet.add(entityName2);
                        "".length();
                        if (4 <= 0) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        hashSet2.add(entityName2);
                    }
                }
            }
        }
        if (!hashSet.isEmpty()) {
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, hashSet.size());
            final String s3 = CommandScoreboard.I[0x3A ^ 0x4A];
            final Object[] array2 = new Object["   ".length()];
            array2["".length()] = hashSet.size();
            array2[" ".length()] = s;
            array2["  ".length()] = CommandBase.joinNiceString(hashSet.toArray(new String[hashSet.size()]));
            CommandBase.notifyOperators(commandSender, this, s3, array2);
        }
        if (!hashSet2.isEmpty()) {
            final String s4 = CommandScoreboard.I[0x1E ^ 0x6F];
            final Object[] array3 = new Object["   ".length()];
            array3["".length()] = hashSet2.size();
            array3[" ".length()] = s;
            array3["  ".length()] = CommandBase.joinNiceString(hashSet2.toArray(new String[hashSet2.size()]));
            throw new CommandException(s4, array3);
        }
    }
    
    protected void removeTeam(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final ScorePlayerTeam team = this.getTeam(array[n]);
        if (team != null) {
            scoreboard.removeTeam(team);
            final String s = CommandScoreboard.I[0x5C ^ 0x35];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = team.getRegisteredName();
            CommandBase.notifyOperators(commandSender, this, s, array2);
        }
    }
    
    static {
        I();
    }
    
    protected void setObjectiveDisplay(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String s = array[n++];
        final int objectiveDisplaySlotNumber = Scoreboard.getObjectiveDisplaySlotNumber(s);
        ScoreObjective objective = null;
        if (array.length == (0xA2 ^ 0xA6)) {
            objective = this.getObjective(array[n], "".length() != 0);
        }
        if (objectiveDisplaySlotNumber < 0) {
            final String s2 = CommandScoreboard.I[0xB9 ^ 0xC2];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = s;
            throw new CommandException(s2, array2);
        }
        scoreboard.setObjectiveInDisplaySlot(objectiveDisplaySlotNumber, objective);
        if (objective != null) {
            final String s3 = CommandScoreboard.I[0xDD ^ 0xA1];
            final Object[] array3 = new Object["  ".length()];
            array3["".length()] = Scoreboard.getObjectiveDisplaySlot(objectiveDisplaySlotNumber);
            array3[" ".length()] = objective.getName();
            CommandBase.notifyOperators(commandSender, this, s3, array3);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            final String s4 = CommandScoreboard.I[0x53 ^ 0x2E];
            final Object[] array4 = new Object[" ".length()];
            array4["".length()] = Scoreboard.getObjectiveDisplaySlot(objectiveDisplaySlotNumber);
            CommandBase.notifyOperators(commandSender, this, s4, array4);
        }
    }
    
    protected List<String> func_147184_a(final boolean b) {
        final Collection<ScoreObjective> scoreObjectives = this.getScoreboard().getScoreObjectives();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<ScoreObjective> iterator = scoreObjectives.iterator();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ScoreObjective scoreObjective = iterator.next();
            if (!b || !scoreObjective.getCriteria().isReadOnly()) {
                arrayList.add(scoreObjective.getName());
            }
        }
        return (List<String>)arrayList;
    }
    
    protected void leaveTeam(final ICommandSender commandSender, final String[] array, int i) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final HashSet hashSet = Sets.newHashSet();
        final HashSet hashSet2 = Sets.newHashSet();
        if (commandSender instanceof EntityPlayer && i == array.length) {
            final String name = CommandBase.getCommandSenderAsPlayer(commandSender).getName();
            if (scoreboard.removePlayerFromTeams(name)) {
                hashSet.add(name);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                hashSet2.add(name);
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
        }
        else {
            while (i < array.length) {
                final String s = array[i++];
                if (s.startsWith(CommandScoreboard.I[0x5E ^ 0x2C])) {
                    final Iterator<Entity> iterator = CommandBase.func_175763_c(commandSender, s).iterator();
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final String entityName = CommandBase.getEntityName(commandSender, iterator.next().getUniqueID().toString());
                        if (scoreboard.removePlayerFromTeams(entityName)) {
                            hashSet.add(entityName);
                            "".length();
                            if (0 >= 1) {
                                throw null;
                            }
                            continue;
                        }
                        else {
                            hashSet2.add(entityName);
                        }
                    }
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                    continue;
                }
                else {
                    final String entityName2 = CommandBase.getEntityName(commandSender, s);
                    if (scoreboard.removePlayerFromTeams(entityName2)) {
                        hashSet.add(entityName2);
                        "".length();
                        if (-1 == 0) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        hashSet2.add(entityName2);
                    }
                }
            }
        }
        if (!hashSet.isEmpty()) {
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, hashSet.size());
            final String s2 = CommandScoreboard.I[0xD6 ^ 0xA5];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = hashSet.size();
            array2[" ".length()] = CommandBase.joinNiceString(hashSet.toArray(new String[hashSet.size()]));
            CommandBase.notifyOperators(commandSender, this, s2, array2);
        }
        if (!hashSet2.isEmpty()) {
            final String s3 = CommandScoreboard.I[0x51 ^ 0x25];
            final Object[] array3 = new Object["  ".length()];
            array3["".length()] = hashSet2.size();
            array3[" ".length()] = CommandBase.joinNiceString(hashSet2.toArray(new String[hashSet2.size()]));
            throw new CommandException(s3, array3);
        }
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == " ".length()) {
            final String[] array2 = new String["   ".length()];
            array2["".length()] = CommandScoreboard.I[29 + 52 + 11 + 71];
            array2[" ".length()] = CommandScoreboard.I[109 + 17 - 111 + 149];
            array2["  ".length()] = CommandScoreboard.I[6 + 48 + 53 + 58];
            return CommandBase.getListOfStringsMatchingLastWord(array, array2);
        }
        if (array["".length()].equalsIgnoreCase(CommandScoreboard.I[147 + 26 - 165 + 158])) {
            if (array.length == "  ".length()) {
                final String[] array3 = new String[0xD ^ 0x9];
                array3["".length()] = CommandScoreboard.I[1 + 112 - 45 + 99];
                array3[" ".length()] = CommandScoreboard.I[121 + 153 - 168 + 62];
                array3["  ".length()] = CommandScoreboard.I[94 + 61 - 111 + 125];
                array3["   ".length()] = CommandScoreboard.I[156 + 116 - 125 + 23];
                return CommandBase.getListOfStringsMatchingLastWord(array, array3);
            }
            if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[117 + 37 - 93 + 110])) {
                if (array.length == (0x85 ^ 0x81)) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, IScoreObjectiveCriteria.INSTANCES.keySet());
                }
            }
            else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[40 + 105 - 125 + 152])) {
                if (array.length == "   ".length()) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, this.func_147184_a((boolean)("".length() != 0)));
                }
            }
            else if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[16 + 148 - 143 + 152])) {
                if (array.length == "   ".length()) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, Scoreboard.getDisplaySlotStrings());
                }
                if (array.length == (0x2 ^ 0x6)) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, this.func_147184_a((boolean)("".length() != 0)));
                }
            }
        }
        else if (array["".length()].equalsIgnoreCase(CommandScoreboard.I[110 + 88 - 71 + 47])) {
            if (array.length == "  ".length()) {
                final String[] array4 = new String[0xC9 ^ 0xC1];
                array4["".length()] = CommandScoreboard.I[53 + 161 - 108 + 69];
                array4[" ".length()] = CommandScoreboard.I[32 + 25 + 116 + 3];
                array4["  ".length()] = CommandScoreboard.I[164 + 92 - 183 + 104];
                array4["   ".length()] = CommandScoreboard.I[50 + 10 - 33 + 151];
                array4[0x51 ^ 0x55] = CommandScoreboard.I[56 + 85 - 54 + 92];
                array4[0x39 ^ 0x3C] = CommandScoreboard.I[163 + 107 - 164 + 74];
                array4[0x22 ^ 0x24] = CommandScoreboard.I[86 + 171 - 149 + 73];
                array4[0x7B ^ 0x7C] = CommandScoreboard.I[87 + 166 - 252 + 181];
                return CommandBase.getListOfStringsMatchingLastWord(array, array4);
            }
            if (!array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[109 + 28 - 82 + 128]) && !array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[158 + 102 - 109 + 33]) && !array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[112 + 166 - 256 + 163]) && !array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[18 + 162 - 119 + 125])) {
                if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[85 + 74 - 148 + 176])) {
                    if (array.length == "   ".length()) {
                        return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                    }
                    if (array.length == (0x1E ^ 0x1A)) {
                        return CommandBase.getListOfStringsMatchingLastWord(array, this.func_175782_e());
                    }
                }
                else if (!array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[10 + 108 - 3 + 73]) && !array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[140 + 184 - 238 + 103])) {
                    if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[155 + 186 - 313 + 162])) {
                        if (array.length == "   ".length()) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, this.getScoreboard().getObjectiveNames());
                        }
                        if (array.length == (0x56 ^ 0x52)) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, this.func_147184_a((boolean)(" ".length() != 0)));
                        }
                        if (array.length == (0x39 ^ 0x3C)) {
                            final String[] array5 = new String[0xCD ^ 0xC4];
                            array5["".length()] = CommandScoreboard.I[80 + 154 - 50 + 7];
                            array5[" ".length()] = CommandScoreboard.I[57 + 177 - 121 + 79];
                            array5["  ".length()] = CommandScoreboard.I[167 + 72 - 53 + 7];
                            array5["   ".length()] = CommandScoreboard.I[63 + 28 + 93 + 10];
                            array5[0xAA ^ 0xAE] = CommandScoreboard.I[116 + 41 - 93 + 131];
                            array5[0x61 ^ 0x64] = CommandScoreboard.I[15 + 101 - 74 + 154];
                            array5[0xD ^ 0xB] = CommandScoreboard.I[145 + 159 - 203 + 96];
                            array5[0x10 ^ 0x17] = CommandScoreboard.I[185 + 137 - 140 + 16];
                            array5[0x25 ^ 0x2D] = CommandScoreboard.I[133 + 141 - 227 + 152];
                            return CommandBase.getListOfStringsMatchingLastWord(array, array5);
                        }
                        if (array.length == (0x6E ^ 0x68)) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                        }
                        if (array.length == (0x3A ^ 0x3D)) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, this.func_147184_a((boolean)("".length() != 0)));
                        }
                    }
                }
                else {
                    if (array.length == "   ".length()) {
                        return CommandBase.getListOfStringsMatchingLastWord(array, this.getScoreboard().getObjectiveNames());
                    }
                    if (array.length == (0x2F ^ 0x2B) && array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[88 + 127 - 71 + 56])) {
                        return CommandBase.getListOfStringsMatchingLastWord(array, this.func_147184_a((boolean)("".length() != 0)));
                    }
                }
            }
            else {
                if (array.length == "   ".length()) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                }
                if (array.length == (0x7 ^ 0x3)) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, this.func_147184_a((boolean)(" ".length() != 0)));
                }
            }
        }
        else if (array["".length()].equalsIgnoreCase(CommandScoreboard.I[108 + 187 - 94 + 0])) {
            if (array.length == "  ".length()) {
                final String[] array6 = new String[0x73 ^ 0x74];
                array6["".length()] = CommandScoreboard.I[42 + 9 + 88 + 63];
                array6[" ".length()] = CommandScoreboard.I[72 + 9 - 8 + 130];
                array6["  ".length()] = CommandScoreboard.I[71 + 14 + 31 + 88];
                array6["   ".length()] = CommandScoreboard.I[82 + 136 - 75 + 62];
                array6[0x8 ^ 0xC] = CommandScoreboard.I[23 + 23 + 64 + 96];
                array6[0x3B ^ 0x3E] = CommandScoreboard.I[153 + 184 - 336 + 206];
                array6[0xB6 ^ 0xB0] = CommandScoreboard.I[158 + 42 - 110 + 118];
                return CommandBase.getListOfStringsMatchingLastWord(array, array6);
            }
            if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[131 + 204 - 301 + 175])) {
                if (array.length == "   ".length()) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, this.getScoreboard().getTeamNames());
                }
                if (array.length >= (0xAE ^ 0xAA)) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                }
            }
            else {
                if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[176 + 199 - 190 + 25])) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                }
                if (!array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[208 + 38 - 168 + 133]) && !array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[38 + 98 - 126 + 202]) && !array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[194 + 15 - 147 + 151])) {
                    if (array[" ".length()].equalsIgnoreCase(CommandScoreboard.I[23 + 138 + 44 + 9])) {
                        if (array.length == "   ".length()) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, this.getScoreboard().getTeamNames());
                        }
                        if (array.length == (0x6C ^ 0x68)) {
                            final String[] array7 = new String[0xB7 ^ 0xB2];
                            array7["".length()] = CommandScoreboard.I[93 + 102 - 187 + 207];
                            array7[" ".length()] = CommandScoreboard.I[77 + 186 - 105 + 58];
                            array7["  ".length()] = CommandScoreboard.I[91 + 169 - 216 + 173];
                            array7["   ".length()] = CommandScoreboard.I[65 + 139 - 78 + 92];
                            array7[0x1 ^ 0x5] = CommandScoreboard.I[196 + 84 - 261 + 200];
                            return CommandBase.getListOfStringsMatchingLastWord(array, array7);
                        }
                        if (array.length == (0xAE ^ 0xAB)) {
                            if (array["   ".length()].equalsIgnoreCase(CommandScoreboard.I[208 + 164 - 221 + 69])) {
                                return CommandBase.getListOfStringsMatchingLastWord(array, EnumChatFormatting.getValidValues((boolean)(" ".length() != 0), (boolean)("".length() != 0)));
                            }
                            if (array["   ".length()].equalsIgnoreCase(CommandScoreboard.I[158 + 134 - 265 + 194]) || array["   ".length()].equalsIgnoreCase(CommandScoreboard.I[32 + 104 - 1 + 87])) {
                                return CommandBase.getListOfStringsMatchingLastWord(array, Team.EnumVisible.func_178825_a());
                            }
                            if (array["   ".length()].equalsIgnoreCase(CommandScoreboard.I[111 + 125 - 21 + 8]) || array["   ".length()].equalsIgnoreCase(CommandScoreboard.I[139 + 36 - 150 + 199])) {
                                final String[] array8 = new String["  ".length()];
                                array8["".length()] = CommandScoreboard.I[63 + 223 - 247 + 186];
                                array8[" ".length()] = CommandScoreboard.I[47 + 18 - 22 + 183];
                                return CommandBase.getListOfStringsMatchingLastWord(array, array8);
                            }
                        }
                    }
                }
                else if (array.length == "   ".length()) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, this.getScoreboard().getTeamNames());
                }
            }
        }
        return null;
    }
}
