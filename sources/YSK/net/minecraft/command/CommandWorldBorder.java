package net.minecraft.command;

import net.minecraft.world.border.*;
import net.minecraft.util.*;
import net.minecraft.server.*;
import java.util.*;

public class CommandWorldBorder extends CommandBase
{
    private static final String[] I;
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < " ".length()) {
            throw new WrongUsageException(CommandWorldBorder.I["  ".length()], new Object["".length()]);
        }
        final WorldBorder worldBorder = this.getWorldBorder();
        if (array["".length()].equals(CommandWorldBorder.I["   ".length()])) {
            if (array.length != "  ".length() && array.length != "   ".length()) {
                throw new WrongUsageException(CommandWorldBorder.I[0x29 ^ 0x2D], new Object["".length()]);
            }
            final double targetSize = worldBorder.getTargetSize();
            final double double1 = CommandBase.parseDouble(array[" ".length()], 1.0, 6.0E7);
            long n;
            if (array.length > "  ".length()) {
                n = CommandBase.parseLong(array["  ".length()], 0L, 9223372036854775L) * 1000L;
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            else {
                n = 0L;
            }
            final long n2 = n;
            if (n2 > 0L) {
                worldBorder.setTransition(targetSize, double1, n2);
                if (targetSize > double1) {
                    final String s = CommandWorldBorder.I[0x70 ^ 0x75];
                    final Object[] array2 = new Object["   ".length()];
                    final int length = "".length();
                    final String s2 = CommandWorldBorder.I[0x63 ^ 0x65];
                    final Object[] array3 = new Object[" ".length()];
                    array3["".length()] = double1;
                    array2[length] = String.format(s2, array3);
                    final int length2 = " ".length();
                    final String s3 = CommandWorldBorder.I[0x32 ^ 0x35];
                    final Object[] array4 = new Object[" ".length()];
                    array4["".length()] = targetSize;
                    array2[length2] = String.format(s3, array4);
                    array2["  ".length()] = Long.toString(n2 / 1000L);
                    CommandBase.notifyOperators(commandSender, this, s, array2);
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                }
                else {
                    final String s4 = CommandWorldBorder.I[0x8F ^ 0x87];
                    final Object[] array5 = new Object["   ".length()];
                    final int length3 = "".length();
                    final String s5 = CommandWorldBorder.I[0x40 ^ 0x49];
                    final Object[] array6 = new Object[" ".length()];
                    array6["".length()] = double1;
                    array5[length3] = String.format(s5, array6);
                    final int length4 = " ".length();
                    final String s6 = CommandWorldBorder.I[0x4B ^ 0x41];
                    final Object[] array7 = new Object[" ".length()];
                    array7["".length()] = targetSize;
                    array5[length4] = String.format(s6, array7);
                    array5["  ".length()] = Long.toString(n2 / 1000L);
                    CommandBase.notifyOperators(commandSender, this, s4, array5);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
            }
            else {
                worldBorder.setTransition(double1);
                final String s7 = CommandWorldBorder.I[0x72 ^ 0x79];
                final Object[] array8 = new Object["  ".length()];
                final int length5 = "".length();
                final String s8 = CommandWorldBorder.I[0x3A ^ 0x36];
                final Object[] array9 = new Object[" ".length()];
                array9["".length()] = double1;
                array8[length5] = String.format(s8, array9);
                final int length6 = " ".length();
                final String s9 = CommandWorldBorder.I[0x21 ^ 0x2C];
                final Object[] array10 = new Object[" ".length()];
                array10["".length()] = targetSize;
                array8[length6] = String.format(s9, array10);
                CommandBase.notifyOperators(commandSender, this, s7, array8);
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
        }
        else if (array["".length()].equals(CommandWorldBorder.I[0x23 ^ 0x2D])) {
            if (array.length != "  ".length() && array.length != "   ".length()) {
                throw new WrongUsageException(CommandWorldBorder.I[0xB2 ^ 0xBD], new Object["".length()]);
            }
            final double diameter = worldBorder.getDiameter();
            final double transition = diameter + CommandBase.parseDouble(array[" ".length()], -diameter, 6.0E7 - diameter);
            final long timeUntilTarget = worldBorder.getTimeUntilTarget();
            long n3;
            if (array.length > "  ".length()) {
                n3 = CommandBase.parseLong(array["  ".length()], 0L, 9223372036854775L) * 1000L;
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else {
                n3 = 0L;
            }
            final long n4 = timeUntilTarget + n3;
            if (n4 > 0L) {
                worldBorder.setTransition(diameter, transition, n4);
                if (diameter > transition) {
                    final String s10 = CommandWorldBorder.I[0xB8 ^ 0xA8];
                    final Object[] array11 = new Object["   ".length()];
                    final int length7 = "".length();
                    final String s11 = CommandWorldBorder.I[0xB5 ^ 0xA4];
                    final Object[] array12 = new Object[" ".length()];
                    array12["".length()] = transition;
                    array11[length7] = String.format(s11, array12);
                    final int length8 = " ".length();
                    final String s12 = CommandWorldBorder.I[0x9 ^ 0x1B];
                    final Object[] array13 = new Object[" ".length()];
                    array13["".length()] = diameter;
                    array11[length8] = String.format(s12, array13);
                    array11["  ".length()] = Long.toString(n4 / 1000L);
                    CommandBase.notifyOperators(commandSender, this, s10, array11);
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
                else {
                    final String s13 = CommandWorldBorder.I[0xB8 ^ 0xAB];
                    final Object[] array14 = new Object["   ".length()];
                    final int length9 = "".length();
                    final String s14 = CommandWorldBorder.I[0x8F ^ 0x9B];
                    final Object[] array15 = new Object[" ".length()];
                    array15["".length()] = transition;
                    array14[length9] = String.format(s14, array15);
                    final int length10 = " ".length();
                    final String s15 = CommandWorldBorder.I[0xB ^ 0x1E];
                    final Object[] array16 = new Object[" ".length()];
                    array16["".length()] = diameter;
                    array14[length10] = String.format(s15, array16);
                    array14["  ".length()] = Long.toString(n4 / 1000L);
                    CommandBase.notifyOperators(commandSender, this, s13, array14);
                    "".length();
                    if (4 == 1) {
                        throw null;
                    }
                }
            }
            else {
                worldBorder.setTransition(transition);
                final String s16 = CommandWorldBorder.I[0x54 ^ 0x42];
                final Object[] array17 = new Object["  ".length()];
                final int length11 = "".length();
                final String s17 = CommandWorldBorder.I[0xB6 ^ 0xA1];
                final Object[] array18 = new Object[" ".length()];
                array18["".length()] = transition;
                array17[length11] = String.format(s17, array18);
                final int length12 = " ".length();
                final String s18 = CommandWorldBorder.I[0x1C ^ 0x4];
                final Object[] array19 = new Object[" ".length()];
                array19["".length()] = diameter;
                array17[length12] = String.format(s18, array19);
                CommandBase.notifyOperators(commandSender, this, s16, array17);
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
        }
        else if (array["".length()].equals(CommandWorldBorder.I[0x74 ^ 0x6D])) {
            if (array.length != "   ".length()) {
                throw new WrongUsageException(CommandWorldBorder.I[0xA5 ^ 0xBF], new Object["".length()]);
            }
            final BlockPos position = commandSender.getPosition();
            final double double2 = CommandBase.parseDouble(position.getX() + 0.5, array[" ".length()], " ".length() != 0);
            final double double3 = CommandBase.parseDouble(position.getZ() + 0.5, array["  ".length()], " ".length() != 0);
            worldBorder.setCenter(double2, double3);
            final String s19 = CommandWorldBorder.I[0x41 ^ 0x5A];
            final Object[] array20 = new Object["  ".length()];
            array20["".length()] = double2;
            array20[" ".length()] = double3;
            CommandBase.notifyOperators(commandSender, this, s19, array20);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else if (array["".length()].equals(CommandWorldBorder.I[0x1 ^ 0x1D])) {
            if (array.length < "  ".length()) {
                throw new WrongUsageException(CommandWorldBorder.I[0x7D ^ 0x60], new Object["".length()]);
            }
            if (array[" ".length()].equals(CommandWorldBorder.I[0x1B ^ 0x5])) {
                if (array.length != "   ".length()) {
                    throw new WrongUsageException(CommandWorldBorder.I[0xAF ^ 0xB0], new Object["".length()]);
                }
                final double double4 = CommandBase.parseDouble(array["  ".length()], 0.0);
                final double damageBuffer = worldBorder.getDamageBuffer();
                worldBorder.setDamageBuffer(double4);
                final String s20 = CommandWorldBorder.I[0x6A ^ 0x4A];
                final Object[] array21 = new Object["  ".length()];
                final int length13 = "".length();
                final String s21 = CommandWorldBorder.I[0x3B ^ 0x1A];
                final Object[] array22 = new Object[" ".length()];
                array22["".length()] = double4;
                array21[length13] = String.format(s21, array22);
                final int length14 = " ".length();
                final String s22 = CommandWorldBorder.I[0x68 ^ 0x4A];
                final Object[] array23 = new Object[" ".length()];
                array23["".length()] = damageBuffer;
                array21[length14] = String.format(s22, array23);
                CommandBase.notifyOperators(commandSender, this, s20, array21);
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            else if (array[" ".length()].equals(CommandWorldBorder.I[0x63 ^ 0x40])) {
                if (array.length != "   ".length()) {
                    throw new WrongUsageException(CommandWorldBorder.I[0xE5 ^ 0xC1], new Object["".length()]);
                }
                final double double5 = CommandBase.parseDouble(array["  ".length()], 0.0);
                final double damageAmount = worldBorder.getDamageAmount();
                worldBorder.setDamageAmount(double5);
                final String s23 = CommandWorldBorder.I[0x3E ^ 0x1B];
                final Object[] array24 = new Object["  ".length()];
                final int length15 = "".length();
                final String s24 = CommandWorldBorder.I[0x23 ^ 0x5];
                final Object[] array25 = new Object[" ".length()];
                array25["".length()] = double5;
                array24[length15] = String.format(s24, array25);
                final int length16 = " ".length();
                final String s25 = CommandWorldBorder.I[0xB1 ^ 0x96];
                final Object[] array26 = new Object[" ".length()];
                array26["".length()] = damageAmount;
                array24[length16] = String.format(s25, array26);
                CommandBase.notifyOperators(commandSender, this, s23, array24);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
        }
        else if (array["".length()].equals(CommandWorldBorder.I[0x43 ^ 0x6B])) {
            if (array.length < "  ".length()) {
                throw new WrongUsageException(CommandWorldBorder.I[0xA5 ^ 0x8C], new Object["".length()]);
            }
            final int int1 = CommandBase.parseInt(array["  ".length()], "".length());
            if (array[" ".length()].equals(CommandWorldBorder.I[0x85 ^ 0xAF])) {
                if (array.length != "   ".length()) {
                    throw new WrongUsageException(CommandWorldBorder.I[0x44 ^ 0x6F], new Object["".length()]);
                }
                final int warningTime = worldBorder.getWarningTime();
                worldBorder.setWarningTime(int1);
                final String s26 = CommandWorldBorder.I[0xEF ^ 0xC3];
                final Object[] array27 = new Object["  ".length()];
                array27["".length()] = int1;
                array27[" ".length()] = warningTime;
                CommandBase.notifyOperators(commandSender, this, s26, array27);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else if (array[" ".length()].equals(CommandWorldBorder.I[0xED ^ 0xC0])) {
                if (array.length != "   ".length()) {
                    throw new WrongUsageException(CommandWorldBorder.I[0x2B ^ 0x5], new Object["".length()]);
                }
                final int warningDistance = worldBorder.getWarningDistance();
                worldBorder.setWarningDistance(int1);
                final String s27 = CommandWorldBorder.I[0x41 ^ 0x6E];
                final Object[] array28 = new Object["  ".length()];
                array28["".length()] = int1;
                array28[" ".length()] = warningDistance;
                CommandBase.notifyOperators(commandSender, this, s27, array28);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
        }
        else {
            if (!array["".length()].equals(CommandWorldBorder.I[0x92 ^ 0xA2])) {
                throw new WrongUsageException(CommandWorldBorder.I[0xBC ^ 0x8D], new Object["".length()]);
            }
            final double diameter2 = worldBorder.getDiameter();
            commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, MathHelper.floor_double(diameter2 + 0.5));
            final String s28 = CommandWorldBorder.I[0x15 ^ 0x27];
            final Object[] array29 = new Object[" ".length()];
            final int length17 = "".length();
            final String s29 = CommandWorldBorder.I[0xF5 ^ 0xC6];
            final Object[] array30 = new Object[" ".length()];
            array30["".length()] = diameter2;
            array29[length17] = String.format(s29, array30);
            commandSender.addChatMessage(new ChatComponentTranslation(s28, array29));
        }
    }
    
    protected WorldBorder getWorldBorder() {
        return MinecraftServer.getServer().worldServers["".length()].getWorldBorder();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandWorldBorder.I[" ".length()];
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            final String[] array2 = new String[0xB3 ^ 0xB5];
            array2["".length()] = CommandWorldBorder.I[0x5E ^ 0x6A];
            array2[" ".length()] = CommandWorldBorder.I[0x94 ^ 0xA1];
            array2["  ".length()] = CommandWorldBorder.I[0x38 ^ 0xE];
            array2["   ".length()] = CommandWorldBorder.I[0x3B ^ 0xC];
            array2[0x5F ^ 0x5B] = CommandWorldBorder.I[0x13 ^ 0x2B];
            array2[0x8F ^ 0x8A] = CommandWorldBorder.I[0xAE ^ 0x97];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else if (array.length == "  ".length() && array["".length()].equals(CommandWorldBorder.I[0xA5 ^ 0x9F])) {
            final String[] array3 = new String["  ".length()];
            array3["".length()] = CommandWorldBorder.I[0x75 ^ 0x4E];
            array3[" ".length()] = CommandWorldBorder.I[0xA ^ 0x36];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array3);
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else if (array.length >= "  ".length() && array.length <= "   ".length() && array["".length()].equals(CommandWorldBorder.I[0xAA ^ 0x97])) {
            list = CommandBase.func_181043_b(array, " ".length(), blockPos);
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else if (array.length == "  ".length() && array["".length()].equals(CommandWorldBorder.I[0x35 ^ 0xB])) {
            final String[] array4 = new String["  ".length()];
            array4["".length()] = CommandWorldBorder.I[0x69 ^ 0x56];
            array4[" ".length()] = CommandWorldBorder.I[0x45 ^ 0x5];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array4);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
    }
    
    @Override
    public String getCommandName() {
        return CommandWorldBorder.I["".length()];
    }
    
    private static void I() {
        (I = new String[0x3F ^ 0x7E])["".length()] = I("8.+\u001f\f-.+\u0017\r=", "OAYsh");
        CommandWorldBorder.I[" ".length()] = I("\u0016\u000e\u001b'\u0006\u001b\u0005\u0005d\u0010\u001a\u0013\u001a.\u0005\u001a\u0013\u0012/\u0015[\u0014\u0005+\u0000\u0010", "uavJg");
        CommandWorldBorder.I["  ".length()] = I("\u0006\"\t \u0015\u000b)\u0017c\u0003\n?\b)\u0016\n?\u0000(\u0006K8\u0017,\u0013\u0000", "eMdMt");
        CommandWorldBorder.I["   ".length()] = I(";\u0016-", "HsYJl");
        CommandWorldBorder.I[0x3B ^ 0x3F] = I("\u0015\"\u00145\u0014\u0018)\nv\u0002\u0019?\u0015<\u0017\u0019?\u001d=\u0007X>\u001c,[\u0003>\u0018?\u0010", "vMyXu");
        CommandWorldBorder.I[0xA2 ^ 0xA7] = I("9\u001a\b\u0015'4\u0011\u0016V15\u0007\t\u001c$5\u0007\u0001\u001d4t\u0006\u0000\f\u00156\u001a\u0012\u0014?t\u0006\r\n/4\u001eK\u000b39\u0016\u0000\u000b5", "ZuexF");
        CommandWorldBorder.I[0xC6 ^ 0xC0] = I("\\Da2", "yjPTx");
        CommandWorldBorder.I[0xB3 ^ 0xB4] = I("\u007fy\\\u0000", "ZWmfu");
        CommandWorldBorder.I[0x6E ^ 0x66] = I("+\n\u0004\u0000/&\u0001\u001aC9'\u0017\u0005\t,'\u0017\r\b<f\u0016\f\u0019\u001d$\n\u001e\u00017f\u0002\u001b\u00029f\u0016\u001c\u000e--\u0016\u001a", "HeimN");
        CommandWorldBorder.I[0x54 ^ 0x5D] = I("GZh7", "btYQL");
        CommandWorldBorder.I[0x39 ^ 0x33] = I("wge\u001f", "RITyA");
        CommandWorldBorder.I[0x24 ^ 0x2F] = I("\u0017&\u0002\b,\u001a-\u001cK:\u001b;\u0003\u0001/\u001b;\u000b\u0000?Z:\n\u0011c\u0007<\f\u0006(\u0007:", "tIoeM");
        CommandWorldBorder.I[0x67 ^ 0x6B] = I("]AZ,", "xokJo");
        CommandWorldBorder.I[0xA9 ^ 0xA4] = I("kdC\u0005", "NJrcb");
        CommandWorldBorder.I[0x87 ^ 0x89] = I("0-\r", "QIiPD");
        CommandWorldBorder.I[0x29 ^ 0x26] = I(",7=\u0014+!<#W= *<\u001d( *4\u001c8a94\u001dd:+1\u001e/", "OXPyJ");
        CommandWorldBorder.I[0x76 ^ 0x66] = I("2\u00075;\u000b?\f+x\u001d>\u001a42\b>\u001a<3\u0018\u007f\u001b=\"9=\u0007/:\u0013\u007f\u001b0$\u0003?\u0003v%\u001f2\u000b=%\u0019", "QhXVj");
        CommandWorldBorder.I[0x29 ^ 0x38] = I("Dth$", "aZYBE");
        CommandWorldBorder.I[0xB5 ^ 0xA7] = I("g_D\u001f", "BquyZ");
        CommandWorldBorder.I[0x61 ^ 0x72] = I("\u0017(8>\u0015\u001a#&}\u0003\u001b597\u0016\u001b516\u0006Z40''\u0018(\"?\rZ '<\u0003Z4 0\u0017\u00114&", "tGUSt");
        CommandWorldBorder.I[0x32 ^ 0x26] = I("K{A?", "nUpYu");
        CommandWorldBorder.I[0x67 ^ 0x72] = I("ite\u0003", "LZTeQ");
        CommandWorldBorder.I[0x60 ^ 0x76] = I("&)\u0019*\f+\"\u0007i\u001a*4\u0018#\u000f*4\u0010\"\u001fk5\u00113C63\u0017$\b65", "EFtGm");
        CommandWorldBorder.I[0x56 ^ 0x41] = I("Vms\r", "sCBkz");
        CommandWorldBorder.I[0x31 ^ 0x29] = I("rZ_6", "WtnPG");
        CommandWorldBorder.I[0x69 ^ 0x70] = I("\f\"\"\u0018&\u001d", "oGLlC");
        CommandWorldBorder.I[0xA0 ^ 0xBA] = I("\u0006\n\u0017/%\u000b\u0001\tl3\n\u0017\u0016&&\n\u0017\u001e'6K\u0006\u001f,0\u0000\u0017T77\u0004\u0002\u001f", "eezBD");
        CommandWorldBorder.I[0xB2 ^ 0xA9] = I("7\u001c\u0014\u001e+:\u0017\n]=;\u0001\u0015\u0017(;\u0001\u001d\u00168z\u0010\u001c\u001d>1\u0001W\u0000?7\u0010\u001c\u00009", "TsysJ");
        CommandWorldBorder.I[0x23 ^ 0x3F] = I("=\u000f\u001f;\u000f<", "YnrZh");
        CommandWorldBorder.I[0x4D ^ 0x50] = I("2&\"\u0002\"?-<A4>;#\u000b!>;+\n1\u007f-.\u0002\"6,a\u001a00.*", "QIOoC");
        CommandWorldBorder.I[0x6E ^ 0x70] = I("%9\u000f.\u00175", "GLiHr");
        CommandWorldBorder.I[0xA1 ^ 0xBE] = I("\",\u001a+\u0014/'\u0004h\u0002.1\u001b\"\u0017.1\u0013#\u0007o'\u0016+\u0014&&Y$\u0000'%\u00124[40\u0016!\u0010", "ACwFu");
        CommandWorldBorder.I[0x7D ^ 0x5D] = I("\"97\u0001(/2)B>.$6\b+.$>\t;o2;\u0001(&3t\u000e<'0?\u001eg2#9\u000f,2%", "AVZlI");
        CommandWorldBorder.I[0x38 ^ 0x19] = I("G`c\u0011", "bNRwU");
        CommandWorldBorder.I[0x3E ^ 0x1C] = I("a^`\u001c", "DpQzU");
        CommandWorldBorder.I[0x6B ^ 0x48] = I("+$\u001a\u001f\u0006>", "JIujh");
        CommandWorldBorder.I[0xB9 ^ 0x9D] = I("\u001b\u001a\f\u0006\u0018\u0016\u0011\u0012E\u000e\u0017\u0007\r\u000f\u001b\u0017\u0007\u0005\u000e\u000bV\u0011\u0000\u0006\u0018\u001f\u0010O\n\u0014\u0017\u0000\u000f\u001fW\r\u0006\u0000\f\u001c", "xuaky");
        CommandWorldBorder.I[0xB0 ^ 0x95] = I("(\u0001%=\t%\n;~\u001f$\u001c$4\n$\u001c,5\u001ae\n)=\t,\u000bf1\u0005$\u001b&$F8\u001b+3\r8\u001d", "KnHPh");
        CommandWorldBorder.I[0xAE ^ 0x88] = I("FzH1", "cTzWN");
        CommandWorldBorder.I[0x7B ^ 0x5C] = I("Wyt\u0011", "rWFwV");
        CommandWorldBorder.I[0x9A ^ 0xB2] = I("5;\u0010\u0002\u0000,=", "BZbli");
        CommandWorldBorder.I[0xB6 ^ 0x9F] = I(".((\u0005\u0016##6F\u0000\"5)\f\u0015\"5!\r\u0005c0$\u001a\u0019$)\"F\u0002>&\"\r", "MGEhw");
        CommandWorldBorder.I[0x82 ^ 0xA8] = I("\u0017\u0007;\u0012", "cnVwI");
        CommandWorldBorder.I[0x77 ^ 0x5C] = I(")\u0016.'\u0011$\u001d0d\u0007%\u000b/.\u0012%\u000b'/\u0002d\u000e\"8\u001e#\u0017$d\u0004#\u0014&d\u00059\u0018$/", "JyCJp");
        CommandWorldBorder.I[0xED ^ 0xC1] = I("\u0015-\u0018\u0004\u000f\u0018&\u0006G\u0019\u00190\u0019\r\f\u00190\u0011\f\u001cX5\u0014\u001b\u0000\u001f,\u0012G\u001a\u001f/\u0010G\u001d\u0003!\u0016\f\u001d\u0005", "vBuin");
        CommandWorldBorder.I[0x28 ^ 0x5] = I(".\u001a\u0003\u0010 $\u0010\u0015", "JspdA");
        CommandWorldBorder.I[0x34 ^ 0x1A] = I("$9\u000e\u001a()2\u0010Y>($\u000f\u0013+($\u0007\u0012;i!\u0002\u0005'.8\u0004Y-.%\u0017\u0016'$3M\u0002:&1\u0006", "GVcwI");
        CommandWorldBorder.I[0x11 ^ 0x3E] = I("/\u0002\u000b\u0015%\"\t\u0015V3#\u001f\n\u001c&#\u001f\u0002\u001d6b\u001a\u0007\n*%\u0003\u0001V %\u001e\u0012\u0019*/\bH\u000b1/\u000e\u0003\u000b7", "LmfxD");
        CommandWorldBorder.I[0xB ^ 0x3B] = I("-?\u0016", "JZbHE");
        CommandWorldBorder.I[0x73 ^ 0x42] = I("\b\u001c!\u0005.\u0005\u0017?F8\u0004\u0001 \f-\u0004\u0001(\r=E\u0006?\t(\u000e", "ksLhO");
        CommandWorldBorder.I[0x36 ^ 0x4] = I("\n74,\u0006\u0007<*o\u0010\u0006*5%\u0005\u0006*=$\u0015G?<5I\u001a-:\"\u0002\u001a+", "iXYAg");
        CommandWorldBorder.I[0x65 ^ 0x56] = I("PkI\u0011", "uEywo");
        CommandWorldBorder.I[0x83 ^ 0xB7] = I(")=\u0018", "ZXlqk");
        CommandWorldBorder.I[0x66 ^ 0x53] = I("7\u0010(\u001f &", "TuFkE");
        CommandWorldBorder.I[0x70 ^ 0x46] = I("\u0011\u0015\u0017\u0000+\u0010", "utzaL");
        CommandWorldBorder.I[0xF6 ^ 0xC1] = I("-\u001b*\u001c;4\u001d", "ZzXrR");
        CommandWorldBorder.I[0x34 ^ 0xC] = I("\u0003\u0017\"", "bsFMo");
        CommandWorldBorder.I[0x1A ^ 0x23] = I("\n#\u0007", "mFshZ");
        CommandWorldBorder.I[0x3C ^ 0x6] = I(",\f\u0004\u0013>-", "HmirY");
        CommandWorldBorder.I[0x8C ^ 0xB7] = I("\u0004\"?\u00157\u0014", "fWYsR");
        CommandWorldBorder.I[0xF8 ^ 0xC4] = I("(7>\u001f\u001d=", "IZQjs");
        CommandWorldBorder.I[0x13 ^ 0x2E] = I("*.\u0007\u0017 ;", "IKicE");
        CommandWorldBorder.I[0xA4 ^ 0x9A] = I("'\r\u00169\u0002>\u000b", "PldWk");
        CommandWorldBorder.I[0xB ^ 0x34] = I("\u0017\r \u001d", "cdMxf");
        CommandWorldBorder.I[0x25 ^ 0x65] = I("+\u0018\u000b:\n!\u0012\u001d", "OqxNk");
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
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
            if (-1 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
