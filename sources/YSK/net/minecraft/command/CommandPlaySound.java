package net.minecraft.command;

import java.util.*;
import net.minecraft.server.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class CommandPlaySound extends CommandBase
{
    private static final String[] I;
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == "  ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else if (array.length > "  ".length() && array.length <= (0xA6 ^ 0xA3)) {
            list = CommandBase.func_175771_a(array, "  ".length(), blockPos);
            "".length();
            if (3 < 3) {
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
        return CommandPlaySound.I["".length()];
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    private static void I() {
        (I = new String[0x47 ^ 0x43])["".length()] = I("\u0013=\u00050\u0011\f$\n-", "cQdIb");
        CommandPlaySound.I[" ".length()] = I("!+\u001e96, \u0000z'.%\n'87*\u0017z\"1%\u00141", "BDsTW");
        CommandPlaySound.I["  ".length()] = I(",\u001b!7\n!\u0010?t\u001b#\u00155)\u0004:\u001a(t\u001b#\u00155?\u0019\u001b\u001b#\u001c\n=", "OtLZk");
        CommandPlaySound.I["   ".length()] = I("\u0012\u000b&\u000f\t\u001f\u00008L\u0018\u001d\u00052\u0011\u0007\u0004\n/L\u001b\u0004\u0007(\u0007\u001b\u0002", "qdKbh");
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < "  ".length()) {
            throw new WrongUsageException(this.getCommandUsage(commandSender), new Object["".length()]);
        }
        int length = "".length();
        final String s = array[length++];
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array[length++]);
        final Vec3 positionVector = commandSender.getPositionVector();
        double n = positionVector.xCoord;
        if (array.length > length) {
            n = CommandBase.parseDouble(n, array[length++], " ".length() != 0);
        }
        double n2 = positionVector.yCoord;
        if (array.length > length) {
            n2 = CommandBase.parseDouble(n2, array[length++], "".length(), "".length(), "".length() != 0);
        }
        double n3 = positionVector.zCoord;
        if (array.length > length) {
            n3 = CommandBase.parseDouble(n3, array[length++], " ".length() != 0);
        }
        double double1 = 1.0;
        if (array.length > length) {
            double1 = CommandBase.parseDouble(array[length++], 0.0, 3.4028234663852886E38);
        }
        double double2 = 1.0;
        if (array.length > length) {
            double2 = CommandBase.parseDouble(array[length++], 0.0, 2.0);
        }
        double double3 = 0.0;
        if (array.length > length) {
            double3 = CommandBase.parseDouble(array[length], 0.0, 1.0);
        }
        double n4;
        if (double1 > 1.0) {
            n4 = double1 * 16.0;
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            n4 = 16.0;
        }
        if (player.getDistance(n, n2, n3) > n4) {
            if (double3 <= 0.0) {
                final String s2 = CommandPlaySound.I["  ".length()];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = player.getName();
                throw new CommandException(s2, array2);
            }
            final double n5 = n - player.posX;
            final double n6 = n2 - player.posY;
            final double n7 = n3 - player.posZ;
            final double sqrt = Math.sqrt(n5 * n5 + n6 * n6 + n7 * n7);
            if (sqrt > 0.0) {
                n = player.posX + n5 / sqrt * 2.0;
                n2 = player.posY + n6 / sqrt * 2.0;
                n3 = player.posZ + n7 / sqrt * 2.0;
            }
            double1 = double3;
        }
        player.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(s, n, n2, n3, (float)double1, (float)double2));
        final String s3 = CommandPlaySound.I["   ".length()];
        final Object[] array3 = new Object["  ".length()];
        array3["".length()] = s;
        array3[" ".length()] = player.getName();
        CommandBase.notifyOperators(commandSender, this, s3, array3);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandPlaySound.I[" ".length()];
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
