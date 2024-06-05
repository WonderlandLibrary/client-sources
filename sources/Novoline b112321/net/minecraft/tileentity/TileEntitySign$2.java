package net.minecraft.tileentity;

import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

class TileEntitySign$2 implements ICommandSender {
   final EntityPlayer val$playerIn;
   final TileEntitySign this$0;

   TileEntitySign$2(TileEntitySign var1, EntityPlayer var2) {
      this.this$0 = var1;
      this.val$playerIn = var2;
   }

   public String getName() {
      return this.val$playerIn.getName();
   }

   public IChatComponent getDisplayName() {
      return this.val$playerIn.getDisplayName();
   }

   public void addChatMessage(IChatComponent var1) {
   }

   public boolean canCommandSenderUseCommand(int var1, String var2) {
      return var1 <= 2;
   }

   public BlockPos getPosition() {
      return this.this$0.pos;
   }

   public Vec3 getPositionVector() {
      return new Vec3((double)this.this$0.pos.getX() + 0.5D, (double)this.this$0.pos.getY() + 0.5D, (double)this.this$0.pos.getZ() + 0.5D);
   }

   public World getEntityWorld() {
      return this.val$playerIn.getEntityWorld();
   }

   public Entity getCommandSenderEntity() {
      return this.val$playerIn;
   }

   public boolean sendCommandFeedback() {
      return false;
   }

   public void setCommandStat(CommandResultStats$Type var1, int var2) {
      TileEntitySign.access$000(this.this$0).func_179672_a(this, var1, var2);
   }
}
