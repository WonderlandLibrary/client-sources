package net.minecraft.command;

import net.minecraft.command.CommandResultStats;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

class CommandResultStats$1 implements ICommandSender {
   final ICommandSender val$sender;
   final CommandResultStats this$0;

   CommandResultStats$1(CommandResultStats var1, ICommandSender var2) {
      this.this$0 = var1;
      this.val$sender = var2;
   }

   public String getName() {
      return this.val$sender.getName();
   }

   public IChatComponent getDisplayName() {
      return this.val$sender.getDisplayName();
   }

   public void addChatMessage(IChatComponent var1) {
      this.val$sender.addChatMessage(var1);
   }

   public boolean canCommandSenderUseCommand(int var1, String var2) {
      return true;
   }

   public BlockPos getPosition() {
      return this.val$sender.getPosition();
   }

   public Vec3 getPositionVector() {
      return this.val$sender.getPositionVector();
   }

   public World getEntityWorld() {
      return this.val$sender.getEntityWorld();
   }

   public Entity getCommandSenderEntity() {
      return this.val$sender.getCommandSenderEntity();
   }

   public boolean sendCommandFeedback() {
      return this.val$sender.sendCommandFeedback();
   }

   public void setCommandStat(CommandResultStats$Type var1, int var2) {
      this.val$sender.setCommandStat(var1, var2);
   }
}
