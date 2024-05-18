package net.minecraft.command;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public interface ICommandSender
{
    boolean canCommandSenderUseCommand(final int p0, final String p1);
    
    void setCommandStat(final CommandResultStats.Type p0, final int p1);
    
    void addChatMessage(final IChatComponent p0);
    
    BlockPos getPosition();
    
    IChatComponent getDisplayName();
    
    World getEntityWorld();
    
    String getName();
    
    boolean sendCommandFeedback();
    
    Entity getCommandSenderEntity();
    
    Vec3 getPositionVector();
}
