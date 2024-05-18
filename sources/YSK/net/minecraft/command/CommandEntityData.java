package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;

public class CommandEntityData extends CommandBase
{
    private static final String[] I;
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < "  ".length()) {
            throw new WrongUsageException(CommandEntityData.I["  ".length()], new Object["".length()]);
        }
        final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array["".length()]);
        if (func_175768_b instanceof EntityPlayer) {
            final String s = CommandEntityData.I["   ".length()];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = func_175768_b.getDisplayName();
            throw new CommandException(s, array2);
        }
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        func_175768_b.writeToNBT(nbtTagCompound);
        final NBTTagCompound nbtTagCompound2 = (NBTTagCompound)nbtTagCompound.copy();
        NBTTagCompound tagFromJson;
        try {
            tagFromJson = JsonToNBT.getTagFromJson(CommandBase.getChatComponentFromNthArg(commandSender, array, " ".length()).getUnformattedText());
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        catch (NBTException ex) {
            final String s2 = CommandEntityData.I[0x59 ^ 0x5D];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = ex.getMessage();
            throw new CommandException(s2, array3);
        }
        tagFromJson.removeTag(CommandEntityData.I[0x25 ^ 0x20]);
        tagFromJson.removeTag(CommandEntityData.I[0x72 ^ 0x74]);
        nbtTagCompound.merge(tagFromJson);
        if (nbtTagCompound.equals(nbtTagCompound2)) {
            final String s3 = CommandEntityData.I[0x28 ^ 0x2F];
            final Object[] array4 = new Object[" ".length()];
            array4["".length()] = nbtTagCompound.toString();
            throw new CommandException(s3, array4);
        }
        func_175768_b.readFromNBT(nbtTagCompound);
        final String s4 = CommandEntityData.I[0x18 ^ 0x10];
        final Object[] array5 = new Object[" ".length()];
        array5["".length()] = nbtTagCompound.toString();
        CommandBase.notifyOperators(commandSender, this, s4, array5);
    }
    
    @Override
    public String getCommandName() {
        return CommandEntityData.I["".length()];
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    private static void I() {
        (I = new String[0x3 ^ 0xA])["".length()] = I(",\u0019-\u001d\u001c0\u00138\u0000\t", "IwYth");
        CommandEntityData.I[" ".length()] = I("-;\u000e\u0005( 0\u0010F,  \n\u001c0*5\u0017\tg;'\u0002\u000f,", "NTchI");
        CommandEntityData.I["  ".length()] = I("\u0010\"(\u0002\f\u001d)6A\b\u001d9,\u001b\u0014\u0017,1\u000eC\u0006>$\b\b", "sMEom");
        CommandEntityData.I["   ".length()] = I("\f\u001f/\f\u0000\u0001\u00141O\u0004\u0001\u0004+\u0015\u0018\u000b\u00116\u0000O\u0001\u001f\u0012\r\u0000\u0016\u00150\u0012", "opBaa");
        CommandEntityData.I[0x80 ^ 0x84] = I("7\u0001?\u001f5:\n!\\1:\u001a;\u0006-0\u000f&\u0013z \u000f57&&\u0001 ", "TnRrT");
        CommandEntityData.I[0x77 ^ 0x72] = I("%' \u000b\u001d\u001f\u0001\u001d", "priOP");
        CommandEntityData.I[0x60 ^ 0x66] = I("28\u0001\" \u0002\f;\u0012", "gmHfl");
        CommandEntityData.I[0x9B ^ 0x9C] = I(";6\u001d,06=\u0003o46-\u00195(<8\u0004 \u007f>8\u0019-4<", "XYpAQ");
        CommandEntityData.I[0x45 ^ 0x4D] = I("$$/8\u0007)/1{\u0003)?+!\u001f#*64H4>!6\u000348", "GKBUf");
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandEntityData.I[" ".length()];
    }
    
    static {
        I();
    }
}
