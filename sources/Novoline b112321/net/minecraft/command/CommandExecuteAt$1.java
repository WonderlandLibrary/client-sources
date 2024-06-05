package net.minecraft.command;

import net.minecraft.command.CommandExecuteAt;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

class CommandExecuteAt$1 implements ICommandSender {
   final Entity val$entity;
   final ICommandSender val$sender;
   final BlockPos val$blockpos;
   final double val$d0;
   final double val$d1;
   final double val$d2;
   final CommandExecuteAt this$0;

   CommandExecuteAt$1(CommandExecuteAt var1, Entity var2, ICommandSender var3, BlockPos var4, double var5, double var7, double var9) {
      this.this$0 = var1;
      this.val$entity = var2;
      this.val$sender = var3;
      this.val$blockpos = var4;
      this.val$d0 = var5;
      this.val$d1 = var7;
      this.val$d2 = var9;
   }

   public String getName() {
      return this.val$entity.getName();
   }

   public IChatComponent getDisplayName() {
      return this.val$entity.getDisplayName();
   }

   public void addChatMessage(IChatComponent var1) {
      this.val$sender.addChatMessage(var1);
   }

   public boolean canCommandSenderUseCommand(int var1, String var2) {
      return this.val$sender.canCommandSenderUseCommand(var1, var2);
   }

   public BlockPos getPosition() {
      return this.val$blockpos;
   }

   public Vec3 getPositionVector() {
      return new Vec3(this.val$d0, this.val$d1, this.val$d2);
   }

   public World getEntityWorld() {
      return this.val$entity.worldObj;
   }

   public Entity getCommandSenderEntity() {
      return this.val$entity;
   }

   public boolean sendCommandFeedback() {
      MinecraftServer var1 = MinecraftServer.getServer();
      return var1.worldServers[0].getGameRules().getBoolean("commandBlockOutput");
   }

   public void setCommandStat(CommandResultStats$Type var1, int var2) {
      this.val$entity.setCommandStat(var1, var2);
   }
}
