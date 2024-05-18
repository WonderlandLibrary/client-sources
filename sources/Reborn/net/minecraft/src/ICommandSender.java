package net.minecraft.src;

public interface ICommandSender
{
    String getCommandSenderName();
    
    void sendChatToPlayer(final String p0);
    
    boolean canCommandSenderUseCommand(final int p0, final String p1);
    
    String translateString(final String p0, final Object... p1);
    
    ChunkCoordinates getPlayerCoordinates();
}
