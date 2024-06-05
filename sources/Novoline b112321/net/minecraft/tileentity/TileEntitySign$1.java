package net.minecraft.tileentity;

import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

class TileEntitySign$1 implements ICommandSender {
   final TileEntitySign this$0;

   TileEntitySign$1(TileEntitySign var1) {
      this.this$0 = var1;
   }

   public String getName() {
      return "Sign";
   }

   public IChatComponent getDisplayName() {
      return new ChatComponentText(this.getName());
   }

   public void addChatMessage(IChatComponent var1) {
   }

   public boolean canCommandSenderUseCommand(int var1, String var2) {
      return true;
   }

   public BlockPos getPosition() {
      return this.this$0.pos;
   }

   public Vec3 getPositionVector() {
      return new Vec3((double)this.this$0.pos.getX() + 0.5D, (double)this.this$0.pos.getY() + 0.5D, (double)this.this$0.pos.getZ() + 0.5D);
   }

   public World getEntityWorld() {
      return this.this$0.worldObj;
   }

   public Entity getCommandSenderEntity() {
      return null;
   }

   public boolean sendCommandFeedback() {
      return false;
   }

   public void setCommandStat(CommandResultStats$Type var1, int var2) {
   }
}
