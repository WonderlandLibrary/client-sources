package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface ICommandSender {
   String getName();

   void addChatMessage(IChatComponent var1);

   void func_174794_a(CommandResultStats.Type var1, int var2);

   IChatComponent getDisplayName();

   World getEntityWorld();

   Entity getCommandSenderEntity();

   boolean canCommandSenderUseCommand(int var1, String var2);

   BlockPos getPosition();

   Vec3 getPositionVector();

   boolean sendCommandFeedback();
}
