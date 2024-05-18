package net.minecraft.network.rcon;

import net.minecraft.command.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class RConConsoleSource implements ICommandSender
{
    private static final RConConsoleSource instance;
    private StringBuffer buffer;
    private static final String[] I;
    
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
    }
    
    @Override
    public Vec3 getPositionVector() {
        return new Vec3(0.0, 0.0, 0.0);
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        this.buffer.append(chatComponent.getUnformattedText());
    }
    
    @Override
    public void setCommandStat(final CommandResultStats.Type type, final int n) {
    }
    
    @Override
    public World getEntityWorld() {
        return MinecraftServer.getServer().getEntityWorld();
    }
    
    @Override
    public Entity getCommandSenderEntity() {
        return null;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0010\u001a\u0007!", "ByhOS");
    }
    
    static {
        I();
        instance = new RConConsoleSource();
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos("".length(), "".length(), "".length());
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return " ".length() != 0;
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getName() {
        return RConConsoleSource.I["".length()];
    }
    
    public RConConsoleSource() {
        this.buffer = new StringBuffer();
    }
    
    @Override
    public boolean sendCommandFeedback() {
        return " ".length() != 0;
    }
}
