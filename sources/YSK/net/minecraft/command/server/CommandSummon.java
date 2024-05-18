package net.minecraft.command.server;

import net.minecraft.entity.effect.*;
import net.minecraft.command.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandSummon extends CommandBase
{
    private static final String[] I;
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < " ".length()) {
            throw new WrongUsageException(CommandSummon.I["  ".length()], new Object["".length()]);
        }
        final String s = array["".length()];
        BlockPos position = commandSender.getPosition();
        final Vec3 positionVector = commandSender.getPositionVector();
        double n = positionVector.xCoord;
        double n2 = positionVector.yCoord;
        double n3 = positionVector.zCoord;
        if (array.length >= (0x15 ^ 0x11)) {
            n = CommandBase.parseDouble(n, array[" ".length()], " ".length() != 0);
            n2 = CommandBase.parseDouble(n2, array["  ".length()], "".length() != 0);
            n3 = CommandBase.parseDouble(n3, array["   ".length()], " ".length() != 0);
            position = new BlockPos(n, n2, n3);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(position)) {
            throw new CommandException(CommandSummon.I["   ".length()], new Object["".length()]);
        }
        if (CommandSummon.I[0x23 ^ 0x27].equals(s)) {
            entityWorld.addWeatherEffect(new EntityLightningBolt(entityWorld, n, n2, n3));
            CommandBase.notifyOperators(commandSender, this, CommandSummon.I[0x8F ^ 0x8A], new Object["".length()]);
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else {
            NBTTagCompound tagFromJson = new NBTTagCompound();
            int n4 = "".length();
            if (array.length >= (0x4B ^ 0x4E)) {
                final IChatComponent chatComponentFromNthArg = CommandBase.getChatComponentFromNthArg(commandSender, array, 0x9B ^ 0x9F);
                try {
                    tagFromJson = JsonToNBT.getTagFromJson(chatComponentFromNthArg.getUnformattedText());
                    n4 = " ".length();
                    "".length();
                    if (0 == 2) {
                        throw null;
                    }
                }
                catch (NBTException ex) {
                    final String s2 = CommandSummon.I[0xC0 ^ 0xC6];
                    final Object[] array2 = new Object[" ".length()];
                    array2["".length()] = ex.getMessage();
                    throw new CommandException(s2, array2);
                }
            }
            tagFromJson.setString(CommandSummon.I[0x53 ^ 0x54], s);
            Entity entityFromNBT;
            try {
                entityFromNBT = EntityList.createEntityFromNBT(tagFromJson, entityWorld);
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            catch (RuntimeException ex2) {
                throw new CommandException(CommandSummon.I[0xBD ^ 0xB5], new Object["".length()]);
            }
            if (entityFromNBT == null) {
                throw new CommandException(CommandSummon.I[0xB3 ^ 0xBA], new Object["".length()]);
            }
            entityFromNBT.setLocationAndAngles(n, n2, n3, entityFromNBT.rotationYaw, entityFromNBT.rotationPitch);
            if (n4 == 0 && entityFromNBT instanceof EntityLiving) {
                ((EntityLiving)entityFromNBT).onInitialSpawn(entityWorld.getDifficultyForLocation(new BlockPos(entityFromNBT)), null);
            }
            entityWorld.spawnEntityInWorld(entityFromNBT);
            Entity entity = entityFromNBT;
            NBTTagCompound compoundTag = tagFromJson;
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (entity != null && compoundTag.hasKey(CommandSummon.I[0x5C ^ 0x50], 0x79 ^ 0x73)) {
                final Entity entityFromNBT2 = EntityList.createEntityFromNBT(compoundTag.getCompoundTag(CommandSummon.I[0x5F ^ 0x55]), entityWorld);
                if (entityFromNBT2 != null) {
                    entityFromNBT2.setLocationAndAngles(n, n2, n3, entityFromNBT2.rotationYaw, entityFromNBT2.rotationPitch);
                    entityWorld.spawnEntityInWorld(entityFromNBT2);
                    entity.mountEntity(entityFromNBT2);
                }
                entity = entityFromNBT2;
                compoundTag = compoundTag.getCompoundTag(CommandSummon.I[0x2D ^ 0x26]);
            }
            CommandBase.notifyOperators(commandSender, this, CommandSummon.I[0x5C ^ 0x51], new Object["".length()]);
        }
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
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
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, EntityList.getEntityNameList());
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else if (array.length > " ".length() && array.length <= (0x2A ^ 0x2E)) {
            list = CommandBase.func_175771_a(array, " ".length(), blockPos);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
    }
    
    private static void I() {
        (I = new String[0x8 ^ 0x6])["".length()] = I("\u001a\"\u0015=\n\u0007", "iWxPe");
        CommandSummon.I[" ".length()] = I("7\"\u000f\u00156:)\u0011V$! \u000f\u00179z8\u0011\u001901", "TMbxW");
        CommandSummon.I["  ".length()] = I("\n-;\u000e\u0015\u0007&%M\u0007\u001c/;\f\u001aG7%\u0002\u0013\f", "iBVct");
        CommandSummon.I["   ".length()] = I("%#\b\u00148((\u0016W*3!\b\u00167h#\u0010\r\u0016 \u001b\n\u000b5\"", "FLeyY");
        CommandSummon.I[0xA9 ^ 0xAD] = I("\u001b\u000e1\u0005\u00119\u000e8\n'8\u000b\"", "WgVme");
        CommandSummon.I[0x4A ^ 0x4F] = I("\u000f\u0006(\u0005.\u0002\r6F<\u0019\u0004(\u0007!B\u001a0\u000b,\t\u001a6", "liEhO");
        CommandSummon.I[0x9 ^ 0xF] = I("\n:*+6\u000714h$\u001c8*)9G!&!\u0012\u001b'(4", "iUGFW");
        CommandSummon.I[0x28 ^ 0x2F] = I(";\u0011", "RuqKl");
        CommandSummon.I[0xBB ^ 0xB3] = I("\u0000)\u0000<,\r\"\u001e\u007f>\u0016+\u0000>#M \f8!\u0006\"", "cFmQM");
        CommandSummon.I[0x8D ^ 0x84] = I("\u0019:\u0018\u001d\u0012\u00141\u0006^\u0000\u000f8\u0018\u001f\u001dT3\u0014\u0019\u001f\u001f1", "zUups");
        CommandSummon.I[0x86 ^ 0x8C] = I("\u0004\u001c2:,1", "VuVSB");
        CommandSummon.I[0x1F ^ 0x14] = I("\u0019\u0000 \u001c4,", "KiDuZ");
        CommandSummon.I[0x8D ^ 0x81] = I("\u0011,\u0014=&$", "CEpTH");
        CommandSummon.I[0x72 ^ 0x7F] = I("(9\u001f+0%2\u0001h\">;\u001f)?e%\u0007%2.%\u0001", "KVrFQ");
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandSummon.I[" ".length()];
    }
    
    @Override
    public String getCommandName() {
        return CommandSummon.I["".length()];
    }
}
