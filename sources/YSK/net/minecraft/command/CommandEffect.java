package net.minecraft.command;

import net.minecraft.server.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;

public class CommandEffect extends CommandBase
{
    private static final String[] I;
    
    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, this.getAllUsernames());
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (array.length == "  ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, Potion.func_181168_c());
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if (array.length == (0x8 ^ 0xD)) {
            final String[] array2 = new String["  ".length()];
            array2["".length()] = CommandEffect.I[0x63 ^ 0x68];
            array2[" ".length()] = CommandEffect.I[0xA9 ^ 0xA5];
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
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public String getCommandName() {
        return CommandEffect.I["".length()];
    }
    
    private static void I() {
        (I = new String[0xB8 ^ 0xB5])["".length()] = I("\t\u0015\u0004,+\u0018", "lsbIH");
        CommandEffect.I[" ".length()] = I("\u000f)\n\u001b\n\u0002\"\u0014X\u000e\n \u0002\u0015\u001fB3\u0014\u0017\f\t", "lFgvk");
        CommandEffect.I["  ".length()] = I("\r :\u0005\u000b\u0000+$F\u000f\b)2\u000b\u001e@:$\t\r\u000b", "nOWhj");
        CommandEffect.I["   ".length()] = I("\u0017:2\u0002\u0018", "tVWcj");
        CommandEffect.I[0xAB ^ 0xAF] = I(";=\u0004\u001d'66\u001a^#>4\f\u00132v4\b\u0019*- \f^(7&(\u001321$\f^'4>", "XRipF");
        CommandEffect.I[0x4B ^ 0x4E] = I("\u0010\u001d;\u0000\u0011\u001d\u0016%C\u0015\u0015\u00143\u000e\u0004]\u0001#\u000e\u0013\u0016\u0001%C\u0002\u0016\u001f9\u001b\u0015\u0017\\7\u0001\u001c", "srVmp");
        CommandEffect.I[0x74 ^ 0x72] = I("\u001d\u0016=\u0013", "idHvA");
        CommandEffect.I[0x9E ^ 0x99] = I("\"5\u000e\u001e)/>\u0010]-'<\u0006\u0010<o)\u0016\u0010+$)\u0010", "AZcsH");
        CommandEffect.I[0x58 ^ 0x50] = I("\u00069 \b)\u000b2>K-\u00030(\u0006<K%8\u0006+\u0000%>K:\u0000;\"\u0013-\u0001", "eVMeH");
        CommandEffect.I[0x56 ^ 0x5F] = I("\u000b\u001e\u0006\u001b,\u0006\u0015\u0018X(\u000e\u0017\u000e\u00159F\u0017\n\u001f!\u001d\u0003\u000eX#\u0007\u0005*\u00159\u0001\u0007\u000e", "hqkvM");
        CommandEffect.I[0x30 ^ 0x3A] = I("9=\u001c= 46\u0002~$<4\u001435t<\u001e$\u00075'\u001f4", "ZRqPA");
        CommandEffect.I[0x5E ^ 0x55] = I("!\n\"\u001d", "UxWxG");
        CommandEffect.I[0x6C ^ 0x60] = I(",\u0000\u001b\u0016\u0013", "Jawev");
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandEffect.I[" ".length()];
    }
    
    static {
        I();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < "  ".length()) {
            throw new WrongUsageException(CommandEffect.I["  ".length()], new Object["".length()]);
        }
        final EntityLivingBase entityLivingBase = CommandBase.getEntity(commandSender, array["".length()], (Class<? extends EntityLivingBase>)EntityLivingBase.class);
        if (array[" ".length()].equals(CommandEffect.I["   ".length()])) {
            if (entityLivingBase.getActivePotionEffects().isEmpty()) {
                final String s = CommandEffect.I[0x95 ^ 0x91];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = entityLivingBase.getName();
                throw new CommandException(s, array2);
            }
            entityLivingBase.clearActivePotions();
            final String s2 = CommandEffect.I[0x9C ^ 0x99];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = entityLivingBase.getName();
            CommandBase.notifyOperators(commandSender, this, s2, array3);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            int n;
            try {
                n = CommandBase.parseInt(array[" ".length()], " ".length());
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            catch (NumberInvalidException ex) {
                final Potion potionFromResourceLocation = Potion.getPotionFromResourceLocation(array[" ".length()]);
                if (potionFromResourceLocation == null) {
                    throw ex;
                }
                n = potionFromResourceLocation.id;
            }
            int length = 397 + 223 - 153 + 133;
            int int1 = 0x35 ^ 0x2B;
            int n2 = "".length();
            if (n < 0 || n >= Potion.potionTypes.length || Potion.potionTypes[n] == null) {
                final String s3 = CommandEffect.I[0x6D ^ 0x67];
                final Object[] array4 = new Object[" ".length()];
                array4["".length()] = n;
                throw new NumberInvalidException(s3, array4);
            }
            final Potion potion = Potion.potionTypes[n];
            if (array.length >= "   ".length()) {
                int1 = CommandBase.parseInt(array["  ".length()], "".length(), 577461 + 619259 - 478659 + 281939);
                if (potion.isInstant()) {
                    length = int1;
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                }
                else {
                    length = int1 * (0x54 ^ 0x40);
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                }
            }
            else if (potion.isInstant()) {
                length = " ".length();
            }
            if (array.length >= (0x12 ^ 0x16)) {
                n2 = CommandBase.parseInt(array["   ".length()], "".length(), 144 + 18 - 110 + 203);
            }
            int n3 = " ".length();
            if (array.length >= (0x9B ^ 0x9E) && CommandEffect.I[0x4 ^ 0x2].equalsIgnoreCase(array[0x8 ^ 0xC])) {
                n3 = "".length();
            }
            if (int1 > 0) {
                final PotionEffect potionEffect = new PotionEffect(n, length, n2, "".length() != 0, n3 != 0);
                entityLivingBase.addPotionEffect(potionEffect);
                final String s4 = CommandEffect.I[0x53 ^ 0x54];
                final Object[] array5 = new Object[0x33 ^ 0x36];
                array5["".length()] = new ChatComponentTranslation(potionEffect.getEffectName(), new Object["".length()]);
                array5[" ".length()] = n;
                array5["  ".length()] = n2;
                array5["   ".length()] = entityLivingBase.getName();
                array5[0x7C ^ 0x78] = int1;
                CommandBase.notifyOperators(commandSender, this, s4, array5);
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                if (!entityLivingBase.isPotionActive(n)) {
                    final String s5 = CommandEffect.I[0x67 ^ 0x6E];
                    final Object[] array6 = new Object["  ".length()];
                    array6["".length()] = new ChatComponentTranslation(potion.getName(), new Object["".length()]);
                    array6[" ".length()] = entityLivingBase.getName();
                    throw new CommandException(s5, array6);
                }
                entityLivingBase.removePotionEffect(n);
                final String s6 = CommandEffect.I[0x6E ^ 0x66];
                final Object[] array7 = new Object["  ".length()];
                array7["".length()] = new ChatComponentTranslation(potion.getName(), new Object["".length()]);
                array7[" ".length()] = entityLivingBase.getName();
                CommandBase.notifyOperators(commandSender, this, s6, array7);
                "".length();
                if (3 == 0) {
                    throw null;
                }
            }
        }
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
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
