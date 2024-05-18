package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.command.*;

public class CommandTeleport extends CommandBase
{
    private static final String[] I;
    
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x22 ^ 0x2B])["".length()] = I("=\n", "IzKJm");
        CommandTeleport.I[" ".length()] = I("\u001b#\u0015%\u0002\u0016(\u000bf\u0017\bb\r;\u0002\u001f)", "xLxHc");
        CommandTeleport.I["  ".length()] = I("$;\u0017\u001c\u0015)0\t_\u00007z\u000f\u0002\u0015 1", "GTzqt");
        CommandTeleport.I["   ".length()] = I("\u0006$$\u001f\"\u000b/:\\7\u0015e<\u0001\"\u0002.", "eKIrC");
        CommandTeleport.I[0x92 ^ 0x96] = I("2", "LDsVG");
        CommandTeleport.I[0x96 ^ 0x93] = I("\u0014", "jMPxo");
        CommandTeleport.I[0x98 ^ 0x9E] = I(").,\u0003-$%2@8:o2\u001b/)$2\u001db)..\u001c(#/ \u001a)9", "JAAnL");
        CommandTeleport.I[0x9 ^ 0xE] = I("\u0000-\u000b\u000b*\r&\u0015H?\u0013l\b\t?0#\u000b\u0003\u000f\n/\u0003\b8\n-\b", "cBffK");
        CommandTeleport.I[0xA2 ^ 0xAA] = I("+\u000b\n:\u0017&\u0000\u0014y\u00028J\u0014\"\u0015+\u0001\u0014$", "HdgWv");
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandTeleport.I[" ".length()];
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length != " ".length() && array.length != "  ".length()) {
            listOfStringsMatchingLastWord = null;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
        }
        return listOfStringsMatchingLastWord;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < " ".length()) {
            throw new WrongUsageException(CommandTeleport.I["  ".length()], new Object["".length()]);
        }
        int n = "".length();
        Entity entity;
        if (array.length != "  ".length() && array.length != (0x48 ^ 0x4C) && array.length != (0x75 ^ 0x73)) {
            entity = CommandBase.getCommandSenderAsPlayer(commandSender);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            entity = CommandBase.func_175768_b(commandSender, array["".length()]);
            n = " ".length();
        }
        if (array.length != " ".length() && array.length != "  ".length()) {
            if (array.length < n + "   ".length()) {
                throw new WrongUsageException(CommandTeleport.I["   ".length()], new Object["".length()]);
            }
            if (entity.worldObj != null) {
                int n2 = n + " ".length();
                final CoordinateArg coordinate = CommandBase.parseCoordinate(entity.posX, array[n], " ".length() != 0);
                final CoordinateArg coordinate2 = CommandBase.parseCoordinate(entity.posY, array[n2++], "".length(), "".length(), "".length() != 0);
                final CoordinateArg coordinate3 = CommandBase.parseCoordinate(entity.posZ, array[n2++], " ".length() != 0);
                final double n3 = entity.rotationYaw;
                String s;
                if (array.length > n2) {
                    s = array[n2++];
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                }
                else {
                    s = CommandTeleport.I[0xB2 ^ 0xB6];
                }
                final CoordinateArg coordinate4 = CommandBase.parseCoordinate(n3, s, "".length() != 0);
                final double n4 = entity.rotationPitch;
                String s2;
                if (array.length > n2) {
                    s2 = array[n2];
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                else {
                    s2 = CommandTeleport.I[0x3D ^ 0x38];
                }
                final CoordinateArg coordinate5 = CommandBase.parseCoordinate(n4, s2, "".length() != 0);
                if (entity instanceof EntityPlayerMP) {
                    final EnumSet<S08PacketPlayerPosLook.EnumFlags> none = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);
                    if (coordinate.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.X);
                    }
                    if (coordinate2.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.Y);
                    }
                    if (coordinate3.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.Z);
                    }
                    if (coordinate5.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.X_ROT);
                    }
                    if (coordinate4.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.Y_ROT);
                    }
                    float rotationYawHead = (float)coordinate4.func_179629_b();
                    if (!coordinate4.func_179630_c()) {
                        rotationYawHead = MathHelper.wrapAngleTo180_float(rotationYawHead);
                    }
                    float n5 = (float)coordinate5.func_179629_b();
                    if (!coordinate5.func_179630_c()) {
                        n5 = MathHelper.wrapAngleTo180_float(n5);
                    }
                    if (n5 > 90.0f || n5 < -90.0f) {
                        n5 = MathHelper.wrapAngleTo180_float(180.0f - n5);
                        rotationYawHead = MathHelper.wrapAngleTo180_float(rotationYawHead + 180.0f);
                    }
                    entity.mountEntity(null);
                    ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(coordinate.func_179629_b(), coordinate2.func_179629_b(), coordinate3.func_179629_b(), rotationYawHead, n5, none);
                    entity.setRotationYawHead(rotationYawHead);
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                }
                else {
                    float wrapAngleTo180_float = (float)MathHelper.wrapAngleTo180_double(coordinate4.func_179628_a());
                    float wrapAngleTo180_float2 = (float)MathHelper.wrapAngleTo180_double(coordinate5.func_179628_a());
                    if (wrapAngleTo180_float2 > 90.0f || wrapAngleTo180_float2 < -90.0f) {
                        wrapAngleTo180_float2 = MathHelper.wrapAngleTo180_float(180.0f - wrapAngleTo180_float2);
                        wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(wrapAngleTo180_float + 180.0f);
                    }
                    entity.setLocationAndAngles(coordinate.func_179628_a(), coordinate2.func_179628_a(), coordinate3.func_179628_a(), wrapAngleTo180_float, wrapAngleTo180_float2);
                    entity.setRotationYawHead(wrapAngleTo180_float);
                }
                final String s3 = CommandTeleport.I[0xC6 ^ 0xC0];
                final Object[] array2 = new Object[0xE ^ 0xA];
                array2["".length()] = entity.getName();
                array2[" ".length()] = coordinate.func_179628_a();
                array2["  ".length()] = coordinate2.func_179628_a();
                array2["   ".length()] = coordinate3.func_179628_a();
                CommandBase.notifyOperators(commandSender, this, s3, array2);
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
        }
        else {
            final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[array.length - " ".length()]);
            if (func_175768_b.worldObj != entity.worldObj) {
                throw new CommandException(CommandTeleport.I[0x33 ^ 0x34], new Object["".length()]);
            }
            entity.mountEntity(null);
            if (entity instanceof EntityPlayerMP) {
                ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(func_175768_b.posX, func_175768_b.posY, func_175768_b.posZ, func_175768_b.rotationYaw, func_175768_b.rotationPitch);
                "".length();
                if (-1 == 3) {
                    throw null;
                }
            }
            else {
                entity.setLocationAndAngles(func_175768_b.posX, func_175768_b.posY, func_175768_b.posZ, func_175768_b.rotationYaw, func_175768_b.rotationPitch);
            }
            final String s4 = CommandTeleport.I[0x8D ^ 0x85];
            final Object[] array3 = new Object["  ".length()];
            array3["".length()] = entity.getName();
            array3[" ".length()] = func_175768_b.getName();
            CommandBase.notifyOperators(commandSender, this, s4, array3);
        }
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public String getCommandName() {
        return CommandTeleport.I["".length()];
    }
}
