package net.minecraft.command;

import net.minecraft.entity.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import java.util.*;

public class CommandExecuteAt extends CommandBase
{
    private static final String[] I;
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < (0x3D ^ 0x38)) {
            throw new WrongUsageException(CommandExecuteAt.I["  ".length()], new Object["".length()]);
        }
        final Entity entity = CommandBase.getEntity(commandSender, array["".length()], (Class<? extends Entity>)Entity.class);
        final double double1 = CommandBase.parseDouble(entity.posX, array[" ".length()], "".length() != 0);
        final double double2 = CommandBase.parseDouble(entity.posY, array["  ".length()], "".length() != 0);
        final double double3 = CommandBase.parseDouble(entity.posZ, array["   ".length()], "".length() != 0);
        final BlockPos blockPos = new BlockPos(double1, double2, double3);
        int n = 0x7F ^ 0x7B;
        if (CommandExecuteAt.I["   ".length()].equals(array[0x68 ^ 0x6C]) && array.length > (0xB4 ^ 0xBE)) {
            final World entityWorld = entity.getEntityWorld();
            final double double4 = CommandBase.parseDouble(double1, array[0x79 ^ 0x7C], "".length() != 0);
            final double double5 = CommandBase.parseDouble(double2, array[0x3C ^ 0x3A], "".length() != 0);
            final double double6 = CommandBase.parseDouble(double3, array[0x9F ^ 0x98], "".length() != 0);
            final Block blockByText = CommandBase.getBlockByText(commandSender, array[0xAC ^ 0xA4]);
            final int int1 = CommandBase.parseInt(array[0xCD ^ 0xC4], -" ".length(), 0x27 ^ 0x28);
            final IBlockState blockState = entityWorld.getBlockState(new BlockPos(double4, double5, double6));
            if (blockState.getBlock() != blockByText || (int1 >= 0 && blockState.getBlock().getMetaFromState(blockState) != int1)) {
                final String s = CommandExecuteAt.I[0x78 ^ 0x7C];
                final Object[] array2 = new Object["  ".length()];
                array2["".length()] = CommandExecuteAt.I[0x78 ^ 0x7D];
                array2[" ".length()] = entity.getName();
                throw new CommandException(s, array2);
            }
            n = (0x38 ^ 0x32);
        }
        final String buildString = CommandBase.buildString(array, n);
        final ICommandSender commandSender2 = new ICommandSender(this, entity, commandSender, blockPos, double1, double2, double3) {
            private static final String[] I;
            private final double val$d0;
            private final BlockPos val$blockpos;
            private final double val$d2;
            final CommandExecuteAt this$0;
            private final Entity val$entity;
            private final ICommandSender val$sender;
            private final double val$d1;
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("3\u001b\u001e82>\u001019<3\u001f< ' \u0001\u0007", "PtsUS");
            }
            
            @Override
            public String getName() {
                return this.val$entity.getName();
            }
            
            @Override
            public BlockPos getPosition() {
                return this.val$blockpos;
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return this.val$entity;
            }
            
            static {
                I();
            }
            
            @Override
            public boolean sendCommandFeedback() {
                final MinecraftServer server = MinecraftServer.getServer();
                if (server != null && !server.worldServers["".length()].getGameRules().getBoolean(CommandExecuteAt$1.I["".length()])) {
                    return "".length() != 0;
                }
                return " ".length() != 0;
            }
            
            @Override
            public IChatComponent getDisplayName() {
                return this.val$entity.getDisplayName();
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
                    if (1 <= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.val$d0, this.val$d1, this.val$d2);
            }
            
            @Override
            public boolean canCommandSenderUseCommand(final int n, final String s) {
                return this.val$sender.canCommandSenderUseCommand(n, s);
            }
            
            @Override
            public void addChatMessage(final IChatComponent chatComponent) {
                this.val$sender.addChatMessage(chatComponent);
            }
            
            @Override
            public void setCommandStat(final CommandResultStats.Type type, final int n) {
                this.val$entity.setCommandStat(type, n);
            }
            
            @Override
            public World getEntityWorld() {
                return this.val$entity.worldObj;
            }
        };
        final ICommandManager commandManager = MinecraftServer.getServer().getCommandManager();
        try {
            if (commandManager.executeCommand(commandSender2, buildString) < " ".length()) {
                final String s2 = CommandExecuteAt.I[0x30 ^ 0x36];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = buildString;
                throw new CommandException(s2, array3);
            }
        }
        catch (Throwable t) {
            final String s3 = CommandExecuteAt.I[0x3C ^ 0x3B];
            final Object[] array4 = new Object["  ".length()];
            array4["".length()] = buildString;
            array4[" ".length()] = entity.getName();
            throw new CommandException(s3, array4);
        }
    }
    
    @Override
    public String getCommandName() {
        return CommandExecuteAt.I["".length()];
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (array.length > " ".length() && array.length <= (0x19 ^ 0x1D)) {
            list = CommandBase.func_175771_a(array, " ".length(), blockPos);
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else if (array.length > (0x17 ^ 0x12) && array.length <= (0x45 ^ 0x4D) && CommandExecuteAt.I[0x44 ^ 0x4C].equals(array[0x45 ^ 0x41])) {
            list = CommandBase.func_175771_a(array, 0xBD ^ 0xB8, blockPos);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else if (array.length == (0x5C ^ 0x55) && CommandExecuteAt.I[0x1A ^ 0x13].equals(array[0x34 ^ 0x30])) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, Block.blockRegistry.getKeys());
            "".length();
            if (-1 != -1) {
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
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandExecuteAt.I[" ".length()];
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x7F ^ 0x75])["".length()] = I("\u0004\"(!\u0000\u0015?", "aZMBu");
        CommandExecuteAt.I[" ".length()] = I("\r\u0017\u001a7(\u0000\u001c\u0004t,\u0016\u001d\u0014/=\u000bV\u0002)(\t\u001d", "nxwZI");
        CommandExecuteAt.I["  ".length()] = I("\t54,\u0000\u0004>*o\u0004\u0012?:4\u0015\u000ft,2\u0000\r?", "jZYAa");
        CommandExecuteAt.I["   ".length()] = I("\u000049\u0004\u0019\u0010", "dQMaz");
        CommandExecuteAt.I[0xC1 ^ 0xC5] = I("%\u00054\u00011(\u000e*B5>\u000f:\u0019$#D?\r9*\u000f=", "FjYlP");
        CommandExecuteAt.I[0x43 ^ 0x46] = I("\u0013 \u0017+\u000f\u0003", "wEcNl");
        CommandExecuteAt.I[0x70 ^ 0x76] = I("3&8\u00079>-&D=(,6\u001f,5g4\u00064\u0019'#\u0005;1=<\u00056#\u000f4\u000345-", "PIUjX");
        CommandExecuteAt.I[0xC1 ^ 0xC6] = I("\b<\u0006\t(\u00057\u0018J,\u00136\b\u0011=\u000e}\r\u0005 \u00076\u000f", "kSkdI");
        CommandExecuteAt.I[0x6C ^ 0x64] = I("\u0014$#\n,\u0004", "pAWoO");
        CommandExecuteAt.I[0x17 ^ 0x1E] = I(".<\u0011\u000b)>", "JYenJ");
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
}
